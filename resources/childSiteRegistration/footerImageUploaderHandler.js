function footerImageQueued(file) {
    startFooterImageUploading($('#childSiteRegSiteId').val());
}

function footerImageUploadStart(file) {
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

function footerImageUploadSuccess(file, serverData) {
    swfUploaderHandleUploadSuccess(file, serverData, this);
    showFooterImageUploader();
    var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
    setFooterImageSrc(uploadedFileInfo.resourceUrl);
    setFooterImageId(uploadedFileInfo.resourceId);
    swfUploaderRemoveBackgroundLoadingMessage();
}

function footerImageUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showFooterImageUploader();
}