package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserWordMapper {

    public void initUserWord(Integer memberId, Integer wordId);

    public List<Integer> findWordId(Integer startWordInteger, Integer endWordInteger, Integer memberId);

    public Integer findDaysRemembered(Integer memberId, Integer wordId);

    public void updateNextReviewDate(Integer daysRemembered, Integer memberId, Integer wordId);

    public void updateDaysRemembered(Integer memberId, Integer wordId);
}


