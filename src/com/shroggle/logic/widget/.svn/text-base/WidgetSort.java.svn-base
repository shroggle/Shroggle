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

import com.shroggle.entity.*;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class WidgetSort {

    public final static Comparator<WidgetItem> widgetPositionComparator = new Comparator<WidgetItem>() {
        public int compare(final WidgetItem wb1, final WidgetItem wb2) {
            int wb1Position = wb1.getPosition();
            int wb2Position = wb2.getPosition();
            if (wb1Position == 0 && wb2Position == 0) {
                wb2Position = -1;
            }
            return Integer.valueOf(wb1Position).compareTo(wb2Position);
        }
    };
}