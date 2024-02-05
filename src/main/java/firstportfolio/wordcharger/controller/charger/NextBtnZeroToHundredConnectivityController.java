
package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NextBtnZeroToHundredConnectivityController {

    private final WordMapper wordMapper;

    @GetMapping("/next-btn-zero-to-hundred-connectivity")
    public String connectivityControllerMethod(@RequestParam String vocabulary, HttpServletRequest request) {
        Integer findWordId = wordMapper.findByWord(vocabulary);

        String startWordId = null;
        String endWordId = null;

        if(findWordId <= 100){
            startWordId = "1";
            endWordId = "100";
        } else if (findWordId >100 && findWordId <=200) {
            startWordId = "101";
            endWordId = "200";
        } else if (findWordId >200 && findWordId <=300){
            startWordId = "201";
            endWordId = "300";
        } else if (findWordId >300 && findWordId <=400) {
            startWordId = "301";
            endWordId = "400";
        } else if (findWordId >400 && findWordId <= 500){
            startWordId  = "401";
            endWordId = "500";
        } else if (findWordId >500 && findWordId <=600){
            startWordId = "501";
            endWordId = "600";
        } else if (findWordId > 600 && findWordId <= 700) {
            startWordId = "601";
            endWordId = "700";
        } else if (findWordId > 700 && findWordId <= 800) {
            startWordId = "701";
            endWordId = "800";
        } else if (findWordId > 800 && findWordId <= 900) {
            startWordId = "801";
            endWordId = "900";
        } else if (findWordId > 900 && findWordId <= 1000) {
            startWordId = "901";
            endWordId = "1000";
        }

        return "redirect:/zero-to-hundred?startWordId="+startWordId + "&endWordId=" + endWordId;
    }


}

