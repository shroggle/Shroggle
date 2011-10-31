var configureBlog = {};
configureBlog.modified = undefined;

configureBlog.onBeforeShow = function (settings) {
    configureBlog.settings = settings;
};

configureBlog.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureBlog.modified = $("#modified").val();
    clickCommentRightsAllow();
    clickCommentOnCommentRightsAllow();

    if ($("#siteOnItemRightType").val() == "READ") {
        disableBlog(true);
    }
};

configureBlog.save = function (closeAfterSaving) {
    var errors = new Errors({}, "blogErrors");
    errors.clear();

    var request = {
        displayAuthorEmailAddress: $("#displayAuthorEmailAddress").attr("checked"),
        displayAuthorScreenName: $("#displayAuthorScreenName").attr("checked"),
        displayDate: $("#displayDate").attr("checked"),
        displayBlogName: $("#displayBlogName").attr("checked"),
        displayNextAndPreviousLinks: $("#displayNextAndPreviousLinks").attr("checked"),
        displayBackToTopLink: $("#displayBackToTopLink").attr("checked"),
        displayPosts: $("#DISPLAY_ALL").attr("checked") ? "DISPLAY_ALL" :
                $("#DISPLAY_FINITE_NUMBER").attr("checked") ? "DISPLAY_FINITE_NUMBER" : "DISPLAY_WITHIN_DATE_RANGE",
        displayPostsFiniteNumber: $("#DISPLAY_FINITE_NUMBERSelect").val(),
        displayPostsWithinDateRange: $("#DISPLAY_WITHIN_DATE_RANGESelect").val(),
        blogId: $("#selectedBlogId").val(),
        blogName: $("#blogName").val(),
        widgetId: configureBlog.settings.widgetId
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.BlogNameNotUniqueException",
            errors.exceptionAction({errorId:"BlogName", fields:[$("#blogName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.BlogNameEmptyException",
            errors.exceptionAction({errorId:"BlogName", fields:[$("#blogName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    fillRequestRights(request);
    // edit blog settings
    serviceCall.executeViaDwr("EditBlogService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + $("#selectedBlogId").val()).html($("#blogName").val());

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureBlog.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureBlog.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }

                    if (configureBlog.modified == 'false') {
                        contentsWidget();
                    }
                }
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        }
    });
};

function fillRequestRights(request) {
    var commentOnCommentRightsAllow = document.getElementById("commentToCommentRightsAllow");
    if (commentOnCommentRightsAllow.checked) {
        request.writeCommentsOnComments = getCheckedItemFromArrays(
                document.getElementsByName("commentOnCommentRights")).value;
    }
    var commentRightsAllow = document.getElementById("commentRightsAllow");
    if (commentRightsAllow.checked) {
        request.writeComments = getCheckedItemFromArrays(
                document.getElementsByName("commentRights")).value;
    }
    request.writePosts = getCheckedItemFromArrays(
            document.getElementsByName("postRights")).value;
    request.editBlogPostRight = getCheckedItemFromArrays(
            document.getElementsByName("editBlogPostRight")).value;
    request.editCommentRight = getCheckedItemFromArrays(
            document.getElementsByName("editCommentRight")).value;
}

function getCheckedItemFromArrays(itemsArray) {
    for (var i = 0; i < itemsArray.length; i++) {
        if (itemsArray[i].checked) return itemsArray[i];
    }
    return null;
}

function clickCommentOnCommentRightsAllow() {
    var commentOnCommentRightsAllow = document.getElementById("commentToCommentRightsAllow");
    var commentOnCommentRights = document.getElementsByName("commentOnCommentRights");
    for (var i = 0; i < commentOnCommentRights.length; i++) {
        commentOnCommentRights[i].disabled = commentOnCommentRightsAllow.checked ? "" : "disabled";
    }
}

function clickCommentRightsAllow() {
    var commentRightsAllow = document.getElementById("commentRightsAllow");
    var commentRights = document.getElementsByName("commentRights");
    for (var i = 0; i < commentRights.length; i++) {
        commentRights[i].disabled = commentRightsAllow.checked ? "" : "disabled";
    }
}

function disableBlog(disable) {
    disableControls($("input[name='postRights']"), disable);
    disableControls($("input[name='editBlogPostRight']"), disable);
    if (disable || (!disable && $("#commentRightsAllow")[0].checked)) {
        disableControls($("input[name='commentRights']"), disable);
    }
    disableControls($("input[name='editCommentRight']"), disable);
    if (disable || (!disable && $("#commentToCommentRightsAllow")[0].checked)) {
        disableControls($("input[name='commentOnCommentRights']"), disable);
    }
    disableControl($("#blogName")[0], disable);
    disableControl($("#commentRightsAllow")[0], disable);
    disableControl($("#commentToCommentRightsAllow")[0], disable);
    disableControl($("#blogDisplayLinksOptions")[0], disable);
    disableControl($("#blogDisplayPostsOptions")[0], disable);

    $("#windowSave", $("#configureBlogButtons")[0]).hide();
    $("#windowApply", $("#configureBlogButtons")[0]).hide();
    $("#windowCancel", $("#configureBlogButtons")[0]).val("Close");

    $("#blogReadOnlyMessage").show();
    $("#blogErrors").hide();
}


function disableDisplayPostsSelect(displayPostValue) {
    var finiteNumberSelect = document.getElementById("DISPLAY_FINITE_NUMBERSelect");
    var dateRangeSelect = document.getElementById("DISPLAY_WITHIN_DATE_RANGESelect");
    finiteNumberSelect.disabled = true;
    dateRangeSelect.disabled = true;
    switch (displayPostValue) {
        case "DISPLAY_FINITE_NUMBER": {
            finiteNumberSelect.disabled = false;
            break;
        }
        case "DISPLAY_WITHIN_DATE_RANGE": {
            dateRangeSelect.disabled = false;
            break;
        }
    }
}