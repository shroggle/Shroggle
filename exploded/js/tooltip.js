//--------------------------------------------------------TOOLTIP-------------------------------------------------------
// contentId -  Id of inserted contet.
// contentElement - element that contains content in innerHTML.
// useValue - forces script to use value from content element instead of innerHTML.
// element - element to show tooltip for.
// delay - showment delay.
// width - tooltip width.
// type - defines tooltip opening style — ONCLICK, ONHOVER.
function Tooltip(settings) {
    //Default settings
    this.settings = {
        fadeIn:100,
        type: "ONHOVER",
        useValue: false,
        width:""
    };

    //Applying default settings
    this.settings = $.extend(this.settings, settings);

    //Binding tooltip to element
    this.settings.element.tooltip = this;

    //Creating contentDiv (tooltip itself)
    this.createContentDiv();

    //Binding events
    this.bindEvents();

    //Updating content and position
    this.updateContent();

    this.show();
}

Tooltip.prototype.updatePosition = function () {
    var scrollHeight = 0;
    var scrollWidth = 0;

    if (isIE()) {
        scrollHeight = window.document.documentElement.scrollTop;
        scrollWidth = window.document.documentElement.scrollLeft;
    }

    $(this.contentDiv).css({top:findPosAbs(this.settings.element).top + this.settings.element.offsetHeight + scrollHeight + "px"
        ,left:findPosAbs(this.settings.element).left + this.settings.element.offsetWidth / 2 - this.contentDiv.offsetWidth / 2 + window.parent.document.body.scrollLeft + scrollWidth + "px"});
};

Tooltip.prototype.bindEvents = function () {
    var self = this;

    if (this.settings.type == "ONHOVER") {
        $(this.settings.element).bind("mouseout", function () {
            self.hide();
            return false;
        });

        $(this.settings.element).bind("mouseover", function () {
            self.show(true);
            return false;
        });

        $(this.contentDiv).mouseout(function () {
            $(this).hide();
            return false;
        });

        $(this.contentDiv).mouseover(function () {
            $(this).show();
            return false;
        });
    } else {
        $(this.settings.element).click(function () {
            self.show();
            return false;
        });
    }
};

Tooltip.prototype.isContentContainerEmpty = function () {
    return (this.settings.contentId && $.trim($("#" + this.settings.contentId).html()).length == 0)
            || (this.settings.contentElement && $.trim(this.settings.useValue ? this.settings.contentElement.val() : this.settings.contentElement.html()).length == 0);
};

Tooltip.prototype.show = function (forInnerEvent) {
    var self = this;
    if (self.isContentContainerEmpty()) {
        return;
    }

    //Creating background to track click outside of hint
    if (this.settings.type == "ONCLICK") {
        var disableBackground = window.parent.document.createElement("div");
        $(disableBackground).css({
            position:"fixed",
            zIndex: "99",
            top:0,
            left:0,
            width:"100%",
            height:"100%"
        });
        this.disableBackground = disableBackground;
        window.parent.document.body.appendChild(disableBackground);
        bringToFront(disableBackground);
        bringToFront(this.contentDiv);

        this.disableBackground.onclick = function () {
            self.hide();
        };
    }

    if (self.settings.delay && !forInnerEvent) {
        self.settings.element.timeout = setTimeout(
                function() {
                    $(self.contentDiv).show();
                    self.updatePosition();
                },
                self.settings.delay);
        self.settings.element.onmouseout = function () {
            clearTimeout(self.settings.element.timeout);
        };
    } else {
        $(self.contentDiv).show();
        self.updatePosition();
    }
};

Tooltip.prototype.hide = function () {
    if (this.disableBackground) {
        window.parent.document.body.removeChild(this.disableBackground);
        this.disableBackground = undefined;
    }

    $(this.contentDiv).hide();
};

Tooltip.prototype.createContentDiv = function() {
    var contentDiv = window.parent.document.createElement("div");
    contentDiv.id = "someUniqueId";
    $(contentDiv).css({
        position:"absolute",
        backgroundColor:"white",
        zIndex:"99999",
        display:"none",
        padding:"10px",
        borderStyle:"solid",
        border:"solid black 1px",
        frameBorder:"0",
        maxWidth: this.settings.width
    });

    window.parent.document.body.appendChild(contentDiv);
    this.contentDiv = contentDiv;
};

Tooltip.prototype.updateContent = function() {
    //Inserting new content
    if (this.settings.contentId) {
        this.contentDiv.innerHTML = limitName($("#" + this.settings.contentId)[0].innerHTML, 1000);
    } else {
        this.contentDiv.innerHTML = limitName(this.settings.useValue ? this.settings.contentElement.val() : this.settings.contentElement.html(), 1000);
    }

    //Removing default margin from <p> tags.
    $(this.contentDiv).find("p").css("margin", "0");

    bringToFront(this.contentDiv);
};

//--------------------------------------------------------TOOLTIP BINDING-----------------------------------------------

function bindTooltip(settings) {
    if (settings.element.tooltip) {
        settings.element.tooltip.updateContent();
    } else {
        new Tooltip(settings);
    }
}