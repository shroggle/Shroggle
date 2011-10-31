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
import com.shroggle.entity.FontsAndColors;
import com.shroggle.entity.FontsAndColorsValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Kanshyn 
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderCssParameterTestCase {

   /* @Test
    public void testExecute_Page() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());

//        CssParameterValue cssParameterValue = pageVersion.addCssParameterValue();
//        cssParameterValue.setSelector(".itemText");
//        cssParameterValue.setName("color");
//        cssParameterValue.setDescription("My ItemText color");
//        cssParameterValue.setValue("red");
//
        RenderCssParameter render = new RenderCssParameter(pageVersion, SiteShowOption.ON_USER_PAGES);
        StringBuilder builder = new StringBuilder("<!-- PAGE_HEADER -->");
        render.execute(new EmptyRenderContext(), builder);

        assertEquals("<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                "*//* My ItemText color *//*\n" +
                "body .itemText {color: red;}\n" +
                "\n" +
                "</style>\n" +
                "<!-- PAGE_HEADER -->", builder.toString());
    }

    @Test
    public void testExecute_PageComlexSelector() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());

//        CssParameterValue cssParameterValue = pageVersion.addCssParameterValue();
//        cssParameterValue.setSelector(".itemText, .dataTex .panel");
//        cssParameterValue.setName("color");
//        cssParameterValue.setDescription("My ItemText color");
//        cssParameterValue.setValue("red");

        RenderCssParameter render = new RenderCssParameter(pageVersion, SiteShowOption.ON_USER_PAGES);
        StringBuilder builder = new StringBuilder("<!-- PAGE_HEADER -->");
        render.execute(new EmptyRenderContext(), builder);

        assertEquals("<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                "*//* My ItemText color *//*\n" +
                "body .itemText, body .dataTex .panel {color: red;}\n" +
                "\n" +
                "</style>\n" +
                "<!-- PAGE_HEADER -->", builder.toString());
    }

    @Test
    public void testExecute_ImageWidget() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        Widget widget = TestUtil.createWidgetItem();
        pageVersion.addWidget(widget);

        RenderCssParameter render = new RenderCssParameter(pageVersion, SiteShowOption.ON_USER_PAGES);
        StringBuilder builder = new StringBuilder("<!-- PAGE_HEADER -->");
        render.execute(new EmptyRenderContext(), builder);

        assertEquals("<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                "*//* My Margin *//*\n" +
                "#widget0 {margin: 11;}\n" +
                "\n" +
                "*//* My ItemText color *//*\n" +
                "#widget0 .itemText, #widget0 .dataTex .panel {color: red;}\n\n" +
                "</style>\n" +
                "<!-- PAGE_HEADER -->", builder.toString());
    }

    @Test
    public void testExecute_AnyWidget() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        Widget widget = TestUtil.createWidgetItem();
        pageVersion.addWidget(widget);

        RenderCssParameter render = new RenderCssParameter(pageVersion, SiteShowOption.ON_USER_PAGES);
        StringBuilder builder = new StringBuilder("<!-- PAGE_HEADER -->");
        render.execute(new EmptyRenderContext(), builder);

        assertEquals("<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                "*//* My CSS Description *//*\n" +
                "#widget0 .menuItem {font-size: 2em;}\n" +
                "\n" +
                "</style>\n" +
                "<!-- PAGE_HEADER -->", builder.toString());
    }

    @Test
    public void testExecute_comlexSelector() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        Widget widget = TestUtil.createWidgetItem();
        pageVersion.addWidget(widget);

        RenderCssParameter render = new RenderCssParameter(pageVersion, SiteShowOption.ON_USER_PAGES);
        StringBuilder builder = new StringBuilder("<!-- PAGE_HEADER -->");
        render.execute(new EmptyRenderContext(), builder);

        assertEquals("<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                "*//* My CSS Description *//*\n" +
                "#widget0 .menuItem, #widget0 .menuData {font-size: 2em;}\n" +
                "\n" +
                "</style>\n" +
                "<!-- PAGE_HEADER -->", builder.toString());
    }*/

    @Test
    public void testBuildOne_withMultipleSelector() throws Exception {
        final RenderCssParameter renderCssParameter = new RenderCssParameter(null, null);

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        final FontsAndColorsValue cssParameterValue = TestUtil.createFontsAndColorsValue(fontsAndColors, "padding", ".selector1, selector2", "100px");
        final StringBuilder builder = new StringBuilder();

        renderCssParameter.buildOne(builder, "widget123", cssParameterValue);

        Assert.assertEquals("/* description */\n" +
                "widget123 .selector1, widget123 selector2 {\n" +
                "padding: 100px;\n" +
                "}", builder.toString().trim());
    }

    @Test
    public void testBuildOne() throws Exception {
        final RenderCssParameter renderCssParameter = new RenderCssParameter(null, null);

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        final FontsAndColorsValue cssParameterValue = TestUtil.createFontsAndColorsValue(fontsAndColors, "padding-top, padding-bottom", ".selector", "100px");
        final StringBuilder builder = new StringBuilder();

        renderCssParameter.buildOne(builder, "widget123", cssParameterValue);

        Assert.assertEquals("/* description */\n" +
                "widget123 .selector {\n" +
                "padding-top: 100px;\n" +
                "padding-bottom: 100px;\n" +
                "}\n", builder.toString());
    }
}
