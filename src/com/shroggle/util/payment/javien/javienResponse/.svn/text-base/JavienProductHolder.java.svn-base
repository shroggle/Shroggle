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

package com.shroggle.util.payment.javien.javienResponse;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
public class JavienProductHolder {

    private List<JavienProduct> javienProduct = new ArrayList<JavienProduct>();

    @XmlElement(name = "Product")
    public List<JavienProduct> getProducts() {
        return javienProduct;
    }

    public void setProduct(List<JavienProduct> javienProduct) {
        this.javienProduct = javienProduct;
    }
}