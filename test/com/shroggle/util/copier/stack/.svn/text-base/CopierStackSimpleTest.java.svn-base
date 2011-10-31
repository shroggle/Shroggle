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
package com.shroggle.util.copier.stack;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class CopierStackSimpleTest {

    @Test(expected = IllegalArgumentException.class)
    public void createNull() {
        new CopierStackSimple(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void copyNull() {
        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        final CopierStackSimple stack = new CopierStackSimple(executorMock);
        Assert.assertNull(stack.copy(null));
    }

    @Test
    public void copySingle() {
        final CopierStackExecutorMock executorMock = new CopierStackExecutorMock();
        executorMock.setResult("12");
        final CopierStackSimple stack = new CopierStackSimple(executorMock);
        Assert.assertEquals("12", stack.copy("13"));
    }

    @Test
    public void copyWithNeed() {
        final CopierStackSimple stack = new CopierStackSimple(new CopierStackExecutor() {

            @Override
            public void copy(final CopierStack stack, final Object original) {
                if (original.equals(1)) {
                    stack.add(original, stack.copy(2));
                }

                if (original.equals(2)) {
                    stack.add(original, "13");
                }
            }

        });
        Assert.assertEquals("13", stack.copy(1));
    }

    @Test
    public void copyWithRelated() {
        final CopierStackSimple stack = new CopierStackSimple(new CopierStackExecutor() {

            @Override
            public void copy(final CopierStack stack, final Object original) {
                if (original.equals(1)) {
                    stack.add(original, "12");
                    stack.copy(2);
                    stack.copy(3);
                }

                if (original.equals(2)) {
                    stack.add(original, "13");
                }

                if (original.equals(3)) {
                    stack.add(original, "14");
                }
            }

        });
        Assert.assertEquals("12", stack.copy(1));
        Assert.assertEquals("13", stack.copy(2));
        Assert.assertEquals("14", stack.copy(3));
    }

    @Test
    public void copyTwice() {
        final CopierStackSimple stack = new CopierStackSimple(new CopierStackExecutor() {

            @Override
            public void copy(final CopierStack stack, final Object original) {
                if (original.equals(3) && !copied3) {
                    copied3 = true;
                    stack.add(original, "14");
                }
            }

            private boolean copied3;

        });
        
        Assert.assertEquals("14", stack.copy(3));
        Assert.assertEquals("14", stack.copy(3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void copyResultNull() {
        final CopierStackSimple stack = new CopierStackSimple(new CopierStackExecutor() {

            @Override
            public void copy(final CopierStack stack, final Object original) {
            }

        });

        stack.copy(3);
    }

}
