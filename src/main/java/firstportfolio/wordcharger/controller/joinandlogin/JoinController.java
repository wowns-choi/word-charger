package firstportfolio.wordcharger.controller.joinandlogin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    @GetMapping("/JoinController")
    public String joinFormControllerMethod(HttpServletRequest request){



        return "";

    }

}
