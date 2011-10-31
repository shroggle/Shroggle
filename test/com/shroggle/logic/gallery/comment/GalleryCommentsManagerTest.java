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
package com.shroggle.logic.gallery.comment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryCommentsManagerTest {

    @Test
    public void getEmpty() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        Assert.assertEquals(0, new GalleryCommentsManager().get(
                filledForm.getFilledFormId(), gallery.getId()).size());
    }

    @Test
    public void add() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment = new GalleryCommentsManager().add(
                filledForm.getFilledFormId(), gallery.getId(), "fff");
        Assert.assertEquals("fff", galleryComment.getText());
        Assert.assertNull(galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void addWithNullText() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment = new GalleryCommentsManager().add(
                filledForm.getFilledFormId(), gallery.getId(), null);
        Assert.assertEquals("", galleryComment.getText());
        Assert.assertNull(galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void edit() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setUserId(user.getUserId());
        galleryComment.setGallery(gallery);
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        new GalleryCommentsManager().edit(galleryComment.getCommentId(), "fff");
        Assert.assertEquals("fff", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void editNotFound() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        TestUtil.createFilledForm(customForm);

        new GalleryCommentsManager().edit(1, "fff");
    }

    @Test
    public void editNotMy() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setGallery(gallery);
        galleryComment.setText("g");
        galleryComment.setUserId(1);
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        new GalleryCommentsManager().edit(galleryComment.getCommentId(), "fff");
        Assert.assertEquals("g", galleryComment.getText());
        TestUtil.assertIntAndBigInt(1, galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void editNotMyWithAllow() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(true);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final GalleryComment galleryComment = new GalleryComment();
        galleryComment.setGallery(gallery);
        galleryComment.setText("g");
        galleryComment.setUserId(1);
        filledForm.addComment(galleryComment);
        persistance.putGalleryComment(galleryComment);

        new GalleryCommentsManager().edit(galleryComment.getCommentId(), "fff");
        Assert.assertEquals("fff", galleryComment.getText());
        TestUtil.assertIntAndBigInt(1, galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test
    public void addWithUser() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment = new GalleryCommentsManager().add(
                filledForm.getFilledFormId(), gallery.getId(), "fff");
        Assert.assertEquals("fff", galleryComment.getText());
        TestUtil.assertIntAndBigInt(user.getUserId(), galleryComment.getUserId());
        Assert.assertEquals(gallery, galleryComment.getGallery());
        Assert.assertEquals(filledForm, galleryComment.getFilledForm());
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void addWithFilledFormNotFound() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);

        new GalleryCommentsManager().add(1, gallery.getId(), "fff");
    }

    @Test
    public void get() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(true);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final List<GalleryComment> galleryComments =
                new GalleryCommentsManager().get(filledForm.getFilledFormId(), gallery.getId());
        Assert.assertEquals(2, galleryComments.size());
        Assert.assertEquals(galleryComment1, galleryComments.get(1));
        Assert.assertEquals(galleryComment2, galleryComments.get(0));
    }

    @Test
    public void getWithStartAndFinish() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(true);
        gallery.getVoteSettings().setStartDate(new Date(System.currentTimeMillis() / 3));
        gallery.getVoteSettings().setEndDate(new Date());
        gallery.getVoteSettings().setDurationOfVoteLimited(true);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        galleryComment2.setCreated(new Date(System.currentTimeMillis() / 2));
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        final List<GalleryComment> galleryComments =
                galleryCommentsManager.get(filledForm.getFilledFormId(), gallery.getId());
        Assert.assertEquals(1, galleryComments.size());
        Assert.assertEquals(galleryComment2, galleryComments.get(0));
    }

    @Test
    public void getWithUser() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(false);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        galleryComment1.setUserId(user.getUserId());
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        final List<GalleryComment> galleryComments =
                galleryCommentsManager.get(filledForm.getFilledFormId(), gallery.getId());
        Assert.assertEquals(1, galleryComments.size());
        Assert.assertEquals(galleryComment1, galleryComments.get(0));
    }

    @Test
    public void getWithOtherUser() {
        final User user = TestUtil.createUser();
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setDisplayComments(false);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        galleryComment1.setUserId(user.getUserId());
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        final List<GalleryComment> galleryComments =
                galleryCommentsManager.get(filledForm.getFilledFormId(), gallery.getId());
        Assert.assertEquals(0, galleryComments.size());
    }

    @Test
    public void getNotThisFilledForm() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        Assert.assertEquals(0, galleryCommentsManager.get(filledForm.getFilledFormId() + 1, gallery.getId()).size());
    }

    @Test
    public void getNotThisGallery() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftGallery galleryOther = TestUtil.createGallery(site);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setGallery(gallery);
        filledForm.addComment(galleryComment1);
        persistance.putGalleryComment(galleryComment1);

        final GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setGallery(gallery);
        filledForm.addComment(galleryComment2);
        persistance.putGalleryComment(galleryComment2);

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        final List<GalleryComment> galleryComments =
                galleryCommentsManager.get(filledForm.getFilledFormId(), galleryOther.getId());
        Assert.assertEquals(0, galleryComments.size());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
