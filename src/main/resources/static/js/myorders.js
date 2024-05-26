
document.addEventListener('DOMContentLoaded',function(){
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