package firstportfolio.wordcharger.controller.common;

import firstportfolio.wordcharger.sevice.common.FindVocaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindVocaController {

    private final FindVocaService findVocaService;

    @PostMapping("/findVoca")
    @ResponseBody
    public Map<String, String> findVocaControllerMethod(@RequestParam String voca){
        Map<String, String> containVocaAndCorrect = findVocaService.findWordByVocaFromWordTable(voca);
        return containVocaAndCorrect;
    }


}
