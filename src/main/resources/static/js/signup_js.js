function limitMobileNumber(input, maxlength) {
    if (input.value.length > maxlength) {
        input.value = input.value.slice(0, maxlength);
    }
}
function ValidateForm() {

    //client side validation
    var pass1 = document.getElementById('pass1').value;
    var pass2 = document.getElementById('pass2').value;
    if (pass1 !== pass2) {
        alert('password doesnot match');
        return false;
    }
    submitSignUpForm();
    return false;
    
}
 async function submitSignUpForm() {

    console.log('I am in submit ginup mehtod');
    document.getElementById('overlay').style.display='flex';



    let formData=new FormData(document.getElementById('signupForm'));


//     let jsonData = {};: This line initializes an empty JavaScript object called jsonData. This object will be used to store key-value pairs extracted from the FormData object.

// for (const [key, value] of formData.entries()) { ... }: This is a for...of loop iterating over the entries of the FormData object (formData). The entries() method returns an iterator object that yields key-value pairs for each entry in the FormData object.

// const [key, value]: This syntax is called destructuring assignment. It allows you to extract values from arrays or iterable objects and assign them to variables. In this case, it extracts the key and value of each entry in the FormData object.

// jsonData[key] = value;: Inside the loop, this line assigns the value (value) to the property of the jsonData object with the key (key). This effectively populates the jsonData object with key-value pairs extracted from the FormData object.
    let jsonData={};
    for(const [key,value] of formData.entries()){
        if(key !== 'password1'){
            jsonData[key]=value;
        }

    }

    console.log(JSON.stringify(jsonData));
   setTimeout( async function(){
    let response=await fetch('handleSignup',{
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(jsonData)
    });

    
     
    if(response.status===400){
        document.getElementById('overlay').style.display='none';
        alert('Invalid OTP entered , email is not successfully verified');
    }
    if(response.status === 200) {
        document.getElementById('overlay').style.display='none';
        alert('User signed up successfully');
        window.location.href = '/Clothing/';
    }
    else{
        // alert('Error is : '+response.text());

        // document.getElementById('overlay').style.display='none';

        response.json().then(errors => {
            errors.forEach(error => {
                var errorFieldName;
                if(error.field!=='password'){
                     errorFieldName=error.field;
                }else{
                     errorFieldName='pass2';
                }
               

                console.log(errorFieldName);
                //change class of input field having error
                const inputField=document.getElementById(errorFieldName);
                inputField.classList.add('is-invalid');

                //display error message in error container of a error field
                const divid=errorFieldName+'Container';
                console.log(divid);
                const errorContainer=document.getElementById(divid);

                const errorMessage=document.createElement('p');
                errorMessage.textContent=error.defaultMessage;

                errorContainer.appendChild(errorMessage);

                setTimeout(function() {
                    errorContainer.removeChild(errorMessage);
                },3000);


            });
        });
        document.getElementById('overlay').style.display='none';
     
        return false;
    }
   },5000)
    
}
function enableVerifyButton(){
    var email=document.getElementById('userId').value;
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if(email.length>0 && emailRegex.test(email)){
        document.getElementById('verifyButton').disabled=false;
    }else{
        document.getElementById('verifyButton').disabled=true;
    }
}

 async function verifyEmail(){
    document.getElementById('overlay').style.display='flex';
    var useremail=document.getElementById('userId').value;
    let response= await fetch('verifyEmail',{
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:useremail

    })
    if(response.status===406){
        document.getElementById('overlay').style.display='none';
        var userid=document.getElementById('userId').value;
        var sidebarHeading=document.getElementById('sidebar-heading')
        sidebarHeading.innerHTML='User is already registered with this '+userid+' account'
        var sidebar=document.getElementById('sidebar');
        sidebar.classList.toggle('active');
        return;
    }
    if(response.status===200){
        // If response is successful, parse the JSON response body
        const responseBody = await response.text();
        alert(responseBody); // Display the response data
        document.getElementById('overlay').style.display='none';
        var verifyContainer=document.querySelector('.verify-container');
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
            // resendButton.style.backgroundColor='red';
            verifyContainer.appendChild(otpInput);
            verifyContainer.appendChild(resendButton);

    }
    else{
        const responseBody = await response.text();
        alert(responseBody); // Display the response data
      document.getElementById('overlay').style.display='none';
    }
}
function hideSidebar(){
    var sidebar=document.getElementById('sidebar');
    sidebar.classList.remove('active');

}
