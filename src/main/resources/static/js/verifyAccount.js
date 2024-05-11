
document.addEventListener('DOMContentLoaded',function(){
    var invalidOtpdiv=document.getElementById('invalid-otp');
    if(invalidOtpdiv){
        setTimeout(() => {
            invalidOtpdiv.classList.add('remove');
        },3000 );
    }
});
async function verifyEmail(){
    document.getElementById('overlay').style.display='flex';
    var useremail=document.getElementById('userId').value;
    let response= await fetch('sendOTP',{
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:useremail

    })
    // if(response.status===406){
    //     document.getElementById('overlay').style.display='none';
    //     var userid=document.getElementById('userId').value;
    //     var sidebarHeading=document.getElementById('sidebar-heading')
    //     sidebarHeading.innerHTML='User is already registered with this '+userid+' account'
    //     var sidebar=document.getElementById('sidebar');
    //     sidebar.classList.toggle('active');
    //     return;
    // }
    if(response.status===200){
        // If response is successful, parse the JSON response body
        const responseBody = await response.text();
        alert(responseBody); // Display the response data
        document.getElementById('overlay').style.display='none';
        var verifyContainer=document.querySelector('.verify-container');
        var verifyParentContainer=document.querySelector('.verifyParentContainer');
       var verifyButton= document.getElementById('verifyButton');
       verifyContainer.removeChild(verifyButton);

       //ADDING OTP AND RESEND OTP BUTTON
       var otpInput=document.createElement('input');
       otpInput.setAttribute('type','number');
       otpInput.setAttribute('id','otp');
       otpInput.setAttribute('class','form-control');
       otpInput.setAttribute('placeholder','Enter OTP');
       otpInput.setAttribute('maxlength','6');
       otpInput.setAttribute('name','otp');
       otpInput.style.width='6rem';
       otpInput.style.borderRadius='10px'
       otpInput.style.paddingTop='3px';
       otpInput.style.paddingLeft='11px';
       otpInput.style.fontSize='13px';

       var resendButton = document.createElement('button');
            resendButton.textContent = 'Resend OTP';
            resendButton.classList.add('btn');
            resendButton.classList.add('btn-success');
            resendButton.id = 'resendButton';
            resendButton.style.width='6rem';
            resendButton.style.padding='15px 15px';
            resendButton.style.marginTop='0px';
            resendButton.style.marginLeft='0px';

            resendButton.addEventListener('click',function(){
               verifyEmail();
            });

            var submitButton=document.createElement('button');
            submitButton.textContent='Submit';
            submitButton.type='submit';
            submitButton.classList.add('btn');
            submitButton.classList.add('btn-success');
            submitButton.id='submitButton';
            submitButton.style.width='6rem';
            submitButton.style.padding='15px 15px';
            submitButton.style.marginTop='0px';

            // resendButton.style.backgroundColor='red';
            verifyContainer.appendChild(otpInput);
            verifyContainer.appendChild(resendButton);
            verifyParentContainer.appendChild(verifyContainer);
            verifyParentContainer.appendChild(submitButton);

    }
    else{
        const responseBody = await response.text();
        alert(responseBody); // Display the response data
      document.getElementById('overlay').style.display='none';
    }
}