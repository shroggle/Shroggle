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
import com.shroggle.exception.MalformedDateRangeException;
import com.shroggle.logic.user.UserManager;
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
public class AdvancedSearchHelperTest extends TestCase {

    @Test
    public void testFormatRangeAsDateFormatAsSingleDate() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), "12;17;1988", false);
        Assert.assertEquals("December/17/1988", range);
    }

    @Test
    public void testFormatRangeAsDateFormatAsRange_ONLY_TILL_DATE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), "12;17;1988", true);
        Assert.assertEquals(">&nbsp;December/17/1988", range);
    }

    @Test
    public void testFormatRangeAsDateFormatAsRangeWithSomeEmptyDataParameters_ONLY_TILL_DATE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), "12;;1988", true);
        Assert.assertEquals(">&nbsp;December/&ndash;/1988", range);
    }

    @Test
    public void testFormatRangeAsDateFormatAsRangeWithSomeEmptyDataParameters_ONLY_FROM_DATE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), ";;;12;;1988", true);
        Assert.assertEquals("<&nbsp;December/&ndash;/1988", range);
    }

    @Test
    public void testFormatRangeAsDateFormatAsRange_ONLY_FROM_DATE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), ";;;12;17;1988", true);
        Assert.assertEquals("<&nbsp;December/17/1988", range);
    }

    @Test
    public void testFormatRangeAsDateFormatAsRange_BOTH_DATES() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), "12;17;1988;12;25;2009", true);
        Assert.assertEquals("December/17/1988&nbsp;&mdash;&nbsp;December/25/2009", range);
    }

    @Test
    public void testFormatRangeAsDateForNonDate() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final String range = AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), "", true);
        Assert.assertTrue(range.isEmpty());
    }

    @Test(expected = MalformedDateRangeException.class)
    public void testFormatRangeAsDateForBothRangesAreEmpty() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.BIRTH_DATE));

        AdvancedSearchHelper.formatRange(form.getDraftFormItems().get(0), ";;;;;;", true);
    }

    @Test
    public void testAddSeparateOptionCriteria() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE));

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(form.getFormItems().get(1).getFormItemId(),
                FormItemName.BIRTH_DATE, "name1");
        filledFormItem1.setValues(new ArrayList<String>() {{
            add("12");
            add("17");
            add("1988");
        }});
        filledFormItems.add(filledFormItem1);
        filledForm1.setFilledFormItems(filledFormItems);

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItem2.setValues(new ArrayList<String>() {{
            add("name_1");
        }});
        filledFormItems2.add(filledFormItem2);
        filledForm2.setFilledFormItems(filledFormItems2);

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItem3.setValues(new ArrayList<String>() {{
            add("name_2");
        }});
        filledFormItems3.add(filledFormItem3);
        filledForm3.setFilledFormItems(filledFormItems3);

        /* CREATING SEARCH RULES */
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("search option1",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});


        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("search option2",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.RANGE_AS_RANGE,
                new ArrayList<String>() {{
                    add("test");
                }});

        new AdvancedSearchHelper().addSeparateOptionCriteria(searchOption1);

        Assert.assertEquals(2, searchOption1.getOptionCriteria().size());
        Assert.assertEquals("name_1", searchOption1.getOptionCriteria().get(0));
        Assert.assertEquals("name_2", searchOption1.getOptionCriteria().get(1));

        new AdvancedSearchHelper().addSeparateOptionCriteria(searchOption2);

        Assert.assertEquals(1, searchOption2.getOptionCriteria().size());
        Assert.assertEquals("12;17;1988", searchOption2.getOptionCriteria().get(0));
    }

    @Test
    public void testAddSeparateOptionCriteriaWithNotUniqueItems() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.BIRTH_DATE));

        /* CREATING FILLED FORMS */
        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems1 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItem1.setValues(new ArrayList<String>() {{
            add("name_1");
        }});
        filledFormItems1.add(filledFormItem1);
        filledForm1.setFilledFormItems(filledFormItems1);

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItem2.setValues(new ArrayList<String>() {{
            add("name_1");
        }});
        filledFormItems2.add(filledFormItem2);
        filledForm2.setFilledFormItems(filledFormItems2);

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        final FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(form.getFormItems().get(0).getFormItemId(),
                FormItemName.FIRST_NAME, "name2");
        filledFormItem3.setValues(new ArrayList<String>() {{
            add("name_2");
        }});
        filledFormItems3.add(filledFormItem3);
        filledForm3.setFilledFormItems(filledFormItems3);

        /* CREATING SEARCH RULES */
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("search option1",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});

        new AdvancedSearchHelper().addSeparateOptionCriteria(searchOption1);

        Assert.assertEquals(2, searchOption1.getOptionCriteria().size());
        Assert.assertEquals("name_1", searchOption1.getOptionCriteria().get(0));
        Assert.assertEquals("name_2", searchOption1.getOptionCriteria().get(1));
    }

    @Test
    public void testSortOptionsByPosition() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADOPTIVE_FATHER_NAME));

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test1",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOption1.setPosition(1);
        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("test2",
                form.getFormItems().get(1).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOption2.setPosition(2);
        final DraftAdvancedSearchOption searchOption3 = TestUtil.createAdvancedSearchOption("test3",
                form.getFormItems().get(2).getFormItemId(), OptionDisplayType.TEXT_AS_FREE, new ArrayList<String>());
        searchOption3.setPosition(0);
        searchOptions.add(searchOption1);
        searchOptions.add(searchOption2);
        searchOptions.add(searchOption3);

        new AdvancedSearchHelper().sortOptionsByPosition(searchOptions);

        Assert.assertEquals(0, searchOptions.get(0).getPosition());
        Assert.assertEquals(searchOption3, searchOptions.get(0));
        Assert.assertEquals(1, searchOptions.get(1).getPosition());
        Assert.assertEquals(searchOption1, searchOptions.get(1));
        Assert.assertEquals(2, searchOptions.get(2).getPosition());
        Assert.assertEquals(searchOption2, searchOptions.get(2));
    }

    @Test
    public void testCreateDefaultSearchForm() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftForm defaultForm = new AdvancedSearchHelper().
                createDefaultSearchForm(site.getSiteId(), new UserManager(user)).getForm();

        Assert.assertNotNull(defaultForm);
        Assert.assertEquals(site.getSiteId(), defaultForm.getSiteId());
        Assert.assertEquals("Advanced Search Default Form1", defaultForm.getName());
    }

    @Test
    public void testCreateDefaultGallery() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);

        final Gallery defaultGallery = new AdvancedSearchHelper().createDefaultGallery(site.getSiteId(), form.getFormId());

        Assert.assertNotNull(defaultGallery);
        Assert.assertEquals(site.getSiteId(), defaultGallery.getSiteId());
        Assert.assertEquals("Default Advanced Search Gallery1", defaultGallery.getName());
    }

}
