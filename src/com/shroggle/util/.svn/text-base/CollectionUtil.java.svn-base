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

package com.shroggle.util;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class CollectionUtil {

    public static <T> void move(final T item, final List<T> items, final int newPosition) {
        if (item == null) {
            throw new NullPointerException(
                    "Can't move null item in position " + newPosition + "!");
        }
        if (items == null) {
            throw new NullPointerException(
                    "Can't move item in null collection!");
        }
        final int correctNewPosition;
        if (newPosition < 0) {
            correctNewPosition = 0;
        } else {
            correctNewPosition = newPosition;
        }
        final int oldPosition = items.indexOf(item);
        if (oldPosition != correctNewPosition) {
            items.remove(item);
            if (items.isEmpty() || correctNewPosition > items.size() - 1) {
                items.add(item);
            } else {
                items.add(correctNewPosition, item);
            }
        }
    }

    public static void removeNull(final Collection items) {
        final Iterator itemsIterator = items.iterator();
        while (itemsIterator.hasNext()) {
            if (itemsIterator.next() == null) {
                itemsIterator.remove();
            }
        }
    }

    public static <T> List<T> getWithoutNull(final T... items) {
        final List<T> list = new ArrayList<T>();
        for (final T item : items) {
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    public static String getEmptyOrString(final List<String> stringList, final int pos) {
        if (stringList.size() <= pos) {
            return "";
        } else {
            return stringList.get(pos);
        }
    }

    CollectionUtil() {

    }

}
