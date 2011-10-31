var groups = {
    errors:{},

    getCheckedVisitorsId : function () {
        var ids = [];
        $(groups.getVisitorsCheckboxes()).each(function() {
            if (this.checked) {
                ids.push($(this).attr("id"));
            }
        });
        return ids;
    },

    getVisitorsCheckboxes : function () {
        return $.find(":checkbox[name=addRemoveVisitorsToGroup]");
    },

    checkDuplicateGroups : function (inputsWithGroupName) {
        var names = [];
        var duplicateNames = [];
        $(inputsWithGroupName).each(function() {
            if ($(this).attr("defaultTr") != "true") {
                var name = $(this).val();
                if (nameContainsInArray(names, name)) {
                    duplicateNames.push(name);
                } else {
                    names.push(name);
                }
            }
        });
        $(inputsWithGroupName).each(function() {
            if ($(this).attr("defaultTr") != "true") {
                var name = $(this).val();
                if (nameContainsInArray(duplicateNames, name)) {
                    groups.errors.set("DUPLICATE_GROUP_NAME",
                            $("#duplicateGroupNames").val(),
                            [this]);
                }
            }
        });

        function nameContainsInArray(array, name) {
            var contains = false;
            $(array).each(function() {
                if (this == name) {
                    contains = true;
                }
            });
            return contains;
        }
    }
};


groups.removeVisitorsFromGroup = function (groupId) {
    if (groupId <= 0) {
        $("#removeFromGroup").val("-1");
        return;
    }
    var checkedVisitorsId = groups.getCheckedVisitorsId();
    if (checkedVisitorsId.length == 0) {
        $("#removeFromGroup").val("-1");
        return;
    }
    var tableContainer = $("#registrantsDiv")[0];
    createLoadingArea({element:tableContainer, text: "Removing visitors from groups, please wait ...", color:"green", guaranteeVisibility:true});

    new ServiceCall().executeViaDwr("RemoveVisitorsFromGroupService", "execute", groupId, checkedVisitorsId, manageRegistrants.createManageRegistrantsRequest(), function(response) {
        if (response) {
            $(tableContainer).html(response);
            removeLoadingArea();
        }
    });
};

groups.removeGroup = function(groupId) {
    if (confirm($("#removeGroupConfirm").val())) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        getActiveWindow().disableContent({backgroundColor: "black", backgroundOpacity: 0.1, text: "Removing group...",
            fontWeight: "bold", color: "green", borderStyle:"solid"});
        serviceCall.executeViaDwr("RemoveGroupService", "execute", groupId, manageRegistrants.createManageRegistrantsRequest(), function(response) {
            if (response) {
                $("#registrantsDiv").html(response);
                $("#trWithGroup" + groupId).remove();
                getActiveWindow().enableContent();
                getActiveWindow().resize();
            }
        });
    }
};

groups.addGroup = function (siteId) {
    var serviceCall = new ServiceCall();

    getActiveWindow().disableContent({backgroundColor: "black", backgroundOpacity: 0.1, text: "Adding group...",
        fontWeight: "bold", color: "green", borderStyle:"solid"});
    serviceCall.executeViaDwr("AddGroupService", "execute", siteId, manageRegistrants.createManageRegistrantsRequest(), function(response) {
        var groupIdWithNewName = response.groupIdWithNewName;
        var newGroupId = groupIdWithNewName.groupId;
        var newGroupName = groupIdWithNewName.name;
        $("#registrantsDiv").html(response.html);

        var table = $("#groupsTable")[0];

        // Cloning default tr.
        var clonedTr = $(table).find("#defaultTrWithGroup").clone()[0];

        // Setting correct style for tr
        if ($(table).find("tr:last")[0].className == "odd") {
            clonedTr.className = "";
        } else {
            clonedTr.className = "odd";
        }
        // Setting correct id by new group
        clonedTr.id = ("trWithGroup" + newGroupId);

        // Setting group name and hidden group id
        var textField = $(clonedTr).find(":text[name=groupNameWithId]")[0];
        textField.id = newGroupId;
        $(textField).val(newGroupName);
        $(textField).removeAttr("defaultTr");

        // Setting visitors count
        $(clonedTr).find("[name=visitorsCount]").html(0);

        $(clonedTr).find(":image[name=deleteGroupButton]")[0].onclick = function() {
            groups.removeGroup(newGroupId);
        };

        $(table).append(clonedTr);

        $(clonedTr).show();
        getActiveWindow().enableContent();
        getActiveWindow().resize();
    });
};

