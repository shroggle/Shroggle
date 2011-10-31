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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftItem;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class AddItemServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLogined() throws Exception {
        new AddItemService().execute(new AddItemRequest());
    }

    @Test
    public void testExecute() throws Exception {
        TestUtil.createUserAndLogin();
        Site site = TestUtil.createSite();

        final AddItemRequest request = new AddItemRequest();
        request.setCopyContent(false);
        request.setItemId(null);
        request.setItemType(ItemType.ADVANCED_SEARCH);
        request.setSiteId(site.getSiteId());

        final AddItemResponse response = new AddItemService().execute(request);

        final DraftItem item = ServiceLocator.getPersistance().getDraftItem(response.getItemId());
        Assert.assertNotNull(item);
        Assert.assertEquals(site.getSiteId(), item.getSiteId());
        Assert.assertEquals(ItemType.ADVANCED_SEARCH, response.getItemType());
    }
}
