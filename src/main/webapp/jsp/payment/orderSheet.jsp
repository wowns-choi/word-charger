<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
      <link rel="stylesheet" href="../../css/payment/orderSheet.css">
      <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
      <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
      <script>
      document.addEventListener("DOMContentLoaded", function() {

          var IMP = window.IMP;
          IMP.init("imp46526078");

          // 폼 선택
          const form = document.getElementById('order-form');

          form.addEventListener('submit', function(event) { //submit(제출) 되면 이벤트 발생
              event.preventDefault(); // 폼의 기본 제출 동작을 방지

              // FormData 객체 생성
              const formData = new FormData(form);

              // fetch API를 사용하여 폼 데이터를 서버로 비동기적으로 전송
              fetch('/order-process', {
                  method: 'POST',
                  body: formData, // 폼 데이터. , 끝에 붙이는 거 문제 안된다고 함. 오히려 개발자들이 선호한다고 함.
              })
              .then(response => {
                  if (!response.ok) {
                      throw new Error('Network response was not ok');
                  }
                  return response.json(); // 서버로부터 반환된 JSON 응답을 파싱. 이러면 아래 data 여기에 알아서 파싱된 json 이 담김. data.json키 를 쓰면, value 를 얻을 수 있지.
              })
              .then(data => {
                  if(data.result = 'success'){
                        //사전검증 시작
                            fetch('/pre-validation?productId=${productId}&merchantUid='+ data.merchantUid)
                            .then(response => {
                                if (!response.ok) {
                                  throw new Error('Network response was not ok');
                                }
                                return response.json();
                            })
                            .then(data => {
                                //사전검증이 끝나면 할 것들 : 포트원 api 통신
                                //일단, 재고부족인 경우 처리
                                if(data.outOfStock == true){
                                    alert('재고가 부족하여 주문이 취소되었습니다.'); // alert창 띄우고,
                                    window.location.href="/payment-home"; // paymentHome.jsp 로 인도하는 컨트롤러로 get요청.
                                }
                                // 재고가 있는 경우,
                                var merchantUid = data.merchantUid;
                                var productName = data.productName;
                                var amount = data.amount;
                                var email = data.memberTotalData.email;
                                var domain = data.memberTotalData.domain;
                                var emailaddress = email + '@' + domain;
                                var userName = data.memberTotalData.userName;
                                var phoneNumStart = data.memberTotalData.phoneNumStart;
                                var phoneNumMiddle = data.memberTotalData.phoneNumMiddle;
                                var phoneNumEnd = data.memberTotalData.phoneNumEnd;
                                var phone = phoneNumStart + phoneNumMiddle + phoneNumEnd;
                                var streetAddress = data.memberTotalData.streetAddress;
                                var zipCode = data.memberTotalData.zipCode;




                                //여기서, portone 시작
                                    IMP.request_pay(

                                              ///////////////////////// 첫번째 ////////////////////////////////
                                              {
                                                pg: "html5_inicis",
                                                pay_method: "card",
                                                merchant_uid: merchantUid,
                                                name: productName,
                                                amount: amount,
                                                buyer_email: emailaddress,
                                                buyer_name: userName,
                                                buyer_tel: phone,
                                                buyer_addr: streetAddress,
                                                buyer_postcode: zipCode,
                                              },

                                              ////////////////////////두번째 //////////////////////////////////
                                              function (rsp) {
                                                    if (rsp.success) {
                                                              // axios로 HTTP 요청
                                                              axios({
                                                                url: "/payment-response", // 실제 서버의 엔드포인트 주소로 수정
                                                                method: "post",
                                                                headers: { "Content-Type": "application/json" },
                                                                data: {
                                                                  imp_uid: rsp.imp_uid,
                                                                  merchant_uid: rsp.merchant_uid
                                                                }
                                                              })

                                                              .then((response) => {
                                                                // 서버 결제 API 성공 시 로직
                                                                if(response = 'success'){ // 성공한 경우
                                                                    alert('결제가 성공하였습니다.');
                                                                    window.location.href="/payment-home";


                                                                } else {
                                                                    alert(response); // 그 외의 경우
                                                                    window.location.href="/payment-home";
                                                                }
                                                              })
                                                              .catch((error) => {
                                                                console.error(error);
                                                              });
                                                    } else {
                                                            alert('결제에 실패하였습니다. 에러 내용 : ' + rsp.error_msg);
                                                            //결제에 실패한 경우, 재고 +1 해줘야 하고, orders테이블에서 행 삭제
                                                            window.location.href="/payment-fail?merchantUid=" + merchantUid + "&impUid=" + rsp.imp_uid;
                                                    }
                                              }


                                    );

                                    //여기서, portone 끝



                            })
                            .catch(error => {
                                console.error('There has been a problem with your fetch operation:', error)
                                alert('결제 중 오류가 발생했습니다.');
                            });


                        //사전검증 끝


                  }

              })
              .catch(error => {
                  console.error('There has been a problem with your fetch operation:',
                                error);
              });

      		});

      });
      </script>


