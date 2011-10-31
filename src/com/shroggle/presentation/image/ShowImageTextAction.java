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


import com.shroggle.entity.DraftImage;
import com.shroggle.entity.Image1;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.WorkImage;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.*;


/**
 * @author Balakirev Anatoliy
 */
//todo add tests (Anatoliy Balakirev)
@UrlBinding("/site/showImageText.action")
public class ShowImageTextAction extends Action implements ActionBean {

    @DefaultHandler
    public Resolution show() {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Image1 draftImage = siteShowOption.isDraft() ? (DraftImage) persistance.getDraftItem(imageId) : (WorkImage) persistance.getWorkItem(imageId);
        textArea = draftImage != null ? StringUtil.getEmptyOrString(draftImage.getTextArea()) : "";
        return new ForwardResolution("/image/showImageText.jsp");
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getTextArea() {
        return textArea;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    private String textArea;
    private Integer imageId;
    private SiteShowOption siteShowOption;
}