package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhoneMapper {
    void insertPhone (Integer memberId, String phoneNumStart, String phoneNumMiddle, String phoneNumEnd);

    void updatePhone(Integer memberId, String phoneNumStart, String phoneNumMiddle, String phoneNumEnd );
}
