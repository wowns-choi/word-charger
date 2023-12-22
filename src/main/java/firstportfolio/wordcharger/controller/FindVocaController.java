package firstportfolio.wordcharger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindVocaController {

    @PostMapping("/findVoca")
    public Map<String, String> findVocaController(){

        Map<String, String> data = new ConcurrentHashMap<>();





    }


}
