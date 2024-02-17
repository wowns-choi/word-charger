$(document).ready(function(){
    $('.answerSheetButton').on('click', function(e){
        e.preventDefault();

        var userAnswer = $(this).val();
        var vocabulary = $('[name="vocabulary"]').val();

        $.ajax({
            type: "POST",
            url: "submit-answer-sheet",
            data: {
                userAnswer: userAnswer,
                vocabulary: vocabulary
            },
            success: function(response){
                if(response.trueOrFalseBox=="correct"){
                    $('#correctAlarm').show();

                    let startWordId = response.startWordId;
                    let endWordId = response.endWordId;

                    setTimeout(function(){
                        window.location.href="/get-words-for-study?startWordId="+startWordId+"&endWordId="+endWordId;
                    },1100);
                } else if(response.trueOrFalseBox=="incorrect"){
                    window.location.href="/explanation-page?vocabulary="+vocabulary;
                }
            }
        });
    });
});