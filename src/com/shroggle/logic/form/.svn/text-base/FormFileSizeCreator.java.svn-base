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
package com.shroggle.logic.form;

import com.shroggle.entity.FormFile;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.filesystem.FileSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * @author Balakirev Anatoliy
 */
public class FormFileSizeCreator {

    public static void execute() {
        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
        for (final FormFile file : ServiceLocator.getPersistance().getFormFiles()) {
            try {
                final BufferedImage sourceBufferedImage = ImageIO.read(fileSystem.getResourceStream(file));
                if (sourceBufferedImage != null) {
                    final Integer width = sourceBufferedImage.getWidth();
                    final Integer height = sourceBufferedImage.getHeight();
                    persistanceTransaction.execute(new Runnable() {
                        public void run() {
                            file.setWidth(width);
                            file.setHeight(height);
                        }
                    });
                }
            } catch (Exception exception) {
                //ignore
            }
        }
    }

}
