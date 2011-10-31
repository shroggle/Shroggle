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

import com.shroggle.entity.GalleryNavigationPaginatorType;


/**
 * @author Balakirev Anatoliy
 */
public class GalleryPaginatorStyleCreator {

    public static void execute(final PaginationCell[] cells, GalleryNavigationPaginatorType paginatorType) {
        paginatorType = paginatorType != null ? paginatorType : GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS;
        switch (paginatorType) {
            case PREVIOUS_NEXT_WITH_BORDERED_NUMBERS: {
                for (PaginationCell cell : cells) {
                    if (cell.isSelected()) {
                        cell.setCssClassName("selected_paginator_cell_with_border");
                        cell.setStyle("color:white");
                    } else {
                        cell.setCssClassName("paginator_cell_with_border");
                        cell.setStyle("");
                    }
                }
                break;
            }
            default: {
                for (PaginationCell cell : cells) {
                    if (cell.isSelected()) {
                        cell.setCssClassName("selected_paginator_cell");
                        cell.setStyle("");
                    } else {
                        cell.setCssClassName("paginator_cell");
                        cell.setStyle("");
                    }
                }
            }
        }
    }

    
}