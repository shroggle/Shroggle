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

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Artem Stasuk
 * @link http://forums.java.net/jive/thread.jspa?messageID=266708
 */
class PricesAdapter extends XmlAdapter<PricesElements[], Map<ChargeType, Double>> {

    public PricesElements[] marshal(final Map<ChargeType, Double> arg0) throws Exception {
        final PricesElements[] pricesElements = new PricesElements[arg0.size()];

        int i = 0;
        for (final Map.Entry<ChargeType, Double> entry : arg0.entrySet())
            pricesElements[i++] = new PricesElements(entry.getKey(), entry.getValue());

        return pricesElements;
    }

    public Map<ChargeType, Double> unmarshal(final PricesElements[] arg0) throws Exception {
        final Map<ChargeType, Double> r = new HashMap<ChargeType, Double>();
        for (final PricesElements mapelement : arg0)
            r.put(mapelement.chargeType, mapelement.price);
        return r;
    }

}
