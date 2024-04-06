package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.CommentUpdateDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateCommentController {
    private final CommentsMapper commentsMapper;

    @PostMapping("update-comment")
    @ResponseBody
    public Map<String, String> updateCommentControllerMethod(@RequestBody CommentUpdateDTO commentUpdateDTO){
        commentsMapper.updateContent(commentUpdateDTO.getCommentId(), commentUpdateDTO.getContent());

        Map<String, String> map = new HashMap<>();
        map.put("content", commentUpdateDTO.getContent());
        return map;
    }
}
