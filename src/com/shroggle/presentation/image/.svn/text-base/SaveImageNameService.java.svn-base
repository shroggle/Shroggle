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
package com.shroggle.presentation.image;

import com.shroggle.entity.Image;
import com.shroggle.exception.ImageDuplicateNameException;
import com.shroggle.exception.ImageNotFoundException;
import com.shroggle.logic.DefaultNameUtil;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SaveImageNameService extends AbstractService {

    @RemoteMethod
    public void execute(final int imageId, final String newImageName) {
        new UsersManager().getLogined();

        if (StringUtil.isNullOrEmpty(newImageName)) {
            return;
        }

        final Image image = persistance.getImageById(imageId);

        if (image == null) {
            throw new ImageNotFoundException("Cannot find image by Id=" + imageId);
        }

        final Image duplicateImage = persistance.getImageByNameAndSiteId(newImageName, image.getSiteId());
        if (duplicateImage != null && duplicateImage.getImageId() != image.getImageId()) {
            final Set<String> namesInUse = new HashSet<String>(persistance.getImagesNamesBySite(image.getSiteId()));

            final String newDefaultImageName = new DefaultNameUtil().getNext(newImageName, false, namesInUse);

            persistanceTransaction.execute(new Runnable() {
                @Override
                public void run() {
                    image.setName(newDefaultImageName);
                }
            });

            throw new ImageDuplicateNameException(newDefaultImageName);
        }

        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                image.setName(newImageName);
            }
        });
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
