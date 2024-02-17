package firstportfolio.wordcharger.sevice.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.AddressMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PhoneMapper;
import firstportfolio.wordcharger.repository.TermsOfAgreementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class InsertMemberService {
    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;
    private final TermsOfAgreementMapper termsOfAgreementMapper;

    public void insertMember(MemberJoinDTO changedMemberJoinDTO){
        //db 에 insert 하는 쿼리 필요.
        //member 테이블에 id password userName insert.
        memberMapper.insertMember(changedMemberJoinDTO.getUserId(), changedMemberJoinDTO.getPassword(), changedMemberJoinDTO.getUserName());
        //방금 넣은 member테이블의 행을 다시 꺼내와서, sequence로 넣어
        MemberJoinDTO findMember = memberMapper.findMemberById(changedMemberJoinDTO.getUserId());

        addressMapper.insertAddress(findMember.getId(), changedMemberJoinDTO.getZipCode(), changedMemberJoinDTO.getStreetAddress(),
                changedMemberJoinDTO.getAddress(), changedMemberJoinDTO.getDetailAddress(), changedMemberJoinDTO.getReferenceItem());
        phoneMapper.insertPhone(findMember.getId(), changedMemberJoinDTO.getPhoneNumberStart(), changedMemberJoinDTO.getPhoneNumberMiddle(), changedMemberJoinDTO.getPhoneNumberEnd());
        termsOfAgreementMapper.insertTermsOfAgreement(findMember.getId(), changedMemberJoinDTO.getMyCheckbox1(), changedMemberJoinDTO.getMyCheckbox2(), changedMemberJoinDTO.getMyCheckbox3());
    }
}
