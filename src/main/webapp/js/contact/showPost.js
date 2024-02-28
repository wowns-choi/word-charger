document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.update-String').forEach(function(element) {
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


