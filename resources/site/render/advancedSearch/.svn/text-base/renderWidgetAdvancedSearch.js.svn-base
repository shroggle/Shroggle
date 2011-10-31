var renderWidgetAdvancedSearch = {};
renderWidgetAdvancedSearch.collapseImg = "/images/minus.png";
renderWidgetAdvancedSearch.expandImg = "/images/plus.png";

renderWidgetAdvancedSearch.collapseOrExpandOption = function (event, header, widgetId, optionId) {
    if ($(isIE() ? event.srcElement : event.target).hasClass("deselectLink")) {
        return;
    }

    var compositId = widgetId + "" + optionId;
    var img = $(header).find("#advancedSearchOptionCollapseImg" + compositId);
    if ($(img).attr('src') == renderWidgetAdvancedSearch.collapseImg) {
        $(img).attr('src', renderWidgetAdvancedSearch.expandImg);

        $(header).parents("#advancedSearchOption" + compositId).find("#advancedSearchOptionBody" + compositId).slideUp(300);
    } else {
        $(img).attr('src', renderWidgetAdvancedSearch.collapseImg);

        $(header).parents("#advancedSearchOption" + compositId).find("#advancedSearchOptionBody" + compositId).slideDown(300);
    }
};

renderWidgetAdvancedSearch.executeSearch = function (widgetId, advancedSearchOptionId) {
    var advancedSearchId = $("#advancedSearchId" + widgetId).val();
    var advancedSearchGalleryId = $("#advancedSearchGalleryId" + widgetId).val();
    var advancedSearchSiteSearchOption = $("#advancedSearchSiteSearchOption" + widgetId).val();
    var searchOptionCriteriaList = renderWidgetAdvancedSearch.collectSearchCriteriaList(widgetId, advancedSearchOptionId, "SELECTED");

    if (!searchOptionCriteriaList) {
        return;
    }

    var includeResultsNumber = $("#includeResultsNumber" + widgetId).val() == "true";
    var criteriaResultsNumberToReloadList = new Array();
    if (includeResultsNumber) {
        var allOptionIds = new Array();
        $("#advancedSearchBlock" + widgetId).find(".advancedSearchOptionId").each(function () {
            allOptionIds.push($(this).val());
        });

        $(allOptionIds).each(function () {
            var optionId = this;
            var searchOptionCriteriaListForResultsNumber = renderWidgetAdvancedSearch.collectSearchCriteriaList(widgetId, optionId, "ALL");
            $(searchOptionCriteriaListForResultsNumber).each(function () {
                var criteriaResultsNumberToReload = new Object();

                criteriaResultsNumberToReload.optionId = optionId;
                criteriaResultsNumberToReload.criteria = this;

                criteriaResultsNumberToReloadList.push(criteriaResultsNumberToReload);
            });
        });
    }

    var request = {
        advancedSearchId: advancedSearchId,
        widgetId: widgetId,
        advancedSearchOptionId: advancedSearchOptionId,
        searchOptionCriteriaList: searchOptionCriteriaList,
        galleryId: advancedSearchGalleryId,
        siteShowOption: advancedSearchSiteSearchOption,
        criteriaResultsNumberToReloadList: criteriaResultsNumberToReloadList
    };

    createLoadingArea({element:$("#advancedSearchGallery" + widgetId)[0], text: "Applying new search parameters...", color: "green", guaranteeVisibility:true});
    renderWidgetAdvancedSearch.disableAdvancedSearchControls(widgetId, true);

    new ServiceCall().executeViaDwr("ExecuteAdvancedSearchService", "execute", request, function(response) {
        $("#advancedSearchGallery" + widgetId).html(response.galleryHtml);

        renderWidgetAdvancedSearch.disableAdvancedSearchControls(widgetId, false);
        removeLoadingArea();

        renderWidgetAdvancedSearch.updateResultsNumbers(widgetId, response.resultsNumberList);
    });
};

renderWidgetAdvancedSearch.deselectLinkClick = function (compositUniqueId, widgetId, advancedSearchOptionId) {
    $("#advancedSearchOption" + compositUniqueId).find("input[type='checkbox']").each(function () {
        this.checked = false;
    });

    renderWidgetAdvancedSearch.executeSearch(widgetId, advancedSearchOptionId);
};

