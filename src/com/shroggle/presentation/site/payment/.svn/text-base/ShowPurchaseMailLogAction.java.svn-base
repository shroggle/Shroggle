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

import com.shroggle.entity.PurchaseMailLog;
import com.shroggle.entity.User;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding(value = "/payment/showPurchaseMailLog.action")
public class ShowPurchaseMailLogAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() throws Exception {
        try {
            user = new UsersManager().getLogined().getUser();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        purchaseMailLogs = ServiceLocator.getPersistance().getAllPurchaseMailLogs();
        return resolutionCreator.forwardToUrl("/payment/purchaseMailLog.jsp");
    }

    public List<PurchaseMailLog> getPurchaseMailLogs() {
        Collections.sort(purchaseMailLogs, new Comparator<PurchaseMailLog>() {
            @Override
            public int compare(PurchaseMailLog o1, PurchaseMailLog o2) {
                final Date date1 = o1.getCreationDate() != null ? o1.getCreationDate() : new Date();
                final Date date2 = o2.getCreationDate() != null ? o2.getCreationDate() : new Date();
                return date1.compareTo(date2);
            }
        });
        return purchaseMailLogs;
    }


    @Override
    public User getLoginUser() {
        return user;
    }

    private User user;
    private List<PurchaseMailLog> purchaseMailLogs;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
