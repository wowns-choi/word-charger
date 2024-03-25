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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    //해싱을 위해서.
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login-form")
    public String getLoginFormControllerMethod(Model model, @RequestParam(required = false) String goToNaverLogin) {
        if (goToNaverLogin != null) {
            model.addAttribute("goToNaverLogin", goToNaverLogin);
        }
        model.addAttribute("loginDTO", new LoginDTO());
        return "/login/loginForm";
    }

    @GetMapping("/login-form-alert-version")
    public String getLoginFormAlertVersionControllerMethod(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("alreadyMember", "회원가입한 아이디로 로그인해주세요.");
        return "/login/loginForm";
    }

    @PostMapping("/login-form")
    public String postLoginFormControllerMethod(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);

            return "/login/loginForm";
        }

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