renderWidgetAdvancedSearch.disableAdvancedSearchControls = function (widgetId, disable) {
    disableControl($("#advancedSearchBlock" + widgetId), disable);
};

/*
 * mode - can either be SELECTED or ALL
 * SELECTED means that method will collect only selected criterias, ALL - means that method will collect all criterias.
 * */
renderWidgetAdvancedSearch.collectSearchCriteriaList = function (widgetId, advancedSearchOptionId, mode) {
    if (mode != "ALL" && mode != "SELECTED") {
        alert("Unknown mode.");
        return;
    }

    var searchCriteriaList = new Array();
    var compositId = widgetId + "" + advancedSearchOptionId;
    var optionType = $("#optionType" + compositId).val();

    if (optionType == "PICK_LIST_SELECT") {
        if (mode == "ALL") {
            $("#searchPickListSelect" + compositId + " > option").each(function () {
                if ($(this).val() != "null") {
                    searchCriteriaList.push($(this).val());
                }
            });
        } else if (mode == "SELECTED") {
            searchCriteriaList.push($("#searchPickListSelect" + compositId + " > option:selected").val());
        }
    } else if (optionType == "PICK_LIST_MULTISELECT") {
        $("#advancedSearchOptionBody" + compositId).find("input" + (mode == "ALL" ? "" : ":checked")).each(function () {
            searchCriteriaList.push($(this).val());
        });
    } else if (optionType == "SINGLE_CHECKBOX") {
        if (mode == "ALL") {
            searchCriteriaList.push($("#singleCheckboxChecked" + compositId + "").val());
            searchCriteriaList.push($("#singleCheckboxUnchecked" + compositId + "").val());
        } else if (mode == "SELECTED") {
            if ($("#singleCheckboxChecked" + compositId + "").attr("checked")) {
                searchCriteriaList.push($("#singleCheckboxChecked" + compositId + "").val());
            }

            if ($("#singleCheckboxUnchecked" + compositId + "").attr("checked")) {
                searchCriteriaList.push($("#singleCheckboxUnchecked" + compositId + "").val());
            }
        }
    } else if (optionType == "TEXT_AS_SEP_OPTION") {
        if (mode == "ALL") {
            $("#searchTextAsSepOptionSelect" + compositId + " > option").each(function () {
                if ($(this).val() != "null") {
                    searchCriteriaList.push($(this).val());
                }
            });
        } else if (mode == "SELECTED") {
            searchCriteriaList.push($("#searchTextAsSepOptionSelect" + compositId + " > option:selected").val());
        }
    } else if (optionType == "TEXT_AS_FREE") {
        searchCriteriaList.push($("#searchTextAsFreeInput" + compositId).val());
    } else if (optionType == "RANGE_AS_RANGE") {
        $("#advancedSearchOptionBody" + compositId).find("input" + (mode == "ALL" ? "" : ":checked")).each(function () {
            searchCriteriaList.push($(this).val());
        });
    } else if (optionType == "RANGE_AS_SEP_OPTION") {
        $("#advancedSearchOptionBody" + compositId).find("input" + (mode == "ALL" ? "" : ":checked")).each(function () {
            searchCriteriaList.push($(this).val());
        });
    } else if (optionType == "RANGE_AS_RANGE_INPUTS") {
        var errorField = $("#advancedSearchOptionBody" + compositId).find("#addRangeError" + compositId);
        var onlyNumbersRange = $(errorField).parent().find(".onlyNumbersRange")[0];
        var resultStartRangeArray = new Array();
        var resultEndRangeArray = new Array();

        //Collecting start range.
        var allStartRangesAreEmpty = true;
        $("#advancedSearchOptionBody" + compositId).find(".rangeStart").find("input").each(function () {
            resultStartRangeArray.push($(this).val());

            if ($(this).val().trim() != "") {
                allStartRangesAreEmpty = false;
            }
        });

        if (!onlyNumbersRange) {
            //Validating start range date.
            if (!validateDateArray(resultStartRangeArray)) {
                errorField.html($("#advSearchStartDateIsntValidException" + widgetId).val());
                addFadingTimeoutEvent(errorField[0], 4000);
                return;
            }
        }

        //Collecting end range.
        var allEndRangesAreEmpty = true;
        $("#advancedSearchOptionBody" + compositId).find(".rangeEnd").find("input").each(function () {
            resultEndRangeArray.push($(this).val());

            if ($(this).val().trim() != "") {
                allEndRangesAreEmpty = false;
            }
        });

        if (!onlyNumbersRange) {
            //Validating end range date.
            if (!validateDateArray(resultEndRangeArray)) {
                errorField.html($("#advSearchEndDateIsntValidException" + widgetId).val());
                addFadingTimeoutEvent(errorField[0], 4000);
                return;
            }
        }

        //Validating that user had entered at least start or end date ranges.
        if (allStartRangesAreEmpty && allEndRangesAreEmpty) {
            errorField.html($("#advSearchBothDateAreEmptyException" + widgetId).val());
            addFadingTimeoutEvent(errorField[0], 4000);
            return;
        }

        var resultStartEndRangeString = "";
        for (var i = 0; i < resultStartRangeArray.length; i++) {
            resultStartEndRangeString += resultStartRangeArray[i] + ((i == resultStartRangeArray.length - 1 && resultEndRangeArray.length == 0) ? "" : ";");
        }

        for (i = 0; i < resultEndRangeArray.length; i++) {
            resultStartEndRangeString += resultEndRangeArray[i] + ((i == resultEndRangeArray.length - 1) ? "" : ";");
        }

        searchCriteriaList.push(resultStartEndRangeString);
    } else if (optionType == "POST_CODE") {
        searchCriteriaList.push($("#zipCode" + compositId).val());
        searchCriteriaList.push($("#zipCodeSelect" + compositId + " > option:selected").val());
    }

    return searchCriteriaList;
};

