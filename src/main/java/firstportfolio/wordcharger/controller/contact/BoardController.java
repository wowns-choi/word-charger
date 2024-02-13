package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.*;
import firstportfolio.wordcharger.repository.*;

import firstportfolio.wordcharger.sevice.board.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final MemberMapper memberMapper;

    private final PostsMapper postsMapper;
    private final PostPasswordMapper postPasswordMapper;
    private final CommentsMapper commentsMapper;
    private final PostViewMapper postViewMapper;
    private final PaginationService paginationService;
    @GetMapping("/board-home")
    public String boardHomeControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page, Model model) {
        Integer currentPage = page; // 현재 페이지
        Integer pageSize = 20; // 페이지당 보여질 post 의 수
        Integer totalPosts = postsMapper.findAllPostsCount(); // 모든 post 의 개수
        Integer pageGroupSize = 9; // 그룹당 페이지의 개수

        // 현재 페이지가 보여줘야 하는 PostsDTO 객체들을 담은 List자료구조
        int startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = postsMapper.findCurrentPagePosts(startRow, pageSize);

        //currentPage,pageSize,totalWritings, pageGroupSize, currentPagePosts
        paginationService.pagination(currentPage,pageSize,totalPosts, pageGroupSize, currentPagePosts, model);

        return "/contact/boardHome";
    }

    @RequestMapping("find-writings-by-title-writer-content")
    public String findWritingControllerMethod(@RequestParam String byWhatType, @RequestParam String hintToFind, @RequestParam(required = false, defaultValue = "1") Integer page, Model model) {

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
        return "/contact/boardHomeFind";
    }

    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        model.addAttribute("postGenerateDTO", new PostGenerateDTO());

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("id", loginedMember.getId());
        model.addAttribute("userId", loginedMember.getUserId());

        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute PostGenerateDTO writingDTO, BindingResult bindingResult, Model model) {


        String title = writingDTO.getTitle();
        Integer memberId = writingDTO.getMemberId();
        Boolean secretWritingCheckBox = writingDTO.getIs_private();
        String writingPassword = writingDTO.getPostPassword();
        String content = writingDTO.getContent();

        if (bindingResult.hasErrors()) {
            if (secretWritingCheckBox.equals(true)) {
                model.addAttribute("show", true);
            }
            return "contact/writingPage";
        }

        if (secretWritingCheckBox.equals(true)) {
            if (writingPassword.equals("")) {
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                return "/contact/writingPage";
            }
        }

        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        postsMapper.insertPost(title, memberId, isPrivate, content); //posts 테이블에 insert
        //비밀번호는 post_password 테이블에 넣어야 함.
        //그러려면, 방금 insert 된 posts 테이블의 행에 id 컬럼값을 알아야 하므로, 조회한다.
        Integer findPostId = postsMapper.selectPostByMemberIdAndTitle(memberId, title);
        postPasswordMapper.insertPostPassword(findPostId, writingPassword); //비밀번호를 post_password테이블에 넣음

        return "redirect:/board-home";
    }



    @GetMapping("show-writing")
    public String showWritingControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam Integer postId, Model model, HttpServletRequest request) {


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

        return "/contact/showWriting";

    }

    @PostMapping("show-writing")
    public String InsertCommentControllerMethod(@RequestParam String content, @RequestParam Integer postId, @RequestParam String memberId) {
        MemberJoinDTO findMember = memberMapper.findMemberById(memberId);
        commentsMapper.insertComment(postId, findMember.getId(), content);
        return "redirect:/show-writing?postId=" + postId;
    }

    @PostMapping("/reply-save")
    public String replySave(@RequestParam String content, @RequestParam String postId, @RequestParam String memberId, @RequestParam String parentCommentId){
        commentsMapper.insertComment2(Integer.valueOf(postId), Integer.valueOf(memberId), content, Integer.valueOf(parentCommentId));
        return "redirect:/show-writing?postId=" + postId;
    }
}
