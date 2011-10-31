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
package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * author: dmitry.solomadin
 * <p/>
 * INSIDE_APP - if viewing widget from context, view/edit forums/blogs link's or from dashboard.
 * OUTSIDE_APP - if viewing site form outer URL like http://site.shroggle.com/page1
 * ON_USER_PAGES - if viewing site from preview/view live links.
 */

@DataTransferObject(converter = EnumConverter.class)
public enum SiteShowOption {

    INSIDE_APP(false), OUTSIDE_APP(true), ON_USER_PAGES(false);

    SiteShowOption(boolean work) {
        this.work = work;
    }

    public boolean isWork() {
        return work;
    }

    public boolean isDraft() {
        return !work;
    }

    private final boolean work;

    public static SiteShowOption getDraftOption() {
        for (SiteShowOption siteShowOption : SiteShowOption.values()) {
            if (siteShowOption.isDraft()) {
                return siteShowOption;
            }
        }
        throw new AssertionError("Unreachable statement!");
    }

    public static SiteShowOption getWorkOption() {
        for (SiteShowOption siteShowOption : SiteShowOption.values()) {
            if (siteShowOption.isWork()) {
                return siteShowOption;
            }
        }
        throw new AssertionError("Unreachable statement!");
    }

}