renderWidgetAdvancedSearch.updateResultsNumbers = function (widgetId, resultsNumberList) {
    $(resultsNumberList).each(function () {
        var resultNumber = this;
        var advancedSearchOptionId = resultNumber.optionId;
        var compositId = widgetId + "" + advancedSearchOptionId;
        var optionType = $("#optionType" + compositId).val();

        if (optionType == "PICK_LIST_SELECT") {
            updateSingleSelectOption(resultNumber, $("#searchPickListSelect" + compositId));
        } else if (optionType == "TEXT_AS_SEP_OPTION") {
            updateSingleSelectOption(resultNumber, $("#searchTextAsSepOptionSelect" + compositId));
        } else if (optionType == "RANGE_AS_RANGE" || optionType == "PICK_LIST_MULTISELECT"
                || optionType == "RANGE_AS_SEP_OPTION") {
            updateMultiselectOption(resultNumber, compositId);
        }
    });

    function updateMultiselectOption(resultNumber, compositId) {
        $("#advancedSearchOptionBody" + compositId).find("input").each(function () {
            if ($(this).val() == resultNumber.criteria) {
                $(this).next("label:first").find(".advancedSearchOptionResultsNumber").html("(" + resultNumber.resultsNumber + ")");
            }
        });
    }

    function updateSingleSelectOption(resultNumber, select) {
        $(select).find("option").each(function () {
            if ($(this).val() == resultNumber.criteria) {
                if ($(this).attr('formItemName') == "LINKED") {
                    $(this).html($(this).attr('linkedValue') + "&nbsp;(" + resultNumber.resultsNumber + ")");
                } else {
                    $(this).html(resultNumber.criteria + "&nbsp;(" + resultNumber.resultsNumber + ")");
                }
            }
        });
    }
};

renderWidgetAdvancedSearch.resetSearch = function(widgetId) {
    var advancedSearchId = $("#advancedSearchId" + widgetId).val();
    var advancedSearchSiteSearchOption = $("#advancedSearchSiteSearchOption" + widgetId).val();

    var request = {
        advancedSearchId: advancedSearchId,
        widgetId: widgetId,
        siteShowOption: advancedSearchSiteSearchOption
    };

    new ServiceCall().executeViaDwr("ResetAdvancedSearchService", "execute", request, function(advancedSearchHtml) {
        $("#widget" + widgetId).html(advancedSearchHtml);
    });
};