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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artem Stasuk
 */
public class HtmlProcessorRegex implements HtmlProcessor {

    @Override
    public void execute(final StringBuilder html, final HtmlListener listener) {
        if (html == null) {
            return;
        }

        int index = 0;
        while (true) {
            final Matcher matcher = pattern.matcher(html);
            if (!matcher.find(index)) {
                break;
            }


            if ("<!-- MEDIA_BLOCK -->".equals(matcher.group())) {
                final HtmlMediaBlockSimple block = new HtmlMediaBlockSimple();
                listener.onMediaBlock(block);
                index = matcher.start();
                if (block.getHtml() != null) {
                    index += block.getHtml().length();
                    html.replace(matcher.start(), matcher.end(), block.getHtml());
                } else {
                    index++;
                }
            } else {
                final HtmlFlatMediaBlockSimple flatBlock = new HtmlFlatMediaBlockSimple();
                listener.onFlatMediaBlock(flatBlock);

                Integer newFlatBlogIndex = null;
                if (flatBlock.getId() != null) {
                    final int tagOpenFinish = html.indexOf(">", matcher.start() + 1);
                    if (tagOpenFinish > -1) {
                        final String id = " id=\"" + flatBlock.getId() + "\"";
                        html.insert(tagOpenFinish, id);
                        newFlatBlogIndex = matcher.start() + id.length();
                    }
                }

                if (flatBlock.getHtml() != null) {
                    final int tagOpenFinish = html.indexOf(">", matcher.start() + 1);
                    if (tagOpenFinish > -1) {
                        final int tagCloseStart = html.indexOf("<", tagOpenFinish + 1);
                        if (tagCloseStart > tagOpenFinish) {
                            html.replace(tagOpenFinish + 1, tagCloseStart, flatBlock.getHtml());
                            newFlatBlogIndex = matcher.start() + flatBlock.getHtml().length() - (tagOpenFinish + 1 - tagCloseStart);
                        }
                    }
                }

                if (newFlatBlogIndex != null) {
                    index = newFlatBlogIndex;
                } else {
                    index = matcher.start() + matcher.group().length();
                }
            }
        }
    }

    private final Pattern pattern = Pattern.compile(
            "<\\!-- MEDIA_BLOCK -->|class=(\"|\"[\\w_\\s]* )mediaBlock(\"| [\\w_\\s]*\")");

}