package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostViewMapper {

    Integer selectNextSequenceValue ();
    void initPostView (Integer sequence, Integer postId);

    void updateByPostId (Integer postId);

    Integer findPostViewCountByPostId (Integer postId);

}
