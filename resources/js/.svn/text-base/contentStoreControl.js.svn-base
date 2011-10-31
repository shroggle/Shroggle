window.contentStoreControl = {

    bind: function (settings) {
        if (!settings.onRestore) {
            throw "Please bind content restore control with onRestore function!";
        }

        if (!settings.onStore) {
            throw "Please bind content restore control with onStore function!";
        }

        if (!settings.id) {
            throw "Please bind content restore control with id field!";
        }

        var item = window.contentStoreControl.pool[settings.id];
        if (item == null) {
            item = {
                clientId: getCookie("CONTENT_CLIENT_ID"),
                id: settings.id,
                onRestore: settings.onRestore,
                onStore: settings.onStore
            };
            window.contentStoreControl.pool[item.id] = item;
        }

        if (item.clientId && !item.reseted) {
            item.restoreRequest = $.ajax({
                url: "/getContentStore.action",
                dataType: "html",
                data: {
                    "contentId.clientId": item.clientId,
                    "contentId.selectId": item.id
                },

                success: function (value) {
                    if (value.trim().length > 0) {
                        item.onRestore({id: item.id, value: value});
                    }
                },

                complete: function () {
                    item.restoreRequest = undefined;
                }
            });
        }

        item.storeTimeout = window.setTimeout(
                "window.contentStoreControl.store(\"" + item.id + "\")", 20000);
    },

    reset: function (id) {
        var item = window.contentStoreControl.pool[id];
        if (item) {
            if (item.stored) {
                $.ajax({
                    url: "/resetContentStore.action",
                    data: {
                        "contentId.clientId": item.clientId,
                        "contentId.selectId": item.id
                    }
                });
            }

            if (item.restoreRequest) {
                item.restoreRequest.abort();
                item.restoreRequest = undefined;
            }

            if (item.storeRequest) {
                item.storeRequest.abort();
                item.storeRequest = undefined;
            }

            if (item.storeTimeout) {
                window.clearTimeout(item.storeTimeout);
                item.storeTimeout = undefined;
            }

            item.reseted = true;
            item.stored = false;
        }
    },

    store: function (id) {
        var item = window.contentStoreControl.pool[id];
        if (item) {
            var value = item.onStore(id);

            if (value && value.length > 0) {
                var data = {
                    "contentId.selectId": item.id,
                    value: value
                };

                if (item.clientId) {
                    data["contentId.clientId"] = item.clientId;
                }

                item.storeRequest = $.ajax({
                    url: "/setContentStore.action",
                    type: "post",
                    dataType: "json",
                    data: data,

                    success: function (clientId) {
                        item.stored = true;
                        item.clientId = clientId;
                        setCookie("CONTENT_CLIENT_ID", clientId, "long");
                        item.storeTimeout = window.setTimeout(
                                "window.contentStoreControl.store(\"" + item.id + "\")", 20000);
                    },

                    complete: function () {
                        item.storedRequest = undefined;
                    }
                });

            } else {
                item.storeTimeout = window.setTimeout(
                        "window.contentStoreControl.store(\"" + item.id + "\")", 20000);
            }
        }
    },

    pool: {}

};
