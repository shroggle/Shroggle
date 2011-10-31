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
package com.shroggle.logic;

import com.shroggle.exception.AllNamesAreUsedException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dmitry.solomadin
 */
public class DefaultNameUtil {

    public String getNext(final String defaultName, final boolean dontUseFirstNumber, final Set<String> namesInUse) {
        int number = 1;

        final Set<String> namesInUseLowerCase = new HashSet<String>();
        for (String nameInUse : namesInUse){
            namesInUseLowerCase.add(nameInUse.toLowerCase());
        }
        
        while (number < Integer.MAX_VALUE) {
            if (!namesInUseLowerCase.contains(defaultName.toLowerCase() +
                    (dontUseFirstNumber && number == 1 ? "" : number))) {
                return defaultName + (dontUseFirstNumber && number == 1 ? "" : number);
            }
            number++;
        }
        throw new AllNamesAreUsedException();
    }

}
