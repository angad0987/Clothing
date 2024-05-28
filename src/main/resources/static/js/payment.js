$(function() {
    $('[data-toggle="tooltip"]').tooltip()
  })
  // Function to navigate back to the checkout page
  function navigateToCheckoutPage() {
    console.log(document.referrer);
    localStorage.setItem('paymentSuccess', 'true');
    // console.log('State set:', history.state); // Debugging: Log the state
    window.history.back() // Go back to the previous page (checkout page)
}