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
package com.shroggle.util;

import com.shroggle.entity.Image;
import com.shroggle.entity.ImageFile;
import com.shroggle.entity.ImageFileType;
import com.shroggle.entity.Video;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * // todo may be more better use special method persistance.getImageByNameAndSiteId(name, siteId) != null?
 *
 * @author Balakirev Anatoliy
 */
public class FileNameUtil {

    public static String getAvailableImageName(String name, final int siteId) {
        List<Image> images = ServiceLocator.getPersistance().getImagesByOwnerSiteId(siteId);
        String extension = name.substring(name.lastIndexOf('.') + 1);
        name = name.substring(0, name.lastIndexOf('.'));
        String initialName = name;
        boolean nameExist;
        int i = 2;
        do {
            nameExist = false;
            for (Image tempImage : images) {
                if (tempImage.getName().isEmpty() || tempImage.getName().indexOf('.') == -1) {
                    continue;
                }
                final String tempImageName = tempImage.getName().substring(0, tempImage.getName().lastIndexOf('.'));
                if (tempImageName.equals(name)) {
                    name = initialName + "(" + i + ")";
                    i++;
                    nameExist = true;
                    break;
                }
            }
        } while (nameExist);
        return name + "." + extension;
    }

    public static String getAvailableImageFileName(String name, final int siteId, final ImageFileType imageFileType) {
        List<ImageFile> imageFiles = ServiceLocator.getPersistance().getImageFilesByTypeAndSiteId(imageFileType, siteId);
        String extension = name.substring(name.lastIndexOf('.') + 1);
        name = name.substring(0, name.lastIndexOf('.'));
        String initialName = name;
        boolean nameExist;
        int i = 2;
        do {
            nameExist = false;
            for (ImageFile tempImageFile : imageFiles) {
                if (tempImageFile.getSourceName().isEmpty() || tempImageFile.getSourceName().indexOf('.') == -1) {
                    continue;
                }
                if (tempImageFile.getSourceName().substring(0, tempImageFile.getSourceName().lastIndexOf('.')).equals(name)) {
                    name = initialName + "(" + i + ")";
                    i++;
                    nameExist = true;
                    break;
                }
            }
        } while (nameExist);
        return name + "." + extension;
    }


    public static String getAvailableVideoName(String name, final int siteId) {
        List<Video> videos = ServiceLocator.getPersistance().getVideosBySiteId(siteId);
        String extension = name.substring(name.lastIndexOf('.') + 1);
        name = name.substring(0, name.lastIndexOf('.'));
        String initialName = name;
        boolean nameExist;
        int i = 2;
        do {
            nameExist = false;
            for (Video tempVideo : videos) {
                if (tempVideo.getSourceName().isEmpty() || tempVideo.getSourceName().indexOf('.') == -1) {
                    continue;
                }
                if (tempVideo.getSourceName().substring(0, tempVideo.getSourceName().lastIndexOf('.')).equals(name)) {
                    name = initialName + "(" + i + ")";
                    i++;
                    nameExist = true;
                    break;
                }
            }
        } while (nameExist);
        return name + "." + extension;
    }

    public static String createExtension(final String fileName) {
        try {
            final int extensionStartIndex = fileName.lastIndexOf('.');
            if (extensionStartIndex > -1) {
                String extension = fileName.substring(extensionStartIndex + 1);
                return !extension.isEmpty() ? extension.toLowerCase() : null;
            }
        } catch (Exception exception) {
            Logger.getLogger(FileNameUtil.class.getName()).log(Level.SEVERE, "Can`t create file extension!", exception);
        }
        return null;
    }

}
