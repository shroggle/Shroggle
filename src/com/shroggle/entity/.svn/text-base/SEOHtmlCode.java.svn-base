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

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dmitry.solomadin
 */
@Embeddable
public class SEOHtmlCode implements Serializable{

    @Column(length = 255)
    private String name;

    @Lob
    private String code;

    @Enumerated(value = EnumType.STRING)
    private CodePlacement codePlacement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodePlacement getCodePlacement() {
        return codePlacement;
    }

    public void setCodePlacement(CodePlacement codePlacement) {
        this.codePlacement = codePlacement;
    }
}
