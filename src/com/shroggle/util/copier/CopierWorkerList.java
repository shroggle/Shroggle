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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierWorkerList implements CopierWorker {

    public CopierWorkerList(final CopierExplorer explorer, final CopierWorker worker) {
        this.explorer = explorer;
        this.worker = worker;
    }

    @Override
    public void copy(CopierItem item) {
        if (item.getOriginalValue() instanceof List) {
            final List original = (List) item.getOriginalValue();
            final List<Object> copy = new ArrayList<Object>();
            item.setCopyValue(copy);
            for (final CopierItem childItem : explorer.find(original, copy)) {
                worker.copy(childItem);
            }
        }
    }

    private final CopierExplorer explorer;
    private final CopierWorker worker;

}
