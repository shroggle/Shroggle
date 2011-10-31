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

import com.shroggle.entity.PageSettings;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTransaction;


/**
 * @author Stasuk Artem
 * @see StartWeb
 */

public class StartUpdate {

    /**
     * Entry point in application for run all updates for this application version.
     *
     * @param args - parameters: config path and version.
     */
    public static void main(final String[] args) {
        System.getProperties().put("com.shroggle.util.config.configFile", args[0]);
        final Start start = new Start();
        try {
            start.initConfig();
            start.initUtil();
            start.initPersistance();
            executeInMain();
        } finally {
            start.destroy();
        }
    }

    static void executeInMain() {
        final Persistance persistance = ServiceLocator.getPersistance();
        final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

        persistance.inContext(new PersistanceContext<Void>() {

            @Override
            public Void execute() {

                persistanceTransaction.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (PageSettings pageSettings : persistance.getPageSettingsWithHtmlOrCss()) {
                            String replacement = replaceIfContains(pageSettings.getHtml(), "Shroggle", "Web-Deva");
                            if (replacement != null) {
                                pageSettings.setHtml(replacement);
                            }
                            replacement = replaceIfContains(pageSettings.getHtml(), " Design by Blackwave", "");
                            if (replacement != null) {
                                pageSettings.setHtml(replacement);
                            }
                        }
                    }
                });
                return null;
            }

        });
    }

    private static String replaceIfContains(final String container, final String oldValue, final String newValue) {
        if (container != null && container.contains(oldValue)) {
            return container.replaceAll(oldValue, newValue);
        } else {
            return null;
        }
    }

}


