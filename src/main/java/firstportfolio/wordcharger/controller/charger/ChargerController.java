package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.repository.CountMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChargerController {
    private final CountMapper countMapper;

    @GetMapping("/charger-home")
    public String chargerHomeControllerMethod(HttpServletRequest request){
        //count 테이블의 today 컬럼에 들어있는 거 꺼내와.
        String id = FindLoginedMemberIdUtil.findLoginedMember(request);
        String pulledTodayColumn = countMapper.findTodayColumnById(id);

        if (pulledTodayColumn == null) {
            request.setAttribute("zeroToHundredTodayAmount", 100);
        }
        else if (pulledTodayColumn.equals("")) {
            //0 을 보내줘야 겠지.
            request.setAttribute("zeroToHundredTodayAmount", 0);
        } else{
            String[] splitedVoca = pulledTodayColumn.split(",");
            int length = splitedVoca.length;
            int realLength = length - 1;
            request.setAttribute("zeroToHundredTodayAmount", realLength);
        }
        return "/charger/chargerHome";
    }
}
