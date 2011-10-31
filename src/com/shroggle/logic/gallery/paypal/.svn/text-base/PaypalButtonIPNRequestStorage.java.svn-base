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
package com.shroggle.logic.gallery.paypal;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * @author dmitry.solomadin
 */
public class PaypalButtonIPNRequestStorage {  // todo. Check this. Items saved in ServiceLocator and never removed from it. Tolik/

    private Map<Integer, PaypalButtonIPNRequest> requests = new HashMap<Integer, PaypalButtonIPNRequest>();

    public int put(PaypalButtonIPNRequest request) {
        final int requestId = Math.abs(new Random().nextInt());

        requests.put(requestId, request);

        return requestId;
    }

    public PaypalButtonIPNRequest getRequest(final int requestId) {
        return requests.get(requestId);
    }

    public String getRequestsLog() {
        String log = "";

        for (Map.Entry<Integer, PaypalButtonIPNRequest> entry : requests.entrySet()) {
            final PaypalButtonIPNRequest request = entry.getValue();

            log += "Gallery Id: " + request.getGalleryId();
            log += "User Id: " + request.getUserId();
            log += "ProductNameFilledItemId:" + request.getProductNameFilledItemId();
            log += "PriceFilledItemId: " + request.getPriceFilledItemId();
            log += "GroupsFilledItemId: " + request.getGroupsFilledItemId();
            log += "\n<br>";
        }

        return log;
    }



    

}
