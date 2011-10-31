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
package com.shroggle.presentation.taxRates;

import com.shroggle.entity.States_US;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class SaveTaxRatesRequest {

    private String name;

    private Integer itemId;

    private Map<States_US, Double> statesTaxes = new HashMap<States_US, Double>();

    public Map<States_US, Double> getStatesTaxes() {
        return statesTaxes;
    }

    public void setStatesTaxes(Map<States_US, Double> statesTaxes) {
        this.statesTaxes = statesTaxes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
