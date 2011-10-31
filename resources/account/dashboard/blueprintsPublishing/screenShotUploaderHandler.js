var screenShotUploaderHandler = {

    screenShotQueued : function (file) {
        screenShotUploader.startUploading(screenShotUploaderHandler.getSelectedPageId());
    },

    screenShotUploadStart : function (file) {
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
    },

    screenShotUploadSuccess : function (file, serverData) {
        swfUploaderHandleUploadSuccess(file, serverData, this);
        screenShotUploader.showUploader(screenShotUploaderHandler.getSelectedPageId());
        var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
        screenShotUploaderHandler.setScreenShotSrc(uploadedFileInfo.resourceUrl);
        screenShotUploaderHandler.setScreenShotId(uploadedFileInfo.resourceId);
        swfUploaderRemoveBackgroundLoadingMessage();
    },

    screenShotUploadError : function (file, errorCode, message) {
        swfUploaderHandleUploadError(file, errorCode, message, this);
        screenShotUploader.showUploader(screenShotUploaderHandler.getSelectedPageId());
    },

    setScreenShotSrc : function (src) {
        $("#screenShot" + screenShotUploaderHandler.getSelectedPageId())[0].src = src;
    },

    setScreenShotId : function (id) {
        $("#selectedScreenShotId" + screenShotUploaderHandler.getSelectedPageId()).val(id);
    },

    getSelectedPageId : function() {
        return $('#selectedPageId').val();
    }
};