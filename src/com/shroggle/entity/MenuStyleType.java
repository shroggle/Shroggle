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
package com.shroggle.entity;

public enum MenuStyleType {

    DROP_DOWN_STYLE_HORIZONTAL(false, true, OldValues.DROP_DOWN_STYLE),
    DROP_DOWN_STYLE_VERTICAL(false, false, OldValues.DROP_DOWN_STYLE),
    FULL_HEIGHT_STYLE_HORIZONTAL(false, true, OldValues.FULL_HEIGHT_STYLE),
    FULL_HEIGHT_STYLE_VERTICAL(false, false, OldValues.FULL_HEIGHT_STYLE),
    TABBED_STYLE_HORIZONTAL(false, true, OldValues.TABBED_STYLE),
    TREE_STYLE_HORIZONTAL(true, true, OldValues.TREE_STYLE),
    TREE_STYLE_VERTICAL(true, false, OldValues.TREE_STYLE);


    public static enum OldValues {
        DROP_DOWN_STYLE, TREE_STYLE, FULL_HEIGHT_STYLE, TABBED_STYLE
    }


    MenuStyleType(boolean hasSubmenu, boolean horizontal, OldValues oldValue) {
        this.hasSubmenu = hasSubmenu;
        this.horizontal = horizontal;
        this.oldValue = oldValue;
    }

    private final boolean hasSubmenu;

    private final boolean horizontal;

    private final OldValues oldValue;

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !isHorizontal();
    }

    public boolean isHasSubmenu() {
        return hasSubmenu;
    }

    public String getOldValueAsString() {
        return oldValue.toString();
    }

    public OldValues getOldValue() {
        return oldValue;
    }
}
