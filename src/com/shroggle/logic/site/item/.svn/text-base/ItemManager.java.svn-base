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
package com.shroggle.logic.site.item;

import com.shroggle.entity.*;
import com.shroggle.logic.SiteOnItemAsOwnerManager;
import com.shroggle.logic.SiteOnItemAsRightManager;
import com.shroggle.logic.SiteOnItemManager;
import com.shroggle.logic.fontsAndColors.FontsAndColorsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.site.cssParameter.CreateFontsAndColorsRequest;
import com.shroggle.util.StringUtil;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy, Artem Stasuk
 */
public class ItemManager implements AccessibleForRender {
    /*--------------------------------------------------Constructors--------------------------------------------------*/

    public ItemManager(DraftItem item) {
        this.item = item;
    }

    public ItemManager(Integer itemId) {
        this.item = persistance.getDraftItem(itemId);
    }
    /*--------------------------------------------------Constructors--------------------------------------------------*/

    public DraftItem getDraftItem() {
        return item;
    }

    public Item getWorkItem() {
        final Integer itemId = item != null ? item.getId() : null;
        return persistance.getWorkItem(itemId);// Work item has the same id as draft one.
    }

    public void removeWorkItem() {
        final WorkItem workItem = (WorkItem) getWorkItem();
        if (workItem != null) {
            persistance.removeWorkItem(workItem);
        }
    }

    public Item getItem(final SiteShowOption siteShowOption) {
        if (siteShowOption.isDraft()) {
            return getDraftItem();
        } else {
            return getWorkItem();
        }
    }

    public static String getTitle(final Item item) {
        if (item instanceof Text) {
            final Text text = (Text) item;
            if (!StringUtil.isNullOrEmpty(text.getName())) {
                return text.getName();
            } else if (!StringUtil.isNullOrEmpty(text.getText())) {
                return text.getText();
            }
            return "text undefined";
        }

        if (item instanceof Script) {
            if (StringUtil.isNullOrEmpty(item.getName())) {
                return "script";
            }
        }

        return item.getName();
    }

    public ItemType getItemType() {
        return item.getItemType();
    }

    public ItemType getItemTypeConsiderGalleryType() {
        if (getItemType() == ItemType.GALLERY) {
            final DraftGallery gallery = (DraftGallery) getDraftItem();
            if (gallery.isIncludeECommerce()) {
                return ItemType.E_COMMERCE_STORE;
            } else if (gallery.isIncludesVotingModule()) {
                return ItemType.VOTING;
            }
        }
        return getItemType();
    }


    public static List<ItemManager> siteItemsToManagers(final List<DraftItem> siteItems) {
        final List<ItemManager> siteItemManagers = new ArrayList<ItemManager>(siteItems.size());
        for (final DraftItem siteItem : siteItems) {
            siteItemManagers.add(new ItemManager(siteItem));
        }
        return siteItemManagers;
    }
    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    public ItemSize getItemSize(final SiteShowOption siteShowOption) {
        final Item item = getItem(siteShowOption);
        if (item != null) {
            final ItemSize itemSize = persistance.getItemSize(item.getItemSizeId());
            if (itemSize != null) {
                return itemSize;
            }
        }
        return new ItemSize();
    }

    public void setItemSize(final ItemSize itemSize) {
        final DraftItem item = getDraftItem();
        if (item != null) {
            item.setItemSizeId(itemSize.getId());
        }
    }

    public void updateItemSize(final ItemSize tempItemSize) {
        final DraftItem item = getDraftItem();
        if (item != null) {
            ItemSize itemSize = getItemSize(SiteShowOption.getDraftOption());
            if (itemSize == null) {
                itemSize = new ItemSize();
            }
            CopierUtil.copyProperties(tempItemSize, itemSize, "Id");
            if (itemSize.getId() <= 0) {
                persistance.putItemSize(itemSize);
                setItemSize(itemSize);
            }
        }
    }
    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/

    public AccessibleSettings getAccessibleSettings(final SiteShowOption siteShowOption) {
        final Item item = getItem(siteShowOption);
        if (item != null) {
            final AccessibleSettings accessibleSettings = persistance.getAccessibleSettings(item.getAccessibleSettingsId());
            if (accessibleSettings != null) {
                return accessibleSettings;
            }
        }
        return new AccessibleSettings();
    }

