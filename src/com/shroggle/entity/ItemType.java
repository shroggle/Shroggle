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
package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@DataTransferObject(converter = EnumConverter.class)
public enum ItemType {

    BLOG(DraftBlog.class, "blogs"),
    FORUM(DraftForum.class, "forums"),
    REGISTRATION(DraftRegistrationForm.class, "registrationForms"),
    CUSTOM_FORM(DraftCustomForm.class, "customForms"),
    CHILD_SITE_REGISTRATION(DraftChildSiteRegistration.class, "childSiteRegistrations"),
    BLOG_SUMMARY(DraftBlogSummary.class, "blogSummaries"),
    MENU(DraftMenu.class, "menus"),
    CONTACT_US(DraftContactUs.class, "contactUs"),
    GALLERY(DraftGallery.class, "galleries"),
    ADVANCED_SEARCH(DraftAdvancedSearch.class, "advancedSearches"),
    MANAGE_VOTES(DraftManageVotes.class, "manageVotes"),
    IMAGE(DraftImage.class, "images1"),
    TELL_FRIEND(TellFriend.class, "tellFriends"),
    LOGIN(DraftLogin.class, "draftLogins"),
    TEXT(DraftText.class, "texts"),
    ADMIN_LOGIN(DraftAdminLogin.class, "adminLogins"),
    VIDEO(DraftVideo.class, "videos1"),
    GALLERY_DATA(DraftGalleryData.class, "draftGalleryDatas"),
    SHOPPING_CART(DraftShoppingCart.class, "draftShoppingCarts"),
    PURCHASE_HISTORY(DraftPurchaseHistory.class, "draftPurchaseHistory"),
    SCRIPT(DraftScript.class, "draftScripts"),
    TAX_RATES(DraftTaxRatesUS.class, "draftTaxRates"),
    SLIDE_SHOW(DraftSlideShow.class, "slideShows"),

    VOTING(DraftGallery.class, "galleries"),
    E_COMMERCE_STORE(DraftGallery.class, "galleries"),
    ORDER_FORM(DraftCustomForm.class, "customForms"), /* This is type of custom form, do not add new table it will complicate things. */
    COMPOSIT(null, "widgetComposits"),
    ALL_FORMS(null, "forms"),
    ALL_ITEMS(null, "siteItems");

    ItemType(final Class itemClass, final String tableName) {
        this.itemClass = itemClass;
        this.tableName = tableName;
    }

    public boolean isShareable() {
        return true;
    }

    public boolean isShowOnDashboard() {
        return this == BLOG || this == FORUM ||
                this == REGISTRATION || this == CHILD_SITE_REGISTRATION || this == CONTACT_US || this == CUSTOM_FORM ||
                this == GALLERY || this == MANAGE_VOTES || this == ADVANCED_SEARCH || this == SHOPPING_CART ||
                this == TAX_RATES || this == PURCHASE_HISTORY;
    }

    public boolean isFormType() {
        return this == CUSTOM_FORM || this == CHILD_SITE_REGISTRATION || this == REGISTRATION || this == CONTACT_US;
    }

    public boolean isExtendedSettingsWindow() {
        return isFormType() || this == MANAGE_VOTES || this == GALLERY;
    }

    public Class getItemClass() {
        return itemClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getGroupName() {
        if (isFormType()) return "Forms & Records";
        else if (this == GALLERY) return "Galleries";
        else return this == BLOG ? "Blogs" : "Forums";
    }

    public static List<ItemType> getDraftItemsTypeWithoutDuplicates() {
        final List<ItemType> itemTypes = new ArrayList<ItemType>();
        final List<Class> classes = new ArrayList<Class>();
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getItemClass() == null) {
                continue;
            }
            if (!classes.contains(itemType.getItemClass())) {
                itemTypes.add(itemType);
                classes.add(itemType.getItemClass());
            }
        }
        return itemTypes;
    }

    public static List<ItemType> getFormItems() {
        return Arrays.asList(CHILD_SITE_REGISTRATION, CONTACT_US, CUSTOM_FORM, REGISTRATION);
    }

    public static ItemType[] getItemTypeForAddPage() {
        return new ItemType[]{
                ItemType.REGISTRATION, ItemType.ADMIN_LOGIN, ItemType.ADVANCED_SEARCH, ItemType.BLOG, ItemType.BLOG_SUMMARY, ItemType.CONTACT_US,
                ItemType.CHILD_SITE_REGISTRATION, ItemType.E_COMMERCE_STORE, ItemType.FORUM, ItemType.CUSTOM_FORM, ItemType.GALLERY, ItemType.IMAGE, ItemType.LOGIN, ItemType.MANAGE_VOTES,
                ItemType.MENU, ItemType.SHOPPING_CART, ItemType.TEXT, ItemType.TELL_FRIEND, ItemType.VIDEO, ItemType.VOTING, ItemType.PURCHASE_HISTORY, ItemType.SCRIPT, ItemType.SLIDE_SHOW
        };
    }

    public static List<ItemType> getItemsForDashboardMenu() {
        return Arrays.asList(ALL_ITEMS, BLOG, FORUM, ALL_FORMS, GALLERY);
    }

    private final Class itemClass;
    private final String tableName;

}
