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
package com.shroggle.util.process.synchronize;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 23.10.2008
 */
public class MockSynchronize implements Synchronize {

    public <T> T execute(final SynchronizeRequest request, final SynchronizeContext<T> context) throws Exception {
        this.request = request;
        return context.execute();
    }

    public SynchronizeRequest getRequest() {
        return request;
    }

    private SynchronizeRequest request;

}
