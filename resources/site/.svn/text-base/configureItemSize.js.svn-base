var configureItemSize = {};

configureItemSize.onAfterShow = function () {
    if ($("#siteOnItemRightType").val() == "READ") {
        configureItemSize.disable();
    }

    configureItemSize.errors = new Errors({}, "itemSizeErrors");
};

function widgetSizeChanged(type) {
    if (type == "width") {
        if ($("#widgetSizeWidthType")[0].options[$("#widgetSizeWidthType")[0].selectedIndex].value == "PERCENT" && parseInt($("#widgetSizeWidth")[0].value) > 100) {
            $("#widgetSizeWidth")[0].value = 100;
        }
    } else if (type == "height") {
        if ($("#widgetSizeHeightType")[0].options[$("#widgetSizeHeightType")[0].selectedIndex].value == "PERCENT" && parseInt($("#widgetSizeHeight")[0].value) > 100) {
            $("#widgetSizeHeight")[0].value = 100;
        }
    }
}

function widgetFloatableCheckboxClick(checkbox) {
    if ($(checkbox).attr("checked")) {
        disableControl($("#createClearDiv")[0], false);
    } else {
        disableControl($("#createClearDiv")[0]);
        $("#createClearDiv").attr("checked", false);
    }
}

configureItemSize.save = function (widgetId, draftItemId, closeAfterSaving) {
    configureItemSize.errors.clear();

    if ($("#widgetSizeWidthType > option:selected").val() == "PERCENT" && parseInt($("#widgetSizeWidth").val()) > 100) {
        configureItemSize.errors.set("WIDGET_WIDTH_MORE_THAN_100_PERCENT", "Width should not be more than 100%.", $("#widgetSizeWidth")[0]);
    }

    if ($("#widgetSizeHeightType > option:selected").val() == "PERCENT" && parseInt($("#widgetSizeHeight").val()) > 100) {
        configureItemSize.errors.set("WIDGET_HEIGHT_MORE_THAN_100_PERCENT", "Height should not be more than 100%.", $("#widgetSizeHeight")[0]);
    }

    if (configureItemSize.errors.hasErrors()) {
        return;
    }

    var request = {
        widgetId: widgetId,
        draftItemId: draftItemId,
        saveItemSizeInCurrentPlace: ($("#saveItemSizeInCurrentPlace").attr("checked") || false),
        itemSize :{
            floatable: $("#widgetSizeFloatable")[0].checked,
            createClearDiv: $("#createClearDiv")[0].checked,
            width: $("#widgetSizeWidth")[0].value,
            widthSizeType: $("#widgetSizeWidthType")[0].options[$("#widgetSizeWidthType")[0].selectedIndex].value,
            height: $("#widgetSizeHeight")[0].value,
            heightSizeType: $("#widgetSizeHeightType")[0].options[$("#widgetSizeHeightType")[0].selectedIndex].value,
            overflow_x: $("#widgetSizeOverflow-x")[0].options[$("#widgetSizeOverflow-x")[0].selectedIndex].value,
            overflow_y: $("#widgetSizeOverflow-y")[0].options[$("#widgetSizeOverflow-y")[0].selectedIndex].value
        }
    };

    var serviceCall = new ServiceCall();


    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateItemSizeService", "execute", request, function () {
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
        if (widgetId && !$("#dashboardPage")[0]) {
            updateWidgetSizeOnPage({widgetId:widgetId, updateRequest:request});
        }
    });
};

configureItemSize.disable = function () {
    disableControl($("#widgetSizeFloatable")[0]);
    disableControl($("#createClearDiv")[0]);

    disableControl($("#widgetSizeWidth")[0]);
    disableControl($("#widgetSizeWidthType")[0]);
    disableControl($("#widgetSizeOverflow-x")[0]);

    disableControl($("#widgetSizeHeight")[0]);
    disableControl($("#widgetSizeHeightType")[0]);
    disableControl($("#widgetSizeOverflow-y")[0]);

    $("#forItemDiv", $("#configureItemSizeButtons")).css("visibility", "hidden");
    $("#windowSave", $("#configureItemSizeButtons")[0]).hide();
    $("#windowApply", $("#configureItemSizeButtons")[0]).hide();
    $("#windowCancel", $("#configureItemSizeButtons")[0]).val("Close");

    $("#itemSizeReadOnlyMessage").show();
};
