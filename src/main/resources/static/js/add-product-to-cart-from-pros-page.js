function addProductToCart(button){
    let productId = button.getAttribute('data-product-id');
    let url = '/cart/add-product-to-cart/' + productId + '/1';
    $.ajax({
        type: "POST",
        url: url,
        success: function (response){
            console.log(response);
            alert("This product has been added to your cart")
        },
        error: function (xhr, status, error){
            console.log(error);
            if (xhr.status === 400){
                alert("Quantity exceeded");
            } else {
                alert("Error: " + xhr.responseText);
            }
        }
    })
}