//package firstportfolio.wordcharger.controller.charger;
//
//import firstportfolio.wordcharger.DTO.WordDTO;
//import firstportfolio.wordcharger.repository.WordMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class ExplanationPageController {
//    private final WordMapper wordMapper;
//    @GetMapping("/explanation-page")
//    public String explanationPageControllerMethod(@RequestParam String vocabulary, HttpServletRequest request){
//
//        WordDTO findedVoca = wordMapper.findByVoca(vocabulary);
//        String correct = findedVoca.getCorrect();
//        String examplesentence1 = findedVoca.getExampleSentence1();
//        String examplesentence2 = findedVoca.getExampleSentence2();
//
//        request.setAttribute("voca", vocabulary);
//        request.setAttribute("correct", correct);
//        request.setAttribute("examplesentence1", examplesentence1);
//        request.setAttribute("examplesentence2", examplesentence2);
//
//        return "/charger/explanationPage2";
//    }
//}
