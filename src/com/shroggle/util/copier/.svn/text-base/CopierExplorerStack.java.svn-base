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

import java.util.List;
import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class CopierExplorerStack implements CopierExplorer {

    public CopierExplorerStack(final CopierExplorer... explorers) {
        this.explorers = Arrays.copyOf(explorers, explorers.length);
    }

    @Override
    public List<CopierItem> find(final Object original, final Object copy) {
        for (final CopierExplorer explorer : explorers) {
            final List<CopierItem> items = explorer.find(original, copy);
            if (items != null) {
                return items;
            }
        }
        return null;
    }

    private final CopierExplorer[] explorers;

}
