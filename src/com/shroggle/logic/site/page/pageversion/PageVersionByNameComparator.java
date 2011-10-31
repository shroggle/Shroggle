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

package com.shroggle.logic.site.page.pageversion;


import com.shroggle.logic.site.page.PageManager;

import java.util.Comparator;

/**
 * @author Artem Stasuk
 */
public class PageVersionByNameComparator implements Comparator<PageManager> {

    public final static Comparator<PageManager> instance = new PageVersionByNameComparator();

    public int compare(final PageManager o1, final PageManager o2) {
        return o1.getName().compareTo(o2.getName());
    }

    private PageVersionByNameComparator() {

    }

}