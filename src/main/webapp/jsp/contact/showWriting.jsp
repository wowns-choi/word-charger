<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

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
            height: 200vh;
            display: flex;
        }


        .leftpunch{
            width: 30vw;
            height: 200vh;
            background: blue;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            color: white;
            z-index: 1;
            overflow: auto; /* 스크롤바 자동 생성 */
        }

        .rightpunch{
            width: 70vw;
            height: 200vh;
            background: #fff;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            color: black;
            z-index: 8;
            margin-top: 4vh;
            font-family: 'NanumSquareNeo-Variable';
            overflow: auto; /* 스크롤바 자동 생성 */
            position: relative; /* 상대 위치 설정 */
        }
        .my-up-container-in-right{
            position: absolute; /* 절대 위치 설정 */
            top: 8vh; /* 위쪽으로부터 10px */
            left: 50%; /* 왼쪽으로부터 20px */
            transform: translateX(-50%);
            font-size: 40px;

        }

        .button-register{
            width:5vw;
            border: none;
            border: 2px solid black;
            border-radius: 10px;
            background-color: #fafafa;
            margin-right: 1vw;
            transition: background-color 0.5s ease; /* 배경색 변경을 0.5초 동안 부드럽게 진행 */
        }
        .button-register:hover{
            background-color: #ffdead;
        }

        .my-middle-container-in-right{
            position: absolute; /* 절대 위치 설정 */
            top: 20vh; /* 위쪽으로부터 10px */
            left: 50%; /* 왼쪽으로부터 20px */
            transform: translateX(-50%);
            width: 80%; /* .rightpunch의 80% 너비 */
            height: 50vh; /* .rightpunch의 70% 높이 */
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .comment{
            position: absolute; /* 절대 위치 설정 */
            top: 80vh; /* 위쪽으로부터 10px */
            left: 50px; /* 왼쪽으로부터 20px */
            border-top: 1px solid black;
        }
        .user-id{
            position: absolute; /* 절대 위치 설정 */
            top: 85vh; /* 위쪽으로부터 10px */
            left: 50px; /* 왼쪽으로부터 20px */
            border: 2px solid black;
            border-radius: 5px;
            background-color: #ffdead;
            padding: 5px;
        }
        .comment-insert-form{
            position: absolute; /* 절대 위치 설정 */
            top: 90vh; /* 위쪽으로부터 10px */
            left: 50px; /* 왼쪽으로부터 20px */
            width: 100%;
            height: 100px;
        }

        .comment-container{
            position: absolute; /* 절대 위치 설정 */
            top: 110vh; /* 위쪽으로부터 10px */
            margin : 0 50px;
            width: calc(100% - 100px); /* 전체 너비에서 양쪽 마진을 뺀 너비 */
            height: 1000px;
            border-top: 3px solid #ff8c8c;

        }
        .pagination{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            font-size: 25px;
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
                ${findedWritingByWritingNum.title}
            </div>

            <div class="my-middle-container-in-right">
                <textarea style="width:100%; height:100%; border:2px solid black; border-radius:5px; background-color:#dedede;" readOnly> ${findedWritingByWritingNum.content} </textarea>
            </div>

            <div class="comment">
                댓글
            </div>

            <div class="user-id">
                ${loginedMemberId}
            </div>

            <div class="comment-insert-form">
                <form:form modelAttribute="commentDTO" method="post">
                <form:textarea path="content" style="width:70%; height:100px; border:2px solid black;"/>
                <form:hidden path="id" value="${loginedMemberId}"/>
                <button type="submit" class="button-register" id="writing-button" style="margin-top:0.3vh;"> 등록 </button>
                </form:form>
            </div>

            <div class="comment-container">

                <c:forEach var="c" items="${findedCommentList}">
                <div style="margin-top: 20px; width:50%;">
                    <div style="font-size: 12px; font-weight: bold; color:#ff3d3d; ">
                        <span style="border: 2px solid black; border-radius: 5px; box-shadow: 0 0 0 2px white, 0 0 0 4px black;">
                        ${c.id}
                        </span>
                    </div>
                    <div>
                        ${c.content}
                    </div>
                </div>
                </c:forEach>

                <div class="pagination">
                    <c:if test="${currentGroupFirstPage != 1}">
                        <a href="/show-writing?page=${currentGroupFirstPage-numberPerGroup}&writingNum=${writingNum}"> &laquo; 이전</a>
                    </c:if>


                    <c:forEach var="i" begin="${currentGroupFirstPage}" end="${currentGroupLastPage}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span>${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="/show-writing?page=${i}&writingNum=${writingNum}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentGroupLastPage != totalPageCount}">
                        <a href="/show-writing?page=${currentGroupLastPage + 1}&writingNum=${writingNum}">다음 &raquo;</a>

                    </c:if>
                </div>

            </div>

        </div>

</div>

<script>
    document.getElementById('secretWritingCheckBox').addEventListener('change', function(){
        var hiddenPasswordInput = document.getElementById('hiddenPasswordInput');
        if(this.checked){
            hiddenPasswordInput.style.display = 'block';
        } else{
            hiddenPasswordInput.style.display = 'none';
        }
    });
</script>


</body>
</html>