function showRegistrationBlockIfConfirm(widgetId) {
    if (confirm($("#youHaveToBeLoggedIn" + widgetId).val())) {
        showGalleryRegistrationBlock(widgetId);
    }
}

function showGalleryRegistrationBlock(widgetId) {
    showGalleryLogin($(".blockToReload" + widgetId), widgetId);
}

function showGalleryLogin(block, widgetId) {
    var formId = $("#registrationFormIdForVoters" + widgetId).val();
    if ($("#itemType" + widgetId).val() == "MANAGE_VOTES") {
        new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForManageVotes", widgetId, formId, function(data) {
            $(block).hide();
            $(block).parent().append(data);

            bindLoginFormSubmitEvent(widgetId);
        });
    } else if ($("#itemType" + widgetId).val() == "GALLERY" ||
            $("#itemType" + widgetId).val() == "GALLERY_DATA") {
        new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForGalleryWithForm", widgetId, formId, null, function(data) {
            $(block).hide();
            $(block).parent().append(data);

            bindLoginFormSubmitEvent(widgetId);
        });
    }
}