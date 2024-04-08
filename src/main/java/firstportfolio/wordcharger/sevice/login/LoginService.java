package firstportfolio.wordcharger.sevice.login;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Slf4j
// @Transactional 안쓴다. select 쿼리 밖에 없음.
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShowOrdersService showOrdersService;

    public String loginCheck(String id, String password, HttpServletRequest request){

        // 현재 id 에는 사용자가 아이디로 입력한 값이 들어와있다.
        // 이 id 값을 가지고 db 에 member테이블에 동일한 user_id 컬럼의 값을 가진 행을 조회한다.
        MemberJoinDTO findedMember = memberMapper.findMemberById(id);

        // 2가지 경우가 있을 수 있다.
        // 그런 user_id 컬럼값을 가진 행이 존재하는 경우와 존재하지 않는 경우이다.
        try {
            // 존재할지 존재하지 않을지 모르는 findedMember 변수에 담긴 객체의 password 필드값을 꺼내와본다.
            // 만약 존재한다면 아무런 문제가 없겠지만, 존재하지 않는 경우 NullPointerException 예외가 터진다. -> catch문으로 이동
            String DBpassword = findedMember.getPassword();
            // 존재하는 경우, BCryptPasswordEncoder 타입 객체의 matches() 메서드를 이용해서
            // 사용자가 입력한 raw한 password 와
            // db에 저장되어 있던 해싱되어 들어가 있던 password 가 일치하는지 검사한다.
            if (!passwordEncoder.matches(password, DBpassword)) {
                // 일치하지 않는 경우 request 스코프에 아래와 같은 데이터를 담아주고, 다시 loginForm.jsp 로 이동되도록 "/login/loginForm" 이라는 문자열을 리턴해준다.
                request.setAttribute("passwordIncorrectMessage", "비밀번호가 일치하지 않습니다.");
                return "/login/loginForm";
            }

        } catch (NullPointerException e) {
            // request 스코프에 아래와 같은 데이터를 담아주고, 다시 loginForm.jsp 로 이동되도록 "/login/loginForm"이라는 문자열을 리턴해준다.
            request.setAttribute("idIncorrectMessage", "존재하지 않는 아이디입니다.");
            return "/login/loginForm";
        }

        // 여기까지 코드가 도착했다는 것은, 아이디와 비밀번호가 일치한다는 이야기이다.
        // 아이디와 비밀번호가 일치하는 경우, session 스코프에 "loginedMember" 라는 키에 db에서 조회된 MEMBER테이블의 행을 담아준다.
        HttpSession session = request.getSession(true);
        session.setAttribute("loginedMember", findedMember);

        //만약, 관리자라면, 다른 흐름을 만들어줘야 함.
        if(id.equals("manager1")){
            // 어떤 페이지를 처음에 보여줄 것인가?
            // 처음에는 orders 테이블의 모든 행을 마치 게시판 글처럼 보여줄 것이다.
            showOrdersService.showOrders(1);
            return "/manager/managerHome";
        }

        //일반 사용자인 경우.
        return "redirect:/";
    }
}
