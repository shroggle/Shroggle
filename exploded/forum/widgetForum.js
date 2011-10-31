//Processes link to "Create new subforum" form
function addNewSubForumLink(forumId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);
        new ServiceCall().executeViaDwr("CreateSubForumService", "showCreateSubForumForm", forumId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    } else {
        showWindow("&dispatchForum=SHOW_CREATE_SUBFORUM", 600, 255, widgetId);
    }
}

var createSubForumDoubleClickProtection = false;
var wasError = false;
//Create's new subforum
function createSubForum(element, forumId, widgetId, isShowOnUserPages) {
    if (createSubForumDoubleClickProtection) return false;
    createSubForumDoubleClickProtection = true;

    var createSubForumRequest = new Object();
    createSubForumRequest.subForumName = document.getElementById("subForumName").value;
    createSubForumRequest.subForumDescription = document.getElementById("subForumDescription").value;

    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var createSubForumError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    createSubForumError.clear();

    if (createSubForumRequest.subForumName.length == 0) {
        createSubForumError.set("SubForumWithNullName", "Please enter subforum name", [document.getElementById("subForumName")]);
    }

    if (createSubForumRequest.subForumDescription.length == 0) {
        createSubForumError.set("SubForumWithNullDescription", "Please enter subforum description");
    }

    if (createSubForumError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        createSubForumDoubleClickProtection = false;

        $('#forumCreateSubforumSpin' + widgetId).makeInvisible();
        return false;
    }

    new ServiceCall().executeViaDwr("CreateSubForumService", "execute", createSubForumRequest, forumId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
        createSubForumDoubleClickProtection = false;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

//Shows thread list in subforum with id = subForumId
function showSubForum(subForumId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    new ServiceCall().executeViaDwr("ShowSubForumService", "execute", subForumId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
    });
}

function processKey(e) {
    var keyId = (window.event) ? event.keyCode : e.keyCode;
    switch (keyId) {
        //Delete
        case 13:{
            addAnswer();
            break;
        }
    }
}

function processAnyKey(e, elementId) {
    var keyId = (window.event) ? event.keyCode : e.keyCode;
    switch (keyId) {
        //Delete
        case 13:{
            document.getElementById(elementId).onclick();
            break;
        }
    }
}

var createThreadError;
//Processes link to "Create new thread" form
function addNewThreadLink(subForumId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);
        new ServiceCall().executeViaDwr("CreateThreadService", "showCreateThreadForm", subForumId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    } else {
        showWindow("&dispatchForum=SHOW_CREATE_THREAD" + "&subForumId=" + subForumId, 800, 685, widgetId);
    }
}

function showThreadTextEditor() {
    var editorPlace = document.getElementById("forumTextEditor");

    if (!editorPlace) {
        ajaxDispatcher.onAfterDispatch = showThreadTextEditor;
        return;
    }

    createEditor({
        width: "100%",
        height: 280,
        showLastSavedData: false,
        place: editorPlace,
        editorId: "forumPostTextEditorThread",
        value: ""});
}

//Processes link to "Create new poll" form
function addNewPollLink(subForumId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);
        new ServiceCall().executeViaDwr("CreateThreadService", "showCreatePollForm", subForumId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;

            var errorClassName = isShowOnUserPages ? "error" : "forumError";

            createThreadError = new Errors({
                highlighting: false,
                scrolling:true,
                errorClassName: errorClassName,
                emptyErrorClassName: "emptyForumError"
            });

            var editorPlace = document.getElementById("forumTextEditor");
            createEditor({
                width: "100%",
                height: 400,
                showLastSavedData: false,
                place: editorPlace,
                editorId: "forumPostTextEditorThread",
                value: ""});
        });
    } else {
        showWindow("&dispatchForum=SHOW_CREATE_POLL" + "&subForumId=" + subForumId, 800, 790, widgetId);
    }
}

