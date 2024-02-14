package firstportfolio.wordcharger.controller.board;


import firstportfolio.wordcharger.sevice.board.InsertCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@Transactional
public class InsertCommentController {

    private final InsertCommentService insertCommentService;

    @PostMapping("/insert-comment")
    public String InsertCommentControllerMethod(@RequestParam String content, @RequestParam Integer postId, @RequestParam String memberId) {
        insertCommentService.insertComment(content, postId, memberId);
        return "redirect:/show-writing?postId=" + postId;
    }
}
