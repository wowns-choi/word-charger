package firstportfolio.wordcharger.DTO;

import lombok.Data;

@Data
public class CommentUpdateDTO {

    private String commentId;
    private String postId;
    private String content;

}
