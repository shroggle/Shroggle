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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.entity.User;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
// todo. Add tests
@RemoteProxy
public class DeleteVisitorService extends AbstractService {

    @RemoteMethod
    public void execute(final List<Integer> visitorsId, final int siteId) {
        new UsersManager().getLogined();

        for (Integer visitorId : visitorsId) {
            final User visitorToDelete = persistance.getUserById(visitorId);
            if (visitorToDelete == null) {
                throw new UserNotFoundException("Cannot find user by Id=" + visitorId);
            }

            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    final VisitorManager visitorManager = new VisitorManager(visitorToDelete);

                    visitorManager.removeVisitorOnSiteRight(siteId);
                    visitorManager.removeVisitor();
                }
            });
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
