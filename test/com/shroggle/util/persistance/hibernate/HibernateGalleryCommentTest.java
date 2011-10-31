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

import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Date;

public class HibernateGalleryCommentTest extends HibernatePersistanceTestBase {

    @Test
    public void getGalleryCommentById() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("ff");
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setFilledForm(filledForm);
        galleryComment1.setGallery(gallery);
        persistance.putGalleryComment(galleryComment1);

        GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setFilledForm(filledForm);
        galleryComment2.setGallery(gallery);
        persistance.putGalleryComment(galleryComment2);

        Assert.assertEquals(galleryComment1, persistance.getGalleryCommentById(galleryComment1.getCommentId()));
    }

    @Test
    public void getGalleryCommentsByFilledFormIdAndGalleryId() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        DraftGallery galleryOther = new DraftGallery();
        galleryOther.setName("blogName");
        persistance.putItem(galleryOther);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("ff");
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledFormOther = new FilledForm();
        filledFormOther.setFormDescription("ff");
        filledFormOther.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledFormOther);

        GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setFilledForm(filledForm);
        galleryComment1.setGallery(gallery);
        persistance.putGalleryComment(galleryComment1);

        GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setFilledForm(filledForm);
        galleryComment2.setGallery(gallery);
        persistance.putGalleryComment(galleryComment2);

        GalleryComment galleryCommentOther = new GalleryComment();
        galleryCommentOther.setFilledForm(filledForm);
        galleryCommentOther.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOther);

        GalleryComment galleryCommentOtherFilledForm = new GalleryComment();
        galleryCommentOtherFilledForm.setFilledForm(filledFormOther);
        galleryCommentOtherFilledForm.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOtherFilledForm);

        final List<GalleryComment> galleryComments =
                persistance.getGalleryCommentsByFilledFormAndGallery(
                        filledForm.getFilledFormId(), gallery.getId(), null, null, null);

        Assert.assertEquals(2, galleryComments.size());
        Assert.assertEquals(galleryComment1, galleryComments.get(1));
        Assert.assertEquals(galleryComment2, galleryComments.get(0));
    }

    @Test
    public void getGalleryCommentsByFilledFormIdAndGalleryIdWithStartAndFinish() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        DraftGallery galleryOther = new DraftGallery();
        galleryOther.setName("blogName");
        persistance.putItem(galleryOther);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("ff");
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledFormOther = new FilledForm();
        filledFormOther.setFormDescription("ff");
        filledFormOther.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledFormOther);

        GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment1.setFilledForm(filledForm);
        galleryComment1.setGallery(gallery);
        persistance.putGalleryComment(galleryComment1);

        GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setFilledForm(filledForm);
        galleryComment2.setGallery(gallery);
        persistance.putGalleryComment(galleryComment2);

        GalleryComment galleryCommentOther = new GalleryComment();
        galleryCommentOther.setFilledForm(filledForm);
        galleryCommentOther.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOther);

        GalleryComment galleryCommentOtherFilledForm = new GalleryComment();
        galleryCommentOtherFilledForm.setFilledForm(filledFormOther);
        galleryCommentOtherFilledForm.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOtherFilledForm);

        final List<GalleryComment> galleryComments =
                persistance.getGalleryCommentsByFilledFormAndGallery(
                        filledForm.getFilledFormId(), gallery.getId(), null,
                        new Date(System.currentTimeMillis() / 2), new Date());

        Assert.assertEquals(1, galleryComments.size());
        Assert.assertEquals(galleryComment2, galleryComments.get(0));
    }

    @Test
    public void getGalleryCommentsByFilledFormIdAndGalleryIdWithUserId() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        DraftGallery galleryOther = new DraftGallery();
        galleryOther.setName("blogName");
        persistance.putItem(galleryOther);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("ff");
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledFormOther = new FilledForm();
        filledFormOther.setFormDescription("ff");
        filledFormOther.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledFormOther);

        GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setUserId(1);
        galleryComment1.setFilledForm(filledForm);
        galleryComment1.setGallery(gallery);
        persistance.putGalleryComment(galleryComment1);

        GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setFilledForm(filledForm);
        galleryComment2.setGallery(gallery);
        persistance.putGalleryComment(galleryComment2);

        GalleryComment galleryCommentOther = new GalleryComment();
        galleryCommentOther.setFilledForm(filledForm);
        galleryCommentOther.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOther);

        GalleryComment galleryCommentOtherFilledForm = new GalleryComment();
        galleryCommentOtherFilledForm.setFilledForm(filledFormOther);
        galleryCommentOtherFilledForm.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOtherFilledForm);

        final List<GalleryComment> galleryComments =
                persistance.getGalleryCommentsByFilledFormAndGallery(
                        filledForm.getFilledFormId(), gallery.getId(), 1, null, null);

        Assert.assertEquals(1, galleryComments.size());
        Assert.assertEquals(galleryComment1, galleryComments.get(0));
    }

    @Test
    public void getGalleryCommentsByFilledFormIdAndGalleryIdWithUserIdAndStartFinish() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        DraftGallery galleryOther = new DraftGallery();
        galleryOther.setName("blogName");
        persistance.putItem(galleryOther);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("ff");
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledFormOther = new FilledForm();
        filledFormOther.setFormDescription("ff");
        filledFormOther.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledFormOther);

        GalleryComment galleryComment1 = new GalleryComment();
        galleryComment1.setUserId(1);
        galleryComment1.setFilledForm(filledForm);
        galleryComment1.setGallery(gallery);
        persistance.putGalleryComment(galleryComment1);

        GalleryComment galleryComment2 = new GalleryComment();
        galleryComment2.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment2.setFilledForm(filledForm);
        galleryComment2.setGallery(gallery);
        persistance.putGalleryComment(galleryComment2);

        GalleryComment galleryComment3 = new GalleryComment();
        galleryComment3.setCreated(new Date(System.currentTimeMillis() * 2));
        galleryComment3.setFilledForm(filledForm);
        galleryComment3.setUserId(1);
        galleryComment3.setGallery(gallery);
        persistance.putGalleryComment(galleryComment3);

        GalleryComment galleryCommentOther = new GalleryComment();
        galleryCommentOther.setFilledForm(filledForm);
        galleryCommentOther.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOther);

        GalleryComment galleryCommentOtherFilledForm = new GalleryComment();
        galleryCommentOtherFilledForm.setFilledForm(filledFormOther);
        galleryCommentOtherFilledForm.setGallery(galleryOther);
        persistance.putGalleryComment(galleryCommentOtherFilledForm);

        final List<GalleryComment> galleryComments =
                persistance.getGalleryCommentsByFilledFormAndGallery(
                        filledForm.getFilledFormId(), gallery.getId(), 1,
                        new Date(System.currentTimeMillis() / 2), new Date());

        Assert.assertEquals(1, galleryComments.size());
        Assert.assertEquals(galleryComment1, galleryComments.get(0));
    }

}