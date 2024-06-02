 // Get user information JSON string from the model
 document.addEventListener('DOMContentLoaded', function(){
    console.log('Background');
   var carasoulBackgroundDiv=document.getElementById('carousel-background');
   var baseUrl=document.getElementById('baseUrl').getAttribute('data-itemid');
    if(carasoulBackgroundDiv){
     
        console.log(baseUrl);
        carasoulBackgroundDiv.style.backgroundImage= 'url('+baseUrl+'/images/newBack.jpg)';
    }

    var username=document.getElementById('username').getAttribute('data-itemid');

console.log(username);
    // Store user information in local storage
    localStorage.setItem('user', username);
   
 }
 );
 
 

