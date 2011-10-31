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
package com.shroggle.presentation.site;

import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.exception.CannotShowHelpWindowException;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

/**
 * Author: dmitry.solomadin
 * Date: 12.04.2009
 */
@RemoteProxy
public class ShowHelpWindowService extends AbstractService{
    private ConfigStorage configStorage = ServiceLocator.getConfigStorage();

    @RemoteMethod
    public String show(){
        if (configStorage.get().getHelpWindowURL() == null || configStorage.get().getHelpWindowURL().isEmpty()){
            throw new CannotShowHelpWindowException("Cannot show help window.");
        }

        return configStorage.get().getHelpWindowURL();
    }

}
