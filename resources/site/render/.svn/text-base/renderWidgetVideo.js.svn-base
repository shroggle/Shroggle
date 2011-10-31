function showVideo(settings) {
    if (!settings.videoStatus) {
        $.ajax({
            url: "/video/checkFlvVideo.action",
            dataType: "json",
            cache: false,
            data: {flvVideoId: settings.videoFlvId},
            success: function (videoStatus) {
                showVideoInternal(videoStatus);
            }
        });
    } else {
        showVideoInternal(settings.videoStatus);
    }

    function showVideoInternal(videoStatus) {
        var videoUrl = document.getElementById(settings.videoUrlId).value;
        var imageUrl = document.getElementById(settings.imageUrlId).value;
        var videoPlayerId = settings.videoPlayerId;
        var videoPlayerDiv = document.getElementById(settings.videoPlayerDivId);
        videoPlayerDiv.innerHTML = "";
        settings.width = settings.width || 250;
        settings.height = settings.height || 20;
        videoPlayerDiv.style.width = settings.width + "px";
        videoPlayerDiv.style.height = settings.height + "px";
        var playerFlashObject = new SWFObject(
                "/video/mediaplayer.swf", videoPlayerId,
                settings.width, settings.height, "7");
        playerFlashObject.addParam("allowfullscreen", "true");
        playerFlashObject.addParam("wmode", "opaque");
        if (settings.width != "" && settings.height != "") {
            playerFlashObject.addVariable("width", settings.width);
            playerFlashObject.addVariable("height", settings.height);
        }
        playerFlashObject.addVariable("file", videoUrl);
        playerFlashObject.addVariable("streamer", videoUrl);
        playerFlashObject.addVariable("image", imageUrl);
        playerFlashObject.addVariable("type", "flv");
        if (playerFlashObject.write(settings.videoPlayerDivId)) {
            if (settings.galleryId && settings.filledFormId) {
                addVideoPlayerListeners({
                    videoPlayerId: videoPlayerId,
                    galleryId: settings.galleryId,
                    filledFormId: settings.filledFormId
                });
            }
        }
        if (settings.centeredElementId) {
            centerElement({elementToCenter:$("#" + settings.centeredElementId)[0]});
        }
        if (videoStatus == "converting") {
            var hrefId = "refreshVideoStatus" + (new Date().getTime());
            showErrorMessage("This video is converting now. Please, come back in several minutes or <a href='#' id='"
                    + hrefId + "'>click</a> for refresh status.", 20);
            $("#" + hrefId).click(function () {
                delete settings.videoStatus;
                showVideo(settings);
                return false;
            });
        } else if (videoStatus == "notfound") {
            showErrorMessage("No film available", 20);
        }

        function showErrorMessage(message, fontSize) {
            var table = getParentWindow().document.createElement("table");
            var tr = getParentWindow().document.createElement("tr");
            var td = getParentWindow().document.createElement("td");
            $(tr).append(td);
            $(table).append(tr);
            table.style.width = settings.width + "px";
            table.style.height = settings.height + "px";
            table.style.position = "relative";
            table.style.top = "-" + settings.height + "px";
            table.style.left = "0";
            td.style.width = "100%";
            td.style.verticalAlign = "middle";
            td.style.textAlign = "center";
            td.style.backgroundImage = "url(../../images/transparentBackground.gif)";
            td.style.backgroundRepeat = "repeat";

            var videoPlayerDiv = document.getElementById(settings.videoPlayerDivId);
            var overlapingDiv = getParentWindow().document.createElement("div");
            overlapingDiv.style.fontWeight = "bold";
            overlapingDiv.style.fontSize = fontSize + "px";
            overlapingDiv.style.color = "red";
            overlapingDiv.innerHTML = message;

            $(td).append(overlapingDiv);
            $(videoPlayerDiv).append(table);
            bringToFront(table);
            bringToFront(overlapingDiv);
        }
    }
}
function createVideo(settings) {
    if (settings.smallVideoPlayerId) {
        try {
            var smallPlayerId = settings.smallVideoPlayerId + "Player";
            var smallVideoPlayer = document.getElementById(smallPlayerId);
            smallVideoPlayer.sendEvent("PLAY", "false");
        } catch(ex) {
        }
    }
    var VIDEO_PLAYER_HOLDER_ID = "videoPlayerHolder";
    removeVideo(VIDEO_PLAYER_HOLDER_ID);
    var videoPlayerHolder = document.createElement("div");
    var videoPlayer = document.createElement("div");
    var closeButton = document.createElement("input");

    videoPlayerHolder.id = VIDEO_PLAYER_HOLDER_ID;
    videoPlayerHolder.align = "center";
    videoPlayerHolder.style.backgroundColor = "#eee";
    videoPlayerHolder.style.padding = "10px";
    videoPlayerHolder.style.border = "1px solid black";
    videoPlayerHolder.style.position = "absolute";
    videoPlayer.id = settings.videoPlayerDivId;
    $(getParentWindow().document.body).append(videoPlayerHolder);
    bringToFront(videoPlayerHolder);
    videoPlayerHolder.style.zIndex += 20;

    closeButton.type = "button";
    closeButton.style.marginTop = "10px";
    closeButton.value = "Close";
    closeButton.onclick = function () {
        return removeVideo(VIDEO_PLAYER_HOLDER_ID);
    };
    $(videoPlayerHolder).append(videoPlayer);
    $(videoPlayerHolder).append(closeButton);
    bringToFront(closeButton);
    closeButton.style.zIndex += 20;
    //settings.shouldBeCentered = true;
    settings.centeredElementId = videoPlayerHolder.id;

    showVideo(settings);
}

function removeVideo(id) {
    if (document.getElementById(id)) {
        getParentWindow().document.body.removeChild(document.getElementById(id));
    }
    return true;
}
