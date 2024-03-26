package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberJoinDTO findMemberById(String userId);

    String findUserIdById(Integer id);

    void insertMember(Integer nextSequenceValue, String userId, String password, String userName);

    Integer selectNextSequenceValue ();

    MemberAllDataFindDTO findMemberTotalData(String id);

    String findPwById(Integer id);

    void updatePassword(Integer id, String password);

    String findUserNameByEmailAndDomain(String email, String domain);

    String findUserIdByUserNameAndEmailAndDomain(String userName, String email, String domain);

    MemberAllDataFindDTO findUserNameAndEmailAndDomainByUserId(String userId);

    void updateDeleteMemberFl(Integer id);
}
