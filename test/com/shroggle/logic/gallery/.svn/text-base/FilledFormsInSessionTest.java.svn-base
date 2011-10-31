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
package com.shroggle.logic.gallery;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@Ignore
@RunWith(value = TestRunnerWithMockServices.class)
public class FilledFormsInSessionTest {

    private FilledFormsInSession filledFormsInSession = new FilledFormsInSession();

    @Test
    public void testGetSortedFilledFormsFromSession() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setRows(1);
        gallery.setColumns(5);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 30; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);

        Assert.assertNull(filledFormsInSession.get(gallery, null));
        filledFormsInSession.set(gallery, filledForms, null);
        List<Integer> filledFormsIds = filledFormsInSession.get(gallery, null);
        Assert.assertNotNull(filledFormsIds);
        Assert.assertEquals(30, filledFormsIds.size());

        Assert.assertNull(filledFormsInSession.get(null, null));
        Assert.assertNull(filledFormsInSession.get(new DraftGallery(), null));

        Assert.assertEquals(filledForms.get(0).getFilledFormId(), ((int)filledFormsIds.get(0)));
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), ((int)filledFormsIds.get(1)));
        Assert.assertEquals(filledForms.get(2).getFilledFormId(), ((int)filledFormsIds.get(2)));


        Assert.assertEquals(5, filledFormsInSession.reduceSortedItems(filledFormsIds, (gallery.getRows() * gallery.getColumns()), 3).size());

    }
}
