/*
 @author Stasuk Artem
 */

function showAddBlogPost(widgetBlogId, blogId) {
    var addBlogPost = document.getElementById("widget" + widgetBlogId + "addBlogPost" + blogId);
    addBlogPost.backup = addBlogPost.innerHTML;
    addBlogPost.innerHTML = "";

    var addBlogPostTitle = document.createElement("input");
    addBlogPostTitle.type = "text";
    addBlogPostTitle.id = "widget" + widgetBlogId + "addBlogPostTitle";
    addBlogPostTitle.className = "addBlogPostTitle";
    addBlogPostTitle.value = "Blog post title";
    addBlogPost.appendChild(addBlogPostTitle);

    var addBlogPostText = document.createElement("div");
    addBlogPost.appendChild(addBlogPostText);

    createEditor({
        width: "100%",
        height: 400,
        showLastSavedData: true,
        place: addBlogPostText,
        editorId: "widget" + widgetBlogId + "addBlogPostText" + blogId,
        value: "",
        root: "../"
    });
    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft", function () {
        saveAddBlogPost(widgetBlogId, blogId, true);
    });
    var publishButton = createButtonForBlogsTextEditor("Publish", function () {
        saveAddBlogPost(widgetBlogId, blogId, false);
    });
    var cancelButton = createButtonForBlogsTextEditor("Cancel", function () {
        cancelAddBlogPost(widgetBlogId, blogId);
    });
    var upperButtonsHolder = document.createElement("div");
    $(upperButtonsHolder).css("float", "right");
    upperButtonsHolder.appendChild($(saveToDraftButton).clone(true)[0]);
    upperButtonsHolder.appendChild($(publishButton).clone(true)[0]);
    $(addBlogPost).prepend(upperButtonsHolder);

    var buttonsHolder = document.createElement("div");
    buttonsHolder.align = "right";
    buttonsHolder.appendChild(saveToDraftButton);
    buttonsHolder.appendChild(publishButton);
    buttonsHolder.appendChild(cancelButton);
    addBlogPost.appendChild(buttonsHolder);
}

function deleteBlogPost(widgetBlogId, blogPostId, blogId, startIndex) {
    if (confirm("You are about to delete a blog post.\nClick Ok to proceed.")) {
        new ServiceCall().executeViaDwr("DeleteBlogPostService", "execute", blogPostId, function () {
            showBlogPosts(widgetBlogId, blogId, startIndex);
        });
    }
}

function postToWorkBlogPost(widgetBlogId, blogId, blogPostId, startIndex) {
    new ServiceCall().executeViaDwr("PostToWorkBlogPostService", "execute", blogPostId, function () {
        showBlogPosts(widgetBlogId, blogId, startIndex);
    });
}

function postToWorkComment(widgetBlogId, blogPostId, commentId) {
    new ServiceCall().executeViaDwr("PostToWorkCommentService", "execute", commentId, function () {
        showBlogPostComments(widgetBlogId, blogPostId);
    });
}

function resetChangesBlogPost(widgetBlogId, blogPostId, blogId, startIndex) {
    new ServiceCall().executeViaDwr("ResetChangesBlogPostService", "execute", blogPostId, function () {
        showBlogPosts(widgetBlogId, blogId, startIndex);
    });
}

function resetChangesComment(widgetBlogId, blogPostId, commentId) {
    new ServiceCall().executeViaDwr("ResetChangesCommentService", "execute", commentId, function () {
        showBlogPostComments(widgetBlogId, blogPostId);
    });
}

function deleteComment(widgetBlogId, blogPostId, commentId) {
    if (confirm("You are about to delete a comment and all sub comments.\nClick Ok to proceed.")) {
        new ServiceCall().executeViaDwr("DeleteCommentService", "execute", commentId, function () {
            var blogPostCountSpan = $("#blogPostCommentCount" + blogPostId);
            var commentCount = parseInt(blogPostCountSpan.html());

            //Check whether we are deleteing comment with subcomments.
            var commentBlock = $("#widget" + widgetBlogId + "comment" + commentId);
            var subCommentsCount = $(commentBlock).find(".blogPostComment").length;

            commentCount = commentCount - (1 + subCommentsCount);
            blogPostCountSpan.html(commentCount);

            if (commentCount == 0) {
                hideBlogPostComments(widgetBlogId, blogPostId);
            } else {
                showBlogPostComments(widgetBlogId, blogPostId);
            }

            setShowCommentsLinkHref(commentCount, widgetBlogId, blogPostId);
        });
    }
}

