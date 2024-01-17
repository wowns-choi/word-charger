package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberDTO findMemberById(String id);

    void insertMember(MemberDTO memberDTO);

}
