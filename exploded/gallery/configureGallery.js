var configureGallery = {};

var configureGalleryNavigationThumbnailsBorderColor = null;
var configureGalleryNavigationThumbnailsBackgroundColor = null;

configureGallery.onBeforeShow = function (settings) {
    configureGallery.settings = settings;
};

configureGallery.onAfterShow = function (response) {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureGallery.galleryEntity = response.gallery;
    configureGallery.readOnly = $("#siteOnItemRightType").val() == "READ";
    configureGallery.formItemsWithSize = response.formItemsWithSize;

    configureGallery.numberFormItems = new Object();
    for (var j = 0; j < response.numberFormItems.length; j++) {
        configureGallery.numberFormItems[response.numberFormItems[j]] = true;
    }

    configureGallery.textFormItems = new Object();
    for (var i = 0; i < response.textFormItems.length; i++) {
        configureGallery.textFormItems[response.textFormItems[i]] = true;
    }

    delete response.formItemsWithSize;

    window.voteStars = response.gallery.voteStars;
    window.voteLinks = response.gallery.voteLinks;

    eval($("#configureGalleryData").val());
    eval($("#configureGalleryForm").val());
    eval($("#configureGalleryNavigationPageSelectorInternational").val());
    eval($("#configureGalleryDataPageSelectorInternational").val());

    //Accuring the error block
    configureGallery.errors = new Errors({}, "galleryErrors");
    resizeTabs();

    window.configureGalleryForm = window.configureGalleryTempForm;
    if (window.configureGalleryForm.items.length < 1) {
        window.configureGalleryForm = window.configureGalleryDefaultForm;
    }

    if (configureGallery.galleryEntity.id < 1) {
        // This line need for disable confirm when you change layout
        configureGallery.galleryEntity.orientationLayout = "some";
    }
    showConfigureGalleryLayout();
    showConfigureGalleryDataWidget();
    showConfigureGalleryFormFilter();
    updateConfigureGalleryAdvancedInfo();
    changeConfigureGalleryECommerceUseCart();
    changeConfigureGalleryECommerceForm();
    showConfigureGalleryECommerce();
    changeConfigureGalleryECommerceEnable();

    if (!configureGallery.readOnly) {
        if (configureGallery.galleryEntity.formFilterId > 0) {
            configureGallerySetUseFilter();
        } else {
            showConfigureGalleryForm();
        }
    }

    $("#configureGalleryShowOnlyMyRecords").attr("checked", configureGallery.galleryEntity.showOnlyMyRecords);
    configureGalleryNavigationThumbnailsBorderColor = new ColorPicker({
        renderTo: "configureGalleryNavigationThumbnailBorderColorContainer",
        valueInputId: "configureGalleryNavigationThumbnailBorderColor",
        width: 175,
        textInputFieldWidth: 140
    });
    configureGalleryNavigationThumbnailsBackgroundColor = new ColorPicker({
        renderTo: "configureGalleryNavigationThumbnailBackgroundColorContainer",
        valueInputId: "configureGalleryNavigationThumbnailBackgroundColor",
        width: 175,
        textInputFieldWidth: 140
    });

    disableFormFilterLinks();

    if (!$("#configureGalleryUseFormFilter")[0].checked) {
        $("#configureGalleryFormFilter").attr("disabled", true);
    }

    if (configureGallery.readOnly) {
        disableConfigureGallery();
    }
    disableVotingArea(configureGallery.readOnly || !$("#includesVotingModule")[0].checked);

    if (configureGallery.settings.showStoreSettings) {
        $("#eCommerceTab").click();
    }
    setWindowSettingsUnchanged();
};

