package firstportfolio.wordcharger.controller.contact;

import firstportfolio.wordcharger.DTO.CommentDTO;
import firstportfolio.wordcharger.DTO.MemberJoinDTO;
import firstportfolio.wordcharger.DTO.PostsDTO;
import firstportfolio.wordcharger.repository.*;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyWritingController {
    private final PostsMapper postsMapper;
    private final CommentsMapper commentsMapper;
    private final MemberMapper memberMapper;
    private final PostViewMapper postViewMapper;
    @GetMapping("/my-writing")
    public String MyWritingHomeControllerMethod (@RequestParam(required = false,defaultValue = "1") Integer page,  HttpServletRequest request, Model model){

        Integer loginedMemberId = FindLoginedMemberIdUtil.findLoginedMember(request);


        //총 글 수 에 대해서.
        //유저 자신이 쓴 글이 있을 수 있고,
        //유저 자신이 댓글을 쓴 글이 있을 수 있음.
        //유저 자신이 쓴 글에 대하여

        HttpSession session = request.getSession(false);
        MemberJoinDTO loginedMember = (MemberJoinDTO) session.getAttribute("loginedMember");
        Integer id = loginedMember.getId();
        List<PostsDTO> findPosts = postsMapper.findPostByMemberId(id);

        List<PostsDTO> totalPostsList = new ArrayList<>();
        List<Integer> findPostId = commentsMapper.findPostIdByMemberId(id);

        for (int i = 0; i < findPostId.size(); i++) {
            PostsDTO findPost = postsMapper.findPostById(findPostId.get(i));
            findPosts.add(findPost);
        }

        //findPosts 안에 들어있는 PostsDTO 객체를 하나씩 꺼내와서
        //id 가 동일한 것들은 제외시켜야함.
        Set<Integer> existingIds = new HashSet<>();
        Iterator<PostsDTO> iterator = findPosts.iterator();
        while (iterator.hasNext()) {
            PostsDTO post = iterator.next();
            if (!existingIds.add(post.getId())) {
                // Set에 id 추가가 실패하면, 이미 존재하는 id이므로 현재 요소를 제거
                iterator.remove();
            }
        }




        log.info("findPost----wownswownswownswownswownswowns{}", findPosts);

        //현재 페이지
        Integer currentPage = page;
        //페이지당 게시물 수
        Integer pageSize = 5;
        //총 글수
        Integer totalWritings = findPosts.size();
        //총 페이지수
        Integer totalPages = (int)Math.ceil((double)totalWritings / pageSize);
        //그룹당 페이지 수
        Integer pageGroupSize = 5;
        //현재 그룹
        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        //현재 그룹 첫페이지
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;
        //현재 그룹 마지막페이지
        int currentGroupLastPage = Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);

        model.addAttribute("currentGroupFirstPage", currentGroupFirstPage);
        model.addAttribute("currentGroupLastPage", currentGroupLastPage);
        model.addAttribute("pageGroupSize",pageGroupSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);


        //findPosts 여기에 이제, posts 들이 모두 들어있단 말이야.
        //정렬이 필요할 거 같은데? posts DTO 의 id라는 필드가 낮을수록 먼저 생성된거니까, 그거 순대로 정렬필요

        //람다를 쓰면 오름차순으로 정렬이 다 되고 난 후 그게 그대로 findPosts에 담김.
        findPosts.sort((post1, post2) -> post2.getId().compareTo(post1.getId()));

        Integer startRow = (currentPage - 1) * pageSize;
        List<PostsDTO> listFinded = new ArrayList<>();

        for (int i = startRow; i < Math.min(findPosts.size(), startRow + 5); i++) {
            PostsDTO postsDTO = findPosts.get(i);
            listFinded.add(postsDTO);
        }


        //몇가지 빼 먹은게 있어.
        //각, postsDTO 안에 userId 가 없다는 거. 중요해.
        //그리고, viewNumber도 비워져 있다는 것.

        for (int i = 0; i < listFinded.size(); i++) {
            PostsDTO postsDTO = listFinded.get(i);

            String findUserId = memberMapper.findUserIdById(postsDTO.getId());
            postsDTO.setUserId(findUserId);

            Integer postViewCount = postViewMapper.findPostViewCountByPostId(postsDTO.getId());
            postsDTO.setViewNumber(postViewCount);
        }


        model.addAttribute("listFinded", listFinded);


        return "/contact/myWriting";

    }
}
