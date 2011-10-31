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
public class CreateSitesManyMethod implements CreateSitesMethod {

    public CreateSitesManyMethod(final CreateSitesMethod method, final int count) {
        this.count = count;
        this.method = method;
    }

    @Override
    public void execute(final CreateSitesStatus status) {
        status.push("count: " + count);

        try {
            int left = count;
            for (int i = 0; i < count; i++) {
                final long start = System.currentTimeMillis();

                try {
                    method.execute(status);
                    left = count - (i + 1);
                } finally {
                    long delta = System.currentTimeMillis() - start;
                    status.change("count: " + count + (left > 0 ? ", left: " + left : "")
                            + ", process one method: " + delta + " msec");
                }
            }
        } finally {
            status.pop();
        }
    }

    private final CreateSitesMethod method;
    private final int count;

}