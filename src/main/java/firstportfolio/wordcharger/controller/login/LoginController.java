package firstportfolio.wordcharger.controller.login;

import firstportfolio.wordcharger.DTO.LoginDTO;
import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.sevice.login.GetKakaoTokenService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    // 해싱을 위해서.
    private final PasswordEncoder passwordEncoder;

    //카카오 토큰을 얻기 위함.
    private final GetKakaoTokenService getKakaoTokenService;
    private final MemberMapper memberMapper;

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

    @RequestMapping("/get-auth-code")
    public String getAuthCode(@RequestParam("code") String code, Model model, HttpServletRequest request){
        log.info("code=============={}", code);
        // code에는 뭐가 들어왔을까? MvMX-FLIi4ulZqhlxLhGshltYfWuujoStaKSkXs3Aqpzr2yHBtHvjd-WT-IKKwzSAAABjrQ-Sx1tZc76WqiBKA
        // 이걸 통해 HTTP요청(토큰달라는 요청임)을 카카오 서버에 보낸다

        Map<String, String> returnMap = getKakaoTokenService.getKakaoToken(code);

        if (returnMap.get("signatureError") != null || returnMap.get("decodingError") != null) {
            //서명이 일치하지 않았거나, 도착한 JWT 디코딩하다가 에러가 난 경우
            model.addAttribute("kakaoError", "인증 과정 중 오류발생");
            return "redirect:/login-form";
        }

        // 뭘 해줘야 하냐면, 이미 이전에 카카오톡으로 로그인해서 회원가입이 된 경우가 있는지 확인해줘야 해.
        String userId = returnMap.get("userId");
        String password = returnMap.get("password");
        String userName = returnMap.get("userName");

        // 이메일로 그런 작업을 처리하고 싶었지만, 이메일 얻으려면 사업자 등록 하고 심사받아야한다고 해서 아이디로 MEMBER테이블에 동일한 아이디가 있는지 확인
        MemberJoinDTO findMember = memberMapper.findMemberById(userId);

        HttpSession session = request.getSession(true);

        log.info("aaaaaaaaaaaaaaaaaaaaaaaa");

        if (findMember != null) {
            log.info("abbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            // 동일한 아이디가 있는 경우 == 이전에 카톡으로 로그인해서 내 애플리케이션에 회원가입이 되어 있는 경우
            // 세션객체에 로그인한 사용자의 데이터를 담아준다.
            session.setAttribute("loginedMember", findMember);
            return "redirect:/";

        } else{
            log.info("cccccccccccccccccccccccccc");
            // 동일한 아이디가 없는 경우 == 내 애플리케이션에서 처음으로 카톡 로그인한 경우
            // 회원가입 진행 + 세션객체에 로그인한 사용자의 데이터를 담아준다.
            Integer sequence = memberMapper.selectNextSequenceValue();
            memberMapper.insertMember(sequence, userId, password, userName);
            // 나머지 주소라든지, 핸드폰번호같은 건 필요할 때 받도록 하면 됨.
            MemberJoinDTO madeMember = memberMapper.findMemberById(userId);
            session.setAttribute("loginedMember", madeMember);
            return "redirect:/";
        }





    }



}
