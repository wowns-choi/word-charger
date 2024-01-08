package firstportfolio.wordcharger.controller.joinandlogin;

import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {
    private final MemberMapper memberMapper;


    private static final String LENGTH_PATTERN = "^.{8,16}$";
    private static final String LETTER_PATTERN = ".*[A-Za-z].*";
    private static final String NUMBER_PATTERN = ".*[0-9].*";
    private static final String SPECIAL_CHAR_PATTERN = ".*[!@#&()–[{}]:;',?/*~$^+=<>].*";


    @GetMapping("/Join-form")
    public String joinFormControllerMethod(HttpServletRequest request, Model model){

        model.addAttribute("memberDTO", new MemberDTO());
        return "/login/joinForm";

    }

    @PostMapping("check-user-id")
    @ResponseBody
    public Map<String,Boolean> duplicateIdValidationControllerMethod(@RequestBody Map<String,String> request){

        String userId = request.get("userId");
        log.info("userId={}", userId);
        MemberDTO memberById = memberMapper.findMemberById(userId);
        if (memberById == null) {
            return Collections.singletonMap("isAvailable", true);
        }else{
            return Collections.singletonMap("isAvailable", false);
        }


    }

    @PostMapping("check-user-password")
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
