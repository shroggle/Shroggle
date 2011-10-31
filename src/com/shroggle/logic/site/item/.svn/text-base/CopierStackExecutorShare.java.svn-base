package com.shroggle.logic.site.item;

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;

/**
 * @author Artem Stasuk
 */
public class CopierStackExecutorShare implements CopierStackExecutor {

    public CopierStackExecutorShare(final Site site, final CopierStackExecutor executor) {
        if (site == null) {
            throw new IllegalArgumentException("Null site!");
        }

        if (executor == null) {
            throw new IllegalArgumentException("Null executor!");
        }

        this.site = site;
        this.executor = executor;
    }

    @Override
    public void copy(final CopierStack stack, final Object original) {
        if (original == null) {
            return;
        }

        executor.copy(stack, original);

        final Object copied = stack.copy(original);
        if (original instanceof DraftItem) {
            final DraftItem copiedDraftItem = (DraftItem) copied;
            new ItemManager(copiedDraftItem).share(site, SiteOnItemRightType.EDIT);
        }
    }

    private final Site site;
    private final CopierStackExecutor executor;

}
