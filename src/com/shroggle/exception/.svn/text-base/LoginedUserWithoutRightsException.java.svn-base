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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;
import org.directwebremoting.convert.ExceptionConverter;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(
        converter = ExceptionConverter.class,
        params = {@Param(name = "include", value = "message, javaClassName")}
)
public class LoginedUserWithoutRightsException extends RuntimeException {
}
