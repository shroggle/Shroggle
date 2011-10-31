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
import com.shroggle.exception.AdvancedSearchOptionNotFoundException;
import com.shroggle.util.ServiceLocator;
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
public class AdvancedSearchOptionManagerTest extends TestCase {

    @Test
    public void testRemove() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);
        form.setFormItems(TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        final DraftAdvancedSearchOption searchOption1 = TestUtil.createAdvancedSearchOption("test",
                form.getFormItems().get(0).getFormItemId(), OptionDisplayType.TEXT_AS_FREE,
                new ArrayList<String>() {{
                    add("test");
                }});
        searchOptions.add(searchOption1);
        searchOption1.setAdvancedSearch(advancedSearch);
        advancedSearch.setAdvancedSearchOptions(searchOptions);

        new AdvancedSearchOptionManager(searchOption1.getAdvancedSearchOptionId()).remove();

        Assert.assertNull(ServiceLocator.getPersistance().getAdvancedSearchOptionById(searchOption1.getAdvancedSearchOptionId()));
        Assert.assertTrue(advancedSearch.getAdvancedSearchOptions().isEmpty());
    }
    
    @Test(expected = AdvancedSearchOptionNotFoundException.class)
    public void testInitializeWithoutAdvancedSearchOption(){
        new AdvancedSearchOptionManager(null);
    }

    @Test(expected = AdvancedSearchOptionNotFoundException.class)
    public void testInitializeWithoutAdvancedSearchOptionId(){
        new AdvancedSearchOptionManager(-1);
    }

}
