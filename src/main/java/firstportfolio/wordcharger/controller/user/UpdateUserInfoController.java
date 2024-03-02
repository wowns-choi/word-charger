package firstportfolio.wordcharger.controller.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.AddressMapper;
import firstportfolio.wordcharger.repository.EmailMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PhoneMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UpdateUserInfoController {
    private final MemberMapper memberMapper;
    private final PhoneMapper phoneMapper;
    private final AddressMapper addressMapper;
    private final EmailMapper emailMapper;

    @GetMapping("/update-user-info")
    public String updateUserInfoControllerMethod(@RequestParam String id, Model model) {

        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        MemberAllDataFindDTO memberAllDataFindDTO = memberMapper.findMemberTotalData(id);

        log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa=============={}", memberAllDataFindDTO);
        model.addAttribute("memberAllData", memberAllDataFindDTO);

        return "/user/userInfoUpdate";

    }

    @PostMapping("/update-user-info")
    @ResponseBody
    public String updateUserInfoPostControllerMethod(@ModelAttribute MemberAllDataFindDTO memberAllDataFindDTO) {

        log.info("ㅁㄴ엄너옴ㄴ어ㅏㅣ롬ㄴ이ㅏㅓ롬ㄴ이ㅓ롬ㄴㅇㄹ {}", memberAllDataFindDTO);

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

        MemberAllDataFindDTO aaaa = memberMapper.findMemberTotalData(String.valueOf(id));
        log.info("aaa ============================={}", aaaa);
        return "hello";
    }

    @GetMapping("/password-validation")
    public String passwordValidationControllerMethod() {
        return "/user/pw-update-validation";
    }

    @PostMapping("/password-validation")
    public String passwordValidationControllerMethod2(@ModelAttribute MemberAllDataFindDTO memberAllDataFindDTO, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        MemberJoinDTO memberJoinDTO = (MemberJoinDTO) session.getAttribute("loginedMember");
        String previousPw = memberMapper.findPwById(memberJoinDTO.getId());

        log.info("previousPw============={}", previousPw);
        log.info("================={}", memberAllDataFindDTO.getPassword());

        if (memberAllDataFindDTO.getPassword().equals(previousPw)) {
            return "/user/pw-update";
        } else {
            model.addAttribute("wrong", "비밀번호가 일치하지 않습니다.");
            return "/user/pw-update-validation";
        }

    }

    @PostMapping("/password-update")
    @ResponseBody
    public String passwordUpdateControllerMethod(@RequestBody MemberAllDataFindDTO memberAllDataFindDTO, HttpServletRequest request) {

        try{
            HttpSession session = request.getSession(false);
            MemberJoinDTO memberJoinDTO = (MemberJoinDTO) session.getAttribute("loginedMember");
            memberMapper.updatePassword(memberJoinDTO.getId(), memberAllDataFindDTO.getPassword());
            return "1";
        }catch(Exception e){
            return "0";
        }
    }


}
