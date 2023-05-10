
$(document).ready(function () {
    $("#button-add-product-to-cart").on("click", function () {
        addProductToCart();
    })
})

function addProductToCart(){
    let quantity  = $("#quantity" + productId).val();
    let url = "/cart/add-product-to-cart/" + productId + "/" + quantity;
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
function decrement(e) {
    const btn = e.target.parentNode.parentElement.querySelector(
        'button[data-action="decrement"]'
    );
    const target = btn.nextElementSibling;
    let value = Number(target.value);
    if (value > 1){
        value--;
        target.value = value;
    }
}

function increment(e) {
    const btn = e.target.parentNode.parentElement.querySelector(
        'button[data-action="decrement"]'
    );
    const target = btn.nextElementSibling;
    let value = Number(target.value);
    if (value < maxQuantityProduct){
        value++;
        target.value = value;
    }

}
const decrementButtons = document.querySelectorAll(
    `button[data-action="decrement"]`
);

const incrementButtons = document.querySelectorAll(
    `button[data-action="increment"]`
);

decrementButtons.forEach(btn => {
    btn.addEventListener("click", decrement);
});

incrementButtons.forEach(btn => {
    btn.addEventListener("click", increment);
});