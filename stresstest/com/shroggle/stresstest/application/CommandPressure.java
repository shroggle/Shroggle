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
package com.shroggle.stresstest.application;

import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class CommandPressure implements Command {

    public CommandPressure(final String server) {
        this.server = server;
    }

    @Override
    public void execute() throws Exception {
        logger.info("Start on server: " + server);

        new CommandLoop(1, new CommandThreads(2,
                new CommandPresentationPages(server),
//                new CommandSites(server, "artem.stasuk%40gmail.com", "1"))
                new CommandSites(server, "mstern%40sidratech.com", "11"))
        ).execute();

        logger.info("Finish on server: " + server);
    }

    private final String server;
    private final static Logger logger = Logger.getLogger(CommandPressure.class.getSimpleName());

}
