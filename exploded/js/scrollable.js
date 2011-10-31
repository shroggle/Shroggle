/**
 * @license
 * jQuery Tools 1.2.5 Scrollable - New wave UI design
 *
 * NO COPYRIGHTS OR LICENSES. DO WHAT YOU LIKE.
 *
 * http://flowplayer.org/tools/scrollable.html
 *
 * Since: March 2008
 * Date:    Wed Sep 22 06:02:10 2010 +0000
 */
(function($) {

    // static constructs
    $.tools = $.tools || {version: '1.2.5'};

    $.tools.scrollable = {

        conf: {
            activeClass: 'active',
            circular: false,
            clonedClass: 'cloned',
            disabledClass: 'disabled',
            easing: 'swing',
            initialIndex: 0,
            item: null,
            items: '.items',
            keyboard: true,
            mousewheel: false,
            next: '.next',
            prev: '.prev',
            speed: 400,
            vertical: false,
            touch: true,
            wheelSpeed: 0
        }
    };

    // get hidden element's width or height even though it's hidden
    function dim(el, key) {
        var v = parseInt(el.css(key), 10);
        if (v) {
            return v;
        }
        var s = el[0].currentStyle;
        return s && s.width && parseInt(s.width, 10);
    }

    function find(root, query) {
        var el = $(query);
        return el.length < 2 ? el : root.parent().find(query);
    }

    var current;

    // constructor
    function Scrollable(root, conf) {

        // current instance
        var self = this,
                fire = root.add(self),
                itemWrap = root.children(),
                index = 0,
                vertical = conf.vertical;

        if (!current) {
            current = self;
        }
        if (itemWrap.length > 1) {
            itemWrap = $(conf.items, root);
        }

        // methods
        $.extend(self, {

            getConf: function() {
                return conf;
            },

            getIndex: function() {
                return index;
            },

            getSize: function() {
                return self.getItems().size();
            },

            getNaviButtons: function() {
                return prev.add(next);
            },

            getRoot: function() {
                return root;
            },

            getItemWrap: function() {
                return itemWrap;
            },

            getItems: function() {
                return itemWrap.children(conf.item).not("." + conf.clonedClass);
            },

            move: function(offset, time) {
                return self.seekTo(index + offset, time);
            },

            next: function(time) {
                return self.move(1, time);
            },

            prev: function(time) {
                return self.move(-1, time);
            },

            begin: function(time) {
                return self.seekTo(0, time);
            },

            end: function(time) {
                return self.seekTo(self.getSize() - 1, time);
            },

            focus: function() {
                current = self;
                return self;
            },

            addItem: function(item) {
                item = $(item);

                if (!conf.circular) {
                    itemWrap.append(item);
                } else {
                    itemWrap.children("." + conf.clonedClass + ":last").before(item);
                    itemWrap.children("." + conf.clonedClass + ":first").replaceWith(item.clone().addClass(conf.clonedClass));
                }

                fire.trigger("onAddItem", [item]);
                return self;
            },


            /* all seeking functions depend on this */
            seekTo: function(i, time, fn) {

                // ensure numeric index
                if (!i.jquery) {
                    i *= 1;
                }

                // avoid seeking from end clone to the beginning
                if (conf.circular && i === 0 && index == -1 && time !== 0) {
                    return self;
                }

                // check that index is sane
                if (!conf.circular && i < 0 || i > self.getSize() || i < -1) {
                    return self;
                }

                var item = i;

                if (i.jquery) {
                    i = self.getItems().index(i);

                } else {
                    item = self.getItems().eq(i);
                }

                // onBeforeSeek
                var e = $.Event("onBeforeSeek");
                if (!fn) {
                    fire.trigger(e, [i, time]);
                    if (e.isDefaultPrevented() || !item.length) {
                        return self;
                    }
                }

                var props = vertical ? {top: -item.position().top} : {left: -item.position().left};

                index = i;
                current = self;
                if (time === undefined) {
                    time = conf.speed;
                }

                itemWrap.animate(props, time, conf.easing, fn || function() {
                    fire.trigger("onSeek", [i]);
                });

                return self;
            }

        });

        // callbacks
        $.each(['onBeforeSeek', 'onSeek', 'onAddItem'], function(i, name) {

            // configuration
            if ($.isFunction(conf[name])) {
                $(self).bind(name, conf[name]);
            }

            self[name] = function(fn) {
                if (fn) {
                    $(self).bind(name, fn);
                }
                return self;
            };
        });

        // circular loop
        if (conf.circular) {

            var cloned1 = self.getItems().slice(-1).clone().prependTo(itemWrap),
                    cloned2 = self.getItems().eq(1).clone().appendTo(itemWrap);

            cloned1.add(cloned2).addClass(conf.clonedClass);

            self.onBeforeSeek(function(e, i, time) {


                if (e.isDefaultPrevented()) {
                    return;
                }

                /*
                 1. animate to the clone without event triggering
                 2. seek to correct position with 0 speed
                 */
                if (i == -1) {
                    self.seekTo(cloned1, time, function() {
                        self.end(0);
                    });
                    return e.preventDefault();

                } else if (i == self.getSize()) {
                    self.seekTo(cloned2, time, function() {
                        self.begin(0);
                    });
                }

            });

            // seek over the cloned item
            self.seekTo(0, 0, function() {
            });
        }

        // next/prev buttons
        var prev = find(root, conf.prev).click(function() {
            self.prev();
        }),
                next = find(root, conf.next).click(function() {
                    self.next();
                });

        if (!conf.circular && self.getSize() > 1) {

            self.onBeforeSeek(function(e, i) {
                setTimeout(function() {
                    if (!e.isDefaultPrevented()) {
                        prev.toggleClass(conf.disabledClass, i <= 0);
                        next.toggleClass(conf.disabledClass, i >= self.getSize() - 1);
                    }
                }, 1);
            });

            if (!conf.initialIndex) {
                prev.addClass(conf.disabledClass);
            }
        }

        // mousewheel support
        if (conf.mousewheel && $.fn.mousewheel) {
            root.mousewheel(function(e, delta) {
                if (conf.mousewheel) {
                    self.move(delta < 0 ? 1 : -1, conf.wheelSpeed || 50);
                    return false;
                }
            });
        }

        // touch event
        if (conf.touch) {
            var touch = {};

            itemWrap[0].ontouchstart = function(e) {
                var t = e.touches[0];
                touch.x = t.clientX;
                touch.y = t.clientY;
            };

            itemWrap[0].ontouchmove = function(e) {

                // only deal with one finger
                if (e.touches.length == 1 && !itemWrap.is(":animated")) {
                    var t = e.touches[0],
                            deltaX = touch.x - t.clientX,
                            deltaY = touch.y - t.clientY;

                    self[vertical && deltaY > 0 || !vertical && deltaX > 0 ? 'next' : 'prev']();
                    e.preventDefault();
                }
            };
        }

        if (conf.keyboard) {

            $(document).bind("keydown.scrollable", function(evt) {

                // skip certain conditions
                if (!conf.keyboard || evt.altKey || evt.ctrlKey || $(evt.target).is(":input")) {
                    return;
                }

                // does this instance have focus?
                if (conf.keyboard != 'static' && current != self) {
                    return;
                }

                var key = evt.keyCode;

                if (vertical && (key == 38 || key == 40)) {
                    self.move(key == 38 ? -1 : 1);
                    return evt.preventDefault();
                }

                if (!vertical && (key == 37 || key == 39)) {
                    self.move(key == 37 ? -1 : 1);
                    return evt.preventDefault();
                }

            });
        }

        // initial index
        if (conf.initialIndex) {
            self.seekTo(conf.initialIndex, 0, function() {
            });
        }
    }


    // jQuery plugin implementation
    $.fn.scrollable = function(conf) {

        // already constructed --> return API
        var el = this.data("scrollable");
        if (el) {
            return el;
        }

        conf = $.extend({}, $.tools.scrollable.conf, conf);

        this.each(function() {
            el = new Scrollable($(this), conf);
            $(this).data("scrollable", el);
        });

        return conf.api ? el : this;

    };


})(jQuery);


