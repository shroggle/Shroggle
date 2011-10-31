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
package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.ItemType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemTypeManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutItemType() throws Exception {
        new ItemTypeManager(null);
    }

    @Test
    public void testGetItemName() throws Exception {
        for (ItemType itemType : ItemType.values()) {
            Assert.assertNotNull(new ItemTypeManager(itemType).getName());
        }
    }
}
