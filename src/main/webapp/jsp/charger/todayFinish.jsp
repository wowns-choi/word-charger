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



    <!-- ajax 자바스크립트 시작 -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $('#toHome').on('click', function(e){
                    e.preventDefault();
                    window.location.href="/chargerHome";
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
            justify-content: center;
            align-items: center; /* 내부 요소를 세로 중앙에 배치 */
            overflow: hidden; /* 내부 컨텐츠가 밖으로 넘치지 않도록 함 */
            box-shadow: -5px -5px 10px #ff6b3f, 5px 5px 8px #bf4b2b;
            border: 10px solid black;
            border-radius: 25px;
            border-style: double;
            font-family: 'MYYeongnamnu';
            font-size : 30px;
        }

        #toHome{
            gap: 5vw;
            transition: background-color 0.5s ease; /* 배경색 변경을 0.5초 동안 부드럽게 진행 */
            font-size: 20px;
            background-color: white;
            border: 3px solid black;
            border-radius: 25px;
            margin-top: 1vh;
        }
        #toHome:hover{
            background-color: #ff6b3f;
        }


    </style>
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar.jsp" />
<!--네브 바 종료 -->

    <div class="exclude">
        <div class="whitecontainer">
            <div> 오늘 영단어 공부 끝! </div>
            <button id="toHome"> Charger Home 으로 가기 </button>
        </div>
    </div>


</body>
</html>

