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
package com.shroggle.util.international;

import com.shroggle.entity.ItemType;
import com.shroggle.util.international.cache.InternationalStorageCache;
import com.shroggle.util.international.property.InternationalStoragePropertyBundle;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
public class InternationalStorageRun {

    public static void main(String[] args) {
        final InternationalStorage internationalStorage =
                new InternationalStoragePropertyBundle();

        final InternationalStorage internationalStorageCache =
                new InternationalStorageCache(new InternationalStoragePropertyBundle());

        final int steps = 10000;

        for (int i = 0; i < steps; i++) {
            final International international = internationalStorage.get("itemType", Locale.US);
            international.get(ItemType.BLOG.toString());
        }

        final long start = System.currentTimeMillis();

        for (int i = 0; i < steps; i++) {
            final International international = internationalStorage.get("itemType", Locale.US);
            international.get(ItemType.BLOG.toString());
        }

        System.out.println("Direct storage, steps: " + steps + ", time: " + (System.currentTimeMillis() - start) + " msec");

        final long startCache = System.currentTimeMillis();
        
        for (int i = 0; i < steps; i++) {
            final International international = internationalStorageCache.get("itemType", Locale.US);
            international.get(ItemType.BLOG.toString());
        }

        System.out.println("Cached storage, steps: " + steps + ", time: " + (System.currentTimeMillis() - startCache) + " msec");
    }

}
