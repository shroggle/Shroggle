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

import com.shroggle.entity.Video;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.VideoItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.video.CreateVideoRequest;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class CreateVideoService extends AbstractService {

    @SynchronizeByMethodParameterProperties({
            @SynchronizeByMethodParameterProperty(
                    entityClass = Widget.class,
                    entityIdPropertyPath = "widgetId",
                    method = SynchronizeMethod.WRITE),
            @SynchronizeByMethodParameterProperty(
                    entityClass = Video.class,
                    entityIdPropertyPath = "videoId")})
    public void execute(final CreateVideoRequest request) {
        final UserManager userManager = new UsersManager().getLogined();
        new VideoItemManager(userManager, request.getVideoItemId()).edit(request);
    }

}