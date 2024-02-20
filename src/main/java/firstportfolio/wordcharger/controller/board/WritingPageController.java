package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.PostGenerateDTO;
import firstportfolio.wordcharger.sevice.board.InsertPostService;
import firstportfolio.wordcharger.sevice.board.ShowWritingFormService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WritingPageController {
    private final ShowWritingFormService showWritingFormService;
    private final InsertPostService insertPostService;

    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        showWritingFormService.showWritingForm(model, request);
        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute PostGenerateDTO pageGenerateDTO, BindingResult bindingResult, Model model, String userId) {

        String title = pageGenerateDTO.getTitle();
        Integer memberId = pageGenerateDTO.getMemberId();
        Boolean secretWritingCheckBox = pageGenerateDTO.getIs_private();
        String writingPassword = pageGenerateDTO.getPostPassword();
        String content = pageGenerateDTO.getContent();

        if(pageGenerateDTO.getTitle().equals("")){
            bindingResult.rejectValue("title", null, "제목을 작성해주세요");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", userId);
            return "/contact/writingPage";
        }


        //비밀글로 하기 체크했으면서, 비밀번호를 안썻을 경우의 처리 로직.
        if (secretWritingCheckBox.equals(true)) {
            if (writingPassword.equals("")) {
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                return "/contact/writingPage";
            }
        }

        insertPostService.insertPost(title, memberId, secretWritingCheckBox, writingPassword, content);
        return "redirect:/board-home";
    }

}
