package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.*;
import firstportfolio.wordcharger.repository.*;

import firstportfolio.wordcharger.sevice.common.WritingService;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
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
    private final WritingMapper writingMapper;
    private final WritingService writingService;
    private final UserCommentMapper userCommentMapper;
    private final PostsMapper postsMapper;
    private final PostPasswordMapper postPasswordMapper;
    private final CommentsMapper commentsMapper;
    @GetMapping("/board-home")
    public String boardHomeControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page, Model model) {

        Integer currentPage = page;
        Integer pageSize = 20;

        Integer totalWritings = postsMapper.findAllPostsCount(); //모든 writing의 "개수"를 가져옴

        //totalWritings 를 double 타입으로 바꾸면, pageSize 는 int->double 로 자동형변환
        //Math.ceil 은 0이 아닌 소수점이 존재하는 무조건 올림처리해버림.
        Integer totalPages = (int) Math.ceil((double) totalWritings / pageSize);

        int startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> currentPagePosts = postsMapper.findCurrentPagePosts(startRow, pageSize);
        // 지울 것List<WritingDTOSelectVersion> currentPageWritings = writingService.findCurrentPageWritings(startRow, pageSize);

        Integer pageGroupSize = 9;
        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);


        model.addAttribute("currentPageWritings", currentPagePosts);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize", pageGroupSize);

        return "/contact/boardHome";

    }

    @RequestMapping("find-writings-by-title-writer-content")
    public String findWritingControllerMethod(@RequestParam String byWhatType, @RequestParam String hintToFind, @RequestParam(required = false, defaultValue = "1") Integer page, Model model){

        Integer currentPage = page;

        //pageSize 는 한페이지당 몇개의 글이 올라갈것인가? 의 문제이다.
        Integer pageSize = 3;

        Integer startRow = (currentPage - 1) * pageSize;


        Map<String, Object> returnMap = writingService.findTotalWritingByTitleWriterContent(byWhatType, hintToFind, startRow, pageSize);
        Integer findedWritingTotal = (Integer) returnMap.get("findedWritingTotal");
        List<WritingDTOSelectVersion> currentPageWritings = (List<WritingDTOSelectVersion>)returnMap.get("currentPageWritings");

        int totalPages = (int) Math.ceil((double) findedWritingTotal / pageSize);

        Integer pageGroupSize = 5;

        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);


        model.addAttribute("currentPageWritings", currentPageWritings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize", pageGroupSize);

        model.addAttribute("byWhatType", byWhatType);
        model.addAttribute("hintToFind", hintToFind);

        return "/contact/boardHomeFind";
    }

    @GetMapping("/writing-page")
    public String boardWritingPageControllerMethod(Model model, HttpServletRequest request) {
        model.addAttribute("postGenerateDTO", new PostGenerateDTO());

        HttpSession session = request.getSession();
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("id", loginedMember.getId());
        model.addAttribute("userId", loginedMember.getUserId());

        return "/contact/writingPage";
    }

    @PostMapping("/writing-page")
    public String boardWritingPagePostMappingControllerMethod(@Valid @ModelAttribute PostGenerateDTO writingDTO, BindingResult bindingResult, Model model) {

        log.info("1111111111111111111111111111111111111111");

        String title = writingDTO.getTitle();
        Integer memberId = writingDTO.getMemberId();
        Boolean secretWritingCheckBox = writingDTO.getIs_private();
        String writingPassword = writingDTO.getPostPassword();
        String content = writingDTO.getContent();

        log.info("secretWritingCheckbox=================", secretWritingCheckBox);

        if (bindingResult.hasErrors()) {
            if (secretWritingCheckBox.equals(true)) {
                model.addAttribute("show", true);
            }
            log.info("bindingResult ==============={}", bindingResult);
            log.info("22222222222222222222222222222222222222222222222222222");
            return "contact/writingPage";
        }


        if (secretWritingCheckBox.equals(true)) {
            if (writingPassword.equals("")) {
                model.addAttribute("show", true);
                model.addAttribute("notInsertPassword", "비밀번호를 안쓰셨어요");
                log.info("333333333333333333333333333333333333333333333333333");
                return "/contact/writingPage";
            }
        }

        Integer isPrivate = secretWritingCheckBox ? 1 : 0;
        log.info("4444444444444444444444444444444444444444444444444");
        postsMapper.insertPost(title, memberId, isPrivate, content); //posts 테이블에 insert
        //비밀번호는 post_password 테이블에 넣어야 함.
        //그러려면, 방금 insert 된 posts 테이블의 행에 id 컬럼값을 알아야 하므로, 조회한다.
        Integer findPostId = postsMapper.selectPostByMemberIdAndTitle(memberId, title);
        postPasswordMapper.insertPostPassword(findPostId, writingPassword); //비밀번호를 post_password테이블에 넣음
        log.info("5555555555555555555555555555555555555555555555");
        //writingMapper.insertWriting(title, userId, isPrivate, writingPassword, content);

        log.info("6666666666666666666666666666666666666666666666666666");
        return "redirect:/board-home";
    }


    //이 아래부터 시작
    @GetMapping("show-writing")
    public String showWritingControllerMethod(@RequestParam(required = false, defaultValue = "1") Integer page ,@RequestParam Integer postId, Model model, HttpServletRequest request){


        //post테이블의 아이디컬럼 값으로 게시글을 찾아와야함.
        PostsDTO findPost = postsMapper.findPostById(postId);

        model.addAttribute("findPost", findPost);

        //만약, 비밀번호가 있는 게시글이라면, 비밀번호창을 띄워줘야지. //
//        if(findPost.getIs_private() == 1){
//            return "";
//        }


//   지워     WritingDTOSelectVersion findedWritingByWritingNum = writingMapper.findWritingByWritingNum(postId);
  // 지워     model.addAttribute("findedWritingByWritingNum", findedWritingByWritingNum);

        HttpSession session = request.getSession();
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        model.addAttribute("loginedMemberId", loginedMember);

//        model.addAttribute("commentDTO", new CommentDTO());
//        model.addAttribute("writingNum", postId);

        //--------------------------------------------------------

        //현재 페이지 1
        Integer currentPage = page;
        log.info("currentPage========================={}", currentPage);

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

        for(CommentDTO comment : findComments){
            if (comment.getParentCommentId() == null) {
                parentCommentList.add(comment);
            }
        }

        //전체 코멘트 개수
        Integer allCommentCount = parentCommentList.size();
        //한 페이지당 댓글 개수
        Integer numberPerPage = 5;

        //총 페이지 개수
        Integer totalPageCount = (int) Math.ceil((double) allCommentCount / numberPerPage);

        //그룹당 페이지수
        Integer numberPerGroup = 5;

        //현재 그룹은?
        Integer currentGroup = (int) Math.ceil((double) currentPage / numberPerGroup);

        //현재 그룹의 첫페이지
        Integer currentGroupFirstPage = (currentGroup - 1) * numberPerGroup + 1;
        //현재 그룹의 마지막 페이지
        Integer currentGroupLastPage = Math.min(currentGroupFirstPage + numberPerGroup - 1, totalPageCount);

        log.info("currentGroupFirstPage==============={}", currentGroupFirstPage);
        log.info("currentGroupLastPage==============={}", currentGroupLastPage);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("numberPerGroup", numberPerGroup);
        model.addAttribute("totalPageCount", totalPageCount);

        // 게시물 5개 주는 거는 아래 코드로 됬음.
        Integer startRow = (currentPage - 1) * numberPerPage;

        List<CommentDTO> listFinded = new ArrayList<>();
        log.info("startRow==========={}", startRow);
        log.info("findedCommentList = {}", listFinded);

        for (int i = startRow; i < Math.min(parentCommentList.size(), startRow + 5); i++) {
            CommentDTO commentDTO = parentCommentList.get(i);
            listFinded.add(commentDTO);
        }

        model.addAttribute("findedCommentList", listFinded);
        log.info("findedCommentList aaaaaaaaaaaaaaaaaaaaa= {}", listFinded);

        return "/contact/showWriting";

    }
    @PostMapping("show-writing")
    public String InsertCommentControllerMethod(@RequestParam String content, @RequestParam String postId, @RequestParam String memberId){
//        userCommentMapper.insertComment(commentDTO.getWritingNum(), commentDTO.getId(), commentDTO.getContent());
//        return "redirect:/show-writing?writingNum=" + commentDTO.getWritingNum();
        return "hello";
    }


}
