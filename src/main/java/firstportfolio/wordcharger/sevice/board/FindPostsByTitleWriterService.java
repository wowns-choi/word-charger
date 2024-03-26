package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
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
    public void findPostsService(String byWhatType, String hintToFind, Integer page, Model model){

        Integer currentPage = page; //현재 페이지

        // 총 페이지 수 구하기
        Integer totalPosts = 0; // 총 게시글 수를 담을 변수를 만들어둠.
            // 사용자가 제목으로 조회한 경우, 그 제목과 일치하는 게시글의 개수를 조회하기
            if (byWhatType.equals("title")) {
                totalPosts = postsMapper.findPostCountByTitle(hintToFind);
            }
            // 사용자가 작성자로 조회한 경우, 그 작성자가 작성한 게시글의 개수를 조회하기
            if (byWhatType.equals("writer")) {
                MemberJoinDTO findMember = memberMapper.findMemberById(hintToFind);
                Integer id = findMember.getId();
                totalPosts = postsMapper.findPostCountByMemberId(id);
            }
            // 사용자가 내용으로 조회한 경우, 그 내용을 담고 있는 게시글의 개수를 조회하기
            if (byWhatType.equals("content")) {
                totalPosts = postsMapper.findPostCountByContent(hintToFind);
            }

            Integer pageSize = 5; // 페이지당 보여질 게시글 의 수

            // 총 페이지 수 = 총 게시글 수(totalPosts) / 페이지당 보여질 게시글의 수(pageSize)
            Integer totalPages = (int) Math.ceil((double) totalPosts / pageSize);

            Integer pageGroupSize = 5; // 몇개의 페이지를 하나의 그룹으로 묶었는지.

    //----------------------------------------------------------------------------------
        //현재 페이지에 보여질 게시글(PostsDTO) 을 담은 List 자료구조
        // SELECT posts.id, posts.member_id, posts.title, posts.is_private, TO_CHAR(posts.writing_date, 'YYYY-MM-DD') writing_date, posts.content, posts.delete_post_fl, post_view.view_number, member.user_id
        // FROM posts
        // LEFT OUTER JOIN post_view ON posts.id = post_view.post_id
        // JOIN member ON posts.member_id = member.id
        // WHERE posts.DELETE_POST_FL = 'N'
        // AND posts.title = #{title}         // AND posts.writer = #{writer} // AND posts.content LIKE #{content}
        // ORDER BY posts.id DESC
        // OFFSET #{startRow} ROWS
        // FETCH NEXT #{pageSize} ROWS ONLY

        Integer startRow = (currentPage - 1) * pageSize;

        List<PostsDTO> currentPagePosts = new ArrayList<>();

        // 사용자가 제목으로 조회한 경우.
        if (byWhatType.equals("title")) { // hintToFind, startRow, pageSize
            currentPagePosts = postsMapper.findPostsByTitle(hintToFind, startRow, pageSize);
        }
        // 사용자가 작성자로 조회한 경우.
        if (byWhatType.equals("writer")) {
            MemberJoinDTO findMember = memberMapper.findMemberById(hintToFind);
            Integer id = findMember.getId();
            currentPagePosts = postsMapper.findPostsByMemberId(id, startRow, pageSize);
        }
        // 사용자가 내용으로 조회한 경우.
        if (byWhatType.equals("content")) {
            currentPagePosts = postsMapper.findPostsByContent(hintToFind, startRow, pageSize);
        }

        // 만약 조회된 게시글이 하나도 없다면, "조회된 게시글이 없습니다" 라는 문구를 표시하기 위해서, model 에 담아줌.
        if (currentPagePosts.size() == 0) {
            model.addAttribute("nothing", "조회된 게시글이 없습니다.");
        }

        //boardHomeFind.jsp 파일에서 사용자가 입력한 byWhatType 과 hintToFind 를 그대로 보여주기 위해서 model 에 넣어둠
        model.addAttribute("byWhatType", byWhatType);
        model.addAttribute("hintToFind", hintToFind);
        paginationService.pagination(currentPage, pageSize, totalPosts, pageGroupSize, currentPagePosts, model);
    }

}
