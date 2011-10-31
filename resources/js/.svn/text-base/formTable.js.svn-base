var formTableManager = {};

formTableManager.mandatoryFormItemNames = undefined;
formTableManager.specialFormItemNames = undefined;
formTableManager.alwaysRequiredFormItemNames = undefined;

formTableManager.initFormTypesArrays = function () {
    formTableManager.mandatoryFormItemNames = initFormTypeArray(".mandatoryFormField");
    formTableManager.specialFormItemNames = initFormTypeArray(".specialFormField");
    formTableManager.alwaysRequiredFormItemNames = initFormTypeArray(".alwaysRequiredFormField");

    function initFormTypeArray(type) {
        var array = new Array();
        $(type).each(function() {
            array.push(this.value);
        });

        return array;
    }
};

formTableManager.isFormFieldAlwaysRequired = function (formItemName) {
    if (!formTableManager.alwaysRequiredFormItemNames) {
        formTableManager.initFormTypesArrays();
    }

    return formTableManager.isFormItemNameInArray(formTableManager.alwaysRequiredFormItemNames, formItemName);
};


formTableManager.isFormFieldMandatory = function (formItemName) {
    if (!formTableManager.mandatoryFormItemNames) {
        formTableManager.initFormTypesArrays();
    }

    return formTableManager.isFormItemNameInArray(formTableManager.mandatoryFormItemNames, formItemName);
};

formTableManager.isFormFieldSpecial = function (formItemName) {
    if (!formTableManager.specialFormItemNames) {
        formTableManager.initFormTypesArrays();
    }

    return formTableManager.isFormItemNameInArray(formTableManager.specialFormItemNames, formItemName);
};

formTableManager.isFormItemNameInArray = function(array, formItemName) {
    if (!array || !formItemName) {
        return false;
    }

    for (var i = 0; i < array.length; i++) {
        if (array[i] == formItemName) {
            return true;
        }
    }

    return false;
};

function isPageBreakBeforeRequiredField() {
    var formItemNames = $("#yourFormTableBody > tr .yourFormStaticTexts");
    var pageBreakIndex;
    for (var i = 0; i < formItemNames.length; i++) {
        if (formItemNames[i].id == "PAGE_BREAK") {
            pageBreakIndex = i;
        }

        if (pageBreakIndex && formTableManager.isFormFieldMandatory(formItemNames[i].id)
                && formItemNames[i].id != "PAYMENT_AREA") {
            return true;
        }
    }

    return false;
}

function clickReq(reqTd) {
    var reqSpan = $(reqTd).find("span")[0];

    if (reqSpan.style.color == "black" || reqSpan.style.color == undefined || reqSpan.style.color == "") {
        reqSpan.style.color = "gray";
        reqSpan.innerHTML = "N";
    } else {
        reqSpan.style.color = "black";
        reqSpan.innerHTML = "Y";
    }
}

function createFormHeaderEditor(header, place, id) {
     createEditor({
        width: 400,
        height: 100,
        showLastSavedData: false,
        place: place,
        disableEditor : false,
        editorId: id,
        value: (header != null ? header : ""),
        root: "../"});
}

function editFormHeader(element) {
    var editDescWindow = createConfigureWindow({width:700, height:500});
    editDescWindow.setContent($("#editHeader")[0].innerHTML);

    var headerHolder = $(element).parents("td:first").find(".headerHolder")[0];
    var headerInlineBlock = $(element).parents("td:first").find(".headerInlineBlock")[0];
    $("#headerTextEditorPlace")[0].headerHolder = headerHolder;
    $("#headerTextEditorPlace")[0].headerInlineBlock = headerInlineBlock;

    createFormHeaderEditor(($(headerHolder).html()).trim(),
            $(editDescWindow.getWindowContentDiv()).find("#headerTextEditorPlace")[0],
            $(headerHolder)[0].id);
}

function saveFormHeader() {
    $("#headerTextEditorPlace")[0].headerHolder.innerHTML
            = getEditorContent($("#headerTextEditorPlace")[0].headerHolder.id);
    $("#headerTextEditorPlace")[0].headerInlineBlock.innerHTML
            = limitName(removeAllHtmlTags(getEditorContent($("#headerTextEditorPlace")[0].headerHolder.id)), 35);
    closeConfigureWidgetDiv();
}

