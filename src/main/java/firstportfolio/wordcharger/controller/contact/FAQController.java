package firstportfolio.wordcharger.controller.contact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FAQController {

    @GetMapping("/faq")
    public String method1(){
        return "/contact/faq";
    }
}
