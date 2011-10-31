var VALUE_DELIMETER = '\u0007';

//Return's true if some of reuired field's are not filled, otherwise return's false.
function collectFilledFormItems(request, widgetId, requiredExceptionList, paymentRequest) {
    request.filledFormItems = new Array();

    collectTextInputFileds(request.filledFormItems, widgetId, requiredExceptionList);
    collectRTFs(request.filledFormItems, widgetId, requiredExceptionList);
    collectRadiobuttonFileds(request.filledFormItems, widgetId, requiredExceptionList);
    collectTextAreaFileds(request.filledFormItems, widgetId, requiredExceptionList);
    collectTwoTextFileds(request.filledFormItems, widgetId, requiredExceptionList);
    collectCheckboxex(request.filledFormItems, widgetId, requiredExceptionList);
    collectPickLists("SELECT", request.filledFormItems, widgetId, requiredExceptionList);
    collectPickLists("SINGLE_CHOICE_OPTION_LIST", request.filledFormItems, widgetId, requiredExceptionList);
    collectPickLists("TWO_PICK_LISTS", request.filledFormItems, widgetId, requiredExceptionList);
    collectPickLists("THREE_PICK_LISTS", request.filledFormItems, widgetId, requiredExceptionList);
    collectPickLists("FIVE_PICK_LISTS", request.filledFormItems, widgetId, requiredExceptionList);
    collectMultiselects(request.filledFormItems, widgetId, requiredExceptionList);
    collectSelectlists(request.filledFormItems, widgetId, requiredExceptionList);
    collectRadiolists(request.filledFormItems, widgetId, requiredExceptionList);
    collectTextFieldAndPickList(request.filledFormItems, widgetId, requiredExceptionList);
    collectAccessGroups(request.filledFormItems, widgetId, requiredExceptionList);
    collectFileInputs(request.filledFormItems, widgetId, requiredExceptionList);
    collectJavienPaymentDetails(widgetId, requiredExceptionList, paymentRequest);
    request.formId = document.getElementById("formId" + widgetId).value;

    return requiredExceptionList.length > 0;
}

//--------------------------------------------COLLECT FORM VALUES FUNCTIONS---------------------------------------------

function collectJavienPaymentDetails(widgetId, requiredExceptionList, paymentRequest) {
    if (paymentBlockExist(widgetId)) {
        if (isPaymentComplete(widgetId)) {
            return;
        }
        if (document.getElementById("creditCard" + widgetId).checked) {
            collectCardInfo(widgetId, paymentRequest);
            paymentRequest.childSiteSettingsId = $("#settingsId" + widgetId).val();
            paymentRequest.childSiteUserId = $("#childSiteUserId" + widgetId).val();
            paymentRequest.chargeType = $("#chargeType" + widgetId).val();
            checkCreditCardInfoForForm(paymentRequest, requiredExceptionList, widgetId);
        }
    }
}

function collectFileInputs(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = $("input[name='fileUploaderName" + widgetId + "']");

    if (inputs != null) {
        for (var i = 0; ; i++) {
            var input = inputs[i];
            if (input == undefined) {
                break;
            }
            if (isVideoImageField(input.id)) {
                continue;
            }
            var elementsId = input.id;
            var filledFormItem = new Object();
            filledFormItem.itemName = document.getElementById(elementsId + "fileUploaderFormItemText").value;
            filledFormItem.formItemName = input.value;
            filledFormItem.position = document.getElementById(elementsId + "fileUploaderPosition").value;
            filledFormItem.formItemId = document.getElementById(elementsId + "fileUploaderItemId").value;
            try {
                filledFormItem.value = document.getElementById(elementsId + "imageKeywords").value;
            } catch(ex) {
                filledFormItem.value = "";
            }
            var value = document.getElementById(elementsId + "TxtFileName").value;
            //Checking if item is required
            var itemRequired = document.getElementById(elementsId + "fileUploaderRequired").value == "true";
            var itemSelected = (value && value != undefined && value != "");
            if (itemRequired && !itemSelected) {
                requiredExceptionList.push(filledFormItem.itemName + " is required.");
            }
            var quality = "";
            if (isVideoField(elementsId)) {
                quality = document.getElementById(elementsId + "Quality").value;
            }
            filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, quality);
            filledFormItems.push(filledFormItem);
        }
    }
}

