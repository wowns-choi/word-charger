package firstportfolio.wordcharger.controller.join;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@Slf4j
public class TermsOfUseController {
    @GetMapping("/terms-of-use")
    public String getTermsOfUseControllerMethod(){
        return "/login/termsOfUse";
    }
    @PostMapping("/terms-of-use")
    public String postTermsOfUseControllerMethod(@RequestParam(required = false) String myCheckbox1,
                                                 @RequestParam(required = false) String myCheckbox2,
                                                 @RequestParam(required = false) String myCheckbox3){
        return "redirect:/Join-form?" + "myCheckbox1=" + myCheckbox1+ "&" + "myCheckbox2=" + myCheckbox2+ "&"  + "myCheckbox3=" + myCheckbox3;
    }
}
