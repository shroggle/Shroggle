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

package com.shroggle.logic.advancedSearch;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.AdvancedSearchNotUniqueNameException;
import com.shroggle.exception.AdvancedSearchNullOrEmptyNameException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.advancedSearch.SaveAdvancedSearchRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AdvancedSearchCreatorTest extends TestCase {

    private Persistance persistance = ServiceLocator.getPersistance();

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        PageManager pageManager = new PageManager(widgetItem.getPage());
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        final DraftGallery gallery2 = TestUtil.createGallery(site);
        final DraftAdvancedSearch existingAdvancedSearch = TestUtil.createAdvancedSearch(site);
        existingAdvancedSearch.setGalleryId(gallery1.getId());
        existingAdvancedSearch.setAdvancedSearchOrientationType(AdvancedSearchOrientationType.ABOVE);
        existingAdvancedSearch.setDescription("Comment");
        existingAdvancedSearch.setDisplayHeaderComments(true);
        existingAdvancedSearch.setIncludeResultsNumber(true);

        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        existingAdvancedSearch.setAdvancedSearchOptions(searchOptions);

        widgetItem.setDraftItem(existingAdvancedSearch);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setAdvancedSearchId(existingAdvancedSearch.getId());
        request.setName("new name");
        request.setIncludeResultsNumber(false);
        request.setDisplayHeaderComment(false);
        request.setGalleryId(gallery2.getId());
        request.setHeaderComment("new comment");
        request.setAdvancedSearchId(existingAdvancedSearch.getId());
        request.setOrientationType(AdvancedSearchOrientationType.LEFT);
        request.setWidgetId(widgetItem.getWidgetId());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOptionInRequest = TestUtil.createAdvancedSearchOption("new_label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>());
        searchOptionInRequest.setAdvancedSearchOptionId(0);
        searchOptions.add(searchOptionInRequest);

        final DraftAdvancedSearchOption searchOptionInRequest2 = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(1).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>());
        searchOptionInRequest2.setAdvancedSearchOptionId(0);
        searchOptions.add(searchOptionInRequest2);

        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);
        final DraftAdvancedSearch createdAdvancedSearch = (DraftAdvancedSearch)(widgetItem.getDraftItem());

        Assert.assertNotNull(createdAdvancedSearch);
        Assert.assertEquals("new name", createdAdvancedSearch.getName());
        Assert.assertEquals(false, createdAdvancedSearch.isIncludeResultsNumber());
        Assert.assertEquals(false, createdAdvancedSearch.isDisplayHeaderComments());
        Assert.assertEquals("new comment", createdAdvancedSearch.getDescription());
        Assert.assertEquals(AdvancedSearchOrientationType.LEFT, createdAdvancedSearch.getAdvancedSearchOrientationType());
        Assert.assertNotNull(createdAdvancedSearch.getGalleryId());
        final DraftGallery galleryFromAdvancedSearch = persistance.getGalleryById(createdAdvancedSearch.getGalleryId());
        Assert.assertEquals(gallery2.getId(), galleryFromAdvancedSearch.getId());
        Assert.assertEquals(3, createdAdvancedSearch.getAdvancedSearchOptions().size());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_FREE, createdAdvancedSearch.getAdvancedSearchOptions().get(0).getDisplayType());
        Assert.assertEquals("label", createdAdvancedSearch.getAdvancedSearchOptions().get(0).getFieldLabel());
        Assert.assertEquals(customForm.getFormItems().get(0).getFormItemId(), createdAdvancedSearch.getAdvancedSearchOptions().get(0).getFormItemId());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_SEP_OPTION, createdAdvancedSearch.getAdvancedSearchOptions().get(1).getDisplayType());
        Assert.assertEquals("new_label", createdAdvancedSearch.getAdvancedSearchOptions().get(1).getFieldLabel());
        Assert.assertEquals(customForm.getFormItems().get(0).getFormItemId(), createdAdvancedSearch.getAdvancedSearchOptions().get(1).getFormItemId());
        Assert.assertEquals(OptionDisplayType.TEXT_AS_SEP_OPTION, createdAdvancedSearch.getAdvancedSearchOptions().get(2).getDisplayType());
        Assert.assertEquals("label", createdAdvancedSearch.getAdvancedSearchOptions().get(2).getFieldLabel());
        Assert.assertEquals(customForm.getFormItems().get(1).getFormItemId(), createdAdvancedSearch.getAdvancedSearchOptions().get(2).getFormItemId());
        Assert.assertFalse(pageManager.isChanged());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Search");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setAdvancedSearchId(advancedSearch.getId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);

        Assert.assertNotNull(advancedSearch);
        Assert.assertEquals("Advanced Search", advancedSearch.getName());
        Assert.assertEquals(true, advancedSearch.isIncludeResultsNumber());
        Assert.assertEquals(true, advancedSearch.isDisplayHeaderComments());
        Assert.assertEquals("Comment", advancedSearch.getDescription());
        Assert.assertEquals(AdvancedSearchOrientationType.ABOVE, advancedSearch.getAdvancedSearchOrientationType());
        Assert.assertEquals(0, advancedSearch.getAdvancedSearchOptions().size());
        Assert.assertNotNull(advancedSearch.getGalleryId());
    }

    @Test
    public void testSaveNewOne_withForm() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Search");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setFormId(customForm.getFormId());
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setAdvancedSearchId(advancedSearch.getId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);

        Assert.assertNotNull(advancedSearch);
        Assert.assertEquals("Advanced Search", advancedSearch.getName());
        Assert.assertEquals(true, advancedSearch.isIncludeResultsNumber());
        Assert.assertEquals(true, advancedSearch.isDisplayHeaderComments());
        Assert.assertEquals("Comment", advancedSearch.getDescription());
        Assert.assertEquals(AdvancedSearchOrientationType.ABOVE, advancedSearch.getAdvancedSearchOrientationType());
        Assert.assertNotNull(advancedSearch.getGalleryId());
        final DraftGallery gallery = persistance.getGalleryById(advancedSearch.getGalleryId());
        Assert.assertEquals(customForm.getFormId(), gallery.getFormId1());
        Assert.assertEquals(0, advancedSearch.getAdvancedSearchOptions().size());
    }

    @Test
    public void testSaveNewOne_withGallery() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftGallery gallery = TestUtil.createGallery(site);

        DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Search");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setGalleryId(gallery.getId());
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setAdvancedSearchId(advancedSearch.getId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);

        Assert.assertNotNull(advancedSearch);
        Assert.assertEquals("Advanced Search", advancedSearch.getName());
        Assert.assertEquals(true, advancedSearch.isIncludeResultsNumber());
        Assert.assertEquals(true, advancedSearch.isDisplayHeaderComments());
        Assert.assertEquals("Comment", advancedSearch.getDescription());
        Assert.assertEquals(AdvancedSearchOrientationType.ABOVE, advancedSearch.getAdvancedSearchOrientationType());
        Assert.assertNotNull(advancedSearch.getGalleryId());
        final DraftGallery galleryFromAdvancedSearch = persistance.getGalleryById(advancedSearch.getGalleryId());
        Assert.assertEquals(gallery.getId(), galleryFromAdvancedSearch.getId());
        Assert.assertEquals(0, advancedSearch.getAdvancedSearchOptions().size());
    }

    @Test(expected = AdvancedSearchNullOrEmptyNameException.class)
    public void testSaveWithoutName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftGallery gallery = TestUtil.createGallery(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName(null);
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setGalleryId(gallery.getId());
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setWidgetId(widgetItem.getWidgetId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);
    }

    @Test(expected = AdvancedSearchNotUniqueNameException.class)
    public void testSaveWithExistingAdvancedSearch() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftGallery gallery = TestUtil.createGallery(site);

        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        advancedSearch.setName("Advanced Search");

        final DraftAdvancedSearch duplicateAdvancedSearch = TestUtil.createAdvancedSearch(site);
        duplicateAdvancedSearch.setName("Advanced Search2");

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Search2");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setGalleryId(gallery.getId());
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setWidgetId(widgetItem.getWidgetId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);
    }
    
    @Test(expected = AdvancedSearchNotFoundException.class)
    public void executeWithoutAdvancedSearchId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftGallery gallery = TestUtil.createGallery(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Searc2");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setGalleryId(gallery.getId());
        request.setAdvancedSearchId(-1);
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setWidgetId(widgetItem.getWidgetId());

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);
    }
    
    @Test(expected = WidgetNotFoundException.class)
    public void testSaveWithoutWidgetId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftGallery gallery = TestUtil.createGallery(site);

        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setName("Advanced Search");
        request.setIncludeResultsNumber(true);
        request.setDisplayHeaderComment(true);
        request.setGalleryId(gallery.getId());
        request.setHeaderComment("Comment");
        request.setOrientationType(AdvancedSearchOrientationType.ABOVE);
        request.setWidgetId(0);

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("label",
                customForm.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption);
        request.setSearchOptions(searchOptions);

        new AdvancedSearchCreator(new UserManager(user)).save(request);
    }

}
