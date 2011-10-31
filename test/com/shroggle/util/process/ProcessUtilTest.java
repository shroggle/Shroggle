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

package com.shroggle.util.process;

import org.junit.Test;

import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public class ProcessUtilTest {

    @Test
    public void simple() throws IOException, InterruptedException {
        ProcessUtil.execute("ffmpeg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeNullCommand() throws IOException, InterruptedException {
        ProcessUtil.execute(null);
    }

}
