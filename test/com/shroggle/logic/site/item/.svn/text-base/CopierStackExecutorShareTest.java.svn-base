package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;
import com.shroggle.util.copier.stack.CopierStackExecutorMock;
import com.shroggle.util.copier.stack.CopierStackSimple;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class CopierStackExecutorShareTest {

    @Test(expected = IllegalArgumentException.class)
    public void createNullSite() {
        new CopierStackExecutorShare(null, new CopierStackExecutorMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullExecutor() {
        new CopierStackExecutorShare(new Site(), null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeNull() {
        final CopierStackExecutor executor = new CopierStackExecutorShare(
                new Site(), new CopierStackExecutorMock());
        final CopierStack copierStack = new CopierStackSimple(executor);
        copierStack.copy(null);
    }

    public void executeNotDraftItem() {
        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        final CopierStackExecutor executor = new CopierStackExecutorShare(
                new Site(), executorMock);
        final CopierStack copierStack = new CopierStackSimple(executor);
        final WorkText copiedText = copierStack.copy(new WorkText());

        Assert.assertNotNull(copiedText);
        Assert.assertTrue(executorMock.isCalled());
        Assert.assertEquals(0, persistance.getSiteOnItemsByItem(copiedText.getId()).size());
    }

    public void executeDraftItem() {
        TestUtil.createSite();

        final Site site = TestUtil.createSite();
        final DraftText text = new DraftText();
        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        executorMock.setResult(text);
        final CopierStackExecutor executor = new CopierStackExecutorShare(site, executorMock);
        final CopierStack copierStack = new CopierStackSimple(executor);
        final DraftText copiedText = copierStack.copy(text);

        Assert.assertNotNull(copiedText);
        Assert.assertTrue(executorMock.isCalled());

        final List<SiteOnItem> siteOnItems = persistance.getSiteOnItemsByItem(copiedText.getId());
        Assert.assertEquals(1, siteOnItems.size());
        Assert.assertEquals(site, siteOnItems.get(0).getSite());
        Assert.assertEquals(text, siteOnItems.get(0).getItem());
        Assert.assertEquals(SiteOnItemRightType.EDIT, siteOnItems.get(0).getType());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
