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
package com.shroggle.logic.javien;

import com.shroggle.util.payment.javien.javienResponse.JavienResponse;
import com.shroggle.util.payment.javien.javienResponse.JavienProduct;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.08.2009
 */
public class JavienResponseManager {

    public JavienResponseManager(final JavienResponse javienResponse) {
        this.javienResponse = javienResponse;
    }

    public JavienProduct getCorrectProductOrNull() {
        return javienResponse != null && isProductCorrect(javienResponse.getProduct()) ? javienResponse.getProduct() : null;
    }

    public List<JavienProduct> getCorrectProductsOrEmptyList() {
        final List<JavienProduct> javienProducts = new ArrayList<JavienProduct>();
        if (javienResponse != null && javienResponse.getJavienProductHolder() != null) {
            for (JavienProduct javienProduct : javienResponse.getJavienProductHolder().getProducts()) {
                if (isProductCorrect(javienProduct)) {
                    javienProducts.add(javienProduct);
                }
            }
        }
        return javienProducts;
    }

    public String getSessionId() {
        return (javienResponse != null && javienResponse.getSession() != null && javienResponse.getSession().isEnabled() &&
                javienResponse.getSession().getId() != null && !javienResponse.getSession().getId().isEmpty())
                ? javienResponse.getSession().getId() : null;
    }

    private static boolean isProductCorrect(final JavienProduct javienProduct) {
        return (javienProduct != null && javienProduct.getCode() != null && !javienProduct.getCode().isEmpty() &&
                javienProduct.getPrice() != null && !javienProduct.getPrice().isNaN() && !javienProduct.getPrice().isInfinite());
    }

    private final JavienResponse javienResponse;

}
