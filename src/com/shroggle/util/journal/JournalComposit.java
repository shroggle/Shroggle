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

import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class JournalComposit implements Journal {

    public JournalComposit(final List<Journal> journals) {
        this.journals = Collections.unmodifiableList(journals);
    }

    public void add(final List<JournalItem> journalItems) {
        for (final Journal journal : journals) {
            journal.add(journalItems);
        }
    }

    public void destroy() {
        for (final Journal journal : journals) {
            journal.destroy();
        }
    }

    private final List<Journal> journals;

}
