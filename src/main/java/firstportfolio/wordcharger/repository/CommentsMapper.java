package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {
    List<CommentDTO> findCommentsByPostId (Integer postId);

    void insertComment (Integer postId, Integer memberId, String content);

    void insertComment2 (Integer postId, Integer memberId, String content, Integer parentCommentId);

    List<Integer> findPostIdByMemberId(Integer memberId);
}
