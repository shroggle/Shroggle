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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GetBlueprintsActionTest {

    @Test
    public void executeWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        final Site publishedBlueprint1 = TestUtil.createBlueprint();
        publishedBlueprint1.setTitle("1");

        persistanceMock.getActiveBlueprints(null).add(publishedBlueprint1);

        Assert.assertEquals(0, action.getItems().size());
        Assert.assertEquals("/WEB-INF/pages/getBlueprints.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void execute() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, action.getItems().size());
        Assert.assertEquals("/WEB-INF/pages/getBlueprints.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithActive() {
        TestUtil.createUserAndLogin();

        TestUtil.createBlueprint();

        final Site activeBlueprint1 = TestUtil.createBlueprint();
        activeBlueprint1.setTitle("1");
        activeBlueprint1.getPublicBlueprintsSettings().setActivated(new Date());
        activeBlueprint1.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.BUSINESS_SERVICES);

        final Site activeBlueprint2 = TestUtil.createBlueprint();
        activeBlueprint2.setTitle("2");
        activeBlueprint2.getPublicBlueprintsSettings().setActivated(new Date());
        activeBlueprint2.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.APPAREL);

        persistanceMock.getActiveBlueprints(null).add(activeBlueprint1);
        persistanceMock.getActiveBlueprints(null).add(activeBlueprint2);


        action.setBlueprintCategory(BlueprintCategory.BUSINESS_SERVICES);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, action.getItems().size());
//        Assert.assertEquals("11", action.getKeywords());
        Assert.assertEquals(activeBlueprint1.getId(), action.getItems().get(0).getId());
        Assert.assertEquals("1", action.getItems().get(0).getTitle());
        Assert.assertEquals("/WEB-INF/pages/getBlueprints.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithPublished_withoutBlueprintsCategory() {
        TestUtil.createUserAndLogin();

        TestUtil.createBlueprint();

        final Site activeBlueprint1 = TestUtil.createBlueprint();
        activeBlueprint1.setTitle("1");
        activeBlueprint1.getPublicBlueprintsSettings().setActivated(new Date());
        activeBlueprint1.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.BUSINESS_SERVICES);

        final Site activeBlueprint2 = TestUtil.createBlueprint();
        activeBlueprint2.setTitle("2");
        activeBlueprint2.getPublicBlueprintsSettings().setActivated(new Date());
        activeBlueprint2.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.APPAREL);

        persistanceMock.getActiveBlueprints(null).add(activeBlueprint1);
        persistanceMock.getActiveBlueprints(null).add(activeBlueprint2);

        action.setBlueprintCategory(null);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(2, action.getItems().size());
        Assert.assertEquals(activeBlueprint1.getId(), action.getItems().get(0).getId());
        Assert.assertEquals(activeBlueprint2.getId(), action.getItems().get(1).getId());
        Assert.assertEquals("1", action.getItems().get(0).getTitle());
        Assert.assertEquals("2", action.getItems().get(1).getTitle());
        Assert.assertEquals("/WEB-INF/pages/getBlueprints.jsp", resolutionMock.getForwardToUrl());
    }

    private final GetBlueprintsAction action = new GetBlueprintsAction();
    private final PersistanceMock persistanceMock = (PersistanceMock) ServiceLocator.getPersistance();

}