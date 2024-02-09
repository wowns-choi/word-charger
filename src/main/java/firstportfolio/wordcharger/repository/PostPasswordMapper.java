package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostPasswordMapper {

    public void insertPostPassword (Integer postId, String postPassword);
}
