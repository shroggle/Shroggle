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
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.*;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public final class ItemCopierUtil {

    private ItemCopierUtil() {
    }

    public static void copyBorderProperties(final Border original, final Border copy) {
        CopierUtil.copyProperties(original, copy, "Id", "BorderWidth", "BorderStyle", "BorderColor", "BorderPadding", "BorderMargin");
        CopierUtil.copyProperties(original.getBorderWidth(), copy.getBorderWidth(), "StyleId");
        CopierUtil.copyProperties(original.getBorderStyle(), copy.getBorderStyle(), "StyleId");
        CopierUtil.copyProperties(original.getBorderColor(), copy.getBorderColor(), "StyleId");
        CopierUtil.copyProperties(original.getBorderPadding(), copy.getBorderPadding(), "StyleId");
        CopierUtil.copyProperties(original.getBorderMargin(), copy.getBorderMargin(), "StyleId");
    }

    public static void copyStyles(final StylesOwner original, final StylesOwner copy) {
        final Persistance persistance = ServiceLocator.getPersistance();

        final FontsAndColors fontsAndColors = persistance.getFontsAndColors(original.getFontsAndColorsId());
        final FontsAndColors newFontsAndColors = copyFontsAndColors(fontsAndColors);
        copy.setFontsAndColorsId(newFontsAndColors != null ? newFontsAndColors.getId() : null);

        final Border border = persistance.getBorder(original.getBorderId());
        final Border newBorder = copyBorder(border);
        copy.setBorderId(newBorder != null ? newBorder.getId() : null);

        final Background background = persistance.getBackground(original.getBackgroundId());
        final Background newBackground = copyBackground(background);
        copy.setBackgroundId(newBackground != null ? newBackground.getId() : null);

        final ItemSize itemSize = persistance.getItemSize(original.getItemSizeId());
        final ItemSize newItemSize = copyItemSize(itemSize);
        copy.setItemSizeId(newItemSize != null ? newItemSize.getId() : null);

        final AccessibleSettings accessibleSettings = persistance.getAccessibleSettings(original.getAccessibleSettingsId());
        final AccessibleSettings newAccessibleSettings = copyAccessibleSettings(accessibleSettings);
        copy.setAccessibleSettingsId(newAccessibleSettings != null ? newAccessibleSettings.getAccessibleSettingsId() : null);
    }

    static FontsAndColors copyFontsAndColors(final FontsAndColors original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final FontsAndColors newFontsAndColors = new FontsAndColors();
            persistance.putFontsAndColors(newFontsAndColors);
            for (FontsAndColorsValue value : original.getCssValues()) {
                final FontsAndColorsValue newValue = new FontsAndColorsValue();
                copyProperties(value, newValue);
                newFontsAndColors.addCssValue(newValue);
                persistance.putFontsAndColorsValue(newValue);
            }
            return newFontsAndColors;
        }
        return null;
    }

    public static Border copyBorder(final Border original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final Border newBorder = new Border();
            CopierUtil.copyProperties(original, newBorder, "Id", "BorderWidth", "BorderStyle", "BorderColor", "BorderPadding", "BorderMargin");

            final Style borderWidth = new Style();
            CopierUtil.copyProperties(original.getBorderWidth(), borderWidth, "StyleId");
            persistance.putStyle(borderWidth);

            final Style borderStyle = new Style();
            CopierUtil.copyProperties(original.getBorderStyle(), borderStyle, "StyleId");
            persistance.putStyle(borderStyle);

            final Style borderColor = new Style();
            CopierUtil.copyProperties(original.getBorderColor(), borderColor, "StyleId");
            persistance.putStyle(borderColor);

            final Style borderPadding = new Style();
            CopierUtil.copyProperties(original.getBorderPadding(), borderPadding, "StyleId");
            persistance.putStyle(borderPadding);

            final Style borderMargin = new Style();
            CopierUtil.copyProperties(original.getBorderMargin(), borderMargin, "StyleId");
            persistance.putStyle(borderMargin);


            newBorder.setBorderWidth(borderWidth);
            newBorder.setBorderStyle(borderStyle);
            newBorder.setBorderColor(borderColor);
            newBorder.setBorderPadding(borderPadding);
            newBorder.setBorderMargin(borderMargin);

            persistance.putBorder(newBorder);
            return newBorder;
        }
        return null;
    }

    public static Background copyBackground(final Background original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final Background newBackground = new Background();
            CopierUtil.copyProperties(original, newBackground, "Id");

            persistance.putBackground(newBackground);
            return newBackground;
        }
        return null;
    }

    static ItemSize copyItemSize(final ItemSize original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final ItemSize newItemSize = new ItemSize();
            CopierUtil.copyProperties(original, newItemSize, "Id");

            persistance.putItemSize(newItemSize);
            return newItemSize;
        }
        return null;
    }

    public static AccessibleSettings copyAccessibleSettings(final AccessibleSettings original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final AccessibleSettings newAccessibleSettings = new AccessibleSettings();
            CopierUtil.copyProperties(original, newAccessibleSettings, "AccessibleSettingsId");
            persistance.putAccessibleSettings(newAccessibleSettings);
            return newAccessibleSettings;
        }
        return null;
    }

    public static List<Integer> copyKeywordsGroups(final List<KeywordsGroup> keywordsGroups) {
        final List<Integer> newKeywordsGroups = new ArrayList<Integer>();
        for (KeywordsGroup oldKeywordsGroup : keywordsGroups) {
            final KeywordsGroup newKeywordsGroup = copyKeywordsGroup(oldKeywordsGroup);
            newKeywordsGroups.add(newKeywordsGroup.getKeywordsGroupId());
        }
        return newKeywordsGroups;
    }

    public static void copyProperties(final Object original, final Object copy) {
        CopierUtil.copyProperties(original, copy, "Parent", "Form", "PaypalSetings", "MenuItems", "BlogId",
                "SiteId", "ForumId", "FormItems", "Filters", "FormId", "Site", "FilledFormId",
                "FilledFormItems", "Comments", "FilledForm", "FormFileId", "FormVideoId",
                "CommentId", "User", "ItemId", "VideoId", "Items", "Labels", "TaxRates",
                "ItemSize", "AccessibleSettings", "BorderId", "BackgroundId", "FontsAndColorsId", "Images");
    }

    public static DraftFormFilter copyFormFilter(final DraftFormFilter formFilter) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "FormFilterId"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<DraftFormFilter> wraper = new CopierWraper<DraftFormFilter>(formFilter);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    public static DraftFormFilterRule copyFilterRule(final DraftFormFilterRule filterRule) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "FormFilterRuleId"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<DraftFormFilterRule> wraper = new CopierWraper<DraftFormFilterRule>(filterRule);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    public static Image copyImage(final Image image) {// If you will replace this Image by Image1, please, dont forget to
        // change ImageId to Id. We don`t need its copy.
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "ImageId", "SiteId"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)

                        )
                ));

        final CopierWraper<Image> wraper = new CopierWraper<Image>(image);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    public static DraftMenu copyMenu(final DraftMenu menu) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "Id", "SiteId", "MenuItems"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)

                        )
                ));

        final CopierWraper<DraftMenu> wraper = new CopierWraper<DraftMenu>(menu);
        worker.copy(explorer.find(wraper, wraper).get(0));

        // I don`t know why, but cssValues don`t copying here. Tolik
        final DraftMenu copiedMenu = wraper.getObject();
        copyStyles(menu, copiedMenu);
        return copiedMenu;
    }

    public static void copyItemsWithoutDraftAndSetCorrectPageIds(
            final Map<Integer, Integer> pageIds,
            final List<MenuItem> oldItems, final Menu menu, final MenuItem parent) {
        for (final MenuItem item : oldItems) {
            // If we have blueprints page with its equivalent (page was copied) - copy this menuItem with correct pageId
            // from copied site. Else - trying to copy all its children.
            if (pageIds == null || pageIds.containsKey(item.getPageId())) {
                final MenuItem newItem = MenuItemsManager.createDraftCopy(item);
                newItem.setPageId(pageIds == null ? item.getPageId() : pageIds.get(item.getPageId()));
                newItem.setMenu(menu);
                newItem.setParent(parent);
                ServiceLocator.getPersistance().putMenuItem(newItem);
                copyItemsWithoutDraftAndSetCorrectPageIds(pageIds, item.getChildren(), menu, newItem);
            } else {
                copyItemsWithoutDraftAndSetCorrectPageIds(pageIds, item.getChildren(), menu, parent);
            }
        }
    }

    private static KeywordsGroup copyKeywordsGroup(final KeywordsGroup original) {
        if (original != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final KeywordsGroup newKeywordsGroup = new KeywordsGroup();
            CopierUtil.copyProperties(original, newKeywordsGroup, "KeywordsGroupId", "Site");
            newKeywordsGroup.setSite(original.getSite());
            persistance.putKeywordsGroup(newKeywordsGroup);
            return newKeywordsGroup;
        }
        return null;
    }
}
