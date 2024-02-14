package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InsertCommentService {
    private final MemberMapper memberMapper;
    private final CommentsMapper commentsMapper;

    public void insertComment(String content, Integer postId, String memberId){
        MemberJoinDTO findMember = memberMapper.findMemberById(memberId);
        commentsMapper.insertComment(postId, findMember.getId(), content);
    }
}
