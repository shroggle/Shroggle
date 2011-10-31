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
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class EditGalleryCommentServiceTest {

    @Test
    public void executeWithUser() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setGallery(gallery);
        galleryComment.setUserId(user.getUserId());
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        request.setCommentId(galleryComment.getCommentId());
        request.setText("ff");

        service.execute(request);

        Assert.assertEquals("ff", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void executeWithOtherUser() {
        TestUtil.createUserAndLogin();
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(false);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setGallery(gallery);
        galleryComment.setUserId(user.getUserId());
        galleryComment.setText("fa");
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        request.setCommentId(galleryComment.getCommentId());
        request.setText("ff");

        service.execute(request);

        Assert.assertEquals("fa", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void executeWithOtherUserAllow() {
        TestUtil.createUserAndLogin();
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(true);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setGallery(gallery);
        galleryComment.setUserId(user.getUserId());
        galleryComment.setText("fa");
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        request.setCommentId(galleryComment.getCommentId());
        request.setText("ff2");

        service.execute(request);

        Assert.assertEquals("ff2", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final EditGalleryCommentRequest request = new EditGalleryCommentRequest();
    private final EditGalleryCommentService service = new EditGalleryCommentService();

}