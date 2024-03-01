//[comment] update 버튼 누르면, textarea 로 바뀌는 거
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.update-comment').forEach(function(element) {
        element.addEventListener('click', function() {
            let textarea = this.previousElementSibling;
            let firstChild = this.previousElementSibling.previousElementSibling;

            let flag = textarea.style.display === 'block' ? true : false; // 토글 효과 적용

            if(flag == false){
                textarea.style.display = 'block';
                firstChild.style.display = 'none';
            }else{
                textarea.style.display = 'none';
                firstChild.style.display = 'block';
            }
        });
    });
});

//[reply] update 버튼 누르면, textarea 로 바뀌는 거
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.update-reply').forEach(function(element) {
        element.addEventListener('click', function() {
            let textarea = this.previousElementSibling;
            let firstChild = this.previousElementSibling.previousElementSibling;

            let flag = textarea.style.display === 'block' ? true : false; // 토글 효과 적용

            if(flag == false){
                textarea.style.display = 'block';
                firstChild.style.display = 'none';
            }else{
                textarea.style.display = 'none';
                firstChild.style.display = 'block';
            }
        });
    });
});


