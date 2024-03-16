package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data
public class OrderFormDTO {
    private String userName;
    private String email;
    private String domain;
    private String phoneNumStart;
    private String phoneNumMiddle;
    private String phoneNumEnd;
    private String zipCode;
    private String streetAddress;
    private String address;
    private String detailAddress;
    private String referenceItem;
    private String productName;
    private String productId;
    private String totalAmount;


}
