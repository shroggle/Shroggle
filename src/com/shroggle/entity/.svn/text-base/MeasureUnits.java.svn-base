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
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Embeddable;

/**
 * @author Balakirev Anatoliy
 *         Date: 22.07.2009
 *         Time: 16:29:23
 */
@DataTransferObject
@Embeddable
public class MeasureUnits {

    public MeasureUnits() {
        topMeasureUnit = MeasureUnit.PX;
        rightMeasureUnit = MeasureUnit.PX;
        bottomMeasureUnit = MeasureUnit.PX;
        leftMeasureUnit = MeasureUnit.PX;
    }

    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private MeasureUnit topMeasureUnit;

    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private MeasureUnit rightMeasureUnit;

    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private MeasureUnit bottomMeasureUnit;

    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private MeasureUnit leftMeasureUnit;

    public MeasureUnit getTopMeasureUnit() {
        return topMeasureUnit;
    }

    public void setTopMeasureUnit(MeasureUnit topMeasureUnit) {
        this.topMeasureUnit = topMeasureUnit;
    }

    public MeasureUnit getRightMeasureUnit() {
        return rightMeasureUnit;
    }

    public void setRightMeasureUnit(MeasureUnit rightMeasureUnit) {
        this.rightMeasureUnit = rightMeasureUnit;
    }

    public MeasureUnit getBottomMeasureUnit() {
        return bottomMeasureUnit;
    }

    public void setBottomMeasureUnit(MeasureUnit bottomMeasureUnit) {
        this.bottomMeasureUnit = bottomMeasureUnit;
    }

    public MeasureUnit getLeftMeasureUnit() {
        return leftMeasureUnit;
    }

    public void setLeftMeasureUnit(MeasureUnit leftMeasureUnit) {
        this.leftMeasureUnit = leftMeasureUnit;
    }
}
