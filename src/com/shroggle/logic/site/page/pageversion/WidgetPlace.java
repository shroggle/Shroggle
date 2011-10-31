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
public class WidgetPlace {

    public WidgetPlace(final WidgetPlaceType type, final int position) {
        this.type = type;
        this.position = position;
    }


    public int getPosition() {
        return position;
    }

    public WidgetPlaceType getType() {
        return type;
    }

    private final int position;
    private final WidgetPlaceType type;

}
