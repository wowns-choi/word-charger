<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->
<%@ page import="firstportfolio.wordcharger.DTO.WritingDTOSelectVersion" %>

<html>
<head>
    <link rel="stylesheet" href="../../css/login/pwUpdateForm.css">
</head>
<body>

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <form action="/update-pw" method="post" >
        <div id="update-container">
                <div id="first-container">
                    <div></div>
                    <input type="text" name="password" id="userName-input" placeholder="새 비밀번호를 입력해주세요">
                    <div></div>
                </div>
                <input type="hidden" name="id" value="${id}">

                <button type="submit" id="find-btn">
                변경하기
                </button>
        </div>
    </form>

</body>
</html>