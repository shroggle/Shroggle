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
import com.shroggle.entity.LayoutPattern;
import com.shroggle.entity.PatternPosition;
import com.shroggle.entity.ItemType;

/**
 * @author Artem Stasuk
 */
public class LayoutPatternLogicReal implements LayoutPatternLogic {

    public LayoutPatternLogicReal(final LayoutPattern pattern) {
        this.pattern = pattern;
        this.withoutPosition = new LayoutPatternLogicEmpty(pattern.getLayout());
    }

    @Override
    public int getPosition(final ItemType type) {
        Integer defaultPosition = null;
        for (final PatternPosition position : pattern.getPositions()) {
            if (position.getType() == null) {
                defaultPosition = position.getPosition();
            }

            if (position.getType() == type) {
                return position.getPosition();
            }
        }

        if (defaultPosition != null) {
            return defaultPosition;
        }

        return withoutPosition.getPosition(type);
    }

    @Override
    public Layout getLayout() {
        return withoutPosition.getLayout();
    }

    private final LayoutPattern pattern;
    private final LayoutPatternLogic withoutPosition;

}
