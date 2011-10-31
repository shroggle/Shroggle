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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CoordinateCreatorTest {

    @Ignore      //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withExistingCoordinate() throws Exception {
        final Coordinate coordinate = new Coordinate(Country.AE, 21.0, 45.678, "123");
        persistance.putCoordinate(coordinate);
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("123", Country.AE);
        Assert.assertEquals(newCoordinate, coordinate);
    }

    @Ignore  //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withoutExistingCoordinate() throws Exception {
        final Coordinate coordinate = new Coordinate(Country.AE, 21.0, 45.678, "48208");
        persistance.putCoordinate(coordinate);

        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("48208", Country.US);
        Assert.assertNotSame(newCoordinate, coordinate);
        Assert.assertEquals("48208", newCoordinate.getZip());
        Assert.assertEquals(Country.US, newCoordinate.getCountry());
        Assert.assertEquals(42.3481541, newCoordinate.getLatitude(), 3);
        Assert.assertEquals(-83.0951852, newCoordinate.getLongitude(), 3);
    }

    @Ignore //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withoutCoordinateInDB_withCorrectCountryCode() throws Exception {
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("48208", Country.US);
        Assert.assertEquals("48208", newCoordinate.getZip());
        Assert.assertEquals(Country.US, newCoordinate.getCountry());
        Assert.assertEquals(42.3481541, newCoordinate.getLatitude(), 3);
        Assert.assertEquals(-83.0951852, newCoordinate.getLongitude(), 3);
    }

    @Ignore //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withoutCoordinateInDB_withWrongCountryCode() throws Exception {
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("48208", Country.AE);
        Assert.assertEquals("48208", newCoordinate.getZip());
        Assert.assertEquals("Created coordinate for correct country (US). Because there is no such zip code in AE.",
                Country.US, newCoordinate.getCountry());
        Assert.assertEquals(42.3481541, newCoordinate.getLatitude(), 3);
        Assert.assertEquals(-83.0951852, newCoordinate.getLongitude(), 3);
    }

    @Ignore     //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withCoordinateInDB_withWrongCountryCodeInRequest() throws Exception {
        final Coordinate coordinate = new Coordinate(Country.US, 42.3481541, -83.0951852, "48208");
        persistance.putCoordinate(coordinate);
        Assert.assertEquals(1, ((PersistanceMock) persistance).getAllCoordinates().size());
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("48208", Country.AE);

        //We can`t find coordinate with current zip code and country = AE in our database and can`t find this zip code in AE (United Arab Emirates).
        //Trying to find it in another country.
        //We can find it in US. Then we are trying to find coordinate with zip code = "48208" and Country = US in our DB.
        //There is such coordinate there and we jst return it.
        Assert.assertEquals(coordinate.getZip(), newCoordinate.getZip());
        Assert.assertEquals(coordinate.getLongitude(), newCoordinate.getLongitude());
        Assert.assertEquals(coordinate.getLatitude(), newCoordinate.getLatitude());
        Assert.assertEquals(coordinate.getCountry(), newCoordinate.getCountry());
        Assert.assertEquals(1, ((PersistanceMock) persistance).getAllCoordinates().size());
    }

    @Test
    public void testGetExistingOrCreateNew_withCoordinateInDB_withWrongZip() throws Exception {
        final Coordinate coordinate = new Coordinate(null, null, null, "adsfasdf");
        persistance.putCoordinate(coordinate);
        Assert.assertEquals(1, ((PersistanceMock) persistance).getAllCoordinates().size());
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("adsfasdf", null);

        //We can find this coordinate in DB and just return it without adding another one in DB
        Assert.assertEquals(coordinate.getZip(), newCoordinate.getZip());
        Assert.assertEquals(coordinate.getLongitude(), newCoordinate.getLongitude());
        Assert.assertEquals(coordinate.getLatitude(), newCoordinate.getLatitude());
        Assert.assertEquals(coordinate.getCountry(), newCoordinate.getCountry());
        Assert.assertEquals(1, ((PersistanceMock) persistance).getAllCoordinates().size());
    }

    @Ignore       //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withoutCoordinateInDB_withWrongZip_withoutCountry() throws Exception {
        Assert.assertEquals(0, ((PersistanceMock) persistance).getAllCoordinates().size());
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("adsfasdf", null);

        //We can find this coordinate in DB and just return it without adding another one in DB
        Assert.assertEquals("adsfasdf", newCoordinate.getZip());
        Assert.assertNull(newCoordinate.getLongitude());
        Assert.assertNull(newCoordinate.getLatitude());
        Assert.assertNull(newCoordinate.getCountry());
        Assert.assertEquals(0, ((PersistanceMock) persistance).getAllCoordinates().size());
    }

    @Ignore      //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew_withoutCoordinateInDB_withWrongZip_country_US() throws Exception {
        Assert.assertEquals(0, ((PersistanceMock) persistance).getAllCoordinates().size());
        final Coordinate newCoordinate = CoordinateCreator.getExistingOrCreateNew("adsfasdf", Country.US);

        //We can find this coordinate in DB and just return it without adding another one in DB
        Assert.assertEquals("adsfasdf", newCoordinate.getZip());
        Assert.assertNull(newCoordinate.getLongitude());
        Assert.assertNull(newCoordinate.getLatitude());
        Assert.assertNull(newCoordinate.getCountry());
        Assert.assertEquals(0, ((PersistanceMock) persistance).getAllCoordinates().size());
    }

    @Ignore      //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testGetExistingOrCreateNew() {
        final String zip = "33155";
        final Coordinate coordinate = CoordinateCreator.getExistingOrCreateNew(zip);

        Assert.assertNotNull(coordinate);

        Assert.assertEquals("By default this method use Country.US. Because of this we get coordinate in USA although " +
                "there is coordinate with such zipCode in Spain", Country.US, coordinate.getCountry());
        Assert.assertEquals(zip, coordinate.getZip());
        Assert.assertEquals(25.7360194, coordinate.getLatitude(), 5);
        Assert.assertEquals(-80.3157397, coordinate.getLongitude(), 5);
    }

    /*---------------------------------------Create coordinate by FilledFormItem--------------------------------------*/

    @Test
    public void testCreateCoordinate() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "US", "1.0", "2.0"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(Country.US, coordinate.getCountry());
        junit.framework.Assert.assertEquals(1.0, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(2.0, coordinate.getLongitude());
    }

    @Test
    public void testCreateCoordinate_withoutCountry() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", null, "1.0", "2.0"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }


    @Test
    public void testCreateCoordinate_withWrongCountry() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "adsfasd", "1.0", "2.0"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }


    @Test
    public void testCreateCoordinate_withoutLatitude() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "US", null, "2.0"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }


    @Test
    public void testCreateCoordinate_withWrongLatitude() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "US", "1.asd", "2.0"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }


    @Test
    public void testCreateCoordinate_withoutLongitude() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "US", "1.0", null));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }


    @Test
    public void testCreateCoordinate_withWrongLongitude() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(Arrays.asList("zip", "US", "1.0", "2sgh.asdgsfdh"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertEquals("zip", coordinate.getZip());
        junit.framework.Assert.assertEquals(null, coordinate.getCountry());
        junit.framework.Assert.assertEquals(null, coordinate.getLatitude());
        junit.framework.Assert.assertEquals(null, coordinate.getLongitude());
    }

    @Test
    public void testCreateCoordinate_withEmptyValues() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValues(new ArrayList<String>());
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertNull(coordinate);
    }

    @Test
    public void testCreateCoordinate_forWrongFormItemName() throws Exception {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        filledFormItem.setValues(Arrays.asList("name"));
        Coordinate coordinate = CoordinateCreator.createCoordinate(filledFormItem);
        junit.framework.Assert.assertNull(coordinate);
    }


    @Test
    public void testCreateCoordinates() throws Exception {
        final FilledFormItem correctFilledFormItemUS = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        correctFilledFormItemUS.setValues(Arrays.asList("zip", "US", "1.0", "2.0"));
        final FilledFormItem correctFilledFormItemUA = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        correctFilledFormItemUA.setValues(Arrays.asList("zipUA", "UA", "1.546456456", "2.456456"));
        final FilledFormItem wrongFilledFormItem1 = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        wrongFilledFormItem1.setValues(new ArrayList<String>());
        final FilledFormItem wrongFilledFormItem2 = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        wrongFilledFormItem2.setValues(Arrays.asList("name"));

        List<FilledFormItem> filledFormItems = Arrays.asList(correctFilledFormItemUS, correctFilledFormItemUA, wrongFilledFormItem1, wrongFilledFormItem2);

        FilledForm filledForm = TestUtil.createFilledContactUsForm(TestUtil.createUser(), TestUtil.createDefaultRegistrationFilledFormItems(), TestUtil.createContactUsForm());
        filledForm.setFilledFormItems(filledFormItems);


        final List<Coordinate> coordinates = CoordinateCreator.createCoordinates(filledForm);
        junit.framework.Assert.assertEquals(2, coordinates.size());

        junit.framework.Assert.assertEquals("zip", coordinates.get(0).getZip());
        junit.framework.Assert.assertEquals(Country.US, coordinates.get(0).getCountry());
        junit.framework.Assert.assertEquals(1.0, coordinates.get(0).getLatitude(), 1);
        junit.framework.Assert.assertEquals(2.0, coordinates.get(0).getLongitude(), 1);

        junit.framework.Assert.assertEquals("zipUA", coordinates.get(1).getZip());
        junit.framework.Assert.assertEquals(Country.UA, coordinates.get(1).getCountry());
        junit.framework.Assert.assertEquals(1.546456456, coordinates.get(1).getLatitude(), 1);
        junit.framework.Assert.assertEquals(2.456456, coordinates.get(1).getLongitude(), 1);
    }

    /*---------------------------------------Create coordinate by FilledFormItem--------------------------------------*/

    private final Persistance persistance = ServiceLocator.getPersistance();
}
