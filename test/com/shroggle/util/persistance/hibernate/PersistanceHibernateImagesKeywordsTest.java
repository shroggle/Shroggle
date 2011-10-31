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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.Image;
import com.shroggle.entity.Site;
import com.shroggle.entity.ThemeId;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PersistanceHibernateImagesKeywordsTest extends HibernatePersistanceTestBase {

    @Test
    public void getImagesKeywordsByNotFound() {
        Assert.assertEquals(Collections.<String>emptyList(), persistance.getImagesKeywordsBySite(-1));
    }

    @Test
    public void getKeywordsImagesByNotFoundSite() {
        Assert.assertEquals(Collections.<Image>emptyList(), persistance.getImagesByKeywords(-1, Arrays.asList("a")));
    }

    @Test
    public void getKeywordImagesByNotFoundKeyword() {
        Assert.assertEquals(Collections.<Image>emptyList(), persistance.getImagesByKeywords(1, Arrays.asList("a")));
    }

    @Test
    public void getImagesKeywords() {
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        final Image image1 = new Image();
        image1.setKeywords("a,b,ff ff");
        image1.setThumbnailExtension("3");
        image1.setName("333");
        image1.setSiteId(site1.getSiteId());
        image1.setSourceExtension("3");
        persistance.putImage(image1);

        final Image image2 = new Image();
        image2.setSourceExtension("3");
        image2.setThumbnailExtension("3");
        image2.setName("f");
        image2.setSiteId(site1.getSiteId());
        image2.setKeywords("1,2,ff ff");
        persistance.putImage(image2);

        final Site site2 = new Site();
        site2.setThemeId(new ThemeId("a", "b"));
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title11");
        site2.setSubDomain("11");
        persistance.putSite(site2);

        final List<String> keywords = new ArrayList<String>() {{
            add("1");
            add("2");
            add("a");
            add("b");
            add("ff ff");
        }};
        Assert.assertEquals(keywords, persistance.getImagesKeywordsBySite(site1.getSiteId()));
        Assert.assertEquals(Collections.<String>emptyList(), persistance.getImagesKeywordsBySite(site2.getSiteId()));
    }

    @Test
    public void getKeywordImages() {
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        final Image image1 = new Image();
        image1.setKeywords("a,b,ff ff");
        image1.setThumbnailExtension("3");
        image1.setName("333");
        image1.setSiteId(site1.getSiteId());
        image1.setSourceExtension("3");
        persistance.putImage(image1);

        final Image image2 = new Image();
        image2.setSourceExtension("3");
        image2.setThumbnailExtension("3");
        image2.setName("f");
        image2.setSiteId(site1.getSiteId());
        image2.setKeywords("1,2,ff ff");
        persistance.putImage(image2);

        final Site site2 = new Site();
        site2.setThemeId(new ThemeId("a", "b"));
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title11");
        site2.setSubDomain("11");
        persistance.putSite(site2);

        final List<Image> images = new ArrayList<Image>() {{
            add(image1);
            add(image2);
        }};

        Assert.assertEquals(images, persistance.getImagesByKeywords(site1.getSiteId(), Arrays.asList("ff ff")));
        Assert.assertEquals(Collections.<Image>emptyList(), persistance.getImagesByKeywords(site1.getSiteId(), Arrays.asList("3")));
    }

}