</head>
<body>
<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->
    <form method="post" id="order-form">

    <div id="full-container">
        <div id="order-sheet-container">
                <div id="left-container">


                    <div id="page-title">주문서</div>
                    <div id="buyer-info-title">구매자 정보</div>
                    <div id="buyer-info-container">
                        <div id="buyer-info-first">
                            <span>이름  </span>
                            <input type="text" name="userName" value="${memberTotalData.userName}" readonly="true">
                        </div>
                        <div id="buyer-info-second">
                            <span>이메일 </span>
                            <span>${memberTotalData.email} @ ${memberTotalData.domain}</span>
                        </div>
                        <div id="buyer-info-third">
                            <span>휴대폰 번호  </span>
                            <input type="text" name="phoneNumStart" value="${memberTotalData.phoneNumStart}" class="can-write">
                            <input type="text" name="phoneNumMiddle" value="${memberTotalData.phoneNumMiddle}" class="can-write">
                            <input type="text" name="phoneNumEnd" value="${memberTotalData.phoneNumEnd}" class="can-write">
                        </div>

                        <div id="buyer-info-fourth">
                            <input class="address-btn" type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
                        </div>

                        <div id="buyer-info-fifth">
                            <span>우편번호</span>
                            <input type="text" name="zipCode" id="sample4_postcode" class="input-tag" placeholder="우편번호" value="${memberTotalData.zipCode}" readonly="true"/>
                        </div>

                        <div id="buyer-info-sixth">
                            <span>도로명 주소</span>
                            <input type="text" name="streetAddress" id="sample4_roadAddress" class="input-tag" placeholder="도로명주소" style="margin-top:1.5px;" value="${memberTotalData.streetAddress}" readonly="true" />
                        </div>
                        <div id="buyer-info-seventh">
                            <span>지번주소</span>
                            <input type="text" name="address" id="sample4_jibunAddress" class="input-tag" placeholder="지번주소" value="${memberTotalData.address}"style="margin-top:1.5px;" readonly="true"/>
                        </div>
                            <span id="guide" style="color:#999;display:none"></span>
                        <div id="buyer-info-eightth">
                            <span>상세주소</span>
                            <input type="text" name="detailAddress" id="sample4_detailAddress" class="can-write" value="${memberTotalData.detailAddress}" placeholder="상세주소" style="margin-top:1.5px;" class="can-write" />

                        </div>
                        <div id="buyer-info-nineth">
                            <span>참고항목</span>
                            <input type="text" name="referenceItem" id="sample4_extraAddress" class="input-tag" placeholder="참고항목" value="${memberTotalData.referenceItem}"  readonly="true" />
                        </div>

                        <div id="product-info-title">상품 정보</div>
                            <div id="product-info-first">
                                <span>상품 이름 </span>
                                <input type="text" name="productName" value="${productName}" readonly="true">
                                <input type="hidden" name="productId" value="${productId}">
                            </div>
                        <div id="pay-info-title">결제 정보</div>
                        <div id="pay-info-container">
                                <span>총상품가격 : </span>
                                <input type="text" name="totalAmount" value="${amount}" readonly="true">
                        </div>

                    </div>



                </div>
                <div id="middle-container"></div>
                <div id="right-container">
                        <button type="submit" id="pay-btn">결제하기</button>
                </div>
        </div>
    </div>
기
                    </form>






    <!-- 다음 api script 태그 시작 -->
        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script src="../../js/login/daumAddressApi.js"> </script>
    <!--다음 api script 태그 종료 -->
</body>
</html>
