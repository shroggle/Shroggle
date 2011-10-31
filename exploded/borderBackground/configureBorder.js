var configureBorder = {
    borderTabSelector: "#borderTabContent"
};

configureBorder.onAfterShow = function () {
    if ($("#siteOnItemRightType").val() == "READ") {
        configureBorder.disable();
    }
};

configureBorder.disable = function () {
    disableControl($("#borderWidth")[0]);
    disableControl($("#borderWidthEmptyDiv"));
    disableControl($("#borderWidthValueDiv"));
    disableControl($("#borderWidthVerticalHorizontal"));
    disableControl($("#borderWidthSeparately"));

    disableControl($("#borderStyle")[0]);
    disableControl($("#borderStyleEmptyDiv"));
    disableControl($("#borderStyleValueDiv"));
    disableControl($("#borderStyleVerticalHorizontal"));
    disableControl($("#borderStyleSeparately"));

    disableControl($("#borderColor")[0]);
    // color pickers are disabled in styleInputField.js on theirs render.

    disableControl($("#borderPadding")[0]);
    disableControl($("#borderPaddingEmptyDiv"));
    disableControl($("#borderPaddingValueDiv"));
    disableControl($("#borderPaddingVerticalHorizontal"));
    disableControl($("#borderPaddingSeparately"));

    disableControl($("#borderMargin")[0]);
    disableControl($("#borderMarginEmptyDiv"));
    disableControl($("#borderMarginValueDiv"));
    disableControl($("#borderMarginVerticalHorizontal"));
    disableControl($("#borderMarginSeparately"));

    $("#forItemDiv", $("#configureBorderButtons")).css("visibility", "hidden");
    $("#windowSave", $("#configureBorderButtons")[0]).hide();
    $("#windowApply", $("#configureBorderButtons")[0]).hide();
    $("#windowCancel", $("#configureBorderButtons")[0]).val("Close");

    $("#borderReadOnlyMessage").show();
};

function borderStyleChanged() {
    showStyleInputs();

    //----------------------------------setting changed border-style for preview image----------------------------------
    var marginValues = getStyleValues("borderMargin");
    var marginMeasurements = getMeasurementValues("borderMargin");
    var margin = styleToStringWithSign(marginValues, marginMeasurements);

    var paddingValues = getStyleValues("borderPadding");
    var paddingMeasurements = getMeasurementValues("borderPadding");
    var padding = styleToStringWithSign(paddingValues, paddingMeasurements);

    if (isStyleValueNegative(paddingValues) ||
         isStyleValueNegative(marginValues) ){
        $("#borderPaddingWarning").show();
    }
    else {
        $("#borderPaddingWarning").hide();
    }

    var widthValues = getStyleValues("borderWidth");
    var widthMeasurements = getMeasurementValues("borderWidth");
    var width = styleToString(widthValues, widthMeasurements);

    var styleValues = getStyleValues("borderStyle");
    var style = styleToString(styleValues, {top:"",right:"",bottom:"",left:""});

    var color = styleToString(getColorValues("borderColor"), {top:"",right:"",bottom:"",left:""});

    var imageSize = createNewImageSize();
    setStyleInnerHtml(document.getElementById("borderSampleImageStyle"), createStyle());

    function createStyle() {
        return " #borderSampleImage{" +
                "border-style:" + style +
                "border-width:" + width +
                "border-color:" + color + ";" +
                "margin:" + margin +
                "padding:" + padding +
                "width:" + imageSize.width + "px;" +
                "height:" + imageSize.height + "px;" +
                "}";
    }

    function createNewImageSize() {
        var WIDTH = document.getElementById("IMAGE_WIDTH").value;
        var HEIGHT = document.getElementById("IMAGE_HEIGHT").value;
        var marginPaddingWidth = createPX(numberValue(marginValues.left), marginMeasurements.left) +
                createPX(numberValue(marginValues.right), marginMeasurements.right) +
                createPX(numberValue(paddingValues.left), paddingMeasurements.left) +
                createPX(numberValue(paddingValues.right), paddingMeasurements.right);
        var marginPaddingHeight = createPX(numberValue(marginValues.top), marginMeasurements.top) +
                createPX(numberValue(marginValues.bottom), marginMeasurements.bottom) +
                createPX(numberValue(paddingValues.top), paddingMeasurements.top) +
                createPX(numberValue(paddingValues.bottom), paddingMeasurements.bottom);
        var borderWidth = (styleValues.left && styleValues.left != "NONE" ? createPX(numberValue(widthValues.left), widthMeasurements.left) : 0) +
                (styleValues.right && styleValues.right != "NONE" ? createPX(numberValue(widthValues.right), widthMeasurements.right) : 0);
        var borderHeight = (styleValues.top && styleValues.top != "NONE" ? createPX(numberValue(widthValues.top), widthMeasurements.top) : 0) +
                (styleValues.bottom && styleValues.bottom != "NONE" ? createPX(numberValue(widthValues.bottom), widthMeasurements.bottom) : 0);
        var response = new Object();
        response.width = (WIDTH - (marginPaddingWidth + borderWidth));
        response.height = (HEIGHT - (marginPaddingHeight + borderHeight));
        return response;
    }

    //----------------------------------setting changed border-style for preview image----------------------------------
}


function confirmApplyToAllPages() {
    if (isApplyToAllPagesCheckboxChecked()) {
        return confirm(document.getElementById("applyToAllPagesText").value);
    }
    return true;
}

function isApplyToAllPagesCheckboxChecked() {
    var checkbox = document.getElementById("applyToAllPages");
    if (checkbox) {
        return checkbox.checked;
    }
    return false;
}

function submitBorderSettings(itemId, draftItemId, closeAfterSaving, siteId) {

    if (!confirmApplyToAllPages()) {
        return;
    }
    var border = {
        borderColor : new Style("borderColor", "border-color", true, true),
        borderStyle : new Style("borderStyle", "border-style", true, false),
        borderWidth : new Style("borderWidth", "border-width", false, false),
        borderMargin : new Style("borderMargin", "margin", false, false),
        borderPadding : new Style("borderPadding", "padding", false, false),
        siteId : siteId
    };

    if (border.borderColor.values.topValue.length < 7) {
        border.borderColor.values.topValue = "transparent";
    }
    if (border.borderColor.values.rightValue.length < 7) {
        border.borderColor.values.rightValue = "transparent";
    }
    if (border.borderColor.values.bottomValue.length < 7) {
        border.borderColor.values.bottomValue = "transparent";
    }
    if (border.borderColor.values.leftValue.length < 7) {
        border.borderColor.values.leftValue = "transparent";
    }

    var serviceCall = new ServiceCall();
    
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateBorderService", "executeForWidget",
            itemId, draftItemId, border, ($("#saveBorderInCurrentPlace").attr("checked") || false), function(newStyle) {
        updatePageAfterBorderSaving(newStyle);
        closeConfigureWindow();
    });

    function updatePageAfterBorderSaving(newStyle) {
        if (itemId && !$("#dashboardPage")[0]) {
            updateWidgetBorderBackgroundStyle(newStyle);
            makePageDraftVisual(window.parent.getActivePage());
        }
    }

    function closeConfigureWindow() {
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    }
}

function isStyleValueNegative(value){
       if(isNegativeInteger(value.top)) {
           return true;
       }
       if(isNegativeInteger(value.right)){
           return true;
       }
       if(isNegativeInteger(value.bottom)){
           return true;
       }
       if(isNegativeInteger(value.left)){
           return true;
       }
    return false;
}