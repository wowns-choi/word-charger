<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">

    <!--chargerHomeYellowButton.css 시작-->
        <link rel="stylesheet" type="text/css" href="../css/chargerHomeYellowButton.css">
    <!--chargerHomeYellowButton.css 종료-->

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
            width: 50vw;
            height: 50vh;
            background: #fafafa;
            display: flex; /* Flexbox를 사용하여 내부 요소를 가로로 배치 */
            align-items: center; /* 내부 요소를 세로 중앙에 배치 */
            text-align: left; /* 텍스트를 왼쪽으로 정렬 */
            overflow: hidden; /* 내부 컨텐츠가 밖으로 넘치지 않도록 함 */

            margin-bottom: 2vh;
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
            border: 10px solid black;
            border-radius: 25px;


        }
        .right-WC{
            width: 40%;
            height: 100%;
            font-family: 'MYYeongnamnu';
            font-weight: bold;
            font-size: 17px;
            color: #525252;
            border: 10px solid black;
            border-radius: 25px;
            border-style: double;
        }
        .text-divtag{
            margin-top: 1vh;
            margin-left: 0.7vw;
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
        .button_containering {
            display: flex; /* Flexbox를 활성화 */
            flex-direction: row; /* 요소들을 가로로 나열 */
            justify-content: center; /* 요소들을 수평 중앙에 정렬 */
            align-items: center; /* 요소들을 수직 중앙에 정렬 */
            margin: 10px; /* 필요에 따라 마진 조정 */
            font-family: 'MYYeongnamnu';
            font-size: 12px;
            gap: 20px; /* 버튼 사이의 간격 설정 */
        }


    </style>
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar.jsp" />
<!--네브 바 종료 -->

    <div class="exclude">
        <div class="whitecontainer">

            <div class="left-WC" >
                <img src="충전기.png" style="max-width:100%; max-height:100%;  ">
            </div>
            <div class="right-WC">

                <div class="text-divtag">5초 안에 정답을 고르세요. </div>
                <div class="text-divtag">틀렸다면, 내일도 하면 됩니다. </div>
                <div class="text-divtag">이미 알고 있는 단어를 다시 보는 시간을    아껴서, 모르는 것만 외우게 해드릴게요  </div>
                <div class="text-divtag"></div>
            </div>

        </div>


        <!--버튼시작-->
            <div class="button_containering">
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>

            </div>
            <!-- 버튼 두번째 줄 시작 -->
            <div class="button_containering">
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
                    <div class="atagAndTextContainer">
                        <a class="atag" href="/zeroToHundred" style="text-decoration: none;">
                            <button class="bts" style="font-size:18px;">
                                <span>1 ~ 100</span>
                            </button>
                        </a>
                        <div>
                            충전할 단어가 ${zeroToHundredTodayAmount}개  있습니다.
                        </div>
                    </div>
            </div>
        <!-- 버튼종료-->
    </div>




</body>
</html>