function disableFormFilterLinks() {
    disableControl($("#editFilterLink")[0], !$("#configureGalleryUseFormFilter")[0].checked);
    disableControl($("#createFilterLink")[0], !$("#configureGalleryUseFormFilter")[0].checked);

    if ($("#configureGalleryFormFilter")[0].selectedIndex <= 0 && !$("#editFilterLink")[0].disabled) {
        disableControl($("#editFilterLink")[0]);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryEditFormFilter() {
    if (document.getElementById("configureGalleryUseFormFilter").checked) {
        var formFilters = document.getElementById("configureGalleryFormFilter");
        if (formFilters.selectedIndex > 0) {
            configureFormFilter({
                formFilterId: formFilters.options[formFilters.selectedIndex].value,
                disableFormPickList: true,
                cameFromGallery: true,
                onSave: function (formFilter) {
                    $("#configureGalleryFormFilter > option[value=" + formFilter.id + "]").html(formFilter.name);
                }
            });
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryCreateFormFilter() {
    if (document.getElementById("configureGalleryUseFormFilter").checked) {
        configureFormFilter({
            formId: configureGallery.galleryEntity.formId ? configureGallery.galleryEntity.formId : null,
            onSave: function (formFilter) {
                $("#configureGalleryFormFilter").append(
                        "<option value=\"" + formFilter.id + "\" selected=\"selected\">" +
                                formFilter.name + "</option>");
                $("#configureGalleryFormFilter").change();
            }});
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryDataWidget() {
    var dataWidget = document.getElementById("configureGalleryAllowWidgetDatas");
    if (dataWidget && configureGallery.galleryEntity.dataCrossWidgetId) {
        $("#configureGalleryAllowWidgetsRadio").attr("checked", true);
        for (var i = 1; i < dataWidget.options.length; i++) {
            var crossAndWidgetId = dataWidget.options[i].value.split("|");
            if (crossAndWidgetId[0] == configureGallery.galleryEntity.dataCrossWidgetId) {
                dataWidget.selectedIndex = i;
                break;
            }
        }
    }

    changeConfigureGalleryAllowData();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryFormFilter() {
    var formFilter = document.getElementById("configureGalleryFormFilter");
    for (var i = 1; i < formFilter.options.length; i++) {
        if (formFilter.options[i].value == configureGallery.galleryEntity.formFilterId) {
            formFilter.selectedIndex = i;
            break;
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGallerySiteForData() {
    var sitesForData = document.getElementById("configureGallerySitesForData");
    if (sitesForData && sitesForData.selectedIndex > -1) {
        $("#configureGalleryAllowWidgetsRadio").attr("checked", false);
        var siteForDataId = sitesForData.options[sitesForData.selectedIndex].value;
        new ServiceCall().executeViaJQuery("/getSitePages.action", {siteId:siteForDataId}, function (html) {
            $("#configureGalleryPagesForDataHolder").html(html);
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGalleryAllowData() {
    if ($("#configureGalleryDataTypeNew").attr("checked")) {
        $("#configureGallerySitesForData").removeAttr("disabled");
        $("#configureGalleryPagesForData").removeAttr("disabled");
        $("#configureGalleryAllowWidgetDatas").attr("disabled", "disabled");
    } else {
        $("#configureGallerySitesForData").attr("disabled", "disabled");
        $("#configureGalleryPagesForData").attr("disabled", "disabled");
        $("#configureGalleryAllowWidgetDatas").removeAttr("disabled");
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryForm() {
    if (configureGallery.galleryEntity.formId) {
        $("#configureGalleryFormFilter").attr("disabled", true);
        $("#configureGalleryForms").removeAttr("disabled");
        $("#configureGalleryUploadImagesExists").attr("checked", true);
        var forms = document.getElementById("configureGalleryForms");
        for (var i = 1; i < forms.options.length; i++) {
            if (forms.options[i].value == configureGallery.galleryEntity.formId) {
                forms.selectedIndex = i;
                return;
            }
        }

        configureGalleryGetDefaultForm();
        $("#configureGalleryUploadImagesNew").attr("checked", true);
        configureGallery.errors.set("formId",
                "Form is not selected. Please, choose one of the existing forms or select another option.",
        {});
    }
    $("#configureGalleryForms").attr("disabled", true);
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryGetDefaultForm() {
    $("#configureGalleryFormFilter").attr("disabled", true);
    $("#configureGalleryForms").attr("disabled", true);
    configureGallery.errors.clear();
    configureGallery.galleryEntity.formId = null;
    configureGalleryUpdateGalleryAfterSetForm(window.configureGalleryDefaultForm);
    updateConfigureGalleryAdvancedInfo();
    changeConfigureGalleryECommerceForm();
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGallerySetUseFilter() {
    configureGallery.errors.clear();
    $("#configureGalleryFormFilter").removeAttr("disabled");
    $("#configureGalleryUseFormFilter").attr("checked", true);
    $("#configureGalleryForms").attr("disabled", true);
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryGetForm() {
    $("#configureGalleryFormFilter").attr("disabled", true);
    $("#configureGalleryForms").removeAttr("disabled");
    var forms = $("#configureGalleryForms").get(0);
    var formId = null;
    if (forms.selectedIndex > 0) {
        formId = forms.options[forms.selectedIndex].value;
    } else if (forms.options.length > 0) {
        formId = forms.options[0].value;
    } else {
        return;
    }

    $.ajax({
        cache: false,
        type: "get",
        url: "/gallery/getForm.action",
        dataType: "html",
        data: {formId: formId},
        success: function (formHtml) {
            eval(formHtml);
            configureGallery.errors.clear();
            configureGallery.galleryEntity.formId = formId;
            configureGalleryUpdateGalleryAfterSetForm(window.configureGalleryTempForm);
            changeConfigureGalleryECommerceForm();
            updateConfigureGalleryAdvancedInfo();
        }
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGalleryECommerceForm() {
    $("#configureGalleryECommerceFullPrice").html("<option>Select price field</option>");
    $("#configureGalleryECommerceProductName").html("<option>Select product field</option>");
    $("#configureGalleryECommerceProductDescription").html("<option>Select description field</option>");
    $("#configureGalleryECommerceProductImage").html("<option>Select image field</option>");

    for (var i = 0; i < window.configureGalleryForm.items.length; i++) {
        var item = window.configureGalleryForm.items[i];
        if (item.type == "IMAGE_FILE_UPLOAD") {
            $("#configureGalleryECommerceProductImage").append("<option value=\"" + item.id + "\">" + item.name + "</option>");
            if ($("#configureGalleryECommerceProductImage")[0].selectedIndex < 1)
                $("#configureGalleryECommerceProductImage")[0].selectedIndex = $("#configureGalleryECommerceProductImage")[0].options.length - 1;
        }

        if (configureGallery.numberFormItems[item.type]) {
            $("#configureGalleryECommerceFullPrice").append("<option value=\"" + item.id + "\">" + item.name + "</option>");
            if ($("#configureGalleryECommerceFullPrice")[0].selectedIndex < 1)
                $("#configureGalleryECommerceFullPrice")[0].selectedIndex = $("#configureGalleryECommerceFullPrice")[0].options.length - 1;
        }

        if (configureGallery.textFormItems[item.type]) {
            $("#configureGalleryECommerceProductName").append("<option value=\"" + item.id + "\">" + item.name + "</option>");
            $("#configureGalleryECommerceProductDescription").append("<option value=\"" + item.id + "\">" + item.name + "</option>");
            if ($("#configureGalleryECommerceProductName")[0].selectedIndex < 1)
                $("#configureGalleryECommerceProductName")[0].selectedIndex = $("#configureGalleryECommerceProductName")[0].options.length - 1;
            else if ($("#configureGalleryECommerceProductDescription")[0].selectedIndex < 1)
                $("#configureGalleryECommerceProductDescription")[0].selectedIndex = $("#configureGalleryECommerceProductDescription")[0].options.length - 1;
        }
    }

    if ($("#configureGalleryECommerceProductImage").attr("options").length == 1)
        $("#configureGalleryECommerceProductImage").html("<option>n/a</option>");

    if ($("#configureGalleryECommerceProductName").attr("options").length == 1)
        $("#configureGalleryECommerceProductName").html("<option>n/a</option>");

    if ($("#configureGalleryECommerceProductDescription").attr("options").length == 1)
        $("#configureGalleryECommerceProductDescription").html("<option>n/a</option>");

    if ($("#configureGalleryECommerceFullPrice").attr("options").length == 1)
        $("#configureGalleryECommerceFullPrice").html("<option>n/a</option>");
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryUpdateGalleryAfterSetForm(newForm) {
    setWindowSettingsChanged();
    var labelsWithFormItems = new Array();
    for (i = 0; i < configureGallery.galleryEntity.labels.length; i++) {
        var label = configureGallery.galleryEntity.labels[i];
        labelsWithFormItems.push({
            label: label,
            formItem: getConfigureGalleryFormItemById(window.configureGalleryForm, label.id)
        });
    }

    var firstSortItem = getConfigureGalleryFormItemById(
            window.configureGalleryForm, configureGallery.galleryEntity.firstSort);
    var secondSortItem = getConfigureGalleryFormItemById(
            window.configureGalleryForm, configureGallery.galleryEntity.secondSort);

    var itemsWithFormItems = new Array();
    for (i = 0; i < configureGallery.galleryEntity.items.length; i++) {
        var item = configureGallery.galleryEntity.items[i];
        itemsWithFormItems.push({
            item: item,
            formItem: getConfigureGalleryFormItemById(window.configureGalleryForm, item.id)
        });
    }

    window.configureGalleryForm = newForm;
    configureGallery.galleryEntity.items = new Array();
    configureGallery.galleryEntity.labels = new Array();

    var newFormItemsForSort = getConfigureGalleryFormItems(newForm);
    var newFirstSortItem = popConfigureGalleryRelatedFormItem(newFormItemsForSort, firstSortItem);
    if (newFirstSortItem) {
        configureGallery.galleryEntity.firstSort = newFirstSortItem.id;
    }
    var newSecondSortItem = popConfigureGalleryRelatedFormItem(newFormItemsForSort, secondSortItem);
    if (newSecondSortItem) {
        configureGallery.galleryEntity.secondSort = newSecondSortItem.id;
    }


    var newFormItemsForLabel = getConfigureGalleryFormItems(newForm);
    for (var a = 0; a < labelsWithFormItems.length; a++) {
        var labelWithFormItem1 = labelsWithFormItems[a];
        var newFormItem = popConfigureGalleryRelatedFormItem(newFormItemsForLabel, labelWithFormItem1.formItem);
        if (newFormItem) {
            labelWithFormItem1.label.id = newFormItem.id;
            configureGallery.galleryEntity.labels.push(labelWithFormItem1.label);
        }
    }

    var newFormItemsForItem = getConfigureGalleryFormItems(newForm);
    for (var d = 0; d < itemsWithFormItems.length; d++) {
        var itemsWithFormItem = itemsWithFormItems[d];
        var newFormItem1 = popConfigureGalleryRelatedFormItem(newFormItemsForItem, itemsWithFormItem.formItem);
        if (newFormItem1) {
            itemsWithFormItem.item.id = newFormItem1.id;
            configureGallery.galleryEntity.items.push(itemsWithFormItem.item);
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function getConfigureGalleryFormItems(form) {
    var items = new Array();
    for (var i = 0; i < form.items.length; i++) {
        items.push({
            type: form.items[i].type,
            name: form.items[i].name,
            id: form.items[i].id
        });
    }
    return items;
}

// ---------------------------------------------------------------------------------------------------------------------

function popConfigureGalleryRelatedFormItem(newItems, item) {
    for (var i = 0; i < newItems.length; i++) {
        var newItem = newItems[i];
        if (newItem && item && newItem.type == item.type) {
            newItems[i] = null;
            return newItem;
        }
    }

    for (var j = 0; j < newItems.length; j++) {
        var newItem1 = newItems[j];
        if (newItem1) {
            newItems[j] = null;
            return newItem1;
        }
    }

    return null;
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryNavigationThumbnails() {
    createConfigureWindow({
        width: 800,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryNavigationThumbnails")[0]
    });

    configureGalleryNavigationThumbnailsBackgroundColor.setValue(configureGallery.galleryEntity.backgroundColor);
    configureGalleryNavigationThumbnailsBorderColor.setValue(configureGallery.galleryEntity.borderColor);

    var borderStyle = document.getElementById("configureGallryNavigationThumbnailsBorderStyle");
    for (var i = 0; i < borderStyle.options.length; i++) {
        if (borderStyle.options[i].value == configureGallery.galleryEntity.borderStyle) {
            borderStyle.selectedIndex = i;
            break;
        }
    }

    $("#configureGalleryNavigationThumbnailsSmallCellsInfo").html("&nbsp;");
    $("#configureGalleryNavigationThumbnailsColumns").attr("selectedIndex", configureGallery.galleryEntity.columns - 1);
    $("#configureGalleryNavigationThumbnailsRows").attr("selectedIndex", configureGallery.galleryEntity.rows - 1);
    $("#configureGalleryNavigationThumbnailsCellHeight").val(configureGallery.galleryEntity.cellHeight);
    $("#configureGalleryNavigationThumbnailsCellHeight").bind("change", function () {
        var thumbnailHeight = $("#configureGalleryNavigationThumbnailsHeight");
        if (parseInt(thumbnailHeight.val()) > parseInt(this.value)) {
            $("#configureGalleryNavigationThumbnailsSmallCellsInfo").html(
                    "You can not make the image larger than the cell that contains it.");
        }
    });

    $("#configureGalleryNavigationThumbnailsCellWidth").val(configureGallery.galleryEntity.cellWidth);
    $("#configureGalleryNavigationThumbnailsCellWidth").bind("change", function () {
        var thumbnailWidth = $("#configureGalleryNavigationThumbnailsWidth");
        if (parseInt(thumbnailWidth.val()) > parseInt(this.value)) {
            $("#configureGalleryNavigationThumbnailsSmallCellsInfo").html(
                    "You can not make the image larger than the cell that contains it.");
        }
    });

    $("#configureGalleryNavigationThumbnailsWidth").val(configureGallery.galleryEntity.thumbnailWidth);
    $("#configureGalleryNavigationThumbnailsWidth").bind("change", function () {
        var cellWidth = $("#configureGalleryNavigationThumbnailsCellWidth");
        if (parseInt(cellWidth.val()) < parseInt(this.value) + 5) cellWidth.val(parseInt(this.value) + 5);
    });

    $("#configureGalleryNavigationThumbnailsHeight").val(configureGallery.galleryEntity.thumbnailHeight);
    $("#configureGalleryNavigationThumbnailsHeight").bind("change", function () {
        var cellHeight = $("#configureGalleryNavigationThumbnailsCellHeight");
        if (parseInt(cellHeight.val()) < parseInt(this.value) + 5) cellHeight.val(parseInt(this.value) + 5);
    });

    $("#configureGalleryNavigationThumbnailsHorizontalMargin").val(configureGallery.galleryEntity.cellHorizontalMargin);
    $("#configureGalleryNavigationThumbnailsVerticalMargin").val(configureGallery.galleryEntity.cellVerticalMargin);
    $("#configureGalleryNavigationThumbnailsBorderWidth").attr("selectedIndex", configureGallery.galleryEntity.cellBorderWidth);

    // Disable or enable
    disableControls($("#configureGalleryNavigationThumbnailsColumns"), configureGallery.readOnly);
    disableControls($("#configureGallryNavigationThumbnailsBorderStyle"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsRows"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailBackgroundColor"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailBorderColor"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsSmallCellsInfo"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsWidth"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsBorderWidth"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsCellWidth"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsVerticalMargin"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsCellHeight"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsHorizontalMargin"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationThumbnailsHeight"), configureGallery.readOnly);
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryNavigationThumbnailsInfo() {
    $("#configureGalleryNavigationThumbnailsColumnsInfo").html(configureGallery.galleryEntity.columns);
    $("#configureGalleryNavigationThumbnailsRowsInfo").html(configureGallery.galleryEntity.rows);
    $("#configureGalleryNavigationThumbnailsWidthInfo").html(configureGallery.galleryEntity.thumbnailWidth);
    $("#configureGalleryNavigationThumbnailsHeightInfo").html(configureGallery.galleryEntity.thumbnailHeight);
    $("#configureGalleryNavigationThumbnailsVerticalMarginInfo").html(configureGallery.galleryEntity.cellVerticalMargin);
    $("#configureGalleryNavigationThumbnailsBorderWidthInfo").html(configureGallery.galleryEntity.cellBorderWidth);
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryNavigationThumbnails() {
    var thumbnailHeight = $("#configureGalleryNavigationThumbnailsHeight").val();
    var cellHeight = $("#configureGalleryNavigationThumbnailsCellHeight").val();
    if (parseInt(thumbnailHeight) > parseInt(cellHeight)) {
        $("#configureGalleryNavigationThumbnailsSmallCellsInfo").html(
                "You can not make the image larger than the cell that contains it.");
        return;
    }
    var thumbnailWidth = $("#configureGalleryNavigationThumbnailsWidth").val();
    var cellWidth = $("#configureGalleryNavigationThumbnailsCellWidth").val();
    if (parseInt(thumbnailWidth) > parseInt(cellWidth)) {
        $("#configureGalleryNavigationThumbnailsSmallCellsInfo").html(
                "You can not make the image larger than the cell that contains it.");
        return;
    }

    setWindowSettingsChanged();
    closeConfigureWidgetDiv();

    configureGallery.galleryEntity.backgroundColor = configureGalleryNavigationThumbnailsBackgroundColor.getValue();
    configureGallery.galleryEntity.borderColor = configureGalleryNavigationThumbnailsBorderColor.getValue();
    configureGallery.galleryEntity.columns = $("#configureGalleryNavigationThumbnailsColumns").attr("selectedIndex") + 1;
    configureGallery.galleryEntity.rows = $("#configureGalleryNavigationThumbnailsRows").attr("selectedIndex") + 1;
    var borderStyle = document.getElementById("configureGallryNavigationThumbnailsBorderStyle");
    configureGallery.galleryEntity.borderStyle = borderStyle.options[borderStyle.selectedIndex].value;
    configureGallery.galleryEntity.cellHeight = $("#configureGalleryNavigationThumbnailsCellHeight").val();
    configureGallery.galleryEntity.cellWidth = $("#configureGalleryNavigationThumbnailsCellWidth").val();
    configureGallery.galleryEntity.thumbnailWidth = $("#configureGalleryNavigationThumbnailsWidth").val();
    configureGallery.galleryEntity.thumbnailHeight = $("#configureGalleryNavigationThumbnailsHeight").val();
    configureGallery.galleryEntity.cellHorizontalMargin = $("#configureGalleryNavigationThumbnailsHorizontalMargin").val();
    configureGallery.galleryEntity.cellVerticalMargin = $("#configureGalleryNavigationThumbnailsVerticalMargin").val();
    configureGallery.galleryEntity.cellBorderWidth
            = $("#configureGalleryNavigationThumbnailsBorderWidth").attr("selectedIndex");

    upConfigureGalleryUseDefaultLayout();
    updateConfigureGalleryNavigationThumbnailsInfo();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryNavigationLabels() {
    createConfigureWindow({
        width: 600,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryNavigationLabels")[0]
    });

    // add form items on window
    $("#configureGalleryNavigationLabelItems > *").remove();
    var galleryLabels = configureGalleryGetGalleryLabels();
    for (var i = 0; i < galleryLabels.length; i++) {
        var galleryLabel = galleryLabels[i];
        var galleryLabelContainer = document.createElement("tr");
        galleryLabelContainer.className = "configureGalleryNavigationLabel";
        galleryLabelContainer.itemId = galleryLabel.id;
        galleryLabelContainer.itemName = galleryLabel.name;

        galleryLabelContainer.up = document.createElement("img");
        galleryLabelContainer.up.title = "Move Up";
        if (!configureGallery.readOnly) {
            $(galleryLabelContainer.up).click(function () {
                var self = this.parentNode.parentNode;
                var previous = $(self).prev();
                $(previous).before(self);
            });
        }
        galleryLabelContainer.up.src = "/images/up.gif";

        galleryLabelContainer.down = document.createElement("img");
        galleryLabelContainer.down.title = "Move Down";
        galleryLabelContainer.down.src = "/images/down.gif";
        if (!configureGallery.readOnly) {
            $(galleryLabelContainer.down).click(function () {
                var self = this.parentNode.parentNode;
                var previous = $(self).next();
                $(previous).after(self);
            });
        }

        galleryLabelContainer.toTheTop = document.createElement("img");
        galleryLabelContainer.toTheTop.title = "Move to the Top";
        if (!configureGallery.readOnly) {
            $(galleryLabelContainer.toTheTop).click(function () {
                //alert("not implemeted.");
                var self = this.parentNode.parentNode;
                var first = getFirstTr(self);
                if (first != self) {
                    $(first).before(self);
                }
            });
        }
        galleryLabelContainer.toTheTop.src = "/images/toTheTop.gif";

        var position = document.createElement("td");
        $(position).append(galleryLabelContainer.up);
        $(position).append(galleryLabelContainer.down);
        $(position).append(galleryLabelContainer.toTheTop);
        $(galleryLabelContainer).append(position);

        galleryLabelContainer.display = document.createElement("input");
        galleryLabelContainer.display.type = "checkbox";
        galleryLabelContainer.display.checked = galleryLabel.display;
        if (configureGallery.readOnly) galleryLabelContainer.display.disabled = true;

        var displayContainer = document.createElement("td");
        $(displayContainer).append(galleryLabelContainer.display);
        $(galleryLabelContainer).append(displayContainer);

        $(galleryLabelContainer).append("<td>" + galleryLabel.name + "</td>");

        var columnContainer = document.createElement("td");
        galleryLabelContainer.column = document.createElement("select");
        if (configureGallery.readOnly) galleryLabelContainer.column.disabled = true;
        galleryLabelContainer.column.size = 1;
        $(galleryLabelContainer.column).append("<option>Column left</option>");
        $(galleryLabelContainer.column).append("<option>Column center</option>");
        $(galleryLabelContainer.column).append("<option>Column right</option>");
        $(columnContainer).append(galleryLabelContainer.column);
        galleryLabelContainer.column.selectedIndex = galleryLabel.column;


        galleryLabelContainer.itemAlign = document.createElement("select");
        if (configureGallery.readOnly) galleryLabelContainer.itemAlign.disabled = true;
        galleryLabelContainer.itemAlign.size = 1;
        $(galleryLabelContainer.itemAlign).append("<option value=\"LEFT\">Left</option>");
        $(galleryLabelContainer.itemAlign).append("<option value=\"CENTER\">Center</option>");
        $(galleryLabelContainer.itemAlign).append("<option value=\"RIGHT\">Right</option>");

        if (galleryLabel.align == "LEFT") {
            galleryLabelContainer.itemAlign.selectedIndex = 0;
        } else if (galleryLabel.align == "RIGHT") {
            galleryLabelContainer.itemAlign.selectedIndex = 2;
        } else {
            galleryLabelContainer.itemAlign.selectedIndex = 1;
        }

        var alignContainer = document.createElement("td");
        $(alignContainer).append(galleryLabelContainer.itemAlign);
        $(galleryLabelContainer).append(alignContainer);
        $(galleryLabelContainer).append(columnContainer);
        $("#configureGalleryNavigationLabelItems").append(galleryLabelContainer);
    }

    function getFirstTr(tr) {
        var first = $(tr).prevAll();
        if (first.length == 0) {
            return tr;
        } else {
            return first[first.length - 1];
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryDataItems() {
    setWindowSettingsChanged();

    configureGallery.galleryEntity.hideEmpty = $("#configureGalleryDataHideEmpty")[0].checked;
    configureGallery.galleryEntity.items = new Array();
    $(".configureGalleryDataItem").each(function (position) {
        //        alert(this.isVotingFields + ", " + this.isChildSiteLink + ", " + this.isPaypalButtonSettings);
        if (this.isChildSiteLink) {
            updateChildSiteLink(this, position, this.display.checked);
        } else if (this.isVotingFields) {
            updateVotingField(this, position);
        } else if (this.isPaypalButtonSettings) {
            updatePaymentSettings(this, position);
        } else if (this.goToShoppingCart) {
            var item = configureGallery.galleryEntity.paypalSettings;
            item.goToShoppingCartPosition = position;
            item.goToShoppingCartAlign = this.itemAlign.options[this.itemAlign.selectedIndex].value;
            item.goToShoppingCartColumn = this.column.options[this.column.selectedIndex].value;
            item.goToShoppingCartRow = this.row.selectedIndex;
            item.goToShoppingCartName = this.label.value;
            item.goToShoppingCartDisplay = this.display.checked;
        } else if (this.viewPurchaseHistory) {
            var item = configureGallery.galleryEntity.paypalSettings;
            item.viewPurchaseHistoryPosition = position;
            item.viewPurchaseHistoryAlign = this.itemAlign.options[this.itemAlign.selectedIndex].value;
            item.viewPurchaseHistoryColumn = this.column.options[this.column.selectedIndex].value;
            item.viewPurchaseHistoryRow = this.row.selectedIndex;
            item.viewPurchaseHistoryName = this.label.value;
            item.viewPurchaseHistoryDisplay = this.display.checked;
        } else if (this.dataPaginator) {
            var item = configureGallery.galleryEntity.dataPaginator;
            item.dataPaginatorPosition = position;
            item.dataPaginatorAlign = this.itemAlign.options[this.itemAlign.selectedIndex].value;
            item.dataPaginatorColumn = this.column.options[this.column.selectedIndex].value;
            item.dataPaginatorRow = this.row.selectedIndex;
        } else if (this.backToNavigation) {
            configureGallery.galleryEntity.backToNavigation = {
                backToNavigationPosition: position,
                backToNavigationName: this.label.value,
                backToNavigationRow: this.row.selectedIndex,
                backToNavigationAlign: this.itemAlign.options[this.itemAlign.selectedIndex].value,
                backToNavigationColumn: this.display.checked ? this.column.options[this.column.selectedIndex].value : null
            };
        } else if (this.display.checked) {
            configureGallery.galleryEntity.items.push({
                id: this.itemId,
                name: this.label.value,
                width: this.itemWidth ? this.itemWidth.value : 0,
                height: this.itemHeight ? this.itemHeight.value : 0,
                row: this.row.selectedIndex,
                column: this.column.options[this.column.selectedIndex].value,
                align: this.itemAlign.options[this.itemAlign.selectedIndex].value,
                type: this.type,
                crop: this.itemCrop ? this.itemCrop.checked : false
            });
        }
    });

    closeConfigureWidgetDiv();
    updateConfigureGalleryAdvancedInfo();
    upConfigureGalleryUseDefaultLayout();

    function updateVotingField(field, position) {
        var item = (field.votingItemName == window.voteStars.itemName) ? window.voteStars : window.voteLinks;
        item.position = position;
        item.align = field.itemAlign.options[field.itemAlign.selectedIndex].value;
        item.column = field.column.options[field.column.selectedIndex].value;
        item.row = field.row.selectedIndex;
        item.name = field.label.value;
    }

    function updateChildSiteLink(field, position, display) {
        var item = configureGallery.galleryEntity.childSiteLinkData;
        item.position = position;
        item.align = field.itemAlign.options[field.itemAlign.selectedIndex].value;
        item.column = field.column.options[field.column.selectedIndex].value;
        item.row = field.row.selectedIndex;
        item.name = field.label.value;
        item.display = display;
    }

    function updatePaymentSettings(field, position) {
        var item = configureGallery.galleryEntity.paypalSettings;
        item.position = position;
        item.align = field.itemAlign.options[field.itemAlign.selectedIndex].value;
        item.column = field.column.options[field.column.selectedIndex].value;
        item.row = field.row.selectedIndex;
        item.name = field.label.value;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryNavigationLabels() {
    setWindowSettingsChanged();
    configureGallery.galleryEntity.labels = new Array();
    $(".configureGalleryNavigationLabel").each(function () {
        if (this.display.checked) {
            configureGallery.galleryEntity.labels.push({
                id: this.itemId,
                name: this.itemName,
                column: this.column.selectedIndex,
                align: this.itemAlign.options[this.itemAlign.selectedIndex].value
            });
        }
    });

    closeConfigureWidgetDiv();
    updateConfigureGalleryNavigationLabelsInfo();
    upConfigureGalleryUseDefaultLayout();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryDataItems() {
    createConfigureWindow({
        width: 900,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryDataItems")[0]
    });

    $("#configureGalleryDataHideEmpty")[0].checked = configureGallery.galleryEntity.hideEmpty;

    // add form items on window
    $("#сonfigureGalleryDataItemsItems").empty();
    var galleryItems = configureGalleryGetGalleryItems();

    insertSpecialFields();

    for (var i = 0; i < galleryItems.length; i++) {
        var galleryItem = galleryItems[i];
        var galleryItemContainer = document.createElement("tr");
        galleryItemContainer.className = "configureGalleryDataItem";
        galleryItemContainer.itemId = galleryItem.id;
        galleryItemContainer.type = galleryItem.type;
        galleryItemContainer.itemName = galleryItem.name;
        galleryItemContainer.isVotingFields = galleryItem.hideCheckbox;
        galleryItemContainer.isChildSiteLink = galleryItem.childSiteLink;
        galleryItemContainer.dataPaginator = galleryItem.dataPaginator;
        galleryItemContainer.isPaypalButtonSettings = galleryItem.paypalButtonSettings;
        galleryItemContainer.goToShoppingCart = galleryItem.goToShoppingCart;
        galleryItemContainer.viewPurchaseHistory = galleryItem.viewPurchaseHistory;
        galleryItemContainer.backToNavigation = galleryItem.backToNavigation;
        galleryItemContainer.votingItemName = galleryItem.itemName;

        galleryItemContainer.up = document.createElement("img");
        galleryItemContainer.up.title = "Move Up";
        if (!configureGallery.readOnly) {
            $(galleryItemContainer.up).click(function () {
                var self = this.parentNode.parentNode;
                var previous = $(self).prev();
                $(previous).before(self);
            });
        }
        galleryItemContainer.up.src = "/images/up.gif";

        galleryItemContainer.down = document.createElement("img");
        galleryItemContainer.down.title = "Move Down";
        galleryItemContainer.down.src = "/images/down.gif";
        if (!configureGallery.readOnly) {
            $(galleryItemContainer.down).click(function () {
                var self = this.parentNode.parentNode;
                var previous = $(self).next();
                $(previous).after(self);
            });
        }

        galleryItemContainer.toTheTop = document.createElement("img");
        galleryItemContainer.toTheTop.title = "Move to the Top";
        if (!configureGallery.readOnly) {
            $(galleryItemContainer.toTheTop).click(function () {
                var self = this.parentNode.parentNode;
                var first = getFirstTr(self);
                if (first != self) {
                    $(first).before(self);
                }
            });
        }
        galleryItemContainer.toTheTop.src = "/images/toTheTop.gif";

        var position = document.createElement("td");
        $(position).append(galleryItemContainer.up);
        $(position).append(galleryItemContainer.down);
        $(position).append(galleryItemContainer.toTheTop);
        $(galleryItemContainer).append(position);

        galleryItemContainer.display = document.createElement("input");
        galleryItemContainer.display.type = "checkbox";

        var displayContainer = document.createElement("td");
        displayContainer.style.verticalAlign = "middle";
        $(galleryItemContainer).append(displayContainer);
        $(displayContainer).append(galleryItemContainer.display);

        /**
         *We set checkbox property after add it in dom, because only in this case it
         * apply to element in IE7 http://jira.web-deva.com/browse/SW-3694
         */
        if (configureGallery.readOnly) galleryItemContainer.display.disabled = true;
        galleryItemContainer.display.checked = galleryItem.display;
        galleryItemContainer.display.style.verticalAlign = "bottom";
        galleryItemContainer.display.style.display = galleryItem.hideCheckbox
                || galleryItem.paypalButtonSettings || galleryItem.dataPaginator ? "none" : "block";

        $(galleryItemContainer).append("<td>" + galleryItem.itemName + "</td>");

        var labelContainer = document.createElement("td");
        galleryItemContainer.label = document.createElement("input");
        if (configureGallery.readOnly) galleryItemContainer.label.disabled = true;
        galleryItemContainer.label.type = "text";
        galleryItemContainer.label.value = galleryItem.name;
        galleryItemContainer.label.style.display = galleryItem.dataPaginator ? "none" : "block";

        $(labelContainer).append(galleryItemContainer.label);
        $(galleryItemContainer).append(labelContainer);

        var columnContainer = document.createElement("td");
        galleryItemContainer.column = document.createElement("select");
        if (configureGallery.readOnly) galleryItemContainer.column.disabled = true;
        galleryItemContainer.column.size = 1;
        $(galleryItemContainer.column).append("<option value=\"COLUMN_1\">Column 1</option>");
        $(galleryItemContainer.column).append("<option value=\"COLUMN_2\">Column 2</option>");
        $(galleryItemContainer.column).append("<option value=\"COLUMN_3\">Column 3</option>");
        $(galleryItemContainer.column).append("<option value=\"COLUMN_123\">Column 1&2&3</option>");
        $(galleryItemContainer.column).append("<option value=\"COLUMN_12\">Column 1&2</option>");
        $(galleryItemContainer.column).append("<option value=\"COLUMN_23\">Column 2&3</option>");
        $(columnContainer).append(galleryItemContainer.column);
        for (var c = 0; c < galleryItemContainer.column.options.length; c++) {
            if (galleryItemContainer.column.options[c].value == galleryItem.column) {
                galleryItemContainer.column.selectedIndex = c;
                break;
            }
        }
        $(galleryItemContainer).append(columnContainer);

        var rowContainer = document.createElement("td");
        galleryItemContainer.row = document.createElement("select");
        if (configureGallery.readOnly) galleryItemContainer.row.disabled = true;
        galleryItemContainer.column.size = 1;
        $(galleryItemContainer.row).append("<option>Row 1</option>");
        $(galleryItemContainer.row).append("<option>Row 2</option>");
        $(galleryItemContainer.row).append("<option>Row 3</option>");
        $(rowContainer).append(galleryItemContainer.row);
        galleryItemContainer.row.selectedIndex = galleryItem.row;
        $(galleryItemContainer).append(rowContainer);

        if (isItemWithSize(galleryItem.type)) {
            var dimension = document.createElement("td");

            var itemResize = document.createElement("input");
            itemResize.type = "radio";
            itemResize.name = "configureGalleryItem" + i + "CropResize";
            itemResize.id = "configureGalleryItem" + i + "Resize";

            galleryItemContainer.itemCrop = document.createElement("input");
            galleryItemContainer.itemCrop.type = "radio";
            galleryItemContainer.itemCrop.name = "configureGalleryItem" + i + "CropResize";
            galleryItemContainer.itemCrop.id = "configureGalleryItem" + i + "Crop";

            if (galleryItem.crop) galleryItemContainer.itemCrop.checked = true;
            else itemResize.checked = true;

            $(dimension).append(itemResize);
            $(dimension).append("<label for=\"" + itemResize.id + "\">resize</label>");
            $(dimension).append(galleryItemContainer.itemCrop);
            $(dimension).append("<label for=\"" + galleryItemContainer.itemCrop.id + "\">crop</label><br>");

            $(dimension).append("W");
            galleryItemContainer.itemWidth = document.createElement("input");
            if (configureGallery.readOnly) galleryItemContainer.itemWidth.disabled = true;
            galleryItemContainer.itemWidth.type = "text";
            galleryItemContainer.itemWidth.style.width = "25px";
            galleryItemContainer.itemWidth.onkeypress = function (event) {
                return numbersOnly(this, event);
            };
            galleryItemContainer.itemWidth.value = (createItemWidth(galleryItem));
            $(dimension).append(galleryItemContainer.itemWidth);
            $(dimension).append("px H");
            galleryItemContainer.itemHeight = document.createElement("input");
            if (configureGallery.readOnly) galleryItemContainer.itemHeight.disabled = true;
            galleryItemContainer.itemHeight.type = "text";
            galleryItemContainer.itemHeight.onkeypress = function (event) {
                return numbersOnly(this, event);
            };
            galleryItemContainer.itemHeight.style.width = "25px";
            galleryItemContainer.itemHeight.value = (createItemHeight(galleryItem));
            $(dimension).append(galleryItemContainer.itemHeight);
            $(dimension).append("px");

            $(galleryItemContainer).append(dimension);
            $(dimension).css("white-space", "nowrap");
        }
        else
            $(galleryItemContainer).append("<td>&nbsp;</td>");

        galleryItemContainer.itemAlign = document.createElement("select");
        if (configureGallery.readOnly) galleryItemContainer.itemAlign.disabled = true;
        galleryItemContainer.itemAlign.size = 1;
        $(galleryItemContainer.itemAlign).append("<option value=\"LEFT\">left align</option>");
        $(galleryItemContainer.itemAlign).append("<option value=\"CENTER\">center align</option>");
        $(galleryItemContainer.itemAlign).append("<option value=\"RIGHT\">right align</option>");

        if (galleryItem.align == "LEFT") {
            galleryItemContainer.itemAlign.selectedIndex = 0;
        } else if (galleryItem.align == "RIGHT") {
            galleryItemContainer.itemAlign.selectedIndex = 2;
        } else {
            galleryItemContainer.itemAlign.selectedIndex = 1;
        }

        var alignContainer = document.createElement("td");
        $(alignContainer).append(galleryItemContainer.itemAlign);
        $(galleryItemContainer).append(alignContainer);
        $("#сonfigureGalleryDataItemsItems").append(galleryItemContainer);

        function getFirstTr(tr) {
            var first = $(tr).prevAll();
            if (first.length == 0) {
                return tr;
            } else {
                return first[first.length - 1];
            }
        }
    }

    function insertSpecialFields() {
        var specialFields = new Array();

        if (isIncludesVotingModule()) {
            addField("VOTE_STARS", window.voteStars.position);
            addField("VOTE_LINKS", window.voteLinks.position);
        }

        if (isIncludesChildSiteLink()) {
            addField("CHILD_SITE_LINK", configureGallery.galleryEntity.childSiteLinkData.position);
        }

        if (configureGallery.galleryEntity.orientation == "NAVIGATION_ONLY") {
            addField("BACK_TO_NAVIGATION", configureGallery.galleryEntity.backToNavigation.backToNavigationPosition);
        }

        if ($("#configureGalleryECommerceEnable").attr("checked")) {
            addField("PAYPAL_BUTTON", configureGallery.galleryEntity.paypalSettings.position);
        }

        if ($("#configureGalleryECommerceUseCart").attr("checked")) {
            addField("CART_BUTTON", configureGallery.galleryEntity.paypalSettings.goToShoppingCartPosition);
        }

        if ($("#configureECommercePurchaseHistory")[0].selectedIndex != 0) {
            addField("PURCHASE_HISTORY", configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryPosition);
        }

        addField("DATA_DISPLAY_PAGINATOR", configureGallery.galleryEntity.dataPaginator.dataPaginatorPosition);

        // Sort fields by position
        var sorted = sortFieldsByPosition(specialFields);

        // Inserting fields
        $(sorted).each(function () {
            if (this.name == "VOTE_STARS") {
                insertVOTE_STARS();
            } else if (this.name == "VOTE_LINKS") {
                insertVOTE_LINKS();
            } else if (this.name == "CHILD_SITE_LINK") {
                insertCHILD_SITE_LINK();
            } else if (this.name == "BACK_TO_NAVIGATION") {
                insertBACK_TO_NAVIGATION();
            } else if (this.name == "PAYPAL_BUTTON") {
                insertPAYPAL_BUTTON();
            } else if (this.name == "CART_BUTTON") {
                insertCART_BUTTON();
            } else if (this.name == "PURCHASE_HISTORY") {
                insertPURCHASE_HISTORY();
            } else if (this.name == "DATA_DISPLAY_PAGINATOR") {
                insertDATA_DISPLAY_PAGINATOR();
            }
        });

        function addField(name, position) {
            var specialField = new Object();
            specialField.name = name;
            specialField.position = position;
            specialFields.push(specialField);
        }
    }

    function sortFieldsByPosition(specialFields){
        var sorted = new Array();
        $(specialFields).each(function (){
             sorted.push(pullFieldByMinPosition(specialFields));
        });

        return sorted;
    }

    function pullFieldByMinPosition(specialFields){
        var minPos = 9999;
        var minPosIndex = -1;
        $(specialFields).each(function (index){
            if (this.position < minPos){
                minPos = this.position;
                minPosIndex = index;
            }
        });

        var field = specialFields[minPosIndex];
        delete specialFields[minPosIndex];

        return field;
    }

    function insertVOTE_STARS() {
        insertElementIntoArray(galleryItems, window.voteStars, window.voteStars.position);
    }

    function insertVOTE_LINKS() {
        insertElementIntoArray(galleryItems, window.voteLinks, window.voteLinks.position);
    }

    function insertCHILD_SITE_LINK() {
        insertElementIntoArray(galleryItems, configureGallery.galleryEntity.childSiteLinkData,
                configureGallery.galleryEntity.childSiteLinkData.position);
    }

    function insertBACK_TO_NAVIGATION() {
        var backToNavigation = configureGallery.galleryEntity.backToNavigation;
        insertElementIntoArray(
                galleryItems, {
            itemName: "Back to Navigation",
            name: backToNavigation.backToNavigationName,
            align: backToNavigation.backToNavigationAlign,
            column: backToNavigation.backToNavigationColumn ? backToNavigation.backToNavigationColumn : "COLUMN_1",
            row: backToNavigation.backToNavigationRow,
            display: backToNavigation.backToNavigationColumn != null,
            width: null,
            height: null,
            backToNavigation: true
        }, backToNavigation.backToNavigationPosition);
    }

    function insertPAYPAL_BUTTON() {
        configureGallery.galleryEntity.paypalSettings.display = true;
        configureGallery.galleryEntity.paypalSettings.paypalButtonSettings = true;
        insertElementIntoArray(
                galleryItems,
                configureGallery.galleryEntity.paypalSettings,
                configureGallery.galleryEntity.paypalSettings.position
                );
    }

    function insertCART_BUTTON() {
        insertElementIntoArray(
                galleryItems, {
            itemName: "Go to shopping cart",
            goToShoppingCart: true,
            display: configureGallery.galleryEntity.paypalSettings.goToShoppingCartDisplay,
            column: configureGallery.galleryEntity.paypalSettings.goToShoppingCartColumn,
            align: configureGallery.galleryEntity.paypalSettings.goToShoppingCartAlign,
            name: configureGallery.galleryEntity.paypalSettings.goToShoppingCartName,
            row: configureGallery.galleryEntity.paypalSettings.goToShoppingCartRow
        }, configureGallery.galleryEntity.paypalSettings.goToShoppingCartPosition);
    }

    function insertPURCHASE_HISTORY() {
        insertElementIntoArray(
                galleryItems, {
            itemName: "View Purchase History",
            viewPurchaseHistory: true,
            display: configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryDisplay,
            column: configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryColumn,
            align: configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryAlign,
            name: configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryName,
            row: configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryRow
        }, configureGallery.galleryEntity.paypalSettings.viewPurchaseHistoryPosition);
    }

    function insertDATA_DISPLAY_PAGINATOR() {
        insertElementIntoArray(
                galleryItems, {
            itemName: "Data display navigation",
            dataPaginator: true,
            column: configureGallery.galleryEntity.dataPaginator.dataPaginatorColumn,
            align: configureGallery.galleryEntity.dataPaginator.dataPaginatorAlign,
            row: configureGallery.galleryEntity.dataPaginator.dataPaginatorRow
        }, configureGallery.galleryEntity.dataPaginator.dataPaginatorPosition);
    }

    function isItemWithSize(type) {
        for (var i in configureGallery.formItemsWithSize) {
            if (configureGallery.formItemsWithSize[i] == type) {
                return true;
            }
        }
        return false;
    }

    function createItemWidth(galleryItem) {
        if (galleryItem.type == "VIDEO_FILE_UPLOAD") {
            return galleryItem.width ? galleryItem.width : "640";
        } else {
            return galleryItem.width ? galleryItem.width : "";
        }
    }

    function createItemHeight(galleryItem) {
        if (galleryItem.type == "VIDEO_FILE_UPLOAD") {
            return galleryItem.height ? galleryItem.height : "480";
        } else {
            return galleryItem.height ? galleryItem.height : "";
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryGetGalleryItems() {
    var galleryItems = new Array();
    var formItemIds = configureGalleryGetFormItemNameByIds(false);
    for (var i = 0; i < configureGallery.galleryEntity.items.length; i++) {
        var galleryItem = configureGallery.galleryEntity.items[i];
        if (formItemIds["id" + galleryItem.id]) {
            var copyGalleryItem = configureGalleryCopyGalleryItem(galleryItem);
            copyGalleryItem.display = true;
            copyGalleryItem.itemName = formItemIds["id" + galleryItem.id].name;
            copyGalleryItem.type = formItemIds["id" + galleryItem.id].type;
            galleryItems.push(copyGalleryItem);
            formItemIds["id" + galleryItem.id] = null;
        }
    }

    for (var j = 0; j < window.configureGalleryForm.items.length; j++) {
        var formItem = window.configureGalleryForm.items[j];
        if (formItemIds["id" + formItem.id]) {
            galleryItems.push(configureGalleryFormItemToGalleryItem(formItem));
        }

    }
    return galleryItems;
}

// ---------------------------------------------------------------------------------------------------------------------

function getConfigureGalleryFormItemById(form, id) {
    for (var i = 0; i < form.items.length; i++) {
        if (form.items[i].id == id) {
            return form.items[i];
        }
    }
    return null;
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryGetGalleryLabels() {
    var galleryLabels = new Array();
    var formItemIds = configureGalleryGetFormItemNameByIds(true);
    for (var i = 0; i < configureGallery.galleryEntity.labels.length; i++) {
        var galleryLabel = configureGallery.galleryEntity.labels[i];
        if (formItemIds["id" + galleryLabel.id]) {
            var copyGalleryLabel = configureGalleryCopyGalleryLabel(galleryLabel);
            copyGalleryLabel.display = true;
            copyGalleryLabel.name = formItemIds["id" + galleryLabel.id].name;
            galleryLabels.push(copyGalleryLabel);
            formItemIds["id" + galleryLabel.id] = null;
        }
    }

    for (var j = 0; j < window.configureGalleryForm.items.length; j++) {
        var formItem = window.configureGalleryForm.items[j];
        if (formItemIds["id" + formItem.id]) {
            galleryLabels.push(configureGalleryFormItemToGalleryLabel(formItem));
        }
    }
    return galleryLabels;
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryFormItemToGalleryItem(formItem) {
    return {
        id: formItem.id,
        itemName: formItem.name,
        align: "CENTER",
        column: "COLUMN_1",
        row: 0,
        width: null,
        height: null,
        name: "",
        type: formItem.type
    };
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryFormItemToGalleryLabel(formItem) {
    return {
        id: formItem.id,
        name: formItem.name,
        align: "LEFT",
        column: 0
    };
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryCopyGalleryItem(galleryItem) {
    return {
        id: galleryItem.id,
        align: galleryItem.align,
        column: galleryItem.column,
        row: galleryItem.row,
        width: galleryItem.width,
        crop: galleryItem.crop,
        height: galleryItem.height,
        name: galleryItem.name
    };
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryCopyGalleryLabel(galleryLabel) {
    return {
        id: galleryLabel.id,
        align: galleryLabel.align,
        column: galleryLabel.column
    };
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryGetFormItemNameByIds(executeForNavigation) {
    var ids = new Object();
    for (var i = 0; i < window.configureGalleryForm.items.length; i++) {
        var formItem = window.configureGalleryForm.items[i];

        if (!isRestrictedForGallery(formItem.type) &&
                ((executeForNavigation && !isRestrictedForGalleryNavigation(formItem.type)) || !executeForNavigation)) {
            ids["id" + formItem.id] = formItem;
        }
    }
    return ids;
}

var restrictedForGalleryItems;
var restrictedForGalleryNavigationItems;

function initRestrictedItemsForGalleryArray() {
    restrictedForGalleryItems = initFormTypeArray(".galleryRestrictedFields");
    restrictedForGalleryNavigationItems = initFormTypeArray(".galleryNavigationRestrictedFields");

    function initFormTypeArray(type) {
        var array = new Array();
        $(type).each(function() {
            array.push(this.value);
        });

        return array;
    }
}

function isRestrictedForGallery(formItemName) {
    if (!restrictedForGalleryItems) {
        initRestrictedItemsForGalleryArray();
    }

    return formTableManager.isFormItemNameInArray(restrictedForGalleryItems, formItemName);
}

function isRestrictedForGalleryNavigation(formItemName) {
    if (!restrictedForGalleryNavigationItems) {
        initRestrictedItemsForGalleryArray();
    }

    return formTableManager.isFormItemNameInArray(restrictedForGalleryNavigationItems, formItemName);
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryFindGalleryItem(id) {
    for (var i = 0; i < configureGallery.galleryEntity.items.length; i++) {
        if (configureGallery.galleryEntity.items[i].id == id) {
            return configureGallery.galleryEntity.items[i];
        }
    }
    return null;
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryNavigationPageSelector() {
    setWindowSettingsChanged();
    if ($("#configureGalleryNavigationPageSelectorScroll").attr("checked")) {
        $("[name=configureGalleryNavigationPageSelectorScroll]").each(function () {
            if (this.checked) configureGallery.galleryEntity.navigationPaginatorType = this.value;
        });
    } else {
        $("[name=configureGalleryNavigationPageSelectorPaginator]").each(function () {
            if (this.checked) configureGallery.galleryEntity.navigationPaginatorType = this.value;
        });
    }

    closeConfigureWidgetDiv();
    upConfigureGalleryUseDefaultLayout();
    updateConfigureGalleryNavigationPageSelectorInfo();
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryDataPageSelector() {
    setWindowSettingsChanged();
    if ($("#configureGalleryDataPageSelectorPaginator").attr("checked")) {
        // Why we don't use [name=configureGalleryDataPageSelectorArrows][checked=true]?
        // Safary 4 can't understand it and return undefine...
        $("[name=configureGalleryDataPageSelectorPaginator]").each(function () {
            if (this.checked) configureGallery.galleryEntity.dataPaginator.dataPaginatorType = this.value;
        });
    } else {
        configureGallery.galleryEntity.dataPaginator.dataPaginatorType = "ARROWS";
        $("[name=configureGalleryDataPageSelectorArrows]").each(function () {
            if (this.checked) configureGallery.galleryEntity.dataPaginator.dataPaginatorArrow = this.value;
        });
    }

    closeConfigureWidgetDiv();
    upConfigureGalleryUseDefaultLayout();
    updateConfigureGalleryDataPageSelectorInfo();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryNavigationPageSelector() {
    createConfigureWindow({
        width: 700,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryNavigationPageSelector")[0]
    });

    $("[name=configureGalleryNavigationPageSelectorType]").attr("checked", false);
    $("[value=" + configureGallery.galleryEntity.navigationPaginatorType + "]").attr("checked", true);
    var paginator = configureGallery.galleryEntity.navigationPaginatorType;
    if (paginator == "SCROLL_HORIZONTALLY" || paginator == "SCROLL_VERTICALLY") {
        $("#configureGalleryNavigationPageSelectorScroll").attr("checked", true);
    } else {
        $("#configureGalleryNavigationPageSelectorPaginator").attr("checked", true);
    }

    selectConfigureGalleryNavigationPageSelector();
    disablePaginatorGoToSelect();

    if (configureGallery.readOnly) {
        disableControls($("[name=configureGalleryNavigationPageSelectorType]"), configureGallery.readOnly);
        disableControls($("[name=configureGalleryNavigationPageSelectorScroll]"), configureGallery.readOnly);
        disableControls($("[name=configureGalleryNavigationPageSelector]"), configureGallery.readOnly);
        disableControls($("[name=configureGalleryNavigationPageSelectorPaginator]"), configureGallery.readOnly);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGalleryECommerceUseCart() {
    setWindowSettingsChanged();
    disableControls($("#configureGalleryECommerceCart"), !$("#configureGalleryECommerceUseCart").attr("checked")
            || !$("#configureGalleryECommerceEnable").attr("checked"));
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGalleryECommerceEnable() {
    setWindowSettingsChanged();
    var enable = $("#configureGalleryECommerceEnable").attr("checked");
    disableControls($(".configureGalleryECommerce"), !enable);
    changeConfigureGalleryECommerceUseCart();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryECommerceTrackOrderInfo() {
    createConfigureWindow({
        width: 400,
        height: 200,
        disableContentOnCreation: false,
        useItem:$("#configureGalleryECommerceInfo")[0]
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryECommerceRegistrationFormInfo() {
    createConfigureWindow({
        width: 400,
        height: 200,
        disableContentOnCreation: false,
        useItem:$("#configureGalleryECommerceRegistrationFormInfo")[0]
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryAllowPageDatasInfo() {
    createConfigureWindow({
        width: 400,
        height: 200,
        disableContentOnCreation: false,
        useItem:$("#configureGalleryAllowPageDatasInfo")[0]
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryGetFormInfo() {
    createConfigureWindow({
        width: 400,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryGetFormInfo")[0]
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryFormFilterInfo() {
    createConfigureWindow({
        width: 400,
        height: 200,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryGetFormFilterInfo")[0]
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryDataPageSelector() {
    createConfigureWindow({
        width: 700,
        height: 200,
        disableContentOnCreation: false,
        useItem:$("#configureGalleryDataPageSelector")[0]
    });

    if (configureGallery.galleryEntity.dataPaginator.dataPaginatorType == "ARROWS") {
        $("#configureGalleryDataPageSelectorArrow").attr("checked", true);
        $("[value=" + configureGallery.galleryEntity.dataPaginator.dataPaginatorArrow + "]").attr("checked", true);
    } else {
        $("#configureGalleryDataPageSelectorPaginator").attr("checked", true);
        $("[value=" + configureGallery.galleryEntity.dataPaginator.dataPaginatorType + "]").attr("checked", true);
    }

    selectConfigureGalleryDataPageSelector();

    // Don`t replace this "if" with disabling by configureGalleryReadOnly parameter (SW-4894). Tolik
    if (configureGallery.readOnly) {
        disableControls($("[name=configureGalleryDataPageSelectorType]"), true);
        disableControls($("[name=configureGalleryDataPageSelectorPaginator]"), true);
        disableControls($("[name=configureGalleryDataPageSelectorArrows]"), true);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function saveConfigureGalleryNavigationSortOrder() {
    setWindowSettingsChanged();
    closeConfigureWidgetDiv();

    var firstSortType = $("#configureGalleryNavigationSortOrderFirstType").get(0);
    configureGallery.galleryEntity.firstSortType = firstSortType.options[firstSortType.selectedIndex].value;
    var secondSortType = $("#configureGalleryNavigationSortOrderSecondType").get(0);
    configureGallery.galleryEntity.secondSortType = secondSortType.options[secondSortType.selectedIndex].value;
    var firstSort = $("#configureGalleryNavigationSortOrderFirst").get(0);
    configureGallery.galleryEntity.firstSort = firstSort.options[firstSort.selectedIndex].value;
    var secondSort = $("#configureGalleryNavigationSortOrderSecond").get(0);
    configureGallery.galleryEntity.secondSort = secondSort.options[secondSort.selectedIndex].value;

    updateConfigureGalleryAdvancedInfo();
    upConfigureGalleryUseDefaultLayout();
}

// ---------------------------------------------------------------------------------------------------------------------

function configureGalleryFindFormItem(id) {
    for (var i = 0; i < window.configureGalleryForm.items.length; i++) {
        if (window.configureGalleryForm.items[i].id == id) {
            return window.configureGalleryForm.items[i];
        }
    }
    return null;
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryNavigationPageSelectorInfo() {
    var html = window.configureGalleryInternational.navigationPageSelector[
            configureGallery.galleryEntity.navigationPaginatorType];

    if (configureGallery.galleryEntity.navigationPaginatorType == "PICK_LIST_WITH_NUMBERS")
        html += getConfigureGallerySelectedOption("#paginatorGoToSelect", true).innerHTML;

    $("#configureGalleryNavigationPageSelectorInfo").html(html);
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryDataPageSelectorInfo() {
    $("#configureGalleryDataPageSelectorInfo").html(
            window.configureGalleryInternational.dataPageSelector[
                    configureGallery.galleryEntity.dataPaginator.dataPaginatorType]);
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryNavigationSortOrderInfo() {
    var firstSortItem = configureGalleryFindFormItem(configureGallery.galleryEntity.firstSort);
    var secondSortItem = configureGalleryFindFormItem(configureGallery.galleryEntity.secondSort);
    $("#configureGalleryNavigationSortOrderInfo").html(
            (firstSortItem ? firstSortItem.name : "Not set") + ", " + (secondSortItem ? secondSortItem.name : "Not set"));
}

// ---------------------------------------------------------------------------------------------------------------------

function selectConfigureGalleryNavigationPageSelector() {
    var pageSelector = $("#configureGalleryNavigationPageSelectorScroll").attr("checked");
    $("[name=configureGalleryNavigationPageSelectorScroll]").attr("disabled", !pageSelector);
    $("[name=configureGalleryNavigationPageSelectorPaginator]").attr("disabled", pageSelector);
}
// ---------------------------------------------------------------------------------------------------------------------

function disablePaginatorGoToSelect() {
    $("#paginatorGoToSelect")[0].disabled = !$("#configureGalleryNavigationPageSelectorPaginator5")[0].checked;
}
// ---------------------------------------------------------------------------------------------------------------------

function selectConfigureGalleryDataPageSelector() {
    var pageSelector = $("#configureGalleryDataPageSelectorPaginator").attr("checked");
    $("[name=configureGalleryDataPageSelectorPaginator]").attr("disabled", !pageSelector);
    $("[name=configureGalleryDataPageSelectorArrows]").attr("disabled", pageSelector);
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryNavigationLabelsInfo() {
    $("#configureGalleryNavigationLabelsInfo").html("");
    $("#configureGalleryNavigationLabelsDetailInfo").html("");
    for (var i = 0; i < configureGallery.galleryEntity.labels.length; i++) {
        var label = configureGallery.galleryEntity.labels[i];
        var formItem = configureGalleryFindFormItem(label.id);
        var formItemName = formItem ? formItem.name : "Label";
        if (i > 0) {
            $("#configureGalleryNavigationLabelsInfo").append(", ");
        }
        $("#configureGalleryNavigationLabelsInfo").append(formItemName);
        var alignName = "Left";
        if (label.align == "CENTER") {
            alignName = "Center";
        } else if (label.align == "RIGHT") {
            alignName = "Right";
        }
        if (i > 0) {
            $("#configureGalleryNavigationLabelsDetailInfo").append(", ");
        }
        $("#configureGalleryNavigationLabelsDetailInfo").append(alignName);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryDataItemsInfo() {
    $("#configureGalleryDataItemsInfo").html("");
    $("#configureGalleryDataItemsDetailInfo").html("");
    var items = configureGallery.galleryEntity.items.slice(0);
    var childSiteLink = configureGallery.galleryEntity.childSiteLinkData;
    if (childSiteLink && childSiteLink.display) {// Adding childSiteLink to displayed items.
        insertElementIntoArray(items, childSiteLink, childSiteLink.position);
    }
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var name = createName(item);

        addItemInfo((i > 0), name);

        var columnInfo = createReadableColumnName(item.column);

        addItemInfoDetails(name, columnInfo, item.align);
    }


    function addItemInfo(addComa, info) {
        if (addComa) {
            $("#configureGalleryDataItemsInfo").append(", ");
        }
        $("#configureGalleryDataItemsInfo").append(info);
    }

    function addItemInfoDetails(name, columnInfo, align) {
        $("#configureGalleryDataItemsDetailInfo").append(name + ": column " + columnInfo + ": " + align + "<br>");
    }

    function createName(item) {
        if (item.childSiteLink) {
            return "Text link to child site";
        } else {
            var formItem = configureGalleryFindFormItem(item.id);
            return (formItem ? formItem.name : "Item");
        }
    }

    function createReadableColumnName(column) {
        var columnInfo;
        switch (column) {
            default:{
                columnInfo = "Column 1";
                break;
            }
            case "COLUMN_2":{
                columnInfo = "Column 2";
                break;
            }
            case "COLUMN_3":{
                columnInfo = "Column 3";
                break;
            }
            case "COLUMN_123":{
                columnInfo = "Column 1&2&3";
                break;
            }
            case "COLUMN_12":{
                columnInfo = "Column 1&2";
                break;
            }
            case "COLUMN_23":{
                columnInfo = "Column 2&3";
                break;
            }
        }
        return columnInfo;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function changeConfigureGallerytDefaultLayoutGroup() {
    $("#configureGalleryDefaultLayout").empty();

    var layoutGroups = document.getElementById("configureGalleryDefaultLayoutGroups");
    var layoutGroupName = layoutGroups.options[layoutGroups.selectedIndex].value;
    var layoutGroup = window.configureGalleryDefault[layoutGroupName];
    for (var layoutName in layoutGroup) {
        var layout = layoutGroup[layoutName];
        layout.groupName = layoutGroupName;
        layout.name = layoutName;
        layout.custom = false;
        showConfigureGalleryDefaultLayout(layout);
    }

    showConfigureGalleryDefaultLayout({
        groupName: layoutGroupName,
        name: null,
        image: "/images/gallery/customLayoutImage.png",
        thumbnail: "/images/gallery/customLayoutThumbnail.png",
        custom: true
    });

    if (!configureGallery.galleryEntity.orientationLayout) {
        // if layout custom it must stay custom
        $("#configureGalleryDefaultCustomLayout").click();
    } else {
        var layoutSelector = document.getElementById(
                "configureGalleryDefaultLayout" + configureGallery.galleryEntity.orientation
                        + configureGallery.galleryEntity.orientationLayout);
        if (layoutSelector) {
            // if this group containt selected for gallery layout higlight it
            $(layoutSelector).click();
        } else {
            // Else select first allow layout for group and apply its
            $("#configureGalleryDefaultLayout" + layoutGroupName + "test1").click();
        }
    }

    $("#configureGalleryAllowPagesBlock").hide();
    $("#configureGalleryAllowWidgetDatasBlock").hide();

    if (layoutGroupName == "NAVIGATION_ONLY") {
        $("#configureGalleryAllowPagesBlock").show();
        $("#configureGalleryAllowWidgetDatasBlock").show();
    }

    getActiveWindow().resize();
}

// ---------------------------------------------------------------------------------------------------------------------

function updateConfigureGalleryAdvancedInfo() {
    updateConfigureGalleryNavigationLabelsInfo();
    updateConfigureGalleryNavigationSortOrderInfo();
    updateConfigureGalleryDataItemsInfo();
    updateConfigureGalleryNavigationThumbnailsInfo();
    updateConfigureGalleryNavigationPageSelectorInfo();
    updateConfigureGalleryDataPageSelectorInfo();
}

// ---------------------------------------------------------------------------------------------------------------------

function upConfigureGalleryUseDefaultLayout() {
    $("#configureGalleryDefaultCustomLayout").click();
    configureGallery.galleryEntity.orientationLayout = null;
}

// ---------------------------------------------------------------------------------------------------------------------

/**
 * Add to list now show layouts new layout. Need three parameters: thumbnail - small image, image is image
 * that show when you select this layout, onClick - execute when you select layout.
 */
function showConfigureGalleryDefaultLayout(layout) {
    var layoutContainer = document.createElement("td");
    $("#configureGalleryDefaultLayout").append(layoutContainer);

    var layoutSelectorId = "configureGalleryDefaultLayout" + layout.groupName + layout.name;
    if (layout.custom) {
        layoutSelectorId = "configureGalleryDefaultCustomLayout";
    }

    $(layoutContainer).append(
            "<input type=\"radio\" name=\"configureGalleryDefaultLayout\" id=\"" + layoutSelectorId + "\"><br>");

    var layoutLabel = document.createElement("label");
    layoutLabel.htmlFor = layoutSelectorId;
    $(layoutContainer).append(layoutLabel);

    var layoutThumbnail = document.createElement("img");
    layoutThumbnail.width = 70;
    layoutThumbnail.height = 51;
    layoutThumbnail.src = layout.thumbnail;
    $(layoutLabel).append(layoutThumbnail);

    $("#" + layoutSelectorId).click(function () {
        if (!layout.custom && !configureGallery.galleryEntity.orientationLayout) {
            if (!confirm("You are about to discard all of your custom gallery layout " +
                    "settings. This action is not reversible. Clicks Ok to proceed.")) {
                // this line need only for IE7... it not restore check
                $("#configureGalleryDefaultCustomLayout").attr("checked", true);
                return false;
            }
        }

        $("#configureGalleryDefaultLayoutImage").attr("src", layout.image);

        configureGallery.galleryEntity.orientationLayout = layout.name;
        if (!layout.custom) {
            setWindowSettingsChanged();
            configureGallery.galleryEntity.orientation = layout.groupName;
            configureGallery.galleryEntity.firstSortType = getValueOrDefault(layout.firstSortType, "ASCENDING");
            configureGallery.galleryEntity.secondSortType = getValueOrDefault(layout.secondSortType, "ASCENDING");
            configureGallery.galleryEntity.rows = getValueOrDefault(layout.rows, 1);
            configureGallery.galleryEntity.columns = getValueOrDefault(layout.columns, 2);
            configureGallery.galleryEntity.cellWidth = getValueOrDefault(layout.cellWidth, 100);
            configureGallery.galleryEntity.cellHeight = getValueOrDefault(layout.cellHeight, 100);
            configureGallery.galleryEntity.thumbnailWidth = getValueOrDefault(layout.thumbnailWidth, 50);
            configureGallery.galleryEntity.thumbnailHeight = getValueOrDefault(layout.thumbnailHeight, 50);
            configureGallery.galleryEntity.cellVerticalMargin = getValueOrDefault(layout.cellVerticalMargin, 0);
            configureGallery.galleryEntity.cellHorizontalMargin = getValueOrDefault(layout.cellHorizontalMargin, 0);
            configureGallery.galleryEntity.cellBorderWidth = getValueOrDefault(layout.cellBorderWidth, 0);
            configureGallery.galleryEntity.cellBorderStyle = getValueOrDefault(layout.cellBorderStyle, "none");
            configureGallery.galleryEntity.navigationPaginatorType = getValueOrDefault(layout.navigationPaginatorType, "PREVIOUS_NEXT_WITH_NUMBERS");

            var formItemsForSort = getConfigureGalleryFormItems(window.configureGalleryForm);
            var newFormItemFirstSort = popConfigureGalleryRelatedFormItem(
                    formItemsForSort, {type: layout.firstSort});
            if (newFormItemFirstSort) {
                configureGallery.galleryEntity.firstSort = newFormItemFirstSort.id;
            }
            var newFormItemSecondSort = popConfigureGalleryRelatedFormItem(
                    formItemsForSort, {type: layout.secondSort});
            if (newFormItemFirstSort) {
                configureGallery.galleryEntity.secondSort = newFormItemSecondSort.id;
            }

            var formItemsForLabel = getConfigureGalleryFormItems(window.configureGalleryForm);
            configureGallery.galleryEntity.labels = new Array();
            $(layout.labels).each(function () {
                var newFormItem = popConfigureGalleryRelatedFormItem(formItemsForLabel, this);
                if (newFormItem) {
                    var galleryLabel = configureGalleryCopyGalleryLabel(this);
                    galleryLabel.id = newFormItem.id;
                    configureGallery.galleryEntity.labels.push(galleryLabel);
                }
            });

            var formItemsForItem = getConfigureGalleryFormItems(window.configureGalleryForm);
            configureGallery.galleryEntity.items = new Array();
            $(layout.items).each(function () {
                var newFormItem = popConfigureGalleryRelatedFormItem(formItemsForItem, this);
                if (newFormItem) {
                    var galleryItem = configureGalleryCopyGalleryItem(this);
                    if (!galleryItem.row) galleryItem.row = 0;
                    galleryItem.name = "";
                    galleryItem.id = newFormItem.id;
                    configureGallery.galleryEntity.items.push(galleryItem);
                }
            });

            updateConfigureGalleryAdvancedInfo();
        }
        return true;
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function getValueOrDefault(value, defaultValue) {
    if (value == null || value == undefined) return defaultValue;
    return value;
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryLayout() {
    var layoutGroups = document.getElementById("configureGalleryDefaultLayoutGroups");
    for (var i = 0; i < layoutGroups.options.length; i++) {
        if (layoutGroups.options[i].value == configureGallery.galleryEntity.orientation) {
            layoutGroups.selectedIndex = i;
            break;
        }
    }

    changeConfigureGallerytDefaultLayoutGroup();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryNavigationSortOrder() {
    createConfigureWindow({
        width: 700,
        height: 500,
        disableContentOnCreation: false,
        useItem: $("#configureGalleryNavigationSortOrder")[0]
    });

    var firstSortType = $("#configureGalleryNavigationSortOrderFirstType").get(0);
    for (var j = 0; j < firstSortType.options.length; j++) {
        if (firstSortType.options[j].value == configureGallery.galleryEntity.firstSortType) {
            firstSortType.selectedIndex = j;
            break;
        }
    }

    var secondSortType = $("#configureGalleryNavigationSortOrderSecondType").get(0);
    for (var m = 0; m < secondSortType.options.length; m++) {
        if (secondSortType.options[m].value == configureGallery.galleryEntity.secondSortType) {
            secondSortType.selectedIndex = m;
            break;
        }
    }

    $("#configureGalleryNavigationSortOrderFirst").empty();
    $("#configureGalleryNavigationSortOrderSecond").empty();
    for (var i = 0; i < window.configureGalleryForm.items.length; i++) {
        var formItem = window.configureGalleryForm.items[i];

        if (!isRestrictedForGallery(formItem.type) && !isRestrictedForGalleryNavigation(formItem.type)) {
            $("#configureGalleryNavigationSortOrderFirst").append(
                    "<option value=\"" + formItem.id + "\">" + formItem.name + "</option>");
            $("#configureGalleryNavigationSortOrderSecond").append(
                    "<option value=\"" + formItem.id + "\">" + formItem.name + "</option>");

            if (configureGallery.galleryEntity.firstSort == formItem.id) {
                $("#configureGalleryNavigationSortOrderFirst").get(0).selectedIndex = i;
            }
            if (configureGallery.galleryEntity.secondSort == formItem.id) {
                $("#configureGalleryNavigationSortOrderSecond").get(0).selectedIndex = i;
            }
        }
    }

    disableControls($("#configureGalleryNavigationSortOrderFirst"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationSortOrderSecond"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationSortOrderFirstType"), configureGallery.readOnly);
    disableControls($("#configureGalleryNavigationSortOrderSecondType"), configureGallery.readOnly);
}

// ---------------------------------------------------------------------------------------------------------------------

function disableConfigureGallery() {
    disableControl($("#configureGalleryShowOnlyMyRecords")[0]);
    disableControl($("#configureGalleryFormFilter")[0]);
    disableControl($("#configureGalleryForms")[0]);
    disableControl($("#configureGalleryName")[0]);
    disableControl($("#configureGalleryPagesForData")[0]);
    disableControl($("#configureGallerySitesForData")[0]);
    disableControl($("#configureGalleryDefaultLayoutGroups")[0]);
    disableControls($("input[name=configureGalleryAllowData]"));
    disableControls($("input[name=configureGalleryUploadImages]"));
    disableControls($("input[name=configureGalleryDefaultLayout]"));
    disableControl($("#includesVotingModule")[0]);
    disableControls($("input[name=durationOfVote]"));
    disableControl($("#configureGalleryECommerceEnable")[0]);
    disableControl($("#displaySiteVisitorCommentsPublicly")[0]);
    disableControl($("#includeManageYourVotesLink")[0]);
    disableControl($("#displayVoteDataPublicly")[0]);
    disableControls($(".configureGalleryECommerce"));
    disableControl($("#configureGalleryDataHideEmpty")[0]);

    $("#windowSave", $("#configureGalleryButtons")[0]).hide();
    $("#windowApply", $("#configureGalleryButtons")[0]).hide();
    $("#windowCancel", $("#configureGalleryButtons")[0]).val("Close");

    $("#galleryReadOnlyMessage").show();
    $("#galleryErrors").hide();
}

// ---------------------------------------------------------------------------------------------------------------------

function showConfigureGalleryECommerce() {
    $("#configureGalleryECommerceMerchantEmail").val(configureGallery.galleryEntity.paypalSettings.paypalEmail);
    $("#configureGalleryECommerceEnable").attr("checked", configureGallery.galleryEntity.paypalSettings.enable);
    selectConfigureGalleryOption("#configureGalleryECommerceFullPrice", configureGallery.galleryEntity.paypalSettings.formItemIdWithFullPrice);
    selectConfigureGalleryOption("#configureGalleryECommerceProductName", configureGallery.galleryEntity.paypalSettings.formItemIdWithProductName);
    selectConfigureGalleryOption("#configureGalleryECommerceProductDescription", configureGallery.galleryEntity.paypalSettings.formItemIdWithProductDescription);
    selectConfigureGalleryOption("#configureGalleryECommerceProductImage", configureGallery.galleryEntity.paypalSettings.formItemIdWithProductImage);
    selectConfigureGalleryOption("#configureGalleryECommerceRegistrationForm", configureGallery.galleryEntity.paypalSettings.registrationFormId);
    selectConfigureGalleryOption("#configureECommercePurchaseHistory", configureGallery.galleryEntity.paypalSettings.purchaseHistoryId);
    if (selectConfigureGalleryOption("#configureGalleryECommerceCart", configureGallery.galleryEntity.paypalSettings.shoppingCartId))
        $("#configureGalleryECommerceUseCart").attr("checked", true);
    else $("#configureGalleryECommerceDirectPaypal").attr("checked", true);
}

// ---------------------------------------------------------------------------------------------------------------------

function selectConfigureGalleryOption(item, value) {
    return listSelectOption(item, value);
}

// ---------------------------------------------------------------------------------------------------------------------

function getConfigureGallerySelectedOptionValue(item, noDefaultOption) {
    var option = getConfigureGallerySelectedOption(item, noDefaultOption);
    return option ? option.value : null;
}

function getConfigureGallerySelectedOption(item, noDefaultOption) {
    if ($(item).attr("selectedIndex") < (noDefaultOption ? 0 : 1)) return null;
    return $(item).attr("options")[$(item).attr("selectedIndex")];
}

// ---------------------------------------------------------------------------------------------------------------------

configureGallery.save = function (closeAfterSaving) {
    if (configureGallery.readOnly) return;

    configureGallery.errors.clear();
    var forms = document.getElementById("configureGalleryForms");
    var formsRadio = $("#configureGalleryUploadImagesExists")[0];
    if (formsRadio.checked && forms.selectedIndex < 1) {
        configureGallery.errors.set("formId",
                "Form is not selected. Please, choose one of the existing forms or select another option.",
                [document.getElementById("configureGalleryForms")]);
        return;
    }

    var enableEcommerce = $("#configureGalleryECommerceEnable").attr("checked");
    var selectedRegistrationFormId =
            getConfigureGallerySelectedOptionValue("#configureGalleryECommerceRegistrationForm", true);
    if (!selectedRegistrationFormId && enableEcommerce) {
        configureGallery.errors.set("registrationFormId",
                "Please, select registration form.",
                [$("#configureGalleryECommerceRegistrationForm")[0]]);
        return;
    }

    var useDirectPaypal = $("#configureGalleryECommerceDirectPaypal").attr("checked");
    var selectedShoppingCartId =
            getConfigureGallerySelectedOptionValue("#configureGalleryECommerceCart", false);
    var selectedShoppingCartPageId = $("#configureGalleryECommerceCart > option:selected").attr("pageId");
    if (!selectedShoppingCartId && !useDirectPaypal && enableEcommerce) {
        configureGallery.errors.set("shoppingCartId",
                "Shopping cart should be selected.",
                [$("#configureGalleryECommerceCart")[0]]);
        return;
    }

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.GalleryNameNotUniqueException",
            configureGallery.errors.exceptionAction({errorId:"configureGalleryName", fields:[$("#configureGalleryName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.GalleryNameIncorrectException",
            configureGallery.errors.exceptionAction({errorId:"configureGalleryName", fields:[$("#configureGalleryName")[0]]}));

    getActiveWindow().disableContentBeforeSaveSettings();
    var request = new Object();
    request.gallerySave = configureGallery.galleryEntity;
    request.gallerySave.name = $("#configureGalleryName").val();

    request.gallerySave.paypalSettings.paypalEmail = $("#configureGalleryECommerceMerchantEmail").val();
    request.gallerySave.paypalSettings.enable = enableEcommerce;
    request.gallerySave.paypalSettings.formItemIdWithFullPrice = getConfigureGallerySelectedOptionValue("#configureGalleryECommerceFullPrice", false);
    request.gallerySave.paypalSettings.formItemIdWithProductName = getConfigureGallerySelectedOptionValue("#configureGalleryECommerceProductName", false);
    request.gallerySave.paypalSettings.formItemIdWithProductDescription = getConfigureGallerySelectedOptionValue("#configureGalleryECommerceProductDescription", false);
    request.gallerySave.paypalSettings.formItemIdWithProductImage = getConfigureGallerySelectedOptionValue("#configureGalleryECommerceProductImage", false);
    request.gallerySave.paypalSettings.registrationFormId = selectedRegistrationFormId;
    request.gallerySave.paypalSettings.position = configureGallery.galleryEntity.paypalSettings.position;
    request.gallerySave.paypalSettings.shoppingCartId = useDirectPaypal ? null : selectedShoppingCartId;
    request.gallerySave.paypalSettings.selectedShoppingCartPageId = useDirectPaypal ? null : selectedShoppingCartPageId;
    request.gallerySave.paypalSettings.selectedPurchaseHistoryPageId = $("#configureGalleryECommerceCart > option:selected").attr("pageId");
    request.gallerySave.paypalSettings.purchaseHistoryId = getConfigureGallerySelectedOptionValue("#configureECommercePurchaseHistory", false);

    if (request.gallerySave.paypalSettings.enable
            && $("#configureGalleryECommerceUseCart").attr("checked")
            && !request.gallerySave.paypalSettings.shoppingCartId) {
        configureGallery.errors.set("WRONG_E_COMMERCE_REGISTRATION_FORM_ID",
                window.configureGalleryInternational.eCommerce.selectShoppingCart,
                [document.getElementById("configureGalleryECommerceCart")]);
    }

    if (request.gallerySave.paypalSettings.enable
            && !request.gallerySave.paypalSettings.formItemIdWithFullPrice) {
        configureGallery.errors.set("WRONG_E_COMMERCE_FULL_PRICE",
                window.configureGalleryInternational.eCommerce.selectFullPrice,
                [document.getElementById("configureGalleryECommerceFullPrice")]);
    }

    request.gallerySave.notesComments = $("#GalleryHeader").html();
    request.gallerySave.showNotesComments = $("#showGalleryHeader").val();

    var dataWidget = document.getElementById("configureGalleryAllowWidgetDatas");
    if (dataWidget && dataWidget.selectedIndex > 0) {
        var crossAndWidgetId = dataWidget.options[dataWidget.selectedIndex].value.split("|");
        request.gallerySave.dataWidgetId = crossAndWidgetId[1];
    }

    var pageForData = document.getElementById("configureGalleryPagesForData");
    if (pageForData && pageForData.selectedIndex > 0) {
        request.gallerySave.dataPageId = pageForData.options[pageForData.selectedIndex].value;
    }

    request.gallerySave.formFilterId = 0;
    if ($("#configureGalleryUseFormFilter").attr("checked")) {
        var formFilter = document.getElementById("configureGalleryFormFilter");
        if (formFilter.selectedIndex > -1) {
            request.gallerySave.formFilterId = formFilter.options[formFilter.selectedIndex].value;
        }
    }

    request.gallerySave.showOnlyMyRecords = $("#configureGalleryShowOnlyMyRecords").attr("checked");
    request.gallerySave.includesVotingModule = document.getElementById('includesVotingModule').checked;
    request.gallerySave.voteSettings = createVoteSettings();
    if (request.gallerySave.includesVotingModule) {
        if (request.gallerySave.voteSettings.durationOfVoteLimited) {
            checkVotingDates(request.gallerySave.voteSettings.startDate, request.gallerySave.voteSettings.endDate);
        }
        if (request.gallerySave.voteSettings.includeLinkToManageYourVotes && request.gallerySave.voteSettings.manageYourVotesCrossWidgetId == -1) {
            configureGallery.errors.set("WRONG_MANAGE_YOUR_VOTES_ID",
                    document.getElementById("wrongYourVotesId").value,
                    [document.getElementById("selectAManageYourVotesPage")]);
        }
        if (request.gallerySave.voteSettings.registrationFormIdForVoters == -1) {
            configureGallery.errors.set("WRONG_REGISTRATION_FORM_ID",
                    document.getElementById("wrongRegistrationFormId").value,
                    [document.getElementById("selectARegistrationFormForVoters")]);
        }
    }

    if (configureGallery.errors.hasErrors()) {
        return;
    }

    request.gallerySave.votingStartDateString = document.getElementById("startDateText").value;
    request.gallerySave.votingEndDateString = document.getElementById("endDateText").value;
    request.gallerySave.voteSettings.startDate = null;
    request.gallerySave.voteSettings.endDate = null;
    request.widgetGalleryId = configureGallery.settings.widgetId;
    request.galleryId = configureGallery.galleryEntity.id;
    delete request.gallerySave.voteStars;
    delete request.gallerySave.voteLinks;
    if (!isIncludesChildSiteLink()) {
        delete request.gallerySave.childSiteLinkData;
    }
    serviceCall.executeViaDwr("SaveGalleryService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.galleryId).html(request.gallerySave.name);

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureGallery.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureGallery.settings.widgetId) {
                    if (!configureGallery.galleryEntity.modified) {
                        // It's new gallery, or it has new form
                        contentsWidget();
                    }

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
    });
};

function isIncludesChildSiteLink() {
    if (document.getElementById("configureGalleryUploadImagesExists").checked) {
        var select = document.getElementById("configureGalleryForms");
        return select[select.selectedIndex].attributes["isChildSiteRegistration"].value == "true";
    }
    return false;
}