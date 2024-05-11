function decreaseQuantity(itemid) {
    var span = document.getElementById('span-quantity' + itemid);
    var spanvalue = parseInt(span.innerText);
    if (spanvalue > 1) {
        var current_cart_Total_price = document.getElementById('price' + itemid);
        var priceValue = parseFloat(current_cart_Total_price.innerText);

        var initial_price = document.getElementById('initial_price' + itemid);
        var initial_price_value = parseFloat(initial_price.innerText);
        priceValue = priceValue - initial_price_value;


        span.innerText = spanvalue - 1;
        current_cart_Total_price.innerText = priceValue;

    }

}
function increaseQuantity(itemid) {
    var span = document.getElementById('span-quantity' + itemid);
    var spanvalue = parseInt(span.innerText);

    var current_cart_Total_price = document.getElementById('price' + itemid);
    var priceValue = parseFloat(current_cart_Total_price.innerText);

    var initial_price = document.getElementById('initial_price' + itemid);
    var initial_price_value = parseFloat(initial_price.innerText);
    priceValue = priceValue + initial_price_value;


    span.innerText = spanvalue + 1;
    current_cart_Total_price.innerText = priceValue;


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



