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
package com.shroggle.exception;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.07.2009
 *         Time: 20:56:31
 */
public class ChildSiteSettingsNotFoundException extends RuntimeException {
    public ChildSiteSettingsNotFoundException() {
        super();
    }

    public ChildSiteSettingsNotFoundException(String message) {
        super(message);
    }
}