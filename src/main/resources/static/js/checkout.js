function getlocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showAddress, showError);
    } else {
        alert("Geolocation is not supported by this browser.");
    }
}

document.addEventListener("DOMContentLoaded",function(){
 var total=document.getElementById('totalId').getAttribute('data-itemid');
 var discount=document.getElementById('discountId').getAttribute('data-itemid');
 var username=document.getElementById('customerId').getAttribute('data-itemid');
 var tax=document.getElementById('tax').getAttribute('data-itemid');
 
 //user is stoed in local storage
 localStorage.setItem('customerId',username);
 localStorage.setItem('tax',tax);

 document.getElementById('TAX').value=tax;
//  var customerIdInput1=document.getElementById('customerid').value;
//  console.log(customerIdInput1);

 //setting total amount after discoubnt

 var amount_after_discount=total-discount;
 var  total_amount=document.getElementById('total-amount-value');
 total_amount.textContent='₹'+amount_after_discount;




});



function showAddress(position) {
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;

    console.log(latitude);
    console.log(longitude);
    // Construct the API request URL for reverse geocoding
    var apiURL = "https://geocode.maps.co/reverse?lat=" + latitude + "&lon=" + longitude + "&api_key=65f1ed7a89a95958311597ecjbae0f8";

    //make a get reuqest to the api
    fetch(apiURL)
            .then(response => response.json())
            .then(data => {
                if (data.display_name && data.display_name.length > 0) {
                    var address = data.display_name;
                    // Update address text
                    var addressText = document.querySelector(".address-text");
                    addressText.textContent = address;
                } else {
                    alert('address not found');
                }
            })

            //this catch method in the chain of first then method 
            //if any error occurs in parsing of json object then it return promise in failuer statet
            //then this catch method catch the error why it faild
            .catch(error => {
                alert('error fetching address' + error);
            });
}
function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.");
            break;
        case error.UNKNOWN_ERROR:
            alert("An unknown error occurred.");
            break;
    }
    ;
}






function showAddressForm() {
    var modal = document.getElementById("addressModal");
    modal.style.display = "block";
}

function hideAddressForm() {
    var modal = document.getElementById("addressModal");
    modal.style.display = "none";
}
document.addEventListener("DOMContentLoaded", function () {
    var form = document.getElementById("addressForm");
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Get form values
        var houseNo = document.getElementById("houseno").value;
        var streetName = document.getElementById("street").value;
        var pincode = document.getElementById("pincode").value;
        var city = document.getElementById("city").value;
        var state = document.getElementById("state").value;
        var country = document.getElementById("country").value;
        // Update address text
        var addressText = document.querySelector(".address-text");
        addressText.textContent = houseNo + " " + streetName + ", " + pincode + ", " + city + ", " + state + ", " + country;
        // Hide the modal
        hideAddressForm();
    });
});

  function confirmAddress(){
    var loadGif=document.getElementById('load-gif');
    loadGif.style.display='block';
     setTimeout(function(){
        var shippingAddress=document.getElementById('address-p').innerHTML;

        console.log('Shipping address'+document.getElementById('shippingAddress').value);

        //setting this shipping address to local storage
        localStorage.setItem('shippingAddress',shippingAddress);
       
        var autofill_address_card= document.getElementById("autofill-address-card");
        autofill_address_card.classList.toggle('active');
        loadGif.style.display='none';

        updateSideBar('address');
        // showPaymentCard();
        showOfferCard();
     },2000);
 }
 async function updateSideBar(element){
    if(element!==null){
        if(element==='address'){
            var price_detail_heading=document.getElementById('price-detail-heading');
            price_detail_heading.innerText='Address is selected';
            var addresButton=document.getElementById('changeAddressButton').style.display='block';
        }
        if(element==='payment'){
            var price_detail_heading=document.getElementById('price-detail-heading');
            price_detail_heading.innerText='Payment Succesfull';
            document.getElementById('changeAddressButton').style.display='none';
            document.getElementById('placeorder').style.display='block';
        }
        if(element==='offer'){
            var price_detail_heading=document.getElementById('price-detail-heading');
            price_detail_heading.innerText='Offer Applied !';
            var addresButton=document.getElementById('changeAddressButton').style.display='none';
        }
        }
        
 }
 function setNewAddress(){
    removePaymentCard();
    var autofill_address_card= document.getElementById("autofill-address-card");
    autofill_address_card.classList.remove('active');
    regainSidebar('address');
    removeOfferCard();
 }
 async function regainSidebar(element){
    if(element!==null){
        if(element==='address'){
            var price_detail_heading=document.getElementById('price-detail-heading');
            price_detail_heading.innerText='Price Details';
            var addresButton=document.getElementById('changeAddressButton').style.display='none';
        }
        }
 }
 async function showPaymentCard(){
    console.log('I am in payment card');
    var payment_card=document.getElementById('payment-card');
    payment_card.classList.toggle('active');
 }
 
 async function removePaymentCard(){
    console.log('I am in payment card');
    var payment_card=document.getElementById('payment-card');
    payment_card.classList.remove('active');
 }
 async function showOfferCard(){
    console.log('I am in offer card');
    var offer_card=document.getElementById('offer-card');
    offer_card.classList.toggle('active');
    // setTimeout(()=>{
    //  payment_card.style.display='none';
    // },1000);
 }
 async function removeOfferCard(){
    console.log('I am in offer card');
    var offer_card=document.getElementById('offer-card');
    offer_card.classList.remove('active');
    // setTimeout(()=>{
    //  payment_card.style.display='none';
    // },1000);
 }
 document.addEventListener('DOMContentLoaded',function(){
    // var shippingAddressFromLocalStorage=localStorage.getItem('shippingAddress');
    var paymentSuccess=localStorage.getItem('paymentSuccess');
   
        if(paymentSuccess){
            var autofill_address_card= document.getElementById("autofill-address-card");
            autofill_address_card.classList.toggle('active');
            console.log('payment success');
            //if payment is successfull we have to also show which offer is applied so we change the side bar
            var offerCode=document.getElementById('offer-code');
            offerCode.style.display='block';
            offerCode.textContent=localStorage.getItem('promocodeName');
            updateSideBar('payment'); 
            return; 
        }
        if(paymentSuccess==='false'){
            showAlert('payment is not successfull');
        }
          
        
        
    
 });

 function showAlert(message) {
    var custom_alert = document.getElementById('custom-alert');
    var alert_message = document.getElementById('alert-message');
    alert_message.textContent = message;
    custom_alert.classList.toggle('active');
    setTimeout(function () {
        custom_alert.classList.remove('active');
    }, 3000);
    
}
 let promoCodesCache = []; // Local cache for promo codes

