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
    회원 정보 수정
</div>

    <div id="update-container">
        <div id="left-container">
        </div>
        <div id="mid-container">
            <div id="one">
                <form method="post">
                아이디 : ${memberAllData.userId}
            </div>

            <div id="two">
                이름 : ${memberAllData.userName}
            </div>

            <div id="three">
                핸드폰 번호 :
            </div>
            <div id="four">
                <input type="text" name="phoneNumStart"  value="${memberAllData.phoneNumStart}" class="input-tag">
                <input type="text" name="phoneNumMiddle"  value="${memberAllData.phoneNumMiddle}" class="input-tag">
                <input type="text" name="phoneNumEnd"  value="${memberAllData.phoneNumEnd}" class="input-tag">
            </div>

            <div id="five">
                이메일 :
            </div>
            <div id="six">
                <span id="email-address">
                ${memberAllData.email} ${memberAllData.domain}
                </span>
                <button id="update-email-btn" type="button" style="border-radius: 4px;">수정하기</button>
            </div>

            <div id="update-email-div" style="display: none;">
                <input type="text" name="email" placeholder="이메일을 입력해주세요">
                <input type="text" name="customEmailDomain" id="customEmailDomain" placeholder="도메인 직접 입력">

                <select id="emailDomain" name="domain" style="width: 7vw; height: 80%;">
                    <option value="@daum.net">@daum.net</option>
                    <option value="@gamil.com">@gmail.com</option>
                    <option value="@naver.com">@naver.com</option>
                    <option value="custom">직접입력</option>
                </select>
            </div>


            <div class="address-container" id="eight">
                    <input class="address-btn" type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
                    <input type="text" name="zipCode" id="sample4_postcode"  placeholder="우편번호" readonly="true" value="${memberAllData.zipCode}"/>
                    <div>
                        <input type="text" name="streetAddress" id="sample4_roadAddress"  placeholder="도로명주소" value="${memberAllData.streetAddress}" style="margin-top:1.5px;" readonly="true" />
                    </div>
                    <div>
                        <input type="text" name="address" id="sample4_jibunAddress"  placeholder="지번주소" value="${memberAllData.address}" style="margin-top:1.5px;" readonly="true"/>
                    </div>
                    <div>
                        <span id="guide" style="color:#999;display:none"></span>
                    </div>
                    <div>
                        <input type="text" name="detailAddress" id="sample4_detailAddress" class="detail-address" placeholder="상세주소" value="${memberAllData.detailAddress}" style="margin-top:1.5px;" />
                    </div>
                    <div>
                        <input type="text" name="referenceItem" id="sample4_extraAddress"  placeholder="참고항목" value="${memberAllData.referenceItem}" style="margin-top:1.5px;" readonly="true" />
                    </div>
            </div>

            <div id="nine">
                <button type="submit" id="update-btn"> 수정하기 </button>
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
    this.parentElement.style.display = 'none';

})




</script>

<!-- 다음 api script 태그 시작 -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="../../js/login/daumAddressApi.js"> </script>
<!--다음 api script 태그 종료 -->

</body>
</html>