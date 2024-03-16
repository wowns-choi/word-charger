package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import firstportfolio.wordcharger.sevice.board.common.WritingDateChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
//@Transactional 안쓴다. 모두 select 쿼리니까.
@RequiredArgsConstructor
@Slf4j
public class FindPostsByTitleWriterService {
    private final PostsMapper postsMapper;
    private final MemberMapper memberMapper;
    private final PaginationService paginationService;
    private final WritingDateChangeService writingDateChangeService;
    public void findPostsService(String byWhatType, String hintToFind, Integer page, Model model){

        List<PostsDTO> findPostsList = new ArrayList<>();
        //title 로 찾기
        if (byWhatType.equals("title")) {
            findPostsList = postsMapper.findPostByTitle(hintToFind);
        }
        //writer 로 찾기
        if (byWhatType.equals("writer")) {
            MemberJoinDTO findMember = memberMapper.findMemberById(hintToFind);
            Integer id = findMember.getId();
            findPostsList = postsMapper.findPostByMemberId(id);
        }
        //content 로 찾기
        if (byWhatType.equals("content")) {
            findPostsList = postsMapper.findPostByContent(hintToFind);
        }

        // 이 changeDate 메서드는 작성날짜를 xxxx(년)-xx(월)-xx(일) 로 표시해주기 위한 것이다.
        List<PostsDTO> postsList = writingDateChangeService.changeDate(findPostsList);

        Integer currentPage = page; //현재 페이지
        Integer pageSize = 5; // 페이지당 보여질 post 의 수
        Integer totalPosts = postsList.size();  // 모든 post 의 개수
        Integer pageGroupSize = 5; // 그룹당 페이지의 개수

        // 현재 페이지가 보여줘야 하는 PostsDTO 객체들을 담은 List자료구조 : currentPagePosts
        Integer startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = new ArrayList<>();
        for (int i = startRow; i < Math.min(startRow + 5, postsList.size()); i++) {
            currentPagePosts.add(postsList.get(i));
        }
        model.addAttribute("byWhatType", byWhatType);
        model.addAttribute("hintToFind", hintToFind);
        paginationService.pagination(currentPage, pageSize, totalPosts, pageGroupSize, currentPagePosts, model);
    }

}
