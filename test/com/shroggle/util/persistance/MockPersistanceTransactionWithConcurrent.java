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

package com.shroggle.util.persistance;

/**
 * Paused first enter thread on some time
 *
 * @author Stasuk Artem
 */
public class MockPersistanceTransactionWithConcurrent implements PersistanceTransaction {

    public void execute(Runnable runnable) {
        System.out.println("Enter " + Thread.currentThread());
        final boolean tempFirstEnter;
        synchronized (this) {
            tempFirstEnter = firstEnter;
            firstEnter = false;
        }
        if (tempFirstEnter) {
            try {
                System.out.println("Start sleep for " + Thread.currentThread());
                Thread.sleep(2000);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Run " + Thread.currentThread());
        runnable.run();
    }

    public <R> R execute(PersistanceTransactionContext<R> context) {
        return context.execute();
    }

    private static boolean firstEnter = true;

}
