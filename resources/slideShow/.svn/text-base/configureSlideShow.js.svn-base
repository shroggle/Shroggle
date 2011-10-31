var configureSlideShow = {};

configureSlideShow.onBeforeShow = function (settings) {
    configureSlideShow.settings = settings;
};

configureSlideShow.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureSlideShow.errors = new Errors({}, "slideShowErrors");

    //Disabling form if it's shared in read-only mode.
    if ($("#siteOnItemRightType").val() == "READ") {
        configureSlideShow.disable();
    }

    if ($("#numberOfImagesShown > option:selected").val() == 1) {
        disableControls($("#imagesDisplayStyle"), true);
    }

    window.uploadFilesControl.bind({
        element: "#slideShowUpload",
        label: "Browse and Upload",
        siteId: $("#slideShowSiteId").val(),
        onClose: function () {
            new ServiceCall().executeViaDwr("UpdateSlideShowImagesService", "execute",
                    $("#selectedSlideShowId").val(), function (response) {
                configureSlideShow.updateImages(response);
            });
        },
        customParameters:["slideShowId=" + $("#selectedSlideShowId").val()],
        customUploadAction: "/slideShowUploadImage.action"
    });
    
    getActiveWindow().resize();
    getActiveWindow().enableContent();
};

configureSlideShow.save = function (closeAfterSaving) {
    var serviceCall = new ServiceCall();

    configureSlideShow.errors.clear();

    if ($("#slideShowName").val() == "") {
        configureSlideShow.errors.set("SlideShowNameEmptyException", $("#SlideShowNameEmptyException").val(),
                [$("#slideShowName")[0]]);
    }

    if ($("#imageWidth").val() == "") {
        configureSlideShow.errors.set("SlideShowImageWidthIsEmpty", $("#SlideShowImageWidthIsEmpty").val(),
                [$("#imageWidth")[0]]);
    }

    if ($("#imageHeight").val() == "") {
        configureSlideShow.errors.set("SlideShowImageHeightIsEmpty", $("#SlideShowImageHeightIsEmpty").val(),
                [$("#imageHeight")[0]]);
    }

    if (configureSlideShow.errors.hasErrors()) {
        return;
    }

    var autoPlay = false;
    if ( $("#autoPlayInterval > option:selected").val() > 0 ){
        autoPlay = true;
    }
    var slideShowId = $("#selectedSlideShowId").val();

    var request = {
        widgetId: configureSlideShow.settings.widgetId,
        slideShowId: slideShowId,
        name: $("#slideShowName").val(),
        header: $("#SlideShowHeader")[0].innerHTML,
        showHeader: $("#showSlideShowHeader")[0].value,
        selectedFormId: $("#selectExistingForm > option:selected").val(),
        selectedFilterId: $("#selectExistingFilter > option:selected").val(),
        selectedLinkBackToGalleryWidgetId: $("#selectLinkBackToGallery > option:selected").val(),
        imageWidth: $("#imageWidth").val(),
        imageHeight: $("#imageHeight").val(),
        transitionEffectType: $("#transitionEffect > option:selected").val(),
        numberOfImagesShown: $("#numberOfImagesShown > option:selected").val(),
        displayType: $("#displayStyle > option:selected").val(),
        displayControls: $("#displayControls").attr("checked"),
        autoPlay: autoPlay,
        autoPlayInterval: $("#autoPlayInterval > option:selected").val()
    };


    serviceCall.addExceptionHandler(
            "com.shroggle.exception.SlideShowNameEmptyException",
            configureSlideShow.errors.exceptionAction({errorId:"SlideShowNameEmptyException", fields:[$("#slideShowName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.SlideShowNameNotUniqueException",
            configureSlideShow.errors.exceptionAction({errorId:"SlideShowNameNotUniqueException", fields:[$("#slideShowName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveSlideShowService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + slideShowId).html($("#slideShowName").val());

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureSlideShow.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureSlideShow.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }
                }
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        }
    });
};

configureSlideShow.selectFormChange = function (formSelect, siteId) {
    var selectedFormId = $(formSelect).find("option:selected").val();

    if (selectedFormId == -1) {
        return;
    }

    $("#selectImageFormItem").parent().hide();

    var serviceCall = new ServiceCall();

    createLoadingArea({element:$("#FORM_SOURCEDiv")[0], text: "Loading data...",
        color: "green", guaranteeVisibility:true});
    serviceCall.executeViaDwr("UpdateGalleryAndFilterSelectService", "execute", selectedFormId, siteId,
            function (response) {
                if (response) {
                    //Updating galleries.
                    $("#selectLinkBackToGallery").html("");

                    $(response.linkBackToGalleryWidgetIdLocationPair).each(function () {
                        for (var widgetId in this) {
                            if (this.hasOwnProperty(widgetId)) {
                                var widgetLocation = response.linkBackToGalleryWidgetIdLocationPair[widgetId];

                                $("#selectLinkBackToGallery").append("<option value='" + widgetId + "'>" +
                                        widgetLocation + "</option>");
                            }
                        }
                    });

                    if ($("#selectLinkBackToGallery").html() == "") {
                        $("#selectLinkBackToGallery").html("<option value='-1'>" + $("#noGalleries").val() + "</option>");
                    } else {
                        $("#selectLinkBackToGallery").prepend("<option value='-1'>" + $("#selectGalleryText").val() + "</option>");
                    }

                    // Updating filters.
                    $("#selectExistingFilter").html("");
                    if (response.filters.length > 0) {
                        $("#selectExistingFilter").append("<option value='-1'>" + $("#selectExistingFilterText").val() + "</option>");
                        $(response.filters).each(function () {
                            $("#selectExistingFilter").append("<option value='" + this.formFilterId + "'>" +
                                    this.name + "</option>");
                        });
                    } else {
                        $("#selectExistingFilter").append("<option value='-1'>" + $("#noFilters").val() + "</option>");
                    }

                    // If selected form has more than one image form item then we should display select with them.
                    $("#selectImageFormItem").html("");
                    if (response.imageFormItems.length > 1) {
                        $("#selectImageFormItem").parent().show();
                        $("#selectImageFormItem").append("<option value='-1'>" + $("#selectImageFormItemText").val() + "</option>");
                        $(response.imageFormItems).each(function () {
                            $("#selectImageFormItem").append("<option value='" + this.formItemId + "'>" + this.itemName + "</option>")
                        });
                    } else {
                        $("#selectImageFormItem").append("<option value='-1'>" + $("#selectImageFormItemText").val() + "</option>");
                    }

                    removeLoadingArea();
                }
            });
};

configureSlideShow.uploadImagesFromForm = function () {
    var selectedFormId = $("#selectExistingForm > option:selected").val();

    if (selectedFormId == -1) {
        return;
    }

    var selectedFilterId = $("#selectExistingFilter > option:selected").val();
    var selectedImageItemId = $("#selectImageFormItem > option:selected").val();
    var selectedLinkBackToGalleryId = $("#selectLinkBackToGallery > option:selected").val();

    var request = {
        slideShowId: $("#selectedSlideShowId").val(),
        selectedFormId: selectedFormId == -1 ? null : selectedFormId,
        selectedImageFormItemId: selectedImageItemId == -1 ? null : selectedImageItemId,
        selectedFilterId: selectedFilterId == -1 ? null : selectedFilterId,
        selectedLinkBackToGalleryId: selectedLinkBackToGalleryId == -1 ? null : selectedLinkBackToGalleryId
    };

    var serviceCall = new ServiceCall();

    $("#uploadSlideShowImagesLoadingDiv").css("display", "inline");
    serviceCall.executeViaDwr("SlideShowUploadFormImagesService", "execute", request,
            function (response) {
                $("#uploadSlideShowImagesLoadingDiv").hide();

                if (response.numberOfImagesUploaded > 0) {
                    $("#numberOfUploadedImagesText").html("Successfully uploaded " + response.numberOfImagesUploaded + " images.");
                } else {
                    $("#numberOfUploadedImagesText").html("No images were uploaded.");
                }
                addFadingTimeoutEvent($("#numberOfUploadedImagesText")[0], 4000);

                configureSlideShow.updateImages(response.manageImagesDivHtml);
            });
};

configureSlideShow.updateImages = function (images) {
    $("#slideShowManageImagesWholeDiv").replaceWith(images);
    getActiveWindow().invalidCache = true;
};

configureSlideShow.sourceSelectChange = function (select) {
    if ($(select).find("option:selected")[0].id == "UPLOAD_SOURCE") {
        $("#UPLOAD_SOURCEDiv").show();
        $("#FORM_SOURCEDiv").hide();
    } else {
        $("#UPLOAD_SOURCEDiv").hide();
        $("#FORM_SOURCEDiv").show();
    }
};

configureSlideShow.onChangingNumOfSlides = function (numOfSlides) {
    if ($(numOfSlides).find("option:selected").val() == 1)  {
        disableControl($("#displayStyle")[0], true);
        disableControl($("#transitionEffect")[0], false);
    }
    else {
         if ( $("#displayStyle > option:selected").val() == "MOVING_STRIP_HORIZONTAL" ||
            $("#displayStyle > option:selected").val() == "MOVING_STRIP_VERTICAL") {
                disableControl($("#displayStyle")[0], false);
                disableControl($("#transitionEffect")[0], true);
                $("#transitionEffect > option[value='SLIDE_SLOW']").attr("selected", "selected");
         } else {
                disableControl($("#displayStyle")[0], false);
                disableControl($("#transitionEffect")[0], false);
         }
    }
};

configureSlideShow.displayStyleChange = function (displayStyleSelect) {
    if (($("#numberOfImagesShown > option:selected").val() > 1)  &&
            ($(displayStyleSelect).find("option:selected").val() == "MOVING_STRIP_HORIZONTAL" ||
            $(displayStyleSelect).find("option:selected").val() == "MOVING_STRIP_VERTICAL")) {
        disableControl($("#transitionEffect")[0], true);
        $("#transitionEffect > option[value='SLIDE_SLOW']").attr("selected", "selected");
    } else {
        disableControl($("#transitionEffect")[0], false);
    }
};

configureSlideShow.selectSettingsTab = function () {
    $("#manageImagesTab").hide();
    $("#selectedSettingsTabName").html('Module Settings');
    $("#slideShowSettingsTab").show();
};

configureSlideShow.selectManageImagesTab = function () {
    $("#slideShowSettingsTab").hide();
    $("#selectedSettingsTabName").html('Manage Images');
    $("#manageImagesTab").show();
};

configureSlideShow.displayControlsClick = function () {
    if (!$("#displayControls").attr("checked")) {
        // If it is unchecked, the auto play option can not have 0
        // So we remove this from the auto play select option
        if ($("#autoPlayInterval option[value=0]").length > 0) {
                // The auto play interval 0 sec is present in the select option list. We need to remove it.
            var autoPlayInterval = $("#autoPlayInterval").val();
            $("#autoPlayInterval option[value=0]").remove();
            if (autoPlayInterval == 0) {
                autoPlayInterval = 1000;
            }
            $("#autoPlayInterval").val(autoPlayInterval);
        }
    } else {
        var autoPlayVal = $("#autoPlayInterval").val();
        $("#autoPlayInterval").empty();
        $("#autoPlayInterval").append('<option value="0">0s</option>');
        $("#autoPlayInterval").append('<option value="1000">1s</option>');
        $("#autoPlayInterval").append('<option value="2000">2s</option>');
        $("#autoPlayInterval").append('<option value="3000">3s</option>');
        $("#autoPlayInterval").append('<option value="4000">4s</option>');
        $("#autoPlayInterval").append('<option value="5000">5s</option>');
        $("#autoPlayInterval").append('<option value="7000">7s</option>');
        $("#autoPlayInterval").append('<option value="10000">10s</option>');
        $("#autoPlayInterval").append('<option value="15000">15s</option>');
        $("#autoPlayInterval").val(autoPlayVal);
    }
};

configureSlideShow.disable = function () {
    // todo add disabling
};

/*----------------------------------------------------MANAGE IMAGES---------------------------------------------------*/

configureSlideShow.deleteImage = function(deleteImageIcon, slideShowImageId) {
    var tdToRemove = $(deleteImageIcon).parents("td.slideShowImageTd");
    var parentTr = $(tdToRemove).parent();
    tdToRemove.remove();

    reorderTable();

    new ServiceCall().executeViaDwr("ConfigureSlideShowManageImagesService", "removeImage", slideShowImageId);

    function reorderTable() {
        var nextTr = $(parentTr).next();
        var prevTr = $(parentTr);

        while (nextTr[0]) {
            var firstTdInNextTr = $(nextTr).find("td:first");
            $(prevTr).append(firstTdInNextTr);

            prevTr = nextTr;
            nextTr = $(nextTr).next();
        }
    }
};

configureSlideShow.moveImageLeft = function(moveImageLeftIcon, slideShowImageId) {
    var currentImageTd = $(moveImageLeftIcon).parents("td:first");
    var currentImageTr = $(moveImageLeftIcon).parents("tr:first");

    if ($(currentImageTd).prev()[0]) {
        var prevImage = $(currentImageTd).prev();
        $(currentImageTd).after(prevImage);

        executeServiceCall();
    } else {
        var prevTr = $(currentImageTd).parent().prev();

        if (prevTr[0]) {
            var lastImage = $(prevTr).children().last();
            $(prevTr).append(currentImageTd);
            $(currentImageTr).prepend(lastImage);

            executeServiceCall();
        }
    }

    function executeServiceCall() {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ConfigureSlideShowManageImagesService", "moveImageLeft", slideShowImageId);
    }
};

configureSlideShow.moveImageRight = function(moveImageRightIcon, slideShowImageId) {
    var currentImageTd = $(moveImageRightIcon).parents("td:first");
    var currentImageTr = $(moveImageRightIcon).parents("tr:first");

    if ($(currentImageTd).next()[0]) {
        var nextImage = $(currentImageTd).next();
        $(currentImageTd).before(nextImage);

        executeServiceCall();
    } else {
        var nextTr = $(currentImageTd).parent().next();

        if (nextTr[0]) {
            var lastImage = $(nextTr).children().first();
            $(nextTr).prepend(currentImageTd);
            $(currentImageTr).append(lastImage);

            executeServiceCall();
        }
    }

    function executeServiceCall() {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ConfigureSlideShowManageImagesService", "moveImageRight", slideShowImageId);
    }
};
