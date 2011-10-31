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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ItemTypeTest {

    @Test
    public void testIsShareable() {
        for(ItemType itemType : ItemType.values()){
            Assert.assertTrue(itemType.isShareable());
        }
    }

    @Test
    public void testGetDraftItemsTypeWithoutDuplicates() throws Exception {
        final List<Class> classes = new ArrayList<Class>();
        for (ItemType itemType : ItemType.getDraftItemsTypeWithoutDuplicates()) {
            Assert.assertNotNull(itemType.getItemClass());
            Assert.assertFalse(classes.contains(itemType.getItemClass()));
            classes.add(itemType.getItemClass());
        }
    }
}
