package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.PostPasswordDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostPasswordMapper {

    public void insertPostPassword (Integer postId, String postPassword);

    public PostPasswordDTO findRow(Integer postId);

    void updatePostPassword(Integer id, String postPassword);
}
