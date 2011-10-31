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
 * @author dmitry.solomadin
 */
public class MapUtil {

    public static <K, V> Map<K, V> sortByValue(final Map<K, V> map, final boolean desc) {
        final List<Map.Entry<K, V>> mapEntries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(mapEntries, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                V v1 = o1.getValue();
                V v2 = o2.getValue();

                if (!(v1 instanceof Comparable) || !(v2 instanceof Comparable)) {
                    throw new IllegalStateException("Map value should be Comparable.");
                }


                return ((Comparable) v1).compareTo(v2);
            }
        });

        if (desc) {
            Collections.reverse(mapEntries);
        }

        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : mapEntries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
