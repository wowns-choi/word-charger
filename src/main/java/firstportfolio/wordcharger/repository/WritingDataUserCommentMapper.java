package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.WritingDataUserCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WritingDataUserCommentMapper {

    Integer findTotalMyWritingSize (String id);

    List<WritingDataUserCommentDTO> findMyWriting(String id, Integer startRow, Integer pageSize);

}
