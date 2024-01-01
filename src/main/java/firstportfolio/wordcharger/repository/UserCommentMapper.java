package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCommentMapper {

    void insertComment (String writingNum, String id, String content);

    List<CommentDTO> findCommentByWritingNum(Integer startRow, Integer numberPerPage, String writingNum);

    Integer allCommentCount (String writingNum);

}
