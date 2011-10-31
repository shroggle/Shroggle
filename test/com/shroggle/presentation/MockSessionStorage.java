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

package com.shroggle.presentation;

import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.presentation.gallery.NavigationSortedFormsInSession;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.context.SessionStorage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public class MockSessionStorage implements SessionStorage {

    public long getLastAccessedTime(AbstractService dwrService) {
        final long lastAccessTimeBefore = lastAccessedTime;
        lastAccessedTime = System.currentTimeMillis();
        return lastAccessTimeBefore;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public String getNoBotCode(Action actionBean, String notBotPreffix) {
        return noBotCodes.get(notBotPreffix);
    }

    public String getNoBotCode(AbstractService dwrService, String notBotPreffix) {
        return noBotCodes.get(notBotPreffix);
    }

    public void setNoBotCode(Action actionBean, String notBotPreffix, String noBotCode) {
        noBotCodes.put(notBotPreffix, noBotCode);
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForPage(final Action action) {
        return statisticsTimePeriodTypeForPage;
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForPage(final AbstractService service) {
        return statisticsTimePeriodTypeForPage;
    }

    public void setStatisticsTimePeriodTypeInActionForPage(
            final Action action, final StatisticsTimePeriodType timePeriodType) {
        this.statisticsTimePeriodTypeForPage = timePeriodType;
    }

    public void setRegistrationFormFilledInThisSession(final Action action, Integer registrationFormId) {
        this.registrationFormFilledInThisSession = registrationFormId;
    }

    public Integer getRegistrationFormFilledInThisSession(final Action action) {
        return registrationFormFilledInThisSession;
    }

    public void setRegistrationFormFilledInThisSessionInService(final AbstractService service, Integer registrationFormId) {
        this.registrationFormFilledInThisSession = registrationFormId;
    }

    public Integer getRegistrationFormFilledInThisSessionInService(final AbstractService service) {
        return registrationFormFilledInThisSession;
    }

    public void setStatisticsTimePeriodTypeInServiceForPage(
            final AbstractService service, final StatisticsTimePeriodType timePeriodType) {
        this.statisticsTimePeriodTypeForPage = timePeriodType;
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForVisitor(final Action action) {
        return statisticsTimePeriodTypeForVisitor;
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForVisitor(final AbstractService service) {
        return statisticsTimePeriodTypeForVisitor;
    }

    public void setStatisticsTimePeriodTypeInActionForVisitor(
            final Action action, final StatisticsTimePeriodType timePeriodType) {
        this.statisticsTimePeriodTypeForVisitor = timePeriodType;
    }

    public void setStatisticsTimePeriodTypeInServiceForVisitor(
            final AbstractService service, final StatisticsTimePeriodType timePeriodType) {
        this.statisticsTimePeriodTypeForVisitor = timePeriodType;
    }

    public NavigationSortedFormsInSession getSortedFilledFormsByFormId(HttpSession session, int formId) {
        return sortedFilledFirms;
    }

    public void setSortedFilledForms(HttpSession session, NavigationSortedFormsInSession sortedFilledFirms, int formId) {
        this.sortedFilledFirms = sortedFilledFirms;
    }

    public Integer getCurrentlyViewedPageId(HttpSession session, int siteId) {
        if (currentlyViewedPageIds == null) {
            return null;
        }
        return currentlyViewedPageIds.get(siteId);
    }

    public void putCurrentlyViewedPageId(HttpSession session, int siteId, int pageId) {
        Map<Integer, Integer> currenlyViewedPageIds = new HashMap<Integer, Integer>();
        if (currentlyViewedPageIds == null) {
            currenlyViewedPageIds.put(siteId, pageId);
            currentlyViewedPageIds = currenlyViewedPageIds;
        } else {
            currentlyViewedPageIds.put(siteId, pageId);
        }
    }

    @Override
    public void setSelectedSiteInfoForDashboard(HttpSession session, DashboardSiteInfo siteInfo) {
        selectedSiteIdForDashboard = (siteInfo != null ? siteInfo.hashCode() : null);
    }

    @Override
    public Integer getSelectedSiteInfoHashForDashboard(Action action) {
        return selectedSiteIdForDashboard;
    }

    public void clear() {
        noBotCodes.clear();
    }

    private final Map<String, String> noBotCodes = new HashMap<String, String>();
    private Integer loginUserId;
    private Integer deactivatedSiteId;
    private StatisticsTimePeriodType statisticsTimePeriodTypeForPage;
    private StatisticsTimePeriodType statisticsTimePeriodTypeForVisitor;
    private Map<Integer, Integer> currentlyViewedPageIds;
    private Long lastAccessedTime;
    private Integer registrationFormFilledInThisSession;
    private NavigationSortedFormsInSession sortedFilledFirms;
    private Integer selectedSiteIdForDashboard;
}
