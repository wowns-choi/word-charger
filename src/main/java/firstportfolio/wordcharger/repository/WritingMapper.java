package firstportfolio.wordcharger.repository;


import firstportfolio.wordcharger.DTO.WritingDTOSelectVersion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WritingMapper {

    void deleteAll();

    void init(int i);

    void insertWriting (String title, String userId, Integer isPrivate, String writingPassword, String content);

    Integer countAllWriting ();

    List<WritingDTOSelectVersion> findCurrentPageWritings(Integer startRow, Integer pageSize);

    Integer findWritingByTitle (String hintToFind);

    List<WritingDTOSelectVersion> findCurrentPageWritingsByTitle(String title,Integer startRow, Integer pageSize);

    Integer findWritingByWriter (String hintToFind);

    List<WritingDTOSelectVersion> findCurrentPageWritingsByWriter(String writer, Integer startRow, Integer pageSize);
    Integer findWritingByContent (String hintToFind);

    List<WritingDTOSelectVersion> findCurrentPageWritingsByContent(String content, Integer startRow, Integer pageSize);


    WritingDTOSelectVersion findWritingByWritingNum (String writingNum);
}

