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
 * @author Stasuk Artem
 */
public interface CssValue {

    String getValue();

    void setValue(String value);

    void setName(String name);

    String getSelector();

    void setSelector(final String selector);

    String getDescription();

    String getName();

    void setDescription(String description);

}