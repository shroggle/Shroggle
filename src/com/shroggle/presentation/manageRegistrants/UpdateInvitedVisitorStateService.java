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

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.util.List;
import java.io.IOException;

import com.shroggle.logic.user.UsersManager;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateInvitedVisitorStateService extends AbstractService {

    @RemoteMethod
    public void execute(final List<Integer> visitorIdsInvited, final List<Integer> visitorIdsRegistered, final int siteId) throws IOException, ServletException {
        new UsersManager().getLogined();

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                for (int visitorId : visitorIdsInvited) {
                    final User visitor = persistance.getUserById(visitorId);
                    UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(visitor.getUserId(), siteId);
                    visitorOnSiteRight.setInvited(true);
                }

                for (int visitorId : visitorIdsRegistered) {
                    final User visitor = persistance.getUserById(visitorId);
                    UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(visitor.getUserId(), siteId);
                    visitorOnSiteRight.setInvited(false);
                }
            }
        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