/**
 * @license
 * jQuery Tools 1.2.5 Mousewheel
 *
 * NO COPYRIGHTS OR LICENSES. DO WHAT YOU LIKE.
 *
 * http://flowplayer.org/tools/toolbox/mousewheel.html
 *
 * based on jquery.event.wheel.js ~ rev 1 ~
 * Copyright (c) 2008, Three Dub Media
 * http://threedubmedia.com
 *
 * Since: Mar 2010
 * Date:    Wed Sep 22 06:02:10 2010 +0000
 */
(function($) {

    $.fn.mousewheel = function(fn) {
        return this[ fn ? "bind" : "trigger" ]("wheel", fn);
    };

    // special event config
    $.event.special.wheel = {
        setup: function() {
            $.event.add(this, wheelEvents, wheelHandler, {});
        },
        teardown: function() {
            $.event.remove(this, wheelEvents, wheelHandler);
        }
    };

    // events to bind ( browser sniffed... )
    var wheelEvents = !$.browser.mozilla ? "mousewheel" : // IE, opera, safari
            "DOMMouseScroll" + ( $.browser.version < "1.9" ? " mousemove" : "" ); // firefox

    // shared event handler
    function wheelHandler(event) {

        switch (event.type) {

            // FF2 has incorrect event positions
            case "mousemove":
                return $.extend(event.data, { // store the correct properties
                    clientX: event.clientX, clientY: event.clientY,
                    pageX: event.pageX, pageY: event.pageY
                });

            // firefox
            case "DOMMouseScroll":
                $.extend(event, event.data); // fix event properties in FF2
                event.delta = -event.detail / 3; // normalize delta
                break;

            // IE, opera, safari
            case "mousewheel":
                event.delta = event.wheelDelta / 120;
                break;
        }

        event.type = "wheel"; // hijack the event
        return $.event.handle.call(this, event, event.delta);
    }

})(jQuery);


