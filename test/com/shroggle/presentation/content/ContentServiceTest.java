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

package com.shroggle.presentation.content;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Content;
import com.shroggle.entity.ContentId;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


@RunWith(value = TestRunnerWithMockServices.class)
public class ContentServiceTest extends TestBaseWithMockService {

    private Persistance persistance = ServiceLocator.getPersistance();
    private ContentService service = new ContentService();

    @Test
    public void getValueByNotFoundId() throws Exception {
        Assert.assertNull(service.getValue(new ContentId()));
    }

    @Test
    public void getValue() throws Exception {
        final Content content = new Content();
        content.setId(new ContentId(1, "aa"));
        content.setValue("1");
        content.setDate(new Date());
        persistance.putContent(content);

        Assert.assertEquals("1", service.getValue(new ContentId(1, "aa")).getValue());
    }

    @Test
    public void getValueWithDifferendClientId() throws Exception {
        final Content content1 = new Content();
        content1.setId(new ContentId(1, "aa"));
        content1.setValue("1");
        persistance.putContent(content1);

        final Content content2 = new Content();
        content2.setId(new ContentId(2, "aa"));
        content2.setValue("2");
        persistance.putContent(content2);

        Assert.assertEquals("2", service.getValue(new ContentId(2, "aa")).getValue());
    }

    @Test
    public void setValueByNotFoundId() throws Exception {
        service.setValue(new ContentId(1, "aa"), "test");

        final Content content = persistance.getContentById(new ContentId(1, "aa"));
        Assert.assertNotNull(content);
        Assert.assertNotNull(content.getDate());
        Assert.assertEquals("test", content.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueWithLargeId() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append("a");
        }
        service.setValue(new ContentId(null, stringBuilder.toString()), "test");
    }

    @Test(expected = NullPointerException.class)
    public void setValueWithNullId() throws Exception {
        service.setValue(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void getValueWithNullId() throws Exception {
        service.getValue(null);
    }

    @Test
    public void setValue() throws Exception {
        final Content content = new Content();
        content.setId(new ContentId(1, "aa"));
        persistance.putContent(content);

        service.setValue(new ContentId(1, "aa"), "test");

        final Content getContent = persistance.getContentById(new ContentId(1, "aa"));
        Assert.assertNotNull(getContent);
        Assert.assertEquals("test", getContent.getValue());
    }

    @Test
    public void setValueLarge() throws Exception {
        service.setValue(new ContentId(1, "aa"), TestUtil.createString(650000));

        final Content getContent = persistance.getContentById(new ContentId(1, "aa"));
        Assert.assertNotNull(getContent);
        Assert.assertEquals(500000, getContent.getValue().length());
    }

    @Test
    public void setValueWithNull() throws Exception {
        final Content content = new Content();
        content.setId(new ContentId(1, "aa"));
        persistance.putContent(content);

        Assert.assertNull(service.setValue(new ContentId(1, "aa"), null));

        Assert.assertNull(persistance.getContentById(new ContentId(1, "aa")));
    }

    @Test
    public void setValueWithoutClientId() throws Exception {
        ContentId contentId = service.setValue(new ContentId(null, "aa"), "test");

        final Content getContent = persistance.getContentById(contentId);
        Assert.assertNotNull(getContent);
        Assert.assertNotNull(contentId.getClientId());
        Assert.assertEquals("test", getContent.getValue());
    }

    @Test
    public void setValueWithoutClientIdAndWithNullValue() throws Exception {
        Assert.assertNull(service.setValue(new ContentId(null, "aa"), null));
    }

    @Test
    public void setValueWithDifferentClientId() throws Exception {
        final Content content = new Content();
        content.setId(new ContentId(1, "aa"));
        persistance.putContent(content);

        service.setValue(new ContentId(2, "aa"), "ff");

        final Content getContent = persistance.getContentById(new ContentId(2, "aa"));
        Assert.assertNotNull(getContent);
        Assert.assertEquals("ff", getContent.getValue());
    }

    @Test
    public void setValueWithDifferentSelectId() throws Exception {
        final Content content = new Content();
        content.setId(new ContentId(1, "aa"));
        persistance.putContent(content);

        service.setValue(new ContentId(1, "bb"), "ff");

        final Content getContent = persistance.getContentById(new ContentId(1, "bb"));
        Assert.assertNotNull(getContent);
        Assert.assertEquals("ff", getContent.getValue());
    }

}