function editInstruction(element) {
    var editDescWindow = createConfigureWindow({width:400, height:100});
    editDescWindow.setContent($("#editInstruction").html());

    var instructionHolder = $(element).parent().find(".instruction")[0];
    /* No trimming here, please — http://jira.web-deva.com/browse/SW-4264. */
    $("#instructionTextarea").val($(instructionHolder).val());
    $("#instructionTextarea")[0].instructionHolder = instructionHolder;
}

function saveInstruction() {
    $($("#instructionTextarea")[0].instructionHolder).val($("#instructionTextarea").val());
    closeConfigureWidgetDiv();
}

function disableFormTables(disable) {
    //Disabling all form links.
    disableControl($("#addFormItemLink")[0], disable);
    disableControl($("#removeFormItemLink")[0], disable);
    disableControl($("#selectAllLink")[0], disable);
    disableControl($("#unselectAllLink")[0], disable);
    disableControl($("#selectAllYourTableLink")[0], disable);
    disableControl($("#unselectAllYourTableeLink")[0], disable);

    //Disabling all form inputs by name.
    disableControls($("input[name='yourFormCheckbox']"), disable);
    disableControls($("input[name='initFormCheckbox']"), disable);
    disableControls($(".yourFormTexts"), disable);

    //Disabling all images.
    disableControls($("img[name='upImages']"), disable);
    disableControls($("img[name='downImages']"), disable);
    disableControls($("img[name='moveImages']"), disable);
    disableControl($("#addFormItemImg")[0], disable);
    disableControl($("#removeFormItemImg")[0], disable);

    //Disabling filter select.
    disableControl($("#filterSelect")[0], disable);

    //Disabling req spans.
    disableControls($("#yourFormTableBody > tr > td[onclick != '']"), disable);

    //Disabling instruction and header edit links.
    disableControls($(".instruction").parent("td").find("a"), disable);

    //Disabling 'Add linked field' link.
    disableControl($("#addLinkedFieldLink")[0], disable);
}

