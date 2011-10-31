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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TripleDESCrypter;
import com.shroggle.util.config.FileConfigStorage;
import com.shroggle.util.reflection.ClassesSimple;

/**
 * @author Artem Stasuk
 */
public class HibernateTestBase {

    public HibernateTestBase() {
        final String configPath = IOUtil.baseDir() + "/../superWikiTestPersistanceConfig.xml";
        ServiceLocator.setClasses(new ClassesSimple());
        ServiceLocator.setConfigStorage(new FileConfigStorage(configPath));
        ServiceLocator.setTripleDESCrypter(new TripleDESCrypter(ServiceLocator.getConfigStorage().get().getEncryptionKey()));
        ServiceLocator.setPersistance(new HibernatePersistance());
        ServiceLocator.setPersistanceTransaction(new HibernateTransaction());
    }

    public void destroy() {
        if (ServiceLocator.getPersistance() != null) {
            ServiceLocator.getPersistance().destroy();
        }
    }

}
