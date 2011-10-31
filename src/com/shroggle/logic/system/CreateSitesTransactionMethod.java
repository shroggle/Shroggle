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
package com.shroggle.logic.system;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTransaction;

/**
 * @author Artem Stasuk
 */
public class CreateSitesTransactionMethod implements CreateSitesMethod {

    public CreateSitesTransactionMethod(final CreateSitesMethod method) {
        this.method = method;
    }

    @Override
    public void execute(final CreateSitesStatus status) {
        persistance.inContext(new PersistanceContext<Void>() {

            @Override
            public Void execute() {
                persistanceTransaction.execute(new Runnable() {

                    @Override
                    public void run() {
                        method.execute(status);
                    }

                });
                return null;
            }

        });
    }

    @Override
    public String toString() {
        return method.toString();
    }

    private final CreateSitesMethod method;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}