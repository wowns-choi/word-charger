document.addEventListener('DOMContentLoaded', function(){
    const checkbox1 = document.getElementById('myCheckbox1');
    const checkbox2 = document.getElementById('myCheckbox2');
    const submitButton = document.querySelector('.submit-btn');

    // 버튼 초기 상태 설정
    submitButton.disabled = true;
    submitButton.style.color = "#636363";
    submitButton.style.backgroundColor = "#a8a8a8";

    // 체크박스 상태 변경 시 버튼 상태와 스타일 업데이트
    function updateButtonState() {
        const isEnabled = checkbox1.checked && checkbox2.checked;
        //둘 다 체크되었다면,
        if(isEnabled) {
            //submitButton 의 disabled(클릭할 수 없음) 이 false 로 되어라.
            submitButton.disabled = false;
            submitButton.style.backgroundColor = "#478eff";
            submitButton.style.color = "#fff";
        }else{
            submitButton.disabled = true;
            submitButton.style.backgroundColor = "#a8a8a8";
            submitButton.style.color = "#636363";
        }
    }

    // 각 체크박스에 이벤트 리스너 추가
    checkbox1.addEventListener('change', updateButtonState);
    checkbox2.addEventListener('change', updateButtonState);
});