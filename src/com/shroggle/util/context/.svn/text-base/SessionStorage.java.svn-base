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

package com.shroggle.util.context;

import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.gallery.NavigationSortedFormsInSession;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;

import javax.servlet.http.HttpSession;

/**
 * @author Stasuk Artem
 */
public interface SessionStorage {

    long getLastAccessedTime(AbstractService service);

    String getNoBotCode(Action actionBean, String notBotPreffix);

    String getNoBotCode(AbstractService dwrService, String notBotPreffix);

    void setNoBotCode(Action actionBean, String notBotPreffix, String noBotCode);

    StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForPage(Action action);

    StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForPage(AbstractService service);

    StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForVisitor(Action action);

    StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForVisitor(AbstractService service);

    void setStatisticsTimePeriodTypeInActionForPage(Action action, StatisticsTimePeriodType timePeriodType);

    void setRegistrationFormFilledInThisSession(Action action, Integer registrationFormId);

    Integer getRegistrationFormFilledInThisSession(Action action);

    void setRegistrationFormFilledInThisSessionInService(AbstractService service, Integer registrationFormId);

    Integer getRegistrationFormFilledInThisSessionInService(AbstractService service);

    void setStatisticsTimePeriodTypeInServiceForPage(
            AbstractService service, StatisticsTimePeriodType timePeriodType);

    void setStatisticsTimePeriodTypeInActionForVisitor(Action action, StatisticsTimePeriodType timePeriodType);

    void setStatisticsTimePeriodTypeInServiceForVisitor(
            AbstractService service, StatisticsTimePeriodType timePeriodType);

    NavigationSortedFormsInSession getSortedFilledFormsByFormId(HttpSession session, int formId);

    void setSortedFilledForms(HttpSession session, NavigationSortedFormsInSession sortedFilledFirms, int formId);

    Integer getCurrentlyViewedPageId(HttpSession session, int siteId);

    void putCurrentlyViewedPageId(HttpSession session, int siteId, int pageId);

    void setSelectedSiteInfoForDashboard(HttpSession session, DashboardSiteInfo siteInfo);

    Integer getSelectedSiteInfoHashForDashboard(Action action);
}
