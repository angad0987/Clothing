document.addEventListener('DOMContentLoaded',function(){
    // Check if the block with ID 'emptyCartMessage' exists
    var emptyCartMessage = document.getElementById('empty-cart-message');
     var emptyWishlistMessage = document.getElementById('empty-wishlist-message');
    if (emptyCartMessage || emptyWishlistMessage) {
        // Run your JavaScript function here
        yourFunction();
    }
    });
     
    
     // Define your JavaScript function
     function yourFunction() {
         // Your function logic goes here
         var footer=document.getElementById('wishlistFooter');
         footer.style.position="relative";
         footer.style.bottom="-300px";
     }
function handleLinkClick(event, itemid) {
    event.preventDefault();//prevents default behavious of link
//            var link = event.currentTarget;
//            var itemid=link.getAttribute('data-itemid');
    // Use SweetAlert to create a customized confirmation dialog
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to delete this item from your wishlist!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#041e3a',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            console.log('Item deletion confirmed');
            // Proceed with the deletion logic here
            console.log('In handle link click method');
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'deleteFromWishlist/' + itemid, true);
            xhr.setRequestHeader('Accept', 'application/json');
            xhr.onload = function () {  
                if (xhr.status === 200) {
                    console.log('After successfully deleted item from wishlist');
                    var response = JSON.parse(xhr.responseText);

                    console.log("I am angad " + response);
                    if (response.includes('success')) {
                        //updating size of wishlist
                        var wishlistSizeSpan = document.getElementById('wishlistSize');
                        var wishlistSize = parseInt(wishlistSizeSpan.innerText);
                        wishlistSize -= 1;
                        //check if wishlist size is 0
                        if (wishlistSize === 0) {

                            var maincontainer = document.querySelector('.main-container');
                            maincontainer.remove();

                            var relatedProducts = document.querySelector('.related-products');
                            relatedProducts.remove();
                            // Display empty wishlist message
                            // Create the empty wishlist message element
                            var emptyWishlistMessage = document.createElement('div');
                            emptyWishlistMessage.classList.add('empty-wishlist-message');

                            // Set the inner HTML of the empty wishlist message
                            emptyWishlistMessage.innerHTML = `
<h1 class="empty-wishlist-empty-message">Oops! Your wishlist seems to be empty...</h1>
<p class="empty-wishlist-description">Looks like you haven't added anything to your wishlist yet. Start adding items now to save them for later!</p>
<small><a href="${pageContext.request.contextPath}/collection" class="continue-shopping-link">START EXPLORING</a></small>
`;

                            var containerfooter = document.querySelector('.containerfooter');
                            containerfooter.insertAdjacentElement('beforebegin', emptyWishlistMessage);
                        } else {
                            wishlistSizeSpan.innerText = wishlistSize;

                            FetchUpdatedWishlistContent();
                        }


                    }
                } else {
                    alert('Request failed with status :' + xhr.status);
                }
            };
            xhr.send();
        }
    });
//            console.log(itemid);
//            var confirmation = confirm('Are you sure you want to delete this item from your wishlist?');
//            if (confirmation) {
//            
//            }

}
function FetchUpdatedWishlistContent() {
    console.log('In fetch updated content method');
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'updatedWishlist', true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('After successfully deleted item from wishlist');
            var response = JSON.parse(xhr.responseText);
            console.log(response);
            updateWishlistUI(response);
        } else {
            alert('Request failed with status :' + xhr.status);
        }
    };
    xhr.send();
}

