package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.repository.CommentsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// @Transactional 안쓴다. insert 쿼리가 '하나' 잖아.
@Slf4j
@Service
@RequiredArgsConstructor
public class ReplySaveService {
    private final CommentsMapper commentsMapper;
    public void replySave(String content, String postId, String memberId, String parentCommentId){
        LocalDateTime now = LocalDateTime.now();
        commentsMapper.insertComment2(Integer.valueOf(postId), Integer.valueOf(memberId), content, Integer.valueOf(parentCommentId), now);
    }
}
