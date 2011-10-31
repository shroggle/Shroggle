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

package com.shroggle.logic.widget;

import com.shroggle.entity.Widget;

import java.util.Comparator;

/**
 * @author Stasuk Artem
 */
public class WidgetComparator implements Comparator<Widget> {

    public static final WidgetComparator INSTANCE = new WidgetComparator();

    public int compare(Widget o1, Widget o2) {
        return o1.getPosition() - o2.getPosition();
    }

    protected WidgetComparator() {
    }

}
