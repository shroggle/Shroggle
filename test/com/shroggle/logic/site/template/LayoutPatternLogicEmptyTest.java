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
package com.shroggle.logic.site.template;

import com.shroggle.entity.Layout;
import com.shroggle.entity.ItemType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class LayoutPatternLogicEmptyTest {

    @Test
    public void getLayout() {
        Layout layout = new Layout();
        LayoutPatternLogic patternLogic = new LayoutPatternLogicEmpty(layout);

        Assert.assertEquals(layout, patternLogic.getLayout());
    }

    @Test
    public void getPosition() {
        LayoutPatternLogic patternLogic = new LayoutPatternLogicEmpty(null);

        Assert.assertEquals(1, patternLogic.getPosition(null));
        Assert.assertEquals(1, patternLogic.getPosition(ItemType.BLOG));
    }

}
