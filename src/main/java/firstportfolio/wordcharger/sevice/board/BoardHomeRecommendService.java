package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.PostViewMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

//@Transactional 안쓴다. 모두 select 쿼리니까.
@Slf4j
@RequiredArgsConstructor
@Service
public class BoardHomeRecommendService {
    private final PostsMapper postsMapper;
    private final PaginationService paginationService;
    private final PostViewMapper postViewMapper;

    public void findAllPosts(Integer page, Model model){
        Integer currentPage = page; // 현재 페이지
        Integer pageSize = 20; // 페이지당 보여질 post 의 수
        Integer totalPosts = postsMapper.findAllPostsCount(); // 모든 post 의 개수
        Integer pageGroupSize = 9; // 그룹당 페이지의 개수

        // 현재 페이지가 보여줘야 하는 PostsDTO 객체들을 담은 List자료구조
        int startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = postsMapper.findCurrentPagePostsRecommendVersion(startRow, pageSize);

        //조회수는 또 post_view 테이블에 있기 때문에 그걸 지금 currentPagePosts 라는 List자료구조에 넣어줘야 함.
        for (PostsDTO post : currentPagePosts) {
            Integer postId = post.getId();
            Integer findPostViewCount = postViewMapper.findPostViewCountByPostId(postId);
            post.setViewNumber(findPostViewCount);
            log.info("postViewCount==={}", post.getViewNumber());
        }



        // 이 changeDate 메서드는 작성날짜를 xxxx(년)-xx(월)-xx(일) 로 표시해주기 위한 것이다.
        //List<PostsDTO> postsList = writingDateChangeService.changeDate(currentPagePosts);

        //currentPage,pageSize,totalWritings, pageGroupSize, currentPagePosts
        //paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, postsList, model);

    }
}