function collectTextInputFileds(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("TEXT_INPUT_FIELD" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        //Checking if item is required
        if ($(input.attributes["required"]).val() == "true" && (input.value == undefined || input.value == "")) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        checkForError(input, input.id, requiredExceptionList);

        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, input.value);
        filledFormItems.push(filledFormItem);
    }
}

function collectRTFs(filledFormItems, widgetId, requiredExceptionList) {
    var rtfDivs = document.getElementsByName("RTF" + widgetId);

    for (var i = 0; ; i++) {
        var rtfDiv = rtfDivs[i];
        if (rtfDiv == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(rtfDiv).attr("formItemText");
        filledFormItem.formItemName = $(rtfDiv).attr("formItemName");
        filledFormItem.position = $(rtfDiv).attr("position");
        filledFormItem.formItemId = $(rtfDiv).attr("formItemId");
        filledFormItem.value = "";

        var value = getEditorContent("formItemEditorDiv" + filledFormItem.formItemId);

        //Checking if item is required
        if ($(rtfDiv.attributes["required"]).val() == "true" && (value == undefined || value == "")) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        checkForError(rtfDiv, rtfDiv.id, requiredExceptionList);

        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, value);
        filledFormItems.push(filledFormItem);
    }
}

function collectTextFieldAndPickList(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("PICK_LIST_AND_TEXT_FIELD" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        var pickList = document.getElementById(input.id + "_SELECT");

        //Checking if item is required
        if ($(input.attributes["required"]).val() == "true" && (input.value == undefined || input.value == "" || pickList.selectedIndex == "0")) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, pickList.options[pickList.selectedIndex].value);
        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, input.value);
        filledFormItems.push(filledFormItem);
    }
}

function collectPickLists(pickListType, filledFormItems, widgetId, requiredExceptionList) {
    var hiddenInputs = $("input[name='" + pickListType + widgetId + "']");

    if (hiddenInputs != null) {
        for (var i = 0; ; i++) {
            var hiddenInput = hiddenInputs[i];
            if (hiddenInput == undefined) {
                break;
            }

            var selectCount = $(hiddenInput).next()[0].value;
            var wasReqException = false;

            var filledFormItem = new Object();
            filledFormItem.itemName = $(hiddenInput).attr("formItemText");
            filledFormItem.formItemName = $(hiddenInput).attr("formItemName");
            filledFormItem.position = $(hiddenInput).attr("position");
            filledFormItem.formItemId = $(hiddenInput).attr("formItemId");
            filledFormItem.linkedFormItemId = getNullOrValue($(hiddenInput).attr("linkedFormItemId"));
            filledFormItem.formItemDisplayType = getNullOrValue($(hiddenInput).attr("formItemDisplayType"));
            filledFormItem.value = "";

            for (var j = 1; j <= selectCount; j++) {
                var select = document.getElementById($(hiddenInput).attr("formItemText") + j);

                //Checking if item is required
                if ($(hiddenInput.attributes["required"]).val() == "true" && !wasReqException &&
                        (select.selectedIndex == "0" || select.selectedIndex == -1)) {
                    wasReqException = true;
                    requiredExceptionList.push(filledFormItem.itemName + " is required.");
                }

                if (select.selectedIndex != -1) {
                    var linkedFilledFormId = getNullOrValue($(select.options[select.selectedIndex]).attr("linkedFilledFormId"));
                    var linkedFilledFormItemId = getNullOrValue($(select.options[select.selectedIndex]).attr("linkedFilledFormItemId"));
                    if (linkedFilledFormId != null && linkedFilledFormItemId != null) {
                        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, linkedFilledFormId + ";" + linkedFilledFormItemId);
                        //If this select is a linked item then we should write only linked record as a value.
                        continue;
                    }

                    filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, select.options[select.selectedIndex].value);
                }
            }

            filledFormItems.push(filledFormItem);
        }
    }
}

function collectMultiselects(filledFormItems, widgetId, requiredExceptionList) {
    var hiddenInputs = document.getElementsByName("MULITSELECT" + widgetId);

    for (var i = 0; ; i++) {
        var hiddenInput = hiddenInputs[i];
        if (hiddenInput == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(hiddenInput).attr("formItemText");
        filledFormItem.formItemName = $(hiddenInput).attr("formItemName");
        filledFormItem.position = $(hiddenInput).attr("position");
        filledFormItem.formItemId = $(hiddenInput).attr("formItemId");
        filledFormItem.value = "";

        var select = document.getElementById($(hiddenInput).attr("formItemText"));

        //Checking if item is required
        if ($(hiddenInput.attributes["required"]).val() == "true" && (select.selectedIndex == -1)) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        for (var j = 0; ; j++) {
            var option = select.options[j];

            if (option == undefined) {
                break;
            }

            if (option.selected) {
                filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, option.value);
            }
        }

        filledFormItems.push(filledFormItem);
    }
}

