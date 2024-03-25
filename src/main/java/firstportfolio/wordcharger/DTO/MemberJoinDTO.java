package firstportfolio.wordcharger.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class MemberJoinDTO {
    private Integer id;
    private String userId;

    private String password;
    private String userName;
    private String delete_member_fl;


    private String zipCode;
    private String phoneNumberStart;
    private String phoneNumberMiddle;
    private String phoneNumberEnd;


    private String streetAddress;
    private String address;
    private String detailAddress;
    private String referenceItem;
    private String myCheckbox1;
    private String myCheckbox2;
    private String myCheckbox3;

    private String email;
    private String emailDomain;
    private String customEmailDomain;


}


