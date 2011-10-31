/*
 * jQuery RealySimpleTree Drag&Drop plugin
 * Update on 24th September 2009
 * Version 1.0
 *
 * Licensed under BSD <http://en.wikipedia.org/wiki/BSD_License>
 * Copyright (c) 2009, Dmitry Solomadin <dmitry.solomadin@gmail.com>
 * All rights reserved.
 */

$.fn.simpleTree = function(opt) {
    return this.each(function() {
        var TREE = this;
        var ROOT = $('.root', this);
        var plusImage = $("<img>")
                .attr({
            id:"tree_plus",
            src:"/images/tree/plus.gif"})
                .css({
            width:"7px",
            display:"none",
            position:"absolute",
            left:"5px",
            top:"5px"}).appendTo("body");
        var plusLineImage = plusImage.clone().appendTo("body");
        var mousePressed = false;
        var mouseMoved = false;
        var dragNode_destination = false;
        var dragNode_source = false;
        var dragDropTimer = false;
        var clickX;
        var clickY;
        var prevX;
        var prevY;

        TREE.option = {
            drag: true,
            animate: false,
            speed: 'fast',
            afterMove: false,
            afterClick: false,
            openOnStartup: true,
            ddTolerance: 5,
            docToFolderConvert: false
        };
        TREE.option = $.extend(TREE.option, opt);
        $.extend(this, {getSelected: function() {
            return $('div.active', this).parent();
        }});

        //Processes click on node elbow. Open's or close's folder.
        TREE.nodeToggle = function(node) {
            var childUl = $(node).find("ul:first");
            if (childUl.is(':visible')) {
                $(node.elbow).removeClass("elbow-minus");
                $(node.elbow).addClass("elbow-plus");
                $(node.icon).removeClass("folder-open");
                $(node.icon).addClass("folder");

                if (TREE.option.animate) {
                    childUl.animate({height:"toggle"}, TREE.option.speed);
                } else {
                    childUl.hide();
                }
            } else {
                $(node.elbow).addClass("elbow-minus");
                $(node.elbow).removeClass("elbow-plus");
                $(node.icon).addClass("folder-open");
                $(node.icon).removeClass("folder");

                if (TREE.option.animate) {
                    childUl.animate({height:"toggle"}, TREE.option.speed);
                } else {
                    childUl.show();
                }
            }
        };

        TREE.setTreeNodes = function(obj, useParent) {
            obj = useParent ? obj.parent() : obj;

            $('li', obj).find("div:first > span").addClass('text');
            $('li', obj).find("div:first").attr("container", "container");

            $('li', obj).find("div:first").bind('selectstart', function() {
                return false;
            }).bind('mouseover', function() {
                $(this).addClass("nodeOver");
                return false;
            }).bind('mouseout', function() {
                $(this).removeClass("nodeOver");
                return false;
            }).click(function(event) {
                /*var menuCheckbox = $(this).find(".menuCheckbox")[0];
                 if (menuCheckbox && event.target.className != "menuCheckbox") {
                 menuCheckbox.checked = !menuCheckbox.checked;
                 }*/
                if (event.target.type != "checkbox") {
                    $('.active', TREE).removeClass('active');
                    $(this).addClass('active');
                    if (typeof TREE.option.afterClick == 'function') {
                        TREE.option.afterClick($(this).parent());
                    }
                }
            }).mousemove(function(event) {
                prevX = event.clientX;
                prevY = event.clientY;
            }).mousedown(function(event) {
                clickX = event.clientX;
                clickY = event.clientY;

                mousePressed = true;
                cloneNode = $(this).parent().clone();
                var LI = $(this).parent();
                if (TREE.option.drag) {
                    $('>ul', cloneNode).hide();
                    $('body').append('<div id="drag_container"><ul></ul></div>');
                    $('#drag_container').hide().css({opacity:'0.8'});
                    $('#drag_container >ul').append(cloneNode);
                    $(document).bind("mousemove", {LI:LI}, TREE.dragStart).bind("mouseup", TREE.dragEnd);
                }
                return false;
            }).mouseup(function() {
                if (mousePressed && mouseMoved && dragNode_source) {
                    TREE.moveNodeToFolder($(this).parent());
                }
                TREE.eventDestroy();
            });
            $('li', obj).each(function() {
                var open = TREE.option.openOnStartup;
                var childNode = $('>ul', this);

                //Adding indent to each node.
                var indents = document.createElement("span");
                this.indents = indents;
                $(this).find("div:first").find("span:first").before(indents);
                TREE.addIndent(this);

                //Creating elbow node.
                var elbow = document.createElement("img");
                this.elbow = elbow;
                elbow.node = this;
                elbow.src = '/images/tree/s.gif';

                //Creating icon node.
                var icon = document.createElement("img");
                this.icon = icon;
                icon.src = '/images/tree/s.gif';

                if (childNode.size() > 0) {
                    if (!open) childNode.hide();

                    //Adding node icon.
                    var iconName = open ? "folder-open " : "folder ";
                    icon.className = "treeImage icon " + iconName + (this.attributes['icon'] ? this.attributes['icon'].value : "");
                    $(this.indents).after(icon);

                    //Adding elbow to node
                    TREE.addElbowEvents(elbow);

                    if (open) {
                        elbow.className = "treeImage elbow elbow-minus";
                    } else {
                        elbow.className = "treeImage elbow elbow-plus";
                    }
                    $(this.indents).after(elbow);
                } else {
                    //Adding node icon.
                    icon.className = "treeImage icon doc " + (this.attributes['icon'] ? this.attributes['icon'].value : "");
                    $(this.indents).after(icon);

                    //Adding elbow to node
                    elbow.className = "treeImage elbow";
                    $(this.indents).after(elbow);
                }
            }).before('<li class="line">&nbsp;</li>')
                    .filter(':last-child').after('<li class="line"></li>');
            TREE.setEventLine($('.line', obj));
        };

        TREE.addElbowEvents = function (elbow) {
            $(elbow).mouseover(function() {
                $(this).parent("div").addClass("overElbow");
            });
            $(elbow).mouseout(function() {
                $(this).parent("div").removeClass("overElbow");
            });
            $(elbow).click(function() {
                TREE.nodeToggle(elbow.node);

                return false;
            });
        };

        TREE.removeElbowEvents = function (elbow) {
            $(elbow).unbind("mouseover").unbind("mouseout").unbind("click");
        };

        TREE.addIndent = function (node) {
            //Clearing old indents.
            $(node.indents).html("");

            //Adding one indent image for every parent.
            $(node).parents('ul.parent').each(function() {
                $(node.indents).append("<img class='treeImage spacer' src='/images/tree/s.gif'/>");
            });
        };

        TREE.addIndentsRecorsively = function (node) {
            if (node.indents == undefined) {
                node.indents = $(node).find("div:first").find("span:first")[0];
            }

            TREE.addIndent(node);

            $(node).find("li[page='page']").each(function () {
                TREE.addIndentsRecorsively(this);
            });
        };

        TREE.dragStart = function(event) {
            var LI = $(event.data.LI);
            if (mousePressed && (prevX && prevY) && (prevY > clickY + TREE.option.ddTolerance ||
                                                     prevY < clickY - TREE.option.ddTolerance ||
                                                     prevX > clickX + TREE.option.ddTolerance ||
                                                     prevX < clickX - TREE.option.ddTolerance)) {
                mouseMoved = true;
                if (dragDropTimer) clearTimeout(dragDropTimer);
                if ($('#drag_container:not(:visible)')) {
                    $('#drag_container').show();
                    LI.prev('.line').hide();
                    dragNode_source = LI;
                }
                $('#drag_container').css({position:'absolute', "left" : (event.pageX + 5), "top": (event.pageY + 15) });
                if ($('#drag_container').css("zIndex") == "auto") {
                    bringToFront($('#drag_container')[0]);
                }
                if (LI.is(':visible'))LI.hide();

                if ($(event.target).hasClass("postedText")
                        || $(event.target).hasClass("pageName")
                        || $(event.target).hasClass("text")
                        || $(event.target).hasClass("treeImage")) {
                    event.target = $(event.target).parents("div:first")[0];
                }

                if (event.target.tagName.toLowerCase() == 'div' && $(event.target).attr("container") == "container") {
                    var nodeDiv = event.target;
                    var screenScroll = findPosAbs($(nodeDiv).parent()[0].icon);

                    if ($($(nodeDiv).parent()[0].icon).hasClass('folder')) {
                        //Closed folder
                        plusImage.css({left: screenScroll.left, top: screenScroll.top}).show();
                        dragDropTimer = setTimeout(function() {
                            TREE.nodeToggle($(nodeDiv).parent()[0]);
                        }, 700);
                    } else if ($($(nodeDiv).parent()[0].icon).hasClass('folder-open')) {
                        plusImage.css({left: screenScroll.left, top: screenScroll.top}).show();
                    } else {
                        if (TREE.option.docToFolderConvert) {
                            plusImage.css({left: screenScroll.left, top: screenScroll.top}).show();
                        } else {
                            plusImage.hide();
                        }
                    }
                } else {
                    plusImage.hide();
                }
                return false;
            }
            return true;
        };

        TREE.dragEnd = function() {
            if (dragDropTimer) clearTimeout(dragDropTimer);
            TREE.eventDestroy();
        };

        TREE.setEventLine = function(obj) {
            obj.mouseover(function() {
                if (this.className.indexOf('over') < 0 && mousePressed && mouseMoved) {
                    this.className = this.className.replace('line', 'line-over');
                    plusImage.show();
                    plusLineImage.css({left: findPosAbs(this).left + 6, top: findPosAbs(this).top - 3}).show();
                }
            }).mouseout(function() {
                if (this.className.indexOf('over') >= 0) {
                    this.className = this.className.replace('-over', '');
                }
                plusLineImage.hide();
            }).mouseup(function() {
                if (mousePressed && dragNode_source && mouseMoved) {
                    dragNode_destination = $(this).parents('li:first');
                    TREE.moveNodeToLine(this, dragNode_destination);
                    TREE.eventDestroy();
                }
            });
        };

        TREE.eventDestroy = function() {
            // added by Erik Dohmen (2BinBusiness.nl), the unbind mousemove TREE.dragStart action
            // like this other mousemove actions binded through other actions ain't removed (use it myself
            // to determine location for context menu)
            $(document).unbind('mousemove', TREE.dragStart).unbind('mouseup').unbind('mousedown');
            $('#drag_container').remove();
            plusImage.hide();
            if (dragNode_source) {
                $(dragNode_source).show().prev('.line').show();
            }
            dragNode_destination = dragNode_source = mousePressed = mouseMoved = false;
        };

        TREE.convertToFolder = function(node) {
            $(node[0].icon).addClass('folder-open');
            $(node[0].icon).removeClass('doc');

            $(node[0].elbow).addClass('elbow-minus');

            TREE.addElbowEvents(node[0].elbow);
            node.append('<ul class="parent"><li class="line"></li></ul>');
            TREE.setEventLine($('.line', node));
        };

        TREE.convertToDoc = function(node) {
            $('>ul', node).remove();
            node[0].icon.className = node[0].icon.className.replace(/folder-(open|close)/gi, 'doc');
            $(node[0].elbow).removeClass('elbow-minus');
            $(node[0].elbow).removeClass('elbow-plus');

            TREE.removeElbowEvents(node[0].elbow);
        };

        TREE.moveNodeToFolder = function(node) {
            if (!TREE.option.docToFolderConvert && node[0].icon.className.indexOf('doc') != -1) {
                return true;
            } else if (TREE.option.docToFolderConvert && node[0].icon.className.indexOf('doc') != -1) {
                TREE.convertToFolder(node);
            }
            var lastLine = $('>ul >.line:last-child', node);
            if (lastLine.size() > 0) {
                TREE.moveNodeToLine(lastLine[0], node);
            }
        };

        TREE.moveNodeToLine = function(node, realParent) {
            var parent = $(dragNode_source).parents('li:first');
            var line = $(dragNode_source).prev('.line');
            $(node).before(dragNode_source);
            $(dragNode_source).before(line);
            node.className = node.className.replace('-over', '');
            var nodeSize = $('>ul >li', parent).not('.line').filter(':visible').size();
            if (TREE.option.docToFolderConvert && nodeSize == 0 && ((realParent && parent[0].id != realParent[0].id) || realParent == undefined)) {
                TREE.convertToDoc(parent);
            }
            /* else if (nodeSize == 0) {
             parent[0].className = parent[0].className.replace('open', 'close');
             $('>ul', parent).hide();
             }*/

            if ($('span:first', dragNode_source).attr('class') == 'text') {
                $('.active', TREE).attr('class', 'text');
                $('span:first', dragNode_source).attr('class', 'active');
            }

            TREE.addIndentsRecorsively(dragNode_source[0]);

            if (typeof(TREE.option.afterMove) == 'function') {
                var pos = $(dragNode_source).prevAll(':not(.line)').size();
                TREE.option.afterMove($(node).parents('li:first'), $(dragNode_source), pos);
            }
        };

        TREE.init = function(obj) {
            disableSelection(obj[0]);

            TREE.setTreeNodes(obj, false);
        };
        TREE.init(ROOT);
    });
};