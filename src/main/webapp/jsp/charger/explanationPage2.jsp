<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">

    <!--chargerHomeYellowButton.css 시작-->
        <link rel="stylesheet" type="text/css" href="../css/chargerHomeYellowButton.css">
    <!--chargerHomeYellowButton.css 종료-->

    <!-- ajax 자바스크립트 시작 -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $('#next-word').on('click', function(e){
                    var vocabulary = $('[name="vocabulary"]').val();

                    e.preventDefault();
                    window.location.href="/delete-pulled-voca?vocabulary="+vocabulary;
                });
            });
        </script>
    <!-- ajax 자바스크립트 종료 -->


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

        .exclude{
            width: 100vw;
            min-height: 100vh;
            display: flex;
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            justify-content: center;
            align-items: center;
            text-align: center;
            background: #fafafa;

        }
        .whitecontainer{
            width: 50vw;
            height: 50vh;
            background: #fafafa;
            display: flex; /* Flexbox를 사용하여 내부 요소를 가로로 배치 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 수정 */
            align-items: center; /* 내부 요소를 세로 중앙에 배치 */
            text-align: left; /* 텍스트를 왼쪽으로 정렬 */
            overflow: hidden; /* 내부 컨텐츠가 밖으로 넘치지 않도록 함 */
            margin-bottom: 2vh;

            border: 10px solid black;
                        border-radius: 25px;
                        border-style: double;

        }
        .top-WC{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: flex-end; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            width: 100%;
            height: 40%;
            font-size: 60px;

        }
        .up-WC{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 수정 */
            width: 100%;
            height: 40%;
            font-size: 20px;

                        font-family: 'MYYeongnamnu';
        }
        .down-WC{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            width: 100%;
            height: 20%;
            font-family: 'MYYeongnamnu';
            font-weight: bold;
            font-size: 17px;
            color: #525252;

        }
        #next-word{
            gap: 5vw;
            transition: background-color 0.5s ease; /* 배경색 변경을 0.5초 동안 부드럽게 진행 */
            background-color: white;
            border: 3px solid black;
            border-radius: 25px;
        }
        #next-word:hover{
            background-color: #fcc065;
        }


    </style>
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar.jsp" />
<!--네브 바 종료 -->

    <div class="exclude">
        <div class="whitecontainer">
            <div class="top-WC">
                <div id="correctAlarm" style="font-family: 'NanumSquareNeo-Variable';">${voca}</div>
            </div>
            <div class="up-WC">
                <div style="color: #ff7a7d;"> ${correct} </div>
                <div> ${examplesentence1} </div>
                <div> ${examplesentence2}</div>

            </div>
            <div class="down-WC">
                   <button id="next-word"> Next Word </button>
                   <input type="hidden" name="vocabulary" value="${voca}">
            </div>
        </div>
    </div>

</body>
</html>





