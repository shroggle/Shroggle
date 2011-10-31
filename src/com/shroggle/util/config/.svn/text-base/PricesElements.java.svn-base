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
package com.shroggle.util.config;

import com.shroggle.entity.ChargeType;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Artem Stasuk
 */
class PricesElements {

    /**
     * Special constructor for jaxb.
     */
    PricesElements() {
    }

    public PricesElements(final ChargeType chargeType, final Double price) {
        this.chargeType = chargeType;
        this.price = price;
    }

    @XmlAttribute
    public ChargeType chargeType;

    @XmlAttribute
    public Double price;

}
