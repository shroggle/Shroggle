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

/**
 * @author Artem Stasuk
 */
public class CustomTagIteratorSimple {

    public CustomTagIteratorSimple(final StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public boolean next() {
        if (stringBuilder == null) {
            return false;
        }

        final int find = stringBuilder.indexOf(PATTERN, position);
        if (find < 0) {
            return false;
        }

        start = find;
        while (stringBuilder.charAt(start) != '<') {
            start--;
        }

        end = stringBuilder.indexOf(">", start);
        if (end < 0) {
            return false;
        }

        end = end;

        position = end;
        return true;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    private final StringBuilder stringBuilder;
    private int position;
    private int start;
    private int end;

    public final static String PATTERN = "shroggleTag";
//    public final static String END_PATTERN = "</shroggletag>";

}