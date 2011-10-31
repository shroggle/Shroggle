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
public class Response {

    public Response(String responseValue, final String delimiter) {
        this.response = responseValue.split("\\" + delimiter);
    }

    public ResponseCodes getResponseCode() {
        return ResponseCodes.getByCode(Integer.valueOf(response[0]));
    }

    public String getDetailedReasonText() {
        return ResponseReasonCode.getResponseTextByCode(Integer.valueOf(response[2]));
    }

    public String getReasonText() {
        return response[3];
    }

    private final String[] response;
}
