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

import com.shroggle.util.process.synchronize.Synchronize;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeRequest;

/**
 * We create a path to the object root and then check whether this object is
 * available (unlocked).
 * In this particular case we are working with account.
 * If everything is OK (object is available) the path is stored with access
 * flag and them we perform the required operation.
 * <p/>
 * Page1 -> Site2 -> User1
 * Page3 -> Site1 -> User1
 * <p/>
 * LockKey {region: ACCOUNT; id: 12}
 * <p/>
 * The common LockKey {region: ACCOUNT; id: 12; readingCount: 1; writing: false}
 *
 * @author Stasuk Artem
 */
public class ClassicSynchronize implements Synchronize {

    public <T> T execute(SynchronizeRequest request, SynchronizeContext<T> context) throws Exception {
        if (request == null) {
            throw new NullPointerException(
                    "Can't execute synchronize on null request!");
        }
        if (context == null) {
            throw new NullPointerException(
                    "Can't execute synchronize for null context!");
        }

        final ClassicSynchronizeRequest internalRequest = new ClassicSynchronizeRequest(request, heap);
        // wait free needs points and return ticker for unlock
        final ClassicSynchronizeTicker ticker = internalRequest.getTicker();
        try {
            // This is the required execution
            return context.execute();
        } finally {
            ticker.finish();
        }
    }

    private final ClassicSynchronizeHeap heap = new ClassicSynchronizeHeap();

}
