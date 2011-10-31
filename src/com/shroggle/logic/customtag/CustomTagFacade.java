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
package com.shroggle.logic.customtag;

import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
public class CustomTagFacade {

    public String internalToHtml(final String string, final SiteShowOption siteShowOption) {
        return xToY(internalToHtmlTags, string, siteShowOption);
    }

    public String xToY(final CustomTag[] tags, final String string, SiteShowOption siteShowOption) {
        if (string == null) {
            return null;
        }

        return ServiceLocator.getCustomTagProcessor().execute(tags, string, siteShowOption);
    }

    private CustomTag[] internalToHtmlTags = new CustomTag[]{
            new CustomTagInternalToHtmlXuy(), new CustomTagInternalToHtmlPageLink()};

}
