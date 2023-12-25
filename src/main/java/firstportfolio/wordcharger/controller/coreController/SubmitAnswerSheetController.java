package firstportfolio.wordcharger.controller.coreController;

import firstportfolio.wordcharger.DTO.WordDTO;
import firstportfolio.wordcharger.repository.WordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubmitAnswerSheetController {
    private final WordMapper wordMapper;

    @PostMapping("/submitAnswerSheet")
    @ResponseBody
    public String submitAnswerSheetControllerMethod(@RequestParam String voca, @RequestParam String userAnswer){

        //voca = 영단어가 뭐였는지. userAnswer = 사용자가 뭘 선택했는지.
        WordDTO findedVoca = wordMapper.findByVoca(voca);
        String correct = findedVoca.getCorrect();
        if (!userAnswer.equals(correct)) {
            return "/charger/wrongCaseRenderingPage";
        }



        return "zzz";
    }
}
