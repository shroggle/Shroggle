// This file is not in use but let it live. If someday we will add own d&d system it will be helpful.

var dropWidget = null;
var mediaBlock = null;
var startDrag = false;

function startDropWidget(widget) {
    var iFrame = window.parent.document.getElementById("site");
    var document;
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        document = iFrame.Document;
    } else {
        document = iFrame.contentDocument;
    }
    mediaBlock = widget.parentNode.parentNode;

    dropWidget = widget;
    selectWidgetFunc(document.getElementById("widgetLabelDiv" + widget.widgetId));

    document.onmouseup = stopDragging;

    if (document.body.attachEvent) {
        document.body.attachEvent("onmousemove", moveDropWidget);
    } else {
        document.body.onmousemove = moveDropWidget;
    }

    clickX = undefined;
    clickY = undefined;
    prevX = undefined;
    prevY = undefined;

    //If we left droper after successful d&d then delete it
    if (previousDroperId) {
        var droper = document.getElementById(previousDroperId);
        droper.parentNode.removeChild(droper);
        previousDroperId = null;
    }
}

function stopDragging() {
    var iFrame = window.parent.document.getElementById("site");
    var document;
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        document = iFrame.Document;
    } else {
        document = iFrame.contentDocument;
    }

    document.onmousemove = null;
    document.onmouseup = null;

    dropWidget = null;
}

