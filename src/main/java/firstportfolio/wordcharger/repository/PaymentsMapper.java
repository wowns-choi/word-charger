package firstportfolio.wordcharger.repository;


import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface PaymentsMapper {
//    void insertPayment(
//            Integer memberId
//
//
//    );




    void insertPayment(
            Integer memberId,
            Integer orderId,
            String impUid,
            String merchantUid,
            Integer amount,
            String currency,
            String status,
            //
            String failReason,
            LocalDateTime failAt,
            String payMethod,
            String name,
            LocalDateTime paidAt,
            String receiptUrl,
            LocalDateTime startedAt,
            String userAgent,
            String buyerName,
            String buyerTel,
            //
            String buyerAddr,
            String buyerPostcode,
            String buyerEmail,
            String applyNum,
            String cardCode,
            String cardName,
            String cardNumber,
            Integer cardQuota,
            Integer cardType,
            String bankCode,
            //
            String bankName,
            String vbankCode,
            Integer vbankDate,
            String vbankHolder,
            LocalDateTime vbankIssuedAt,
            String vbankNum,
            String vbankName,
            String customData,
            String customerUid,
            String customerUidUsage,
            //
            String channel,
            String CashReceiptIssued,
            String escrow,
            String pgId,
            String pgProvider,
            String embPgProvider,
            String pgTid,
            Integer cancelAmount,
            String cancelReason,
            String cancelledAt
    );




}
