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


import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.render.RenderContext;

/**
 * @author Artem Stasuk
 */
public class ShowPageVersionActionRenderMock implements ShowPageVersionActionRender {

    public String execute(final RenderContext context, final PageManager pageManager) {
        this.pageManager = pageManager;
        return renderResult;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public void setRenderResult(String renderResult) {
        this.renderResult = renderResult;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    private PageManager pageManager;
    private String renderResult;
    private SiteShowOption siteShowOption;

}
