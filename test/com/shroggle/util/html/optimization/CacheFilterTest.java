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

package com.shroggle.util.html.optimization;

import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Test;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class CacheFilterTest {

    @Test
    public void doFilter() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        MockHttpServletResponse response = new MockHttpServletResponse();

        Filter filter = new CacheControlFilter();
        filter.init(null);
        filter.doFilter(request, response, new FilterChain() {

            public void doFilter(
                    final ServletRequest servletRequest,
                    final ServletResponse servletResponse)
                    throws IOException, ServletException {
                chainExecuted = true;
            }

        });
        filter.destroy();

        Assert.assertTrue("Cache filter don't call chain!", chainExecuted);
        final Map<String, List<Object>> headers = response.getHeaderMap();
        Assert.assertEquals("Cache filter don't set headers for cache!", 4, headers.size());
    }

    private boolean chainExecuted = false;

}
