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
public class CopierWorkerHistory implements CopierWorker {

    public CopierWorkerHistory(CopierWorker worker) {
        this.worker = worker;
    }

    @Override
    public void copy(CopierItem item) {
        CopierWorkerHistoryItem history = localHistory.get();
        if (history == null) {
            history = new CopierWorkerHistoryItem();
            localHistory.set(history);
        }

        history.upLevel();
        try {
            CopierItem copierItem = history.getInHistory(item.getOriginalValue());
            if (copierItem == null) {
                history.addInHistory(item.getOriginalValue(), item);
                worker.copy(item);
            } else {
                item.setCopyValue(copierItem.getCopyValue());
            }
        } finally {
            history.downLevel();

            if (history.isFinish()) {
                localHistory.remove();
            }
        }
    }

    private final CopierWorker worker;
    private final ThreadLocal<CopierWorkerHistoryItem> localHistory = new ThreadLocal<CopierWorkerHistoryItem>();

}
