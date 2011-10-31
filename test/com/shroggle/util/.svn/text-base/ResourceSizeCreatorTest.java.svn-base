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
package com.shroggle.util;

import org.junit.Test;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
public class ResourceSizeCreatorTest {

    @Test
    public void testExecute_saveProportion() {
        Dimension dimension1 = ResourceSizeCreator.execute(100, 100, 200, 300, true);
        Assert.assertEquals(66, dimension1.getWidth());
        Assert.assertEquals(100, dimension1.getHeight());
        Assert.assertEquals(0.3333333333333333, dimension1.getScaleX());
        Assert.assertEquals(0.3333333333333333, dimension1.getScaleY());

        Dimension dimension2 = ResourceSizeCreator.execute(100, 100, 200, 100, true);
        Assert.assertEquals(100, dimension2.getWidth());
        Assert.assertEquals(50, dimension2.getHeight());
        Assert.assertEquals(0.5, dimension2.getScaleX());
        Assert.assertEquals(0.5, dimension2.getScaleY());

        Dimension dimension3 = ResourceSizeCreator.execute(100, 100, 200, 200, true);
        Assert.assertEquals(100, dimension3.getWidth());
        Assert.assertEquals(100, dimension3.getHeight());
        Assert.assertEquals(0.5, dimension3.getScaleX());
        Assert.assertEquals(0.5, dimension3.getScaleY());

        Dimension dimension4 = ResourceSizeCreator.execute(1000, 1000, 200, 300, true);
        Assert.assertEquals(666, dimension4.getWidth());
        Assert.assertEquals(1000, dimension4.getHeight());
        Assert.assertEquals(3.3333333333333335, dimension4.getScaleX());
        Assert.assertEquals(3.3333333333333335, dimension4.getScaleY());

        Dimension dimension5 = ResourceSizeCreator.execute(1000, 1000, 200, 100, true);
        Assert.assertEquals(1000, dimension5.getWidth());
        Assert.assertEquals(500, dimension5.getHeight());
        Assert.assertEquals(5.0, dimension5.getScaleX());
        Assert.assertEquals(5.0, dimension5.getScaleY());

        Dimension dimension6 = ResourceSizeCreator.execute(1000, 1000, 200, 200, true);
        Assert.assertEquals(1000, dimension6.getWidth());
        Assert.assertEquals(1000, dimension6.getHeight());
        Assert.assertEquals(5.0, dimension6.getScaleX());
        Assert.assertEquals(5.0, dimension6.getScaleY());

        Dimension dimension7 = ResourceSizeCreator.execute(100, 100, 100, 100, true);
        Assert.assertEquals(100, dimension7.getWidth());
        Assert.assertEquals(100, dimension7.getHeight());
        Assert.assertEquals(1.0, dimension7.getScaleX());
        Assert.assertEquals(1.0, dimension7.getScaleY());
    }


    @Test
    public void testExecute_saveProportion_standardVideoSizes() {
        Dimension dimension;

        dimension = ResourceSizeCreator.execute(128, 72, 640, 480, true);
        
        Assert.assertEquals(96, dimension.getWidth());
        Assert.assertEquals(72, dimension.getHeight());
        Assert.assertEquals(0.15, dimension.getScaleX());
        Assert.assertEquals(0.15, dimension.getScaleY());
        //------------------------------------------------------------------------------------
        dimension = ResourceSizeCreator.execute(800, 600, 640, 480, true);

        Assert.assertEquals(800, dimension.getWidth());
        Assert.assertEquals(600, dimension.getHeight());
        Assert.assertEquals(1.25, dimension.getScaleX());
        Assert.assertEquals(1.25, dimension.getScaleY());
        //------------------------------------------------------------------------------------
        dimension = ResourceSizeCreator.execute(720, 576, 640, 480, true);

        Assert.assertEquals(720, dimension.getWidth());
        Assert.assertEquals(540, dimension.getHeight());
        Assert.assertEquals(1.125, dimension.getScaleX());
        Assert.assertEquals(1.125, dimension.getScaleY());
        //------------------------------------------------------------------------------------

        dimension = ResourceSizeCreator.execute(null, 72, 640, 480, true);

        Assert.assertEquals(96, dimension.getWidth());
        Assert.assertEquals(72, dimension.getHeight());
        Assert.assertEquals(0.15, dimension.getScaleX());
        Assert.assertEquals(0.15, dimension.getScaleY());
        //------------------------------------------------------------------------------------

        dimension = ResourceSizeCreator.execute(128, null, 640, 480, true);

        Assert.assertEquals(128, dimension.getWidth());
        Assert.assertEquals(96, dimension.getHeight());
        Assert.assertEquals(0.2, dimension.getScaleX());
        Assert.assertEquals(0.2, dimension.getScaleY());
        //------------------------------------------------------------------------------------
    }

