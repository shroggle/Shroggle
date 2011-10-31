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

import com.shroggle.entity.*;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.advancedSearch.resultsNumber.AdvancedSearchResultsNumberCache;
import com.shroggle.logic.customtag.CustomTagFacade;
import com.shroggle.logic.customtag.CustomTagProcessorNone;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.paypal.PaypalButtonIPNRequestStorage;
import com.shroggle.logic.site.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerMock;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.PaypalPaymentInfoRequestStorage;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.MockSessionStorage;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.MockWebContextGetter;
import com.shroggle.presentation.ResolutionCreatorMock;
import com.shroggle.presentation.site.RenderedPageHtmlProviderMock;
import com.shroggle.presentation.site.cssParameter.CssParameter;
import com.shroggle.util.IOUtil;
import com.shroggle.util.NowTimeMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigSmtp;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.context.ContextManual;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.copier.CopierMock;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.filesystem.FileSystemRealTest;
import com.shroggle.util.filesystem.fileWriter.AsynchronousFilesWriter;
import com.shroggle.util.html.HtmlGetterMock;
import com.shroggle.util.html.optimization.PageResourcesMockAccelerator;
import com.shroggle.util.html.processor.simple.HtmlProcessorRegex;
import com.shroggle.util.international.International;
import com.shroggle.util.international.parameters.InternationalStorageParameters;
import com.shroggle.util.international.property.InternationalStoragePropertyBundle;
import com.shroggle.util.journal.JournalMock;
import com.shroggle.util.mail.MailAddressNoInternetUseValidator;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.payment.authorize.AuthorizeNetMock;
import com.shroggle.util.payment.javien.JavienMock;
import com.shroggle.util.payment.paypal.PayPalMock;
import com.shroggle.util.persistance.MockPersistanceTransaction;
import com.shroggle.util.process.SystemConsoleMock;
import com.shroggle.util.process.synchronize.NoSynchronize;
import com.shroggle.util.resource.ResourceGetterUrlInternal;
import com.shroggle.util.resource.ResourceProducerMock;
import com.shroggle.util.testhelp.TestHelpStorage;
import com.shroggle.util.transcode.VideoTranscodeMock;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Contains most popular method for create environment for tests.
 *
 * @author Stasuk Artem
 */
public class TestUtil {

    static {
        /*
        set this options for test code in server enviroment without monitor, mouse and other
        devices. http://java.sun.com/developer/technicalArticles/J2SE/Desktop/headless/
        It's the headless mode.
         */
        System.setProperty("java.awt.headless", "true");
    }

    public static void assertBigIntAndInt(final Integer int1, final int int2) {
        Assert.assertEquals(int1, (Integer) int2);
    }

    public static void assertIntAndBigInt(final int int1, final Integer int2) {
        Assert.assertEquals((Integer) int1, int2);
    }

    public static Visit createVisitForPageVisitor(final PageVisitor pageVisitor, final Page page) {
        return createVisitForPageVisitor(pageVisitor, page, new Date());
    }

