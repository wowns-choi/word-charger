package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardHomeService {

    private final PostsMapper postsMapper;
    private final PaginationService paginationService;

    public void findAllPosts(Integer page, Model model){
        Integer currentPage = page; // 현재 페이지
        Integer pageSize = 20; // 페이지당 보여질 post 의 수
        Integer totalPosts = postsMapper.findAllPostsCount(); // 모든 post 의 개수
        Integer pageGroupSize = 9; // 그룹당 페이지의 개수

        // 현재 페이지가 보여줘야 하는 PostsDTO 객체들을 담은 List자료구조
        int startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = postsMapper.findCurrentPagePosts(startRow, pageSize);

        for (PostsDTO post : currentPagePosts) {
            Date date = post.getWritingDate();
            // Date -> Instant -> LocalDateTime 변환
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // DateTimeFormatter를 사용하여 년-월-일 형식으로 포맷팅
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDateTime.format(formatter);
            post.setStringWritingDate(formattedDate);

        }


        //currentPage,pageSize,totalWritings, pageGroupSize, currentPagePosts
        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, currentPagePosts, model);

    }
}
