package firstportfolio.wordcharger.sevice.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional // 하나라도 안들어가면 rollback 되도록 함.
@Slf4j
@Service
@RequiredArgsConstructor
public class InsertMemberService {
    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;
    private final TermsOfAgreementMapper termsOfAgreementMapper;
    private final EmailMapper emailMapper;

    public void insertMember(MemberJoinDTO changedMemberJoinDTO){

        String userId = changedMemberJoinDTO.getUserId();
        String password = changedMemberJoinDTO.getPassword();
        String userName = changedMemberJoinDTO.getUserName();

        String zipCode = changedMemberJoinDTO.getZipCode();
        String streetAddress = changedMemberJoinDTO.getStreetAddress();
        String address = changedMemberJoinDTO.getAddress();
        String detailAddress = changedMemberJoinDTO.getDetailAddress();
        String referenceItem = changedMemberJoinDTO.getReferenceItem();

        String phoneNumberStart = changedMemberJoinDTO.getPhoneNumberStart();
        String phoneNumberMiddle = changedMemberJoinDTO.getPhoneNumberMiddle();
        String phoneNumberEnd = changedMemberJoinDTO.getPhoneNumberEnd();

        String myCheckbox1 = changedMemberJoinDTO.getMyCheckbox1();
        String myCheckbox2 = changedMemberJoinDTO.getMyCheckbox2();
        String myCheckbox3 = changedMemberJoinDTO.getMyCheckbox3();

        // 1. MEMBER_SEQUENCE 라는 이름의 시퀀스 다음값을 얻어옵니다.
        // 이 시퀀스 값을 이용해서, MEMBER ADDRESS PHONE TERMS_OF_AGREEMENT 테이블에 행을 삽입합니다.
            Integer nextSequenceValue = memberMapper.selectNextSequenceValue();

        // 2. MEMBER 테이블에 행을 삽입합니다. 이때 얻어온 시퀀스값(nextSequenceValue)으로 pk인 컬럼값을 채웁니다.
            memberMapper.insertMember(nextSequenceValue, userId, password, userName);

        // 3. ADDRESS 테이블에 행을 삽입합니다. 이때 얻어온 시퀀스값(nextSequenceValue)으로 pk인 컬럼값을 채웁니다.
            addressMapper.insertAddress(nextSequenceValue, zipCode, streetAddress,
                    address, detailAddress, referenceItem);

        // 4. PHONE 테이블에 행을 삽입합니다. 이때 얻어온 시퀀스값(nextSequenceValue)으로 pk인 컬럼값을 채웁니다.
            phoneMapper.insertPhone(nextSequenceValue, phoneNumberStart, phoneNumberMiddle, phoneNumberEnd);

        // 5. TERMS_OF_AGREEMENT 테이블에 행을 삽입합니다. 이때 얻어온 시퀀스값(nextSequenceValue)으로 pk인 컬럼값을 채웁니다.
            termsOfAgreementMapper.insertTermsOfAgreement(nextSequenceValue, myCheckbox1, myCheckbox2, myCheckbox3);

        // 6. EMAIL 테이블에 행을 삽입합니다.
        // changedMemberJoinDTO 의 email 필드는 사용자가 입력한 이메일아이디가 바인딩 되어 있다. 그냥 이대로 EMAIL 테이블에 넣으면 된다.
        // 문제는 도메인이다. changedMemberJoinDTO 에는 domain 필드와 customEmailDomain 필드가 있다.
        // 만약, 사용자가 '직접입력' 을 누른 경우, domain 필드에는 "custom" 이라는 문자열이 바인딩 되어 있고
        // customEmailDomain 필드에 사용자가 직접 입력한 도메인이 저장되어 있을 것이다.
        // 만약, 사용자가 '직접입력' 을 누르지 않고, 선택지 중 naver.com 이라든지, daum.net 같은 걸 select했다면,
        // domain 필드에는 "naver.com" 또는 "daum.net" 이런 식으로 들어와 있을 것이다.
        // 따라서, 어떤식으로 할것이냐면, 만약 changedMemberJoinDTO 객체의 domain 필드 값이 "custom" 이면
        // domain 변수에 customEmailDomain 필드값을 넣을 것이고,
        // 만약 changedMemberJoinDTO 객체의 domain 필드 값이 "custom" 이 아니라면,
        // domain 변수에 changedMemberJoinDTO 객체의 domain 필드값을 넣을 것이다.

            String email = changedMemberJoinDTO.getEmail();
            String domain = null;

            if(changedMemberJoinDTO.getEmailDomain().equals("custom")){
                domain = changedMemberJoinDTO.getCustomEmailDomain();// 이걸 도메인으로
            } else{
                domain = changedMemberJoinDTO.getEmailDomain(); // 이걸 도메인으로
            }

            emailMapper.insertEmail(nextSequenceValue, email, domain);
    }
}
