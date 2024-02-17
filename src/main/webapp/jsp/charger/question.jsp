<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- 필요한 경우에 추가 -->

<html>
<head>
    <link rel="stylesheet" href="../../css/charger/question.css">
</head>
<body >

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="blue-container">
        <div id="inner-container">
                <div style="font-size: 90px; "> ${voca} </div>
                <div style="height:3vh;"></div>
                <div style="width: 60%; height: 1px;box-shadow: 0px 1px 10px rgba(224, 101, 0, 1); background-color:#e06500;"></div>
                <div style="height: 10vh; "></div>
                <!-- 정답입니다 -->
                    <div id="correctAlarm" style="font-size:20px; display: none; font-family: 'Gugi', sans-serif; color:#5496ff;">
                        정답입니다
                    </div>
                <!-- 정답입니다 -->
                <div id="answer-container">
                    <%
                       List<String> shuffledAnswers = (List<String>) request.getAttribute("answer");
                        for(String answer : shuffledAnswers) {
                    %>
                        <div class="card"id="one">
                            <button type="submit" class="answerSheetButton" name="userAnswer" value="<%=answer%>"> <%=answer%> </button>
                            <input type="hidden" name="vocabulary" value="${voca}">
                        </div>

                    <%
                    }
                    %>
                </div>
        </div>
    </div>




    <!-- question.js -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../../js/charger/question.js"></script>





</body>
</html>