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
package com.shroggle.logic.coordinates;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class CoordinateCreator {

    /**
     * @param zip String - selected zip code. Necessary parameter. We can`t create Coordinate without it.
     * @return Coordinate for selected zip. Firstly we are trying to find coordinate with this zip in the USA.
     *         If there is no such zip in the USA we are trying to find it in another country (I don`t know order of
     *         countries for search, I use google geocoding for this).
     *         Note: If we can`t find correct coordinate for selected zip - return Coordinate without country, latitude
     *         and longitude. It means, what such zip code does not exist.
     */
    public static Coordinate getExistingOrCreateNew(final String zip) {
        return getExistingOrCreateNew(zip, Country.US);
    }

    /**
     * @param zip     String - selected zip code. Necessary parameter. We can`t create Coordinate without it.
     * @param country - preferred country for search. Optional parameter.
     * @return Coordinate for selected zip. Firstly we are trying to find coordinate with this zip in the preferred country.
     *         If there is no such zip in the preferred country or preferred country is null - we are trying to find it in the USA.
     *         If there is no such zip in the USA we are trying to find it in another country (I don`t know order of
     *         countries for search, I use google geocoding for this).
     *         Note: If we can`t find correct coordinate for selected zip - return Coordinate without country, latitude
     *         and longitude. It means, what such zip code does not exist.
     */
    public static Coordinate getExistingOrCreateNew(final String zip, final Country country) {
        final Persistance persistance = ServiceLocator.getPersistance();
        //Trying to find coordinate in DB. If there is coordinate with such zip and country - return it.
        final Coordinate coordinate = persistance.getCoordinate(zip, country);
        if (coordinate != null) {
            return coordinate;
        }
        //Creating new coordinate by zip and country.
        final Coordinate createdCoordinate = new GoogleGeocoding(country).getCoordinateByZip(zip);
        // I save this coordinate only to speed up this method. (In next call we just find it in DB without googleGeocoding).
        if (createdCoordinate.isValid() && persistance.getCoordinate(zip, createdCoordinate.getCountry()) == null) {
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                public void run() {
                    persistance.putCoordinate(createdCoordinate);
                }
            });
        }
        return createdCoordinate;
    }

    public static List<Coordinate> createCoordinates(final FilledForm filledForm) {
        final List<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (FilledFormItem filledFormItem : FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.POST_CODE)) {
            final Coordinate coordinate = createCoordinate(filledFormItem);
            if (coordinate != null) {
                coordinates.add(coordinate);
            }
        }
        return coordinates;
    }

    public static Coordinate createCoordinate(final FilledFormItem filledFormItem) {
        if (filledFormItem.getValues().isEmpty() || filledFormItem.getFormItemName() != FormItemName.POST_CODE) {
            return null;
        }
        final String zip = filledFormItem.getValues().get(0);
        Country country = null;
        Double latitude = null;
        Double longitude = null;
        try {
            country = Country.valueOf(filledFormItem.getValues().get(1));
            latitude = Double.valueOf(filledFormItem.getValues().get(2));
            longitude = Double.valueOf(filledFormItem.getValues().get(3));
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can`t get country, latitude or longitude for filledForm with id = " + filledFormItem.getItemId());
        }
        if (country == null || latitude == null || longitude == null) {
            country = null;
            latitude = null;
            longitude = null;
        }
        if (zip != null && !zip.trim().isEmpty()) {
            return new Coordinate(country, latitude, longitude, zip);
        }
        return null;
    }

    private static final Logger logger = Logger.getLogger(CoordinateCreator.class.getName());
}
