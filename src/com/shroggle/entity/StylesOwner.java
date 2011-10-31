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

/**
 * @author Balakirev Anatoliy
 */
public interface StylesOwner {

    Integer getFontsAndColorsId();

    void setFontsAndColorsId(Integer fontsAndColorsId);

    public Integer getBackgroundId();

    public void setBackgroundId(Integer backgroundId);

    public Integer getBorderId();

    public void setBorderId(Integer borderId);

    public Integer getItemSizeId();

    public void setItemSizeId(Integer itemSizeId);

    public Integer getAccessibleSettingsId();

    public void setAccessibleSettingsId(Integer accessibleSettingsId);

}
