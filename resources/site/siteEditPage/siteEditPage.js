//Variables for widget selecting.
var selectedMediaBlock;
var selectedWidget;
var hover = true;

var comeFormWidgetSelection = false;
var dragging = false;

var widgetList = new Array();

function showLeftPart(showComponents) {
    $("#left_part").show();
    $("#left_part").animate({width: 300}, 200, function () {

    });

    $("#left_part_links").show();
    $("#site_edit_page_arrow_turned").show();

    if (showComponents) {
        $("#pageComponentsBtn")[0].onclick = hideLeftPart;
        $("#sitePageBtn")[0].onclick = function() {
            showLeftPart(false);
        };
        $("#pageComponentsBtn")[0].className = "pageComponentsBtn_Over";
        $("#sitePageBtn")[0].className = "sitePageBtn";

        $("#pageComponentsBtn")[0].onmouseover = function () {
        };
        $("#pageComponentsBtn")[0].onmouseout = function () {
        };
        $("#sitePageBtn")[0].onmouseover = function () {
            this.className = "sitePageBtn_Over";
        };
        $("#sitePageBtn")[0].onmouseout = function () {
            this.className = "sitePageBtn";
        };

        $("#pageComponentsContent").show();
        $("#tree-div").hide();
    } else {
        $("#sitePageBtn")[0].onclick = hideLeftPart;
        $("#pageComponentsBtn")[0].onclick = function() {
            showLeftPart(true);
        };
        $("#sitePageBtn")[0].className = "sitePageBtn_Over";
        $("#pageComponentsBtn")[0].className = "pageComponentsBtn";

        $("#sitePageBtn")[0].onmouseover = function () {
        };
        $("#sitePageBtn")[0].onmouseout = function () {
        };
        $("#pageComponentsBtn")[0].onmouseover = function () {
            this.className = "pageComponentsBtn_Over";
        };
        $("#pageComponentsBtn")[0].onmouseout = function () {
            this.className = "pageComponentsBtn";
        };

        $("#pageComponentsContent").hide();
        $("#tree-div").show();
    }

    if (selectedWidget) {
        setWidgetSelected(true, true);
    }
}

function hideLeftPart() {
    $("#left_part").animate({width: 0}, 200, function () {

    });
    $("#left_part_links").hide();
    $("#site_edit_page_arrow_turned").hide();
    $("#pageComponentsContent").hide();
    $("#tree-div").hide();
    $("#left_part").hide();

    $("#pageComponentsBtn")[0].className = "pageComponentsBtn";
    $("#sitePageBtn")[0].className = "sitePageBtn";
    $("#pageComponentsBtn")[0].onmouseover = function () {
        this.className = "pageComponentsBtn_Over";
    };
    $("#pageComponentsBtn")[0].onmouseout = function () {
        this.className = "pageComponentsBtn";
    };
    $("#sitePageBtn")[0].onmouseover = function () {
        this.className = "sitePageBtn_Over";
    };
    $("#sitePageBtn")[0].onmouseout = function () {
        this.className = "sitePageBtn";
    };

    $("#pageComponentsBtn")[0].onclick = function() {
        showLeftPart(true);
    };
    $("#sitePageBtn")[0].onclick = function() {
        showLeftPart(false);
    };

    if (selectedWidget) {
        setWidgetSelected(true, true);
    }
}

function showDDPageHelp() {
    var configureDiv = createConfigureWindow({width:600, height:500});
    configureDiv.setContent($("#ddPageHelpContent").html());
}

function showPageVersion(pageId) {
    //Checking if user logined before showing page version in iframe;
    new ServiceCall().executeViaDwr("IsUserLoginedService", "execute", function (result) {
        if (result) {
            document.getElementById("site").src = "/site/editWidgets.action?pageId=" + pageId;
        } else {
            LoginInAccount.EXCEPTION_ACTION("user_not_logined");
            LoginInAccount.CUSTOM_AFTER_LOGIN_ACTION = function () {
                document.getElementById("site").src = "/site/editWidgets.action?pageId=" + pageId;
            };
        }
    });
}

function showNotImplemented() {
    alert(internationalTexts.theOptionNotYetAvailable);
}

//Enables or disables "Add Page" link.
function enableAddPage(enable) {
    var addPage = $("#addPage")[0];
    if (addPage) {
        if (enable) {
            disableControl(addPage, false);
        } else {
            disableControl(addPage);
        }
    }
}

