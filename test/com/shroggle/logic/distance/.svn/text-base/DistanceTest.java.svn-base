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
import com.shroggle.entity.Country;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class DistanceTest {


    @Test
    public void testCreate() {
        //http://www.timeanddate.com/worldclock/distanceresult.html?p1=166&p2=367
        Coordinate coordinateOfMoscow = new Coordinate(Country.US, 55.45, 37.37, "");
        Coordinate coordinateOfKiev = new Coordinate(Country.US, 50.28, 30.29, "");
        final Distance distance = new Distance(coordinateOfMoscow, coordinateOfKiev);
        Assert.assertEquals(745.097, distance.getKilometres(), 3);
        Assert.assertEquals(462.981, distance.getStatuteMiles(), 3);
        Assert.assertEquals(402.053, distance.getNauticalMiles(), 3);


        //http://www.timeanddate.com/worldclock/distanceresult.html?p1=113&p2=367
        Coordinate coordinateOfKabul = new Coordinate(Country.US, 34.30, 69.10, "");
        final Distance distanceBetweenKievAndKabul = new Distance(coordinateOfKabul, coordinateOfKiev);
        Assert.assertEquals(3601.313, distanceBetweenKievAndKabul.getKilometres(), 3);
        Assert.assertEquals(2237.447, distanceBetweenKievAndKabul.getStatuteMiles(), 3);
        Assert.assertEquals(1940.897, distanceBetweenKievAndKabul.getNauticalMiles(), 3);
    }


    @Test
    public void testCreate_withoutCoordinates() {
        Coordinate firstCoordinate;
        Coordinate secondCoordinate;
        Distance distance;

        firstCoordinate = new Coordinate(Country.US, null, 37.37, "");
        secondCoordinate = new Coordinate(Country.US, 50.28, 30.29, "");
        distance = new Distance(firstCoordinate, secondCoordinate);
        Assert.assertNull(distance.getKilometres());
        Assert.assertNull(distance.getStatuteMiles());
        Assert.assertNull(distance.getNauticalMiles());


        firstCoordinate = new Coordinate(Country.US, 55.45, null, "");
        secondCoordinate = new Coordinate(Country.US, 50.28, 30.29, "");
        distance = new Distance(firstCoordinate, secondCoordinate);
        Assert.assertNull(distance.getKilometres());
        Assert.assertNull(distance.getStatuteMiles());
        Assert.assertNull(distance.getNauticalMiles());


        firstCoordinate = new Coordinate(Country.US, 55.45, 37.37, "");
        secondCoordinate = new Coordinate(Country.US, null, 30.29, "");
        distance = new Distance(firstCoordinate, secondCoordinate);
        Assert.assertNull(distance.getKilometres());
        Assert.assertNull(distance.getStatuteMiles());
        Assert.assertNull(distance.getNauticalMiles());


        firstCoordinate = new Coordinate(Country.US, 55.45, 37.37, "");
        secondCoordinate = new Coordinate(Country.US, 50.28, null, "");
        distance = new Distance(firstCoordinate, secondCoordinate);
        Assert.assertNull(distance.getKilometres());
        Assert.assertNull(distance.getStatuteMiles());
        Assert.assertNull(distance.getNauticalMiles());
    }

}
