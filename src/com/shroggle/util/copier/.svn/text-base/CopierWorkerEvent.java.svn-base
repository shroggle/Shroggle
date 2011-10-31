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

import java.util.List;
import java.util.ArrayList;

/**
 * @author Artem Stasuk
 */
public class CopierWorkerEvent implements CopierWorker {

    public CopierWorkerEvent(CopierWorker worker) {
        this.worker = worker;
    }

    public CopierWorkerEvent(CopierWorker worker, CopierAfterListener afterListener) {
        this.worker = worker;
        this.afterListeners.add(afterListener);
    }

    public void addAfterListener(CopierAfterListener afterListener) {
        afterListeners.add(afterListener);
    }

    @Override
    public void copy(CopierItem item) {
        worker.copy(item);

        for (final CopierAfterListener listener : afterListeners) {
            listener.execute(item.getOriginalValue(), item.getCopyValue());
        }
    }

    private final CopierWorker worker;
    private final List<CopierAfterListener> afterListeners = new ArrayList<CopierAfterListener>();

}
