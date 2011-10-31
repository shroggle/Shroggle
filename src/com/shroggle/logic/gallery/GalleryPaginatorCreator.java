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
import com.shroggle.presentation.gallery.GalleryPaginatorStyleCreator;
import com.shroggle.presentation.gallery.PaginationCell;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;

import java.util.Locale;


/**
 * @author Balakirev Anatoliy
 */
public class GalleryPaginatorCreator {

    public static PaginationCell[] createPaginator(final Gallery gallery, final DraftItem draftItem, Integer selectedPage, final SiteShowOption siteShowOption) {
        PaginationCell[] paginationCells = null;
        long pagesNumber = GalleryPaginatorManager.getPaginatorPagesNumber(gallery, draftItem, siteShowOption);
        if (pagesNumber > 0) {
            selectedPage = selectedPage != null ? selectedPage : 1;
            paginationCells = createPaginator(selectedPage, pagesNumber, gallery.getNavigationPaginatorType());
            GalleryPaginatorStyleCreator.execute(paginationCells, gallery.getNavigationPaginatorType());
        }
        return paginationCells;
    }

    public static PaginationCell[] createPaginator(final int selectedPageIndex, final long pagesNumber,
                                                   GalleryNavigationPaginatorType paginatorType) {
        final International intenational = ServiceLocator.getInternationStorage().get("configureGalleryPaginator", Locale.US);
        paginatorType = paginatorType != null ? paginatorType : GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS;
        PaginationCell[] paginationCells;
        switch (paginatorType) {
            case PREVIOUS_NEXT: {
                paginationCells = new PaginationCell[2];
                paginationCells[0] = new PaginationCell(intenational.get("previous"));
                paginationCells[0].setPageNumber(getPreviousPageIndex(selectedPageIndex));
                paginationCells[1] = new PaginationCell(intenational.get("next"));
                paginationCells[1].setPageNumber(getNextPageIndex(selectedPageIndex, pagesNumber));
                break;
            }
            case PREV_NEXT: {
                paginationCells = new PaginationCell[2];
                paginationCells[0] = new PaginationCell(intenational.get("prev"));
                paginationCells[0].setPageNumber(getPreviousPageIndex(selectedPageIndex));
                paginationCells[1] = new PaginationCell(intenational.get("next"));
                paginationCells[1].setPageNumber(getNextPageIndex(selectedPageIndex, pagesNumber));
                break;
            }
            default: {
                PaginationCell[] cells = createPaginatorNumbers(selectedPageIndex, pagesNumber);
                final int cellsNumber = PAGINATOR_ELEMENTS_NUMBER > cells.length ? cells.length : PAGINATOR_ELEMENTS_NUMBER;
                paginationCells = new PaginationCell[cellsNumber + 2];

                System.arraycopy(cells, 0, paginationCells, 1, cellsNumber);
                paginationCells[0] = new PaginationCell(intenational.get("previous"));
                paginationCells[0].setPageNumber(getPreviousPageIndex(selectedPageIndex));
                paginationCells[paginationCells.length - 1] = new PaginationCell(intenational.get("next"));
                paginationCells[paginationCells.length - 1].setPageNumber(getNextPageIndex(selectedPageIndex, pagesNumber));
                break;
            }
        }
        return paginationCells;
    }

    private static int getPreviousPageIndex(final int selectedPageIndex) {
        return selectedPageIndex > 1 ? (selectedPageIndex - 1) : -1;
    }

    private static int getNextPageIndex(final int selectedPageIndex, final long pagesNumber) {
        if (selectedPageIndex + 1 > pagesNumber) {
            return -1;
        } else {
            return (selectedPageIndex + 1);
        }
    }

