package firstportfolio.wordcharger.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FAQController {

    @GetMapping("/faq")
    public String method1(){
        return "/contact/faq";
    }
}
