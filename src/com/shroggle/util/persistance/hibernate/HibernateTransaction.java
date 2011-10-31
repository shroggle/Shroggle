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

import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

public class HibernateTransaction implements PersistanceTransaction {

    public void execute(Runnable transactionContext) {
        HibernateManager.inTransaction(transactionContext);
    }

    public <R> R execute(PersistanceTransactionContext<R> context) {
        return HibernateManager.inTransaction(context);
    }

}