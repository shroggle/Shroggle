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
package com.shroggle.logic.gallery;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.presentation.gallery.PaginationCell;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;

import java.util.List;
import java.util.ArrayList;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryPaginatorCreatorTest {


    @Test
    public void testCreatePaginatorWith1Page1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 1, null);
        Assert.assertEquals(3, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("Next >", cell[2].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(-1, cell[2].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith2Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 2, null);
        Assert.assertEquals(4, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("Next >", cell[3].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(2, cell[3].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith3Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 3, null);
        Assert.assertEquals(5, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("Next >", cell[4].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(2, cell[4].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith4Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 4, null);
        Assert.assertEquals(6, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("Next >", cell[5].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(2, cell[5].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith5Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 5, null);
        Assert.assertEquals(7, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("Next >", cell[6].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(2, cell[6].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith6Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 6, null);
        Assert.assertEquals(8, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("Next >", cell[7].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(2, cell[7].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith7Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 7, null);
        Assert.assertEquals(9, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("Next >", cell[8].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(2, cell[8].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith8Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 8, null);
        Assert.assertEquals(10, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("Next >", cell[9].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(2, cell[9].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(2, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages2Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(2, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertTrue(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(3, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages3Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(3, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertTrue(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(2, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(4, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages4Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(4, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertTrue(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(3, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(5, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages5Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(5, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertTrue(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(4, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(6, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages6Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(6, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertTrue(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(5, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(7, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages7Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(7, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertTrue(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());

        Assert.assertEquals(6, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(8, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages8Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(8, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertTrue(cell[8].isSelected());

        Assert.assertEquals(7, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(9, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith9Pages9Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(9, 9, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("8", cell[8].getValue());
        Assert.assertEquals("9", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertTrue(cell[9].isSelected());

        Assert.assertEquals(8, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(9, cell[9].getPageNumber());
        Assert.assertEquals(-1, cell[10].getPageNumber());
    }


    @Test
    public void testCreatePaginatorWith10Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 10, null);

        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(2, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages2Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(2, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertTrue(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(3, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages3Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(3, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertTrue(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(2, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(4, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages4Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(4, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertTrue(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(3, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(5, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages5Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(5, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertTrue(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(4, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(6, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages6Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(6, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("4", cell[3].getValue());
        Assert.assertEquals("5", cell[4].getValue());
        Assert.assertEquals("6", cell[5].getValue());
        Assert.assertEquals("7", cell[6].getValue());
        Assert.assertEquals("8", cell[7].getValue());
        Assert.assertEquals("9", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertTrue(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(5, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(3, cell[2].getPageNumber());
        Assert.assertEquals(4, cell[3].getPageNumber());
        Assert.assertEquals(5, cell[4].getPageNumber());
        Assert.assertEquals(6, cell[5].getPageNumber());
        Assert.assertEquals(7, cell[6].getPageNumber());
        Assert.assertEquals(8, cell[7].getPageNumber());
        Assert.assertEquals(9, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(7, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages7Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(7, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("4", cell[3].getValue());
        Assert.assertEquals("5", cell[4].getValue());
        Assert.assertEquals("6", cell[5].getValue());
        Assert.assertEquals("7", cell[6].getValue());
        Assert.assertEquals("8", cell[7].getValue());
        Assert.assertEquals("9", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertTrue(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(6, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(3, cell[2].getPageNumber());
        Assert.assertEquals(4, cell[3].getPageNumber());
        Assert.assertEquals(5, cell[4].getPageNumber());
        Assert.assertEquals(6, cell[5].getPageNumber());
        Assert.assertEquals(7, cell[6].getPageNumber());
        Assert.assertEquals(8, cell[7].getPageNumber());
        Assert.assertEquals(9, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(8, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages8Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(8, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("4", cell[3].getValue());
        Assert.assertEquals("5", cell[4].getValue());
        Assert.assertEquals("6", cell[5].getValue());
        Assert.assertEquals("7", cell[6].getValue());
        Assert.assertEquals("8", cell[7].getValue());
        Assert.assertEquals("9", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertTrue(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(7, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(3, cell[2].getPageNumber());
        Assert.assertEquals(4, cell[3].getPageNumber());
        Assert.assertEquals(5, cell[4].getPageNumber());
        Assert.assertEquals(6, cell[5].getPageNumber());
        Assert.assertEquals(7, cell[6].getPageNumber());
        Assert.assertEquals(8, cell[7].getPageNumber());
        Assert.assertEquals(9, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(9, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages9Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(9, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("4", cell[3].getValue());
        Assert.assertEquals("5", cell[4].getValue());
        Assert.assertEquals("6", cell[5].getValue());
        Assert.assertEquals("7", cell[6].getValue());
        Assert.assertEquals("8", cell[7].getValue());
        Assert.assertEquals("9", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertTrue(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(8, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(3, cell[2].getPageNumber());
        Assert.assertEquals(4, cell[3].getPageNumber());
        Assert.assertEquals(5, cell[4].getPageNumber());
        Assert.assertEquals(6, cell[5].getPageNumber());
        Assert.assertEquals(7, cell[6].getPageNumber());
        Assert.assertEquals(8, cell[7].getPageNumber());
        Assert.assertEquals(9, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(10, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith10Pages10Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(10, 10, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("4", cell[3].getValue());
        Assert.assertEquals("5", cell[4].getValue());
        Assert.assertEquals("6", cell[5].getValue());
        Assert.assertEquals("7", cell[6].getValue());
        Assert.assertEquals("8", cell[7].getValue());
        Assert.assertEquals("9", cell[8].getValue());
        Assert.assertEquals("10", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertTrue(cell[9].isSelected());

        Assert.assertEquals(9, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(3, cell[2].getPageNumber());
        Assert.assertEquals(4, cell[3].getPageNumber());
        Assert.assertEquals(5, cell[4].getPageNumber());
        Assert.assertEquals(6, cell[5].getPageNumber());
        Assert.assertEquals(7, cell[6].getPageNumber());
        Assert.assertEquals(8, cell[7].getPageNumber());
        Assert.assertEquals(9, cell[8].getPageNumber());
        Assert.assertEquals(10, cell[9].getPageNumber());
        Assert.assertEquals(-1, cell[10].getPageNumber());
    }


    @Test
    public void testCreatePaginatorWith100Pages24Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(24, 100, null);

        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("22", cell[3].getValue());
        Assert.assertEquals("23", cell[4].getValue());
        Assert.assertEquals("24", cell[5].getValue());
        Assert.assertEquals("25", cell[6].getValue());
        Assert.assertEquals("26", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("100", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertTrue(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(23, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(21, cell[2].getPageNumber());
        Assert.assertEquals(22, cell[3].getPageNumber());
        Assert.assertEquals(23, cell[4].getPageNumber());
        Assert.assertEquals(24, cell[5].getPageNumber());
        Assert.assertEquals(25, cell[6].getPageNumber());
        Assert.assertEquals(26, cell[7].getPageNumber());
        Assert.assertEquals(27, cell[8].getPageNumber());
        Assert.assertEquals(100, cell[9].getPageNumber());
        Assert.assertEquals(25, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith100Pages1Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(1, 100, null);
        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("2", cell[2].getValue());
        Assert.assertEquals("3", cell[3].getValue());
        Assert.assertEquals("4", cell[4].getValue());
        Assert.assertEquals("5", cell[5].getValue());
        Assert.assertEquals("6", cell[6].getValue());
        Assert.assertEquals("7", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("100", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertTrue(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(-1, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(2, cell[2].getPageNumber());
        Assert.assertEquals(3, cell[3].getPageNumber());
        Assert.assertEquals(4, cell[4].getPageNumber());
        Assert.assertEquals(5, cell[5].getPageNumber());
        Assert.assertEquals(6, cell[6].getPageNumber());
        Assert.assertEquals(7, cell[7].getPageNumber());
        Assert.assertEquals(8, cell[8].getPageNumber());
        Assert.assertEquals(100, cell[9].getPageNumber());
        Assert.assertEquals(2, cell[10].getPageNumber());
    }

    @Test
    public void testCreatePaginatorWith100Pages57Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(57, 100, null);

        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("55", cell[3].getValue());
        Assert.assertEquals("56", cell[4].getValue());
        Assert.assertEquals("57", cell[5].getValue());
        Assert.assertEquals("58", cell[6].getValue());
        Assert.assertEquals("59", cell[7].getValue());
        Assert.assertEquals("...", cell[8].getValue());
        Assert.assertEquals("100", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertTrue(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertFalse(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(56, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(54, cell[2].getPageNumber());
        Assert.assertEquals(55, cell[3].getPageNumber());
        Assert.assertEquals(56, cell[4].getPageNumber());
        Assert.assertEquals(57, cell[5].getPageNumber());
        Assert.assertEquals(58, cell[6].getPageNumber());
        Assert.assertEquals(59, cell[7].getPageNumber());
        Assert.assertEquals(60, cell[8].getPageNumber());
        Assert.assertEquals(100, cell[9].getPageNumber());
        Assert.assertEquals(58, cell[10].getPageNumber());
    }


    @Test
    public void testCreatePaginatorWith100Pages98Selected() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(98, 100, null);

        Assert.assertEquals(11, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("1", cell[1].getValue());
        Assert.assertEquals("...", cell[2].getValue());
        Assert.assertEquals("94", cell[3].getValue());
        Assert.assertEquals("95", cell[4].getValue());
        Assert.assertEquals("96", cell[5].getValue());
        Assert.assertEquals("97", cell[6].getValue());
        Assert.assertEquals("98", cell[7].getValue());
        Assert.assertEquals("99", cell[8].getValue());
        Assert.assertEquals("100", cell[9].getValue());
        Assert.assertEquals("Next >", cell[10].getValue());
        Assert.assertFalse(cell[1].isSelected());
        Assert.assertFalse(cell[2].isSelected());
        Assert.assertFalse(cell[3].isSelected());
        Assert.assertFalse(cell[4].isSelected());
        Assert.assertFalse(cell[5].isSelected());
        Assert.assertFalse(cell[6].isSelected());
        Assert.assertTrue(cell[7].isSelected());
        Assert.assertFalse(cell[8].isSelected());
        Assert.assertFalse(cell[9].isSelected());

        Assert.assertEquals(97, cell[0].getPageNumber());
        Assert.assertEquals(1, cell[1].getPageNumber());
        Assert.assertEquals(93, cell[2].getPageNumber());
        Assert.assertEquals(94, cell[3].getPageNumber());
        Assert.assertEquals(95, cell[4].getPageNumber());
        Assert.assertEquals(96, cell[5].getPageNumber());
        Assert.assertEquals(97, cell[6].getPageNumber());
        Assert.assertEquals(98, cell[7].getPageNumber());
        Assert.assertEquals(99, cell[8].getPageNumber());
        Assert.assertEquals(100, cell[9].getPageNumber());
        Assert.assertEquals(99, cell[10].getPageNumber());
    }


    @Test
    public void testCreatePaginatorWith100Pages57Selected_PREVIOUS_NEXT() {
        PaginationCell[] cell = GalleryPaginatorCreator.createPaginator(57, 100, GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(2, cell.length);
        Assert.assertEquals("< Previous", cell[0].getValue());
        Assert.assertEquals("Next >", cell[1].getValue());
        Assert.assertFalse(cell[0].isSelected());
        Assert.assertFalse(cell[1].isSelected());

        Assert.assertEquals(56, cell[0].getPageNumber());
        Assert.assertEquals(58, cell[1].getPageNumber());
    }

    @Test
    public void createPaginator_PREVIOUS_NEXT_WITH_NUMBERS() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 20; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS);


        PaginationCell[] paginationCells = GalleryPaginatorCreator.createPaginator(gallery, null, 1, SiteShowOption.getDraftOption());
        Assert.assertNotNull(paginationCells);
        Assert.assertEquals(5, paginationCells.length);
    }

    @Test
    public void createPaginator_PREVIOUS_NEXT() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 20; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);


        PaginationCell[] paginationCells = GalleryPaginatorCreator.createPaginator(gallery, null, 1, SiteShowOption.getDraftOption());
        Assert.assertNotNull(paginationCells);
        Assert.assertEquals(2, paginationCells.length);
    }

    @Test
    public void createPaginator_withColumnsNumber0() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(0);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 20; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREV_NEXT);

        Assert.assertNull(GalleryPaginatorCreator.createPaginator(gallery, null, 1, SiteShowOption.getDraftOption()));
    }

}
