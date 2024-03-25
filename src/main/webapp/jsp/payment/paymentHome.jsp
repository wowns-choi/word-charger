<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
      <link rel="stylesheet" href="../../css/payment/paymentHome.css">
      <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
      <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
</head>
<body>
<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <!-- 재고가 없을 경우에만 띄워질 alert 창 -->
        <% if (request.getAttribute("outOfStockMessage") != null) { %>
        <script>
            // alert 창으로 메시지 띄우기
            alert("<%= request.getAttribute("outOfStockMessage") %>");
        </script>
        <% } %>
    <!-- ---------------------------- -->

     <div id="big-container">
        <div id="card-container">
            <div></div>
            <div>
                <div id="first-card">
                    <div id="picture-part-1"></div>
                    <div class="first-text">처음 3개월 무료</div>
                    <div class="second-text">간편하게 로그인하고 이용해보세요</div>
                    <a href="/show-order-sheet?productId=21" class="Learn-more" id="one-btn">결제하기</a>
                </div>
                <div id="second-card">
                    <div id="picture-part-2"></div>
                    <div class="first-text">월 3천원 구독</div>
                    <div class="second-text">1년동안 구독하시면 평생이 무료!</div>
                    <a href="/show-order-sheet?productId=2" class="Learn-more">결제하기</a>
                </div>
                <div id="third-card">
                    <div id="picture-part-3"></div>
                    <div class="first-text">평생 무료 결제</div>
                    <div class="second-text">3만원 결제시 평생 무료!</div>
                    <a href="/show-order-sheet?productId=3" class="Learn-more">결제하기</a>
                </div>
            </div>
            <div></div>
        </div>
     </div>
</body>
</html>
