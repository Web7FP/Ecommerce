$(document).ready(function () {
    let url = '/search?q=' + query +
        '&sortField=' +  sortField +
        '&sortDir=' + sortDir;
    if (categories != null) {
        url += '&category=' + categories;
    }

    if (tags != null){
        url += '&tag=' + tags;
    }

    if (upperBoundPrice != null && lowerBoundPrice != null){
        url += '&lowerBoundPrice=' + lowerBoundPrice +
            '&upperBoundPrice=' + upperBoundPrice;
    }

    for (let i = 1; i <= totalPages; i++){
        if (i !== currentPage){
            let link =  '<a href="' + url + '&page=' +i + '">' +i+ '</a>' ;
            $('span.page-links').append(link);
        } else {
            $('span.page-links').append('<span>'+i+'</span>');
        }
    }

    if (currentPage < totalPages) {
        let p = currentPage + 1;
        let nextPageLinkButton = '<a href="'+url+'&page=' + p +'">&raquo;</a>';
        $('div.pagination-button-next').append(nextPageLinkButton);
    }

    if (currentPage <= totalPages && currentPage > 1){
        let p = currentPage - 1;
        let previousPageLinkButton = '<a href="'+url+'&page=' + p +'">&laquo;</a>';
        $('div.pagination-button-prev').append(previousPageLinkButton);
    }
})