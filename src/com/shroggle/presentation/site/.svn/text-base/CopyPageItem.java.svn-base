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
package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class CopyPageItem {

    public boolean isClearFontColors() {
        return clearFontColors;
    }

    public void setClearFontColors(boolean clearFontColors) {
        this.clearFontColors = clearFontColors;
    }

    public boolean isClearBorderBackground() {
        return clearBorderBackground;
    }

    public void setClearBorderBackground(boolean clearBorderBackground) {
        this.clearBorderBackground = clearBorderBackground;
    }

    public CopyPageItemType getType() {
        return type;
    }

    public void setType(CopyPageItemType type) {
        this.type = type;
    }

    @RemoteProperty
    private CopyPageItemType type;

    @RemoteProperty
    private boolean clearFontColors;

    @RemoteProperty
    private boolean clearBorderBackground;

}
