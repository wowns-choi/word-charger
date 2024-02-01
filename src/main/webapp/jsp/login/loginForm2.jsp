<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">


</head>
<body>



    <div class="exclude">


        <div class="charge-your-brain" >
         [ Login ]
        </div>

        <div class="whitecontainer">
            <div class="left-WC">
            <form:form modelAttribute="loginDTO" method="post" class="formForm">
                <form:input class="idInput" path="id"  placeholder="ID"/>
                ${idIncorrectMessage}
                <form:input class="passwordInput" path="password" placeholder="Password"/>
                ${passwordIncorrectMessage}
                <button type="submit"  class="loginButton">
                    Login
                </button>
            </form:form>


            </div>
            <div class="right-WC">

                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">아이디 찾기</span></a></div>
                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">비밀번호 찾기</span></a></div>
                <div class="text-divtag"><a href="#" style="text-decoration: none;"><span class="text-decoration">회원가입 하기</span></a></div>


            </div>
        </div>
    </div>



</body>
</html>