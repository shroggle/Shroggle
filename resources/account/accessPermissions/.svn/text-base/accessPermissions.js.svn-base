var errors;
function showShareYourSitesWindow(userId) {
    var window = createConfigureWindow({width:750, height:650});

    new ServiceCall().executeViaDwr("AddEditPermissionsService", "showShareYourSitesPage", userId, function (response) {
        window.setContent(response);

        //Accuring the error block
        errors = new Errors();
    });
}


function cancelAccessPermissions() {
    unselecteAllUsers();
    var errorBlock = document.getElementById("errorBlock");
    errorBlock.innerHTML = "";
}

function deleteAccount() {
    var selectedUsersIdArray = getSelectedUsersId();
    var loginedUserId = document.getElementById("loginedUserId").value;
    var errorBlock = document.getElementById("errorBlock");
    if (selectedUsersIdArray.length == 0) {
        alert(window.parent.internationalAccessPermissionsErrorTexts.pleaseSelectUser);
    } else {
        if (confirm(window.parent.internationalAccessPermissionsErrorTexts.deleteUserConfirmation)) {
            if (arrayContainsValue(selectedUsersIdArray, loginedUserId)) {
                if (!confirm(window.parent.internationalAccessPermissionsErrorTexts.yourUserIsDeleted)) {
                    return;
                }
            }

            var serviceCall = new ServiceCall();
            serviceCall.addExceptionHandler("com.shroggle.exception.OneSiteAdminException", function() {
                errorBlock.style.display = "inline";
                errorBlock.style.color = "red";
                errorBlock.innerHTML = window.parent.internationalAccessPermissionsErrorTexts.lastAdmin + "<br>";
                unselecteAllUsers();
            });
            serviceCall.addExceptionHandler("com.shroggle.exception.LoginedUserWithoutRightsException", function() {
                window.location = "/account/dashboard.action";
            });
            serviceCall.executeViaDwr("AddEditPermissionsService", "deleteUserRights", selectedUsersIdArray, function (response) {
                document.getElementById("accountUsersTable").innerHTML = response;
                if ($("#deletedUsers").length == 0) {
                    alert("You have no enough rights to delete user`s permissions.");
                }
            });
        }
    }
}

function arrayContainsValue(array, value) {
    for (var i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return true;
        }
    }
    return false;
}


function getSelectedUsersId() {
    var selectedUsersIdArray = new Array();
    for (var i = 0; i < window.parent.document.getElementById("usersNumber").value; i++) {
        var checkboxInput = window.parent.document.getElementById("user" + i);
        if (checkboxInput.checked) {
            var userValues = checkboxInput.value.split(";");
            selectedUsersIdArray.push(userValues[0]);
        }
    }
    return selectedUsersIdArray;
}

function unselecteAllUsers() {
    var checkboxes = $("input[type='checkbox']");
    for (var i = 0; ; i++) {
        var checkbox = checkboxes[i];
        if (checkbox == undefined) {
            break;
        }
        checkbox.checked = "";
    }
}


var shareYourSitesEmailRecent;
function shareYourSitesResentAnActivationEmail(accountEmail) {
    new ServiceCall().executeViaDwr("AddEditPermissionsService", "resendInvitation", accountEmail, showInvitationResentWindow());
}


// ---------------------------------------------------------------------------------------------------------------------

function showInvitationResentWindow() {
    var invitationResentWindow = createConfigureWindow({width:400, height:500});
    invitationResentWindow.setContent($("#shareYourSitesInvitationResent").html());
}

// ---------------------------------------------------------------------------------------------------------------------


function saveChanges(userId) {
    errors.clear();
    var request = new Object();
    request.invitationText = document.getElementById("invitationText").value;
    var selectedSitesMap = new Array();
    getSelectedSitesList(selectedSitesMap);
    /*if (!getSelectedSitesList(selectedSitesMap)) {
     errors.set("EMPTY_SITES",
     window.parent.internationalAccessPermissionsErrorTexts.selectOneOrMoreSites);
     }*/
    request.selectedSites = selectedSitesMap;
    if (errors.hasErrors()) {
        return;
    }
    request.userId = userId;

    new ServiceCall().executeViaDwr("AddEditPermissionsService", "changeUserInfo", request, function (response) {
        var table = window.parent.document.getElementById("accountUsersTable");
        table.innerHTML = response;
        closeConfigureWidgetDiv();
    });
    errors.clear();
}


function sendInvitation() {
    errors.clear();
    var request = new Object();
    request.email = document.getElementById("email").value;
    if (!request.email || request.email == "") {
        errors.set("EMPTY_EMAIL",
                window.parent.internationalAccessPermissionsErrorTexts.emptyEmail,
                [document.getElementById("email")]);
    }
    request.firstName = document.getElementById("firstName").value;
    request.lastName = document.getElementById("lastName").value;
    request.invitationText = document.getElementById("invitationText").value;
    var selectedSitesMap = new Array();
    if (!getSelectedSitesList(selectedSitesMap)) {
        errors.set("EMPTY_SITES",
                window.parent.internationalAccessPermissionsErrorTexts.selectOneOrMoreSites);
    }
    request.selectedSites = selectedSitesMap;
    if (request.invitationText == "") {
        errors.set("EMPTY_INVITATION_TEXT",
                window.parent.internationalAccessPermissionsErrorTexts.emptyInvitationText,
                [document.getElementById("invitationText")]);
    }
    if (errors.hasErrors()) {
        return;
    }

    getActiveWindow().disableContentBeforeSaveSettings("Sending invitation...");
    new ServiceCall().executeViaDwr("AddEditPermissionsService", "inviteUser", request, function (response) {
        if (response == "wrongEmail") {
            errors.set("WRONG_EMAIL",
                    request.email + window.parent.internationalAccessPermissionsErrorTexts.emailNotCorrect,
                    [document.getElementById("email")]);
        } else if (response == "userExist") {
            errors.set("USER_EXIST",
                    window.parent.internationalAccessPermissionsErrorTexts.userExist,
                    [document.getElementById("email")]);
        } else {
            closeConfigureWidgetDiv();
            var table = window.parent.document.getElementById("accountUsersTable");
            table.innerHTML = response;
        }
    });
    errors.clear();
}

function getSelectedSitesList(selectedSitesMap) {
    var numberOfNotNoneAccessLevel = 0;
    var sitesId = document.getElementById("sitesId").value.split(";");
    for (i = 0; i < sitesId.length; i++) {
        if (sitesId[i] != "") {
            var siteAccessLevelRadio = document.getElementsByName("accessLevel" + sitesId[i]);
            for (j = 0; j < 3; j++) {
                if (siteAccessLevelRadio[j].checked && siteAccessLevelRadio[j].value != "NONE") {
                    var siteAccessTypeHolder = new Object();
                    siteAccessTypeHolder.siteId = sitesId[i];
                    siteAccessTypeHolder.accessLevel = siteAccessLevelRadio[j].value;
                    if (siteAccessLevelRadio[j].value != "NONE") {
                        numberOfNotNoneAccessLevel++;
                    }
                    selectedSitesMap.push(siteAccessTypeHolder);
                    break;
                }
            }
        }
    }
    return  numberOfNotNoneAccessLevel > 0;
}
