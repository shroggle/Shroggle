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
package com.shroggle.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "formFileShouldBeCopied")
public class FormFileShouldBeCopied {

    public FormFileShouldBeCopied() {
    }

    public FormFileShouldBeCopied(int copiedFormFile, int sourceFormFile) {
        this.copiedFormFile = copiedFormFile;
        this.sourceFormFile = sourceFormFile;
    }

    @Id
    private int id;

    private int sourceFormFile;

    private int copiedFormFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceFormFile() {
        return sourceFormFile;
    }

    public void setSourceFormFile(int sourceFormFile) {
        this.sourceFormFile = sourceFormFile;
    }

    public int getCopiedFormFile() {
        return copiedFormFile;
    }

    public void setCopiedFormFile(int copiedFormFile) {
        this.copiedFormFile = copiedFormFile;
    }
}
