document.addEventListener("DOMContentLoaded", function () {
    var userid=document.getElementById('userid').getAttribute('data-itemid');
    checkWishlist(userid);
    // var filterContainer = document.querySelector(".filter-container");
    // var contentContainer = document.querySelector(".content-container");

    // // Check if both filter-container and content-container exist
    // if (filterContainer && contentContainer) {
    //     // Set the height of filter-container to match the height of content-container
    //     filterContainer.style.height = contentContainer.clientHeight + "px";
    // }
    // Get the .filter-container element
    //   const filterContainer = document.querySelector('.filter-container');

    //   // Calculate the height of the content
    //   const contentHeight = filterContainer.scrollHeight;

    //   // Set the bottom position to ensure content visibility
    //   filterContainer.style.bottom = `calc(100vh - ${contentHeight}px)`;



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

    filterForm.addEventListener('submit', function (event) {
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
            // console.log(response);
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
    console.log('on adding in wishlist'+userid);
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
            xhr.open('POST', 'AddToWishlist', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () {
                if (xhr.status === 200) {
                    wishlistIcon.classList.replace('fa-regular', 'fa-solid');
                    wishlistIcon.style.color = '#041e3a';
                }
                ;
            };
            xhr.send('itemname=' + formData.get('itemname') + '&itemprice=' + formData.get('itemprice') + '&quantity=' + formData.get('quantity') + '&category=' + formData.get('category') + '&description=' + formData.get('description') + '&user_id=' + userid + '&itemid=' + formData.get('itemid'));
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
function open_filter_section() {
    var filter_section = document.getElementById('filter-container');
    filter_section.classList.toggle('active');
    var open_filter = document.getElementById('openFilterId');
    open_filter.innerHTML=`    <svg  onclick="close_filter_section()"  width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">

    <g id="SVGRepo_bgCarrier" stroke-width="0"/>
    
    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"/>
    
    <g id="SVGRepo_iconCarrier"> <g id="Arrow / Chevron_Up"> <path id="Vector" d="M5 16L12 9L19 16" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/> </g> </g>
    
    </svg>`;
}
function close_filter_section() {
    var filter_section = document.getElementById('filter-container');
    filter_section.classList.remove('active');
    var open_filter = document.getElementById('openFilterId');
    open_filter.innerHTML=` <svg onclick="open_filter_section()" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M7 10L12 15L17 10" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>`;
}

function removing_filter_section(filtername) {
 // Hide all other filter contents
 const allFilterContents = document.querySelectorAll('.filter-content');
 allFilterContents.forEach(filterContent => {
     if (filterContent.id !== 'filter-content' + filtername) {
         filterContent.classList.remove('active');
         var filtercontentName=filterContent.id;
         const substringName=filtercontentName.slice(14);
         console.log(substringName);
         var inner_heading = document.getElementById('inner-heading' + substringName);
         inner_heading.innerHTML = `
         <svg onclick="toggleFilterContent('`+ substringName + `')" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
         <path d="M7 10L12 15L17 10" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
         </svg>
         <h1 class="filter-heading">`+ substringName + `</h1>
     `;
     }
 });

 //explicityly removing color content
 var filterContent1=document.querySelector('.color-filter');
 filterContent1.classList.remove('active');

}
function toggleFilterContent(filtername) {

   removing_filter_section(filtername);

    console.log('after toggle ' + filtername);
    const inner_heading = document.getElementById('inner-heading' + filtername);
    inner_heading.innerHTML = `
    <svg  onclick="removeFilterContent('`+ filtername + `')"  width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">

<g id="SVGRepo_bgCarrier" stroke-width="0"/>

<g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"/>

<g id="SVGRepo_iconCarrier"> <g id="Arrow / Chevron_Up"> <path id="Vector" d="M5 16L12 9L19 16" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/> </g> </g>

</svg>

    <h1 class="filter-heading">`+ filtername + `</h1>
`;
    if (filtername === 'Color') {
        const filterContent = document.querySelector('.color-filter');
        filterContent.classList.toggle('active');
        return;
    }
    const filterContent = document.getElementById('filter-content' + filtername);
    filterContent.classList.toggle('active');
}
function removeFilterContent(filtername) {
    console.log('after remove ' + filtername);
    const inner_heading = document.getElementById('inner-heading' + filtername);
    inner_heading.innerHTML = `
    <svg onclick="toggleFilterContent('`+ filtername + `')" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M7 10L12 15L17 10" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
    <h1 class="filter-heading">`+ filtername + `</h1>
`;
    if (filtername === 'Color') {
        const filterContent = document.querySelector('.color-filter');
        filterContent.classList.remove('active');
        return;
    }
    const filterContent = document.getElementById('filter-content' + filtername);
    filterContent.classList.remove('active');
}

