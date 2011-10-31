function showFormFileUploadMoreInfoText(innerHTMLId) {
    var innerHTML = document.getElementById(innerHTMLId).innerHTML;
    var formFileUploadMoreInfoText = createConfigureWindow({width:500, height:150});
    formFileUploadMoreInfoText.setContent(innerHTML);
}

function showFormFileUploadEstimatedTimesText(innerHTMLId) {
    var innerHTML = document.getElementById(innerHTMLId).innerHTML;
    var formFileUploadEstimatedTimesText = createConfigureWindow({width:600, height:250});
    formFileUploadEstimatedTimesText.setContent(innerHTML);
}

function isVideoOrVideoImageField(id) {
    return (isVideoField(id) || isVideoImageField(id));
}

function isVideoField(id) {
    return document.getElementById(id + "IsVideoField");
}

function isVideoImageField(id) {
    return document.getElementById(id + "IsVideoImageField");
}