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
package com.shroggle.logic.site;



import com.shroggle.entity.Site;
import com.shroggle.entity.ThemeId;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TripleDESCrypter;
import com.shroggle.util.config.FileConfigStorage;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.hibernate.HibernatePersistance;
import com.shroggle.util.persistance.hibernate.HibernateTransaction;
import com.shroggle.util.reflection.ClassesSimple;
import junit.framework.Assert;

/**
 * @author Artem Stasuk
 */
public class SiteByUrlGetterSpeedRun {

    public static void main(final String[] args) {
        init();
        execute();
        destroy();
    }

    private static void execute() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {

            @Override
            public Void execute() {
                ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            final Site site = new Site();
                            site.getSitePaymentSettings().setUserId(-1);
                            site.setThemeId(new ThemeId("a", "b"));
                            site.setTitle("s" + i);
                            site.setSubDomain("s" + i);
                            //            site.setCustomUrl("s" + i);
                            ServiceLocator.getPersistance().putSite(site);
                        }
                    }

                });
                return null;
            }

        });

        final SiteByUrlGetter[] siteByUrlGetters = new SiteByUrlGetter[]{
                new SiteByUrlGetterReal(),
                new SiteByUrlGetterCache(new SiteByUrlGetterReal())};

        for (final SiteByUrlGetter siteByUrlGetter : siteByUrlGetters) {

            final long start = System.currentTimeMillis();
            final int GET_COUNT = 100;
            for (int i = 0; i < GET_COUNT; i++) {
                ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {

                    @Override
                    public Void execute() {
                        Assert.assertNotNull(siteByUrlGetter.get("s5.shroggle.com"));
                        return null;
                    }

                });
            }
            System.out.println(siteByUrlGetter + ", calls: " + GET_COUNT + ", time: " + (System.currentTimeMillis() - start) + " msec");
        }

    }

    private static void destroy() {
        ServiceLocator.getPersistance().destroy();
    }

    private static void init() {
        final String configPath = IOUtil.baseDir() + "/../superWikiTestPersistanceConfig.xml";
        ServiceLocator.setClasses(new ClassesSimple());
        ServiceLocator.setConfigStorage(new FileConfigStorage(configPath));
        ServiceLocator.setTripleDESCrypter(new TripleDESCrypter(ServiceLocator.getConfigStorage().get().getEncryptionKey()));
        ServiceLocator.setPersistance(new HibernatePersistance());
        ServiceLocator.setPersistanceTransaction(new HibernateTransaction());
    }

}
