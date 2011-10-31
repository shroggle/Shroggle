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
package com.shroggle.presentation.site;

import com.shroggle.entity.FormItemName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class FilledFormInfo {

    private String fillDate;

    private String updateDate;

    private int filledFormId;

    private int visitorId;

    private boolean hidden;

    private final List<CustomCellValue> customCellsItemValues = new ArrayList<CustomCellValue>();

    public List<CustomCellValue> getCustomCellsItemValues() {
        return customCellsItemValues;
    }

    public void addCustomCell(final FormItemName formItemName, final String value) {
        this.customCellsItemValues.add(new CustomCellValue(formItemName, value));
    }

    public void addCustomCell(final FormItemName formItemName, final String imageUrl, final String imageAlt, final String notSpecified) {
        this.customCellsItemValues.add(new CustomCellValue(formItemName, imageUrl, imageAlt, notSpecified));
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public class CustomCellValue {

        public CustomCellValue(FormItemName formItemName, String value) {
            this.formItemName = formItemName;
            this.value = value;
            imageUrl = null;
            imageAlt = null;
        }

        public CustomCellValue(FormItemName formItemName, String imageUrl, String imageAlt, final String notSpecified) {
            this.imageUrl = imageUrl;
            this.imageAlt = imageAlt;
            this.formItemName = formItemName;
            this.notSpecified = notSpecified;
            value = null;
        }

        private final FormItemName formItemName;

        private final String value;

        private final String imageUrl;

        private final String imageAlt;

        private String notSpecified = null;

        public String getNotSpecified() {
            return notSpecified;
        }

        public void setNotSpecified(String notSpecified) {
            this.notSpecified = notSpecified;
        }

        public FormItemName getFormItemName() {
            return formItemName;
        }

        public String getValue() {
            return value;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageAlt() {
            return imageAlt;
        }
    }
}