    public static String createString(final int size) {
        StringBuilder string = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            string.append("Z");
        }
        return string.toString();
    }

    public static Vote createVote(final int galleryId, final int filledFormId, final int userId) {
        final Vote vote = new Vote();
        vote.setGalleryId(galleryId);
        vote.setFilledFormId(filledFormId);
        vote.setUserId(userId);

        ServiceLocator.getPersistance().putVote(vote);
        return vote;
    }

    public static void castVote(final Vote vote, final int voteValue) {
        vote.setVoteDate(new Date());
        vote.setVoteValue(voteValue);
    }

    public static Style createStyle(String styleName, StyleType styleType, MeasureUnit measureUnit, String styleValue) {
        Style style = new Style();
        style.setName(styleName);
        style.setType(styleType);

        MeasureUnits widthMeasureUnit = new MeasureUnits();
        widthMeasureUnit.setTopMeasureUnit(measureUnit);
        widthMeasureUnit.setRightMeasureUnit(measureUnit);
        widthMeasureUnit.setBottomMeasureUnit(measureUnit);
        widthMeasureUnit.setLeftMeasureUnit(measureUnit);
        style.setMeasureUnits(widthMeasureUnit);

        StyleValue styleValues = new StyleValue();
        styleValues.setTopValue(styleValue);
        styleValues.setRightValue(styleValue);
        styleValues.setBottomValue(styleValue);
        styleValues.setLeftValue(styleValue);
        style.setValues(styleValues);
        ServiceLocator.getPersistance().putStyle(style);
        return style;
    }

    public static Visit createVisitForPageVisitor(PageVisitor pageVisitor, Page page, Date date) {
        Visit visit = new Visit();
        visit.setVisitedPage(page);
        visit.setVisitCreationDate(date);
        visit.setVisitCount(1);
        visit.setOverallTimeOfVisit(1);
        visit.addReferrerURL("ref_url");
        visit.addReferrerSearchTerm("search_term");
        visit.setPageVisitor(pageVisitor);
        ServiceLocator.getPersistance().putVisit(visit);
        page.addVisit(visit);
        pageVisitor.addVisit(visit);

        return visit;
    }

    public static PageVisitor createPageVisitor() {
        PageVisitor pageVisitor = new PageVisitor();
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        return pageVisitor;
    }

    public static DraftMenu createMenu() {
        DraftMenu menu = new DraftMenu();
        ServiceLocator.getPersistance().putMenu(menu);
        return menu;
    }

    public static DraftMenu createMenu(final Site site) {
        DraftMenu menu = new DraftMenu();
        ServiceLocator.getPersistance().putMenu(menu);
        site.setMenu(menu);
        menu.setSiteId(site.getSiteId());
        return menu;
    }

    public static ImageForVideo createImageForVideo(final int siteId, final int width, final int height) {
        ImageForVideo imageForVideo = new ImageForVideo();
        imageForVideo.setCreated(new Date());
        imageForVideo.setWidth(width);
        imageForVideo.setHeight(height);
        imageForVideo.setTitle("title");
        imageForVideo.setSiteId(siteId);
        ServiceLocator.getPersistance().putImageForVideo(imageForVideo);
        return imageForVideo;
    }

    public static MenuImage createMenuImage(final int siteId) {
        MenuImage menuImage = new MenuImage();
        menuImage.setName("image");
        menuImage.setExtension("png");
        menuImage.setSiteId(siteId);
        ServiceLocator.getPersistance().putMenuImage(menuImage);
        ServiceLocator.getFileSystem().setResourceStream(menuImage, TestUtil.getTempImageStream());
        return menuImage;
    }

    public static User createUserAndUserOnSiteRightAndLogin(final Site site, final SiteAccessLevel accessLevel) {
        final User user = createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, site, accessLevel);
        return user;
    }

    public static <T extends FormItem> List<T> createFormItems(final FormItemName... formItemNames) {
        final List<T> formItems = new ArrayList<T>();
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
        for (int i = 0; i < formItemNames.length; i++) {
            final DraftFormItem formItem = new DraftFormItem();

            formItem.setPosition(i);
            formItem.setFormItemName(formItemNames[i]);
            formItem.setItemName(international.get(formItemNames[i].toString() + "_FN"));
            formItems.add((T) formItem);
            ServiceLocator.getPersistance().putFormItem(formItem);
        }
        return formItems;
    }

    public static List<FilledFormItem> createFilledFormItems(FormItemName... itemNames) {
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        for (FormItemName itemName : itemNames) {
            filledFormItems.add(createFilledFormItem(itemName));
        }
        return filledFormItems;
    }

    public static FilledFormItem createFilledFormItem(FormItemName itemName) {
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
        FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setItemName(international.get(itemName.toString() + "_FN"));
        filledFormItem.setFormItemName(itemName);
        List<String> values = new ArrayList<String>();
        values.add("value");
        filledFormItem.setValues(values);
        ServiceLocator.getPersistance().putFilledFormItem(filledFormItem);

        return filledFormItem;
    }

    public static FormFile createFormFile(final String name, final int siteId) {
        FormFile file = new FormFile();
        file.setSourceName(name);
        file.setSourceExtension("jpeg");
        file.setSiteId(siteId);
        ServiceLocator.getPersistance().putFormFile(file);
        ServiceLocator.getFileSystem().setResourceStream(file, TestUtil.getTempImageStream());
        return file;
    }

    public static FormVideo createFormVideo(final Integer videoId, final Integer imageId) {
        FormVideo formVideo = new FormVideo();
        formVideo.setVideoId(videoId);
        formVideo.setImageId(imageId);
        ServiceLocator.getPersistance().putFormVideo(formVideo);
        return formVideo;
    }


    public static FilledFormItem createFilledFormItem(final int formItemId, final FormItemName itemName, final String value) {
        final FilledFormItem filledFormItem = new FilledFormItem();
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
        filledFormItem.setItemName(international.get(itemName.toString() + "_FN"));
        filledFormItem.setFormItemName(itemName);
        List<String> values = new ArrayList<String>();
        if (itemName.getType() == FormItemType.FILE_UPLOAD) {
            FormFile file = createFormFile(value, -1);
            values.add(String.valueOf(file.getFormFileId()));
        } else {
            values.add(value);
        }
        filledFormItem.setValues(values);
        filledFormItem.setFormItemId(formItemId);
        ServiceLocator.getPersistance().putFilledFormItem(filledFormItem);
        return filledFormItem;
    }

    public static FilledForm createFilledRegistrationForm(final User user, final Site site, final int registratioFormId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setType(FormType.REGISTRATION);
        filledForm.setUser(user);
        filledForm.setFilledFormItems(createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        filledForm.setFormId(registratioFormId);
        ServiceLocator.getPersistance().putFilledForm(filledForm);

        final UserOnSiteRight userOnSiteRight = createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.addFilledRegistrationFormId(filledForm.getFilledFormId());
        return filledForm;
    }

    public static FilledForm createFilledFormByFormId(final int formId, final List<FilledFormItem> items) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setFormId(formId);
        filledForm.setType(FormType.CUSTOM_FORM);
        filledForm.setFilledFormItems(items);
        ServiceLocator.getPersistance().putFilledForm(filledForm);

        return filledForm;
    }

    public static FilledForm createFilledChildSiteRegistrationForm(User user) {
        return createFilledChildSiteRegistrationFormForVisitor(user, -1);
    }

    public static FilledForm createFilledChildSiteRegistrationFormForVisitor(User user, final int formId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setType(FormType.CHILD_SITE_REGISTRATION);
        filledForm.setUser(user);
        filledForm.setFilledFormItems(createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        filledForm.setFormId(formId);

        ServiceLocator.getPersistance().putFilledForm(filledForm);

        user.addFilledForm(filledForm);
        return filledForm;
    }

    public static FilledForm createDefaultRegistrationFilledFormForVisitorAndFormId(User user, Site site, int formId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setType(FormType.REGISTRATION);
        filledForm.setUser(user);
        filledForm.setFilledFormItems(createDefaultRegistrationFilledFormItems());
        filledForm.setFormId(formId);
        ServiceLocator.getPersistance().putFilledForm(filledForm);

        final UserOnSiteRight visitorOnNewSiteRights = new UserOnSiteRight();
        visitorOnNewSiteRights.setSiteAccessType(SiteAccessLevel.VISITOR);
        visitorOnNewSiteRights.addFilledRegistrationFormId(filledForm.getFilledFormId());
        visitorOnNewSiteRights.setVisitorStatus(VisitorStatus.REGISTERED);
        site.addUserOnSiteRight(visitorOnNewSiteRights);
        user.addUserOnSiteRight(visitorOnNewSiteRights);

        ServiceLocator.getPersistance().putUserOnSiteRight(visitorOnNewSiteRights);

        return filledForm;
    }

    public static FilledForm createDefaultRegistrationFilledFormForMasterVisitor(User user) {
        final FilledForm filledForm = new FilledForm();
        final International formInternational = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        filledForm.setType(FormType.REGISTRATION);
        filledForm.setUser(user);

        FilledFormItem emailItem = new FilledFormItem();
        emailItem.setItemName(formInternational.get(FormItemName.EMAIL.toString() + "_FN"));
        List<String> values = new ArrayList<String>();
        values.add(StringUtil.getEmptyOrString(user.getEmail()));
        emailItem.setValues(values);

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(formInternational.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(StringUtil.getEmptyOrString(user.getFirstName()));
        firstNameItem.setValues(values);

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setItemName(formInternational.get(FormItemName.LAST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(StringUtil.getEmptyOrString(user.getLastName()));
        lastNameItem.setValues(values);

        FilledFormItem screenNameItem = new FilledFormItem();
        screenNameItem.setItemName(formInternational.get(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(StringUtil.getEmptyOrString(user.getScreenName()));
        screenNameItem.setValues(values);

        FilledFormItem telephone = new FilledFormItem();
        telephone.setItemName(formInternational.get(FormItemName.TELEPHONE.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(StringUtil.getEmptyOrString(user.getTelephone()));
        telephone.setValues(values);

        FilledFormItem title = new FilledFormItem();
        title.setItemName(formInternational.get(FormItemName.TITLE.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("some text");
        title.setValues(values);

        filledForm.addFilledFormItem(emailItem);
        filledForm.addFilledFormItem(firstNameItem);
        filledForm.addFilledFormItem(lastNameItem);
        filledForm.addFilledFormItem(screenNameItem);
        filledForm.addFilledFormItem(title);
        filledForm.addFilledFormItem(telephone);

        ServiceLocator.getPersistance().putFilledForm(filledForm);

        return filledForm;
    }

    public static List<FilledFormItem> createDefaultRegistrationFilledFormItems() {
        return TestUtil.createDefaultRegistrationFilledFormItems("My First Name", "My Last Name", "My Screen Name", "My Telephone Number", "qwe@qwe.qwe");
    }

    public static List<FilledFormItem> createDefaultRegistrationFilledFormItems(String firstName, String lastName, String screenName, String telephoneNumber, String email) {
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        final International formInternational = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        FilledFormItem emailItem = new FilledFormItem();
        emailItem.setItemName(formInternational.get(FormItemName.EMAIL.toString() + "_FN"));
        List<String> values = new ArrayList<String>();
        values.add(email);
        emailItem.setValues(values);
        emailItem.setFormItemName(FormItemName.EMAIL);
        filledFormItems.add(emailItem);

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(formInternational.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(firstName);
        firstNameItem.setValues(values);
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        filledFormItems.add(firstNameItem);

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setItemName(formInternational.get(FormItemName.LAST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(lastName);
        lastNameItem.setValues(values);
        lastNameItem.setFormItemName(FormItemName.LAST_NAME);
        filledFormItems.add(lastNameItem);

        FilledFormItem screenNameItem = new FilledFormItem();
        screenNameItem.setItemName(formInternational.get(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(screenName);
        screenNameItem.setValues(values);
        screenNameItem.setFormItemName(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME);
        filledFormItems.add(screenNameItem);

        FilledFormItem telephone = new FilledFormItem();
        telephone.setItemName(formInternational.get(FormItemName.TELEPHONE.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add(telephoneNumber);
        telephone.setValues(values);
        telephone.setFormItemName(FormItemName.TELEPHONE);
        filledFormItems.add(telephone);

        FilledFormItem notIncludedField = new FilledFormItem();
        notIncludedField.setItemName(formInternational.get(FormItemName.TITLE.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("some text");
        notIncludedField.setValues(values);
        notIncludedField.setFormItemName(FormItemName.TITLE);
        filledFormItems.add(notIncludedField);

        return filledFormItems;
    }

    public static FilledForm createFilledContactUsForm(User user, List<FilledFormItem> filledFormItems, int formId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setType(FormType.CONTACT_US);
        filledForm.setFilledFormItems(filledFormItems);

        ServiceLocator.getPersistance().putFilledForm(filledForm);
        user.addFilledForm(filledForm);
        filledForm.setFormId(formId);

        return filledForm;
    }

    public static FilledForm createFilledContactUsForm(User user) {
        return createFilledContactUsForm(user, createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), -1);
    }

    public static FilledForm createFilledContactUsForm(User user, int formId) {
        return createFilledContactUsForm(user, createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), formId);
    }

    public static FilledForm createFilledContactUsForm(User user, List<FilledFormItem> filledFormItems, DraftForm form) {
        return createFilledContactUsForm(user, filledFormItems, form.getFormId());
    }

    public static FilledForm createCustomForm(User user, List<FilledFormItem> filledFormItems, DraftForm form) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setType(FormType.CUSTOM_FORM);
        filledForm.setUser(user);
        filledForm.setFilledFormItems(filledFormItems);
        filledForm.setFormId(form.getFormId());

        ServiceLocator.getPersistance().putFilledForm(filledForm);
        user.addFilledForm(filledForm);

        return filledForm;
    }

    public static DraftGallery createGallery(final Site site) {
        final String name = SiteItemsManager.getNextDefaultName(ItemType.GALLERY, site.getSiteId());
        return createGallery(site.getSiteId(), name, "commentsNotes" + name);
    }

    public static DraftAdvancedSearch createAdvancedSearch(final Site site) {
        final String name = SiteItemsManager.getNextDefaultName(ItemType.ADVANCED_SEARCH, site.getSiteId());
        final DraftAdvancedSearch draftAdvancedSearch = new DraftAdvancedSearch();
        draftAdvancedSearch.setName(name);
        draftAdvancedSearch.setSiteId(site.getSiteId());

        ServiceLocator.getPersistance().putItem(draftAdvancedSearch);
        return draftAdvancedSearch;
    }

    public static DraftGallery createGallery(final int siteId, final String name) {
        return createGallery(siteId, name, "");
    }

    public static DraftGallery createGallery(final int siteId, final String name, final String commentsNotes) {
        final DraftGallery gallery = new DraftGallery();
        gallery.setName(name);
        gallery.setDescription(commentsNotes);
        gallery.setSiteId(siteId);
        ServiceLocator.getPersistance().putItem(gallery);
        return gallery;
    }

    public static DraftGallery createGallery(final int siteId, final String name, final String commentsNotes, final DraftForm form) {
        final DraftGallery gallery = createGallery(siteId, name, commentsNotes);
        gallery.setFormId1(form.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);
        return gallery;
    }

    public static User createUserAndUserOnSiteRight(final Site site, final SiteAccessLevel accountAccessType) {
        final User user = createUser();
        TestUtil.createUserOnSiteRightActive(user, site, accountAccessType);
        return user;
    }

    public static User createUserAndUserOnSiteRight(final Site site) {
        return createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
    }

    public static UserOnSiteRight createUserOnSiteRightActiveAdmin(final User user, final Site site) {
        return createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
    }

    public static UserOnSiteRight createUserOnSiteRightActive(final User user, final Site site, final SiteAccessLevel accessLevel) {
        return createUserOnSiteRight(user, site, accessLevel, true);
    }

    public static UserOnSiteRight createUserOnSiteRightInactiveAdmin(final User user, final Site site) {
        return createUserOnSiteRightInactive(user, site, SiteAccessLevel.ADMINISTRATOR);
    }

    public static UserOnSiteRight createUserOnSiteRightInactive(final User user, final Site site, final SiteAccessLevel accessLevel) {
        return createUserOnSiteRight(user, site, accessLevel, false);
    }

    private static UserOnSiteRight createUserOnSiteRight(final User user, final Site site,
                                                         final SiteAccessLevel accessLevel, final boolean setActive) {
        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setSiteAccessType(accessLevel);
        userOnSiteRight.setActive(setActive);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        ServiceLocator.getPersistance().putUserOnSiteRight(userOnSiteRight);
        return userOnSiteRight;
    }

    public static SiteOnItem createSiteOnItemRight(final Site site, final DraftItem item) {
        return createSiteOnItemRight(site, item, SiteOnItemRightType.EDIT);
    }

    public static SiteOnItem createSiteOnItemRight(final Site site, final DraftItem item, final SiteOnItemRightType rightType) {
        final SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setType(rightType);
        siteOnItem.setAcceptDate(new Date());
        siteOnItem.setAcceptCode("");
        siteOnItem.setFromBlueprint(false);
        siteOnItem.setSendDate(new Date());
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(item);
        ServiceLocator.getPersistance().putSiteOnItem(siteOnItem);
        return siteOnItem;
    }

    //Difference between visitor and user is that visitor has registration form in his rights for site

    public static User createVisitorForSite(final Site site) {
        return createVisitorForSite(site, false, VisitorStatus.REGISTERED);
    }

    //Difference between visitor and user is that visitor has registration form in his rights for site

    public static User createVisitorForSite(final Site site, final boolean invited) {
        return createVisitorForSite(site, invited, VisitorStatus.REGISTERED);
    }

    //Difference between visitor and user is that visitor has registration form in his rights for site

    public static User createVisitorForSite(final Site site, final boolean invited, final VisitorStatus visitorStatus) {
        final User user = createVisitorForSite(site, invited, visitorStatus, null);
        final DraftForm form = TestUtil.createRegistrationForm();
        ServiceLocator.getPersistance().getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId()).
                addFilledRegistrationFormId(TestUtil.createFilledForm(form.getFormId()).getFilledFormId());

        return user;
    }

    public static User createVisitorForSite(final Site site, int formId) {
        final User user = createVisitorForSite(site, false, VisitorStatus.REGISTERED, null);
        ServiceLocator.getPersistance().getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId()).
                addFilledRegistrationFormId(TestUtil.createFilledForm(formId).getFilledFormId());

        return user;
    }

    //Difference between visitor and user is that visitor has registration form in his rights for site.
    //Please, do not use with empty filledForm this is wrong, aslo, please, do not insert check's if the filled form == null, this is also wrong 'cos
    //we need it for upper method.

    public static User createVisitorForSite(final Site site, final boolean invited, final VisitorStatus visitorStatus, final FilledForm filledForm) {
        final User user = createUser();

        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setInvited(invited);
        userOnSiteRight.setVisitorStatus(visitorStatus);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.VISITOR);
        userOnSiteRight.setActive(true);

        if (filledForm != null) {
            userOnSiteRight.addFilledRegistrationFormId(filledForm.getFilledFormId());
        }

        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        ServiceLocator.getPersistance().putUserOnSiteRight(userOnSiteRight);

        return user;
    }

    public static User createUser() {
        return createUser("");
    }

    public static User createUser(final String email) {
        final User user = new User();
        user.setPassword("1");
        user.setEmail(email);
        user.setActiveted(new Date());
        user.setRegistrationDate(user.getActiveted());
        ServiceLocator.getPersistance().putUser(user);
        return user;
    }

    public static User createUser(final String firstName, final String lastName) {
        final User user = new User();
        user.setPassword("1");
        user.setEmail("email");
        user.setActiveted(new Date());
        user.setRegistrationDate(user.getActiveted());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        ServiceLocator.getPersistance().putUser(user);
        return user;
    }

    public static Video createVideoForSite(final String name, final Integer siteId) {
        final Video video = new Video();
        video.setSiteId(siteId);
        video.setSourceExtension("avi");
        video.setSourceName(name);
        ServiceLocator.getPersistance().putVideo(video);
        ServiceLocator.getFileSystem().setResourceStream(video, getTempVideoStream());
        return video;
    }

    public static User createUserAndLogin(final String email) {
        final User user = createUser(email);
        user.setActiveted(new Date(System.currentTimeMillis() / 2));
        loginUser(user);
        return user;
    }

    public static void loginUser(final User user) {
        ServiceLocator.getContextStorage().get().setUserId(user.getUserId());
    }

    public static User createUserAndLogin() {
        return createUserAndLogin("");
    }

    public static PageManager createPageVersionSiteUser() {
        return new PageManager(createPage(createSite()));
    }

    public static PageManager createPageVersionPageAndSite() {
        return createPageVersionAndPage(createSite());
    }

    public static PageManager createPageVersionAndPage(final Site site) {
        return new PageManager(createPage(site));
    }

    public static PageManager createPageVersionAndPage(final Site site, PageVersionType pageVersionType) {
        final PageManager pageManager = new PageManager(createPage(site));
        if (pageVersionType == PageVersionType.WORK) {
            createWorkPageSettings(pageManager.getDraftPageSettings());
            return new PageManager(pageManager.getPage(), SiteShowOption.getWorkOption());
        }
        return pageManager;
    }

    public static DraftManageVotesSettings createManageVotesGallerySettings(final WidgetItem widgetGallery) {
        final DraftManageVotesSettings manageVotesGallerySettings = new DraftManageVotesSettings();
        final DraftGallery gallery = ServiceLocator.getPersistance().getGalleryById(widgetGallery.getDraftItem().getId());

        manageVotesGallerySettings.setCustomName(gallery.getName());
        manageVotesGallerySettings.setGalleryCrossWidgetId(widgetGallery.getCrossWidgetId());

        ServiceLocator.getPersistance().putManageVotesGallerySettings(manageVotesGallerySettings);

        return manageVotesGallerySettings;
    }

    public static DraftManageVotes createManageVotes(final Site site) {
        final DraftManageVotes manageVotes = new DraftManageVotes();
        final String name = SiteItemsManager.getNextDefaultName(ItemType.MANAGE_VOTES, site.getSiteId());
        manageVotes.setName(name);
        manageVotes.setSiteId(site.getSiteId());

        ServiceLocator.getPersistance().putItem(manageVotes);
        return manageVotes;
    }

    public static DraftManageVotes createManageVotes(final Site site, final List<DraftManageVotesSettings> manageVotesGallerySettingses) {
        final DraftManageVotes manageVotes = new DraftManageVotes();
        final String name = SiteItemsManager.getNextDefaultName(ItemType.MANAGE_VOTES, site.getSiteId());
        manageVotes.setName(name);
        manageVotes.setSiteId(site.getSiteId());
        manageVotes.setManageVotesGallerySettingsList(manageVotesGallerySettingses);

        ServiceLocator.getPersistance().putItem(manageVotes);
        return manageVotes;
    }

    public static PageManager createPageVersionAndPage(final Site site, final String name, final PageVersionType type) {
        final PageManager pageVersion = createPageVersionAndPage(site, name);
        if (type == PageVersionType.WORK) {
            createWorkPageSettings(pageVersion.getDraftPageSettings());
            return new PageManager(pageVersion.getPage(), SiteShowOption.getWorkOption());
        }
        return pageVersion;
    }

    public static PageManager createPageVersionAndPage(final Site site, final String name) {
        final PageManager pageManager = new PageManager(createPage(site));
        pageManager.setName(name);
        return pageManager;
    }

    public static PageManager createPageVersion(final Page page) {
        final PageManager pageManager = new PageManager(page);
        pageManager.setName("pageName");
        return pageManager;
    }


    public static PageManager createPageVersion(final Page page, final PageVersionType type) {
        final PageManager pageVersion = createPageVersion(page);
        if (type == PageVersionType.WORK) {
            createWorkPageSettings(pageVersion.getDraftPageSettings());
            return new PageManager(pageVersion.getPage(), SiteShowOption.getWorkOption());
        }
        return pageVersion;
    }

    public static Page createParentPage(final Site site) {
        final Page page = new Page();
        createPageSettings(page);
        site.addPage(page);
        ServiceLocator.getPersistance().putPage(page);
        return page;
    }

    public static Page createChildPage(final Site site, final int position, final Page parentPage) {
        final Page page = new Page();
        createPageSettings(page);
        site.addPage(page);
        ServiceLocator.getPersistance().putPage(page);
        return page;
    }

    public static Page createPage(final Site site) {
        final Page page = new Page();
        createPageSettings(page);
        site.addPage(page);
        ServiceLocator.getPersistance().putPage(page);
        return page;
    }

    public static Page createWorkPage(final Site site) {
        final Page page = createPage(site);
        createWorkPageSettings(page.getPageSettings());
        return page;
    }

    public static Page createPageAndSite() {
        return createPage(createSite());
    }

    public static DraftPageSettings createPageSettings(final Page page) {
        final DraftPageSettings pageSettings = new DraftPageSettings();
        pageSettings.setPage(page);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        pageSettings.setAccessibleSettings(accessibleSettings);
        page.setPageSettings(pageSettings);
        ServiceLocator.getPersistance().putPageSettings(pageSettings);
        return pageSettings;
    }

    public static WorkPageSettings createWorkPageSettings(final DraftPageSettings draftPageSettings) {
        final WorkPageSettings pageSettings = new WorkPageSettings();
        pageSettings.setPage(draftPageSettings.getPage());
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(draftPageSettings.getAccessibleSettings().getAccess());
        accessibleSettings.setAdministrators(draftPageSettings.getAccessibleSettings().isAdministrators());
        accessibleSettings.setVisitors(draftPageSettings.getAccessibleSettings().isVisitors());
        accessibleSettings.setVisitorsGroups(new ArrayList<Integer>(draftPageSettings.getAccessibleSettings().getVisitorsGroups()));
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        pageSettings.setAccessibleSettings(accessibleSettings);
        pageSettings.setPageSettingsId(draftPageSettings.getPageSettingsId());
        ServiceLocator.getPersistance().putPageSettings(pageSettings);
        return pageSettings;
    }

    public static Site createBlueprint() {
        final Site blueprint = createSite();
        blueprint.setType(SiteType.BLUEPRINT);
        return blueprint;
    }

    public static Site createSite() {
        int userId = -1;
        try {
            final User user = new UsersManager().getLoginedUser();
            userId = user != null ? user.getUserId() : -1;
        } catch (Exception e) {
        }
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(userId);
        site.setThemeId(new ThemeId("", ""));
        site.setTitle("title");
        ServiceLocator.getPersistance().putSite(site);

        final DraftMenu menu = new DraftMenu();
        menu.setName("Default site menu");
        menu.setSiteId(site.getSiteId());
        menu.setDefaultSiteMenu(true);
        ServiceLocator.getPersistance().putMenu(menu);
        site.setMenu(menu);

        final PageManager loginPageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);
        loginPageVersion.getPage().setType(PageType.LOGIN);
        loginPageVersion.getPage().setSystem(true);
        loginPageVersion.setHtml("FF");
        loginPageVersion.setUrl("defaultLoginPage");
        loginPageVersion.setName("defaultLoginPage");
        site.setLoginPage(loginPageVersion.getPage());
        return site;
    }

    public static SitePaymentSettings createPaymentSettingsForJavien(final double price,
                                                                     final ChargeType chargeType) {
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setPrice(price);
        sitePaymentSettings.setChargeType(chargeType);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        ServiceLocator.getPersistance().putSitePaymentSettings(sitePaymentSettings);

        return sitePaymentSettings;
    }

    public static SitePaymentSettings createPaymentSettingsForPaypal(final String profileId,
                                                                     final ChargeType chargeType) {
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setRecurringPaymentId(profileId);
        sitePaymentSettings.setChargeType(chargeType);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);
        ServiceLocator.getPersistance().putSitePaymentSettings(sitePaymentSettings);

        return sitePaymentSettings;
    }

    public static PaymentLog createPaymentLog(final PaymentMethod paymentMethod, final TransactionStatus transactionStatus,
                                              final PaymentLogRequest paymentLogRequest) {
        final PaymentLog paymentLog = new PaymentLog();
        paymentLog.setTransactionStatus(transactionStatus);
        paymentLog.setPaymentMethod(paymentMethod);

        paymentLog.setSum(paymentLogRequest.getSum());
        paymentLog.setMonthlySum(paymentLogRequest.getMonthlySum());
        paymentLog.setSiteId(paymentLogRequest.getSiteId());
        paymentLog.setCreditCardId(paymentLogRequest.getCreditCard() != null ? paymentLogRequest.getCreditCard().getCreditCardId() : null);
        paymentLog.setChildSiteSettingsId(paymentLogRequest.getChildSiteSettingsId());
        paymentLog.setMessage(paymentLogRequest.getMessage());
        paymentLog.setErrorMessage(paymentLogRequest.getErrorMessage());
        paymentLog.setRecurringPaymentProfileId(paymentLogRequest.getProfileId());

        ServiceLocator.getPersistance().putPaymentLog(paymentLog);

        return paymentLog;
    }

    public static Site createSite(final Date creationDate) {
        final Site site = createSite();
        site.setCreationDate(creationDate);
        return site;
    }

    public static Site createChildSite() {
        return createChildSite(new Site());
    }

    public static Site createChildSite(Site parentSite) {
        final Site site = createSite();
        createChildSiteSettings(parentSite, site);
        return site;
    }

    public static Site createNetworkSite() {
        final Site site = createSite();
        site.addChildSiteRegistrationId(1);
        site.addChildSiteRegistrationId(2);
        site.addChildSiteRegistrationId(3);
        return site;
    }

    public static CreditCard createCreditCard(final Date expirationDate, final Site site) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(expirationDate);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

        final CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) calendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) calendar.get(Calendar.MONTH));
        site.getSitePaymentSettings().setCreditCard(creditCard);
        ServiceLocator.getPersistance().putCreditCard(creditCard);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        return creditCard;
    }

    public static CreditCard createCreditCard(final Date expirationDate, final User user) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(expirationDate);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

        final CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) calendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) calendar.get(Calendar.MONTH));
        ServiceLocator.getPersistance().putCreditCard(creditCard);
        user.addCreditCard(creditCard);
        creditCard.setUser(user);
        return creditCard;
    }

    public static IncomeSettings createIncomeSettings(final Site site, final String paypal, final double sum) {
        final IncomeSettings incomeSettings = new IncomeSettings();
        incomeSettings.setPaypalAddress(paypal);
        incomeSettings.setSum(sum);
        ServiceLocator.getPersistance().putIncomeSettings(incomeSettings);
        site.setIncomeSettings(incomeSettings);
        return incomeSettings;
    }

    public static IncomeSettings createIncomeSettings(final Site site, final String paypal, final double sum, final String paymentDetails) {
        final IncomeSettings incomeSettings = createIncomeSettings(site, paypal, sum);
        incomeSettings.setPaymentDetails(paymentDetails);
        return incomeSettings;
    }

    public static Image createImage(final int siteId, final String name, final String extension) {
        final Image image = new Image();
        image.setWidth(10);
        image.setHeight(10);
        image.setName(name);
        image.setSiteId(siteId);
        image.setSourceExtension(extension);
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        ServiceLocator.getPersistance().putImage(image);
        ServiceLocator.getFileSystem().setResourceStream(image, TestUtil.getTempImageStream());
        return image;
    }

    public static ImageFile createImageFile(final int siteId, final String name, final String extension, final ImageFileType imageFileType) {
        final ImageFile imageFile = new ImageFile();
        imageFile.setSourceName(name);
        imageFile.setImageFileType(imageFileType);
        imageFile.setSiteId(siteId);
        imageFile.setSourceExtension(extension);
        imageFile.setCreated(new Date());
        ServiceLocator.getPersistance().putImageFile(imageFile);
        return imageFile;
    }

    public static DraftChildSiteRegistration createChildSiteRegistration(final Site site) {
        final String name = SiteItemsManager.getNextDefaultName(ItemType.CHILD_SITE_REGISTRATION, site.getSiteId());
        DraftChildSiteRegistration childSiteRegistration = createChildSiteRegistration(name, "comment" + name);
        childSiteRegistration.setSiteId(site.getSiteId());
        site.addChildSiteRegistrationId(childSiteRegistration.getFormId());
        return childSiteRegistration;
    }

    public static DraftChildSiteRegistration createChildSiteRegistration(final String name, final String comment, final Site site) {
        DraftChildSiteRegistration childSiteRegistration = createChildSiteRegistration(name, comment);
        childSiteRegistration.setSiteId(site.getSiteId());
        site.addChildSiteRegistrationId(childSiteRegistration.getFormId());
        return childSiteRegistration;
    }


    public static DraftChildSiteRegistration createChildSiteRegistration(final String name, final String comment) {
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(-1);
        childSiteRegistration.setDescription(comment);
        childSiteRegistration.setName(name);
        childSiteRegistration.setDescription("Description");
        childSiteRegistration.setShowDescription(true);
        childSiteRegistration.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteRegistration.setPrice1gb(10.0);
        childSiteRegistration.setPrice250mb(15.0);
        childSiteRegistration.setPrice3gb(20.0);
        childSiteRegistration.setPrice500mb(25.0);
        childSiteRegistration.setTermsAndConditions("TermsAndConditions");
        childSiteRegistration.setStartDate(new Date());
        childSiteRegistration.setEndDate(new Date());
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setWelcomeText("");
        ServiceLocator.getPersistance().putItem(childSiteRegistration);
        return childSiteRegistration;
    }

    public static ChildSiteSettings createChildSiteSettings(final DraftChildSiteRegistration childSiteRegistration, final Site parentSite) {
        int userId = -1;
        try {
            final User user = new UsersManager().getLoginedUser();
            userId = user != null ? user.getUserId() : -1;
        } catch (Exception e) {
        }

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setParentSite(parentSite);
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setAccessLevel(childSiteRegistration.getAccessLevel());
        settings.setEndDate(childSiteRegistration.getEndDate() != null ? childSiteRegistration.getEndDate() : null);
        settings.setStartDate(childSiteRegistration.getStartDate() != null ? childSiteRegistration.getStartDate() : null);
        settings.setPrice1gb(childSiteRegistration.getPrice1gb());
        settings.setPrice250mb(childSiteRegistration.getPrice250mb());
        settings.setPrice500mb(childSiteRegistration.getPrice500mb());
        settings.setPrice3gb(childSiteRegistration.getPrice3gb());
        settings.setUserId(userId);
        settings.getSitePaymentSettings().setUserId(userId);
        settings.setLogoId(1);
        settings.setConfirmCode("ConfirmCode");
        settings.setCreatedDate(new Date());
        ServiceLocator.getPersistance().putChildSiteSettings(settings);
        return settings;
    }

    public static ChildSiteSettings createChildSiteSettings(final DraftChildSiteRegistration childSiteRegistration, final Site parentSite, final Site childSite) {
        ChildSiteSettings settings = createChildSiteSettings(childSiteRegistration, parentSite);
        childSite.setChildSiteSettings(settings);
        settings.setSite(childSite);
        return settings;
    }

    public static ChildSiteSettings createChildSiteSettings() {
        return createChildSiteSettings(createChildSiteRegistration("name", "comment"), TestUtil.createSite());
    }

    public static ChildSiteSettings createChildSiteSettings(final Site parentSite, final Site childSite) {
        final DraftChildSiteRegistration childSiteRegistration = createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = createChildSiteSettings(childSiteRegistration, parentSite);
        if (childSite != null) {
            childSite.setChildSiteSettings(settings);
            settings.setSite(childSite);
        }
        return settings;
    }

    public static DraftFormItem createFormItem(final FormItemName formItemName, final DraftForm form, final int position) {
        DraftFormItem item = new DraftFormItem();
        item.setFormItemName(formItemName);
        item.setDraftForm(form);
        item.setPosition(position);
        ServiceLocator.getPersistance().putFormItem(item);
        form.addFormItem(item);
        return item;
    }

    public static DraftFormItem createFormItem(final FormItemName formItemName, final int position) {
        DraftFormItem item = new DraftFormItem();
        item.setFormItemName(formItemName);
        item.setPosition(position);
        ServiceLocator.getPersistance().putFormItem(item);
        return item;
    }

    public static Site createSite(final String title, final String url) {
        Site site = createSite();
        site.setTitle(title);
        site.setSubDomain(url);
        return site;
    }

    public static DraftRegistrationForm createRegistrationForm() {
        return createRegistrationForm(0, "Default registration form");
    }

    public static DraftContactUs createContactUsForm() {
        return createContactUsForm(null);
    }

    public static DraftContactUs createContactUsForm(final Integer siteId) {
        final DraftContactUs contactUs = new DraftContactUs();
        contactUs.setSiteId(siteId);
        final FormManager formManager = new FormManager();

        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        ServiceLocator.getPersistance().putContactUs(contactUs);
        return contactUs;
    }

    public static DraftRegistrationForm createRegistrationForm(int siteId) {
        return createRegistrationForm(siteId, "Default registration form");
    }

    public static DraftRegistrationForm createRegistrationForm(final Site site) {
        String name = SiteItemsManager.getNextDefaultName(ItemType.REGISTRATION, site.getSiteId());
        return createRegistrationForm(site.getSiteId(), name);
    }

    public static DraftRegistrationForm createRegistrationForm(int siteId, String registrationFormName) {
        final DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        final FormManager formManager = new FormManager();

        registrationForm.setName(registrationFormName);
        registrationForm.setSiteId(siteId);
        DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        registrationForm.addFormItem(formItem);
        formItem = FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        registrationForm.addFormItem(formItem);
        ServiceLocator.getPersistance().putRegistrationForm(registrationForm);
        return registrationForm;
    }

    public static DraftPurchaseHistory createPurchaseHistory(final Site site) {
        final DraftPurchaseHistory purchaseHistory = new DraftPurchaseHistory();

        purchaseHistory.setName("Purcahse history default name.");
        purchaseHistory.setSiteId(site.getId());
        ServiceLocator.getPersistance().putItem(purchaseHistory);
        return purchaseHistory;
    }

    public static DraftShoppingCart createShoppingCart(final Site site) {
        final DraftShoppingCart shoppingCart = new DraftShoppingCart();

        shoppingCart.setName("Shopping cart default name.");
        shoppingCart.setSiteId(site.getId());
        ServiceLocator.getPersistance().putItem(shoppingCart);
        return shoppingCart;
    }

    public static DraftTellFriend createTellFriend(final Site site) {
        final DraftTellFriend tellFriend = new DraftTellFriend();

        tellFriend.setName("Tell friend default name.");
        tellFriend.setSiteId(site.getId());
        ServiceLocator.getPersistance().putItem(tellFriend);
        return tellFriend;
    }

    public static DraftAdminLogin createAdminLogin(final Site site) {
        final DraftAdminLogin adminLogin = new DraftAdminLogin();

        adminLogin.setSiteId(site.getId());
        ServiceLocator.getPersistance().putItem(adminLogin);
        return adminLogin;
    }

    public static DraftCustomForm createCustomForm(final Site site) {
        String customFormName = SiteItemsManager.getNextDefaultName(ItemType.CUSTOM_FORM, site.getSiteId());
        return createCustomForm(site.getSiteId(), customFormName);
    }

    public static DraftForm createOrderFormAndAddToGallerySettings(final Integer productFormItemId, final Integer registrationFormId, final Gallery gallery) {
        final DraftForm orderForm = new GalleryManager(gallery).createOrdersForm(productFormItemId, registrationFormId, new UsersManager().getLogined());
        gallery.getPaypalSettings().setOrdersFormId(orderForm.getId());
        return orderForm;
    }

    public static DraftAdvancedSearchOption createAdvancedSearchOption(final String label, final int formItemId,
                                                                       final OptionDisplayType displayType,
                                                                       final List<String> criteriaList) {
        final DraftAdvancedSearchOption advancedSearchOption = new DraftAdvancedSearchOption();

        advancedSearchOption.setFormItemId(formItemId);
        advancedSearchOption.setFieldLabel(label);
        advancedSearchOption.setDisplayType(displayType);
        advancedSearchOption.setOptionCriteria(criteriaList);

        ServiceLocator.getPersistance().putAdvancedSearchOption(advancedSearchOption);

        return advancedSearchOption;
    }

    public static DraftCustomForm createCustomForm(int siteId, String customFormName) {
        final DraftCustomForm customForm = new DraftCustomForm();
        final FormManager formManager = new FormManager();

        customForm.setName(customFormName);
        customForm.setSiteId(siteId);

        DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        customForm.addFormItem(formItem);

        formItem = FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        customForm.addFormItem(formItem);

        ServiceLocator.getPersistance().putCustomForm(customForm);
        return customForm;
    }

    public static DraftFormFilter createFormFilter(final DraftForm form) {
        final DraftFormFilter formFilter = new DraftFormFilter();
        formFilter.setName("name");
        form.addFilter(formFilter);
//        formFilter.setForm(form);

        ServiceLocator.getPersistance().putFormFilter(formFilter);
        return formFilter;
    }

    public static DraftFormFilter createFormFilter(
            final DraftForm form, final String name,
            final DraftFormFilterRule... rules) {
        final DraftFormFilter formFilter = createFormFilter(form);

        for (final DraftFormFilterRule rule : rules) {
            formFilter.addRule(rule);
            ServiceLocator.getPersistance().putFormFilterRule(rule);
        }

        formFilter.setName(name);

        return formFilter;
    }

    public static DraftFormFilter createFormFilter(final DraftForm form, final String name) {
        final DraftFormFilter formFilter = createFormFilter(form);
        formFilter.setName(name);
        return formFilter;
    }

    public static DraftCustomForm createCustomForm(final int siteId, String customFormName, final List<DraftFormItem> items) {
        final DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName(customFormName);
        customForm.setSiteId(siteId);
        ServiceLocator.getPersistance().putCustomForm(customForm);
        for (final DraftFormItem item : items) {
            ServiceLocator.getPersistance().putFormItem(item);
            customForm.addFormItem(item);
        }
        return customForm;
    }


    public static List<DraftFormItem> createDefaultFormItemsForGallery() {
        final FormManager formManager = new FormManager();
        List<DraftFormItem> items = new ArrayList<DraftFormItem>();
        DraftFormItem item;
        item = FormItemManager.createFormItemByName(FormItemName.NAME, 0, false);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        item = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 1, false);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        item = FormItemManager.createFormItemByName(FormItemName.DESCRIPTION, 2, false);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        item = FormItemManager.createFormItemByName(FormItemName.DATE_ADDED, 3, true);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        item = FormItemManager.createFormItemByName(FormItemName.SORT_ORDER, 4, true);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        item = FormItemManager.createFormItemByName(FormItemName.ENTERED_BY, 5, true);
        ServiceLocator.getPersistance().putFormItem(item);
        items.add(item);
        return items;
    }

    public static Widget createForumWidget() {
        final PageManager pageVersion = createPageVersionSiteUser();

        final Widget widget = TestUtil.createWidgetItem();
        pageVersion.addWidget(widget);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(pageVersion.getPage().getSite().getSiteId());
        forum.setName("Forum1");
        forum.setAllowSubForums(true);
        ServiceLocator.getPersistance().putItem(forum);
        ((WidgetItem) widget).setDraftItem(forum);

        return widget;
    }

    public static WidgetItem createWidgetChildSiteRegistration(final Integer itemId) {
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(ServiceLocator.getPersistance().getDraftItem(itemId));
        return widgetItem;
    }

    public static WidgetItem createWidgetCustomForm(final Integer itemId) {
        final Widget widget = TestUtil.createWidgetItem();
        ((WidgetItem) widget).setDraftItem(ServiceLocator.getPersistance().getDraftItem(itemId));
        return ((WidgetItem) widget);
    }

    public static WidgetItem createWidgetItemWithPageAndPageVersion(final Site site) {
        final WidgetItem widgetItem = createWidgetItem();
        final PageManager pageVersion = createPageVersionAndPage(site);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);
        return widgetItem;
    }

    public static WidgetItem createWidgetItem() {
        final WidgetItem widgetItem = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widgetItem);
        widgetItem.setAccessibleSettingsId(createAccessibleSettings().getAccessibleSettingsId());
        widgetItem.setItemSizeId(createItemSize().getId());
        widgetItem.setBackgroundId(createBackground().getId());
        widgetItem.setBorderId(createBorder().getId());
        widgetItem.setFontsAndColorsId(createFontsAndColorsWithOneCssValue().getId());
        return widgetItem;
    }

    public static WidgetComposit createWidgetComposit() {
        final WidgetComposit widgetItem = new WidgetComposit();
        ServiceLocator.getPersistance().putWidget(widgetItem);
        widgetItem.setItemSizeId(createItemSize().getId());
        widgetItem.setBackgroundId(createBackground().getId());
        widgetItem.setBorderId(createBorder().getId());
        widgetItem.setFontsAndColorsId(createFontsAndColorsWithOneCssValue().getId());
        return widgetItem;
    }

    public static WidgetItem createWidgetItem(final PageManager pageVersion) {
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetItem);
        return widgetItem;
    }

    public static WidgetItem createTextWidget(PageManager pageVersion) {
        final DraftText draftText = new DraftText();
        draftText.setSiteId(pageVersion.getSiteId());
        ServiceLocator.getPersistance().putItem(draftText);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftText);
        pageVersion.addWidget(widgetItem);

        return widgetItem;
    }

    public static WidgetItem createWidgetScript(PageManager pageVersion) {
        final DraftScript draftScript = new DraftScript();
        draftScript.setSiteId(pageVersion.getSiteId());
        ServiceLocator.getPersistance().putItem(draftScript);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftScript);
        pageVersion.addWidget(widgetItem);

        return widgetItem;
    }

    public static WidgetItem createTextWidget() {
        final PageManager pageVersion = createPageVersionSiteUser();

        final DraftText draftText = new DraftText();
        ServiceLocator.getPersistance().putItem(draftText);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftText);
        pageVersion.addWidget(widgetItem);

        return widgetItem;
    }

    public static WidgetItem createContactUsWidget() {
        final PageManager pageVersion = createPageVersionSiteUser();
        final WidgetItem widgetContactUs = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widgetContactUs);
        pageVersion.addWidget(widgetContactUs);
        return widgetContactUs;
    }

    public static WidgetItem createCustomFormWidget(final PageManager pageManager) {
        final WidgetItem widgetCustomForm = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widgetCustomForm);
        pageManager.addWidget(widgetCustomForm);
        return widgetCustomForm;
    }

    public static WidgetItem createWidgetManageVotes() {
        final PageManager pageVersion = createPageVersionSiteUser();

        final DraftManageVotes draftManageVotes = new DraftManageVotes();
        ServiceLocator.getPersistance().putItem(draftManageVotes);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftManageVotes);
        pageVersion.addWidget(widgetItem);
        return widgetItem;
    }

    public static WidgetItem addForumWidgetToPage(PageManager page) {
        final WidgetItem widgetForum = TestUtil.createWidgetItem();
        page.addWidget(widgetForum);

        final DraftForum forum = new DraftForum();
        forum.setName("Forum1");
        forum.setSiteId(page.getPage().getSite().getSiteId());
        ServiceLocator.getPersistance().putItem(forum);
        widgetForum.setDraftItem(forum);

        return widgetForum;
    }

    public static SubForum createSubForum(DraftForum forum) {
        final SubForum subForum = new SubForum();
        subForum.setSubForumName("SubForum1");
        subForum.setSubForumDescription("SubForum_Description1");
        subForum.setForum(forum);
        ServiceLocator.getPersistance().putSubForum(subForum);
        forum.addSubForum(subForum);

        return subForum;
    }

    public static SubForum createSubForum() {
        final WidgetItem forumWidget = (WidgetItem) createForumWidget();

        final DraftForum forum = (DraftForum) forumWidget.getDraftItem();//ServiceLocator.getPersistance().getDraftItem(forumWidget.getDraftItem());
        final SubForum subForum = new SubForum();
        subForum.setSubForumName("SubForum1");
        subForum.setSubForumDescription("SubForum_Description1");
        subForum.setForum(forum);
        ServiceLocator.getPersistance().putSubForum(subForum);
        forum.addSubForum(subForum);

        return subForum;
    }

    public static ForumThread createForumThread(SubForum subForum) {
        return createForumThread(subForum, null);
    }

    public static ForumThread createForumThread(SubForum subForum, final User author) {
        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Thread1");
        forumThread.setThreadDescription("Thread_Description1");
        forumThread.setSubForum(subForum);
        forumThread.setAuthor(author);
        ServiceLocator.getPersistance().putForumThread(forumThread);
        subForum.addForumThread(forumThread);

        return forumThread;
    }

    public static ForumPost createForumPost(ForumThread forumThread) {
        return createForumPost(forumThread, null);
    }

    public static ForumPost createForumPost(ForumThread forumThread, final User user) {
        final ForumPost forumPost = new ForumPost();
        forumPost.setText("Post1");
        forumPost.setThread(forumThread);
        forumPost.setAuthor(user);
        ServiceLocator.getPersistance().putForumPost(forumPost);
        forumThread.addForumPost(forumPost);

        return forumPost;
    }

    public static ForumPost createDraftForumPost(ForumThread forumThread) {
        final ForumPost forumPost = new ForumPost();
        forumPost.setText(null);
        forumPost.setDraftText("Post1");
        forumPost.setThread(forumThread);
        ServiceLocator.getPersistance().putForumPost(forumPost);
        forumThread.addForumPost(forumPost);
        return forumPost;
    }

    public static DraftSlideShow createSlideShow(final Site site) {
        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        ServiceLocator.getPersistance().putItem(slideShow);

        return slideShow;
    }

    public static DraftSlideShowImage createSlideShowImage(final DraftSlideShow slideShow) {
        final DraftSlideShowImage slideShowImage = new DraftSlideShowImage();
        slideShowImage.setSlideShow(slideShow);
        ServiceLocator.getPersistance().putSlideShowImage(slideShowImage);

        slideShow.addSlideShowImage(slideShowImage);

        return slideShowImage;
    }

    public static void initServiceLocator() {
        ServiceLocator.setSiteCopierBlueprint(new SiteCopierBlueprintMock());
        ServiceLocator.setSiteCopierFromActivatedBlueprint(new SiteCopierFromActivatedBlueprintMock());
        ServiceLocator.setSiteCopierFromBlueprint(new SiteCopierFromBlueprintMock());
        ServiceLocator.setRenderedPageHtmlProvider(new RenderedPageHtmlProviderMock());
        ServiceLocator.setCopier(new CopierMock());
        ServiceLocator.setPageVersionNormalizer(new PageVersionNormalizerMock());
        ServiceLocator.setSiteCreatorOrUpdater(new SiteCreatorOrUpdaterMock());
        ServiceLocator.setSiteByUrlGetter(new SiteByUrlGetterMock());
        ServiceLocator.setCustomTagFacade(new CustomTagFacade());
        ServiceLocator.setCustomTagProcessor(new CustomTagProcessorNone());
        ServiceLocator.setAdvancedSearchResultsNumberCache(new AdvancedSearchResultsNumberCache());
        ServiceLocator.setPaypalPaymentInfoRequestStorage(new PaypalPaymentInfoRequestStorage());
        ServiceLocator.setPaypalButtonIPNRequestStorage(new PaypalButtonIPNRequestStorage());
        ServiceLocator.setNowTime(new NowTimeMock());
        ServiceLocator.setHtmlProcessor(new HtmlProcessorRegex());
        ServiceLocator.setResourceProducer(new ResourceProducerMock());
        ServiceLocator.setResourceGetter(new ResourceGetterUrlInternal());
        ServiceLocator.setPageResourcesAccelerator(new PageResourcesMockAccelerator());
        ServiceLocator.setSystemConsole(new SystemConsoleMock(null));
        ServiceLocator.setMailAddressValidator(new MailAddressNoInternetUseValidator());
        ServiceLocator.setPersistance(new PersistanceMock());
        ServiceLocator.setMailSender(new MockMailSender());
        ServiceLocator.setInternationStorage(new InternationalStorageParameters(new InternationalStoragePropertyBundle()));
        ServiceLocator.setSynchronize(new NoSynchronize());
        final ConfigStorage configStorage = new MockConfigStorage();
        configStorage.get().setUseCacheHibernate(false);
        configStorage.get().setUseCacheFileSystem(false);
        configStorage.get().setUseCacheShowWorkPageVersion(false);
        configStorage.get().setUseInternationalHightlight(false);
        configStorage.get().setUseJournalToPersistance(false);
        configStorage.get().setUseJournalToConsole(false);
        configStorage.get().setUseCacheConfigStorage(false);
        configStorage.get().setSiteResourcesPath(IOUtil.baseDir() + "/testSiteResources");
        configStorage.get().getSiteResourcesVideo().setPath(IOUtil.baseDir() + "/testSiteResourcesVideo");
        ServiceLocator.setConfigStorage(configStorage);
        ServiceLocator.setTestHelpStorage(new TestHelpStorage());
        ServiceLocator.setSessionStorage(new MockSessionStorage());
        ServiceLocator.setWebContextGetter(new MockWebContextGetter(new MockWebContext()));
        ServiceLocator.setJournal(new JournalMock());
        ServiceLocator.setContextStorage(new ContextStorage());
        ServiceLocator.getContextStorage().set(new ContextManual());
        ServiceLocator.setPersistanceTransaction(new MockPersistanceTransaction());
        ServiceLocator.setFileSystem(new FileSystemMock());
        ServiceLocator.setFilesWriter(new AsynchronousFilesWriter());
        ServiceLocator.setVideoTranscode(new VideoTranscodeMock());
        ServiceLocator.setResolutionCreator(new ResolutionCreatorMock());
        ServiceLocator.setHtmlGetter(new HtmlGetterMock());
        ServiceLocator.setJavien(new JavienMock());
        ServiceLocator.setAuthorizeNet(new AuthorizeNetMock());
        ServiceLocator.setPayPal(new PayPalMock());

        final ConfigSmtp configSmtp = new ConfigSmtp();
        configSmtp.setLogin("shroggle-admin@email");
        configSmtp.setEmailUpdateApprovalLink("");
        configSmtp.setNetworkFrom("");
        configSmtp.setPassword("");
        configSmtp.setUrl("");
        ServiceLocator.getConfigStorage().get().setMail(configSmtp);
    }

    public static void clearServiceLocator() {
        ServiceLocator.setHtmlProcessor(null);
        ServiceLocator.setPageResourcesAccelerator(null);
        ServiceLocator.setNowTime(null);
        ServiceLocator.setSystemConsole(null);
        ServiceLocator.setMailAddressValidator(null);
        ServiceLocator.setPersistance(null);
        ServiceLocator.setMailSender(null);
        ServiceLocator.setInternationStorage(null);
        ServiceLocator.setSynchronize(null);
        ServiceLocator.setConfigStorage(null);
        ServiceLocator.setTestHelpStorage(null);
        ServiceLocator.setSessionStorage(null);
        ServiceLocator.setWebContextGetter(null);
        ServiceLocator.setJournal(null);
        ServiceLocator.setContextStorage(null);
        ServiceLocator.setPersistanceTransaction(null);
        ServiceLocator.setFileSystem(null);
        ServiceLocator.setFilesWriter(null);
        ServiceLocator.setVideoTranscode(null);
        ServiceLocator.setResolutionCreator(null);
        ServiceLocator.setHtmlGetter(null);
        ServiceLocator.setJavien(null);
        ServiceLocator.setAuthorizeNet(null);
    }

    //Creates sites, pages, visits and pageVisitor structure to test statistics
    //
    //s1
    //  s1_p1 one visitor
    //s2
    //  s2_p1 one visitor
    //  s2_p2 two visitors
    //  s2_p3 one visitor
    //s3
    //  s3_p1 one visitor
    //  s3_p2 one visitor

    public static List<Site> initSitesForStatisticsTests() {
        List<Site> siteList = new ArrayList<Site>();

        //First site
        Site s1 = new Site();
        s1.setCreationDate(new Date());
        s1.setTitle("1_site");
        ServiceLocator.getPersistance().putSite(s1);
        Page p1 = new Page();
        createPageSettings(p1);
        PageManager p1_pv = new PageManager(p1);
        p1_pv.setUrl("1_page");
        p1_pv.setName("1_page");
        p1_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(p1_pv.getDraftPageSettings());
        p1.setSite(s1);
        ServiceLocator.getPersistance().putPage(p1);
        s1.addPage(p1);
        Visit visit = new Visit();
        PageVisitor pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(p1);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(1);
        visit.setOverallTimeOfVisit(100);
        visit.addReferrerURL("1_my_url");
        p1.addVisit(visit);

        siteList.add(s1);

        //Second site
        Site s2 = new Site();
        s2.setCreationDate(new Date());
        s2.setTitle("2_site");
        ServiceLocator.getPersistance().putSite(s2);
        Page s2_p1 = new Page();
        createPageSettings(s2_p1);
        PageManager s2_p1_pv = new PageManager(s2_p1);
        s2_p1_pv.setUrl("1_page");
        s2_p1_pv.setName("1_page");
        s2_p1_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(s2_p1_pv.getDraftPageSettings());
        s2_p1.setSite(s1);
        ServiceLocator.getPersistance().putPage(s2_p1);
        s2.addPage(s2_p1);
        Page s2_p2 = new Page();
        createPageSettings(s2_p2);
        PageManager s2_p2_pv = new PageManager(s2_p2);
        s2_p2_pv.setUrl("2_page");
        s2_p2_pv.setName("2_page");
        s2_p2_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(s2_p2_pv.getDraftPageSettings());
        s2_p2.setSite(s1);
        ServiceLocator.getPersistance().putPage(s2_p2);
        s2.addPage(s2_p2);
        Page s2_p3 = new Page();
        createPageSettings(s2_p3);
        PageManager s2_p3_pv = new PageManager(s2_p3);
        s2_p3_pv.setUrl("3_page");
        s2_p3_pv.setName("3_page");
        s2_p3_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(s2_p3_pv.getDraftPageSettings());
        s2_p3.setSite(s1);
        ServiceLocator.getPersistance().putPage(s2_p3);
        s2.addPage(s2_p3);
        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(s2_p1);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(5);
        visit.setOverallTimeOfVisit(500);
        visit.addReferrerURL("2_my_url");
        visit.addReferrerURL("2_my_url");
        s2_p1.addVisit(visit);

        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(s2_p2);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(9);
        visit.setOverallTimeOfVisit(900);
        visit.addReferrerURL("2_my_url_visit_1");
        visit.addReferrerURL("2_my_url_visit_1");
        s2_p2.addVisit(visit);
        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(s2_p2);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(3);
        visit.setOverallTimeOfVisit(300);
        visit.addReferrerURL("2_my_url_visit_2");
        visit.addReferrerURL("2_my_url_visit_2");
        visit.addReferrerURL("2_my_url_visit_2");
        s2_p2.addVisit(visit);

        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(s2_p3);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(1000);
        visit.setOverallTimeOfVisit(10000);
        visit.addReferrerURL("2_my_url");
        visit.addReferrerURL("2_my_url");
        s2_p3.addVisit(visit);

        siteList.add(s2);

        //Third site
        Site s3 = new Site();
        s3.setCreationDate(new Date());
        s3.setTitle("3_site");
        ServiceLocator.getPersistance().putSite(s3);
        Page p3 = new Page();
        createPageSettings(p3);
        PageManager p3_pv = new PageManager(p3);
        p3_pv.setUrl("1_page");
        p3_pv.setName("1_page");
        p3_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(p3_pv.getDraftPageSettings());
        Page p4 = new Page();
        createPageSettings(p4);
        PageManager p4_pv = new PageManager(p4);
        p4_pv.setUrl("1_page");
        p4_pv.setName("1_page");
        p4_pv.setCreationDate(new Date());
        ServiceLocator.getPersistance().putPageSettings(p4_pv.getDraftPageSettings());
        p3.setSite(s3);
        p4.setSite(s3);
        ServiceLocator.getPersistance().putPage(p3);
        ServiceLocator.getPersistance().putPage(p4);
        s3.addPage(p3);
        s3.addPage(p4);
        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(p3);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(10);
        visit.setOverallTimeOfVisit(1000);
        visit.addReferrerURL("3_my_url");
        visit.addReferrerURL("3_my_url");
        visit.addReferrerURL("3_my_url");
        p3.addVisit(visit);

        visit = new Visit();
        pageVisitor = new PageVisitor();
        pageVisitor.addVisit(visit);
        ServiceLocator.getPersistance().putPageVisitor(pageVisitor);
        visit.setVisitCreationDate(new Date());
        visit.setVisitedPage(p4);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitCount(10);
        visit.setOverallTimeOfVisit(1000);
        visit.addReferrerURL("3_my_url");
        visit.addReferrerURL("3_my_url");
        visit.addReferrerURL("3_my_url");
        p4.addVisit(visit);

        siteList.add(s3);

        return siteList;
    }

    public static Widget createWidgetStructure(PageManager pageVersion) {
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        WidgetItem widgetRegistration = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetRegistration);
        widgetRegistration.setDraftItem(registrationForm);

        DraftForum forum = TestUtil.createForum(pageVersion.getPage().getSite());

        WidgetItem widgetForum = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetForum);
        widgetForum.setDraftItem(forum);

        DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        WidgetItem widgetLogin = TestUtil.createWidgetItem();
        widgetLogin.setDraftItem(draftLogin);
        pageVersion.addWidget(widgetLogin);

        WidgetComposit widgetComposit = new WidgetComposit();
        widgetComposit.addChild(widgetRegistration);
        widgetComposit.addChild(widgetForum);
        widgetComposit.addChild(widgetLogin);
        ServiceLocator.getPersistance().putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);

        return widgetComposit;
    }

    public static WidgetItem createWidgetGalleryForPageVersion(PageManager pageVersion) {
        return createWidgetGalleryForPageVersion(pageVersion, null, true, true);
    }

    public static WidgetItem createWidgetGalleryForPageVersion(PageManager pageVersion, int galleryId) {
        return createWidgetGalleryForPageVersion(pageVersion, galleryId, true, true);
    }

    public static WidgetItem createWidgetGalleryForPageVersion(
            PageManager pageVersion, final Integer galleryId, final boolean showData, final boolean showNavigation) {
        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(ServiceLocator.getPersistance().getDraftItem(galleryId));
        pageVersion.addWidget(widget);
        return widget;
    }


    public static WidgetItem createWidgetBlogSummaryForPageVersion(PageManager pageVersion, final Integer blogSumaryId) {
        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(ServiceLocator.getPersistance().getDraftItem(blogSumaryId));
        pageVersion.addWidget(widgetItem);
        return widgetItem;
    }

    public static DraftBlogSummary createBlogSummary(final Site site) {
        return createBlogSummary(site, "");
    }

    public static DraftBlogSummary createBlogSummary(final Site site, final String name) {
        if (site == null || site.getSiteId() <= 0) {
            throw new IllegalArgumentException("Site is null or has not been stored yet!");
        }
        final DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setSiteId(site.getSiteId());
        blogSummary.setName(name);
        ServiceLocator.getPersistance().putItem(blogSummary);
        return blogSummary;
    }

    public static WidgetItem createWidgetBlogForPageVersion(PageManager pageVersion, final Integer blogId, final int position) {
        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(ServiceLocator.getPersistance().getDraftItem(blogId));
        widget.setPosition(position);
        pageVersion.addWidget(widget);
        return widget;
    }


    public static WidgetItem createWidgetMenu(final Integer menuId) {
        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(ServiceLocator.getPersistance().getDraftItem(menuId));
        widget.setCrossWidgetId(widget.getWidgetId());
        return widget;
    }


    public static WidgetItem createWidgetManageVotes(final Integer itemId) {
        final WidgetItem widget = TestUtil.createWidgetItem();
        setDefaultValues(widget, itemId);
        return widget;
    }

    public static WidgetItem createWidgetBlog(final Integer itemId) {
        WidgetItem widget = TestUtil.createWidgetItem();
        setDefaultValues(widget, itemId);
        return widget;
    }

    public static WidgetItem createWidgetImage(final Integer imageId, final Integer rollOverImageId) {
        return createWidgetImage(imageId, rollOverImageId, createPageVersionAndPage(createSite()));
    }

    public static WidgetItem createWidgetImage(final Integer imageId, final Integer rollOverImageId, final PageManager pageVersion) {
        final DraftImage draftImage = new DraftImage();
        ServiceLocator.getPersistance().putItem(draftImage);
        draftImage.setImageId(imageId);
        draftImage.setRollOverImageId(rollOverImageId);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(draftImage);
        widget.setCrossWidgetId(widget.getWidgetId());
        pageVersion.addWidget(widget);
        return widget;
    }

    public static Widget createWidgetByItemType(final ItemType type, final Integer itemId) {
        Widget widget;
        switch (type) {
            case BLOG: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case FORUM: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case REGISTRATION: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case CHILD_SITE_REGISTRATION: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case CONTACT_US: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case GALLERY: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            case CUSTOM_FORM: {
                widget = TestUtil.createWidgetItem();
                break;
            }
            default: {
                widget = TestUtil.createWidgetItem();
                break;
            }
        }
        setDefaultValues(widget, itemId);
        return widget;
    }

    private static void setDefaultValues(final Widget widget, final Integer itemId) {
        ((WidgetItem) widget).setDraftItem(ServiceLocator.getPersistance().getDraftItem(itemId));
        widget.setCrossWidgetId(widget.getWidgetId());
    }

    public static WidgetItem createWidgetBlogForPageVersion(PageManager pageVersion, final Integer blogId) {
        return createWidgetBlogForPageVersion(pageVersion, blogId, 1);
    }

    public static DraftBlog createBlog(final Site site) {
        return createBlog(site, SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId()));
    }

    public static DraftBlog createBlog(final Site site, final String name) {
        if (site == null || site.getSiteId() <= 0)
            throw new IllegalArgumentException("Site is null or has not been stored yet!");
        final DraftBlog blog = new DraftBlog();
        blog.setName(name);
        blog.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(blog);
        return blog;
    }

    public static DraftForum createForum(final Site site) {
        if (site == null || site.getSiteId() <= 0)
            throw new IllegalArgumentException("Site is null or has not been stored yet!");
        final DraftForum forum = new DraftForum();
        forum.setName(SiteItemsManager.getNextDefaultName(ItemType.FORUM, site.getSiteId()));
        forum.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(forum);
        return forum;
    }

    public static DraftContactUs createContactUs(final Site site) {
        if (site == null || site.getSiteId() <= 0)
            throw new IllegalArgumentException("Site is null or has not been stored yet!");
        final DraftContactUs contactUs = new DraftContactUs();
        contactUs.setName(SiteItemsManager.getNextDefaultName(ItemType.CONTACT_US, site.getSiteId()));
        contactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(contactUs);

        DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        contactUs.addFormItem(formItem);

        formItem = FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        contactUs.addFormItem(formItem);

        return contactUs;
    }

    public static BlogPost createBlogPost(final String blogPostText, Date date) {
        final BlogPost blogPost = createBlogPost(blogPostText);
        blogPost.setCreationDate(date);
        return blogPost;
    }

    public static BlogPost createBlogPost(final String blogPostText, int postReadAndCommentsNumber) {
        final BlogPost blogPost = createBlogPost(blogPostText);
        blogPost.setPostRead(postReadAndCommentsNumber);
        for (int i = 0; i < postReadAndCommentsNumber; i++) {
            blogPost.addComment(createComment());
        }
        ServiceLocator.getPersistance().putBlogPost(blogPost);
        return blogPost;
    }

    public static BlogPost createBlogPost(final String blogPostText, User user) {
        final BlogPost blogPost = createBlogPost(blogPostText);
        blogPost.setVisitorId(user.getUserId());
        ServiceLocator.getPersistance().putBlogPost(blogPost);
        return blogPost;
    }

    public static BlogPost createBlogPost(final String blogPostText, String title) {
        final BlogPost blogPost = createBlogPost(blogPostText);
        blogPost.setPostTitle(title);
        return blogPost;
    }

    public static BlogPost createBlogPost(final DraftBlog draftBlog, final String blogPostText, String title) {
        final BlogPost blogPost = new BlogPost();
        blogPost.setText(blogPostText);
        blogPost.setPostTitle(title);
        blogPost.setBlog(draftBlog);
        draftBlog.addBlogPost(blogPost);
        ServiceLocator.getPersistance().putBlogPost(blogPost);
        return blogPost;
    }

    public static Comment createComment() {
        return createComment("");
    }

    public static Comment createComment(final String text) {
        return createComment(null, text);
    }

    public static Comment createComment(final BlogPost blogPost, final String text) {
        final Comment comment = new Comment();
        comment.setText(text);
        if (blogPost != null) {
            blogPost.addComment(comment);
            comment.setBlogPost(blogPost);
        }
        ServiceLocator.getPersistance().putComment(comment);
        return comment;
    }

    public static BlogPost createBlogPost(final String blogPostText) {
        final BlogPost blogPost = new BlogPost();
        blogPost.setText(blogPostText);
        ServiceLocator.getPersistance().putBlogPost(blogPost);
        return blogPost;
    }

    public static PatternPosition createPatternPosition(
            final LayoutPattern pattern, final ItemType type, final int position) {
        final PatternPosition patternPosition = new PatternPosition();
        pattern.getPositions().add(patternPosition);
        patternPosition.setType(type);
        patternPosition.setPosition(position);
        return patternPosition;
    }

    public static LayoutPattern createLayoutPattern(final Layout layout, final PageType type) {
        final LayoutPattern pattern = new LayoutPattern();
        pattern.setLayout(layout);
        pattern.setType(type);
        layout.getPatterns().add(pattern);
        return pattern;
    }

    public static LayoutPattern createLayoutPattern(final Layout layout) {
        return createLayoutPattern(layout, null);
    }

    public static Layout createLayout(final Template template, final String file) {
        final Layout layout = new Layout();
        layout.setFile(file);
        layout.setTemplate(template);
        template.getLayouts().add(layout);
        return layout;
    }

    public static FilledForm createFilledForm(final DraftForm form) {
        return createFilledForm(form.getFormId());
    }

    public static FilledForm createFilledForm(final int formId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setFormId(formId);
        filledForm.setType(FormType.REGISTRATION);
        filledForm.setFilledFormItems(createDefaultRegistrationFilledFormItems("User First Name",
                "User Last Name", "User Screen Name", "User Telephone Number", "User Email Address"));
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        return filledForm;
    }

    public static FilledForm createFilledFormEmpty(final int formId) {
        final FilledForm filledForm = new FilledForm();
        filledForm.setFormId(formId);
        filledForm.setType(FormType.CUSTOM_FORM);
        ServiceLocator.getPersistance().putFilledForm(filledForm);
        return filledForm;
    }

    public static FilledForm createFilledForm(final int formId, final User user) {
        final FilledForm filledForm = createFilledForm(formId);
        filledForm.setUser(user);
        user.addFilledForm(filledForm);
        return filledForm;
    }

    public static FilledForm createFilledForm(final DraftForm form, final User user) {
        final FilledForm filledForm = createFilledForm(form);
        filledForm.setUser(user);
        user.addFilledForm(filledForm);
        return filledForm;
    }

    public static User createUserAndUserOnSiteRightAndLogin(final Site site) {
        return createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
    }

    public static void createSiteItemOwnersWithSiteItemAndAddThemToPageSettings(final PageSettings pageSettings, final Site site) {
        DraftBlog blog = TestUtil.createBlog(site);
        Widget widgetBlog = TestUtil.createWidgetByItemType(ItemType.BLOG, blog.getId());
        pageSettings.addWidget(widgetBlog);

        DraftForum forum = TestUtil.createForum(site);
        Widget widgetForum = TestUtil.createWidgetByItemType(ItemType.FORUM, forum.getId());
        pageSettings.addWidget(widgetForum);

        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        Widget widgetRegistration = TestUtil.createWidgetByItemType(ItemType.REGISTRATION, registrationForm.getFormId());
        pageSettings.addWidget(widgetRegistration);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(site);
        Widget widgetChildRegistration = TestUtil.createWidgetByItemType(ItemType.CHILD_SITE_REGISTRATION, childSiteRegistration.getFormId());
        pageSettings.addWidget(widgetChildRegistration);

        DraftContactUs contactUs = TestUtil.createContactUs(site);
        Widget widgetContactUs = TestUtil.createWidgetByItemType(ItemType.CONTACT_US, contactUs.getFormId());
        pageSettings.addWidget(widgetContactUs);

        DraftGallery gallery = TestUtil.createGallery(site);
        Widget widgetGallery = TestUtil.createWidgetByItemType(ItemType.GALLERY, gallery.getId());
        pageSettings.addWidget(widgetGallery);

        DraftCustomForm customForm = TestUtil.createCustomForm(site);
        Widget widgetCustomForm = TestUtil.createWidgetByItemType(ItemType.CUSTOM_FORM, customForm.getFormId());
        pageSettings.addWidget(widgetCustomForm);

        DraftBlogSummary blogSummary = new DraftBlogSummary();
        String blogSummaryName = SiteItemsManager.getNextDefaultName(ItemType.BLOG_SUMMARY, site.getSiteId());
        blogSummary.setName(blogSummaryName);
        ServiceLocator.getPersistance().putItem(blogSummary);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(blogSummary);
        pageSettings.addWidget(widgetItem);
    }

    public static File resourceAsFile(final Class loader, final String name) {
        if (loader == null) {
            throw new UnsupportedOperationException("Can't get resource for null loader!");
        }
        if (name == null) {
            throw new UnsupportedOperationException("Can't get resource for null name!");
        }
        final URL url = loader.getResource(name);
        if (url == null) {
            throw new UnsupportedOperationException(
                    "Can't get resource for loader " + loader + " with name " + name);
        }
        return new File(url.getFile());
    }

    public static FileBean createFileBean(final Class loader, final String name) throws Exception {
        return new FileBean(createFile(loader, name), "", name);
    }

    public static File createFile(final Class loader, final String name) {
        try {
            final File file = resourceAsFile(loader, name);
            final File temp = File.createTempFile("test", name);
            IOUtil.copyFile(file, temp);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream createInputStream(final Class loader, final String name) {
        try {
            final File file = TestUtil.resourceAsFile(loader, name);
            final File temp = File.createTempFile("test", name);
            IOUtil.copyFile(file, temp);
            return new FileInputStream(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream getTempImageStream() {
        try {
            final File file = TestUtil.resourceAsFile(FileSystemRealTest.class, "test.png");
            final File temp = File.createTempFile("test", "test.png");
            IOUtil.copyFile(file, temp);
            return new FileInputStream(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream getTempVideoStream() {
        try {
            final File file = TestUtil.resourceAsFile(FileSystemRealTest.class, "test.avi");
            final File temp = File.createTempFile("test", "test.avi");
            IOUtil.copyFile(file, temp);
            return new FileInputStream(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static File getTempImageFile() throws Exception {
        final File file = TestUtil.resourceAsFile(FileSystemRealTest.class, "test.png");
        final File temp = File.createTempFile("test", "test.png");
        IOUtil.copyFile(file, temp);
        return temp;
    }

    public static File getTempVideoFile() throws Exception {
        final File file = TestUtil.resourceAsFile(FileSystemRealTest.class, "test.avi");
        final File temp = File.createTempFile("test", "test.avi");
        IOUtil.copyFile(file, temp);
        return temp;
    }

    public static Template createTemplate() {
        final Template template = new Template();
        template.setDirectory("directory");
        template.setName("name");
        template.setOrder(1);
        template.setThumbnail("");
        createLayout(template, "");
        createTheme(template);
        return template;
    }

    public static Theme createTheme(final Template template) {
        Theme theme = new Theme();
        theme.setFile("");
        theme.setColorTileFile("");
        theme.setImageFile("");
        theme.setName("name");
        theme.setTemplate(template);
        theme.setThumbnailFile("");
        template.setThemes(Arrays.asList(theme));
        return theme;
    }

    public static FlvVideo createVideoFLV(Integer videoId, Integer width, Integer height, Integer quality) {
        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setSourceVideoId(videoId);
        flvVideo.setWidth(width);
        flvVideo.setHeight(height);
        flvVideo.setQuality(quality);
        ServiceLocator.getPersistance().putFlvVideo(flvVideo);
        return flvVideo;
    }

    public static FlvVideo createVideoFLV(Integer videoId, Integer width, Integer height) {
        return createVideoFLV(videoId, width, height, FlvVideo.DEFAULT_VIDEO_QUALITY);
    }

    public static WidgetItem createWidgetGalleryData() {
        WidgetItem widgetGalleryData = TestUtil.createWidgetItem();
        widgetGalleryData.setCrossWidgetId(widgetGalleryData.getWidgetId());

        final DraftGalleryData draftGalleryData = new DraftGalleryData();
        ServiceLocator.getPersistance().putItem(draftGalleryData);
        widgetGalleryData.setDraftItem(draftGalleryData);

        return widgetGalleryData;
    }

    public static DraftGalleryItem createGalleryItem(Integer width, Integer height, DraftGallery gallery, int formItemId) {
        DraftGalleryItem item = new DraftGalleryItem();
        item.setWidth(width);
        item.setColumn(GalleryItemColumn.COLUMN_1);
        item.setHeight(height);
        DraftGalleryItemId itemId = new DraftGalleryItemId();
        itemId.setGallery(gallery);
        itemId.setFormItemId(formItemId);
        item.setId(itemId);
        ServiceLocator.getPersistance().putGalleryItem(item);
        gallery.addItem(item);
        return item;
    }

    public static BackgroundImage createBackgroundImage(final int siteId) {
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setSiteId(siteId);
        ServiceLocator.getPersistance().putBackgroundImage(backgroundImage);
        ServiceLocator.getFileSystem().setResourceStream(backgroundImage, TestUtil.getTempImageStream());
        return backgroundImage;
    }

    public static void createGalleryVideoRange(
            final User user, final DraftGallery gallery, final FilledForm filledForm,
            final float start, final float finish, final float total) {
        final GalleryVideoRange galleryVideoRange = new GalleryVideoRange();
        galleryVideoRange.setTotal(total);
        galleryVideoRange.setStart(start);
        galleryVideoRange.setFinish(finish);
        galleryVideoRange.setFilledFormId(filledForm.getFilledFormId());
        galleryVideoRange.setGalleryId(gallery.getId());
        user.addVideoRange(galleryVideoRange);
        ServiceLocator.getPersistance().putGalleryVideoRange(galleryVideoRange);
    }

    public static WidgetItem createWidgetAdminLogin() {
        final WidgetItem widgetAdminLogin = new WidgetItem();
        widgetAdminLogin.setDraftItem(new DraftAdminLogin());
        ServiceLocator.getPersistance().putWidget(widgetAdminLogin);
        return widgetAdminLogin;
    }

    public static DraftVideo createVideo1(final Site site) {
        final DraftVideo video1 = new DraftVideo();
        video1.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(video1);
        return video1;
    }

    public static DraftImage createDraftImage(final Site site) {
        final DraftImage draftImage = new DraftImage();
        draftImage.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(draftImage);
        return draftImage;
    }

    public static MenuItem createMenuItem(final DraftMenu menu) {
        final MenuItem menuItem = new DraftMenuItem();
        menuItem.setMenu(menu);
        menuItem.setIncludeInMenu(true);
        ServiceLocator.getPersistance().putMenuItem(menuItem);
        return menuItem;
    }

    public static MenuItem createMenuItem(final int pageId, final DraftMenu menu) {
        final MenuItem menuItem = createMenuItem(menu);
        menuItem.setPageId(pageId);
        menuItem.setDefaultPageId(pageId);
        menuItem.setIncludeInMenu(true);
        ServiceLocator.getPersistance().putMenuItem(menuItem);
        return menuItem;
    }

    public static MenuItem createMenuItem(final int pageId, final DraftMenu menu, final boolean includeInMenu) {
        final MenuItem menuItem = createMenuItem(menu);
        menuItem.setPageId(pageId);
        menuItem.setDefaultPageId(pageId);
        menuItem.setIncludeInMenu(includeInMenu);
        ServiceLocator.getPersistance().putMenuItem(menuItem);
        return menuItem;
    }

    public static MenuItem createMenuItem() {
        final MenuItem menuItem = new DraftMenuItem();
        menuItem.setIncludeInMenu(true);
        ServiceLocator.getPersistance().putMenuItem(menuItem);
        return menuItem;
    }

    public static Group createGroup(final String name, final Site owner) {
        final Group group = new Group();
        group.setName(name);
        group.setOwner(owner);
        owner.addGroup(group);
        ServiceLocator.getPersistance().putGroup(group);
        return group;
    }

    public static UsersGroup createUsersGroup(final User user, final Group group) {
        return createUsersGroup(user, group, null);
    }

    public static UsersGroup createUsersGroup(final User user, final Group group, final Date expirationDate) {
        final UsersGroup usersGroup = new UsersGroup();
        usersGroup.setExpirationDate(expirationDate);
        usersGroup.setId(user, group);
        user.addAccessToGroup(usersGroup);
        ServiceLocator.getPersistance().putUsersGroup(usersGroup);
        return usersGroup;
    }

    public static KeywordsGroup createKeywordsGroup() {
        return createKeywordsGroup(TestUtil.createSite());
    }

    public static KeywordsGroup createKeywordsGroup(final Site site) {
        final KeywordsGroup keywordsGroup = new KeywordsGroup();
        site.addKeywordsGroup(keywordsGroup);
        ServiceLocator.getPersistance().putKeywordsGroup(keywordsGroup);
        return keywordsGroup;
    }

    public static Icon createIcon() {
        final Icon icon = new Icon();
        ServiceLocator.getPersistance().putIcon(icon);
        return icon;
    }

    public static PaymentLog createPaymentLog(final int userId) {
        final PaymentLog paymentLog = new PaymentLog();
        paymentLog.setMessage("");
        paymentLog.setUserId(userId);
        ServiceLocator.getPersistance().putPaymentLog(paymentLog);
        return paymentLog;
    }

    public static CSVDataExport createCSVDataExport() {
        final CSVDataExport csvDataExport = new CSVDataExport();
        ServiceLocator.getPersistance().putCSVDataExport(csvDataExport);
        return csvDataExport;
    }

    public static CustomizeManageRecords createCustomizeManageRecords(final Integer formId, final Integer userId) {
        final CustomizeManageRecords CustomizeManageRecords = new CustomizeManageRecords();
        CustomizeManageRecords.setFormId(formId);
        CustomizeManageRecords.setUserId(userId);
        ServiceLocator.getPersistance().putCustomizeManageRecords(CustomizeManageRecords);
        return CustomizeManageRecords;
    }

    public static CustomizeManageRecordsField createCustomizeManageRecordsField(final int position) {
        final CustomizeManageRecordsField field = new CustomizeManageRecordsField();
        field.setPosition(position);
        ServiceLocator.getPersistance().putCustomizeManageRecordsField(field);
        return field;
    }

    public static CustomizeManageRecordsField createCustomizeManageRecordsField(final int position, final int formItemId) {
        final CustomizeManageRecordsField field = createCustomizeManageRecordsField(position);
        field.setFormItemId(formItemId);
        return field;
    }

    public static CSVDataExportField createCustomizeDataExportField(final int position) {
        final CSVDataExportField field = new CSVDataExportField();
        field.setPosition(position);
        ServiceLocator.getPersistance().putCSVDataExportField(field);
        return field;
    }

    public static CSVDataExportField createCustomizeDataExportField(final int position, final int formItemId) {
        final CSVDataExportField field = createCustomizeDataExportField(position);
        field.setFormItemId(formItemId);
        return field;
    }

    public static DraftTaxRateUS createTaxRateUS(final States_US states, final boolean included) {
        final DraftTaxRateUS taxRateUS = new DraftTaxRateUS(states);
        taxRateUS.setIncluded(included);
        ServiceLocator.getPersistance().putTaxRate(taxRateUS);
        return taxRateUS;
    }

    public static DraftTaxRatesUS createTaxRatesUS(final Site site) {
        final DraftTaxRatesUS taxRatesUS = new DraftTaxRatesUS();
        taxRatesUS.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(taxRatesUS);
        return taxRatesUS;
    }

    public static ItemSize createItemSize() {
        final ItemSize itemSize = new ItemSize();
        itemSize.setWidth(1234);
        itemSize.setHeight(12345);
        itemSize.setOverflow_x(WidgetOverflowType.SCROLL);
        itemSize.setOverflow_y(WidgetOverflowType.VISIBLE);
        itemSize.setWidthSizeType(WidgetSizeType.PX);
        itemSize.setHeightSizeType(WidgetSizeType.PX);
        itemSize.setCreateClearDiv(true);
        itemSize.setFloatable(true);
        ServiceLocator.getPersistance().putItemSize(itemSize);
        return itemSize;
    }

    public static AccessibleSettings createAccessibleSettings() {
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.RESTRICTED);
        accessibleSettings.setAdministrators(true);
        accessibleSettings.setVisitors(true);
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        return accessibleSettings;
    }

    public static Background createBackground() {
        final Background background = new Background();
        background.setBackgroundPosition("left");
        background.setBackgroundRepeat("repeat");
        background.setBackgroundImageId(123);
        ServiceLocator.getPersistance().putBackground(background);
        return background;
    }

    public static Border createBorder() {
        final Border border = new Border();
        border.setBorderColor(createStyle("color", StyleType.ALL_SIDES, MeasureUnit.CM, "red"));
        border.setBorderMargin(createStyle("margin", StyleType.ALL_SIDES, MeasureUnit.EM, "10"));
        border.setBorderPadding(createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.PX, "30"));
        border.setBorderWidth(createStyle("width", StyleType.ALL_SIDES, MeasureUnit.PX, "30"));
        border.setBorderStyle(createStyle("solid", StyleType.ALL_SIDES, MeasureUnit.PX, "30"));
        ServiceLocator.getPersistance().putBorder(border);
        return border;
    }

    public static FontsAndColors createFontsAndColorsWithOneCssValue() {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        ServiceLocator.getPersistance().putFontsAndColors(fontsAndColors);
        createFontsAndColorsValue(fontsAndColors);
        return fontsAndColors;
    }

    public static FontsAndColors createFontsAndColors() {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        ServiceLocator.getPersistance().putFontsAndColors(fontsAndColors);
        return fontsAndColors;
    }

    public static List<FontsAndColorsValue> createFontsAndColorsValue(FontsAndColors fontsAndColors) {
        return createFontsAndColorsValue(fontsAndColors, "selector");
    }

    public static List<FontsAndColorsValue> createFontsAndColorsValue(FontsAndColors fontsAndColors, final String selector) {
        return Arrays.asList(createFontsAndColorsValue(fontsAndColors, "name", selector, "value"));
    }

    public static FontsAndColorsValue createFontsAndColorsValue(FontsAndColors fontsAndColors, final String name, final String selector, final String value) {
        final FontsAndColorsValue fontsAndColorsValue = new FontsAndColorsValue();
        fontsAndColorsValue.setDescription("description");
        fontsAndColorsValue.setName(name);
        fontsAndColorsValue.setSelector(selector);
        fontsAndColorsValue.setValue(value);
        fontsAndColors.addCssValue(fontsAndColorsValue);
        ServiceLocator.getPersistance().putFontsAndColorsValue(fontsAndColorsValue);
        return fontsAndColorsValue;
    }

    public static FormExportTask createFormExportTask() {
        return createFormExportTask(1, "name");
    }

    public static FormExportTask createFormExportTask(final int formId, final String name) {
        final FormExportTask formExportTask = new FormExportTask();
        formExportTask.setFormId(formId);
        formExportTask.setName(name);
        formExportTask.setCsvDataExport(createCSVDataExport());
        ServiceLocator.getPersistance().putFormExportTask(formExportTask);
        return formExportTask;
    }

    public static CssParameter createCssParameter(final String name, final String selector, final String value) {
        final CssParameter fontsAndColorsValue = new CssParameter();
        fontsAndColorsValue.setDescription("description");
        fontsAndColorsValue.setName(name);
        fontsAndColorsValue.setSelector(selector);
        fontsAndColorsValue.setValue(value);
        return fontsAndColorsValue;
    }
}

