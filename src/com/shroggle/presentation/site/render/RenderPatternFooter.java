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

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;

/**
 * @author Artem Stasuk
 */
public class RenderPatternFooter implements RenderPatternListener {

    public RenderPatternFooter(final Site site, final SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
        if (site == null) {
            throw new IllegalArgumentException("Can't create with null site!");
        }

        if (siteShowOption == null) {
            throw new IllegalArgumentException("Can't create with null siteShowOption!");
        }

        this.site = site;
    }

    @Override
    public String[] getPatterns() {
        return new String[]{"<!-- FOOTER -->", "</body>"};
    }

    @Override
    public String execute(final RenderContext context, final String pattern) {
        String footerHtml;

        final RenderFooterRegistration registration = new RenderFooterRegistration(site, siteShowOption);

        if (registration.get() != null) {
            footerHtml = HTML_TEMPLATE;

            final RenderFooterCustom custom = new RenderFooterCustom(site, registration.get());
            footerHtml = footerHtml.replace("{href}", custom.getHref());
            footerHtml = footerHtml.replace("{text}", custom.getText());
            footerHtml = footerHtml.replace("{src}", custom.getSrc());
        } else {
            footerHtml = DEFAULT_HTML;
            footerHtml = footerHtml.replace("{href}", "http://"+configStorage.get().getApplicationUrl());
        }

        return footerHtml + pattern;
    }

    private final Site site;
    private final SiteShowOption siteShowOption;
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

    private final static String HTML_TEMPLATE =
            "<div class=\"shroggleLogo\"><a href=\"{href}/\" target=\"_blank\" externalurl=\"true\"><img src=\"{src}\" " +
                    "border=\"0\" alt=\"{text}\"></a> <a href=\"{href}\">{text}</a></div>";
    private final static String DEFAULT_HTML_TEMPLATE =
            "<div class=\"shroggleLogo\"><p>Powered by <a href=\"{href}/\" target=\"_blank\"><img" +
                    " src=\"{src}\" alt=\"\"></a>. Website creation tools for professionals</p></div>";

    public final static String DEFAULT_SRC = "/images/wdfooter-logo-small.png";
    public final static String DEFAULT_HTML = DEFAULT_HTML_TEMPLATE.replace("{src}", DEFAULT_SRC);

}