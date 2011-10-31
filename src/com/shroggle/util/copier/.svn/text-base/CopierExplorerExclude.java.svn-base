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
package com.shroggle.util.copier;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class CopierExplorerExclude implements CopierExplorer {

    public CopierExplorerExclude(final CopierExplorer explorer, final String... exclude) {
        this.exclude = new HashSet<String>(Arrays.asList(exclude));
        this.explorer = explorer;
    }

    @Override
    public List<CopierItem> find(Object original, Object copy) {
        final List<CopierItem> items = explorer.find(original, copy);
        final Iterator<CopierItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            if (exclude.contains(itemIterator.next().getName())) {
                itemIterator.remove();
            }
        }
        return items;
    }

    private final CopierExplorer explorer;
    private final Set<String> exclude;

}
