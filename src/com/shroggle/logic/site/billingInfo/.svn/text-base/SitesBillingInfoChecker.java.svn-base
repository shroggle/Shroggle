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
package com.shroggle.logic.site.billingInfo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.07.2009
 *         Time: 16:12:58
 */
public abstract class SitesBillingInfoChecker extends TimerTask {

    private final Timer timer;
    private final long delay;
    private final long period;

    protected SitesBillingInfoChecker(final long delay, final long period) {
        timer = new Timer();
        this.delay = delay;
        this.period = period;
    }

    public void execute() {
        timer.schedule(this, delay, period);
    }

    public void destroy() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
