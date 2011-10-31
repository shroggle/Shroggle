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

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


/**
 * @author Balakirev Anatoliy
 */
public class NavigationCell {

    private int filledFormId;

    private int galleryId;

    private GalleryNavigationUrl url;

    private String margin;

    private String border;

    private String cellWidth;

    private String cellHeight;

    private String background;

    private String style;

    private int thumbnailWidth;

    private int thumbnailHeight;

    private List<NavigationInnerCell> navigationInnerCells;


    public List<NavigationInnerCell> getInnerCellsForLeftColumn() {
        List<NavigationInnerCell> innerCells = new ArrayList<NavigationInnerCell>();
        if (navigationInnerCells != null) {
            for (NavigationInnerCell cell : navigationInnerCells) {
                if (cell.getColumn() == 0) {
                    innerCells.add(cell);
                }
            }
        }
        sortCellsByPosition(innerCells);
        return innerCells;
    }

    public List<NavigationInnerCell> getInnerCellsForCenterColumn() {
        List<NavigationInnerCell> innerCells = new ArrayList<NavigationInnerCell>();
        if (navigationInnerCells != null) {
            for (NavigationInnerCell cell : navigationInnerCells) {
                if (cell.getColumn() == 1) {
                    innerCells.add(cell);
                }
            }
        }
        sortCellsByPosition(innerCells);
        return innerCells;
    }

    public List<NavigationInnerCell> getInnerCellsForRightColumn() {
        List<NavigationInnerCell> innerCells = new ArrayList<NavigationInnerCell>();
        if (navigationInnerCells != null) {
            for (NavigationInnerCell cell : navigationInnerCells) {
                if (cell.getColumn() == 2) {
                    innerCells.add(cell);
                }
            }
        }
        sortCellsByPosition(innerCells);
        return innerCells;
    }

    private void sortCellsByPosition(List<NavigationInnerCell> cells) {
        Collections.sort(cells, new Comparator<NavigationInnerCell>() {
            public int compare(NavigationInnerCell cell1, NavigationInnerCell cell2) {
                return Integer.valueOf(cell1.getPosition()).compareTo(cell2.getPosition());
            }
        });
    }

    public GalleryNavigationUrl getUrl() {
        return url;
    }

    public void setUrl(GalleryNavigationUrl url) {
        this.url = url;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(String cellWidth) {
        this.cellWidth = cellWidth;
    }

    public String getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(String cellHeight) {
        this.cellHeight = cellHeight;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public List<NavigationInnerCell> getNavigationInnerCells() {
        return navigationInnerCells;
    }

    public void setNavigationInnerCells(List<NavigationInnerCell> navigationInnerCells) {
        this.navigationInnerCells = navigationInnerCells;
    }
}