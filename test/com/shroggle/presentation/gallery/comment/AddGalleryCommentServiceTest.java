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
package com.shroggle.presentation.gallery.comment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.gallery.comment.GalleryCommentInfo;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class AddGalleryCommentServiceTest {

    @Test
    public void executeWithUser() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftGallery gallery = TestUtil.createGallery(site);
        request.setGalleryId(gallery.getId());
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        request.setFilledFormId(filledForm.getFilledFormId());
        request.setSiteId(site.getSiteId());
        request.setText("ff");

        final GalleryCommentInfo galleryCommentInfo = service.execute(request);
        GalleryComment galleryComment = persistance.getGalleryCommentById(galleryCommentInfo.getCommentId());

        Assert.assertEquals("ff", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }


    @Test(expected = UserNotLoginedException.class)
    public void execute_withoutRights() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();
        request.setSiteId(site.getSiteId());

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void execute_withoutLoginedUser() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        Site site = TestUtil.createSite();
        request.setSiteId(site.getSiteId());

        service.execute(request);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final AddGalleryCommentRequest request = new AddGalleryCommentRequest();
    private final AddGalleryCommentService service = new AddGalleryCommentService();
    private final Context context = ServiceLocator.getContextStorage().get();

}