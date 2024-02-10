package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.PostsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsMapper {

    public Integer findAllPostsCount ();

    public List<PostsDTO> findCurrentPagePosts(Integer startRow, Integer pageSize);

    public void insertPost (String title, Integer memberId, Integer isPrivate, String content);

    public Integer selectPostByMemberIdAndTitle(Integer memberId, String title);

    public PostsDTO findPostById(Integer postId);

    public List<PostsDTO> findPostByMemberId(Integer memberId);
}