    @Test
    public void testExecute_withoutHeight() {
        Dimension dimension1 = ResourceSizeCreator.execute(100, null, 200, 300, true);
        Dimension dimension2 = ResourceSizeCreator.execute(100, null, 200, 100, true);
        Dimension dimension3 = ResourceSizeCreator.execute(100, null, 200, 200, true);

        Assert.assertEquals(100, dimension1.getWidth());
        Assert.assertEquals(150, dimension1.getHeight());
        Assert.assertEquals(0.5, dimension1.getScaleX());
        Assert.assertEquals(0.5, dimension1.getScaleY());

        Assert.assertEquals(100, dimension2.getWidth());
        Assert.assertEquals(50, dimension2.getHeight());
        Assert.assertEquals(0.5, dimension2.getScaleX());
        Assert.assertEquals(0.5, dimension2.getScaleY());

        Assert.assertEquals(100, dimension3.getWidth());
        Assert.assertEquals(100, dimension3.getHeight());
        Assert.assertEquals(0.5, dimension3.getScaleX());
        Assert.assertEquals(0.5, dimension3.getScaleY());



        Dimension dimension4 = ResourceSizeCreator.execute(1000, null, 200, 300, true);
        Dimension dimension5 = ResourceSizeCreator.execute(1000, null, 200, 100, true);
        Dimension dimension6 = ResourceSizeCreator.execute(1000, null, 200, 200, true);

        Assert.assertEquals(1000, dimension4.getWidth());
        Assert.assertEquals(1500, dimension4.getHeight());
        Assert.assertEquals(5.0, dimension4.getScaleX());
        Assert.assertEquals(5.0, dimension4.getScaleY());

        Assert.assertEquals(1000, dimension5.getWidth());
        Assert.assertEquals(500, dimension5.getHeight());
        Assert.assertEquals(5.0, dimension5.getScaleX());
        Assert.assertEquals(5.0, dimension5.getScaleY());

        Assert.assertEquals(1000, dimension6.getWidth());
        Assert.assertEquals(1000, dimension6.getHeight());
        Assert.assertEquals(5.0, dimension6.getScaleX());
        Assert.assertEquals(5.0, dimension6.getScaleY());
    }

    @Test
    public void testExecute_withoutWidth() {
        Dimension dimension1 = ResourceSizeCreator.execute(null, 100, 200, 300, true);
        Dimension dimension2 = ResourceSizeCreator.execute(null, 100, 200, 100, true);
        Dimension dimension3 = ResourceSizeCreator.execute(null, 100, 200, 200, true);

        Assert.assertEquals(66, dimension1.getWidth());
        Assert.assertEquals(100, dimension1.getHeight());
        Assert.assertEquals(0.3333333333333333, dimension1.getScaleX());
        Assert.assertEquals(0.3333333333333333, dimension1.getScaleY());

        Assert.assertEquals(200, dimension2.getWidth());
        Assert.assertEquals(100, dimension2.getHeight());
        Assert.assertEquals(1.0, dimension2.getScaleX());
        Assert.assertEquals(1.0, dimension2.getScaleY());

        Assert.assertEquals(100, dimension3.getWidth());
        Assert.assertEquals(100, dimension3.getHeight());
        Assert.assertEquals(0.5, dimension3.getScaleX());
        Assert.assertEquals(0.5, dimension3.getScaleY());



        Dimension dimension4 = ResourceSizeCreator.execute(null, 1000, 200, 300, true);
        Dimension dimension5 = ResourceSizeCreator.execute(null, 1000, 200, 100, true);
        Dimension dimension6 = ResourceSizeCreator.execute(null, 1000, 200, 200, true);

        Assert.assertEquals(666, dimension4.getWidth());
        Assert.assertEquals(1000, dimension4.getHeight());
        Assert.assertEquals(3.3333333333333335, dimension4.getScaleX());
        Assert.assertEquals(3.3333333333333335, dimension4.getScaleY());

        Assert.assertEquals(2000, dimension5.getWidth());
        Assert.assertEquals(1000, dimension5.getHeight());
        Assert.assertEquals(10.0, dimension5.getScaleX());
        Assert.assertEquals(10.0, dimension5.getScaleY());

        Assert.assertEquals(1000, dimension6.getWidth());
        Assert.assertEquals(1000, dimension6.getHeight());
        Assert.assertEquals(5.0, dimension6.getScaleX());
        Assert.assertEquals(5.0, dimension6.getScaleY());
    }

