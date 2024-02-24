package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

    void initPostLike (Integer postId);

    Integer findPostLikeCountByPostId (Integer postId);

    void updateByPostId (Integer postId);
}