function collectSelectlists(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("SELECTION_LIST" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.linkedFormItemId = getNullOrValue($(input).attr("linkedFormItemId"));
        filledFormItem.formItemDisplayType = getNullOrValue($(input).attr("formItemDisplayType"));
        filledFormItem.value = "";

        $(input).parents("tr:first").find(".selectionListItem").each(function () {
            if (this.checked) {
                var linkedFilledFormId = getNullOrValue($(this).attr("linkedFilledFormId"));
                var linkedFilledFormItemId = getNullOrValue($(this).attr("linkedFilledFormItemId"));
                if (linkedFilledFormId != null && linkedFilledFormItemId != null) {
                    filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, linkedFilledFormId + ";" + linkedFilledFormItemId);
                }
            }
        });

        filledFormItems.push(filledFormItem);
    }
}

function collectAccessGroups(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("ACCESS_GROUPS" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        $(input).parents("tr:first").find(".accessGroupItem").each(function () {
            if (this.checked) {
                filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, this.value);

                $(this).parents("div.formGroupDiv:first").find(".formsLimitedTimeDiv").each(function () {
                    if ($(this).find("input[type='checkbox']")[0].checked) {
                        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, $(this).find("select > option:selected").val());
                    } else {
                        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, "INDEFINITE");
                    }
                });
            }
        });

        filledFormItems.push(filledFormItem);
    }
}

function collectRadiolists(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("RADIO_LIST" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.linkedFormItemId = getNullOrValue($(input).attr("linkedFormItemId"));
        filledFormItem.formItemDisplayType = getNullOrValue($(input).attr("formItemDisplayType"));
        filledFormItem.value = "";

        $(input).parents("tr:first").find(".radioListItem").each(function () {
            if (this.checked) {
                var linkedFilledFormId = getNullOrValue($(this).attr("linkedFilledFormId"));
                var linkedFilledFormItemId = getNullOrValue($(this).attr("linkedFilledFormItemId"));
                if (linkedFilledFormId != null && linkedFilledFormItemId != null) {
                    filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, linkedFilledFormId + ";" + linkedFilledFormItemId);
                }
            }
        });

        filledFormItems.push(filledFormItem);
    }
}

function collectCheckboxex(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("CHECKBOX" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        if (input.checked) {
            filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, "Checked");
        } else {
            //Checking if item is required
            if ($(input.attributes["required"]).val() == "true") {
                requiredExceptionList.push("Please check " + filledFormItem.itemName);
            }

            filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, "Unchecked");
        }
        filledFormItems.push(filledFormItem);
    }
}

function collectTextAreaFileds(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("TEXT_AREA" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        trimTextArea(input);

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        //Checking if item is required
        if ($(input.attributes["required"]).val() == "true" && (input.value == undefined || input.value == "")) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, input.value);
        filledFormItems.push(filledFormItem);
    }
}

function collectTwoTextFileds(filledFormItems, widgetId, requiredExceptionList) {
    var hiddenInputs = document.getElementsByName("TWO_TEXT_FIELDS" + widgetId);

    for (var i = 0; ; i++) {
        var hiddenInput = hiddenInputs[i];
        if (hiddenInput == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(hiddenInput).attr("formItemText");
        filledFormItem.formItemName = $(hiddenInput).attr("formItemName");
        filledFormItem.position = $(hiddenInput).attr("position");
        filledFormItem.formItemId = $(hiddenInput).attr("formItemId");
        filledFormItem.value = "";

        var inputs = document.getElementsByName(hiddenInput.id);
        for (var j = 0; ; j++) {
            var input = inputs[j];
            if (input == undefined) {
                break;
            }

            //Checking if item is required
            if ($(hiddenInput.attributes["required"]).val() == "true" && (input.value == undefined || input.value == "")) {
                requiredExceptionList.push(filledFormItem.itemName + " is required.");
                break;
            }

            if (checkForError(input, hiddenInput.id, requiredExceptionList)) {
                break;
            }

            filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, input.value);
        }

        filledFormItems.push(filledFormItem);
    }
}

