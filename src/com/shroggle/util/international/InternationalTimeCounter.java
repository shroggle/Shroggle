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

import com.shroggle.util.process.timecounter.TimeCounter;

/**
 * @author Stasuk Artem
 */
public class InternationalTimeCounter implements International {

    public InternationalTimeCounter(final International international, final String part) {
        this.international = international;
        this.part = part;
    }

    @Override
    public String get(final String name, final Object... parameters) {
        final TimeCounter timeCounter = InternationalStorageTimeCounter.createTimeCounter(part);
        try {
            return international.get(name, parameters);
        } finally {
            timeCounter.stop();
        }
    }

    private final International international;
    private final String part;

}