//Create's new Forum Thread
function createNewThread(element, subForumId, widgetId, isShowOnUserPages) {
    var createThreadRequest = new Object();
    createThreadRequest.threadName = document.getElementById("threadName").value;
    createThreadRequest.threadDescription = document.getElementById("threadDescription").value;
    createThreadRequest.forumPostText = getEditorContent("forumPostTextEditorThread");
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var createThreadError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    createThreadError.clear();

    if (createThreadRequest.threadName.length == 0) {
        createThreadError.set("ThreadWithNullOrEmptyName", "Please enter thread name", [document.getElementById("threadName")]);
    }

    if (createThreadRequest.forumPostText.length == 0) {
        createThreadError.set("ForumPostWithNullOrEmptyText", "Please enter post text");
    }

    if (createThreadError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }

        $('#forumCreateThreadSpin' + widgetId).makeInvisible();
        return false;
    }

    new ServiceCall().executeViaDwr("CreateThreadService", "execute", createThreadRequest, subForumId, widgetId, isShowOnUserPages, function(data) {
        closeEditor("forumPostTextEditorThread");
        forumBlock.innerHTML = data;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

//Create's new Forum Thread with poll
function createNewPoll(element, subForumId, widgetId, isShowOnUserPages) {
    var createThreadRequest = new Object();
    createThreadRequest.threadName = document.getElementById("threadName").value;
    createThreadRequest.threadDescription = document.getElementById("threadDescription").value;
    createThreadRequest.forumPostText = getEditorContent("forumPostTextEditorThread");
    createThreadRequest.pollQuestion = document.getElementById("pollQuestion").value;

    var answers = new Array();
    var existingAnswers = document.getElementsByTagName("INPUT");
    for (var i = 0; ; i++) {
        var answerObject = existingAnswers[i];
        if (answerObject == undefined) {
            break;
        }
        if (answerObject.value.length != 0 && answerObject.name == "answer") {
            answers.push(answerObject.value);
        }
    }
    createThreadRequest.pollAnswers = answers;
    createThreadRequest.poll = true;
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var createThreadError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    createThreadError.clear();

    if (createThreadRequest.threadName.length == 0) {
        createThreadError.set("ThreadWithNullOrEmptyName", "Please enter thread name");
    }

    if (createThreadRequest.forumPostText.length == 0) {
        createThreadError.set("ForumPostWithNullOrEmptyText", "Please enter post text");
    }

    if (createThreadRequest.pollQuestion.length == 0) {
        createThreadError.set("EmptyQuestionException", "Please enter question text");
    }

    if (createThreadRequest.pollAnswers.length < 2) {
        createThreadError.set("PollWithLessThanTwoAnswersException", "Please enter at least two answers");
    }

    if (createThreadError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }

        $('#forumCreateThreadSpin' + widgetId).makeInvisible();
        return false;
    }

    new ServiceCall().executeViaDwr("CreateThreadService", "execute", createThreadRequest, subForumId, widgetId, isShowOnUserPages, function(data) {
        closeEditor("forumPostTextEditorThread");
        forumBlock.innerHTML = data;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

function forumPollVote(threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var number;
    for (var i = 0; i < document.getElementsByName("vote").length; i++) {
        if (document.getElementById(i).checked) {
            number = i;
        }
    }

    new ServiceCall().executeViaDwr("ForumThreadVotingService", "execute", threadId, number, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
    });
}

var answerCount = 1 + (document.getElementById("answerCount") != undefined ? parseInt(document.getElementById("answerCount").value) : 0);
//Adds new input on page when creating new poll
function addAnswer() {
    var answersBlock = document.getElementById("answers");
    var parent = answersBlock.parentNode;

    var tr = document.createElement("tr");
    tr.id = "answer" + answerCount;
    var faketd = document.createElement("td");
    faketd.innerHTML = "&nbsp;";
    var td = document.createElement("td");
    tr.appendChild(faketd);
    tr.appendChild(td);

    var newAnswer = document.createElement("input");
    newAnswer.id = "answerInput" + answerCount;
    newAnswer.type = "text";
    newAnswer.onkeydown = processKey;
    newAnswer.className = "answers";
    newAnswer.name = "answer";

    var newBlank = document.createElement("span");
    newBlank.innerHTML = " ";

    var deleteAnswer = document.createElement("a");
    deleteAnswer.href = "javascript:deleteAnswer(" + answerCount + ")";
    deleteAnswer.id = "deleteAsnwer" + answerCount;
    var deleteAnswerImage = document.createElement("img");
    deleteAnswerImage.border = "0";
    deleteAnswerImage.alt = "Delete";
    deleteAnswerImage.src = "/images/cross-circle.png";
    deleteAnswer.appendChild(deleteAnswerImage);

    td.appendChild(newAnswer);
    td.appendChild(newBlank);
    td.appendChild(deleteAnswer);
    parent.insertBefore(tr, answersBlock);

    answerCount = answerCount + 1;

    if (document.getElementById("isShowOnUserPages").value == "true") {
        window.parent.getActiveWindow().resize();
    }

    newAnswer.focus();
}

function deleteAnswer(number) {
    var answersBlock = document.getElementById("answers");
    var parent = answersBlock.parentNode;
    var answerTd = document.getElementById("answer" + number);
    var answerInput = document.getElementById("answerInput" + number);

    if (answerInput.name == "answer") {
        if (answerInput.value.length == 0 || confirm("Are you sure want to delete this answer?")) {
            parent.removeChild(answerTd);

            if (document.getElementById("isShowOnUserPages").value == "true") {
                window.parent.getActiveWindow().resize();
            }
        }
    } else {
        alert("You cannot delete this answer because somebody already voted for it!");
    }
}

//Shows posts in thread with id = threadId
function showThread(threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    new ServiceCall().executeViaDwr("ShowThreadService", "execute", threadId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
    });
}

//Processes link that return's on forum widget start page
function returnToForum(forumId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    new ServiceCall().executeViaDwr("ShowForumService", "execute", forumId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
        if (window.parent.getCountOfOpenedWindows() >= 2) {
            window.parent.closeConfigureWidgetDiv();
        }
    });
}

//Pocesses link that return's on subforum with id = subForumId
function returnToSubForum(subForumId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    new ServiceCall().executeViaDwr("ShowSubForumService", "execute", subForumId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;

        if (window.parent.getCountOfOpenedWindows() >= 2) {
            window.parent.closeConfigureWidgetDiv();
        }
    });
}

