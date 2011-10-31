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
package com.shroggle.logic.site.item;

import com.shroggle.entity.*;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.SaveGalleryRequest;
import com.shroggle.logic.menu.MenusManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;

import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public abstract class ItemCreators {

    public abstract DraftItem create(final int siteId);

    public static ItemCreators newInstance(ItemType itemType) {
        switch (itemType) {
            case ADMIN_LOGIN:
                return new AdminLoginCreator();
            case ADVANCED_SEARCH:
                return new AdvancedSearchCreator();
            case BLOG:
                return new BlogCreator();
            case SCRIPT:
                return new ScriptCreator();
            case BLOG_SUMMARY:
                return new BlogSummaryCreator();
            case CONTACT_US:
                return new ContactUsCreator();
            case CHILD_SITE_REGISTRATION:
                return new ChildSiteRegistartionCreator();
            case CUSTOM_FORM:
                return new CustomFormCreator();
            case IMAGE:
                return new ImageCreator();
            case FORUM:
                return new ForumCreator();
            case SHOPPING_CART:
                return new ShoppingCartCreator();
            case GALLERY:
                return new GalleryCreator();
            case VOTING:
                return new VotingCreator();
            case E_COMMERCE_STORE:
                return new ECommerceCreator();
            case GALLERY_DATA:
                return new GalleryDataCreator();
            case LOGIN:
                return new LoginCreator();
            case MANAGE_VOTES:
                return new ManageVotesCreator();
            case MENU:
                return new MenuCreator();
            case TELL_FRIEND:
                return new TellFriendCreator();
            case TEXT:
                return new TextCreator();
            case VIDEO:
                return new VideoCreator();
            case REGISTRATION:
                return new RegistartionCreator();
            case PURCHASE_HISTORY:
                return new PurchaseHistoryCreator();
            case TAX_RATES:
                return new TaxRatesCreator();
            case SLIDE_SHOW:
                return new SlideShowCreator();
            default: {
                throw new IllegalArgumentException("Unknown ItemType = " + itemType);
            }
        }
    }

    private static class AdminLoginCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftAdminLogin draftAdminLogin = new DraftAdminLogin();
            final International adminLoginInternational = ServiceLocator.getInternationStorage().get("configureAdminLogin", Locale.US);
            draftAdminLogin.setText(adminLoginInternational.get("defaultText"));
            draftAdminLogin.setName(SiteItemsManager.getNextDefaultName(ItemType.ADMIN_LOGIN, siteId));
            draftAdminLogin.setSiteId(siteId);

            ServiceLocator.getPersistance().putItem(draftAdminLogin);
            return draftAdminLogin;
        }
    }

    private static class AdvancedSearchCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftAdvancedSearch draftAdvancedSearch = new DraftAdvancedSearch();
            draftAdvancedSearch.setName(SiteItemsManager.getNextDefaultName(ItemType.ADVANCED_SEARCH, siteId));
            draftAdvancedSearch.setSiteId(siteId);
            draftAdvancedSearch.setGalleryId(-1);
            ServiceLocator.getPersistance().putItem(draftAdvancedSearch);
            return draftAdvancedSearch;
        }
    }

    private static class BlogCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftBlog blog = new DraftBlog();
            blog.setName(SiteItemsManager.getNextDefaultName(ItemType.BLOG, siteId));
            blog.setAddPostRight(AccessGroup.ALL);
            blog.setAddCommentOnPostRight(AccessGroup.ALL);
            blog.setAddCommentOnCommentRight(AccessGroup.ALL);
            blog.setSiteId(siteId);
            persistance.putItem(blog);
            return blog;
        }
    }

    private static class ScriptCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftScript draftScript = new DraftScript();
            draftScript.setName(SiteItemsManager.getNextDefaultName(ItemType.SCRIPT, siteId));
            draftScript.setSiteId(siteId);
            persistance.putItem(draftScript);
            return draftScript;
        }
    }

    private static class BlogSummaryCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftBlogSummary draftBlogSummary = new DraftBlogSummary();
            draftBlogSummary.setName(SiteItemsManager.getNextDefaultName(ItemType.BLOG_SUMMARY, siteId));
            draftBlogSummary.setSiteId(siteId);
            ServiceLocator.getPersistance().putItem(draftBlogSummary);
            return draftBlogSummary;
        }
    }

    private static class ContactUsCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftContactUs contactUs = new DraftContactUs();
            contactUs.setName(SiteItemsManager.getNextDefaultName(ItemType.CONTACT_US, siteId));
            contactUs.setSiteId(siteId);
            final User loginedUser = new UsersManager().getLoginedUser();
            contactUs.setEmail(loginedUser != null ? loginedUser.getEmail() : "");
            persistance.putItem(contactUs);

            final List<DraftFormItem> formItems = FormItemsManager.getFormItems(FormType.CONTACT_US);
            for (final DraftFormItem formItem : formItems) {
                contactUs.addFormItem(formItem);
                persistance.putFormItem(formItem);
            }
            return contactUs;
        }
    }

    private static class ChildSiteRegistartionCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {

            final DraftChildSiteRegistration draftChildSiteRegistration =
                    ChildSiteRegistrationManager.createDefaultChildSiteRegistration(siteId);
            final Persistance persistance = ServiceLocator.getPersistance();
            persistance.putItem(draftChildSiteRegistration);
            persistance.getSite(siteId).addChildSiteRegistrationId(draftChildSiteRegistration.getId());

            for (DraftFormItem item : FormItemsManager.getFormItems(FormType.CHILD_SITE_REGISTRATION)) {
                item.setDraftForm(draftChildSiteRegistration);
                persistance.putFormItem(item);
                draftChildSiteRegistration.addFormItem(item);
            }
            return draftChildSiteRegistration;
        }
    }

    private static class CustomFormCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {

            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftCustomForm customForm = new DraftCustomForm();
            customForm.setName(SiteItemsManager.getNextDefaultName(ItemType.CUSTOM_FORM, siteId));
            customForm.setSiteId(siteId);
            persistance.putItem(customForm);

            final List<DraftFormItem> formItems = FormItemsManager.getFormItems(FormType.CUSTOM_FORM);
            for (final DraftFormItem formItem : formItems) {
                customForm.addFormItem(formItem);
                persistance.putFormItem(formItem);
            }
            return customForm;
        }
    }

    private static class ImageCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();


            final DraftImage image1 = new DraftImage();
            image1.setSiteId(siteId);
            image1.setShowDescriptionOnMouseOver(true);
            persistance.putItem(image1);
            return image1;
        }
    }

    private static class ForumCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftForum forum = new DraftForum();
            forum.setName(SiteItemsManager.getNextDefaultName(ItemType.FORUM, siteId));
            forum.setSiteId(siteId);
            forum.setAllowPolls(true);
            forum.setAllowSubForums(true);
            forum.setCreateSubForumRight(AccessGroup.OWNER);
            forum.setCreatePollRight(AccessGroup.OWNER);
            forum.setCreateThreadRight(AccessGroup.VISITORS);
            forum.setVoteInPollRight(AccessGroup.VISITORS);
            forum.setManageSubForumsRight(AccessGroup.OWNER);
            forum.setManagePostsRight(AccessGroup.OWNER);
            forum.setCreatePostRight(AccessGroup.VISITORS);
            persistance.putItem(forum);

            return forum;
        }
    }

    private static class ShoppingCartCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();


            final DraftShoppingCart draftShoppingCart = new DraftShoppingCart();
            draftShoppingCart.setName(SiteItemsManager.getNextDefaultName(ItemType.SHOPPING_CART, siteId));
            draftShoppingCart.setSiteId(siteId);
            persistance.putItem(draftShoppingCart);

            return draftShoppingCart;
        }
    }

    private static class GalleryCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final SaveGalleryRequest request = GalleryManager.createDefaultGalleryRequest(null, persistance.getSite(siteId), GalleryManager.CreateGalleryRequestType.GALLERY, null);
            final GalleryManager galleryManager = GalleryManager.createInstance(request.getGalleryId(), siteId);
            return galleryManager.createGallery(request.getGallerySave());
        }
    }

    private static class VotingCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final SaveGalleryRequest request = GalleryManager.createDefaultGalleryRequest(null, persistance.getSite(siteId), GalleryManager.CreateGalleryRequestType.VOTING, null);
            final GalleryManager galleryManager = GalleryManager.createInstance(request.getGalleryId(), siteId);
            return galleryManager.createGallery(request.getGallerySave());
        }
    }

    private static class ECommerceCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final SaveGalleryRequest request = GalleryManager.createDefaultGalleryRequest(null, persistance.getSite(siteId), GalleryManager.CreateGalleryRequestType.E_COMMERCE_STORE, "E-Commerce Store");
            final GalleryManager galleryManager = GalleryManager.createInstance(request.getGalleryId(), siteId);
            return galleryManager.createGallery(request.getGallerySave());
        }
    }

    private static class GalleryDataCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftGalleryData draftGalleryData = new DraftGalleryData();
            persistance.putItem(draftGalleryData);

            return draftGalleryData;
        }
    }

    private static class LoginCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftLogin draftLogin = new DraftLogin();
            draftLogin.setSiteId(siteId);
            ServiceLocator.getPersistance().putItem(draftLogin);
            return draftLogin;
        }
    }

    private static class ManageVotesCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftManageVotes draftManageVotes = new DraftManageVotes();
            draftManageVotes.setName(SiteItemsManager.getNextDefaultName(ItemType.MANAGE_VOTES, siteId));
            draftManageVotes.setSiteId(siteId);
            ServiceLocator.getPersistance().putItem(draftManageVotes);

            return draftManageVotes;
        }
    }


    private static class MenuCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();
            final Site site = persistance.getSite(siteId);

            return new MenusManager().createDefaultMenu(site.getMenu());
        }
    }

    private static class TellFriendCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftTellFriend tellFriend = new DraftTellFriend();
            tellFriend.setSiteId(siteId);
            tellFriend.setName(SiteItemsManager.getNextDefaultName(ItemType.TELL_FRIEND, siteId));

            final International international = ServiceLocator.getInternationStorage().get("configureTellFriend", Locale.US);
            tellFriend.setMailSubject(international.get("mailDefaultSubject"));
            final SiteManager siteManager = new SiteManager(siteId);
            tellFriend.setMailText(international.get("mailDefaultText", siteManager.getHisNetworkName(), siteManager.getHisNetworkUrl()));

            persistance.putTellFriend(tellFriend);

            return tellFriend;
        }
    }

    private static class TextCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftText text = new DraftText();
            text.setSiteId(siteId);
            persistance.putItem(text);

            return text;
        }
    }

    private static class VideoCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final DraftVideo video1 = new DraftVideo();
            video1.setSiteId(siteId);
            ServiceLocator.getPersistance().putItem(video1);

            return video1;
        }
    }

    private static class RegistartionCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftRegistrationForm registrationForm = new DraftRegistrationForm();
            registrationForm.setName(SiteItemsManager.getNextDefaultName(ItemType.REGISTRATION, siteId));
            registrationForm.setSiteId(siteId);
            persistance.putRegistrationForm(registrationForm);

            for (DraftFormItem item : FormItemsManager.getFormItems(FormType.REGISTRATION)) {
                item.setDraftForm(registrationForm);
                persistance.putFormItem(item);
                registrationForm.addFormItem(item);
            }

            return registrationForm;
        }
    }

    private static class PurchaseHistoryCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftPurchaseHistory draftPurchaseHistory = new DraftPurchaseHistory();
            draftPurchaseHistory.setName(SiteItemsManager.getNextDefaultName(ItemType.PURCHASE_HISTORY, siteId));
            draftPurchaseHistory.setSiteId(siteId);
            persistance.putItem(draftPurchaseHistory);

            return draftPurchaseHistory;
        }
    }

    private static class TaxRatesCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftTaxRatesUS taxRatesUS = new DraftTaxRatesUS();
            taxRatesUS.setName(SiteItemsManager.getNextDefaultName(ItemType.TAX_RATES, siteId));
            taxRatesUS.setSiteId(siteId);
            persistance.putItem(taxRatesUS);
            for (State state : Country.US.getStates()) {
                final DraftTaxRateUS draftTaxRateUS = new DraftTaxRateUS((States_US) state);
                taxRatesUS.addTaxRate(draftTaxRateUS);
                persistance.putTaxRate(draftTaxRateUS);
            }
            return taxRatesUS;
        }
    }

    private static class SlideShowCreator extends ItemCreators {
        @Override
        public DraftItem create(final int siteId) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final DraftSlideShow slideShow = new DraftSlideShow();
            slideShow.setName(SiteItemsManager.getNextDefaultName(ItemType.SLIDE_SHOW, siteId));
            slideShow.setSiteId(siteId);
            persistance.putItem(slideShow);

            return slideShow;
        }
    }


}
