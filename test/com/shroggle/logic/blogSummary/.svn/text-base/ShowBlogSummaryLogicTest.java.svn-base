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
package com.shroggle.logic.blogSummary;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.BlogPost;
import com.shroggle.entity.PostDisplayCriteria;
import com.shroggle.entity.PostSortCriteria;
import com.shroggle.logic.blog.BlogPostManager;
import com.shroggle.presentation.blogSummary.BlogSummaryDataForPreview;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

@RunWith(TestRunnerWithMockServices.class)
public class ShowBlogSummaryLogicTest {

    /*-------------------------------------------------Sort Blog Posts------------------------------------------------*/

    @Test
    public void testSortBlogPosts_ALPHABETICALLY_BY_POST_TITLE() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", "title5");
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", "title4");
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", "title3");
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", "title2");
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", "title1");
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals("title5", dataForPreview.getBlogPosts().get(0).getPostTitle());
        Assert.assertEquals("title4", dataForPreview.getBlogPosts().get(1).getPostTitle());
        Assert.assertEquals("title3", dataForPreview.getBlogPosts().get(2).getPostTitle());
        Assert.assertEquals("title2", dataForPreview.getBlogPosts().get(3).getPostTitle());
        Assert.assertEquals("title1", dataForPreview.getBlogPosts().get(4).getPostTitle());


        ShowBlogSummaryLogic.sortBlogPosts(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE, Arrays.asList(dataForPreview));


