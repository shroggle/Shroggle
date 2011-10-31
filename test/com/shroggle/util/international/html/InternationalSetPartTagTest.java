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

package com.shroggle.util.international.html;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.MockJspWriter;
import com.shroggle.util.PageContextMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.international.property.InternationalStoragePropertyBundle;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class InternationalSetPartTagTest {

    @Test
    public void doStartTag() throws JspException {
        ServiceLocator.setInternationStorage(new InternationalStoragePropertyBundle());
        InternationalSetPartTag tag = new InternationalSetPartTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        tag.setPart("test");
        tag.setPageContext(pageContextMock);

        Assert.assertEquals(BodyTagSupport.SKIP_BODY, tag.doStartTag());
        final International international =
                (International) pageContextMock.getAttribute(InternationalSetPartTag.INTERNATIONAL);
        Assert.assertNotNull(international);
        Assert.assertNotNull(international.get("test"));
    }

    @Test(expected = JspException.class)
    public void doStartTagWithNullPart() throws JspException {
        ServiceLocator.setInternationStorage(new InternationalStoragePropertyBundle());
        InternationalSetPartTag tag = new InternationalSetPartTag();
        final PageContextMock pageContextMock = new PageContextMock();
        tag.setPart(null);
        tag.setPageContext(pageContextMock);

        tag.doStartTag();
    }

    @Test(expected = JspException.class)
    public void doStartTagWithNullInternalStorage() throws JspException {
        ServiceLocator.setInternationStorage(null);
        InternationalSetPartTag tag = new InternationalSetPartTag();
        final PageContextMock pageContextMock = new PageContextMock();
        tag.setPart("test");
        tag.setPageContext(pageContextMock);

        tag.doStartTag();
    }

}
