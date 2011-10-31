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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

/**
 * @author dmitry.solomadin
 */
public class ConfigPayment {

    public ConfigPayment() {
        for (final ChargeType chargeType : ChargeType.values()) {
            prices.put(chargeType, 0f);
        }
    }

    @XmlElement(name = "prices")
    @XmlJavaTypeAdapter(PricesAdapter.class)
    public HashMap<ChargeType, Float> getPrices() {
        return prices;
    }

    public void setPrices(final HashMap<ChargeType, Float> prices) {
        this.prices = prices;
    }

    private HashMap<ChargeType, Float> prices = new HashMap<ChargeType, Float>();

}

