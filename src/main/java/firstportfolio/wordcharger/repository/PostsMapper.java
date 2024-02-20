package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.PostsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsMapper {

    public Integer findAllPostsCount ();

    public List<PostsDTO> findCurrentPagePosts(Integer startRow, Integer pageSize);

    public void insertPost (Integer selectNextSequenceValue, String title, Integer memberId, Integer isPrivate, String content);


    public PostsDTO findPostById(Integer postId);

    public List<PostsDTO> findPostByTitle(String hintToFind);
    public List<PostsDTO> findPostByMemberId(Integer memberId);
    public List<PostsDTO> findPostByContent (String content);
    public Integer selectNextSequenceValue ();

}
