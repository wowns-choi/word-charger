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
        .charge-your-brain{
            font-family: 'Sofia', cursive;
            font-size: 100px;
             font-weight: bold;
             margin-bottom:5vh;



        }
        .subText{
            font-family: 'NanumSquareNeo-Variable';
            font-size : 20px;
            font-weight: bold;
            margin-bottom: 5vh;
        }
        .whitecontainer{
            width: 60vw;
            height: 40vh;
            background: #fafafa;
            display: flex; /* Flexbox를 사용하여 내부 요소를 가로로 배치 */
            align-items: center; /* 내부 요소를 세로 중앙에 배치 */
            text-align: left; /* 텍스트를 왼쪽으로 정렬 */
            overflow: hidden; /* 내부 컨텐츠가 밖으로 넘치지 않도록 함 */
            border: 4px solid black;
            border-radius: 25px;
        }

        .left-WC, .right-WC {
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
        }

        .left-WC{
            width: 60%;
            height: 100%;

        }
        .right-WC{
            width: 40%;
            height: 100%;
            font-family: 'MYYeongnamnu';
            font-weight: bold;
            font-size: 16px;
            color: #525252;
            border: 10px solid black;
            border-radius: 25px;
            border-style: double;
        }
        .text-divtag{
            margin-top: 2.5vh;
        }
        .text-decoration{
            color: #001840;
            font-size: 20px;
            border-bottom: 2px solid black;
            border-radius: 25px;
            box-sizing: border-box;  /* border와 padding을 요소의 총 크기에 포함 */
            padding: 7px;
            font-family: 'MYYeongnamnu';

        }
        .picture-container{
            width: 30%;
            height: 50%;
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            align-items: center; /* 세로 중앙 정렬 *
            outline: 2px solid red;
            margin-bottom: 50px;
        }
        .img-thumbnail {
            max-width: 100%; /* 이미지가 컨테이너 너비를 넘지 않도록 함 */
            max-height: 100%; /* 이미지가 컨테이너 높이를 넘지 않도록 함 */
        }
        .formForm{
            display: flex; /* Flexbox를 사용하여 내부 요소 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            gap: 5px;
            margin-top: 5vh;
        }
        .idInput, .passwordInput{
            width: 20vw;
            height: 5vh;
            border-radius: 25px;
            font-size: 16px;

        }
        .loginButton{
            color: #001840;
            font-size: 20px;
            background: #fafafa;
            /*#fcc065 주황컬러*/
            border-radius: 1px;
            box-sizing: border-box;  /* border와 padding을 요소의 총 크기에 포함 */
            padding: 7px;
            font-family: 'MYYeongnamnu';
            transition: all 0.3s ease;
        }
        .loginButton:hover {
            /* 마우스 호버 시 애니메이션 적용 */
            animation: blink-animation 5s ease infinite;
        }

        @keyframes blink-animation {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
        }

    </style>
</head>
<body>



    <div class="exclude">


        <div class="charge-your-brain" >
         [ Login ]
        </div>

        <div class="whitecontainer">
            <div class="left-WC">
            <form:form modelAttribute="loginDTO" method="post" class="formForm">
                <form:input class="idInput" path="id"  placeholder="ID"/>
                ${idIncorrectMessage}
                <form:input class="passwordInput" path="password" placeholder="Password"/>
                ${passwordIncorrectMessage}
                <button type="submit"  class="loginButton" >
                    Login
                </button>
            </form:form>


            </div>
            <div class="right-WC">

                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">아이디 찾기</span></a></div>
                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">비밀번호 찾기</span></a></div>
                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">회원가입 하기</span></a></div>


            </div>
        </div>
    </div>



</body>
</html>