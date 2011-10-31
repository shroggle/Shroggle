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
package com.shroggle.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Balakirev Anatoliy
 */

@RunWith(value = TestRunnerWithMockServices.class)
public class FileNameUtilTest {


    @Test
    public void getAvailableImageName() {
        final Site site = TestUtil.createSite();
        TestUtil.createImage(site.getSiteId(), "aaa.jpeg", "jpeg");
        TestUtil.createImage(site.getSiteId(), "aaa(2).jpeg", "jpeg");
        TestUtil.createImage(site.getSiteId(), "aaa(3).jpeg", "jpeg");

        TestUtil.createImage(site.getSiteId(), "bbb.jpeg", "jpeg");
        TestUtil.createImage(site.getSiteId(), "bbb(2).jpeg", "jpeg");

        TestUtil.createImage(site.getSiteId(), "ccc.jpeg", "jpeg");

        TestUtil.createImage(site.getSiteId(), "name.jpeg", "jpeg");
        TestUtil.createImage(site.getSiteId(), "name(2).jpeg", "jpeg");
        TestUtil.createImage(site.getSiteId(), "name(3).jpeg", "jpeg");

        Assert.assertEquals("aaa(4).jpeg", FileNameUtil.getAvailableImageName("aaa.jpeg", site.getSiteId()));
        Assert.assertEquals("bbb(3).jpeg", FileNameUtil.getAvailableImageName("bbb.jpeg", site.getSiteId()));
        Assert.assertEquals("ccc(2).jpeg", FileNameUtil.getAvailableImageName("ccc.jpeg", site.getSiteId()));
        Assert.assertEquals("name(4).jpeg", FileNameUtil.getAvailableImageName("name.jpeg", site.getSiteId()));
    }


    @Test
    public void getAvailableImageFileName() {
        final Site site = TestUtil.createSite();
        TestUtil.createImageFile(site.getSiteId(), "aaa.jpeg", "jpeg", ImageFileType.IMAGE);
        TestUtil.createImageFile(site.getSiteId(), "aaa(2).jpeg", "jpeg", ImageFileType.IMAGE);
        TestUtil.createImageFile(site.getSiteId(), "aaa(3).jpeg", "jpeg", ImageFileType.IMAGE);

        TestUtil.createImageFile(site.getSiteId(), "bbb.mp3", "mp3", ImageFileType.AUDIO);
        TestUtil.createImageFile(site.getSiteId(), "bbb(2).mp3", "mp3", ImageFileType.AUDIO);

        TestUtil.createImageFile(site.getSiteId(), "ccc.jpeg", "jpeg", ImageFileType.IMAGE);

        TestUtil.createImageFile(site.getSiteId(), "name.swf", "swf", ImageFileType.FLASH);
        TestUtil.createImageFile(site.getSiteId(), "name(2).swf", "swf", ImageFileType.FLASH);
        TestUtil.createImageFile(site.getSiteId(), "name(3).swf", "swf", ImageFileType.FLASH);


        TestUtil.createImageFile(site.getSiteId(), "name.pdf", "pdf", ImageFileType.PDF);
        TestUtil.createImageFile(site.getSiteId(), "name(2).pdf", "pdf", ImageFileType.PDF);
        TestUtil.createImageFile(site.getSiteId(), "name(3).pdf", "pdf", ImageFileType.PDF);
        TestUtil.createImageFile(site.getSiteId(), "name(4).pdf", "pdf", ImageFileType.PDF);
        TestUtil.createImageFile(site.getSiteId(), "name(5).pdf", "pdf", ImageFileType.PDF);
        TestUtil.createImageFile(site.getSiteId(), "name(6).pdf", "pdf", ImageFileType.PDF);

        Assert.assertEquals("aaa(4).jpeg", FileNameUtil.getAvailableImageFileName("aaa.jpeg", site.getSiteId(), ImageFileType.IMAGE));
        Assert.assertEquals("bbb(3).mp3", FileNameUtil.getAvailableImageFileName("bbb.mp3", site.getSiteId(), ImageFileType.AUDIO));
        Assert.assertEquals("ccc(2).jpeg", FileNameUtil.getAvailableImageFileName("ccc.jpeg", site.getSiteId(), ImageFileType.IMAGE));
        Assert.assertEquals("name(4).swf", FileNameUtil.getAvailableImageFileName("name.swf", site.getSiteId(), ImageFileType.FLASH));
        Assert.assertEquals("name(7).pdf", FileNameUtil.getAvailableImageFileName("name.pdf", site.getSiteId(), ImageFileType.PDF));
    }


    @Test
    public void getAvailableVideoName() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        TestUtil.createVideoForSite("aaa.avi", site.getSiteId());
        TestUtil.createVideoForSite("aaa(2).avi", site.getSiteId());
        TestUtil.createVideoForSite("aaa(3).avi", site.getSiteId());

        TestUtil.createVideoForSite("bbb.avi", site.getSiteId());
        TestUtil.createVideoForSite("bbb(2).avi", site.getSiteId());

        TestUtil.createVideoForSite("ccc.avi", site.getSiteId());

        TestUtil.createVideoForSite("name.avi", site.getSiteId());
        TestUtil.createVideoForSite("name(2).avi", site.getSiteId());
        TestUtil.createVideoForSite("name(3).avi", site.getSiteId());

        Assert.assertEquals("aaa(4).avi", FileNameUtil.getAvailableVideoName("aaa.avi", site.getSiteId()));
        Assert.assertEquals("bbb(3).avi", FileNameUtil.getAvailableVideoName("bbb.avi", site.getSiteId()));
        Assert.assertEquals("ccc(2).avi", FileNameUtil.getAvailableVideoName("ccc.avi", site.getSiteId()));
        Assert.assertEquals("name(4).avi", FileNameUtil.getAvailableVideoName("name.avi", site.getSiteId()));
    }

    @Test
    public void testCreateExtension() {
        Assert.assertEquals("b", FileNameUtil.createExtension("a.b"));
        Assert.assertEquals("jpeg", FileNameUtil.createExtension("1.jpeg"));
        Assert.assertEquals("jpeg", FileNameUtil.createExtension(".jpeg"));
        Assert.assertEquals("jpeg", FileNameUtil.createExtension("adfasdf.adsfasdf.asdf.asdfasd....jpeg"));
        Assert.assertEquals("jpeg", FileNameUtil.createExtension("adfasdf.adsfasdf.asdf.asdfasd....JPEG"));
        Assert.assertEquals(null, FileNameUtil.createExtension("."));
        Assert.assertEquals(null, FileNameUtil.createExtension("afdasdfasdf"));
        Assert.assertEquals(null, FileNameUtil.createExtension(""));
        Assert.assertEquals(null, FileNameUtil.createExtension(null));
    }
}
