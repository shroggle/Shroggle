var createSite = {};
createSite.errors = undefined;

// ---------------------------------------------------------------------------------------------------------------------

createSite.checkSiteUrlPrefix = function() {
    var siteUrlPrefixCheckResult = $("#siteUrlPrefixCheckResult");
    var siteUrl = $("#siteUrlPrefix");
    var siteId = $("#createSiteSiteId").val() == "null" ? null : $("#createSiteSiteId").val();

    siteUrlPrefixCheckResult.html("Checking if selected site url is available...");
    new ServiceCall().executeViaDwr("CheckSiteUrlPrefixService", "execute", siteUrl.val(), siteId, function (response) {
        if (response) {
            siteUrlPrefixCheckResult.html("<span style=\"color: red; font-weight: bold\">" + response + "</span>");
            siteUrl[0].className = "title errorBorder";
        } else {
            siteUrlPrefixCheckResult.html("<span style=\"color: green; font-weight: bold\">" +
                    "The sub domain which you have chosen is available.<br>" +
                    "Please confirm that you would like to use this name as your web site address." +
                    "</span>");
            siteUrl[0].className = "title";
        }
    });
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.checkBrandedSubDomain = function(workChildSiteRegistrationId) {
    var brandedSubDomainCheckResult = $("#brandedSubDomainCheckResult");
    var brandedSubDomain = $("#brandedSubDomain");
    var siteId = $("#createSiteSiteId").val() == "null" ? null : $("#createSiteSiteId").val();

    brandedSubDomainCheckResult.html("Checking if selected site url is available...");
    new ServiceCall().executeViaDwr("CheckBrandedSubDomainService", "execute",
            brandedSubDomain.val(), workChildSiteRegistrationId, siteId, function (response) {
        if (response) {
            brandedSubDomainCheckResult.html("<span style=\"color: red; font-weight: bold\">" + response + "</span>");
            brandedSubDomain[0].className = "title errorBorder";
        } else {
            brandedSubDomainCheckResult.html("<span style=\"color: green; font-weight: bold\">" +
                    "The sub domain which you have chosen is available.<br>" +
                    "Please confirm that you would like to use this name as your web site address." +
                    "</span>");
            brandedSubDomain[0].className = "title";
        }
    });
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.checkSiteAlias = function() {
    var siteAliasCheckResult = $("#siteAliasCheckResult");
    var aliasUrl = $("#customUrl");
    var siteId = $("#createSiteSiteId").val() == "null" ? null : $("#createSiteSiteId").val();

    if (aliasUrl.val().length < 1) {
        siteAliasCheckResult.html("");
        aliasUrl[0].className = "title";
        return;
    }

    siteAliasCheckResult.html("Checking selected domain name ...");
    new ServiceCall().executeViaDwr("CheckSiteAliasService", "execute", aliasUrl.val(), siteId, function (response) {
        if (response) {
            siteAliasCheckResult.html("<span style=\"color: red; font-weight: bold\">" + response + "</span>");
            aliasUrl[0].className = "title errorBorder";
        } else {
            siteAliasCheckResult.html("<span style=\"color: green; font-weight: bold\">" +
                    "The domain you have chosen is available and already pointing to Web-Deva IP address.<br>" +
                    "Please note that Web-Deva will support your site on two domain (your own domain and Web-Deva sub-domain)." +
                    "</span>");
            aliasUrl[0].className = "title";
        }
    });
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.getKeywordsGroupCount = function () {
    return $("tr.siteKeywordGroupRow").length;
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.addKeywordsGroup = function() {
    var keywordsGroups = $("#keywordsGroups")[0];
    var lastIndex = createSite.getKeywordsGroupCount();
    var keywordsGroup = keywordsGroups.insertRow(keywordsGroup);
    keywordsGroup.className = "siteKeywordGroupRow";
    keywordsGroup.id = "keywordsGroup" + lastIndex;
    var keywordsGroupTdName = document.createElement("td");
    var keywordsGroupTdValue = document.createElement("td");
    var keywordsGroupTdDelete = document.createElement("td");
    keywordsGroupTdDelete.className = "siteKeywordsDeleteColumn";
    keywordsGroup.appendChild(keywordsGroupTdName);
    keywordsGroup.appendChild(keywordsGroupTdValue);
    keywordsGroup.appendChild(keywordsGroupTdDelete);
    var keywordsGroupName = document.createElement("input");
    keywordsGroupName.type = "text";
    keywordsGroupName.name = "keywordsGroups[" + lastIndex + "].name";
    keywordsGroupName.id = "keywordsGroupName" + lastIndex;
    keywordsGroupName.className = "txt95";
    keywordsGroupTdName.appendChild(keywordsGroupName);
    var keywordsGroupValue = document.createElement("input");
    keywordsGroupValue.type = "text";
    keywordsGroupValue.className = "txt95";
    keywordsGroupValue.name = "keywordsGroups[" + lastIndex + "].value";
    keywordsGroupValue.id = "keywordsGroupValue" + lastIndex;
    keywordsGroupTdValue.appendChild(keywordsGroupValue);
    var keywordsGroupId = document.createElement("input");
    keywordsGroupId.type = "hidden";
    keywordsGroupId.name = "keywordsGroups[" + lastIndex + "].id";
    keywordsGroupId.value = lastIndex;
    keywordsGroupTdValue.appendChild(keywordsGroupId);
    var keywordsGroupDelete = document.createElement("input");
    keywordsGroupDelete.type = "image";
    keywordsGroupDelete.value = "Delete";
    keywordsGroupDelete.src = "/images/cross-circle.png";
    keywordsGroupDelete.onclick = function () {
        return createSite.deleteKeywordsGroup(keywordsGroupDelete);
    };
    keywordsGroupTdDelete.appendChild(keywordsGroupDelete);
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.deleteKeywordsGroup = function (element) {
    var confirmDeleteMessage = "Are you sure that you want to remove the meta-tag option." +
            " It will be removed from all site pages where it‘s used.";
    if (confirm(confirmDeleteMessage)) {
        $(element).parents(".siteKeywordGroupRow").remove();
    }
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.showHowToSetUpDomainNameForSite = function() {
    var helpWindow = createConfigureWindow({
        width: 600,
        height: 300
    });
    helpWindow.setContent($("#siteUrlHelpWindow").html());
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.addCustomMetaTag = function () {
    var metaTagValue = $("#addCustomMetaTagInput").val();
    $("#addCustomMetaTagInput").val("");

    var HTMLToInsert = "<tr class='customMetaTagRow'>" +
            "<td class='createSiteAddCustomMetaTagNameColumn'>" +
            "<input type='text' class='customMetaTagInput txt95' name='seoSettings.customMetaTagList' value='" + metaTagValue + "'/>" +
            "</td>" +
            "<td class='createSiteAddCustomMetaTagDeleteColumn'>" +
            "<input type='image' class='customMetaTagDeleteImg' onclick='createSite.removeCustomMetaTag(this)'" +
            " src='/images/cross-circle.png' value='Delete'>" +
            "</td>" +
            "</tr>";

    if ($("tr.customMetaTagRow").length > 0) {
        $("tr.customMetaTagRow:last").after(HTMLToInsert);
    } else {
        $("#customMetaTagBody").prepend(HTMLToInsert);
    }
};

createSite.removeCustomMetaTag = function (element) {
    $(element).parents("tr:first").remove();
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.addCustomHtmlCode = function () {
    var htmlCodeName = $("#htmlCodeInput").val();
    var htmlCode = $("#htmlCodeTextArea").val();
    var htmlCodePlacement = $("input[name='htmlCodeRadio']:checked").val();
    //    var currentCustomHtmlCount = getCurrentCustomHtmlCount();

    $("#htmlCodeInput").val("");
    $("#htmlCodeTextArea").val("");

    var HTMLToInsert = "<tr class='customHtmlCodeRow'>" +
            "<td class='createSiteCustomHtmlCodeNameColumn'>" +
            "<input type='hidden' class='customHtmlCodeNameHiddenInput' name='seoSettings.htmlCodeList[" + customHtmlMaxIndex + "].name' value='" + htmlCodeName + "'/>" +
            htmlCodeName +
            "</td>" +
            "" +
            "<td class='createSiteCustomHtmlCodeColumn'>" +
            "<textarea name='seoSettings.htmlCodeList[" + customHtmlMaxIndex + "].code' readonly='readonly' class='createSiteCustomHtmlCodeTextArea'></textarea>" +
            "</td>" +
            "" +
            "<td class='createSiteCustomHtmlCodePlacementColumn'>" +
            "<input type='hidden' class='customHtmlCodePlacementHiddenInput' name='seoSettings.htmlCodeList[" + customHtmlMaxIndex + "].codePlacement' value='" + htmlCodePlacement + "'/>" + htmlCodePlacement +
            "</td>" +
            "" +
            "<td class='createSiteCustomHtmlCodeDeleteColumn'>" +
            "<input type='image' class='customMetaTagDeleteImg' onclick='createSite.removeCustomHtmlCode(this)' src='/images/cross-circle.png' value='Delete'>" +
            "</td></tr>";

    if ($("tr.customHtmlCodeRow").length > 0) {
        $("tr.customHtmlCodeRow:last").after(HTMLToInsert);
    } else {
        $("#customHtmlBody").prepend(HTMLToInsert);
    }
    $("tr.customHtmlCodeRow:last textarea").val(htmlCode);
    customHtmlMaxIndex++;

    //    function getCurrentCustomHtmlCount() {
    //        return $("tr.customHtmlCodeRow").length;
    //    }
};

createSite.removeCustomHtmlCode = function (element) {
    $(element).parents("tr:first").remove();
};

// ---------------------------------------------------------------------------------------------------------------------

createSite.showGeneralSettingsTab = function () {
    var tab = $("#generalSettingsTab")[0];
    selectTabNew(tab);
    $("#SEOSettingsTagContentDiv").hide();
    $("#generalSettingsTabContentDiv").show();
};

createSite.showSEOSettingsTab = function () {
    var tab = $("#SEOSettingsTab")[0];
    selectTabNew(tab);
    $("#generalSettingsTabContentDiv").hide();
    $("#SEOSettingsTagContentDiv").show();
};

// ---------------------------------------------------------------------------------------------------------------------