function siteIconQueued(file) {
    startSiteIconUploading();
}

function siteIconUploadStart(file) {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgresDiv = window.parent.document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "65px";
            loadingMessage.appendChild(fileProgresDiv);
            centerElement({elementToCenter:loadingMessage});
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.showCancelButton(this, true);
        }
    }
    catch (ex) {
    }
    return true;
}

function siteIconUploadSuccess(file, serverData) {
    swfUploaderHandleUploadSuccess(file, serverData, this);
    var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
    setSiteIconSrc(uploadedFileInfo.resourceUrl);
    setSiteIconId(uploadedFileInfo.resourceId);
    setIconExists(true);
    showSiteIconUploader();
    swfUploaderRemoveBackgroundLoadingMessage();
}

function siteIconUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showSiteIconUploader();
}

function setSiteIconSrc(src) {
    $("#siteIconImage")[0].src = src;
}

function setSiteIconId(id) {
    $("#siteIconId").val(id);
    $("#iconId").val(id);
}

function getSiteIconId() {
    return $("#siteIconId").val();
}

function setIconExists(value) {
    $("#isIconExist").val(value);
}

function isIconExists() {
    return $("#isIconExist").val() == "true";
}

function removeIcon(width, height, defaultIconUrl) {
    if (!getSiteIconId()) {
        return;
    }

    if (confirm("You are about to remove your site custom favicon picture. Click OK to proceed.")) {

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("RemoveIconService", "remove", getSiteIconId(), function() {
            setSiteIconSrc(defaultIconUrl);
            $("#siteIconImage").width(width);
            $("#siteIconImage").height(height);
            setIconExists(false);
            showSiteIconUploader();
        });
    }
}