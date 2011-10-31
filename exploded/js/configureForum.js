var configureForum = {};
configureForum.errors = undefined;
configureForum.modified = undefined;

configureForum.onBeforeShow = function (settings) {
    configureForum.settings = settings;
};

configureForum.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureForum.modified = $("#modified").val();

    configureForum.errors = new Errors({}, "forumErrors");

    if ($("#siteOnItemRightType").val() == "READ") {
        disableForum(true);
    }
};

configureForum.save = function (closeAfterSaving) {
    configureForum.errors.clear();
    if ($("#forumName").val() == "") {
        configureForum.errors.set("EMPTY_FORUM_NAME", $("#emptyForumName").val(), [$("#forumName")[0]]);
        return;
    }

    configureForum.errors.clear();

    var request = {
        newForumName: $("#forumName").val(),
        forumId: getNullOrValue($("#selectedForumId").val()),
        createSubForumRight: getCheckedRadioButtonValue("subForum"),
        createThreadRight: getCheckedRadioButtonValue("thread"),
        createPostRight: getCheckedRadioButtonValue("post"),
        createPollRight: getCheckedRadioButtonValue("poll"),
        voteInPollRight: getCheckedRadioButtonValue("vote"),
        manageSubForumsRight: getCheckedRadioButtonValue("manageSubForums"),
        managePostsRight: getCheckedRadioButtonValue("managePosts"),
        allowPolls: $("#allowPolls").attr("checked"),
        allowSubForums: $("#allowSubForums").attr("checked"),
        widgetId: configureForum.settings.widgetId
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ForumNameNotUniqueException",
            configureForum.errors.exceptionAction({errorId:"FORUM_NAME_NOT_UNIQUE", fields:[$("#forumName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("EditForumService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + $("#selectedForumId").val()).html($("#forumName").val());
                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureForum.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureForum.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }

                    if (configureForum.modified == 'false') {
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

function getCheckedRadioButtonValue(name) {
    var radioButtons = document.getElementsByName(name);
    for (var i = 0; radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            return radioButtons[i].value;
        }
    }
    return null;
}

function checkAllowPolls() {
    var check = document.getElementById("ownerPollRights").disabled;
    if (!check) {
        disablePollsRadioButtons(true);
    } else {
        disablePollsRadioButtons(false);
    }
}

function checkAllowSubForums() {
    var check = document.getElementById("ownerSubForumRights").disabled;
    if (!check) {
        disableSubForumsRadioButtons(true);
    } else {
        disableSubForumsRadioButtons(false);
    }
}

function disableSubForumsRadioButtons(enable) {
    document.getElementById("ownerSubForumRights").disabled = enable;
    document.getElementById("visitorsSubForumRights").disabled = enable;
    document.getElementById("guestSubForumRights").disabled = enable;
}

function disablePollsRadioButtons(enable) {
    document.getElementById("ownerPollRights").disabled = enable;
    document.getElementById("guestPollRights").disabled = enable;
    document.getElementById("visitorsPollRights").disabled = enable;
    document.getElementById("allPollRights").disabled = enable;
    document.getElementById("ownerVoteRights").disabled = enable;
    document.getElementById("guestVoteRights").disabled = enable;
    document.getElementById("visitorsVoteRights").disabled = enable;
    document.getElementById("allVoteRights").disabled = enable;
}

function enablePolls(enable) {
    if (!enable) {
        disablePollsRadioButtons(true);
    } else {
        disablePollsRadioButtons(false);
    }
}

function enableSubForums(enable) {
    if (!enable) {
        disableSubForumsRadioButtons(true);
    } else {
        disableSubForumsRadioButtons(false);
    }
}

function applyNewForumParameters(forumInfo) {
    var forumName = $("#forumName")[0];
    forumName.value = forumInfo.forum.name;

    //Removing onclick event setted by default name getter.
    forumName.onclick = "";

    $("#" + forumInfo.forum.createSubForumRight.toLowerCase() + "SubForumRights")[0].checked = "checked";
    $("#" + forumInfo.forum.createThreadRight.toLowerCase() + "ThreadRights")[0].checked = "checked";
    $("#" + forumInfo.forum.createPostRight.toLowerCase() + "PostRights")[0].checked = "checked";
    $("#" + forumInfo.forum.createPollRight.toLowerCase() + "PollRights")[0].checked = "checked";
    $("#" + forumInfo.forum.voteInPollRight.toLowerCase() + "VoteRights")[0].checked = "checked";
    $("#" + forumInfo.forum.voteInPollRight.toLowerCase() + "ManageSubForumsRights")[0].checked = "checked";
    $("#" + forumInfo.forum.voteInPollRight.toLowerCase() + "ManagePostsRights")[0].checked = "checked";

    if (forumInfo.forum.allowPolls) {
        $("#allowPolls")[0].checked = "checked";
        enablePolls(true);
    } else {
        $("#allowPolls")[0].checked = "";
        enablePolls(false);
    }

    if (forumInfo.forum.allowSubForums) {
        $("#allowSubForums")[0].checked = "checked";
        enableSubForums(true);
    } else {
        $("#allowSubForums")[0].checked = "";
        enableSubForums(false);
    }

    //Selecting proper site
    var siteForCreate = $("#siteForCreate")[0];
    var sites = siteForCreate.options;
    for (var i = 0; ; i++) {
        if (sites[i] == undefined) {
            break;
        }

        if (sites[i].id == forumInfo.forum.siteId) {
            sites[i].selected = "selected";
            break;
        }
    }

    if (forumInfo.showOnItemRightType == "READ") {
        disableForum(true);
    } else {
        disableForum(false);
    }
}

function disableForum(disable) {
    disableControls($("input[name='subForum']"), disable);
    disableControls($("input[name='thread']"), disable);
    disableControls($("input[name='manageSubForums']"), disable);
    disableControls($("input[name='post']"), disable);
    disableControls($("input[name='poll']"), disable);
    disableControls($("input[name='managePosts']"), disable);
    disableControls($("input[name='vote']"), disable);
    disableControl($("#forumName")[0], disable);
    disableControl($("#allowPolls")[0], disable);
    disableControl($("#allowSubForums")[0], disable);

    $("#windowSave", $("#configureForumButtons")[0]).hide();
    $("#windowApply", $("#configureForumButtons")[0]).hide();
    $("#windowCancel", $("#configureForumButtons")[0]).val("Close");

    $("#forumReadOnlyMessage").show();
    $("#forumErrors").hide();
}