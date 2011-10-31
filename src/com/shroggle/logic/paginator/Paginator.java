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

package com.shroggle.logic.paginator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin, Balakirev Anatoliy
 */
public class Paginator<T> {

    public Paginator(final List<T> items) {
        this(items, PAGINATION_DEFAULT_ITEMS_PER_PAGE);
    }

    public Paginator(List<T> items, int itemsPerPage) {
        this.items = items;
        this.itemsPerPage = itemsPerPage;
    }

    public List<T> getItems() {
        final int startPosition = (pageNumber - 1) * itemsPerPage;

        //Getting last item for this page position.
        int endPosition = items.size() < startPosition + itemsPerPage ?
                items.size() : startPosition + itemsPerPage;

        return new ArrayList<T>(items.subList(startPosition, endPosition));
    }

    public Paginator<T> setPageNumber(Integer pageNumber) {
        pageNumber = pageNumber == null ? FIRST_PAGE_NUMBER : pageNumber;
        final int maxPageNumber = getPagesCount();
        if (pageNumber > maxPageNumber) {
            logger.info("Page number = " + pageNumber + " is too big (max page number = " + maxPageNumber + "). Setting it to the max page number...");
            pageNumber = maxPageNumber;
        } else if (pageNumber <= 0) {
            logger.info("Page number = " + pageNumber + " is too small (min page number = 1). Setting it to the min page number...");
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPagesCount() {
        final int pagesCount = (int) Math.ceil((double) items.size() / itemsPerPage);
        return pagesCount == 0 ? 1 : pagesCount;
    }

    public int getMaxPagesBackwardAndForwardFromCurrent() {
        return PAGINATOR_MAX_PAGES_BACKWARD_AND_FORWARD_FROM_CURRENT;
    }

    public static int getFirstPageNumber() {
        return FIRST_PAGE_NUMBER;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    protected boolean shouldBePaginated() {
        return items.size() > PAGINATION_DEFAULT_THRESHOLD;
    }


    private List<T> items;
    private int pageNumber = FIRST_PAGE_NUMBER;
    private final int itemsPerPage;

    /*----------------------------------------------------Constants---------------------------------------------------*/
    private final static int PAGINATION_DEFAULT_THRESHOLD = 30;
    private final static int PAGINATION_DEFAULT_ITEMS_PER_PAGE = 20;
    private final static int PAGINATOR_MAX_PAGES_BACKWARD_AND_FORWARD_FROM_CURRENT = 6;
    private final static int FIRST_PAGE_NUMBER = 1;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    /*----------------------------------------------------Constants---------------------------------------------------*/
}
