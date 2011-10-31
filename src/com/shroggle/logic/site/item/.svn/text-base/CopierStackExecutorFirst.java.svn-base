package com.shroggle.logic.site.item;

import com.shroggle.entity.*;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;

/**
 * @author Artem Stasuk
 */
public class CopierStackExecutorFirst implements CopierStackExecutor {

    public CopierStackExecutorFirst(
            final Item first, final CopierStackExecutor copy, final CopierStackExecutor share) {
        if (first == null) {
            throw new IllegalArgumentException("Null first!");
        }

        if (copy == null) {
            throw new IllegalArgumentException("Null copy!");
        }

        if (share == null) {
            throw new IllegalArgumentException("Null share!");
        }

        this.first = first;
        this.copy = copy;
        this.share = share;
    }

    @Override
    public void copy(final CopierStack stack, final Object original) {
        if (first instanceof Gallery && first == original) {
            copy.copy(stack, original);
            return;
        }

        if (first instanceof Form) {
            if (first == original) {
                copy.copy(stack, original);
                return;
            }

            if (original instanceof FormItem) {
                final FormItem formItem = (FormItem) original;
                if (formItem.getForm() == first) {
                    copy.copy(stack, original);
                    return;
                }
            }
        }

        if (first == original) {
            copy.copy(stack, original);
            return;
        }

        share.copy(stack, original);
    }

    private final Item first;
    private final CopierStackExecutor copy;
    private final CopierStackExecutor share;

}
