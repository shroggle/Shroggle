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

import com.shroggle.entity.FontsAndColorsValue;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.widget.CssParametersLinkSorter;
import com.shroggle.presentation.site.cssParameter.CssParameter;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Stasuk Artem
 */
public class RenderCssParameter implements Render {

    public RenderCssParameter(final PageManager pageManager, SiteShowOption siteShowOption) {
        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final int i = html.indexOf("<!-- PAGE_HEADER -->");
        if (i < 0) return;
        html.insert(i, pageCssValuesToCss(pageManager));
    }

    public String pageCssValuesToString(final PageManager pageManager) {
        final StringBuilder css = new StringBuilder();

        //final Persistance persistance = ServiceLocator.getPersistance();
        //final Item itemPage = null; // todo. Create items for pageVersions. Tolik
        //build(css, itemPage, null, "body");

        for (final Widget widget : pageManager.getWidgets()) {
            build(css, widget);
        }


        return css.toString();
    }

    private String pageCssValuesToCss(final PageManager pageManager) {
        final StringBuilder css = new StringBuilder();
        css.append("<style id=\"cssParameterStyle\" type=\"text/css\">\n");
        css.append(pageCssValuesToString(pageManager));
        css.append("</style>\n");
        return css.toString();
    }

    private void build(final StringBuilder css, final Widget widget) {
        final WidgetManager widgetManager = new WidgetManager(widget);
        final List<FontsAndColorsValue> cssValues = widgetManager.getFontsAndColors(siteShowOption).getCssValues();
        for (final FontsAndColorsValue cssParameterValue : CssParametersLinkSorter.execute(cssValues)) {
            buildOne(css, "#widget" + widget.getWidgetId(), cssParameterValue);
        }
    }

    void buildOne(final StringBuilder css, String id, final FontsAndColorsValue cssParameterValue) {
        if (cssParameterValue.getDescription() != null && !cssParameterValue.getDescription().isEmpty()) {
            css.append("/* ");
            css.append(cssParameterValue.getDescription());
            css.append(" */\n");
        }
        if (!CssParameter.NONE_SELECTOR.equals(cssParameterValue.getSelector())) {
            String selector = cssParameterValue.getSelector();
            final StringTokenizer tokenizer = new StringTokenizer(selector, ",");
            while (tokenizer.hasMoreTokens()) {
                String oneSelector = tokenizer.nextToken();
                css.append(id).append(" ");
                css.append(oneSelector.trim());
                if (tokenizer.hasMoreTokens()) {
                    css.append(", ");
                }
            }
        } else {
            css.append(id);
        }
        css.append(" {\n");
        for (String name : cssParameterValue.getName().split(",")) {
            css.append(name.trim());
            css.append(": ");
            css.append(cssParameterValue.getValue());
            css.append(";\n");
        }
        css.append("}\n");
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;

}