var paginator = {

    pageNumber : 1,

    next : function (updatePaginatorItemsFunction) {
        this.setPageNumber(this.getNextPageNumber());
        updatePaginatorItemsFunction();
    },

    prev : function (updatePaginatorItemsFunction) {
        this.setPageNumber(this.getPreviousPageNumber());
        updatePaginatorItemsFunction();
    },

    byPageNumber : function (pageNumber, updatePaginatorItemsFunction) {
        this.setPageNumber(pageNumber);
        updatePaginatorItemsFunction();
    },

    getPageNumber : function() {
        return this.pageNumber;
    },

    setPageNumber : function(pageNumber) {
        this.pageNumber = pageNumber;
    },

    setFirstPage : function() {
        this.setPageNumber(1);
    },

    getNextPageNumber : function() {
        var currentPageNumber = this.getCurrentPageNumber();
        return currentPageNumber ? (currentPageNumber + 1) : null;
    },

    getPreviousPageNumber : function() {
        var currentPageNumber = this.getCurrentPageNumber();
        return currentPageNumber ? (currentPageNumber - 1) : null;
    },

    getCurrentPageNumber : function() {
        var currentPage = $("#currentPaginatorPage");
        if (currentPage.length > 0) {
            return parseInt($(currentPage[0]).attr("pageNumber"));
        }
        return null;
    }

};