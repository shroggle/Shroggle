/*
 * Test. Tolik
 */

(function($) {
    var ua = navigator.userAgent;
    var moz = $.browser.mozilla && /gecko/i.test(ua);
    var webkit = $.browser.safari && /Safari\/[5-9]/.test(ua);

    var expr = $.browser.msie && (function() {
        var div = document.createElement('div');
        try {
            div.style.setExpression('width', '0+0');
            div.style.removeExpression('width');
        }
        catch(e) {
            return false;
        }
        return true;
    })();

    function sz(el, p) {
        return parseInt($.css(el, p)) || 0;
    }

    function hex2(s) {
        var s = parseInt(s).toString(16);
        return ( s.length < 2 ) ? '0' + s : s;
    }

    function gpc(node) {
        for (; node && node.nodeName.toLowerCase() != 'html'; node = node.parentNode) {
            var v = $.css(node, 'backgroundColor');
            if (v == 'rgba(0, 0, 0, 0)')
                continue; // webkit
            if (v.indexOf('rgb') >= 0) {
                var rgb = v.match(/\d+/g);
                return '#' + hex2(rgb[0]) + hex2(rgb[1]) + hex2(rgb[2]);
            }
            if (v && v != 'transparent' && v != 'inherit')
                return v;
        }
        return '#ffffff';
    }

    function getWidth(fx, i, width) {
        switch (fx) {
            case 'round':  return Math.round(width * (1 - Math.cos(Math.asin(i / width))));
            case 'cool':   return Math.round(width * (1 + Math.cos(Math.asin(i / width))));
            case 'sharp':  return Math.round(width * (1 - Math.cos(Math.acos(i / width))));
            case 'bite':   return Math.round(width * (Math.cos(Math.asin((width - i - 1) / width))));
            case 'slide':  return Math.round(width * (Math.atan2(i, width / i)));
            case 'jut':    return Math.round(width * (Math.atan2(width, (width - i - 1))));
            case 'curl':   return Math.round(width * (Math.atan(i)));
            case 'tear':   return Math.round(width * (Math.cos(i)));
            case 'wicked': return Math.round(width * (Math.tan(i)));
            case 'long':   return Math.round(width * (Math.sqrt(i)));
            case 'sculpt': return Math.round(width * (Math.log((width - i - 1), width)));
            case 'dog':    return (i & 1) ? (i + 1) : width;
            case 'dog2':   return (i & 2) ? (i + 1) : width;
            case 'dog3':   return (i & 3) ? (i + 1) : width;
            case 'fray':   return (i % 2) * width;
            case 'notch':  return width;
            case 'bevel':  return i + 1;
        }
    }

    function findPos2(obj) {
        var curleft = curtop = 0;

        if (obj.offsetParent) {
            do {
                curleft += obj.offsetLeft;
                curtop += obj.offsetTop;
            } while (obj = obj.offsetParent);
        }

        return({
            'x': curleft,
            'y': curtop
        });
    }

    $.fn.corner = function(options) {
        // in 1.3+ we can fix mistakes with the ready state
        if (this.length == 0) {
            if (!$.isReady && this.selector) {
                var s = this.selector, c = this.context;
                $(function() {
                    $(s, c).corner(options);
                });
            }
            return this;
        }

        return this.each(function(index) {
            var $this = $(this);
            var o = [ options || '', $this.attr($.fn.corner.defaults.metaAttr) || ''].join(' ').toLowerCase();
            //var o = (options || $this.attr($.fn.corner.defaults.metaAttr) || '').toLowerCase();
            var keep = /keep/.test(o);                       // keep borders?
            var cc = ((o.match(/cc:(#[0-9a-f]+)/) || [])[1]);  // corner color
            var sc = ((o.match(/sc:(#[0-9a-f]+)/) || [])[1]);  // strip color
            var width = parseInt((o.match(/(\d+)px/) || [])[1]) || 10; // corner width
            var re = /round|bevel|notch|bite|cool|sharp|slide|jut|curl|tear|fray|wicked|sculpt|long|dog3|dog2|dog/;
            var fx = ((o.match(re) || ['round'])[0]);
            var edges = { T:0, B:1 };
            var opts = {
                TL:  /top|tl|left/.test(o),       TR:  /top|tr|right/.test(o),
                BL:  /bottom|bl|left/.test(o),    BR:  /bottom|br|right/.test(o)
            };
            if (!opts.TL && !opts.TR && !opts.BL && !opts.BR)
                opts = { TL:1, TR:1, BL:1, BR:1 };

            // support native rounding
            if (opts.TL)
                $this.css(moz ? '-moz-border-radius-topleft' : '-webkit-border-top-left-radius', width + 'px');
            if (opts.TR)
                $this.css(moz ? '-moz-border-radius-topright' : '-webkit-border-top-right-radius', width + 'px');
            if (opts.BL)
                $this.css(moz ? '-moz-border-radius-bottomleft' : '-webkit-border-bottom-left-radius', width + 'px');
            if (opts.BR)
                $this.css(moz ? '-moz-border-radius-bottomright' : '-webkit-border-bottom-right-radius', width + 'px');
            if ($.fn.corner.defaults.useNative && fx == 'round' && (moz || webkit) && !cc && !sc) {
                return;
            }

            var classID = "sadfawti34325jrgasfras";
            if (this.className.match(classID)) {
                return(false);
            }

            if (!document.namespaces.v) {
                document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
            }

            this.className = this.className.concat(' ', classID);
            var arcSize = Math.min(width / Math.min(this.offsetWidth, this.offsetHeight), 1);
            var fillColor = this.currentStyle.backgroundColor;
            var backgroundImage = this.currentStyle.backgroundImage;
            var fillSrc = this.currentStyle.backgroundImage.replace(/^url\("(.+)"\)$/, '$1');
            var borderColor = this.currentStyle.borderColor;
            var borderWidth = parseInt(this.currentStyle.borderWidth);
            var stroked = 'true';
            if (isNaN(borderWidth)) {
                borderWidth = 0;
                borderColor = fillColor;
                stroked = 'false';
            }

            this.style.background = 'transparent';
            this.style.borderColor = 'transparent';

            var rect_size = {
                'width': this.offsetWidth - borderWidth,
                'height': this.offsetHeight - borderWidth
            };

            var rect = document.createElement('v:roundrect');
            rect.arcsize = arcSize + 'px';
            rect.strokecolor = borderColor;
            rect.strokeWeight = borderWidth + 'px';
            rect.stroked = stroked;
            rect.style.display = 'block';
            rect.style.width = rect_size.width + 'px';
            rect.style.height = rect_size.height + 'px';
            rect.style.antialias = true;
            rect.fill = true;
            rect.fillcolor = fillColor;

            var fill = document.createElement('v:fill');
            fill.src = fillSrc;
            fill.color = fillColor;
            fill.type = 'tile';
            rect.appendChild(fill);

            var divWitOldContent = document.createElement('div');
            $(divWitOldContent).html($(this).html());
            $(divWitOldContent).width("100%");
            $(divWitOldContent).height("100%");
            rect.appendChild(divWitOldContent);


            var tempDiv = document.createElement('div');
            tempDiv.style.height = "0";
            tempDiv.style.position = "relative";
            tempDiv.style.top = "-" + rect.style.height;
            tempDiv.style.left = "0";


            tempDiv.appendChild(rect);
            this.appendChild(tempDiv);



            var css = this.document.createStyleSheet();
            css.addRule("v\\:roundrect", "behavior: url(#default#VML)");
            css.addRule("v\\:fill", "behavior: url(#default#VML)");

            /*isIE6 = /msie|MSIE 6/.test(navigator.userAgent);
            // IE6 doesn't support transparent borders, use padding to offset original element
            if (isIE6 && (borderWidth > 0)) {
                this.style.borderStyle = 'none';
                this.style.paddingTop = parseInt(this.currentStyle.paddingTop || 0) + borderWidth;
                this.style.paddingBottom = parseInt(this.currentStyle.paddingBottom || 0) + borderWidth;
            }*/
        });
    };

    $.fn.uncorner = function() {
        if (moz || webkit)
            this.css(moz ? '-moz-border-radius' : '-webkit-border-radius', 0);
        $('div.jquery-corner', this).remove();
        return this;
    };

    // expose options
    $.fn.corner.defaults = {
        useNative: true, // true if plugin should attempt to use native browser support for border radius rounding
        metaAttr:  'data-corner' // name of meta attribute to use for options
    };

})(jQuery);
