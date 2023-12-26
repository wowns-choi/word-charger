package firstportfolio.wordcharger.controller;


import firstportfolio.wordcharger.DTO.MemberDTO;
import firstportfolio.wordcharger.repository.CountMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final CountMapper countMapper;

    @GetMapping("/")
    public String homeController(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("session==null");
            return "/home/home";
        }
        MemberDTO loginedMember = (MemberDTO) session.getAttribute("loginedMember");

        if (loginedMember == null) {
            log.info("session이 있긴 한데, loginedMember 라는 키의 세션은 없다");
            return "/home/home";
        }
        log.info("세션이 있고, loginedMember라는 키의 세션이 있다");

        return "/home/loginedHome";
    }


}
