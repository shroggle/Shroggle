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
import com.shroggle.entity.SiteShowOption;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class NavigationRowCreator {


    public static List<NavigationRow> createRows(final Gallery gallery, final Widget widget, final Integer pageNumber,
                                                 final HttpSession session, final SiteShowOption siteShowOption) {
        List<NavigationCell> cells = NavigationCellCreator.createSortedCells(gallery, widget, pageNumber, session, siteShowOption);
        List<NavigationRow> rows = new ArrayList<NavigationRow>();
        final int rowsNumber = getRowsNumber(gallery, cells.size());
        final int columnsNumber = getColumnsNumber(gallery, cells.size(), rowsNumber);

        if (gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY) {
            for (int i = 0; i < rowsNumber; i++) {
                rows.add(new NavigationRow());
            }
            for (int i = 0; i < columnsNumber; i++) {
                for (NavigationRow row : rows) {
                    if (cells.size() > 0) {
                        row.addCell(cells.get(0));
                        cells.set(0, null);
                        cells.remove(0);
                    }
                }
            }
        } else {
            for (int i = 0; i < rowsNumber; i++) {
                NavigationRow row = new NavigationRow();
                for (int j = 0; j < columnsNumber; j++) {
                    if (cells.size() > 0) {
                        row.addCell(cells.get(0));
                        cells.set(0, null);
                        cells.remove(0);
                    }
                }
                rows.add(row);
            }
        }
        return rows;
    }

    private static int getColumnsNumber(final Gallery gallery, final int cellsNumber, final int rowsNumber) {
        if (gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_VERTICALLY) {
            return gallery.getColumns();
        } else {
            return (int) Math.ceil(cellsNumber / (double) rowsNumber);
        }
    }

    private static int getRowsNumber(final Gallery gallery, final int cellsNumber) {
        if (gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_VERTICALLY) {
            return (int) Math.ceil(cellsNumber / (double) gallery.getColumns());
        } else {
            return gallery.getRows();
        }
    }
}