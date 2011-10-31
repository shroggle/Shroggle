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
package com.shroggle.util.journal;

import com.shroggle.entity.JournalItem;
import com.shroggle.util.process.ThreadUtil;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class JournalSleep implements Journal {

    public void add(final List<JournalItem> journalItems) {
        ThreadUtil.sleep(sleep);
    }

    public void destroy() {
    }

    public void setSleep(final long sleep) {
        this.sleep = sleep;
    }

    private long sleep;

}
