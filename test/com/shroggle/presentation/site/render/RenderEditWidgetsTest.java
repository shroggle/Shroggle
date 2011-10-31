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
import com.shroggle.entity.WidgetComposit;
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
public class RenderEditWidgetsTest {

    @Test
    public void execute() throws IOException, ServletException {
        final PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.addWidget(TestUtil.createWidgetComposit());
        WidgetComposit composit2 = TestUtil.createWidgetComposit();
        composit2.setPosition(1);
        pageVersion.addWidget(composit2);
        final StringBuilder html = new StringBuilder("a <!-- MEDIA_BLOCK --> <b class=\"mediaBlock\"></b>");
        new RenderEditWidgets(pageVersion).execute(null, html);

        Assert.assertEquals(
                "a <div class=\"widgetPosition\" " +
                        "id=\"widgetPosition0\"></div><script> loadWidget(" + pageVersion.getPageId() + ",0); </script> " +
                        "<b class=\"mediaBlock\" id=\"widgetStyle" + composit2.getWidgetId() + "\"><div " +
                        "class=\"widgetPosition\" id=\"widgetPosition1\"></div><script> loadWidget(" + pageVersion.getPageId() +
                        ",1); </script></b>",
                html.toString());
    }

    @Test
    public void executeWithHtmlWithoutMediaBlocks() throws IOException, ServletException {
        final PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        final StringBuilder html = new StringBuilder("a f");
        new RenderEditWidgets(pageVersion).execute(null, html);

        Assert.assertEquals("a f", html.toString());
    }

}
