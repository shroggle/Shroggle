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
package com.shroggle.presentation.site.render;

import com.shroggle.entity.SEOHtmlCode;
import com.shroggle.entity.CodePlacement;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class RenderCustomHTMLCode implements Render {

    public RenderCustomHTMLCode(final PageManager pageManager) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderCustomHTMLCode with null pageManager.");
        }
        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final List<SEOHtmlCode> customHtmlCodeList = new ArrayList<SEOHtmlCode>();

        //Add custom HTML code from page.
        if (pageManager.getSeoSettings().getHtmlCodeList() != null) {
            customHtmlCodeList.addAll(pageManager.getSeoSettings().getHtmlCodeList());
        }

        //Add custom HTML code from site.
        if (pageManager.getPage().getSite().getSeoSettings().getHtmlCodeList() != null) {
            customHtmlCodeList.addAll(pageManager.getPage().getSite().getSeoSettings().getHtmlCodeList());
        }

        for (SEOHtmlCode seoHtmlCode : customHtmlCodeList) {
            if (!StringUtil.isNullOrEmpty(seoHtmlCode.getCode())) {
                if (seoHtmlCode.getCodePlacement() == CodePlacement.BEGINNING) {
                    final int bodyTagStartIndex = html.indexOf("<body");
                    final int bodyEndStartIndex = html.indexOf(">", bodyTagStartIndex);
                    if (bodyEndStartIndex > -1) {
                        html.insert(bodyEndStartIndex + 1, seoHtmlCode.getCode());
                    }
                } else if (seoHtmlCode.getCodePlacement() == CodePlacement.END) {
                    final int bodyClosingTagStartIndex = html.indexOf("</body>");
                    if (bodyClosingTagStartIndex > -1) {
                        html.insert(bodyClosingTagStartIndex, seoHtmlCode.getCode());
                    }
                } else {
                    throw new IllegalArgumentException("Unknown code placement: " + seoHtmlCode.getCodePlacement());
                }
            }
        }
    }

    private final PageManager pageManager;

}
