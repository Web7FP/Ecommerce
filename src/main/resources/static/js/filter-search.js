function sortNameASC() {
    sortFieldSelection= 'titleKeyword';
    sortDirectionSelection = 'asc';
    updateSearchForm();
}

function sortNameDESC() {
    sortFieldSelection= 'titleKeyword';
    sortDirectionSelection = 'desc';
    updateSearchForm();
}

function sortPriceASC(){
    sortFieldSelection = 'price';
    sortDirectionSelection = 'asc';
    updateSearchForm();
}

function sortPriceDESC(){
    sortFieldSelection = 'price';
    sortDirectionSelection = 'desc';
    updateSearchForm();
}
function updatePriceSelection() {
    updateSearchForm();
}
function updateCategoriesSelection(checkbox) {
    if (selectedCategories == null){
        selectedCategories = [];
    }
    let categoryValue = checkbox.value;
    let index = selectedCategories.indexOf(categoryValue);
    if (checkbox.checked && index === -1){
        selectedCategories.push(categoryValue);
    } else if (!checkbox.checked && index !== -1){
        selectedCategories.splice(index, 1);
    }
    updateSearchForm();
}

function updateTagsSelection(checkbox){
    if (selectedTags == null){
        selectedTags = [];
    }
    let tagValue = checkbox.value;
    let index = selectedTags.indexOf(tagValue);
    if (checkbox.checked && index === -1){
        selectedTags.push(tagValue);
    } else if (!checkbox.checked && index !== -1){
        selectedTags.splice(index, 1);
    }
    updateSearchForm();
}

function updateSearchForm() {
    let categoriesInput = document.getElementById('categories-selection-input');
    let tagsInput = document.getElementById('tags-selection-input');
    let minPriceInput = document.getElementById('lower-bound-price-input');
    let maxPriceInput = document.getElementById('upper-bound-price-input');
    let sortFieldInput = document.getElementById('sort-field-input');
    let sortDirectionInput = document.getElementById('sort-direction-input');

    let minPrice = document.getElementById('lower-bound-price-selection').value.toString();
    let maxPrice = document.getElementById('upper-bound-price-selection').value.toString();

    if (selectedCategories != null) {
        if (selectedCategories.length !== 0) {
            categoriesInput.value = selectedCategories.join(',');
            categoriesInput.name = 'category';
        } else {
            categoriesInput.removeAttribute("name");
        }
    }
    if (selectedTags != null){
        if (selectedTags.length !== 0){
            tagsInput.value = selectedTags.join(',');
            tagsInput.name = 'tag';
        } else {
            tagsInput.removeAttribute("name");
        }
    }

    if (minPrice.length !== 0 && maxPrice.length !== 0){
        minPriceInput.value = minPrice;
        minPriceInput.name = 'lowerBoundPrice';

        maxPriceInput.value = maxPrice;
        maxPriceInput.name = 'upperBoundPrice';
    } else {
        minPriceInput.removeAttribute('name');
        maxPriceInput.removeAttribute('name');
    }

    if (sortDirectionSelection != null && sortFieldSelection != null){
        sortFieldInput.value = sortFieldSelection;
        sortFieldInput.name = 'sortField';

        sortDirectionInput.value = sortDirectionSelection;
        sortDirectionInput.name = 'sortDir';
    } else {
        sortFieldInput.removeAttribute('name');
        sortDirectionInput.removeAttribute('name');
    }
    document.getElementById('search-form').submit();
}