 // Get user information JSON string from the model
 document.addEventListener('DOMContentLoaded', function(){
   var carasoulBackgroundDiv=document.getElementById('carasoul-background');
   var baseUrl=document.getElementById('baseUrl').getAttribute('data-itemid');
    if(carasoulBackgroundDiv){
        carasoulBackgroundDiv.style.backgroundImage= 'url('+baseUrl+'/images/newBack.jpg)';
    }

    var username=document.getElementById('username').getAttribute('data-itemid');

console.log(username);
    // Store user information in local storage
    localStorage.setItem('user', username);
   
 }
 );
 
 


            // Set JavaScript variables based on server-side logic
            // var successMessage = "${success}";
            // var success1Message = "${success1}";
            // var loginmessage = "${loginMessage}";
            // console.log(successMessage);
            // console.log(success1Message);
            // // Display alerts if the variables are not empty
            // if (successMessage !== "") {
            //     alert(successMessage);
            // }
            // if (loginmessage !== "") {
            //     alert(loginmessage);
            // }

            // if (success1Message !== "") {
            //     alert(success1Message);
            // }
            // Initialization for ES Users
import { Input, initMDB } from "mdb-ui-kit";

initMDB({ Input });
        