function createNewRowForYourTable(itemNameText, formItemName, formItemId, required, linkedSourceItemId, linkedItemDisplayType,
                                  defaultInstruction) {
    var newTr = document.createElement("tr");
    newTr.className = "yourTableRow";
    var rowCount = getYourTableRowCount();
    newTr.id = rowCount + 1;

    var numberTd = document.createElement("td");
    numberTd.style.width = "15px";
    numberTd.style.textAlign = "center";
    numberTd.className = "numberTd";
    numberTd.innerHTML = rowCount + 1;
    $(numberTd).attr('position', rowCount + 1);

    var selectTd = document.createElement("td");
    selectTd.style.width = "20px";
    if (!formTableManager.isFormFieldMandatory(formItemName)) {
        var ch = document.createElement("input");
        ch.type = "checkbox";
        ch.name = "yourFormCheckbox";
        selectTd.appendChild(ch);
    }

    var arrowTd = document.createElement("td");
    arrowTd.style.width = "50px";
    arrowTd.className = "imagesTd";
    var upImg = document.createElement("img");
    upImg.alt = "";
    upImg.src = "/images/up_arrow.gif";
    upImg.name = "upImages";
    upImg.style.cursor = "pointer";
    upImg.onclick = function () {
        moveUp(upImg);
    };
    arrowTd.appendChild(upImg);
    var downImg = document.createElement("img");
    downImg.alt = "";
    downImg.src = "/images/down_arrow.gif";
    downImg.name = "downImages";
    downImg.style.cursor = "pointer";
    downImg.style.marginLeft = "3px";
    downImg.onclick = function () {
        moveDown(downImg);
    };
    arrowTd.appendChild(downImg);
    var moveImg = document.createElement("img");
    moveImg.alt = "";
    moveImg.src = "/images/arrow-return-180-left.png";
    moveImg.name = "moveImages";
    moveImg.style.cursor = "pointer";
    moveImg.style.marginLeft = "3px";
    moveImg.onclick = function () {
        showMoveToPosition(moveImg);
    };
    arrowTd.appendChild(moveImg);

    var nameTd = document.createElement("td");
    if (formTableManager.isFormFieldSpecial(formItemName)) {
        nameTd.colSpan = "4";
        nameTd.style.textAlign = "center";
    } else if (formItemName == "LINKED") {
        nameTd.colSpan = "2";
        nameTd.style.textAlign = "center";
    }
    nameTd.style.width = "110px";
    var nameTdSpan = document.createElement("span");
    nameTdSpan.id = formItemName;
    nameTdSpan.innerHTML = ignoreHtml(itemNameText) + (formItemName == "HEADER" ? ": " : "" );
    nameTdSpan.className = "yourFormStaticTexts";
    $(nameTdSpan).attr("itemName", ignoreHtml(itemNameText));
    nameTd.appendChild(nameTdSpan);
    var hiddenIdInput = document.createElement("input");
    hiddenIdInput.value = formItemId;
    hiddenIdInput.id = formItemName + "itemId";
    hiddenIdInput.className = "yourItemId";
    hiddenIdInput.type = "hidden";
    nameTd.appendChild(hiddenIdInput);
    if (formItemName == "LINKED") {
        var hiddenLinkedSourceItemId = document.createElement("input");
        hiddenLinkedSourceItemId.value = linkedSourceItemId;
        hiddenLinkedSourceItemId.className = "linkedSourceItemId";
        hiddenLinkedSourceItemId.type = "hidden";
        nameTd.appendChild(hiddenLinkedSourceItemId);

        var hiddentLinkedItemDisplayType = document.createElement("input");
        hiddentLinkedItemDisplayType.value = linkedItemDisplayType;
        hiddentLinkedItemDisplayType.className = "linkedItemDisplayType";
        hiddentLinkedItemDisplayType.type = "hidden";
        nameTd.appendChild(hiddentLinkedItemDisplayType);

        var editLinkedFieldLink = document.createElement("a");
        editLinkedFieldLink.innerHTML = "Edit linked field";
        editLinkedFieldLink.style.marginLeft = "5px";

        var linkUniqueId = "linkUniqueId" + Math.floor(100 * Math.random());
        editLinkedFieldLink.id = linkUniqueId;
        editLinkedFieldLink.href = "javascript:addLinkedField.show({linkId:'" + linkUniqueId + "'})";
        nameTd.appendChild(editLinkedFieldLink);
    }

    var editNameTd = document.createElement("td");
    editNameTd.style.width = "118px";
    var nameInput = document.createElement("input");
    nameInput.className = "txt100 yourFormTexts";
    nameInput.value = itemNameText;
    nameInput.type = "text";
    editNameTd.appendChild(nameInput);

    var reqTd = document.createElement("td");
    reqTd.id = "reqTd" + itemNameText;
    reqTd.style.textAlign = "center";
    reqTd.style.width = "28px";
    if (!formTableManager.isFormFieldAlwaysRequired(formItemName)) {
        reqTd.style.cursor = "pointer";
        reqTd.onclick = function() {
            clickReq(reqTd);
        };
    }
    var reqSpan = document.createElement("span");
    reqSpan.className = "asterisk";
    if (required || formTableManager.isFormFieldAlwaysRequired(formItemName)) {
        reqSpan.style.color = "black";
        reqSpan.innerHTML = "Y";
    } else {
        reqSpan.style.color = "gray";
        reqSpan.innerHTML = "N";
    }
    reqTd.appendChild(reqSpan);

    var instructTd = document.createElement("td");
    instructTd.style.textAlign = "center";
    var instructLink = document.createElement("a");
    instructLink.href = "javascript:void(0)";
    instructLink.onclick = function() {
        if (formItemName == "HEADER") {
            editFormHeader(instructLink);
        } else {
            editInstruction(instructLink);
        }
    };
    instructLink.onmouseover = function () {
        bindTooltip({element:instructLink, contentElement:$(instructLink).parent().find(".instruction"), useValue:true});
    };
    instructLink.innerHTML = "Edit";
    instructLink.style.cursor = "pointer";
    var instructDiv;
    if (formItemName == "HEADER") {
        instructDiv = document.createElement("div");
        instructDiv.className = "headerHolder";
        instructDiv.style.display = "none";
        instructDiv.id = "uniqueEditorId" + Math.floor(100 * Math.random());
    } else {
        instructDiv = document.createElement("input");
        instructDiv.type = "hidden";
        instructDiv.value = defaultInstruction;
        instructDiv.className = "instruction";

        instructDiv.style.display = "none";
        instructTd.appendChild(instructLink);
        instructTd.appendChild(instructDiv);
    }

    newTr.appendChild(numberTd);
    newTr.appendChild(selectTd);
    newTr.appendChild(arrowTd);
    if (!(formTableManager.isFormFieldSpecial(formItemName)) && formItemName != "LINKED") {
        newTr.appendChild(editNameTd);
    }
    newTr.appendChild(nameTd);

    if (formItemName == "HEADER") {
        var headerInlineBlock = document.createElement("div");
        headerInlineBlock.className = "headerInlineBlock";
        headerInlineBlock.style.fontStyle = "italic";
        headerInlineBlock.style.display = "inline";
        var spacer = document.createElement("span");
        spacer.innerHTML = "&nbsp;";

        nameTd.appendChild(headerInlineBlock);
        nameTd.appendChild(spacer);
        nameTd.appendChild(instructLink);
        nameTd.appendChild(instructDiv);
    }

    if (!(formTableManager.isFormFieldSpecial(formItemName))) {
        newTr.appendChild(reqTd);
        newTr.appendChild(instructTd);
    }

    return newTr;
}

