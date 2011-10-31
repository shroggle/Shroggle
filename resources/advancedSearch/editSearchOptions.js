/* Look at configureAdvancedSearch.js to see parent variables. */

configureAdvancedSearch.showEditOptions = function () {
    configureAdvancedSearch.errors.clear();

    if (configureAdvancedSearch.getSelectedFormId() == -1 && $("#recordsSourceGallery").is(":checked")) {
        configureAdvancedSearch.errors.set("AdvancedSearchGalleryNotSelectException", $("#AdvancedSearchGalleryNotSelectException").val(),
                [$("#recordsSourceGallery")[0]]);
        return;
    }

    if (configureAdvancedSearch.getSelectedFormId() == -1 && $("#recordsSourceForm").is(":checked")) {
        configureAdvancedSearch.errors.set("AdvancedSearchFormNotSelectException", $("#AdvancedSearchFormNotSelectException").val(),
                [$("#recordsSourceForm")[0]]);
        return;
    }

    var editShowOptionWindow = createConfigureWindow({width:750, height: 600, enableContentOnSetContent:false});

    var request = {
        advancedSearchId: $("#selectedAdvancedSearchId").val() == "null" ? null : $("#selectedAdvancedSearchId").val(),
        formId : configureAdvancedSearch.getSelectedFormId(),
        siteId : $("#advancedSearchSiteId").val(),
        newSearchOptions : configureAdvancedSearch.searchOptions
    };

    new ServiceCall().executeViaDwr("EditSearchOptionsService", "show", request, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        editShowOptionWindow.setContent(response);

        configureAdvancedSearch.editOptionsErrors = new Errors({highlighting:true}, "editOptionsErrors");

        if ($("#recordsSourceUpload").is(":checked")) {
            $("#alreadyCreatedDefaultFormId").val($("#editSearchOptionsFormId").val());
        }

        editShowOptionWindow.resize();
        editShowOptionWindow.disableContent();
    });
};

configureAdvancedSearch.saveEditOptions = function() {
    //If we have already configured adv. search then save it's options on saveEditOptions,
    //otherwise — just close window, we will save option on main window save click.
    if ($("#selectedAdvancedSearchId").val() != "null") {
        getActiveWindow().disableContentBeforeSaveSettings();
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("UpdateAdvancedSearchOptionsService", "execute", $("#selectedAdvancedSearchId").val(), configureAdvancedSearch.collectSearchOptions(), function() {
            configureAdvancedSearch.clearSearchOptionsArray();
            closeConfigureWidgetDiv();
        });
    } else {
        configureAdvancedSearch.searchOptions = configureAdvancedSearch.collectSearchOptions();
        closeConfigureWidgetDiv();
    }
};

configureAdvancedSearch.cancelEditOptions = function() {
    configureAdvancedSearch.clearSearchOptionsArray();
    closeConfigureWidgetDiv();
};

configureAdvancedSearch.addSearchOption = function () {
    configureAdvancedSearch.editOptionsErrors.clear();

    var siteId = $("#advancedSearchSiteId").val();
    var formId = $("#editSearchOptionsFormId").val();

    var formItemId = $("#formFieldsSelect > option:selected").val();

    if (formItemId == -1) {
        configureAdvancedSearch.editOptionsErrors.set("EditOptionsFieldNotSelectedException", $("#EditOptionsFieldNotSelectedException").val(),
                [$("#formFieldsSelect")[0]]);
        return;
    }

    if (formItemId == "allFields") {
        $("#formFieldsSelect > option").each(function () {
            var initialPosition = $("tr.optionTr").length;
            if ($(this).val() != -1 && $(this).val() != "allFields") {
                var newSearchOption = new Object();
                newSearchOption.formItemId = $(this).val();
                newSearchOption.position = initialPosition++;

                configureAdvancedSearch.searchOptions.push(newSearchOption);
            }
        });
    } else {
        newSearchOption = new Object();
        newSearchOption.formItemId = formItemId;
        newSearchOption.position = $("tr.optionTr").length;

        configureAdvancedSearch.searchOptions.push(newSearchOption);
    }

    var request = {
        siteId: siteId,
        formId: formId,
        advancedSearchId: $("#selectedAdvancedSearchId").val() == "null" ? null : $("#selectedAdvancedSearchId").val(),
        newSearchOptions: configureAdvancedSearch.searchOptions
    };

    //Show 'spin' image while adding an option and 'grey out' edit search options table.
    $("#addSearchOptionLoadingDiv").css("display", "inline");
    createBackground({element:$("#editSearchOptionsList")[0]});

    new ServiceCall().executeViaDwr("UpdateSearchOptionsListOnPageService", "execute", request, function (response) {
        //Hide 'spin' image while adding an option and remove 'grey out'.
        $("#addSearchOptionLoadingDiv").hide();
        removeBackground();

        $("#editSearchOptionsList").html(response);
        getActiveWindow().resize();
    });
};

