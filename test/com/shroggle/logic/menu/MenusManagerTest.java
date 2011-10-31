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
package com.shroggle.logic.menu;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.MenuStructureType;
import com.shroggle.entity.Site;
import com.shroggle.entity.DraftMenu;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class MenusManagerTest {

    //todo: Tolik, add please some tests here. Testing that it get's correct default structure would be enough.
    @Test
    public void testCreateDefaultMenu() {
        Site site = TestUtil.createSite();

        final DraftMenu draftMenu = new MenusManager().createDefaultMenu(site.getMenu());
        Assert.assertNotNull(draftMenu);
        Assert.assertEquals(site.getSiteId(), draftMenu.getSiteId());
        Assert.assertEquals("Menu1", draftMenu.getName());
        Assert.assertEquals(MenuStructureType.DEFAULT, draftMenu.getMenuStructure());
    }

}
