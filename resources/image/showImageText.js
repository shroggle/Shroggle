var PLAYING = 0;
var PAUSED = 1;
function showWidgetImageTextInNewWindow(url) {
    window.open(url);
}

function showWidgetImageTextInSameWindow(url) {
    window.location = url;
}

function showWidgetImageTextInSmallWindow(widgetImageId, width, height, siteShowOption) {
    new ServiceCall().executeViaDwr("ShowImageFilesService", "showImageText", widgetImageId, width, height, siteShowOption, function (text) {
        showWidgetImageTextInSmallWindowInternal(text, width, height, 0, 0);
    });
}

function showWidgetImageInSmallWindow(imageId, windowWidth, windowHeight, itemWidth, itemHeight, showFlash, siteShowOption) {
    new ServiceCall().executeViaDwr("ShowImageFilesService", "getImageFileUrl", imageId, windowWidth, windowHeight, itemWidth, itemHeight, siteShowOption, function (text) {
        showWidgetImageTextInSmallWindowInternal(text, windowWidth, windowHeight, showFlash);
    });
}


function showWidgetImageTextInSmallWindowInternal(text, windowWidth, windowHeight, showFlash) {
    removeWidgetImageTextInSmallWindow();
    var message = window.parent.document.createElement("div");
    message.innerHTML = text;
    message.id = "widgetImageTextDiv";
    message.style.position = "absolute";
    message.style.zIndex = "100";
    message.style.backgroundColor = "white";
    message.style.borderStyle = "solid";
    message.style.overflow = "hidden";
    message.style.padding = "5px";
    message.style.borderWidth = "1px";
    if (windowWidth > 0) {
        message.style.width = windowWidth + "px";
    }
    if (windowHeight > 0) {
        if (showFlash) {
            message.style.height = (windowHeight + 30) + "px";
        } else {
            // Add space for close button
            message.style.height = (windowHeight + 25) + "px";
        }
    }
    message.align = "center";
    window.parent.document.body.appendChild(message);
    centerElement({elementToCenter:message});
}

function removeWidgetImageTextInSmallWindow() {
    var messageDiv = document.getElementById("widgetImageTextDiv");
    if (messageDiv) {
        window.parent.document.body.removeChild(messageDiv);
    }
}

var mp3Player;
var mp3PlayerState;
function playMusic(widgetImageId, playerId, playerWidth, playerHeight, siteShowOption) {
    if (!mp3Player || !mp3Player.id || mp3Player.id != widgetImageId) {
        showPlayerInternal(widgetImageId, playerId, playerWidth, playerHeight, "/images/stop.gif", siteShowOption);
    } else if (mp3PlayerState == PLAYING) {
        mp3PlayerState = PAUSED;
        mp3Player.sendEvent("PLAY", "false");
        document.getElementById("audioPlayStopImage" + widgetImageId).src = "/images/play.gif";
    } else if (mp3PlayerState == PAUSED) {
        mp3PlayerState = PLAYING;
        mp3Player.sendEvent("PLAY", "true");
        document.getElementById("audioPlayStopImage" + widgetImageId).src = "/images/stop.gif";
    }
}

function showPlayerInternal(imageId, playerId, playerWidth, playerHeight, smallImageUrl, siteShowOption) {
    document.getElementById("audioPlayStopImage" + imageId).src = "/images/ajax-loader.gif";
    new ServiceCall().executeViaDwr("ShowImageFilesService", "getAudioUrl", imageId, siteShowOption, function (url) {
        var player = new SWFObject('/video/mediaplayer.swf', 'mp3Player', playerWidth, playerHeight, "9");
        player.addParam("allowfullscreen", "false");
        player.addParam("wmode", "transparent");
        player.addVariable("autostart", "true");
        player.addVariable("mute", "false");
        player.addVariable("volume", "100");
        player.addVariable("file", url);
        player.addVariable("repeat", "false");
        player.addVariable("showdownload", "false");
        player.addVariable("showdigits", "false");
        player.addVariable("shownavigation", "true");
        player.addVariable("type", "mp3");
        player.write(playerId + imageId);
        mp3PlayerState = PLAYING;
        mp3Player = document.getElementById("mp3Player");
        mp3Player.id = imageId;
        document.getElementById("audioPlayStopImage" + imageId).src = smallImageUrl;
    });
}

function showSmallWindowForAudio(imageId, playerWidth, playerHeight, siteShowOption) {
    var playerId = "mp3playerSmallWindow";
    removeSmallWindowForAudio(imageId);
    var button = window.parent.document.createElement("input");
    button.type = "button";
    button.align = "center";
    button.value = "Close";
    button.onclick = function () {
        removeSmallWindowForAudio(imageId);
    };
    var message = window.parent.document.createElement("div");
    message.innerHTML = "<div id='" + playerId + imageId + "'></div>";
    message.id = "widgetImageAudioDiv";
    message.style.position = "absolute";
    message.style.zIndex = "100";
    message.style.backgroundColor = "white";
    message.style.borderStyle = "solid";
    message.style.borderWidth = "1px";
    message.style.width = 270 + "px";
    message.style.height = 40 + "px";
    message.align = "center";
    message.appendChild(button);
    window.parent.document.body.appendChild(message);
    centerElement({elementToCenter:message});
    showPlayerInternal(imageId, playerId, playerWidth, playerHeight, "/images/play.gif", siteShowOption);
}

function removeSmallWindowForAudio(widgetImageId) {
    var audioPlayStopImage = document.getElementById("audioPlayStopImage" + widgetImageId);
    if (audioPlayStopImage) {
        audioPlayStopImage.src = "/images/play.gif";
    }
    var messageDiv = document.getElementById("widgetImageAudioDiv");
    if (messageDiv) {
        window.parent.document.body.removeChild(messageDiv);
    }
}


function showPlayIconForImage(widgetId, parentElement, hrefId) {
    parentElement.onload = function() {
        return false;
    };
    var a = window.parent.document.createElement("a");
    a.className = "imageLink";
    a.style.border = "none";
    a.href = document.getElementById(hrefId).innerHTML.replaceAll("&amp;", "&");

    var playStopImg = window.parent.document.createElement("img");
    playStopImg.id = "audioPlayStopImage" + widgetId;
    playStopImg.src = "/images/play.gif";
    playStopImg.style.position = "absolute";
    playStopImg.style.width = PLAY_STOP_IMAGE_WIDTH + "px";
    playStopImg.style.height = PLAY_STOP_IMAGE_HEIGHT + "px";
    var position = findPosAbs(parentElement);
    var top = ((parentElement.offsetHeight / 2) - (PLAY_STOP_IMAGE_HEIGHT / 2));
    var left = ((parentElement.offsetWidth / 2) - (PLAY_STOP_IMAGE_WIDTH / 2));
    playStopImg.style.top = (position.top + top) + "px";
    playStopImg.style.left = (position.left + left) + "px";
    playStopImg.style.border = "none";
    a.appendChild(playStopImg);
    window.parent.document.body.appendChild(a);
}


var PLAY_STOP_IMAGE_WIDTH = 70;
var PLAY_STOP_IMAGE_HEIGHT = 70;