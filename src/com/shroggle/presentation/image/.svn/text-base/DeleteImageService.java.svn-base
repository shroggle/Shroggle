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
import com.shroggle.exception.ImageNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
public class DeleteImageService {

    @RemoteMethod
    public void execute(final int imageId) {
        new UsersManager().getLogined();

        final Image image = persistance.getImageById(imageId);
        if (image == null) {
            throw new ImageNotFoundException();
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                fileSystem.removeResource(image);
                persistance.removeImage(image);
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
}
