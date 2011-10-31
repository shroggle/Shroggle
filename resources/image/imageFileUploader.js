function showImageFileUploader(siteId, imageFileType) {
    document.getElementById("imageFileButtonContainer").style.visibility = "visible";
    removeUploaders(uploaderIds.IMAGE_UPLOADER_ID);
    try {
        document.getElementById("imageFileButtonContainer").innerHTML = "<span id='imageFileButtonPlaceHolder'></span>";
    } catch(exception) {
    }

    var fileTypes = createFileTypesByUploaderType(imageFileType);
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : fileTypes.file_types,
        file_types_description : fileTypes.file_types_description,
        file_upload_limit : 1,
        file_queue_limit : 0,
        file_size_limit : fileTypes.fileMaxSize,
        custom_settings : {
            progressTarget : "fileProgressDivId",
            site_id : siteId,
            image_file_type : imageFileType
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: "imageFileButtonPlaceHolder",


        file_queued_handler : imageFileQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : imageFileUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : imageFileUploadError,
        upload_success_handler : imageFileUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };

    var uploader = new SWFUpload(settings);
    uploader.afterUploadFunction = updateImageFileSelect;
    uploader.siteId = siteId;
    uploader.imageFileType = imageFileType;
    uploader.swfUploaderUrl = "/uploadImageFiles.action";
    uploader.id = uploaderIds.IMAGE_UPLOADER_ID;
    putUploader(uploader);

    function createFileTypesByUploaderType(uploaderType) {
        var object = new Object();
        object.fileMaxSize = "1Gb";
        if (uploaderType) {
            switch (uploaderType) {
                case "PDF" : {
                    object.file_types_description = "PDF Files";
                    object.file_types = "*.pdf;";
                    return object;
                }
                case "AUDIO" : {
                    object.file_types_description = "Audio Files";
                    object.file_types = "*.mp3;";
                    return object;
                }
                case "FLASH" : {
                    object.file_types_description = "Flash Files";
                    object.file_types = "*.swf;";
                    return object;
                }
                case "IMAGE" : {
                    object.file_types_description = "Image Files";
                    object.file_types = "*.gif;*.jpeg;*.jpg;*.png;*.tif;*.bmp;";
                    object.fileMaxSize = "10Mb";
                    return object;
                }
                case "CAD" : {
                    object.file_types_description = "CAD Files";
                    object.file_types = "*.DXF;*.DWG;*.obj;*.max;*.Skp;";
                    return object;
                }
                default:{
                    object.file_types_description = "PDF Files";
                    object.file_types = "*.pdf;";
                    return object;
                }
            }
        } else {
            object.file_types_description = "PDF Files";
            object.file_types = "*.pdf;";
            return object;
        }
    }
}

function startImageFileUploading(siteId) {
    configureImage.errors.clear();
    var uploader = getUploaderById(uploaderIds.IMAGE_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId + "&imageFileType=" + uploader.imageFileType);
            uploader.startUpload();
        }
    }
}

function updateImageFileSelect() {
    var imageFileType = window.parent.document.getElementById("imageFileType").value;
    var widgetId = getNullOrValue(window.parent.document.getElementById("widgetId").value);
    var imageItemId = window.parent.document.getElementById("selectedImageItemId").value;

    new ServiceCall().executeViaDwr("ConfigureImageService", "createImageFileList", widgetId, imageItemId, imageFileType, function (response) {
        window.parent.document.getElementById("uploadedImageFilesSelect").innerHTML = response;
        swfUploaderRemoveBackgroundLoadingMessage();
    });
}

function updateImages() {
    window.images.select.refresh("#uploadedImages");
    swfUploaderRemoveBackgroundLoadingMessage();
}

function updateSelectedImage(imageId) {
    if (imageId && imageId != "undefined") {
        var imageWidth = document.getElementById("imageWidth" + imageId).value;
        var imageHeight = document.getElementById("imageHeight" + imageId).value;
        var imageThumbnailUrl = document.getElementById("imageThumbnailUrl" + imageId).value;
        if (imageWidth && imageHeight && imageThumbnailUrl) {
            selectImage(imageId, imageWidth, imageHeight, imageThumbnailUrl);
        }
    }
}

function setImageSize() {
    var selectedImageId = document.getElementById("selectedImageId").value;
    if (configureImage.isPrimaryImageTabSelected() && selectedImageId) {
        var imageWidth = document.getElementById("imageWidth" + selectedImageId).value;
        var imageHeight = document.getElementById("imageHeight" + selectedImageId).value;
        if (imageWidth && imageHeight) {
            document.getElementById("selectedImageWidth").value = imageWidth;
            document.getElementById("selectedImageHeight").value = imageHeight;
            document.getElementById("widgetImageWidth").value = imageWidth;
            document.getElementById("widgetImageHeight").value = imageHeight;
        }
    }
}

function startImageUploading(siteId) {
    configureImage.errors.clear();
    var uploader = getUploaderById(uploaderIds.IMAGE_FILE_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
            uploader.startUpload();
        }
    }
}