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

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class UpdatesExecutorReal extends UpdatesExecutor {

    public UpdatesExecutorReal(Updates updates) {
        if (updates == null) {
            throw new IllegalArgumentException("Unable to create updates executor without updates.");
        }
        this.updates = updates;
    }

    @Override
    public void execute() {
        while (updates.hasNext()) {
            final Update update = updates.next();
            logger.info("Executing update #" + update.getVersion() + "...");
            update.execute();
            logger.info("Update #" + update.getVersion() + " executed successfully, adding its version to DB...");
            setCurrentDataVersion(update.getVersion());
            logger.info(update.getVersion() + " update`s version successfully added to DB.\n\n");
        }
        logger.info("All updates has been executed successfully.");
    }

    private final Updates updates;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