    private static PaginationCell[] createPaginatorNumbers(final int selectedPageIndex, final long pagesNumber) {
        if (pagesNumber > PAGINATOR_ELEMENTS_NUMBER) {
            boolean showLeftEllipsis = (selectedPageIndex - 1) > SHOW_LEFT_ELLIPSIS_INDEX;
            boolean showRightEllipsis = (selectedPageIndex - 1) < pagesNumber - SHOW_RIGHT_ELLIPSIS_INDEX;
            PaginationCell[] paginationCells = new PaginationCell[PAGINATOR_ELEMENTS_NUMBER];
            //----------------------------------------------first element-----------------------------------------------
            paginationCells[0] = new PaginationCell("1");
            //----------------------------------------------first element-----------------------------------------------

            //----------------------------------------------second element----------------------------------------------
            if (showLeftEllipsis) {
                paginationCells[1] = new PaginationCell("...");
            }
            //----------------------------------------------second element----------------------------------------------

            //--------------------------------------------last but one element------------------------------------------
            if (showRightEllipsis) {
                paginationCells[PAGINATOR_ELEMENTS_NUMBER - 2] = new PaginationCell("...");
            }
            //--------------------------------------------last but one element------------------------------------------

            //-----------------------------------------------last element-----------------------------------------------
            paginationCells[PAGINATOR_ELEMENTS_NUMBER - 1] = new PaginationCell(String.valueOf(pagesNumber));
            //-----------------------------------------------last element-----------------------------------------------


            if (showLeftEllipsis && showRightEllipsis) {
                int firstValue = selectedPageIndex - ((PAGINATOR_ELEMENTS_NUMBER - 4) / 2);
                for (int i = 2; i < (PAGINATOR_ELEMENTS_NUMBER - 2); i++) {
                    paginationCells[i] = new PaginationCell(String.valueOf(firstValue));
                    firstValue++;
                }
            } else if (showLeftEllipsis) {
                if ((selectedPageIndex - 1) >= pagesNumber - SHOW_RIGHT_ELLIPSIS_INDEX) {
                    long firstValue = pagesNumber - 1;
                    for (int i = (PAGINATOR_ELEMENTS_NUMBER - 2); i > 1; i--) {
                        paginationCells[i] = new PaginationCell(String.valueOf(firstValue));
                        firstValue--;
                    }
                }
            } else if (showRightEllipsis) {
                for (int i = 1; i < (PAGINATOR_ELEMENTS_NUMBER - 2); i++) {
                    paginationCells[i] = new PaginationCell(String.valueOf(i + 1));
                }
            }

            for (int i = 0; i < PAGINATOR_ELEMENTS_NUMBER; i++) {
                paginationCells[i].setSelected(paginationCells[i].getValue().equals(String.valueOf(selectedPageIndex)));
            }
            setPagesNumber(paginationCells);
            return paginationCells;
        } else {
            PaginationCell[] paginationCells = new PaginationCell[(int) pagesNumber];
            for (int i = 0; i < pagesNumber; i++) {
                paginationCells[i] = new PaginationCell(String.valueOf(i + 1));
                paginationCells[i].setSelected(i == (selectedPageIndex - 1));
            }
            setPagesNumber(paginationCells);
            return paginationCells;
        }
    }

    private static void setPagesNumber(final PaginationCell[] paginationCells) {
        for (int i = 0; i < paginationCells.length; i++) {
            String pageNumber = paginationCells[i].getValue();
            if (pageNumber.equals("...")) {
                if (i == 1) {
                    paginationCells[i].setPageNumber(Integer.valueOf(paginationCells[i + 1].getValue()) - 1);
                } else {
                    paginationCells[i].setPageNumber(Integer.valueOf(paginationCells[i - 1].getValue()) + 1);
                }
            } else {
                paginationCells[i].setPageNumber(Integer.valueOf(paginationCells[i].getValue()));
            }
        }
    }

    private final static int PAGINATOR_ELEMENTS_NUMBER = 9;
    private final static int SHOW_LEFT_ELLIPSIS_INDEX = (((PAGINATOR_ELEMENTS_NUMBER + 1) / 2) - 1);
    private final static int SHOW_RIGHT_ELLIPSIS_INDEX = ((PAGINATOR_ELEMENTS_NUMBER + 1) / 2);

}