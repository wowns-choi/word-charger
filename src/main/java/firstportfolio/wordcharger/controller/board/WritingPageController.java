package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostGenerateDTO;
import firstportfolio.wordcharger.repository.PostPasswordMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final PostsMapper postsMapper;
    private final PostPasswordMapper postPasswordMapper;
    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        model.addAttribute("postGenerateDTO", new PostGenerateDTO());

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("id", loginedMember.getId());
        model.addAttribute("userId", loginedMember.getUserId());
        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute PostGenerateDTO pageGenerateDTO, BindingResult bindingResult, Model model) {

        String title = pageGenerateDTO.getTitle();
        Integer memberId = pageGenerateDTO.getMemberId();
        Boolean secretWritingCheckBox = pageGenerateDTO.getIs_private();
        String writingPassword = pageGenerateDTO.getPostPassword();
        String content = pageGenerateDTO.getContent();

        if (bindingResult.hasErrors()) {
            if (secretWritingCheckBox.equals(true)) {
                model.addAttribute("show", true);
            }
            return "contact/writingPage";
        }

        if (secretWritingCheckBox.equals(true)) {
            if (writingPassword.equals("")) {
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                return "/contact/writingPage";
            }
        }

        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        postsMapper.insertPost(title, memberId, isPrivate, content);
        Integer findPostId = postsMapper.selectPostByMemberIdAndTitle(memberId, title);
        postPasswordMapper.insertPostPassword(findPostId, writingPassword);
        return "redirect:/board-home";
    }

}
