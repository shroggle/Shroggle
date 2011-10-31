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

package com.shroggle.util.process.synchronize.classic;

import com.shroggle.util.process.synchronize.SynchronizeMethod;

/**
 * @author Stasuk Artem
 */
final class ClassicSynchronizePoint {

    public boolean isAllow(SynchronizeMethod method) {
        if (method == SynchronizeMethod.READ) {
            return !writing;
        } else {
            return readingCount == 0 && !writing;
        }
    }

    public void finish(SynchronizeMethod method) {
        if (method == SynchronizeMethod.READ) {
            readingCount--;
        } else {
            writing = false;
        }
    }

    public void start(SynchronizeMethod method) {
        if (method == SynchronizeMethod.READ) {
            readingCount++;
        } else {
            writing = true;
        }
    }

    public boolean isEmpty() {
        return readingCount == 0 && !writing;
    }

    private int readingCount = 0;
    private boolean writing = false;

}