package com.shroggle.logic.site.item;

import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;
import com.shroggle.util.copier.stack.CopierStackSimple;

/**
 * @author Artem Stasuk
 */
public class CopierStackFirst extends CopierStackSimple {

    public CopierStackFirst(final CopierStackExecutor executor) {
        super(executor);
    }

    @Override
    public <T> T copy(T original) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
