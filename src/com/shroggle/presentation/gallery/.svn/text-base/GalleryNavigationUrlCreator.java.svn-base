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
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.Arrays;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
public class GalleryNavigationUrlCreator {

    public static GalleryNavigationUrl executeForCurrentWindow(final Gallery gallery, final Widget navigationWidget,
                                                               final int filledFormId, final SiteShowOption siteShowOption) {
        return execute(gallery, navigationWidget, filledFormId, siteShowOption, false, null);
    }

    public static GalleryNavigationUrl executeForNewWindow(final Gallery gallery, final Widget navigationWidget,
                                                           final int filledFormId, final SiteShowOption siteShowOption) {
        return execute(gallery, navigationWidget, filledFormId, siteShowOption, true, null);
    }

    public static GalleryNavigationUrl executeForOtherWidget(final Gallery gallery, final Widget navigationWidget,
                                                             final Widget otherWidget,
                                                             final int filledFormId, final SiteShowOption siteShowOption) {
        return execute(gallery, navigationWidget, filledFormId, siteShowOption, true, otherWidget);
    }

    private static GalleryNavigationUrl execute(final Gallery gallery, final Widget navigationWidget,
                                                final int filledFormId, final SiteShowOption siteShowOption,
                                                final boolean openNewWindow, final Widget fromWidget) {
        final Integer crossWidgetId = (gallery.getOrientation() == GalleryOrientation.NAVIGATION_ONLY
                ? gallery.getDataCrossWidgetId() : (Integer) navigationWidget.getCrossWidgetId());

        // If fromWidget is null then we are redirecting within gallery (gallery navigation and data is one part)
        // otherwise we are redirecting from some other widget(like Manage Your Votes or Shopping Cart).
        Widget fromWidgetInternal = fromWidget == null ? navigationWidget : fromWidget;

        if (gallery.getOrientation() == GalleryOrientation.NAVIGATION_ONLY) {
            final Widget dataWidget = getDataWidget(gallery, filledFormId, siteShowOption);
            if (dataWidget != null) {
                return executeInternal(fromWidgetInternal, dataWidget, gallery, filledFormId, crossWidgetId, openNewWindow, siteShowOption);
            } else {
                return null;
            }
        } else {
            return executeInternal(fromWidgetInternal, navigationWidget, gallery, filledFormId, crossWidgetId, openNewWindow, siteShowOption);
        }
    }

    private static GalleryNavigationUrl executeInternal(final Widget fromWidget, final Widget toWidget,
                                                        final Gallery gallery, final int filledFormId,
                                                        final Integer crossWidgetId,
                                                        final boolean openNewWindow,
                                                        final SiteShowOption siteShowOption) {
        final GalleryNavigationUrl galleryUrl = new GalleryNavigationUrl();

        final int fromPageId = fromWidget.getPage().getPageId();
        final int toPageId = toWidget.getPage().getPageId();

        final GalleryUrlForCurrentPage urlForCurrentPage = new GalleryUrlForCurrentPage(gallery.getId(), filledFormId,
                toWidget.getWidgetId(), siteShowOption);

        if (fromPageId == toPageId) {
            galleryUrl.setUserScript(urlForCurrentPage.getUserScript());
            galleryUrl.setAjaxDispatch(urlForCurrentPage.getAjaxDispatch());
            galleryUrl.setSearchEngineUrl(getSearchEngineUrl(toWidget, filledFormId, gallery, siteShowOption));
            galleryUrl.setKeywords(FilledFormManager.extractKeywords(filledFormId, " "));
        } else if (siteShowOption.isWork()) {
            String link = "'" + new PageSettingsManager(toWidget.getWorkPageSettings()).getPublicUrl() +
                    "?gallerySelectedFilledFormId=" + filledFormId +
                    "&selectedGalleryId=" + gallery.getId() +
                    "&gallerySelectedCrossWidgetId=" + crossWidgetId + "'";
            galleryUrl.setUserScript(addScriptForRedirecting(link, openNewWindow));
            galleryUrl.setSearchEngineUrl(getSearchEngineUrl(toWidget, filledFormId, gallery, siteShowOption));
            galleryUrl.setKeywords(FilledFormManager.extractKeywords(filledFormId, " "));
        } else {
            String link = "'showPageVersion.action?pageId=" + toPageId +
                    "&selectedGalleryId=" + gallery.getId() +
                    "&gallerySelectedCrossWidgetId=" + crossWidgetId +
                    "&gallerySelectedFilledFormId=" + filledFormId + "&siteShowOption=" + siteShowOption.toString() + "'";
            galleryUrl.setUserScript(addScriptForRedirecting(link, openNewWindow));
            galleryUrl.setSearchEngineUrl(getSearchEngineUrl(toWidget, filledFormId, gallery, siteShowOption));
            galleryUrl.setKeywords(FilledFormManager.extractKeywords(filledFormId, " "));
        }

        return galleryUrl;
    }

    private static String getSearchEngineUrl(Widget widget, int filledFormId, Gallery gallery, SiteShowOption siteShowOption) {
        if (siteShowOption.isWork()) {
            return new PageSettingsManager(widget.getWorkPageSettings()).getPublicUrl() +
                    "?gallerySelectedFilledFormId=" + filledFormId + "&selectedGalleryId=" + gallery.getId() +
                    "&SELink=true" +
                    "&" + FilledFormManager.extractKeywords(filledFormId, "+");
        } else {
            return "/site/" + new PageSettingsManager(widget.getDraftPageSettings()).getUrl() +
                    "&gallerySelectedFilledFormId=" + filledFormId + "&selectedGalleryId=" + gallery.getId() +
                    "&SELink=true" +
                    "&" + FilledFormManager.extractKeywords(filledFormId, "+");
        }
    }

    private static String addScriptForRedirecting(String oldLink, final boolean openNewWindow) {
        oldLink = HtmlUtil.insertJsessionId(oldLink);
        if (openNewWindow) {
            return "window.open(" + oldLink + ",'')";
        } else {
            return "window.location = " + oldLink;
        }
    }

    private static Widget getDataWidget(
            final Gallery gallery, final int filledFormId, final SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Widget dataWidget = persistance.getWidgetByCrossWidgetsId(gallery.getDataCrossWidgetId(), siteShowOption);
        Site dataWidgetSite = dataWidget != null ? dataWidget.getSite() : null;
        if (dataWidgetSite != null && dataWidgetSite.getType() != SiteType.BLUEPRINT) {
            return dataWidget;
        } else {
            final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
            ChildSiteSettings settings = persistance.getChildSiteSettingsById(filledForm != null ? filledForm.getChildSiteSettingsId() : null);
            if (settings != null) {
                Site childSite = settings.getSite();
                if (childSite != null) {
                    /**
                     * If we show gallery on work page we use only work gallery data, in other cases
                     * we can use any gallery data.
                     */
                    final List<WidgetItem> widgetGalleryDatas = persistance.getGalleryDataWidgetsBySitesId(
                            Arrays.asList(childSite.getSiteId()), siteShowOption);

                    if (widgetGalleryDatas.size() > 0) {
                        return widgetGalleryDatas.get(0);
                    }
                }
            }
            return null;
        }
    }


}