<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../../css/common/navbar.css">
    <title>제목</title>
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

    <style>

    </style>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
            <!--<script>
                    $(document).ready(function() {
                        $('#myForm').submit(function(e) {
                            e.preventDefault();

                            var voca = $('[name="voca"]').val();

                            $.ajax({
                                type: "POST",
                                url: "/find-voca",
                                data: { voca : voca },
                                success: function(response) {
                                    $('#modalText').text(response.voca);
                                    $('#correctAnswer').text(response.translated);
                                    $('#mymyModal').show();
                                    const modal= document.querySelector("#mymyModal")
                                    modal.style.display = "flex";
                                    modal.style.justifyContent = "center";
                                    modal.style.alignItems = "center";
                                }
                            });
                        });

                        $('.close').click(function() {
                            $('#mymyModal').hide();
                        });
                    });
                </script>
                -->



</head>
<body>
    <!-- find Voca 모달창 시작 -->
        <!--<div id="mymyModal" class="modal">
            <span class="modal-content">
                <div>
                    <span class="close">&times;</span>
                </div>
                <div>
                    <p id="modalText"></p>
                    <p id="correctAnswer"></p>
                </div>
            </span>
        </div> -->
    <!--  find Voca 모달창 종료 -->



<!--navbar 시작-->
    <nav>
        <div>
            <a href="">
                <div id="logo">
                    <div>단어</div>
                    <div>충전소</div>
                </div>
            </a>
        </div>

        <div>

            <a href="/" style="font-size: 13px;">
                ABOUT
            </a>
        </div>
        <div>
            <a href="/payment-home" style="font-size: 13px;">
                <i class="fa-solid fa-dollar-sign"></i>
            </a>
        </div>
        <div>
            <a href="/charger-home" style="font-size: 13px;">
                CHARGER
            </a>
        </div>
        <div class="board-btn-div">
            <a href="/board-home" id="board-btn" style="font-size: 13px;">
                BOARD
            </a>

        </div>
        <div>
            <a href="/faq" style="font-size: 13px;">
                FAQ
            </a>
        </div>
        <div>
<%--            <form action="/find-voca" method="post" id="myForm">--%>
        </div>
        <div>
<%--                <input type="text" name="voca" placeholder="Find Voca">--%>
        </div>
        <div>
<%--            <button type="submit">search</button>--%>
<%--            </form>--%>
        </div>
        <div>
            <a href="/login-form" id="sign-in">SIGN IN</a>
            <a href="/terms-of-use" id="sign-up">SIGN UP</a>
        </div>

    </nav>
<!--navbar 종료-->




</body>
</html>
