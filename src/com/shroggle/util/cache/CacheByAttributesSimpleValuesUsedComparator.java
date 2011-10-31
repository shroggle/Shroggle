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
package com.shroggle.util.cache;

import java.util.Comparator;

/**
 * @author Artem Stasuk
 */
final class CacheByAttributesSimpleValuesUsedComparator
        implements Comparator<CacheByAttributesSimpleValuesUsed> {

    public int compare(
            final CacheByAttributesSimpleValuesUsed o1,
            final CacheByAttributesSimpleValuesUsed o2) {
        return (int) (o1.getUsed().getUsed() - o2.getUsed().getUsed());
    }

    private CacheByAttributesSimpleValuesUsedComparator() {

    }

    public static final Comparator<CacheByAttributesSimpleValuesUsed> instance =
            new CacheByAttributesSimpleValuesUsedComparator();

}
