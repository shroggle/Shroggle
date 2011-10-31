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

package com.shroggle.util.filesystem;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@XmlRootElement
class CssParametersLibrary {

    public List<CssParametersRecord> getRecords() {
        return records;
    }

    public void setRecords(final List<CssParametersRecord> records) {
        this.records = records;
    }

    @XmlElement(name = "floatRecord")
    public List<CssParametersFloatRecord> getFloatRecords() {
        return floatRecords;
    }

    public void setFloatRecords(final List<CssParametersFloatRecord> floatRecords) {
        this.floatRecords = floatRecords;
    }

    private List<CssParametersFloatRecord> floatRecords = new ArrayList<CssParametersFloatRecord>();
    private List<CssParametersRecord> records = new ArrayList<CssParametersRecord>();

}
