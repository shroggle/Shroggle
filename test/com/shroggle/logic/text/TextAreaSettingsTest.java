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
package com.shroggle.logic.text;

import org.junit.Test;
import org.junit.Assert;
import com.shroggle.entity.FormItemType;

/**
 * @author Balakirev Anatoliy
 */
public class TextAreaSettingsTest {

    @Test
    public void testGetTextAreaSettings_TEXT_AREA() {
        TextAreaSettings textAreaSettings = TextAreaSettings.getTextAreaSettings(FormItemType.TEXT_AREA);
        Assert.assertEquals(4, textAreaSettings.getRows());
        Assert.assertEquals(17, textAreaSettings.getCols());
    }

    @Test
    public void testGetTextAreaSettings_TEXT_AREA_DOUBLE_SIZE() {
        TextAreaSettings textAreaSettings = TextAreaSettings.getTextAreaSettings(FormItemType.TEXT_AREA_DOUBLE_SIZE);
        Assert.assertEquals(8, textAreaSettings.getRows());
        Assert.assertEquals(40, textAreaSettings.getCols());
    }


    @Test
    public void testGetTextAreaSettings_wrongType() {
        TextAreaSettings textAreaSettings = TextAreaSettings.getTextAreaSettings(FormItemType.CHECKBOX);
        Assert.assertEquals(4, textAreaSettings.getRows());
        Assert.assertEquals(17, textAreaSettings.getCols());
    }

    @Test
    public void testGetTextAreaSettings_withoutType() {
        TextAreaSettings textAreaSettings = TextAreaSettings.getTextAreaSettings(null);
        Assert.assertEquals(4, textAreaSettings.getRows());
        Assert.assertEquals(17, textAreaSettings.getCols());
    }

}
