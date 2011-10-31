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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@XmlRootElement(name = "Response")
public class JavienResponse {

    public static JavienResponse newInstance(final String response) {
        try {
            String responseText = response;
            responseText = responseText.replace("response", "Response");
            responseText = responseText.replace("<value>", "");
            responseText = responseText.replace("</value>", "");
            responseText = responseText.replace("<errorList>", "");
            responseText = responseText.replace("</errorList>", "");
            responseText = responseText.replace("<Id>", "<id>");
            responseText = responseText.replace("</Id>", "</id>");
            final JAXBContext jaxbContext = JAXBContext.newInstance(JavienResponse.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (JavienResponse) unmarshaller.unmarshal(new StreamSource(new StringReader(responseText)));
        } catch (Exception exception) {
            final Logger logger = Logger.getLogger(JavienResponse.class.getName());
            logger.log(Level.SEVERE, "Can`t create JavienResponse! ", exception);
            return null;
        }
    }

    private JavienProductHolder javienProductHolder;

    private JavienErrorHolder javienErrorHolder;

    private JavienProduct javienProduct;

    private JavienSession session;


    private JavienResponse() {
    }

    @XmlElement(name = "Errors")
    public JavienErrorHolder getJavienErrorHolder() {
        return javienErrorHolder;
    }

    public void setJavienErrorHolder(JavienErrorHolder javienErrorHolder) {
        this.javienErrorHolder = javienErrorHolder;
    }

    @XmlElement(name = "Session")
    public JavienSession getSession() {
        return session;
    }

    public void setSession(JavienSession session) {
        this.session = session;
    }

    @XmlElement(name = "Product")
    public JavienProduct getProduct() {
        return javienProduct;
    }

    public void setProduct(JavienProduct javienProduct) {
        this.javienProduct = javienProduct;
    }

    @XmlElement(name = "ProductList")
    public JavienProductHolder getJavienProductHolder() {
        return javienProductHolder;
    }

    public void setJavienProductHolder(JavienProductHolder javienProductHolder) {
        this.javienProductHolder = javienProductHolder;
    }
}