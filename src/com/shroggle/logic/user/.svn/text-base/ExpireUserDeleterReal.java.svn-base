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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextTimerTask;
import com.shroggle.util.persistance.PersistanceTimerTaskWithContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Delete expired users and their accounts if it not use other. This is heavy code because
 * every work it lock all accounts and users.
 *
 * @author Artem Stasuk
 */
public class ExpireUserDeleterReal implements ExpireUserDeleter {

    public ExpireUserDeleterReal() {
        timer = new Timer("expireUserDeleter", true);
        final long expireUserTime = ServiceLocator.getConfigStorage().get().getExpireUserTime();
        final long expireUserTimePart = expireUserTime / 5;
        TimerTask timerTask = new ExpireUserDeleterTaskReal();
        timerTask = new ContextTimerTask(timerTask);
        timerTask = new PersistanceTimerTaskWithContext(timerTask);
        timer.schedule(timerTask, expireUserTimePart, expireUserTimePart);
    }

    public void destroy() {
        timer.cancel();
    }

    private final Timer timer;

}
