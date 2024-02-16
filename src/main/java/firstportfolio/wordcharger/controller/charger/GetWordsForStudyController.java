package firstportfolio.wordcharger.controller.charger;


import firstportfolio.wordcharger.DTO.UserWordDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.sevice.charger.GetWordsForStudyService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
