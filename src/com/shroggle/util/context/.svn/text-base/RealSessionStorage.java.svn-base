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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class RealSessionStorage implements SessionStorage {

    public long getLastAccessedTime(AbstractService dwrService) {
        return dwrService.getContext().getSession().getLastAccessedTime();
    }

    public String getNoBotCode(Action actionBean, String notBotPreffix) {
        return (String) actionBean.getContext().getRequest().getSession().getAttribute("noBotCode" + notBotPreffix);
    }

    public String getNoBotCode(AbstractService dwrService, String notBotPreffix) {
        return (String) dwrService.getContext().getHttpServletRequest().getSession().getAttribute("noBotCode" + notBotPreffix);
    }

    public void setNoBotCode(Action actionBean, String notBotPreffix, String noBotCode) {
        actionBean.getContext().getRequest().getSession().setAttribute("noBotCode" + notBotPreffix, noBotCode);
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForPage(final Action action) {
        return (StatisticsTimePeriodType) action.getContext().getRequest().getSession().getAttribute("pages_sort_type");
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForPage(final AbstractService service) {
        return (StatisticsTimePeriodType) service.getContext().getHttpServletRequest().getSession().getAttribute("pages_sort_type");
    }

    public void setStatisticsTimePeriodTypeInActionForPage(
            final Action action, final StatisticsTimePeriodType timePeriodType) {
        action.getContext().getRequest().getSession().setAttribute("pages_sort_type", timePeriodType);
    }

    public void setRegistrationFormFilledInThisSession(final Action action, Integer registrationFormId) {
        action.getContext().getRequest().getSession().setAttribute("registration_form_filled_in_this_session", registrationFormId);
    }

    public Integer getRegistrationFormFilledInThisSession(final Action action) {
        return (Integer) action.getContext().getRequest().getSession().getAttribute("registration_form_filled_in_this_session");
    }

    public void setRegistrationFormFilledInThisSessionInService(final AbstractService service, Integer registrationFormId) {
        service.getContext().getHttpServletRequest().getSession().setAttribute("registration_form_filled_in_this_session", registrationFormId);
    }

    public Integer getRegistrationFormFilledInThisSessionInService(final AbstractService service) {
        return (Integer) service.getContext().getHttpServletRequest().getSession().getAttribute("registration_form_filled_in_this_session");
    }

    public void setStatisticsTimePeriodTypeInServiceForPage(
            final AbstractService service, final StatisticsTimePeriodType timePeriodType) {
        service.getContext().getHttpServletRequest().getSession().setAttribute("pages_sort_type", timePeriodType);
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInActionForVisitor(final Action action) {
        return (StatisticsTimePeriodType) action.getContext().getRequest().getSession().getAttribute("visitor_sort_type");
    }

    public StatisticsTimePeriodType getStatisticsTimePeriodTypeInSeviceForVisitor(final AbstractService service) {
        return (StatisticsTimePeriodType) service.getContext().getHttpServletRequest().getSession().getAttribute("visitor_sort_type");
    }

    public void setStatisticsTimePeriodTypeInActionForVisitor(
            final Action action, final StatisticsTimePeriodType timePeriodType) {
        action.getContext().getRequest().getSession().setAttribute("visitor_sort_type", timePeriodType);
    }

    public void setStatisticsTimePeriodTypeInServiceForVisitor(
            final AbstractService service, final StatisticsTimePeriodType timePeriodType) {
        service.getContext().getHttpServletRequest().getSession().setAttribute("visitor_sort_type", timePeriodType);
    }

    public NavigationSortedFormsInSession getSortedFilledFormsByFormId(HttpSession session, int formId) {
        if (session != null) {
            return (NavigationSortedFormsInSession) session.getAttribute("sortedFilledFirms" + formId);
        } else {
            return null;
        }
    }

    public void setSortedFilledForms(HttpSession session, NavigationSortedFormsInSession sortedFilledFirms, int formId) {
        if (session != null) {
            session.setAttribute("sortedFilledFirms" + formId, sortedFilledFirms);
        }
    }

    public Integer getCurrentlyViewedPageId(HttpSession session, int siteId) {
        if (session.getAttribute("currenlyViewedPageIds") == null) {
            return null;
        }
        Map<Integer, Integer> currenlyViewedPageIds = (Map<Integer, Integer>) session.getAttribute("currenlyViewedPageIds");

        return currenlyViewedPageIds.get(siteId);
    }

    public void putCurrentlyViewedPageId(HttpSession session, int siteId, int pageId) {
        Map<Integer, Integer> currenlyViewedPageIds = new HashMap<Integer, Integer>();
        if (session.getAttribute("currenlyViewedPageIds") == null) {
            currenlyViewedPageIds.put(siteId, pageId);
            session.setAttribute("currenlyViewedPageIds", currenlyViewedPageIds);
        } else {
            currenlyViewedPageIds = (Map<Integer, Integer>) session.getAttribute("currenlyViewedPageIds");
            currenlyViewedPageIds.put(siteId, pageId);
        }
    }

    @Override
    public void setSelectedSiteInfoForDashboard(HttpSession session, DashboardSiteInfo siteInfo) {
        session.setAttribute("dashboardsSiteInfoHashCode", (siteInfo != null ? siteInfo.hashCode() : null));
    }

    @Override
    public Integer getSelectedSiteInfoHashForDashboard(Action action) {
        return (Integer) action.getContext().getRequest().getSession().getAttribute("dashboardsSiteInfoHashCode");
    }

}
