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
package com.shroggle.presentation.account.items;

import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveSiteItemsActionTest {

    @Test
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        action.setItemType(ItemType.ALL_FORMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(childSiteRegistration.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_FORMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(0, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(0, failedToRemoveMessages.size());
    }

    @Test
    public void executeWithoutType() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        action.setItemType(null);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(childSiteRegistration.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        action.setItemType(ItemType.ALL_FORMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(childSiteRegistration.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_FORMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(1, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(0, failedToRemoveMessages.size());
        Assert.assertNull(persistance.getChildSiteRegistrationById(childSiteRegistration.getFormId()));
    }

    @Test
    public void executeForMany() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration1 = new DraftChildSiteRegistration();
        childSiteRegistration1.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration1);

        DraftChildSiteRegistration childSiteRegistration2 = new DraftChildSiteRegistration();
        childSiteRegistration2.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration2);

        action.setItemType(ItemType.ALL_FORMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(childSiteRegistration1.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        itemTypeByIds.put(childSiteRegistration2.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_FORMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(0, failedToRemoveMessages.size());
        Assert.assertNull(persistance.getChildSiteRegistrationById(childSiteRegistration1.getFormId()));
    }

    @Test
    public void executeForUsedChildSiteRegistration() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        action.setItemType(ItemType.ALL_FORMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(childSiteRegistration.getFormId(), ItemType.CHILD_SITE_REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_FORMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(0, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(1, failedToRemoveMessages.size());
        Assert.assertNotNull(persistance.getChildSiteRegistrationById(childSiteRegistration.getFormId()));
    }

    @Test
    public void executeForItemWithReadOnlyRights() {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForum draftForum = TestUtil.createForum(site2);
        TestUtil.createSiteOnItemRight(site, draftForum, SiteOnItemRightType.READ);

        action.setItemType(ItemType.ALL_ITEMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(draftForum.getId(), ItemType.FORUM);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_ITEMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(0, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(1, failedToRemoveMessages.size());
        Assert.assertNotNull(persistance.getDraftItem(draftForum.getId()));
    }

    @Test
    public void executeForDefaultRegistrationForm() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftRegistrationForm defaultRegistration = TestUtil.createRegistrationForm(site);
        site.setDefaultFormId(defaultRegistration.getId());

        action.setItemType(ItemType.ALL_FORMS);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(defaultRegistration.getId(), ItemType.REGISTRATION);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.ALL_FORMS, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(0, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(1, failedToRemoveMessages.size());
        Assert.assertNotNull(persistance.getDraftItem(defaultRegistration.getId()));
    }

    @Test
    public void executeForDefaultMenu() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftMenu draftMenu = TestUtil.createMenu();
        site.setMenu(draftMenu);

        action.setItemType(ItemType.MENU);
        Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
        itemTypeByIds.put(draftMenu.getId(), ItemType.MENU);
        action.setItemTypeByIds(itemTypeByIds);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        List<String> failedToRemoveMessages = (List<String>)
                resolutionMock.getRedirectByActionParameters()[3].getValue();

        Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(ItemType.MENU, resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(true, resolutionMock.getRedirectByActionParameters()[1].getValue());
        Assert.assertEquals(0, resolutionMock.getRedirectByActionParameters()[2].getValue());
        Assert.assertEquals(1, failedToRemoveMessages.size());
        Assert.assertNotNull(persistance.getDraftItem(draftMenu.getId()));
    }

    private final RemoveSiteItemsAction action = new RemoveSiteItemsAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
