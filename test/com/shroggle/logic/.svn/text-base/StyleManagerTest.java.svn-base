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

package com.shroggle.logic;

import com.shroggle.TestRunnerWithMockServices;
import org.junit.Test;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import junit.framework.Assert;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class StyleManagerTest {

    @Test
    public void testCreateNew() throws Exception {
        final Style style = new Style();
        Assert.assertEquals(StyleType.EACH_SIDE_SEPARATELY, style.getType());
        Assert.assertEquals("", style.getName());
        Assert.assertEquals(-1, style.getStyleId());
    }

    @Test
    public void createStyleValue() {
        Style style = TestUtil.createStyle("border-color", StyleType.ALL_SIDES, MeasureUnit.PX, "red");
        StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("red");
        styleValue.setRightValue("green");
        styleValue.setBottomValue("blue");
        styleValue.setLeftValue("yellow");
        style.setValues(styleValue);

        Assert.assertEquals("red", StyleManager.createStyleValue("Value", style));
        Assert.assertEquals("red", StyleManager.createStyleValue("Vertical", style));
        Assert.assertEquals("red", StyleManager.createStyleValue("Top", style));
        Assert.assertEquals("green", StyleManager.createStyleValue("Horizontal", style));
        Assert.assertEquals("green", StyleManager.createStyleValue("Right", style));

        Assert.assertEquals("blue", StyleManager.createStyleValue("Bottom", style));
        Assert.assertEquals("yellow", StyleManager.createStyleValue("Left", style));
        Assert.assertEquals("", StyleManager.createStyleValue("Right", null));
        Assert.assertEquals("", StyleManager.createStyleValue(null, null));
        Assert.assertEquals("", StyleManager.createStyleValue(null, style));
        Assert.assertEquals("", StyleManager.createStyleValue("", style));
        Assert.assertEquals("", StyleManager.createStyleValue("", null));
        Assert.assertEquals("", StyleManager.createStyleValue("sadgf", style));
    }

    @Test
    public void createStyleValueWithAllEmptyValues() {
        Style style = TestUtil.createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.PX, "red");
        StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("2");
        styleValue.setRightValue("2");
        styleValue.setBottomValue("");
        styleValue.setLeftValue("2");
        style.setValues(styleValue);

        Assert.assertEquals("Checking that method still creating padding if one of the sides is empty",
                "padding:2px 2px 0px 2px !important;", style.stringValue(true));

        style = TestUtil.createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.PX, "red");
        styleValue = new StyleValue();
        styleValue.setTopValue("");
        styleValue.setRightValue("");
        styleValue.setBottomValue("");
        styleValue.setLeftValue("");
        style.setValues(styleValue);

        Assert.assertEquals("Checking that method won't create any styles at all if all sides are empty",
                " ", style.stringValue(true));
    }

    @Test
    public void createMeasureValue() {
        Style style = TestUtil.createStyle("border-width", StyleType.ALL_SIDES, MeasureUnit.PX, "10");
        MeasureUnits measureUnits = new MeasureUnits();
        measureUnits.setTopMeasureUnit(MeasureUnit.PX);
        measureUnits.setRightMeasureUnit(MeasureUnit.EM);
        measureUnits.setBottomMeasureUnit(MeasureUnit.MM);
        measureUnits.setLeftMeasureUnit(MeasureUnit.PT);
        style.setMeasureUnits(measureUnits);

        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("Value", style));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("Vertical", style));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("Top", style));

        Assert.assertEquals(MeasureUnit.EM, StyleManager.createMeasureValue("Horizontal", style));
        Assert.assertEquals(MeasureUnit.EM, StyleManager.createMeasureValue("Right", style));

        Assert.assertEquals(MeasureUnit.MM, StyleManager.createMeasureValue("Bottom", style));
        Assert.assertEquals(MeasureUnit.PT, StyleManager.createMeasureValue("Left", style));

        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("Right", null));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue(null, null));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue(null, style));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("", style));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("", null));
        Assert.assertEquals(MeasureUnit.PX, StyleManager.createMeasureValue("sadgf", style));
    }


    @Test
    public void createPX() {
        String nonPXValue;
        MeasureUnit oldMeasurementUnit;
        int pxValue;

        nonPXValue = "";
        oldMeasurementUnit = MeasureUnit.PX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(0, pxValue);


        nonPXValue = " ";
        oldMeasurementUnit = MeasureUnit.PX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(0, pxValue);

        nonPXValue = "dsfasd ";
        oldMeasurementUnit = MeasureUnit.PX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(0, pxValue);
        
        nonPXValue = null;
        oldMeasurementUnit = MeasureUnit.PX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(0, pxValue);


        nonPXValue = "10";
        oldMeasurementUnit = null;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(0, pxValue);
        //--------------------------------------------------------------------------------------------------------------
        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.PX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(10, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.EM;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(160, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.PT;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(14, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.IN;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(963, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.CM;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(379, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.MM;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(38, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.EX;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(76, pxValue);

        nonPXValue = "10";
        oldMeasurementUnit = MeasureUnit.PC;
        pxValue = StyleManager.createPX(nonPXValue, oldMeasurementUnit);
        Assert.assertEquals(160, pxValue);
    }
}
