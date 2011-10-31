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
package com.shroggle.util.journal;

import com.shroggle.presentation.MockFilterChain;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class JournalFilterTest {

    @Before
    public void before() {
        ServiceLocator.setJournal(journalMock);
        ServiceLocator.setContextStorage(new ContextStorage());
    }

    @Test
    public void doFilter() throws IOException, ServletException {
        journalFilter.init(null);
        journalFilter.doFilter(new MockHttpServletRequest("b", "a"), null, mockFilterChain);
        journalFilter.destroy();

        Assert.assertTrue(mockFilterChain.isVisitDoFilter());
        Assert.assertNotNull(journalMock.get());
    }

    private final MockFilterChain mockFilterChain = new MockFilterChain();
    private final JournalMock journalMock = new JournalMock();
    private final JournalFilter journalFilter = new JournalFilter();

}
