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
package com.shroggle.presentation.site.payment;

import com.shroggle.entity.IPNLog;
import com.shroggle.util.ServiceLocator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author dmitry.solomadin
 */
public class IPNLogger {

    public static void log(final HttpServletRequest request) {
        String parameters = "";
        Set<Map.Entry<String, String[]>> parameterEntrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> parameterEntry : parameterEntrySet) {
            parameters = parameters + parameterEntry.getKey() + "=" + (parameterEntry.getValue().length > 0 ? parameterEntry.getValue()[0] + "&" : "");
        }

        final String notification = parameters;

        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                IPNLog log = new IPNLog();
                log.setNotification(notification);

                ServiceLocator.getPersistance().putIPNLog(log);
            }
        });
    }

}
