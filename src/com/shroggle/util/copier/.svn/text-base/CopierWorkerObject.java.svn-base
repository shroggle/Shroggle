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
public class CopierWorkerObject implements CopierWorker {

    public CopierWorkerObject(CopierWorker worker, CopierExplorer explorer) {
        this.worker = worker;
        this.explorer = explorer;
    }

    @Override
    public void copy(CopierItem item) {
        final Object copy;
        try {
            copy = item.getOriginalValue().getClass().newInstance();
        } catch (InstantiationException e) {
            throw new CopierException(e);
        } catch (IllegalAccessException e) {
            throw new CopierException(e);
        }

        /**
         * Very important that worker set copy object in need field or method before
         * copy structure. It need for correct work history copier and correct
         * process cyclic relation. 
         */
        item.setCopyValue(copy);

        for (final CopierItem childItem : explorer.find(item.getOriginalValue(), copy)) {
            worker.copy(childItem);
        }
    }

    private final CopierExplorer explorer;
    private final CopierWorker worker;

}
