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
package com.shroggle.util.process.timecounter;

import java.util.Collection;

/**
 * @author Artem Stasuk
 */
public class TimeCounterCreatorPrefix implements TimeCounterCreator {

    public TimeCounterCreatorPrefix(final String prefix, final TimeCounterCreator timeCounterCreator) {
        this.prefix = prefix;
        this.timeCounterCreator = timeCounterCreator;
    }

    @Override
    public TimeCounter create(final String name) {
        return timeCounterCreator.create(prefix + name);
    }

    @Override
    public Collection<TimeCounterResult> getResults() {
        return timeCounterCreator.getResults();
    }

    @Override
    public void clear() {
        timeCounterCreator.clear();
    }

    private final String prefix;
    private final TimeCounterCreator timeCounterCreator;

}
