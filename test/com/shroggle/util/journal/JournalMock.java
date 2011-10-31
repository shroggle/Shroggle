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

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class JournalMock implements Journal {

    public void add(final List<JournalItem> journalItems) {
        this.journalItems = journalItems;
    }

    public void destroy() {
        destroyed = true;
    }

    public List<JournalItem> get() {
        return journalItems;
    }

    public boolean isDestroy() {
        return destroyed;
    }

    private boolean destroyed;
    private List<JournalItem> journalItems;

}
