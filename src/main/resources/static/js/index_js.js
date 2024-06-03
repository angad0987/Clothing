 // Get user information JSON string from the model
 document.addEventListener('DOMContentLoaded', function(){
    console.log('Background');
   var carasoulBackgroundDiv=document.getElementById('carousel-background');
   var baseUrl=document.getElementById('baseUrl').getAttribute('data-itemid');
    if(carasoulBackgroundDiv){
     
        console.log(baseUrl);
        carasoulBackgroundDiv.style.backgroundImage= 'url('+baseUrl+'/images/newBackNew.jpg)';
    }

    var username=document.getElementById('username').getAttribute('data-itemid');

console.log(username);
    // Store user information in local storage
    localStorage.setItem('user', username);
   
 }
 );
 // JavaScript code for responsive behavior
// const itemBoxes = document.querySelectorAll('.item-box');

// function resizeItemBoxes() {
//   const screenWidth = window.innerWidth;
//   if (screenWidth < 640) {
//     itemBoxes.forEach((itemBox) => {
//       itemBox.style.flex = '0 1 100%';
//     });
//   } else if (screenWidth < 1024) {
//     itemBoxes.forEach((itemBox) => {
//       itemBox.style.flex = '0 1 50%';
//     });
//   } else {
//     itemBoxes.forEach((itemBox) => {
//       itemBox.style.flex = '0 1 25%';
//     });
//   }
// }

// window.addEventListener('resize', resizeItemBoxes);
// resizeItemBoxes();
 
 

