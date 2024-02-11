package firstportfolio.wordcharger.controller.home;


import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String homeControllerMethod(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("session==null");
            return "/home/home";
        }
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");

        if (loginedMember == null) {
            log.info("session이 있긴 한데, loginedMember 라는 키의 세션은 없다");
            return "/home/home";
        }
        log.info("세션이 있고, loginedMember라는 키의 세션이 있다");

        return "/home/loginedHome2";
    }


}
