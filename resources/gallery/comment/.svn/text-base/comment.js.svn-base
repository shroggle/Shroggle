function showGalleryComments(a, filledFormId, galleryId, widgetId, showFromAddComments) {
    if ($(".galleryComment", a.parentNode)[0]) {
        return false;
    }

    new ServiceCall().executeViaDwr("ShowGalleryCommentService", "execute", {
        filledFormId: filledFormId,
        galleryId: galleryId
    }, function (galleryComments) {
        var galleryCommentsDiv = $(a.parentNode);
        $(a).hide();
        showFromAddComments ? $(".hideGalleryCommentsLink", galleryCommentsDiv).hide() :
                $(".hideGalleryCommentsLink", galleryCommentsDiv).show();

        for (var i = 0; i < galleryComments.length; i++) {
            showGalleryComment(galleryComments[i], galleryCommentsDiv, widgetId);
        }
    });
    return false;
}

// ---------------------------------------------------------------------------------------------------------------------

function hideGalleryComments(a) {
    $(a).hide();
    $(".viewGalleryCommentsLink", a.parentNode).show();
    $(".galleryComment", a.parentNode).remove();
}

// ---------------------------------------------------------------------------------------------------------------------

function showGalleryComment(galleryComment, galleryCommentsDiv, widgetId) {
    var galleryCommentDiv = document.createElement("div");
    galleryCommentDiv.className = "galleryComment galleryCommentAlt";
    galleryCommentDiv.commentId = galleryComment.commentId;

    var galleryCommentInfoBlock = document.createElement("span");
    galleryCommentInfoBlock.className = "galleryCommentInfoBlock";

    var galleryCommentAuthor = document.createElement("span");
    galleryCommentAuthor.className = "galleryCommentAuthor";
    galleryCommentAuthor.innerHTML = galleryComment.userEmail;
    $(galleryCommentInfoBlock).append(galleryCommentAuthor);

    var galleryCommentCreationDate = document.createElement("span");
    galleryCommentCreationDate.className = "galleryCommentCreationDate";
    galleryCommentCreationDate.innerHTML = galleryComment.creationDate;
    $(galleryCommentInfoBlock).append(galleryCommentCreationDate);

    $(galleryCommentDiv).append(galleryCommentInfoBlock);

    var galleryCommentText = document.createElement("div");
    galleryCommentText.className = "galleryCommentText";
    galleryCommentText.innerHTML = galleryComment.text;
    $(galleryCommentDiv).append(galleryCommentText);

    var loginedUserId = $("#galleryLoginedUserId" + widgetId).val();
    if (galleryComment.userId == loginedUserId || galleryComment.showFromAdd) {
        $(galleryCommentDiv).append("<a href=\"#\" class=\"galleryCommentEdit\">Edit</a>");
        $(galleryCommentDiv).append(" <a href=\"#\" class=\"galleryCommentRemove\">Remove</a>");
    }

    if ($(".addGalleryCommentBlock", galleryCommentsDiv)[0]) {
        $(".addGalleryCommentBlock", galleryCommentsDiv).before(galleryCommentDiv);
    } else {
        $(galleryCommentsDiv).append(galleryCommentDiv);
    }

    $(".galleryCommentEdit", galleryCommentDiv).click(function () {
        $(galleryCommentDiv).hide();

        var editGalleryCommentDiv = document.createElement("div");
        $(galleryCommentDiv).after(editGalleryCommentDiv);
        var editorId = "editGalleryComment" + galleryComment.commentId;
        createEditor({
            value: $(galleryCommentText).html(),
            width: parseInt($(".editorWidth", galleryCommentsDiv).val()),
            height: parseInt($(".editorHeight", galleryCommentsDiv).val()),
            showLastSavedData: true,
            root: "../",
            editorId: editorId,
            place: editGalleryCommentDiv
        });
        editGalleryCommentDiv.appendChild(createTextEditorButtons(editorId));

        $(".cancelTextEditor", editGalleryCommentDiv).click(function () {
            closeEditor("editGalleryComment" + galleryComment.commentId);
            $(editGalleryCommentDiv).remove();
            $(galleryCommentDiv).show();
        });

        $(".saveTextEditor", editGalleryCommentDiv).click(function () {
            var text = getEditorContent("editGalleryComment" + galleryComment.commentId);
            new ServiceCall().executeViaDwr("EditGalleryCommentService", "execute", {
                commentId: galleryComment.commentId,
                text: text
            }, function () {
                closeEditor("editGalleryComment" + galleryComment.commentId);
                $(editGalleryCommentDiv).remove();
                $(galleryCommentDiv).show();

                $(galleryCommentText).html(text);
                $(galleryCommentDiv).show();
            });
        });

        return false;
    });

    $(".galleryCommentRemove", galleryCommentDiv).click(function () {
        if (confirm("You are about to permanently delete this comment. Clicks Ok to proceed.")) {
            new ServiceCall().executeViaDwr("RemoveGalleryCommentService", "execute",
                    galleryComment.commentId, function () {
                $(galleryCommentDiv).remove();
                $(".galleryCommentCount", galleryCommentsDiv).html(parseInt($(".galleryCommentCount", galleryCommentsDiv).html()) - 1);
            });
        }
        return false;
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showAddGalleryComment(a, filledFormId, galleryId, siteId, widgetId) {
    new ServiceCall().executeViaDwr("IsRightUserToVoteCommentService", "execute", siteId, function (response) {
        if (response) {
            showGalleryComments($(".viewGalleryCommentsLink", a.parentNode)[0], filledFormId, galleryId, widgetId, true);
            $(a).hide();

            var galleryCommentsDiv = a.parentNode;

            var addGalleryCommentDiv = document.createElement("div");
            addGalleryCommentDiv.className = "addGalleryCommentBlock";
            addGalleryCommentDiv.style.marginTop = "5px";
            $(galleryCommentsDiv).append(addGalleryCommentDiv);
            var editorId = "addGalleryComment" + filledFormId + galleryId;
            createEditor({
                value: "",
                width: "100%",
                height: parseInt($(".editorHeight", a.parentNode).val()),
                showLastSavedData: true,
                root: "../",
                editorId: editorId,
                place: addGalleryCommentDiv
            });
            addGalleryCommentDiv.appendChild(createTextEditorButtons(editorId));

            $(".cancelTextEditor", addGalleryCommentDiv).click(function () {
                closeEditor("addGalleryComment" + filledFormId + galleryId);
                $(addGalleryCommentDiv).remove();
                $(a).show();

                $(".hideGalleryCommentsLink", galleryCommentsDiv).show();
            });

            $(".saveTextEditor", addGalleryCommentDiv).click(function () {
                var serviceCall = new ServiceCall();
                serviceCall.addExceptionHandler(
                        LoginInAccount.EXCEPTION_CLASS,
                        showCommentsRegistrationBlock);

                var text = getEditorContent("addGalleryComment" + filledFormId + galleryId);
                serviceCall.executeViaDwr("AddGalleryCommentService", "execute", {
                    filledFormId: filledFormId,
                    galleryId: galleryId,
                    siteId: siteId,
                    text: text
                }, function (comment) {
                    closeEditor("addGalleryComment" + filledFormId + galleryId);
                    $(addGalleryCommentDiv).remove();
                    $(a).show();

                    var afterAddingCommentMessage = $(a.parentNode).find(".afterAddingCommentBlock")[0];
                    addSlidingTimeoutEvent(afterAddingCommentMessage, 2500);

                    //Adding one to comment count.
                    $(".galleryCommentCount", a.parentNode).html(parseInt($(".galleryCommentCount", a.parentNode).html()) + 1);

                    comment.showFromAdd = true;
                    showGalleryComment(comment, a.parentNode, widgetId);

                    $(".hideGalleryCommentsLink", galleryCommentsDiv).show();
                });
            });

            return false;
        } else {
            showCommentsRegistrationBlock();
        }
    });

    function showCommentsRegistrationBlock() {
        showRegistrationBlockIfConfirm(widgetId);
    }
}


function createTextEditorButtons(editorId, onSaveCommand, onCancelCommand) {
    var saveButton = document.createElement("input");
    //if (saveButtonId) saveButton.id = saveButtonId;
    saveButton.type = "button";
    saveButton.className = "but_w73 saveTextEditor";
    saveButton.onmouseover = function () {
        saveButton.className = "but_w73_Over saveTextEditor";
    };
    saveButton.onmouseout = function () {
        saveButton.className = "but_w73 saveTextEditor";
    };
    saveButton.onclick = function () {
        this.disabled = "disabled";
        if (onSaveCommand) {
            eval(onSaveCommand);
        }
    };
    saveButton.value = "Save";

    var cancelButton = document.createElement("input");
    cancelButton.type = "button";
    cancelButton.className = "but_w73 cancelTextEditor";
    cancelButton.onmouseover = function () {
        cancelButton.className = "but_w73_Over cancelTextEditor";
    };
    cancelButton.onmouseout = function () {
        cancelButton.className = "but_w73 cancelTextEditor";
    };
    cancelButton.onclick = function () {
        contents[editorId].setValue(null);
        if (onCancelCommand) {
            eval(onCancelCommand);
        }
    };
    cancelButton.value = "Cancel";

    var saveAndCancelContainer = document.createElement("div");
    saveAndCancelContainer.className = "buttons_box";
    saveAndCancelContainer.appendChild(saveButton);
    saveAndCancelContainer.appendChild(cancelButton);
    saveAndCancelContainer.saveButton = saveButton;
    saveAndCancelContainer.cancelButton = cancelButton;
    return saveAndCancelContainer;
}