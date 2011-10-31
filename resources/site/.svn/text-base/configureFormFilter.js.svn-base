var errors;
var itemInfos = new Array();

// Shows add/edit form filter window.
// For NEW filter:
// Set formId and filterId to null. (formId not null only on manageFormRecords).
// For EDIT EXISTING filter:
// Set filterId not null and formId null.
// selectFormDropdown true if existing filter is selected from filter dropdown.
// disableFormPickList - true if we must disable form pick list, need this only for manageFormRecords.
function showAddFilter(filterId, formId, selectFromDropdown, disableFormPickList) {
    configureFormFilter({
        formFilterId: filterId,
        formId: formId,
        disableFormPickList: disableFormPickList,
        selectFromDropdown: selectFromDropdown});
}

// ---------------------------------------------------------------------------------------------------------------------

/**
 *
 * @param settings - settings. You can use next properties for this parameter:
 * settings.onSave(formFilter : {id, name}) - define onSave listener
 */
function configureFormFilter(settings) {
    if ((settings.formFilterId || settings.selectFromDropdown) && settings.formId) {
        alert("filterId and formId both cannot be not null.");
    }

    if (settings.selectFromDropdown && settings.formFilterId == null) {
        settings.formFilterId = $("#filterPickList")[0].options[$("#filterPickList")[0].selectedIndex].value;
    }

    cameFromGallery = settings.cameFromGallery;

    var configureDiv = createConfigureWindow({width:750, height:500});
    new ServiceCall().executeViaDwr("ConfigureFormFilterService", "execute", settings.formFilterId, settings.formId, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureDiv.setContent(response.html);

        if (settings.formFilterId) {
            $("#filterName")[0].customTextEntered = true;
        }

        $("#configureFormFilterSettings")[0].settings = settings;
        itemInfos = response.itemInfos;

        if (response.rules) {
            for (var i = 0; i < response.rules.length; i++) {
                var rule = response.rules[i];

                var itemInfo = getItemInfoById(rule.formItemId);
                if (itemInfo) {
                    var criteria = rule.criteria ? rule.criteria : "";
                    addFormFilterRuleInternal(itemInfo, rule.include, criteria);
                }
            }
        }

        if (settings.disableFormPickList && !settings.cameFromGallery) {
            $("#formSelect")[0].disabled = true;
        }

        $("#formSelect")[0].previousSelectedIndex = $("#formSelect")[0].selectedIndex;
        $("#formSelect").bind("change", change);

        configureDiv.resize();
        //Accuring the error block
        errors = new Errors();
    });
}

