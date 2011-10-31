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
package com.shroggle.logic.gallery;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.GalleryVideoRange;
import com.shroggle.entity.User;
import com.shroggle.logic.user.UserManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class VideoRangesManagerTest {

    /*--------------------------------------------Create Gallery Video Range------------------------------------------*/

    @Test
    public void testCreateGalleryVideoRange() {
        final VideoRangeEdit videoRangeEdit = new VideoRangeEdit();
        videoRangeEdit.setStart(0);
        videoRangeEdit.setFinish(10);
        videoRangeEdit.setTotal(10);
        videoRangeEdit.setGalleryId(158);
        videoRangeEdit.setFilledFormId(97);
        final GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(videoRangeEdit);
        Assert.assertNotNull(galleryVideoRange);
        Assert.assertNull(galleryVideoRange.getUser());
        Assert.assertEquals(0.0, galleryVideoRange.getStart(), 1);
        Assert.assertEquals(10.0, galleryVideoRange.getFinish(), 1);
        Assert.assertEquals(10.0, galleryVideoRange.getTotal(), 1);
        Assert.assertEquals(158, galleryVideoRange.getGalleryId());
        Assert.assertEquals(97, galleryVideoRange.getFilledFormId());
    }

    @Test
    public void testCreateGalleryVideoRange_withoutEdit() {
        GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(null);
        Assert.assertNull(galleryVideoRange);
    }

    @Test
    public void testCreateGalleryVideoRange_withoutStartMoreThanFinish() {
        VideoRangeEdit videoRangeEdit = new VideoRangeEdit();
        videoRangeEdit.setStart(10);
        videoRangeEdit.setFinish(1);
        videoRangeEdit.setTotal(10);
        GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(videoRangeEdit);
        Assert.assertNull(galleryVideoRange);
    }

    @Test
    public void testCreateGalleryVideoRange_withoutFinishLessThanOneTenth() {
        VideoRangeEdit videoRangeEdit = new VideoRangeEdit();
        videoRangeEdit.setStart(1);
        videoRangeEdit.setFinish(0.09f);
        videoRangeEdit.setTotal(10);
        GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(videoRangeEdit);
        Assert.assertNull(galleryVideoRange);
    }

    @Test
    public void testCreateGalleryVideoRange_withoutFinishMoreThanTotal() {
        VideoRangeEdit videoRangeEdit = new VideoRangeEdit();
        videoRangeEdit.setStart(1);
        videoRangeEdit.setFinish(11);
        videoRangeEdit.setTotal(10);
        GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(videoRangeEdit);
        Assert.assertNull(galleryVideoRange);
    }
    /*--------------------------------------------Create Gallery Video Range------------------------------------------*/


    /*------------------------------------------------Add Video Range-------------------------------------------------*/

    @Test
    public void testAdd() {
        final User user = TestUtil.createUser();
        final UserManager userManager = new UserManager(user);

        final GalleryVideoRange videoRange = new GalleryVideoRange();
        videoRange.setStart(0);
        videoRange.setFinish(10);
        videoRange.setTotal(10);
        videoRange.setGalleryId(158);
        videoRange.setFilledFormId(97);
        Assert.assertNull(videoRange.getUser());
        Assert.assertFalse(user.getVideoRanges().contains(videoRange));

        final VideoRangesManager manager = new VideoRangesManager(userManager);
        manager.add(videoRange);

        Assert.assertNotNull(videoRange.getUser());
        Assert.assertEquals(user, videoRange.getUser());
        Assert.assertTrue(user.getVideoRanges().contains(videoRange));
        Assert.assertEquals(0.0, videoRange.getStart(), 1);
        Assert.assertEquals(10.0, videoRange.getFinish(), 1);
        Assert.assertEquals(10.0, videoRange.getTotal(), 1);
        Assert.assertEquals(158, videoRange.getGalleryId());
        Assert.assertEquals(97, videoRange.getFilledFormId());
    }

    @Test
    public void testAdd_withoutVideoRange() {
        final User user = TestUtil.createUser();
        final UserManager userManager = new UserManager(user);

        final GalleryVideoRange videoRange = null;
        Assert.assertFalse(user.getVideoRanges().contains(videoRange));

        final VideoRangesManager manager = new VideoRangesManager(userManager);
        manager.add(videoRange);

        Assert.assertFalse(user.getVideoRanges().contains(videoRange));
    }

    @Test
    public void testAdd_withUserInVideoRange() {
        final User user = TestUtil.createUser();
        final UserManager userManager = new UserManager(user);

        final GalleryVideoRange videoRange = new GalleryVideoRange();
        videoRange.setUser(new User());
        Assert.assertFalse(user.getVideoRanges().contains(videoRange));

        final VideoRangesManager manager = new VideoRangesManager(userManager);
        manager.add(videoRange);

        Assert.assertFalse(user.getVideoRanges().contains(videoRange));
    }

    @Test
    public void testAdd_withVideoRangeInUser() {
        final User user = TestUtil.createUser();
        final UserManager userManager = new UserManager(user);

        final GalleryVideoRange videoRange = new GalleryVideoRange();
        videoRange.setUser(user);
        user.addVideoRange(videoRange);
        Assert.assertTrue(user.getVideoRanges().contains(videoRange));
        Assert.assertEquals(1, user.getVideoRanges().size());

        final VideoRangesManager manager = new VideoRangesManager(userManager);
        manager.add(videoRange);

        Assert.assertTrue(user.getVideoRanges().contains(videoRange));
        Assert.assertEquals(1, user.getVideoRanges().size());
    }
    /*------------------------------------------------Add Video Range-------------------------------------------------*/
}
