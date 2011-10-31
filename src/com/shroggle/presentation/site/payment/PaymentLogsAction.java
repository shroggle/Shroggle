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

import com.shroggle.entity.PaymentLog;
import com.shroggle.entity.User;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/payment/paymentLogs.action")
public class PaymentLogsAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    @SynchronizeByLoginUser
    public Resolution show() {
        try {
            user = new UsersManager().getLogined().getUser();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        List<PaymentLog> paymentLogs = new ArrayList<PaymentLog>();
//        if (user.getEmail().equals("mstern@sidratech.com")) {
        paymentLogs.addAll(persistance.getAllPaymentLogs());
        /*} else {
            paymentLogs.addAll(persistance.getPaymentLogsByUserId(user.getUserId()));
        }*/

        getContext().getRequest().setAttribute("paymentLogs", paymentLogs);
        return resolutionCreator.forwardToUrl("/payment/paymentLogs.jsp");
    }

    public User getLoginUser() {
        return user;
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private User user;

}
