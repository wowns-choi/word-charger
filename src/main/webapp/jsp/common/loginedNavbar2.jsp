<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../../css/common/navbar.css">
    <title>제목</title>
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

    <style>

    </style>
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



</head>
<body>
    <!-- find Voca 모달창 시작 -->
        <div id="mymyModal" class="modal">
            <span class="modal-content">
                <div>
                    <span class="close">&times;</span>
                </div>
                <div>
                    <p id="modalText"></p>
                    <p id="correctAnswer"></p>
                </div>
            </span>
        </div>
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
        </div>
        <div>
        </div>
        <div>
        </div>
        <div>
            <div id="welcome">
                <a href="/#" id="dropdown-menu-link">
                    ${loginedMember.userName}                님 어서오세요!
                </a>
                <div id="update-user-info-and-logout-dropdown">
                    <div id="update-user-info-and-logout-dropdown-child">
                        <a href="/update-user-info?id=${loginedMember.id}">회원 정보 수정하기</a>
                        <a href="/password-validation">비밀번호 변경</a>
                        <a href="/logout" id="logout">주문내역 확인하기</a>
                        <a href="/withdrawal" id="withdrawal">회원 탈퇴하기</a>
                        <a href="/logout" id="logout">로그아웃</a>
                    </div>
                </div>
            </div>

        </div>

    </nav>
<!--navbar 종료-->

<script>
    let withdrawal = document.querySelector('#withdrawal');
    withdrawal.addEventListener('click', function(e){
        e.preventDefault();
        if(confirm('정말 삭제하시겠습니까?')){
            window.location.href="/withdrawal";
        }
    });


</script>




</body>
</html>
