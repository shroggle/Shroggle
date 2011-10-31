function showGalleryDataRequestHtml(galleryId, filledFormId, widgetId, siteShowOption, crossWidgetId) {
    new ServiceCall().executeViaDwr("ShowGalleryDataService", "execute", galleryId, filledFormId, widgetId, siteShowOption, function (response) {
        showGalleryData(galleryId, filledFormId, widgetId, siteShowOption, crossWidgetId, response.html);
    });
}

function showGalleryData(galleryId, filledFormId, widgetId, siteShowOption, crossWidgetId, html) {
    window.pageParameters.value("gallerySelectedFilledFormId", filledFormId);
    window.pageParameters.value("gallerySelectedCrossWidgetId", crossWidgetId);
    window.pageParameters.value("selectedGalleryId", galleryId);
    if ($("#galleryData" + widgetId).length > 0) {
        $("#galleryData" + widgetId)[0].parentNode.innerHTML = html;
        createVotingStars(widgetId + "" + filledFormId + "" + galleryId);
    }
}

function hideGalleryWatchedVideos(widgetId, galleryId, siteShowOption, hide) {
    new ServiceCall().executeViaDwr("HideGalleryWatchedVideosService", "execute", {
        widgetId: widgetId,
        galleryId: galleryId,
        siteShowOption: siteShowOption,
        hide: hide
    }, function (response) {
        document.getElementById("galleryNavigation" + widgetId).innerHTML = "";
        document.getElementById("galleryNavigation" + widgetId).innerHTML = response.navigationHtml;
    });
}

function showGalleryNavigation(widgetId, galleryId, pageId, siteShowOption) {
    new ServiceCall().executeViaDwr("ShowGalleryNavigationService", "execute", widgetId, galleryId, pageId, siteShowOption, function (html) {
        document.getElementById("galleryNavigation" + widgetId).innerHTML = "";
        document.getElementById("galleryNavigation" + widgetId).innerHTML = html;
    });
}

function galleryNavigationSelectChanged(select) {
    var values = select[select.selectedIndex].value.split(";");
    showGalleryNavigation(values[0], values[1], values[2], values[3]);
}

// Showing message about registration completion on redirect from paypal.
$(function () {
    if (document.location.hash.indexOf('#showPaypalButtonPaymentSuccess') != -1) {
        var hash = document.location.hash;
        var widgetId = hash.substring('#showPaypalButtonPaymentSuccess'.length, hash.length);

        $("#showPaypalButtonPaymentSuccess" + widgetId).show();

        document.location.hash = '';
    }
});
