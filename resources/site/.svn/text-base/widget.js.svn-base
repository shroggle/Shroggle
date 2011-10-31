/**
 * @param pageId
 * @param widgetPosition
 * @param widgetSequince
 */
function showWidget(widgetId) {
    new ServiceCall().executeViaDwr("ShowWidgetService", "execute", widgetId, function (response) {
        document.getElementById("widget" + widgetId).innerHTML = response;
    });
}

function setImageSrc(imageId, src) {
    document.getElementById(imageId).src = src;
}