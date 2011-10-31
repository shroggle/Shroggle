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
import java.util.ArrayList;

/**
 * @author Artem Stasuk
 */
public class CopierExplorerList implements CopierExplorer {

    @Override
    public List<CopierItem> find(final Object original, final Object copy) {
        if (original instanceof List) {
            final List<CopierItem> items = new ArrayList<CopierItem>();
            final List originalList = (List) original;
            final List copyList = (List) copy;
            for (final Object originalItem : originalList) {
                items.add(new CopierItemList(copyList, originalItem));
            }
            return items;
        }
        return null;
    }

}