var widgetListWithoutDropWidget = new Array();
var clickX, clickY, prevX, prevY, emptyMediaBlockToInsertDroper, prevEmptyMediaBlockToInsertDroper;
function moveDropWidget(event) {
    //Getting click coord's
    if (clickX == undefined && clickY == undefined) {
        if (!event) event = window.event;

        clickX = event.clientX;
        clickY = event.clientY;
    }
    if ((prevY && prevX && clickX && clickY) && (prevY > clickY + 10 || prevY < clickY - 10 || prevX > clickX + 10 || prevX < clickX - 10)) {
        if (!startDrag) {
            if (dropWidget == null) {
                return;
            }

            sort(window.parent.widgetList);

            //Changeing drop widget appearence
            dropWidget.style.width = dropWidget.offsetWidth;
            dropWidget.style.height = dropWidget.offsetHeight;
            dropWidget.style.position = "absolute";
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                dropWidget.style.filter = "alpha(opacity=35)";
            } else {
                dropWidget.style.opacity = "0.35";
            }

            dropWidget.style.left = dropWidget.offsetLeft;
            dropWidget.style.top = dropWidget.offsetTop;

            var iFrame = window.parent.document.getElementById("site");
            var document;
            if (iFrame) {
                if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                    document = iFrame.Document;
                } else {
                    document = iFrame.contentDocument;
                }
            }

            if (!event) event = window.event;

            //Applying previous mouse coord's
            prevX = event.clientX + document.body.scrollLeft;
            prevY = event.clientY + document.body.scrollTop;

            //Apllying finish drop function
            document.onmouseup = finishDropWidget;

            for (var j = 0; ; j++) {
                var widget = window.parent.widgetList[j];

                if (widget == undefined) {
                    break;
                }

                if (widget.widgetId != dropWidget.widgetId) {
                    widgetListWithoutDropWidget.push(widget);
                }
            }

            //Reduce child quanity and reposition widget's that are remaning
            mediaBlock.childQuanity = mediaBlock.childQuanity - 1;
            var mediaBlockChilds = mediaBlock.firstChild.childNodes;
            var pos = 0;
            for (var i = 0; ; i++) {
                var mediaBlockChild = mediaBlockChilds[i];
                if (mediaBlockChild == undefined) {
                    break;
                }

                if (mediaBlockChild.widgetId == dropWidget.widgetId) {
                    continue;
                }

                if (mediaBlockChild.className == "widget") {
                    mediaBlockChild.position = pos;
                    pos++;
                }
            }
        }
        startDrag = true;

        iFrame = window.parent.document.getElementById("site");
        if (iFrame) {
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                document = iFrame.Document;
            } else {
                document = iFrame.contentDocument;
            }
        }

        if (!event) event = window.event;

        var afterNumber, insertBefore = false, mediaBlocksWithoutChilds = new Array(), showDroperOnMediaBlock = false;

        //Checking if of of the media block's haven't a child

        for (i = 0; ; i++) {
            var widgetComposit = window.parent.widgetCompositIdList[i];

            if (widgetComposit == undefined) {
                break;
            }


            if (document.getElementById("widgetId" + widgetComposit).childQuanity == 0) {
                mediaBlocksWithoutChilds.push(document.getElementById("widgetId" + widgetComposit));
            }
        }

        //Calculating widget number after(or before) which we must create droper
        for (i = 0; ; i++) {
            widget = widgetListWithoutDropWidget[i];

            if (widget == undefined) {
                break;
            }

            var coordY = findPosAbs(widget).top + widget.offsetHeight, leftSideOfWidget = findPosAbs(widget).left, rightSideOfWidget = leftSideOfWidget + widget.offsetWidth;

            if ((event.clientY + document.body.scrollTop) > coordY && (event.clientX + document.body.scrollLeft) > leftSideOfWidget - 5 && (event.clientX + document.body.scrollLeft) < rightSideOfWidget + 5) {
                afterNumber = i;
                insertBefore = false;
            }
        }

        //Checking if user mouse is after some widget in end of MB but before some other widget in the start of MB.
        //Then adding droper to the start of MB.
        if (afterNumber != undefined) {
            var nextWidgetToAddDroperBefore = 0;
            widget = widgetListWithoutDropWidget[afterNumber];
            coordY = findPosAbs(widget).top + widget.offsetHeight,leftSideOfWidget = findPosAbs(widget).left,rightSideOfWidget = leftSideOfWidget + widget.offsetWidth;
            for (var k = 1; ; k++) {
                var nextWidget = widgetListWithoutDropWidget[afterNumber + k];

                if (nextWidget == undefined) {
                    break;
                }

                if (nextWidget && widget.parentNode.parentNode.childQuanity - 1 == widget.position && nextWidget.position == 0) {
                    var nextWidgetCoordY = findPosAbs(nextWidget).top + nextWidget.offsetHeight, nextWidgetLeft = findPosAbs(nextWidget).left, nextWidgetRight = nextWidgetLeft + nextWidget.offsetWidth;

                    var previousDroper = getPreviousDroper();
                    var previousDroperHeigth = previousDroper > nextWidget.position ? previousDroper.offsetHeight : 0;

                    if ((event.clientY + document.body.scrollTop) > (((nextWidgetCoordY + coordY) / 2) - previousDroperHeigth) && (event.clientX + document.body.scrollLeft) > nextWidgetLeft - 5 && (event.clientX + document.body.scrollLeft) < nextWidgetRight + 5) {
                        nextWidgetToAddDroperBefore = k;
                        insertBefore = true;
                    }
                }
            }
            afterNumber = afterNumber + nextWidgetToAddDroperBefore;
        }

        //Checking if user's mouse is inside media block that haven't childs.
        for (j = 0; ; j++) {
            var mediaBlockWithoutChild = mediaBlocksWithoutChilds[j];
            if (mediaBlockWithoutChild == undefined) {
                break;
            }

            var topOfMediaBlock = findPosAbs(mediaBlockWithoutChild).top, bottomOfMediaBlock = topOfMediaBlock + mediaBlockWithoutChild.offsetHeight;
            var leftSideOfMediaBlock = findPosAbs(mediaBlockWithoutChild).left, rightSideOfMediaBlock = leftSideOfMediaBlock + mediaBlockWithoutChild.offsetWidth;
            var mbPartWidth = parseInt(mediaBlockWithoutChild.offsetWidth / 10), mbPartHeigth = parseInt(mediaBlockWithoutChild.offsetHeight / 5);

            if ((event.clientY + document.body.scrollTop) > topOfMediaBlock - mbPartHeigth && (event.clientY + document.body.scrollTop) < bottomOfMediaBlock + mbPartHeigth
                    && (event.clientX + document.body.scrollLeft) > leftSideOfMediaBlock - mbPartWidth && (event.clientX + document.body.scrollLeft) < rightSideOfMediaBlock + mbPartWidth) {
                showDroperOnMediaBlock = true;
                emptyMediaBlockToInsertDroper = mediaBlockWithoutChild;
            }
        }

        //Adding droper
        if (showDroperOnMediaBlock) {
            addDroperIntoMediaBlock();
        } else {
            if (afterNumber == undefined && widgetListWithoutDropWidget.length >= 1) {
                insertBefore = true;
                addDroper(0, insertBefore);
            } else if (afterNumber != undefined) {
                addDroper(afterNumber, insertBefore);
            }
        }


        //Calculating new coord's
        if (dropWidget) {
            if (prevY && prevX) {
                dropWidget.style.left = parseInt(dropWidget.style.left.substring(0, dropWidget.style.left.length - 2)) + ((event.clientX + document.body.scrollLeft) - prevX) + "px";
                dropWidget.style.top = parseInt(dropWidget.style.top.substring(0, dropWidget.style.top.length - 2)) + ((event.clientY + document.body.scrollTop) - prevY) + "px";
            }
        }
    }

    iFrame = window.parent.document.getElementById("site");
    if (iFrame) {
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            prevX = event.clientX + iFrame.Document.body.scrollLeft;
            prevY = event.clientY + iFrame.Document.body.scrollTop;
        } else {
            prevX = event.clientX + iFrame.contentDocument.body.scrollLeft;
            prevY = event.clientY + iFrame.contentDocument.body.scrollTop;
        }
    }

}

