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

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class ThemeIdTest {

    @Test
    public void equals() {
        Assert.assertEquals(new ThemeId("a", "f"), new ThemeId("a", "f"));
    }

    @Test
    public void hashCode1() {
        Assert.assertEquals(new ThemeId("a", "f").hashCode(), new ThemeId("a", "f").hashCode());
    }

    @Test
    public void equalsWithDifferentThemeCss() {
        Assert.assertNotSame(new ThemeId("a", "f1"), new ThemeId("a", "f"));
    }

    @Test
    public void equalsWithDifferentTemplateDirectory() {
        Assert.assertNotSame(new ThemeId("a1", "f"), new ThemeId("a", "f"));
    }

    @Test
    public void testEquals() throws Exception {
        ThemeId themeId1 = new ThemeId("1", "1");
        ThemeId themeId2 = new ThemeId("1", "1");
        ThemeId themeId3 = new ThemeId("1", "1");

        Assert.assertFalse(themeId1.equals(null));

        // Reflexive
        Assert.assertTrue(themeId1.equals(themeId1));

        // Symmetric
        Assert.assertTrue(themeId1.equals(themeId2));
        Assert.assertTrue(themeId2.equals(themeId1));
        
        // Transitive
        Assert.assertTrue(themeId1.equals(themeId2));
        Assert.assertTrue(themeId2.equals(themeId3));
        Assert.assertTrue(themeId1.equals(themeId3));

        // Consistent is broken!
        // Multiple invocations of themeId1.equals(themeId2) will not consistently
        // return true or false if some of fields will be changed.

        
        //___________________________________________
        Assert.assertEquals(themeId1.hashCode(), themeId2.hashCode());
        Assert.assertEquals(themeId1.hashCode(), themeId3.hashCode());
        Assert.assertEquals(themeId2.hashCode(), themeId3.hashCode());
    }

    @Test
    public void testEquals_withNullInOneOfField() throws Exception {
        ThemeId themeId1 = new ThemeId("1", null);
        ThemeId themeId2 = new ThemeId("1", null);
        ThemeId themeId3 = new ThemeId("1", null);

        Assert.assertFalse(themeId1.equals(null));

        // Reflexive
        Assert.assertTrue(themeId1.equals(themeId1));

        // Symmetric
        Assert.assertTrue(themeId1.equals(themeId2));
        Assert.assertTrue(themeId2.equals(themeId1));

        // Transitive
        Assert.assertTrue(themeId1.equals(themeId2));
        Assert.assertTrue(themeId2.equals(themeId3));
        Assert.assertTrue(themeId1.equals(themeId3));

        // Consistent is broken!
        // Multiple invocations of themeId1.equals(themeId2) will not consistently
        // return true or false if some of fields will be changed.

        //___________________________________________
        Assert.assertEquals(themeId1.hashCode(), themeId2.hashCode());
        Assert.assertEquals(themeId1.hashCode(), themeId3.hashCode());
        Assert.assertEquals(themeId2.hashCode(), themeId3.hashCode());
    }
}
