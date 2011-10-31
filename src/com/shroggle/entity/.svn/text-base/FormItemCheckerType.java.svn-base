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

/**
 * @author dmitry.solomadin
 */
public enum FormItemCheckerType {

    NO_CHECKER,
    ONLY_NUMBERS, // FLOAT. Permits to enter only numbers. Do not use this checker for date fields.
    ONLY_INTEGER_NUMBERS, // INTEGER. Permits to enter only numbers.
    DATE_PICKER_VALIDITY_CHECK, // Automatically changes values of date selects to proper (I.e. when user selects 'February' it adds only 28 days to day select).
    DATE_FORMAT_HH_MM, // Indicates that this form item is a date and it holds date in next format: hours:minutes
    DATE_FORMAT_DD_MM_YYYY, // Indicates that this form item is a date and it holds date in next format: day/month/year
    DATE_FORMAT_DD_MM_YYYY_HH_MM, // Indicates that this form item is a date and it holds date in next format: day/month/year hours:minutes
    ALPHA_NUMERIC,
    NOT_DISPLAY_ON_SHOW_RECORDS, // Fields with this checker are not displayed on manage form records page (on main page).
    MANDATORY, // Fields with this checker cannot be removed,
    ALWAYS_REQUIRED, // Fields with this checker are always required,
    NOT_DISPLAY_IN_INIT_FIELD_TABLE, // Fields with this checker are not displayed in init table (left table on configure form page).
    INTERNAL_PAGES, // Prefills select with the pages of the site on witch this select is displayed.
    TAX_RATES, // http://jira.web-deva.com/browse/SW-5776

}
