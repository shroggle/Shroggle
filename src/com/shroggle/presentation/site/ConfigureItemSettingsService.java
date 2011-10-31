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
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.advancedSearch.ConfigureAdvancedSearchService;
import com.shroggle.presentation.blog.ConfigureBlogService;
import com.shroggle.presentation.blogSummary.ConfigureBlogSummaryService;
import com.shroggle.presentation.customForm.ConfigureCustomFormService;
import com.shroggle.presentation.gallery.ConfigureGalleryDataService;
import com.shroggle.presentation.gallery.ConfigureGalleryService;
import com.shroggle.presentation.image.ConfigureImageService;
import com.shroggle.presentation.manageVotes.ConfigureManageVotesService;
import com.shroggle.presentation.menu.ConfigureMenuService;
import com.shroggle.presentation.purchaseHistory.ConfigurePurchaseHistoryService;
import com.shroggle.presentation.registration.ConfigureRegistrationService;
import com.shroggle.presentation.shoppingCart.ConfigureShoppingCartService;
import com.shroggle.presentation.site.accessibilityForRender.ConfigureAccessibleSettingsService;
import com.shroggle.presentation.site.borderBackground.ConfigureBackgroundService;
import com.shroggle.presentation.site.borderBackground.ConfigureBorderService;
import com.shroggle.presentation.site.cssParameter.ConfigureFontsAndColorsService;
import com.shroggle.presentation.slideShow.ConfigureSlideShowService;
import com.shroggle.presentation.tellFriend.ConfigureTellFriendService;
import com.shroggle.presentation.video.ConfigureVideoService;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureItemSettingsService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public ConfigureItemSettingsResponse execute(final Integer widgetId, final Integer itemId,
                                                 final ConfigureItemSettingsTab tab, final ItemType itemType,
                                                 final boolean showSeparateTab) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final ConfigureItemSettingsResponse response = new ConfigureItemSettingsResponse();

        this.tab = tab;
        this.itemType = itemType;
        this.widgetId = widgetId;


        final ConfigureItemData itemData =
                new ItemsManager().getConfigureItemData(widgetId, itemId);
        this.item = itemData.getDraftItem();

        site = itemData.getSite();

        siteOnItemRightType = userManager.getRight().toSiteItem(itemData.getDraftItem(), itemData.getSite());

        if (itemType == ItemType.FORUM) {
            forumService.execute(widgetId, itemId);
        } else if (itemType == ItemType.BLOG) {
            blogService.execute(widgetId, itemId);
        } else if (itemType == ItemType.REGISTRATION) {
            registrationService.show(widgetId, itemId);
        } else if (itemType == ItemType.CUSTOM_FORM) {
            customFormService.show(widgetId, itemId);
        } else if (itemType == ItemType.CONTACT_US) {
            contactUsService.execute(widgetId, itemId);
        } else if (itemType == ItemType.CHILD_SITE_REGISTRATION) {
            childSiteRegistrationService.execute(widgetId, itemId);
        } else if (itemType == ItemType.ADVANCED_SEARCH) {
            advancedSearchService.show(widgetId, itemId);
        } else if (itemType == ItemType.MANAGE_VOTES) {
            manageVotesService.execute(widgetId, itemId);
        } else if (itemType == ItemType.BLOG_SUMMARY) {
            blogSummaryService.execute(widgetId, itemId);
        } else if (itemType == ItemType.PURCHASE_HISTORY) {
            purchaseHistoryService.execute(widgetId, itemId);
        } else if (itemType == ItemType.SHOPPING_CART) {
            shoppingCartService.execute(widgetId, itemId);
        } else if (itemType == ItemType.TELL_FRIEND) {
            tellFriendService.execute(widgetId, itemId);
        } else if (itemType == ItemType.ADMIN_LOGIN) {
            adminLoginService.execute(widgetId, itemId);
        } else if (itemType == ItemType.MENU) {
            menuService.execute(widgetId, itemId);
        } else if (itemType == ItemType.IMAGE) {
            imageService.execute(widgetId, itemId);
        } else if (itemType == ItemType.TEXT) {
            textService.execute(widgetId, itemId);
        } else if (itemType == ItemType.SCRIPT) {
            scriptService.execute(widgetId, itemId);
        } else if (itemType == ItemType.GALLERY) {
            response.setConfigureGalleryResponse(galleryService.execute(widgetId, itemId));
        } else if (itemType == ItemType.VIDEO) {
            videoService.execute(widgetId, itemId);
        } else if (itemType == ItemType.SLIDE_SHOW) {
            slideShowService.execute(widgetId, itemId);
        } else if (itemType == ItemType.LOGIN) {
            // Do nothing.
        } else if (itemType == ItemType.GALLERY_DATA) {
            if (widgetId == null) {
                throw new IllegalArgumentException("Gallery data window should be shown only from site edit page.");
            }

            galleryDataService.execute(widgetId);
        } else {
            throw new IllegalArgumentException("Unknown itemType.");
        }

        if (tab == ConfigureItemSettingsTab.FONTS_COLORS) {
            fontsColorsService.execute(widgetId, itemId);
        } else if (tab == ConfigureItemSettingsTab.BORDER) {
            borderService.execute(widgetId, itemId, null);
        } else if (tab == ConfigureItemSettingsTab.BACKGROUND) {
            backgroundService.execute(widgetId, itemId, null, false);
        } else if (tab == ConfigureItemSettingsTab.ITEM_SIZE) {
            itemSizeService.execute(widgetId, itemId);
        } else if (tab == ConfigureItemSettingsTab.ACCESSIBLE) {
            if (widgetId != null) {
                accessibleService.execute(widgetId, AccessibleElementType.WIDGET, false);
            } else {
                accessibleService.execute(itemId, AccessibleElementType.ITEM, false);
            }
        } else if (tab == ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS) {
            blueprintItemPermissionsService.execute(widgetId);
        }

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("itemSettingsService", this);

        if (showSeparateTab) {
            response.setHtml(webContext.forwardToString("/site/configureItemSettingsSeparateTab.jsp"));
        } else {
            response.setHtml(webContext.forwardToString("/site/configureItemSettings.jsp"));
        }
        return response;
    }

    public ConfigureItemSettingsTab getTab() {
        return tab;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public SiteOnItemRightType getSiteOnItemRightType() {
        return siteOnItemRightType;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public DraftItem getItem() {
        return item;
    }

    public Site getSite() {
        return site;
    }

    private ConfigureItemSettingsTab tab;
    private ItemType itemType;
    private SiteOnItemRightType siteOnItemRightType;
    private Integer widgetId;
    private Site site;
    private DraftItem item;

    private final ConfigureForumService forumService = new ConfigureForumService();
    private final ConfigureBlogService blogService = new ConfigureBlogService();
    private final ConfigureRegistrationService registrationService = new ConfigureRegistrationService();
    private final ConfigureCustomFormService customFormService = new ConfigureCustomFormService();
    private final ConfigureContactUsService contactUsService = new ConfigureContactUsService();
    private final ConfigureChildSiteRegistrationService childSiteRegistrationService =
            new ConfigureChildSiteRegistrationService();
    private final ConfigureAdvancedSearchService advancedSearchService = new ConfigureAdvancedSearchService();
    private final ConfigureManageVotesService manageVotesService = new ConfigureManageVotesService();
    private final ConfigureBlogSummaryService blogSummaryService = new ConfigureBlogSummaryService();
    private final ConfigurePurchaseHistoryService purchaseHistoryService = new ConfigurePurchaseHistoryService();
    private final ConfigureShoppingCartService shoppingCartService = new ConfigureShoppingCartService();
    private final ConfigureTellFriendService tellFriendService = new ConfigureTellFriendService();
    private final ConfigureAdminLoginService adminLoginService = new ConfigureAdminLoginService();
    private final ConfigureMenuService menuService = new ConfigureMenuService();
    private final ConfigureImageService imageService = new ConfigureImageService();
    private final ConfigureTextService textService = new ConfigureTextService();
    private final ConfigureScriptService scriptService = new ConfigureScriptService();
    private final ConfigureGalleryService galleryService = new ConfigureGalleryService();
    private final ConfigureVideoService videoService = new ConfigureVideoService();
    private final ConfigureGalleryDataService galleryDataService = new ConfigureGalleryDataService();
    private final ConfigureSlideShowService slideShowService = new ConfigureSlideShowService();

    private final ConfigureFontsAndColorsService fontsColorsService = new ConfigureFontsAndColorsService();
    private final ConfigureBorderService borderService = new ConfigureBorderService();
    private final ConfigureBackgroundService backgroundService = new ConfigureBackgroundService();
    private final ConfigureItemSizeService itemSizeService = new ConfigureItemSizeService();
    private final ConfigureAccessibleSettingsService accessibleService = new ConfigureAccessibleSettingsService();
    private final ConfigureBlueprintItemPermissionsService blueprintItemPermissionsService =
            new ConfigureBlueprintItemPermissionsService();

}