    public void setAccessibleSettings(final AccessibleSettings accessibleSettings) {
        final DraftItem item = getDraftItem();
        if (item != null) {
            item.setAccessibleSettingsId(accessibleSettings.getAccessibleSettingsId());
        }
    }

    public void updateAccessibleSettings(final AccessibleSettings tempAccessibleSettings) {
        AccessibleSettings accessibleSettings = getAccessibleSettings(SiteShowOption.getDraftOption());
        if (accessibleSettings == null) {
            accessibleSettings = new AccessibleSettings();
        }
        CopierUtil.copyProperties(tempAccessibleSettings, accessibleSettings, "AccessibleSettingsId");
        if (accessibleSettings.getAccessibleSettingsId() <= 0) {
            persistance.putAccessibleSettings(accessibleSettings);
            setAccessibleSettings(accessibleSettings);
        }
    }

    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/

    /*---------------------------------------------------Background---------------------------------------------------*/

    public Background getBackground(final SiteShowOption siteShowOption) {
        final Item item = getItem(siteShowOption);
        if (item != null) {
            return persistance.getBackground(item.getBackgroundId());
        } else {
            return null;
        }
    }

    public void setBackground(Background background) {
        final DraftItem item = getDraftItem();
        if (item != null) {
            item.setBackgroundId(background.getId());
        }
    }

    public void updateBackground(final Background tempBackground) {
        Background background = getBackground(SiteShowOption.getDraftOption());
        if (background == null) {
            background = new Background();
        }
        CopierUtil.copyProperties(tempBackground, background, "Id");
        if (background.getId() <= 0) {
            persistance.putBackground(background);
            setBackground(background);
        }
    }

    /*---------------------------------------------------Background---------------------------------------------------*/


    /*-----------------------------------------------------Border-----------------------------------------------------*/

    public Border getBorder(final SiteShowOption siteShowOption) {
        final Item item = getItem(siteShowOption);
        if (item != null) {
            return persistance.getBorder(item.getBorderId());
        } else {
            return null;
        }
    }

    public void setBorder(Border border) {
        final DraftItem item = getDraftItem();
        if (item != null) {
            item.setBorderId(border.getId());
        }
    }

    public void updateBorder(final Border tempBorder) {
        Border border = getBorder(SiteShowOption.getDraftOption());
        if (border == null) {
            border = new Border();
        }
        ItemCopierUtil.copyBorderProperties(tempBorder, border);

        if (border.getId() <= 0) {
            persistance.putBorder(border);
            setBorder(border);
        }
    }

    /*-----------------------------------------------------Border-----------------------------------------------------*/


    /*-------------------------------------------------FontsAndColors-------------------------------------------------*/

    public FontsAndColors getExistingFontsAndColorsOrCreateNew(final SiteShowOption siteShowOption) {
        final Item item = getItem(siteShowOption);
        if (item != null) {
            FontsAndColors fontsAndColors = persistance.getFontsAndColors(item.getFontsAndColorsId());
            if (fontsAndColors == null) {
                fontsAndColors = new FontsAndColors();
                persistance.putFontsAndColors(fontsAndColors);
                item.setFontsAndColorsId(fontsAndColors.getId());
            }
            return fontsAndColors;
        } else {
            return new FontsAndColors();
        }
    }

    public void updateFontsAndColors(final CreateFontsAndColorsRequest request) {
        final FontsAndColors fontsAndColors = getExistingFontsAndColorsOrCreateNew(SiteShowOption.getDraftOption());
        new FontsAndColorsManager(fontsAndColors).updateValues(request);
    }
    /*-------------------------------------------------FontsAndColors-------------------------------------------------*/

    public boolean canBeSavedInAllPlaces() {
        final UserManager userManager = new UsersManager().getLogined();
        return userManager.getRight().toSiteItem(getDraftItem()) == SiteOnItemRightType.EDIT;
    }

    /*------------------------------------------------Artem`s methods-------------------------------------------------*/

    public int getId() {
        return item.getId();
    }

    public ItemType getType() {
        return item.getItemType();
    }

    public Date getItemCreatedDate() {
        return item.getCreated();
    }

    public String getName() {
        return item.getName();
    }

    public String getMaskedName() {
        return item.getName().replaceAll("'", "\\\\'");
    }

