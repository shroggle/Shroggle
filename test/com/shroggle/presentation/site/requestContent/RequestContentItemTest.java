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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.entity.ItemType;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class RequestContentItemTest {

    @Test
    public void getType() {
        Assert.assertEquals(
                ItemType.BLOG,
                new RequestContentItem("", ItemType.BLOG, 1).getType());
    }

    @Test
    public void getItemId() {
        Assert.assertEquals(
                12, new RequestContentItem("", ItemType.BLOG, 12).getItemId());
    }

    @Test
    public void getName() {
        Assert.assertEquals(
                "F", new RequestContentItem("F", ItemType.BLOG, 12).getName());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithoutType() {
        new RequestContentItem("F", null, 12);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithoutName() {
        new RequestContentItem(null, ItemType.FORUM, 12);
    }

}
