var configureBlogSummary = {
    errors: undefined
};

configureBlogSummary.onBeforeShow = function (settings) {
    configureBlogSummary.settings = settings;
};

configureBlogSummary.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        disableConfigureBlogSummaryWidget();
    }

    //Accuring the error block
    configureBlogSummary.errors = new Errors({}, "blogSummaryErrors");
};

configureBlogSummary.save = function (closeAfterSaving) {
    configureBlogSummary.errors.clear();

    var request = {
        selectedBlogSummaryId: $("#selectedBlogSummaryId").val(),
        widgetId: configureBlogSummary.settings.widgetId,
        blogSummaryHeader: $("#BlogSummaryHeader").html(),
        displayBlogSummaryHeader: $("#showBlogSummaryHeader").val(),
        blogSummaryName: $("#newBlogSummaryName").val(),
        includedPostNumber: $("#numberOfBlogPosts").val(),
        numberOfWordsToDisplay: $("#numberOfWordsToDisplay").val(),
        postDisplayCriteria: $("#postDisplayCriteria").val(),
        postSortCriteria: $("#postSortCriteria").val()
    };

    var temp = 0;
    if ($("#showPostName").attr("checked")) {
        temp++;
        request.showPostName = true;
    }
    if ($("#showPostContents").attr("checked")) {
        temp++;
        request.showPostContents = true;
    }
    if ($("#showPostAuthor").attr("checked")) {
        temp++;
        request.showPostAuthor = true;
    }
    if ($("#showPostDate").attr("checked")) {
        temp++;
        request.showPostDate = true;
    }

    if ($("showBlogName").attr("checked") && !$("#showBlogName")[0].disabled) {
        temp++;
        request.showBlogName = true;
    }

    if (!temp > 0) {
        configureBlogSummary.errors.set("EMPTY_BLOG_SUMMARY_DATA", $("#emptyBlogSummaryDisplayedData").val(),
                [$("#newBlogSummaryName")[0]]);
    }

    if ($("#currentSite").attr("checked")) {
        request.currentSiteBlogs = true;
        request.includedCrossWidgetId = getSelectedCrossWidgetId("CurrentSite");
    } else if ($("#allBlogs").attr("checked")) {
        request.allSiteBlogs = true;
        request.includedCrossWidgetId = getSelectedCrossWidgetId("CurrentAccount");
    }

    if (!$("#newBlogSummaryName").val().length > 0) {
        configureBlogSummary.errors.set("EMPTY_BLOG_SUMMARY_NAME", $("#blogSummaryNameCannotBeEmpty").val(),
                [$("#newBlogSummaryName")[0]]);
    }

    if (configureBlogSummary.errors.hasErrors()) {
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.BlogSummaryNameNotUnique",
            configureBlogSummary.errors.exceptionAction({errorId:"BlogSummaryNameNotUnique", fields:[$("#newBlogSummaryName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateBlogSummaryService", "execute", request, function(response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + request.selectedBlogSummaryId).html(request.blogSummaryName);

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureBlogSummary.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureBlogSummary.settings.widgetId) {
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
        }
    });
};

function allAvailableBlogs() {
    var selectedBlogsId = getSelectedCrossWidgetId("CurrentAccount");
    disableBlogNameCheckbox(selectedBlogsId.length <= 1);
    $("#displayBlogFromCurrentSite").hide();
    $("#displayBlogFromCurrentAccount").show();
}

function currentSiteBlogs() {
    var selectedBlogsId = getSelectedCrossWidgetId("CurrentSite");
    disableBlogNameCheckbox(selectedBlogsId.length <= 1);
    $("#displayBlogFromCurrentSite").show();
    $("#displayBlogFromCurrentAccount").hide();
}

function getSelectedCrossWidgetId(id) {
    var selectedWidgetBlogs = new Array();
    var blogsId = $("#blogsIdFrom" + id).val();
    if (blogsId) {
        $(blogsId.split(";")).each(function() {
            if (document.getElementById(this + id).checked) {
                var crossWidgetId = parseInt($("#widgetBlogsFrom" + id + this)[0].value);
                if (crossWidgetId) {
                    selectedWidgetBlogs.push(crossWidgetId);
                }
            }
        });
    }
    return selectedWidgetBlogs;
}

function checkBlogWithSameId(blogId, checked, prefix) {
    var currentSiteCheckbox = document.getElementById(blogId + 'CurrentSite');
    var allSitesCheckbox = document.getElementById(blogId + 'CurrentAccount');
    if (currentSiteCheckbox) {
        currentSiteCheckbox.checked = checked;
    }
    if (allSitesCheckbox) {
        allSitesCheckbox.checked = checked;
    }
    var selectedBlogsId = getSelectedCrossWidgetId(prefix);
    disableBlogNameCheckbox(selectedBlogsId.length <= 1);
}

function disableBlogNameCheckbox(disabled) {
    document.getElementById("showBlogName").disabled = disabled;
}

function disableNumberOfWordsToDisplaySelect(disabled) {
    document.getElementById('numberOfWordsToDisplay').disabled = !disabled;
}

function disableConfigureBlogSummaryWidget() {
    disableControl($("#newBlogSummaryName")[0]);
    disableControl($("#blogSummaryShowEditorLink")[0]);
    disableControl($("#currentSite")[0]);
    disableControl($("#allBlogs")[0]);

    disableControls($("#displayBlogFromCurrentAccount").find("input[type='checkbox'], select"));
    disableControls($("#displayBlogFromCurrentSite").find("input[type='checkbox'], select"));

    disableControl($("#numberOfBlogPosts")[0]);
    disableControl($("#postDisplayCriteria")[0]);
    disableControl($("#postSortCriteria")[0]);
    disableControl($("#showPostContents")[0]);
    disableControl($("#showPostName")[0]);
    disableControl($("#showPostAuthor")[0]);
    disableControl($("#showPostDate")[0]);
    disableControl($("#showBlogName")[0]);
    disableControl($("#numberOfWordsToDisplay")[0]);

    $("#windowSave", $("#configureBlogSummaryButtons")[0]).hide();
    $("#windowApply", $("#configureBlogSummaryButtons")[0]).hide();
    $("#windowCancel", $("#configureBlogSummaryButtons")[0]).val("Close");

    $("#blogSummaryReadOnlyMessage").show();
    $("#blogSummaryErrors").hide();
}
