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

package com.shroggle.logic.site.page.pageversion;

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetComposit;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageWidgetsByPosition;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public class PageVersionNormalizerReal implements PageVersionNormalizer {

    @Override
    public void execute(final PageManager pageManager) {
        new NormalizerInternal().execute(pageManager);
    }

    private static class NormalizerInternal implements HtmlListener {

        public void execute(final PageManager pageManager) {
            this.pageManager = pageManager;
            mediaBlockByPositions = PageWidgetsByPosition.execute(pageManager);
            final StringBuilder html = new StringBuilder(pageManager.getSavedHtmlOrDefault());
            ServiceLocator.getHtmlProcessor().execute(html, this);

            if (lastMediaBlock != null) {
                position++;

                while (position < mediaBlockByPositions.size()) {
                    final Widget widget = mediaBlockByPositions.get(position);
                    if (widget.isWidgetComposit()) {
                        final WidgetComposit widgetComposit = (WidgetComposit) widget;
                        final List<Widget> childWidgets = new ArrayList<Widget>(widgetComposit.getChilds());
                        for (final Widget childWidget : childWidgets) {
                            widgetComposit.removeChild(childWidget);
                            childWidget.setPosition(lastMediaBlock.getChilds().size());
                            lastMediaBlock.addChild(childWidget);
                        }
                        pageManager.removeWidget(widgetComposit);
                        ServiceLocator.getPersistance().removeWidget(widgetComposit);
                    } else {
                        lastMediaBlock.addChild(widget);
                    }
                    position++;
                }
            }
        }

        @Override
        public void onMediaBlock(final HtmlMediaBlock block) {
            onAnyMediaBlock();
        }

        @Override
        public void onFlatMediaBlock(final HtmlFlatMediaBlock block) {
            onAnyMediaBlock();
        }

        private void onAnyMediaBlock() {
            position++;
            final Widget widget = mediaBlockByPositions.get(position);
            if (widget == null) {
                lastMediaBlock = new WidgetComposit();
                lastMediaBlock.setPosition(position);
                ServiceLocator.getPersistance().putWidget(lastMediaBlock);
                pageManager.addWidget(lastMediaBlock);
                mediaBlockByPositions.put(position, lastMediaBlock);
            } else {
                if (widget.isWidgetComposit()) {
                    lastMediaBlock = (WidgetComposit) widget;
                }
            }
        }

        private int position = -1;
        private WidgetComposit lastMediaBlock;
        private PageManager pageManager;
        private Map<Integer, Widget> mediaBlockByPositions;

    }

}
