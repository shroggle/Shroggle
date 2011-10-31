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
package com.shroggle;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.ServiceLocator
 * @see com.shroggle.TestUtil
 */
@RunWith(TestRunnerWithMockServices.class)
public abstract class TestBaseWithMockService {

    protected final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    protected final Persistance persistance = ServiceLocator.getPersistance();
    protected final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    protected final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
    protected final FileSystemMock fileSystem = (FileSystemMock) ServiceLocator.getFileSystem();

}
