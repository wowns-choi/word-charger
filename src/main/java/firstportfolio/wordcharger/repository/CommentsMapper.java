package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsMapper {
    List<CommentDTO> findCommentsByPostId (Integer postId);


}
