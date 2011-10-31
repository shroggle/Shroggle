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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceContext;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.07.2009
 *         Time: 16:14:46
 */
public class SitesBillingInfoLightChecker extends SitesBillingInfoChecker {

    SitesBillingInfoLightChecker(long delay, long period) {
        super(delay, period);
    }

    public void run() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {
            public Void execute() {
                ChildSitesBillingInfoChecker.execute();
                return null;
            }
        });
    }
}
