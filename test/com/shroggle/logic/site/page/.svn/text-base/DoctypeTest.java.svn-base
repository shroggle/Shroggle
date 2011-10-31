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
package com.shroggle.logic.site.page;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class DoctypeTest {
    @Test
    public void testAddDoctypeIfNeeded_withoutDoctype() {
        final String newHtml = Doctype.addDoctypeIfNeeded("<html>");
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>", newHtml);
    }

    @Test
    public void testAddDoctypeIfNeeded_withoutHTML() {
        final String newHtml = Doctype.addDoctypeIfNeeded(null);
        Assert.assertEquals(null, newHtml);
    }

    @Test
    public void testAddDoctypeIfNeeded_withDoctype() {
        final String newHtml = Doctype.addDoctypeIfNeeded("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html>");
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html>", newHtml);
    }
}
