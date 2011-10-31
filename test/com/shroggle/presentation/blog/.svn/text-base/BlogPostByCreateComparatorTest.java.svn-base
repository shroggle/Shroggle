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
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
public class BlogPostByCreateComparatorTest {

    @Test
    public void compare() {
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() / 2));
        BlogPost blogPost2 = new BlogPost();

        Assert.assertEquals(1, BlogPostByCreateComparator.INSTANCE.compare(blogPost1, blogPost2));
    }

    @Test
    public void compareEquals() {
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setCreationDate(new Date(12));
        BlogPost blogPost2 = new BlogPost();
        blogPost2.setCreationDate(new Date(12));

        Assert.assertEquals(0, BlogPostByCreateComparator.INSTANCE.compare(blogPost1, blogPost2));
    }

}
