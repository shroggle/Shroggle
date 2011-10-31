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
package com.shroggle.presentation.video;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.GalleryVideoRange;
import com.shroggle.entity.PageVisitor;
import com.shroggle.entity.User;
import com.shroggle.logic.gallery.VideoRangeEdit;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.MockWebContextBuilder;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.directwebremoting.WebContextFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class AddVideoRangeServiceTest {

    @Test
    public void executeWithLoginedUser() {
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);


        final User user = TestUtil.createUserAndLogin();
        VideoRangeEdit request = new VideoRangeEdit();
        request.setFilledFormId(12);
        request.setGalleryId(3);
        request.setFinish(10);
        request.setStart(1);
        request.setTotal(20);

        Assert.assertEquals(0, user.getVideoRanges().size());

        service.execute(request);

        final List<Integer> videoRanges = pageVisitor.getVideoRangeIds();
        Assert.assertEquals(0, videoRanges.size());

        Assert.assertEquals(1, user.getVideoRanges().size());
        final GalleryVideoRange videoRange = user.getVideoRanges().get(0);
        Assert.assertEquals(12, videoRange.getFilledFormId());
        Assert.assertEquals(3, videoRange.getGalleryId());
        Assert.assertEquals(10.0, videoRange.getFinish(), 1);
        Assert.assertEquals(1.0, videoRange.getStart(), 1);
        Assert.assertEquals(20.0, videoRange.getTotal(), 1);
    }

    @Test
    public void execute_withWrongVideoRangeEdit() {
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);


        final User user = TestUtil.createUserAndLogin();
        Assert.assertEquals(0, user.getVideoRanges().size());


        service.execute(null);

        final List<Integer> videoRanges = pageVisitor.getVideoRangeIds();
        Assert.assertEquals(0, videoRanges.size());
        Assert.assertEquals(0, user.getVideoRanges().size());
    }

    @Test
    public void executeWithoutLogin() {
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        VideoRangeEdit request = new VideoRangeEdit();
        request.setFilledFormId(12);
        request.setGalleryId(3);
        request.setFinish(10);
        request.setStart(1);
        request.setTotal(20);

        service.execute(request);


        final List<Integer> videoRanges = pageVisitor.getVideoRangeIds();
        Assert.assertEquals(1, videoRanges.size());
        final List<GalleryVideoRange> galleryVideoRanges = ServiceLocator.getPersistance().getGalleryVideoRanges(videoRanges);
        final GalleryVideoRange videoRange = galleryVideoRanges.get(0);
        Assert.assertEquals(12, videoRange.getFilledFormId());
        Assert.assertEquals(3, videoRange.getGalleryId());
        Assert.assertEquals(10.0, videoRange.getFinish(), 1);
        Assert.assertEquals(1.0, videoRange.getStart(), 1);
        Assert.assertEquals(20.0, videoRange.getTotal(), 1);
    }


    private final AddVideoRangeService service = new AddVideoRangeService();
}
