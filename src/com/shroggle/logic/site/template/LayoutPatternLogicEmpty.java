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
package com.shroggle.logic.site.template;

import com.shroggle.entity.Layout;
import com.shroggle.entity.ItemType;

/**
 * @author Artem Stasuk
 */
public class LayoutPatternLogicEmpty implements LayoutPatternLogic {

    public LayoutPatternLogicEmpty(final Layout layout) {
        this.layout = layout;
    }

    @Override
    public int getPosition(final ItemType type) {
        return 1;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    private Layout layout;

}
