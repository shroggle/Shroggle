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
import com.shroggle.entity.Site;
import com.shroggle.exception.ImageException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.provider.ResourceGetterType;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/tinymce/plugins/example/dialogResult.action")
public class UploadImageFromTinyMceAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        if (new UsersManager().getLoginedUser() != null) {
            final Site site = ServiceLocator.getPersistance().getSite(siteId);
            if (site != null) {
                try {
                    if (imageuploadfile != null) {
                        final UploadImageToSiteCommand command = new UploadImageToSiteCommand();
                        command.setTitle(FileNameUtil.getAvailableImageName(imageuploadfile.getFileName(), siteId));
                        command.setDescription(imageuploaddescription);
                        command.setSiteId(siteId);
                        command.setFileBean(imageuploadfile);
                        command.execute();
                        image = command.getImage();
                        path = ResourceGetterType.IMAGE.getUrl(image.getImageId());
                    }
                } catch (ImageException exception) {
                    addValidationError(exception);
                }
            }
        }
        return resolutionCreator.forwardToUrl("/tinymce/plugins/example/dialogResult.jsp");
    }

    public void setImageuploadfile(FileBean imageuploadfile) {
        this.imageuploadfile = imageuploadfile;
    }

    public Image getImage() {
        return image;
    }

    public void setImageuploaddescription(String imageuploaddescription) {
        this.imageuploaddescription = imageuploaddescription;
    }

    public String getPath() {
        return path;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    private FileBean imageuploadfile;
    private String imageuploaddescription;
    private Image image;
    private String path;
    private int siteId;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}