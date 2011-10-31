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
package com.shroggle.logic.form;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.FormFile;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FormFileSizeCreatorTest {

    @Test
    public void testExecute() throws Exception {
        ServiceLocator.setFileSystem(new FileSystemMock());
        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        File imageFile = new File(getClass().getResource("test.png").toURI());
        FileBean fileBean = new FileBean(imageFile, "file", "test.png");

        Assert.assertEquals(0, persistance.getFormFiles().size());

        FormFile file = new FormFile();
        file.setSourceExtension("jpeg");
        file.setSourceName("1.jpeg");
        persistance.putFormFile(file);
        try {
            fileSystem.setResourceStream(file, fileBean.getInputStream());
        } catch (FileSystemException exception) {
        }

        FormFile file2 = new FormFile();
        file2.setSourceExtension("jpeg");
        file2.setSourceName("2.jpeg");
        persistance.putFormFile(file2);


        FormFile file3 = new FormFile();
        file3.setSourceExtension("jpeg");
        file3.setSourceName("3.jpeg");
        persistance.putFormFile(file3);
        try {
            fileSystem.setResourceStream(file3, fileBean.getInputStream());
        } catch (FileSystemException exception) {
        }

        Assert.assertEquals(3, persistance.getFormFiles().size());

        Assert.assertNull(file.getWidth());
        Assert.assertNull(file.getHeight());

        Assert.assertNull(file2.getWidth());
        Assert.assertNull(file2.getHeight());

        Assert.assertNull(file3.getWidth());
        Assert.assertNull(file3.getHeight());


        FormFileSizeCreator.execute();

        Assert.assertEquals(new Integer(800), file.getWidth());
        Assert.assertEquals(new Integer(600), file.getHeight());

        Assert.assertNull(file2.getWidth());
        Assert.assertNull(file2.getHeight());

        Assert.assertEquals(new Integer(800), file3.getWidth());
        Assert.assertEquals(new Integer(600), file3.getHeight());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
