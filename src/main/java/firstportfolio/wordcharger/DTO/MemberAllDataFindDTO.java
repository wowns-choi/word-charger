package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data

public class MemberAllDataFindDTO {
    private Integer id;
    private String userId;


    private String password;
    private String userName;


    private String zipCode;
    private String phoneNumStart;
    private String phoneNumMiddle;
    private String phoneNumEnd;


    private String streetAddress;
    private String address;
    private String detailAddress;
    private String referenceItem;


    private String email;
    private String domain;
    private String customEmailDomain;




}


