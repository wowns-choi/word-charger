package firstportfolio.wordcharger.controller.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {
    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;
    private final TermsOfAgreementMapper termsOfAgreementMapper;

    private static final String LENGTH_PATTERN = "^.{8,16}$";
    private static final String LETTER_PATTERN = ".*[A-Za-z].*";
    private static final String NUMBER_PATTERN = ".*[0-9].*";
    private static final String SPECIAL_CHAR_PATTERN = ".*[!@#&()–[{}]:;',?/*~$^+=<>].*";


    @GetMapping("/terms-of-use")
    public String getTermsOfUseControllerMethod(){
        return "/login/termsOfUse";
    }
    @PostMapping("/terms-of-use")
    public String postTermsOfUseControllerMethod(@RequestParam(required = false) String myCheckbox1,@RequestParam(required = false) String myCheckbox2, @RequestParam(required = false) String myCheckbox3){
        log.info("================{}", myCheckbox1);
        log.info("================{}", myCheckbox2);
        log.info("================{}", myCheckbox3);
        return "redirect:/Join-form?" + "myCheckbox1=" + myCheckbox1+ "&" + "myCheckbox2=" + myCheckbox2+ "&"  + "myCheckbox3=" + myCheckbox3;
    }

    @GetMapping("/Join-form")
    public String getJoinFormControllerMethod(HttpServletRequest request, Model model,
        @RequestParam String myCheckbox1, @RequestParam String myCheckbox2, @RequestParam String myCheckbox3
    ){

        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        memberJoinDTO.setMyCheckbox1(myCheckbox1);
        memberJoinDTO.setMyCheckbox2(myCheckbox2);
        memberJoinDTO.setMyCheckbox3(myCheckbox3);


        model.addAttribute("memberDTO", memberJoinDTO);
        return "/login/joinForm";
    }

    @PostMapping("/Join-form")

    public String postJoinFormControllerMethod (@Valid @ModelAttribute MemberJoinDTO memberJoinDTO, BindingResult bindingResult){
        log.info("phoneNumberStart==============={}", memberJoinDTO.getPhoneNumberStart().equals(""));
        log.info("phoneNumberStart==============={}", memberJoinDTO.getPhoneNumberStart()==null);


//        if (memberJoinDTO.getUserId().equals("")) {
        if (memberJoinDTO.getUserId().equals("") ) {
            bindingResult.rejectValue("userId", null, "아이디를 입력 해주세요");
        }
        if (memberJoinDTO.getPassword().equals("")) {
            bindingResult.rejectValue("password", null, "비밀번호를 입력해주세요");
        }
        if (memberJoinDTO.getUserName().equals("")) {
            bindingResult.rejectValue("userName", null, "이름을 입력해주세요");
        }

        if (memberJoinDTO.getZipCode().equals("")|| memberJoinDTO.getStreetAddress().equals("")|| memberJoinDTO.getAddress().equals("")) {
            bindingResult.rejectValue("zipCode", null, "우편번호를 찾기를 통해 주소를 찾아주세요.");
        }
        if (memberJoinDTO.getPhoneNumberStart().equals("") || memberJoinDTO.getPhoneNumberMiddle().equals("") || memberJoinDTO.getPhoneNumberEnd().equals("")) {
            bindingResult.rejectValue("phoneNumberStart", null, "전화번호를 입력해주세요");
        }

        if (bindingResult.hasErrors()) {
            return "/login/joinForm";
        }

        //checkbox value : on => 1 , null => 0  변환
        if(memberJoinDTO.getMyCheckbox1().equals("on")){
            memberJoinDTO.setMyCheckbox1("1");
        } else{
            memberJoinDTO.setMyCheckbox1("0");
        }

        if(memberJoinDTO.getMyCheckbox2().equals("on")){
            memberJoinDTO.setMyCheckbox2("1");
        } else{
            memberJoinDTO.setMyCheckbox2("0");
        }


        if(memberJoinDTO.getMyCheckbox3().equals("on")){
            memberJoinDTO.setMyCheckbox3("1");
        } else{
            memberJoinDTO.setMyCheckbox3("0");
        }

        //db 에 insert 하는 쿼리 필요.
        //member 테이블에 id password userName insert.
        memberMapper.insertMember(memberJoinDTO.getUserId(), memberJoinDTO.getPassword(), memberJoinDTO.getUserName());
        //방금 넣은 member테이블의 행을 다시 꺼내와서, sequence로 넣어
        MemberJoinDTO findMember = memberMapper.findMemberById(memberJoinDTO.getUserId());

        addressMapper.insertAddress(findMember.getId(), memberJoinDTO.getZipCode(), memberJoinDTO.getStreetAddress(),
        memberJoinDTO.getAddress(), memberJoinDTO.getDetailAddress(), memberJoinDTO.getReferenceItem());
        phoneMapper.insertPhone(findMember.getId(), memberJoinDTO.getPhoneNumberStart(), memberJoinDTO.getPhoneNumberMiddle(), memberJoinDTO.getPhoneNumberEnd());
        termsOfAgreementMapper.insertTermsOfAgreement(findMember.getId(), memberJoinDTO.getMyCheckbox1(), memberJoinDTO.getMyCheckbox2(), memberJoinDTO.getMyCheckbox3());


        return "/home/home";
    }

    @PostMapping("check-user-id") // 아이디 중복확인 해주는 컨트롤러
    @ResponseBody
    public Map<String,Boolean> duplicateIdValidationControllerMethod(@RequestBody Map<String,String> request){
        String userId = request.get("userId");
        log.info("userId={}", userId);
        MemberJoinDTO memberById = memberMapper.findMemberById(userId);
        if (memberById == null) {
            return Collections.singletonMap("isAvailable", true);
        }else{
            return Collections.singletonMap("isAvailable", false);
        }
    }

    @PostMapping("check-user-password") // 비밀번호가 8~16글자인지, 영어대/소문자와 특수문자와 숫자 를 모두 사용해서 비밀번호를 조합했는지, 검증해주는 컨트롤러
    @ResponseBody
    public Map<String, Boolean> availablePasswordValidationControllerMethod(@RequestBody Map<String,String> request){
        String userPassword = request.get("userPassword");

        if (!userPassword.matches(LENGTH_PATTERN)) {
            return Collections.singletonMap("insufficientLength", true);
        }else if (!userPassword.matches(LETTER_PATTERN)) {
            return Collections.singletonMap("englishNotIncluded", true);
        }else if(!userPassword.matches(NUMBER_PATTERN)){
            return Collections.singletonMap("numberNotIncluded", true);
        } else if (!userPassword.matches(SPECIAL_CHAR_PATTERN)) {
            return Collections.singletonMap("specialCharactersNotIncluded", true);
        }else{
            return Collections.singletonMap("무의미한전달", true);
        }





    }


}
