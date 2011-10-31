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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.fontsAndColors.FontsAndColorsManager;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetComparator;
import com.shroggle.presentation.site.cssParameter.CreateFontsAndColorsRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class WidgetManager implements AccessibleForRender {

    public WidgetManager(final int widgetId) {
        this(new UsersManager().getLogined().getRight().getSiteRight().getWidgetForEdit(widgetId));
    }

    public WidgetManager(final Widget widget) {
        this.widget = widget;
    }

    public ItemManager getItemManager() {
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;
            return new ItemManager(widgetItem.getDraftItem());
        } else {
            return null;
        }
    }

    public PageSettingsManager getPageSettings() {// We don`t need SiteShowOption here. Widget has only work or draft settings at a time. Tolik
        return new PageSettingsManager(widget.getPageSettings());
    }

    public Site getSite() {
        return persistance.getSite(getSiteId());
    }

    public Integer getCrossWidgetId() {
        return widget.getCrossWidgetId();
    }


    public void remove() {
        final PageManager pageManager = new PageManager(widget.getPage());
        if (widget.getParent() != null) {
            final WidgetComposit widgetComposit = widget.getParent();
            final List<Widget> widgets = new ArrayList<Widget>(widgetComposit.getChilds());
            widgets.remove(widget);
            Collections.sort(widgets, WidgetComparator.INSTANCE);
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    pageManager.setChanged(true);
                    pageManager.removeWidget(widget);
                    widgetComposit.removeChild(widget);
                    for (int sequince = 0; sequince < widgets.size(); sequince++) {
                        widget.setPosition(sequince);
                    }

                    persistance.removeWidget(widget);
                }
            });
        } else {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    pageManager.setChanged(true);
                    pageManager.removeWidget(widget);
                    persistance.removeWidget(widget);
                }
            });
        }
    }

    public Widget getWidget() {
        return widget;
    }

    public WidgetRightsManager getRights() {
        return new WidgetRightsManager(widget);
    }

    public String getLocation() {
        StringBuilder name = new StringBuilder("");
        final PageSettingsManager pageSettings = getPageSettings();
        final SiteManager siteManager = new SiteManager(pageSettings.getPage().getSite());
        name.append(siteManager.getName());
        name.append(" ");
        name.append(siteManager.getPublicUrl());
        name.append(", ");
        name.append(pageSettings.getName());
        return name.toString();
    }

    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    public ItemSize getItemSize(final SiteShowOption siteShowOption) {
        final ItemSize itemSize = persistance.getItemSize(widget.getItemSizeId());
        if (itemSize != null) {
            return itemSize;
        } else {
            final ItemManager itemManager = getItemManager();
            if (itemManager != null) {
                return itemManager.getItemSize(siteShowOption);
            }
        }
        return new ItemSize();
    }

    public boolean isItemSizeSavedInCurrentPlace() {
        return widget.getItemSizeId() != null;
    }

    public void setItemSizeInCurrentPlace(final ItemSize tempItemSize) {
        ItemSize itemSize = persistance.getItemSize(widget.getItemSizeId());
        if (itemSize == null) {
            itemSize = new ItemSize();
        }
        CopierUtil.copyProperties(tempItemSize, itemSize, "Id");
        if (itemSize.getId() <= 0) {
            persistance.putItemSize(itemSize);
            widget.setItemSizeId(itemSize.getId());
        }
    }

    public void setItemSizeInAllPlaces(final ItemSize tempItemSize) {
        final ItemManager itemManager = getItemManager();
        if (itemManager == null) {
            throw new UnsupportedOperationException("Unable to save changes in all places for widget, which doesn`t have item!");
        }
        if (itemManager.canBeSavedInAllPlaces()) {
            itemManager.updateItemSize(tempItemSize);
            widget.setItemSizeId(null);
        } else {
            logger.info("Unable to save itemSize in all items. You don`t have enough rights for this. Saving changes in current place only...");
            setItemSizeInCurrentPlace(tempItemSize);
        }
    }
    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/


    public AccessibleSettings getAccessibleSettings(final SiteShowOption siteShowOption) {
        final AccessibleSettings accessibleSettings = persistance.getAccessibleSettings(widget.getAccessibleSettingsId());
        if (accessibleSettings != null) {
            return accessibleSettings;
        } else {
            final ItemManager itemManager = getItemManager();
            if (itemManager != null) {
                return itemManager.getAccessibleSettings(siteShowOption);
            }
        }
        return new AccessibleSettings();
    }

    public boolean isAccessibleSettingsSavedInCurrentPlace() {
        return widget.getAccessibleSettingsId() != null;
    }

    public void setAccessibleSettingsInCurrentPlace(final AccessibleSettings tempAccessibleSettings) {
        AccessibleSettings accessibleSettings = persistance.getAccessibleSettings(widget.getAccessibleSettingsId());
        if (accessibleSettings == null) {
            accessibleSettings = new AccessibleSettings();
        }
        CopierUtil.copyProperties(tempAccessibleSettings, accessibleSettings, "AccessibleSettingsId");
        if (accessibleSettings.getAccessibleSettingsId() <= 0) {
            persistance.putAccessibleSettings(accessibleSettings);
            widget.setAccessibleSettingsId(accessibleSettings.getAccessibleSettingsId());
        }
    }

    public void setAccessibleSettingsInAllPlaces(final AccessibleSettings tempAccessibleSettings) {
        final ItemManager itemManager = getItemManager();
        if (itemManager == null) {
            throw new UnsupportedOperationException("Unable to save changes in all places for widget, which doesn`t have item!");
        }
        if (itemManager.canBeSavedInAllPlaces()) {
            itemManager.updateAccessibleSettings(tempAccessibleSettings);
            widget.setAccessibleSettingsId(null);
        } else {
            logger.info("Unable to save accessibleSettings in all items. You don`t have enough rights for this. Saving changes in current place only...");
            setAccessibleSettingsInCurrentPlace(tempAccessibleSettings);
        }
    }

    @Override
    public int getId() {
        return widget.getWidgetId();
    }

    @Override
    public int getSiteId() {
        return widget.getSite().getSiteId();
    }

    @Override
    public AccessibleForRender getAccessibleParent() {
        return widget.getPageSettings() != null ? new PageManager(getPageSettings()) : null;
    }

    @Override
    public AccessibleElementType getAccessibleElementType() {
        return AccessibleElementType.WIDGET;
    }

    @Override
    public List<Group> getAvailableGroups() {
        if (widget.isWidgetItem()) {
            return ((WidgetItem) widget).getAvailableGroups();
        }
        return Collections.emptyList();
    }

    @Override
    public AccessibleSettings getAccessibleSettings() {
        return getAccessibleSettings(SiteShowOption.getDraftOption());
    }

    @Override
    public void setAccessibleSettings(AccessibleSettings accessibleSettings) {
        widget.setAccessibleSettingsId(accessibleSettings != null ? accessibleSettings.getAccessibleSettingsId() : null);
    }
    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/

    /*---------------------------------------------------Background---------------------------------------------------*/

    public Background getBackground(final SiteShowOption siteShowOption) {
        if (widget.getBackgroundId() != null) {
            return persistance.getBackground(widget.getBackgroundId());
        } else {
            final ItemManager itemManager = getItemManager();
            if (itemManager != null) {
                return itemManager.getBackground(siteShowOption);
            }
        }
        return null;
    }

    public boolean isBackgroundSavedInCurrentPlace() {
        return widget.getBackgroundId() != null;
    }

    public void setBackgroundInCurrentPlace(final Background tempBackground) {
        Background background = persistance.getBackground(widget.getBackgroundId());
        if (background == null) {
            background = new Background();
        }
        CopierUtil.copyProperties(tempBackground, background, "Id");
        if (background.getId() <= 0) {
            persistance.putBackground(background);
            widget.setBackgroundId(background.getId());
        }
    }

    public void setBackgroundInAllPlaces(final Background tempBackground) {
        final ItemManager itemManager = getItemManager();
        if (itemManager == null) {
            throw new UnsupportedOperationException("Unable to save changes in all places for widget, which doesn`t have item!");
        }
        if (itemManager.canBeSavedInAllPlaces()) {
            itemManager.updateBackground(tempBackground);
            widget.setBackgroundId(null);
        } else {
            logger.info("Unable to save background in all items. You don`t have enough rights for this. Saving changes in current place only...");
            setBackgroundInCurrentPlace(tempBackground);
        }
    }
    /*---------------------------------------------------Background---------------------------------------------------*/

    /*-----------------------------------------------------Border-----------------------------------------------------*/

    public Border getBorder(final SiteShowOption siteShowOption) {
        if (widget.getBorderId() != null) {
            return persistance.getBorder(widget.getBorderId());
        } else {
            final ItemManager itemManager = getItemManager();
            if (itemManager != null) {
                return itemManager.getBorder(siteShowOption);
            }
        }
        return null;
    }

    public boolean isBorderSavedInCurrentPlace() {
        return widget.getBorderId() != null;
    }

    public void setBorderInCurrentPlace(final Border tempBorder) {
        Border border = persistance.getBorder(widget.getBorderId());
        if (border == null) {
            border = new Border();
        }
        ItemCopierUtil.copyBorderProperties(tempBorder, border);

        if (border.getId() <= 0) {
            persistance.putBorder(border);
            widget.setBorderId(border.getId());
        }
    }

    public void setBorderInAllPlaces(final Border tempBorder) {
        final ItemManager itemManager = getItemManager();
        if (itemManager == null) {
            throw new UnsupportedOperationException("Unable to save changes in all places for widget, which doesn`t have item!");
        }
        if (itemManager.canBeSavedInAllPlaces()) {
            itemManager.updateBorder(tempBorder);
            widget.setBorderId(null);
        } else {
            logger.info("Unable to save border in all items. You don`t have enough rights for this. Saving changes in current place only...");
            setBorderInCurrentPlace(tempBorder);
        }
    }
    /*-----------------------------------------------------Border-----------------------------------------------------*/

    /*-------------------------------------------------FontsAndColors-------------------------------------------------*/

    public FontsAndColors getFontsAndColors(final SiteShowOption siteShowOption) {
        if (widget.getFontsAndColorsId() != null) {
            return persistance.getFontsAndColors(widget.getFontsAndColorsId());
        } else {
            final ItemManager itemManager = getItemManager();
            if (itemManager != null) {
                return itemManager.getExistingFontsAndColorsOrCreateNew(siteShowOption);
            }
        }
        return new FontsAndColors();
    }

    public boolean isFontsAndColorsSavedInCurrentPlace() {
        return widget.getFontsAndColorsId() != null;
    }

    public void setFontsAndColorsInCurrentPlace(final CreateFontsAndColorsRequest request) {
        FontsAndColors fontsAndColors = persistance.getFontsAndColors(widget.getFontsAndColorsId());
        if (fontsAndColors == null) {
            fontsAndColors = new FontsAndColors();
            persistance.putFontsAndColors(fontsAndColors);
            widget.setFontsAndColorsId(fontsAndColors.getId());
        }
        new FontsAndColorsManager(fontsAndColors).updateValues(request);
    }

    public void setFontsAndColorsInAllPlaces(final CreateFontsAndColorsRequest request) {
        final ItemManager itemManager = getItemManager();
        if (itemManager == null) {
            throw new UnsupportedOperationException("Unable to save changes in all places for widget, which doesn`t have item!");
        }
        if (itemManager.canBeSavedInAllPlaces()) {
            itemManager.updateFontsAndColors(request);
            persistance.removeFontsAndColors(persistance.getFontsAndColors(widget.getFontsAndColorsId()));
            widget.setFontsAndColorsId(null);
        } else {
            logger.info("Unable to save fontsAndColors in all items. You don`t have enough rights for this. Saving changes in current place only...");
            setFontsAndColorsInCurrentPlace(request);
        }
    }
    /*-------------------------------------------------FontsAndColors-------------------------------------------------*/


    private final Widget widget;
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
