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
package com.shroggle.logic.childSites;

import com.shroggle.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class ExpirationDateLogic {

    public static boolean isNetworkMembershipExpired(Date currentDate, Date endDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date can`t be null!");
        }
        endDate = endDate != null ? DateUtil.roundDateTo(endDate, Calendar.DAY_OF_MONTH) : null;
        currentDate = DateUtil.roundDateTo(currentDate, Calendar.DAY_OF_MONTH);
        return endDate != null && endDate.before(currentDate);
    }

}
