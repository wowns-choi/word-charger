package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostIdMemberIdForNotDuplicateLikeMapper {

    void insertRow(Integer postId, Integer memberId);

    Integer findRowCountByPostIdAndMemberId (Integer postId, Integer memberId);

}
