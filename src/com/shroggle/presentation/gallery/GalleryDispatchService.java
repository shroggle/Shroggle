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
package com.shroggle.presentation.gallery;

import com.shroggle.logic.gallery.GalleryDispatchHelper;
import com.shroggle.presentation.AbstractService;

import javax.servlet.ServletException;
import java.io.IOException;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class GalleryDispatchService extends AbstractService {
    
    @RemoteMethod
    public String executeDispatch(final GalleryDispatchRequest request) throws IOException, ServletException {
        return new GalleryDispatchHelper().dispatch(request, createRenderContext(false));
    }

}
