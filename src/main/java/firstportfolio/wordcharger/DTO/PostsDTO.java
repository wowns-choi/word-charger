package firstportfolio.wordcharger.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PostsDTO {
    //posts 테이블
    private Integer id;
    private Integer memberId;
    private String title;
    private Integer is_private;
    private Date writing_date;
    private String content;

    //post_view 테이블
    private Integer viewNumber;

    //post_password 테이블
    private String postPassword;


}
