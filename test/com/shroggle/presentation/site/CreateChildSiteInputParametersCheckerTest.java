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

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.exception.*;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateChildSiteInputParametersCheckerTest {

    private final Persistance persistance = ServiceLocator.getPersistance();

    @Test(expected = UserNotFoundException.class)
    public void executeForNotFirstPageWithoutUserId() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        CreateChildSiteInputParametersChecker.executeForNotFirstPage(request);
    }

    @Test(expected = ChildSiteSettingsNotFoundException.class)
    public void executeForNotFirstPageWithoutSettingsId() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        final User user = TestUtil.createUser();
        request.setUserId(user.getUserId());
        CreateChildSiteInputParametersChecker.executeForNotFirstPage(request);
    }

    @Test(expected = ChildSiteRegistrationNotFoundException.class)
    public void executeForNotFirstPageWithoutFormId() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        final User user = TestUtil.createUser();
        request.setUserId(user.getUserId());
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        request.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        CreateChildSiteInputParametersChecker.executeForNotFirstPage(request);
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void executeForNotFirstPageWithoutFilledFormId() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        final User user = TestUtil.createUser();
        request.setUserId(user.getUserId());
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        request.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(parentSite);
        request.setFormId(childSiteRegistration.getFormId());
        request.setFilledFormId(0);
        CreateChildSiteInputParametersChecker.executeForNotFirstPage(request);
    }

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithWrongVerificationCode() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        CreateChildSiteInputParametersChecker.execute(request, -1, "sad");
    }

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithoutVerificationCode() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode(null);
        CreateChildSiteInputParametersChecker.execute(request, -1, "sad");
    }

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithoutSavedVerificationCode() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("code");
        CreateChildSiteInputParametersChecker.execute(request, -1, null);
    }

    @Test(expected = FormNotFoundException.class)
    public void executeWithFormNotFoundException() throws Exception {
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        CreateChildSiteInputParametersChecker.execute(request, -1, "aaa");
    }

    @Test(expected = VisitorWithNullOrEmptyEmailException.class)
    public void executeWithVisitorWithNullOrEmptyEmailException_emptyEmail() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("");
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = VisitorWithNullOrEmptyEmailException.class)
    public void executeWithVisitorWithNullOrEmptyEmailException_nullEmail() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail(null);
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }

    @Test(expected = VisitorWithNullPasswordException.class)
    public void executeWithVisitorWithNullPasswordException_nullPassword() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email@email.com");
        request.setPassword(null);
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = VisitorWithNullPasswordException.class)
    public void executeWithVisitorWithNullPasswordException_emptyPassword() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email@email.com");
        request.setPassword("");
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = VisitorWithNotEqualsPasswordAndConfirmPaswordException.class)
    public void executeWithVisitorWithNotEqualsPasswordAndConfirmPaswordException_emptyConfirmPassword() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email@email.com");
        request.setPassword("a");
        request.setConfirmPassword("");
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }

    @Test(expected = VisitorWithNotEqualsPasswordAndConfirmPaswordException.class)
    public void executeWithVisitorWithNotEqualsPasswordAndConfirmPaswordException_nullConfirmPassword() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email@email.com");
        request.setPassword("a");
        request.setConfirmPassword(null);
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = CannotCreateVisitorForNullSiteException.class)
    public void executeWithCannotCreateVisitorForNullSiteException() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        widget.getPage().setSite(null);
        persistance.putWidget(widget);
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email@email.com");
        request.setPassword("a");
        request.setConfirmPassword("a");
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }

    @Test(expected = NotValidVisitorEmailException.class)
    public void executeWithNotValidVisitorEmailException() throws Exception {
        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail("email");
        request.setPassword("a");
        request.setConfirmPassword("a");
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = ChildSiteRegistrationNotFoundException.class)
    public void executeWithChildSiteRegistrationNotFoundException() throws Exception {
        User user = TestUtil.createUser("email@email.com");
        user.setPassword("password");

        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFormId(-1);
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }


    @Test(expected = WrongSubDomainNameException.class)
    public void executeWithWrongSubDomainNameException() throws Exception {
        User user = TestUtil.createUser("email@email.com");
        user.setPassword("password");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);

        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFormId(registration.getFormId());

        FilledFormItem filledFormItemDomainName = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledFormItemDomainName.setValue("Just Text.");
        request.setFilledFormItems(Arrays.asList(filledFormItemDomainName));


        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }

    @Test(expected = VisitorWithNotUniqueLogin.class)
    public void executeWithVisitorWithNotUniqueLogin() throws Exception {
        User user = TestUtil.createUser("email@email.com");
        user.setPassword("password");

        final Widget widget = TestUtil.createWidgetGalleryForPageVersion(TestUtil.createPageVersionPageAndSite());
        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setVerificationCode("aaa");
        request.setEmail(user.getEmail());
        request.setPassword("a");
        request.setConfirmPassword("a");
        request.setFormId(-1);
        CreateChildSiteInputParametersChecker.execute(request, widget.getWidgetId(), "aaa");
    }

    @Test
    public void testCheckDomainName() {
        CreateChildSiteInputParametersChecker.checkDomainName("site");
    }

    @Test(expected = WrongSubDomainNameException.class)
    public void testCheckDomainName_withWrongDomainNameAndDomainNameRequired() {
        CreateChildSiteInputParametersChecker.checkDomainName("Just Text.");
    }

    @Test
    public void testCheckDomainName_withNullDomainName() {
        CreateChildSiteInputParametersChecker.checkDomainName(null);
    }

    @Test
    public void testCheckDomainName_withEmptyDomainName() {
        CreateChildSiteInputParametersChecker.checkDomainName("");
    }

}
