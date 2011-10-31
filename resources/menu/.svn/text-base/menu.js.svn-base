(function($) {
    $.mbMenu = {
        actualMenuOpener:false,
        options: {
            skipInitialization:false,
            openOnRight:false,
            containment:"window",
            fadeInTime:100,
            fadeOutTime:200,
            shadow:false,
            shadowColor:"black",
            shadowOpacity:.2,
            closeOnMouseOut:false,
            closeAfter:500
        },
        addMenuEventHandlers : function (options) {
            if (options.skipInitialization) {
                return;
            }
            
            this.each(function () {
                var thisMenu = this;
                /*-------------------------------------------Extending settings---------------------------------------*/
                thisMenu.id = thisMenu.id || ("menu_" + Math.floor(Math.random() * 1000));
                thisMenu.options = {};
                $.extend(thisMenu.options, $.mbMenu.options);
                $.extend(thisMenu.options, options);
                $.extend($.mbMenu.options, thisMenu.options);
                thisMenu.rootMenu = false;
                thisMenu.actualOpenedMenu = false;
                /*-------------------------------------------Extending settings---------------------------------------*/

                /*-------------------------------------Adding styles for tabbed menu----------------------------------*/
                var rootElements = $(thisMenu).find("[menuRootElement=true]");
                if (thisMenu.options.menuStyleType == "TABBED_STYLE") {
                    $(rootElements).corner("top 6px");
                }
                /*-------------------------------------Adding styles for tabbed menu----------------------------------*/
            });

            this.each(function () {
                var thisMenu = this;
                var rootElements = $(thisMenu).find("[menuRootElement=true]");
                /*------------------------------Setting submenu width equals parent width-----------------------------*/
                if (thisMenu.options.menuStyleType == "FULL_HEIGHT_STYLE") {
                    $(rootElements).each(function() {
                        var width = (isIE() ? this.offsetWidth : $(this).css("width"));
                        $("#" + $(this).attr("menu")).find(thisMenu.options.submenuSelector).css({width:width});
                    });
                } else if (thisMenu.options.menuStyleType == "TREE_STYLE") {
                    $(rootElements).each(function() {
                        var submenus = getAllSubmenuElements($("#" + $(this).attr("menu"))[0]);
                        $(submenus).each(function() {
                            var width = 0;
                            $(this).find("span").each(function() {
                                var temp = document.createElement("div");
                                temp.innerHTML = $(this).html();
                                $(document.body).append(temp);
                                $(temp).css("float", "left");
                                var tempWidth = $(temp).outerWidth();
                                if (tempWidth > width) {
                                    width = tempWidth;
                                }
                                $(temp).remove();
                            });
                            if (isIE7() || isIE6()) {
                                width += 50;
                            } else if ($(this).find(".subMenuOpener").length > 0) {
                                width += 30;
                            } else {
                                width += 15;
                            }
                            $(this).css({width:(width)});
                        });
                    });
                }
                /*------------------------------Setting submenu width equals parent width-----------------------------*/
                /*-------------------------------------Mouse Enter (Root Elements)------------------------------------*/
                $(rootElements).bind("mouseenter", function() {
                    if (thisMenu.options.closeOnMouseOut) {
                        thisMenu.timeoutCreated = false;
                        clearTimeout($.mbMenu.deleteOnMouseOut);
                    }
                    var currentlySelectedElement = $(thisMenu).find(".mouseOver");
                    $(currentlySelectedElement).each(function() {
                        $(this).removeClass("mouseOver");
                    });
                    $(this).addClass("mouseOver");
                    if (!$(this).attr("isOpen") && $(this).attr("menu") != "empty") {
                        $(this).buildMbMenu(thisMenu, $(this).attr("menu"), "root");
                    }
                });
                /*-------------------------------------Mouse Enter (Root Elements)------------------------------------*/

                /*-----------------------------------Mouse Leave (Root Elements)--------------------------------------*/
                $(rootElements).bind("mouseleave", function() {
                    if (thisMenu.options.closeOnMouseOut && !thisMenu.timeoutCreated) {
                        thisMenu.timeoutCreated = true;
                        $.mbMenu.deleteOnMouseOut = setTimeout(function() {
                            removeMbMenu(thisMenu);
                        }, thisMenu.options.closeAfter);
                    }
                    $(document).bind("click", function() {
                        removeMbMenu(thisMenu);
                    });
                });
                /*-----------------------------------Mouse Leave (Root Elements)--------------------------------------*/
                var menuElementsWithChilds = $(thisMenu).find("[menu]");
                $(menuElementsWithChilds).each(function() {
                    var menuContainer = $("#" + $(this).attr("menu")).find(thisMenu.options.submenuSelector);
                    var voices = $(menuContainer).find("table");
                    /*--------------------------------Mouse Enter (Submenu Elements)----------------------------------*/
                    $(voices).bind("mouseenter", function() {
                        $(this).addClass("mouseOver");
                    });
                    /*--------------------------------Mouse Enter (Submenu Elements)----------------------------------*/

                    /*--------------------------------Mouse Leave (Submenu Elements)----------------------------------*/
                    $(voices).bind("mouseleave", function() {
                        $(this).removeClass("mouseOver");
                    });
                    /*--------------------------------Mouse Leave (Submenu Elements)----------------------------------*/
                    if (thisMenu.options.closeOnMouseOut) {
                        /*-------------------------------Mouse Enter (Submenu Container)------------------------------*/
                        var container = menuContainer;
                        if (thisMenu.options.menuStyleType == "TABBED_STYLE") {
                            container = $(menuContainer).parents("table")[0];
                        }
                        $(container).bind("mouseenter", function() {
                            var menuToRemove = $.mbMenu.options.actualMenuOpener;
                            menuToRemove.timeoutCreated = false;
                            clearTimeout($.mbMenu.deleteOnMouseOut);
                        });
                        /*-------------------------------Mouse Enter (Submenu Container)------------------------------*/

                        /*-------------------------------Mouse Leave (Submenu Containers)-----------------------------*/
                        $(container).bind("mouseleave", function() {
                            var menuToRemove = $.mbMenu.options.actualMenuOpener;
                            if (!menuToRemove.timeoutCreated) {
                                menuToRemove.timeoutCreated = true;
                                $.mbMenu.deleteOnMouseOut = setTimeout(function() {
                                    removeMbMenu(menuToRemove);
                                }, thisMenu.options.closeAfter);
                            }
                        });
                        /*-------------------------------Mouse Leave (Submenu Containers)-----------------------------*/
                    }
                });
                // });
            });
        }
    };
    $.fn.extend({
        buildMbMenu: function(menuOpener, subMenuId, type) {
            $().bind("click", function() {
                removeMbMenu(menuOpener);
            });
            if ($.mbMenu.options.actualMenuOpener && $.mbMenu.options.actualMenuOpener != menuOpener) {
                removeMbMenu($.mbMenu.options.actualMenuOpener);
            }
            $.mbMenu.options.actualMenuOpener = menuOpener;
            if (type == "root") {
                if (menuOpener.rootMenu) {
                    removeMbMenu(menuOpener);
                    $(menuOpener.actualOpenedMenu).removeAttr("isOpen");
                }
                menuOpener.actualOpenedMenu = this;
                $(menuOpener.actualOpenedMenu).addClass("mouseOver");
            }
            this.menu = $(menuOpener).find("#" + subMenuId);
            if (type == "root") {
                menuOpener.rootMenu = this.menu;
            }
            if (!$(this).attr("menu")) {
                return;
            }
            bringToFront(this.menu[0]);
            $(menuOpener.actualOpenedMenu).attr("isOpen", "true");
            this.menuContainer = $(this.menu).find(menuOpener.options.submenuSelector);
            /*-------------------------------Mouseenter event handler for submenu ites--------------------------------*/
            var opener = this;
            var voices = $(this.menuContainer).find("table");
            if ($(voices).attr("mouseenterCreated") != "true") {
                $(voices).bind("mouseenter", function() {
                    menuOpener.timeoutCreated = false;
                    clearTimeout($.mbMenu.deleteOnMouseOut);
                    /*---------------------------------------Remove old submenus--------------------------------------*/
                    if (opener.menuContainer.previouslySelectedSubmenu && opener.menuContainer.previouslySelectedSubmenu != this) {
                        $(opener.menuContainer.previouslySelectedSubmenu).removeClass("mouseOver");
                        $(opener.menuContainer.previouslySelectedSubmenu).removeAttr("isOpen");
                        var submenus = getAllSubmenuElements($("#" + $(opener.menuContainer.previouslySelectedSubmenu).attr("menu"))[0]);
                        hideSubmenuElements(submenus, $.mbMenu.options.fadeOutTime);
                        opener.menuContainer.previouslySelectedSubmenu = false;
                    }
                    /*---------------------------------------Remove old submenus--------------------------------------*/

                    /*---------------------------------------Create new submenu---------------------------------------*/
                    if ($(this).attr("menu") && !$(this).attr("isOpen")) {
                        opener.menuContainer.previouslySelectedSubmenu = false;
                        $(this).buildMbMenu(menuOpener, $(this).attr("menu"), "submenu");
                        opener.menuContainer.previouslySelectedSubmenu = this;
                        $(this).attr("isOpen", "true");
                        return false;
                    }
                    /*---------------------------------------Create new submenu---------------------------------------*/
                });
            }
            $(voices).attr("mouseenterCreated", "true");
            /*-------------------------------Mouseenter event handler for submenu ites--------------------------------*/


            /*-------------------------------------------Set menu position--------------------------------------------*/
            var submenuTop = 0,submenuLeft = 0;
            switch (type) {
                case "root" : {
                    var element = $(this).css("text-align") == "center" ? $(this).find("a") : this;
                    if (menuOpener.options.openOnRight) {
                        submenuTop = $(element).offset().top - ($.browser.msie ? 2 : 0);
                        submenuLeft = $(element).offset().left + $(this).outerWidth() - ($.browser.msie ? 2 : 0);
                    } else {
                        submenuTop = $(element).offset().top + $(this).outerHeight();
                        submenuLeft = $(element).offset().left;
                        if (menuOpener.options.menuStyleType == "TABBED_STYLE") {
                            submenuLeft--;
                            submenuTop--;
                        }
                    }
                    break;
                }
                case "submenu" : {
                    var position = getSubmenuOffset(this[0]);
                    submenuTop = position.top;
                    submenuLeft = position.left;
                    break;
                }
            }
            $(this.menu).css({
                top:submenuTop,
                left:submenuLeft
            });
            /*-------------------------------------------Set menu position--------------------------------------------*/
            $(this.menu).show();
            /*if (menuOpener.options.fadeInTime > 0) {
             $(this.menu).fadeIn(menuOpener.options.fadeInTime);
             } else {
             $(this.menu).show();
             }*/
            /*---------------------Fit menu position if menu dimension more than window dimension---------------------*/
            var windowWidth = (menuOpener.options.containment == "window") ? $(window).width() :
                    $("#" + menuOpener.options.containment).offset().left +
                            $("#" + menuOpener.options.containment).outerWidth();
            var menuWidth = $(this.menuContainer).outerWidth();
            var actualX = $(this.menu).offset().left - $(window).scrollLeft();
            if ((actualX + menuWidth) >= windowWidth && menuWidth < windowWidth) {
                switch (type) {
                    case "root" : {
                        submenuLeft -= ($(this.menuContainer).offset().left + menuWidth) - windowWidth + 1;
                        break;
                    }
                    case "submenu" : {
                        submenuLeft -= ((menuWidth * 2) - (menuOpener.options.submenuLeft * 2));
                        break;
                    }
                }
                $(this.menu).css({
                    left:submenuLeft
                });
            }
            /*---------------------Fit menu position if menu dimension more than window dimension---------------------*/
            function getSubmenuOffset(element) {
                var parent = $(element).parent();
                var newTop = ($(parent).offset().top + element.offsetTop);
                var newLeft = ($(parent).offset().left + element.offsetLeft + $(element).outerWidth());
                return {top:newTop, left:newLeft};
            }
        }
    });
    $.fn.addMenuEventHandlers = $.mbMenu.addMenuEventHandlers;
})(jQuery);


