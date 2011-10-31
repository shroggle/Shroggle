var ajaxDispatcher = {};
ajaxDispatcher.onAfterDispatch = undefined;

$(document).ready(function () {
    $.history.init(ajaxDispatcher.dispatchOnPageLoad);
});

ajaxDispatcher.execute = function (element) {
    var useDefaultDispatchFunction = true;
    if ($(element).hasClass("notUserAjaxDefaultDispatch")) {
        useDefaultDispatchFunction = false;
    }

    var hash = $(element).attr("ajaxHistory");
    // Extracting hash from href.
    hash = hash.replace(/^.*#/, '');
    // Remembering link in history. Calling ajaxDispatcher.dispatchOnPageLoad at once.
    $.history.load(hash, useDefaultDispatchFunction);
    return false;
};

ajaxDispatcher.dispatchOnPageLoad = function (hash) {
    ajaxDispatcher.dispatchForum(hash);
    ajaxDispatcher.dispatchBlog(hash);
    ajaxDispatcher.dispatchGallery(hash);
};

ajaxDispatcher.dispatchGallery = function (hash) {
    var dispatchedWidgetsInCurrentSession = new Array();

    if (hash.indexOf("dispatchGallery") != -1) {
        var galleryId = hash.match(/.*dispatchGallery(\d+)=/);
        var dispatchGallery = hash.match(/.*dispatchGallery\d+=([^\/]*)/);
        var widgetId = hash.match(/widgetId=([^\/]*)/);
        var siteShowOption = hash.match(/siteShowOption=([^\/]*)/);
        var filledFormId = hash.match(/filledFormId=([^\/]*)/);
        var request = {
            galleryId: galleryId[1],
            galleryDispatchType: dispatchGallery[1],
            widgetId: widgetId[1],
            filledFormId: filledFormId != null ? filledFormId[1] : 0,
            siteShowOption: siteShowOption[1]
        };

        dispatchedWidgetsInCurrentSession.push(request.widgetId);

        new ServiceCall().executeViaDwr("GalleryDispatchService", "executeDispatch", request, function (response) {
            showGalleryData(request.galleryId, request.filledFormId, request.widgetId, request.siteShowOption,
                    null, response);
            if ($("#widget" + request.widgetId).length > 0) {
                $("#widget" + request.widgetId)[0].widgetDispatched = true;
            }
            if (ajaxDispatcher.onAfterDispatch) {
                ajaxDispatcher.onAfterDispatch();
                ajaxDispatcher.onAfterDispatch = undefined;
            }
        });
    }

    //Execute gallery default dispatch.
    $("div.widgetGALLERYStyle").each(function () {
        var widgetId = $(this).attr("widgetId");

        if (this.widgetDispatched && !contains(dispatchedWidgetsInCurrentSession, widgetId)) {
            var request = {
                galleryId: $(this).find("#galleryId" + widgetId).val(),
                galleryDispatchType: "SHOW_GALLERY",
                widgetId: widgetId,
                filledFormId: $(this).find("#firstFilledFormId" + widgetId).val(),
                siteShowOption: $(this).find("#siteShowOption" + widgetId).val()
            };

            if (request.filledFormId != "null") {
                new ServiceCall().executeViaDwr("GalleryDispatchService", "executeDispatch", request, function (response) {
                    showGalleryData(request.galleryId, request.filledFormId, request.widgetId, request.siteShowOption,
                            null, response);

                    $("#widget" + widgetId)[0].widgetDispatched = false;
                });
            }
        }
    });
};

ajaxDispatcher.dispatchBlog = function (hash) {
    var dispatchedWidgetsInCurrentSession = new Array();

    if (hash.indexOf("dispatchBlog") != -1) {
        var blogId = hash.match(/.*dispatchBlog(\d+)=/);
        var dispatchBlog = hash.match(/.*dispatchBlog\d+=([^\/]*)/);
        var widgetId = hash.match(/widgetId=([^\/]*)/);
        var siteShowOption = hash.match(/siteShowOption=([^\/]*)/);
        var startIndex = hash.match(/startIndex=([^\/]*)/);
        var exactBlogPostId = hash.match(/exactBlogPostId=([^\/]*)/);
        var request = {
            blogId: blogId[1],
            dispatchBlog: dispatchBlog[1],
            widgetId: widgetId[1],
            startIndex: startIndex != null ? startIndex[1] : 0,
            exactBlogPostId: exactBlogPostId != null ? exactBlogPostId[1] : null,
            siteShowOption: siteShowOption[1]
        };

        dispatchedWidgetsInCurrentSession.push(request.widgetId);

        new ServiceCall().executeViaDwr("BlogDispatchService", "executeDispatch", request, function (response) {
            $("#widget" + request.widgetId).html(response);
            $("#widget" + request.widgetId)[0].widgetDispatched = true;

            if (ajaxDispatcher.onAfterDispatch) {
                ajaxDispatcher.onAfterDispatch();
                ajaxDispatcher.onAfterDispatch = undefined;
            }
        });
    }

    //Execute forum default dispatch - when returning on first forum page.
    $("div.widgetBLOGStyle").each(function () {
        var widgetId = $(this).attr("widgetId");

        if (this.widgetDispatched && !contains(dispatchedWidgetsInCurrentSession, widgetId)) {
            var request = {
                blogId: $(this).find("#blogId" + widgetId).val(),
                dispatchBlog: "SHOW_BLOG",
                widgetId: widgetId,
                siteShowOption: $(this).find("#siteShowOption" + widgetId).val()
            };

            new ServiceCall().executeViaDwr("BlogDispatchService", "executeDispatch", request, function (response) {
                $("#widget" + widgetId).html(response);
                $("#widget" + widgetId)[0].widgetDispatched = false;
            });
        }
    });
};

ajaxDispatcher.dispatchForum = function (hash) {
    var dispatchedWidgetsInCurrentSession = new Array();

    if (hash.indexOf("dispatchForum") != -1) {
        var forumId = hash.match(/.*dispatchForum(\d+)=/);
        var dispatchForum = hash.match(/.*dispatchForum\d+=([^\/]*)/);
        var subForumId = hash.match(/subForumId=([^\/]*)/);
        var threadId = hash.match(/threadId=([^\/]*)/);
        var widgetId = hash.match(/widgetId=([^\/]*)/);
        var showOnUserPages = hash.match(/showOnUserPages=([^\/]*)/);
        var request = {
            dispatchForum: dispatchForum[1],
            widgetId: widgetId[1],
            showOnUserPages: showOnUserPages[1],
            forumId: forumId != null ? forumId[1] : null,
            subForumId: subForumId != null ? subForumId[1] : null,
            threadId: threadId != null ? threadId[1] : null
        };

        dispatchedWidgetsInCurrentSession.push(request.widgetId);

        new ServiceCall().executeViaDwr("ForumDispatchService", "executeDispatch", request, function (response) {
            $("#widget" + request.widgetId).html(response);
            $("#widget" + request.widgetId)[0].widgetDispatched = true;

            if (ajaxDispatcher.onAfterDispatch) {
                ajaxDispatcher.onAfterDispatch();
                ajaxDispatcher.onAfterDispatch = undefined;
            }
        });
    }

    //Execute forum default dispatch - when returning on first forum page.
    $("div.widgetFORUMStyle").each(function () {
        var widgetId = $(this).attr("widgetId");

        if (this.widgetDispatched && !contains(dispatchedWidgetsInCurrentSession, widgetId)) {
            var request = {
                forumId: $(this).find("#forumId" + widgetId).val(),
                dispatchForum: "SHOW_FORUM",
                widgetId: widgetId,
                showOnUserPages: $(this).find("#showOnUserPages" + widgetId).val()
            };

            new ServiceCall().executeViaDwr("ForumDispatchService", "executeDispatch", request, function (response) {
                $("#widget" + widgetId).html(response);
                $("#widget" + widgetId)[0].widgetDispatched = false;
            });
        }
    });
};
