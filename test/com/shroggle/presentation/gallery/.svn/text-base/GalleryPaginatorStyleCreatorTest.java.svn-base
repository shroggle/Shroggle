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
package com.shroggle.presentation.gallery;

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.GalleryNavigationPaginatorType;
import com.shroggle.logic.gallery.GalleryPaginatorCreator;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryPaginatorStyleCreatorTest {

    GalleryPaginatorCreator creator = new GalleryPaginatorCreator();

    @Test
    public void testExecute_PREVIOUS_NEXT() {
        PaginationCell[] cells = GalleryPaginatorCreator.createPaginator(1, 9, null);
        GalleryPaginatorStyleCreator.execute(cells, GalleryNavigationPaginatorType.PREVIOUS_NEXT);
        Assert.assertEquals("paginator_cell", cells[0].getCssClassName());
        Assert.assertEquals("selected_paginator_cell", cells[1].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[2].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[3].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[4].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[5].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[6].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[7].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[8].getCssClassName());

        Assert.assertEquals("", cells[0].getStyle());
        Assert.assertEquals("", cells[1].getStyle());
        Assert.assertEquals("", cells[2].getStyle());
        Assert.assertEquals("", cells[3].getStyle());
        Assert.assertEquals("", cells[4].getStyle());
        Assert.assertEquals("", cells[5].getStyle());
        Assert.assertEquals("", cells[6].getStyle());
        Assert.assertEquals("", cells[7].getStyle());
        Assert.assertEquals("", cells[8].getStyle());
    }

    @Test
    public void testExecute_PREVIOUS_NEXT_WITH_NUMBERS() {
        PaginationCell[] cells = GalleryPaginatorCreator.createPaginator(1, 9, null);
        GalleryPaginatorStyleCreator.execute(cells, GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS);
        Assert.assertEquals("paginator_cell", cells[0].getCssClassName());
        Assert.assertEquals("selected_paginator_cell", cells[1].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[2].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[3].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[4].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[5].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[6].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[7].getCssClassName());
        Assert.assertEquals("paginator_cell", cells[8].getCssClassName());

        Assert.assertEquals("", cells[0].getStyle());
        Assert.assertEquals("", cells[1].getStyle());
        Assert.assertEquals("", cells[2].getStyle());
        Assert.assertEquals("", cells[3].getStyle());
        Assert.assertEquals("", cells[4].getStyle());
        Assert.assertEquals("", cells[5].getStyle());
        Assert.assertEquals("", cells[6].getStyle());
        Assert.assertEquals("", cells[7].getStyle());
        Assert.assertEquals("", cells[8].getStyle());
    }


    @Test
    public void testExecute_PREVIOUS_NEXT_WITH_BORDERED_NUMBERS() {
        PaginationCell[] cells = GalleryPaginatorCreator.createPaginator(1, 9, null);
        GalleryPaginatorStyleCreator.execute(cells, GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_BORDERED_NUMBERS);
        Assert.assertEquals("paginator_cell_with_border", cells[0].getCssClassName());
        Assert.assertEquals("selected_paginator_cell_with_border", cells[1].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[2].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[3].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[4].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[5].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[6].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[7].getCssClassName());
        Assert.assertEquals("paginator_cell_with_border", cells[8].getCssClassName());

        Assert.assertEquals("", cells[0].getStyle());
        Assert.assertEquals("color:white", cells[1].getStyle());
        Assert.assertEquals("", cells[2].getStyle());
        Assert.assertEquals("", cells[3].getStyle());
        Assert.assertEquals("", cells[4].getStyle());
        Assert.assertEquals("", cells[5].getStyle());
        Assert.assertEquals("", cells[6].getStyle());
        Assert.assertEquals("", cells[7].getStyle());
        Assert.assertEquals("", cells[8].getStyle());
    }
}
