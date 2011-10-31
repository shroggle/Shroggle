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
package com.shroggle.presentation.blog;

import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.entity.SiteShowOption;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowBlogPostsService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final int blogId, final int widgetId, final int startIndex, final Integer exactBlogPostId,
                          final SiteShowOption siteShowOption) throws IOException, ServletException {
        return executePage("/blog/showBlogPosts.action?blogId=" + blogId + "&widgetBlogId=" + widgetId +
                "&startIndex=" + startIndex +
                (exactBlogPostId != null ? "&exactBlogPostId=" + exactBlogPostId : "") +
                "&siteShowOption=" + siteShowOption);
    }

}
