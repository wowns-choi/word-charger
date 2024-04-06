package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional 안쓴다. insert 쿼리 하나니까.
public class InsertCommentService {
    private final CommentsMapper commentsMapper;

    public void insertComment(String content, Integer postId, String memberId){
        LocalDateTime now = LocalDateTime.now();
        commentsMapper.insertComment(postId, Integer.parseInt(memberId), content, now);
    }
}
