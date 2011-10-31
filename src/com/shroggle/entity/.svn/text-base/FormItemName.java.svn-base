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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject(converter = EnumConverter.class)
public enum FormItemName {

    //Keep this on top
    PAGE_BREAK(FormItemType.SPECIAL, Arrays.asList(FormItemFilter.ALL_FILTERS)),
    LINE_HR(FormItemType.SPECIAL, Arrays.asList(FormItemFilter.ALL_FILTERS)),
    HEADER(FormItemType.SPECIAL, Arrays.asList(FormItemFilter.ALL_FILTERS)),

    ACADEMIC_DEGREE(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT)),
    ASSIGNED(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.CRM_TICKET_TRACKING)),
    ACADEMIC_EXPERIENCE_SCHOOL(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY)),
    ACADEMIC_EXPERIENCE_DESCRIPTION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY)),
    ADDRESS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    ADOPTED(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.FAMILY)),
    ADOPTIVE_FATHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    ADOPTIVE_MOTHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    AGE(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS)),
    AGE_RANGE(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS)),
    AIR_CONDITIONING(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL)),
    ALIASES_NOM_DE_PLUME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY)),
    ALLERGIES(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.VACATION_RENTAL)),
    ASTROLOGICAL_SIGN(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY)),
    AUDIO_FILE_UPLOAD(FormItemType.FILE_UPLOAD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.GALLERY)),
    AWARDS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    BATHROOMS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    BEDROOMS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    BEDROOMS_WITH_ENSUTE_BATHROOM(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    BILLING_ADDRESS(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.PERSONAL_DATING, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS)),
    BIRTH_DATE(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY)),
    BIRTH_CIRCUMSTANCES(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    BODY_TYPE(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    BREAKFAST_INCLUDED(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    CAST_LIST(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.FILM)),
    CAST_LIST_LONG(FormItemType.TEXT_AREA_DOUBLE_SIZE, Arrays.asList(FormItemFilter.FILM)),
    CEILING_HEIGHT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    CENTRAL_HEATING(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    CHILDREN_UNDER_10_ALLOWED(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    CHINESE_ASTROLOGICAL_SIGN(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    CITIZENSHIPS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    CITY(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    CITY_BORN(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    CITY_DIED(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    CITY_WHERE_MARRIED(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    CLIMATE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    COMPANY(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    CONCIERGE_SERVICE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL)),
    COUNTER_TOPS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    COUNTRY(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    COUNTRY_BORN(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    COUNTRY_DIED(FormItemType.SELECT, Arrays.asList(FormItemFilter.FAMILY)),
    COUNTRY_WHERE_MARRIED(FormItemType.SELECT, Arrays.asList(FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING)),
    CC_NUMBER(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.PERSONAL_DATING, FormItemFilter.FILM, FormItemFilter.EVENTS)),
    CURRENT_EMPLOYMENT(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    CUSTOMER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS)),
    CHECKBOX(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.BASIC)),
    DATE_ADDED(FormItemType.FIVE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM), Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.GALLERY)),
    DATE_HH_MM(FormItemType.TWO_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_HH_MM), Arrays.asList(FormItemFilter.BASIC)),
    DATE_DD_MM_YYYY(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM), Arrays.asList(FormItemFilter.BASIC)),
    DATE_DD_MM_YYYY_HH_MM(FormItemType.FIVE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM), Arrays.asList(FormItemFilter.BASIC)),
    DEATH_CIRCUMSTANCES(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.FAMILY)),
    DEATH_DATE(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.FAMILY)),
    DELIVERY_ADDRESS(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    DEPARTMENT(FormItemType.SELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS)),
    DEPTH(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS)),
    DESCRIPTION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.CRM_TICKET_TRACKING)),
    DESCRIPTION_OF_BREAKFAST(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    DESCRIBE_VIEWS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL)),
    DESCRIBE_POWER_SOURCE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    DESCRIBE_WATER_SOURCE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    DIETARY_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    DINING_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    DIRECTIONS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS, FormItemFilter.FAMILY, FormItemFilter.CRM_TICKET_TRACKING)),
    DIVORCE_DATE(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING)),
    DIVORCED(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    DO_YOU_HAVE_CHILDREN(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    DOGS_ALLOWED(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    DOG_BREED(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.FAMILY, FormItemFilter.PRODUCTS)),
    DOUBLE_PANED_WINDOWS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    DURATION(FormItemType.TWO_PICK_LISTS, FormItemCheckerType.DATE_FORMAT_HH_MM, Arrays.asList(FormItemFilter.EVENTS)),
    ELEVATOR(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    EMAIL(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.CRM_TICKET_TRACKING)),
    EMBEDDED_HTML_OBJECT(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    EMIGRATED_TO(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    EN_SUITE_ROOM(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    ENTERED_BY(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING, FormItemFilter.GALLERY)),
    END_DATE_AND_TIME(FormItemType.FIVE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM), Arrays.asList(FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    EQUIPPED_KITCHEN(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    EXPIRATION_DATE(FormItemType.TWO_TEXT_FIELDS, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.FILM, FormItemFilter.EVENTS)),
    EXTRA_LONG_RTF_TEXT(FormItemType.RTF, Arrays.asList(FormItemFilter.BASIC)),
    EYE_COLOR(FormItemType.SELECT, Arrays.asList(FormItemFilter.FAMILY)),
    FAMILY(FormItemType.SINGLE_CHOICE_OPTION_LIST, Arrays.asList(FormItemFilter.PLANTS)),
    FATHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    FAX_NUMBER(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS)),
    FIRST_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    FILM_LENGTH(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY)),
    FLOORING(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    FLOORS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    FULL_DAY(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.EVENTS, FormItemFilter.JOBS_EMPLOYMENT)),
    FORMAT_OF_PROPOSED_COURSE(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT)),
    GARDEN(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    GENDER(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS)),
    GENUS(FormItemType.SINGLE_CHOICE_OPTION_LIST, Arrays.asList(FormItemFilter.PLANTS)),
    GPS_COORDINATES(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS, FormItemFilter.FAMILY)),
    GRAND_FATHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    GRAND_MOTHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    HAIR(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    HEIGHT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.GALLERY)),
    HEIGHT_RANGE(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    HOBBIES(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS)),
    HOBBIES_DESCRIPTION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    HOLYDAY_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    HOME_OWNERSHIP(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    HOT_TUB(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    HOT_WATER_HEATER(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    HOUSEHOLD_INCOME(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    HOW_ACTIVE_ARE_YOU(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    HOW_MANY_CHILDREN_DO_YOU_HAVE(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    IMAGE_FILE_UPLOAD(FormItemType.FILE_UPLOAD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING, FormItemFilter.GALLERY)),
    INCOME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    INCOME_RANGE_PER_YEAR(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    INGREDIENTS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS)),
    IM(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    INSULATION(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    INTERNAL_LINK(FormItemType.SELECT, FormItemCheckerType.INTERNAL_PAGES, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.CRM_TICKET_TRACKING, FormItemFilter.GALLERY)),
    LANGUAGES(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS)),
    LAST_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.CRM_TICKET_TRACKING)),
    LAST_REMODELED(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    LAND_IN_ACRES(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    LAND_IN_M2(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    LAND_IN_HECTARES(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    LAUNDRY_SERVICE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    LENGTH(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS)),
    LIST_OF_SKILLS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    LIVED_THERE_FROM(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.FAMILY)),
    LIVED_THERE_UNTIL(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.FAMILY)),
    LOCAL_ATTRACTIONS_AMENITIES(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    LOCAL_SCHOOLS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    LOOKING_FOR(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.CRM_TICKET_TRACKING)),
    LONG_TEXT(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.BASIC)),
    MAID_SERVICE(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    MAILING_ADDRESS(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    MAILING_LIST(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.CRM_TICKET_TRACKING)),
    MARITAL_STATUS(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    MARRIAGE_DATE(FormItemType.THREE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY), Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    MATERIALS_DESCRIPTION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    MEETING_ROOMS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    MESSAGE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.EVENTS)),
    MIDDLE_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    MINIMUM_DOWN_PAYMENT(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    MONTHLY_RATE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.ONLY_NUMBERS), Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    MOTHER_MAIDEN_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    MOTHER_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    MOVIE_GENRE_FULL(FormItemType.SELECT, Arrays.asList(FormItemFilter.FILM)),
    MOVIE_GENRE_FESTIVAL(FormItemType.SELECT, Arrays.asList(FormItemFilter.FILM)),
    MOVIE_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS)),
    MUSIC_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS)),
    MUNICIPAL_WATER_SUPPLY(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    MUNICIPAL_SEWAGE_DRAINS(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    MUNICIPAL_ELECTRICITY(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    MUNICIPAL_GAS(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.CRM_TICKET_TRACKING)),
    NAME_OF_BROTHER(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    NAME_OF_PROJECT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FILM, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.CRM_TICKET_TRACKING)),
    NAME_OF_SISTER(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY)),
    NEAREST_AIRPORT(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    NEEDS_WORK(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.REAL_ESTATE, FormItemFilter.CRM_TICKET_TRACKING)),
    NEWSLETTER(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    NICKNAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING, FormItemFilter.CRM_TICKET_TRACKING)),
    NIGHTLY_RATE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.ONLY_NUMBERS), Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    NUMBER_OF_ADULTS_IN_PARTY(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    NUMBER_OF_BATH_TUBS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    NUMBER_OF_CHILDREN_IN_PARTY(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    NUMBER_OF_COUNTRIES_VISITED(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    NUMBER_OF_KIDS_IN_HOUSEHOLD(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    NUMBER_OF_KNOWN_RESIDENTS(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    NUMBER_OF_MARRIAGES(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    NUMBER_OF_PEOPLE_IN_FAMILY(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    NUMBER_OF_ROOMS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    NUMERIC_FIELD_FLOAT(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.BASIC)),
    NUMERIC_FIELD_INTEGER(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_INTEGER_NUMBERS, Arrays.asList(FormItemFilter.BASIC)),
    OFFICES(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    OPTIONAL_EXTRAS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    ORDER_STATUS(FormItemType.SELECT, Arrays.asList(FormItemFilter.CRM_TICKET_TRACKING, FormItemFilter.PRODUCTS)),
    PACKAGING(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    PAID_AMOUNT(FormItemType.TEXT_INPUT_FIELD, FormItemCheckerType.ONLY_NUMBERS, Arrays.asList(FormItemFilter.PRODUCTS)),
    PDF_FILE_UPLOAD(FormItemType.FILE_UPLOAD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.FILM)),
    PERSONAL_BIO(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    PERSONAL_PHILOSOPHY(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS)),
    PETS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    PLANTS(FormItemType.SELECT, Arrays.asList(FormItemFilter.PLANTS)),
    POLITICS(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    POST_CODE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.PRODUCTS)),
    PHONE_LINE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    PRICE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.ONLY_NUMBERS), Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM, FormItemFilter.EVENTS)),
    PROPERTY_TAX(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    PRODUCT_TAX_RATE(FormItemType.SELECT, FormItemCheckerType.TAX_RATES, Arrays.asList(FormItemFilter.PRODUCTS)),
    PRODUCT_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS)),
    PRODUCT_ACCESS_GROUPS(FormItemType.ACCESS_GROUPS, Arrays.asList(FormItemFilter.PRODUCTS)),
    PURCHASE_DATE_AND_TIME(FormItemType.FIVE_PICK_LISTS, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM, Arrays.asList(FormItemFilter.PRODUCTS)),
    RADIOBUTTONS_YN(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.BASIC)),
    RADIOBUTTONS_YNM(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.BASIC)),
    RACE(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    READING_PREFS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    REASON_FOR_EMIGRATION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    REASON_FOR_MOVE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    REFERENCES(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM, FormItemFilter.PRODUCTS, FormItemFilter.PERSONAL_DATING, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS)),
    RELATIONSHIP_GOALS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING)),
    RELIGIONS(FormItemType.SELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    REGISTER(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.PERSONAL_DATING, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.FAMILY, FormItemFilter.FILM)),
    ROOM_SERVICE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL)),
    RSVP(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.EVENTS)),
    SCHOOL(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.REAL_ESTATE, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM)),
    SCREEN_NAME_NICKNAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC)),
    SECOND_EMAIL_ADDRESS(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    SECOND_TELEPHONE_NUMBER(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    SECOND_URL(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING, FormItemFilter.GALLERY)),
    SEO_KEYWORDS(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    SELLER_WILLING_TO_FINANCE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    SEPTIC_SYSTEM(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    SHIPPING(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS)),
    SKILL_SET(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.FILM)),
    SHORT_TEXT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC)),
    SKYPE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    SMOKING(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.VACATION_RENTAL)),
    SORT_ORDER(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.ONLY_NUMBERS), Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.PERSONAL_DATING, FormItemFilter.EVENTS, FormItemFilter.GALLERY)),
    SPORTS(FormItemType.MULITSELECT, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY)),
    SPOUSE_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.EVENTS)),
    SPECIES(FormItemType.SINGLE_CHOICE_OPTION_LIST, Arrays.asList(FormItemFilter.PLANTS)),
    SQUARE_FOOTAGE_OF_INTERIOR(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    SQUARE_FOOTAGE_OF_INTERIOR2(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    SQUARE_FOOTAGE_OF_LAND(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    STATE(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    START_DATE_AND_TIME(FormItemType.FIVE_PICK_LISTS, Arrays.asList(FormItemCheckerType.DATE_PICKER_VALIDITY_CHECK, FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM), Arrays.asList(FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    STATUS(FormItemType.SELECT, Arrays.asList(FormItemFilter.CRM_TICKET_TRACKING)),
    STATUS_NOTES(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.CRM_TICKET_TRACKING)),
    STORAGE_SPACE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    STYLE_OF_ARCHITECTURE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    SUBSCRIPTION_BILLING_PERIOD(FormItemType.SELECT, Arrays.asList(FormItemFilter.PRODUCTS)),
    SWIMMING_POOL(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE)),
    SYNOPSIS(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.FILM)),
    TAX_AMOUNT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM, FormItemFilter.EVENTS)),
    TELEPHONE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.FILM, FormItemFilter.REAL_ESTATE, FormItemFilter.PRODUCTS, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    TIME_DAYS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS)),
    TIME_MONTHS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT)),
    TIME_WEEKS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.FILM, FormItemFilter.REAL_ESTATE, FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT)),
    TIME_YEARS(FormItemType.SELECT, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.REAL_ESTATE, FormItemFilter.FILM, FormItemFilter.FAMILY, FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT)),
    TITLE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FAMILY, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),
    TRNSPORTATION(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT)),
    TYPE_OF_PROPERTY(FormItemType.SELECT, Arrays.asList(FormItemFilter.REAL_ESTATE)),
    URL(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.CRM_TICKET_TRACKING)),
    VIDEO_FILE_UPLOAD(FormItemType.FILE_UPLOAD, Arrays.asList(FormItemFilter.BASIC, FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.GALLERY, FormItemFilter.CRM_TICKET_TRACKING)),
    VOLUNTEER(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.EVENTS, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM)),
    WATER_FILTRATION(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL)),
    WEEKLY_RATE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.EVENTS)),
    WEIGHT(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.PRODUCTS)),
    WHEEL_CHAIR_ACCESSIBLE(FormItemType.CHECKBOX, Arrays.asList(FormItemFilter.VACATION_RENTAL, FormItemFilter.REAL_ESTATE, FormItemFilter.EVENTS)),
    WHERE_ARE_YOU_FROM(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.CRM_TICKET_TRACKING)),
    WIDTH(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.ONLY_NUMBERS), Arrays.asList(FormItemFilter.PRODUCTS, FormItemFilter.GALLERY)),
    WILLING_TO_COMMUTE_TO_WORK(FormItemType.SELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT)),
    WILLING_TO_TRAVEL(FormItemType.SELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT)),
    WILLING_TO_TRAVEL_INTERNATIONALLY(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT)),
    WORK_EXPERIENCE(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM)),
    WOULD_YOU_LIKE_TO_HAVE_CHILDREN(FormItemType.RADIOBUTTON, Arrays.asList(FormItemFilter.PERSONAL_DATING)),
    YEAR_BUILD_ESTABLISHED(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.EVENTS, FormItemFilter.PRODUCTS, FormItemFilter.JOBS_EMPLOYMENT)),
    YEARS_OF_EXPERIENCE(FormItemType.SELECT, Arrays.asList(FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.FILM, FormItemFilter.PERSONAL_DATING, FormItemFilter.PRODUCTS, FormItemFilter.EVENTS)),
    YOUR_PAGE_SITE_NAME_NOT_MANDATORY(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FILM, FormItemFilter.CREATE_A_SITE)),
    YOUTUBE_VIDEO(FormItemType.TEXT_AREA, Arrays.asList(FormItemFilter.PERSONAL_DATING, FormItemFilter.FAMILY, FormItemFilter.JOBS_EMPLOYMENT, FormItemFilter.PRODUCTS, FormItemFilter.REAL_ESTATE, FormItemFilter.VACATION_RENTAL, FormItemFilter.FILM, FormItemFilter.EVENTS, FormItemFilter.CRM_TICKET_TRACKING)),

    //Non-editable fields
    REGISTRATION_EMAIL(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER)),
    REGISTRATION_PASSWORD(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE, FormItemCheckerType.NOT_DISPLAY_ON_SHOW_RECORDS), Arrays.asList(FormItemFilter.NO_FILTER)),
    REGISTRATION_PASSWORD_RETYPE(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE, FormItemCheckerType.NOT_DISPLAY_ON_SHOW_RECORDS), Arrays.asList(FormItemFilter.NO_FILTER)),
    REGISTRATION_UNREMOVABLE_SCREEN_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER)),
    CONTACT_US_MESSAGE(FormItemType.TEXT_AREA, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER)),
    CONTACT_US_REGISTER_FOR_NEWSLETTER(FormItemType.CHECKBOX, Arrays.asList(FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER)),
    YOUR_PAGE_SITE_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED), Arrays.asList(FormItemFilter.NO_FILTER)),
    YOUR_OWN_DOMAIN_NAME(FormItemType.TEXT_INPUT_FIELD, Arrays.asList(FormItemFilter.FILM, FormItemFilter.CREATE_A_SITE)),
    PAYMENT_AREA(FormItemType.SPECIAL, Arrays.asList(FormItemCheckerType.MANDATORY, FormItemCheckerType.ALWAYS_REQUIRED, FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER)),
    LINKED(FormItemType.LINKED, Arrays.asList(FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE), Arrays.asList(FormItemFilter.NO_FILTER));

    public static FormItemName[] getNumbers() {
        final List<FormItemName> names = new ArrayList<FormItemName>();
        for (final FormItemName name : values()) {
            if (name.isNumber()) {
                names.add(name);
            }
        }
        return names.toArray(new FormItemName[names.size()]);
    }

    public static FormItemName[] getTexts() {
        final List<FormItemName> names = new ArrayList<FormItemName>();
        for (final FormItemName name : values()) {
            if (name.isText()) {
                names.add(name);
            }
        }
        return names.toArray(new FormItemName[names.size()]);
    }

    FormItemName(FormItemType type, List<FormItemFilter> formItemFilters) {
        this.type = type;
        this.formItemFilters = formItemFilters;
        this.checkers.add(FormItemCheckerType.NO_CHECKER);
    }

    FormItemName(FormItemType type, FormItemCheckerType checkerType, List<FormItemFilter> formItemFilters) {
        this.type = type;
        this.formItemFilters = formItemFilters;
        this.checkers.add(checkerType);
    }

    FormItemName(FormItemType type, List<FormItemCheckerType> checkers, List<FormItemFilter> formItemFilters) {
        this.type = type;
        this.formItemFilters = formItemFilters;
        this.checkers = checkers;
    }

    public FormItemType getType() {
        return type;
    }

    public List<FormItemFilter> getFormItemFilters() {
        return formItemFilters;
    }

    public List<FormItemCheckerType> getCheckers() {
        return checkers;
    }

    public boolean isPickList() {
        return this.getType() == FormItemType.SELECT || this.getType() == FormItemType.TWO_PICK_LISTS ||
                this.getType() == FormItemType.THREE_PICK_LISTS || this.getType() == FormItemType.FIVE_PICK_LISTS ||
                this.getType() == FormItemType.MULITSELECT;
    }

    public boolean isSingleOptionPickList() {
        return this.getType() == FormItemType.SELECT || this.getType() == FormItemType.MULITSELECT ||
                this.getType() == FormItemType.RADIOBUTTON;
    }

    public boolean isRange() {
        return this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY) ||
                this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM) ||
                this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_HH_MM) ||
                this.getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS);
    }

    public boolean isNumber() {
        return this.getCheckers().contains(FormItemCheckerType.ALPHA_NUMERIC) ||
                this.getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS);
    }

    public boolean isDate() {
        return this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY) ||
                this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM) ||
                this.getCheckers().contains(FormItemCheckerType.DATE_FORMAT_HH_MM);
    }

    public boolean isText() {
        return this.getType() == FormItemType.TEXT_AREA || this.getType() == FormItemType.TEXT_AREA_DOUBLE_SIZE ||
                this.getType() == FormItemType.TEXT_INPUT_FIELD || this == LINKED || this.getType() == FormItemType.RTF;
    }

    private final FormItemType type;
    private List<FormItemCheckerType> checkers = new ArrayList<FormItemCheckerType>();
    private final List<FormItemFilter> formItemFilters;
}
