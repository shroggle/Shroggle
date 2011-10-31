function updateVisitorsStatistics() {
    var statisticsVisitorsTable = $("#statisticsVisitorsTable")[0];
    var timePeriods = $("#timePeriodSelect")[0];
    if (timePeriods.selectedIndex < 0) return;

    var timePeriod = timePeriods.options[timePeriods.selectedIndex].value;

    createLoadingArea({element:statisticsVisitorsTable, text: "Updating data, please wait ...", color:"green", guaranteeVisibility:true});
    var serviceCall = new ServiceCall();

    var sortProperties = tableWithSort.getSortProperties();
    serviceCall.executeViaDwr("TrafficVisitorsService", "sort", $("#pageId").val(), sortProperties.sortFieldType, sortProperties.descending, timePeriod, paginator.getPageNumber(), function(response) {
        if (response) {
            $("#visitorsTableDiv").html(response);
            removeLoadingArea();
        }
    });
}

function updateSiteStatistics() {
    var statisticsSiteTable = $("#statisticsSiteTable")[0];
    createLoadingArea({element:statisticsSiteTable, text: "Updating data, please wait ...", color:"green", guaranteeVisibility:true});

    var serviceCall = new ServiceCall();

    var sortProperties = tableWithSort.getSortProperties();
    serviceCall.executeViaDwr("TrafficSitesService", "sort", sortProperties.sortFieldType, sortProperties.descending, paginator.getPageNumber(), function(response) {
        if (response) {
            $("#sitesTableDiv").html(response);
            removeLoadingArea();
        }
    });
}

function updatePageStatistics() {
    var statisticsPageTable = $("#statisticsPageTable")[0];
    var timePeriods = $("#timePeriodSelect")[0];
    if (timePeriods.selectedIndex < 0) return;

    var timePeriod = timePeriods.options[timePeriods.selectedIndex].value;

    createLoadingArea({element:statisticsPageTable, text: "Updating data, please wait ...", color:"green", guaranteeVisibility:true});
    var serviceCall = new ServiceCall();

    var sortProperties = tableWithSort.getSortProperties();
    serviceCall.executeViaDwr("TrafficPagesService", "sort", $("#siteId").val(), sortProperties.sortFieldType, sortProperties.descending, timePeriod, paginator.getPageNumber(), function (response) {
        if (response) {
            $("#pagesTableDiv").html(response);
            removeLoadingArea();
        }
    });
}

function showAllRefUrlsForSite(siteId) {
    var allRefUrlsForSiteWindow = createConfigureWindow({width:725, height:250});

    new ServiceCall().executeViaDwr("ShowAllRefUrlSearchTermsService", "executeRefUrlsForSite", siteId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        allRefUrlsForSiteWindow.setContent(response);
    });
}

function showAllRefUrlsForPage(pageId) {
    var allRefUrlsForSiteWindow = createConfigureWindow({width:725, height:250});

    new ServiceCall().executeViaDwr("ShowAllRefUrlSearchTermsService", "executeRefUrlsForPage", pageId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        allRefUrlsForSiteWindow.setContent(response);
    });
}

function showAllSearchTermsForPage(pageId) {
    var allRefUrlsForSiteWindow = createConfigureWindow({width:725, height:250});

    new ServiceCall().executeViaDwr("ShowAllRefUrlSearchTermsService", "executeSearchTermsForPage", pageId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        allRefUrlsForSiteWindow.setContent(response);
    });
}

function showAllSearchTermsForVisitor(pageVisitorId, pageId) {
    var allRefUrlsForSiteWindow = createConfigureWindow({width:725, height:250});

    new ServiceCall().executeViaDwr("ShowAllRefUrlSearchTermsService", "executeSearchTermsForVisitor", pageVisitorId, pageId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        allRefUrlsForSiteWindow.setContent(response);
    });
}

function showAllRefUrlsForVisitor(pageVisitorId, pageId) {
    var allRefUrlsForSiteWindow = createConfigureWindow({width:725, height:250});

    new ServiceCall().executeViaDwr("ShowAllRefUrlSearchTermsService", "executeRefUrlsForVisitor", pageVisitorId, pageId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        allRefUrlsForSiteWindow.setContent(response);
    });
}

