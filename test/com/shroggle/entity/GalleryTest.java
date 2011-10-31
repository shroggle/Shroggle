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
package com.shroggle.entity;

import org.junit.Test;
import junit.framework.Assert;

/**
 * @author Artem Stasuk
 */
public class GalleryTest {

    @Test
    public void defaultValues() {
        final DraftGallery gallery = new DraftGallery();

        Assert.assertEquals(1, gallery.getRows());
        Assert.assertEquals(8, gallery.getColumns());
        Assert.assertEquals(55, gallery.getThumbnailHeight());
        Assert.assertEquals(55, gallery.getThumbnailWidth());
        Assert.assertEquals(5, gallery.getCellHorizontalMargin());
        Assert.assertEquals(5, gallery.getCellVerticalMargin());
        Assert.assertEquals(0, gallery.getCellBorderWidth());
        Assert.assertEquals(60, gallery.getCellHeight());
        Assert.assertEquals(60, gallery.getCellWidth());
    }

}
