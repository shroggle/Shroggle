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

import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Balakirev Anatoliy
 *         Date: 22.07.2009
 *         Time: 17:51:37
 */
@DataTransferObject
@Embeddable
public class StyleValue {

    public StyleValue() {
        topValue = "";
        rightValue = "";
        bottomValue = "";
        leftValue = "";
    }

    @RemoteProperty
    @Column(length = 20, nullable = false)
    private String topValue;

    @RemoteProperty
    @Column(length = 20, nullable = false)
    private String rightValue;

    @RemoteProperty
    @Column(length = 20, nullable = false)
    private String bottomValue;

    @RemoteProperty
    @Column(length = 20, nullable = false)
    private String leftValue;

    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue;
    }

    public String getRightValue() {
        return rightValue;
    }

    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }

    public String getBottomValue() {
        return bottomValue;
    }

    public void setBottomValue(String bottomValue) {
        this.bottomValue = bottomValue;
    }

    public String getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(String leftValue) {
        this.leftValue = leftValue;
    }
}
