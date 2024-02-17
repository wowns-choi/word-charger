let nameInput = document.getElementById('name-input');
nameInput.addEventListener('input', function(){

    const regExp = /^[가-힣]{2,5}$/;
    let nameInputValue = this.value;
    if(nameInputValue == ''){
    }

})


//statusExpressSpan.style.color = 'red';
//    let userPassword = this.value; //passwordInput 의 값
//    if(userPassword == ''){
//        statusExpressSpan.innerText = '';
//        return;
//    }
//
//    const lengthPattern = /^.{8,16}$/;
//    const letterPattern = /.*[A-Za-z].*/;
//    const numberPattern = /.*[0-9].*/;
//    const specialCharPattern = /.*[!@#&()–[{}\]:;',?/*~$^+=<>].*/;
//
//
//    // 길이 검사
//    if (!lengthPattern.test(userPassword)) {
//        statusExpressSpan.innerText = '비밀번호는 8자 이상 16자 이하이어야 합니다.';
//        return;
//    }