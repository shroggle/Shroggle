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
package com.shroggle.util.copier;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class CopierWorkerStack implements CopierWorker {

    public CopierWorkerStack(final CopierWorker... workers) {
        this.workers = Arrays.copyOf(workers, workers.length);
    }

    @Override
    public void copy(final CopierItem item) {
        for (CopierWorker worker : workers) {
            worker.copy(item);

            if (item.isSet()) {
                return;
            }
        }
    }

    private final CopierWorker[] workers;

}