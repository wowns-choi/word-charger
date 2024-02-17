let passwordInput = document.getElementById('user-password');
let statusExpressSpan = document.getElementById('user-password-status');

passwordInput.addEventListener('input', function(){
    statusExpressSpan.style.color = 'red';
    let userPassword = this.value; //passwordInput 의 값
    if(userPassword == ''){
        statusExpressSpan.innerText = '';
        return;
    }

    const lengthPattern = /^.{8,16}$/;
    const letterPattern = /.*[A-Za-z].*/;
    const numberPattern = /.*[0-9].*/;
    const specialCharPattern = /.*[!@#&()–[{}\]:;',?/*~$^+=<>].*/;


    // 길이 검사
    if (!lengthPattern.test(userPassword)) {
        statusExpressSpan.innerText = '비밀번호는 8자 이상 16자 이하이어야 합니다.';
        return;
    }

    // 알파벳 문자 검사
    if (!letterPattern.test(userPassword)) {
        statusExpressSpan.innerText = '비밀번호에는 최소 한 개의 알파벳 문자가 포함되어야 합니다.';
        return;
    }

    // 숫자 검사
    if (!numberPattern.test(userPassword)) {
        statusExpressSpan.innerText = '비밀번호에는 최소 한 개의 숫자가 포함되어야 합니다.';
        return;
    }

    // 특수 문자 검사
    if (!specialCharPattern.test(userPassword)) {
        statusExpressSpan.innerText = '비밀번호에는 최소 한 개의 특수 문자가 포함되어야 합니다.';
        return;
    }

    statusExpressSpan.style.color = 'blue';
    // 모든 조건을 통과한 경우
    statusExpressSpan.innerText = '적합한 비밀번호 입니다!'; // 아무 메시지도 표시하지 않음



});