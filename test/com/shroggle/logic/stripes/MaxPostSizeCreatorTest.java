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
package com.shroggle.logic.stripes;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Balakirev Anatoliy
 *         Date: 29.09.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class MaxPostSizeCreatorTest {

    @Test
    public void testExecute() {
        final Config config = ServiceLocator.getConfigStorage().get();

        /*--------------------------------------------------1245 Gb---------------------------------------------------*/
        config.setMaxPostSize("1245GB");
        Assert.assertEquals(1245 * 1024 * 1024 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1245Gb");
        Assert.assertEquals(1245 * 1024 * 1024 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1245gb");
        Assert.assertEquals(1245 * 1024 * 1024 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1245g");
        Assert.assertEquals(1245 * 1024 * 1024 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((1245 * 1024 * 1024 * 1024L) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*--------------------------------------------------1245 Gb---------------------------------------------------*/


        /*--------------------------------------------------1000 Mb---------------------------------------------------*/
        config.setMaxPostSize("1000MB");
        Assert.assertEquals(1000 * 1024 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1000Mb");
        Assert.assertEquals(1000 * 1024 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1000mb");
        Assert.assertEquals(1000 * 1024 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("1000m");
        Assert.assertEquals(1000 * 1024 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((1000 * 1024 * 1024) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*--------------------------------------------------1000 Mb---------------------------------------------------*/


        /*---------------------------------------------------10 Kb----------------------------------------------------*/
        config.setMaxPostSize("10KB");
        Assert.assertEquals(10 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("10Kb");
        Assert.assertEquals(10 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("10kb");
        Assert.assertEquals(10 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("10k");
        Assert.assertEquals(10 * 1024L, MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((10 * 1024L) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*---------------------------------------------------10 Kb----------------------------------------------------*/

        /*-------------------------------------------------10 bytes---------------------------------------------------*/
        config.setMaxPostSize("10B");
        Assert.assertEquals(10, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("10b");
        Assert.assertEquals(10, MaxPostSizeCreator.createMaxPostSizeInBytes());

        config.setMaxPostSize("10");
        Assert.assertEquals(10, MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((10) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*-------------------------------------------------10 bytes---------------------------------------------------*/


        /*-----------------------------------------Without value in config--------------------------------------------*/
        config.setMaxPostSize(null);
        Assert.assertEquals(MaxPostSizeCreator.getDefaultMaxPostSize(), MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((MaxPostSizeCreator.getDefaultMaxPostSize()) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*-----------------------------------------Without value in config--------------------------------------------*/


        /*---------------------------------------With wrong value in config-------------------------------------------*/
        config.setMaxPostSize("adfasdfwea");
        Assert.assertEquals(MaxPostSizeCreator.getDefaultMaxPostSize(), MaxPostSizeCreator.createMaxPostSizeInBytes());

        Assert.assertEquals((MaxPostSizeCreator.getDefaultMaxPostSize()) + " B", MaxPostSizeCreator.createMaxPostSizeString());
        /*---------------------------------------With wrong value in config-------------------------------------------*/

        
        config.setMaxPostSize("100,2KB");
        Assert.assertEquals(100 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());


        config.setMaxPostSize("1054.2KB");
        Assert.assertEquals(1054 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());


        config.setMaxPostSize("1054.222222KB");
        Assert.assertEquals(1054 * 1024, MaxPostSizeCreator.createMaxPostSizeInBytes());
    }
}
