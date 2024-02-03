package firstportfolio.wordcharger.logininterceptor;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    private MemberMapper memberMapper;

    @Autowired
    public LoginInterceptor(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        //세션이 존재하니?
        if (session == null) {
            log.info("세션이 없다");
            response.sendRedirect("/?modal=true");
            return false;
        }

        //키가 loginedMember 인 세션이 존재하니?
        MemberJoinDTO loginedMember = (MemberJoinDTO)session.getAttribute("loginedMember");
        if(loginedMember==null){
            log.info("세션이 있긴한데, loginedMember 라는 세션은 없다");
            response.sendRedirect("/?modal=true");
            return false;
        }
        String id = loginedMember.getUserId();
        //비밀번호가 맞니?
        MemberJoinDTO findedMember = memberMapper.findMemberById(id);
        if (findedMember.getPassword().equals(loginedMember.getPassword())) {
            return true;
        }
        log.info("비밀번호가 맞지않다. 해커일듯");
        response.sendRedirect("/?modal=true");
        return false;
    }

}
