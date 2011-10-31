function showEditCSRHelpWindow(textId, height) {
    var editCSRHelpWindow = createConfigureWindow({
        width: 400,
        height: 200
    });
    document.getElementById("editCSRHelpText").innerHTML = document.getElementById(textId).value;
    document.getElementById("innerTextDiv").style.height = height + "px;";
    editCSRHelpWindow.setContent($("#editCSRWhatsThis")[0].innerHTML);
}

function sendVisitorPassword(visitorId) {
    new ServiceCall().executeViaDwr("EmailVisitorPasswordService", "emailForChildSiteVisitor", visitorId, function(data) {
        if (data) {
            document.getElementById("passwordSentText").style.visibility = "visible";
        }
    });
}
