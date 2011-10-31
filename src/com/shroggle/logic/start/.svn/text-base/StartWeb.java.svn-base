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

import com.shroggle.util.IOUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartWeb implements ServletContextListener {

    public void contextInitialized(final ServletContextEvent sce) {
        logger.info("Start init application...");
        start = new Start();

        try {
            IOUtil.printDefaultEncoding();
            final String configFile = start.initConfig();
            start.initUtil();

            Updates.getExecutor(configFile).execute();

            start.initPersistance();
            start.initJournals();
            start.initFileSystem();
            start.initFilesWriter();
            start.initMailSender();

            start.initPresentation();

            start.initCache();
            start.initPay();
            start.initFormExportTaskScheduler();
            start.initCron();
            start.initVisitorsGroupsChecker();
            start.addRightsForAppAdmins();

            logger.info("Success started application.");
        } catch (final Exception exception) {
            logger.log(Level.SEVERE, "", exception);
            throw new RuntimeException(exception);
        } finally {
            logger.info("Finish started application.");
        }
    }

    public void contextDestroyed(final ServletContextEvent sce) {
        logger.info("Start destroy application...");
        start.destroy();
        logger.info("Success destroyed application.");
    }

    private Start start;
    private final Logger logger = Logger.getLogger(StartWeb.class.getName());

}