    public List<SiteOnItemManager> getRights() {
        final List<SiteOnItemManager> siteOnItemManagers = new ArrayList<SiteOnItemManager>();
        if (item.getSiteId() > 0) {
            final Site ownerSite = ServiceLocator.getPersistance().getSite(item.getSiteId());
            if (ownerSite != null) {
                siteOnItemManagers.add(new SiteOnItemAsOwnerManager(ownerSite));
            }
        }
        for (final SiteOnItem siteOnItemRight : persistance.getSiteOnItemsByItem(item.getId())) {
            siteOnItemManagers.add(new SiteOnItemAsRightManager(siteOnItemRight));
        }
        return siteOnItemManagers;
    }

    public Date getLastRecordDate() {
        if (lastRecordDate != null) {
            return lastRecordDate;
        }
        final Persistance persistance = ServiceLocator.getPersistance();
        if (item.getItemType() == ItemType.BLOG) {
            final BlogPost lastBlogPost = persistance.getLastBlogPost(item.getId());
            lastRecordDate = lastBlogPost == null ? null : lastBlogPost.getCreationDate();
            return lastRecordDate;
        } else if (item.getItemType() == ItemType.FORUM) {
            final ForumPost lastSubForumPost = persistance.getLastForumPost(item.getId());
            lastRecordDate = lastSubForumPost == null ? null : lastSubForumPost.getDateCreated();
            return lastRecordDate;
        } else if (item.getItemType().isFormType()) {
            lastRecordDate = persistance.getLastFillFormDateByFormId(item.getId());
            return lastRecordDate;
        }
        return null;
    }

    private Date lastRecordDate;// For better performance. Tolik


    public int getRecordsCount() {
        if (recordsCount != null) {
            return recordsCount;
        }
        final Persistance persistance = ServiceLocator.getPersistance();
        if (item.getItemType() == ItemType.BLOG) {
            recordsCount = persistance.getBlogPostsCountByBlog(item.getId());
            return recordsCount;
        } else if (item.getItemType() == ItemType.FORUM) {
            recordsCount = persistance.getSubForumsCountByForumId(item.getId());
            return recordsCount;
        } else if (item.getItemType().isFormType()) {
            recordsCount = persistance.getFilledFormsCountByFormId(item.getId());
            return recordsCount;
        } else if (item.getItemType() == ItemType.GALLERY) {
            recordsCount = persistance.getFilledFormsCountByFormId(((DraftGallery) item).getFormId1());
            return recordsCount;
        }
        return 0;
    }

    private Integer recordsCount;// For better performance. Tolik

    public void share(final Site site, final SiteOnItemRightType rightType) {
        if (site == null) {
            return;
        }

        // If site that we wish to share item with already owns this item.
        if (site.getId() == item.getSiteId()) {
            return;
        }

        final SiteOnItem existingSiteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), item.getId(), item.getItemType());
        if (existingSiteOnItemRight != null) {
            existingSiteOnItemRight.setType(rightType);
            return;
        }

        //Share.
        final SiteOnItem siteOnItemRight = item.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        siteOnItemRight.setType(rightType);
        persistance.putSiteOnItem(siteOnItemRight);
    }

    public boolean isSharedWithSite(final Site site) {
        final SiteOnItem existingSiteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), item.getId(), item.getItemType());
        return existingSiteOnItemRight != null;
    }

    // todo need tests

    public String getOwnerSiteName() {
        if (item.getSiteId() > 0) {
            final Site site = persistance.getSite(item.getSiteId());
            if (site != null) {
                return site.getTitle();
            }
        }
        return "none";
        //return persistance.getSiteTitleBySiteId(item.getSiteId());
    }

    public int getOwnerSiteId() {
        return item.getSiteId();
    }

    public FormType getFormType() {
        if (item.getItemType().isFormType()) {
            return ((Form) item).getType();
        }

        return null;
    }

    public List<FormItem> getFormItems() {
        if (item.getItemType().isFormType()) {
            return ((Form) item).getFormItems();
        }

        return null;
    }

    public boolean isChildSiteRegistration() {
        return item instanceof DraftChildSiteRegistration;
    }

    @Override
    public AccessibleForRender getAccessibleParent() {
        return null;
    }

    @Override
    public AccessibleElementType getAccessibleElementType() {
        return AccessibleElementType.ITEM;
    }

    @Override
    public List<Group> getAvailableGroups() {
        return persistance.getSite(getSiteId()).getAvailableGroups();
    }

    @Override
    public int getSiteId() {
        return getDraftItem().getSiteId();
    }

    @Override
    public AccessibleSettings getAccessibleSettings() {
        return getAccessibleSettings(SiteShowOption.getDraftOption());
    }

    private final DraftItem item;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