function returnToThread(threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    new ServiceCall().executeViaDwr("ShowThreadService", "execute", threadId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;

        if (window.parent.getCountOfOpenedWindows() >= 2) {
            window.parent.closeConfigureWidgetDiv();
        }
    });
}

//Processes link to "Create new post" form
function addNewPostLink(threadId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var postBlock = document.getElementById("addPost");
        var addPostLink = document.getElementById("addPostLink");

        var postId = 0;

        new ServiceCall().executeViaDwr("CreatePostService", "showCreatePostForm", false, false, threadId, postId, false, widgetId, isShowOnUserPages, function(data) {
            closeEditPostInput();

            addPostLink.style.display = "none";

            postBlock.innerHTML = data;

            var editorPlace = document.getElementById("forumTextEditor");
            createEditor({
                width: "100%",
                height: 300,
                showLastSavedData: false,
                place: editorPlace,
                editorId: "forumPostTextEditor",
                value: "",
                root: "../"});
        });
    } else {
        showWindow("&dispatchForum=SHOW_CREATE_POST" + "&threadId=" + threadId, 750, 590, widgetId);
    }
}

function closeEditPostInput() {
    var postForm = document.getElementById("postForm");

    if (postForm == undefined) {
        return;
    }

    var parentDiv = postForm.parentNode;

    if (parentDiv.id == "addPost") {
        parentDiv.innerHTML = "";
        var addPostLink = document.getElementById("addPostLink");
        addPostLink.style.display = "block";
    }
    else {
        parentDiv.innerHTML = getEditorContent("forumPostTextEditor");
    }
}

//Adding new post to thread with Id = threadId
var createNewPostDCProtection = false;
function createNewPost(threadId, widgetId, isShowOnUserPages) {
    if (createNewPostDCProtection) {
        return;
    }
    createNewPostDCProtection = true;
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var postBlock = document.getElementById("addPost");
    var postText = getEditorContent("forumPostTextEditor");

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var createPostErrors = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    createPostErrors.clear();

    if (postText.length == 0) {
        createPostErrors.set("PostWithNullOrEmptyText", "Please enter post text");
    }

    if (createPostErrors.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        createNewPostDCProtection = false;
        return;
    }

    new ServiceCall().executeViaDwr("CreatePostService", "execute", postText, threadId, widgetId, isShowOnUserPages, function(data) {
        if (data) {
            //Clearing post block after submitting
            if (!isShowOnUserPages) {
                postBlock.innerHTML = "";
            }
            forumBlock.innerHTML = data;
            createNewPostDCProtection = false;

            if (isShowOnUserPages) {
                window.parent.closeConfigureWidgetDiv();
            }
        }
    });
}

