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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FindPostsByTitleWriterService {
    private final PostsMapper postsMapper;
    private final MemberMapper memberMapper;
    private final PaginationService paginationService;
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


        Integer currentPage = page; //현재 페이지
        Integer pageSize = 5; // 페이지당 보여질 post 의 수
        Integer totalPosts = findPostsList.size();  // 모든 post 의 개수
        Integer pageGroupSize = 5; // 그룹당 페이지의 개수

        // 현재 페이지가 보여줘야 하는 PostsDTO 객체들을 담은 List자료구조 : currentPagePosts
        Integer startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = new ArrayList<>();
        for (int i = startRow; i < Math.min(startRow + 5, findPostsList.size()); i++) {
            currentPagePosts.add(findPostsList.get(i));
        }
        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, currentPagePosts, model);
    }

}
