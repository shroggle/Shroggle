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

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmitry.solomadin
 */
public class RenderedPageHtmlProviderMock implements RenderedPageHtmlProvider {

    @Override
    public String provide(final Page page) {
        return htmlMap.get(page.getPageId());
    }

    public void addHtml(final Page page, final String html){
        htmlMap.put(page.getPageId(), html);
    }

    final Map<Integer, String> htmlMap = new HashMap<Integer, String>();

}
