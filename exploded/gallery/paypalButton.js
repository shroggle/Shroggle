var galleryPaypalButton = {};

galleryPaypalButton.showLoginIfConfirm = function (widgetId, showFromShoppingCart) {
    if (confirm($("#youHaveToBeLoggedInToBuy" + widgetId).val())) {
        galleryPaypalButton.showPurchaseLogin($(".blockToReload" + widgetId), widgetId, showFromShoppingCart);
    }
};

galleryPaypalButton.showPurchaseLogin = function (block, widgetId, showFromShoppingCart) {
    var buyersFormId = $("#buyersFormId" + widgetId).val();

    if (!buyersFormId || buyersFormId.length == 0 || buyersFormId == "null") {
        alert("There is no buyers form selected although track orders is enabled.");
    }

    if (showFromShoppingCart) {
        new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForShoppingCart", widgetId, buyersFormId, function(data) {
            showLoginCallback(data);
        });
    } else {
        new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForGalleryWithForm", widgetId, buyersFormId,
                buyersFormId, function(data) {
            showLoginCallback(data);
        });
    }

    function showLoginCallback(data) {
        $(block).hide();
        $(block).parent().append(data);

        bindLoginFormSubmitEvent(widgetId);
    }
};

galleryPaypalButton.performRecurringPayment = function (widgetId, galleryId, userId, productFilledItemId,
                                                        priceFilledItemId, subscriptionFilledItemId, groupsFilledItemId, filledFormId,
                                                        registrationFormId, shoppingCartId, showFromShoppingCart) {
    var request = {
        widgetId: widgetId,
        galleryId: galleryId,
        userId: userId == "null" ? null : userId,
        productFilledItemId: productFilledItemId,
        priceFilledItemId: priceFilledItemId,
        subscriptionFilledItemId: subscriptionFilledItemId == "null" ? null : subscriptionFilledItemId,
        groupsFilledItemId: groupsFilledItemId == "null" ? null : groupsFilledItemId,
        filledFormId: filledFormId,
        registrationFormId: registrationFormId,
        redirectToUrl: window.location.href
    };

    var serviceCall = new ServiceCall();

    galleryPaypalButton.createLoadingMessage($("#redirectingToPaypal" + widgetId).val());
    serviceCall.executeViaDwr("PaypalButtonPaymentService", "goToPaypalFromPaypalButton", request, function (response) {
        if (showFromShoppingCart) {
            shoppingCart.removeItemFromCookie(shoppingCartId, filledFormId, galleryId);
        }

        galleryPaypalButton.removeLoadingImage();
        window.location = response;
    });
};

galleryPaypalButton.removeItemFromCookie = function (paypalButtonForm, shoppingCartId, galleryId) {
    $(paypalButtonForm).find(".filledFormIds").each(function () {
        shoppingCart.removeItemFromCookie(shoppingCartId, $(this).attr("filledFormId"), galleryId);
    });
};

galleryPaypalButton.createLoadingMessage = function (message) {
    createBackground();
    createLoadingMessage({text:message, color:"darkgreen"});
};

galleryPaypalButton.removeLoadingImage = function() {
    removeLoadingMessage();
    removeBackground();
};
