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
package com.shroggle.logic.fontsAndColors;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.FontsAndColors;
import com.shroggle.entity.FontsAndColorsValue;
import com.shroggle.presentation.site.cssParameter.CreateFontsAndColorsRequest;
import com.shroggle.presentation.site.cssParameter.CssParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FontsAndColorsManagerTest {
    @Test
    public void testUpdateValues() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);

        final FontsAndColorsValue value1 = TestUtil.createFontsAndColorsValue(fontsAndColors, "name1", "selector1", "value1");
        final FontsAndColorsValue value2 = TestUtil.createFontsAndColorsValue(fontsAndColors, "name2", "selector2", "value2");


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name1", "selector1", "value_new");
        final CssParameter cssParameter2 = TestUtil.createCssParameter("name2", "selector2", "value2_new");
        final CssParameter cssParameter3 = TestUtil.createCssParameter("name3", "selector3", "value3_new");

        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1, cssParameter2, cssParameter3));
        new FontsAndColorsManager(fontsAndColors).updateValues(request);


        final FontsAndColorsManager manager = new FontsAndColorsManager(fontsAndColors);
        Assert.assertEquals(3, fontsAndColors.getCssValues().size());

        Assert.assertEquals("value_new", manager.getFontsAndColorsValue("name1", "selector1").getValue());
        Assert.assertEquals("FontsAndColorsValue must be tha same. Only it`s value must be updated.",
                value1.getId(), manager.getFontsAndColorsValue("name1", "selector1").getId());

        Assert.assertEquals("value2_new", manager.getFontsAndColorsValue("name2", "selector2").getValue());
        Assert.assertEquals("FontsAndColorsValue must be tha same. Only it`s value must be updated.",
                value2.getId(), manager.getFontsAndColorsValue("name2", "selector2").getId());

        Assert.assertEquals("Not existing before value must be added.", "value3_new", manager.getFontsAndColorsValue("name3", "selector3").getValue());
    }
    
    @Test
    public void testUpdateValues_removeOld() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);

        final FontsAndColorsValue value1 = TestUtil.createFontsAndColorsValue(fontsAndColors, "name1", "selector1", "value1");


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name1", "selector1", "");

        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1));
        new FontsAndColorsManager(fontsAndColors).updateValues(request);


        final FontsAndColorsManager manager = new FontsAndColorsManager(fontsAndColors);
        Assert.assertEquals(1, fontsAndColors.getCssValues().size());

        Assert.assertEquals("", manager.getFontsAndColorsValue("name1", "selector1").getValue());
        Assert.assertEquals("FontsAndColorsValue must be tha same. Only it`s value must be updated.",
                value1.getId(), manager.getFontsAndColorsValue("name1", "selector1").getId());
    }

    @Test
    public void testGetFontsAndColorsValue() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);

        TestUtil.createFontsAndColorsValue(fontsAndColors, "name1", "selector1", "value1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "name2", "selector2", "value2");

        final FontsAndColorsValueManager manager = new FontsAndColorsManager(fontsAndColors).getFontsAndColorsValue("name1", "selector1");
        Assert.assertEquals("name1", manager.getName());
        Assert.assertEquals("selector1", manager.getSelector());
        Assert.assertEquals("value1", manager.getValue());
    }

    @Test
    public void testGetFontsAndColorsValue_withWrongName() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);

        TestUtil.createFontsAndColorsValue(fontsAndColors, "name1", "selector1", "value1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "name2", "selector2", "value2");

        Assert.assertEquals(null, new FontsAndColorsManager(fontsAndColors).getFontsAndColorsValue("name2", "selector1"));
    }

    @Test
    public void testGetFontsAndColorsValue_withWrongSelector() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);

        TestUtil.createFontsAndColorsValue(fontsAndColors, "name1", "selector1", "value1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "name2", "selector2", "value2");

        Assert.assertEquals(null, new FontsAndColorsManager(fontsAndColors).getFontsAndColorsValue("name1", "selector2"));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
