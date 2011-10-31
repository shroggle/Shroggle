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
package com.shroggle.presentation.account.dashboard.keywordManager;

import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.logic.user.dashboard.keywordManager.SEOTerm;
import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateKeyphraseService extends AbstractService {

    @RemoteMethod
    public List<UpdateKeyphraseResponse> execute(final int pageId, final String keyphrase){
        final List<UpdateKeyphraseResponse> response = new ArrayList<UpdateKeyphraseResponse>();

        final KeywordManager keywordManager = new KeywordManager(pageId);
        for (SEOTerm seoTerm : SEOTerm.values()){
            final UpdateKeyphraseResponse updateKeyphraseResponse = new UpdateKeyphraseResponse();

            updateKeyphraseResponse.setSeoTerm(seoTerm);
            updateKeyphraseResponse.setPresent(keywordManager.isKeyphrasePresent(seoTerm, keyphrase));
            updateKeyphraseResponse.setDensity(keywordManager.calculateDensity(seoTerm, keyphrase));

            response.add(updateKeyphraseResponse);
        }

        return response;
    }
}
