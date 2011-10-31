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
package com.shroggle.logic.form.export;

import com.shroggle.entity.FormExportTask;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Balakirev Anatoliy
 */
public class FormExportTaskScheduler extends TimerTask {

    private final Timer timer;

    public FormExportTaskScheduler(final long delay, final long period) {
        timer = new Timer();
        timer.schedule(this, delay, period);
    }

    public void destroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void run() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {
            public Void execute() {
                for (FormExportTask formExportTask : ServiceLocator.getPersistance().getAllFormExportTasks()) {
                    new FormExportTaskManager(formExportTask).sendDataIfNeeded();
                }
                return null;
            }
        });
    }

}
