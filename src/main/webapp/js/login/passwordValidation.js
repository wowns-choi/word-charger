
document.getElementById('user-password').addEventListener('input', function(){
        var userPassword = this.value;

        if(userPassword==''){
            document.getElementById('user-password-status').innerText = '';
            return; // 만약, 아무것도 입력하지 않은 상태라면, 이 함수를 빠져나가서 아무 문구도 안나오게 함.
        }

        fetch('check-user-password', {
            method: 'POST',
            body: JSON.stringify({userPassword: userPassword}),
            headers: {
                'Content-Type' : 'application/json'
            }
        })

        .then(response => response.json())

        .then(data => {
            if(data.insufficientLength){
                document.getElementById('user-password-status').innerText='8~16글자 사이어야 합니다.';
                document.getElementById('user-password-status').style.color='red';
            }else if(data.englishNotIncluded){
                document.getElementById('user-password-status').innerText='영문이 포함되지 않았습니다';
                document.getElementById('user-password-status').style.color='red';
            }else if(data.specialCharactersNotIncluded){
                document.getElementById('user-password-status').innerText='특수문자가 포함되지 않았습니다.';
                document.getElementById('user-password-status').style.color='red';
            }else if(data.numberNotIncluded){
                document.getElementById('user-password-status').innerText='숫자가 포함되지 않았습니다.';
                document.getElementById('user-password-status').style.color='red';
            }else{
                document.getElementById('user-password-status').innerText='사용가능한 비밀번호입니다.';
                document.getElementById('user-password-status').style.color='#0066ff';
            }

        });



});