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
package com.shroggle.logic.countries.states;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Country;
import com.shroggle.entity.States_US;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class StateManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        new StateManager(null);
    }

    @Test
    public void testGetName() throws Exception {
        final International international = ServiceLocator.getInternationStorage().get("states" + Country.US, Locale.US);
        Assert.assertEquals(international.get("AR"), new StateManager(States_US.AR).getName());
    }
}
