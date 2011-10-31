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

package com.shroggle.util.international.cache;

import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Attention this class cache international values without parameters,
 * so you must use parameters provider after cache. 
 *
 * @author Stasuk Artem
 */
public class InternationalStorageCache implements InternationalStorage {

    public InternationalStorageCache(final InternationalStorage internationalStorage) {
        this.internationalStorage = internationalStorage;
    }

    public International get(final String part, final Locale locale) {
        International international = internationals.get(part + locale);
        if (international == null) {
            international = new InternationalCache(internationalStorage.get(part, locale));
            internationals.putIfAbsent(part + locale, international);
        }

        return international;
    }

    private final InternationalStorage internationalStorage;
    private final ConcurrentMap<String, International> internationals
            = new ConcurrentHashMap<String, International>();

}