//Saves edited post
function saveEditedPost(postId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var postText = getEditorContent("forumPostTextEditor" + postId);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var editPostErrors = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    editPostErrors.clear();

    if (postText.length == 0) {
        editPostErrors.set("PostWithNullOrEmptyText", "Please enter post text");
    }

    if (editPostErrors.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        return;
    }

    new ServiceCall().executeViaDwr("CreatePostService", "saveEditedPost", postText, postId, widgetId, isShowOnUserPages, function(data) {
        if (data) {
            //Clearing post block after submitting
            forumBlock.innerHTML = data;

            if (isShowOnUserPages) {
                window.parent.closeConfigureWidgetDiv();
            }
        }
    });
}

function submitDraftEditedPost(postId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var postText = getEditorContent("forumPostTextEditor" + postId);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var draftPostErrors = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    draftPostErrors.clear();

    if (postText.length == 0) {
        draftPostErrors.set("PostWithNullOrEmptyText", "Please enter post text");
    }

    if (draftPostErrors.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        return;
    }


    new ServiceCall().executeViaDwr("CreatePostService", "submitDraftEditedPost", postText, postId, widgetId, isShowOnUserPages, function(data) {
        if (data) {
            //Clearing post block after submitting
            forumBlock.innerHTML = data;

            if (isShowOnUserPages) {
                window.parent.closeConfigureWidgetDiv();
            }
        }
    });
}

function previewEditedPost(postId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var postText = getEditorContent("forumPostTextEditor" + postId);

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var previewEditedPostErrors = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    previewEditedPostErrors.clear();

    if (postText.length == 0) {
        previewEditedPostErrors.set("PostWithNullOrEmptyText", "Please enter post text");
    }

    if (previewEditedPostErrors.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        return;
    }


    new ServiceCall().executeViaDwr("CreatePostService", "previewEditedPost", postText, postId, widgetId, isShowOnUserPages, function(data) {
        if (data) {
            //Clearing post block after submitting
            forumBlock.innerHTML = data;

            if (isShowOnUserPages) {
                window.parent.closeConfigureWidgetDiv();
            }
        }
    });
}

function previewNewPost(threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var postBlock = document.getElementById("addPost");
    var postText = getEditorContent("forumPostTextEditor");

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var previewNewPostErrors = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    previewNewPostErrors.clear();

    if (postText.length == 0) {
        previewNewPostErrors.set("PostWithNullOrEmptyText", "Please enter post text");
    }

    if (previewNewPostErrors.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }
        return;
    }


    new ServiceCall().executeViaDwr("CreatePostService", "previewNewPost", postText, threadId, widgetId, isShowOnUserPages, function(data) {
        if (data) {
            //Clearing post block after submitting
            if (!isShowOnUserPages) {
                postBlock.innerHTML = "";
            }
            forumBlock.innerHTML = data;

            if (isShowOnUserPages) {
                window.parent.closeConfigureWidgetDiv();
            }
        }
    });
}

//Adding new post to thread with Id = threadId, with quoted text = quoteText
function quotePost(postIdToEdit, threadId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var postBlock = document.getElementById("addPost");
        var addPostLink = document.getElementById("addPostLink");


        new ServiceCall().executeViaDwr("CreatePostService", "showCreatePostForm", false, true, threadId, postIdToEdit, false, widgetId, isShowOnUserPages, function(data) {
            closeEditPostInput();

            addPostLink.style.display = "none";

            postBlock.innerHTML = data;

            var editorPlace = document.getElementById("forumTextEditor");
            var text = document.getElementById("forumPostTextToEdit").value;
            if (document.getElementById("forumPostTextEditor")) {
                closeEditor("forumPostTextEditor");
            }

            createEditor({
                width: "100%",
                height: 300,
                showLastSavedData: false,
                place: editorPlace,
                editorId: "forumPostTextEditor",
                value: text,
                root: "../"});
        });
    } else {
        showWindow("&dispatchForum=SHOW_QUOTE_POST" + "&threadId=" + threadId + "&postId=" + postIdToEdit, 750, 540, widgetId);
    }
}