function createNewRowForInitTable(formItemInfo, id) {
    var newTr = document.createElement("tr");

    var selectTd = document.createElement("td");
    selectTd.style.width = "20px";
    selectTd.style.textAlign = "center";
    var ch = document.createElement("input");
    ch.type = "checkbox";
    ch.id = id;
    ch.name = "initFormCheckbox";
    selectTd.appendChild(ch);

    var formNameTD = document.createElement("td");
    formNameTD.style.width = "160px";
    formNameTD.style.color = "black";
    var initFormItemNameText = document.createElement("input");
    initFormItemNameText.type = "hidden";
    initFormItemNameText.className = "initFormItemNameText";
    initFormItemNameText.value = formItemInfo.fieldName;
    formNameTD.appendChild(initFormItemNameText);
    var initFormItemName = document.createElement("input");
    initFormItemName.type = "hidden";
    initFormItemName.className = "initFormItemName";
    initFormItemName.value = formItemInfo.formItemName;
    formNameTD.appendChild(initFormItemName);
    var defaultInstruction = document.createElement("input");
    defaultInstruction.type = "hidden";
    defaultInstruction.className = "initFormItemDefaultInstruction";
    defaultInstruction.value = formItemInfo.itemDefaultInstruction;
    formNameTD.appendChild(defaultInstruction);
    var formItemNameLabel = document.createElement("label");
    formItemNameLabel.htmlFor = id;
    formItemNameLabel.innerHTML = formItemInfo.fieldName;
    formNameTD.appendChild(formItemNameLabel);

    var fieldTypeTd = document.createElement("td");
    fieldTypeTd.style.color = "black";
    var fieldTypeLabel = document.createElement("label");
    fieldTypeLabel.htmlFor = id;
    fieldTypeLabel.innerHTML = formItemInfo.itemFieldType;
    fieldTypeTd.appendChild(fieldTypeLabel);

    var descriptionDiv = document.createElement("div");
    formItemInfo.itemDescription = formItemInfo.itemDescription.trim();
    descriptionDiv.innerHTML = "<span style=\"font-weight: bold;\">Description:</span><br>" + formItemInfo.itemDescription;
    descriptionDiv.style.display = "none";
    descriptionDiv.id = "description" + id;

    var descriptionImageDiv = document.createElement("div");
    descriptionImageDiv.style.display = "inline";
    if (!isIE()) {
        descriptionImageDiv.style.cssFloat = "right";
    }
    var descriptionImageSpan = document.createElement("span");
    descriptionImageSpan.className = "inform_mark";
    descriptionImageSpan.innerHTML = "&nbsp;";
    descriptionImageSpan.onmouseover = function() {
        descriptionImageSpan.className = "inform_mark_Over";
        bindTooltip({element:this, contentId:'description' + id, width:400});
    };
    descriptionImageSpan.onmouseout = function() {
        descriptionImageSpan.className = "inform_mark";
    };
    descriptionImageDiv.appendChild(descriptionImageSpan);

    if (formItemInfo.itemDescription.length > 0) {
        fieldTypeTd.appendChild(descriptionImageDiv);
        fieldTypeTd.appendChild(descriptionDiv);
    }

    newTr.appendChild(selectTd);
    newTr.appendChild(formNameTD);
    newTr.appendChild(fieldTypeTd);

    return newTr;
}

function unselectAll() {
    selectCheckboxes($("input[name='initFormCheckbox']"), false);
}

function selectAll() {
    selectCheckboxes($("input[name='initFormCheckbox']"), true);
}

function selectAllYourTable() {
    selectCheckboxes($("input[name='yourFormCheckbox']"), true);
}

function unselectAllYourTable() {
    selectCheckboxes($("input[name='yourFormCheckbox']"), false);
}

function selectCheckboxes(checkboxesArray, checked) {
    $(checkboxesArray).each(function () {
        this.checked = checked;
    });
}

function removeFormItem() {
    $("input[name='yourFormCheckbox']:checked").each(function () {
        $(this).parents("tr:first").remove();
    });
    hideDownUpImagesAndNumberRows();
}

