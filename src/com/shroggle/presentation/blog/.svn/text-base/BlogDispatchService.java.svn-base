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

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;

import com.shroggle.logic.blog.BlogDispatchHelper;
import com.shroggle.presentation.AbstractService;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class BlogDispatchService extends AbstractService {

    @RemoteMethod
    public String executeDispatch(final BlogDispatchRequest request) throws IOException, ServletException {
        return new BlogDispatchHelper().dispatch(request);
    }
    
}
