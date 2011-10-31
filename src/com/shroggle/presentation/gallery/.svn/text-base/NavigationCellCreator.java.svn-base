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
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.*;
import com.shroggle.util.resource.provider.ResourceGetterType;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class NavigationCellCreator {


    public static List<NavigationCell> createSortedCells(final Gallery gallery, final Widget widget, final Integer pageNumber,
                                                         final HttpSession session, final SiteShowOption siteShowOption) {
        final List<NavigationCell> navigationThumbnailCells = new ArrayList<NavigationCell>();
        if (gallery != null) {
            final List<FilledForm> sortedFilledForms = SortedFilledFormsCreator.execute(gallery, ((WidgetItem) widget).getDraftItem(), pageNumber, session, false, siteShowOption);

            if (sortedFilledForms != null && sortedFilledForms.size() > 0) {
                for (FilledForm filledForm : sortedFilledForms) {
                    List<NavigationInnerCell> navigationInnerCells = new ArrayList<NavigationInnerCell>();
                    //-----------------------------------inner cells creation (thumbnail)-------------------------------
                    for (GalleryLabel galleryLabel : gallery.getLabels()) {
                        NavigationInnerCell navigationThumbnailInnerCell = createInnerCell(gallery, widget.getSiteId(), galleryLabel, filledForm);
                        navigationInnerCells.add(navigationThumbnailInnerCell);
                    }
                    //-----------------------------------inner cells creation (thumbnail)-------------------------------

                    //-----------------------------main cell (contain several inner cells)------------------------------
                    final NavigationCell navigationCell =
                            createParentCell(gallery, widget, filledForm.getFilledFormId(), navigationInnerCells, siteShowOption);
                    //-----------------------------main cell (contain several inner cells)------------------------------
                    navigationThumbnailCells.add(navigationCell);
                }
            }
            //------------------------------------------empty main cell creation----------------------------------------
            //if (pageNumber != null) {
            final int elementsNumber = gallery.getRows() * gallery.getColumns();
            if (navigationThumbnailCells.size() < elementsNumber) {
                navigationThumbnailCells.addAll(createEmptyCells(gallery, elementsNumber - navigationThumbnailCells.size()));
            }
            // }
            //------------------------------------------empty main cell creation----------------------------------------
        }
        return navigationThumbnailCells;
    }

    //todo add test (Tolik)
    //-------------------------------------------inner cell creation (thumbnail)----------------------------------------
    private static NavigationInnerCell createInnerCell(final Gallery gallery, final int siteId, final GalleryLabel galleryLabel,
                                                       final FilledForm filledForm) {
        NavigationInnerCell innerCell = new NavigationInnerCell();
        innerCell.setAlign(galleryLabel.getAlign().getValue());
        innerCell.setFormItemId(galleryLabel.getId().getFormItemId());
        innerCell.setColumn(galleryLabel.getColumn());
        innerCell.setPosition(galleryLabel.getPosition());
        final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByFormItemId(filledForm,
                galleryLabel.getId().getFormItemId());
        /*-------------------------------------------------image size-------------------------------------------------*/
        FormFile formFile = ServiceLocator.getPersistance().getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));
        innerCell.setSourceImageWidth((formFile != null && formFile.getWidth() != null) ? formFile.getWidth() : gallery.getThumbnailWidth());
        innerCell.setSourceImageHeight((formFile != null && formFile.getHeight() != null) ? formFile.getHeight() : gallery.getThumbnailHeight());
        Dimension dimension = ResourceSizeCreator.execute(gallery.getThumbnailWidth(), gallery.getThumbnailHeight(), innerCell.getSourceImageWidth(), innerCell.getSourceImageHeight(), true);
        innerCell.setResizedWidth(dimension.getWidth());
        innerCell.setResizedHeight(dimension.getHeight());
        /*-------------------------------------------------image size-------------------------------------------------*/

        /*------------------------------------------------thumbnail url-----------------------------------------------*/
        if (filledFormItem == null) {
            innerCell.setNavigationCellType(NavigationCellType.TEXT);
            innerCell.setValue("");
        } else if (filledFormItem.getFormItemName().equals(FormItemName.IMAGE_FILE_UPLOAD)) {
            if (formFile != null && ServiceLocator.getFileSystem().isResourceExist(formFile)) {
                innerCell.setNavigationCellType(NavigationCellType.IMAGE);
                innerCell.setAlt(new FilledFormItemManager(filledFormItem).getFormImageAlt());
                innerCell.setValue(
                        ServiceLocator.getResourceGetter().get(
                                ResourceGetterType.GALLERY, formFile.getFormFileId(),
                                gallery.getId(), 0, gallery.getVersion(), false));
            } else {
                innerCell.setNavigationCellType(NavigationCellType.GRAY_RECTANGLE);
            }
        } else {
            innerCell.setNavigationCellType(NavigationCellType.TEXT);
            innerCell.setValue(new FilledFormItemManager(filledFormItem).getFormattedValue(siteId));
        }
        /*------------------------------------------------thumbnail url-----------------------------------------------*/
        return innerCell;
    }
    //-------------------------------------------inner cell creation (thumbnail)----------------------------------------


    //---------------------------------main cell creation (contain several inner cells)---------------------------------

    private static NavigationCell createParentCell(final Gallery gallery, final Widget widget, final int filledFormId,
                                                   final List<NavigationInnerCell> innerCells,
                                                   final SiteShowOption siteShowOption) {
        final NavigationCell navigationCell = new NavigationCell();
        final GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widget, filledFormId, siteShowOption);
        navigationCell.setUrl(url);
        navigationCell.setThumbnailWidth(gallery.getThumbnailWidth());
        navigationCell.setThumbnailHeight(gallery.getThumbnailHeight());
        navigationCell.setFilledFormId(filledFormId);
        navigationCell.setGalleryId(gallery.getId());
        navigationCell.setMargin("margin : " + gallery.getCellVerticalMargin() + "px " +
                gallery.getCellHorizontalMargin() + "px; ");
        navigationCell.setBorder("border : " + gallery.getCellBorderWidth() + "px " +
                StringUtil.getEmptyOrString(gallery.getBorderStyle()) + " " +
                StringUtil.getEmptyOrString(gallery.getBorderColor()) + "; ");
        navigationCell.setCellWidth(gallery.getCellWidth() + "px");
        navigationCell.setCellHeight(gallery.getCellHeight() + "px");
        String background = gallery.getBackgroundColor() == null || gallery.getBackgroundColor().isEmpty() ? "" :
                "background : " + gallery.getBackgroundColor() + "; ";
        navigationCell.setBackground(background);
        navigationCell.setStyle(createStyleForCell(navigationCell));
        Collections.sort(innerCells, new Comparator<NavigationInnerCell>() {
            public int compare(NavigationInnerCell cell1, NavigationInnerCell cell2) {
                return Integer.valueOf(cell1.getColumn()).compareTo(cell2.getColumn());
            }
        });
        navigationCell.setNavigationInnerCells(innerCells);
        return navigationCell;
    }

    //---------------------------------main cell creation (contain several inner cells)-------------------------------//
    //------------------------------------------empty main cell creation------------------------------------------------
    private static List<NavigationCell> createEmptyCells(final Gallery gallery, final int cellsNumber) {
        final List<NavigationCell> cells = new ArrayList<NavigationCell>();
        for (int i = 0; i < cellsNumber; i++) {
            final NavigationCell navigationCell = new NavigationCell();
            navigationCell.setFilledFormId(-1);
            navigationCell.setGalleryId(-1);
            navigationCell.setThumbnailWidth(gallery.getThumbnailWidth());
            navigationCell.setThumbnailHeight(gallery.getThumbnailHeight());
            navigationCell.setMargin("margin : " + gallery.getCellVerticalMargin() + "px " +
                    gallery.getCellHorizontalMargin() + "px; ");
            navigationCell.setBorder("border : " + gallery.getCellBorderWidth() + "px " +
                    StringUtil.getEmptyOrString(gallery.getBorderStyle()) + " " +
                    StringUtil.getEmptyOrString(gallery.getBorderColor()) + "; ");
            navigationCell.setCellWidth(gallery.getCellWidth() + "px");
            navigationCell.setCellHeight(gallery.getCellHeight() + "px");
            navigationCell.setBackground("background : #F0F0F0; ");
            navigationCell.setStyle(createStyleForCell(navigationCell));
            navigationCell.setNavigationInnerCells(new ArrayList<NavigationInnerCell>());
            cells.add(navigationCell);
        }
        return cells;
    }

    //------------------------------------------empty main cell creation------------------------------------------------
    private static String createStyleForCell(NavigationCell cell) {
        return cell.getMargin() + cell.getBorder() + cell.getBackground() + "width : " + cell.getCellWidth()
                + "; height : " + cell.getCellHeight() + ";";
    }

}