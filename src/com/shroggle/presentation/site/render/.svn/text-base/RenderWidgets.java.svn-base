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
package com.shroggle.presentation.site.render;

import com.shroggle.entity.*;
import com.shroggle.exception.UserException;
import com.shroggle.logic.accessibility.AccessibleForRenderManager;
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.forum.ForumDispatchHelper;
import com.shroggle.logic.gallery.GalleryData;
import com.shroggle.logic.menu.MenuItemDataManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageWidgetsByPosition;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.presentation.blogSummary.BlogSummaryDataForPreview;
import com.shroggle.presentation.blogSummary.BlogSummaryDataForPreviewCreator;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.presentation.image.ImageUrl;
import com.shroggle.presentation.site.ShowVisitorLoginService;
import com.shroggle.presentation.site.render.advancedSearch.RenderWidgetAdvancedSearch;
import com.shroggle.presentation.site.render.childSiteRegistration.RenderChildSiteRegistration;
import com.shroggle.presentation.site.render.shoppingCart.RenderShoppingCart;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.html.WebContextManual;
import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;

import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class RenderWidgets implements Render {

    public RenderWidgets(final PageManager pageManager, final SiteShowOption siteShowOption) {
        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, final StringBuilder html) throws IOException, ServletException {
        final Map<Integer, Widget> mediaBlockByPositions = PageWidgetsByPosition.execute(pageManager);
        ServiceLocator.getHtmlProcessor().execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
                position++;
                final Widget widget = mediaBlockByPositions.get(position);
                if (widget != null) {
                    try {
                        block.setHtml(executeWidget(widget, context, false));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    block.setHtml("No select widgets for this position!");
                }
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                position++;
                final Widget widget = mediaBlockByPositions.get(position);
                if (widget != null) {
                    try {
                        block.setId("widget" + widget.getWidgetId());
                        block.setHtml(executeWidget(widget, context, true));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    block.setHtml("No select widgets for this position!");
                }
            }

            private int position = -1;

        });
    }

    public String executeWidget(
            final Widget widget, final RenderContext context)
            throws IOException, ServletException {
        return executeWidget(widget, context, false);
    }

    private String executeWidget(final Widget widget, final RenderContext context, final boolean flat) throws IOException, ServletException {
        final String widgetHtml = executeWidgetWithoutItsSize(widget, context, flat);

        return execute(
                "/site/render/renderWidget.jsp", context,
                new RenderParameter("widgetType", widget.getItemType()),
                new RenderParameter("widget", widget),
                new RenderParameter("itemSize", new WidgetManager(widget).getItemSize(siteShowOption)),
                new RenderParameter("widgetHtml", widgetHtml),
                new RenderParameter("siteShowOption", siteShowOption),
                new RenderParameter("showFromSiteEditPage", context.isShowFromSiteEditPage()));
    }


    public String executeWidgetWithoutItsSize(final Widget widget, final RenderContext context, final boolean flat)
            throws IOException, ServletException {
        String widgetHtml = null;
        ItemType itemType = ItemType.COMPOSIT;
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;

            final AccessibleForRenderManager accessManager = new AccessibleForRenderManager(new WidgetManager(widgetItem));
            final boolean elementAccessible = accessManager.isAccessibleForRender();
            if (!elementAccessible && accessManager.isOnlyAdminsHasAccess()) {
                widgetHtml = execute(
                        "/site/render/renderWidgetWithoutOwnerShowRight.jsp", context,
                        new RenderParameter("widgetId", widget.getWidgetId()));
            } else if (!elementAccessible) {
                widgetHtml = execute(
                        "/site/render/renderWidgetWithoutShowRight.jsp", context,
                        new RenderParameter("widgetId", widget.getWidgetId()));
            } else {
                final Item item = new WidgetManager(widgetItem).getItemManager().getItem(siteShowOption);
                if (item == null || (item.getItemType() == ItemType.VIDEO && ((Video1) item).getFlvVideoId() == null)) {
                    widgetHtml = execute(
                            "/site/render/renderWidgetNotConfigure.jsp", context,
                            new RenderParameter("siteShowOption", siteShowOption),
                            new RenderParameter("showFromSiteEditPage", context.isShowFromSiteEditPage())
                    );
                } else {
                    if (item.getSiteId() < 1) {
                        logger.severe("Trying to render SiteItem without siteId! We can`t correctly render items without siteId. " +
                                "Please, fix this item! itemId = " + item.getId() + ", itemType = " + item.getItemType());
                    }
                    itemType = item.getItemType();
                    switch (item.getItemType()) {
                        case TEXT: {
                            widgetHtml = executeWidgetText(item, widgetItem.getPage().isSystem(), context);
                            break;
                        }
                        case GALLERY: {
                            widgetHtml = new ShowGalleryUtils(siteShowOption).createGalleryInnerHtml((Gallery) item, widgetItem, context);
                            break;
                        }
                        case CONTACT_US: {
                            widgetHtml = executeWidgetContactUs(widget.getWidgetId(), item, context);
                            break;
                        }
                        case CHILD_SITE_REGISTRATION: {
                            widgetHtml = RenderChildSiteRegistration.execute(widget, context);
                            break;
                        }
                        case REGISTRATION: {
                            widgetHtml = renderWidgetRegistration(widget.getWidgetId(), context);
                            break;
                        }
                        case BLOG: {
                            widgetHtml = executeWidgetBlog(widget.getWidgetId(), item, context);
                            break;
                        }
                        case FORUM: {
                            widgetHtml = renderWidgetForum(widget, item, context);
                            break;
                        }
                        case TELL_FRIEND: {
                            widgetHtml = renderWidgetTellFriend(widget.getWidgetId(), widget.getSiteId(),
                                    ((TellFriend)new WidgetManager(widget).getItemManager().getItem(siteShowOption)).isSendEmails(), context);
                            break;
                        }
                        case ADMIN_LOGIN: {
                            widgetHtml = renderAdminLogin(item, context);
                            break;
                        }
                        case SCRIPT: {
                            widgetHtml = renderScript(item, context);
                            break;
                        }
                        case CUSTOM_FORM: {
                            widgetHtml = renderWidgetCustomForm(widget.getWidgetId(), item, context);
                            break;
                        }
                        case VIDEO: {
                            widgetHtml = executeWidgetVideo(widget.getWidgetId(), item, context);
                            break;
                        }
                        case IMAGE: {
                            widgetHtml = executeImage(item, context);
                            break;
                        }
                        case MANAGE_VOTES: {
                            widgetHtml = renderWidgetManageVotes(widget, item, context);
                            break;
                        }
                        case SHOPPING_CART: {
                            widgetHtml = RenderShoppingCart.execute(widget, siteShowOption, context);
                            break;
                        }
                        case GALLERY_DATA: {
                            widgetHtml = renderWidgetGalleryData(widgetItem, context);
                            break;
                        }
                        case PURCHASE_HISTORY: {
                            widgetHtml = renderWidgetPurchaseHistory(widget, item, context);
                            break;
                        }
                        case ADVANCED_SEARCH: {
                            widgetHtml = RenderWidgetAdvancedSearch.renderWidgetAdvancedSearch(widget, siteShowOption, context);
                            break;
                        }
                        case LOGIN: {
                            final ShowVisitorLoginService service = new ShowVisitorLoginService();
                            service.setContext(new WebContextManual(
                                    context.getRequest(), context.getResponse(), context.getServletContext()));
                            widgetHtml = service.execute(widget.getWidgetId(), false);
                            break;
                        }
                        case BLOG_SUMMARY: {
                            widgetHtml = executeWidgetBlogSummary(item, context);
                            break;
                        }
                        case MENU: {
                            widgetHtml = executeWidgetMenu(item, context);
                            break;
                        }
                        case TAX_RATES: {
                            widgetHtml = context.isShowFromSiteEditPage() ? "Taxes Rates item doesn't show any content. It can applied to your e-commerce products." : "";
                            break;
                        }
                        case SLIDE_SHOW: {
                            widgetHtml = renderSlideShow(widget, item, context);
                        }
                    }
                }
            }
        } else if (widget.isWidgetComposit()) {
            final WidgetComposit widgetComposit = (WidgetComposit) widget;
            widgetHtml = "";
            for (final Widget childWidget : widgetComposit.getChilds()) {
                widgetHtml += executeWidget(childWidget, context);
            }
        }

        if (flat) {
            return widgetHtml;
        } else {
            // !!!Be aware that itemType is needed for huge amount of our template style. Before changing this to itemType
            // you should change some of these styles 'couse name in itemType is not always the same in itemType.
            // I`ve changed all our styles for "REGISTRATION". And there were no any styles for "SITE_MENU". Tolik
            return "<div class=\"widget" + itemType + "Style\" id=\"widget" + widget.getWidgetId() + "\"" +
                    " widgetId=\"" + widget.getWidgetId() + "\">" +
                    widgetHtml + "</div>";
        }
    }

    private String renderWidgetGalleryData(final WidgetItem widgetItem, final RenderContext context) throws IOException, ServletException {
        Gallery gallery = null;
        /*-----------------------------------------------Getting gallery----------------------------------------------*/
        Integer selectedGalleryId = context.getIntParameterByName("selectedGalleryId");
        if (selectedGalleryId == null) {
            final ContextStorage contextStorage = ServiceLocator.getContextStorage();
            selectedGalleryId = contextStorage.get().getGalleryShowInData(widgetItem.getCrossWidgetId());
        }

        if (selectedGalleryId != null) {
            gallery = (Gallery) new ItemManager(selectedGalleryId).getItem(siteShowOption);
        }

        if (gallery == null) {
            int crossWidgetId = widgetItem.getCrossWidgetId();
            if (widgetItem.getParentCrossWidgetId() != null) {
                crossWidgetId = widgetItem.getParentCrossWidgetId();
            }

            final List<DraftGallery> galleries = ServiceLocator.getPersistance().getGalleriesByDataCrossWidgetIds(crossWidgetId);
            if (!galleries.isEmpty()) {
                gallery = (Gallery) new ItemManager(galleries.get(0)).getItem(siteShowOption);
            }
        }

        // If we still can't find gallery for some reasons (and currently it's a possible situation),
        // then let's display nothing on the page. 
        if (gallery == null) {
            return "";
        }

        /*-----------------------------------------------Getting gallery----------------------------------------------*/
        final GalleryData galleryData = GalleryData.newInstance(gallery, context, widgetItem, siteShowOption);
        return new ShowGalleryUtils(siteShowOption).createDataHtml(galleryData, widgetItem, context);
    }

    private String renderWidgetPurchaseHistory(Widget widget, final Item item, final RenderContext context) throws IOException, ServletException {
        final PurchaseHistory purchaseHistory = (PurchaseHistory) item;
        return execute(
                "/site/render/renderWidgetPurchaseHistory.jsp?" + (context.getParameterMap().get(ItemType.PURCHASE_HISTORY) != null ? context.getParameterMap().get(ItemType.PURCHASE_HISTORY) : ""), context,
                new RenderParameter("widget", widget),
                new RenderParameter("siteShowOption", siteShowOption),
                new RenderParameter("purchaseHistory", purchaseHistory));
    }

    private String renderSlideShow(Widget widget, final Item item, final RenderContext context) throws IOException, ServletException {
        final SlideShow slideShow = (SlideShow) item;
        return execute(
                "/site/render/renderSlideShow.jsp?" + (context.getParameterMap().get(ItemType.SLIDE_SHOW) != null ? context.getParameterMap().get(ItemType.SLIDE_SHOW) : ""), context,
                new RenderParameter("widget", widget),
                new RenderParameter("siteShowOption", siteShowOption),
                new RenderParameter("slideShow", slideShow));
    }

    private String renderWidgetManageVotes(Widget widget, final Item item, final RenderContext context) throws IOException, ServletException {
        final ManageVotes manageVotes = (ManageVotes) item;
        return execute(
                "/site/render/manageVotes/renderWidgetManageVotes.jsp?" + (context.getParameterMap().get(ItemType.MANAGE_VOTES) != null ? context.getParameterMap().get(ItemType.MANAGE_VOTES) : ""), context,
                new RenderParameter("widget", widget),
                new RenderParameter("siteShowOption", siteShowOption),
                new RenderParameter("manageVotes", manageVotes));
    }

    private String executeWidgetMenu(final Item item, final RenderContext context) throws IOException, ServletException {
        final Menu menu = (Menu) item;
        final Integer pageId = (pageManager != null && pageManager.getPage() != null) ? pageManager.getPage().getPageId() : -1;
        final MenuItemDataManager menuItemManager = new MenuItemDataManager(menu, siteShowOption, pageId);

        return execute("/site/render/menu/renderWidgetMenu.jsp", context,
                new RenderParameter("menuItemManager", menuItemManager),
                new RenderParameter("showFromSiteEditPage", context.isShowFromSiteEditPage()),
                new RenderParameter("menu", menu));
    }

    private String executeWidgetBlog(final int widgetId, final Item item, final RenderContext context)
            throws IOException, ServletException {
        return execute(
                "/blog/showBlogPosts.action?blogId=" + item.getId() +
                        "&widgetBlogId=" + widgetId +
                        "&pageId=" + pageManager.getPageId() +
                        "&siteShowOption=" + siteShowOption, context);
    }

    private String executeWidgetBlogSummary(final Item item, final RenderContext context) throws IOException,
            ServletException {
        final BlogSummary blogSummary = (BlogSummary) item;

        final List<BlogSummaryDataForPreview> blogSummaryPreviewData =
                BlogSummaryDataForPreviewCreator.create(blogSummary, pageManager, siteShowOption);

        return execute(
                "/site/render/renderWidgetBlogSummary.jsp", context,
                new RenderParameter("blogsWithWidgets", blogSummaryPreviewData),
                new RenderParameter("blogSummary", blogSummary));
    }

    private String renderWidgetCustomForm(int widgetId, final Item item, RenderContext context) throws IOException, ServletException {
        return execute(
                "/site/render/renderWidgetCustomForm.jsp?" + (context.getParameterMap().get(ItemType.CUSTOM_FORM) != null ? context.getParameterMap().get(ItemType.CUSTOM_FORM) : ""), context,
                new RenderParameter("widgetId", widgetId),
                new RenderParameter("form", new FormData((Form) item)),
                new RenderParameter("siteId", item.getSiteId()));
    }

    private String renderWidgetRegistration(int widgetId, RenderContext context) throws IOException, ServletException {
        return execute("/site/widgetRegistration.action?widgetId=" + widgetId + (context.getParameterMap().get(ItemType.REGISTRATION) != null ? context.getParameterMap().get(ItemType.REGISTRATION) : ""), context);
    }

    public String renderWidgetTellFriend(int widgetId, final int siteId, final boolean sendEmails, final RenderContext context) throws IOException, ServletException {
        return execute("/site/render/tellFriend.jsp", context,
                new RenderParameter("widgetId", widgetId),
                new RenderParameter("siteId", siteId),
                new RenderParameter("sendEmails", sendEmails),
                new RenderParameter("siteShowOption", siteShowOption));
    }

    private String renderAdminLogin(final Item item, final RenderContext context)
            throws IOException, ServletException {
        final String applicationUrl = ServiceLocator.getConfigStorage().get().getApplicationUrl();
        UserManager userManager = null;
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            // None
        }
        return execute("/site/render/renderAdminLogin.jsp", context,
                new RenderParameter("userManager", userManager),
                new RenderParameter("adminLogin", item),
                new RenderParameter("applicationUrl", applicationUrl));
    }

    private String renderScript(final Item item, final RenderContext context)
            throws IOException, ServletException {
        final Script script = (Script) item;
        final String header = (StringUtil.isNullOrEmpty(script.getDescription()) || !script.isShowDescription()) ? "" : ("<div class='scriptsHeader'>" + script.getDescription() + "</div>");
        return header + StringUtil.getEmptyOrString(script.getText());
    }

    private String renderWidgetForum(Widget widget, final Item item, RenderContext context) throws IOException, ServletException {
        final String forumParameters =
                ForumDispatchHelper.extractForumDispatchParameters(context.getRequest().getParameterMap());

        return execute(
                "/forum/widgetForum.action?forumId=" + item.getId() +
                        "&siteShowOption=" + siteShowOption +
                        "&widgetId=" + widget.getWidgetId() + "&isShowOnUserPages=" + siteShowOption.equals(SiteShowOption.INSIDE_APP) +
                        (context.getParameterMap().get(ItemType.FORUM) != null ? context.getParameterMap().get(ItemType.FORUM) : "") +
                        forumParameters, context);
    }

    private String executeWidgetText(final Item item, final boolean systemPage, final RenderContext context) throws IOException, ServletException {
        final String text;
        if (systemPage && new UsersManager().getLoginedUser() != null) {
            final International international = ServiceLocator.getInternationStorage().get("systemPageTexts", Locale.US);
            text = international.get("notEnoughPrivileges");
        } else {
            text = ServiceLocator.getCustomTagFacade().internalToHtml(((Text) item).getText(), siteShowOption);
        }
        return execute("/site/render/renderWidgetText.jsp", context, new RenderParameter("widgetText",
                HtmlUtil.ignoreWordIf(text)));
    }

    private String executeWidgetVideo(final int widgetId, final Item item, final RenderContext context)
            throws IOException, ServletException {
        final Video1 video1 = (Video1) item;
        final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
        final String imageUrl = video1.getImageId() != null ? resourceGetterUrl.get(ResourceGetterType.IMAGE_FOR_VIDEO, video1.getImageId(), 0, 0, 0, false) : "";
        final int imageWidth = video1.getImageWidth() != null ? video1.getImageWidth() : -1;
        final int imageHeight = video1.getImageHeight() != null ? video1.getImageHeight() : -1;
        final Persistance persistance = ServiceLocator.getPersistance();
        final FlvVideo normalFlvVideo = persistance.getFlvVideo(video1.getFlvVideoId());
        final String normalVideoStatus = new FlvVideoManager(normalFlvVideo).getFlvVideoStatusAndStartNewConversionIfNeeded();
        String videoUrl = HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(normalFlvVideo));
        String largeVideoUrl = "";
        String largeVideoStatus = "";
        if (video1.getLargeFlvVideoId() != null) {
            FlvVideo largeFlv = persistance.getFlvVideo(video1.getLargeFlvVideoId());
            largeVideoUrl = HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(largeFlv));
            largeVideoStatus = new FlvVideoManager(largeFlv).getFlvVideoStatusAndStartNewConversionIfNeeded();
        }

        String smallVideoUrl = "";
        String smallVideoStatus = "";
        if (video1.getSmallFlvVideoId() != null) {
            FlvVideo smallFlv = persistance.getFlvVideo(video1.getSmallFlvVideoId());
            smallVideoUrl = HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(smallFlv));
            smallVideoStatus = new FlvVideoManager(smallFlv).getFlvVideoStatusAndStartNewConversionIfNeeded();
        }
        return execute(
                "/site/render/renderWidgetVideo.jsp", context,
                new RenderParameter("widgetId", widgetId),
                new RenderParameter("videoFlvId", video1.getFlvVideoId()),
                new RenderParameter("largeVideoFlvId", video1.getLargeFlvVideoId()),
                new RenderParameter("smallVideoFlvId", video1.getSmallFlvVideoId()),
                new RenderParameter("widgetVideoUrl", videoUrl),
                new RenderParameter("widgetLargeVideoUrl", largeVideoUrl),
                new RenderParameter("widgetSmallVideoUrl", smallVideoUrl),
                new RenderParameter("widgetVideoImageUrl", imageUrl),
                new RenderParameter("widgetVideoWidth", normalFlvVideo.getWidth()),
                new RenderParameter("widgetVideoHeight", normalFlvVideo.getHeight()),
                new RenderParameter("widgetVideoSmallSize", video1.getVideoSmallSize()),
                new RenderParameter("widgetVideoLargeSize", video1.getVideoLargeSize()),
                new RenderParameter("widgetVideoShowDescription", (video1.isIncludeDescription() && !StringUtil.getEmptyOrString(video1.getDescription()).replace("\n", "").trim().isEmpty())),
                new RenderParameter("widgetVideoDescription", StringUtil.getEmptyOrString(video1.getDescription())),
                new RenderParameter("widgetPlayInCurrentPage", video1.isPlayInCurrentPage()),
                new RenderParameter("imageWidth", imageWidth),
                new RenderParameter("imageHeight", imageHeight),
                new RenderParameter("widgetDisplaySmallOptions", video1.isDisplaySmallOptions()),
                new RenderParameter("widgetDisplayLargeOptions", video1.isDisplayLargeOptions()),
                new RenderParameter("normalVideoStatus", normalVideoStatus),
                new RenderParameter("largeVideoStatus", largeVideoStatus),
                new RenderParameter("smallVideoStatus", smallVideoStatus));
    }

    private String executeWidgetContactUs(final int widgetId, final Item item, final RenderContext context)
            throws IOException, ServletException {
        final Site site = ServiceLocator.getPersistance().getSite(item.getSiteId());
        final String siteName = site != null ? site.getTitle() : "";
        return execute(
                "/site/render/renderWidgetContactUs.jsp?" + (context.getParameterMap().get(ItemType.CONTACT_US) != null ? context.getParameterMap().get(ItemType.CONTACT_US) : ""), context,
                new RenderParameter("widgetId", widgetId),
                new RenderParameter("form", new FormData((Form) item)),
                new RenderParameter("siteName", siteName),
                new RenderParameter("siteShowOption", siteShowOption),
                new RenderParameter("siteId", item.getSiteId()));
    }

    private String executeImage(final Item item, final RenderContext context)
            throws IOException, ServletException {
        final Image1 image1 = (Image1) item;
        final String src = resourceGetterUrl.get(
                ResourceGetterType.WIDGET_IMAGE, image1.getId(), 0, 0, image1.getVersion(), false);
        String overSrc = src;
        if (image1.getRollOverExtension() != null) {
            overSrc = resourceGetterUrl.get(ResourceGetterType.WIDGET_IMAGE_ROLLOVER,
                    image1.getId(), 0, 0, image1.getVersion(), false);
        }

        String onMouseOverText = "";
        boolean showDescription = image1.getDescription() != null;
        if (image1.isShowDescriptionOnMouseOver()) {
            onMouseOverText = StringUtil.getEmptyOrString(image1.getDescription());
        } else {
            onMouseOverText = StringUtil.getEmptyOrString(image1.getOnMouseOverText());
        }

        final boolean showBelowTitle = image1.getTitle() != null
                && image1.getTitlePosition() == TitlePosition.BELOW;
        final boolean showUpTitle = image1.getTitle() != null
                && image1.getTitlePosition() == TitlePosition.ABOVE;
        ImageUrl imageUrl = createWidgetImageUrl(image1);
        final String url = imageUrl.getUrl();
        final String title = createWidgetImageTitle(image1, imageUrl);
        final boolean showStartStopAudioIcon = isShowStartStopAudioIcon(image1, ImageFileType.AUDIO);
        final boolean showStartStopFlashIcon = isShowStartStopAudioIcon(image1, ImageFileType.FLASH);
        return execute(
                "/site/render/renderWidgetImage.jsp", context,
                new RenderParameter(
                        "widgetImageUrl", image1.isImageIsALinnk() ? url : ""),
                new RenderParameter("isBlank", imageUrl.isBlank()),
                new RenderParameter("widgetImageRolloverThumbnailUrl", overSrc),
                new RenderParameter("margin", image1.getMargin()),
                new RenderParameter("thumbnailWidth", image1.getThumbnailWidth()),
                new RenderParameter("thumbnailHeight", image1.getThumbnailHeight()),
                new RenderParameter("showStartStopAudioIcon", showStartStopAudioIcon),
                new RenderParameter("showStartStopFlashIcon", showStartStopFlashIcon),
                new RenderParameter("widgetImageTitle", title),
                new RenderParameter("widgetImageThumbnailUrl", src),
                new RenderParameter("widgetImageAlt", onMouseOverText),
                new RenderParameter("widgetImageDescription", image1.getDescription()),
                new RenderParameter("widgetImageShowDescription", showDescription),
                new RenderParameter("widgetImageShowBelowTitle", showBelowTitle),
                new RenderParameter("widgetImageShowUpTitle", showUpTitle),
                new RenderParameter("widgetImageAlign", image1.getAligment()),
                new RenderParameter("id", image1.getId()),
                new RenderParameter("showFromSiteEditPage", context.isShowFromSiteEditPage())
        );
    }

    private boolean isShowStartStopAudioIcon(final Image1 image1, final ImageFileType type) {
        if (image1 != null) {
            if (image1.getImageLinkType() == ImageLinkType.MEDIA_FILE) {
                if (image1.getImageFileType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    private ImageUrl createWidgetImageUrl(final Image1 image1) {
        ImageUrl imageUrl = new ImageUrl();
        final Persistance persistance = ServiceLocator.getPersistance();
        String url = "";
        if (image1.isImageIsALinnk() || image1.isLabelIsALinnk()) {
            switch (image1.getImageLinkType()) {
                case EXTERNAL_URL: {
                    String externalUrl = StringUtil.getEmptyOrString(image1.getExternalUrl());
                    url += (externalUrl.startsWith("http://") || externalUrl.startsWith("https://") || externalUrl.startsWith("ftp://")) ? "" : "http://";
                    url += externalUrl;
                    if (image1.getExternalUrlDisplaySettings().equals(ExternalUrlDisplaySettings.OPEN_IN_NEW_WINDOW)) {
                        imageUrl.setBlank(true);
                    }
                    break;
                }
                case INTERNAL_URL: {
                    final PageManager pageManager = new PageManager(persistance.getPage(image1.getInternalPageId()), siteShowOption);
                    url += pageManager.getUrl();
                    if (image1.getInternalPageDisplaySettings().equals(InternalPageDisplaySettings.OPEN_IN_NEW_WINDOW)) {
                        imageUrl.setBlank(true);
                    }
                    break;
                }
                case TEXT_AREA: {
                    if (image1.getTextAreaDisplaySettings().equals(TextAreaDisplaySettings.OPEN_IN_NEW_WINDOW)) {
                        url += "javascript:showWidgetImageTextInNewWindow(\"/site/showImageText.action?imageId=" + image1.getId() + "&siteShowOption=" + siteShowOption.toString() + "\");";
                        break;
                    } else if (image1.getTextAreaDisplaySettings().equals(TextAreaDisplaySettings.OPEN_IN_SAME_WINDOW)) {
                        url += "javascript:showWidgetImageTextInSameWindow(\"/site/showImageText.action?imageId=" + image1.getId() + "&siteShowOption=" + siteShowOption.toString() + "\");";
                        break;
                    } else {
                        int windowWidth, windowHeight;
                        if (image1.isCustomizeWindowSize() &&
                                (image1.getNewWindowWidth() > 0 && image1.getNewWindowHeight() > 0)) {
                            windowWidth = image1.getNewWindowWidth();
                            windowHeight = image1.getNewWindowHeight();
                        } else {
                            windowWidth = 0;
                            windowHeight = 0;
                        }
                        url += "javascript:showWidgetImageTextInSmallWindow(" + image1.getId() + "," + windowWidth + "," + windowHeight + ",\"" + siteShowOption.toString() + "\");";
                        break;
                    }
                }
                case MEDIA_FILE: {
                    switch (image1.getImageFileType()) {
                        case IMAGE: {
                            return createImageFlashUrl(image1);
                        }
                        case PDF: {
                            final String imageFileUrl = resourceGetterUrl.get(
                                    ResourceGetterType.IMAGE_FILE, image1.getImageFileId(), 0, 0, image1.getVersion(), false);
                            if (image1.getImagePdfDisplaySettings().equals(ImagePdfDisplaySettings.OPEN_IN_NEW_WINDOW)) {
                                url += "" + imageFileUrl + "";
                                imageUrl.setBlank(true);
                                break;
                            } else if (image1.getImagePdfDisplaySettings().equals(ImagePdfDisplaySettings.OPEN_IN_SAME_WINDOW)) {
                                url += "" + imageFileUrl + "";
                                break;
                            } else {
                                url += "" + resourceGetterUrl.get(
                                        ResourceGetterType.IMAGE_FILE, image1.getImageFileId(), 0, 0, 0, true) + "";
                                break;
                            }
                        }
                        case FLASH: {
                            return createImageFlashUrl(image1);
                        }
                        case CAD: {
                            url += "" + resourceGetterUrl.get(
                                    ResourceGetterType.IMAGE_FILE, image1.getImageFileId(), 0, 0, 0, true) + "";
                            break;
                        }
                        case AUDIO: {
                            if (image1.getImageAudioDisplaySettings().equals(ImageAudioDisplaySettings.PLAY_IN_SMALL_WINDOW)) {
                                url += "javascript:showSmallWindowForAudio(" + image1.getId() + ", 270, 16, \"" + siteShowOption.toString() + "\");";
                                break;
                            } else {
                                url += "javascript:playMusic(" + image1.getId() + ", \"mp3playerDiv\", 1, 1, \"" + siteShowOption.toString() + "\");";
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            imageUrl.setUrl(url);
            return imageUrl;
        } else {
            imageUrl.setUrl(url);
            return imageUrl;
        }
    }


    private ImageUrl createImageFlashUrl(final Image1 image1) {
        ImageUrl imageUrl = new ImageUrl();
        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        final Persistance persistance = ServiceLocator.getPersistance();
        final ImageFile imageFile = persistance.getImageFileById(image1.getImageFileId());
        String url = "";
        final String imageFileUrl = resourceGetterUrl.get(
                ResourceGetterType.IMAGE_FILE, image1.getImageFileId(), 0, 0, image1.getVersion(), false);
        if (image1.getImageFlashDisplaySettings().equals(ImageFlashDisplaySettings.OPEN_IN_NEW_WINDOW)) {
            url += "" + imageFileUrl + "";
            imageUrl.setBlank(true);
        } else {
            if (image1.getImageFlashDisplaySettings().equals(ImageFlashDisplaySettings.OPEN_IN_SAME_WINDOW)) {
                url += "" + imageFileUrl + "";
            } else {
                if (image1.getImageFlashDisplaySettings().equals(ImageFlashDisplaySettings.OPEN_IN_SMALL_WINDOW)) {
                    int windowWidth, windowHeight, itemWidth, itemHeight;
                    if (image1.getImageFileType().equals(ImageFileType.IMAGE)) {
                        final BufferedImage image = fileSystem.getResource(imageFile);
                        itemWidth = image == null ? 600 : image.getWidth();
                        itemHeight = image == null ? 400 : image.getHeight();
                    } else {
                        itemWidth = 600;
                        itemHeight = 400;
                    }
                    if (image1.isCustomizeWindowSize() &&
                            (image1.getNewWindowWidth() > 0 && image1.getNewWindowHeight() > 0)) {
                        windowWidth = image1.getNewWindowWidth();
                        windowHeight = image1.getNewWindowHeight();
                        if (image1.getImageFileType().equals(ImageFileType.FLASH)) {
                            itemWidth = windowWidth;
                            itemHeight = windowHeight;
                        }
                    } else {
                        windowWidth = itemWidth;
                        windowHeight = itemHeight;
                    }
                    url += "javascript:showWidgetImageInSmallWindow(" + image1.getId() + "," +
                            windowWidth + "," + windowHeight + ", " + itemWidth + "," + itemHeight + "," +
                            image1.getImageFileType().equals(ImageFileType.FLASH) +
                            ",\"" + siteShowOption.toString() + "\");";
                } else {
                    url += "" + resourceGetterUrl.get(
                            ResourceGetterType.IMAGE_FILE, image1.getImageFileId(), 0, 0, 0, true) + "";
                }
            }
        }
        imageUrl.setUrl(url);
        return imageUrl;
    }

    private static String createWidgetImageTitle(final Image1 image1, final ImageUrl imageUrl) {
        String tempTitle = StringUtil.getEmptyOrString(image1.getTitle());
        if (!tempTitle.isEmpty() && image1.isLabelIsALinnk()) {
            return "<a href='" + imageUrl.getUrl() + "'" + (imageUrl.isBlank() ? "target='_blank'" : "") + ">" + tempTitle + "</a>";
        }
        return tempTitle;
    }

    private static String execute(
            final String renderWidgetUrl, final RenderContext context,
            final RenderParameter... parameters) throws IOException, ServletException {
        for (final RenderParameter parameter : parameters) {
            context.getRequest().setAttribute(parameter.getName(), parameter.getValue());
        }
        return ServiceLocator.getHtmlGetter().get(renderWidgetUrl,
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;
    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
    private static final Logger logger = Logger.getLogger(RenderWidgets.class.getName());

}
