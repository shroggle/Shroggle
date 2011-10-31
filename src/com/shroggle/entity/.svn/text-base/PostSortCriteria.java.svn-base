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

import com.shroggle.logic.blog.BlogPostManager;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

import java.util.Comparator;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum PostSortCriteria {

    ALPHABETICALLY_BY_AUTHOR(new Comparator<BlogPost>() {
        public int compare(final BlogPost blogPost1, final BlogPost blogPost2) {
            return new BlogPostManager(blogPost1).getPostAuthor().compareTo(new BlogPostManager(blogPost2).getPostAuthor());
        }
    }),
    CHRONOLOGICALLY_BY_POST_DATE_ASC(new Comparator<BlogPost>() {
        public int compare(final BlogPost blogPost1, final BlogPost blogPost2) {
            return blogPost1.getCreationDate().compareTo(blogPost2.getCreationDate());
        }
    }),
    CHRONOLOGICALLY_BY_POST_DATE_DESC(new Comparator<BlogPost>() {
        public int compare(final BlogPost blogPost1, final BlogPost blogPost2) {
            return blogPost2.getCreationDate().compareTo(blogPost1.getCreationDate());
        }
    }),
    ALPHABETICALLY_BY_POST_TITLE(new Comparator<BlogPost>() {
        public int compare(final BlogPost blogPost1, final BlogPost blogPost2) {
            return blogPost1.getPostTitle().compareTo(blogPost2.getPostTitle());
        }
    });

    PostSortCriteria(final Comparator<BlogPost> comparator) {
        this.comparator = comparator;
    }

    public Comparator<BlogPost> getComparator() {
        return comparator;
    }

    private final Comparator<BlogPost> comparator;
}