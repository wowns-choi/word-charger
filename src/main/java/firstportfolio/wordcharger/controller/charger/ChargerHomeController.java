package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.sevice.charger.ChargerHomeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChargerHomeController {
    private final ChargerHomeService chargerHomeService;
    @GetMapping("/charger-home")
    public String chargerHomeControllerMethod(Model model,HttpServletRequest request){
        chargerHomeService.getTodaysWordsCount(model, request);
        return "/charger/chargerHome";
    }
}