async function getPromoCodes() {
   
    let response = await fetch('getPromoCodes', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (response.status === 200) {
        promoCodesCache = await response.json();
        console.log('Second CACHE IS FILLED');

    } else {
        alert('Error in getting promo codes');
    }
}
 async function applyPromoCode(){
    if (promoCodesCache.length === 0) {
        console.log('LENGTH OF CACHE IS 0');
        await getPromoCodes();
    }
        //now performs checking of promo codes
        var inputPromoCode=document.getElementById('promo-code-input').value;
        var promoCodes=promoCodesCache;

        promoCodes.forEach(function(promocode){
            console.log(promocode);
        });
        promoCodes.forEach(function(promocode){
            var promocodeTimesUsed=promocode.timesUsed;
            var promocodeUsageLimit=promocode.usageLimit;
            var promocodeName=promocode.code;
            if(promocodeName===inputPromoCode){
                if(promocodeTimesUsed>promocodeUsageLimit){
                    console.log('promo code is not more available');
                }
                else{
                    usePromoCode(promocode);
                    return;
                }
            }
          
        });
        //here when it reaches here means no promo code matches
        // alert('promo code is not valid');
    }
    
    function usePromoCode(promocode){
        var promocodeTimesUsed=promocode.timesUsed;
        var promocodeUsageLimit=promocode.usageLimit;
        var promocodeName=promocode.code;
        var promocodeDiscount=promocode.discountValue;
        console.log(promocodeDiscount);
        
        //setting offer name to local storage
        localStorage.setItem('promocodeName',promocodeName);
        

        //change side bar
         updateSideBar('offer');
        //change total amount after offer code
        var total=document.getElementById('totalId').getAttribute('data-itemid');
        var discount=document.getElementById('discountId').getAttribute('data-itemid');
       
        //setting total amount after discoubnt
       
        var amount_after_discount=total-discount;
        var  total_amount=document.getElementById('total-amount-value');

        // console.log(totalAmountValueInNumber);
        var offerAmount=amount_after_discount-parseInt(promocodeDiscount);
        //setting amoutn after offer in placed order form
        var totalAmountInput=document.getElementById('totalAmount');
        totalAmountInput.value=total;

        console.log(offerAmount);
        //setting total amount after offer in local storage for further processing
        localStorage.setItem('totalAmount',offerAmount);
        total_amount.innerText=offerAmount;
        //showing that offer is placed
        var offerCode=document.getElementById('offer-code');
        offerCode.style.display='block';
        offerCode.textContent=promocodeName;

        //AFTER applying offer
        //remove offer card
        removeOfferCard();
        //then show payment card
        showPaymentCard();

    }

    function placeOrder(){
         var customerId=localStorage.getItem('customerId');
         var totalAmount=localStorage.getItem('totalAmount');
         var promocodeName=localStorage.getItem('promocodeName');
         var shippingAddress=localStorage.getItem('shippingAddress');
         var taxRate=localStorage.getItem('tax');
         // Check if all values are not null
    if (customerId && totalAmount && promocodeName && shippingAddress && tax) {
        // Simulate a payment success check

        var paymentSuccess = localStorage.getItem('paymentSuccess'); // Replace this with actual payment success check
        
        if (paymentSuccess) {
            // Fill in the form with the retrieved values
            document.getElementById('customerid').value = customerId;
            document.getElementById('totalAmount').value = totalAmount;
            document.getElementById('offer').value = promocodeName;
            document.getElementById('shippingAddress').value = shippingAddress;
            
            // Submit the form
            document.getElementById('placeOrderForm').submit();
        } else {
            console.error("Payment failed");
            // Handle payment failure (e.g., show error message to user)
        }
    } else {
        console.error("One or more required values are missing");
        // Handle missing values (e.g., show error message to user)
    }

    }
   
 