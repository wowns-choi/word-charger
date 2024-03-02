<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/user/userInfoUpdate.css">
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->
<div id="height-8vh-from-top">
</div>

    <div id="update-container">
        <div id="left-container"></div>
        <div id="mid-container">
            <form method="post">
            <div>
                아이디 : ${memberAllData.userId}
            </div>
            <div>
                <a href="/password-validation" id="password-update-btn">비밀번호 변경하기</button>
            </div>

            <div>
                이름 : ${memberAllData.userName}
            </div>

            <div>
                핸드폰 번호 :
            </div>
            <div>
                <input type="text" name="phoneNumStart" style="width:25%;" value="${memberAllData.phoneNumStart}" >
                <input type="text" name="phoneNumMiddle" style="width:25%;" value="${memberAllData.phoneNumMiddle}" class="input-tag">
                <input type="text" name="phoneNumEnd" style="width:25%;" value="${memberAllData.phoneNumEnd}" class="input-tag">
            </div>

            <div>
                이메일 :
            </div>
            <div>

                <span>
                ${memberAllData.email} ${memberAllData.domain}
                </span>
                <button id="update-email-btn" type="button"> 이메일 수정하기 </button>
            </div>

            <div id="update-email-div" style="display: none;">

                <input type="text" name="email" placeholder="이메일을 입력해주세요">
                <input type="text" name="customEmailDomain" id="customEmailDomain" placeholder="도메인 직접 입력">

                <select id="emailDomain" name="domain" style="width: 7vw; height: 80%;">
                    <option value="daum.net">daum.net</option>
                    <option value="gamil.com">gmail.com</option>
                    <option value="naver.com">naver.com</option>
                    <option value="custom">직접입력</option>
                </select>

            </div>


            <div class="address-container">
                    <input type="text" name="zipCode" id="sample4_postcode" class="input-tag" placeholder="우편번호" readonly="true" value="${memberAllData.zipCode}"/>
                    <input class="address-btn" type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
                    <div>
                        <input type="text" name="streetAddress" id="sample4_roadAddress" class="input-tag" placeholder="도로명주소" value="${memberAllData.streetAddress}" style="margin-top:1.5px;" readonly="true" />
                    </div>
                    <div>
                        <input type="text" name="address" id="sample4_jibunAddress" class="input-tag" placeholder="지번주소" value="${memberAllData.address}" style="margin-top:1.5px;" readonly="true"/>
                    </div>
                    <div>
                        <span id="guide" style="color:#999;display:none"></span>
                    </div>
                    <div>
                        <input type="text" name="detailAddress" id="sample4_detailAddress" class="input-tag" placeholder="상세주소" value="${memberAllData.detailAddress}" style="margin-top:1.5px;" />
                    </div>
                    <div>
                        <input type="text" name="referenceItem" id="sample4_extraAddress" class="input-tag" placeholder="참고항목" value="${memberAllData.referenceItem}" style="margin-top:1.5px;" readonly="true" />
                    </div>

            </div>

            <div>
                <button type="submit"> 수정하기 </button>
                </form>
            </div>

        </div>
        <div id="right-container"></div>
    </div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#emailDomain').change(function() {
            if ($(this).val() == 'custom') {
                // '직접 입력' 선택 시, select 박스를 숨기고 input 박스를 보여줌
                $('#customEmailDomain').show();
            } else{
                $('#customEmailDomain').hide();
                $('#customEmailDomain').val(''); // input 박스의 값을 비움
    }

});
});


let updateEmailBtn = document.querySelector('#update-email-btn');
let updateEmailDiv = document.querySelector('#update-email-div');
updateEmailBtn.addEventListener('click', function(){
    updateEmailDiv.style.display = 'block';
    this.previousElementSibling.style.display = 'none';
    this.style.display = 'none';
})


// password-btn
let passwordUpdateBtn= document.querySelector('#password-update-btn');
let pwUpdateDiv = document.querySelector('#pw-update-div');
passwordUpdateBtn.addEventListener('click', function(){
    pwUpdateDiv.style.display = 'block';

    let updatePw = null;
    let inputPw = prompt("현재 비밀번호를 입력하세요");
    if( inputPw == null || inputPw == ""){ //사용자가 취소를 누르거나, 비워둔 경우
        alert("비밀번호 변경이 취소되었습니다");
        return;
    }

    fetch('http://localhost:8080/password-validation', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify({
            'id' : ${memberAllData.id},
            'password' : inputPw,
        }),
    })
    .then(response => {
        if(!response.ok){
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data =>{
        if(data == '1'){ //비밀번호가 옳은 경우. 참고로, 화살표 함수 내에서 여러 줄을 써야 할 경우, {} 안에 써야함.
            updatePw = prompt("변경하고 싶은 비밀번호를 입력하세요");
        } else{
            alert('비밀번호가 일치하지 않습니다.');
        }
    })
    .catch(error =>
        console.error('There has been a problem with your fetch operation:', error)
    );

    if(updatePw != null){
        fetch('http://localhost:8080/password-update', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify({
                'id' : ${memberAllData.id},
                'password' : updatePw,
            }),
        })
        .then(response => {
            if(!response.ok){
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data =>{
            if(data == '1'){ // 성공적으로 비밀번호를 변경한 경우
                alert('비밀번호가 성공적으로 변경되었습니다');
            } else{
                alert('비밀번호 변경에서 오류가 발생했습니다');
            }
        })
        .catch(error =>
            console.error('There has been a problem with your fetch operation:', error)
        );
    }



});


</script>

<!-- 다음 api script 태그 시작 -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="../../js/login/daumAddressApi.js"> </script>
<!--다음 api script 태그 종료 -->

</body>
</html>