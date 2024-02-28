package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.repository.CommentsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateCommentController {
    private final CommentsMapper commentsMapper;

    @PostMapping("update-comment")
    public String updateCommentControllerMethod(@RequestParam String commentId, @RequestParam String content, String postId){
        commentsMapper.updateContent(commentId, content);
        return "redirect:/show-writing?postId=" + postId;
    }
}
