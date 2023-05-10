function addProductMeta(){
    var productMetasDiv = document.getElementById("product-meta-new");
    var index = productMetasDiv.children.length;
    var newProductMetaDiv = document.createElement("div");
    newProductMetaDiv.classList.add("form-group-product-meta")
    newProductMetaDiv.innerHTML =
        '<label for="key-product-meta-new">Key Product Meta ' + (index + 1) + ' :</label>' +
        '<input type="text" name="productMetas[' + index + '].keyProductMeta"  required id="key-product-meta-new"/><br><br>' +
        '<label for="content-product-meta-new">Content ' + (index + 1) + ':</label>' +
        '<textarea name="productMetas[' + index + '].content" rows="3" id="content-product-meta-new"></textarea><br><br>' +
        '<button type="button" onclick="removeProductMeta(this)">Delete</button>';
    productMetasDiv.appendChild(newProductMetaDiv);
}

function removeProductMeta(button){
    var productMetaDiv = button.parentElement;
    var productMetasDiv = document.getElementById("product-meta-new");
    productMetasDiv.removeChild(productMetaDiv);
    updateProductMetaIndex();
}

function updateProductMetaIndex() {
    var productMetasDiv = document.getElementById("product-meta-new");
    var productMetaList = productMetasDiv.querySelectorAll(".form-group-product-meta");
    for (var i = 0; i < productMetaList.length; i++) {
        var keyProductMeta = productMetaList[i].querySelector("input[type='text']");
        var contentProductMeta = productMetaList[i].querySelector("textarea");
        var keyProductMetaLabel = productMetaList[i].querySelector("label[for='key-product-meta-new']");
        var contentProductMetaLabel = productMetaList[i].querySelector("label[for='content-product-meta-new']");

        keyProductMeta.name = "productMetas[" + i + "].keyProductMeta";
        keyProductMeta.id = "key-product-meta-new" + i;
        contentProductMeta.name = "productMetas[" + i + "].content";
        contentProductMeta.id = "content-product-meta-new" + i;
        keyProductMetaLabel.innerHTML = "Key product meta " + (i + 1) + ":";
        contentProductMetaLabel.innerHTML = "Content " + (i + 1) + ":";
    }
}