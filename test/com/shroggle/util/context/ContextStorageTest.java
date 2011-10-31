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
package com.shroggle.util.context;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class ContextStorageTest {

    @Before
    public void before() {
        new ContextStorage().remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setNull() {
        new ContextStorage().set(null);
    }

    @Test
    public void doubleSet() {
        new ContextStorage().set(new ContextManual());
        new ContextStorage().set(new ContextManual());
    }

}