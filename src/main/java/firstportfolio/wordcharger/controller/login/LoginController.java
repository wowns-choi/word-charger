package firstportfolio.wordcharger.controller.login;

import firstportfolio.wordcharger.DTO.LoginDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.sevice.login.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberMapper memberMapper;

    private final LoginService loginService;

    @GetMapping("/login-form")
    public String getLoginFormControllerMethod(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/login/loginForm2";
    }

    @PostMapping("/login-form")
    public String postLoginFormControllerMethod(@ModelAttribute LoginDTO loginDTO, HttpServletRequest request) {
        String id = loginDTO.getId();
        String password = loginDTO.getPassword();
        String viewPath = loginService.loginCheck(id, password, request);
        return viewPath;
    }

    @GetMapping("/logout")
    public String logoutControllerMethod (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            //내 서버에 있는 사용자의 세션 통에 있는 세션데이터를 삭제하는 것 + 세션 통 자체도 지워버림.
            session.invalidate();
        }
        return "redirect:/";
    }
}
