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
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.childSites.ManageNetworkRegistrantsService;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ManageNetworkRegistrantsServiceTest {

    ManageNetworkRegistrantsService service = new ManageNetworkRegistrantsService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    /*----------------------------------------------------execute-----------------------------------------------------*/
    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        DraftForm form = TestUtil.createRegistrationForm();
        filledForm.setFormId(form.getFormId());

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, site);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());

        final List<Integer> templatesIds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            templatesIds.add(TestUtil.createBlueprint().getSiteId());
        }
        childSiteRegistration.setBlueprintsId(templatesIds);

        service.execute(user.getUserId(), filledForm.getFilledFormId(), site.getSiteId(), false);

        Assert.assertEquals(site.getSiteId(), service.getParentSiteId());
        Assert.assertEquals(user.getUserId(),
                ((User) service.getContext().getHttpServletRequest().getAttribute("loginedUser")).getUserId());
        Assert.assertEquals(filledForm.getFilledFormId(),
                ((FilledForm) service.getContext().getHttpServletRequest().getAttribute("prefilledForm")).getFilledFormId());
        Assert.assertNotNull(service.getFormData());
        Assert.assertEquals(2, service.getFormData().getFormItems().size());

        Assert.assertNotNull(service.getChildSiteSettings());
        Assert.assertNotNull(service.getBlueprints());
        Assert.assertEquals(10, service.getBlueprints().size());

        Assert.assertEquals(settings.getChildSiteSettingsId(), service.getChildSiteSettings().getChildSiteSettingsId());
        Assert.assertEquals(childSiteRegistration.getFormId(), service.getChildSiteSettings().getChildSiteRegistration().getFormId());
    }

    @Test
    public void testExecuteWithoutSettings() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        DraftForm form = TestUtil.createRegistrationForm();
        filledForm.setFormId(form.getFormId());

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);

        final List<Integer> templatesIds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            templatesIds.add(TestUtil.createSite().getSiteId());
        }
        childSiteRegistration.setBlueprintsId(templatesIds);

        service.execute(user.getUserId(), filledForm.getFilledFormId(), site.getSiteId(), false);

        Assert.assertEquals(site.getSiteId(), service.getParentSiteId());
        Assert.assertEquals(user.getUserId(),
                ((User) service.getContext().getHttpServletRequest().getAttribute("loginedUser")).getUserId());
        Assert.assertEquals(filledForm.getFilledFormId(),
                ((FilledForm) service.getContext().getHttpServletRequest().getAttribute("prefilledForm")).getFilledFormId());
        Assert.assertNotNull(service.getFormData());
        Assert.assertEquals(2, service.getFormData().getFormItems().size());

        Assert.assertNull(service.getChildSiteSettings());
        Assert.assertNull(service.getBlueprints());
    }

    @Test
    public void testExecute_withoutBlueprints() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        DraftForm form = TestUtil.createRegistrationForm();
        filledForm.setFormId(form.getFormId());

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, site);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());

        service.execute(user.getUserId(), filledForm.getFilledFormId(), site.getSiteId(), false);

        Assert.assertEquals(site.getSiteId(), service.getParentSiteId());
        Assert.assertEquals(user.getUserId(),
                ((User) service.getContext().getHttpServletRequest().getAttribute("loginedUser")).getUserId());
        Assert.assertEquals(filledForm.getFilledFormId(),
                ((FilledForm) service.getContext().getHttpServletRequest().getAttribute("prefilledForm")).getFilledFormId());
        Assert.assertNotNull(service.getFormData());
        Assert.assertEquals(2, service.getFormData().getFormItems().size());

        Assert.assertNotNull(service.getChildSiteSettings());
        Assert.assertNotNull(service.getBlueprints());
        Assert.assertEquals(0, service.getBlueprints().size());

        Assert.assertEquals(settings.getChildSiteSettingsId(), service.getChildSiteSettings().getChildSiteSettingsId());
        Assert.assertEquals(childSiteRegistration.getFormId(), service.getChildSiteSettings().getChildSiteRegistration().getFormId());
    }

    @Test
    public void testExecute_withoutChildSiteSettings() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        DraftForm form = TestUtil.createRegistrationForm();
        filledForm.setFormId(form.getFormId());

        service.execute(user.getUserId(), filledForm.getFilledFormId(), site.getSiteId(), false);

        Assert.assertEquals(site.getSiteId(), service.getParentSiteId());
        Assert.assertEquals(user.getUserId(),
                ((User) service.getContext().getHttpServletRequest().getAttribute("loginedUser")).getUserId());
        Assert.assertEquals(filledForm.getFilledFormId(),
                ((FilledForm) service.getContext().getHttpServletRequest().getAttribute("prefilledForm")).getFilledFormId());
        Assert.assertNotNull(service.getFormData());
        Assert.assertEquals(2, service.getFormData().getFormItems().size());

        Assert.assertNull(service.getChildSiteSettings());
        Assert.assertNull(service.getBlueprints());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLogin() throws Exception {
        service.execute(0, 0, 0, false);
    }

    @Test(expected = UserNotFoundException.class)
    public void testExecute_withoutVisitor() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        service.execute(0, 0, 0, false);
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void testExecute_withoutFilledForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        service.execute(user.getUserId(), 0, 0, false);
    }
    /*----------------------------------------------------execute-----------------------------------------------------*/


    /*-------------------------------------------updateNetworkVisitorForm---------------------------------------------*/

    @Test
    public void testUpdateNetworkVisitorForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS);
        filledFormItems.get(0).setValue("firstName");
        filledFormItems.get(1).setValue("lastName");
        filledFormItems.get(2).setValue("adress");
        filledForm.setFilledFormItems(filledFormItems);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("firstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("lastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("adress", filledForm.getFilledFormItems().get(2).getValues().get(0));

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, site);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());


        final List<FilledFormItem> newFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS);
        newFilledFormItems.get(0).setValue("newFirstName");
        newFilledFormItems.get(1).setValue("newLastName");
        newFilledFormItems.get(2).setValue("newAdress");

        service.updateNetworkVisitorForm(user.getUserId(), filledForm.getFilledFormId(), newFilledFormItems, site.getSiteId());

        Assert.assertEquals(site.getSiteId(), service.getParentSiteId());

        Assert.assertNotNull(service.getFilledForms());

        Assert.assertNull(service.getFormData());
        Assert.assertNull(service.getChildSiteSettings());
        Assert.assertNull(service.getBlueprints());
    }


    @Test(expected = UserNotLoginedException.class)
    public void testUpdateNetworkVisitorForm_withoutLogin() throws Exception {
        service.updateNetworkVisitorForm(0, 0, null, 0);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateNetworkVisitorForm_withoutVisitor() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        service.updateNetworkVisitorForm(0, 0, null, 0);
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void testUpdateNetworkVisitorForm_withoutFilledForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        service.updateNetworkVisitorForm(user.getUserId(), 0, null, 0);
    }
    /*-------------------------------------------updateNetworkVisitorForm---------------------------------------------*/

}
