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
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.process.ThreadUtil;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class FilledFormManagerTest {

    @Test
    public void findPrefilledRegistrationForm() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setFirstName("My First Name");
        user.setLastName("My Last Name");
        user.setScreenName("My Screen Name");
        user.setTelephone("My Telephone Number");
        final FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForVisitorAndFormId(user, site, registrationForm.getFormId());
        final List<FilledFormItem> filledFormItems = filledForm.getFilledFormItems();

        final FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), false);
        final List<FilledFormItem> foundFilledFormItems = foundFilledForm.getFilledFormItems();

        Assert.assertEquals(filledFormItems.size() - 1, foundFilledFormItems.size());
        for (int i = 0; i < foundFilledFormItems.size(); i++) {
            //Title filed must not be included in found filled form
            final FilledFormItem formItem = filledFormItems.get(i);
            final FilledFormItem foundFormItem = foundFilledFormItems.get(i);

            if (!formItem.getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(formItem.getItemName(), foundFormItem.getItemName());

                final List<String> foundItemValues = foundFormItem.getValues();
                final List<String> itemValues = formItem.getValues();

                for (int j = 0; j < foundItemValues.size(); j++) {
                    Assert.assertEquals(itemValues.get(j), foundItemValues.get(j));
                }
            }
        }
    }

    @Test
    public void findPrefilledRegistrationFormForEditVisitorDetails() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setFirstName("My First Name");
        user.setLastName("My Last Name");
        user.setScreenName("My Screen Name");
        user.setTelephone("My Telephone Number");
        FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForVisitorAndFormId(user, site, registrationForm.getFormId());

        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), true);
        final List<FilledFormItem> foundFilledFormItems = foundFilledForm.getFilledFormItems();

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledFormItems.size());
        for (int i = 0; i < foundFilledFormItems.size(); i++) {
            //Title filed must not be included in found filled form
            final FilledFormItem formItem = filledForm.getFilledFormItems().get(i);
            final FilledFormItem foundFormItem = foundFilledFormItems.get(i);

            if (!formItem.getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(formItem.getItemName(), foundFormItem.getItemName());

                final List<String> foundItemValues = foundFormItem.getValues();
                final List<String> itemValues = formItem.getValues();

                for (int j = 0; j < foundItemValues.size(); j++) {
                    Assert.assertEquals(itemValues.get(j), foundItemValues.get(j));
                }
            }
        }
    }

    @Test
    public void findPrefilledRegistrationFormForMasterVisitor() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        User user = new User();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setScreenName("screenName");
        user.setTelephone("telephone");
        ServiceLocator.getPersistance().putUser(user);

        TestUtil.createRegistrationForm();

        FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForMasterVisitor(user);
        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), false);

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size(); i++) {
            //Title filed must not be included in found filled form and for master visitor too
            if (!filledForm.getFilledFormItems().get(i).getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }
    }

    @Test
    public void findPrefilledRegistrationFormForMasterVisitorForEditVisitorDetailsWithForm() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        User user = new User();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setTelephone("telephone");
        ServiceLocator.getPersistance().putUser(user);

        FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForMasterVisitor(user);
        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), true);

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size(); i++) {
            if (!filledForm.getFilledFormItems().get(i).getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }
    }

    @Test
    public void findPrefilledRegistrationFormForMasterVisitorForEditVisitorDetails() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        User user = new User();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setTelephone("telephone");
        ServiceLocator.getPersistance().putUser(user);

        FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForMasterVisitor(user);
        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), true);

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size(); i++) {
            //Title filed must not be included in found filled form and for master visitor too
            if (!filledForm.getFilledFormItems().get(i).getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }
    }

    @Test
    public void findPrefilledCustomForm() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setFirstName("My First Name");
        user.setLastName("My Last Name");
        user.setScreenName("My Screen Name");
        user.setTelephone("My Telephone Number");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), false);

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size(); i++) {
            //Title filed must not be included in found filled form
            if (!filledForm.getFilledFormItems().get(i).getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }
    }

    @Test
    public void findNewestPrefilledCustomForm() throws ServletException, IOException {
        Site site = TestUtil.createSite();
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setFirstName("qwe");
        user.setLastName("qwe1");
        user.setScreenName("qwe3");
        user.setTelephone("qwe2");
        TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);
        ThreadUtil.sleep(1000);
        FilledForm filledForm2 = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems("qwe", "qwe1", "qwe3", "qwe2", "qwe@qwe.qwe"), contactUs);

        FilledForm foundFilledForm = FilledFormManager.findPrefilledForm(user, site.getSiteId(), false);

        Assert.assertEquals(filledForm2.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size(); i++) {
            //Title filed must not be included in found filled form
            if (!filledForm2.getFilledFormItems().get(i).getItemName().equals(formInternational.get(FormItemName.TITLE.toString() + "_FN"))) {
                Assert.assertEquals(filledForm2.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm2.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }
    }

    @Test
    public void getPrefilledFormFilledItem() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        List<String> values = FilledFormManager.getFilledFormItemValueByItemNameList(filledForm, FormItemName.FIRST_NAME, "First Name", contactUs.getFormId());
        Assert.assertEquals(1, values.size());
        Assert.assertEquals("My First Name", values.get(0));
    }

    @Test
    public void getPrefilledFormFilledItemNotExisting() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        List<String> values = FilledFormManager.getFilledFormItemValueByItemNameList(filledForm, FormItemName.FIRST_NAME, "", contactUs.getFormId());
        Assert.assertTrue(values.isEmpty());
    }


    @Test
    public void getFirstFilledFormItemByFormItemName() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(filledFormItems);

        Assert.assertNotNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADDRESS));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_OWN_DOMAIN_NAME));

        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.EMAIL));
        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADOPTIVE_FATHER_NAME));
        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.NAME));

        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName((Integer) null, FormItemName.FIRST_NAME));
        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName(filledForm, null));
        Assert.assertNull(FilledFormManager.getFilledFormItemByFormItemName((Integer) null, null));
    }

    @Test
    public void getFilledFormItemsByFormItemName() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(filledFormItems);

        Assert.assertEquals(2, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.FIRST_NAME).size());
        Assert.assertEquals(1, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.LAST_NAME).size());
        Assert.assertEquals(1, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.ADDRESS).size());
        Assert.assertEquals(2, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.YOUR_OWN_DOMAIN_NAME).size());

        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.EMAIL).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.ADOPTIVE_FATHER_NAME).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, FormItemName.NAME).size());


        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName((FilledForm) null, FormItemName.FIRST_NAME).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm, null).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName((FilledForm) null, null).size());
    }

    @Test
    public void testUpdateFilledFormItems() {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.IMAGE_FILE_UPLOAD);
        oldFilledFormItems.get(0).setValue("old_first_name");
        oldFilledFormItems.get(1).setValue("old_last_name");
        oldFilledFormItems.get(2).setValues(Arrays.asList("123", "keywords"));
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.ADOPTED, FormItemName.AUDIO_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue("new_adopted");
        newFilledFormItems.get(3).setValue("aeraeraser");
        newFilledFormItems.get(4).setValue("new_imageUpload_keywords");

        FilledFormManager.updateFilledFormItems(newFilledFormItems, filledForm);

        Assert.assertEquals(6, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("old_last_name", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("123", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals("new_imageUpload_keywords", filledForm.getFilledFormItems().get(2).getValues().get(1));
        Assert.assertEquals("new_academic_degree", filledForm.getFilledFormItems().get(3).getValues().get(0));
        Assert.assertEquals("new_adopted", filledForm.getFilledFormItems().get(4).getValues().get(0));
        Assert.assertEquals("aeraeraser", filledForm.getFilledFormItems().get(5).getValues().get(0));
    }

    @Test
    public void testUpdateFilledFormItems_withoutOldImage() {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        oldFilledFormItems.get(0).setValue("old_first_name");
        oldFilledFormItems.get(1).setValue("old_last_name");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.ADOPTED, FormItemName.AUDIO_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue("new_adopted");
        newFilledFormItems.get(3).setValue("aeraeraser");
        newFilledFormItems.get(4).setValue("new_imageUpload_keywords");

        FilledFormManager.updateFilledFormItems(newFilledFormItems, filledForm);

        Assert.assertEquals(6, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("old_last_name", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("new_academic_degree", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals("new_adopted", filledForm.getFilledFormItems().get(3).getValues().get(0));
        Assert.assertEquals("aeraeraser", filledForm.getFilledFormItems().get(4).getValues().get(0));
        

        Assert.assertEquals("-1", filledForm.getFilledFormItems().get(5).getValues().get(0));
        Assert.assertEquals("new_imageUpload_keywords", filledForm.getFilledFormItems().get(5).getValues().get(1));
    }

    @Test
    public void testUpdateFilledFormItems_POST_CODE() {
        //Create new coordinate and put it in DB. Then system just get it from there, it don`t use GoogleGeocoding class.
        Coordinate coordinateUS = new Coordinate(Country.US, 0.0, 1.0, "zip");
        ServiceLocator.getPersistance().putCoordinate(coordinateUS);
        Coordinate coordinateAE = new Coordinate(Country.AE, 24332.0, 54.0, "12345");
        ServiceLocator.getPersistance().putCoordinate(coordinateAE);

        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.POST_CODE, FormItemName.COUNTRY);
        oldFilledFormItems.get(0).setValues(Arrays.asList("zip", "US", "0.0", "1.0"));
        oldFilledFormItems.get(1).setValue("US");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.POST_CODE, FormItemName.COUNTRY);
        newFilledFormItems.get(0).setValue("12345");
        newFilledFormItems.get(1).setValue("AE");

        FilledFormManager.updateFilledFormItems(newFilledFormItems, filledForm);

        Assert.assertEquals(2, filledForm.getFilledFormItems().size());
        Assert.assertEquals(4, filledForm.getFilledFormItems().get(0).getValues().size());
        Assert.assertEquals("12345", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("AE", filledForm.getFilledFormItems().get(0).getValues().get(1));
        Assert.assertEquals("24332.0", filledForm.getFilledFormItems().get(0).getValues().get(2));
        Assert.assertEquals("54.0", filledForm.getFilledFormItems().get(0).getValues().get(3));

        Assert.assertEquals("AE", filledForm.getFilledFormItems().get(1).getValue());
    }

    @Test
    public void testUpdateFilledFormItems_createNewVIDEO_FILE_UPLOAD() {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        oldFilledFormItems.get(0).setValue("old_first_name");
        oldFilledFormItems.get(1).setValue("old_last_name");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.VIDEO_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue(("3 "));

        FilledFormManager.updateFilledFormItems(newFilledFormItems, filledForm);

        Assert.assertEquals(4, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("old_last_name", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("new_academic_degree", filledForm.getFilledFormItems().get(2).getValues().get(0));
        int formVideoId = FilledFormItemManager.getIntValue(filledForm.getFilledFormItems().get(3));
        Assert.assertTrue(formVideoId > 0);
        FormVideo formVideo = ServiceLocator.getPersistance().getFormVideoById(formVideoId);
        Assert.assertNotNull(formVideo);
        Assert.assertEquals(3, formVideo.getQuality());
        Assert.assertEquals(null, formVideo.getVideoId());
        Assert.assertEquals(null, formVideo.getImageId());
    }

    @Test
    public void testUpdateFilledFormItems_withExistingVIDEO_FILE_UPLOAD() {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.VIDEO_FILE_UPLOAD);
        oldFilledFormItems.get(0).setValue("old_first_name");
        oldFilledFormItems.get(1).setValue("old_last_name");
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        formVideo.setQuality(1);
        oldFilledFormItems.get(2).setValue("" + formVideo.getFormVideoId());

        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.VIDEO_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue(("3 "));

        FilledFormManager.updateFilledFormItems(newFilledFormItems, filledForm);

        Assert.assertEquals(4, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("old_last_name", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("new_academic_degree", filledForm.getFilledFormItems().get(3).getValues().get(0));
        int formVideoId = FilledFormItemManager.getIntValue(filledForm.getFilledFormItems().get(2));
        Assert.assertTrue(formVideoId > 0);
        FormVideo newFormVideo = ServiceLocator.getPersistance().getFormVideoById(formVideoId);
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(3, newFormVideo.getQuality());
        Assert.assertEquals(video.getVideoId(), newFormVideo.getVideoId().intValue());
        Assert.assertEquals(image.getFormFileId(), newFormVideo.getImageId().intValue());
        Assert.assertEquals(formVideo.getFormVideoId(), newFormVideo.getFormVideoId());
    }

    @Test
    public void selectFilledFormItemsByFormItemName() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(filledFormItems);

        Assert.assertEquals(2, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.FIRST_NAME).size());
        Assert.assertEquals(1, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.LAST_NAME).size());
        Assert.assertEquals(1, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.ADDRESS).size());
        Assert.assertEquals(1, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.YOUR_OWN_DOMAIN_NAME).size());

        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.EMAIL).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.ADOPTIVE_FATHER_NAME).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), FormItemName.NAME).size());


        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName((FilledForm) null, FormItemName.FIRST_NAME).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), null).size());
        Assert.assertEquals(0, FilledFormManager.getFilledFormItemsByFormItemName((FilledForm) null, null).size());
    }


    @Test
    public void getFilledFormItemByItemName() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledFormItems.get(0).setItemName("firstName");
        filledFormItems.get(1).setItemName("lastName");
        filledFormItems.get(2).setItemName("adress");
        filledFormItems.get(3).setItemName("domain");
        filledForm.setFilledFormItems(filledFormItems);

        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemName(filledForm, "firstName"));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemName(filledForm, "lastName"));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemName(filledForm, "adress"));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemName(filledForm, "domain"));

        Assert.assertEquals(filledFormItems.get(0), FilledFormManager.getFilledFormItemByItemName(filledForm, "firstName"));
        Assert.assertEquals(filledFormItems.get(1), FilledFormManager.getFilledFormItemByItemName(filledForm, "lastName"));
        Assert.assertEquals(filledFormItems.get(2), FilledFormManager.getFilledFormItemByItemName(filledForm, "adress"));
        Assert.assertEquals(filledFormItems.get(3), FilledFormManager.getFilledFormItemByItemName(filledForm, "domain"));

        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(filledForm, ""));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(filledForm, ""));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(filledForm, ""));


        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(null, "domain"));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(filledForm, null));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemName(null, null));
    }


    @Test
    public void getFilledFormItemByItemPosition() {
        DraftContactUs contactUs = TestUtil.createContactUsForm();
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createDefaultRegistrationFilledFormItems(), contactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledFormItems.get(0).setPosition(0);
        filledFormItems.get(1).setPosition(1);
        filledFormItems.get(2).setPosition(2);
        filledFormItems.get(3).setPosition(3);
        filledForm.setFilledFormItems(filledFormItems);

        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 0));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 1));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 2));
        Assert.assertNotNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 3));

        Assert.assertEquals(filledFormItems.get(0), FilledFormManager.getFilledFormItemByItemPosition(filledForm, 0));
        Assert.assertEquals(filledFormItems.get(1), FilledFormManager.getFilledFormItemByItemPosition(filledForm, 1));
        Assert.assertEquals(filledFormItems.get(2), FilledFormManager.getFilledFormItemByItemPosition(filledForm, 2));
        Assert.assertEquals(filledFormItems.get(3), FilledFormManager.getFilledFormItemByItemPosition(filledForm, 3));

        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 4));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 5));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, 6));


        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(null, 1));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(filledForm, null));
        Assert.assertNull(FilledFormManager.getFilledFormItemByItemPosition(null, null));
    }

    @Test
    public void testGetDomainName() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledFormItem.setValue("domainName");
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(filledFormItem);
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));

        Assert.assertEquals("domainName", FilledFormManager.getDomainName(filledFormItems));
    }


    @Test
    public void testGetDomainName_withoutFormItems() {
        Assert.assertEquals(null, FilledFormManager.getDomainName(null));
    }

    @Test
    public void testGetDomainName_withEmptyFormItems() {
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();

        Assert.assertEquals(null, FilledFormManager.getDomainName(filledFormItems));
    }


    @Test
    public void testGetDomainName_withoutDomainNameFormItem() {
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));
        Assert.assertEquals(null, FilledFormManager.getDomainName(filledFormItems));
    }


    @Test
    public void testGetDomainName_withoutItemValues() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<FilledFormItem> filledFormItems = null;
        List<String> itemValues = null;
        filledFormItem.setValues(itemValues);

        Assert.assertEquals(null, FilledFormManager.getDomainName(filledFormItems));
    }

    @Test
    public void testGetDomainName_withEmptyItemValue() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledFormItem.setValue("");
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(filledFormItem);
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));

        Assert.assertEquals(null, FilledFormManager.getDomainName(filledFormItems));
    }


    @Test
    public void testGetPageSiteName() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        filledFormItem.setValue("pageSiteName");
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(filledFormItem);
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));

        Assert.assertEquals("pageSiteName", FilledFormManager.getPageSiteName(filledFormItems));
    }


    @Test
    public void testGetPageSiteName_withoutFormItems() {
        Assert.assertEquals(null, FilledFormManager.getPageSiteName(null));
    }

    @Test
    public void testGetPageSiteName_withEmptyFormItems() {
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();

        Assert.assertEquals(null, FilledFormManager.getPageSiteName(filledFormItems));
    }


    @Test
    public void testGetPageSiteName_withoutDomainNameFormItem() {
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));
        Assert.assertEquals(null, FilledFormManager.getPageSiteName(filledFormItems));
    }


    @Test
    public void testGetPageSiteName_withoutItemValues() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<FilledFormItem> filledFormItems = null;
        List<String> itemValues = null;
        filledFormItem.setValues(itemValues);

        Assert.assertEquals(null, FilledFormManager.getPageSiteName(filledFormItems));
    }

    @Test
    public void testGetPageSiteName_withEmptyItemValue() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        filledFormItem.setValue("");
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        filledFormItems.add(filledFormItem);
        filledFormItems.add(TestUtil.createFilledFormItem(FormItemName.NAME));

        Assert.assertEquals(null, FilledFormManager.getPageSiteName(filledFormItems));
    }

    @Test
    public void testGetFilledFormsByNetworkSiteId_withChildSiteRegistrationsAndChildSites() {
        final Site networkSite = TestUtil.createSite();
        List<Integer> childSiteRegistrationsIds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            final User user = TestUtil.createUser();
            DraftChildSiteRegistration reg = TestUtil.createChildSiteRegistration("name", "comment", networkSite);
            FilledForm childSiteRegistrationForm = TestUtil.createFilledChildSiteRegistrationFormForVisitor(user, reg.getFormId());
            user.addFilledForm(childSiteRegistrationForm);
            childSiteRegistrationsIds.add(reg.getFormId());
        }
        networkSite.setChildSiteRegistrationsId(childSiteRegistrationsIds);


        List<FilledForm> filledForms = FilledFormManager.getFilledFormsByNetworkSiteId(networkSite.getSiteId());


        Assert.assertNotNull(filledForms.size());
        Assert.assertEquals(10, filledForms.size());
    }

    @Test
    public void testCreateFilledFormByLoginedUser_withoutDataInUser() {
        User user = TestUtil.createUser();
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setTelephone(null);
        user.setScreenName(null);

        FilledForm filledForm = FilledFormManager.createFilledFormByLoginedUser(user);

        FilledFormItem filledFormItemEmail = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.EMAIL);
        Assert.assertEquals(filledFormItemEmail.getValue(), "");

        FilledFormItem filledFormItemFirstName = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME);
        Assert.assertEquals(filledFormItemFirstName.getValue(), "");

        FilledFormItem filledFormItemLastName = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME);
        Assert.assertEquals(filledFormItemLastName.getValue(), "");

        FilledFormItem filledFormItemNick = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME);
        Assert.assertEquals(filledFormItemNick.getValue(), "");

        FilledFormItem filledFormItemTelephone = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.TELEPHONE);
        Assert.assertEquals(filledFormItemTelephone.getValue(), "");
    }

    @Test
    public void testCreateCoordinateValues_withCountryInOldValues() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.COUNTRY, FormItemName.LAST_NAME, FormItemName.IMAGE_FILE_UPLOAD);
        oldFilledFormItems.get(0).setValue("US");
        oldFilledFormItems.get(1).setValue("old_last_name");
        oldFilledFormItems.get(2).setValue("old_imageUpload");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.ADOPTED, FormItemName.AUDIO_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue("new_adopted");
        newFilledFormItems.get(3).setValue("aeraeraser");
        newFilledFormItems.get(4).setValue("new_imageUpload");

        //Create new coordinate and put it in DB. Then system just get it from there, it don`t use GoogleGeocoding class.
        Coordinate coordinate = new Coordinate(Country.US, 1.0, 2.0, "123");
        ServiceLocator.getPersistance().putCoordinate(coordinate);

        String[] array = FilledFormManager.createCoordinateValues(newFilledFormItems, filledForm, "123");
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("US", array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals("2.0", array[3]);
    }


    @Test
    public void testCreateCoordinateValues_withCountryInNewValues() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.COUNTRY, FormItemName.LAST_NAME, FormItemName.IMAGE_FILE_UPLOAD);
        oldFilledFormItems.get(0).setValue("US");
        oldFilledFormItems.get(1).setValue("old_last_name");
        oldFilledFormItems.get(2).setValue("old_imageUpload");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.COUNTRY, FormItemName.ACADEMIC_DEGREE, FormItemName.ADOPTED, FormItemName.AUDIO_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("AE");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue("new_adopted");
        newFilledFormItems.get(3).setValue("aeraeraser");
        newFilledFormItems.get(4).setValue("new_imageUpload");

        //Create new coordinate and put it in DB. Then system just get it from there, it don`t use GoogleGeocoding class.
        Coordinate coordinate = new Coordinate(Country.AE, 1.0, 2.0, "123");
        ServiceLocator.getPersistance().putCoordinate(coordinate);

        String[] array = FilledFormManager.createCoordinateValues(newFilledFormItems, filledForm, "123");
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("AE", array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals("2.0", array[3]);
    }

    @Ignore //todo fix this on server (java.net.ConnectException: Network is unreachable). Tolik
    @Test
    public void testCreateCoordinateValues_withoutCountry() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        //Creating filled form.
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        //Creating old items.
        final List<FilledFormItem> oldFilledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.IMAGE_FILE_UPLOAD);
        oldFilledFormItems.get(0).setValue("name");
        oldFilledFormItems.get(1).setValue("old_last_name");
        oldFilledFormItems.get(2).setValue("old_imageUpload");
        filledForm.setFilledFormItems(oldFilledFormItems);

        //Creating new items.
        final List<FilledFormItem> newFilledFormItems =
                TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ACADEMIC_DEGREE, FormItemName.ADOPTED, FormItemName.AUDIO_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        newFilledFormItems.get(0).setValue("new_first_name");
        newFilledFormItems.get(1).setValue("new_academic_degree");
        newFilledFormItems.get(2).setValue("new_adopted");
        newFilledFormItems.get(3).setValue("aeraeraser");
        newFilledFormItems.get(4).setValue("new_imageUpload");

        //Create new coordinate and put it in DB. Then system just get it from there, it don`t use GoogleGeocoding class.
        Coordinate coordinate = new Coordinate(Country.US, 1.0, 2.0, "123");
        ServiceLocator.getPersistance().putCoordinate(coordinate);

        String[] array = FilledFormManager.createCoordinateValues(newFilledFormItems, filledForm, "123");
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("US", array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals("2.0", array[3]);
    }

    @Test
    public void isGotPrefilledRecordForSelect(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, 0);

        Assert.assertTrue(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("asd");
        }}, formItem, 1, "asd"));
    }

    @Test
    public void isGotPrefilledRecordForSelect_withoutRightRecord(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, 0);

        Assert.assertFalse(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("asd");
        }}, formItem, 1, "as"));
    }

    @Test
    public void isGotPrefilledRecordForSelect_withWrongSelectNumber(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, 0);

        Assert.assertFalse(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("asd");
        }}, formItem, 2, "asd"));
    }
    
    @Test
    public void isGotPrefilledRecordForSelect_withDateField1(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.DATE_ADDED, 0);

        Assert.assertTrue(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("05");
        }}, formItem, 1, "5"));
    }

    @Test
    public void isGotPrefilledRecordForSelect_withDateField2(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.DATE_ADDED, 0);

        Assert.assertTrue(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("5");
        }}, formItem, 1, "05"));
    }
    
    @Test
    public void isGotPrefilledRecordForSelect_withDateField3(){
        FormItem formItem = TestUtil.createFormItem(FormItemName.DATE_ADDED, 0);

        Assert.assertFalse(FilledFormManager.isGotPrefilledRecordForSelect(new ArrayList<String>(){{
            add("asd");
        }}, formItem, 1, "as"));
    }

    private final International formInternational = FilledFormManager.getFormInternational();

}