configureAdvancedSearch.collectSearchOptions = function () {
    configureAdvancedSearch.clearSearchOptionsArray();
    var i = 0;
    $("#editSearchOptionsListTableBody > tr").each(function () {
        var searchOption = new Object();
        searchOption.advancedSearchOptionId = $(this).find(".optionId").val();
        searchOption.fieldLabel = $(this).find(".fieldLabel").val();
        searchOption.formItemId = $(this).find(".formItemId").val();
        searchOption.displayType = $(this).find(".advSearchOptionDisplayType:checked").val();
        searchOption.position = i;

        searchOption.optionCriteria = configureAdvancedSearch.collectOptionCriterias(this);
        searchOption.alsoSearchByFields = configureAdvancedSearch.collectAlsoSearchByFields(this);

        configureAdvancedSearch.searchOptions.push(searchOption);
        i++;
    });

    return configureAdvancedSearch.searchOptions;
};

configureAdvancedSearch.collectAlsoSearchByFields = function (optionTr) {
    var alsoSearchByFields = new Array();
    var optionDisplayType = $(optionTr).find(".advSearchOptionDisplayType:checked").val();
    var optionType = $(optionTr).find(".optionType").val();

    if (optionType == "TEXT" && optionDisplayType == "TEXT_AS_FREE") {
        $(optionTr).find(".searchOptionParameter").each(function () {
            alsoSearchByFields.push($(this).attr('value'));
        });
    }

    return alsoSearchByFields;
};

configureAdvancedSearch.collectOptionCriterias = function (optionTr) {
    var optionType = $(optionTr).find(".optionType").val();
    var optionDisplayType = $(optionTr).find(".advSearchOptionDisplayType:checked").val();
    var optionCriterias = new Array();

    if (optionType == "PICK_LIST" || optionType == "SINGLE_CHECKBOX") {
        $(optionTr).find(".pickListOption").each(function() {
            if ($(this).is(":checked")) {
                optionCriterias.push($(this).val());
            }
        });
    } else if (optionType == "RANGE" && optionDisplayType == "RANGE_AS_RANGE") {
        $(optionTr).find(".searchOptionParameter").each(function () {
            optionCriterias.push($(this).attr('value'));
        });
    }

    return optionCriterias;
};

configureAdvancedSearch.addFieldClick = function (button) {
    var selectedOption = $(button).parents(".optionTr").find('.otherTextFieldsSelect > option:selected');
    var alsoSearchByFieldId = selectedOption.attr('value');
    if (alsoSearchByFieldId == -1) {
        addFadingTimeoutEvent($(button).parents(".optionTr").find(".addFieldError")[0], 4000);
    } else {
        configureAdvancedSearch.addSearchOptionParameter($(button).parents(".optionTr").find('.otherTextSourcesDiv'),
                selectedOption.attr('itemName'), selectedOption.attr('value'));

        // Remove just added field
        selectedOption.remove();
        // Select next field
        $(button).parents(".optionTr").find('.otherTextFieldsSelect > option[value=\'-1\']').next().attr('selected', 'selected');

        var optionId = $(button).parents(".optionTr").find(".optionId").val();
        // If option is saved then update it on server.
        if (optionId > 0) {
            new ServiceCall().executeViaDwr("UpdateSearchOptionAlsoSearchByFieldsService", "execute",
                    optionId, alsoSearchByFieldId, function (response) {
                // do nothing.
            });
        }
    }
};