function removeMbMenu(opener) {
    try {
        opener = opener || $.mbMenu.options.actualMenuOpener;
        if (opener.rootMenu) {
            $(opener.actualOpenedMenu).removeClass("mouseOver");
            if ($(opener.actualOpenedMenu).attr("isOpen")) {
                $(opener.actualOpenedMenu).removeAttr("isOpen");
                var submenus = getAllSubmenuElements((opener.rootMenu[0]));
                hideSubmenuElements(submenus, opener.options.fadeOutTime);
            }
        }
    } catch(ex) {
    }
}

function getAllSubmenuElements(opener) {
    var submenus = getSubmenus($(opener).find("[menu]"));
    submenus.push(opener);
    return submenus;

    function getSubmenus(elementsWithChilds) {
        var submenus = new Array();
        $(elementsWithChilds).each(function() {
            submenus = submenus.concat($.makeArray($("#" + $(this).attr("menu"))));
            var tempelEmentsWithChilds = $(submenus).find("[menu]");
            if (tempelEmentsWithChilds.length > 0) {
                submenus = submenus.concat(getSubmenus(tempelEmentsWithChilds));
            }
        });
        return submenus;
    }
}

function hideSubmenuElements(submenus, fadeOutTime) {
    $(submenus).find("[isOpen]").removeAttr("isOpen");
    $(submenus).each(function() {
        $(this).hide();
        /* if (fadeOutTime) {
         $(this).fadeOut(fadeOutTime);
         } else {
         $(this).hide();
         }*/
    });
}