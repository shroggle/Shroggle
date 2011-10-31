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
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
@Ignore   //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
public class GoogleGeocodingTest {

    @Test
    public void testGetCoordinateByZip33155_withPreferredCountry_US() throws Exception {
        //33155 for (US country code): Miami, USA  Latitude: 25.7352, Longitude: -80.3056
        final String zip = "33155";
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(Country.US);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip(zip);
        Assert.assertNotNull(coordinate);
        Assert.assertEquals(Country.US, coordinate.getCountry());
        Assert.assertEquals(zip, coordinate.getZip());
        Assert.assertEquals(25.7360194, coordinate.getLatitude(), 5);
        Assert.assertEquals(-80.3157397, coordinate.getLongitude(), 5);
    }

    @Test
    public void testGetCoordinateByZip33155_withPreferredCountry_ES() throws Exception {
        //33155 for (ES country code):, Cudillero, Spain  Latitude: 43.5596886, Longitude: -6.1865104
        final String zip = "33155";
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(Country.ES);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip(zip);
        Assert.assertNotNull(coordinate);

        Assert.assertEquals(Country.ES, coordinate.getCountry());
        Assert.assertEquals(zip, coordinate.getZip());
        Assert.assertEquals(43.5596886, coordinate.getLatitude(), 5);
        Assert.assertEquals(-6.1865104, coordinate.getLongitude(), 5);
    }

    @Test
    public void testGetCoordinateByZip33155_withoutCountry() throws Exception {
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(null);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip("33155");
        Assert.assertNotNull(coordinate);
        Assert.assertEquals("By default we are using US", Country.US, coordinate.getCountry());
        Assert.assertEquals("33155", coordinate.getZip());
        Assert.assertEquals(25.7360194, coordinate.getLatitude(), 5);
        Assert.assertEquals(-80.3157397, coordinate.getLongitude(), 5);
    }

    @Test
    public void testGetCoordinateByZip62468_withWrongCountry_ES_withoutSuchZipCodeInSpain() throws Exception {
        //62468 for (ES country code):, Toledo, USA  Latitude: 43.5596886, Longitude: -6.1865104
        final String zip = "62468";
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(Country.ES);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip(zip);
        Assert.assertNotNull(coordinate);

        Assert.assertEquals("Created zip code for correct country (US) because there is no such zip code in preferred country (ES, Spain)",
                Country.US, coordinate.getCountry());
        Assert.assertEquals(zip, coordinate.getZip());
        Assert.assertEquals(39.2869979, coordinate.getLatitude(), 5);
        Assert.assertEquals(-88.2655845, coordinate.getLongitude(), 5);
    }

    @Test
    public void testGetCoordinateByZipNull() throws Exception {
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(null);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip(null);
        Assert.assertNotNull(coordinate);
        Assert.assertNull(coordinate.getCountry());
        Assert.assertEquals("", coordinate.getZip());
        Assert.assertNull(coordinate.getLatitude());
        Assert.assertNull(coordinate.getLongitude());
    }


    @Test
    public void testGetCoordinateByZipEmpty() throws Exception {
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(null);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip(" ");
        Assert.assertNotNull(coordinate);
        Assert.assertNull(coordinate.getCountry());
        Assert.assertEquals("", coordinate.getZip());
        Assert.assertNull(coordinate.getLatitude());
        Assert.assertNull(coordinate.getLongitude());
    }
    
    @Test
    public void testGetCoordinateByZipWitrhoutDigits() throws Exception {
        final GoogleGeocoding googleGeocoding = new GoogleGeocoding(null);
        final Coordinate coordinate = googleGeocoding.getCoordinateByZip("ferwferwfrwefer ref");
        Assert.assertNotNull(coordinate);
        Assert.assertNull(coordinate.getCountry());
        Assert.assertEquals("ferwferwfrwefer ref", coordinate.getZip());
        Assert.assertNull(coordinate.getLatitude());
        Assert.assertNull(coordinate.getLongitude());
    }
}
