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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class DashboardActionTest {


    @Before
    public void before() {
        final ActionBeanContext actionBeanContext = new ActionBeanContext();
        actionBeanContext.setRequest(new MockHttpServletRequest("", ""));
        action.setContext(actionBeanContext);
    }

    @Test
    public void testShow() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite("commonSite", "url");
        final Site blueprint = TestUtil.createSite("blueprint", "url");
        final Site networkSite1 = TestUtil.createSite("networkSite1", "url");
        final Site networkSite2 = TestUtil.createSite("networkSite2", "url");// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite("childSite1", "url");
        final Site childSite2 = TestUtil.createSite("childSite2", "url");

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(networkSite1, new Site());
        user.addChildSiteSettingsId(childSiteSettings.getId());
        childSiteSettings.setSite(null);

        action.show();


        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(commonSite.getSiteId(), action.getDashboardCommonSitesInfo().get(0).getSiteId());
        Assert.assertEquals("Now here is also network site",
                networkSite1.getSiteId(), action.getDashboardCommonSitesInfo().get(1).getSiteId());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(5, action.getDashboardNetworksInfo().size());
        Assert.assertEquals("Network site precedes it`s child sites.", networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isSiteCreated());

        /*-------------------------------------------------Child Sites------------------------------------------------*/
        Assert.assertEquals("This site has not been created yet.",
                false, action.getDashboardNetworksInfo().get(1).isSiteCreated());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(2).isSiteCreated());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());

        //Child site inside their network`s site block was sorted alphabetically.
        Assert.assertEquals(" - inactive", action.getDashboardNetworksInfo().get(1).getName());
        Assert.assertEquals("childSite1", action.getDashboardNetworksInfo().get(2).getName());         
        /*-------------------------------------------------Child Sites------------------------------------------------*/

        Assert.assertEquals("Network site precedes it`s child sites.", networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        /*-------------------------------------------------Child Sites------------------------------------------------*/
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(4).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(3).isEditable());
        /*-------------------------------------------------Child Sites------------------------------------------------*/

        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(commonSite.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.COMMON, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withSavedSiteId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(null, DashboardSiteInfo.newInstance(blueprint, DashboardSiteType.BLUEPRINT));

        action.show();


        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(commonSite.getSiteId(), action.getDashboardCommonSitesInfo().get(0).getSiteId());
        Assert.assertEquals("Now here is also network site",
                networkSite1.getSiteId(), action.getDashboardCommonSitesInfo().get(1).getSiteId());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(blueprint.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withSavedSiteIdInSessionAndSiteIdPassedThroughParameter() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(null, DashboardSiteInfo.newInstance(blueprint, DashboardSiteType.BLUEPRINT));
        final Integer networkSiteHash = DashboardSiteInfo.newInstance(networkSite1, DashboardSiteType.NETWORK).hashCode();
        action.setSelectedSite(networkSiteHash);

        action.show();


        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(commonSite.getSiteId(), action.getDashboardCommonSitesInfo().get(0).getSiteId());
        Assert.assertEquals("Now here is also network site",
                networkSite1.getSiteId(), action.getDashboardCommonSitesInfo().get(1).getSiteId());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertNotSame(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(networkSiteHash.intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(networkSite1.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.NETWORK, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withWrongSavedSiteIdInSession() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);

        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(null, null);
        action.show();


        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(commonSite.getSiteId(), action.getDashboardCommonSitesInfo().get(0).getSiteId());
        Assert.assertEquals("Now here is also network site",
                networkSite1.getSiteId(), action.getDashboardCommonSitesInfo().get(1).getSiteId());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(commonSite.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.COMMON, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSites() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        action.show();


        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals(true, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(networkSite1.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals("Because network site is also shown as common now.", DashboardSiteType.COMMON, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSites_withoutRightToFirstNetworkSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(childSite1.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.NETWORK, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSites_withoutRightToFirstNetworkSite_butWithItsIdSavedInSession() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createSite();
        final Site networkSite1 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site networkSite2 = TestUtil.createSite();// Site to which current logined user has no rights.
        final Site childSite1 = TestUtil.createSite();
        final Site childSite2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);

        blueprint.setType(SiteType.BLUEPRINT);
        final ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(networkSite1, childSite1);
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(networkSite2, childSite2);
        final DraftChildSiteRegistration childSiteRegistration1 = TestUtil.createChildSiteRegistration(networkSite1);
        final DraftChildSiteRegistration childSiteRegistration2 = TestUtil.createChildSiteRegistration(networkSite2);
        childSiteSettings1.setChildSiteRegistration(childSiteRegistration1);
        childSiteSettings2.setChildSiteRegistration(childSiteRegistration2);


        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(null, DashboardSiteInfo.newInstance(networkSite1, DashboardSiteType.NETWORK));

        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());

        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(4, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(networkSite1.getSiteId(), action.getDashboardNetworksInfo().get(0).getSiteId());
        Assert.assertEquals(childSite1.getSiteId(), action.getDashboardNetworksInfo().get(1).getSiteId());
        Assert.assertEquals(networkSite2.getSiteId(), action.getDashboardNetworksInfo().get(2).getSiteId());
        Assert.assertEquals(childSite2.getSiteId(), action.getDashboardNetworksInfo().get(3).getSiteId());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(0).isEditable());
        Assert.assertEquals("Site to which current logined user has no rights. We show it, but it`s not editable.",
                false, action.getDashboardNetworksInfo().get(2).isEditable());


        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(childSite1.getSiteId(), action.getSelectedSiteInfo().getSiteId());
        Assert.assertEquals(DashboardSiteType.NETWORK, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSitesAndNetworkSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, blueprint);
        blueprint.setType(SiteType.BLUEPRINT);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(blueprint.getSiteId(), action.getDashboardBlueprintsInfo().get(1).getSiteId());

        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSitesAndNetworkSiteAndBlueprint_forShroggleAdmin() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("aaa"));
        final User user = TestUtil.createUserAndLogin("aaa");
        final Site blueprint = TestUtil.createBlueprint();

        blueprint.getPublicBlueprintsSettings().setPublished(new Date());


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(1, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());
        Assert.assertEquals(blueprint.getSiteId(), action.getPublishedBlueprints().get(0).getSiteId());

        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(DashboardSiteType.PUBLISHED_BLUEPRINT, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSitesAndNetworkSiteAndBlueprint_forNotShroggleAdmin() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createBlueprint();

        blueprint.getPublicBlueprintsSettings().setPublished(new Date());


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());
    }

    @Test
    public void testShow_withoutCommonSitesAndNetworkSiteAndBlueprintAndPublished_forShroggleAdmin() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("aaa"));
        TestUtil.createUserAndLogin("aaa");
        final Site blueprint = TestUtil.createBlueprint();

        blueprint.getPublicBlueprintsSettings().setPublished(null);
        blueprint.getPublicBlueprintsSettings().setActivated(new Date());


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(1, action.getActivatedBlueprints().size());
        Assert.assertEquals(blueprint.getSiteId(), action.getActivatedBlueprints().get(0).getSiteId());

        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), action.getSelectedSiteInfo().hashCode());
        Assert.assertEquals(DashboardSiteType.ACTIVATED_BLUEPRINT, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutSites() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("aaa"));
        TestUtil.createUserAndLogin("aaa");


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());
        Assert.assertEquals(null, action.getSelectedSiteType());
    }

    @Test
    public void testShow_withoutCommonSitesAndNetworkSiteAndBlueprintAndPublished_forNotShroggleAdmin() throws Exception {
        TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createBlueprint();

        blueprint.getPublicBlueprintsSettings().setPublished(null);
        blueprint.getPublicBlueprintsSettings().setActivated(new Date());


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());
    }

    // Following test is for http://jira.web-deva.com/browse/SW-6220. Tolik

    @Test
    public void test_sorting_WithOneCommonSiteAndOneNetwork() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final Site networkSite = TestUtil.createNetworkSite();

        site.setTitle("site");
        networkSite.setTitle("networkSite");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite);

        action.show();

        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());

        Assert.assertEquals("networkSite", action.getDashboardCommonSitesInfo().get(0).getName());
        Assert.assertEquals("site", action.getDashboardCommonSitesInfo().get(1).getName());
    }


    // Following test is for http://jira.web-deva.com/browse/SW-6220. Tolik

    @Test
    public void test_sorting_WithOneCommonSiteAndOneNetwork_ignoreCase() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final Site networkSite = TestUtil.createNetworkSite();

        site.setTitle("Site");
        networkSite.setTitle("networkSite");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite);

        action.show();

        Assert.assertEquals(2, action.getDashboardCommonSitesInfo().size());

        Assert.assertEquals("networkSite", action.getDashboardCommonSitesInfo().get(0).getName());
        Assert.assertEquals("Site", action.getDashboardCommonSitesInfo().get(1).getName());
    }

    @Test
    public void testExecute_withPublishedActivatedAndCommonBlueprintsForAppAdmin() throws Exception {
        final User user = TestUtil.createUserAndLogin("admin");
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList(user.getEmail()));

        final Site commonBlueprint = TestUtil.createBlueprint();
        final Site publishedBlueprint = TestUtil.createBlueprint();
        final Site activatedBlueprint = TestUtil.createBlueprint();

        new SiteManager(publishedBlueprint).getPublicBlueprintsSettings().publish();
        new SiteManager(activatedBlueprint).getPublicBlueprintsSettings().activate();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, publishedBlueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, activatedBlueprint);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(2, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(1, action.getPublishedBlueprints().size());
        Assert.assertEquals(1, action.getActivatedBlueprints().size());
    }

    @Test
    public void testExecute_withPublishedActivatedAndCommonBlueprintsForSimpleAdmin() throws Exception {
        final User user = TestUtil.createUserAndLogin("admin");

        final Site commonBlueprint = TestUtil.createBlueprint();
        final Site publishedBlueprint = TestUtil.createBlueprint();
        final Site activatedBlueprint = TestUtil.createBlueprint();

        new SiteManager(publishedBlueprint).getPublicBlueprintsSettings().publish();
        new SiteManager(activatedBlueprint).getPublicBlueprintsSettings().activate();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, publishedBlueprint);
        TestUtil.createUserOnSiteRightActiveAdmin(user, activatedBlueprint);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(4, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());
    }


    /*-----------------------------------------Add create new blueprint link------------------------------------------*/

    @Test
    public void testCreateWithTenBlueprints() throws Exception {
        final User user = TestUtil.createUserAndLogin("admin");

        final Site commonBlueprint1 = TestUtil.createBlueprint();
        final Site commonBlueprint2 = TestUtil.createBlueprint();
        final Site commonBlueprint3 = TestUtil.createBlueprint();
        final Site commonBlueprint4 = TestUtil.createBlueprint();
        final Site commonBlueprint5 = TestUtil.createBlueprint();
        final Site commonBlueprint6 = TestUtil.createBlueprint();
        final Site commonBlueprint7 = TestUtil.createBlueprint();
        final Site commonBlueprint8 = TestUtil.createBlueprint();
        final Site commonBlueprint9 = TestUtil.createBlueprint();
        final Site commonBlueprint10 = TestUtil.createBlueprint();


        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint7);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint8);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint9);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint10);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(12, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());


        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(1).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(2).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(3).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(4).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(5).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(6).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(7).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(8).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(9).getSiteType());
        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(10).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(11).getSiteType());
    }

    @Test
    public void testCreateWithNineBlueprints() throws Exception {
        final User user = TestUtil.createUserAndLogin("admin");

        final Site commonBlueprint1 = TestUtil.createBlueprint();
        final Site commonBlueprint2 = TestUtil.createBlueprint();
        final Site commonBlueprint3 = TestUtil.createBlueprint();
        final Site commonBlueprint4 = TestUtil.createBlueprint();
        final Site commonBlueprint5 = TestUtil.createBlueprint();
        final Site commonBlueprint6 = TestUtil.createBlueprint();
        final Site commonBlueprint7 = TestUtil.createBlueprint();
        final Site commonBlueprint8 = TestUtil.createBlueprint();
        final Site commonBlueprint9 = TestUtil.createBlueprint();


        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint7);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint8);
        TestUtil.createUserOnSiteRightActiveAdmin(user, commonBlueprint9);


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(10, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());


        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(1).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(2).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(3).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(4).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(5).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(6).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(7).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(8).getSiteType());
        Assert.assertEquals(DashboardSiteType.BLUEPRINT, action.getDashboardBlueprintsInfo().get(9).getSiteType());
    }

    @Test
    public void testCreateWithoutBlueprints() throws Exception {
        TestUtil.createUserAndLogin("admin");


        action.show();


        Assert.assertEquals(0, action.getDashboardCommonSitesInfo().size());
        Assert.assertEquals(0, action.getDashboardNetworksInfo().size());
        Assert.assertEquals(1, action.getDashboardBlueprintsInfo().size());
        Assert.assertEquals(0, action.getPublishedBlueprints().size());
        Assert.assertEquals(0, action.getActivatedBlueprints().size());


        Assert.assertEquals(DashboardSiteType.CREATE_BLUEPRINT_CELL, action.getDashboardBlueprintsInfo().get(0).getSiteType());
    }

    /*-----------------------------------------Add create new blueprint link------------------------------------------*/
    private final DashboardAction action = new DashboardAction();
}
