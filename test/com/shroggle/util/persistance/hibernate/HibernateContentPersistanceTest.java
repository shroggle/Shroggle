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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.Content;
import com.shroggle.entity.ContentId;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

public class HibernateContentPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putContent() {
        Content content = new Content();
        content.setDate(new Date());
        content.setId(new ContentId(1, "a"));
        persistance.putContent(content);
        Assert.assertEquals(content.getId(), persistance.getContentById(content.getId()).getId());
        persistance.removeContent(content);
    }

    @Test
    public void getMaxContentClientId() {
        Content content = new Content();
        content.setDate(new Date());
        content.setId(new ContentId(1, "a"));
        ContentId contentId = new ContentId();
        contentId.setSelectId("11");
        contentId.setClientId(1);
        content.setId(contentId);
        persistance.putContent(content);

        Assert.assertEquals(1, persistance.getMaxContentClientId());
    }

    @Test
    public void getMaxContentClientId2() {
        Assert.assertEquals(0, persistance.getMaxContentClientId());
    }
}