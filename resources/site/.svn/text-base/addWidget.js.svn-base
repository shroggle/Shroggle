var previousClickElement;
var previousHoverElement;
var addWidgetDefaultSelectedItem = "TEXT";

function addWidgetItem(widgetId, showFromManageItemsPage) {
    var configureWidget = createConfigureWindow({width:700, height:650});
    hover = true;

    var request = {
        showFromManageItemsPage: showFromManageItemsPage || false,
        widgetId: widgetId ? widgetId : -1 // don't know why but stripes refuse to take null as argument here (SW-5829).
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.SiteNotFoundException", function () {
        if (isAnyWindowOpened()) {
            closeConfigureWidgetDiv();
        }
        alert($("#youHaveNoSitesToCreateItem").val());
    });

    serviceCall.executeViaJQuery("/site/showAddWidget.action", request, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureWidget.setContent(response);
        configureWidget.resize();

        $("#addWidgetElements").accordion({
            header: "span.addWidgetHeader",
            icons: {
                header: 'addWidgetClosedIcon',
                headerSelected: 'addWidgetOpenedIcon'
            },
            autoHeight: false,
            change: function(event, ui) {
                window.parent.hover = true;
                window.parent.previousClickElement = null;
            }
        });

        setSelected($("#" + addWidgetDefaultSelectedItem + "Widget")[0]);
        setSelectedClick($("#" + addWidgetDefaultSelectedItem + "Widget")[0]);
    });
}

function setSelected(element) {
    if (window.parent.hover) {
        if (previousHoverElement) {
            previousHoverElement.className = "addWidgetItem"
        }

        element.className = "selectedAddWidget";

        $("#infoHolder > span").hide();

        showInfo(element);

        previousHoverElement = element;
    }
}

function showInfo(element) {
    $(".addItemType").html($("label", element).html());
    $("#" + $(element).attr("widgetType") + "Info").showInline();
    $("#insertNew").attr("checked", true);
    $(".insertCopy").attr("disabled", true);
    $("#insertItems").empty();
    $("#onlyCurrentSite").attr("checked", true);
}

function clickInsertExist() {
    $(".insertCopy").removeAttr("disabled");
}

function clickInsertNew() {
    $(".insertCopy").attr("disabled", true);
}

function setUnselected(element, className) {
    if (window.parent.hover) {
        element.className = className;
    }
}

function setSelectedClick(element) {
    window.parent.hover = false;

    if (previousClickElement) {
        previousClickElement.className = "addWidgetItem";
    }

    previousClickElement = element;

    element.className = "selectedAddWidget";
    element.name = "selectedAddWidget";

    $("#infoHolder > span").hide();

    showInfo(element);
    reloadElementsArea();
}

function reloadElementsArea() {
    createLoadingArea({
        element: $("#addWidgetRightInsertDiv")[0],
        text: "Loading data...",
        color: "green",
        guaranteeVisibility: true});

    $("#insertItems").empty();
    $("#insertItems").append("<option value=\"-1\">There are no items created/available</option>");

    var request = {
        siteId: $('#selectedSiteId').val(),
        itemType: getWidgetType(previousClickElement),
        onlyCurrentSite: $("#onlyCurrentSite").attr("checked")
    };

    new ServiceCall().executeViaDwr("GetAllowItemsService", "execute", request, function (allowItems) {
        if (allowItems.length > 0) {
            $("#insertItems").empty();
            for (var i = 0; i < allowItems.length; i++) {
                $("#insertItems").append("<option value=\"" + allowItems[i].id + "\">" + allowItems[i].name + "</option>");
            }
        }

        if ($("#insertingElement").val() != "true") {
            removeLoadingArea();
        }
    });
}

function createWidgetDelegate(fromManageItems) {
    //Removing loading area that was created on insert new/existing options.
    //We need this to disable whole window before inserting element.
    removeLoadingArea();

    $("#insertingElement").val("true");
    var errors = new Errors();
    errors.clear();

    var widgetType = getWidgetType(previousClickElement);
    if (widgetType) {
        previousClickElement = null;
    } else {
        errors.set("SELECT_WIDGET", $("#selectContentModule").val());
        return;
    }

    if ($("#insertExist").attr("checked")) {
        var selectedItemId = $("#insertItems > option:selected").val();
        if (selectedItemId == -1) {
            errors.set("SELECT_EXISTING_ITEM", $("#selectExistingItem").val());
            return;
        }
    }

    if (fromManageItems) {
        manageItems.createItemOnManageItemsPage(widgetType);
    } else {
        createWidget(widgetType);
    }
}

function getWidgetType(element) {
    return $(element).attr("realWidgetType") ? $(element).attr("realWidgetType") : $(element).attr("widgetType");
}

function notReadyAlert() {
    alert("The option that you have selected is not yet available.\nPlease try another element.");
}