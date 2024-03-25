<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../../css/login/joinForm.css">

</head>
<body>
    <div class="full-frame">
        <div class="content-container">
            <form:form modelAttribute="memberJoinDTO" method="post">

                <div class="id-password-container">
                    <form:input type="text" path="userId" id="user-id" class="input-tag" placeholder="아이디" style="display: inline-block;"/>
                    <span id="user-id-status" style="font-size:11px; display:inline-block;">
                    </span>
                    <div class="tag-for-verification-message" >
                        <form:errors path="userId" class="error-message"/>
                    </div>


                    <form:input type="password" path="password" id="user-password" class="input-tag" placeholder="비밀번호" style=" display:inline-block;"/>
                    <span id="user-password-status" style="font-size:11px; display:inline-block;">
                    </span>
                    <div style="font-size:14px;">
                        비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.
                    </div>
                    <div class="tag-for-verification-message">
                    <form:errors path="password" class="error-message"/>
                    </div>
                </div>

                <div class="name-phone-container">
                    <form:input type="text" path="userName" id="name-input" class="input-tag" placeholder="이름" />
                    <div class="tag-for-verification-message" >
                        <form:errors path="userName"  class="error-message"/>
                    </div>

                    <div>
                        <form:input path="phoneNumberStart" type="text" style="width:25%;" placeholder="000" class="input-tag"/>
                        <form:input path="phoneNumberMiddle" type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                        <form:input path="phoneNumberEnd" type="text" style="width:25%;" placeholder="0000" class="input-tag"/>
                        <br/>
                        <div class="tag-for-verification-message" >
                            <form:errors path="phoneNumberStart" style="width:25%;"  class="error-message"/>
                        </div>
                    </div>
                </div>

                <div class="email-div" style="font-size: 12px; ">
                        <form:input path="email" style="width: 10vw; height: 60%;" /> &nbsp &nbsp @ &nbsp &nbsp
                        <form:input type="text" id="customEmailDomain" path="customEmailDomain" style="width: 7vw; display: none;" placeholder="도메인 직접 입력" />

                        <form:select path="emailDomain" style="width: 7vw; height: 80%;">
                            <form:option value="naver.com" label="naver.com" />
                            <form:option value="daum.net" label="daum.net" />
                            <form:option value="gmail.com" label="gmail.com" />
                            <form:option value="custom" label="직접 입력" />
                        </form:select>
                </div>

                <!--다음 주소 api 사용 시작-->
                <div class="address-container">
                    <form:input type="text" path="zipCode" id="sample4_postcode" class="input-tag" placeholder="우편번호" readonly="true"/>
                    <form:errors path="zipCode"  class="error-message"/>
                    <input class="address-btn" type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>

                    <div>
                        <form:input type="text" path="streetAddress" id="sample4_roadAddress" class="input-tag" placeholder="도로명주소" style="margin-top:1.5px;" readonly="true" />
                    </div>
                    <div>
                        <form:input type="text" path="address" id="sample4_jibunAddress" class="input-tag" placeholder="지번주소" style="margin-top:1.5px;" readonly="true"/>
                    </div>
                    <div>
                        <span id="guide" style="color:#999;display:none"></span>
                    </div>
                    <div>
                        <form:input type="text" path="detailAddress" id="sample4_detailAddress" class="input-tag" placeholder="상세주소" style="margin-top:1.5px;" />
                    </div>
                    <div>
                        <form:input type="text" path="referenceItem" id="sample4_extraAddress" class="input-tag" placeholder="참고항목" style="margin-top:1.5px;" readonly="true" />
                    </div>
                    <!--다음 주소 api 사용 종료-->

                    <!-- 이걸 왜 안써줬냐? 이걸 써줬더니 오히려 중복으로 on 이 아니라, on,on 이 나왔음.
                    <input type="hidden" name="myCheckbox1" value="${memberDTO["myCheckbox1"]}">
                    <input type="hidden" name="myCheckbox2" value="${memberDTO["myCheckbox2"]}">
                    <input type="hidden" name="myCheckbox3" value="${memberDTO["myCheckbox3"]}">
                     -->
            </div>

                <div class="join-btn-div">
                <input class="join-btn" type="submit" value="회원가입"/>
                </div>
            </form:form>
        </div>
    </div>

    <!-- idDuplicateCheck -->
        <script src="../../js/login/idDuplicateCheck.js"></script>

    <!-- passwordValidation -->
        <script src="../../js/login/passwordValidation.js"></script>

    <!-- 다음 api script 태그 시작 -->
        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script src="../../js/login/daumAddressApi.js"> </script>
    <!--다음 api script 태그 종료 -->


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
    </script>


</body>
</html>