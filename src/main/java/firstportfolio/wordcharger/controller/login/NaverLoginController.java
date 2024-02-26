package firstportfolio.wordcharger.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NaverLoginController {

    @GetMapping("/naver-callback")
    public String naverController(){
        return "/login/naverLogin";
    }

}
