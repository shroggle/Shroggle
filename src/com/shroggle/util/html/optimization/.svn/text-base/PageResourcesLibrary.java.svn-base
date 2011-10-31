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
package com.shroggle.util.html.optimization;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@XmlRootElement(name = "pageResources")
public class PageResourcesLibrary {

    @XmlElement(name = "part")
    public List<PageResourcesPart> getParts() {
        return parts;
    }

    public void setParts(List<PageResourcesPart> parts) {
        this.parts = parts;
    }

    private List<PageResourcesPart> parts;

}