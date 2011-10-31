function getTitlePosition() {
    if (document.getElementById("displayLabel").checked) {
        if (document.getElementById("displayLabelBelow").checked) {
            return "BELOW";
        } else {
            return "ABOVE";
        }
    } else {
        return "NONE";
    }
}

function getPdfDisplaySettings() {
    if (document.getElementById("OPEN_IN_NEW_WINDOW_PDF").checked) {
        return "OPEN_IN_NEW_WINDOW";
    } else if (document.getElementById("OPEN_IN_SAME_WINDOW_PDF").checked) {
        return "OPEN_IN_SAME_WINDOW";
    } else {
        return "PROMPT_DOWNLOAD";
    }
}

function getAudioDisplaySettings() {
    if (document.getElementById("PLAY_IN_CURRENT_WINDOW").checked) {
        return "PLAY_IN_CURRENT_WINDOW";
    } else {
        return "PLAY_IN_SMALL_WINDOW";
    }
}

function getImageFlashDisplaySettings() {
    if (document.getElementById("OPEN_IN_NEW_WINDOW_IMAGE_FLASH").checked) {
        return "OPEN_IN_NEW_WINDOW";
    } else if (document.getElementById("OPEN_IN_SAME_WINDOW_IMAGE_FLASH").checked) {
        return "OPEN_IN_SAME_WINDOW";
    } else if (document.getElementById("OPEN_IN_SMALL_WINDOW_IMAGE_FLASH").checked) {
        return "OPEN_IN_SMALL_WINDOW";
    } else {
        return "PROMPT_DOWNLOAD";
    }
}


function getExternalUrlDisplaySettings() {
    if (document.getElementById("OPEN_IN_NEW_WINDOW_EXTERNAL_URL").checked) {
        return "OPEN_IN_NEW_WINDOW";
    } else {
        return "OPEN_IN_SAME_WINDOW";
    }
}

function getInternalPageDisplaySettings() {
    if (document.getElementById("OPEN_IN_NEW_WINDOW_INTERNAL_URL").checked) {
        return "OPEN_IN_NEW_WINDOW";
    } else {
        return "OPEN_IN_SAME_WINDOW";
    }
}

function getTextAreaDisplaySettings() {
    if (document.getElementById("OPEN_IN_NEW_WINDOW_TEXT_AREA").checked) {
        return "OPEN_IN_NEW_WINDOW";
    } else if (document.getElementById("OPEN_IN_SAME_WINDOW_TEXT_AREA").checked) {
        return "OPEN_IN_SAME_WINDOW";
    } else {
        return "OPEN_IN_SMALL_WINDOW";
    }
}

function getWidthHeght() {
    var response = new Object();
    var linkType = document.getElementById("imageLinkType").value;
    response.wrongWidth = false;
    response.wrongHeght = false;
    if (linkType == "TEXT_AREA") {
        response.checkbox = document.getElementById("customizeTextWindowSize");
        response.width = document.getElementById("customizeTextWindowWidth").value;
        response.heght = document.getElementById("customizeTextWindowHeight").value;
        if (document.getElementById("TEXT_AREADiv").style.display != "none" && response.checkbox.checked && !response.checkbox.disabled) {
            if (!response.width || response.width == 0 || response.width == "") {
                response.wrongWidth = true;
                response.wrongWidthElement = document.getElementById("customizeTextWindowWidth");
            }
            if (!response.heght || response.heght == 0 || response.heght == "") {
                response.wrongHeight = true;
                response.wrongHeightElement = document.getElementById("customizeTextWindowHeight");
            }
        }
    } else {
        response.checkbox = document.getElementById("customizeWindowSize");
        response.width = document.getElementById("customizeWindowWidth").value;
        response.heght = document.getElementById("customizeWindowHeight").value;
        if (document.getElementById("imageFlashRadioDiv").style.display != "none" && response.checkbox.checked && !response.checkbox.disabled) {
            if (!response.width || response.width == 0 || response.width == "") {
                response.wrongWidth = true;
                response.wrongWidthElement = document.getElementById("customizeWindowWidth");
            }
            if (!response.heght || response.heght == 0 || response.heght == "") {
                response.wrongHeight = true;
                response.wrongHeightElement = document.getElementById("customizeWindowHeight");
            }
        }
    }
    return response;
}


function isLinkTargetSelected() {
    var response = new Object();
    response.linkTargetSelected = false;
    switch (document.getElementById("imageLinkType").value) {
        case "EXTERNAL_URL":{
            response.linkTargetSelected = document.getElementById('externalUrl').value && document.getElementById('externalUrl').value != "";
            response.errorElement = document.getElementById('externalUrl');
            break;
        }
        case "INTERNAL_URL":{
            response.linkTargetSelected = true;
            break;
        }
        case "MEDIA_FILE":{
            response.linkTargetSelected = document.getElementById('imageFileSelect').value > 0;
            response.errorElement = document.getElementById('imageFileSelect');
            break;
        }
        case "TEXT_AREA":{
            response.linkTargetSelected = getEditorContent("imageTextEditor") != "";
            response.errorElement = document.getElementById('imageTextEditor');
            break;
        }
    }
    return response;
}


