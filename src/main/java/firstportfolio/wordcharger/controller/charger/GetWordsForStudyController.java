package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.sevice.charger.GetWordsForStudyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
public class GetWordsForStudyController {

    private final GetWordsForStudyService getWordsForStudyService;

    @GetMapping("/get-words-for-study")
    public String getWordsForStudyControllerMethod(HttpServletRequest request, Model model, @RequestParam String startWordId, @RequestParam String endWordId){
        String viewPath = getWordsForStudyService.getWordsForStudy(request, model, startWordId, endWordId);
        return viewPath;
    }


}
