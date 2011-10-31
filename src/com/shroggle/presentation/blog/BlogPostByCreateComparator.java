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

package com.shroggle.presentation.blog;

import com.shroggle.entity.BlogPost;

import java.util.Comparator;

/**
 * @author Artem Stasuk
 */
final class BlogPostByCreateComparator implements Comparator<BlogPost> {

    public final static Comparator<BlogPost> INSTANCE = new BlogPostByCreateComparator();

    public int compare(final BlogPost o1, final BlogPost o2) {
        return o2.getCreationDate().compareTo(o1.getCreationDate());
    }

    private BlogPostByCreateComparator() {

    }

}
