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
package com.shroggle.logic.blog;

import com.shroggle.presentation.blog.BlogDispatchRequest;
import com.shroggle.presentation.blog.ShowBlogPostsAction;
import com.shroggle.presentation.blog.ShowBlogPostsService;
import com.shroggle.exception.MissingDispatchParameterException;
import com.shroggle.exception.UnknownBlogDispatchTypeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.Date;

/**
 * @author dmitry.solomadin
 */
public class BlogDispatchHelper {

    public String dispatch(final BlogDispatchRequest request) throws IOException, ServletException {
        if (request.getDispatchBlog() == BlogDispatchType.SHOW_BLOG) {
            final ShowBlogPostsService service = new ShowBlogPostsService();

            return service.execute(request.getBlogId(), request.getWidgetId(), request.getStartIndex(),
                    request.getExactBlogPostId(), request.getSiteShowOption());
        } else {
            throw new UnknownBlogDispatchTypeException("Unknown blog dispatch type: " + request.getDispatchBlog());
        }
    }

}