//Editing post with text = textToEdit and Id = postIdToEdit
function editPost(threadId, postIdToEdit, isEditDraft, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var postBlock = document.getElementById("editPost" + postIdToEdit);

        new ServiceCall().executeViaDwr("CreatePostService", "showCreatePostForm", !isEditDraft, false, threadId, postIdToEdit, isEditDraft, widgetId, isShowOnUserPages, function(data) {
            closeEditPostInput();

            postBlock.innerHTML = data;

            var editorPlace = $("#forumTextEditor")[0];
            var text = $("#forumPostTextToEdit").html();
            if ($("#forumPostTextEditor")[0]) {
                closeEditor("forumPostTextEditor");
            }

            createEditor({
                width: "100%",
                height: 300,
                showLastSavedData: false,
                place: editorPlace,
                editorId: "forumPostTextEditor" + postIdToEdit,
                value: text,
                root: "../"});
        });
    } else {
        showWindow("&dispatchForum=SHOW_EDIT_POST" + "&threadId=" + threadId + "&postId=" + postIdToEdit + "&draftPostEdit=" + isEditDraft, 750, 540, widgetId);
    }
}

//Editing post with text = textToEdit and Id = postIdToEdit
function submitDraftPost(postId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


    new ServiceCall().executeViaDwr("CreatePostService", "submitDraftPost", postId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
    });
}


function discardDraftPost(postId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


    new ServiceCall().executeViaDwr("CreatePostService", "discardDraftPost", postId, widgetId, isShowOnUserPages, function(data) {
        forumBlock.innerHTML = data;
    });
}

//Removes post with id=postId from database
function deletePost(threadId, postId, widgetId, isShowOnUserPages) {
    if (confirm("Delete this post?")) {
        var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


        new ServiceCall().executeViaDwr("CreatePostService", "deletePost", threadId, postId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    }
}

//Removes thread with id = threadId
function deleteThread(threadId, widgetId, isShowOnUserPages) {
    if (confirm("Delete this thread?")) {
        var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


        new ServiceCall().executeViaDwr("CreateThreadService", "deleteThread", threadId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    }
}

function deleteSubForum(subForumId, widgetId, isShowOnUserPages) {
    if (confirm("Delete this subforum?")) {
        var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


        new ServiceCall().executeViaDwr("CreateSubForumService", "deleteSubForum", subForumId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    }
}

//Closes thread with id = threadId
function closeThread(subForumId, threadId, widgetId, isShowOnUserPages) {
    if (confirm("Are you sure you want to close the thread?")) {
        var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


        new ServiceCall().executeViaDwr("CreateThreadService", "closeThread", subForumId, threadId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    }
}

//Opens closed thread with id = threadId
function openThread(subForumId, threadId, widgetId, isShowOnUserPages) {
    if (confirm("Are you sure you want to open the thread?")) {
        var forumBlock = getForumBlock(widgetId, isShowOnUserPages);


        new ServiceCall().executeViaDwr("CreateThreadService", "openThread", subForumId, threadId, widgetId, isShowOnUserPages, function(data) {
            forumBlock.innerHTML = data;
        });
    }
}

//MOVE&RENAME SCRIPTS

//Show's thread move dialog window
function showMoveThread(threadId, widgetId, isShowOnUserPages) {
    var configureWindow = window.parent.createConfigureWindow({width:400});


    new ServiceCall().executeViaDwr("MoveThreadService", "show", threadId, widgetId, isShowOnUserPages, function (data) {
        configureWindow.setContent(data);
    });
}

//Show's thread rename dialog window
function showRenameThread(threadId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);


        new ServiceCall().executeViaDwr("CreateThreadService", "showRenameThreadForm", threadId, widgetId, isShowOnUserPages, function (data) {
            forumBlock.innerHTML = data;
        });
    } else {
        showWindow("&dispatchForum=SHOW_RENAME_THREAD" + "&threadId=" + threadId, 800, 315, widgetId);
    }
}

function showRenamePoll(threadId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);


        new ServiceCall().executeViaDwr("CreateThreadService", "showRenamePollForm", threadId, widgetId, isShowOnUserPages, function (data) {
            forumBlock.innerHTML = data;
        });
    } else {
        showWindow("&dispatchForum=SHOW_RENAME_POLL" + "&threadId=" + threadId, 800, 425, widgetId);
    }
}


