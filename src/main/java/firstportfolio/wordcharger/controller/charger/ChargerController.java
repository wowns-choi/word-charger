package firstportfolio.wordcharger.controller.charger;

import firstportfolio.wordcharger.repository.UserWordMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChargerController {
//    private final CountMapper countMapper;
    private final UserWordMapper userWordMapper;
    @GetMapping("/charger-home")
    public String chargerHomeControllerMethod(HttpServletRequest request){
        //count 테이블의 today 컬럼에 들어있는 거 꺼내와.
        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);

        List<Integer> todayCountList = new ArrayList<>();

        for(int i=1; i<11; i++){
            List<Integer> wordId = userWordMapper.findWordId(i*100 -99, i*100, id);
            int size = wordId.size();
            todayCountList.add(size);
        }

        request.setAttribute("a", todayCountList.get(0));
        request.setAttribute("b", todayCountList.get(1));
        request.setAttribute("c", todayCountList.get(2));
        request.setAttribute("d", todayCountList.get(3));
        request.setAttribute("e", todayCountList.get(4));

        request.setAttribute("f", todayCountList.get(5));
        request.setAttribute("g", todayCountList.get(6));
        request.setAttribute("h", todayCountList.get(7));
        request.setAttribute("i", todayCountList.get(8));
        request.setAttribute("j", todayCountList.get(9));


        return "/charger/chargerHome";
    }
}
