var SUPPORTED_IMAGE_FORMATS = "*.gif;*.jpeg;*.jpg;*.png;*.tif;*.bmp";

var uploaderIds = {
    FOOTER_IMAGE_UPLOADER_ID : "footerImageUploader",
    LOGO_UPLOADER_ID         : "logoUploader",
    SCREEN_SHOT_UPLOADER_ID  : "screenShotUploader",
    SITE_ICON_UPLOADER_ID    : "siteIconUploader",
    BACKGROUND_UPLOADER_ID   : "backgroundUploader",
    BULK_UPLOADER_ID         : "bulkUploader",
    IMAGE_UPLOADER_ID        : "imageUploader",
    IMAGE_FILE_UPLOADER_ID   : "imageFileUploader",
    VIDEO_IMAGE_UPLOADER_ID  : "videoImageUploader",
    VIDEO_UPLOADER_ID        : "videoUploader",
    MENU_IMAGE_UPLOADER_ID   : "menuImageUploader"
};


function putUploader(uploader) {
    if (!uploader) {
        return;
    }
    removeUploaders(uploader.id);
    var window = getUploadersWindow();
    if (!window.uploaders) {
        window.uploaders = new Array();
    }
    window.uploaders.push(uploader);
}

function getUploaderById(id) {
    var window = getUploadersWindow();
    if (window && window.uploaders) {
        for (var i in window.uploaders) {
            if (window.uploaders[i].id == id) {
                return window.uploaders[i];
            }
        }
    }
    return null;
}

function removeUploaders(uploaderId) {
    var window = getUploadersWindow();
    if (!uploaderId || !window || !window.uploaders || window.uploaders.length == 0) {
        return;
    }
    var notLastElement = true, currentIndex = 0;
    do{
        if (window.uploaders[currentIndex].id == uploaderId) {
            var removedUploader = window.uploaders.splice(currentIndex, 1)[0];
            try {
                removedUploader.destroy();
            } catch(ex) {
            }
            try {
                removedUploader.clearSessionKeeper();
            } catch(ex) {
            }
        } else {
            currentIndex++;
        }
        notLastElement = currentIndex != window.uploaders.length;
    } while (notLastElement);
}

function getUploadersWindow() {
    var window = getActiveWindow();
    if (window) {
        return window;
    } else {
        return getParentWindow();
    }
}