function selectTab(tab) {
    $(".selected_tab").removeClass("selected_tab").addClass("unselected_tab");
    $(tab).removeClass("unselected_tab").addClass("selected_tab");
    resizeTabs();
}

function selectTabNew(tab) {
    $(".new_selected_tab").removeClass("new_selected_tab").addClass("new_unselected_tab");
    $(tab).removeClass("new_unselected_tab").addClass("new_selected_tab");
}

function selectTabInTwoColumnWindow(tab, tabContentId, afterChangeFunction) {
    // After pressing "Apply" button we always enabling currently selected window, so I just add function, which
    // executes after window`s content enabling and switching the tab in this function. Tolik
    // Note, that we are also enabling window after exception, but "onEnableContent" event is not triggered in this case.
    if (isWindowSettingsChanged()) {
        if (confirm(getParentWindow().internationalErrorTexts.saveUnsavedChanges)) {
            getActiveWindow().onEnableContent = switchTab;
            getActiveWindow().clickApply();
        }
    } else {
        switchTab();
    }

    function switchTab() {
        getActiveWindow().onEnableContent = function() {};
        setWindowSettingsUnchanged();
        $(".c1current").removeClass("c1current").addClass("c1");
        $(tab).removeClass("c1").addClass("c1current");

        $(".tabContent", getActiveWindow().getWindowContentDiv()).hide();
        $("#" + tabContentId).show();

        if (afterChangeFunction) {
            afterChangeFunction();
        }
    }
}

function resizeTabs() {
    var selectesTabs = $("a.selected_tab");
    var unselectedTabs = $("a.unselected_tab");
    var newWidth = $("#tabWidth")[0].value;
    if (navigator.appVersion.indexOf("MSIE 8.0") != -1) {
        newWidth -= 7;
    }
    var padding, width;
    for (var i = 0; i < selectesTabs.length; i++) {
        setTabSize(selectesTabs[i]);
    }
    for (i = 0; i < unselectedTabs.length; i++) {
        setTabSize(unselectedTabs[i]);
    }

    function setTabSize(tab) {
        tab.style.paddingLeft = "0px";
        tab.style.paddingRight = "0px";
        width = tab.offsetWidth;
        if (width || width == 0) {
            createPadding();
            tab.style.paddingLeft = padding + "px";
            tab.style.paddingRight = padding + "px";
        }
    }

    function createPadding() {
        var difference = (newWidth - width);
        if (((difference % 2)) != 0) {
            difference = difference - 1;
        }
        padding = (difference / 2);
    }
}

function showTab(tab) {
    selectTab(tab);
    $(".tab").hide();
    var index = $.inArray(tab, $(".tabTitle").get());
    $($(".tab").get(index)).show();
}
