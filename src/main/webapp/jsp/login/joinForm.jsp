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
        .full-frame{
            width: 100vw;
            min-height: 100vh;
            display: flex;
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            justify-content: center;
            align-items: center;
            background: #757575;
        }
        .content-container{
            width: 35vw;
            height: 80vh;
            background: #fff;
            border-radius: 10px;
            /* flex-box */
            display: flex;
            flex-direction: column;
            /*padding 조절 */
            padding-left: 3vw;
            padding-right: 3vw;
            padding-top: 3vh;
            padding-bottom: 3vh;
            background-color: green;
        }
        .id-password-container{
            width: 100%;
            height: 15vh;
            background-color: yellow;
            padding-top: 1vh;
            padding-bottom: 1vh;

        }
        .name-phone-container{
            width: 100%;
            height: 15vh;
            background-color: blue;
            padding-top: 1vh;
            padding-bottom: 1vh;
        }
        .tag-for-verification-message{
            width: 100%;
            height: 20px;
            background-color: purple;
        }


    </style>
</head>
<body>
    <div class="full-frame">
        <div class="content-container">
            <form:form modelAttribute="memberDTO" method="post">

                <div class="id-password-container">
                    <form:input type="text" path="id" id="user-id" class="input-tag" placeholder="아이디" style="display: inline-block;"/>
                    <span id="user-id-status" style="font-size:11px; display:inline-block;">
                    </span>
                    <div class="tag-for-verification-message" >
                        <form:errors path="id"/>
                    </div>


                    <form:input type="password" path="password" id="user-password" class="input-tag" placeholder="비밀번호" style=" display:inline-block;"/>
                    <span id="user-password-status" style="display:inline-block;">
                    </span>
                    <div style="font-size:14px;">
                        비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.
                    </div>
                    <div class="tag-for-verification-message">
                    <form:errors path="password" class="form-errors-css"/>
                    </div>
                </div>

                <div class="name-phone-container">
                    <form:input type="text" path="userName" class="input-tag" placeholder="이름" />
                    <form:errors path="userName" class="form-errors-css" />

                    <div>
                    <form:input path="phoneNumberStart" type="text" style="width:25%;" placeholder="000" class="input-tag"/>
                    <form:input path="phoneNumberMiddle" type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                    <form:input path="phoneNumberEnd" type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                    <br/>
                    <form:errors path="phoneNumberStart" style="width:25%;" class="form-errors-css"/>
                    </div>
                </div>




                <!--다음 주소 api 사용 시작-->
                <form:input type="text" path="zipCode" id="sample4_postcode" class="input-tag" placeholder="우편번호" readonly="true"/>
                <form:errors path="zipCode" class="form-errors-css"/>
                <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>

                <div>
                <form:input type="text" path="streetAddress" id="sample4_roadAddress" class="input-tag" placeholder="도로명주소" style="margin-top:1.5px;" readonly="true" />
                <!--<form:errors path="streetAddress"/>-->
                </div>
                <div>
                <form:input type="text" path="address" id="sample4_jibunAddress" class="input-tag" placeholder="지번주소" style="margin-top:1.5px;" readonly="true"/>
                <!--<form:errors path="address"/>-->
                </div>
                <div>
                <span id="guide" style="color:#999;display:none"></span>
                </div>
                <div>
                <form:input type="text" path="detailAddress" id="sample4_detailAddress" class="input-tag" placeholder="상세주소" style="margin-top:1.5px;" />
                <!--<form:errors path="detailAddress"/>-->
                </div>
                <div>
                <form:input type="text" path="referenceItem" id="sample4_extraAddress" class="input-tag" placeholder="참고항목" style="margin-top:1.5px;" readonly="true" />
                <!--다음 주소 api 사용 종료-->

                <!-- 이걸 왜 안써줬냐? 이걸 써줬더니 오히려 중복으로 on 이 아니라, on,on 이 나왔음.
                <input type="hidden" name="myCheckbox1" value="${memberDTO["myCheckbox1"]}">
                <input type="hidden" name="myCheckbox2" value="${memberDTO["myCheckbox2"]}">
                <input type="hidden" name="myCheckbox3" value="${memberDTO["myCheckbox3"]}">
                -->

                <div class="join-btn">
                <input type="submit" value="회원가입"/>
                </div>
            </form:form>
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