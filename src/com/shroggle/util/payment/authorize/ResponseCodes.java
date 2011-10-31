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
package com.shroggle.util.payment.authorize;

/**
 * @author Balakirev Anatoliy
 */
public enum ResponseCodes {

    APPROVED(1), DECLINE(2), ERROR(3), HELD_FOR_REVIEW(4);

    ResponseCodes(final int code) {
        this.code = code;
    }

    public static ResponseCodes getByCode(final int code) {
        for (ResponseCodes responseCodes : ResponseCodes.values()) {
            if (responseCodes.code == code) {
                return responseCodes;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    private final int code;
}
