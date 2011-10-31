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

import com.shroggle.entity.Widget;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageWidgetsByPosition;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class RenderEditWidgets implements Render {

    public RenderEditWidgets(final PageManager pageManager) {
        this.pageManager = pageManager;
    }

    @Override
    public void execute(final RenderContext context, final StringBuilder html) throws IOException, ServletException {
        // todo Dima must refactor this code.
        final Map<Integer, Widget> mediaBlockByPositions = PageWidgetsByPosition.execute(pageManager);

        ServiceLocator.getHtmlProcessor().execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(final HtmlMediaBlock block) {
                position++;

                block.setHtml(onAnyMediaBlock());
            }

            @Override
            public void onFlatMediaBlock(final HtmlFlatMediaBlock block) {
                position++;

                final Widget widget = mediaBlockByPositions.get(position);
                if (widget != null) {
                    block.setId("widgetStyle" + widget.getWidgetId());
                    block.setHtml(onAnyMediaBlock());
                }
            }

            private String onAnyMediaBlock() {
                return "<div class=\"widgetPosition\" id=\"widgetPosition" + position +
                        "\"></div><script> loadWidget(" + pageManager.getPageId()
                        + "," + position + "); </script>";
            }

            private int position = -1;

        });
    }

    private final PageManager pageManager;

}
