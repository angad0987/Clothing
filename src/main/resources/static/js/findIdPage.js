 async function handleFormSubmission(event){
    event.preventDefault();
    var userid=document.getElementById('userid').value;
    let response= await fetch("forgotPassword/checkUser",
        {
            method:'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body:userid
        }
    );
    if(response.status ===404 ){
     //user not found
     const responseBody = await response.text();
        showAlert("There is no account with this email !") // Display the response data
    }
    if(response.status===200){
        const responseBody = await response.text();
        hideAlert();
        // alert(responseBody); // Display the response data
//user founder
          document.getElementById('account-not-found-container').remove();

      var accountFoundContainer=document.getElementById('account-found-container');
      accountFoundContainer.classList.toggle('active');

      var usernameSpan=document.getElementById('username-span');
      usernameSpan.textContent=responseBody;

      var verifyEmailInput=document.getElementById('verifyEmail');
      verifyEmailInput.value=userid;
    
    }
 }
 function showAlert(message) {
    var custom_alert = document.getElementById('custom-alert');
    var alert_message = document.getElementById('alert-message');
    alert_message.textContent = message;
    custom_alert.classList.toggle('active');
    
}
function hideAlert() {
    var custom_alert = document.getElementById('custom-alert');
    custom_alert.classList.remove('active');
}