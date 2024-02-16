package firstportfolio.wordcharger.sevice.login;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;

    public String loginCheck(String id, String password, HttpServletRequest request){

        //db Member 테이블에서 아이디를 찾아와서 여기있는 password랑 같은지 봐야겠지.
        MemberJoinDTO findedMember = memberMapper.findMemberById(id);
        try {
            if (!findedMember.getPassword().equals(password)) {
                request.setAttribute("passwordIncorrectMessage", "비밀번호가 일치하지 않습니다.");
                return "/login/loginForm";
            }

        } catch (NullPointerException e) {
            request.setAttribute("idIncorrectMessage", "존재하지 않는 아이디입니다.");
            return "/login/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("loginedMember", findedMember);
        return "redirect:/";
    }
}
