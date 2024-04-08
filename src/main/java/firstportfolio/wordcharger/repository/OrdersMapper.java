package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper {

    void insertOrder(Integer memberId,
                     String productId,
                     String merchantUid,
                     Integer amount,
                     String status,
                     String failReason,
                     String buyerPhoneNumStart,
                     String buyerPhoneNumMiddle,
                     String buyerPhoneNumEnd,
                     String buyerZipCode,
                     String buyerStreetAddress,
                     String buyerAddress,
                     String buyerDetailAddress,
                     String buyerReferenceItem);
    Integer findAmountByMerchantUid(String merchantUid);

    void updateStatusByMerchantUid(String merchantUid, String status);

    Integer findOrderIdByMerchantUid(String merchantUid);

    void updateFailReasonByMerchantUid(String merchantUid, String failReason);

    Integer findProductIdByMerchantUid(String merchantUid);

    Integer selectAllCount();

}