function showRenameSubForum(subForumId, widgetId, isShowOnUserPages) {
    if (!isShowOnUserPages) {
        var forumBlock = document.getElementById("forum" + widgetId);


        new ServiceCall().executeViaDwr("CreateSubForumService", "showRenameSubForumForm", subForumId, widgetId, isShowOnUserPages, function (data) {
            forumBlock.innerHTML = data;
        });
    } else {
        showWindow("&dispatchForum=SHOW_RENAME_SUBFORUM&subForumId=" + subForumId, 600, 275, widgetId);
    }
}

function submitRenameThread(element, threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var renameThreadRequest = new Object();
    renameThreadRequest.threadName = document.getElementById("threadName").value;
    renameThreadRequest.threadDescription = document.getElementById("threadDescription").value;
    renameThreadRequest.threadId = threadId;

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var renameThreadError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    renameThreadError.clear();

    if (renameThreadRequest.threadName.length == 0) {
        renameThreadError.set("ThreadWithNullOrEmptyName", "Please enter thread name", [document.getElementById("threadName")]);
    }

    if (renameThreadError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }

        $('#forumCreateThreadSpin' + widgetId).makeInvisible();
        return false;
    }


    new ServiceCall().executeViaDwr("CreateThreadService", "rename", renameThreadRequest, widgetId, isShowOnUserPages, function (data) {
        forumBlock.innerHTML = data;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

function plusWindowHeigthOnError(isShowOnUserPages) {
    if (isShowOnUserPages) {
        window.parent.getActiveWindow().resize();
    }
}

function submitRenamePoll(element, threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var renameThreadRequest = new Object();
    renameThreadRequest.threadName = document.getElementById("threadName").value;
    renameThreadRequest.threadDescription = document.getElementById("threadDescription").value;
    renameThreadRequest.threadId = threadId;
    renameThreadRequest.pollQuestion = document.getElementById("pollQuestion").value;

    var answers = new Array();
    var existingAnswers = document.getElementsByTagName("INPUT");
    for (var i = 0; ; i++) {
        var answerObject = existingAnswers[i];
        if (answerObject == undefined) {
            break;
        }
        if (answerObject.value.length != 0 && answerObject.name == "answer") {
            answers.push(answerObject.value);
        }
    }

    renameThreadRequest.pollAnswers = answers;
    renameThreadRequest.poll = true;

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var renamePollError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });
    renamePollError.clear();

    if (renameThreadRequest.threadName.length == 0) {
        renamePollError.set("ThreadWithNullOrEmptyName", "Please enter thread name");
    }

    if (renameThreadRequest.pollQuestion.length == 0) {
        renamePollError.set("EmptyQuestionException", "Please enter question text");
    }

    if (renameThreadRequest.pollAnswers.length < 2) {
        renamePollError.set("PollWithLessThanTwoAnswersException", "Please enter at least two answers");
    }

    if (renamePollError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }

        $('#forumCreateThreadSpin' + widgetId).makeInvisible();
        return false;
    }


    new ServiceCall().executeViaDwr("CreateThreadService", "rename", renameThreadRequest, widgetId, isShowOnUserPages, function (data) {
        forumBlock.innerHTML = data;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

function getForumBlock(widgetId, isShowOnUserPages) {
    var forumBlock;
    if (isShowOnUserPages) {
        var iFrame = window.parent.document.getElementById("forum_main_window");
        if (iFrame) {
            var iFrameDocument;
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                iFrameDocument = iFrame.Document;
            } else {
                iFrameDocument = iFrame.contentDocument;
            }

            forumBlock = iFrameDocument.getElementById("forum");
        } else {
            forumBlock = document.getElementById("forum");
        }
    } else {
        forumBlock = document.getElementById("forum" + widgetId);
    }
    return forumBlock;
}

function submitRenameSubForum(element, subForumId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);

    var renameSubForumRequest = new Object();
    renameSubForumRequest.subForumName = document.getElementById("subForumName").value;
    renameSubForumRequest.subForumDescription = document.getElementById("subForumDescription").value;
    renameSubForumRequest.subForumId = subForumId;

    var errorClassName = isShowOnUserPages ? "error" : "forumError";

    var renameSubForumError = new Errors({
        highlighting: false,
        scrolling:true,
        errorClassName: errorClassName,
        emptyErrorClassName: "emptyForumError"
    });

    renameSubForumError.clear();

    if (renameSubForumRequest.subForumName.length == 0) {
        renameSubForumError.set("SubForumWithNullName", "Please enter subforum name", [document.getElementById("subForumName")]);
    }

    if (renameSubForumRequest.subForumDescription.length == 0) {
        renameSubForumError.set("SubForumWithNullDescription", "Please enter subforum description");
    }

    if (renameSubForumError.hasErrors()) {
        if (!wasError) {
            plusWindowHeigthOnError(isShowOnUserPages);
            wasError = true;
        }

        $('#forumCreateSubforumSpin' + widgetId).makeInvisible();
        return false;
    }


    new ServiceCall().executeViaDwr("CreateSubForumService", "rename", renameSubForumRequest, widgetId, isShowOnUserPages, function (data) {
        forumBlock.innerHTML = data;

        if (isShowOnUserPages) {
            window.parent.closeConfigureWidgetDiv();
        } else {
            ajaxDispatcher.execute(element);
        }
    });

    return false;
}

