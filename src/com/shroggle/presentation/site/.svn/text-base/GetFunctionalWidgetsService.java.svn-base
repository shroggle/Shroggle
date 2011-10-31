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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.RenderTheme;
import com.shroggle.presentation.site.render.RenderWidgets;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

@RemoteProxy
public class GetFunctionalWidgetsService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo getInfoForSingleWidget(final int widgetId)
            throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final Widget widget = userManager.getRight().getSiteRight().getWidgetForEdit(widgetId);
        return createFunctionalWidgetInfo(widget, "widget", true);
    }

    public FunctionalWidgetInfo createFunctionalWidgetInfo(Widget widget, String place, boolean renderContent) throws IOException, ServletException {
        if (widget == null) {
            return new FunctionalWidgetInfo();
        }

        final FunctionalWidgetInfo functionalWidgetInfo = new FunctionalWidgetInfo();
        functionalWidgetInfo.setWidgetId(widget.getWidgetId());

        final Item item;
        if (widget.isWidgetItem()) {
            item = new WidgetManager(widget).getItemManager().getItem(SiteShowOption.getDraftOption());
        } else {
            item = null;
        }

        functionalWidgetInfo.setWidget(widget, SiteShowOption.getDraftOption());
        functionalWidgetInfo.setWidgetInfo(getInfoForWidget(widget, item, place));
        functionalWidgetInfo.setWidgetPosition(widget.getPosition());
        functionalWidgetInfo.setWidgetStyle(new RenderTheme(new PageManager(widget.getPage()), SiteShowOption.getDraftOption()).createBorderBackgroundStyle(true));

        if (item != null && item.getItemType() == ItemType.MENU) {
            final DraftMenu menu = (DraftMenu) item;
            functionalWidgetInfo.setMenuType(menu.getMenuStyleType());
        }

        if (renderContent) {
            final RenderContext context = createRenderContext(true);
            functionalWidgetInfo.setWidgetContent(new RenderWidgets(new PageManager(widget.getPage()), SiteShowOption.getDraftOption()).executeWidget(widget, context));
        }
        return functionalWidgetInfo;
    }

    public String getInfoForWidget(final Widget widget, final Item item, final String place) {
        if (item == null) {
            return "Unknown type of widget";
        }
        final String draftItemTitle = international.get(item.getItemType().toString());
        switch (item.getItemType()) {
            case FORUM:
            case BLOG:
            case BLOG_SUMMARY:
            case REGISTRATION:
            case CHILD_SITE_REGISTRATION:
            case CUSTOM_FORM:
            case MENU:
            case CONTACT_US:
            case MANAGE_VOTES:
            case SHOPPING_CART:
            case TELL_FRIEND:
            case ADVANCED_SEARCH:
            case PURCHASE_HISTORY:
            case TAX_RATES:
            case SLIDE_SHOW:
            case IMAGE: {
                return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + item.getName() + "</span>";
            }
            case LOGIN: {
                return draftItemTitle;
            }
            case ADMIN_LOGIN: {
                return "<span style=\"word-wrap:break-word; vertical-align: top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + StringUtil.getEmptyOrString(((DraftAdminLogin) item).getText()) + "</span>";
            }
            case SCRIPT:
                return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + ItemManager.getTitle(item) + "</span>";
            case TEXT: {
                String noHTMLText = ItemManager.getTitle(item);
                noHTMLText = noHTMLText.replaceAll("\\<.*?\\>", "");
                return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + (noHTMLText.length() < 36 ? noHTMLText + "</span>" : noHTMLText.substring(0, 36) + "..." + "</span>");
            }
            case VIDEO: {
                final DraftVideo video1 = (DraftVideo) item;
                if (video1.getFlvVideoId() != null) {
                    return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + video1.getName() + "</span>";
                } else {
                    return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + "video / audio undefined" + "</span>";
                }
            }
            case GALLERY: {
                final String preTitle;
                final DraftGallery draftGallery = (DraftGallery) item;
                if (draftGallery.getPaypalSettings().isEnable()) {
                    preTitle = international.get("E_COMMERCE_STORE");
                } else if (draftGallery.isIncludesVotingModule()) {
                    preTitle = international.get("Voting1");
                } else {
                    preTitle = draftItemTitle;
                }
                return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + preTitle + ": " + item.getName() + "</span>";
            }
            case GALLERY_DATA: { // todo. Check this. I`m not sure that it`s correct to keep crossWidgetId in widget. Tolik.
                final WidgetItem widgetGalleryData = (WidgetItem) widget;
                String galleryTitle = "";

                for (final DraftGallery gallery : persistance.getGalleriesByDataCrossWidgetIds(widgetGalleryData.getCrossWidgetId(),
                        widgetGalleryData.getParentCrossWidgetId())) {
                    if (galleryTitle.length() > 0) {
                        galleryTitle += ", ";
                    }
                    galleryTitle += gallery.getName();
                }

                if (galleryTitle.length() == 0) {
                    galleryTitle = international.get("noConnectedGalleries");
                }

                return "<span style=\"word-wrap:break-word; vertical-align:top;\" id=\"widgetNameSpan" + widget.getWidgetId() + place + "\">" + draftItemTitle + ": " + galleryTitle + "</span>";
            }
            default: {
                return "Unknown type of widget";
            }
        }
    }

    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    private final International international = internationalStorage.get("addWidget", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();

}
