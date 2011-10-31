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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryDataTest {

    @Ignore
    @Test
    public void getItemsWithoutFilledForm() {
        final DraftGallery gallery = new DraftGallery();
        final DraftGalleryItem item = new DraftGalleryItem();
        gallery.addItem(item);
        Assert.assertTrue(new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getCells().length == 0);
    }

    @Test
    public void getBeforeItemsWithoutFilledForm() {
        final DraftGallery gallery = new DraftGallery();
        Assert.assertNull(new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getNavigation().getBeforeItem());
    }

    @Test
    public void getAfterItemsWithoutFilledForm() {
        final DraftGallery gallery = new DraftGallery();
        Assert.assertNull(new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getNavigation().getAfterItem());
    }

    @Test
    public void testGetChildSiteLinkText_withoutFilledForm() {
        final DraftGallery gallery = new DraftGallery();
        String siteIsNotCreated = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("siteIsNotCreated");
        Assert.assertEquals(siteIsNotCreated, new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withoutChildSiteSettingsId() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.setChildSiteSettingsId(null);
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        String siteIsNotCreated = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("siteIsNotCreated");
        Assert.assertEquals(siteIsNotCreated, new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withoutChildSite() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), null);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        String siteIsNotCreated = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("siteIsNotCreated");
        Assert.assertEquals(siteIsNotCreated, new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withoutActiveChildSite() {
        Site childSite = TestUtil.createSite();
        new SiteManager(childSite).setSiteStatus(SiteStatus.SUSPENDED);
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        gallery.setFormId1(form.getFormId());
        String siteIsNotCreated = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("siteIsNotCreated");
        Assert.assertEquals(siteIsNotCreated, new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withActiveChildSite_withoutChildSiteLinkName() {
        Site childSite = TestUtil.createSite();
        new SiteManager(childSite).setSiteStatus(SiteStatus.ACTIVE);
        childSite.setTitle("childSiteTitle");
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        gallery.getChildSiteLink().setChildSiteLinkName(null);
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("Site is not created/active yet.", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withActiveChildSite_withStandardChildSiteLinkName() {
        Site childSite = TestUtil.createSite();
        new SiteManager(childSite).setSiteStatus(SiteStatus.ACTIVE);
        childSite.setTitle("childSiteTitle");
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        String moreInfoAboutChildSite = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("moreInfoAboutChildSite");
        gallery.getChildSiteLink().setChildSiteLinkName(moreInfoAboutChildSite);
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("Site is not created/active yet.", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }

    @Test
    public void testGetChildSiteLinkText_withActiveChildSite_withNotStandardChildSiteLinkName() {
        Site childSite = TestUtil.createSite();
        new SiteManager(childSite).setSiteStatus(SiteStatus.ACTIVE);
        childSite.setTitle("childSiteTitle");
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        gallery.getChildSiteLink().setChildSiteLinkName("adsfkjadshf");
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("Site is not created/active yet.", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLinkText());
    }


    @Test
    public void testGetChildSiteLink_withoutFilledForm() {
        final DraftGallery gallery = new DraftGallery();
        Assert.assertEquals("javascript:void(0);", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLink());
    }

    @Test
    public void testGetChildSiteLink_withoutChildSiteSettingsId() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.setChildSiteSettingsId(null);
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("javascript:void(0);", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLink());
    }

    @Test
    public void testGetChildSiteLink_withoutChildSite() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), null);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("javascript:void(0);", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLink());
    }

    @Test
    public void testGetChildSiteLink_withoutActiveChildSite() {
        Site childSite = TestUtil.createSite();
        new SiteManager(childSite).setSiteStatus(SiteStatus.SUSPENDED);
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        gallery.setFormId1(form.getFormId());
        Assert.assertEquals("javascript:void(0);", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLink());
    }

    @Test
    public void testGetChildSiteLink_withActiveChildSite_withStandardChildSiteLinkName() {
        Site childSite = TestUtil.createSite();
        childSite.setSubDomain("childSite");
        new SiteManager(childSite).setSiteStatus(SiteStatus.ACTIVE);
        childSite.setTitle("childSiteTitle");
        DraftForm form = TestUtil.createChildSiteRegistration(childSite);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), childSite);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        final DraftGallery gallery = TestUtil.createGallery(childSite);
        gallery.getChildSiteLink().setChildSiteLinkName("");
        gallery.setFormId1(form.getFormId());

        
        Assert.assertEquals("javascript:void(0);", new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getChildSiteLink());
    }

    @Test
    public void testGetPaypalButtonAlign() {
        final DraftGallery gallery = TestUtil.createGallery(new Site());
        gallery.getPaypalSettings().setPaypalSettingsAlign(GalleryAlign.LEFT);

        
        Assert.assertEquals(GalleryAlign.LEFT.toString(), new GalleryData(gallery, null, null, SiteShowOption.getDraftOption()).getPaypalButtonAlign());
    }
}
