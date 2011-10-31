function trackVisitor(pageId) {
    var trackVisitorRequest = new Object();
    trackVisitorRequest.os = navigator.platform;
    trackVisitorRequest.referrerUrl = document.referrer;
    trackVisitorRequest.browser = navigator.appName;
    trackVisitorRequest.screenResolution = screen.width + "x" + screen.height;
    new ServiceCall().executeViaDwr("TrackVisitorService", "track", trackVisitorRequest, pageId);
}

var pageOpen = new Date();

// This function is called when the person closes the window or navigate away.
function leavingPage(pageId) {
    var pageClose = new Date();
    var time = (pageClose.getTime() - pageOpen.getTime());
    new ServiceCall().executeViaDwr("TrackVisitorService", "updateTime", pageId, time);
}