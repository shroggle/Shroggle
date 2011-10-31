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
package com.shroggle.util.html.processor.simple;

import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlProcessor;

/**
 * @author Artem Stasuk
 */
public class HtmlProcessorSimple implements HtmlProcessor {

    @Override
    public void execute(final StringBuilder html, final HtmlListener listener) {
        if (html == null) {
            return;
        }

        int index = -1;
        while (true) {
            final int blockIndex = html.indexOf(pattern, index + 1);
            final int flatBlockIndex = html.indexOf(flatPattern, index + 1);
            if (blockIndex == -1 && flatBlockIndex == -1) {
                return;
            }

            if (blockIndex > -1 && (blockIndex < flatBlockIndex || flatBlockIndex == -1)) {
                final HtmlMediaBlockSimple block = new HtmlMediaBlockSimple();
                listener.onMediaBlock(block);
                if (block.getHtml() != null) {
                    html.replace(blockIndex, blockIndex + pattern.length(), block.getHtml());
                    index = blockIndex + block.getHtml().length() - pattern.length();
                } else {
                    index = blockIndex;
                }
            } else {
                final HtmlFlatMediaBlockSimple flatBlock = new HtmlFlatMediaBlockSimple();
                listener.onFlatMediaBlock(flatBlock);

                int newFlatBlogIndex = flatBlockIndex;
                if (flatBlock.getId() != null) {
                    final int tagOpenFinish = html.indexOf(">", flatBlockIndex + 1);
                    if (tagOpenFinish > -1) {
                        final String id = " id=\"" + flatBlock.getId() + "\"";
                        html.insert(tagOpenFinish, id);
                        newFlatBlogIndex += id.length();
                    }
                }

                if (flatBlock.getHtml() != null) {
                    final int tagOpenFinish = html.indexOf(">", flatBlockIndex + 1);
                    if (tagOpenFinish > -1) {
                        final int tagCloseStart = html.indexOf("<", tagOpenFinish + 1);
                        if (tagCloseStart > tagOpenFinish) {
                            html.replace(tagOpenFinish + 1, tagCloseStart, flatBlock.getHtml());
                            newFlatBlogIndex += flatBlock.getHtml().length() - (tagOpenFinish + 1 - tagCloseStart);
                        }
                    }
                }
                index = newFlatBlogIndex;
            }
        }
    }

    private static final String pattern = "<!-- MEDIA_BLOCK -->";
    private static final String flatPattern = "mediaBlock";

}
