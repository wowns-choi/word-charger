package firstportfolio.wordcharger.controller.join;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.sevice.join.IdDuplicateCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

import static firstportfolio.wordcharger.controller.join.RegExConstants.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CheckUserController {

    private final MemberMapper memberMapper;
    private final IdDuplicateCheckService idDuplicateCheckService;
    @PostMapping("check-user-id")
    @ResponseBody
    public Map<String,Boolean> duplicateIdValidationControllerMethod(@RequestBody Map<String,String> request){



        String userId = request.get("userId");
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

        if (!userPassword.matches(RegExConstants.LENGTH_PATTERN)) {
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
