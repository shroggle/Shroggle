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

package com.shroggle.presentation.site;

import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.statistics.StatisticsManager;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.StartAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

@UrlBinding("/account/siteDetails.action")
public class SiteDetailsAction extends Action {

    @DefaultHandler
    public Resolution show() {
        final User user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }

        if (!user.getEmail().equals(configStorage.get().getAdminLogin())) {
            return resolutionCreator.redirectToAction(StartAction.class);
        }

        site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site with Id=" + siteId);
        }

        if (site.getPages() == null || site.getPages().isEmpty()) {
            return resolutionCreator.forwardToUrl("/account/siteDetails.jsp");
        }

        referringUrls = new HashMap<Integer, Map<String, Integer>>();
        for (final Page page : site.getPages()) {
            Map<String, Integer> refURLs = new StatisticsManager().getRefURLsForPage(page, StatisticsTimePeriodType.ALL_TIME);
            if (refURLs != null && !refURLs.isEmpty()) {
                referringUrls.put(page.getPageId(), sortByValue(refURLs));
            }
        }

        return resolutionCreator.forwardToUrl("/account/siteDetails.jsp");
    }

    private class EntryComparator implements Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> map) {
        LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new EntryComparator());
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        Collections.reverse(list);
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Site getSite() {
        return site;
    }

    public Map<Integer, Map<String, Integer>> getReferringUrls() {
        return referringUrls;
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private int siteId;
    private Site site;
    private Map<Integer, Map<String, Integer>> referringUrls;

}
