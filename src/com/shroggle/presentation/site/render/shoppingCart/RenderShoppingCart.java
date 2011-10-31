/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.presentation.site.render.shoppingCart;

import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.net.URLDecoder;

/**
 * @author dmitry.solomadin
 */
public class RenderShoppingCart {

    public static String execute(final Widget widget, final SiteShowOption siteShowOption,
                                 final RenderContext context) throws IOException, ServletException {
        Item item = ((WidgetItem) widget).getDraftItem();

        return renderShoppingCart(widget, siteShowOption, item, null, context);
    }

    public static String executeWithCookies(final Widget widget, final SiteShowOption siteShowOption,
                                            final String cookieValue, final RenderContext context) throws IOException, ServletException {
        Item item = ((WidgetItem) widget).getDraftItem();

        return renderShoppingCart(widget, siteShowOption, item, cookieValue, context);
    }

    private static String renderShoppingCart(final Widget widget, final SiteShowOption siteShowOption,
                                             final Item item, final String inputCookieValue,
                                             final RenderContext context) throws IOException, ServletException {
        List<FilledFormGalleryIdsPair> filledFormGalleryPairs = new ArrayList<FilledFormGalleryIdsPair>();

        String cookieValue = "";
        if (inputCookieValue != null && !inputCookieValue.isEmpty()) {
            cookieValue = inputCookieValue;
        } else {
            Cookie cookie = ActionUtil.findCookie(context.getRequest().getCookies(), "shoppingCart" + item.getId());
            if (cookie != null) {
                cookieValue = cookie.getValue();
            }
        }
        if (!cookieValue.isEmpty()) {
            String[] rawFilledFormGalleryPairs = cookieValue.split("\\.");
            try {
                for (String rawFilledFormGalleryPair : rawFilledFormGalleryPairs) {
                    rawFilledFormGalleryPair = URLDecoder.decode(rawFilledFormGalleryPair, "UTF-8");

                    String[] parsedFilledFormGalleryPair = rawFilledFormGalleryPair.split(",");
                    final FilledFormGalleryIdsPair filledFormGalleryIdsPair = new FilledFormGalleryIdsPair();
                    filledFormGalleryIdsPair.setFilledFormId(Integer.parseInt(parsedFilledFormGalleryPair[0]));
                    filledFormGalleryIdsPair.setGalleryId(Integer.parseInt(parsedFilledFormGalleryPair[1]));
                    filledFormGalleryIdsPair.setWidgetId(Integer.parseInt(parsedFilledFormGalleryPair[2]));
                    filledFormGalleryIdsPair.setQuantity(Integer.parseInt(parsedFilledFormGalleryPair[3]));

                    filledFormGalleryPairs.add(filledFormGalleryIdsPair);
                }
            } catch (Exception ex) {
                filledFormGalleryPairs = null;
            }
        }

        context.getRequest().setAttribute("widget", widget);
        context.getRequest().setAttribute("filledFormGalleryPairs", filledFormGalleryPairs);
        context.getRequest().setAttribute("shoppingCart", item);
        context.getRequest().setAttribute("siteShowOption", siteShowOption);

        return ServiceLocator.getHtmlGetter().get(
                "/site/render/renderShoppingCart.jsp?" +
                        StringUtil.getEmptyOrString(context.getParameterMap().get(ItemType.SHOPPING_CART)),
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

}
