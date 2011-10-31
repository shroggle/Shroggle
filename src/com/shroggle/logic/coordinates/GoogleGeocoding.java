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


import com.shroggle.entity.Coordinate;
import com.shroggle.entity.Country;
import com.shroggle.logic.coordinates.geocodingResponse.Response;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class GoogleGeocoding {

    public GoogleGeocoding(Country preferredCountry) {
        final String baseURL = "http://maps.google.com/maps/geo?output=xml&oe=utf8&sensor=false&gl=";
        if (preferredCountry == null) {
            URL = baseURL + Country.US.toString();
        } else {
            URL = baseURL + preferredCountry.toString();
        }
    }

    /**
     * @param zip String - selected zip code
     * @return Coordinate - coordinate for selected zip.
     *         If we can find such zip in preferred country - just return it.
     *         If we can`t find such zip in preferred country - we are trying to find it in another one.
     *         If we can`t find this zip anywhere - return Coordinate without country, latitude and longitude.
     *         It means, what such zip code does not exist.
     */
    public Coordinate getCoordinateByZip(String zip) {
        try {
            if (zip == null || zip.trim().isEmpty()) {
                zip = "";
                throw new IllegalArgumentException("Can`t create correct coordinate by null zip code.");
            }
            final URL url = new URL(URL + "&q=" + zip /*+ "&key=" + KEY*/);
            final URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.connect();                             
            final InputStream inputStream = urlConnection.getInputStream();
            final byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer, 0, buffer.length - 1);
            final Response response = new Response(new String(buffer));
            return new Coordinate(response.getCountry(), response.getLatitude(), response.getLongitude(), zip);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Can`t create coordinate!", exception);
            return new Coordinate(null, null, null, zip);
        }
    }

    private final String URL;
    private static final int TIMEOUT = 5000;
    private final static String KEY = "ABQIAAAANsyzmMZ9m73J4achVAce5RSAfSCceo72sUxVgZ1w8ceXqDbzlxSLNCbCXsFvgwdw-Hs4imZe-3_VMA";
}