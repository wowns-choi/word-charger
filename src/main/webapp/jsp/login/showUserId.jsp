<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/login/showUserId.css">


</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->
    <div id="full-container">
        <div id="content-container">
            <div id="top-container">
                <span style="font-">${findUserId}</span>
            </div>
            <div id="bottom-container">
                <a href="/login-form"> 로그인 </a>
                <a href="/find-password"> 비밀번호 찾기 </a>
            </div>
        </div>
    </div>



</body>
</html>