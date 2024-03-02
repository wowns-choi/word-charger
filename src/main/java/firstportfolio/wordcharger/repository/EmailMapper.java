package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.EmailDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

    void insertEmail (Integer memberId, String email, String domain);

    EmailDTO emailCountByEmail (String email);

    void updateEmail(Integer memberId, String email, String domain);
}
