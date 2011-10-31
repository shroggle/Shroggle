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
 * @author dmitry.solomadin
 */
public interface PurchaseHistory extends Item {

    public boolean isShowProductImage();

    public void setShowProductImage(boolean showProductImage);

    public int getImageWidth();

    public void setImageWidth(int imageWidth);

    public int getImageHeight();

    public void setImageHeight(int imageHeight);

    public boolean isShowProductDescription();

    public void setShowProductDescription(boolean showProductDescription);
}
