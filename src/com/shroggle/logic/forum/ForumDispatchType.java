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
package com.shroggle.logic.forum;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject(converter = EnumConverter.class)
public enum ForumDispatchType {

    SHOW_FORUM, SHOW_SUBFORUM, SHOW_THREAD,
    SHOW_CREATE_SUBFORUM, SHOW_RENAME_SUBFORUM,
    SHOW_CREATE_THREAD, SHOW_CREATE_POLL, SHOW_RENAME_THREAD, SHOW_RENAME_POLL,
    SHOW_CREATE_POST, SHOW_EDIT_POST, SHOW_QUOTE_POST

}
