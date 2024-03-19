package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.PWFindTokenDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface TokenMapper {

    void tokenInsertRow(Integer memberId, String token, LocalDateTime expiredAt);

    PWFindTokenDTO findRowByToken(String token);
}
