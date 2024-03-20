package firstportfolio.wordcharger.controller.board;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DeleteCommentController {
    private final CommentsMapper commentsMapper;

    @PostMapping("/delete-comment")
    @ResponseBody
    public Map<String, String> deleteCommentControllerMethod(@RequestBody CommentDTO commentDTO) {
        //commentDTO.getId() 에는 뭐가 들어있어?
        //사용자가 지우고 싶다고 했던 comment테이블의 행의 id 컬럼값이 들어있음.
        commentsMapper.updateDeleteFlag(String.valueOf(commentDTO.getId()));
        Map<String, String> map = new HashMap<>();
        map.put("text", "삭제된 댓글입니다.");
        return map;
    }


}
