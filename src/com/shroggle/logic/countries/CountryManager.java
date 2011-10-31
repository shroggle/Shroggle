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
package com.shroggle.logic.countries;

import com.shroggle.entity.Country;
import com.shroggle.entity.State;
import com.shroggle.exception.CountryNotFoundException;
import com.shroggle.logic.countries.states.StateManager;
import com.shroggle.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class CountryManager {

    public CountryManager(Country country) {
        if (country == null) {
            throw new CountryNotFoundException("");
        }
        this.country = country;
    }

    public List<String> getStatesByCountry() {
        if (country.getStates() != null) {
            final List<String> statesNames = new ArrayList<String>();
            for (State state : country.getStates()) {
                statesNames.add(new StateManager(state).getName());
            }
            return statesNames;
        } else {
            logger.log(Level.INFO, "Can`t create states for country = " + country.toString());
            return new ArrayList<String>();
        }
    }

    public static String getCountryValue(final Country country) {
        try {
            return ServiceLocator.getInternationStorage().get("countries", Locale.US).get(country.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Can`t find country by code = " + country.toString());
            return "";
        }
    }

    public static List<String> getCountriesStringValues() {
        List<String> countries = new ArrayList<String>();
        for (Country country : Country.values()) {
            countries.add(getCountryValue(country));
        }
        return countries;
    }

    private final Country country;
    private final static Logger logger = Logger.getLogger(CountryManager.class.getName());

}
