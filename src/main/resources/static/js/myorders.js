
document.addEventListener('DOMContentLoaded',function(){
    var orderSuccess=document.getElementById('orderSuccess').getAttribute('data-itemid');
    if(orderSuccess==='Success'){
        showAlert('Order is placed successfully');
    }
    // Check if the block with ID 'emptyCartMessage' exists
    var emptyCartMessage = document.getElementById('emptyCartMessage');
     
    if (emptyCartMessage) {
        // Run your JavaScript function here
        yourFunction();
    }
    });
     
    
     // Define your JavaScript function
     function yourFunction() {
         // Your function logic goes here
         var footer=document.getElementById('Cartfooter');
         footer.style.position="relative";
         footer.style.bottom="-300px";
     }


     window.onload = function() {
         // Check if the attribute to clear local storage is present
             localStorage.clear(); // Clear all local storage
             console.log("Local storage has been cleared.");
         
     };
     function showAlert(message) {
        var custom_alert = document.getElementById('custom-alert');
        var alert_message = document.getElementById('alert-message');
        alert_message.textContent = message;
        custom_alert.classList.toggle('active');
        //hide alert after 2 seconds;
        setTimeout(function () {
            custom_alert.classList.remove('active');
        }, 2000);
    }