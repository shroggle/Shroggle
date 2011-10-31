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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.advancedSearch.AdvancedSearchHelper;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class EditSearchOptionsServiceTest extends TestCase {

    private final EditSearchOptionsService service = new EditSearchOptionsService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testShowForNew() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_PASSWORD));

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setNewSearchOptions(null);
        request.setAdvancedSearchId(null);

        Assert.assertEquals("/advancedSearch/editSearchOptions.jsp", service.show(request));
        Assert.assertEquals(form.getFormId(), (int) service.getFormId());
        Assert.assertEquals(1, service.getSelectedFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, service.getSelectedFormItems().get(0).getFormItemName());
        Assert.assertEquals(new ArrayList<DraftAdvancedSearchOption>(),
                service.getContext().getHttpServletRequest().getAttribute("searchOptions"));
        Assert.assertEquals(site.getSiteId(),
                service.getContext().getHttpServletRequest().getAttribute("siteId"));
        Assert.assertEquals(form.getFormId(),
                service.getContext().getHttpServletRequest().getAttribute("formId"));
    }
    
    @Test
    public void testShowForNewWithSomeAdditionalIncorrectFormItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME,
                FormItemName.REGISTRATION_PASSWORD, FormItemName.PAGE_BREAK, FormItemName.IMAGE_FILE_UPLOAD));

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setNewSearchOptions(null);
        request.setAdvancedSearchId(null);

        Assert.assertEquals("/advancedSearch/editSearchOptions.jsp", service.show(request));
        Assert.assertEquals(form.getFormId(), (int) service.getFormId());
        Assert.assertEquals(1, service.getSelectedFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, service.getSelectedFormItems().get(0).getFormItemName());
        Assert.assertEquals(new ArrayList<DraftAdvancedSearchOption>(),
                service.getContext().getHttpServletRequest().getAttribute("searchOptions"));
        Assert.assertEquals(site.getSiteId(),
                service.getContext().getHttpServletRequest().getAttribute("siteId"));
        Assert.assertEquals(form.getFormId(),
                service.getContext().getHttpServletRequest().getAttribute("formId"));
    }
    
    @Test
    public void testShowForNewWithoutForm() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(null);
        request.setSiteId(site.getSiteId());
        request.setNewSearchOptions(null);
        request.setAdvancedSearchId(null);

        Assert.assertEquals("/advancedSearch/editSearchOptions.jsp", service.show(request));
        Assert.assertNotNull((int) service.getFormId());
        final List<DraftFormItem> defSearchItems =
                new AdvancedSearchHelper().createDefaultSearchForm(site.getSiteId(), new UserManager(user)).getItems();
        Assert.assertEquals(defSearchItems.size() - 1 /* -1 is because one of the items is IMAGE_FILE_UPLOAD witch is not included into selected search items */,
                service.getSelectedFormItems().size());
        Assert.assertEquals(new ArrayList<DraftAdvancedSearchOption>(),
                service.getContext().getHttpServletRequest().getAttribute("searchOptions"));
        Assert.assertEquals(site.getSiteId(),
                service.getContext().getHttpServletRequest().getAttribute("siteId"));
        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("formId"));
    }

    @Test
    public void testShowForExisting() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_PASSWORD));

        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setNewSearchOptions(null);

        Assert.assertEquals("/advancedSearch/editSearchOptions.jsp", service.show(request));
        Assert.assertEquals(form.getFormId(), (int) service.getFormId());
        Assert.assertEquals(1, service.getSelectedFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, service.getSelectedFormItems().get(0).getFormItemName());
        final List<DraftAdvancedSearchOption> searchOptionsInService =
                ((List<DraftAdvancedSearchOption>) service.getContext().getHttpServletRequest().getAttribute("searchOptions"));
        Assert.assertEquals(1, searchOptionsInService.size());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_FREE, searchOptionsInService.get(0).getDisplayType());
        Assert.assertEquals("test", searchOptionsInService.get(0).getFieldLabel());
        Assert.assertEquals(site.getSiteId(),
                service.getContext().getHttpServletRequest().getAttribute("siteId"));
        Assert.assertEquals(form.getFormId(),
                service.getContext().getHttpServletRequest().getAttribute("formId"));
    }

    @Test
    public void testShowForExistingWithNewSearchOptions() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_PASSWORD));

        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test1",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>());
        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOptions.add(searchOption1);

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setNewSearchOptions(searchOptions);

        Assert.assertEquals("/advancedSearch/editSearchOptions.jsp", service.show(request));
        Assert.assertEquals(form.getFormId(), (int) service.getFormId());
        Assert.assertEquals(1, service.getSelectedFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, service.getSelectedFormItems().get(0).getFormItemName());
        final List<DraftAdvancedSearchOption> searchOptionsInService =
                ((List<DraftAdvancedSearchOption>) service.getContext().getHttpServletRequest().getAttribute("searchOptions"));
        Assert.assertEquals(2, searchOptionsInService.size());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_FREE, searchOptionsInService.get(0).getDisplayType());
        Assert.assertEquals("test", searchOptionsInService.get(0).getFieldLabel());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_SEP_OPTION, searchOptionsInService.get(1).getDisplayType());
        Assert.assertEquals("test1", searchOptionsInService.get(1).getFieldLabel());
        Assert.assertEquals(site.getSiteId(),
                service.getContext().getHttpServletRequest().getAttribute("siteId"));
        Assert.assertEquals(form.getFormId(),
                service.getContext().getHttpServletRequest().getAttribute("formId"));
    }
    
    @Test(expected = UserNotLoginedException.class)
    public void testShowWithNotLoginedUser() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_PASSWORD));

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setAdvancedSearchId(null);
        request.setNewSearchOptions(null);

        service.show(request);
    }
    
    @Test(expected = AdvancedSearchNotFoundException.class)
    public void testShowWithNotFoundAdvancedSearch() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_PASSWORD));

        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        final EditSearchOptionsRequest request = new EditSearchOptionsRequest();
        request.setFormId(form.getFormId());
        request.setSiteId(site.getSiteId());
        request.setAdvancedSearchId(0);
        request.setNewSearchOptions(null);

        service.show(request);
    }

}
