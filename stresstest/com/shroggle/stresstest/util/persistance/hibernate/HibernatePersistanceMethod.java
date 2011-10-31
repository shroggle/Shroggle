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
package com.shroggle.stresstest.util.persistance.hibernate;

import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.FileConfigStorage;
import com.shroggle.util.persistance.hibernate.HibernatePersistance;
import com.shroggle.util.persistance.hibernate.HibernateTransaction;

/**
 * @author Stasuk Artem
 */
public abstract class HibernatePersistanceMethod {

    public void run() {
        final String configPath = IOUtil.baseDir() + "/../superWikiStressTestConfig.xml";
        ServiceLocator.setConfigStorage(new FileConfigStorage(configPath));
        ServiceLocator.setPersistance(new HibernatePersistance());
        ServiceLocator.setPersistanceTransaction(new HibernateTransaction());
        try {
            for (int i = 0; i < getIterations(); i++) {
                execute();
            }
        } finally {
            if (ServiceLocator.getPersistance() != null) {
                ServiceLocator.getPersistance().destroy();
            }
        }
    }

    protected abstract int getIterations();

    protected abstract void execute();

}