/**
 * @license
 * jQuery Tools 1.2.5 / Scrollable Autoscroll
 *
 * NO COPYRIGHTS OR LICENSES. DO WHAT YOU LIKE.
 *
 * http://flowplayer.org/tools/scrollable/autoscroll.html
 *
 * Since: September 2009
 * Date:    Wed Sep 22 06:02:10 2010 +0000
 */
(function($) {

    var t = $.tools.scrollable;

    t.autoscroll = {

        conf: {
            autoplay: true,
            interval: 3000,
            autopause: true
        }
    };

    // jQuery plugin implementation
    $.fn.autoscroll = function(conf) {

        if (typeof conf == 'number') {
            conf = {interval: conf};
        }

        var opts = $.extend({}, t.autoscroll.conf, conf), ret;

        this.each(function() {

            var api = $(this).data("scrollable");
            if (api) {
                ret = api;
            }

            // interval stuff
            var timer, stopped = true;

            api.play = function() {

                // do not start additional timer if already exists
                if (timer) {
                    return;
                }

                stopped = false;

                // construct new timer
                timer = setInterval(function() {
                    api.next();
                }, opts.interval);

            };

            api.pause = function() {
                timer = clearInterval(timer);
            };

            // when stopped - mouseover won't restart
            api.stop = function() {
                api.pause();
                stopped = true;
            };

            /* when mouse enters, autoscroll stops */
            if (opts.autopause) {
                api.getRoot().add(api.getNaviButtons()).hover(api.pause, api.play);
            }

            if (opts.autoplay) {
                api.play();
            }

        });

        return opts.api ? ret : this;

    };

})(jQuery);


/**
 * @license
 * jQuery Tools 1.2.5 / Scrollable Navigator
 *
 * NO COPYRIGHTS OR LICENSES. DO WHAT YOU LIKE.
 *
 * http://flowplayer.org/tools/scrollable/navigator.html
 *
 * Since: September 2009
 * Date:    Wed Sep 22 06:02:10 2010 +0000
 */
(function($) {

    var t = $.tools.scrollable;

    t.navigator = {

        conf: {
            navi: '.navi',
            naviItem: null,
            activeClass: 'active',
            indexed: false,
            idPrefix: null,

            // 1.2
            history: false
        }
    };

    function find(root, query) {
        var el = $(query);
        return el.length < 2 ? el : root.parent().find(query);
    }

    // jQuery plugin implementation
    $.fn.navigator = function(conf) {

        // configuration
        if (typeof conf == 'string') {
            conf = {navi: conf};
        }
        conf = $.extend({}, t.navigator.conf, conf);

        var ret;

        this.each(function() {

            var api = $(this).data("scrollable"),
                    navi = conf.navi.jquery ? conf.navi : find(api.getRoot(), conf.navi),
                    buttons = api.getNaviButtons(),
                    cls = conf.activeClass,
                    history = conf.history && $.fn.history;

            // @deprecated stuff
            if (api) {
                ret = api;
            }

            api.getNaviButtons = function() {
                return buttons.add(navi);
            };


            function doClick(el, i, e) {
                api.seekTo(i);
                if (history) {
                    if (location.hash) {
                        location.hash = el.attr("href").replace("#", "");
                    }
                } else {
                    return e.preventDefault();
                }
            }

            function els() {
                return navi.find(conf.naviItem || '> *');
            }

            function addItem(i) {

                var item = $("<" + (conf.naviItem || 'a') + "/>").click(function(e) {
                    doClick($(this), i, e);

                }).attr("href", "#" + i);

                // index number / id attribute
                if (i === 0) {
                    item.addClass(cls);
                }
                if (conf.indexed) {
                    item.text(i + 1);
                }
                if (conf.idPrefix) {
                    item.attr("id", conf.idPrefix + i);
                }

                return item.appendTo(navi);
            }


            // generate navigator
            if (els().length) {
                els().each(function(i) {
                    $(this).click(function(e) {
                        doClick($(this), i, e);
                    });
                });

            } else {
                $.each(api.getItems(), function(i) {
                    addItem(i);
                });
            }

            // activate correct entry
            api.onBeforeSeek(function(e, index) {
                setTimeout(function() {
                    if (!e.isDefaultPrevented()) {
                        var el = els().eq(index);
                        if (!e.isDefaultPrevented() && el.length) {
                            els().removeClass(cls).eq(index).addClass(cls);
                        }
                    }
                }, 1);
            });

            function doHistory(evt, hash) {
                var el = els().eq(hash.replace("#", ""));
                if (!el.length) {
                    el = els().filter("[href=" + hash + "]");
                }
                el.click();
            }

            // new item being added
            api.onAddItem(function(e, item) {
                item = addItem(api.getItems().index(item));
                if (history) {
                    item.history(doHistory);
                }
            });

            if (history) {
                els().history(doHistory);
            }

        });

        return conf.api ? ret : this;

    };

})(jQuery);
