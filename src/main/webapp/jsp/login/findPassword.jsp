<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/login/findPassword.css">
    <script>
        document.addEventListener("DOMContentLoaded", function(){
                let element = document.querySelector("#find-userId-form");

                element.addEventListener('submit', function(e){
                    e.preventDefault();
                    const formData = new FormData(element);

                    // FormData를 JSON으로 변환
                    const data = {};
                    formData.forEach((value, key) => { //(key, value) 가 아님을 주의
                        data[key] = value;
                    });
                    fetch('/user-info-validation', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json', // 서버에 JSON 형식임을 알림
                        },
                        body: JSON.stringify(data), // 직렬화된 JSON 문자열
                    })
                    .then(response => response.text())
                    .then(data => {
                        if(data == 'not_found'){
                            alert('아이디가 일치하지 않습니다.');
                        } else if(data == 'not_name'){
                            alert('이름이 일치하지 않습니다.');
                        } else if(data == 'not_email'){
                            alert('이메일이 일치하지 않습니다.');
                        } else if(data == 'not_domain'){
                            alert('도메인을 잘못 입력하셨습니다.');
                        } else if(data == 'success'){
                            alert('비밀번호를 변경할 수 있는 링크가 포함된 메일이 전송되었습니다');
                            window.location.href="/";
                        }
                    })
                    .catch(error => console.error('Error:', error));
                });

        });
    </script>

</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->
    <form  method="post" id="find-userId-form">
        <div id="update-container">
                <div id="first-container">
                    <div></div>
                    <input type="text" name="userId" id="userId-input" placeholder="아이디">
                    <div></div>
                </div>
                <div id="second-container">
                    <div></div>
                    <input type="text" name="userName" id="userName-input" placeholder="이름">
                    <div></div>
                </div>
                <div id="third-container">
                    <div></div>
                    <input type="text" name="email" id="email-input" placeholder="회원가입시 입력한 이메일 주소">
                    <div></div>
                </div>
                <button type="submit" id="find-btn">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
        </div>
    </form>

</body>
</html>