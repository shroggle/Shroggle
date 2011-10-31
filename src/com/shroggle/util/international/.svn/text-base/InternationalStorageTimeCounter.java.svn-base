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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;

import java.util.Locale;

/**
 * @author Stasuk Artem
 */
public class InternationalStorageTimeCounter implements InternationalStorage {

    public InternationalStorageTimeCounter(final InternationalStorage internationalStorage) {
        this.internationalStorage = internationalStorage;
    }

    @Override
    public International get(final String part, final Locale locale) {
        final TimeCounter timeCounter = createTimeCounter(part);
        try {
            return new InternationalTimeCounter(internationalStorage.get(part, locale), part);
        } finally {
            timeCounter.stop();
        }
    }

    static TimeCounter createTimeCounter(final String name) {
        return ServiceLocator.getTimeCounterCreator().create("internationalStorage://" + name);
    }

    private final InternationalStorage internationalStorage;

}
