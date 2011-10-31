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

package com.shroggle.util.process.synchronize.classic;

import com.shroggle.PersistanceMock;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.process.synchronize.Synchronize;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Stasuk Artem
 */
public class ClassicSynchronizeWithConccurentTest {

    private final class UseSynchronizeThread extends Thread {

        public UseSynchronizeThread(SynchronizeRequestEntity request) {
            this.request = request;
        }

        public void run() {
            System.out.println("Start work " + Thread.currentThread());
            try {
                synchronize.execute(request, new SynchronizeContext<Void>() {

                    public Void execute() {
                        System.out.println("Start in synchronize " + Thread.currentThread());
                        if (counter < 0) {
                            counter = Integer.MAX_VALUE;
                            System.out.println("Start sleep in synchronize " + Thread.currentThread());
                            ThreadUtil.sleep(400);
                        }
                        ThreadUtil.sleep(100);
                        System.out.println("Finish in synchornize " + Thread.currentThread());
                        return null;
                    }

                });
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            System.out.println("Finish work " + Thread.currentThread());
        }

        private final SynchronizeRequestEntity request;

    }

    @Before
    public void before() {
        ServiceLocator.setPersistance(new PersistanceMock());
    }

    private final Synchronize synchronize = new ClassicSynchronize();
    private int counter = Integer.MIN_VALUE;

    @Test
    public void execute() throws InterruptedException {
        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(User.class, SynchronizeMethod.WRITE, 11));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(User.class, SynchronizeMethod.WRITE, 11));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Second thread not wait exit with synchronize block first thread!", thread1.isAlive());
    }

    @Test
    public void executeRead() throws InterruptedException {
        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 11));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 11));
        thread2.start();
        thread2.join();

        Assert.assertTrue("Second wait first on read!", thread1.isAlive());
    }

    @Test
    public void executeReadParent() throws InterruptedException {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createPage(site);
        ServiceLocator.getPersistance().putPage(page);

        PageManager pageVersion = new PageManager(page);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        PageManager.class, SynchronizeMethod.READ, pageVersion.getPageId()));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 2));
        thread2.start();
        thread2.join();

        Assert.assertTrue("Second wait first on read!", thread1.isAlive());
    }

    @Test
    public void executeWriteRead() throws InterruptedException {
        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.WRITE, 1));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 1));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Read not wait write!", thread1.isAlive());
    }

    @Test
    public void executeWriteReadParent() throws InterruptedException {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createPage(site);
        ServiceLocator.getPersistance().putPage(page);

        PageManager pageVersion = new PageManager(page);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        PageManager.class, SynchronizeMethod.WRITE, pageVersion.getPageId()));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 12));
        thread2.start();
        thread2.join();

        Assert.assertTrue("Read wait write!", thread1.isAlive());
    }

    @Test
    public void executeReadWrite() throws InterruptedException {
        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, 1));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.WRITE, 1));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Write not wait read!", thread1.isAlive());
    }

    @Test
    public void executeReadWriteParent() throws InterruptedException {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        Page page1 = TestUtil.createPage(site);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        Page.class, SynchronizeMethod.READ, page1.getPageId()));
        thread1.start();
        ThreadUtil.sleep(200);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        Site.class, SynchronizeMethod.WRITE, site.getSiteId()));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Write parent not wait read!", thread1.isAlive());
    }

    @Test
    public void executeBlogReadUserWrite() throws InterruptedException {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(blog);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        DraftBlog.class, SynchronizeMethod.READ, blog.getId()));
        thread1.start();
        ThreadUtil.sleep(200);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        Site.class, SynchronizeMethod.WRITE, site.getSiteId()));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Write site not wait read blog!", thread1.isAlive());
    }

    @Test
    public void executeForumReadUserWrite() throws InterruptedException {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(forum);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        DraftForum.class, SynchronizeMethod.READ, forum.getId()));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        Site.class, SynchronizeMethod.WRITE, site.getSiteId()));
        thread2.start();
        thread2.join();

        Assert.assertFalse("Write site not wait read forum!", thread1.isAlive());
    }

    @Test
    public void executeForumWriteForumWrite() throws InterruptedException {
        DraftForum forum1 = new DraftForum();
        ServiceLocator.getPersistance().putItem(forum1);

        DraftForum forum2 = new DraftForum();
        ServiceLocator.getPersistance().putItem(forum2);

        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        DraftForum.class, SynchronizeMethod.WRITE, forum1.getId()));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        DraftForum.class, SynchronizeMethod.WRITE, forum2.getId()));
        thread2.start();
        thread2.join();

        Assert.assertTrue("Write account wait write other forum!", thread1.isAlive());
    }

    @Test
    public void executeWriteForDifferendKey() throws InterruptedException {
        Thread thread1 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.READ, new Object()));
        thread1.start();
        ThreadUtil.sleep(100);
        Thread thread2 = new UseSynchronizeThread(
                new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.WRITE, new Object()));
        thread2.start();
        thread2.join();

        Assert.assertTrue("Second wait first!", thread1.isAlive());
    }

}