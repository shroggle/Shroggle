var manageRegistrants = {};

manageRegistrants.showEditVisitor = function (visitorId, siteId) {
    var configureWidget = createConfigureWindow({width:600, height:680});

    new ServiceCall().executeViaDwr("EditVisitorService", "show", visitorId, siteId, function (html) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureWidget.setContent(html);
    });
};

manageRegistrants.saveVisitorEdit = function (visitorId) {
    var formItems = new Object();
    var siteId = $("#manageRegistrantsSiteId").val();
    collectFilledFormItems(formItems, 0, new Array());

    new ServiceCall().executeViaDwr("SaveEditVisitorService", "execute", visitorId, siteId, formItems.filledFormItems, function (visitorInfo) {
        manageRegistrants.manageRegistrantsResponse = visitorInfo;
        var widgetId = $("#formWidgetId").val();
        var filledFormId = $("#prefilledFormId").val();
        uploadersStarter.startFileUploading(widgetId, filledFormId, manageRegistrants.manageRegistrantsOnUploadFunction);
    });
};

manageRegistrants.manageRegistrantsResponse = undefined;
manageRegistrants.manageRegistrantsOnUploadFunction = function() {
    manageRegistrants.search();
    swfUploaderRemoveBackgroundLoadingMessage();
    closeConfigureWidgetDiv();
};

manageRegistrants.resetSearchAndFilter = function() {
    $("#show_all")[0].selected = true;
    $("#search").val("");

    manageRegistrants.search();

    $("#show_all_div")[0].style.visibility = "hidden";
};

manageRegistrants.updatePending = undefined;
manageRegistrants.delayedSearch = function () {
    if (manageRegistrants.updatePending) {
        clearTimeout(manageRegistrants.updatePending);
    }

    manageRegistrants.updatePending = setTimeout(manageRegistrants.search, 500);
};

manageRegistrants.createManageRegistrantsRequest = function() {
    var sortProperties = tableWithSort.getSortProperties();
    return {
        siteId: $("#manageRegistrantsSiteId").val(),
        status: $("#filter > option:selected").val(),
        searchKey: $("#search").val(),
        sortType: sortProperties.sortFieldType,
        desc: sortProperties.descending,
        pageNumber : paginator.getPageNumber()
    };
};

manageRegistrants.search = function () {
    manageRegistrants.updatePending = false;
    manageRegistrants.updateRegistrantsTable("Applying new search parameters...");
};

manageRegistrants.sort = function () {
    manageRegistrants.updateRegistrantsTable("Data sorting, please wait ...");
};

manageRegistrants.updateRegistrantsTable = function (message) {
    message = message || "Updating data, please wait...";
    var tableContainer = $("#registrantsDiv")[0];

    if (!tableContainer) {
        return;
    }

    createLoadingArea({element:$("#registrantsDiv")[0], text: message, color:"green", guaranteeVisibility:true});
    var request = manageRegistrants.createManageRegistrantsRequest();
    new ServiceCall().executeViaDwr("ShowManageRegistrantsTableService", "execute", request, function (response) {
        $(tableContainer).html(response);

        removeLoadingArea();

        $("#show_all_div")[0].style.visibility = (request.searchKey != "" ? "visible" : "hidden");
    });
};

manageRegistrants.deleteVisitor = function(visitorId) {
    var siteId = $("#manageRegistrantsSiteId").val();

    var invited = $("#" + visitorId)[0].checked;
    var registered = $("#statusSpan" + visitorId).html() == "REGISTERED";
    if (confirm(invited && registered ? $("#delRegGuest").val() : registered ? $("#delRegVisitor").val() : $("#delUnregGuest").val())) {
        manageRegistrants.deleteSelectedVisitorsInternal([visitorId], siteId);
    }
};

manageRegistrants.deleteSelectedVisitors = function() {
    var checkedVisitorsId = groups.getCheckedVisitorsId();
    if (checkedVisitorsId.length == 0) {
        alert($("#selectVisitors").val());
        return;
    }
    if (checkedVisitorsId.length == 1) {
        manageRegistrants.deleteVisitor(checkedVisitorsId[0]);
    } else {
        var deleteVisitorsMessage = $("#deleteVisitors").val();
        deleteVisitorsMessage = deleteVisitorsMessage.replace("{0}", checkedVisitorsId.length);
        if (confirm(deleteVisitorsMessage)) {
            manageRegistrants.deleteSelectedVisitorsInternal(checkedVisitorsId, $("#manageRegistrantsSiteId").val());
        }
    }
};

manageRegistrants.deleteSelectedVisitorsInternal = function(visitorsId, siteId) {
    new ServiceCall().executeViaDwr("DeleteVisitorService", "execute", visitorsId, siteId, function() {
        $("#updateDiv").html($("#visitorDeleted").val());
        addSlidingTimeoutEvent($("#updateDiv")[0], 3000);
        for (var i in visitorsId) {
            manageRegistrants.removeManageRegistrantsRow(visitorsId[i]);
        }
    });
};

manageRegistrants.removeManageRegistrantsRow = function (visitorId) {
    var rowToRemove = $("#row" + visitorId);
    rowToRemove.parent()[0].removeChild(rowToRemove[0]);
};


manageRegistrants.updateManageRegistransForms = function (request) {
    $(".itemName" + request.formId).each(function() {
        this.innerHTML = request.formName;
    });
};

manageRegistrants.processKey = function(searchKey, e) {
    if (searchKey.length == 0) {
        manageRegistrants.search();
        return;
    }
    manageRegistrants.delayedSearch();
};

manageRegistrants.selectAllVisitors = function() {
    $(groups.getVisitorsCheckboxes()).each(function() {
        this.checked = true;
    });
};

manageRegistrants.deselectAllVisitors = function() {
    $(groups.getVisitorsCheckboxes()).each(function() {
        this.checked = false;
    });
};


manageRegistrants.selectColumn = function(column) {
    $(groups.getVisitorsCheckboxes()).each(function() {
        this.checked = false;
    });
};