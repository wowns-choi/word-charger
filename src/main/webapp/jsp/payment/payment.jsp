<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
      <link rel="stylesheet" href="../../css/payment/payment.css">
      <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
      <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
      <script>







        var IMP = window.IMP;
        IMP.init("imp46526078");

function requestPay() {
    IMP.request_pay(

          ///////////////////////// 첫번째 ////////////////////////////////
          {
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "57008833-33011",
            name: "당근 10kg",
            amount: 1,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "포트원 기술지원팀",
            buyer_tel: "010-1234-5678",
            buyer_addr: "서울특별시 강남구 삼성동",
            buyer_postcode: "123-456",
          },

          ////////////////////////두번째 //////////////////////////////////
          function (rsp) {
                if (rsp.success) {
                          // axios로 HTTP 요청
                          axios({
                            url: "http://localhost:8080/payment-response", // 실제 서버의 엔드포인트 주소로 수정
                            method: "post",
                            headers: { "Content-Type": "application/json" },
                            data: {
                              imp_uid: rsp.imp_uid,
                              merchant_uid: rsp.merchant_uid
                            }
                          })

                          .then((response) => {
                            // 서버 결제 API 성공 시 로직
                          })

                          .catch((error) => {
                            // HTTP 요청 실패 시 로직
                            console.error(error);
                          });
                } else {
                          alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
                }
          }


    );



}

        </script>


</head>
<body>
<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="heigth-8vh-from-top"></div>

    <div id="big-container">
        <div>
            <div></div>
            <div>

            </div>
        </div>
        <div id="card-container">
            <div></div>
            <div>
                <div id="first-card">
                    <div id="picture-part-1"></div>
                    <div class="first-text">처음 3개월 무료</div>
                    <div class="second-text">간편하게 로그인하고 이용해보세요</div>
                    <a href="/board-home" class="Learn-more">구독하기</a>
                </div>
                <div id="second-card">
                    <div id="picture-part-2"></div>
                    <div class="first-text">월 3천원 구독</div>
                    <div class="second-text">1년동안 구독하시면 평생이 무료!</div>
                    <a href="/board-home" class="Learn-more">구독하기</a>
                </div>
                <div id="third-card">
                    <div id="picture-part-3"></div>
                    <div class="first-text">평생 무료 결제</div>
                    <div class="second-text">3만원 결제시 평생 무료!</div>
                    <a href="/board-home" class="Learn-more">구독하기</a>
                </div>
            </div>
            <div></div>
        </div>
    </div>






<button onclick="requestPay()">결제하기</button>

</body>
</html>
