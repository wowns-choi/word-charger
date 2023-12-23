package firstportfolio.wordcharger.controller;

import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.WordMapper;
import firstportfolio.wordcharger.sevice.FindVocaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindVocaController {

    private final FindVocaService findVocaService;

    @PostMapping("/findVoca")
    @ResponseBody
    public Map<String, String> findVocaController(@RequestParam String voca){
        Map<String, String> containVocaAndCorrect = findVocaService.findWordByVocaFromWordTable(voca);
        return containVocaAndCorrect;
    }


}
