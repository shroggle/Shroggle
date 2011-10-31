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
import com.shroggle.util.MockBodyContent;
import com.shroggle.util.MockJspWriter;
import com.shroggle.util.PageContextMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.international.property.MockInternational;
import com.shroggle.util.international.property.MockIntertationalStorage;
import com.shroggle.util.international.property.InternationalStoragePropertyBundle;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.Locale;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class InternationalGetTagTest {

    @Test
    public void doStartTag() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();

        Assert.assertEquals(BodyTagSupport.EVAL_BODY_TAG, tag.doStartTag());
    }

    @Test
    public void doEndTagWithoutBodyContent() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        pageContextMock.setAttribute(InternationalSetPartTag.INTERNATIONAL, internationalStorage.get("test", Locale.US));
        tag.setName("test1");
        tag.addParameter("12");
        tag.setPageContext(pageContextMock);

        Assert.assertEquals(BodyTagSupport.SKIP_BODY, tag.doEndTag());
        Assert.assertEquals("super 12", mockJspWriter.toString());
    }

    @Test
    public void doEndTagWithoutBodyContentAndParameter() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        final MockIntertationalStorage mockIntertationalStorage = new MockIntertationalStorage();
        final MockInternational mockInternational = (MockInternational) mockIntertationalStorage.get(null, null);
        mockInternational.setResult("super 12");
        pageContextMock.setAttribute(InternationalSetPartTag.INTERNATIONAL, mockIntertationalStorage.get("test", Locale.US));
        tag.setName("test1");
        tag.setPageContext(pageContextMock);

        Assert.assertEquals(BodyTagSupport.SKIP_BODY, tag.doEndTag());
        Assert.assertEquals("super 12", mockJspWriter.toString());
        Assert.assertNotNull(mockInternational.getParameters());
        Assert.assertEquals(0, mockInternational.getParameters().length);
        Assert.assertEquals("test1", mockInternational.getName());
    }

    @Test(expected = JspException.class)
    public void doEndTagWithoutBodyContentWithoutInternational() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        tag.setName("test");
        tag.setPageContext(pageContextMock);

        tag.doEndTag();
    }

    @Test
    public void doEndTagWithBodyContent() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        tag.setBodyContent(new MockBodyContent(mockJspWriter));

        Assert.assertEquals(BodyTagSupport.EVAL_BODY_TAG, tag.doEndTag());
    }

    @Test
    public void doAfterBodyWithBodyContent() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        tag.setPageContext(pageContextMock);
        tag.setName("test2");
        tag.addParameter("13");
        tag.addParameter("r");
        tag.setBodyContent(new MockBodyContent(mockJspWriter));
        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        pageContextMock.setAttribute(InternationalSetPartTag.INTERNATIONAL, internationalStorage.get("test", Locale.US));

        Assert.assertEquals(BodyTagSupport.SKIP_BODY, tag.doAfterBody());
        Assert.assertEquals("super 13 r", mockJspWriter.toString());
    }

    @Test(expected = JspException.class)
    public void doAfterBodyWithBodyContentWithoutInternational() throws JspException {
        InternationalGetTag tag = new InternationalGetTag();
        final MockJspWriter mockJspWriter = new MockJspWriter();
        final PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        tag.setPageContext(pageContextMock);
        tag.setName("test");
        tag.setBodyContent(new MockBodyContent(mockJspWriter));

        tag.doAfterBody();
    }

}