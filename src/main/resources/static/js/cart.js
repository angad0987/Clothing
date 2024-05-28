
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
let updateQueue=[];
let batchTimeOut;

function queueUpdate(itemid,quantity){
    const existingUpdate=updateQueue.find(q => q.itemid === itemid);
    //if item exist in update queue then updates is quantity
    if(existingUpdate){
        existingUpdate.quantity=quantity;
    }
    else{
        updateQueue.push({
            'itemid':itemid+'',
            'quantity':quantity
        });
    }
   console.log(updateQueue);
   if(batchTimeOut){
    clearTimeout(batchTimeOut);
   }
   batchTimeOut=setTimeout(sendBatchUpdate,10000);
}

 async function sendBatchUpdate(){
    if(updateQueue.length===0){
        return;
    }
try{
    let response=await fetch('updateCartQuantity',{
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(updateQueue)
    });
    if(response.status===200){
        //items are updateed successfully
        updateQueue=[];
    }
    else{
        alert('Error in updating cart items');
    }
}
catch(error){
    alert('Error in updating cart items');
}
   
}


function decreaseQuantity(cartitemid,itemid) {
    var span = document.getElementById('span-quantity' + cartitemid);
    var spanvalue = parseInt(span.innerText);
    if (spanvalue > 1) {
        var current_cart_Total_price = document.getElementById('price' + cartitemid);
        var priceValue = parseFloat(current_cart_Total_price.innerText);

        var initial_price = document.getElementById('initial_price' + cartitemid);
        var initial_price_value = parseFloat(initial_price.innerText);
        priceValue = priceValue - initial_price_value;


        span.innerText = spanvalue - 1;
        current_cart_Total_price.innerText = priceValue;

        queueUpdate(itemid,spanvalue-1);

    }

}
function increaseQuantity(cartitemid,itemid) {
    var span = document.getElementById('span-quantity' + cartitemid);
    var spanvalue = parseInt(span.innerText);

    var current_cart_Total_price = document.getElementById('price' + cartitemid);
    var priceValue = parseFloat(current_cart_Total_price.innerText);

    var initial_price = document.getElementById('initial_price' + cartitemid);
    var initial_price_value = parseFloat(initial_price.innerText);
    priceValue = priceValue + initial_price_value;


    span.innerText = spanvalue + 1;
    current_cart_Total_price.innerText = priceValue;
    
    queueUpdate(itemid,spanvalue+1);


}
document.addEventListener("DOMContentLoaded", function () {
    var summarySection = document.querySelector('.footer-purchase');
    var sidebar = document.getElementById('sidebar');


    summarySection.addEventListener('click', function () {
        var ItemPrices = document.getElementsByClassName('h-item-price');
        var subtotal1 = 0;
        var totalSum = 0;
        for (var i = 0; i < ItemPrices.length; i++) {
            var price = parseFloat(ItemPrices[i].innerText);
            var tax = Math.round(price * 0.12);
            console.log(tax);
            document.getElementById('taxOnAmount').value=tax;
            subtotal1 = subtotal1 + price;
            totalSum = totalSum + price + tax;
            console.log(total);

        }
        var subtotal = document.getElementById('subtotal');
        subtotal.innerText = "Sub Total: ₹ " + subtotal1;
        var total = document.getElementById('total');
        total.innerText = "Total: ₹ " + totalSum.toFixed(2);
        
        var input_title=document.getElementById('total-input');
        input_title.value=totalSum.toFixed(0);
        

        sidebar.classList.toggle('active');
    });
});
document.addEventListener("DOMContentLoaded", function () {
    var downArrowImage = document.querySelector('.down-img');

    var promoField = document.getElementById('promo-text-field');
    downArrowImage.addEventListener('click', function () {
        if (promoField.classList.contains('active')) {
            promoField.classList.remove('active');
            downArrowImage.src = '/Clothing/images/downNew.png';
        } else {
            promoField.classList.toggle('active');
            downArrowImage.src = '/Clothing/images/upNew.png';
        }

    });

});

function hideTheSummary() {
    var sidebar = document.getElementById('sidebar');
    sidebar.classList.remove('active');
}

async function formSubmit(){
    //if there are updates in the cart then make changes in database also
    if(updateQueue.length==!0){
        await sendBatchUpdate();
    }
    var form=document.getElementById('checkout-form');
    form.submit();
}



