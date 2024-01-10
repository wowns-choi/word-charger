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

        .full-frame{
            width: 100vw;
            min-height: 100vh;
            display: flex;
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            justify-content: center;
            align-items: center;
            text-align: center;
            background: #757575;
            font-family: 'NanumSquareNeo-Variable';

        }
        .content-container{
            width: 35vw;
            height: 80vh;
            background: #fff;
            border-radius: 10px; /* 여기를 추가하세요 */
            text-align:left;
            padding-left: 20px; /* 왼쪽 여유 공간 추가 */
            padding-top : 50px;

        }
        .inner-align{


        }
        .input-tag{
            font-family: 'NanumSquareNeo-Variable';
            width: 65%;
            border-radius: 3px;
            border: 1px solid #ccc; /* 경계선 색상과 스타일 */
        }
        input:focus {
            background-color: #c7e8ff; /* 클릭 시 배경색을 노란색으로 변경 */
        }
        .join-btn{
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 10vh;
        }


    </style>
</head>
<body>



    <div class="full-frame">
        <div class="content-container">
            <div class="inner-align">
            <form:form modelAttribute="memberDTO" method="post">
                <form:input type="text" path="id" id="user-id" class="input-tag" placeholder="아이디"/>
                <div id="user-id-status" style="font-size:11px; ">
                </div>



                <form:input type="password" path="password" id="user-password" class="input-tag" placeholder="비밀번호" style="margin-top:1.5px;"/>
                <div id="user-password-status" style="font-size:11px; color: red; font-family: 'NanumSquareNeo-Variable';">
                </div>
                <div style="font-size:14px;">
                비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.
                </div>

                <form:input type="text" path="userName" class="input-tag" placeholder="이름" style="margin-top:40px; margin-bottom:1.5px;"/>

                <div>
                <input name="phoneNumberStart"type="text" style="width:25%;" placeholder="000" class="input-tag"/>
                <input name="phoneNumberMiddle"type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                <input name="phoneNumberEnd"type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                </div>

                <!--다음 주소 api 사용 시작-->
                <div style="margin-top:80px;">
                <input type="text" name="zipCode" id="sample4_postcode" class="input-tag" placeholder="우편번호">
                <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
                </div>
                <div>
                <input type="text" name="streetAddress"id="sample4_roadAddress" class="input-tag" placeholder="도로명주소" style="margin-top:1.5px;" >
                </div>
                <div>
                <input type="text" name="address"id="sample4_jibunAddress" class="input-tag" placeholder="지번주소" style="margin-top:1.5px;">
                </div>
                <div>
                <span id="guide" style="color:#999;display:none"></span>
                </div>
                <div>
                <input type="text" name="detailAddress"id="sample4_detailAddress" class="input-tag" placeholder="상세주소" style="margin-top:1.5px;">
                </div>
                <div>
                <input type="text" name="referenceItem"id="sample4_extraAddress" class="input-tag" placeholder="참고항목" style="margin-top:1.5px;">
                </div>
                <!--다음 주소 api 사용 종료-->



                <div class="join-btn">
                <input type="submit" value="회원가입"/>
                </div>


            </form:form>
            </div>
        </div>
    </div>









    <script>
        document.getElementById('user-id').addEventListener('input', function(){
            var userId = this.value;
            //Fetch API를 사용하여 서버에 비동기 요청을 보냄
            fetch('check-user-id', {
                method:'POST',
                body: JSON.stringify({userId: userId}),
                headers: {
                    'Content-Type' : 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                if(data.isAvailable){
                    document.getElementById('user-id-status').innerText='사용 가능한 아이디입니다';
                    document.getElementById('user-id-status').style.color='#0066ff';
                }else{
                    document.getElementById('user-id-status').innerText='이미 사용 중인 아이디입니다.';
                    document.getElementById('user-id-status').style.color='red';
                }
            });
        });


        document.getElementById('user-password').addEventListener('input', function(){
            var userPassword = this.value;
            fetch('check-user-password', {
                method: 'POST',
                body: JSON.stringify({userPassword: userPassword}),
                headers: {
                    'Content-Type' : 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                if(data.insufficientLength){
                    document.getElementById('user-password-status').innerText='8~16글자 사이어야 합니다.';
                    document.getElementById('user-password-status').style.color='red';
                }else if(data.englishNotIncluded){
                    document.getElementById('user-password-status').innerText='영문이 포함되지 않았습니다';
                    document.getElementById('user-password-status').style.color='red';
                }else if(data.specialCharactersNotIncluded){
                    document.getElementById('user-password-status').innerText='특수문자가 포함되지 않았습니다.';
                    document.getElementById('user-password-status').style.color='red';
                }else if(data.numberNotIncluded){
                    document.getElementById('user-password-status').innerText='숫자가 포함되지 않았습니다.';
                    document.getElementById('user-password-status').style.color='red';
                }else{
                    document.getElementById('user-password-status').innerText='사용가능한 비밀번호입니다.';
                    document.getElementById('user-password-status').style.color='#0066ff';
                }
            });



        });
    </script>


<!-- 다음 script 태그 시작 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;

                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>
<!--다음 script 태그 종료 -->


</body>
</html>