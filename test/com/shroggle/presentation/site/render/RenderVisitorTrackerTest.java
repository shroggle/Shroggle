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
package com.shroggle.presentation.site.render;


import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderVisitorTrackerTest {

    @Test
    public void execute() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.getPage().setPageId(12);

        Render render = new RenderVisitorTracker(pageVersion);

        StringBuilder html = new StringBuilder("f");
        render.execute(null, html);

        Assert.assertEquals("f<script type=\"text/javascript\"> trackVisitor(12) </script>", html.toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new RenderVisitorTracker(null);
    }

    @Test
    public void executeWithBody() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.getPage().setPageId(12);

        Render render = new RenderVisitorTracker(pageVersion);
        StringBuilder html = new StringBuilder("<body></body>");
        render.execute(null, html);

        Assert.assertEquals("<body onunload=\"leavingPage(12)\"></body><script type=\"text/javascript\"> trackVisitor(12) </script>", html.toString());
    }

}