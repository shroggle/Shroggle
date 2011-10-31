var addLinkedField = {};
addLinkedField.errors = null;
addLinkedField.formItemTr = null;
addLinkedField.linkedItemNameOnWindowOpen = null;

/*
 * settings are:
 * linkId - represents 'Edit linked field' link id. We need this parameter to determine which form item we need to edit exacly.
 * */
addLinkedField.show = function (settings) {
    var addLinkedFieldWindow = createConfigureWindow({width:515, height:400});

    var selectedFormId = ($("#formTableSelectedFormId").val() == "null" || $("#formTableSelectedFormId").val() == -1) ?
            null : $("#formTableSelectedFormId").val();

    if (settings.linkId) {
        addLinkedField.formItemTr = $("#" + settings.linkId).parents("tr:first")[0];
    }

    var request = {
        targetFormId: selectedFormId,
        linkedFormItemId: settings.linkId ? getNullOrValue($(addLinkedField.formItemTr).find(".linkedSourceItemId").val()) : null,
        formItemText: settings.linkId ? $(addLinkedField.formItemTr).find(".yourFormStaticTexts").attr("itemName") : null,
        formItemDisplayType: settings.linkId ? $(addLinkedField.formItemTr).find(".linkedItemDisplayType").val() : null
    };

    addLinkedField.linkedItemNameOnWindowOpen = request.formItemText;

    new ServiceCall().executeViaDwr("ShowAddLinkedFieldService", "execute", request, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        addLinkedFieldWindow.setContent(response);

        if (settings.linkId == null) {
            disableControl($("#addLinkedFieldEditSelectedFormLink")[0]);
        }

        addLinkedField.errors = new Errors();
    });
};

addLinkedField.editSelectedForm = function() {
    var formType, formId;
    var selectedOption = $("#linkedFieldFormSelect > option:selected");
    formType = selectedOption.attr("formType");
    formId = selectedOption.val();

    editForm(formType, formId);
};

addLinkedField.createNewForm = function (siteId) {
    manageItems.createItem({itemType:"CUSTOM_FORM", siteId: siteId,
        onAfterClose: function () {
            var oldSelectedFormId = $("#linkedFieldFormSelect > option:selected").val();

            $("#linkedFieldFormSelectLoadingDiv").makeVisible();
            disableControl($("#linkedFieldFormSelect")[0]);
            var serviceCall = new ServiceCall();
            serviceCall.addExceptionHandler(
                    LoginInAccount.EXCEPTION_CLASS,
                    LoginInAccount.EXCEPTION_ACTION);
            serviceCall.executeViaDwr("GetAvailableFormsService", "execute", function (availableForms) {
                var select = $("#linkedFieldFormSelect");
                select.html("");

                //Generating and appending default option.
                var option = getParentWindow().document.createElement("option");
                $(option).val(-1);
                $(option).html($("#selectFormDefaultOption").val());
                select.append(option);

                //Generating and appending available forms.
                $(availableForms).each(function () {
                    var option = getParentWindow().document.createElement("option");
                    $(option).val(this.formId);
                    $(option).attr("formType", this.formType);
                    $(option).html(this.formName);
                    //Preseve old selected form.
                    if (oldSelectedFormId == this.formId) {
                        option.selected = "selected";
                    }

                    select.append(option);
                });

                $("#linkedFieldFormSelectLoadingDiv").makeInvisible();
                disableControl($("#linkedFieldFormSelect")[0], false);
            });
        }
    });

};

addLinkedField.formsSelectOnChange = function () {
    addLinkedField.errors.clear();
    $("#linkedFieldTextEdit").val("");

    var formId = $("#linkedFieldFormSelect > option:selected").val();
    if (formId == -1) {
        disableControl($("#addLinkedFieldEditSelectedFormLink")[0]);
        $("#linkedFieldFormItemSelect").html("<option value='-1'>" + $("#selectFormItemDefaultOption").val() + "</option>");
    } else {
        $("#linkedFieldFormSelectLoadingDiv").css('visibility', 'visible');

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("GetFormItemsService", "executeForLinked", formId, function (formItems) {
            $("#linkedFieldFormItemSelect").html("<option value='-1'>" + $("#selectFormItemDefaultOption").val() + "</option>");
            $(formItems).each(function () {
                var formItemOption = getParentWindow().createElement("option");
                $(formItemOption).val(this.formItemId);
                $(formItemOption).attr("itemName", this.itemName);
                $(formItemOption).html(this.itemName);

                $("#linkedFieldFormItemSelect").append(formItemOption);
            });
            disableControl($("#addLinkedFieldEditSelectedFormLink")[0], {disabled:false});
            $("#linkedFieldFormSelectLoadingDiv").css('visibility', 'hidden');
        });
    }
};

addLinkedField.formItemsSelectOnChange = function () {
    var selectedOption = $("#linkedFieldFormItemSelect > option:selected");
    var formItemId = selectedOption.val();
    if (formItemId == -1) {
        $("#linkedFieldTextEdit").val("");
    } else {
        $("#linkedFieldTextEdit").val(selectedOption.attr("itemName"));
    }
};

addLinkedField.save = function () {
    addLinkedField.errors.clear();

    if ($("#linkedFieldFormSelect > option:selected").val() == -1) {
        addLinkedField.errors.set("AddLinkedFieldFormNotSelectedException", $("#AddLinkedFieldFormNotSelectedException").val(),
                [$("#linkedFieldFormSelect")[0]]);
        return;
    }

    if ($("#linkedFieldFormItemSelect > option:selected").val() == -1) {
        addLinkedField.errors.set("AddLinkedFieldFormItemNotSelectedException", $("#AddLinkedFieldFormItemNotSelectedException").val(),
                [$("#linkedFieldFormItemSelect")[0]]);
        return;
    }

    var itemText = $("#linkedFieldTextEdit").val();
    var formItem = {
        itemName: itemText.trim().length == 0 ? $("#linkedFieldFormItemSelect > option:selected").attr("itemName") : itemText,
        formItemName: "LINKED",
        formItemId: 0,
        required: false,
        linkedFormItemId: $("#linkedFieldFormItemSelect > option:selected").val(),
        formItemDisplayType: $("#linkedFieldItemTypeSelect > option:selected").val()
    };

    var isEdit = $("#isEditLinkedField")[0];
    if (!isEdit) {
        var returnCode = addYourTableRow(formItem);
        if (returnCode == "FAILURE") {
            addLinkedField.errors.set("AddLinkedFieldDuplicateNameException", $("#AddLinkedFieldDuplicateNameException").val(),
                    [$("#linkedFieldTextEdit")[0]]);
            return;
        }
    } else {
        if (addLinkedField.linkedItemNameOnWindowOpen != formItem.itemName) {
            if (isItemTextInUse(formItem.itemName, formItem.formItemName)) {
                addLinkedField.errors.set("AddLinkedFieldDuplicateNameException", $("#AddLinkedFieldDuplicateNameException").val(),
                        [$("#linkedFieldTextEdit")[0]]);
                return;
            }
        }

        //Updating existing item.
        $(addLinkedField.formItemTr).find(".linkedSourceItemId").val(formItem.linkedFormItemId);
        $(addLinkedField.formItemTr).find(".linkedItemDisplayType").val(formItem.formItemDisplayType);
        $(addLinkedField.formItemTr).find(".yourFormStaticTexts").html(formItem.itemName);
        $(addLinkedField.formItemTr).find(".yourFormStaticTexts").attr("itemName", formItem.itemName);
    }

    closeConfigureWidgetDiv();
};

