package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WritingMapper {

    void init();

    void insertWriting (String title, String userId, Integer isPrivate, String writingPassword, String content);

}

