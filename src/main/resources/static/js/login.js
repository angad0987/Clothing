 document.addEventListener("DOMContentLoaded",function(){
    
    var error=new URLSearchParams(window.location.search).has('error');
    console.log('error is '+error);

    if(error){
        var errorMessage=document.querySelector('.error-message');
        if(errorMessage!==null){
            setTimeout(function(){
             errorMessage.remove();
            },2000);
        }
    }
    else{
        console.log('Erros is null '+error);
    }




 });
 document.addEventListener('DOMContentLoaded', function() {
    var inputField = document.getElementById('login');

    inputField.addEventListener('focus', function() {
        inputField.classList.add('active');
    });

    inputField.addEventListener('blur', function() {
            inputField.classList.remove('active');
    });

    var inputField1 = document.getElementById('password');

    inputField1.addEventListener('focus', function() {
        inputField1.classList.add('active');
    });

    inputField1.addEventListener('blur', function() {
    
            inputField1.classList.remove('active');
        
    });
});
