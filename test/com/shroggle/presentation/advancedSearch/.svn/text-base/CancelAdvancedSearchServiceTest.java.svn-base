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
import com.shroggle.entity.DraftCustomForm;
import com.shroggle.entity.Site;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CancelAdvancedSearchServiceTest extends TestCase {

    final private CancelAdvancedSearchService service = new CancelAdvancedSearchService();

    @Test
    public void testRemoveDefaultForm() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm form = TestUtil.createCustomForm(site);

        service.removeDefaultForm(form.getFormId());

        Assert.assertNull(ServiceLocator.getPersistance().getFormById(form.getFormId()));
    }
    
    @Test(expected = FormNotFoundException.class)
    public void testRemoveDefaultFormWithoutForm() throws Exception {
        service.removeDefaultForm(0);
    }

}