function updateWishlistUI(data) {
    console.log('In update wishlist UI method');
    //assuming data[0] contains the wishlist items
    var wishlistItems = data[0];
    //assuming data[1] contains the related items to wishlist content
    var relatedItems = data[1];
//
    //clear the existing wishlist container
    var wishlistContainer = document.querySelector('.cart-container');
    wishlistContainer.innerHTML = '';
//            wishlistContainer.innerHTML ='<h1>Angad</h1>';
//
//            //updating the wishlist container
    wishlistItems.forEach(function (item) {
//                var card = document.createElement('div');
//                card.className = 'card';
//
//                var cardBody = document.createElement('div');
//                cardBody.className = 'card-body wishlist-items';
//                // Get item ID before using it in the HTML string
//               
var baseUrl=document.getElementById('baseUrl').getAttribute('data-itemid');
        var itemId = item.item_id;
        console.log('item id is: ' + itemId);
        var imageUrl = baseUrl+"/images/" + itemId + ".jpg";
        console.log('Image url is :' + imageUrl);
        var itemname = item.itemname;
        console.log('Item name :' + itemname);
        var itemprice = item.itemprice;
        console.log('Item price :' + itemprice);
        var itemcategory = item.category;
        console.log('Item category :' + itemcategory);
        var itemquan = item.quantity;
        console.log('Item quantity :' + itemquan);

        //creating the inner html structure
        //we use backticks here `` these are template literals 
        //Template literals allow you to embed expressions () within a string. When the string is evaluated, the expressions are replaced with their corresponding values. This makes it easier to create complex strings that include dynamic content.
        var htmlString = '<div class="card" th:data-itemid="' + itemId + '">' +
                '<div class="card-body wishlist-items">' +
                '<div class="card-body-first-part">' +
                '<div class="image-container-senior">' +
                '<div class="item-image">' +
                '<img src="' + imageUrl + '" />' +
                '</div>' +
                '</div>' +
                '<div class="item-information">' +
                '<div class="stars" style="font-size: 12px;">' +
                '<i id="star-item' + itemId + '" class="fa-regular fa-star" style="color: #f28c28;" onclick="setRating(1,' + itemId + ')"></i>' +
                '<i id="star-item' + itemId + '" class="fa-regular fa-star" style="color: #f28c28;" onclick="setRating(2,' + itemId + ')"></i>' +
                '<i id="star-item' + itemId + '" class="fa-regular fa-star" style="color: #f28c28;" onclick="setRating(3,' + itemId + ')"></i>' +
                '<i id="star-item' + itemId + '" class="fa-regular fa-star" style="color: #f28c28;" onclick="setRating(4,' + itemId + ')"></i>' +
                '<i id="star-item' + itemId + '" class="fa-regular fa-star" style="color: #f28c28;" onclick="setRating(5,' + itemId + ')"></i>' +
                '</div>' +
                '<div class="item-info">' +
                '<h1 class="item-heading">' + itemname + '</h1>' +
                '</div>' +
                '<div class="item-price">' +
                '<h1 class="price">&#8377;' + itemprice + '</h1>' +
                '</div>' +
                '<div class="item-category">' +
                '<p class="category">Category:' + itemcategory + '</p>' +
                '</div>' +
                '<div class="item-quan1">' +
                '<i class="fa-solid fa-chevron-left quan-icon" onclick="decreaseQuantity(' + itemId + ')"></i>' +
                '<div class="quan">' +
                '<span id="span-quantity' + itemId + '">' + itemquan + '</span>' +
                '</div>' +
                '<i class="fa-solid fa-chevron-right quan-icon" onclick="increaseQuantity(' + itemId + ')"></i>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<div class="item-remove-addcart">' +
                '<a class="ajax-link" href="#"><i class="fa-solid fa-trash" onclick="handleLinkClick(event,' + itemId + ')" style="color:#001f3f; margin-right: 0px"></i></a>' +
                '<div class="wishlist-buttons">' +
                '<button class="btn-new" style="background-color:#FFD700">Buy Now</button>' +
                '<form id="singleItemForm" th:action="@{/addtocart}" method="POST"  th:onsubmit="addQuantity('+itemId+',event)" >'+

                '<input type="hidden" th:value="'+itemId+'" name="item_id" >'+
                '<input type="hidden" name="quan" id="quanNew" >'+
                '<div class="button-container text-center">'+
                    '<button class="btn-new" type="submit">Add to Cart</button>'+
                '</div>'+
            '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
        wishlistContainer.innerHTML += htmlString;
//                     cardBody.innerHTML='<h1>I am angad</h1>';
        console.log('In the end of update wishlist ui');
        // Trigger a reload asynchronously after the wishlist UI update
    setTimeout(function () {
        location.reload();
    }, 1000);
    });
}
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



function setRating(rating, itemid) {
    let stars = document.querySelectorAll('#star-item' + itemid);
    stars.forEach(star => {
        star.classList.replace('fa-solid', 'fa-regular');
    });
    // Highlight stars up to the selected rating
    for (var i = 0; i < rating; i++) {
        stars[i].classList.replace('fa-regular', 'fa-solid');
    }

}
function decreaseQuantity(itemid) {
    var span = document.getElementById('span-quantity' + itemid);
    var spanvalue = parseInt(span.innerText);
    if (spanvalue > 1) {
        // var current_cart_Total_price = document.getElementById('price' + itemid);
        // var priceValue = parseFloat(current_cart_Total_price.innerText);

        // var initial_price = document.getElementById('initial_price' + itemid);
        // var initial_price_value = parseFloat(initial_price.innerText);
        // priceValue = priceValue - initial_price_value;


        span.innerText = spanvalue - 1;
        // current_cart_Total_price.innerText = priceValue;

    }

}
function increaseQuantity(itemid) {
    var span = document.getElementById('span-quantity' + itemid);
    var spanvalue = parseInt(span.innerText);

    // var current_cart_Total_price = document.getElementById('price' + itemid);
    // var priceValue = parseFloat(current_cart_Total_price.innerText);

    // var initial_price = document.getElementById('initial_price' + itemid);
    // var initial_price_value = parseFloat(initial_price.innerText);
    // priceValue = priceValue + initial_price_value;


    if (spanvalue <10) {
        // var current_cart_Total_price = document.getElementById('price' + itemid);
        // var priceValue = parseFloat(current_cart_Total_price.innerText);

        // var initial_price = document.getElementById('initial_price' + itemid);
        // var initial_price_value = parseFloat(initial_price.innerText);
        // priceValue = priceValue - initial_price_value;


        span.innerText = spanvalue + 1;
        // current_cart_Total_price.innerText = priceValue;

    }

}

function addQuantity(itemid,event){
    event.preventDefault();
    //setting quantity of items
    var span = document.getElementById('span-quantity' + itemid);
    var spanvalue = parseInt(span.innerText);
    document.getElementById('quanNew'+itemid).value=spanvalue;

    //asking to add to cart or not
    Swal.fire({
        title: 'You like this product ?',
        text: "You want to add this item to you cart",
        icon: 'success',
        showCancelButton: true,
        confirmButtonColor: '#041e3a',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, add it!'
    }).then((result) => {
        if (result.isConfirmed) {
            console.log('result is confirmed');
           document.getElementById('singleItemForm'+itemid).submit();

        }
    });



}