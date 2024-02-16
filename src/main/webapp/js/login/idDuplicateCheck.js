document.getElementById('user-id').addEventListener('input', function(){
    var userId = this.value;
    if(userId==''){
        document.getElementById('user-id-status').innerText = '';
        return; // 만약, 아무것도 입력하지 않은 상태라면, 이 함수를 빠져나가서 아무 문구도 안나오게 함.
    }
    //Fetch API를 사용하여 서버에 비동기 요청을 보냄
    fetch('check-user-id', {
        method:'POST',
        body: JSON.stringify({userId: userId}),
        headers: {
            'Content-Type' : 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if(data.isAvailable){
            document.getElementById('user-id-status').innerText='사용 가능한 아이디입니다';
            document.getElementById('user-id-status').style.color='#0066ff';
        }else{
            document.getElementById('user-id-status').innerText='이미 사용 중인 아이디입니다.';
            document.getElementById('user-id-status').style.color='red';
        }
    });
});