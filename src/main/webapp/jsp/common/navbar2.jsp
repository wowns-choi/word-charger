<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../../css/common/navbar.css">
    <title>제목</title>
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
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
                            $('#correctAnswer').text(response.correct);
                            $('#mymyModal').show();
                            console.log("hello");
                        }
                    });
                });

                $('.close').click(function() {
                    $('#mymyModal').hide();
                });
            });
        </script>
</head>
<body>
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
            <button style="font-size: 13px;">
                ABOUT
            </button>
        </div>
        <div>
            <button style="font-size: 13px;">
                <i class="fa-solid fa-dollar-sign"></i>
            </button>
        </div>
        <div>
            <button style="font-size: 13px;">
                CHARGER
            </button>
        </div>
        <div class="board-btn-div">
            <button id="board-btn" style="font-size: 13px;">
                BOARD
            </button>
            <div class="board-btn-dropdown">
                <div class="board-btn-dropdown-child">
                    <a href=""> 게시판</a>
                    <a href=""> 내가 작성한 글</a>
                </div>
            </div>
        </div>
        <div>
            <button style="font-size: 13px;">
                FAQ
            </button>
        </div>
        <div></div>
        <div>
            <form action="">
                <input type="" placeholder="Find Voca">
        </div>
        <div>
            <button type="submit">search</button>
            </form>
        </div>
        <div>
            <a href="" id="sign-in">SIGN IN</a>
            <a href="" id="sign-up">SIGN UP</a>
        </div>

    </nav>
<!--navbar 종료-->

</body>
</html>
