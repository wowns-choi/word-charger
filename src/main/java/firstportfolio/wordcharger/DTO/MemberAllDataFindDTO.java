package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data

public class MemberAllDataFindDTO {
    private Integer id;
    private String userId;


    private String password;
    private String userName;


    private String zipCode; //01076
    private String phoneNumStart;
    private String phoneNumMiddle;
    private String phoneNumEnd;


    private String streetAddress; //서울 강북구 노해로13길 40 : 도로명 주소
    private String address; //서울 강북구 수유동 31-67 : 지번주소
    private String detailAddress; //정면 왼쪽 초록색 대문 : 상세주소
    private String referenceItem; // (수유동)


    private String email;
    private String domain;
    private String customEmailDomain;




}


