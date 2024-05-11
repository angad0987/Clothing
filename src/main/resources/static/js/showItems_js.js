document.addEventListener("DOMContentLoaded", function () {
    var filterContainer = document.querySelector(".filter-container");
    var contentContainer = document.querySelector(".content-container");
    
    // Check if both filter-container and content-container exist
    if (filterContainer && contentContainer) {
        // Set the height of filter-container to match the height of content-container
        filterContainer.style.height = contentContainer.clientHeight + "px";
    }



    var userid = document.getElementById('userid').getAttribute('data-userid');
    console.log(userid);
    if (userid !== null) {
        checkWishlist(userid);
    }

    //selecting color boxes

    var colorBoxes = document.querySelectorAll('.color-box');
    colorBoxes.forEach(function (box) {
        box.addEventListener('click', function () {

            //remove selected boxes
            colorBoxes.forEach(function (box) {
                box.classList.remove('selected');
            });

            // Add 'selected' class to the clicked color box
            this.classList.add('selected');


            var dataItemColor = this.getAttribute('data-itemid');
            console.log(dataItemColor);
            document.getElementById('color').value = dataItemColor;



        });
    });


    //clearing checked boxes
    var categoryButton = document.getElementById('category-button');
    var productButton = document.getElementById('product-button');
    var priceButton = document.getElementById('price-button');
    var discountButton = document.getElementById('discount_button');
    var colorButton = document.getElementById('color-button');

    categoryButton.addEventListener('click', function () {
        var checkedradioButton = document.querySelector('input[name="gender"]:checked');

        if (checkedradioButton) {
            checkedradioButton.checked = false;
        }
    });
    productButton.addEventListener('click', function () {
        var checkedradioButton = document.querySelector('input[name="product"]:checked');

        if (checkedradioButton) {
            checkedradioButton.checked = false;
        }
    });
    priceButton.addEventListener('click', function () {
        var checkedradioButton = document.querySelector('input[name="price"]:checked');

        if (checkedradioButton) {
            checkedradioButton.checked = false;
        }
    });
    discountButton.addEventListener('click', function () {
        var checkedradioButton = document.querySelector('input[name="discount"]:checked');

        if (checkedradioButton) {
            checkedradioButton.checked = false;
        }
    });
    colorButton.addEventListener('click', function () {
        console.log('i am in color change event listener');

        var colorBoxes = document.querySelectorAll('.color-box');
        colorBoxes.forEach(function (box) {
            box.classList.remove('selected');

        });
        document.getElementById('color').value = '';
    });
    var filterForm = document.getElementById('filter-form');
    var colorInput = document.getElementById('color');

    filterForm.addEventListener('submit', function(event) {
        // Check if a color is selected
        if (colorInput.value.trim() === '') {
            // Remove the color field from the form
            colorInput.remove();
        }
    });



});

function checkWishlist(userid) {
    var applicationPath = window.location.pathname.split('/')[1];
    console.log(applicationPath);
    var xhr = new XMLHttpRequest();
    console.log("Userid is : " + userid);
    var encodedUserid = encodeURIComponent(userid).replace(/\./g, '%2E');
    console.log(encodedUserid);
    xhr.open('GET', 'http://localhost:8181/' + applicationPath + '/CheckWishlist/' + encodedUserid, true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            var cards = document.querySelectorAll('.card');
            cards.forEach(function (card) {
                var itemid = card.getAttribute('data-itemid');
                console.log(itemid);
                console.log(response);
                if (response.includes(parseInt(itemid))) {
                    console.log('I am in if block');
                    var wishlistIcon = document.getElementById('wishlist-icon' + itemid);
                    wishlistIcon.classList.replace('fa-regular', 'fa-solid');
                    wishlistIcon.style.color = '#041e3a';
                }
            });
        }
    };
    xhr.send();
}

// var message = "${itemAdded}";
// if (message !== "") {
//     alert(message);
// }

function addToCart(itemid) {
    var overlay = document.getElementById('overlay');
    overlay.style.display = 'flex';
    setTimeout(function () {
        overlay.style.display = 'none'; // Hide the overlay
        document.getElementById('cart-form' + itemid).submit(); // Submit the form
    }, 2000);
}
function AddToWishList(itemid) {
    var userid = document.getElementById('userid').getAttribute('data-userid');
    if (userid !== null) {
        var wishlistIcon = document.getElementById('wishlist-icon' + itemid);
        wishlistIcon.style.transition = 'color 0.3s';
        if (wishlistIcon.classList.contains('fa-regular')) {
            //accessing form from dom and accessing form data from form object
            var form = document.getElementById('wishlist-form' + itemid);
            var formData = new FormData(form);
            console.log(formData.get('itemname'));
            //send async reuqest
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/Clothing/AddToWishlist', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () {
                if (xhr.status === 200) {
                    wishlistIcon.classList.replace('fa-regular', 'fa-solid');
                    wishlistIcon.style.color = '#041e3a';
                }
                ;
            };
            xhr.send('itemname=' + formData.get('itemname') + '&itemprice=' + formData.get('itemprice') + '&quantity=' + formData.get('quantity') + '&category=' + formData.get('category') + '&description=' + formData.get('description') + '&user_id=' + formData.get('user_id') + '&itemid=' + formData.get('itemid'));
            //                    if (wishlistIcon.classList.contains('fa-regular')) {
            //                        wishlistIcon.classList.replace('fa-regular', 'fa-solid');
            //                        wishlistIcon.style.color = '#FF0000';
            ////                        document.getElementById('wishlist-form'+itemid).submit();
            //                    } else {
            //                        wishlistIcon.classList.replace('fa-solid', 'fa-regular');
            //                        wishlistIcon.style.color = '#000000';
            //                    }

        } else {
            wishlistIcon.classList.replace('fa-solid', 'fa-regular');
            wishlistIcon.style.color = '#000000';
        }
    } else {

        showAlert('Log in to add item in wishlist!');
        // Add style to the alert

    }
}

//                var wishlistForm=document.getElementById('wishlist-form'+itemid).submit();

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

