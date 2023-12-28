<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">

    <!--아이콘 쓰기 위한 링크 Font Awesome -->
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

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
        .my-side-bar{
            width: 30vw;
            height: 100vh;
            background: #fcfcfc;
            position:fixed;
            z-index: 2;
            border-right: 1px solid black;
        }
        .side-bar-inner-container{
            margin-top: 6vh;
            margin-left: 10vw;

        }
        .side-bar-button{
            width:100%;
            height: 5vh;
            background: #fff;
            border: none;
            border-left: 2px solid black;
            font-family: 'NanumSquareNeo-Variable';
            transition: background-color 0.5s ease; /* 배경색 변경을 0.5초 동안 부드럽게 진행 */
        }
        .side-bar-button:hover{
            background-color: #ffdead;
        }
        .side-bar-button-disabled{
            width:100%;
            height: 5vh;
            background: #fff;
            border: none;
            border-left: 2px solid black;
            font-family: 'NanumSquareNeo-Variable';
        }
        #borrowd-icon{
            margin-right: 1vw;
        }



    </style>
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar.jsp" />
<!--네브 바 종료 -->

        <div class="my-side-bar">
            <div class="side-bar-inner-container">
                <button class="side-bar-button-disabled" disabled>
                </button>

                <button class="side-bar-button" id="side-bar-first-button">
                        <i class="fa-regular fa-circle-question" id="borrowd-icon"></i>자주 묻는 질문
                </button>
                <button class="side-bar-button" id="side-bar-second-button">
                        <i class="fa-solid fa-train-subway" id="borrowd-icon"></i>오시는 길
                </button>
                <button class="side-bar-button" id="side-bar-third-button">
                        <i class="fa-regular fa-comments" id="borrowd-icon"></i>자유게시판
                </button>
                <button class="side-bar-button" id="side-bar-fourth-button">
                        <i class="fa-regular fa-paper-plane" id="borrowd-icon"></i>내가 작성한 글
                </button>

                <button class="side-bar-button-disabled" style="border-bottom:1px solid black;" disabled>
                </button>
            </div>
        </div>



    <script>
        document.getElementById('side-bar-first-button').addEventListener("click",function(){
            window.location.href="/faq"
        });
        document.getElementById('side-bar-second-button').addEventListener("click",function(){
            window.location.href="/way-to-come"
        });
        document.getElementById('side-bar-third-button').addEventListener("click",function(){
            window.location.href="/board-home"
        });
        document.getElementById('side-bar-fourth-button').addEventListener("click",function(){
            window.location.href="/my-writing"
        });
    </script>

</body>
</html>