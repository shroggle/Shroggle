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
package com.shroggle.presentation;

import com.shroggle.TestBaseWithMockService;

/**
 * @author Artem Stasuk
 */
public abstract class TestActionOrService<T> extends TestBaseWithMockService {

    public TestActionOrService(final Class<T> actionOrServiceClass) {
        try {
            actionOrService = actionOrServiceClass.newInstance();
        } catch (final InstantiationException exception) {
            throw new UnsupportedOperationException(exception);
        } catch (final IllegalAccessException exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

    protected final T actionOrService;

}
