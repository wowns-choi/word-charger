<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/board/boardHome.css">
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

    <script>
    document.addEventListener('DOMContentLoaded', function(){
        document.querySelectorAll('.post-link').forEach(function(element){
            element.addEventListener('click', function(event){
                event.preventDefault(); // 기본 앵커 태그 동작 방지.

                var postId = this.getAttribute('data-postid');
                console.log('AAAAAAAAAAAAAAAAAAA');

                //우선, 글을 클릭한 사용자가 글을 쓴 사람과 같은 사람인지부터 확인해줘야함.
                //동일인이라면, 굳이 비밀번호를 안물어보기 위함이다.
                fetch('/is-this-you?postId=' + postId)
                    .then(response => {
                        if(!response.ok){
                            throw new Error('Network response was not ok');
                        }
                        return response.text();
                    })
                    .then(data => {
                        console.log(data);
                                        console.log('ABBBBBBBBBBBBBBB');

                        if(data == 'yes'){
                            window.location.href="/show-writing?postId="+postId;
                        }else{
                             //fetch 이용해서 통신. 비밀글이 있는지 여부를 확인해줄거임.
                            fetch('/is-this-secret?postId=' + postId)
                                .then(response => {
                                    if(!response.ok){
                                        throw new Error('Network response was not ok');
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                                                        console.log('ccccccccccccccccccccc');
                                    console.log(data.isThisSecret);
                                    if(data.isThisSecret == 'no'){ // 비밀글이 아닌 경우
                                        window.location.href="/show-writing?postId="+postId;
                                    }else{ //비밀글인 경우
                                                                            console.log('dddddddddddddddddddddddd');

                                        let input = prompt('게시물의 비밀번호를 입력하세요');
                                        if(input == data.writingPassword){ //비밀번호가 일치한다면
                                            window.location.href="/show-writing?postId="+postId;
                                        } else{
                                            alert('비밀번호가 틀렸습니다.');
                                        }
                                    }
                                })
                                .catch(error => console.error('There has been a problem with your fetch operation:', error));
                        }
                    })
                    .catch(error => console.error('There has been a problem with your fetch operation:', error));



            });
        })
    });
    </script>


</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <!-- 게시물을 수정할 수 없는 사람이 수정을 시도했을 경우에만 띄워질 alert 창 -->
        <% if (request.getAttribute("hecker") != null) { %>
        <script>
            // alert 창으로 메시지 띄우기
            alert("<%= request.getAttribute("hecker") %>");
        </script>
        <% } %>
    <!-- ---------------------------- -->

    <div id="board-container">
        <div id="page-title">
            <div></div>
            <div>게시판</div>
            <div></div>
        </div>
        <div id="page-body">
            <div></div>
                <div>
                    <div></div>
                    <div>
                        <div class="board">
                            <span>글번호</span>
                            <span>제목</span>
                            <span>작성자</span>
                            <span>작성날짜</span>
                            <span>조회수</span>
                        </div>
                    </div>



                    <div>
                        <div class="board-2">
                            <c:forEach var="post" items="${currentPagePosts}">
                                <div class="post-row"> <!-- 각 게시물을 감싸는 div 추가 -->
                                    <span>${post.id}</span>
                                    <a href="#" class="post-link" data-postid="${post.id}">
                                        <c:if test="${post.isPrivate == 1}">
                                            <i class="fa-solid fa-lock" style="color:red;"></i>
                                        </c:if>
                                        ${post.title}
                                    </a>
                                    <span>${post.userId}</span>
                                    <span>${post.stringWritingDate}</span>
                                    <span>${post.viewNumber}</span>
                                </div>
                            </c:forEach>
                        </div>
                        <div id="post-make-div">
                            <a href="/writing-page" class="post-make" >&nbsp;글 작성&nbsp;</a>
                            <a href="/board-home" class="post-make" >&nbsp;최신순&nbsp;</a>
                            <a href="/board-home-order-by-like-num" class="post-make" >&nbsp;좋아요순&nbsp;</a>
                            <a href="/board-home-order-by-view-num" class="post-make" >&nbsp;조회수순&nbsp;</a>

                        </div>
                        <div id="find-posts">
                            <!--제목, 작성자, 내용 으로 게시글 찾기 -->
                            <form action="/find-posts-by-title-writer-content" method="post">
                                <select name="byWhatType">
                                    <option value="title">제목</option>성
                                    <option value="writer">작성자</option>
                                    <option value="content">내용</option>
                                </select>

                                <input type="text" name="hintToFind" />
                                <button type="submit"> 찾기 </button>
                            </form>
                        </div>
                        <div id="pagination">
                            <!-- 페이지네이션 -->
                            <!-- 이전 그룹 링크 -->
                            <c:if test="${currentGroupFirstPage != 1}">
                                <a href="/board-home?page=${currentGroupFirstPage - pageGroupSize}">&laquo; 이전</a>
                            </c:if>

                            <!-- 현재 페이지 그룹의 페이지 링크 forEach 문 돌림 -->
                            <c:forEach var="i" begin="${currentGroupFirstPage}" end="${currentGroupLastPage}">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span class="current-page" style="color:#e06500; ">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/board-home?page=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <!-- 다음 그룹 링크 -->
                            <c:if test="${currentGroupLastPage != totalPages}">
                                <a href="/board-home?page=${currentGroupLastPage + 1}">다음 &raquo;</a>
                            </c:if>
                        </div>


                    </div>

                </div>
            <div>
            </div>
        </div>

    </div>
</body>
</html>