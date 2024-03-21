<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/login/withdrawal.css">

</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <form action="/withdrawalssss" method="post" id="withdrawal-form">
        <div id="update-container">
                <div id="first-container">
                    <div></div>
                    <input type="password" name="password" id="password-input" placeholder="비밀번호를 입력해주세요.">
                    <div></div>
                </div>

                <button type="submit" id="find-btn">
                    변경하기
                </button>
        </div>
    </form>

    <script>
            document.addEventListener('DOMContentLoaded', function() {
                    document.querySelector('#withdrawal-form').addEventListener('submit', function(e) {
                    e.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

                    // 폼 제출 이벤트가 발생했을 때 입력값을 동적으로 가져옵니다.
                    let inputPassword = document.querySelector('#password-input').value;

                    fetch('/withdrawal', {
                        method: 'POST',
                        headers: {
                        'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                        password: inputPassword,
                        }),
                        })
                    .then(response => {
                        if (!response.ok) {
                        throw new Error('Network response was not ok');
                        }
                        return response.text();
                    })
                    .then(data => {
                        if(data == 'success'){
                            alert('계정이 삭제되었습니다.');
                            window.location.href="/";
                        } else{
                            alert('비밀번호가 일치하지 않습니다');
                        }
                    })
                    .catch(error => console.error('There has been a problem with your fetch operation:', error));
                    });
            });
    </script>



</body>
</html>