function removeDropers(fromParent) {
    var iFrame = window.parent.document.getElementById("site");
    var document;
    if (iFrame) {
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            document = iFrame.Document;
        } else {
            document = iFrame.contentDocument;
        }
    }

    if (fromParent.parentNode.widgetType == "COMPOSIT" && fromParent.parentNode.childQuanity == 0) {
        iFrame = window.parent.document.getElementById("site");
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            iFrame.Document.getElementById("widgetAddButton" + fromParent.parentNode.id).style.display = "block";
        } else {
            iFrame.contentDocument.getElementById("widgetAddButton" + fromParent.parentNode.id).style.display = "block";
        }
    }

    if (previousDroperId != undefined) {
        fromParent.removeChild(document.getElementById(previousDroperId));
    }
}

function getPreviousDroper() {
    var iFrame = window.parent.document.getElementById("site");
    var document;
    if (iFrame) {
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            document = iFrame.Document;
        } else {
            document = iFrame.contentDocument;
        }
    }

    if (previousDroperId != undefined) {
        return document.getElementById(previousDroperId);
    }

    return undefined;
}

var previousWidgetNumber;
var previousDroperId;
function addDroper(widgetNumber, insertBefore) {
    var thisDroperId = "droper_for_" + widgetNumber + (insertBefore ? "before" : "after");
    if (previousDroperId == undefined || thisDroperId != previousDroperId) {
        var widget = widgetListWithoutDropWidget[widgetNumber];
        if (prevEmptyMediaBlockToInsertDroper) {
            var previousWidget = prevEmptyMediaBlockToInsertDroper.firstChild.firstChild;
            prevEmptyMediaBlockToInsertDroper = null;
        } else {
            previousWidget = widgetListWithoutDropWidget[previousWidgetNumber != undefined ? previousWidgetNumber : widgetNumber];
        }
        var parentMediaBlock = previousWidget.parentNode;
        var insertMediaBlock = widget.parentNode;

        removeDropers(parentMediaBlock);

        var iFrame = window.parent.document.getElementById("site");
        var document;
        if (iFrame) {
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                document = iFrame.Document;
            } else {
                document = iFrame.contentDocument;
            }
        }

        var droper = document.createElement("div");
        droper.id = thisDroperId;
        droper.className = "droper";

        if (insertMediaBlock && insertMediaBlock.mediaBlockFixed) {
            droper.style.height = "10px";
            droper.style.margin = "1px";
        }

        droper.widgetId = insertMediaBlock.widgetId;
        droper.position = insertBefore ? widgetNumber : widgetNumber + 1;

        if (!insertBefore) {
            insertMediaBlock.insertBefore(droper, widget.nextSibling);
        } else {
            insertMediaBlock.insertBefore(droper, widget);
        }

        previousDroperId = thisDroperId;
        previousWidgetNumber = widgetNumber;

    }
}

function addDroperIntoMediaBlock() {
    var thisDroperId = "droper_for_mediaBlock_" + emptyMediaBlockToInsertDroper.widgetId;
    if (previousDroperId == undefined || thisDroperId != previousDroperId) {
        if (prevEmptyMediaBlockToInsertDroper) {
            removeDropers(prevEmptyMediaBlockToInsertDroper.firstChild);
        } else if (previousWidgetNumber != undefined) {
            removeDropers(widgetListWithoutDropWidget[previousWidgetNumber].parentNode);
        }

        var iFrame = window.parent.document.getElementById("site");
        var document;
        if (iFrame) {
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                document = iFrame.Document;
            } else {
                document = iFrame.contentDocument;
            }
        }

        iFrame = window.parent.document.getElementById("site");
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            iFrame.Document.getElementById("widgetAddButton" + emptyMediaBlockToInsertDroper.id).style.display = "none";
        } else {
            iFrame.contentDocument.getElementById("widgetAddButton" + emptyMediaBlockToInsertDroper.id).style.display = "none";
        }

        var droper = document.createElement("div");
        droper.id = thisDroperId;
        droper.className = "droper";

        if (emptyMediaBlockToInsertDroper && emptyMediaBlockToInsertDroper.mediaBlockFixed) {
            droper.style.height = "10px";
            droper.style.margin = "1px";
        }

        droper.widgetId = emptyMediaBlockToInsertDroper.widgetId;
        droper.position = 0;

        emptyMediaBlockToInsertDroper.firstChild.appendChild(droper);

        previousDroperId = thisDroperId;
        prevEmptyMediaBlockToInsertDroper = emptyMediaBlockToInsertDroper;
    }
}

