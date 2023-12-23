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
            width: 40%;
            height: 100%;
            border: 10px solid #5496ff;
            border-radius: 25px;
            border-style: dotted;
        }
        .right-WC{
            width: 60%;
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
            margin-top: 1vh;
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
    </style>
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar.jsp" />
<!--네브 바 종료 -->

    <div class="exclude">

        <div class="charge-your-brain">
        Charge Your Brain !!
        </div>
        <div class="subText">
            단어 충전소를 통해서 잊어버린 영어단어를 충전하세요 !!
        </div>
        <div class="whitecontainer">
            <div class="left-WC">

                    <img src="head.jpeg" style="max-width:100%; max-height:100%;  ">

            </div>
            <div class="right-WC">
                <div class="text-divtag">에빙하우스의 망각곡선에 따르면,</div>
                <div class="text-divtag"><span class="text-decoration">20분 뒤</span>에는 <span class="text-decoration">58%</span> 만이,</div>
                <div class="text-divtag"><span class="text-decoration">1시간 뒤</span>에는 <span class="text-decoration">44%</span> 만이,</div>
                <div class="text-divtag"><span class="text-decoration">하루 뒤</span>에는 <span class="text-decoration">33%</span> 만이</div>
                <div class="text-divtag">기억에 남습니다.</div>

            </div>
        </div>
    </div>


    <!-- 2page -->
    <div class="exclude">
            <div class="charge-your-brain" style="margin-right:30vw; font-size:130px; margin-bottom:0;">
                But,
            </div>
            <div class="subText" style="margin-left:10vw; font-style: italic;font-size:25px;  margin-top:0; margin-bottom:3vh;">
                "The subject was the only one, being oneself."
            </div>
            <div class="whitecontainer">
                <div class="left-WC">
                        <img src="ebinghaus.jpeg" style="max-width:100%; max-height:100%;  ">
                </div>
                <div class="right-WC">
                    <div class="text-divtag"><span class="text-decoration">피험자가 자기자신 1명 뿐이었으며... -위키백과 </span></div>
                    <div class="text-divtag" style="border-top: 2px solid black; margin-top:30px;">20분 뒤에 58% 만이 기억에 남는다는 것은 에빙하우스 본인에 한정된 진실이었기 때문에, 많은 사람들이 망각이론에 따라 공부했지만 실패했습니다.
                </div>
            </div>
    </div>

    <!-- 3page -->
    <div class="exclude">
        <div class="text-divtag"><span class="text-decoration" style="font-size:30px;">그렇다면, 시간이 지나면 잊어버린다는 사실마저 부정되어야 마땅할까요?</span></div>
        <div class="text-divtag" style="margin-top:3vh"><span class="text-decoration" style="font-size:30px;">사람마다 기억할 수 있는 시간이 다르다면, 그 시간을 측정해주는 기계가 있으면 되지 않을까요?</span></div>
        <div class="text-divtag" style="margin-top:3vh"><span class="text-decoration" style="font-size:30px;">단어충전소는 고객마다 단어마다 기억할 수 있는 시간을 체크해드립니다.  </span></div>
    </div>



</body>
</html>


