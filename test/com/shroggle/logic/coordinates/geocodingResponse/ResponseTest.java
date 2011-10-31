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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class ResponseTest {

    @Test
    public void createResponseTest() throws Exception {
        Response response = new Response("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.0\"><Response>\n" +
                "  <name>62468</name>\n" +
                "  <Status>\n" +
                "    <code>200</code>\n" +
                "    <request>geocode</request>\n" +
                "  </Status>\n" +
                "  <Placemark id=\"p1\">\n" +
                "    <address>Toledo, IL 62468, USA</address>\n" +
                "    <AddressDetails Accuracy=\"5\" xmlns=\"urn:oasis:names:tc:ciq:xsdschema:xAL:2.0\"><Country><CountryNameCode>US</CountryNameCode><CountryName>USA</CountryName><AdministrativeArea><AdministrativeAreaName>IL</AdministrativeAreaName><SubAdministrativeArea><SubAdministrativeAreaName>Cumberland</SubAdministrativeAreaName><Locality><LocalityName>Toledo</LocalityName><PostalCode><PostalCodeNumber>62468</PostalCodeNumber></PostalCode></Locality></SubAdministrativeArea></AdministrativeArea></Country></AddressDetails>\n" +
                "    <ExtendedData>\n" +
                "      <LatLonBox north=\"39.3610060\" south=\"39.1821690\" east=\"-88.1391529\" west=\"-88.3782310\" />\n" +
                "    </ExtendedData>\n" +
                "    <Point><coordinates>-88.2655845,39.2869979,0</coordinates></Point>\n" +
                "  </Placemark>\n" +
                "</Response></kml>");
        Assert.assertNotNull(response);
        Assert.assertEquals(39.2869979, response.getLatitude(), 5);
        Assert.assertEquals(-88.2655845, response.getLongitude(), 5);
        Assert.assertEquals(Country.US, response.getCountry());
    }

    @Test()
    public void createResponseTestWithException400() throws Exception {
        Response response = new Response("<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" ?>\\n\" +\n" +
                "                \"<kml xmlns=\\\"http://earth.google.com/kml/2.0\\\"><Response>\\n\" +\n" +
                "                \"  <name>62468</name>\\n\" +\n" +
                "                \"  <Status>\\n\" +\n" +
                "                \"    <code>400</code>\\n\" +\n" +
                "                \"    <request>geocode</request>\\n\" +\n" +
                "                \"  </Status>\\n\" +\n" +
                "                \"</Response></kml>");
        Assert.assertNotNull(response);
        Assert.assertNull(response.getLatitude());
        Assert.assertNull(response.getLongitude());
        Assert.assertNull(response.getCountry());
    }

    @Test()
    public void createResponseTestWithoutResponse() throws Exception {
        Response response = new Response(null);
        Assert.assertNull(response.getLatitude());
        Assert.assertNull(response.getLongitude());
        Assert.assertNull(response.getCountry());
    }
}
