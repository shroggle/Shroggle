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

package com.shroggle.logic.gallery;

import com.shroggle.entity.*;
import com.shroggle.presentation.gallery.PaginationCell;

/**
 * @author Balakirev Anatoliy
 */
public class GalleryPaginatorManager {

    public static PaginatorType getPaginatorType(final Gallery gallery) {
        if (gallery == null || gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_VERTICALLY ||
                gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY) {
            return PaginatorType.NONE;
        }
        if (gallery.getOrientation() == GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW) {
            return PaginatorType.ABOVE;
        }
        return PaginatorType.BELOW;
    }

    public static int createPaginatorWidth(final PaginationCell[] paginationCells, final Gallery gallery) {
        final PaginatorType paginatorType = getPaginatorType(gallery);
        if (paginatorType != PaginatorType.NONE && paginationCells != null && gallery != null) {
            int length = 0;
            for (PaginationCell cell : paginationCells) {
                length += cell.getValue().length();
            }
            int marginPadding = gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_BORDERED_NUMBERS ? 16 : 2;
            final int margin = marginPadding * paginationCells.length;
            final int textWidth = (int) (length * 11.3);
            final int width = (textWidth + margin);
            return width + (int) ((width / 100.0) * 2.0);
        } else {
            return 0;
        }
    }

    public static boolean hidePaginator(final Gallery gallery, final DraftItem draftItem, final SiteShowOption siteShowOption) {
        return getPaginatorPagesNumber(gallery, draftItem, siteShowOption) <= 1;
    }

    public static long getPaginatorPagesNumber(final Gallery gallery, final DraftItem draftItem, final SiteShowOption siteShowOption) {
        if (gallery != null) {
            final int filedFormsNumber = new GalleryItemsSorter().getFilledForms(gallery, draftItem, siteShowOption).size();
            if (GalleryPaginatorManager.getPaginatorType(gallery) != PaginatorType.NONE) {
                int numberOfCellsOnPage = (gallery.getRows() * gallery.getColumns());
                if (numberOfCellsOnPage == 0) {
                    return 0;
                }
                return ((long) Math.ceil(filedFormsNumber / (double) numberOfCellsOnPage));
            }
        }
        return 0;
    }

    public static Integer createSelectedPageNumber(final Gallery gallery, final Integer pageNumber) {
        if (GalleryPaginatorManager.getPaginatorType(gallery) != PaginatorType.NONE) {
            return pageNumber == null ? 1 : pageNumber;
        } else {
            return null;
        }
    }

}
