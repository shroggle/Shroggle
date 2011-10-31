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

import com.shroggle.entity.*;
import com.shroggle.logic.borderBackground.RenderBorderBackground;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.StringUtil;

/**
 * HtmlProcessor htmlProcessor = ...;
 * htmlProcessor.addListener("tag", new ProcessorListenerTitle());
 *
 * @author Stasuk Artem
 */
public class RenderTheme implements Render {

    public RenderTheme(final PageManager pageManager, final SiteShowOption siteShowOption) {
        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    //Method for site edit page on fly update

    public String createBorderBackgroundStyle(final boolean showFromSiteEditPage) {
        final StringBuilder styleText = new StringBuilder();
        for (final Widget widget : pageManager.getWidgets()) {
            final Border border = new WidgetManager(widget).getBorder(siteShowOption);
            final Background background = new WidgetManager(widget).getBackground(siteShowOption);
            final RenderBorderBackground borderBackgroundRender = new RenderBorderBackground(border, background);
            final String borderBackgroundStyle = borderBackgroundRender.getBorderBackgroundStyle();
            if (!borderBackgroundStyle.trim().isEmpty()) {
                styleText.append(createClassName(widget, showFromSiteEditPage));
                styleText.append(" { ");
                styleText.append(borderBackgroundStyle);
                styleText.append("} ");
            }
        }

        final Border border = pageManager.getBorder();
        final Background background = pageManager.getBackground();
        final String pageStyle = new RenderBorderBackground(border, background).getBorderBackgroundStyle();
        if (!StringUtil.isNullOrEmpty(pageStyle)) {
            styleText.append(" body { ");
            styleText.append(pageStyle);
            styleText.append("} ");
        }
        return styleText.toString();
    }

    protected String createClassName(final Widget widget, final boolean showFromSiteEditPage) {
        final StringBuilder className = new StringBuilder("");
        if (widget != null) {
            className.append(" #widget");
            if (showFromSiteEditPage && widget.isWidgetComposit()) {
                className.append("Style");
            }
            className.append(widget.getWidgetId());
        }
        return className.toString();
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        StringBuilder cssClasses = new StringBuilder("");
        int i = html.indexOf("<!-- PAGE_HEADER -->");
        if (i > -1) {
            if (pageManager.getCss() != null) {
                cssClasses.append("<!-- User modified template css -->\n");
                cssClasses.append("<style type=\"text/css\">\n");
                cssClasses.append(pageManager.getCss());
                cssClasses.append("\n");
                cssClasses.append("</style>\n");
            } else {
                /* We are inserting raw template css to avoid problems with images (SW-5831). */
                cssClasses.append("<!-- Raw template css -->\n");
                cssClasses.append("<style type=\"text/css\">\n");
                cssClasses.append(pageManager.getTemplateCss());
                cssClasses.append("\n");
                cssClasses.append("</style>\n");
            }
            cssClasses.append("<style id=\"widgetBorderBackgrounds\" type=\"text/css\"> ");
            cssClasses.append(createBorderBackgroundStyle(context.isShowFromSiteEditPage()));
            cssClasses.append(" </style>\n");
            html.insert(i, cssClasses);
        }
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;

}