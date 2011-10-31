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
package com.shroggle.presentation.form;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.exception.FormItemNotFoundException;
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.FormItemName;
import com.shroggle.entity.FormItemType;
import com.shroggle.presentation.MockWebContext;

import javax.servlet.ServletException;
import java.io.IOException;

import net.sourceforge.stripes.mock.MockHttpServletRequest;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowAddLinkedFieldServiceTest {

    private ShowAddLinkedFieldService service = new ShowAddLinkedFieldService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecuteCreateNewWithoutTargetForm() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        final ShowAddLinkedFieldRequest request = new ShowAddLinkedFieldRequest();

        Assert.assertEquals("/site/addLinkedField.jsp", service.execute(request));
    }

    @Test
    public void testExecuteCreateNewWithTargetForm() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final DraftForm form = TestUtil.createContactUsForm();

        final ShowAddLinkedFieldRequest request = new ShowAddLinkedFieldRequest();
        request.setTargetFormId(form.getFormId());

        Assert.assertEquals("/site/addLinkedField.jsp", service.execute(request));
        Assert.assertEquals(form.getId(), service.getTargetForm().getId());
    }

    @Test
    public void testExecuteEditSaved() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftForm linkedForm = TestUtil.createContactUsForm();
        final DraftFormItem linkedItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, linkedForm, 0);

        final ShowAddLinkedFieldRequest request = new ShowAddLinkedFieldRequest();
        request.setTargetFormId(form.getFormId());
        request.setFormItemText("item text");
        request.setFormItemDisplayType(FormItemType.RADIOBUTTON);
        request.setLinkedFormItemId(linkedItem.getFormItemId());

        Assert.assertEquals("/site/addLinkedField.jsp", service.execute(request));
        Assert.assertEquals(form.getId(), service.getTargetForm().getId());
        Assert.assertNotNull(service.getSelectedFormItem());
        Assert.assertEquals(0, service.getSelectedFormItem().getFormItemId());
        Assert.assertEquals((int) linkedItem.getFormItemId(), (int) service.getSelectedFormItem().getLinkedFormItemId());
        Assert.assertEquals(FormItemType.RADIOBUTTON, service.getSelectedFormItem().getFormItemDisplayType());
        Assert.assertEquals("item text", service.getSelectedFormItem().getItemName());
        Assert.assertEquals(linkedForm.getId(), service.getSourceForm().getId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutLoginedUser() throws IOException, ServletException {
        service.execute(new ShowAddLinkedFieldRequest());
    }

    @Test(expected = FormNotFoundException.class)
    public void testExecuteWithNotFoundTargetForm() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        final ShowAddLinkedFieldRequest request = new ShowAddLinkedFieldRequest();
        request.setTargetFormId(0);

        service.execute(request);
    }

    @Test
    public void testExecuteWithNotFoundLinkedItemInSavedItem() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final DraftForm form = TestUtil.createContactUsForm();

        final ShowAddLinkedFieldRequest request = new ShowAddLinkedFieldRequest();
        request.setTargetFormId(form.getFormId());
        request.setFormItemText("item text");
        request.setFormItemDisplayType(FormItemType.RADIOBUTTON);
        request.setLinkedFormItemId(999);

        Assert.assertEquals("/site/addLinkedField.jsp", service.execute(request));
        Assert.assertEquals(form.getId(), service.getTargetForm().getId());
        Assert.assertNotNull(service.getSelectedFormItem());
        Assert.assertNull(service.getSourceForm());
    }

}
