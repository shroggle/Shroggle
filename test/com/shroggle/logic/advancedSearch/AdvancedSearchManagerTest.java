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
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AdvancedSearchManagerTest extends TestCase {

    @Test
    public void testGetSearchOptionById() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("test1",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>());
        searchOptions.add(searchOption1);
        searchOptions.add(searchOption2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        final DraftAdvancedSearchOption foundSearchOption1 =
                new AdvancedSearchManager(advancedSearch).getSearchOptionById(searchOption2.getAdvancedSearchOptionId());

        Assert.assertEquals(foundSearchOption1.getAdvancedSearchOptionId(), searchOption2.getAdvancedSearchOptionId());

        final DraftAdvancedSearchOption foundSearchOption2 = new AdvancedSearchManager(advancedSearch).getSearchOptionById(0);

        Assert.assertNull(foundSearchOption2);
    }

    @Test
    public void testGetFilteredFilledForms_SIMPLE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name1");
        filledFormItems.add(filledFormItem1);
        filledForm1.setFilledFormItems(filledFormItems);

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItems2.add(filledFormItem2);
        filledForm2.setFilledFormItems(filledFormItems2);

        final List<FilledForm> initialFilledForms = new ArrayList<FilledForm>();
        initialFilledForms.add(filledForm1);
        initialFilledForms.add(filledForm2);

        /* CREATING SEARCH RULES */
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        Assert.assertEquals(0, filteredFilledForms.size());

        /* CREATING SEARCH RULES */
        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("name");
                }});
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        Assert.assertEquals(2, filteredFilledForms.size());

        /* CREATING SEARCH RULES */
        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("name1");
                }});
        searchOptions.add(searchOption);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        Assert.assertEquals(1, filteredFilledForms.size());
    }

    @Test
    public void testGetFilteredFilledForms_MULTISELECT_OPTIONS_TEST() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE, FormItemName.RELIGIONS));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        final FilledFormItem filledFormItem1_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem1_2.setValues(Arrays.asList("01", "15", "2010"));
        final FilledFormItem filledFormItem1_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "new_religion");
        filledFormItems.add(filledFormItem1_1);
        filledFormItems.add(filledFormItem1_2);
        filledFormItems.add(filledFormItem1_3);
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
        filledFormItems2.add(filledFormItem2_1);
        filledFormItems2.add(filledFormItem2_2);
        filledFormItems2.add(filledFormItem2_3);
        filledForm2.setFilledFormItems(filledFormItems2);

        final List<FilledForm> initialFilledForms = new ArrayList<FilledForm>();
        initialFilledForms.add(filledForm1);
        initialFilledForms.add(filledForm2);

        /* CREATING SEARCH RULES */
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        DraftAdvancedSearchOption searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                    add("shitty_religion");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Checking that by selecting two criterias in one multiselect search option fetches from these criterias
        //weren't intersected but joined.
        Assert.assertEquals(2, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                }});
        DraftAdvancedSearchOption searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;1988");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Now unselecting one of criterias and selecting another one from other search option.
        Assert.assertEquals(0, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;1988");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Ok, now unselecting new_religion criteria, now we should get one result.
        Assert.assertEquals(1, filteredFilledForms.size());
    }

    @Test
    public void testGetFilteredFilledForms_DATE_RANGES_TEST() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE, FormItemName.RELIGIONS));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        final FilledFormItem filledFormItem1_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem1_2.setValues(Arrays.asList("01", "15", "2010"));
        final FilledFormItem filledFormItem1_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "new_religion");
        filledFormItems.add(filledFormItem1_1);
        filledFormItems.add(filledFormItem1_2);
        filledFormItems.add(filledFormItem1_3);
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
        filledFormItems2.add(filledFormItem2_1);
        filledFormItems2.add(filledFormItem2_2);
        filledFormItems2.add(filledFormItem2_3);
        filledForm2.setFilledFormItems(filledFormItems2);

        final List<FilledForm> initialFilledForms = new ArrayList<FilledForm>();
        initialFilledForms.add(filledForm1);
        initialFilledForms.add(filledForm2);

        /* CREATING SEARCH RULES */
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        DraftAdvancedSearchOption searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;1988");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Checking single value equals
        Assert.assertEquals(1, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;6757");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Checking single value not equals
        Assert.assertEquals(0, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.RANGE_AS_RANGE,
                new ArrayList<String>() {{
                    add("01;01;1900;01;01;2100");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Now let's check range that includes two values freely.
        Assert.assertEquals(2, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;1988;01;15;2010");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Now let's the same but range start and end's on filled form values.
        Assert.assertEquals(2, filteredFilledForms.size());
    }

    @Test
    public void testGetFilteredFilledForms_SINGLE_SELECT_OPTIONS_TEST() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE, FormItemName.RELIGIONS));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1_1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        final FilledFormItem filledFormItem1_2 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "");
        filledFormItem1_2.setValues(Arrays.asList("01", "15", "2010"));
        final FilledFormItem filledFormItem1_3 = TestUtil.createFilledFormItem(form.getFormItems().get(2).getFormItemId(),
                FormItemName.RELIGIONS, "new_religion");
        filledFormItems.add(filledFormItem1_1);
        filledFormItems.add(filledFormItem1_2);
        filledFormItems.add(filledFormItem1_3);
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
        filledFormItems2.add(filledFormItem2_1);
        filledFormItems2.add(filledFormItem2_2);
        filledFormItems2.add(filledFormItem2_3);
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
        filledFormItems3.add(filledFormItem3_1);
        filledFormItems3.add(filledFormItem3_2);
        filledFormItems3.add(filledFormItem3_3);
        filledForm3.setFilledFormItems(filledFormItems3);

        final List<FilledForm> initialFilledForms = new ArrayList<FilledForm>();
        initialFilledForms.add(filledForm1);
        initialFilledForms.add(filledForm2);
        initialFilledForms.add(filledForm3);

        /* CREATING SEARCH RULES */
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        DraftAdvancedSearchOption searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                    add("shitty_religion");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Checking absurd situation when two values in one single select option are selected. It should return zero.
        Assert.assertEquals(0, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("shitty_religion");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Selecting one of single select options.
        Assert.assertEquals(2, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("shitty_religion");
                }});
        DraftAdvancedSearchOption searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;1988");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Selecting one of single select options and selecting one other option that includes one of results from first option.
        Assert.assertEquals(1, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("shitty_religion");
                }});
        searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("12;17;5676");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Selecting one of single select options and selecting one other option that has different fetch.
        Assert.assertEquals(0, filteredFilledForms.size());
    }

    // This test contains single and multiselect options at same time. Additionaly it has a free text option.
    @Test
    public void testGetFilteredFilledForms_COMPLEX() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE, FormItemName.RELIGIONS, FormItemName.COUNTRY));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        /* CREATING FILLED FORMS */
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

        /* CREATING SEARCH RULES */
        List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        DraftAdvancedSearchOption searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                    add("shitty_religion");
                }});
        searchOptions.add(searchOption_1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        List<FilledForm> filteredFilledForms =
                new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Firstly, let's select two criterias from one multiselect option.
        Assert.assertEquals(3, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                    add("shitty_religion");
                }});
        DraftAdvancedSearchOption searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT,
                new ArrayList<String>() {{
                    add("Ukraine");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Now let's concretize current fetch by selecting one of single select options.
        Assert.assertEquals(2, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("new_religion");
                    add("shitty_religion");
                }});
        searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT,
                new ArrayList<String>() {{
                    add("Ukraine");
                }});
        DraftAdvancedSearchOption searchOption_3 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("name1");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        searchOptions.add(searchOption_3);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //More concretizing. Let's enter 'name1' into free text search
        Assert.assertEquals(1, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("shitty_religion");
                }});
        searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT,
                new ArrayList<String>() {{
                    add("Ukraine");
                }});
        searchOption_3 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("name1");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        searchOptions.add(searchOption_3);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Ok, and now let's deselct 'new_religion' options, after this we should get empty search results.
        Assert.assertEquals(0, filteredFilledForms.size());

        searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        searchOption_1 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.PICK_LIST_MULTISELECT,
                new ArrayList<String>() {{
                    add("shitty_religion");
                }});
        searchOption_2 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(3).getFormItemId(), OptionDisplayType.PICK_LIST_SELECT,
                new ArrayList<String>() {{
                    add("Ukraine");
                }});
        searchOption_3 = TestUtil.createAdvancedSearchOption("search option",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("name");
                }});
        searchOptions.add(searchOption_1);
        searchOptions.add(searchOption_2);
        searchOptions.add(searchOption_3);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        filteredFilledForms = new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(initialFilledForms);

        //Now let's enter another text into free text search - 'name' this should return all 4 filled forms. If to count
        //existing search - 1
        Assert.assertEquals(1, filteredFilledForms.size());
    }

    @Test
    public void testRemoveAllOptions() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOption1.setAdvancedSearch(advancedSearch);
        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("test1",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION, new ArrayList<String>());
        searchOption2.setAdvancedSearch(advancedSearch);
        searchOptions.add(searchOption1);
        searchOptions.add(searchOption2);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        new AdvancedSearchManager(advancedSearch).removeAllOptions();

        Assert.assertNull(ServiceLocator.getPersistance().getAdvancedSearchOptionById(searchOption1.getAdvancedSearchOptionId()));
        Assert.assertNull(ServiceLocator.getPersistance().getAdvancedSearchOptionById(searchOption2.getAdvancedSearchOptionId()));
    }

    @Test
    public void testCreateOrUpdateSearchOptions() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOptions.add(searchOption1);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        final List<DraftAdvancedSearchOption> searchOptionsInRequest = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1_updated = TestUtil.createAdvancedSearchOption("test_new_name",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_SEP_OPTION,
                new ArrayList<String>() {{
                    add("test");
                }});
        searchOption1_updated.setAdvancedSearchOptionId(searchOption1.getAdvancedSearchOptionId());
        searchOption1_updated.setAlsoSearchByFields(new ArrayList<Integer>() {{
            add(111);
            add(222);
        }});
        searchOptionsInRequest.add(searchOption1_updated);
        final DraftAdvancedSearchOption searchOption2_new = TestUtil.createAdvancedSearchOption("test1",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test1");
                }});
        searchOption2_new.setAdvancedSearchOptionId(0);
        searchOptionsInRequest.add(searchOption2_new);

        new AdvancedSearchManager(advancedSearch).createOrUpdateSearchOptions(searchOptionsInRequest);
        Assert.assertEquals(2, advancedSearch.getAdvancedSearchOptions().size());

        Assert.assertEquals(OptionDisplayType.TEXT_AS_SEP_OPTION, advancedSearch.getAdvancedSearchOptions().get(0).getDisplayType());
        Assert.assertEquals("test_new_name", advancedSearch.getAdvancedSearchOptions().get(0).getFieldLabel());
        Assert.assertEquals(form.getFormItems().get(0).getFormItemId(), advancedSearch.getAdvancedSearchOptions().get(0).getFormItemId());
        Assert.assertEquals(1, advancedSearch.getAdvancedSearchOptions().get(0).getCriteria().size());
        Assert.assertEquals("test", advancedSearch.getAdvancedSearchOptions().get(0).getCriteria().get(0));
        Assert.assertEquals(2, advancedSearch.getAdvancedSearchOptions().get(0).getAlsoSearchByFields().size());
        Assert.assertEquals(111, (int) advancedSearch.getAdvancedSearchOptions().get(0).getAlsoSearchByFields().get(0));
        Assert.assertEquals(222, (int) advancedSearch.getAdvancedSearchOptions().get(0).getAlsoSearchByFields().get(1));

        Assert.assertEquals(OptionDisplayType.TEXT_AS_FREE, advancedSearch.getAdvancedSearchOptions().get(1).getDisplayType());
        Assert.assertEquals("test1", advancedSearch.getAdvancedSearchOptions().get(1).getFieldLabel());
        Assert.assertEquals(form.getFormItems().get(1).getFormItemId(), advancedSearch.getAdvancedSearchOptions().get(1).getFormItemId());
        Assert.assertEquals(1, advancedSearch.getAdvancedSearchOptions().get(1).getCriteria().size());
        Assert.assertEquals("test1", advancedSearch.getAdvancedSearchOptions().get(1).getCriteria().get(0));
    }

    @Test(expected = AdvancedSearchNotFoundException.class)
    public void testInitializeWithoutAdvancedSearch() throws Exception {
        new AdvancedSearchManager(null);
    }

    @Test(expected = AdvancedSearchNotFoundException.class)
    public void testInitializeWithoutAdvancedSearchId() throws Exception {
        new AdvancedSearchManager(-1);
    }
}
