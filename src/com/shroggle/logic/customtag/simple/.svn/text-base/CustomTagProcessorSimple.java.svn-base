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
package com.shroggle.logic.customtag.simple;

import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.customtag.CustomTag;
import com.shroggle.logic.customtag.CustomTagProcessor;
import com.shroggle.logic.customtag.HtmlTag;

/**
 * @author Artem Stasuk
 */
public class CustomTagProcessorSimple implements CustomTagProcessor {

    @Override
    public String execute(final CustomTag[] tags, final String string, final SiteShowOption siteShowOption) {
        if (string == null) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder(string);
        final CustomTagIteratorSimple iterator = new CustomTagIteratorSimple(stringBuilder);

        while (iterator.next()) {
            for (final CustomTag tag : tags) {
                final HtmlTag htmlTag = new HtmlTagSimple(iterator);
                tag.execute(htmlTag, siteShowOption);

                if (htmlTag.isChanged()) {
                    break;
                }
            }
        }

        return stringBuilder.toString();
    }

}