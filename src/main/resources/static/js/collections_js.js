function changeImage() {
    var img1 = document.getElementById('image');
    img1.style.opacity = 0; // Set opacity to 0
    setTimeout(function () {
        img1.src = 'resources/images/men2.jpg';
        img1.style.opacity = 1;
    }, 200);
}
function retainImage() {
    var img1 = document.getElementById('image');
    img1.style.opacity = 0; // Set opacity to 0
    setTimeout(function () {
        img1.src = 'resources/images/men.jpg';
        img1.style.opacity = 1;
    }, 200);
}