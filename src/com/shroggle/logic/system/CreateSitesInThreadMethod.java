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

/**
 * @author Artem Stasuk
 */
public class CreateSitesInThreadMethod implements CreateSitesMethod {

    public CreateSitesInThreadMethod(final CreateSitesMethod method) {
        this.method = method;
    }

    @Override
    public void execute(final CreateSitesStatus status) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final long start = System.currentTimeMillis();

                status.push("work");
                try {
                    method.execute(status);

                    final long delta = System.currentTimeMillis() - start;

                    status.change("success finish, in " + delta + " msec");
                } catch (final Throwable throwable) {
                    status.change("exception: " + throwable.getMessage());
                }
            }

        }, this.getClass().getSimpleName()).start();
    }

    private final CreateSitesMethod method;

}