configureAdvancedSearch.addRangeClick = function (button) {
    var errorField = $(button).parents(".optionTr").find(".addRangeError");
    var onlyNumbersRange = $(button).parents(".optionTr").find(".onlyNumbersRange")[0];

    var rangeString = "";
    var rangeValueString = "";

    //Collecting start range.
    var startRange = new Array();
    var allStartRangesAreEmpty = true;
    var rangeStartInputs = $(button).parents(".optionTr").find(".rangeStart").find("input");
    var startRangeValueString = "";
    for (var i = 0; i < rangeStartInputs.length; i++) {
        var rangeStartInput = rangeStartInputs[i];
        var startValue = $(rangeStartInput).val();

        //Clearing input
        $(rangeStartInput).val("");

        startRangeValueString += startValue + (i != rangeStartInputs.length - 1 ? ";" : "");

        if (startValue.trim() != "") {
            allStartRangesAreEmpty = false;
        } else {
            startValue = "&ndash;"
        }

        startRange.push(startValue);
    }

    if (!onlyNumbersRange) {
        //Validating start range date.
        if (!validateDate(startRangeValueString)) {
            errorField.html($("#advSearchStartDateIsntValidException").val());
            addFadingTimeoutEvent(errorField[0], 4000);
            return;
        }
    }

    rangeValueString += startRangeValueString + ";";

    //Collecting end range.
    var endRange = new Array();
    var allEndRangesAreEmpty = true;
    var rangeEndInputs = $(button).parents(".optionTr").find(".rangeEnd").find("input");
    var endRangeValueString = "";
    for (i = 0; i < rangeEndInputs.length; i++) {
        var rangeEndInput = rangeEndInputs[i];
        var endValue = $(rangeEndInput).val();

        //Clearing input
        $(rangeEndInput).val("");

        endRangeValueString += endValue + (i != rangeEndInputs.length - 1 ? ";" : "");

        if (endValue.trim() != "") {
            allEndRangesAreEmpty = false;
        } else {
            endValue = "&ndash;"
        }

        endRange.push(endValue);
    }

    if (!onlyNumbersRange) {
        //Validating end range date.
        if (!validateDate(endRangeValueString)) {
            errorField.html($("#advSearchEndDateIsntValidException").val());
            addFadingTimeoutEvent(errorField[0], 4000);
            return;
        }
    }

    rangeValueString += endRangeValueString;

    //Validating that user had entered at least start or end date ranges.
    if (allStartRangesAreEmpty && allEndRangesAreEmpty) {
        errorField.html($("#advSearchBothDateAreEmptyException").val());
        addFadingTimeoutEvent(errorField[0], 4000);
        return;
    }

    if (allEndRangesAreEmpty) {
        rangeString = ">&nbsp;"
    }

    if (allStartRangesAreEmpty) {
        rangeString = "<&nbsp;"
    }

    if (!allStartRangesAreEmpty) {
        for (i = 0; i < startRange.length; i++) {
            var sepSymbol = (i == 0 || i == 1 ? "/" : (i == 3 ? ":" : " "));
            rangeString += startRange[i] + sepSymbol;
        }
    }

    if (!allEndRangesAreEmpty && !allStartRangesAreEmpty) {
        rangeString += "&nbsp;&mdash;&nbsp;";
    }

    if (!allEndRangesAreEmpty) {
        for (i = 0; i < endRange.length; i++) {
            sepSymbol = (i == 0 || i == 1 ? "/" : (i == 3 ? ":" : " "));
            rangeString += endRange[i] + sepSymbol;
        }
    }

    configureAdvancedSearch.addSearchOptionParameter($(button).parents(".optionTr").find('.rangesDiv'), rangeString, rangeValueString);
};

configureAdvancedSearch.removeOptionParameter = function (removeBtn) {
    var optionType = $(removeBtn).parents(".optionTr").find(".optionType").val();
    if (optionType == "TEXT") {
        var formItemId = $(removeBtn).parents(".searchOptionParameter:first").attr('value');
        var formItemName = $(removeBtn).parents(".searchOptionParameter:first").attr('itemname');
        var optionHTML = "<option value='" + formItemId + "' itemname='" + formItemName + "'>" + formItemName + "</option>";
        $(removeBtn).parents(".optionTr").find('.otherTextFieldsSelect').append(optionHTML);
        $(removeBtn).parent().remove();
    } else if (optionType == "RANGE") {
        $(removeBtn).parent().remove();
    } else {
        $(removeBtn).parent().remove();
    }
};

configureAdvancedSearch.addSearchOptionParameter = function(parameterHolder, parameterName, parameterValue) {
    var parameterHtml = "<div class='searchOptionParameter' value='" + parameterValue + "' itemname='" + parameterName + "'>" + parameterName +
            "<img src='/images/cross-circle.png' alt='' class='searchOptionParameterDeleteImg' onclick='configureAdvancedSearch.removeOptionParameter(this)'/>" +
            "</div>";
    $(parameterHolder).append(parameterHtml);
    getActiveWindow().resize();
};

