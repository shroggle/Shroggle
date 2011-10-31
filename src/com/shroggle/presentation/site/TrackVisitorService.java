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
import com.shroggle.entity.PageVisitor;
import com.shroggle.entity.Visit;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.VisitNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.statistics.searchengines.engines.Engine;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.*;

@RemoteProxy
public class TrackVisitorService extends AbstractService {

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

    @RemoteMethod
    public String track(final TrackVisitorRequest trackVisitorRequest, final int pageId)
            throws IOException, ServletException {
        final PageManager pageManager = new PageManager(persistance.getPage(pageId));

        final Page page = pageManager.getPage();

        if (page == null) {
            throw new PageNotFoundException("Cannot find page for page version with Id=" + pageId);
        }

        //Looking if we user have visitor cookies(Someone from this computer has visited this site).
        final Integer pageVisitorId = ActionUtil.getPageVisitorId
                (getContext().getHttpServletRequest().getCookies());

        final String searchTerm = Engine.getFilteredKeywords(trackVisitorRequest.getReferrerUrl());

        if (trackVisitorRequest.getReferrerUrl().isEmpty()) {
            trackVisitorRequest.setReferrerUrl("direct traffic");
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                //If we cannot find our cookies or we did not find page visitor by cookie value(our cookie is corrupted)
                // then we will create a new page visitor and set up new cookies.
                if (pageVisitorId == null || persistance.getPageVisitorById(pageVisitorId) == null) {
                    PageVisitor pageVisitor = new PageVisitor();
                    persistance.putPageVisitor(pageVisitor);
                    //If user haven't visited our site then he surely haven't visited this page.
                    Visit visit = new Visit();
                    visit.setVisitedPage(page);
                    visit.setPageVisitor(pageVisitor);
                    visit.setVisitCreationDate(new Date());
                    visit.setVisitCount(1);
                    persistance.putVisit(visit);
                    visit.addReferrerURL(trackVisitorRequest.getReferrerUrl());
                    if (!searchTerm.isEmpty()) {
                        visit.addReferrerSearchTerm(searchTerm);
                    }

                    pageVisitor.addVisit(visit);
                    page.addVisit(visit);

                    getContext().getHttpServletResponse().addCookie
                            (new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId()));
                } else {
                    PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);

                    if (pageVisitor != null) {
                        //Trying to find visit for this page and update it.
                        List<Visit> visits = pageVisitor.getVisits();
                        List<Visit> prevVisits = new ArrayList<Visit>();
                        for (Visit visit : visits) {
                            if (visit.getVisitedPage().getPageId() == page.getPageId()) {
                                prevVisits.add(visit);
                            }
                        }

                        if (!prevVisits.isEmpty()) {
                            Visit newestVisit = prevVisits.get(0);
                            for (Visit visit : prevVisits) {
                                if (visit.getVisitCreationDate().after(newestVisit.getVisitCreationDate())) {
                                    newestVisit = visit;
                                }
                            }

                            Calendar c1 = new GregorianCalendar();
                            c1.setTime(newestVisit.getVisitCreationDate());
                            Calendar c2 = new GregorianCalendar();
                            c2.setTime(new Date());

                            //If newest visit for this user is older that one month then we will create a new one for him
                            if (c2.get(Calendar.MONTH) > c1.get(Calendar.MONTH) || c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR)) {
                                Visit visit = new Visit();
                                visit.setVisitedPage(page);
                                visit.setVisitCreationDate(new Date());
                                visit.setVisitCount(1);
                                visit.setPageVisitor(pageVisitor);
                                persistance.putVisit(visit);
                                visit.addReferrerURL(trackVisitorRequest.getReferrerUrl());
                                if (!searchTerm.isEmpty()) {
                                    visit.addReferrerSearchTerm(searchTerm);
                                }

                                page.addVisit(visit);
                                pageVisitor.addVisit(visit);
                            } else {
                                //In other case we just updating newest visit
                                newestVisit.setVisitCount(newestVisit.getVisitCount() + 1);
                                newestVisit.addReferrerURL(trackVisitorRequest.getReferrerUrl());
                                if (!searchTerm.isEmpty()) {
                                    newestVisit.addReferrerSearchTerm(searchTerm);
                                }
                            }
                        } else {
                            Visit visit = new Visit();
                            visit.setVisitedPage(page);
                            visit.setVisitCreationDate(new Date());
                            visit.setVisitCount(1);
                            visit.setPageVisitor(pageVisitor);
                            persistance.putVisit(visit);
                            visit.addReferrerURL(trackVisitorRequest.getReferrerUrl());
                            if (!searchTerm.isEmpty()) {
                                visit.addReferrerSearchTerm(searchTerm);
                            }

                            page.addVisit(visit);
                            pageVisitor.addVisit(visit);
                        }
                    }
                }
            }
        });

        return "ok";
    }

    @RemoteMethod
    public String updateTime(final int pageId,
                             final long time) throws IOException, ServletException {
        final PageManager pageManager = new PageManager(persistance.getPage(pageId));

        final Page page = pageManager.getPage();

        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id = " + pageId);
        }

        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());

        if (pageVisitorId != null) {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    Visit visit = persistance.getVisitByPageIdAndUserId(page.getPageId(), pageVisitorId);

                    if (visit == null) {
                        throw new VisitNotFoundException
                                ("Cannot find visit by pair pageId=" + page.getPageId() + " and pageVisitorId=" + pageVisitorId);
                    }

                    visit.setOverallTimeOfVisit(visit.getOverallTimeOfVisit() + time);
                }
            });
        }

        return "ok";
    }
}
