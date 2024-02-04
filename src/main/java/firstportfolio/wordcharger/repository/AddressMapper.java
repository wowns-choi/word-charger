package firstportfolio.wordcharger.repository;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper{

    void insertAddress(Integer memberId, String zipCode, String streetAddress,
                       String address, String detailAddress, String referenceItem);

}