configureAdvancedSearch.moveSearchOptionDown = function(moveImg) {
    var optionTr = $(moveImg).parents(".optionTr");
    var upImg = optionTr.find('.advSearchUpImg');

    //Getting next TR to this.
    var nextOptionTr = optionTr.next();

    //Hiding down img if we are about to move this TR to the last position.
    var nextOptionDownImg = nextOptionTr.find(".advSearchDownImg");
    var nextOptionUpImg = nextOptionTr.find(".advSearchUpImg");
    if (nextOptionDownImg && $(nextOptionDownImg).css('display') == 'none') {
        $(moveImg).css('display', 'none');
        $(nextOptionDownImg).css('display', 'inline');
    }

    //Hiding next TR up image if we are moving current TR from top position
    if (upImg.css('display') == 'none') {
        nextOptionUpImg.css('display', 'none');
        $(nextOptionDownImg).css('margin-left', '18px');
    }

    //Showing UP image.
    upImg.css('display', 'inline');
    $(moveImg).css('margin-left', 0);

    //Removing this TR from current position and appending it after next TR.
    $(optionTr).remove();
    $(nextOptionTr).after(optionTr);
};

configureAdvancedSearch.moveSearchOptionUp = function(moveImg) {
    var optionTr = $(moveImg).parents(".optionTr");
    var downImg = optionTr.find('.advSearchDownImg');
    //Getting prev TR to this.
    var prevOptionTr = optionTr.prev();

    //Hiding up img if we are about to move this TR to the first position.
    var prevOptionDownImg = prevOptionTr.find(".advSearchDownImg");
    var prevOptionUpImg = prevOptionTr.find(".advSearchUpImg");
    if (prevOptionUpImg && $(prevOptionUpImg).css('display') == 'none') {
        $(moveImg).css('display', 'none');
        $(downImg).css('margin-left', '18px');
        $(prevOptionUpImg).css('display', 'inline');
        $(prevOptionDownImg).css('margin-left', 0);
    }

    //Hiding prev TR down image if we are moving current TR from last position
    if (downImg.css('display') == 'none') {
        prevOptionDownImg.css('display', 'none');
    }

    //Showing DOWN image.
    downImg.css('display', 'inline');

    //Removing this TR from current position and appending it before prev TR.
    $(optionTr).remove();
    $(prevOptionTr).before(optionTr);
};

configureAdvancedSearch.removeSearchOption = function(deleteBtn, optionId) {
    //If optionId == 0 then this field wasn't persisted yet, so we can delete it only from new fields array.
    if (confirm($("#optionRemoveConfirm").val())) {
        if (optionId == 0) {
            var optionTr = $(deleteBtn).parents(".optionTr");
            var optionsTable = $(optionTr).parents("table:first");
            var i = 0;
            $(optionsTable).find(".optionId").each(function() {
                //If option wasn't persisted yet and it equals to option that should be deleted then
                //remove it form new options array, else go to next not persisted option.
                if ($(this).val() == 0 && $(this).parents(".optionTr")[0] == optionTr[0]) {
                    configureAdvancedSearch.searchOptions.splice(i, 1);
                    removeOptionInternal();
                }
                i++;
            });
        } else {
            //Otherwise let's send request and delete it form db.
            new ServiceCall().executeViaDwr("RemoveSearchOptionService", "removeOne", optionId, function () {
                removeOptionInternal();
            });
        }

        function removeOptionInternal() {
            var optionTr = $(deleteBtn).parents(".optionTr");
            var optionsCount = $(optionTr).parents("tbody:first").find("tr").length;
            var prevOptionTr = $(optionTr).prev();
            var nextOptionTr = $(optionTr).next();
            getActiveWindow().resize();

            // if option was first option then try to remove a UP img from next option
            if (optionTr.find(".advSearchUpImg").css('display') == 'none') {
                nextOptionTr.find(".advSearchUpImg").css('display', 'none');
                nextOptionTr.find(".advSearchDownImg").css('margin-left', '18px');
            }

            if (optionTr.find(".advSearchDownImg").css('display') == 'none') {
                prevOptionTr.find(".advSearchDownImg").css('display', 'none');
            }

            if (optionsCount == 2) {
                prevOptionTr.find(".advSearchDownImg").css('display', 'none');
                prevOptionTr.find(".advSearchUpImg").css('display', 'none');
                nextOptionTr.find(".advSearchDownImg").css('display', 'none');
                nextOptionTr.find(".advSearchUpImg").css('display', 'none');
            }

            optionTr.remove();
            getActiveWindow().resize();
        }
    }
};

configureAdvancedSearch.selectAllPickOptions = function (element) {
    $(element).parents("div.pickListOptionsList").find("input[type='checkbox']").attr('checked', 'checked');
};

configureAdvancedSearch.deselectAllPickOptions = function (element) {
    $(element).parents("div.pickListOptionsList").find("input[type='checkbox']").attr('checked', null);
};