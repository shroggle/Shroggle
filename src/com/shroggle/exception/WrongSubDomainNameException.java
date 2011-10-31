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
 *         Date: 29.08.2009
 */
public class WrongSubDomainNameException extends RuntimeException {
    public WrongSubDomainNameException(String message) {
        super(message);
    }
}