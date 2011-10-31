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
package com.shroggle.presentation.site;

import com.shroggle.entity.Site;

import java.util.Comparator;

/**
 * @author Artem Stasuk
 */
public class SiteByTitleComparator implements Comparator<Site> {

    public int compare(Site o1, Site o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }

}