function collectRadiobuttonFileds(filledFormItems, widgetId, requiredExceptionList) {
    var inputs = document.getElementsByName("RADIOBUTTON" + widgetId);

    for (var i = 0; ; i++) {
        var input = inputs[i];
        if (input == undefined) {
            break;
        }

        var filledFormItem = new Object();
        filledFormItem.itemName = $(input).attr("formItemText");
        filledFormItem.formItemName = $(input).attr("formItemName");
        filledFormItem.position = $(input).attr("position");
        filledFormItem.formItemId = $(input).attr("formItemId");
        filledFormItem.value = "";

        var radioInputs = document.getElementsByName(input.id);
        var someButtonChecked = false;
        for (var j = 0; ; j++) {
            var radioInput = radioInputs[j];
            if (radioInput == undefined) {
                break;
            }

            if (radioInput.checked) {
                someButtonChecked = true;
                filledFormItem.value += getRightFilledFormItemValue(filledFormItem.value, radioInput.value);
            }
        }

        //Checking if item is required
        if ($(input.attributes["required"]).val() == "true" && !someButtonChecked) {
            requiredExceptionList.push(filledFormItem.itemName + " is required.");
        }

        filledFormItems.push(filledFormItem);
    }
}

//----------------------------------------------------MISC FUNCTIONS----------------------------------------------------

function getRightFilledFormItemValue(valueContainer, value) {
    if (!valueContainer || valueContainer.length == 0) {
        return value;
    } else {
        return VALUE_DELIMETER + value;
    }
}

function checkForError(input, inputName, requiredExceptionList) {
    if ($(input).hasClass("notNumberInputError")) {
        requiredExceptionList.push(inputName + " accepts only numbers.");
        return true;
    }
    return false;
}

//Checking numbers for ONLY_NUMBERS FormItemCheckerType
function checkNumbersOnlyFilter(el) {
    if (/(^\d+\.\d+$)|(^\d+$)/.test(el.value) || el.value.length == 0) {
        $(el).removeClass("notNumberInputError");
    } else {
        $(el).addClass("notNumberInputError");
    }
}

//Checking numbers for ONLY_INTEGER_NUMBERS FormItemCheckerType
function checkIntegerNumbersOnlyFilter(el) {
    if (/(^\d+$)/.test(el.value) || el.value.length == 0) {
        $(el).removeClass("notNumberInputError");
    } else {
        $(el).addClass("notNumberInputError");
    }
}

function updateDays(monthSelect) {
    var daysSelect = $(monthSelect).next()[0];
    var month = monthSelect.options[monthSelect.selectedIndex].value;
    var lastSelected = daysSelect.selectedIndex;

    if (month == "January" || month == "March" || month == "May" || month == "July"
            || month == "August" || month == "October" || month == "December") {
        if (daysSelect.options[29] == undefined) {
            var option = document.createElement("option");
            option.value = 29;
            option.innerHTML = 29;
            daysSelect.appendChild(option);
        }
        if (daysSelect.options[30] == undefined) {
            option = document.createElement("option");
            option.value = 30;
            option.innerHTML = 30;
            daysSelect.appendChild(option);
        }
        if (daysSelect.options[31] == undefined) {
            option = document.createElement("option");
            option.value = 31;
            option.innerHTML = 31;
            daysSelect.appendChild(option);
        }
    } else if (month == "April" || month == "June" || month == "September" || month == "November") {
        if (daysSelect.options[31] != undefined) {
            daysSelect.removeChild(daysSelect.options[31]);
        }
        if (daysSelect.options[29] == undefined) {
            option = document.createElement("option");
            option.value = 29;
            option.innerHTML = 29;
            daysSelect.appendChild(option);
        }
        if (daysSelect.options[30] == undefined) {
            option = document.createElement("option");
            option.value = 30;
            option.innerHTML = 30;
            daysSelect.appendChild(option);
        }
        if (lastSelected > 30) {
            daysSelect.selectedIndex = 28;
        }
    } else {
        if (daysSelect.options[31] != undefined) {
            daysSelect.removeChild(daysSelect.options[31]);
        }
        if (daysSelect.options[30] != undefined) {
            daysSelect.removeChild(daysSelect.options[30]);
        }
        if (daysSelect.options[29] != undefined) {
            daysSelect.removeChild(daysSelect.options[29]);
        }
        if (lastSelected > 28) {
            daysSelect.selectedIndex = 28;
        }
    }
}

