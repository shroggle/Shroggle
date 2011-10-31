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

package com.shroggle.presentation.forum;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftForum;
import com.shroggle.entity.SubForum;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.CannotFindSubForumException;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.Before;

public class ShowSubForumActionTest extends TestBaseWithMockService {

    @Before
    public void before() {
        final ActionBeanContext actionBeanContext = new ActionBeanContext();
        actionBeanContext.setRequest(new MockHttpServletRequest("", ""));
        action.setContext(actionBeanContext);
    }

    @Test(expected = CannotFindSubForumException.class)
    public void executeWithoutWidget() throws Exception {
        action.execute();
    }

    @Test
    public void execute() throws Exception {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(forumWidget.getDraftItem()));

        action.subForumId = subForum.getSubForumId();
        Assert.assertEquals("/forum/showSubForum.jsp", ((ForwardResolution) action.execute()).getPath());
    }

    private final ShowSubForumActionBean action = new ShowSubForumActionBean();

}