    @Test
    public void testExecute_withoutWidthAndHeight() {
        Dimension dimension1 = ResourceSizeCreator.execute(null, null, 200, 300, true);
        Dimension dimension2 = ResourceSizeCreator.execute(null, null, 200, 100, true);
        Dimension dimension3 = ResourceSizeCreator.execute(null, null, 200, 200, true);

        Assert.assertEquals(200, dimension1.getWidth());
        Assert.assertEquals(300, dimension1.getHeight());
        Assert.assertEquals(1.0, dimension1.getScaleX());
        Assert.assertEquals(1.0, dimension1.getScaleY());

        Assert.assertEquals(200, dimension2.getWidth());
        Assert.assertEquals(100, dimension2.getHeight());
        Assert.assertEquals(1.0, dimension2.getScaleX());
        Assert.assertEquals(1.0, dimension2.getScaleY());

        Assert.assertEquals(200, dimension3.getWidth());
        Assert.assertEquals(200, dimension3.getHeight());
        Assert.assertEquals(1.0, dimension3.getScaleX());
        Assert.assertEquals(1.0, dimension3.getScaleY());



        Dimension dimension4 = ResourceSizeCreator.execute(null, null, 200, 300, true);
        Dimension dimension5 = ResourceSizeCreator.execute(null, null, 200, 100, true);
        Dimension dimension6 = ResourceSizeCreator.execute(null, null, 200, 200, true);

        Assert.assertEquals(200, dimension4.getWidth());
        Assert.assertEquals(300, dimension4.getHeight());
        Assert.assertEquals(1.0, dimension4.getScaleX());
        Assert.assertEquals(1.0, dimension4.getScaleY());

        Assert.assertEquals(200, dimension5.getWidth());
        Assert.assertEquals(100, dimension5.getHeight());
        Assert.assertEquals(1.0, dimension5.getScaleX());
        Assert.assertEquals(1.0, dimension5.getScaleY());

        Assert.assertEquals(200, dimension6.getWidth());
        Assert.assertEquals(200, dimension6.getHeight());
        Assert.assertEquals(1.0, dimension6.getScaleX());
        Assert.assertEquals(1.0, dimension6.getScaleY());
    }

    @Test
    public void testExecute_dontSaveProportion() {
        Dimension dimension1 = ResourceSizeCreator.execute(100, 100, 200, 300, false);
        Dimension dimension2 = ResourceSizeCreator.execute(100, 100, 200, 100, false);
        Dimension dimension3 = ResourceSizeCreator.execute(100, 100, 200, 200, false);

        Assert.assertEquals(100, dimension1.getWidth());
        Assert.assertEquals(100, dimension1.getHeight());
        Assert.assertEquals(0.5, dimension1.getScaleX());
        Assert.assertEquals(0.3333333333333333, dimension1.getScaleY());

        Assert.assertEquals(100, dimension2.getWidth());
        Assert.assertEquals(100, dimension2.getHeight());
        Assert.assertEquals(0.5, dimension2.getScaleX());
        Assert.assertEquals(1.0, dimension2.getScaleY());

        Assert.assertEquals(100, dimension3.getWidth());
        Assert.assertEquals(100, dimension3.getHeight());
        Assert.assertEquals(0.5, dimension3.getScaleX());
        Assert.assertEquals(0.5, dimension3.getScaleY());



        Dimension dimension4 = ResourceSizeCreator.execute(1000, 1000, 200, 300, false);
        Dimension dimension5 = ResourceSizeCreator.execute(1000, 1000, 200, 100, false);
        Dimension dimension6 = ResourceSizeCreator.execute(1000, 1000, 200, 200, false);

        Assert.assertEquals(1000, dimension4.getWidth());
        Assert.assertEquals(1000, dimension4.getHeight());
        Assert.assertEquals(5.0, dimension4.getScaleX());
        Assert.assertEquals(3.3333333333333335, dimension4.getScaleY());

        Assert.assertEquals(1000, dimension5.getWidth());
        Assert.assertEquals(1000, dimension5.getHeight());
        Assert.assertEquals(5.0, dimension5.getScaleX());
        Assert.assertEquals(10.0, dimension5.getScaleY());

        Assert.assertEquals(1000, dimension6.getWidth());
        Assert.assertEquals(1000, dimension6.getHeight());
        Assert.assertEquals(5.0, dimension6.getScaleX());
        Assert.assertEquals(5.0, dimension6.getScaleY());
    }
}
