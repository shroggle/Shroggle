function Style(id, name, createDefaultMeasurements, createColor) {
    this.name = name;
    this.type = getStyleTypeById(id);
    if (createDefaultMeasurements) {
        this.measureUnits = createDefaultMeasureUnits();
    } else {
        this.measureUnits = createStyleMeasurements(getMeasurementValues(id));
    }
    if (createColor) {
        this.values = createStyleValues(getColorValues(id));
    } else {
        this.values = createStyleValues(getStyleValues(id));
    }

    function createDefaultMeasureUnits() {
        return {topMeasureUnit:"PX",rightMeasureUnit:"PX",bottomMeasureUnit:"PX",leftMeasureUnit:"PX"};
    }

    function createStyleValues(oldValues) {
        return {topValue : oldValues.top, rightValue : oldValues.right,
            bottomValue : oldValues.bottom, leftValue : oldValues.left};
    }

    function createStyleMeasurements(oldValues) {
        return {topMeasureUnit : oldValues.top, rightMeasureUnit : oldValues.right,
            bottomMeasureUnit : oldValues.bottom, leftMeasureUnit : oldValues.left};
    }
}