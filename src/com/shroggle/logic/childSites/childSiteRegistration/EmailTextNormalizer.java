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
package com.shroggle.logic.childSites.childSiteRegistration;

import com.shroggle.entity.DraftChildSiteRegistration;
import com.shroggle.logic.video.SourceVideoSizeCreator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.ServiceLocator;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 */
public class EmailTextNormalizer {

    public static void execute() {
        final Logger logger = Logger.getLogger(SourceVideoSizeCreator.class.getName());
        logger.log(Level.INFO, "EmailTextNormalizer started!");
        final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
        final Persistance persistance = ServiceLocator.getPersistance();
        persistance.inContext(new PersistanceContext<Void>() {
            @Override
            public Void execute() {
                for (final DraftChildSiteRegistration childSiteRegistration : persistance.getAllChildSiteRegistrations()) {
                    if (!childSiteRegistration.getEmailText().contains("&lt;fee for 250mb membership at network price&gt;") &&
                            !childSiteRegistration.getEmailText().contains("&lt;automatic account activation and shows The Set Up a New Site page&gt;")) {
                        continue;
                    }
                    logger.log(Level.INFO, "Replace old email text by new one for child site registration with id = " + childSiteRegistration.getFormId());
                    persistanceTransaction.execute(new Runnable() {
                        public void run() {
                            String correctEmailText = childSiteRegistration.getEmailText();
                            correctEmailText = correctEmailText.replace("&lt;fee for 250mb membership at network price&gt;", "&lt;fee for membership at network price&gt;");
                            correctEmailText = correctEmailText.replace("&lt;automatic account activation and shows The Set Up a New Site page&gt;", "&lt;account activation link&gt;");
                            childSiteRegistration.setEmailText(correctEmailText);
                        }
                    });
                }
                return null;
            }
        });
        logger.log(Level.INFO, "EmailTextNormalizer finished!");
    }

}
