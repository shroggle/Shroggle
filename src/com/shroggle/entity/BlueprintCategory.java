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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum BlueprintCategory {

    BUILDING_ENGINEERING,
    FOOD_NUTRITION,
    RESTAURANTS_FOOD_SERVICE,
    APPAREL,
    FURNITURE_INTERIORS,
    BOOKS_PRINTING,
    AUTOMOBILES_MECHANICS,
    LANDSCAPE_NURSERY_GARDENING,
    OPTICIANS_EYE_GLASSES,
    JEWELRY,
    ART_CRAFTS,
    MUSIC_DJS,
    RADIO_BROADCASTING,
    FINANCE_BANKING_INVESTMENT,
    REAL_ESTATE_DEVELOPMENT,
    HOTELS_ACCOMMODATIONS,
    ALTERNATIVE_THERAPISTS,
    FILM_VIDEO,
    MEDICAL_NURSING,
    CHILD_CARE_EDUCATION,
    BUSINESS_SERVICES,
    TRAVEL_ADVENTURE,
    RESUMES,
    FAMILY_SITES,
    ECOMMERCE_SITES,
    ENTERPRISE_BACKOFFICE,
    DOGWALKERS_VETS_GROOMERS,
    BIRTHS_AND_BABIES,
    WEDDINGS,
    EDUCATION,
    WEBSITE_DEVELOPERS
}
