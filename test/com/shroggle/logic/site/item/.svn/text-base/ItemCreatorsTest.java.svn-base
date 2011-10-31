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
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemCreatorsTest {

    @Test
    public void testCreate() throws Exception {
        final DraftImage draftImage = (DraftImage)ItemCreators.newInstance(ItemType.IMAGE).create(1);
        Assert.assertEquals(true, draftImage.isShowDescriptionOnMouseOver());
    }

    @Test
    public void testCreate_GALLERY() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftGallery gallery = (DraftGallery)ItemCreators.newInstance(ItemType.GALLERY).create(site.getSiteId());
        Assert.assertEquals(false, gallery.isModified());
    }
}
