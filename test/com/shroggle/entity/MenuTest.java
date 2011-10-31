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
package com.shroggle.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class MenuTest {

    @Test
    public void defaultValues() {
        final DraftMenu menu = new DraftMenu();

        Assert.assertEquals(MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL, menu.getMenuStyleType());
        Assert.assertNull(null);
        Assert.assertEquals(-1, menu.getId());
    }

}
