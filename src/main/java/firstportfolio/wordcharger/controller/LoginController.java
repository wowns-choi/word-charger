package firstportfolio.wordcharger.controller;

import firstportfolio.wordcharger.DTO.LoginDTO;
import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberMapper memberMapper;

    @GetMapping("/loginForm")
    public String getLoginFormController(HttpServletRequest request) {
        request.setAttribute("loginDTO", new LoginDTO());
        return "/login/loginForm";
    }

    @PostMapping("/loginForm")
    public String postLoginFormController(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request) {
        String id = loginDTO.getId();
        String password = loginDTO.getPassword();
        //db Member 테이블에서 아이디를 찾아와서 여기있는 password랑 같은지 봐야겠지.
        MemberDTO findedMember = memberMapper.findMemberById(id);
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

    @GetMapping("/logout")
    public String logoutController (HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            //내 서버에 있는 사용자의 세션 통에 있는 세션데이터를 삭제하는 것 + 세션 통 자체도 지워버림.
            session.invalidate();
        }
        return "redirect:/";




    }
}
