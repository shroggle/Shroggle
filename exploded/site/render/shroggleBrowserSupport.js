$(function () {
    var browserVersion = css_browser_selector(navigator.userAgent);

    if (!isSupportedShroggleBrowser(browserVersion)) {
        var warningDiv = document.createElement("div");
        warningDiv.innerHTML = "The site is not loading correctly because your <a href='http://www.whatbrowser.org'" +
                               " target='_blank'>browser</a> version is no longer supported. Please update your browser." +
                               " <br> " +
                               " We suggest you to use one of these browsers:" +
                               " <br>" +
                               " <ul>" +
                               " <li>" +
                               " <img src='/images/chrome.gif'/>" +
                               " <a class='shroggleBrowserLink' href='http://www.google.com/chrome/index.html?brand=CHOF&utm_campaign=en&utm_source=en-et-whatbrowser&utm_medium=et' target='_blank'>Google Chrome</a>" +
                               " </li>" +
                               " <li>" +
                               " <img src='/images/ff.gif'/>" +
                               " <a class='shroggleBrowserLink' href='http://www.mozilla.com/en-US/firefox/firefox.html' target='_blank'>Firefox</a>" +
                               " </li>" +
                               " <li>" +
                               " <img src='/images/ie.gif'/>" +
                               " <a class='shroggleBrowserLink' href='http://www.microsoft.com/windows/internet-explorer/default.aspx' target='_blank'>Internet Explorer 8</a>" +
                               " </li>" +
                               " <li>" +
                               " <img src='/images/safari.gif'/>" +
                               " <a class='shroggleBrowserLink' href='http://www.apple.com/safari/' target='_blank'>Safari</a>" +
                               " </li>" +
                               " </ul>";
        warningDiv.className = "notSupportedBrowserDiv";
        $(document.body).prepend(warningDiv);
    }
});

function isSupportedShroggleBrowser(browserVersion) {
    var result = false;
    if (browserVersion.indexOf("ie6") != -1
            || browserVersion.indexOf("ie7") != -1
            || browserVersion.indexOf("ie8") != -1
            || browserVersion.indexOf("ie9") != -1
            || browserVersion.indexOf("chrome") != -1
            || browserVersion.indexOf("safari4") != -1
            || browserVersion.indexOf("safari5") != -1
            || browserVersion.indexOf("ff3") != -1
            || browserVersion.indexOf("ff3_5") != -1
            || browserVersion.indexOf("ff4") != -1) {
        result = true;
    }

    return result;
}