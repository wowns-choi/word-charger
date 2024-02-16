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
        //세션 자체가 없다.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/home/home";
        }

        //세션은 있는데, loginedMember 라는 키의 세션은 없다.
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        if (loginedMember == null) {
            return "/home/home";
        }

        //loginedMember 라는 키의 세션이 있다.
        return "/home/loginedHome2";
    }


}
