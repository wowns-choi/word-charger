package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.sevice.charger.ExplanationPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ExplanationPageController {
    private final ExplanationPageService explanationPageService;
    @GetMapping("/explanation-page")
    public String explanationPageControllerMethod(@RequestParam String vocabulary, Model model){
        explanationPageService.getCorrectionForMistakenWord(vocabulary, model);
        return "/charger/explain";
    }
}
