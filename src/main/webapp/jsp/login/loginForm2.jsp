<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>


<html>
<head>
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="../../css/login/loginForm.css">

    <!-- naver 로그인 관련 -->
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>


</head>
<body>

    <div id="full-container">
        <div></div>
        <div>
            <div id="left-container"></div>
            <div id="mid-container">
                <div id="zero">
                </div>

                <div id="first">
                    <form:form modelAttribute="loginDTO" method="post" class="formForm">
                </div>

                <div id="second">
                    <div>
                        <div><i class="fa-regular fa-user"></i></div>
                        <form:input id="idInput" path="id"   placeholder="아이디"/>
                    </div>
                    <div>
                        <div><i class="fa-solid fa-lock"></i></div>
                        <form:input id="passwordInput" path="password" placeholder="비밀번호"/>
                    </div>
                </div>

                <div id="third">
                    ${idIncorrectMessage}
                    ${passwordIncorrectMessage}
                </div>

                <div id="fourth">
                    <button type="submit"  class="loginButton">
                        로그인
                    </button>
                    </form:form>
                </div>

                <div id="fifth">
                        <a href="/find-user-id">아이디 찾기</a> &nbsp &nbsp
                        <a href="/find-password">비밀번호 찾기</a> &nbsp &nbsp
                        <a href="">회원가입</a> &nbsp &nbsp
                </div>
                <div id="sixth">

                    <div id="naver_id_login">
                        <!-- 네이버 로그인 버튼 노출 영역 -->
                        <div id="naver_id_login"></div>
                    </div>

                </div>
            </div>
            <div id="right-container"></div>
        </div>
        <div></div>
    </div>



    <!-- //네이버 로그인 버튼 노출 영역 -->
        <script type="text/javascript">
            var naver_id_login = new naver_id_login("akLY3ixqz148IBJVFcFD", "http://localhost:8080/naver-callback");
            var state = naver_id_login.getUniqState();
            naver_id_login.setButton("white", 2,40);
            naver_id_login.setDomain("http://localhost:8080");
            naver_id_login.setState(state);
            naver_id_login.setPopup();
            naver_id_login.init_naver_id_login();
        </script>

    <c:if test="${not empty alreadyMember}">
        <script>
            alert('${alreadyMember}');
        </script>
    </c:if>



</body>
</html>