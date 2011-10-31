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

/**
 * @author Artem Stasuk
 */
public class HtmlVisitorReal implements HtmlVisitor {

    @Override
    public void execute(final StringBuilder html, final HtmlVisitorListener listener) {
        int position = 0;
        HtmlVisitorPlace place = null;
        while (true) {
            place = find(html, place);
            if (place == null) {
                break;
            }

            place.call(listener, position);
            position++;
        }
    }

    private HtmlVisitorPlace find(final StringBuilder html, HtmlVisitorPlace start) {
        HtmlVisitorPlace place = null;
        final int startIndex = start == null ? 0 : start.getStart() + 1;
        for (final HtmlVisitorPattern pattern : patterns) {
            final HtmlVisitorPlace tempPlace = pattern.find(html, startIndex);
            if (tempPlace != null) {
                if (place == null || place.getStart() > tempPlace.getStart()) {
                    place = tempPlace;
                }
            }
        }

        return place;
    }

    private static final HtmlVisitorPattern[] patterns =
            {new HtmlVisitorPatternStandart(), new HtmlVisitorPatternFlat()};

}
