package com.shroggle.logic.site.page;

import com.shroggle.entity.PageSettings;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetComposit;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
class WidgetsCopier {

    public static void copyWidgets(final PageSettings original, final PageSettings copy, final Set<Integer> itemIds) {
        for (Widget widget : selectWidgetsWithoutParent(original.getWidgets())) {
            copyWithCompositChildrenRecursively(widget, copy, itemIds);
        }
    }

    private static List<Widget> selectWidgetsWithoutParent(final List<Widget> widgets) {
        final List<Widget> widgetsWithoutParent = new ArrayList<Widget>();
        for (Widget widget : widgets) {
            if (widget.getParent() == null) {
                widgetsWithoutParent.add(widget);
            }
        }
        return widgetsWithoutParent;
    }

    private static Widget copyWithCompositChildrenRecursively(
            final Widget original, final PageSettings pageSettings, final Set<Integer> itemIds) {
        // Copying widgets
        final Widget copied = execute(original, pageSettings, itemIds);

        // Copying composit children.
        if (original.isWidgetComposit()) {
            final WidgetComposit composit = (WidgetComposit) original;
            for (final Widget originalChild : composit.getChilds()) {
                final WidgetComposit copiedComposit = (WidgetComposit) copied;
                final Widget copiedChild = execute(originalChild, pageSettings, itemIds);
                if (copiedChild != null) {
                    copiedComposit.addChild(copiedChild);
                }
            }
        }
        return copied;
    }

    private static Widget execute(final Widget widget, final PageSettings pageSettings, final Set<Integer> itemIds) {
        /**
         * Skip widget items if we can find it in itemIds or itemIds is null in this
         * case we copy all widgets.
         */
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;
            if (itemIds != null
                    && widgetItem.getDraftItem() != null
                    && !itemIds.contains(widgetItem.getDraftItem().getId())) {
                return null;
            }
        }

        final Persistance persistance = ServiceLocator.getPersistance();
        final Widget copyWidget = widget.isWidgetItem() ? new WidgetItem() : new WidgetComposit();
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;
            final WidgetItem copyWidgetItem = (WidgetItem) copyWidget;
            copyWidgetItem.setDraftItem(widgetItem.getDraftItem());
        }

        copyWidget.setPosition(widget.getPosition());
        copyWidget.setCrossWidgetId(widget.getCrossWidgetId());
        copyWidget.setBlueprintEditRuche(widget.isBlueprintEditRuche());
        copyWidget.setBlueprintEditable(widget.isBlueprintEditable());
        copyWidget.setBlueprintRequired(widget.isBlueprintRequired());
        copyWidget.setBlueprintShareble(widget.isBlueprintShareble());
        copyWidget.setCreatedByBlueprintWidget(widget.isCreatedByBlueprintWidget());
        copyWidget.setParentCrossWidgetId(widget.getParentCrossWidgetId());

        ItemCopierUtil.copyStyles(widget, copyWidget);

        persistance.putWidget(copyWidget);
        pageSettings.addWidget(copyWidget);
        return copyWidget;
    }
}
