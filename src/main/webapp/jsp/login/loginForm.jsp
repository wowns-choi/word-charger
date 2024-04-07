<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <c:import url="/jsp/bootstrapconfig/index.jsp" />


            <html>

            <head>
                <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

                <link rel="stylesheet" href="../../css/login/loginForm.css">

                <!-- naver 로그인 관련 -->
                <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
                    charset="utf-8"></script>
                <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

                <!-- kakao 로그인 관련 -->
                <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.1/kakao.min.js"
                    integrity="sha384-kDljxUXHaJ9xAb2AzRd59KxjrFjzHa5TAoFQ6GbYTCAG0bjM55XohjjDT7tDDC01"
                    crossorigin="anonymous"></script>
                <script>
                    Kakao.init('f136f82ee91f39749dc1d23128da7bfa'); // 사용하려는 앱의 JavaScript 키 입력
                    console.log(Kakao.isInitialized());
                </script>



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
                                    <form:input id="idInput" path="id" placeholder="아이디" />
                                </div>
                                <div>
                                    <div><i class="fa-solid fa-lock"></i></div>
                                    <form:input id="passwordInput" path="password" placeholder="비밀번호" />
                                </div>
                            </div>

                            <div id="third">
                                <form:errors path="id" />
                                <form:errors path="password" />
                                ${idIncorrectMessage}
                                ${passwordIncorrectMessage}
                            </div>

                            <div id="fourth">
                                <button type="submit" class="loginButton">
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
                                <a id="kakao-login-btn" href="javascript:loginWithKakao()">
                                    <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
                                        width="222" alt="카카오 로그인 버튼" />
                                </a>
                                <p id="token-result"></p>
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
                    naver_id_login.setButton("white", 2, 40);
                    naver_id_login.setDomain("http://localhost:8080");
                    naver_id_login.setState(state);
                    naver_id_login.setPopup();
                    naver_id_login.init_naver_id_login();
                </script>

                <c:if test="${not empty goToNaverLogin}">
                    <script>
                        alert('네이버로그인으로 생성된 계정이 확인되었습니다. 네이버로 로그인 해주세요.');
                    </script>
                </c:if>
                <script src="../../js/login/loginForm.js"></script>

                <script>
                    function loginWithKakao() {
                        Kakao.Auth.authorize({
                            redirectUri: 'http://localhost:8080/get-auth-code',
                        });
                    }
                </script>


            </body>

            </html>