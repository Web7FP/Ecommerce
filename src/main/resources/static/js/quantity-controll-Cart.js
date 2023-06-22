function decrement(e) {
    const btn = e.target.parentNode.parentElement.querySelector(
        'button[data-action="decrement"]'
    );
    const target = btn.nextElementSibling;
    let value = Number(target.value);
    if (value > 1){
        value--;
        target.value = value;
        updateQuantity(target.getAttribute("data-cart-item-id"), value);
    }
}

function increment(e) {
    const btn = e.target.parentNode.parentElement.querySelector(
        'button[data-action="decrement"]'
    );
    const target = btn.nextElementSibling;
    let value = Number(target.value);
    let maxQuantity  = Number(target.getAttribute("maxQuantity"))
    if (value < maxQuantity){
        value++;
        target.value = value;
        updateQuantity(target.getAttribute("data-cart-item-id"), value);
    }

}

function updateQuantity(cartItemId, quantity){
    $.ajax({
        type:"POST",
        url:"/cart/update-quantity-cart-item/" + cartItemId + "/" + quantity,
        success: function (response){
            console.log(response);
            updatePrice(cartItemId, quantity);
            updateSubTotal();
        },
        error: function (xhr, status, error){
            console.log(error);
        }
    })
}

function updatePrice(cartItemId, quantity){
    let priceSpan = document.getElementById('price-cart-item-'+cartItemId);
    let priceProduct = Number(priceSpan.getAttribute("data-price-product"));
    let priceCartItem = priceProduct * quantity;
    priceSpan.textContent = String(priceCartItem);
}

function updateSubTotal(){
    let subTotal = 0;
    let priceSpans = document.querySelectorAll('p[id^="price-cart-item-"]');
    priceSpans.forEach(priceSpan => {
        subTotal += Number(priceSpan.textContent)
    });
    document.getElementById("sub-total-price-cartItem").textContent = String(subTotal);
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