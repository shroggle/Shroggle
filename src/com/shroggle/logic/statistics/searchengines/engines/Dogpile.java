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

import com.shroggle.logic.statistics.searchengines.SearchEngineUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLDecoder;

class Dogpile extends MinorEngine {
    private static final Log log = LogFactory.getLog(Dogpile.class);

    public String getSearchWord(String ref) {
        if (SearchEngineUtils.isStringEmpty(ref)) return "";
        String result = ref.replaceFirst(".*info\\.dogpl.*/search/web/", "");
        result = result.replaceFirst("&(?!amp;).*", "");
        try {
            result = URLDecoder.decode(result, "utf-8");
        }
        catch (Exception ex) {
            log.error("", ex);
        }
        return result;
    }

    public String getName() {
        return "Dogpile";
    }
}
