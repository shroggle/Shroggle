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

package com.shroggle.presentation.site.payment;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.entity.User;
import com.shroggle.entity.PaymentLog;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class FilterPaymentLogsService extends ServiceWithExecutePage {

    @RemoteMethod
    public String filter(final PaymentMethod paymentMethod, final Integer siteId, final Integer childSiteSettingsId,
                         final boolean showForLogined) throws IOException, ServletException {
        final User user = new UsersManager().getLogined().getUser();

        List<PaymentLog> allLogs = new ArrayList<PaymentLog>();
        if (showForLogined) {
            allLogs.addAll(persistance.getPaymentLogsByUsersId(user.getUserId()));
        } else {
            allLogs.addAll(persistance.getAllPaymentLogs());
        }
        List<PaymentLog> filteredLogs = new ArrayList<PaymentLog>();
        for (PaymentLog paymentLog : allLogs) {
            if (paymentMethod != null && paymentLog.getPaymentMethod() != paymentMethod) {
                continue;
            }
            if (siteId != null && !siteId.equals(paymentLog.getSiteId())) {
                continue;
            }
            if (childSiteSettingsId != null && !childSiteSettingsId.equals(paymentLog.getChildSiteSettingsId())) {
                continue;
            }
            filteredLogs.add(paymentLog);
        }

        getContext().getHttpServletRequest().setAttribute("paymentLogs", filteredLogs);
        return executePage("/payment/paymentLogsList.jsp");
    }

    @RemoteMethod
    public String showAll(final Integer siteId, final Integer childSiteSettingsId, final boolean showForLogined) throws IOException, ServletException {
        return filter(null, siteId, childSiteSettingsId, showForLogined);
    }

    private Persistance persistance = ServiceLocator.getPersistance();
}
