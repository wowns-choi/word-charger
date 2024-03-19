package firstportfolio.wordcharger.controller.login;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
public class FindUserIdController {
    private final MemberMapper memberMapper;

    @GetMapping("/find-user-id")
    public String findUserId(){
        return "/login/findUserId";
    }

    @PostMapping("/email-validation")
    @ResponseBody
    public String emailValidation(@RequestBody MemberAllDataFindDTO memberAllDataFindDTO) {

        // userName=최재준, email=wowns590@naver.com 이렇게 2가지 데이터 들어옴. 나머지는 모두 null.

        // 여기서 해줄 건, MEMBER 테이블에서 해당 email을 가진 계정의 user_name 컬럼 값을 조회한다.
        // 그게 파라미터로 넘어온 userName 과 일치한다면 success 를, 불일치한다면 fail 을 리턴해줄 것이다.

        // 1. 현재 email 필드 값에는 이메일 뿐만 아니라 도메인까지 있음. 따라서, 분리한다.
        String emailPlusDomain = memberAllDataFindDTO.getEmail();
        String[] split = emailPlusDomain.split("@");

        String email = split[0];
        String domain = split[1];

        // 2. MEMBER 테이블에서 select 해보자.
        String findUserName = memberMapper.findUserNameByEmailAndDomain(email, domain);
        log.info("findUserName==={}", findUserName);
        log.info("memberAllDataFindDTO.getUserName()=={}", memberAllDataFindDTO.getUserName());

        String userName = memberAllDataFindDTO.getUserName();

        //미리 만들어둔 계정 아이디 : findUserId
        // 이름 : 최재주니
        // 이메일 : 최재주니@naver.com

        if (findUserName.equals(userName)) {
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/show-user-id")
    public String showUserId(@RequestParam String userName, @RequestParam String email, Model model){

        log.info("userName============{}", userName);
        log.info("email========={}", email);
        // 넘겨온 정보로 member테이블의 user_id 컬럼값을 찾아올거야.
        // 1. 지금 email 안에는 email + domain 모두가 들어있기 때문에, 분리시킬 것.
        String[] split = email.split("@");
        String email2 = split[0];
        String domain = split[1];

        // 2. 분리한 email2 domain userName 을 가지고 member 테이블에서 user_id 컬럼값을 조회한다.
        String findUserId = memberMapper.findUserIdByUserNameAndEmailAndDomain(userName, email2, domain);
        model.addAttribute("findUserId", findUserId);
        return "/login/showUserId";
    }

}
