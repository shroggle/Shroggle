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

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.logic.forum.ForumDispatchHelper;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ForumDispatchService extends AbstractService {

    @RemoteMethod
    public String executeDispatch(final ForumDispatchRequest request) throws IOException, ServletException {
        final ForumDispatchHelper dispatchHelper = new ForumDispatchHelper(getContext().getHttpServletRequest());
        return dispatchHelper.dispatch(request);
    }

}
