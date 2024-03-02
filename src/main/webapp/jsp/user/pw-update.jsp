<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/user/pw-update-validation.css">
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
            <form action="/" method="post" id="pw-update-form">
                <span>새로운 비밀번호 :</span>
                <input type="password" name="password">
                <button type="submit"> 제출 </button>
            </form>
        </div>
        <div id="right-container"></div>
    </div>

<script>
    let pwUpdateForm = document.querySelector('#pw-update-form');
    pwUpdateForm.addEventListener('submit', function(e){
        e.preventDefault();
        const formData = new FormData(pwUpdateForm);

        //FormData 를 JSON 으로 변환
        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });

        fetch('/password-update', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.text())
        .then(data => {
            if(data == '1'){
                //alert창띄워서 비밀번호 변경되었다고 알려주고
                alert('비밀번호가 성공적으로 변경되었습니다');
            } else{
                //비밀번호 변경 중 오류 발생했다고 하고,
                alert('비밀번호 변경 중 오류가 발생했습니다');
            }
            //비밀번호가 변경됬든 오류나서 변경이 안됬든 홈으로 이동
            window.location.href='/';
        })
        .catch(error => {
            console.error('Error : ', error);
            window.location.href='/';
        });
    });


</script>

</body>
</html>