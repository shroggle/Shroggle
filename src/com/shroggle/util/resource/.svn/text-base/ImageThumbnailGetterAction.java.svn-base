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

package com.shroggle.util.resource;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.resource.provider.ResourceGetterType;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/thumbnails/{width}x{height}/{id}-{name}.{extension}")
public class ImageThumbnailGetterAction extends Action {

    public Resolution execute() throws Exception {
        final ResourceGetter getter = ResourceGetterType.IMAGE.getGetter();
        final ResourceRequest request = new ResourceRequest(
                id, 0, 0, width < 1 ? null : width, height < 1? null : height);
        final ResourceResponse response = getter.execute(request);
        if (response.getData() != null) {
            return resolutionCreator.resourceGetter(
                    response.getResource().getExtension(), response.getData(),
                    StringUtil.getEmptyOrString(response.getResource().getName()));
        }
        return resolutionCreator.notFound();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int id;
    private int width;
    private int height;
    private String name;
    private String extension;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}