function setShowCommentsLinkHref(commentsCount, widgetBlogId, blogPostId) {
    var showBlogPostCommentsLink = $("#widget" + widgetBlogId + "blogPostCommentsShowLink" + blogPostId);
    if (commentsCount == 0) {
        showBlogPostCommentsLink.attr("href", "");
    } else {
        showBlogPostCommentsLink.attr("href", "javascript:showBlogPostComments(" + widgetBlogId + ", " + blogPostId + ")");
    }
}

function showEditBlogPost(widgetBlogId, blogPostId, creationDateString) {
    var editBlogPost = document.getElementById("widget" + widgetBlogId + "blogPostText" + blogPostId);
    editBlogPost.backup = editBlogPost.innerHTML;
    editBlogPost.innerHTML = "";

    var editBlogPostTitle = document.createElement("input");
    editBlogPostTitle.id = "widget" + widgetBlogId + "editBlogPostTitle" + blogPostId;
    editBlogPostTitle.className = "editBlogPostTitle";
    editBlogPostTitle.type = "text";
    editBlogPostTitle.value = document.getElementById("widget" + widgetBlogId + "blogPostTitle" + blogPostId).innerHTML.trim();
    editBlogPost.appendChild(editBlogPostTitle);

    var creationDate = document.createElement("input");
    creationDate.id = "widget" + widgetBlogId + "creationDate" + blogPostId;
    creationDate.className = "editBlogPostCreationDate";
    creationDate.type = "text";
    creationDate.value = document.getElementById("widget" + widgetBlogId + "blogPostCreationDate" + blogPostId).value;
    creationDate.style.width = "80px";
    $(creationDate).attr("maxlength", "10");
    $(creationDate).keypress(function(event) {
        return numbersOrSlashOnly(creationDate, event);
    });

    editBlogPost.appendChild(creationDate);

    var editBlogPostText = document.createElement("div");
    editBlogPost.appendChild(editBlogPostText);

    createEditor({
        width: "100%",
        height: 400,
        showLastSavedData: true,
        place: editBlogPostText,
        editorId: "widget" + widgetBlogId + "editBlogPostText" + blogPostId,
        value: editBlogPost.backup,
        root: "../"
    });
    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft", function () {
        saveEditBlogPost(widgetBlogId, blogPostId, true);
    });
    var publishButton = createButtonForBlogsTextEditor("Publish", function () {
        saveEditBlogPost(widgetBlogId, blogPostId, false);
    });
    var cancelButton = createButtonForBlogsTextEditor("Cancel", function () {
        cancelEditBlogPost(widgetBlogId, blogPostId);
    });
    var upperButtonsHolder = document.createElement("div");
    $(upperButtonsHolder).css("float", "right");
    upperButtonsHolder.appendChild($(saveToDraftButton).clone(true)[0]);
    upperButtonsHolder.appendChild($(publishButton).clone(true)[0]);
    $(editBlogPost).prepend(upperButtonsHolder);

    var buttonsHolder = document.createElement("div");
    buttonsHolder.align = "right";
    buttonsHolder.appendChild(saveToDraftButton);
    buttonsHolder.appendChild(publishButton);
    buttonsHolder.appendChild(cancelButton);
    editBlogPost.appendChild(buttonsHolder);
}

function createButtonsForBlogTextEditor(widgetBlogId, blogPostId, buttonsAlign) {
    var buttonsHolder = getActiveWindow().document.createElement("div");
    buttonsHolder.align = buttonsAlign;

    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft");
    saveToDraftButton.onclick = function () {
        saveEditBlogPost(widgetBlogId, blogPostId, true);
    };
    buttonsHolder.appendChild(saveToDraftButton);

    var publishButton = createButtonForBlogsTextEditor("Publish");
    publishButton.onclick = function () {
        saveEditBlogPost(widgetBlogId, blogPostId, false);
    };
    buttonsHolder.appendChild(publishButton);

    var cancelButton = createButtonForBlogsTextEditor("Cancel");
    cancelButton.onclick = function () {
        cancelEditBlogPost(widgetBlogId, blogPostId);
    };
    buttonsHolder.appendChild(cancelButton);
}