function updateDaysByYear(yearSelect) {
    var daysSelect = $(yearSelect).prev()[0];
    var monthSelect = $(yearSelect).prev().prev()[0];

    var month = monthSelect.options[monthSelect.selectedIndex].value;
    var year = yearSelect.options[yearSelect.selectedIndex].value;

    var i = year % 4;

    if (month == "February" && i == 0) {
        var option = document.createElement("option");
        option.value = 29;
        option.innerHTML = 29;
        daysSelect.appendChild(option);
    } else if (month == "February" && i != 0 && daysSelect.options[29] != undefined) {
        daysSelect.removeChild(daysSelect.options[29]);
    }
}

function clearForm(widgetId, exceptPrefilled) {
    var form = $(".form" + widgetId);

    //TEXT INPUT FIELDS
    form.find("input[type='text']").each(function() {
        if (!this.disabled) {
            if (!exceptPrefilled ||
                    (exceptPrefilled && ($(this).attr("formItemName") != "FIRST_NAME" &&
                            $(this).attr("formItemName") != "LAST_NAME" &&
                            $(this).attr("formItemName") != "REGISTRATION_EMAIL" &&
                            $(this).attr("formItemName") != "EMAIL"))) {
                this.value = "";
            }
        }
    });

    //SELECTS
    form.find("select").each(function() {
        if (!$(this).attr("multiple")) {
            this.selectedIndex = "0";
        }
    });

    //MULITSELECTS
    form.find("select").each(function() {
        if ($(this).attr("multiple")) {
            while (this.selectedIndex != -1) {
                this.options[this.selectedIndex].selected = false;
            }
        }
    });

    //CHECKBOXES
    form.find("input[type='checkbox']").each(function() {
        this.checked = "";
    });

    //TEXT_AREAS
    form.find("textarea").each(function() {
        this.value = "";
    });

    //RADIOBUTTONS
    form.find("input[type='radio']").each(function() {
        this.checked = "";
    });

    //disabling 'limited time ends in' selects and checkboxes. (this is only for subscription group type field).
    disableControls(form.find(".formsLimitedTimeSelect"));
    disableControls(form.find(".limitedTimeCheckbox"));
}

function formAccessGroupsCheckboxLimitedTimeClick(checkbox) {
    var durationSelect = $(checkbox).parents("div.formsLimitedTimeDiv:first").find("select")[0];

    if ($(checkbox).attr("checked")) {
        disableControl(durationSelect, false);
    } else {
        disableControl(durationSelect);
    }
}

function formAccessGroupsCheckboxClick(checkbox) {
    var durationSelect = $(checkbox).parents("div.formGroupDiv:first").find("select")[0];
    var durationCheckbox = $(checkbox).parents("div.formGroupDiv:first").find(".limitedTimeCheckbox")[0];

    if ($(checkbox).attr("checked")) {
        disableControl(durationCheckbox, false);

        if ($(durationCheckbox).attr("checked")) {
            disableControl(durationSelect, false);
        } else {
            disableControl(durationSelect);
        }
    } else {
        disableControl(durationCheckbox);
        disableControl(durationSelect);
    }
}

function changeLinkedRecordLinkOnReslect(linkedFilledFormId, selectLinkedRecrodDropdown) {
    var showLinkedRecordLink = $(selectLinkedRecrodDropdown).parent().find(".showLinkedRecordLink")[0];

    if (showLinkedRecordLink) {
        var newLinkedFilledFormId = $(selectLinkedRecrodDropdown).find("option:selected").attr("linkedFilledFormId");
        showLinkedRecordLink.href = "javascript:showEditRecordWindow(" + newLinkedFilledFormId + ", " + linkedFilledFormId + ")";
    }
}

function initFormTextEditor(formItemId) {
    var editorDiv = $("#formItemEditorDiv" + formItemId)[0];
    var editorValue = $("#formItemEditorValue" + formItemId).html();

    createEditor({
        width: 300,
        height: 100,
        showLastSavedData: false,
        place: editorDiv,
        disableEditor : false,
        editorId: "formItemEditorDiv" + formItemId,
        value: editorValue,
        root: "../"});
}
