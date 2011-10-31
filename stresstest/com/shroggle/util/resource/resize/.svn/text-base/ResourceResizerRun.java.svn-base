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
package com.shroggle.util.resource.resize;

import com.shroggle.entity.ResourceSize;
import com.shroggle.entity.ResourceSizeCustom;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceResizerRun {

    public static void main(String[] args) throws FileNotFoundException {
        ServiceLocator.setTimeCounterCreator(new TimeCounterCreatorSimple());
        final ResourceSize resourceSize = ResourceSizeCustom.create(800, null, true);

        wrapToTimeCounter(new ResourceResizerStandart()).execute(
                new FileInputStream("c:/artem/original.jpg"), new FileOutputStream("c:/artem/standart.jpg"), "jpg", resourceSize);

        wrapToTimeCounter(new ResourceResizerHighQuality()).execute(
                new FileInputStream("c:/artem/original.jpg"), new FileOutputStream("c:/artem/high.jpg"), "jpg", resourceSize);

//        wrapToTimeCounter(new ResourceResizerStandart()).execute(
//                new FileInputStream("c:/test.png"), new FileOutputStream("c:/small.png"), "png", resourceSize);
//        wrapToTimeCounter(new ResourceResizerHighQuality()).execute(
//                new FileInputStream("c:/test.png"), new FileOutputStream("c:/smallhq.png"), "png", resourceSize);

        printTimeCounters();
    }

    private static ResourceResizer wrapToTimeCounter(final ResourceResizer resourceResizer) {
        return new ResourceResizerTimeCounter(resourceResizer);
    }

    private static void printTimeCounters() {
        for (final TimeCounterResult timeCounterResult : ServiceLocator.getTimeCounterCreator().getResults()) {
            System.out.println(timeCounterResult.getName() + ": " + timeCounterResult.getExecutedTime() + " msec");
        }
    }

}
