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
package com.shroggle.logic.user;

import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestAllEntity;
import com.shroggle.util.process.synchronize.SynchronizeRequestComposit;

import java.util.Date;
import java.util.TimerTask;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class ExpireUserDeleterTaskReal extends TimerTask {

    public void run() {
        try {
            runInTryCatch();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void runInTryCatch() throws Exception {
        final SynchronizeRequest request = new SynchronizeRequestComposit(
                new SynchronizeRequestAllEntity(User.class));
        ServiceLocator.getSynchronize().execute(request, new SynchronizeContext<Void>() {

            public Void execute() throws Exception {
                runInSynchronize();
                return null;
            }

        });
    }

    private void runInSynchronize() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final Persistance persistance = ServiceLocator.getPersistance();
        final Date expireDate = new Date(System.currentTimeMillis() - config.getExpireUserTime());
        final List<User> users = persistance.getNotActivatedUsers(expireDate, config.getExpireUserDeleteCount());
        if (users.isEmpty()) {
            logger.info("Didn't find expired users. =(");
        }

        for (final User user : users) {
            logger.info("Start delete expired user " + user.getUserId());
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

                public void run() {
                    persistance.removeUser(user);
                }

            });
            logger.info("Success deleted expired user " + user.getUserId());
        }
    }

    private final Logger logger = Logger.getLogger(ExpireUserDeleterTaskReal.class.getSimpleName());

}