function submitMoveThread(threadId, widgetId, isShowOnUserPages) {
    var forumBlock = getForumBlock(widgetId, isShowOnUserPages);
    var select = window.parent.document.getElementById("subForums");

    for (var i = 0; ; i++) {
        var option = select.options[i];
        if (option == undefined)
            break;
        if (option.selected) {
            subForumId = option.value;
            break;
        }
    }


    new ServiceCall().executeViaDwr("MoveThreadService", "execute", threadId, subForumId, widgetId, isShowOnUserPages, function (data) {
        forumBlock.innerHTML = data;
        window.parent.closeConfigureWidgetDiv();
    });
}

function showForumRegistration(widgetId) {
    var block = $(".blockToReload" + widgetId);

    $("#forumRegisterReloadingMessageDiv" + widgetId).css('visibility', 'visible');

    new ServiceCall().executeViaDwr("ShowRegistrationService", "executeWithReturnToForum", widgetId, function(data) {
        $("#forumRegisterReloadingMessageDiv" + widgetId).css('visibility', 'hidden');
        $(block).hide();
        $(block).parent().append(data);

        bindLoginFormSubmitEvent(widgetId);
    });
}

function showForumLogin(widgetId) {
    var block = $(".blockToReload" + widgetId);

    $("#forumRegisterReloadingMessageDiv" + widgetId).css('visibility', 'visible');

    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForForum", widgetId, function(data) {
        $("#forumRegisterReloadingMessageDiv" + widgetId).css('visibility', 'hidden');
        $(block).hide();
        $(block).parent().append(data);

        bindLoginFormSubmitEvent(widgetId);
    });
}

function showWindow(src, width, height, widgetId) {
    src = "/forum/showForum.action?forumId=" + document.getElementById("forumId" + widgetId).value + src;

    var forum_window = window.parent.createConfigureWindow({
        width: width,
        height: height,
        resizeInnerIFrames:true,
        windowDivId: "forum_window_container",
        windowContentDivId: "forum_window"
    });

    //Creating iframe that will contain blog/forum or smth else content
    var configureWidget = window.parent.document.createElement("iframe");
    configureWidget.style.width = width + "px";
    configureWidget.style.height = height + "px";
    configureWidget.style.overflow = "hidden";
    configureWidget.frameBorder = "0";
    configureWidget.id = "forum_window_content";
    configureWidget.src = src;

    $(configureWidget).bind("load", function () {
        forum_window.resize();
        forum_window.enableContent();
    });

    forum_window.getWindowContentDiv().appendChild(configureWidget);
}
