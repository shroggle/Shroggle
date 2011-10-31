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
package com.shroggle.stresstest.application;

import java.util.*;

/**
 * @author Stasuk Artem
 */
class CommandDashboard extends CommandHttpGet {

    public CommandDashboard(final String server, final Context context) {
        super(server + "/account/dashboard.action", context);
    }

    @Override
    public void execute() throws Exception {
        super.execute();

        pages = new HashMap<String, List<String>>();
        final String pattern = "<!--dashboardsitespages:";
        final String response1 = getResponse().replaceAll(" ", "");
        final int start = response1.indexOf(pattern);
        final int finish = response1.indexOf("-->", start + 1);

        String sitesString = response1.substring(start, finish);
        sitesString = sitesString.replace(pattern, "");

        final String[] sitesStrings = sitesString.replaceFirst(";", "").split(";");
        for (final String siteString : sitesStrings) {
            final int finishSite = siteString.lastIndexOf(":");
            final String pagesString = siteString.substring(finishSite + 1);

            final List<String> pages = new ArrayList<String>();
            this.pages.put(siteString.substring(0, finishSite), pages);

            final String[] pagesStrings = pagesString.split(",");
            pages.addAll(Arrays.asList(pagesStrings));
        }
    }

    public Map<String, List<String>> getPages() {
        return pages;
    }

    private Map<String, List<String>> pages;

}