package firstportfolio.wordcharger.controller.join;

import firstportfolio.wordcharger.sevice.join.IdDuplicateCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class idDuplicateCheckController {

    private final IdDuplicateCheckService idDuplicateCheckService;
    @PostMapping("check-user-id")
    @ResponseBody
    public Map<String,Boolean> duplicateIdValidationControllerMethod(@RequestBody Map<String,String> request){

        Map<String, Boolean> returnMap = idDuplicateCheckService.idCheck(request);
        return returnMap;
    }
}
