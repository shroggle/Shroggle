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
package com.shroggle.logic.distance;

import com.shroggle.entity.Coordinate;

/**
 * @author Balakirev Anatoliy
 */
public strictfp class Distance {

    public Distance(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        if (firstCoordinate.getLatitude() == null || firstCoordinate.getLongitude() == null ||
                secondCoordinate.getLatitude() == null || secondCoordinate.getLongitude() == null) {
            statuteMiles = null;
            nauticalMiles = null;
            kilometres = null;
            return;
        }
        final double firstLatitudeRad = StrictMath.toRadians(firstCoordinate.getLatitude());
        final double firstLongitudeRad = StrictMath.toRadians(firstCoordinate.getLongitude());
        final double secondLatitudeRad = StrictMath.toRadians(secondCoordinate.getLatitude());
        final double secondLongitudeRad = StrictMath.toRadians(secondCoordinate.getLongitude());

        final double distance = StrictMath.acos(Math.sin(firstLatitudeRad)
                * StrictMath.sin(secondLatitudeRad)
                + StrictMath.cos(firstLatitudeRad) * Math.cos(secondLatitudeRad)
                * StrictMath.cos(firstLongitudeRad - secondLongitudeRad));
        statuteMiles = distance * EARTH_RADIUS_IN_STATUTE_MILES;
        nauticalMiles = distance * EARTH_RADIUS_IN_NAUTICAL_MILES;
        kilometres = distance * EARTH_RADIUS_IN_KILOMETRES;
    }

    public Double getStatuteMiles() {
        return statuteMiles;
    }

    public Double getNauticalMiles() {
        return nauticalMiles;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public final Double statuteMiles;
    public final Double nauticalMiles;
    public final Double kilometres;
    private static final double EARTH_RADIUS_IN_STATUTE_MILES = 3963.0;
    private static final double EARTH_RADIUS_IN_NAUTICAL_MILES = 3437.74677;
    private static final double EARTH_RADIUS_IN_KILOMETRES = 6378.7;
}