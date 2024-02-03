package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberJoinDTO findMemberById(String userId);

    void insertMember(String id, String password, String userName);

}
