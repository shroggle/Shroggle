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
package com.shroggle.util.copier;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class CopierWorkerUnmodificable implements CopierWorker {

    @Override
    public void copy(CopierItem item) {
        if (item.getOriginalValue() == null) {
            item.setCopyValue(null);
        } else if (item.getOriginalValue() instanceof Enum) {
            item.setCopyValue(item.getOriginalValue());
        } else {
            if (unmodificableTypes.contains(item.getOriginalValue().getClass())) {
                item.setCopyValue(item.getOriginalValue());
            }
        }
    }

    private final static Set<Class> unmodificableTypes;

    static {
        final Set<Class> tempScalarTypes = new HashSet<Class>();
        tempScalarTypes.add(Integer.class);
        tempScalarTypes.add(Boolean.class);
//        tempScalarTypes.add(Byte.class);
        tempScalarTypes.add(String.class);
        tempScalarTypes.add(Double.class);
        tempScalarTypes.add(Long.class);
        tempScalarTypes.add(Float.class);
        tempScalarTypes.add(Short.class);
        tempScalarTypes.add(Date.class);
        tempScalarTypes.add(Timestamp.class);
        tempScalarTypes.add(java.sql.Date.class);
        unmodificableTypes = Collections.unmodifiableSet(tempScalarTypes);
    }

}