function updateByFilter() {
    var filterType = $("#filterSelect > option:selected").val();

    createLoadingArea({element:$("#initFormDiv")[0], text: "Reloading fields...", color: "green", guaranteeVisibility:true});

    if (filterType != "-1") {
        new ServiceCall().executeViaDwr("ShowFormService", "updateByFilter", filterType, function(formItemNames) {
            initInitTable(formItemNames);
            removeLoadingArea();
        });
    }
}

function initInitTable(formItemNames) {
    removeAllInitTableRows();
    addInitTableRows(formItemNames);
}

function initYourTable(formItems) {
    removeAllYourTableRows();
    addYourTableRows(formItems);
}

function collectFormItems(request) {
    var trs = $("#yourFormTableBody > tr");
    var formItems = new Array();

    for (var i = 0; ; i++) {
        var tr = trs[i];
        if (tr == undefined) {
            break;
        }

        var formItem = new Object();
        var formItemName = $(tr).find(".yourFormStaticTexts")[0];
        formItem.formItemName = formItemName.id;

        if (!(formTableManager.isFormFieldSpecial(formItem.formItemName))) {
            if (formItem.formItemName == "LINKED") {
                formItem.itemName = $(tr).find(".yourFormStaticTexts").attr("itemName");
            } else {
                formItem.itemName = $(tr).find(".yourFormTexts").val();
            }


            var asterisk = $(tr).find(".asterisk")[0];
            formItem.required = asterisk.style.color == "black";
            formItem.instruction = $(tr).find(".instruction").val();
        } else {
            formItem.itemName = formItemName.id;
        }

        formItem.formItemId = $(tr).find(".yourItemId").val();
        if ($(tr).find(".linkedSourceItemId")[0] && formItem.formItemName == "LINKED") {
            formItem.linkedFormItemId = $(tr).find(".linkedSourceItemId").val();
            formItem.linkedFormItemId = (formItem.linkedFormItemId == "null") ? null : formItem.linkedFormItemId;
            formItem.formItemDisplayType = $(tr).find(".linkedItemDisplayType").val();
        }

        if (formItem.formItemName == "PAYMENT_AREA") {
            formItem.required = true;
        }

        if (formItem.formItemName == "HEADER") {
            formItem.instruction = $(tr).find(".headerHolder").html();
        }

        if (isItemTextInUseInArray(formItems, formItem)) {
            return false;
        }

        formItems.push(formItem);
    }

    request.formItems = formItems;

    return true;
}

function removeAllYourTableRows() {
    var yourTable = document.getElementById("yourFormTableBody");

    while (yourTable.firstChild) {
        yourTable.removeChild(yourTable.firstChild);
    }
}

function removeAllInitTableRows() {
    var initTable = document.getElementById("formTableBody");

    while (initTable.firstChild) {
        initTable.removeChild(initTable.firstChild);
    }
}

function addInitTableRows(formItemNames) {
    var initTable = $("#formTableBody");
    $(formItemNames).each(function(index) {
        $(initTable).append(createNewRowForInitTable(this, index));
    });
}

function addYourTableRows(formItems) {
    $(formItems).each(function() {
        addYourTableRow(this);
    });
}

function addYourTableRow(formItem) {
    if (isItemTextInUse(formItem.itemName, formItem.formItemName)) {
        addFadingTimeoutEvent($("#dublicateFieldExceptionText")[0], 4000);
        return "FAILURE";
    }

    var yourTable = $("#yourFormTableBody");
    var newRow = createNewRowForYourTable(formItem.itemName, formItem.formItemName, formItem.formItemId,
            formItem.required, formItem.linkedFormItemId, formItem.formItemDisplayType, formItem.defaultInstruction ? formItem.defaultInstruction : "");
    yourTable.append(newRow);

    hideDownUpImagesAndNumberRows();

    return "SUCCESS";
}

function getYourTableRowCount() {
    return $(".yourTableRow").length;
}

function addFormItem() {
    $("input[name='initFormCheckbox']:checked").each(function() {
        var formItem = {
            itemName: $(this).parents("tr:first").find(".initFormItemNameText").val(),
            formItemName: $(this).parents("tr:first").find(".initFormItemName").val(),
            defaultInstruction:  $(this).parents("tr:first").find(".initFormItemDefaultInstruction").val(),
            formItemId: 0,
            required: false
        };

        addYourTableRow(formItem);
    });

    unselectAll();
    hideDownUpImagesAndNumberRows();
}

