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

package com.shroggle.logic.advancedSearch.resultsNumber;

import com.shroggle.logic.form.FilledFormManager;
import junit.framework.Assert;
import org.junit.runner.RunWith;
import org.junit.Test;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.advancedSearch.AdvancedSearchManager;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AdvancedSearchResultsNumberCacheTest {

    @Test
    public void testGetResultsNumber_FOR_MULTISELECT_OPTIONS() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE,
                FormItemName.RELIGIONS, FormItemName.COUNTRY));
        gallery.setFormId1(form.getFormId());

        /* CREATING FILLED FORMS */
        final List<FilledForm> filledForms = preapareFilledForms(form);

        final List<Integer> fullFilledFormsHashCodesFetch = FilledFormManager.generateFilledFormHashCodes(filledForms);

        /* CREATING SEARCH RULES */
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("Search by Religions",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT, new ArrayList<String>() {{
                    add("new_religion");
                }});
        advancedSearch.addSearchOption(searchOption);

        //Let's check that "new_religion" has 1 result.
        int resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "new_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "sitty_religion" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "shitty_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Now let's add "new_religion" into request and check that "shitty_religion" still have 2 results.
        ServiceLocator.getContextStorage().get().setAdvancedSearchRequest(advancedSearch);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        List<Integer> filteredFilledFormsHashCodes = FilledFormManager.generateFilledFormHashCodes(filteredFilledForms);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "shitty_religion", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);

        //Let's select a a first criteria in multiselect option and see how many results we will get if we will select a second one.
        //After this we should get 1 filled form with "new_religion" and 2 results number for "shitty_religion".
        Assert.assertEquals(1, filteredFilledForms.size());
        Assert.assertEquals(2, resutlsNumber);
    }

    @Test
    public void testGetResultsNumber_FOR_SINGLESELECT_OPTIONS() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE,
                FormItemName.RELIGIONS, FormItemName.COUNTRY));
        gallery.setFormId1(form.getFormId());

        /* CREATING FILLED FORMS */
        final List<FilledForm> filledForms = preapareFilledForms(form);

        final List<Integer> fullFilledFormsHashCodesFetch = FilledFormManager.generateFilledFormHashCodes(filledForms);

        /* CREATING SEARCH RULES */
        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("Search by Country",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                    add("Russia");
                }});
        advancedSearch.addSearchOption(searchOption);

        //Let's check that "Russia" has 1 result.
        int resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "Russia", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "Ukraine" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "Ukraine", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Now let's add "Russia" into request and check that "Ukraine" still have 2 results.
        ServiceLocator.getContextStorage().get().setAdvancedSearchRequest(advancedSearch);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        List<Integer> filteredFilledFormsHashCodes = FilledFormManager.generateFilledFormHashCodes(filteredFilledForms);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption.getAdvancedSearchOptionId(),
                "Ukraine", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);

        //Let's select a a first criteria in multiselect option and see how many results we will get if we will select a second one.
        //After this we should get 1 filled form with "new_religion" and 2 results number for "shitty_religion".
        Assert.assertEquals(1, filteredFilledForms.size());
        Assert.assertEquals(2, resutlsNumber);
    }

    @Test
    public void testGetResultsNumber_COMPLEX() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE,
                FormItemName.RELIGIONS, FormItemName.COUNTRY));
        gallery.setFormId1(form.getFormId());

        /* CREATING FILLED FORMS */
        final List<FilledForm> filledForms = preapareFilledForms(form);

        final List<Integer> fullFilledFormsHashCodesFetch = FilledFormManager.generateFilledFormHashCodes(filledForms);

        /* CREATING SEARCH RULES */
        DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("Search by Country",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                }});
        DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("Search by Religions",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT, new ArrayList<String>() {{
                }});
        DraftAdvancedSearchOption searchOption3 = TestUtil.createAdvancedSearchOption("Search by date",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.RANGE_AS_SEP_OPTION, new ArrayList<String>() {{
                }});
        DraftAdvancedSearchOption searchOption4 = TestUtil.createAdvancedSearchOption("Search by Name",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>() {{
                }});
        advancedSearch.addSearchOption(searchOption1);
        advancedSearch.addSearchOption(searchOption2);
        advancedSearch.addSearchOption(searchOption3);
        advancedSearch.addSearchOption(searchOption4);

        /* CHECKING INITIAL RESULTS*/
        //Let's check that "Russia" has 1 result.
        int resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Russia", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "Ukraine" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Ukraine", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Let's check that "USA" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "USA", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "new_religion" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "new_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "cool_religion" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "cool_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "sitty_religion" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "shitty_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Let's check that "01;15;2010" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;15;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "12;17;1988" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "12;17;1988", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "01;16;2010" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;16;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "01;17;2010" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;17;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name1" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name1", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name2" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name2", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name3" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name3", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name4" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name4", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Now let's add "12;17;1988" and "01;15;2010" this will return 2 filled forms.
        advancedSearch.getAdvancedSearchOptions().get(2).setOptionCriteria(new ArrayList<String>() {{
            add("01;16;2010");
            add("01;15;2010");
        }});
        ServiceLocator.getContextStorage().get().setAdvancedSearchRequest(advancedSearch);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        List<Integer> filteredFilledFormsHashCodes = FilledFormManager.generateFilledFormHashCodes(filteredFilledForms);

        //Checking that 'Ukraine', 'Russia', 'new_religion', 'shitty_religion' - all have 1 reslut.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Ukraine", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Russia", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "new_religion", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "shitty_religion", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Now let's select 'Ukraine' from drop-down box.
        advancedSearch.getAdvancedSearchOptions().get(0).setOptionCriteria(new ArrayList<String>() {{
            add("Ukraine");
        }});
        ServiceLocator.getContextStorage().get().setAdvancedSearchRequest(advancedSearch);

        filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        filteredFilledFormsHashCodes = FilledFormManager.generateFilledFormHashCodes(filteredFilledForms);

        //Let's check that 'Russia' still have 1 result, 'new_religion' too and 'shitty_religion' - zero.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Russia", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "new_religion", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "shitty_religion", filteredFilledFormsHashCodes, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(0, resutlsNumber);
    }

    @Test
    public void testCaching() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE,
                FormItemName.RELIGIONS, FormItemName.COUNTRY));
        gallery.setFormId1(form.getFormId());

        /* CREATING FILLED FORMS */
        final List<FilledForm> filledForms = preapareFilledForms(form);

        final List<Integer> fullFilledFormsHashCodesFetch = FilledFormManager.generateFilledFormHashCodes(filledForms);

        /* CREATING SEARCH RULES */
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("Search by Country",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                }});
        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("Search by Religions",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                }});
        final DraftAdvancedSearchOption searchOption3 = TestUtil.createAdvancedSearchOption("Search by date",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                }});
        final DraftAdvancedSearchOption searchOption4 = TestUtil.createAdvancedSearchOption("Search by Name",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT, new ArrayList<String>() {{
                }});
        advancedSearch.addSearchOption(searchOption1);
        advancedSearch.addSearchOption(searchOption2);
        advancedSearch.addSearchOption(searchOption3);
        advancedSearch.addSearchOption(searchOption4);

        /* CHECKING INITIAL RESULTS*/
        //Let's check that "Russia" has 1 result.
        int resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Russia", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "Ukraine" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "Ukraine", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Let's check that "USA" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption1.getAdvancedSearchOptionId(),
                "USA", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "new_religion" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "new_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "cool_religion" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "cool_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "sitty_religion" has 2 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption2.getAdvancedSearchOptionId(),
                "shitty_religion", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(2, resutlsNumber);

        //Let's check that "01;15;2010" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;15;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "12;17;1988" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "12;17;1988", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "01;16;2010" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;16;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "01;17;2010" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption3.getAdvancedSearchOptionId(),
                "01;17;2010", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name1" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name1", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name2" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name2", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name3" has 1 result.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name3", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's check that "name4" has 1 results.
        resutlsNumber = ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                advancedSearch.getId(), gallery.getId(), searchOption4.getAdvancedSearchOptionId(),
                "name4", fullFilledFormsHashCodesFetch, fullFilledFormsHashCodesFetch, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals(1, resutlsNumber);

        //Let's now check that all criterias that we were searching by are in cache.
        final AdvancedSearchGalleryPair advancedSearchGalleryPair = ServiceLocator.getAdvancedSearchResultsNumberCache().
                findAdvancedSearchGalleryPairByIds(advancedSearch.getId(), gallery.getId());
        Assert.assertEquals(advancedSearch.getId(), advancedSearchGalleryPair.getAdvancedSearchId());
        Assert.assertEquals(gallery.getId(), advancedSearchGalleryPair.getGalleryId());
        Assert.assertEquals(14, advancedSearchGalleryPair.getCachedSearchCriterias().size());

        Assert.assertEquals("Russia", advancedSearchGalleryPair.getCachedSearchCriterias().get(0).getCriteria());
        Assert.assertEquals(searchOption1.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(0).getOptionId());

        Assert.assertEquals("Ukraine", advancedSearchGalleryPair.getCachedSearchCriterias().get(1).getCriteria());
        Assert.assertEquals(searchOption1.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(1).getOptionId());

        Assert.assertEquals("USA", advancedSearchGalleryPair.getCachedSearchCriterias().get(2).getCriteria());
        Assert.assertEquals(searchOption1.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(2).getOptionId());

        Assert.assertEquals("new_religion", advancedSearchGalleryPair.getCachedSearchCriterias().get(3).getCriteria());
        Assert.assertEquals(searchOption2.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(3).getOptionId());

        Assert.assertEquals("cool_religion", advancedSearchGalleryPair.getCachedSearchCriterias().get(4).getCriteria());
        Assert.assertEquals(searchOption2.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(4).getOptionId());

        Assert.assertEquals("shitty_religion", advancedSearchGalleryPair.getCachedSearchCriterias().get(5).getCriteria());
        Assert.assertEquals(searchOption2.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(5).getOptionId());

        Assert.assertEquals("01;15;2010", advancedSearchGalleryPair.getCachedSearchCriterias().get(6).getCriteria());
        Assert.assertEquals(searchOption3.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(6).getOptionId());

        Assert.assertEquals("12;17;1988", advancedSearchGalleryPair.getCachedSearchCriterias().get(7).getCriteria());
        Assert.assertEquals(searchOption3.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(7).getOptionId());

        Assert.assertEquals("01;16;2010", advancedSearchGalleryPair.getCachedSearchCriterias().get(8).getCriteria());
        Assert.assertEquals(searchOption3.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(8).getOptionId());

        Assert.assertEquals("01;17;2010", advancedSearchGalleryPair.getCachedSearchCriterias().get(9).getCriteria());
        Assert.assertEquals(searchOption3.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(9).getOptionId());

        Assert.assertEquals("name1", advancedSearchGalleryPair.getCachedSearchCriterias().get(10).getCriteria());
        Assert.assertEquals(searchOption4.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(10).getOptionId());

        Assert.assertEquals("name2", advancedSearchGalleryPair.getCachedSearchCriterias().get(11).getCriteria());
        Assert.assertEquals(searchOption4.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(11).getOptionId());

        Assert.assertEquals("name3", advancedSearchGalleryPair.getCachedSearchCriterias().get(12).getCriteria());
        Assert.assertEquals(searchOption4.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(12).getOptionId());

        Assert.assertEquals("name4", advancedSearchGalleryPair.getCachedSearchCriterias().get(13).getCriteria());
        Assert.assertEquals(searchOption4.getAdvancedSearchOptionId(),
                advancedSearchGalleryPair.getCachedSearchCriterias().get(13).getOptionId());

        Assert.assertEquals(4, advancedSearchGalleryPair.getFullFilledFormsHashCodesFetch().size());
    }

    private List<FilledForm> preapareFilledForms(final DraftForm form) {
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name1");
        final FilledFormItem filledFormItem1_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem1_2.setValues(Arrays.asList("01", "15", "2010"));
        final FilledFormItem filledFormItem1_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "new_religion");
        final FilledFormItem filledFormItem1_4 = TestUtil.createFilledFormItem(form.getFormItems().get(3).getFormItemId(),
                FormItemName.COUNTRY, "Ukraine");
        filledFormItems.add(filledFormItem1_1);
        filledFormItems.add(filledFormItem1_2);
        filledFormItems.add(filledFormItem1_3);
        filledFormItems.add(filledFormItem1_4);
        filledForm1.setFilledFormItems(filledFormItems);

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem2_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        final FilledFormItem filledFormItem2_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem2_2.setValues(Arrays.asList("12", "17", "1988"));
        final FilledFormItem filledFormItem2_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "shitty_religion");
        final FilledFormItem filledFormItem2_4 = TestUtil.createFilledFormItem(form.getFormItems().get(3).getFormItemId(),
                FormItemName.COUNTRY, "Ukraine");
        filledFormItems2.add(filledFormItem2_1);
        filledFormItems2.add(filledFormItem2_2);
        filledFormItems2.add(filledFormItem2_3);
        filledFormItems2.add(filledFormItem2_4);

        filledForm2.setFilledFormItems(filledFormItems2);

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem3_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name3");
        final FilledFormItem filledFormItem3_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem3_2.setValues(Arrays.asList("01", "16", "2010"));
        final FilledFormItem filledFormItem3_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "shitty_religion");
        final FilledFormItem filledFormItem3_4 = TestUtil.createFilledFormItem(form.getFormItems().get(3).getFormItemId(),
                FormItemName.COUNTRY, "Russia");
        filledFormItems3.add(filledFormItem3_1);
        filledFormItems3.add(filledFormItem3_2);
        filledFormItems3.add(filledFormItem3_3);
        filledFormItems3.add(filledFormItem3_4);

        filledForm3.setFilledFormItems(filledFormItems3);

        final FilledForm filledForm4 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems4 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem4_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name4");
        final FilledFormItem filledFormItem4_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem4_2.setValues(Arrays.asList("01", "17", "2010"));
        final FilledFormItem filledFormItem4_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "cool_religion");
        final FilledFormItem filledFormItem4_4 = TestUtil.createFilledFormItem(form.getFormItems().get(3).getFormItemId(),
                FormItemName.COUNTRY, "USA");
        filledFormItems4.add(filledFormItem4_1);
        filledFormItems4.add(filledFormItem4_2);
        filledFormItems4.add(filledFormItem4_3);
        filledFormItems4.add(filledFormItem4_4);
        filledForm4.setFilledFormItems(filledFormItems4);

        final List<FilledForm> initialFilledForms = new ArrayList<FilledForm>();
        initialFilledForms.add(filledForm1);
        initialFilledForms.add(filledForm2);
        initialFilledForms.add(filledForm3);
        initialFilledForms.add(filledForm4);

        return initialFilledForms;
    }

}
