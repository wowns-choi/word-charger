package firstportfolio.wordcharger.sevice.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.repository.AddressMapper;
import firstportfolio.wordcharger.repository.EmailMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PhoneMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserInfoService {

    private final MemberMapper memberMapper;
    private final PhoneMapper phoneMapper;
    private final AddressMapper addressMapper;
    private final EmailMapper emailMapper;
    public void updateUserInfo(MemberAllDataFindDTO memberAllDataFindDTO, Model model){
        Integer id = memberAllDataFindDTO.getId();// update 해야하는 user 의 Member테이블 id 컬럼값
        MemberAllDataFindDTO previousUserInfo = memberMapper.findMemberTotalData(String.valueOf(id));

        if (!(memberAllDataFindDTO.getPhoneNumStart().equals(previousUserInfo.getPhoneNumStart()) &&
                memberAllDataFindDTO.getPhoneNumMiddle().equals(previousUserInfo.getPhoneNumMiddle()) &&
                memberAllDataFindDTO.getPhoneNumEnd().equals(previousUserInfo.getPhoneNumEnd()))) {

            phoneMapper.updatePhone(id, memberAllDataFindDTO.getPhoneNumStart(), memberAllDataFindDTO.getPhoneNumMiddle(), memberAllDataFindDTO.getPhoneNumEnd());
        }

        if (!(
                memberAllDataFindDTO.getZipCode().equals(previousUserInfo.getZipCode()) &&
                        memberAllDataFindDTO.getStreetAddress().equals(previousUserInfo.getStreetAddress()) &&
                        memberAllDataFindDTO.getAddress().equals(previousUserInfo.getAddress()) &&
                        memberAllDataFindDTO.getDetailAddress().equals(previousUserInfo.getDetailAddress()) &&
                        memberAllDataFindDTO.getReferenceItem().equals(previousUserInfo.getReferenceItem())
        )) {

            addressMapper.updateAddress(id,
                    memberAllDataFindDTO.getZipCode(),
                    memberAllDataFindDTO.getStreetAddress(),
                    memberAllDataFindDTO.getAddress(),
                    memberAllDataFindDTO.getDetailAddress(),
                    memberAllDataFindDTO.getReferenceItem());
        }

        if (!memberAllDataFindDTO.getEmail().equals("")) {
            String domain = "";
            //수정은 했음. 도메인으로 써야 되는 거 경우의 수는 2개야.
            if (!memberAllDataFindDTO.getCustomEmailDomain().equals("")) {
                domain = memberAllDataFindDTO.getCustomEmailDomain();
            } else {
                domain = memberAllDataFindDTO.getDomain();
            }
            emailMapper.updateEmail(id, memberAllDataFindDTO.getEmail(), domain);
        }

        model.addAttribute("userInfoUpdatesuccess", "userInfoUpdatesuccess");

    }

}
