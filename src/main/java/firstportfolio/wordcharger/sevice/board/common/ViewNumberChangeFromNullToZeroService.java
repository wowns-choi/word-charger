package firstportfolio.wordcharger.sevice.board.common;

import firstportfolio.wordcharger.DTO.PostsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class ViewNumberChangeFromNullToZeroService {

    public List<PostsDTO> viewNumberChange(List<PostsDTO> currentPagePosts){

        for (PostsDTO post : currentPagePosts) {
            if (post.getViewNumber() == null) {
                post.setViewNumber(0);
            }
        }
        return currentPagePosts;

    }
}
