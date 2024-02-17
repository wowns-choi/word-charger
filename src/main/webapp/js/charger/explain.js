$(document).ready(function(){
    $('#next-word').on('click', function(e){
        var vocabulary = $('[name="vocabulary"]').val();

        e.preventDefault();
        window.location.href="/next-btn-click-connectivity?vocabulary="+vocabulary;
    });
});