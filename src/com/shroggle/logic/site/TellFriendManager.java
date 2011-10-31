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
package com.shroggle.logic.site;

import com.shroggle.entity.DraftTellFriend;

/**
 * @author Artem Stasuk
 */
public class TellFriendManager {

    public TellFriendManager(final DraftTellFriend tellFriend) {
        this.tellFriend = tellFriend;
    }

    public TellFriendEdit getEdit() {
        final TellFriendEdit edit = new TellFriendEdit();
        edit.setMailSubject(tellFriend.getMailSubject());
        edit.setMailText(tellFriend.getMailText());
        edit.setTellFriendName(tellFriend.getName());
        edit.setTellFriendId(tellFriend.getId());
        return edit;
    }

    private final DraftTellFriend tellFriend;

}
