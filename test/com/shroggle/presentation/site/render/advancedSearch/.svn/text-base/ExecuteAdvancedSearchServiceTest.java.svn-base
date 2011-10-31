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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.exception.AdvancedSearchOptionNotFoundException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ExecuteAdvancedSearchServiceTest extends TestCase {

    private final ExecuteAdvancedSearchService service = new ExecuteAdvancedSearchService();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        widget.setDraftItem(advancedSearch);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        advancedSearch.setGalleryId(gallery.getId());

        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption1);

        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption2);

        final ExecuteAdvancedSearchRequest request = new ExecuteAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setGalleryId(gallery.getId());
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setSearchOptionCriteriaList(new ArrayList<String>() {{
            add("test_new");
        }});
        request.setAdvancedSearchOptionId(searchOption1.getAdvancedSearchOptionId());

        final ExecuteAdvancedSearchResponse response = service.execute(request);
        Assert.assertNotNull(response.getGalleryHtml());
        final DraftAdvancedSearch advancedSearchInContext =
                contextStorage.get().getAdvancedSearchRequestById(advancedSearch.getId());
        Assert.assertNotNull(advancedSearchInContext);
        Assert.assertEquals(0, advancedSearchInContext.getAdvancedSearchOptions().get(1).getOptionCriteria().size());
    }

    @Test
    public void testExecuteWithExistingRequestInContext() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        widget.setDraftItem(advancedSearch);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));
        advancedSearch.setGalleryId(gallery.getId());

        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption1);

        final DraftAdvancedSearchOption searchOption2 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption2);

        ExecuteAdvancedSearchRequest request = new ExecuteAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setGalleryId(gallery.getId());
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setSearchOptionCriteriaList(new ArrayList<String>() {{
            add("test_new");
        }});
        request.setAdvancedSearchOptionId(searchOption1.getAdvancedSearchOptionId());

        ExecuteAdvancedSearchResponse response = service.execute(request);

        Assert.assertNotNull(response.getGalleryHtml());
        DraftAdvancedSearch advancedSearchInContext = contextStorage.get().getAdvancedSearchRequestById(advancedSearch.getId());
        Assert.assertNotNull(advancedSearchInContext);
        //Testing that in request search option have new search parameters. It will not affect real search option
        //'cos code in service isn't in a transaction.
        Assert.assertEquals(2, advancedSearchInContext.getAdvancedSearchOptions().size());
        Assert.assertEquals("test_new", advancedSearchInContext.getAdvancedSearchOptions().get(0).getOptionCriteria().get(0));
        Assert.assertEquals(true, advancedSearchInContext.getAdvancedSearchOptions().get(1).getOptionCriteria().isEmpty());

        // Adding new search option to request.
        final DraftAdvancedSearchOption searchOption3 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption3);

        request = new ExecuteAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setGalleryId(gallery.getId());
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setSearchOptionCriteriaList(new ArrayList<String>() {{
            add("test111");
        }});
        request.setAdvancedSearchOptionId(searchOption3.getAdvancedSearchOptionId());

        response = service.execute(request);
        Assert.assertNotNull(response.getGalleryHtml());
        advancedSearchInContext = contextStorage.get().getAdvancedSearchRequestById(advancedSearch.getId());
        Assert.assertNotNull(advancedSearchInContext);
        //Testing that in request search option have new search parameters. It will not affect real search option
        //'cos code in service isn't in a transaction.
        Assert.assertEquals(3, advancedSearchInContext.getAdvancedSearchOptions().size());
        Assert.assertEquals("test_new", advancedSearchInContext.getAdvancedSearchOptions().get(0).getOptionCriteria().get(0));
        Assert.assertEquals(true, advancedSearchInContext.getAdvancedSearchOptions().get(1).getOptionCriteria().isEmpty());
        Assert.assertEquals("test111", advancedSearchInContext.getAdvancedSearchOptions().get(2).getOptionCriteria().get(0));
    }

    @Test(expected = AdvancedSearchNotFoundException.class)
    public void testExecuteWithoutAdvancedSearch() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final ExecuteAdvancedSearchRequest request = new ExecuteAdvancedSearchRequest();
        request.setAdvancedSearchId(0);
        request.setWidgetId(widget.getWidgetId());
        request.setGalleryId(gallery.getId());
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setSearchOptionCriteriaList(new ArrayList<String>() {{
            add("test_new");
        }});

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void testExecuteWithoutWidget() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final ExecuteAdvancedSearchRequest request = new ExecuteAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(0);
        request.setGalleryId(gallery.getId());
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setSearchOptionCriteriaList(new ArrayList<String>() {{
            add("test_new");
        }});

        service.execute(request);
    }

    @Test
    public void testAddSearchCriterias() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        advancedSearch.setGalleryId(gallery.getId());

        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption);

        service.addArtificialSearchOptionIntoAdvancedSearch(advancedSearch, null, searchOption.getAdvancedSearchOptionId(), new ArrayList<String>() {{
            add("test");
            add("test1");
        }});

        Assert.assertEquals(2, searchOption.getOptionCriteria().size());
        Assert.assertEquals("test", searchOption.getOptionCriteria().get(0));
        Assert.assertEquals("test1", searchOption.getOptionCriteria().get(1));
    }

    @Test
    public void testAddSearchCriteriasWithOptionsShouldBeCleaned() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        advancedSearch.setGalleryId(gallery.getId());

        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption);


        service.addArtificialSearchOptionIntoAdvancedSearch(advancedSearch, null, searchOption.getAdvancedSearchOptionId(), new ArrayList<String>() {{
            add("null");
        }});

        Assert.assertEquals(0, searchOption.getOptionCriteria().size());
    }

    @Test
    public void testAddSearchCriteriasWithOptionsShouldNOTBeCleaned() {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME));

        final DraftAdvancedSearchOption searchOption = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        advancedSearch.addSearchOption(searchOption);


        service.addArtificialSearchOptionIntoAdvancedSearch(advancedSearch, null, searchOption.getAdvancedSearchOptionId(), new ArrayList<String>() {{
            add("null");
            add("test");
        }});

        Assert.assertEquals(2, searchOption.getOptionCriteria().size());
        Assert.assertEquals("null", searchOption.getOptionCriteria().get(0));
        Assert.assertEquals("test", searchOption.getOptionCriteria().get(1));
    }


}