        Assert.assertEquals("title1", dataForPreview.getBlogPosts().get(0).getPostTitle());
        Assert.assertEquals("title2", dataForPreview.getBlogPosts().get(1).getPostTitle());
        Assert.assertEquals("title3", dataForPreview.getBlogPosts().get(2).getPostTitle());
        Assert.assertEquals("title4", dataForPreview.getBlogPosts().get(3).getPostTitle());
        Assert.assertEquals("title5", dataForPreview.getBlogPosts().get(4).getPostTitle());
    }

    @Test
    public void testSortBlogPosts_ALPHABETICALLY_BY_AUTHOR() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", TestUtil.createUser("firstName5", "lastName5"));
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", TestUtil.createUser("firstName4", "lastName4"));
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", TestUtil.createUser("firstName3", "lastName3"));
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", TestUtil.createUser("firstName2", "lastName2"));
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", TestUtil.createUser("firstName1", "lastName1"));
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals("lastName5 firstName5", new BlogPostManager(dataForPreview.getBlogPosts().get(0)).getPostAuthor());
        Assert.assertEquals("lastName4 firstName4", new BlogPostManager(dataForPreview.getBlogPosts().get(1)).getPostAuthor());
        Assert.assertEquals("lastName3 firstName3", new BlogPostManager(dataForPreview.getBlogPosts().get(2)).getPostAuthor());
        Assert.assertEquals("lastName2 firstName2", new BlogPostManager(dataForPreview.getBlogPosts().get(3)).getPostAuthor());
        Assert.assertEquals("lastName1 firstName1", new BlogPostManager(dataForPreview.getBlogPosts().get(4)).getPostAuthor());


        ShowBlogSummaryLogic.sortBlogPosts(PostSortCriteria.ALPHABETICALLY_BY_AUTHOR, Arrays.asList(dataForPreview));


        Assert.assertEquals("lastName1 firstName1", new BlogPostManager(dataForPreview.getBlogPosts().get(0)).getPostAuthor());
        Assert.assertEquals("lastName2 firstName2", new BlogPostManager(dataForPreview.getBlogPosts().get(1)).getPostAuthor());
        Assert.assertEquals("lastName3 firstName3", new BlogPostManager(dataForPreview.getBlogPosts().get(2)).getPostAuthor());
        Assert.assertEquals("lastName4 firstName4", new BlogPostManager(dataForPreview.getBlogPosts().get(3)).getPostAuthor());
        Assert.assertEquals("lastName5 firstName5", new BlogPostManager(dataForPreview.getBlogPosts().get(4)).getPostAuthor());
    }


    @Test
    public void testSortBlogPosts_CHRONOLOGICALLY_BY_POST_DATE_ASC() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        Date currentDate = new Date();

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", new Date(currentDate.getTime() + 500000));
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", new Date(currentDate.getTime() + 400000));
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", new Date(currentDate.getTime() + 300000));
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", new Date(currentDate.getTime() + 200000));
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", new Date(currentDate.getTime() + 100000));
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(4).getCreationDate());


        ShowBlogSummaryLogic.sortBlogPosts(PostSortCriteria.CHRONOLOGICALLY_BY_POST_DATE_ASC, Arrays.asList(dataForPreview));


        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(4).getCreationDate());
    }


    @Test
    public void testSortBlogPosts_CHRONOLOGICALLY_BY_POST_DATE_DESC() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        Date currentDate = new Date();

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", new Date(currentDate.getTime() + 500000));
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", new Date(currentDate.getTime() + 400000));
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", new Date(currentDate.getTime() + 300000));
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", new Date(currentDate.getTime() + 200000));
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", new Date(currentDate.getTime() + 100000));
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(4).getCreationDate());


        ShowBlogSummaryLogic.sortBlogPosts(PostSortCriteria.CHRONOLOGICALLY_BY_POST_DATE_DESC, Arrays.asList(dataForPreview));


        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(4).getCreationDate());
    }
    /*-------------------------------------------------Sort Blog Posts------------------------------------------------*/


    /*------------------------------------Select Blog Posts And Remove Superfluous------------------------------------*/

    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_COMMENTED() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", 1);
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", 2);
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", 3);
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", 4);
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", 5);
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(0).getPostedToWorkComments().size());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(1).getPostedToWorkComments().size());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostedToWorkComments().size());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(3).getPostedToWorkComments().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(4).getPostedToWorkComments().size());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_COMMENTED, 2, Arrays.asList(dataForPreview));

        Assert.assertEquals(2, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(0).getPostedToWorkComments().size());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(1).getPostedToWorkComments().size());
    }

    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_READ() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", 1);
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", 2);
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", 3);
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", 4);
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", 5);
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(1).getPostRead());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostRead());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(3).getPostRead());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(4).getPostRead());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_READ, 2, Arrays.asList(dataForPreview));

        Assert.assertEquals(2, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(1).getPostRead());
    }


    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_RECENT() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        Date currentDate = new Date();

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", new Date(currentDate.getTime() + 100000));
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", new Date(currentDate.getTime() + 200000));
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", new Date(currentDate.getTime() + 300000));
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", new Date(currentDate.getTime() + 400000));
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", new Date(currentDate.getTime() + 500000));
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(4).getCreationDate());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_RECENT, 2, Arrays.asList(dataForPreview));

        Assert.assertEquals(2, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(1).getCreationDate());
    }
    /*------------------------------------Select Blog Posts And Remove Superfluous------------------------------------*/


    /*----------Select Blog Posts And Remove Superfluous(includedPostsNumber more than posts number in blog)----------*/

    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_COMMENTED_includedPostsNumberInBlogSummaryEquals10() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", 1);
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", 2);
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", 3);
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", 4);
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", 5);
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(0).getPostedToWorkComments().size());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(1).getPostedToWorkComments().size());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostedToWorkComments().size());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(3).getPostedToWorkComments().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(4).getPostedToWorkComments().size());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_COMMENTED, 10, Arrays.asList(dataForPreview));

        Assert.assertEquals(5, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(0).getPostedToWorkComments().size());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(1).getPostedToWorkComments().size());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostedToWorkComments().size());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(3).getPostedToWorkComments().size());
        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(4).getPostedToWorkComments().size());
    }

    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_READ_includedPostsNumberInBlogSummaryEquals10() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", 1);
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", 2);
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", 3);
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", 4);
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", 5);
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(1).getPostRead());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostRead());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(3).getPostRead());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(4).getPostRead());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_READ, 10, Arrays.asList(dataForPreview));

        Assert.assertEquals(5, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(5, dataForPreview.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(4, dataForPreview.getBlogPosts().get(1).getPostRead());
        Assert.assertEquals(3, dataForPreview.getBlogPosts().get(2).getPostRead());
        Assert.assertEquals(2, dataForPreview.getBlogPosts().get(3).getPostRead());
        Assert.assertEquals(1, dataForPreview.getBlogPosts().get(4).getPostRead());
    }


    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_RECENT_includedPostsNumberInBlogSummaryEquals10() {
        final BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(1, "", null);

        Date currentDate = new Date();

        final BlogPost blogPost1 = TestUtil.createBlogPost("blog post text1", new Date(currentDate.getTime() + 100000));
        final BlogPost blogPost2 = TestUtil.createBlogPost("blog post text2", new Date(currentDate.getTime() + 200000));
        final BlogPost blogPost3 = TestUtil.createBlogPost("blog post text3", new Date(currentDate.getTime() + 300000));
        final BlogPost blogPost4 = TestUtil.createBlogPost("blog post text4", new Date(currentDate.getTime() + 400000));
        final BlogPost blogPost5 = TestUtil.createBlogPost("blog post text5", new Date(currentDate.getTime() + 500000));
        dataForPreview.setBlogPosts(Arrays.asList(blogPost1, blogPost2, blogPost3, blogPost4, blogPost5));

        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(4).getCreationDate());


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_RECENT, 10, Arrays.asList(dataForPreview));

        Assert.assertEquals(5, dataForPreview.getBlogPosts().size());
        Assert.assertEquals(new Date(currentDate.getTime() + 500000), dataForPreview.getBlogPosts().get(0).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 400000), dataForPreview.getBlogPosts().get(1).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 300000), dataForPreview.getBlogPosts().get(2).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 200000), dataForPreview.getBlogPosts().get(3).getCreationDate());
        Assert.assertEquals(new Date(currentDate.getTime() + 100000), dataForPreview.getBlogPosts().get(4).getCreationDate());
    }
    /*----------Select Blog Posts And Remove Superfluous(includedPostsNumber more than posts number in blog)----------*/


    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_READ_withFewBlogs() {
        /*-------------------------------------------------First data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview1 = new BlogSummaryDataForPreview(1, "", null);
        final BlogPost blogPost11 = TestUtil.createBlogPost("blog post text11", 1);
        final BlogPost blogPost12 = TestUtil.createBlogPost("blog post text12", 4);
        final BlogPost blogPost13 = TestUtil.createBlogPost("blog post text13", 7);
        final BlogPost blogPost14 = TestUtil.createBlogPost("blog post text14", 10);
        final BlogPost blogPost15 = TestUtil.createBlogPost("blog post text15", 13);
        dataForPreview1.setBlogPosts(Arrays.asList(blogPost11, blogPost12, blogPost13, blogPost14, blogPost15));
        /*-------------------------------------------------First data-------------------------------------------------*/

        /*------------------------------------------------Second data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview2 = new BlogSummaryDataForPreview(2, "", null);
        final BlogPost blogPost21 = TestUtil.createBlogPost("blog post text21", 2);
        final BlogPost blogPost22 = TestUtil.createBlogPost("blog post text22", 5);
        final BlogPost blogPost23 = TestUtil.createBlogPost("blog post text23", 8);
        final BlogPost blogPost24 = TestUtil.createBlogPost("blog post text24", 11);
        final BlogPost blogPost25 = TestUtil.createBlogPost("blog post text25", 14);
        dataForPreview2.setBlogPosts(Arrays.asList(blogPost21, blogPost22, blogPost23, blogPost24, blogPost25));
        /*------------------------------------------------Second data-------------------------------------------------*/

        /*-------------------------------------------------Third data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview3 = new BlogSummaryDataForPreview(3, "", null);
        final BlogPost blogPost31 = TestUtil.createBlogPost("blog post text31", 3);
        final BlogPost blogPost32 = TestUtil.createBlogPost("blog post text32", 6);
        final BlogPost blogPost33 = TestUtil.createBlogPost("blog post text33", 9);
        final BlogPost blogPost34 = TestUtil.createBlogPost("blog post text34", 12);
        final BlogPost blogPost35 = TestUtil.createBlogPost("blog post text35", 15);
        dataForPreview3.setBlogPosts(Arrays.asList(blogPost31, blogPost32, blogPost33, blogPost34, blogPost35));
        /*-------------------------------------------------Third data-------------------------------------------------*/

        final List<BlogSummaryDataForPreview> dataForPreview = new ArrayList<BlogSummaryDataForPreview>(Arrays.asList(dataForPreview1, dataForPreview2, dataForPreview3));


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_READ, 8, dataForPreview);


        Assert.assertEquals(3, dataForPreview.size());
        Assert.assertEquals(dataForPreview1, dataForPreview.get(0));
        Assert.assertEquals(dataForPreview2, dataForPreview.get(1));
        Assert.assertEquals(dataForPreview3, dataForPreview.get(2));

        Assert.assertEquals(3, dataForPreview3.getBlogPosts().size());
        Assert.assertEquals(15, dataForPreview3.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(12, dataForPreview3.getBlogPosts().get(1).getPostRead());
        Assert.assertEquals(9, dataForPreview3.getBlogPosts().get(2).getPostRead());

        Assert.assertEquals(3, dataForPreview2.getBlogPosts().size());
        Assert.assertEquals(14, dataForPreview2.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(11, dataForPreview2.getBlogPosts().get(1).getPostRead());
        Assert.assertEquals(8, dataForPreview2.getBlogPosts().get(2).getPostRead());

        Assert.assertEquals(2, dataForPreview1.getBlogPosts().size());
        Assert.assertEquals(13, dataForPreview1.getBlogPosts().get(0).getPostRead());
        Assert.assertEquals(10, dataForPreview1.getBlogPosts().get(1).getPostRead());
    }

    @Test
    public void testSelectBlogPostsAndRemoveSuperfluous_MOST_READ_withThreeBlogsAndOnePost() {
        /*-------------------------------------------------First data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview1 = new BlogSummaryDataForPreview(1, "", null);
        final BlogPost blogPost11 = TestUtil.createBlogPost("blog post text11", 1);
        final BlogPost blogPost12 = TestUtil.createBlogPost("blog post text12", 4);
        final BlogPost blogPost13 = TestUtil.createBlogPost("blog post text13", 7);
        final BlogPost blogPost14 = TestUtil.createBlogPost("blog post text14", 10);
        final BlogPost blogPost15 = TestUtil.createBlogPost("blog post text15", 13);
        dataForPreview1.setBlogPosts(Arrays.asList(blogPost11, blogPost12, blogPost13, blogPost14, blogPost15));
        /*-------------------------------------------------First data-------------------------------------------------*/

        /*------------------------------------------------Second data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview2 = new BlogSummaryDataForPreview(2, "", null);
        final BlogPost blogPost21 = TestUtil.createBlogPost("blog post text21", 2);
        final BlogPost blogPost22 = TestUtil.createBlogPost("blog post text22", 5);
        final BlogPost blogPost23 = TestUtil.createBlogPost("blog post text23", 8);
        final BlogPost blogPost24 = TestUtil.createBlogPost("blog post text24", 11);
        final BlogPost blogPost25 = TestUtil.createBlogPost("blog post text25", 14);
        dataForPreview2.setBlogPosts(Arrays.asList(blogPost21, blogPost22, blogPost23, blogPost24, blogPost25));
        /*------------------------------------------------Second data-------------------------------------------------*/

        /*-------------------------------------------------Third data-------------------------------------------------*/
        final BlogSummaryDataForPreview dataForPreview3 = new BlogSummaryDataForPreview(3, "", null);
        final BlogPost blogPost31 = TestUtil.createBlogPost("blog post text31", 3);
        final BlogPost blogPost32 = TestUtil.createBlogPost("blog post text32", 6);
        final BlogPost blogPost33 = TestUtil.createBlogPost("blog post text33", 9);
        final BlogPost blogPost34 = TestUtil.createBlogPost("blog post text34", 12);
        final BlogPost blogPost35 = TestUtil.createBlogPost("blog post text35", 15);
        dataForPreview3.setBlogPosts(Arrays.asList(blogPost31, blogPost32, blogPost33, blogPost34, blogPost35));
        /*-------------------------------------------------Third data-------------------------------------------------*/

        final List<BlogSummaryDataForPreview> dataForPreview = new ArrayList<BlogSummaryDataForPreview>(Arrays.asList(dataForPreview1, dataForPreview2, dataForPreview3));


        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(PostDisplayCriteria.MOST_READ, 1, dataForPreview);


        Assert.assertEquals(1, dataForPreview.size());
        Assert.assertEquals(dataForPreview3, dataForPreview.get(0));
        Assert.assertEquals(1, dataForPreview3.getBlogPosts().size());
        Assert.assertEquals(15, dataForPreview3.getBlogPosts().get(0).getPostRead());
    }
}
