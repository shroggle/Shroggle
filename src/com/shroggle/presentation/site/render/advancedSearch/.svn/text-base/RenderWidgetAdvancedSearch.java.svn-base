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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.entity.*;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
public class RenderWidgetAdvancedSearch {

    public static String renderWidgetAdvancedSearch(final Widget widget, final SiteShowOption siteShowOption, final RenderContext context) throws IOException, ServletException {
        final WidgetItem widgetItem = (WidgetItem) widget;
        final Item item = widgetItem.getDraftItem();

        if (item == null) {
            return ServiceLocator.getHtmlGetter().get(
                    "/site/render/renderWidgetNotConfigure.jsp?widgetType=Advanced Search",
                    context.getRequest(), context.getResponse(), context.getServletContext());
        } else {
            final DraftAdvancedSearch advancedSearch = (DraftAdvancedSearch) item;
            final DraftGallery draftGallery = ServiceLocator.getPersistance().getDraftItem(advancedSearch.getGalleryId());
            final String galleryHtml = new ShowGalleryUtils(siteShowOption).createGalleryInnerHtml(draftGallery, widget, context);

            final DraftGallery gallery = ServiceLocator.getPersistance().getGalleryById(advancedSearch.getGalleryId());

            if (gallery == null) {
                return ServiceLocator.getHtmlGetter().get(
                        "/site/render/renderWidgetNotConfigure.jsp?widgetType=Advanced Search",
                        context.getRequest(), context.getResponse(), context.getServletContext());
            }

            context.getRequest().setAttribute("widget", widgetItem);
            context.getRequest().setAttribute("siteShowOption", siteShowOption);
            context.getRequest().setAttribute("advancedSearch", advancedSearch);
            context.getRequest().setAttribute("galleryHtml", galleryHtml);
            context.getRequest().setAttribute("currentGalleryItemsHashCodes", gallery.getCurrentFilledFormHashCodes());
            context.getRequest().setAttribute("fullGalleryItemsHashCodes", gallery.getFullFilledFormHashCodes());

            return ServiceLocator.getHtmlGetter().get(
                    "/site/render/advancedSearch/renderWidgetAdvancedSearch.jsp?" +
                            StringUtil.getEmptyOrString(context.getParameterMap().get(ItemType.ADVANCED_SEARCH)),
                    context.getRequest(), context.getResponse(), context.getServletContext());
        }
    }

}
