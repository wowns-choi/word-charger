package firstportfolio.wordcharger.repository;


import firstportfolio.wordcharger.DTO.WritingDTOSelectVersion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WritingMapper {

    void init();

    void insertWriting (String title, String userId, Integer isPrivate, String writingPassword, String content);

    Integer countAllWriting ();

    List<WritingDTOSelectVersion> findCurrentPageWritings(Integer startRow, Integer pageSize);


}