function isItemTextInUse(itemName, formItemName) {
    if (formTableManager.isFormFieldSpecial(formItemName)) {
        return false;
    }

    var inUse = false;
    $("#yourFormTableBody > tr").each(function() {
        var existingName = $(this).find("input.yourFormTexts").val();

        if (!existingName) {
            existingName = $(this).find(".yourFormStaticTexts").attr("itemName");
        }

        if (existingName == itemName) {
            inUse = true;
        }
    });

    return inUse;
}

function isItemTextInUseInArray(itemsArray, newItem) {
    var inUse = false;
    $(itemsArray).each(function () {
        if (!formTableManager.isFormFieldSpecial(this.itemName) && this.itemName == newItem.itemName) {
            inUse = true;
        }
    });

    return inUse;
}

function moveUp(upImg) {
    var tr = $(upImg).parents("tr:first");
    var upperTr = $(tr).prev();

    // Remove current tr from current position.
    $(tr).remove();

    // Add current tr before tr that is upper than current.
    $(upperTr).before(tr);

    hideDownUpImagesAndNumberRows();

    if (!isPageBreakBeforeRequiredField()) {
        getErrorBlockAccordingToOpenedWindow().remove("PageBreakBeforeRequiredFieldsException");
    }
}

function moveDown(downImg) {
    var tr = $(downImg).parents("tr:first");
    var lowerTr = $(tr).next()[0];

    // Remove current tr from current position.
    $(tr).remove();

    // Add current tr after tr that is lower than current.
    $(lowerTr).after(tr);

    hideDownUpImagesAndNumberRows();

    if (!isPageBreakBeforeRequiredField()) {
        getErrorBlockAccordingToOpenedWindow().remove("PageBreakBeforeRequiredFieldsException");
    }
}

function showMoveToPosition(moveImg) {
    var moveToPositionWindow = createConfigureWindow({width:350, height:150});
    moveToPositionWindow.setContent($("#moveToPositionWindowContent").html());
    var currentPosition = $(moveImg).parents("tr:first").find("td.numberTd").attr("position");
    $("#currentRowPosition").val(currentPosition);
}

function moveToPosition(currentPosition, newPosition) {
    if (newPosition == currentPosition) {
        return;
    }

    var tableRows = $("#yourFormTableBody").find("tr");

    var yourTableBody = $("#yourFormTableBody");
    var currentTr = null;
    var trByNewPosition = null;

    tableRows.each(function (index) {
        if (index + 1 == currentPosition) {
            currentTr = this;
            return null;
        }
    });

    tableRows.each(function (index) {
        if (index + 1 == newPosition) {
            trByNewPosition = this;
            return null;
        }
    });

    $(currentTr).remove();

    if (trByNewPosition != null) {
        if (currentPosition > newPosition) {
            $(trByNewPosition).before(currentTr);
        } else {
            $(trByNewPosition).after(currentTr);
        }
    } else {
        $(yourTableBody).append(currentTr);
    }

    hideDownUpImagesAndNumberRows();
    closeConfigureWidgetDiv();
}

function hideDownUpImagesAndNumberRows() {
    var tableRows = $("#yourFormTableBody").find("tr");

    // At first, lets show all manage row position images.
    $(tableRows).find("td.imagesTd").find("img").css('visibility', 'visible');

    // Now, lets hide 'move up' image for first row, and 'move down' image for last row.
    for (var i = 0; i < tableRows.length; i++) {
        var tableRow = tableRows[i];
        if (i == 0) {
            $(tableRow).find("img[name='upImages']").css('visibility', 'hidden');
        } else if (i == tableRows.length - 1) {
            $(tableRow).find("img[name='downImages']").css('visibility', 'hidden');
        }
    }

    // At last, nubmer every row.
    $(tableRows).find("td.numberTd").each(function (index) {
        $(this).html(index + 1);
        $(this).attr('position', index + 1);
    });
}

function getErrorBlockAccordingToOpenedWindow(){
    if (getActiveWindow().registrationWindow){
        return new Errors({}, configureRegistrationErrorFieldId);
    } else if (getActiveWindow().customFormWindow){
        return new Errors({}, customFormErrorFieldId);
    } else if (getActiveWindow().contactUsWidnow){
        return new Errors({}, configureContactUsErrorFieldId);
    } else if (getActiveWindow().csrWindow){
        return new Errors({}, configureChildSiteRegistrationErrorFieldId);
    }

    return null;
}