function finishDropWidget() {
    startDrag = false;

    var iFrame = window.parent.document.getElementById("site");
    var document;
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        document = iFrame.Document;
    } else {
        document = iFrame.contentDocument;
    }

    dropWidget.style.position = "";
    dropWidget.style.width = "";
    dropWidget.style.height = "";
    dropWidget.style.left = "";
    dropWidget.style.top = "";
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        dropWidget.style.filter = "alpha(opacity=100)";
    } else {
        dropWidget.style.opacity = "1";
    }

    document.onmouseup = null;
    document.onmousemove = null;

    //START MOVING
    var droper = document.getElementById(previousDroperId);

    if (droper == null) {
        widgetListWithoutDropWidget = new Array();
        dropWidget.parentNode.parentNode.childQuanity = dropWidget.parentNode.parentNode.childQuanity + 1;
        dropWidget = null;

        return;
    }

    var request = new Object();
    request.widgetId = dropWidget.widgetId;
    request.toWidgetPosition = droper.position;
    request.toWidgetId = droper.widgetId;

    var tempDropWidget = dropWidget;
    var tempSelectedDroper = droper;
    var newMediaBlock = droper.parentNode.parentNode;

    //Applaying new postion and media block Id to widget that we are moving
    dropWidget.position = droper.position;
    dropWidget.parentId = droper.widgetId;

    new ServiceCall().executeViaDwr("MoveWidgetService", "execute", request, function (response) {
        if (response) {
            //Setting page color in the tree to "gray"
            makePageDraftVisual(window.parent.getActivePage());

            // Deleting widget from old position
            tempDropWidget.parentNode.removeChild(tempDropWidget);

            //Inserting into new media block
            newMediaBlock.firstChild.insertBefore(tempDropWidget, tempSelectedDroper);
            newMediaBlock.childQuanity = newMediaBlock.childQuanity + 1;

            var iFrame = window.parent.document.getElementById("site");
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                iFrame.Document.getElementById("widgetAddButton" + newMediaBlock.id).style.display = "none";
            } else {
                iFrame.contentDocument.getElementById("widgetAddButton" + newMediaBlock.id).style.display = "none";
            }

            if (!newMediaBlock.mediaBlockFixed) {
                newMediaBlock.firstChild.style.padding = "10px";
                getInnerBorderDiv(tempDropWidget).style.padding = "10px";
                tempDropWidget.style.marginTop = "10px";
            } else {
                getInnerBorderDiv(tempDropWidget).style.padding = "0";
                tempDropWidget.style.margin = "0";
            }

            iFrame = window.parent.document.getElementById("site");
            if (mediaBlock.childQuanity == 0) {
                if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                    iFrame.Document.getElementById("widgetAddButton" + mediaBlock.id).style.display = "block";
                    iFrame.Document.getElementById("content_area_text" + mediaBlock.widgetId).innerHTML = "Empty Content Area";
                    var contentAreaText = iFrame.Document.getElementById("content_area_text" + mediaBlock.widgetId);
                    contentAreaText.innerHTML = "Empty Content Area";
                    mediaBlock.mediaBlockFixed ? contentAreaText.innerHTML = contentAreaText.innerHTML + " - Fixed Size" : "";
                } else {
                    iFrame.contentDocument.getElementById("widgetAddButton" + mediaBlock.id).style.display = "block";
                    contentAreaText = iFrame.contentDocument.getElementById("content_area_text" + mediaBlock.widgetId);
                    contentAreaText.innerHTML = "Empty Content Area";
                    mediaBlock.mediaBlockFixed ? contentAreaText.innerHTML = contentAreaText.innerHTML + " - Fixed Size" : "";

                }
                mediaBlock.firstChild.style.padding = "0";
            }

            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                contentAreaText = iFrame.Document.getElementById("content_area_text" + newMediaBlock.widgetId);
                contentAreaText.innerHTML = "Content Area";
                newMediaBlock.mediaBlockFixed ? contentAreaText.innerHTML = contentAreaText.innerHTML + " - Fixed Size" : "";
            } else {
                contentAreaText = iFrame.contentDocument.getElementById("content_area_text" + newMediaBlock.widgetId);
                contentAreaText.innerHTML = "Content Area";
                newMediaBlock.mediaBlockFixed ? contentAreaText.innerHTML = contentAreaText.innerHTML + " - Fixed Size" : "";
            }

        } else {
            alert("Can't move widget!");
        }
    });

    droper.style.display = "none";
    widgetListWithoutDropWidget = new Array();

    dropWidget = null;
}

function getInnerBorderDiv(widget) {
    widget = widget.firstChild;
    while (widget.className != "widget_inner_div_selected" && widget.className != "widget_inner_div") {
        widget = widget.firstChild;
    }
    return widget;
}