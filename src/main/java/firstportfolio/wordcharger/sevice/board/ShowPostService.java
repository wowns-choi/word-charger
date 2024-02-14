package firstportfolio.wordcharger.sevice.board;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.CommentsMapper;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.PostViewMapper;
import firstportfolio.wordcharger.repository.PostsMapper;
import firstportfolio.wordcharger.sevice.board.common.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ShowPostService {

    private final PostsMapper postsMapper;
    private final PostViewMapper postViewMapper;
    private final CommentsMapper commentsMapper;
    private final MemberMapper memberMapper;
    private final PaginationService paginationService;

    public void showPost(Integer page, Integer postId, Model model, HttpServletRequest request){

        //post테이블의 아이디컬럼 값으로 게시글을 찾아와야함.
        PostsDTO findPost = postsMapper.findPostById(postId);
        model.addAttribute("findPost", findPost);

        //post_view +1 update : 조회수 1 올리는 작업
        postViewMapper.updateByPostId(findPost.getId());


        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("loginedMemberId", loginedMember);

        //----------자식 댓글(리플) 을 부모 객체에 끼워넣는 작업 시작----------
        List<CommentDTO> findComments = commentsMapper.findCommentsByPostId(postId);//찾아옴
        Map<Integer, CommentDTO> commentMap = new HashMap<>();

        for (CommentDTO comment : findComments) {
            commentMap.put(comment.getId(), comment);
        }

        for (CommentDTO comment : findComments) {
            if (comment.getParentCommentId() != null) {
                CommentDTO parent = commentMap.get(comment.getParentCommentId());
                if (parent != null) {
                    parent.getReplies().add(comment);
                }
            }
        }

        List<CommentDTO> parentCommentList = new ArrayList<>();

        for (CommentDTO comment : findComments) {
            if (comment.getParentCommentId() == null) {
                parentCommentList.add(comment);
            }
        }



        //--------페이징 시작----------
        Integer currentPage = page; // 현재 페이지

        //한 페이지당 댓글 개수
        Integer pageSize = 5;
        //전체 코멘트 개수
        Integer totalPosts = parentCommentList.size(); //전체 comment 의 개수

        //그룹당 페이지수
        Integer pageGroupSize = 5;

        // 게시물 5개 주는 거는 아래 코드로 됬음.
        Integer startRow = (currentPage - 1) * pageSize;

        List<CommentDTO> listFinded = new ArrayList<>();

        for (int i = startRow; i < Math.min(parentCommentList.size(), startRow + 5); i++) {
            CommentDTO commentDTO = parentCommentList.get(i);
            listFinded.add(commentDTO);
        }

        for (int i = 0; i < listFinded.size(); i++) {
            CommentDTO commentDTO = listFinded.get(i);
            Integer id = commentDTO.getMemberId();
            String findUserId = memberMapper.findUserIdById(id);
            listFinded.get(i).setUserId(findUserId);
        }

        for (int i = 0; i < listFinded.size(); i++) {
            if (listFinded.get(i).getReplies() != null) {
                for (CommentDTO child : listFinded.get(i).getReplies()) {
                    Integer id = child.getMemberId();
                    String findUserId = memberMapper.findUserIdById(id);
                    child.setUserId(findUserId);
                }
            }
        }

        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, listFinded, model);
    }
}
