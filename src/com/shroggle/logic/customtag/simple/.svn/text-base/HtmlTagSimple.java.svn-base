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
package com.shroggle.logic.customtag.simple;

import com.shroggle.logic.customtag.HtmlTag;
import com.shroggle.util.StringUtil;

/**
 * @author Artem Stasuk
 */
public class HtmlTagSimple implements HtmlTag {

    HtmlTagSimple(final CustomTagIteratorSimple iterator) {
        this.iterator = iterator;
    }

    @Override
    public String getAttribute(final String name) {
        final String string = iterator.getStringBuilder().substring(iterator.getStart(), iterator.getEnd());
        return StringUtil.find(string, name + "=\"", "\"");
    }

    @Override
    public String getBody() {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void setAttribute(final String name, final String value) {
        final String string = iterator.getStringBuilder().substring(iterator.getStart(), iterator.getEnd());
        final String startPattern = name + "=\"";
        final int start = string.indexOf(startPattern);
        if (start > -1) {
            final int end = string.indexOf("\"", start + startPattern.length());
            if (end > -1) {
                iterator.getStringBuilder().replace(iterator.getStart() + start + startPattern.length(), iterator.getStart() + end, value);
//                iterator.setPosition(iterator.getStart());
                changed = true;
            }
        }
    }

    @Override
    public void setBody(final String value) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public String getName() {
        initWords();

        return words[0].substring(1);
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    private void initWords() {
        if (words == null) {
            words = iterator.getStringBuilder().substring(iterator.getStart(), iterator.getEnd()).split("[ >]");
        }
    }

    private final CustomTagIteratorSimple iterator;
    private String[] words;
    private boolean changed;

}
