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

import java.util.Random;

/**
 * @author Artem Stasuk
 */
public class CustomTagInternalToHtmlXuy implements CustomTag {

    @Override
    public void execute(final HtmlTag tag, final SiteShowOption siteShowOption) {
        if (tag.getName().equals("h1")) {
            tag.setBody("Хуй " + WORDS[random.nextInt(WORDS.length)]);
        }
    }

    private final Random random = new Random();
    static final String[] WORDS = new String[]{"особенный", "прекрасный", "замечательный"};

}