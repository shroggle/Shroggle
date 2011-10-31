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
package com.shroggle.logic.site.payment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.PaymentSettingsOwnerType;
import com.shroggle.entity.ChargeType;
import com.shroggle.entity.PaymentReason;
import com.shroggle.presentation.site.payment.PaypalPaymentInfoRequest;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaypalPaymentInfoRequestStorageTest {

    final PaypalPaymentInfoRequestStorage paypalPaymentInfoRequestStorage = new PaypalPaymentInfoRequestStorage();
    
    @Test
    public void test(){
        final PaypalPaymentInfoRequest request = new PaypalPaymentInfoRequest(1, PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, "", "", PaymentReason.SHROGGLE_MONTHLY_PAYMENT, 1);

        final int requsetId = paypalPaymentInfoRequestStorage.put(request);
        final PaypalPaymentInfoRequest savedRequset = paypalPaymentInfoRequestStorage.getPaymentForChildSiteRequest(requsetId);

        Assert.assertEquals(request, savedRequset);
    }

}
