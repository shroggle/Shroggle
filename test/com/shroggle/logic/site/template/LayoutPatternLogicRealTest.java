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
import com.shroggle.entity.LayoutPattern;
import com.shroggle.entity.PatternPosition;
import com.shroggle.entity.ItemType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class LayoutPatternLogicRealTest {

    @Test
    public void getLayout() {
        Layout layout = new Layout();
        LayoutPattern pattern = new LayoutPattern();
        pattern.setLayout(layout);
        LayoutPatternLogic patternLogic = new LayoutPatternLogicReal(pattern);

        Assert.assertEquals(layout, patternLogic.getLayout());
    }

    @Test
    public void getPositionNotFound() {
        Layout layout = new Layout();
        LayoutPattern pattern = new LayoutPattern();
        pattern.setLayout(layout);
        LayoutPatternLogic patternLogic = new LayoutPatternLogicReal(pattern);

        Assert.assertEquals(1, patternLogic.getPosition(null));
    }

    @Test
    public void getPositionDefault() {
        Layout layout = new Layout();
        LayoutPattern pattern = new LayoutPattern();
        pattern.setLayout(layout);
        PatternPosition position = new PatternPosition();
        position.setPosition(23);
        pattern.getPositions().add(position);
        LayoutPatternLogic patternLogic = new LayoutPatternLogicReal(pattern);

        Assert.assertEquals(23, patternLogic.getPosition(null));
    }

    @Test
    public void getPosition() {
        Layout layout = new Layout();
        LayoutPattern pattern = new LayoutPattern();
        pattern.setLayout(layout);
        PatternPosition position = new PatternPosition();
        position.setPosition(23);
        position.setType(ItemType.BLOG);
        pattern.getPositions().add(position);
        PatternPosition defaultPosition = new PatternPosition();
        defaultPosition.setPosition(2);
        pattern.getPositions().add(defaultPosition);
        LayoutPatternLogic patternLogic = new LayoutPatternLogicReal(pattern);

        Assert.assertEquals(23, patternLogic.getPosition(ItemType.BLOG));
    }

}
