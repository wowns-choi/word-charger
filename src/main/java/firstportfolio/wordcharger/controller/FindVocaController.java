package firstportfolio.wordcharger.controller;

import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.WordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindVocaController {

    private final WordMapper wordMapper;

    @PostMapping("/findVoca")
    public Map<String, String> findVocaController(@RequestParam String voca){
        Map<String, String> data = new ConcurrentHashMap<>();
        WordDTO byVoca = wordMapper.findByVoca(voca);
        String findedvoca = byVoca.getVoca();
        String correct = byVoca.getCorrect();
        data.put("voca", findedvoca);
        data.put("correct", correct);
        return data;
    }


}