//Enables or disables all page managing links except "Add page link".
function enablePageLinks(visible) {
    if ($("#left_part_links")[0]) {
        if (visible) {
            if (window.parent.pageRequired) {
                disableControls($("#left_part_links").find("a").not("#addPage").not("[deletePage=deletePage]"), false);
                disableControls($("#managePageMenuSecondDiv").find("a").not("#addPage").not("[deletePage=deletePage]"), false);
                $("[deletePage=deletePage]").attr("title", "Selected page is Not Removable");
            } else {
                disableControls($("#left_part_links").find("a").not("#addPage"), false);
                disableControls($("#managePageMenuSecondDiv").find("a").not("#addPage"), false);
            }
        } else {
            disableControls($("#left_part_links").find("a").not("#addPage"));
            disableControls($("#managePageMenuSecondDiv").find("a").not("#addPage"));

            //Disabling page illustration
            $("#pageFunctionalElements")[0].innerHTML = internationalTexts.currentPageNotSelected;
            $("#site")[0].src = "noSelectPage.jsp";
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

var previousSelectedPage;
function selectPageVersion(node, forceReselect) {
    if (previousSelectedPage && previousSelectedPage.pageId == node.pageId && !forceReselect) {
        return;
    }
    previousSelectedPage = node;

    //If we clicked on tree — we need to update select also.
    var selectElement = getSelectElementById("pageDiv" + node.pageId);
    SELECT.selectElement(selectElement);

    //Selecting page in tree.
    $("div.active").removeClass("active");
    $(node).children("div").addClass("active");

    var iFrame = document.getElementById("site");
    iFrame.style.display = "none";
    $("#mainLoadingMessageDiv")[0].style.display = "block";
    window.parent.pageLocked = window.parent.checkWidgetBlueprintRights && node.pageLocked;
    window.parent.pageRequired = window.parent.checkWidgetBlueprintRights && node.pageRequired;
    window.parent.pageNotEditable = window.parent.checkWidgetBlueprintRights && node.pageNotEditable;

    if (window.parent.pageRequired) {
        disableControls($("[deletePage=deletePage]"));
        $("[deletePage=deletePage]").attr("title", "Selected page is Not Removable");
    } else {
        disableControls($("[deletePage=deletePage]"), false);
        $("[deletePage=deletePage]").attr("title", null);
    }

    if (window.parent.pageLocked) {
        disableControls($("[editHtml=editHtml]"));
        $("[editHtml=editHtml]").attr("title", "Selected page content is locked.");

        disableControls($("[setLayoutPage=setLayoutPage]"));
        $("[setLayoutPage=setLayoutPage]").attr("title", "Selected page content is locked.");
    } else {
        disableControls($("[editHtml=editHtml]"), false);
        $("[editHtml=editHtml]").attr("title", null);

        disableControls($("[setLayoutPage=setLayoutPage]"), false);
        $("[setLayoutPage=setLayoutPage]").attr("title", null);
    }

    // Disable 'Revert to live' link if page has no live page version's.
    if (node.newPage) {
        disableControls($("[resetChanges=resetChanges]"), true);
    } else {
        disableControls($("[resetChanges=resetChanges]"), false);
    }

    selectedMediaBlock = null;
    selectedWidget = null;
    comeFormWidgetSelection = false;

    // select what link on preview show
    checkPreviewLinkPageVersion(node);

    showPageVersion(node.pageId);
}

// ---------------------------------------------------------------------------------------------------------------------

function checkPreviewLinkPageVersion(node) {
    $($("[preview=preview]")).each(function() {
        var previewLink = this;
        if (node.newPage) {
            $(previewLink).html(internationalTexts.treePreview);
            previewLink.href = "javascript:preview()";
            previewLink.previousHref = "javascript:preview()";
        } else {
            $(previewLink).html(internationalTexts.treeViewLive);
            previewLink.href = "javascript:viewLive()";
            previewLink.previousHref = "javascript:viewLive()";
        }
    });
}

// ---------------------------------------------------------------------------------------------------------------------

var SELECT;
function loadSelect() {
    SELECT = $("#page_select").customSelect({
        width: "270px",
        strecherId: "select_strecher",
        containerMaxHeight: "500px",
        onSelect: function(option) {
            selectPageVersion(getTreeNodeById($(option).attr('pageId')), false);
        }
    });
}

function getSelectElementById(id) {
    return $(SELECT.containerDiv).find("#" + id)[0];
}

function getPageSelect() {
    return SELECT;
}

// ---------------------------------------------------------------------------------------------------------------------

var TREE;
function loadTree() {
    $("li[page=\"page\"]").each(function () {
        applyNodeAttributes(this);
    });

    TREE = $('.simpleTree').simpleTree({
        autoclose: false,
        afterClick: function(node) {
            selectPageVersion($(node)[0], false);
        },

        afterMove: function(destination, source, pos) {
            var serviceCall = new ServiceCall();
            serviceCall.addExceptionHandler(
                    LoginInAccount.EXCEPTION_CLASS,
                    LoginInAccount.EXCEPTION_ACTION);
            serviceCall.executeViaDwr("MovePageService", "execute", $(source)[0].pageId, pos, $(destination)[0].pageId, function(response) {
                reloadPageSelect(response);
                selectPageVersion($(source)[0], false);
            });
        },
        animate: true,
        docToFolderConvert: true
    });

    // If there is no pages in the site then enable only "Add Page" link, otherwise — enable all links.
    if (getPageCount() == 0) {
        if (!window.parent.addPageRestricted) {
            enableAddPage(true);
        }
    } else {
        enablePageLinks(true);

        if (!window.parent.addPageRestricted) {
            enableAddPage(true);
        }
    }
}

function reloadTree(treeHTML) {
    $("li.root > ul").html(treeHTML);
    loadTree();
}

function reloadPageSelect(pageSelectHTML) {
    $(SELECT.containerDiv).html(pageSelectHTML);
    SELECT.initContainerEvents();
}

function applyNodeAttributes(node) {
    node.pageId = $(node).attr("pageId");
    node.pageName = $(node).attr("pageName");
    node.newPage = $(node).attr("newPage") == "true";
    node.pageLocked = $(node).attr("pageLocked") == "true";
    node.pageRequired = $(node).attr("pageRequired") == "true";
    node.pageNotEditable = $(node).attr("pageNotEditable") == "true";
    node.icon = $(node).attr("icon");
}

function getActivePage() {
    return $("div.active").parent("li")[0];
}

function getTreeNodeById(id) {
    return $("#tree-div").find("li[id=\"node" + id + "\"]")[0];
}

function getPageCount() {
    return $("li[page=\"page\"]").length;
}

// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete selected page and delete child or move it to parent level
 */
function clickDeleteSelectPage() {
    var selectedPage = getActivePage();
    if (!selectedPage) return;

    var confirmMessage = internationalTexts.deleteBeg
            + selectedPage.pageName + internationalTexts.deleteNormalEnd;

    // do you want delete this page?
    if (!selectedPage || !confirm(confirmMessage)) {
        return;
    }
    deleteSelectedPage();
}
// ---------------------------------------------------------------------------------------------------------------------
/**
 * Delete only selected page and move all children to parent.
 */
function deleteSelectedPage() {
    var serviceCall = new ServiceCall();


    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageNotFoundException",
            function (exception) {
                alert($("#pageNotFound").html().replace("{0}", exception.loginedUser.email));
            }
            );

    var selectedPage = getActivePage();
    if (!selectedPage) return;
    serviceCall.executeViaDwr("DeletePageService", "execute", selectedPage.pageId, function (response) {
        //Deleting page from page select
        reloadPageSelect(response.selectHtml);
        reloadTree(response.treeHtml);

        if (getPageCount() == 0) {
            enablePageLinks(false);
        } else {
            selectPageVersion($("#tree-div").find("li[page=\"page\"]")[0], false);
        }
    });
}
// ---------------------------------------------------------------------------------------------------------------------

function preview() {
    var selectedPage = getActivePage();
    if (selectedPage) {
        window.open("/site/showPageVersion.action?pageId=" + selectedPage.pageId + "&siteShowOption=ON_USER_PAGES");
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showStatistics() {
    var selectedPage = getActivePage();
    if (selectedPage) {
        window.open("/site/trafficVisitors.action?pageId=" + selectedPage.pageId);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function viewLive() {
    var selectedPage = getActivePage();
    if (selectedPage) {
        viewLivePage(selectedPage.pageId);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

// Call when user want set this draft page to live
function postToLive(canBePublished, startDate, siteId) {
    if (!siteIsBlueprint && document.getElementById("checkSiteStatus").value == "true") {
        if (!canBePublished) {
            alert(document.getElementById("cantBePublishedUntil").value + " " + startDate);
            return;
        }
        if (document.getElementById("isAdmin").value != "true") {
            if (document.getElementById("siteActive").value != "true") {
                alert(document.getElementById("siteAdminPostLiveSiteWithoutCC").value);
                return;
            }
        } else {
            if (document.getElementById("siteActive").value != "true") {
                if (confirm(document.getElementById("enterPaymentDetails").value)) {
                    window.location = "/account/updatePaymentInfo.action?selectedSiteId=" + document.getElementById("selectedSiteId").value;
                    return;
                } else {
                    return;
                }
            }
        }
    }

    var selectedPage = getActivePage();
    if (!selectedPage) {
        return;
    }

    var selectedPageId = selectedPage.pageId;
    if (selectedPage.newPage || confirm(siteId ? internationalTexts.postAllLive : internationalTexts.postLive)) {
        new ServiceCall().executeViaDwr("CopyPageVersionService", "execute", selectedPageId, "WORK", siteId, function () {
            selectedPage.newPage = false;// This page now has one work version.
            checkPreviewLinkPageVersion(selectedPage);

            // Enable 'revert to live' link. (it was disabled if page had no live versions).
            disableControls($("[resetChanges=resetChanges]"), false);
            if (siteId) {
                makeAllPagesLiveVisual();
            } else {
                makePageLiveVisual(selectedPage);
            }
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function resetChanges() {
    if (!getActivePage()) {
        return;
    }

    var selectedPage = getActivePage();

    var newPage = selectedPage.newPage;
    if (!newPage && confirm(internationalTexts.discardDraft)) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        var selectedPageId = selectedPage.pageId;
        serviceCall.executeViaDwr("CopyPageVersionService", "execute", selectedPageId, "DRAFT", null, function () {
            makePageLiveVisual(window.parent.getActivePage());
            selectedPage.newPage = false;
            selectPageVersion(selectedPage, true);
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function selectTemplate() {
    var selectedPage = getActivePage();
    if (selectedPage) {
        window.location = "setPageTemplate.action?pageId=" + selectedPage.pageId;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showBackgroundsForPage() {
    window.parent.configurePageSettings.show({isEdit:true, tab:window.parent.configurePageSettings.backgroundTab});
}

// ---------------------------------------------------------------------------------------------------------------------

function showAccessibleForPage() {
    window.parent.configurePageSettings.show({isEdit:true, tab:window.parent.configurePageSettings.accessibilityTab});
}

// ---------------------------------------------------------------------------------------------------------------------

function editHtml() {
    window.parent.configurePageSettings.show({isEdit:true, tab:window.parent.configurePageSettings.htmlCssTab});
}

// ---------------------------------------------------------------------------------------------------------------------

function showLayout() {
    window.parent.configurePageSettings.show({isEdit:true, tab:window.parent.configurePageSettings.layoutTab});
}

// This var contains function that should open configure widget window after user has created page and choosed a layout
// (or configured page version blueprint rights, for blueprints).
// Function itself is set after page creation, functionReadyToGoVariable is set after layout or blueprint rights were configured.
// Such shitty way to implement this was chosen because of.. suprise-suprise.. IE :(
var openConfigureWidgetWindowFunction = null;
function setOpenConfigureWidgetFunction(createdWidgetId) {
    if (!createdWidgetId) {
        return;
    }

    openConfigureWidgetWindowFunction = function () {
        selectWidget(getElementByIdForSiteFrame("widgetId" + createdWidgetId), false);
        configureWidget({});
    };
}

function executeOpenConfigureWidgetFunction() {
    if (openConfigureWidgetWindowFunction != null) {
        openConfigureWidgetWindowFunction();
        openConfigureWidgetWindowFunction = null;
    }
}

function setHideArrowTransparency(transparent) {
    if (transparent) {
        $("#site_edit_page_arrow_turned")[0].className = "site_edit_page_arrow_turned_transparent";
    } else {
        $("#site_edit_page_arrow_turned")[0].className = "site_edit_page_arrow_turned";
    }
}

function setShowArrowTransparency(transparent) {
    if (transparent) {
        $("#siteEditPageShowArrow")[0].className = "site_edit_page_arrow_transparent";
    } else {
        $("#siteEditPageShowArrow")[0].className = "site_edit_page_arrow";
    }
}

var siteEditPageMenu = {
    showMenu : function(element) {
        var menuContent = $.find("[menuContent=true]", element);
        if (!menuContent || $(menuContent).is(":visible")) {
            return;
        }
        var position = $(element).position();
        $(menuContent).css("top", position.top + 50);
        $(menuContent).css("left", position.left);
        $(menuContent).show();
        if (!hasVisibleSubMenu()) {
            siteEditPageMenu.hideMenu(element);
        }

        function hasVisibleSubMenu() {
            var hasVisibleElements = false;
            $("div", menuContent).each(function() {
                if ($(this).is(":visible")) {
                    hasVisibleElements = true;
                }
            });
            return hasVisibleElements;
        }
    },

    hideMenu : function(element) {
        var menuContent = $.find("[menuContent=true]", element);
        if (!menuContent || !$(menuContent).is(":visible")) {
            return;
        }
        $(menuContent).hide();
    }
};