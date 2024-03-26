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
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

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
                                    <a href="/show-writing?postId=${post.id}">${post.title}</a>
                                    <span>${post.userId}</span>
                                    <span>${post.writingDate}</span>
                                    <span>${post.viewNumber}</span>
                                </div>
                            </c:forEach>
                            <c:if test="${not empty nothing}">
                             <div style="display:flex; justify-content: center; align-items: center;">   ${nothing} </div>
                            </c:if>
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
                                    <option value="title" ${byWhatType == 'title' ? 'selected' : ''} >제목</option>
                                    <option value="writer" ${byWhatType == 'writer' ? 'selected' : ''} >작성자</option>
                                    <option value="content" ${byWhatType == 'content' ? 'selected' : ''} >내용</option>
                                </select>

                                <input type="text" name="hintToFind" value="${hintToFind}" />
                                <button type="submit"> 찾기 </button>
                            </form>
                        </div>
                        <div id="pagination">
                            <!-- 페이지네이션 -->
                            <!-- 이전 그룹 링크 -->
                            <c:if test="${currentGroupFirstPage != 1}">
                                <a href="/find-posts-by-title-writer-content?page=${currentGroupFirstPage - pageGroupSize}&byWhatType=${byWhatType}&hintToFind=${hintToFind}">&laquo; 이전</a>
                            </c:if>

                            <!-- 현재 페이지 그룹의 페이지 링크 forEach 문 돌림 -->
                            <c:forEach var="i" begin="${currentGroupFirstPage}" end="${currentGroupLastPage}">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span class="current-page" style="color:#e06500; ">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/find-posts-by-title-writer-content?page=${i}&byWhatType=${byWhatType}&hintToFind=${hintToFind}"">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <!-- 다음 그룹 링크 -->
                            <c:if test="${currentGroupLastPage != totalPages}">
                                <a href="/find-posts-by-title-writer-content?page=${currentGroupLastPage + 1}&byWhatType=${byWhatType}&hintToFind=${hintToFind}">다음 &raquo;</a>
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