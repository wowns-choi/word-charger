
package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.sevice.charger.NextBtnClickMappingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NextBtnClickMappingController {

    private final NextBtnClickMappingService nextBtnClickMappingService;

    @GetMapping("/next-btn-click-connectivity")
    public String connectivityControllerMethod(@RequestParam String vocabulary, HttpServletRequest request) {
        Map<String, String> returnMap = nextBtnClickMappingService.getStartWordId(vocabulary);
        return "redirect:/get-words-for-study?startWordId="+returnMap.get("startWordId") + "&endWordId=" + returnMap.get("endWordId");
    }


}

