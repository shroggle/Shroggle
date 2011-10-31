var renderSlideShow = {};
renderSlideShow.carousels = new Object();
renderSlideShow.pageHtml = "";
renderSlideShow.carouselHtml = new Object();

renderSlideShow.initCarousel = function (widgetId) {
    var slideShowBlock = $("#slideShow" + widgetId);
    $(slideShowBlock).show();

    var carouselBlock = $("#carousel" + widgetId);

    renderSlideShow.carouselHtml[widgetId] = $("#carouselWholeBlock" + widgetId).html();

    renderSlideShow.carousels[widgetId] = $(carouselBlock).carousel({
        dispItems: parseInt($("#carouselDisplayItems" + widgetId).val()),
        effect: $("#carouselEffect" + widgetId).val(),
        animSpeed: parseInt($("#carouselAnimationSpeed" + widgetId).val()),
        autoSlide: parseBoolean($("#carouselAutoPlay" + widgetId).val()),
        autoSlideInterval: parseInt($("#carouselAutoPlayInterval" + widgetId).val()),
        direction: $("#carouselDirection" + widgetId).val(),
        treatAsCarousel: parseBoolean($("#carouselTreatAsCarousel" + widgetId).val()),
        prevBtnInsertFn: function(carousel) {
            return $("#slideShowPrevBtn" + widgetId).append("<div role='button'></div>").children().first();
        },
        nextBtnInsertFn: function(carousel) {
            return $("#slideShowNextBtn" + widgetId).append("<div role='button'></div>").children().first();
        },
        slideShowBlockId: "slideShow" + widgetId,
        loop: true
    }).carousel;

    $(slideShowBlock).find(".carousel-wrap").css("width", $("#carouselWidth" + widgetId).val() + "px");
    $(slideShowBlock).find(".buttonsDiv").css("padding-left", $("#carouselButtonDivPaddingLeft" + widgetId).val() + "px");
};

renderSlideShow.slideShowPlayClick = function (playButton, widgetId) {
    if ($(playButton)[0].className == "carousel-play") {
        $(playButton)[0].className = "carousel-pause";
        $(playButton)[0].title = "Pause";
        renderSlideShow.carousels[widgetId].play(true);
    } else {
        $(playButton)[0].className = "carousel-play";
        $(playButton)[0].title = "Play";
        renderSlideShow.carousels[widgetId].pause();
    }
};

renderSlideShow.enterFullScreenMode = function (widgetId) {
    // Clearing screen
    renderSlideShow.pageHtml = $("body").html();
    var fullScreenBlockHtml = $("#slideShowFullScreenDiv").html();

    // Inserting full screen block html
    $("body").html(fullScreenBlockHtml);
    $("body").append("<input type='hidden' id='fullScreenSlideShow'/>");

    /* IE bug with jquery plugin fix. */
    $("body").prepend('<iframe id="jQuery_history" style="display: none;"></iframe>');

    var windowHeight = isIE() ? document.body.offsetHeight : window.innerHeight;
    var windowWidth = isIE() ? document.body.offsetWidth : window.innerWidth;

    var bottomDivPadding = 10;
    var bottomDivRealHeight = 78;
    var bottomDivHeight = bottomDivRealHeight - bottomDivPadding;
    var mainDivPadding = 10;
    var mainDivHeight = windowHeight - bottomDivRealHeight - mainDivPadding;
    var mainImageHeight = mainDivHeight - mainDivPadding;

    // Applying css properties.
    $("#slideShowFullScreenMainDiv").css("height", mainDivHeight);
    $("body").css("overflow", "hidden");
    $("#slideShowFullScreenMainDiv").css("padding-top", mainDivPadding);

    $("#slideShowFullScreenMainImage").css("height", mainImageHeight);

    $("#smallGalleryBlock" + widgetId).css("height", bottomDivHeight);
    $("#smallGalleryBlock" + widgetId).css("padding-top", bottomDivPadding / 2);
    $("#smallGalleryBlock" + widgetId).css("padding-bottom", bottomDivPadding / 2);

    $("img").each(function () {
        $(this).attr("src", $(this).attr("realsrc"));
    });

    renderSlideShow.initSmallCarousel(widgetId);

    var smallGalleryBlock = $("#smallGalleryBlock" + widgetId)[0];
    $(smallGalleryBlock).css("padding-left", ((windowWidth - $(smallGalleryBlock).find("ul")[0].offsetWidth) / 2) + "px");

    var slideShowFullScreenButtonsWidth = 48;
    $("#slideShowFullScreenButtons" + widgetId).css("left", ((windowWidth - slideShowFullScreenButtonsWidth) / 2) + "px");

    // Close div
    window.setTimeout(function () {
        $("#slideShowCloseDiv" + widgetId).animate({
            'opacity' : '0'
        });
    }, 3000);

    $("#slideShowCloseDiv" + widgetId).mouseenter(function () {
        $(this).animate({
            'opacity' : '1'
        });
    });

    $("#slideShowCloseDiv" + widgetId).mouseleave(function () {
        $(this).animate({
            'opacity' : '0'
        });
    });
};

renderSlideShow.initSmallCarousel = function(widgetId) {
    $("#smallGallery" + widgetId).carousel({
        dispItems: renderSlideShow.getDispItemForSmallGallery(widgetId),
        effect: "slide",
        animSpeed: 750,
        updateImgOnSelect: $("#slideShowFullScreenMainImage"),
        selectable: true,
        treatAsCarousel: false,
        prevBtnInsertFn: function(carousel) {
            return $("#fullScreenSlideShowPrevBtn" + widgetId).append("<div role='button'></div>").children().first();
        },
        nextBtnInsertFn: function(carousel) {
            return $("#fullScreenSlideShowNextBtn" + widgetId).append("<div role='button'></div>").children().first();
        },
        loop: true
    });

    var carouselWidth = 100 * renderSlideShow.getDispItemForSmallGallery(widgetId);
    $("#smallGallery" + widgetId).css("width", carouselWidth);
    $("#smallGallery" + widgetId).find("ul").css("width", carouselWidth);
    $(".carousel-wrap").css("width", carouselWidth);
};

renderSlideShow.getDispItemForSmallGallery = function (widgetId) {
    return $("#smallGallery" + widgetId).find("li").length;
};

renderSlideShow.exitFullScreenMode = function () {
    var widgetId = $("#slideShowFullScreenWidgetId").val();

    $("body").html(renderSlideShow.pageHtml);
    $("body").css("overflow", "auto");

    $("#carouselWholeBlock" + widgetId).html(renderSlideShow.carouselHtml[widgetId]);
};