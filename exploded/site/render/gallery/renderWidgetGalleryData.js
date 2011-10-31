function showSourceSize(thumbnail, id, thumbnailWidth, thumbnailHeight) {
    if (thumbnail.clicked) {
        return;
    }
    thumbnail.clicked = true;
    var sourceImage = $("#" + $(thumbnail).attr("sourceImageId"));
    var img;
    var newPosition = createNewPosition();
    var newSettings = {
        width: $("#sourceWidth" + id).val() + "px",
        height: $("#sourceHeight" + id).val() + "px",
        top:newPosition.top,
        left:newPosition.left,
        opacity:1
    };
    createBackground({backgroundOpacity: 0.7});
    if (sourceImage.length > 0) {
        img = sourceImage[0];
        img.loaded = true;
    } else {
        img = getParentWindow().document.createElement("img");
        img.id = "sourceImage" + (new Date().getTime());
        $(thumbnail).attr("sourceImageId", img.id);
        $(img).attr("src", $("#sourceSizeUrl" + id).val().trim());
        $(img).css({cursor: "url(../../../images/zoomout.cur), default", position: "absolute", opacity:0});
        createLoadingMessage({text:"Loading...", color:"darkgreen"});
        $(img).load(function() {
            removeLoadingMessage();
            //removeBackground();
            img.loaded = true;
            $(img).animate(newSettings, 1000);
        });
        $(thumbnail).after(img);
        var thumbnailPosition = findPosAbs(thumbnail);
        img.initSettings = {
            width:thumbnailWidth + "px",
            height:thumbnailHeight + "px",
            top:thumbnailPosition.top + "px",
            left:thumbnailPosition.left + "px",
            opacity:0
        };
        img.loaded = false;
    }
    bringToFront(img);
    $(img).css(img.initSettings);
    document['onkeydown'] = hideOnKeydownFunction;
    $(getParentWindow().document).click(hideOnBodyClickFunction);


    if (img.loaded) {
        $(img).animate(newSettings, 1000);
    }

    function hideOnKeydownFunction(event) {
        if (escPressed(event)) {
            removeImageAndHandlers();
        }
    }

    function hideOnBodyClickFunction() {
        removeImageAndHandlers();
    }

    function removeImageAndHandlers() {
        if ($(img).css("opacity") > 0.5) {
            removeBackground();
            $(img).animate(img.initSettings, 1000, removeInternal);
        }
        function removeInternal() {
            thumbnail.clicked = false;
            $(img).css("top", -1000);
            $(getParentWindow().document).unbind("click", hideOnBodyClickFunction);
            $(getParentWindow().document).unbind("keypress", hideOnKeydownFunction);
        }
    }


    function createNewPosition() {
        var tempElement = getParentWindow().document.createElement("div");
        $(tempElement).css({
            width: $("#sourceWidth" + id).val() + "px",
            height: $("#sourceHeight" + id).val() + "px",
            position: "absolute",
            opacity:0
        });
        $(getParentWindow().document.body).append(tempElement);
        centerElement({elementToCenter:tempElement});
        $(tempElement).remove();
        return {top:$(tempElement).css("top"), left:$(tempElement).css("left")};
    }
}