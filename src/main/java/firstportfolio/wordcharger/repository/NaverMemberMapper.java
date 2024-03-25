package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NaverMemberMapper {

    Integer rowCount (String id);

    Integer selectNextSequenceValue();

    void insertRow(Integer nextSequenceValue, Integer memberId, String naverId);

}
