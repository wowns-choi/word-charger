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
            <form action="/password-validation" method="post">
                <span>현재 비밀번호 :</span>
                <input type="password" name="password">
                <button type="submit"> 제출 </button>
            </form>
            ${wrong}
        </div>
        <div id="right-container"></div>
    </div>

</body>
</html>