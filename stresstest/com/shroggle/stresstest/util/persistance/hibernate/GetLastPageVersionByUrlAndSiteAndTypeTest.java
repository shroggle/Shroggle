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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceContext;

/**
 * @author Stasuk Artem
 */
public class GetLastPageVersionByUrlAndSiteAndTypeTest extends HibernatePersistanceMethod {

    public static void main(String[] args) {
        new GetLastPageVersionByUrlAndSiteAndTypeTest().run();
    }

    @Override
    public int getIterations() {
        return 100;
    }

    @Override
    public void execute() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {

            @Override
            public Void execute() {
//                ServiceLocator.getPersistance().getLastPageVersionByUrlAndSiteAndType();

                return null;
            }

        });
    }

}
