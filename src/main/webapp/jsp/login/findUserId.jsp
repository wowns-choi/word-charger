<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/login/findUserId.css">
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
                    fetch('/email-validation', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json', // 서버에 JSON 형식임을 알림
                        },
                        body: JSON.stringify(data), // 직렬화된 JSON 문자열
                    })
                    .then(response => response.text())
                    .then(data => {
                        if(data == 'success'){
                            // 성공한 경우,
                            alert('인증 성공');
                            let userName = document.querySelector('#userName-input').value;
                            let email = document.querySelector('#email-input').value;
                            window.location.href="/show-user-id?userName="+userName+"&email="+email;
                        } else{
                            // 실패한 경우,
                            alert('이름 또는 이메일이 일치하지 않습니다.');
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
    <form action="/password-validation" method="post" id="find-userId-form">
        <div id="update-container">
                <div id="first-container">
                    <div></div>
                    <input type="text" name="userName" id="userName-input" placeholder="이름">
                    <div></div>
                </div>
                <div id="second-container">
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