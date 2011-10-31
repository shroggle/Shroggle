package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.stack.*;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class CopierStackExecutorFirstTest {

    @Test(expected = IllegalArgumentException.class)
    public void executeNull() {
        new CopierStackExecutorFirst(null, new CopierStackExecutorMock(), new CopierStackExecutorMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeNullCopier() {
        new CopierStackExecutorFirst(new DraftText(), null, new CopierStackExecutorMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeNullShare() {
        new CopierStackExecutorFirst(new DraftText(), new CopierStackExecutorMock(), null);
    }

    @Test
    public void executeGallery() {
        final DraftCustomForm customForm = new DraftCustomForm();
        persistance.putItem(customForm);

        final DraftFormItem formItem = new DraftFormItem();
        customForm.addFormItem(formItem);
        persistance.putFormItem(formItem);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(customForm.getId());

        final DraftGalleryItem galleryItem = new DraftGalleryItem();
        galleryItem.getId().setFormItemId(formItem.getFormItemId());
        gallery.addItem(galleryItem);

        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        executorMock.setResult(new Object());

        final CopierStackExecutor executor = new CopierStackExecutorFirst(
                gallery, executorMock, new CopierStackExecutorNone());
        final CopierStack copierStack = new CopierStackSimple(executor);

        copierStack.copy(gallery);
        Assert.assertTrue(executorMock.isCalled());

        executorMock.setCalled(false);
        copierStack.copy(customForm);
        Assert.assertFalse(executorMock.isCalled());

        executorMock.setCalled(false);
        copierStack.copy(formItem);
        Assert.assertFalse(executorMock.isCalled());
    }

    @Test
    public void executeForm() {
        final DraftCustomForm customForm = new DraftCustomForm();
        persistance.putItem(customForm);

        final DraftFormItem formItem = new DraftFormItem();
        customForm.addFormItem(formItem);
        persistance.putFormItem(formItem);

        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        executorMock.setResult(new Object());

        final CopierStackExecutor executor = new CopierStackExecutorFirst(
                customForm, executorMock, new CopierStackExecutorNone());
        final CopierStack copierStack = new CopierStackSimple(executor);

        copierStack.copy(customForm);
        Assert.assertTrue(executorMock.isCalled());

        executorMock.setCalled(false);
        copierStack.copy(formItem);
        Assert.assertTrue("We need copy form item!", executorMock.isCalled());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
