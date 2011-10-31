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

/**
 * @author Artem Stasuk
 */
public final class CopierWorkerWrapper implements CopierWorker {

    @Override
    public void copy(final CopierItem item) {
        worker.copy(item);
    }

    public void setWorker(final CopierWorker worker) {
        this.worker = worker;
    }

    private CopierWorker worker;

}