var cameFromGallery;
var selectChanged = false;
function change() {
    if (!selectChanged) {
        var select = $("#formSelect")[0];
        if (cameFromGallery) {
            if (confirm($("#modifyFormFilter")[0].value)) {
                fillFormItemSelect(select.options[select.selectedIndex].value);
            } else {
                select.selectedIndex = select.previousSelectedIndex;
                selectChanged = true;
            }
        } else {
            fillFormItemSelect(select.options[select.selectedIndex].value);
        }

        select.previousSelectedIndex = select.selectedIndex;
    } else {
        selectChanged = false;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function fillFormItemSelect(formId) {
    if (formId > 0) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("GetFormItemsByFormService", "execute", formId, function(response) {
            if (response) {
                $("#formItemSelect").children().not("option[value='-1']").remove();
                itemInfos = response;
                $(response).each(function() {
                    $("#formItemSelect").append("<option value=" + this.formItemId + ">" + this.formItemText + "</option>");
                });
            }
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function addFilterRule() {
    errors.clear();
    var formItems = document.getElementById("formItemSelect");
    var selectedFormItemIndex = formItems.selectedIndex;
    if (addFilterRule <= 0 || selectedFormItemIndex <= 0) {
        errors.set("FORM_ITEM_NOT_SELECTED", $("#formItemNotSelected")[0].value, [$("#formItemSelect")[0]]);
        return;
    }

    var itemInfo = getItemInfoById(formItems.options[selectedFormItemIndex].value);

    addFormFilterRuleInternal(itemInfo, true, "");
}

// ---------------------------------------------------------------------------------------------------------------------

var someId = 0;

function addFormFilterRuleInternal(itemInfo, include, criteria) {
    var formItemId = itemInfo.formItemId;
    var formItemText = itemInfo.formItemText;

    someId++;

    var tr = document.createElement("tr");
    tr.className = "formFilterRule";
    tr.someId = someId;
    tr.formItemId = formItemId;
    $("#formFilterRules").append(tr);

    var nameTd = document.createElement("td");
    nameTd.className = "ruleNameTd";
    tr.appendChild(nameTd);

    $(nameTd).append("<input type=\"hidden\" value=\"" + itemInfo.formItemType + "\" id=\"ruleType" + tr.someId + "\"/>");
    $(nameTd).append(
            "<span>Field name:&nbsp;</span>" +
                    "<span style=\"font-weight:bold;\">" + formItemText + "</span>");
    $(nameTd).append("<br><br>");

    var includeRadio = document.createElement("input");
    includeRadio.type = "radio";
    includeRadio.id = "formFilterRuleInclude" + tr.someId;
    includeRadio.name = "formFilterRuleInEx" + tr.someId;
    includeRadio.className = "includeRule";
    includeRadio.checked = include;
    $(nameTd).append(includeRadio);

    $(nameTd).append("<label for=\"" + includeRadio.id + "\">&nbsp;Include Records</label>");
    $(nameTd).append("<br>");

    var excludeRadio = document.createElement("input");
    excludeRadio.type = "radio";
    excludeRadio.checked = !include;
    excludeRadio.name = "formFilterRuleInEx" + tr.someId;
    excludeRadio.id = "formFilterRuleEcxlude" + tr.someId;
    $(nameTd).append(excludeRadio);

    $(nameTd).append("<label for=\"" + excludeRadio.id + "\">&nbsp;Exclude Records</label>");
    $(nameTd).append("<br>");

    var criteriaTd = document.createElement("td");
    criteriaTd.className = "ruleCriteriaTd";
    $(tr).append(criteriaTd);

    $(criteriaTd).append("<label for=\"formFilterRuleCriteria" + tr.someId + "\">Select Filter Criteria</label>");
    $(criteriaTd).append("<br>");

    var criteriaDiv = document.createElement("div");
    criteriaDiv.style.marginTop = "5px";
    $(criteriaTd).append(criteriaDiv);

    constructCriteriaFields(itemInfo, criteria, criteriaTd, someId);

    var deleteTd = document.createElement("td");
    deleteTd.style.verticalAlign = "middle";
    deleteTd.className = "deleteRuleTd";
    $(tr).append(deleteTd);

    var deleteImg = document.createElement("img");
    deleteImg.src = "/images/cross-circle.png";
    deleteImg.align = "right";
    deleteImg.alt = "delete";
    deleteImg.style.cursor = "pointer";
    $(deleteTd).append(deleteImg);
    $(deleteImg).click(function () {
        if (confirm($("#deleteFormFilterRuleConfirm")[0].value)) {
            $(this.parentNode.parentNode).remove();
            getActiveWindow().resize();
        }
    });

    getActiveWindow().resize();
}

// ---------------------------------------------------------------------------------------------------------------------

function constructCriteriaFields(itemInfo, criteria, criteriaTd, someId) {
    if (itemInfo.formItemType == "TEXT_INPUT_FIELD" || itemInfo.formItemType == "TEXT_AREA") {
        $(criteriaTd).append(
                "<input type=\"text\" id=\"formFilterRuleCriteria" + someId + "\" " +
                        "value=\"" + getSingleCriteria(criteria) + "\">");
    } else if (itemInfo.formItemType == "SELECT" || itemInfo.formItemType == "MULITSELECT" || itemInfo.formItemType == "SINGLE_CHOICE_OPTION_LIST") {
        var text = "";
        $(itemInfo.itemOptions[1]).each(function () {

            text += "<option" + (criteriaContains(criteria, this) ? " selected=\"selected\"" : "") + " value='" + this + "'>" + this + "</option>";
        });

        $(criteriaTd).append(
                "<select multiple=\"multiple\" id=\"formFilterRuleCriteria" + someId + "\">" +
                        text +
                        "</select>"
                );
    } else if (itemInfo.formItemType == "RADIOBUTTON") {
        text = "<div id=\"formFilterRuleCriteria" + someId + "\">";
        $(itemInfo.itemOptions[1]).each(function () {
            text += "<input type=\"checkbox\"" + (criteriaContains(criteria, this) ? " checked=\"checked\"" : "") + " value='" + this + "'/>" + this + "<br/>";
        });
        text += "</div>";

        $(criteriaTd).append(text);
    } else if (itemInfo.formItemType == "FIVE_PICK_LISTS" || itemInfo.formItemType == "THREE_PICK_LISTS" || itemInfo.formItemType == "TWO_PICK_LISTS") {
        var startFrom;
        if (itemInfo.formItemType == "FIVE_PICK_LISTS") {
            startFrom = 5;
        } else if (itemInfo.formItemType == "THREE_PICK_LISTS") {
            startFrom = 3;
        } else {
            startFrom = 2;
        }

        text = "<table id=\"formFilterRuleCriteria" + someId + "\"><tr class=\"from\"><td>";
        text += "From:</td><td style=\"padding-bottom:5px\">";
        text += constructDate(itemInfo, criteria, 0) + "</td></tr>";
        text += "<tr class=\"till\"><td>Till:</td><td>";
        text += constructDate(itemInfo, criteria, startFrom) + "</td></tr></table>";

        $(criteriaTd).append(text);
    } else if (itemInfo.formItemType == "CHECKBOX") {
        $(criteriaTd).append("<input type=\"checkbox\" id=\"formFilterRuleCriteria" + someId + "\"" + (criteriaContains(criteria, itemInfo.formItemText) ? " checked=\"checked\"" : "") + " value=\"" + itemInfo.formItemText + "\">&nbsp;" + itemInfo.formItemText);
    } else if (itemInfo.formItemType == "PICK_LIST_AND_TEXT_FIELD") {
        text = "";
        $(itemInfo.itemOptions[1]).each(function () {
            text += "<option>" + this + "</option>";
        });

        $(criteriaTd).append(
                "<select id=\"formFilterRuleCriteria" + someId + "\">" +
                        text +
                        "</select><input type=\"text\" style=\"margin-left:5px\"/>"
                );
    } else if (itemInfo.formItemType == "FILE_UPLOAD") {
        text = "<div id='formFilterRuleCriteria" + someId + "'>";
        text += "File size range (in bytes):&nbsp;";
        text += "<input type='text' class='fileSizeRange rangeFrom' onkeypress='return numbersOnly(this, event);'" +
                " value='" + (criteria.length > 0 ? criteria[0] : "") + "'/>";
        text += "&nbsp;...&nbsp;";
        text += "<input type='text' class='fileSizeRange rangeTo' onkeypress='return numbersOnly(this, event);'" +
                " value='" + (criteria.length > 1 ? criteria[1] : "") + "'/>";
        text += "</div>";

        $(criteriaTd).append(text);
    }
}

function criteriaContains(criteries, item) {
    for (var i = 0; ; i++) {
        var criteria = criteries[i];
        if (criteria == undefined) {
            break;
        }

        if (criteria == item) {
            return true;
        }
    }

    return false;
}

function constructDate(itemInfo, criteria, startFrom) {
    startFrom = !startFrom ? 0 : startFrom;
    var text = "";
    for (var i = 1; ; i++) {
        var select = itemInfo.itemOptions[i];
        if (select == undefined) {
            break;
        }
        text += "<select style=\"margin-left:5px\">";
        $(select).each(function() {
            text += "<option" + (criteria[startFrom + i - 1] == this ? " selected=\"selected\"" : "") + " value='" + this + "'>" + this + "</option>";
        });
        text += "</select>";
    }

    return text;
}

function getSingleCriteria(criteria) {
    return criteria.length == 0 ? "" : criteria[0];
}

// ---------------------------------------------------------------------------------------------------------------------

function deleteFormFilter(id) {
    if (confirm($("#deleteFilterConfirm")[0].value)) {
        new ServiceCall().executeViaDwr("DeleteFormFilterService", "execute", id, function(formId) {
            //Updating filter dropdown list.
            $("#filterPickList > option[value='" + id + "']").remove();
            reloadManageRegistrantsTable();
            closeConfigureWidgetDiv();

            if ($("#configureGalleryFormFilter")[0]) {
                $("#configureGalleryFormFilter > option[value='" + id + "']").remove();
                $("#configureGalleryUploadImagesExists").attr("checked", true);
                $("#configureGalleryFormFilter").change();
                listSelectOption("#configureGalleryForms", formId);
                $("#configureGalleryForms").change();
            }
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function saveFormFilter(id) {
    errors.clear();

    var filterName = $("#filterName")[0].value;
    var formId = $("#formSelect")[0].options[$("#formSelect")[0].selectedIndex].value;

    if ((filterName).trim().length == 0) {
        errors.set("FORM_FILTER_WITHOUT_NAME", $("#formFilterWithoutName")[0].value, [$("#filterName")[0]]);
        return;
    }

    if (formId <= 0 || $("#formSelect")[0].selectedIndex <= 0) {
        errors.set("FORM_NOT_SELECTED", $("#formNotSelected")[0].value, [$("#formSelect")[0]]);
        return;
    }

    var hasError = false;
    var request = new Object();
    request.rules = new Array();
    $(".formFilterRule").each(function () {
        var rule = new Object();

        var criteria = collectCriteries(this.someId, $("#ruleType" + this.someId)[0].value);
        rule.formItemId = this.formItemId;
        rule.criteria = criteria;
        rule.include = $("#formFilterRuleInclude" + this.someId).attr("checked");

        if (criteria == "FROM_DATE_IS_AFTER_TILL_DATE_EXCEPTION") {
            errors.set("FROM_DATE_IS_AFTER_TILL_DATE_EXCEPTION", $("#fromDateIsAfterTillDateException")[0].value, []);
            hasError = true;
        }

        request.rules.push(rule);
    });
    request.id = id;
    request.name = filterName;
    request.formId = formId;

    if (hasError) return;

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.FormFilterNotUniqueNameException",
            errors.exceptionAction({errorId:"FormFilterNotUniqueNameException", fields:[$("#filterName")[0]]}));

    serviceCall.executeViaDwr("SaveFormFilterService", "execute", request, function(id) {
        //Updating filter dropdown list.
        if ($("#filterPickList")[0]) {
            var existingOption = $("#filterPickList > option[value='" + id + "']")[0];
            if (!existingOption) {
                $("#filterPickList").append("<option value=\"" + id + "\">" + filterName + "</option>");
                $("#filterPickList > option[value='" + id + "']")[0].selected = "selected";
            } else if (existingOption.innerHTML != filterName) {
                existingOption.innerHTML = filterName;
            }
        }

        //Call filter pick list onChange event.
        //This causes form records table to reload it content according to new filter.
        $("#filterPickList").change();

        // Find settings
        var settings = $("#configureFormFilterSettings")[0].settings;

        closeConfigureWidgetDiv();

        // Call if exists event listener
        if (settings.onSave) settings.onSave({id: id, name: filterName});
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function collectCriteries(id, type) {
    var criteria = new Array();

    if (type == "TEXT_INPUT_FIELD" || type == "TEXT_AREA") {
        criteria.push($("#formFilterRuleCriteria" + id)[0].value);
    } else if (type == "SELECT" || type == "MULITSELECT" || type == "SINGLE_CHOICE_OPTION_LIST") {
        $("#formFilterRuleCriteria" + id + " > option").each(function() {
            if (this.selected) {
                criteria.push(this.value);
            }
        });
    } else if (type == "RADIOBUTTON") {
        $("#formFilterRuleCriteria" + id + " > input[type='checkbox']").each(function() {
            if (this.checked) {
                criteria.push(this.value);
            }
        });
    } else if (type == "FIVE_PICK_LISTS" || type == "THREE_PICK_LISTS" || type == "TWO_PICK_LISTS") {
        $("#formFilterRuleCriteria" + id).find(".from select option").each(function() {
            if (this.selected) {
                criteria.push(this.value);
            }
        });
        $("#formFilterRuleCriteria" + id).find(".till select option").each(function() {
            if (this.selected) {
                criteria.push(this.value);
            }
        });

        if (type == "FIVE_PICK_LISTS") {
            var fromDate = new Date(criteria[2], convertFormStringMonthToInt(criteria[0]), criteria[1], criteria[3], criteria[4]);
            var tillDate = new Date(criteria[7], convertFormStringMonthToInt(criteria[5]), criteria[6], criteria[8], criteria[9]);

            if (fromDate.getTime() > tillDate.getTime()) {
                return "FROM_DATE_IS_AFTER_TILL_DATE_EXCEPTION";
            }
        } else if (type == "THREE_PICK_LISTS") {
            fromDate = new Date(criteria[2], convertFormStringMonthToInt(criteria[0]), criteria[1]);
            tillDate = new Date(criteria[5], convertFormStringMonthToInt(criteria[3]), criteria[4]);

            if (fromDate.getTime() > tillDate.getTime()) {
                return "FROM_DATE_IS_AFTER_TILL_DATE_EXCEPTION";
            }
        } else if (type == "TWO_PICK_LISTS") {
            fromDate = new Date("1900", "01", "01", criteria[0], criteria[1]);
            tillDate = new Date("1900", "01", "01", criteria[2], criteria[3]);

            if (fromDate.getTime() > tillDate.getTime()) {
                return "FROM_DATE_IS_AFTER_TILL_DATE_EXCEPTION";
            }
        }

    } else if (type == "CHECKBOX") {
        var checkbox = $("#formFilterRuleCriteria" + id)[0];
        if (checkbox.checked) {
            criteria.push(checkbox.value);
        }
    } else if (type == "PICK_LIST_AND_TEXT_FIELD") {
        /*text = "";
         $(itemInfo.itemOptions[1]).each(function () {
         text += "<option>" + this + "</option>";
         });

         $(criteriaTd).append(
         "<select id=\"formFilterRuleCriteria" + someId + "\">" +
         text +
         "</select><input type=\"text\" style=\"margin-left:5px\"/>"
         );*/
    } else if (type == "FILE_UPLOAD") {
        var div = $("#formFilterRuleCriteria" + id);
        criteria.push(div.find(".rangeFrom").val());
        criteria.push(div.find(".rangeTo").val());
    }

    return criteria;
}

// ---------------------------------------------------------------------------------------------------------------------

function filterMoreInfo() {
    var moreInfoWindow = createConfigureWindow({width:500, height:250});
    moreInfoWindow.setContent($("#filterMoreInfo")[0].innerHTML);
}

// ---------------------------------------------------------------------------------------------------------------------

function getItemInfoById(itemId) {
    var itemInfo;
    $(itemInfos).each(function () {
        if (this.formItemId == itemId) {
            itemInfo = this;
        }
    });
    return itemInfo;
}

// ---------------------------------------------------------------------------------------------------------------------

function setFilterDefaultName(filterNameInput, filterName) {
    filterNameInput.value = filterName;

    filterNameInput.onclick = function () {
        defaultItemNameClick(filterNameInput);
    };
}

function updateFilterDefaultName(filterNameInput, selectedFormId) {
    //Updating only if nobody enetered nothing in input
    if (!filterNameInput.customTextEntered) {
        new ServiceCall().executeViaDwr("FilterDefaultNameGetterService", "get", selectedFormId, function (response) {
            setFilterDefaultName(filterNameInput, response);
        });
    }
}

function filterNameInputClick(filterNameInput) {
    filterNameInput.value = "";
    filterNameInput.onclick = "";
}

// ---------------------------------------------------------------------------------------------------------------------

