function createCorrectGallerySizeWithoutPaginator(settings) {
    var navigation = document.getElementById(settings.navigationId);
    var displayedSize = createSize(settings.displayedColumnsNumber, settings.displayedRowsNumber);
    var paginator = createPaginatorSize(navigation);
    var additionalSize = createAdditionalSize();
    navigation.style.width = (createWidth(displayedSize, paginator) + additionalSize) + "px";
    navigation.style.height = (createHeight(displayedSize, paginator) + additionalSize) + "px";

    var realSize = createSize(settings.columnsNumber, settings.rowsNumber);
    setRowsWidth((realSize.width + additionalSize), settings.rowId, settings.rowsNumber);

    /*-----------------------------------------------internal functions-----------------------------------------------*/
    function createPaginatorSize(navigation) {
        var paginatorHolder = document.getElementById(settings.paginatorId + "Holder");
        var offsetWidth = paginatorHolder ? paginatorHolder.offsetWidth : 0;
        var offsetHeight = paginatorHolder ? paginatorHolder.offsetHeight : 0;
        if (paginatorHolder && navigation.offsetWidth > offsetWidth) {
            offsetWidth = navigation.offsetWidth;
        }
        var size = new Object();
        size.offsetWidth = offsetWidth;
        size.offsetHeight = offsetHeight;
        return size;
    }

    function createWidth(displayedSize, paginator) {
        return (displayedSize.width > paginator.offsetWidth ? displayedSize.width : paginator.offsetWidth);
    }

    function createHeight(displayedSize, paginator) {
        return (displayedSize.height + paginator.offsetHeight);
    }

    function createSize(columnsNumber, rowsNumber) {
        var size = new Object();
        var cell = document.getElementById(settings.cellId);
        size.width = ((cell.offsetWidth * columnsNumber) + (settings.addWidth ? 30 : 0));
        size.height = ((cell.offsetHeight * rowsNumber) + (settings.addHeight ? 30 : 0));
        return size;
    }

    function setRowsWidth(width, rowId, rowsNumber) {
        for (var i = 0; i < rowsNumber; i++) {
            var row = document.getElementById(rowId + i);
            row.style.width = width + "px";
        }
    }

    function createAdditionalSize() {
        if (navigator.appName.indexOf("Internet Explorer") > 0) {
            return 15;
        } else {
            return 0;
        }
    }

    /*-----------------------------------------------internal functions-----------------------------------------------*/
}