groups.updateGroupNames = function () {
    groups.errors.clear();
    var request = new Object();
    request.manageRegistrantsRequest = manageRegistrants.createManageRegistrantsRequest();
    request.groupIdWithNewName = [];
    var inputsWithGroupName = $.find("input[name=groupNameWithId]");
    $(inputsWithGroupName).each(function() {
        if ($(this).attr("defaultTr") != "true") {
            var name = $(this).val();
            request.groupIdWithNewName.push({groupId : $(this).attr("id"), name : name});

            if (name.length == 0) {
                groups.errors.set("EMPTY_GROUP_NAME",
                        $("#groupNameCantBeEmpty").val(),
                        [this]);
            }
        }
    });
    groups.checkDuplicateGroups(inputsWithGroupName);
    if (groups.errors.hasErrors()) {
        return;
    }

    getActiveWindow().disableContentBeforeSaveSettings();
    new ServiceCall().executeViaDwr("SaveGroupsService", "execute", request, function(response) {
        if (response) {
            $("#registrantsDiv").html(response);
            closeConfigureWidgetDiv();
        }
    });
};

groups.showConfigureGroupsWindow = function (siteId) {
    var configureGroups = createConfigureWindow({width:700, height:500});
    new ServiceCall().executeViaDwr("ShowGroupsService", "execute", siteId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureGroups.setContent(response);

        //Accuring the error block
        groups.errors = new Errors();
    });
};

groups.showManageGroupsWindow = function(siteId) {
    var checkedVisitorsId = groups.getCheckedVisitorsId();
    if (checkedVisitorsId.length == 0) {
        alert($("#selectVisitors").val());
        return;
    }
    var manageGroupsWindow = createConfigureWindow({width:500, height:400});
    new ServiceCall().executeViaDwr("ShowManageGroupsWindowService", "execute", siteId, checkedVisitorsId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        manageGroupsWindow.setContent(response);

        //Accuring the error block
        groups.errors = new Errors();
    });
};

groups.addVisitorsToGroups = function(checkedVisitorsId) {
    var tableContainer = $("#registrantsDiv")[0];
    getActiveWindow().disableContentBeforeSaveSettings("Adding visitors to groups, please wait ...");
    new ServiceCall().executeViaDwr("AddVisitorsToGroupService", "execute", groups.getGroupsWithTimeInterval(), checkedVisitorsId.split(","), manageRegistrants.createManageRegistrantsRequest(), function(response) {
        if (response) {
            $(tableContainer).html(response);
            closeConfigureWidgetDiv();
        }
    });
};


groups.getGroupsWithTimeInterval = function() {
    var groupsId = getVisitorsGroups();
    var groupsWithTimeInterval = [];
    for (var i in groupsId) {
        var groupId = groupsId[i];
        groupsWithTimeInterval.push({
            groupId : groupId,
            timeInterval : ($("#limitedTimeForGroupCheckbox" + groupId)[0].checked ? $("#limitedTimeForGroupSelect" + groupId).val() : null)
        });
    }
    return groupsWithTimeInterval;

    function getVisitorsGroups() {
        var visitorsGroups = [];
        $("#placeInviteeIntoTheFollowingGroups").find("input[id^=groupId]").each(function() {
            if (this.checked) {
                visitorsGroups.push($(this).attr("id").split("groupId")[1]);
            }
        });
        return visitorsGroups;
    }
};