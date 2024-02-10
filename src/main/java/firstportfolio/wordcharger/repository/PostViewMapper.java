package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostViewMapper {

    void updateByPostId (Integer postId);

    Integer findPostViewCountByPostId (Integer postId);

}
