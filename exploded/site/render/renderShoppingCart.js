var shoppingCart = {};

/*---------------------------------------------SHOPPING CART COOKIES PART---------------------------------------------*/
/* Cookies are separated by '.' and stored in next format: .filledFormId,galleryId,widgetId,qty. */

shoppingCart.addItemToCart = function (widgetId, siteShowOption, shoppingCartId, filledFormId, galleryId) {
    addItemIntoCookies();

    // Update shopping cart if it's on the same page with gallery.
    var shoppingCartBlocks = $("div[shoppingCart='shoppingCart" + shoppingCartId + "']");

    if (shoppingCartBlocks[0]) {
        shoppingCartBlocks.each(function () {
            var shoppingCartBlock = this;

            $("#addToCartLoadingImage" + widgetId).show();
            disableControl($("#addItemToCartButton" + widgetId)[0]);

            new ServiceCall().executeViaDwr("RenderShoppingCartService", "execute", $(shoppingCartBlock).attr("widgetId"), siteShowOption,
                    getCookie("shoppingCart" + shoppingCartId), function (data) {
                addSlidingTimeoutEvent($("#successfullyAddedToCartMessage" + widgetId)[0], 3000);

                $(shoppingCartBlock).replaceWith(data);

                $("#addToCartLoadingImage" + widgetId).hide();

                disableControl($("#addItemToCartButton" + widgetId)[0], false);
            });
        });
    } else {
        addSlidingTimeoutEvent($("#successfullyAddedToCartMessage" + widgetId)[0], 3000);
        shoppingCart.addItemDoubleClickProtection = false;
    }

    function addItemIntoCookies() {
        var productCookieValue = filledFormId + "," + galleryId + "," + widgetId;
        var oldCookieValue = getCookie("shoppingCart" + shoppingCartId);

        if (!oldCookieValue) {
            setCookie("shoppingCart" + shoppingCartId, productCookieValue + ",1");
            return;
        }

        if (oldCookieValue.indexOf(productCookieValue) == -1) {
            oldCookieValue = oldCookieValue + "." + productCookieValue + ",1";
            setCookie("shoppingCart" + shoppingCartId, oldCookieValue);
        } else {
            var oldQuantity = shoppingCart.getQuantityInCookies(shoppingCartId, filledFormId, galleryId);

            shoppingCart.setQuantityInCookies(shoppingCartId, filledFormId, galleryId, (oldQuantity + 1));
        }
    }
};

shoppingCart.setQuantityInCookies = function (shoppingCartId, filledFormId, galleryId, newQuantity) {
    var oldCookieValue = getCookie("shoppingCart" + shoppingCartId);
    var reg = new RegExp("" + filledFormId + "," + galleryId + ",(\\d+),\\d+");
    var widgetIdInCookies = reg.exec(oldCookieValue)[1];

    oldCookieValue = oldCookieValue.replace(reg, filledFormId + "," + galleryId + "," + widgetIdInCookies + "," +
            "" + newQuantity);
    setCookie("shoppingCart" + shoppingCartId, oldCookieValue);
};

shoppingCart.getQuantityInCookies = function (shoppingCartId, filledFormId, galleryId) {
    var oldCookieValue = getCookie("shoppingCart" + shoppingCartId);
    var reg = new RegExp("" + filledFormId + "," + galleryId + ",\\d+,(\\d+)");

    return parseInt(reg.exec(oldCookieValue)[1]);
};

shoppingCart.removeItemFromCookie = function (shoppingCartId, filledFormIdToRemove, galleryIdToRemove) {
    var cookieValue = getCookie("shoppingCart" + shoppingCartId);
    var filledFormGalleryIdPair = filledFormIdToRemove + "," + galleryIdToRemove;
    var widgetId = new RegExp("" + filledFormIdToRemove + "," + galleryIdToRemove + ",(\\d+),\\d+").exec(cookieValue)[1];
    var filledFormGalleryIdsPairToRemove = filledFormGalleryIdPair + "," + widgetId + "," + shoppingCart.
            getQuantityInCookies(shoppingCartId, filledFormIdToRemove, galleryIdToRemove);

    var newCookieValue;
    if (cookieValue.indexOf(filledFormGalleryIdsPairToRemove + ".") != -1) {
        newCookieValue = cookieValue.replace(filledFormGalleryIdsPairToRemove + ".", "");
    } else if (cookieValue.indexOf("." + filledFormGalleryIdsPairToRemove) != -1) {
        newCookieValue = cookieValue.replace("." + filledFormGalleryIdsPairToRemove, "");
    } else {
        newCookieValue = cookieValue.replace(filledFormGalleryIdsPairToRemove, "");
    }

    setCookie("shoppingCart" + shoppingCartId, newCookieValue);
};

/*-------------------------------------------SHOPPING CART COOKIES PART END-------------------------------------------*/

shoppingCart.removeItem = function (img, widgetId, siteShowOption, shoppingCartId, filledFormIdToRemove, galleryIdToRemove) {
    if (confirm("Are you sure you want to remove this item from shopping cart?")) {
        //Removing saved item from cookies.
        shoppingCart.removeItemFromCookie(shoppingCartId, filledFormIdToRemove, galleryIdToRemove);

        $(img).parent().find(".shoppingCartLoading").show();
        $(img).hide();

        new ServiceCall().executeViaDwr("RenderShoppingCartService", "execute", widgetId, siteShowOption,
                getCookie("shoppingCart" + shoppingCartId), function (data) {
            $(".blockToReload" + widgetId).replaceWith(data);

            $(img).parent().find(".shoppingCartLoading").hide();
            $(img).show();
        });
    }
};

shoppingCart.quantityBlur = function (quantityInput, shoppingCartId, filledFormId, galleryId) {
    var quantityInputValue = $(quantityInput).val();

    // Update quantity if it exeeds 9000 or lower that 1.
    if (quantityInputValue > 9000) {
        quantityInputValue = 9000;
    } else if (quantityInputValue < 1) {
        quantityInputValue = 1;
    }
    $(quantityInput).val(quantityInputValue);

    // Update price
    var itemInitialPrice = parseFloat($("#shoppingCartItemPrice" + filledFormId + "" + galleryId).attr("initialPrice"));
    var newPrice = itemInitialPrice * quantityInputValue;
    $("#shoppingCartItemPrice" + filledFormId + "" + galleryId).html(newPrice.toFixed(2));

    // Update tax
    var itemInitialTax = parseFloat($("#shoppingCartItemPrice" + filledFormId + "" + galleryId).attr("tax"));
    var newTax = itemInitialTax * quantityInputValue;
    $("#shoppingCartItemPrice" + filledFormId + "" + galleryId).next('.itemTax').html(newTax);

    // Update quantity in cookies
    shoppingCart.setQuantityInCookies(shoppingCartId, filledFormId, galleryId, quantityInputValue);

    // Update quantity in paypal button
    $("#paypalButtonQuantity" + filledFormId + "" + galleryId).val(quantityInputValue);

    // Update total price
    var groupTable = $(quantityInput).parents("table:first")[0];
    $(groupTable).find(".shoppingCartTotalPrice").html(recalculateTotalPrice(groupTable));

    function recalculateTotalPrice(groupTable) {
        var totalPrice = 0.0;
        $(groupTable).find(".shoppingCartItemPrice").each(function () {
            var initialPrice = parseFloat($(this).attr("initialPrice"));
            var tax = parseFloat($(this).attr("tax"));
            totalPrice += (initialPrice + tax) * quantityInputValue;
        });

        return totalPrice.toFixed(2);
    }
};