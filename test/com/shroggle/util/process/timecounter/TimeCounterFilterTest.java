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
package com.shroggle.util.process.timecounter;

import com.shroggle.presentation.MockFilterChain;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Artem Stasuk
 */
public class TimeCounterFilterTest {

    @Test
    public void doFilter() throws ServletException, IOException {
        ServiceLocator.setTimeCounterCreator(new TimeCounterCreatorSimple());
        Filter filter = new TimeCounterFilter();
        filter.init(null);
        final MockFilterChain mockFilterChain = new MockFilterChain();
        filter.doFilter(new MockHttpServletRequest("a", "f"), null, mockFilterChain);
        filter.destroy();

        Assert.assertTrue(mockFilterChain.isVisitDoFilter());
        final Collection<TimeCounterResult> results = ServiceLocator.getTimeCounterCreator().getResults();
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("https://localhost:8080af", results.iterator().next().getName());
        Assert.assertEquals(1, results.iterator().next().getExecutedCount());
    }

}
