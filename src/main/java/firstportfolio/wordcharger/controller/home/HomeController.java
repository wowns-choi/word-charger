package firstportfolio.wordcharger.controller.home;


import firstportfolio.wordcharger.DTO.EmailDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.NaverServerSendMeDataDTO;
import firstportfolio.wordcharger.repository.EmailMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.NaverMemberMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberMapper memberMapper;
    private final NaverMemberMapper naverMemberMapper;
    private final EmailMapper emailMapper;

    @PostMapping("/make-account")
    @ResponseBody
    public String makeAccount(@RequestBody NaverServerSendMeDataDTO naverServerSendMeDataDTO, HttpServletRequest request) {
        String name = naverServerSendMeDataDTO.getName();
        String email = naverServerSendMeDataDTO.getEmail();
        String id = naverServerSendMeDataDTO.getId();

        // 회원가입한 적이 있는가?
        // 이를 판단하기 위해서는 네이버 서버가 준 데이터 중 이메일 부분을 가지고 내 db 테이블 중 email 테이블에 일치하는 행이 있다면
        // 가입한 이력이 있는 회원이라고 판단할 것이다.
        // 그런데, 밑에서 보면 알겠지만, 네이버로그인을 함으로써 자동으로 회원가입되는 경우에는 email 테이블의 행이 안들어가기 때문에,
        // 이메일만으로는 회원가입이력이 있는지 여부를 판단하기에 부족하다.
        // 네이버로 로그인함으로써 자동회원가입한 경우를 판단하기 위해서, 네이버로 로그인함으로써 자동회원가입할 때에,
        // member 테이블의 user_id 컬럼값을 "nv_" + email의 앞부분 으로 하기로 했기 때문에,
        // member 테이블에서 user_id 컬럼값이 "nv_" + email 앞부분인 행이 없는 경우에야
        // 비로소, 회원가입이력이 없다고 판단할 것이다.

        // naver 서버가 준 데이터 중 email 값은 지금 "wowns590@naver.com" 이렇게 들어와있는데,
        // 내 email 테이블에는 email 컬럼으로 wowns590 이렇게만 들어가도록 설계했기 때문에,
        // 지금 email 변수에 들어있는 값을 @ 를 기준으로 앞에 부분인 wowns590 만 잘라서
        // email 테이블의 email 컬럼과 비교를 진행해야 한다.

        int atIndex = email.indexOf("@"); // wowns590@naver.com 이라는 문자열에서 @가 몇번째 위치해있는지 확인.
        String emailFragment = email.substring(0, atIndex); // substring 을 이용해서 잘라냄.
        String domain = email.substring(atIndex + 1); // @는 제외한 @ 뒤의 부분을 얻기 위해 atIndex에 1을 더함
        String beId = "nv_" + emailFragment; // 밑에 코드에 여러군데에서 사용됨.

        // email 테이블에서 email 컬럼값이,
        // emailFragment 이라는 변수와 동일한 행을 조회한다.
        EmailDTO emailDTO = emailMapper.emailCountByEmail(emailFragment);

        boolean flag = false;

        if (emailDTO != null) { // 만약 조회된 행이 있다면, 그 행의 domain 컬럼 값이 domain 변수와 동일한지 체크하고,
                                // domain 컬럼까지 동일하다면, flag 를 true 로 바꾸도록 함.
            if(emailDTO.getDomain().equals(domain)){
                flag = true;
            }
        }

        MemberJoinDTO member = memberMapper.findMemberById(beId);
        if (member != null) { // 만약 네이버로 로그인함으로써 자동으로 회원가입된 경우, flag 가 true가 되도록 함.
            flag = true;
        }

        // 일단, flag 의 값이 true 인지 false 인지에 따라 나눠야 한다.
        // flag 값이 여전히 false 라면,
        // 우리 웹사이트에 직접 회원가입을 하지도 않았고, 네이버로 로그인해서 자동으로 회원가입된적도 없다는 의미거든?
        // 그렇다면, 그냥 네이버서버가 준 데이터로 회원가입을 진행하면 된다.
        // 이때, db 중 naver_member 라는 테이블에도 행을 추가해줘야 한다. <- 용도는 뒤에 설명되어 있다.
        // 이 테이블의 컬럼 중 핵심은
        // 네이버 서버가 네이버 계정마다 부여한 고유한 키인 현재 id 라는 변수에 담겨있는 값을
        // 넣은 컬럼인 naver_id 라는 이름의 컬럼이다.

        if (flag == false) {
            // 회원가입 이력이 있는 경우
            // member 테이블에 행 넣기.
            Integer sequence = memberMapper.selectNextSequenceValue(); // id 컬럼 값
            String password = id; // password 컬럼 값 : 네이버서버가 네이버계정마다 부여한 고유한 값
            String userName = name; // user_name 컬럼 값 : 네이버서버가 준 데이터 중 name 값
            memberMapper.insertMember(sequence, beId, id, name); // 삽입.

            // naver_member 테이블에도 행을 넣을 것이다.
            // naver_member 테이블에는 총 3개의 컬럼이 있다.
            // id : pk 로서, 시퀀스값을 넣어줄 컬럼.
            // member_id : 방금 member 테이블에 insert 쿼리 실행할 때 썻던 member 테이블의 id 컬럼(pk) 값을 참조하는 외래키로 설정.
            // naver_id : 네이버 서버가 네이버계정마다 부여한 고유한 값을 담을 컬럼.
            Integer nextSequenceValue = naverMemberMapper.selectNextSequenceValue(); // id 컬럼 값
            naverMemberMapper.insertRow(nextSequenceValue, sequence, id);

            //세션 설정
            MemberJoinDTO findedMember = memberMapper.findMemberById(beId);
            HttpSession session = request.getSession(true);
            session.setAttribute("loginedMember", findedMember);

            return "createAccount";
        } else{
            // flag 가 true 라면,
            // 우리 웹사이트에 직접 회원가입을 했든, 혹은 네이버로 로그인함으로써 자동으로 회원가입을 했든
            // 우리 웹사이트에 회원이라는 소리이다.

            // 이 경우 2가지로 분류해야 한다.
            // 1. 우리 웹사이트에 직접 회원가입을 한 경우.
            // 2. 네이버로 로그인함으로써 자동으로 회원가입을 한 경우.

            // 1번인지 2번인지는 어떻게 판단할 것인가?
            // 네이버가 네이버계정마다 부여한 고유한 값으로 db 테이블 중 naver_member 라는 테이블에서
            // 행을 조회한다. 만약, 조회된 행의 개수가 0이면, 우리 웹사이트에 직접 회원가입을 한 경우이고,
            // 1이상이면 네이버로 로그인함으로써 자동으로 회원가입된 경우라고 판단할 수 있다.

            // 1. 우리 웹사이트에 직접 회원가입을 한 경우 에는 새로운 계정을 만들지 않는다.
            // 그리고, alreadyExistByWordCharger 라는 문자열을 되돌려 줄것이다.
            // 그러면, fetch 구문에서 alreadyExistByWordCharger 라는 값이 온 경우,
            // alert('회원가입한 이력이 확인되었습니다. 그 아이디로 로그인해주세요.') 라고 띄워주고, loginForm.jsp 이 보여지도록 할 것.

            // 2. 네이버로 로그인함으로써 자동으로 회원가입을 한 경우 에는
            // 세션 설정을 해주고, alreadyExistsByNaver 라는 문자열을 되돌려 줄것이다.
            // 그러면, fetch 구문에서 alreadyExistsByNaver 라는 값이 온 경우,
            // window.location.href="" 를 이용해서 / 라는 url 을 매핑하는 컨트롤러로 get 요청을 하게 할 것이다.

            Integer rowCount = naverMemberMapper.rowCount(id);
            if (rowCount == 0) {
                // 1. 우리 웹사이트에 직접 회원가입을 한 경우.
                return "alreadyExistByWordCharger";

            } else{
                // 2. 네이버로 로그인함으로써 자동으로 회원가입을 한 경우, 세션 설정.
                MemberJoinDTO findedMember = memberMapper.findMemberById(beId); //네이버로 로그인함으로써 자동으로 회원가입할 때, user_id 컬럼값으로 beId 변수를 썻기 때문에 beId를 통해 memeber 테이블의 행을 조회함.
                HttpSession session = request.getSession(true);
                session.setAttribute("loginedMember", findedMember);

                // alreadyExistsByNaver 리턴.
                return "alreadyExistsByNaver";

            }

        }
    }

    @RequestMapping("/")
    public String homeControllerMethod(HttpServletRequest request, Model model
                                       ) {
        //세션 자체가 없다.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/home/home";
        }

        //세션은 있는데, loginedMember 라는 키의 세션은 없다.
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        log.info("lognedMember========={}", loginedMember);
        log.info("lognedMember========={}", loginedMember);
        log.info("lognedMember========={}", loginedMember);
        log.info("lognedMember========={}", loginedMember);

        if (loginedMember == null) {
            return "/home/home";
        }

        //loginedMember 라는 키의 세션이 있다.
        return "/home/loginedHome";
    }


}
