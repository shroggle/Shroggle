var inviteGuests = {};

inviteGuests.errors = undefined;

inviteGuests.show = function (siteId) {
    var configureWindow = createConfigureWindow({width:610, height:400});

    new ServiceCall().executeViaDwr("InviteGuestService", "show", siteId, function (html) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureWindow.setContent(html);
        inviteGuests.errors = new Errors();
    });
};

inviteGuests.sendInvitation = function(siteId) {

    var request = {
        firstName : $("#firstNameInvite").val(),
        lastName : $("#lastNameInvite").val(),
        email : $("#emailInvite").val(),
        formId : $("#regFormSelect > option:selected").val(),
        siteId : siteId,
        invitationText : $("#emailMessage").val(),
        groupsWithTimeInterval : groups.getGroupsWithTimeInterval()
    };

    inviteGuests.errors.clear();

    if (request.email.length == 0) {
        inviteGuests.errors.set("EmailNullOrEmptyException",
                "Please enter email.", [$("#emailInvite")[0]]);
    }

    if (request.invitationText.length == 0) {
        inviteGuests.errors.set("InvitationTextNullOrEmptyException",
                "Please enter invitation text.", [$("#emailMessage")[0]]);
    }

    if (request.formId == -1) {
        inviteGuests.errors.set("RegistrationFormNotSelectedException",
                "Please select registratiom form for guest to complete.", [$("#regFormSelect")[0]]);
    }

    if (inviteGuests.errors.hasErrors()) {
        return;
    }

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.IncorrectEmailException",
            inviteGuests.errors.exceptionAction({errorId:"EMAIL_NOT_VALID", fields:[$("#emailInvite")[0]], alternativeMessage:"Please enter valid email address."}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorWithNotUniqueLogin",
            inviteGuests.errors.exceptionAction({errorId:"VISITOR_ALREADY_EXISTS", fields:[$("#emailInvite")[0]], alternativeMessage:"", onException:""}));
    serviceCall.executeViaDwr("InviteGuestService", "sendGuestInvitation", request, function (response) {
        if (response) {
            manageRegistrants.search();
            $("#updateDiv").html($("#visitorInvited").val());
            addSlidingTimeoutEvent($("#updateDiv")[0], 3000);
            closeConfigureWidgetDiv();
        }
    });
};

function disableTimeIntervalSelect(groupId) {
    $("#limitedTimeForGroupSelect" + groupId)[0].disabled = !$("#limitedTimeForGroupCheckbox" + groupId)[0].checked;
}

function disableTimeIntervalCheckbox(groupId) {
    if (!$("#groupId" + groupId)[0].checked) {
        $("#limitedTimeForGroupCheckbox" + groupId)[0].disabled = true;
        $("#limitedTimeForGroupCheckbox" + groupId)[0].checked = false;
    } else {
        $("#limitedTimeForGroupCheckbox" + groupId)[0].disabled = false;
    }
    disableTimeIntervalSelect(groupId);
}