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
package com.shroggle.logic.coordinates.geocodingResponse;

import com.shroggle.entity.Country;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Balakirev Anatoliy
 */
public class Response {

    public Response(final String response) {
        latitude = getLatitude(response);
        longitude = getLongitude(response);
        country = getCountry(response);
    }

    private final Double latitude;

    private final Double longitude;

    private final Country country;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Country getCountry() {
        return country;
    }

    private Double getLatitude(final String response) {
        try {
            final Pattern pattern = Pattern.compile("<Point>\\s*<coordinates>([^<]*)\\s*</coordinates>\\s*</Point>", Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                return Double.valueOf(matcher.group(1).split(",")[1]);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Double getLongitude(final String response) {
        try {
            final Pattern pattern = Pattern.compile("<Point>\\s*<coordinates>([^<]*)\\s*</coordinates>\\s*</Point>", Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                return Double.valueOf(matcher.group(1).split(",")[0]);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Country getCountry(final String response) {
        try {
            final Pattern pattern = Pattern.compile("<CountryNameCode>(\\w\\w)</CountryNameCode>", Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                return Country.valueOf(matcher.group(1));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
