function configureCopyPage() {
    var pageId = getActivePage().pageId;
    var configureWindow = createConfigureWindow({width:600, height:400});
    new ServiceCall().executeViaDwr("ConfigureCopyPageService", "execute", pageId, function (html) {
        if (!isAnyWindowOpened()) {
            return;
        }

        configureWindow.setContent(html);
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function configureCopyPageChangeCopy(element) {
    var itemId = $(element).val();
    var enable = $("#configureCopyPage" + itemId + "Copy").attr("checked");
    $("#configureCopyPage" + itemId + "ClearFontColors").attr("disabled", !enable);
    $("#configureCopyPage" + itemId + "ClearBorderBackground").attr("disabled", !enable);
    setWindowSettingsChanged();
}

// ---------------------------------------------------------------------------------------------------------------------

function copyPage() {
    var request = {
        pageId: $("#configureCopyPageId").val(),
        items: {}
    };

    $(".configureCopyPageShare").each(function () {
        if (this.checked) {
            request.items[$(this).val()] = {
                type: "SHARE",
                clearFontColors: false,
                clearBorderBackground: false
            };
        }
    });

    $(".configureCopyPageCopy").each(function () {
        if (this.checked) {
            request.items[$(this).val()] = {
                type: "COPY",
                clearFontColors: $("#configureCopyPage" + $(this).val() + "ClearFontColors").attr("checked"),
                clearBorderBackground: $("#configureCopyPage" + $(this).val() + "ClearBorderBackground").attr("checked")
            };
        }
    });

    var serviceCall = new ServiceCall();
    serviceCall.executeViaDwr("CopyPageService", "execute", request, function (response) {
        closeConfigureWidgetDiv();
        reloadPageSelect(response.pageSelectHtml);
        reloadTree(response.treeHtml);
        selectPageVersion(getTreeNodeById(response.pageId), true);
    });
}