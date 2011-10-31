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
import com.shroggle.entity.*;
import com.shroggle.exception.SiteItemNotFoundException;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowGalleryCommentServiceTest {

    @Test(expected = SiteItemNotFoundException.class)
    public void executeForNotFoundGallery() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        service.execute(request);
    }

    @Test
    public void executeForNotFoundFilledForm() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        request.setGalleryId(gallery.getId());

        service.execute(request);
    }

    @Test
    public void execute() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        request.setGalleryId(gallery.getId());
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        request.setFilledFormId(filledForm.getFilledFormId());

        final List<GalleryCommentInfo> galleryCommentInfos = service.execute(request);
        Assert.assertNotNull(galleryCommentInfos);
    }

    private final ShowGalleryCommentRequest request = new ShowGalleryCommentRequest();
    private final ShowGalleryCommentService service = new ShowGalleryCommentService();

}
