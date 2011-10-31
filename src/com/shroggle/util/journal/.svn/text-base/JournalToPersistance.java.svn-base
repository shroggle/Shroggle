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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceContext;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class JournalToPersistance implements Journal {

    public void add(final List<JournalItem> journalItems) {
        if (journalItems == null) {
            throw new UnsupportedOperationException(
                    "Can't add null journal item!");
        }
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {

            public Void execute() {
                ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

                    public void run() {
                        for (final JournalItem journalItem : journalItems) {
                            if (journalItem.getClassName() == null) {
                                throw new UnsupportedOperationException(
                                        "Can't add journal item with null class name!");
                            }

                            ServiceLocator.getPersistance().putJournalItem(journalItem);
                        }
                    }

                });
                return null;
            }

        });
    }

    public void destroy() {
    }

}
