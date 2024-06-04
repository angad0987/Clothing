function validateForm(){
    var userid1=document.getElementById('pass1').value;
    var userid2=document.getElementById('pass2').value;
    if(userid1!==userid2){
        alert('password doesnot match');
        return false;
    }
    submitSignUpForm();
    return false;
}
async function submitSignUpForm() {

    console.log('I am in submit ginup mehtod');
    document.getElementById('overlay').style.display='flex';

    var formData=new FormData(document.getElementById('forgotForm'));

      console.log(formData);
    let jsonData={}
    for (const [key, value] of formData.entries()) {
        if(key !== 'newPassword1'){
            jsonData[key] = value;
        }
      
    }
    console.log(jsonData);

    setTimeout(async function(){
          let response=await fetch('changePassword',{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(jsonData)
        });

        if (response.status === 200) {
            response.text()
                .then(function (baseUrl) {
                    var baseUrlUpdated = '';
                    if (baseUrl === 'Clothing') {
                        baseUrlUpdated = '/Clothing';
                    }
                    else {
                        baseUrlUpdated = baseUrl;
                    }
                    // console.log(baseUrlUpdated);
                    alert('Password changed successfully');
                    document.getElementById('overlay').style.display = 'none';
                    window.location.href = baseUrlUpdated + '/';
                })
                .catch(function (error) {
                    console.error('Error extracting response text:', error);
                });
        }
        if(response.status===500){
            alert("An error occurred while updating your password");
            return false;
        }
        else{
            response.json().then(errors => {
                errors.forEach(error => {
                    var errorFieldName;
                    if(error.field!=='newpassword'){
                        errorFieldName=error.field;
                    }
                    else{
                        errorFieldName='pass2';
                    }
                          //change class of input field having error
                const inputField=document.getElementById(errorFieldName);
                inputField.classList.add('is-invalid');
                
                const errorContainer = document.getElementById(errorFieldName+'Container');
                const errorMessage=document.createElement('p');
                errorMessage.textContent=error.defaultMessage;
                   errorContainer.appendChild(errorMessage);
                   setTimeout(function() {
                    errorContainer.removeChild(errorMessage);
                   },3000);
                });
                
            })
            document.getElementById('overlay').style.display='none';
            return false;

        }
    },3000);




}
