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
            margin-top: 4vh;
            font-family: 'MYYeongnamnu';
        }
        .my-up-container-in-right{
            width: 70vw;
            height:15vh;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            font-size: 40px;


        }
        .my-middle-container-in-right{
            width: 70vw;
            height: 15vh;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
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
            height: 64vh;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */

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
                 ${title}
            </div>

            <div class="my-middle-container-in-right">
                <div style="width:100%">
                    <span style="width:15%; margin-left:7.5vw;">
                        작성자 :
                    </span>
                    <span>
                        ${userId}
                    </span>
                </div>
            </div>

            <div class="my-down-container-in-right">
                <textarea readonly style="width:80%; height:70%; border:2px solid black; border-radius:5px;">${content}</textarea>
                <div style="display:flex;">
                <button type="submit" class="button-in-middle-container" id="writing-button" style="margin-top:0.3vh;"> 글 수정 </button>
                <button type="submit" class="button-in-middle-container" id="writing-button" style="margin-top:0.3vh;"> 게시판 홈으로 </button>
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