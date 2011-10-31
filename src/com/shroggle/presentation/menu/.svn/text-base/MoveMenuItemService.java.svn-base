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
package com.shroggle.presentation.menu;

import com.shroggle.presentation.AbstractService;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.MenuItem;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class MoveMenuItemService extends AbstractService {

    @RemoteMethod
    public void execute(final int movedItemId, final Integer parentItemId, final Integer position) {
        final MenuItem movedItem = persistance.getDraftMenuItem(movedItemId);
        if (movedItem == null) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t find moved menuItem by id = " + movedItemId);
            return;
        }
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                movedItem.setParent(persistance.getDraftMenuItem(parentItemId));
                movedItem.moveToPosition(position);
            }

        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
}