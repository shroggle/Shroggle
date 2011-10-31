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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
class CopierWorkerHistoryItem {

    public CopierWorkerHistoryItem() {

    }

    public void upLevel() {
        level++;
    }

    public void downLevel() {
        level--;
    }

    public boolean isFinish() {
        return level < 1;
    }

    public CopierItem getInHistory(Object original) {
        return history.get(original);
    }

    public void addInHistory(Object originalValue, CopierItem copyItem) {
        history.put(originalValue, copyItem);
    }

    private final Map<Object, CopierItem> history = new HashMap<Object, CopierItem>();
    private int level;

}