function showEditComment(widgetBlogId, commentId) {
    var commentText = document.getElementById("widget" + widgetBlogId + "commentText" + commentId);
    commentText.backup = commentText.innerHTML;
    var showSavedText = true;
    createEditor({
        width: "100%",
        height: 400,
        showLastSavedData: showSavedText,
        place: commentText,
        editorId: "widget" + widgetBlogId + "editCommentText" + commentId,
        value: commentText.innerHTML,
        root: "../"
    });
    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft", function () {
        editComment(widgetBlogId, commentId, true);
    });
    var publishButton = createButtonForBlogsTextEditor("Publish", function () {
        editComment(widgetBlogId, commentId, false);
    });
    var cancelButton = createButtonForBlogsTextEditor("Cancel", function () {
        cancelEditComment(widgetBlogId, commentId);
    });
    var upperButtonsHolder = document.createElement("div");
    upperButtonsHolder.align = "right";
    upperButtonsHolder.appendChild($(saveToDraftButton).clone(true)[0]);
    upperButtonsHolder.appendChild($(publishButton).clone(true)[0]);
    $(commentText).prepend(upperButtonsHolder);

    var buttonsHolder = document.createElement("div");
    buttonsHolder.align = "right";
    buttonsHolder.appendChild(saveToDraftButton);
    buttonsHolder.appendChild(publishButton);
    buttonsHolder.appendChild(cancelButton);
    commentText.appendChild(buttonsHolder);
}

function editComment(widgetBlogId, commentId, asDraft) {
    var commentText = document.getElementById("widget" + widgetBlogId + "commentText" + commentId);
    var request = new Object();
    document.getElementById("widget" + widgetBlogId + "blogPostCommentPostToWork" + commentId).style.display = "block";
    document.getElementById("widget" + widgetBlogId + "blogPostCommentResetChanges" + commentId).style.display = "block";
    request.commentText = getEditorContent("widget" + widgetBlogId + "editCommentText" + commentId);
    request.asDraft = asDraft;
    request.commentId = commentId;
    new ServiceCall().executeViaDwr("EditCommentService", "execute", request, function () {
        closeEditor("widget" + widgetBlogId + "editCommentText" + commentId);
        commentText.innerHTML = request.commentText;
    });
}

function cancelAddBlogPost(widgetBlogId, blogId) {
    closeEditor("widget" + widgetBlogId + "addBlogPostText" + blogId);
    var addBlogPost = document.getElementById("widget" + widgetBlogId + "addBlogPost" + blogId);
    addBlogPost.innerHTML = addBlogPost.backup;
    addBlogPost.backup = null;
}

function cancelEditBlogPost(widgetBlogId, blogPostId) {
    closeEditor("widget" + widgetBlogId + "editBlogPostText" + blogPostId);
    var editBlogPostText = document.getElementById("widget" + widgetBlogId + "blogPostText" + blogPostId);
    editBlogPostText.innerHTML = editBlogPostText.backup;
}

function cancelEditComment(widgetBlogId, commentId) {
    closeEditor("widget" + widgetBlogId + "editCommentText" + commentId);
    var commentText = document.getElementById("widget" + widgetBlogId + "commentText" + commentId);
    commentText.innerHTML = commentText.backup;
    commentText.backup = null;
}

