package firstportfolio.wordcharger.controller.user;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.sevice.user.PasswordUpdateService;
import firstportfolio.wordcharger.sevice.user.PasswordValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiredArgsConstructor
@Slf4j
public class UpdatePasswordController {
    private final PasswordValidationService passwordValidationService;
    private final PasswordUpdateService passwordUpdateService;
    @GetMapping("/password-validation")
    public String passwordValidationControllerMethod() {
        return "/user/pw-update-validation";
    }

    @PostMapping("/password-validation")
    public String passwordValidationControllerMethod2(@ModelAttribute MemberAllDataFindDTO memberAllDataFindDTO, HttpServletRequest request, Model model) {

        String viewPath = passwordValidationService.pwValidation(model, request, memberAllDataFindDTO);
        return viewPath;
    }

    @PostMapping("/password-update")
    @ResponseBody
    public String passwordUpdateControllerMethod(@RequestBody MemberAllDataFindDTO memberAllDataFindDTO, HttpServletRequest request) {

        String viewPath = passwordUpdateService.passwordUpdate(request, memberAllDataFindDTO);
        return viewPath;
    }
}
