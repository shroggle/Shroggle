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

/**
 * @author Artem Stasuk
 */
class RenderAppenderReal implements RenderAppender {

    public RenderAppenderReal(final String[] patterns, final StringBuilder html) {
        this.html = html;
        this.patterns = patterns;
    }

    @Override
    public void append(final String html) {
        if (start == null) {
            exist();
        }

        if (start > -1) {
            this.html.replace(start, end, html == null ? "" : html);
        }
    }

    @Override
    public String getExistPattern() {
        return pattern;
    }

    @Override
    public boolean exist() {
        for (final String pattern : patterns) {
            final int index = html.indexOf(pattern);
            if (index > -1) {
                start = index;
                end = index + pattern.length();
                this.pattern = pattern;
                return true;
            }
        }

        start = -1;
        return false;
    }

    private final StringBuilder html;
    private final String[] patterns;
    private String pattern;
    private Integer start;
    private int end;

}
