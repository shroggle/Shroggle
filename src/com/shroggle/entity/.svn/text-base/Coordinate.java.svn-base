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
package com.shroggle.entity;

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "coordinates")
public class Coordinate {

    public Coordinate() {
    }

    public Coordinate(Country country, Double latitude, Double longitude, String zip) {
        if (zip == null) {
            throw new IllegalArgumentException("Can`t create Coordinate without zip code.");
        }
        this.zip = zip;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    private int coordinateId;

    @Column(nullable = false, updatable = false, length = 20)
    private String zip;

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false, length = 2)
    private Country country;

    @Column(updatable = false)
    private Double latitude;

    @Column(updatable = false)
    private Double longitude;

    public int getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(int coordinateId) {
        this.coordinateId = coordinateId;
    }

    public String getZip() {
        return zip;
    }

    public Country getCountry() {
        return country;
    }

    /**
     * @return Double - Latitude in degrees. If latitude is null - selected zipCode does not exist.
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return Double - Longitude in degrees. If longitude is null - selected zipCode does not exist.
     */
    public Double getLongitude() {
        return longitude;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String[] toStringArray() {
        String[] array = new String[4];
        array[0] = zip;
        array[1] = country != null ? country.toString() : null;
        array[2] = latitude != null ? latitude.toString() : null;
        array[3] = longitude != null ? longitude.toString() : null;
        return array;
    }

    public boolean isValid() {
        return country != null && latitude != null && longitude != null;
    }
}
