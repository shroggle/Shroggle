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
package com.shroggle.logic.forum;

import com.shroggle.entity.DraftForum;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Widget;
import com.shroggle.entity.Site;
import com.shroggle.entity.SubForum;
import com.shroggle.presentation.forum.ForumDispatchRequest;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ForumDispatchHelperTest {

    final ForumDispatchHelper forumDispatchHelper = new ForumDispatchHelper(new MockHttpServletRequest("", ""));

    @Test
    public void testDispatch() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftForum forum = TestUtil.createForum(site);
        final SubForum subFourm = TestUtil.createSubForum(forum);
        final Widget widget = TestUtil.createForumWidget();

        final ForumDispatchRequest request = new ForumDispatchRequest();
        request.setDispatchForum(ForumDispatchType.SHOW_SUBFORUM);
        request.setSubForumId(subFourm.getSubForumId());
        request.setWidgetId(widget.getWidgetId());
        request.setShowOnUserPages(true);

        Assert.assertEquals("/forum/showSubForum.action?subForumId=1&widgetId=1001&isShowOnUserPages=true",
                forumDispatchHelper.dispatch(request));
    }

    @Test
    public void testExtractForumDispatchParameters() {
        final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("dispatchForum", new String[]{"SOME_DISPATCH_VALUE"});
        parameterMap.put("subForumId", new String[]{"SOME_SUBFORUM_ID"});
        parameterMap.put("threadId", new String[]{"SOME_THREAD_ID"});
        parameterMap.put("postId", new String[]{"SOME_POST_ID"});
        parameterMap.put("draftPostEdit", new String[]{"SOME_DRAFT_POST_EDIT_VALUE"});
        parameterMap.put("widgetId", new String[]{"SOME_WIDGET_ID"});

        final String forumParametersString = ForumDispatchHelper.extractForumDispatchParameters(parameterMap);
        Assert.assertTrue(forumParametersString.contains("dispatchForum=SOME_DISPATCH_VALUE"));
        Assert.assertTrue(forumParametersString.contains("subForumId=SOME_SUBFORUM_ID"));
        Assert.assertTrue(forumParametersString.contains("threadId=SOME_THREAD_ID"));
        Assert.assertTrue(forumParametersString.contains("postId=SOME_POST_ID"));
        Assert.assertTrue(forumParametersString.contains("draftPostEdit=SOME_DRAFT_POST_EDIT_VALUE"));
        Assert.assertTrue(forumParametersString.contains("widgetId=SOME_WIDGET_ID"));
    }

}
