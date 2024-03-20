package firstportfolio.wordcharger.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {

    private Integer id;
    private Integer postId;
    private Integer memberId;
    private String content;
    private Date createDate;
    private Integer parentCommentId;
    private String deleteCommentFl;

    private List<CommentDTO> replies = new ArrayList<>();

    private String userId;

    private String stringCreateDate;

}
