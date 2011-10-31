if (isIE()) {
    $(document).keydown(function (event) {
        window.parent.globalKeyProcessor(event);
    });
}

if (isFF()) {
    $(document).keypress(function (event) {
        window.parent.globalKeyProcessor(event);
    });
}

$(document).ready(function () {
    getIFrameDocument((window.parent.document.getElementById("site"))).getElementsByTagName("body")[0].onkeydown = window.parent.globalKeyProcessor;

    window.parent.document.getElementById("pageFunctionalElements").innerHTML = "";

    if (globalPageId) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("GetWidgetByPageWithPositionService", "execute", globalPageId, function (widgetInfos) {
            $("body").click(function () {
                window.parent.hideLeftPart();
            });

            assignButtons();

            var mediaBlocks = $(".widgetPosition");
            for (var i = 0; ; i++) {
                var mediaBlock = mediaBlocks[i];

                if (mediaBlock == undefined) {
                    break;
                }

                var widgetInfo = getWidgetInfoByPosition(widgetInfos, mediaBlock.position);
                if (widgetInfo) {
                    loadWidgetInternal(widgetInfo, mediaBlock);
                }
            }

            var dragFake = createDragFake();

            //Drag helper var's.
            var widgetsToChangeFloating = new Array();
            var blockToRestoreAddButton;

            $(".widgetContainer").sortable({
                placeholder: 'placeHolder',
                connectWith: '.widgetContainer',
                helper: 'original',
                handle: '.widgetHeader',
                opacity: 0.6,
                distance: 10,
                items: "li",
                scroll:false,
                dragFake: dragFake,
                cancel: '.widgetRequired',
                revert: true,
                start: function(event, ui) {
                    //Updating widget list before drag.
                    window.parent.widgetList = $(".widget");

                    //Selecting our widget before drag.
                    selectWidget(ui.item[0], true);
                    if (ui.item[0].mediaBlock.childQuanity == 1) {
                        //Adding some space into media block for user be able drag element back.
                        ui.item[0].mediaBlock.fakeEmpty = true;
                        $(ui.item[0].mediaBlock).addClass("emptyMediaBlock");
                    }

                    //Hiding widget content and showing drag fake.
                    ui.item[0].content.style.display = "none";
                    ui.item[0].savedWidth = ui.item[0].style.width;

                    dragFake.style.display = "block";
                    dragFake.style.width = ui.item.css('width');
                    dragFake.style.top = event.clientY - (dragFake.offsetHeight / 2) + (isIE() || isFF() ? document.documentElement.scrollTop : document.body.scrollTop) + "px";
                    dragFake.style.left = event.clientX - (dragFake.offsetWidth / 2) + (isIE() || isFF() ? document.documentElement.scrollLeft : document.body.scrollLeft) + "px";
                    dragFake.update(ui.item[0].header.headerText.innerHTML);

                    widgetsToChangeFloating = new Array();
                    blockToRestoreAddButton = undefined;
                    $(".widgetContainer").sortable('refreshPositions');

                    window.parent.dragging = true;
                },
                sort: function (event, ui) {
                    var htmlElement = isIE() || isFF() ? document.documentElement : document.body;

                    // Scrolling page if widget is near bottom or top edges of the page.
                    var isNearBottomEdge = isIE() || isFF() ? event.clientY > htmlElement.clientHeight - 50 :
                            event.clientY > $(window).height() - 50;
                    var isNearTopEdge = event.clientY < 50;
                    if (isNearBottomEdge) {
                        if (isIE()) {
                            window.scrollTo(0, htmlElement.scrollTop + 10);
                        } else {
                            $(document).scrollTop($(document).scrollTop() + 10);
                        }
                    } else if (isNearTopEdge) {
                        if (isIE()) {
                            window.scrollTo(0, htmlElement.scrollTop - 10);
                        } else {
                            $(document).scrollTop($(document).scrollTop() - 10);
                        }
                    }

                    dragFake.style.top = event.clientY - (dragFake.offsetHeight / 2) + (htmlElement.scrollTop) + "px";
                    dragFake.style.left = event.clientX - (dragFake.offsetWidth / 2) + (htmlElement.scrollLeft) + "px";

                    var draggedWidget = ui.item[0];

                    //Making header invisible.
                    draggedWidget.header.style.display = "none";

                    //Tracing placeholder position
                    if (ui.placeholder[0]) {
                        var placeholderMediaBlock = ui.placeholder[0].parentNode.parentNode;

                        if ($(ui.placeholder[0]).next().hasClass("clearDiv")) {
                            $(ui.placeholder[0]).addClass("placeHolderAfterClearDiv");
                        } else {
                            $(ui.placeholder[0]).removeClass("placeHolderAfterClearDiv");
                        }

                        if (blockToRestoreAddButton && blockToRestoreAddButton != placeholderMediaBlock) {
                            blockToRestoreAddButton.addWidgetButton.style.display = "block";
                            $(blockToRestoreAddButton).addClass("emptyMediaBlock");
                            blockToRestoreAddButton = undefined;
                        }

                        //If placeholder is inside empty mediablock
                        if (placeholderMediaBlock.childQuanity == 0 ||
                                (placeholderMediaBlock.childQuanity == 1 && placeholderMediaBlock == draggedWidget.mediaBlock)) {
                            placeholderMediaBlock.addWidgetButton.style.display = "none";
                            $(placeholderMediaBlock).removeClass("emptyMediaBlock");
                            blockToRestoreAddButton = placeholderMediaBlock;
                        }
                    }

                    //Checking if user mouse is within left or right bound of some widget. If so then add floating.
                    for (var i = 0; ; i++) {
                        var widget = window.parent.widgetList[i];

                        if (widget == undefined) {
                            break;
                        }

                        if (widget == ui.item[0]) {
                            continue;
                        }

                        if (ui.placeholder[0]) {
                            if (ui.placeholder[0].parentNode.parentNode == widget.mediaBlock &&
                                    (widget.insideLeft(event) || widget.insideRight(event) ||
                                            ($(ui.placeholder[0]).next().hasClass("widgetFloating") && $(ui.placeholder[0]).prev().hasClass("widgetFloating")))) {
                                $(ui.placeholder[0]).addClass("placeHolderFloating");
                                $(widget).addClass("widgetFloating");
                                updateWidgetSizeOnPage({widget:widget, changeFloatable:true});
                                draggedWidget.makeFloatable = true;
                                break;
                            } else {
                                if (draggedWidget.makeFloatable || $(draggedWidget).hasClass("widgetFloating")) {
                                    var floatingRow = getFloatingRowByWidget(draggedWidget, false);
                                    if (floatingRow) {
                                        if (floatingRow.length <= 2) {
                                            //Here we are destroying all floating row.
                                            for (var j = 0; ; j++) {
                                                var floatingWidget = floatingRow[j];
                                                if (floatingWidget == undefined) {
                                                    break;
                                                }

                                                //Destroying clear div
                                                if (floatingWidget.createClearDiv) {
                                                    var clearDiv = floatingWidget.clearDiv;
                                                    var clearDivParent = clearDiv.parentNode;
                                                    clearDivParent.removeChild(clearDiv);
                                                    floatingWidget.clearDiv = undefined;
                                                }

                                                $(floatingWidget).removeClass("widgetFloating");
                                                updateWidgetSizeOnPage({widget:floatingWidget, changeFloatable:false});
                                            }
                                        } else if (draggedWidget.createClearDiv) {
                                            //If we dragged out widget after which we are creating clear div then update floating row
                                            var lastElementInRow = floatingRow[0];

                                            lastElementInRow.createClearDiv = true;
                                            lastElementInRow.clearDiv = draggedWidget.clearDiv;

                                            draggedWidget.createClearDiv = false;
                                            draggedWidget.clearDiv = undefined;
                                        }
                                    }

                                    $(ui.placeholder[0]).removeClass("placeHolderFloating");
                                    draggedWidget.makeFloatable = false;
                                    $(draggedWidget).removeClass("widgetFloating");
                                    updateWidgetSizeOnPage({widget:draggedWidget, changeFloatable:false});
                                }
                            }
                        }
                    }
                },
                stop: function(event, ui) {
                    destroyTempDragArea();
                    window.parent.dragging = false;

                    //Showing widget content and hiding drag fake.
                    ui.item[0].content.style.display = "block";
                    dragFake.style.display = "none";
                    ui.item[0].style.width = ui.item[0].savedWidth;

                    //If we need to make our dragged div floatable.
                    if (ui.item[0].makeFloatable) {
                        //Creating clear div.

                        $(ui.item[0]).addClass("widgetFloating");
                        updateWidgetSizeOnPage({widget:ui.item[0], changeFloatable:true});

                        var floatingRow = getFloatingRowByWidget(ui.item[0], true);
                        var lastElementInRow = floatingRow[floatingRow.length - 1];

                        if (!$(lastElementInRow).next().hasClass("clearDiv")) {
                            var clearDiv = createElement("div");
                            clearDiv.className = "clearDiv";
                            for (var i = 0; ; i++) {
                                var floatingWidget = floatingRow[i];
                                if (floatingWidget == undefined) {
                                    break;
                                }
                                if (floatingWidget.createClearDiv && floatingWidget != lastElementInRow) {
                                    floatingWidget.createClearDiv = false;
                                    floatingWidget.clearDiv = undefined;
                                }
                            }

                            lastElementInRow.createClearDiv = true;
                            lastElementInRow.clearDiv = clearDiv;

                            $(clearDiv).insertAfter(lastElementInRow);
                        }
                    } else {
                        $(ui.item[0]).removeClass("widgetFloating");
                        updateWidgetSizeOnPage({widget:ui.item[0], changeFloatable:false});
                    }

                    if (!ui.item[0].makeFloatable && $(ui.item[0]).next().hasClass("clearDiv")) {
                        //If we are adding new element after floating row then we need to put it's clear div back on place.
                        clearDiv = $(ui.item[0]).next()[0];
                        var clearDivParent = clearDiv.parentNode;
                        clearDivParent.removeChild(clearDiv);
                        clearDivParent.insertBefore(clearDiv, ui.item[0]);
                    }

                    ui.item[0].makeFloatable = false;

                    //Assigning new media block
                    var oldMediaBlock = ui.item[0].mediaBlock;
                    var newMediaBlock = ui.item[0].parentNode.parentNode;
                    ui.item[0].mediaBlock = newMediaBlock;
                    ui.item[0].mediaBlockReal = newMediaBlock.mediaBlockReal;

                    //Composing request
                    var request = new Object();
                    //Dropped widget Id.
                    request.widgetId = ui.item[0].widgetId;
                    //Dropped widget position in new media block.
                    request.toWidgetPosition = calculateWidgetPosition(ui.item[0]);
                    //New media block Id.
                    request.toWidgetId = ui.item[0].mediaBlock.widgetId;
                    //Widget to change floatable state
                    constructRequest(widgetsToChangeFloating);
                    request.widgetsToChangeSize = widgetsToChangeFloating;

                    var serviceCall = new ServiceCall();
                    serviceCall.addExceptionHandler(
                            LoginInAccount.EXCEPTION_CLASS,
                            LoginInAccount.EXCEPTION_ACTION);
                    serviceCall.executeViaDwr("MoveWidgetService", "execute", request, function (response) {
                        if (response) {
                            //Setting page color in the tree to "gray"
                            makePageDraftVisual(window.parent.getActivePage());

                            newMediaBlock.addWidgetButton.style.display = "none";

                            oldMediaBlock.childQuanity--;
                            newMediaBlock.childQuanity++;

                            if (oldMediaBlock.childQuanity == 0) {
                                oldMediaBlock.addWidgetButton.style.display = "block";
                                oldMediaBlock.contentAreaText.innerHTML = "Empty Content Area" + (oldMediaBlock.mediaBlockFixed ? " - Fixed Size" : "");
                                $(oldMediaBlock).addClass("emptyMediaBlock");
                            }

                            $(newMediaBlock).removeClass("emptyMediaBlock");
                            newMediaBlock.contentAreaText.innerHTML = "Content Area" + (newMediaBlock.mediaBlockFixed ? " - Fixed Size" : "");

                            //Re-selecting our dragged widget.
                            setWidgetSelected(true, true);
                        } else {
                            alert("Can't move widget!");
                        }
                    });
                }
            });

            //Disabling all links
            disableControls($("a"));

            //Clearing loading message
            var iFrame = window.parent.document.getElementById("site");
            iFrame.style.display = "block";
            window.parent.document.getElementById("mainLoadingMessageDiv").style.display = "none";

            getParentWindow().executeOpenConfigureWidgetFunction();

            // it may have been disabled on showEditPageWithIncorrectLayout.jsp page.
            enableHTMLlink();

            hideFlash();
        });
    } else {
        var iFrame = window.parent.document.getElementById("site");
        iFrame.style.display = "block";
        window.parent.document.getElementById("mainLoadingMessageDiv").style.display = "none";
    }
});

function enableHTMLlink() {
    window.parent.disableControl($.find("[editHtml=editHtml]", window.parent.document), false);
}

function createDragFake() {
    var dragFake = document.createElement("div");

    dragFake.className = "dragFake";
    $(dragFake).css({
        display:"none",
        backgroundColor:"gray",
        background: "url(/images/drag_gradient.png) repeat-y center center",
        textAlign:"center",
        color:"white",
        padding:"15px 0 15px 0",
        position:"absolute"
    });

    dragFake.update = function (text) {
        $(dragFake).html(text);
    };

    document.body.appendChild(dragFake);
    return dragFake;
}

function hideFlash() {
    window.parent.document.getElementById("site").contentWindow.jQuery("object").each(function () {
        if (isIE()) {
            $(this).attr("wmode", "transparent");
        } else {
            $(this).prepend("<param name='wmode' value='transparent'>");
        }
    });

    window.parent.document.getElementById("site").contentWindow.jQuery("embed").each(function () {
        $(this).attr("wmode", "transparent");
    });

    window.parent.document.getElementById("site").contentWindow.jQuery("object").each(function () {
        $(this).parent().append($(this).clone());
        $(this).remove();
    });
}