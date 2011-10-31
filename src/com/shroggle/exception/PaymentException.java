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
 * Author: dmitry.solomadin
 */
public class PaymentException extends RuntimeException {

    public PaymentException(String msg) {
        super(msg);
    }

    public PaymentException(Throwable t){
        super(t);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
