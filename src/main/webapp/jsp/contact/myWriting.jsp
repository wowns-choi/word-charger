<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">

    <style>
        /*이거 한글 글꼴임*/
        @font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }

        /* right-WC 한글글꼴 */
        @font-face {
            font-family: 'MYYeongnamnu';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-02@1.0/MYYeongnamnu.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }

        .fullpagecontainer{
            width: 100vw;
            height: 100vh;
            display: flex;
        }


        .leftpunch{
            width: 30vw;
            height: 100vh;
            background: blue;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            color: white;
            z-index: 1;
        }

        .rightpunch{
            width: 70vw;
            height: 100vh;
            background: #fff;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            color: black;
            z-index: 8;
            margin-top: 6vh;
            font-family: 'MYYeongnamnu';
        }
        .my-up-container-in-right{
            width: 70vw;
            height:25vh;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            font-size: 40px;
        }
        .my-middle-container-in-right{
            width: 70vw;
            height: 5vh;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: right; /* 가로 중앙 정렬 */
            border-bottom: 1px solid black;
        }
        .button-in-middle-container{
            width:5vw;
            border: none;
            border: 2px solid black;
            border-radius: 10px;
            background-color: #fafafa;
            margin-right: 1vw;
            transition: background-color 0.5s ease; /* 배경색 변경을 0.5초 동안 부드럽게 진행 */


        }
        .button-in-middle-container:hover{
            background-color: #ffdead;
        }
        .my-down-container-in-right{
            width: 70vw;
            height: 70vh;

        }
        .board{
            display: grid;
            grid-template-columns: 1fr 4fr 2fr 1fr 1fr 1fr;
            align-items: center; /* 세로축 중앙 정렬 */
            justify-items: center; /* 가로축 중앙 정렬 */
            border-bottom: 1px solid black;
            font-size: 20px;
            background-color: #ffdead;
        }
        .board-2{
            display: grid;
            grid-template-columns: 1fr 4fr 2fr 1fr 1fr 1fr;
            align-items: center; /* 세로축 중앙 정렬 */
            justify-items: center; /* 가로축 중앙 정렬 */
            border-bottom: 1px solid #878787;
            font-size: 15px;
        }

        .pagination{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            font-size: 25px;
            margin-right: 40px;
        }
        .pagination span, .pagination a{
            margin-right: 40px;
        }
        .pagination span{
            color: #ff6b3f;
        }
        .pagination a{
            text-decoration: none;
            color: inherit;
        }
        .find-by-title-writer-content{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
        }


    </style>
</head>
<body>


<!-- 사이드 바 -->
    <c:import url="/jsp/common/sideBar.jsp" />
<!-- 사이드 바 종료 -->

    <div class="fullpagecontainer">
        <div class="leftpunch">
        </div>

        <div class="rightpunch">

            <div class="my-up-container-in-right">
                자유 게시판
            </div>

            <div class="my-middle-container-in-right">
                <button class="button-in-middle-container" id="writing-button"> 글 작성 </button>
            </div>

            <div class="my-down-container-in-right">
                <div class="board">
                    <span>글번호</span>
                    <span>제목</span>
                    <span>작성자</span>
                    <span>작성날짜</span>
                    <span>조회수</span>
                    <span>좋아요</span>
                </div>

                <div class="board-2">
                    <c:forEach var="writing" items="${myWritings}">
                        <span>${writing.writingNum}</span>
                        <a href="/show-writing?writingNum=${writing.writingNum}"><span style="font-size: 20px;">${writing.title}</span></a>
                        <span>${writing.userId}</span>
                        <span>${writing.writingDate}</span>
                        <span>${writing.viewNumber}</span>
                        <span>${writing.likeNumber}</span>
                    </c:forEach>
                </div>

                <div class="pagination">
                    <!-- 이전 그룹 링크 -->
                    <c:if test="${currentGroupFirstPage != 1}">
                        <a href="/my-writing?page=${currentGroupFirstPage - pageGroupSize}">&laquo; 이전</a>
                    </c:if>

                    <!-- 현재 페이지 그룹의 페이지 링크 forEach 문 돌림 -->
                    <c:forEach var="i" begin="${currentGroupFirstPage}" end="${currentGroupLastPage}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current-page">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="/my-writing?page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- 다음 그룹 링크 -->
                    <c:if test="${currentGroupLastPage != totalPages}">
                        <a href="/my-writing?page=${currentGroupLastPage + 1}">다음 &raquo;</a>
                    </c:if>
                </div>


            </div>
        </div>
</div>

<script>
    document.getElementById('writing-button').addEventListener("click",function(){
        window.location.href="/writing-page"
    });
</script>

</body>
</html>