function saveAddBlogPost(widgetBlogId, blogId, asDraft) {
    var editorId = "widget" + widgetBlogId + "addBlogPostText" + blogId;

    var parameters = {
        blogId : blogId,
        asDraft : asDraft,
        widgetBlogId : widgetBlogId,
        text : getEditorContent(editorId),
        title : document.getElementById("widget" + widgetBlogId + "addBlogPostTitle").value
    };

    jQuery.ajax({
        type: "POST",
        url: "/blog/addBlogPost.action",
        data: {
            siteShowOption: $("#siteShowOption" + widgetBlogId).val(),
            blogId : blogId,
            asDraft : asDraft,
            widgetBlogId : widgetBlogId,
            text : getEditorContent(editorId),
            title : document.getElementById("widget" + widgetBlogId + "addBlogPostTitle").value
        },

        success: function(html) {
            if (html.trim() != "error") {
                closeEditor(editorId);
                document.getElementById("widget" + widgetBlogId).innerHTML = html;
            } else {
                cancelAddBlogPost(widgetBlogId, blogId);
                alert("Text field is empty.\nPlease enter post text before pressing the 'Publish' button.");
            }
        }
    });

}

// ---------------------------------------------------------------------------------------------------------------------

function saveEditBlogPost(widgetBlogId, blogPostId, asDraft) {
    var editBlogPostText = document.getElementById("widget" + widgetBlogId + "blogPostText" + blogPostId);
    var postTitle = document.getElementById("widget" + widgetBlogId + "editBlogPostTitle" + blogPostId).value;
    var request = new Object();
    request.blogPostText = getEditorContent("widget" + widgetBlogId + "editBlogPostText" + blogPostId);
    request.asDraft = asDraft;
    request.postTitle = postTitle;
    request.blogPostId = blogPostId;
    var newCreationDateString = document.getElementById("widget" + widgetBlogId + "creationDate" + blogPostId).value;

    closeEditor("widget" + widgetBlogId + "editBlogPostText" + blogPostId);
    document.getElementById("widget" + widgetBlogId + "blogPostTitle" + blogPostId).innerHTML = request.postTitle;
    editBlogPostText.innerHTML = request.blogPostText;

    document.getElementById("widget" + widgetBlogId + "blogPostResetChanges" + blogPostId).style.display = asDraft ? "inline" : "none";
    document.getElementById("widget" + widgetBlogId + "blogPostPostToWork" + blogPostId).style.display = asDraft ? "inline" : "none";
    document.getElementById("widget" + widgetBlogId + "blogPostEditDraft" + blogPostId).style.display = asDraft ? "inline" : "none";
    document.getElementById("widget" + widgetBlogId + "blogPostEditPost" + blogPostId).style.display = asDraft ? "none" : "inline";

    request.creationDateString = null;
    if (parseDate(newCreationDateString) != null) {
        document.getElementById("widget" + widgetBlogId + "blogPostCreationDate" + blogPostId).value = newCreationDateString;
        request.creationDateString = newCreationDateString;
    }
    new ServiceCall().executeViaDwr("EditBlogPostService", "execute", request, function(newCreationDate) {
        var blogPostDate = document.getElementById("widget" + widgetBlogId + "blogPostDate" + blogPostId);
        if (newCreationDate && blogPostDate) {
            blogPostDate.innerHTML = "at&nbsp;" + newCreationDate;
        }
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showAddBlogPostComment(widgetBlogId, blogPostId, allowed) {
    if (!allowed) {
        if ($("#loginedUserId" + widgetBlogId).val() == "null") {
            if (confirm($("#promptLogin" + widgetBlogId).val())) {
                showBlogLogin(widgetBlogId);
            }
        } else {
            if (confirm($("#promptLoginPrivileges" + widgetBlogId).val())) {
                showBlogLogin(widgetBlogId);
            }
        }
        return;
    }

    var addBlogPostComment = document.getElementById("widget" + widgetBlogId + "addBlogPostComment" + blogPostId);
    addBlogPostComment.backup = addBlogPostComment.innerHTML;
    var showSavedText = true;
    createEditor({
        width: "100%",
        height: 400,
        showLastSavedData: showSavedText,
        place: addBlogPostComment,
        editorId: "widget" + widgetBlogId + "addBlogPostCommentText" + blogPostId,
        value: "",
        root: "../"
    });
    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft", function () {
        saveAddBlogPostComment(widgetBlogId, blogPostId, true);
    });
    var publishButton = createButtonForBlogsTextEditor("Publish", function () {
        saveAddBlogPostComment(widgetBlogId, blogPostId, false);
    });
    var cancelButton = createButtonForBlogsTextEditor("Cancel", function () {
        cancelAddBlogPostComment(widgetBlogId, blogPostId);
    });
    var upperButtonsHolder = document.createElement("div");
    upperButtonsHolder.align = "right";
    upperButtonsHolder.appendChild($(saveToDraftButton).clone(true)[0]);
    upperButtonsHolder.appendChild($(publishButton).clone(true)[0]);
    $(addBlogPostComment).prepend(upperButtonsHolder);

    var buttonsHolder = document.createElement("div");
    buttonsHolder.align = "right";
    buttonsHolder.appendChild(saveToDraftButton);
    buttonsHolder.appendChild(publishButton);
    buttonsHolder.appendChild(cancelButton);
    addBlogPostComment.appendChild(buttonsHolder);
}

// ---------------------------------------------------------------------------------------------------------------------

function showAddComment(widgetBlogId, commentId, blogPostId) {
    var addComment = document.getElementById("widget" + widgetBlogId + "addComment" + commentId);
    addComment.backup = addComment.innerHTML;
    var showSavedText = true;
    createEditor({
        width: "100%",
        height: 400,
        showLastSavedData: showSavedText,
        place: addComment,
        editorId: "widget" + widgetBlogId + "addCommentText" + commentId,
        value: "",
        root: "../"
    });
    var saveToDraftButton = createButtonForBlogsTextEditor("Save to Draft", function () {
        saveAddComment(widgetBlogId, commentId, blogPostId, true);
    });
    var publishButton = createButtonForBlogsTextEditor("Publish", function () {
        saveAddComment(widgetBlogId, commentId, blogPostId, false);
    });
    var cancelButton = createButtonForBlogsTextEditor("Cancel", function () {
        cancelAddComment(widgetBlogId, commentId);
    });
    var upperButtonsHolder = document.createElement("div");
    upperButtonsHolder.align = "right";
    upperButtonsHolder.appendChild($(saveToDraftButton).clone(true)[0]);
    upperButtonsHolder.appendChild($(publishButton).clone(true)[0]);
    $(addComment).prepend(upperButtonsHolder);

    var buttonsHolder = document.createElement("div");
    buttonsHolder.align = "right";
    buttonsHolder.appendChild(saveToDraftButton);
    buttonsHolder.appendChild(publishButton);
    buttonsHolder.appendChild(cancelButton);
    addComment.appendChild(buttonsHolder);
}

// ---------------------------------------------------------------------------------------------------------------------

function cancelAddComment(widgetBlogId, commentId) {
    var addComment = document.getElementById("widget" + widgetBlogId + "addComment" + commentId);
    closeEditor("widget" + widgetBlogId + "addCommentText" + commentId);
    addComment.innerHTML = addComment.backup;
    addComment.backup = null;
}

// ---------------------------------------------------------------------------------------------------------------------

function saveAddBlogPostComment(widgetBlogId, blogPostId, asDraft) {
    var request = new Object();
    request.blogPostId = blogPostId;
    request.asDraft = asDraft;
    request.commentText = getEditorContent("widget" + widgetBlogId + "addBlogPostCommentText" + blogPostId);
    new ServiceCall().executeViaDwr("AddBlogPostCommentService", "execute", request, function () {
        cancelAddBlogPostComment(widgetBlogId, blogPostId);

        var blogPostCountSpan = $("#blogPostCommentCount" + blogPostId);
        var commentCount = parseInt(blogPostCountSpan.html());
        commentCount = commentCount + 1;
        blogPostCountSpan.html(commentCount);

        setShowCommentsLinkHref(commentCount, widgetBlogId, blogPostId);

        showBlogPostComments(widgetBlogId, blogPostId);
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function saveAddComment(widgetBlogId, commentId, blogPostId, asDraft) {
    var request = new Object();
    request.commentId = commentId;
    request.asDraft = asDraft;
    request.commentText = getEditorContent("widget" + widgetBlogId + "addCommentText" + commentId);
    new ServiceCall().executeViaDwr("AddCommentService", "execute", request, function () {
        cancelAddComment(widgetBlogId, commentId);

        var blogPostCountSpan = $("#blogPostCommentCount" + blogPostId);
        var commentCount = parseInt(blogPostCountSpan.html());
        commentCount = commentCount + 1;
        blogPostCountSpan.html(commentCount);

        setShowCommentsLinkHref(commentCount, widgetBlogId, blogPostId);

        showBlogPostComments(widgetBlogId, blogPostId);
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function cancelAddBlogPostComment(widgetBlogId, blogPostId) {
    closeEditor("widget" + widgetBlogId + "addBlogPostCommentText" + blogPostId);
    var addBlogPostComment = document.getElementById("widget" + widgetBlogId + "addBlogPostComment" + blogPostId);
    addBlogPostComment.innerHTML = addBlogPostComment.backup;
    addBlogPostComment.backup = null;
}

// ---------------------------------------------------------------------------------------------------------------------

function showOrHideBlogPostComments(widgetBlogId, blogPostId) {
    var blogPostComments = document.getElementById(
            "widget" + widgetBlogId + "blogPostComments" + blogPostId).childNodes;
    for (var i = 0; i < blogPostComments.length; i++) {
        if (blogPostComments[i].className == "blogPostComment") {
            if (!blogPostComments[i].style.display || blogPostComments[i].style.display == "block") {
                blogPostComments[i].style.display = "none";
            } else {
                blogPostComments[i].style.display = "block";
            }
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function hideBlogPostComments(widgetBlogId, blogPostId) {
    var showBlogPostCommentsDiv = document.getElementById("widget" + widgetBlogId + "blogPostComments" + blogPostId);
    var showBlogPostCommentsShowLink = document.getElementById("widget" + widgetBlogId + "blogPostCommentsShowLink" + blogPostId);
    var showBlogPostCommentsHideLink = document.getElementById("widget" + widgetBlogId + "blogPostCommentsHideLink" + blogPostId);
    $(showBlogPostCommentsShowLink).show();
    $(showBlogPostCommentsHideLink).hide();

    showBlogPostCommentsDiv.innerHTML = "";
}

function showBlogPostComments(widgetBlogId, blogPostId) {
    var showBlogPostCommentsDiv = document.getElementById("widget" + widgetBlogId + "blogPostComments" + blogPostId);
    var showBlogPostCommentsShowLink = document.getElementById("widget" + widgetBlogId + "blogPostCommentsShowLink" + blogPostId);
    var showBlogPostCommentsHideLink = document.getElementById("widget" + widgetBlogId + "blogPostCommentsHideLink" + blogPostId);
    $(showBlogPostCommentsShowLink).hide();
    $(showBlogPostCommentsHideLink).show();

    showBlogPostCommentsDiv.innerHTML = "Loading...";
    jQuery.ajax({
        type: "GET",
        cache: false,
        url: "/blog/showBlogPostComments.action?widgetBlogId=" + widgetBlogId + "&blogPostId=" + blogPostId,
        success: function(html) {
            showBlogPostCommentsDiv.innerHTML = html;
            showBlogPostCommentsDiv.alreadyLoad = true;
        }
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showBlogPosts(widgetBlogId, blogId, startIndex, blogPostId) {
    var request = {
        blogId: blogId,
        widgetBlogId: widgetBlogId,
        startIndex: startIndex,
        siteShowOption: $("#siteShowOption" + widgetBlogId).val()
    };

    if (!request.startIndex) request.startIndex = 0;
    if (blogPostId) request.exactBlogPostId = blogPostId;

    var showBlogPosts = $("#widget" + widgetBlogId);
    showBlogPosts.html("Loading...");
    $.ajax({
        type: "GET",
        cache: false,
        url: "/blog/showBlogPosts.action",
        data: request,
        success: function(html) {
            showBlogPosts.html(html);
            var position = findPosReal($("#widget" + widgetBlogId + "blogPost" + blogPostId)[0]);
            scrollTo(position.left, position.top);
        }
    });
}

function createButtonForBlogsTextEditor(value, onClick) {
    var button = document.createElement("input");
    button.type = "button";
    button.align = "middle";
    button.value = value;
    $(button).click(onClick);
    return button;
}

function showBlogLogin(widgetId) {
    var block = $("#blockToReload" + widgetId);

    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForBlog", widgetId, function(data) {
        $(block).hide();
        $(block).parent().append(data);

        bindLoginFormSubmitEvent(widgetId);
    });
}