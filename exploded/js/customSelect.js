/*
 * jQuery CustomSelect plugin
 * Update on 1th October 2009
 * Version 0.1
 *
 * Licensed under BSD <http://en.wikipedia.org/wiki/BSD_License>
 * Copyright (c) 2009, Dmitry Solomadin <dmitry.solomadin@gmail.com>
 * All rights reserved.
 */

$.fn.customSelect = function(settings) {
    return this.each(function() {
        var select = new Object();
        var oldSelect = this;
        this.select = select;

        select.settings = {

        };

        select.settings = $.extend(select.settings, settings);

        select.init = function () {
            //Removing strecher if exists
            if (select.settings.strecherId) {
                $("#" + select.settings.strecherId).remove();
            }

            //Adding select container.
            var selectInnerHTML = $(oldSelect).html();
            select.createContainerDiv(selectInnerHTML);

            //Adding trigger.
            var selectTrigger = document.createElement('span');
            select.selectTrigger = selectTrigger;
            $(selectTrigger).addClass('triggerImage');
            $(oldSelect).after(selectTrigger);

            //Adding trigger events.
            $(selectTrigger).mouseover(function() {
                $(this).addClass('triggerImageOver');
                return false;
            });

            $(selectTrigger).mouseout(function() {
                $(this).removeClass('triggerImageOver');
                return false;
            });

            $(selectTrigger).click(function() {
                select.onTriggerClick();
            });

            //Adding new select.
            var newSelect = document.createElement('div');
            select.newSelect = newSelect;
            $(newSelect).addClass('selectText');
            $(newSelect).css({
                width: select.settings.width
            });
            $(selectTrigger).after(newSelect);

            //Adding new select events.
            $(newSelect).mouseover(function() {
                $(selectTrigger).addClass('triggerImageOver');
                return false;
            });

            $(newSelect).mouseout(function() {
                $(selectTrigger).removeClass('triggerImageOver');
                return false;
            });

            $(newSelect).click(function() {
                select.onTriggerClick();
            });

            //Removing old select.
            $(oldSelect).remove();
        };

        select.createContainerDiv = function (selectInnerHTML) {
            var containerDiv = document.createElement('div');
            select.containerDiv = containerDiv;
            $(containerDiv).addClass('selectContainer');
            $(containerDiv).css({
                maxHeight: select.settings.containerMaxHeight,
                overflow: 'auto',
                display: 'none'
            });
            $(containerDiv).html(selectInnerHTML);
            document.body.appendChild(containerDiv);

            select.initContainerEvents();
        };

        select.initContainerEvents = function () {
            $(select.containerDiv).find("div").mouseover(function() {
                $(this).addClass('elementMouseOver');
                return false;
            });

            $(select.containerDiv).find("div").mouseout(function() {
                $(this).removeClass('elementMouseOver');
                return false;
            });

            $(select.containerDiv).find("div").click(function() {
                if (typeof(select.settings.onSelect) == 'function') {
                    select.settings.onSelect(this);
                }

                select.selectElement(this);
            });
        };

        select.selectElement = function (element) {
            if (select.selectedElement) {
                $(select.selectedElement).removeClass("selectedElement");
            }

            select.selectedElement = element;
            $(select.selectedElement).addClass("selectedElement");
            $(select.newSelect).html($(element).html());

            if (select.containerDiv) {
                $(select.containerDiv).hide();
                if (select.containerDiv.backgroundDiv) {
                    $(select.containerDiv.backgroundDiv).remove();
                }
            }

            if (typeof(select.settings.afterSelect) == 'function') {
                select.settings.afterSelect(element);
            }

            return false;
        };

        select.onTriggerClick = function () {
            var backgroundDiv = document.createElement('div');
            select.containerDiv.backgroundDiv = backgroundDiv;
            $(backgroundDiv).css({
                position: "fixed",
                left: "0",
                top: "0",
                width: "100%",
                height: "100%"
            });
            $(backgroundDiv).html("&nbsp;");
            document.body.appendChild(backgroundDiv);
            $(backgroundDiv).click(function () {
                select.containerDiv.backgroundDiv = undefined;
                $(select.containerDiv).hide();
                $(backgroundDiv).remove();
            });
            bringToFront(backgroundDiv);

            $(select.containerDiv).show();
            $(select.containerDiv).css({
                position:'absolute',
                minWidth: (select.newSelect.offsetWidth - 2) + "px",
                left: $(select.newSelect).offset().left,
                top: $(select.newSelect).offset().top + select.newSelect.offsetHeight
            });
            bringToFront(select.containerDiv);

            return false;
        };

        select.init();
    })[0].select;
};