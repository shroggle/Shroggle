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

import com.shroggle.logic.statistics.searchengines.SearchEngine.Attributes;

class MinorEngine extends Engine {
    public void incrementSearchServer(Attributes atr) {
        atr.incrementOther();
    }

    public String getName() {
        String nameLike = referrer.toLowerCase().replaceAll("^https?://", "");
        nameLike = nameLike.replaceAll("/.*$", "");
        if (nameLike.indexOf(".") != nameLike.lastIndexOf(".") &&
                !nameLike.matches("^(?:\\d+\\.){3,3}\\d+$")) {
            if (nameLike.charAt(nameLike.length() - 3) != '.')
                nameLike = nameLike.replaceAll("[^.]*\\.(?=[^.]*\\.)", "");
            else nameLike = nameLike.replaceAll("[^.]*\\.(?=[^.]*\\.[^.]*\\.)", "");
        }
        return "Minor Search Engines (" + nameLike + ")";
    }

}
