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
package com.shroggle.presentation.gallery;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryNavigationUrlCreatorTest {

    @Test
    public void testExecute_DATA_ABOVE_NAVIGATION_BELOW() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW);
        ServiceLocator.getPersistance().putItem(gallery);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);
        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getUserScript();
        String ajaxDispatch = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getAjaxDispatch();
        Assert.assertEquals("return ajaxDispatcher.execute(this);", url);
        Assert.assertEquals("#dispatchGallery" + gallery.getId() + "=SHOW_GALLERY/filledFormId=" + filledForm.getFilledFormId() + "/siteShowOption=" + SiteShowOption.INSIDE_APP + "/widgetId=" + widgetGallery.getWidgetId(), ajaxDispatch);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_SamePage() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryData);
        pageVersion.addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getUserScript();
        String ajaxDispatch = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getAjaxDispatch();
        Assert.assertEquals("return ajaxDispatcher.execute(this);", url);
        Assert.assertEquals("#dispatchGallery" + gallery.getId() + "=SHOW_GALLERY/filledFormId=" + filledForm.getFilledFormId() + "/siteShowOption=" + SiteShowOption.INSIDE_APP + "/widgetId=" + widgetGalleryData.getWidgetId(), ajaxDispatch);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_SamePage_ON_USER_PAGES() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryDataDraft = TestUtil.createWidgetItem();
        widgetGalleryDataDraft.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryDataDraft);
        pageVersion.addWidget(widgetGalleryDataDraft);

        WidgetItem widgetGalleryDataWork = TestUtil.createWidgetItem();
        widgetGalleryDataWork.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryDataWork);
        pageVersion.getWorkPageSettings().addWidget(widgetGalleryDataWork);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(2);
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.ON_USER_PAGES).getUserScript();
        String ajaxDispatch = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.ON_USER_PAGES).getAjaxDispatch();
        Assert.assertEquals("return ajaxDispatcher.execute(this);", url);
        //Asserting that url will be with draft widget.
        Assert.assertEquals("#dispatchGallery" + gallery.getId() + "=SHOW_GALLERY/filledFormId=" + filledForm.getFilledFormId() + "/siteShowOption=" + SiteShowOption.ON_USER_PAGES + "/widgetId=" + widgetGalleryDataDraft.getWidgetId(), ajaxDispatch);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_SamePage_OUTSIDE_APP() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryDataDraft = TestUtil.createWidgetItem();
        widgetGalleryDataDraft.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryDataDraft);
        pageVersion.addWidget(widgetGalleryDataDraft);

        WidgetItem widgetGalleryDataWork = TestUtil.createWidgetItem();
        widgetGalleryDataWork.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryDataWork);
        pageVersion.getWorkPageSettings().addWidget(widgetGalleryDataWork);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(2);
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.OUTSIDE_APP).getUserScript();
        String ajaxDispatch = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.OUTSIDE_APP).getAjaxDispatch();
        Assert.assertEquals("return ajaxDispatcher.execute(this);", url);
        //Asserting that url will be with work widget.
        Assert.assertEquals("#dispatchGallery" + gallery.getId() + "=SHOW_GALLERY/filledFormId=" + filledForm.getFilledFormId() + "/siteShowOption=" + SiteShowOption.OUTSIDE_APP + "/widgetId=" + widgetGalleryDataWork.getWidgetId(), ajaxDispatch);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_SamePage_WITH_NULL_CROSS_WIDGET_IN_GALLERY() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);
        ServiceLocator.getPersistance().putWidget(widgetGalleryData);
        pageVersion.addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(null);
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP);

        Assert.assertNull("The url is null. It means that link is not clickable.", url);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_NotSamePage_INSIDE_APP_showNewWindow() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        Page page2 = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);

        final PageManager pageVersionForGalleryData = TestUtil.createPageVersion(page2);
        pageVersionForGalleryData.addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getUserScript();
        Assert.assertEquals("window.open('showPageVersion.action;jsessionid=null?pageId=3&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=2&gallerySelectedFilledFormId=1&siteShowOption=INSIDE_APP','')", url);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_NotSamePage_INSIDE_APP() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        Page page2 = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);

        final PageManager pageVersionForGalleryData = TestUtil.createPageVersion(page2);
        pageVersionForGalleryData.addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForCurrentWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.INSIDE_APP).getUserScript();
        Assert.assertEquals("window.location = 'showPageVersion.action;jsessionid=null?pageId=3&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=2&gallerySelectedFilledFormId=1&siteShowOption=INSIDE_APP'", url);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_NotSamePage_ON_USER_PAGES() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        Page page2 = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);

        final PageManager pageVersionForGalleryData = TestUtil.createPageVersion(page2);
        pageVersionForGalleryData.addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.ON_USER_PAGES).getUserScript();
        Assert.assertEquals("window.open('showPageVersion.action;jsessionid=null?pageId=3&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=2&gallerySelectedFilledFormId=1&siteShowOption=ON_USER_PAGES','')", url);
    }

    @Test
    public void testExecute_NAVIGATION_ONLY_NotSamePage_OUTSIDE_APP() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        Page page = TestUtil.createPage(site1);
        Page page2 = TestUtil.createPage(site1);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);

        final PageManager pageVersionForGalleryData = TestUtil.createPageVersion(page2, PageVersionType.WORK);
        pageVersionForGalleryData.getWorkPageSettings().setUrl("galleryPageUrl");
        pageVersionForGalleryData.getWorkPageSettings().addWidget(widgetGalleryData);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.OUTSIDE_APP).getUserScript();
        Assert.assertTrue(url.contains("window.open('http://url1.shroggle.com/galleryPageUrl;jsessionid=null?gallerySelectedFilledFormId=1&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=2"));
    }

    @Test
    public void execute_NAVIGATION_ONLY_NotSamePageAndSite_OUTSIDE_APP() {
        final User user = TestUtil.createUserAndLogin("aa");

        final Site galleryDataSite = TestUtil.createSite("title1", "a");

        final Site gallerySite = TestUtil.createSite("title1", "url1");
        final Page page = TestUtil.createPage(gallerySite);
        final PageManager pageVersion = new PageManager(page);
        TestUtil.createUserOnSiteRightActive(user, gallerySite, SiteAccessLevel.ADMINISTRATOR);

        final Page pageGalleryData = TestUtil.createPage(galleryDataSite);
        final PageManager pageVersionGalleryData = TestUtil.createPageVersion(pageGalleryData, PageVersionType.WORK);
        pageVersionGalleryData.getWorkPageSettings().setUrl("wer");

        final WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(2);
        pageVersionGalleryData.getWorkPageSettings().addWidget(widgetGalleryData);


        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(
                pageVersion, gallery.getId(), true, true);

        final FilledForm filledForm = new FilledForm();
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.OUTSIDE_APP).getUserScript();
        Assert.assertTrue(url.contains("window.open('http://a.shroggle.com/wer;jsessionid=null?gallerySelectedFilledFormId=1&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=2"));
    }

    @Test
    public void testExecuteForDataDisplayOnChildSiteBasedOnBlueprint() {
        final User user = TestUtil.createUserAndLogin("aa");
        final Site blueprint = TestUtil.createSite("title1", "a");
        blueprint.setType(SiteType.BLUEPRINT);
        final Page pageBlueprint = TestUtil.createPage(blueprint);
        final PageManager blueprintPageVersion = TestUtil.createPageVersion(pageBlueprint);
        final WidgetItem widgetGalleryDataBlueprint = TestUtil.createWidgetGalleryData();
        blueprintPageVersion.addWidget(widgetGalleryDataBlueprint);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryDataBlueprint.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        final Site gallerySite = TestUtil.createSite("title1", "url1");
        final Page galleryPage = TestUtil.createPage(gallerySite);
        final PageManager galleryPageVersion = TestUtil.createPageVersion(galleryPage);
        TestUtil.createUserOnSiteRightActive(user, gallerySite, SiteAccessLevel.ADMINISTRATOR);
        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(galleryPageVersion, gallery.getId(), true, true);
        galleryPageVersion.addWidget(widgetGallery);

        final DraftForm form = TestUtil.createChildSiteRegistration(gallerySite);
        form.setFormItems(TestUtil.createFormItems(FormItemName.NAME, FormItemName.IMAGE_FILE_UPLOAD));
        gallery.setFormId1(form.getFormId());


        /*-------------------------------------------------Child Site-------------------------------------------------*/
        final User childSiteOwner = TestUtil.createUser();
        final Site childSite = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(childSiteOwner, childSite);

        final Page childSitePage = TestUtil.createPage(childSite);
        final PageManager childSitePageVersion = TestUtil.createPageVersion(childSitePage);
        final WidgetItem childSiteWidgetGalleryData = TestUtil.createWidgetGalleryData();
        childSitePageVersion.addWidget(childSiteWidgetGalleryData);

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFilledForm(filledForm);
        filledForm.setUser(childSiteOwner);
        filledForm.setChildSiteSettingsId(childSite.getChildSiteSettings().getChildSiteSettingsId());
        /*-------------------------------------------------Child Site-------------------------------------------------*/

        String url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.ON_USER_PAGES).getUserScript();

        Assert.assertEquals("window.open('showPageVersion.action;jsessionid=null?pageId=6&selectedGalleryId=" + gallery.getId() + "&gallerySelectedCrossWidgetId=1001&gallerySelectedFilledFormId=1&siteShowOption=ON_USER_PAGES','')",
                url);
    }


    @Test
    public void testExecuteForDataDisplayOnChildSiteBasedOnBlueprint_withoutChildSite() {
        final User user = TestUtil.createUserAndLogin("aa");


        final Site blueprint = TestUtil.createSite("title1", "a");
        blueprint.setType(SiteType.BLUEPRINT);
        final Page pageBlueprint = TestUtil.createPage(blueprint);
        final PageManager blueprintPageVersion = TestUtil.createPageVersion(pageBlueprint);
        final WidgetItem widgetGalleryDataBlueprint = TestUtil.createWidgetGalleryData();
        blueprintPageVersion.addWidget(widgetGalleryDataBlueprint);

        final DraftGallery gallery = new DraftGallery();
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setDataCrossWidgetId(widgetGalleryDataBlueprint.getCrossWidgetId());
        ServiceLocator.getPersistance().putItem(gallery);

        final Site gallerySite = TestUtil.createSite("title1", "url1");
        final Page galleryPage = TestUtil.createPage(gallerySite);
        final PageManager galleryPageVersion = TestUtil.createPageVersion(galleryPage);
        TestUtil.createUserOnSiteRightActive(user, gallerySite, SiteAccessLevel.ADMINISTRATOR);
        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(galleryPageVersion, gallery.getId(), true, true);
        galleryPageVersion.addWidget(widgetGallery);


        DraftForm form = TestUtil.createChildSiteRegistration(gallerySite);
        List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.NAME, FormItemName.IMAGE_FILE_UPLOAD);
        form.setFormItems(formItems);
        gallery.setFormId1(form.getFormId());


        /*-------------------------------------------------Child Site-------------------------------------------------*/
        User childSiteOwner = TestUtil.createUser();
        ChildSiteSettings settings = TestUtil.createChildSiteSettings((DraftChildSiteRegistration) form, new Site());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFilledForm(filledForm);
        filledForm.setUser(childSiteOwner);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());
        /*-------------------------------------------------Child Site-------------------------------------------------*/


        GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForNewWindow(gallery, widgetGallery, filledForm.getFilledFormId(), SiteShowOption.ON_USER_PAGES);

        Assert.assertNull("The url is null. It means that link is not clickable.", url);
    }
}
