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
package com.shroggle.presentation.gallery;

import com.shroggle.entity.*;
import com.shroggle.logic.gallery.*;
import com.shroggle.logic.gallery.voting.VoteManager;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.html.HtmlGetter;
import com.shroggle.util.persistance.Persistance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
public class ShowGalleryUtils {

    public ShowGalleryUtils(final SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public <T extends Gallery> String createGalleryInnerHtml(final T gallery, final Widget widgetGallery, final RenderContext context)
            throws IOException, ServletException {
        GalleryManager galleryManager = new GalleryManager(gallery);
        if (galleryManager.showDataAndNavigation()) {
            return createDataNavigationHtml(gallery, context, widgetGallery);
        } else if (galleryManager.showDataOnly()) {
            final GalleryData galleryData = GalleryData.newInstance(gallery, context, widgetGallery, siteShowOption);
            return createDataHtml(galleryData, (WidgetItem) widgetGallery, context);
        } else if (galleryManager.showNavigationOnly()) {
            return createNavigationHtml(gallery, widgetGallery, null, context);
        }
        return "";
    }

    public String createDataHtml(final GalleryData galleryData, final WidgetItem widgetGalleryData, final RenderContext context)
            throws IOException, ServletException {
        final HtmlGetter htmlGetter = ServiceLocator.getHtmlGetter();
        final HttpServletRequest request = context.getRequest();

        if (galleryData == null) {
            return htmlGetter.get("/site/render/renderWidgetNotConfigure.jsp?widgetType=Gallery",
                    request, context.getResponse(), context.getServletContext());
        }

        request.setAttribute("galleryData", galleryData);
        request.setAttribute("siteShowOption", siteShowOption);
        request.setAttribute("widget", widgetGalleryData);
        request.setAttribute("votingStarsData", VoteManager.createVotingStarsData(
                galleryData.getGalleryManager().getEntity(), widgetGalleryData.getWidgetId(), widgetGalleryData.getSiteId(), galleryData.getFilledFormId()).get(0));
        request.setAttribute("votingLinksData", VoteManager.createVotingLinksData(
                galleryData.getGalleryManager().getEntity(), widgetGalleryData.getPage().getPageId(), siteShowOption));
        request.setAttribute("currentDisplayedFilledFormId" + galleryData.getGalleryManager().getId(), galleryData.getFilledFormId());

        return htmlGetter.get("/site/render/gallery/renderWidgetGalleryData.jsp",
                request, context.getResponse(), context.getServletContext());
    }

    public String createNavigationHtml(final Gallery gallery, final Widget widgetGallery,
                                       Integer selectedPage, final RenderContext context)
            throws IOException, ServletException {
        setContextForNavigation(gallery, widgetGallery, context, selectedPage);
        return ServiceLocator.getHtmlGetter().get("/site/render/gallery/renderWidgetGalleryNavigation.jsp",
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    public String createInternalNavigationHtml(final DraftGallery gallery, final Widget widgetGallery,
                                               Integer selectedPage, final RenderContext context)
            throws IOException, ServletException {

        setContextForNavigation(gallery, widgetGallery, context, selectedPage);

        return ServiceLocator.getHtmlGetter().get("/site/render/gallery/renderWidgetGalleryNavigationInternal.jsp",
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    /*-------------------------------------------------Private methods------------------------------------------------*/

    private String createDataNavigationHtml(Gallery gallery, final RenderContext context, final Widget widgetGallery)
            throws IOException, ServletException {
        context.getRequest().setAttribute("orientation", gallery.getOrientation() != null ? gallery.getOrientation() :
                GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        setContextForNavigation(gallery, widgetGallery, context, null);
        context.getRequest().setAttribute("widgetGallery", widgetGallery);
        context.getRequest().setAttribute("widget", widgetGallery);
        final GalleryData galleryData = GalleryData.newInstance(gallery, context, widgetGallery, siteShowOption);
        context.getRequest().setAttribute("galleryData", galleryData);
        context.getRequest().setAttribute("siteShowOption", siteShowOption);
        context.getRequest().setAttribute("votingStarsData", VoteManager.createVotingStarsData(
                gallery, widgetGallery.getWidgetId(), widgetGallery.getSite().getSiteId(),
                galleryData.getFilledFormId()).get(0));
        context.getRequest().setAttribute("votingLinksData", VoteManager.createVotingLinksData(
                gallery, widgetGallery.getPage().getPageId(), siteShowOption));
        context.getRequest().setAttribute("currentDisplayedFilledFormId" + galleryData.getGalleryManager().getId(), galleryData.getFilledFormId());

        return ServiceLocator.getHtmlGetter().get("/site/render/gallery/renderWidgetGalleryDataNavigation.jsp",
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    private void setContextForNavigation(
            final Gallery gallery, final Widget widgetGallery,
            final RenderContext context, Integer selectedPage) {

        final GalleryManager galleryManager = new GalleryManager(gallery);
        selectedPage = GalleryPaginatorManager.createSelectedPageNumber(gallery, selectedPage);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, selectedPage,
                context.getRequest().getSession(), siteShowOption);
        navigationRows = navigationRows != null ? navigationRows : new ArrayList<NavigationRow>();
        context.getRequest().setAttribute("navigationRows", navigationRows);

        final DraftItem draftItem = ((WidgetItem) widgetGallery).getDraftItem();
        PaginationCell[] paginationCells = GalleryPaginatorCreator.createPaginator(gallery, draftItem, selectedPage, siteShowOption);
        final int paginatorWidth = GalleryPaginatorManager.createPaginatorWidth(paginationCells, gallery);
        context.getRequest().setAttribute("paginationCells", paginationCells);
        context.getRequest().setAttribute("navigationWidth", galleryManager.createNavigationWidth(paginatorWidth));
        context.getRequest().setAttribute("navigationRowWidth", galleryManager.createNavigationRowWidth());

        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        if (contextStorage.get().getUserId() != null)
            for (GalleryItem item : gallery.getItems()) {
                if (item != null && item.getWidth() != null && item.getWidth() > 0 && item.getHeight() != null && item.getHeight() > 0) {
                    final DraftFormItem formItem = persistance.getFormItemById(item.getId().getFormItemId());
                    if (formItem != null && formItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
                        context.getRequest().setAttribute("hideWatchedVideos",
                                contextStorage.get().isHideWatchedVideos(gallery.getId()));
                        break;
                    }
                }
            }

        PaginatorType paginatorType = GalleryPaginatorManager.getPaginatorType(gallery);
        context.getRequest().setAttribute("navigationHeight", galleryManager.createNavigationHeight());
        context.getRequest().setAttribute("paginatorType", paginatorType);
        context.getRequest().setAttribute("widgetGallery", widgetGallery);
        context.getRequest().setAttribute("gallery", gallery);
        context.getRequest().setAttribute("navigationType", gallery.getNavigationPaginatorType());
        context.getRequest().setAttribute("verticalScroll", gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_VERTICALLY);
        context.getRequest().setAttribute("horizontalScroll", gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY);
        context.getRequest().setAttribute("siteShowOption", siteShowOption);
        context.getRequest().setAttribute("hidePaginator", GalleryPaginatorManager.hidePaginator(gallery, draftItem, siteShowOption));
        /*-----------------------------------------options for navigation size----------------------------------------*/
        context.getRequest().setAttribute("galleryNavigationDivId", "galleryNavigationDivId" + widgetGallery.getWidgetId());
        context.getRequest().setAttribute("galleryNavigationCellDivId", "galleryNavigationCellDivId" + widgetGallery.getWidgetId());
        context.getRequest().setAttribute("galleryNavigationRowDivId", "galleryNavigationRowDivId" + widgetGallery.getWidgetId());
        context.getRequest().setAttribute("paginatorId", "paginatorId" + widgetGallery.getWidgetId());
        context.getRequest().setAttribute("galleryNavigationRowsNumber", navigationRows.size());
        context.getRequest().setAttribute("galleryNavigationColumnsNumber", (navigationRows.size() > 0 ? navigationRows.get(0).getCells().size() : 0));
        context.getRequest().setAttribute("scrollType", gallery.getNavigationPaginatorType());
        /*-----------------------------------------options for navigation size----------------------------------------*/
    }

    public static Integer getCurrentlyDisplayedFilledFormId(final Item item, final Widget widget, final RenderContext context) {
        Integer currentDisplayedFilledFormId = null;
        if (item.getItemType() == ItemType.GALLERY) {
            currentDisplayedFilledFormId = context.getRequest().getAttribute("currentDisplayedFilledFormId" + item.getId()) != null ?
                    (Integer) context.getRequest().getAttribute("currentDisplayedFilledFormId" + item.getId()) : null;
        } else if (item.getItemType() == ItemType.GALLERY_DATA) {
            final List<DraftGallery> galleries =
                    ServiceLocator.getPersistance().getGalleriesByDataCrossWidgetIds(widget.getCrossWidgetId());
            for (DraftGallery gallery : galleries) {
                currentDisplayedFilledFormId = context.getRequest().getAttribute("currentDisplayedFilledFormId" + gallery.getId()) != null ?
                        (Integer) context.getRequest().getAttribute("currentDisplayedFilledFormId" + gallery.getId()) : null;
                if (currentDisplayedFilledFormId != null) {
                    // we may have gotten a couple of galleries but only one should be in request so if we found it - break the cycle
                    break;
                }
            }
        }

        return currentDisplayedFilledFormId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SiteShowOption siteShowOption;
}