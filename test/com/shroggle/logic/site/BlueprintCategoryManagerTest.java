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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.BlueprintCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlueprintCategoryManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        new BlueprintCategoryManager(null);
    }

    @Test
    public void testGetInternationalValue() throws Exception {
        for (BlueprintCategory category : BlueprintCategory.values()) {
            Assert.assertNotNull(new BlueprintCategoryManager(category).getInternationalValue());
        }
    }

    @Test
    public void testTestMaxLength() throws Exception {
        int length = 0;
        for (BlueprintCategory category : BlueprintCategory.values()) {
            if (length < category.toString().length()) {
                length = category.toString().length();
            }
        }
        Assert.assertTrue("I`m testing max field`s length here because I have fixed field`s " +
                "length in DB = 30 (PublicBlueprintsSettings/blueprintCategory) and if max " +
                "length of this enum will be more than 30 we`ll have a subtle bug. Tolik",
                length <= 30);
    }

    @Test
    public void testGetSortedValues() throws Exception {
        final BlueprintCategory[] categories = BlueprintCategoryManager.getSortedValues();
        Assert.assertEquals(BlueprintCategory.ALTERNATIVE_THERAPISTS, categories[0]);
        Assert.assertEquals(BlueprintCategory.APPAREL, categories[1]);
        Assert.assertEquals(BlueprintCategory.ART_CRAFTS, categories[2]);
        Assert.assertEquals(BlueprintCategory.AUTOMOBILES_MECHANICS, categories[3]);
        Assert.assertEquals(BlueprintCategory.BIRTHS_AND_BABIES, categories[4]);
        Assert.assertEquals(BlueprintCategory.WEDDINGS, categories[30]);

    }
}
