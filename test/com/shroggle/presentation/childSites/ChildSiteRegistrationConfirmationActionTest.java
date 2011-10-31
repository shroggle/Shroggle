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
package com.shroggle.presentation.childSites;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.site.SiteEditPageAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ChildSiteRegistrationConfirmationActionTest extends TestBaseWithMockService {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", ""));
        ServiceLocator.getConfigStorage().get().setApplicationUrl("www.web-deva.com");
        FileSystemMock fileSystemMock = ((FileSystemMock) ServiceLocator.getFileSystem());
        Template template = TestUtil.createTemplate();
        template.setLayouts(new ArrayList<Layout>() {{
            Layout layout = new Layout();
            layout.setName("layout");
            layout.setFile("layout");
            add(layout);
        }});
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource(
                "directory", "layout", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
    }

    @Test
    public void testExecute_WithWrongSettingsId() throws Exception {
        action.setSettingsId(-1);
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }

    @Test
    public void testExecute_WithNullSettingsId() throws Exception {
        action.setSettingsId(null);
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }

    @Test
    public void testExecute_WithNullConfirmCode() throws Exception {
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode(null);
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }

    @Test
    public void testExecute_WithNullConfirmCodeInSettings() throws Exception {
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setConfirmCode(null);
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("aaa");
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }


    @Test
    public void testExecute_WithWrongConfirmCode() throws Exception {
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setConfirmCode("confirm");
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("aaa");
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }


    @Test
    public void testExecute_WithWrongUserIdInSettings() throws Exception {
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setUserId(-1);
        settings.setConfirmCode("confirm");
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getPath());
    }

    @Test
    public void testExecute_WithActiveUser_secondState() throws Exception {
        final Date activationDate = new Date();
        User user = TestUtil.createUser();
        user.setActiveted(activationDate);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        settings.setCreatedDate(new Date());
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Assert.assertEquals("/account/registration/childSiteRigistrationConfirmationSecondState.jsp", resolutionMock.getPath());
    }

    @Test
    public void testExecute_WithActiveUser_firstState() throws Exception {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
        User user = TestUtil.createUser();
        final Date activationDate = new Date();
        user.setActiveted(activationDate);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setCreatedDate(new Date());
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);

        FilledForm filledForm = TestUtil.createFilledForm(registration.getFormId());
        FilledFormItem filledFormItemDomainName = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<String> values = Arrays.asList("domainName");
        filledFormItemDomainName.setValues(values);

        FilledFormItem filledFormItemSiteName = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> siteNameValues = Arrays.asList("siteName");
        filledFormItemSiteName.setValues(siteNameValues);

        filledForm.setFilledFormItems(Arrays.asList(filledFormItemDomainName, filledFormItemSiteName));
        settings.setFilledFormId(filledForm.getFilledFormId());

        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");


        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        final Site blueprint = TestUtil.createBlueprint();
        blueprint.getThemeId().setTemplateDirectory("TemplateDirectory");
        blueprint.getThemeId().setThemeCss("ThemeCss");
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint.getSiteId()));

        settings.setRequiredToUseSiteBlueprint(true);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        settings.setSitePaymentSettings(sitePaymentSettings);
        settings.setChildSiteRegistration(childSiteRegistration);

        Assert.assertEquals(activationDate, user.getActiveted());
        Assert.assertNull(new UsersManager().getLoginedUser());
        Assert.assertNull(ServiceLocator.getPersistance().getSiteBySubDomain("domainname"));

        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Assert.assertEquals("/account/registration/childSiteRigistrationConfirmationFirstState.jsp", resolutionMock.getPath());

        Assert.assertEquals(activationDate, user.getActiveted());
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Site site = ServiceLocator.getPersistance().getSiteBySubDomain("domainname");
        Assert.assertNotNull(site);
        Assert.assertNotNull(new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertEquals("domainname", site.getSubDomain());
        Assert.assertEquals("http://domainname.web-deva.com", action.getUrl());
        Assert.assertNotNull(action.getTellFriendHtml());
        Assert.assertEquals(2, site.getPages().size());

        Assert.assertEquals("siteName", site.getTitle());
        Assert.assertEquals(blueprint, site.getBlueprintParent());

        Assert.assertEquals("TemplateDirectory", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals("ThemeCss", site.getThemeId().getThemeCss());
    }

    @Test
    public void testExecute_WithActiveUserAndCreatedChildSite() throws Exception {
        final Date activationDate = new Date();
        User user = TestUtil.createUser();
        user.setActiveted(activationDate);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        Site childSite = TestUtil.createSite();
        settings.setSite(childSite);
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        settings.setCreatedDate(new Date());
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");
        ResolutionMock resolutionMock = (ResolutionMock) action.show();
        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals("siteId", resolutionMock.getRedirectByActionParameters()[0].getName());
        Assert.assertEquals(childSite.getSiteId(), resolutionMock.getRedirectByActionParameters()[0].getValue());
    }

    @Test
    public void testExecute_WithExpiredSettings() throws Exception {
        User user = TestUtil.createUser();
        user.setActiveted(null);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setCreatedDate(new Date((System.currentTimeMillis() - TimeInterval.TEN_DAYS.getMillis() * 2) + 1));
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");
        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertEquals("/account/registration/registrationLinkExpired.jsp", resolutionMock.getPath());
    }


    @Test
    public void testExecute_WithFirstState() throws Exception {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
        User user = TestUtil.createUser();
        user.setActiveted(null);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setCreatedDate(new Date());
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);

        FilledForm filledForm = TestUtil.createFilledForm(registration.getFormId());
        FilledFormItem filledFormItemDomainName = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<String> values = Arrays.asList("domainName");
        filledFormItemDomainName.setValues(values);

        FilledFormItem filledFormItemSiteName = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> siteNameValues = Arrays.asList("siteName");
        filledFormItemSiteName.setValues(siteNameValues);

        filledForm.setFilledFormItems(Arrays.asList(filledFormItemDomainName, filledFormItemSiteName));
        settings.setFilledFormId(filledForm.getFilledFormId());

        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");


        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        Site blueprint = TestUtil.createBlueprint();
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint.getSiteId()));

        settings.setRequiredToUseSiteBlueprint(true);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        settings.setSitePaymentSettings(sitePaymentSettings);
        settings.setChildSiteRegistration(childSiteRegistration);


        Assert.assertNull(user.getActiveted());
        Assert.assertNull(new UsersManager().getLoginedUser());
        Assert.assertNull(ServiceLocator.getPersistance().getSiteBySubDomain("domainname"));

        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Assert.assertEquals("/account/registration/childSiteRigistrationConfirmationFirstState.jsp", resolutionMock.getPath());


        Assert.assertNotNull(user.getActiveted());
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Site site = ServiceLocator.getPersistance().getSiteBySubDomain("domainname");
        Assert.assertNotNull(site);
        Assert.assertNotNull(new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertEquals("domainname", site.getSubDomain());
        Assert.assertEquals("http://domainname.web-deva.com", action.getUrl());
        Assert.assertNotNull(action.getTellFriendHtml());
        Assert.assertEquals(2, site.getPages().size());

        Assert.assertEquals("siteName", site.getTitle());
        Assert.assertEquals(blueprint, site.getBlueprintParent());
    }

    @Test
    public void testExecute_WithSecondState() throws Exception {
        User user = TestUtil.createUser();
        user.setActiveted(null);
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setCreatedDate(new Date());
        settings.setUserId(user.getUserId());
        settings.setConfirmCode("confirm");
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setConfirmCode("confirm");


        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        childSiteRegistration.setBlueprintsId(Arrays.asList(1, 2));
        settings.setRequiredToUseSiteBlueprint(true);
        settings.setSitePaymentSettings(new SitePaymentSettings());
        settings.setChildSiteRegistration(childSiteRegistration);


        Assert.assertNull(user.getActiveted());
        Assert.assertNull(new UsersManager().getLoginedUser());


        ForwardResolution resolutionMock = (ForwardResolution) action.show();
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Assert.assertEquals("/account/registration/childSiteRigistrationConfirmationSecondState.jsp", resolutionMock.getPath());


        Assert.assertNotNull(user.getActiveted());
        Assert.assertNotNull(new UsersManager().getLoginedUser());
        Assert.assertEquals(user, new UsersManager().getLoginedUser());
        Assert.assertNull(action.getTellFriendHtml());
    }

    private final ChildSiteRegistrationConfirmationAction action = new ChildSiteRegistrationConfirmationAction();
}
