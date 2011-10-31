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
package com.shroggle.logic.start;

import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class RemoveVisitorsFromGroupsTask extends TimerTask {

    public void execute() {
        final long delay = DateUtil.getMillisToMidnight(new Date().getTime());
        final long period = 12 * 60 * 60 * 1000L;// Twelve hours.
        timer.schedule(this, delay, period);
        logger.info("Checking expired users access to groups will be started after " + delay + "ms.");
    }

    public void destroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void run() {
        logger.info("Checking expired users access to groups...");
        final Persistance persistance = ServiceLocator.getPersistance();
        persistance.inContext(new PersistanceContext<Void>() {
            public Void execute() {
                for (User user : persistance.getAllUsers()) {
                    new UsersGroupManager(user).removeExpiredAccessToGroups();
                }
                return null;
            }
        });
        logger.info("All expired users access to groups has been removed successfully.");
    }

    private final Timer timer = new Timer();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
