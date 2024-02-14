package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.repository.CommentsMapper;
import firstportfolio.wordcharger.sevice.board.ReplySaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReplySaveController {
    private final ReplySaveService replySaveService;
    @PostMapping("/reply-save")
    public String replySave(@RequestParam String content, @RequestParam String postId, @RequestParam String memberId, @RequestParam String parentCommentId){
        replySaveService.replySave(content, postId, memberId, parentCommentId);
        return "redirect:/show-writing?postId=" + postId;
    }

}
