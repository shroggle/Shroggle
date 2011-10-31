/*********************************************************************
 *                                                                   *
 * Copyright (c) 2002-2006 by Survey Software Services, Inc.         *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.statistics.searchengines.engines;

import com.shroggle.logic.statistics.searchengines.HttpRequestStream;
import com.shroggle.logic.statistics.searchengines.SearchEngine.Attributes;
import com.shroggle.logic.statistics.searchengines.SearchEngineUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLDecoder;
import java.util.Map;

public class Engine {
    private static final Log log = LogFactory.getLog(Engine.class);
    protected String referrer = "";
    private String query = "";

    public static Engine getEngine(final String ref) {
        Engine ret = new Engine();
        if (SearchEngineUtils.isStringEmpty(ref)) return ret;
        String referer = removeQuotes(ref);
        if (referer.indexOf(".google.") > -1) ret = new Google();
        else if (referer.indexOf("msn.com") > -1) ret = new MSN();
        else if (referer.indexOf("yahoo.com") > -1) ret = new Yahoo();
        else if (referer.indexOf("aol.com") > -1) ret = new AOL();
        else if (referer.indexOf("web.ask") > -1) ret = new Jeeves();
        else if (referer.indexOf("ask.co.uk") > -1) ret = new Jeeves();
        else if (referer.indexOf("mysearch.com") > -1) ret = new MySearch();
        else if (referer.indexOf("searchscout.com") > -1) ret = new SearchScout();
        else if (referer.indexOf("dogpile.com") > -1) ret = new Dogpile();
        else if (referer.indexOf("aport.ru") > -1) ret = new Aport();
        else if (referer.indexOf("a9.com") > -1) ret = new A9();
        else if (referer.indexOf("websearch.com") > -1 ||
                referer.indexOf("/search/web/") > -1) ret = new WebSearchOrExcite();
        else if (referer.indexOf("?") > -1 &&
                !SearchEngineUtils.isStringEmpty(getFilteredKeywords(referer)) &&
                referer.indexOf("excite.com") < 0) ret = new MinorEngine();
        else if (referer.indexOf("/search/") > -1 &&
                !SearchEngineUtils.isStringEmpty(getFilteredKeywords(referer))) ret = new MinorEngine();
        else if (referer.indexOf("metacrawler.com") > -1) ret = new MetaCrawler();
        else ret = new Engine();

        if (!SearchEngineUtils.isStringEmpty(ref)) ret.referrer = referer;
        else ret.referrer = "";
        ret.query = ret.getSearchWord(referer).toLowerCase();
        return ret;
    }

    public String getSearchWord(String ref) {
        return getFilteredKeywords(ref);
    }

    public static String getFilteredKeywords(String ref) {
        String parm = getParameterFromUrlString(ref, "query");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "qkw");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "qry");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "words");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "word");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "txtSearchTerm");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "Keywords");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "keywords");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "keyword");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "text");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;

        parm = getParameterFromUrlString(ref, "searchfor");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "MT");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "Terms");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "term");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "string");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "str");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "qs");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "st");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;

        parm = getParameterFromUrlString(ref, "w");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "q");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "t");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "s");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;
        parm = getParameterFromUrlString(ref, "p");
        if (!SearchEngineUtils.isStringEmpty(parm)) return parm;

        return "";
    }

    static String getParameterFromUrlString(String url, String param) {
        Map params = HttpRequestStream.readParametersWithSense(getParameterFromString(url));
        String[] values = (String[]) params.get(param);
        if (values != null && values.length > 1) return values[1];
        if (values != null && values.length > 0) return values[0];
        return "";
    }

    private static String getParameterFromString(String param) {
        if (param == null) return null;
        int ind = param.indexOf("?");
        if (ind <= 0) return param;
        return param.substring(ind + 1, param.length());
    }

    public void incrementSearchServer(Attributes atr) {
        atr.incrementOther();
    }

    public String getName() {
        return "Other";
    }

    public String getQuery() {
        return transform(query);
    }

    protected String transform(String filteredkeywords) {
        String result = "";
        try {
            result = URLDecoder.decode(filteredkeywords.trim().toLowerCase(), "utf-8");
        }
        catch (Exception ex) {
            log.error("", ex);
        }
        return removeQuotes(result);
    }

    private static String removeQuotes(String result) {
        if (!SearchEngineUtils.isStringEmpty(result) &&
                result.charAt(0) == '"' &&
                result.charAt(result.length() - 1) == '"') { //remove double quotas from filteredkeywords
            try {
                return result.substring(1, result.length() - 1);
            }
            catch (Exception ex) {
                log.error("", ex);
            }
        }
        return result;
    }

    public String getReferrer() {
        return referrer;
    }
}
