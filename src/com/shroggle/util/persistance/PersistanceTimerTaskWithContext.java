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
package com.shroggle.util.persistance;

import com.shroggle.util.ServiceLocator;

import java.util.TimerTask;

/**
 * Open persistance context on every run timer task.
 *
 * @author Artem Stasuk
 */
public class PersistanceTimerTaskWithContext extends TimerTask {

    public PersistanceTimerTaskWithContext(final TimerTask timerTask) {
        this.timerTask = timerTask;
    }


    public final void run() {
        final Persistance persistance = ServiceLocator.getPersistance();
        if (persistance == null) {
            throw new UnsupportedOperationException(
                    "Can't find persistance in service locator!");
        }
        persistance.inContext(new PersistanceContext<Void>() {

            public Void execute() {
                timerTask.run();
                return null;
            }

        });
    }

    private final TimerTask timerTask;

}
