
document.addEventListener("DOMContentLoaded", function() {
 

    // Define the color map object
    const colorMap = {
        'black': '#000000',
        'pink': '#ffc0cb',
        'white': '#ffffff',
        'brown': '#a52a2a',
        'blue': '#0000ff',
        'orange': '#ffa500',
        'dark blue': '#00008b',
        'olive green': '#808000',
        'navy blue': '#000080',
        'purple': '#800080',
        'grey': '#808080',
        'black quartz': '#191919',
        'jet black': '#080808',
        'almond': '#efdecd',
        'night forest': '#232f34',
        'beige': '#f5f5dc',
        'peach pink': '#ffdab9',
        'hydro blue': '#0066cc',
        'dark green': '#006400',
        'french wine': '#ac1e44',
        'sage': '#bcb88a',
        'pale blue': '#afeeee',
        'grey charcoal': '#36454f',
        'light grey': '#d3d3d3',
        'light blue': '#add8e6',
        'black grey': '#333333',
        'sky blue': '#87ceeb',
        'red': '#ff0000',
        'black silver': '#848482',
        'black brown': '#3b2f2f',
        'burgundy': '#800020',
        'dark beige': '#554c3c',
        'lavender': '#e6e6fa',
        'maroon': '#800000',
        'cream': '#fffdd0',
        'onion': '#a92940'
    };
    

    // Output the color map
    console.log(colorMap);

    const itemColor=document.getElementById('item-color');
    const itemColorValue=itemColor.getAttribute('data-itemid').toLowerCase();
    const hexCode = colorMap[itemColorValue];

    document.getElementById('color-box').style.backgroundColor=hexCode;


    var addedMessage = document.getElementById('addedMessage').getAttribute('data-userid');

if(addedMessage!== null){
    showAlert('Item added to cart');
}





});
function checkUser(event) {
    // event.preventDefault();
    var userid = document.getElementById('userid').getAttribute('data-userid');
     var booli=false;
    if(userid===null || userid===' ' ){
        showAlert('Sign in to add items to cart');
        return false;
    }

    Swal.fire({
        title: 'You like this product ?',
        text: "You want to add this item to you cart",
        icon: 'delete',
        showCancelButton: true,
        confirmButtonColor: '#041e3a',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, add it!'
    }).then((result) => {
        if (result.isConfirmed) {
            console.log('result is confirmed');
           document.getElementById('singleItemForm').submit();

        }
    }
);
return false;


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
