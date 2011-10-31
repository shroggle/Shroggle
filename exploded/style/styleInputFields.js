function    showStyleInputs() {
    var styleElements = ["borderMargin", "borderPadding", "borderWidth", "borderStyle", "borderColor"];
    for (var i in styleElements) {
        var elementId = styleElements[i];
        try {
            document.getElementById(elementId + "ValueDiv").style.display = "none";
            document.getElementById(elementId + "VerticalHorizontal").style.display = "none";
            document.getElementById(elementId + "Separately").style.display = "none";
            document.getElementById(elementId + "EmptyDiv").style.display = "none";
            document.getElementById(elementId + createSelectedElementName(elementId)).style.display = "block";
        } catch(ex) {
        }
    }
    function createSelectedElementName(elementId) {
        var type = document.getElementById(elementId).value;
        if (type == "NONE") {
            return "EmptyDiv";
        } else if (type == "ALL_SIDES") {
            return "ValueDiv";
        } else if (type == "VERTICAL_HORIZONTAL") {
            return "VerticalHorizontal";
        } else if (type == "EACH_SIDE_SEPARATELY") {
            return "Separately";
        }
        return "EmptyDiv";
    }
}


function getStyleValues(id) {
    var response = new Object();
    var type = getStyleTypeById(id);
    if (type == "NONE") {
        response.top = response.right = response.bottom = response.left = "";
    } else if (type == "ALL_SIDES") {
        response.right = response.bottom = response.left = response.top = document.getElementById(id + "Value").value;
    } else if (type == "VERTICAL_HORIZONTAL") {
        response.top = response.bottom = document.getElementById(id + "Vertical").value;
        response.right = response.left = document.getElementById(id + "Horizontal").value;
    } else if (type == "EACH_SIDE_SEPARATELY") {
        response.top = document.getElementById(id + "Top").value;
        response.right = document.getElementById(id + "Right").value;
        response.bottom = document.getElementById(id + "Bottom").value;
        response.left = document.getElementById(id + "Left").value;
    }
    return response;
}

function getMeasurementValues(id) {
    var response = new Object();
    var type = getStyleTypeById(id);
    if (type == "NONE") {
        response.top = response.right = response.bottom = response.left = "PX";
    } else if (type == "ALL_SIDES") {
        response.right = response.bottom = response.left = response.top = document.getElementById(id + "ValueMeasureUnit").value;
    } else if (type == "VERTICAL_HORIZONTAL") {
        response.top = response.bottom = document.getElementById(id + "VerticalMeasureUnit").value;
        response.right = response.left = document.getElementById(id + "HorizontalMeasureUnit").value;
    } else if (type == "EACH_SIDE_SEPARATELY") {
        response.top = document.getElementById(id + "TopMeasureUnit").value;
        response.right = document.getElementById(id + "RightMeasureUnit").value;
        response.bottom = document.getElementById(id + "BottomMeasureUnit").value;
        response.left = document.getElementById(id + "LeftMeasureUnit").value;
    }
    return response;
}

function getColorValues(id) {
    var response = new Object();
    var type = getStyleTypeById(id);
    if (type == "NONE") {
        response.top = response.right = response.bottom = response.left = "transparent";
    } else if (type == "ALL_SIDES") {
        response.right = response.bottom = response.left = response.top = getColorPickerValueById(id + "Value");
    } else if (type == "VERTICAL_HORIZONTAL") {
        response.top = response.bottom = getColorPickerValueById(id + "Vertical");
        response.right = response.left = getColorPickerValueById(id + "Horizontal");
    } else if (type == "EACH_SIDE_SEPARATELY") {
        response.top = getColorPickerValueById(id + "Top");
        response.right = getColorPickerValueById(id + "Right");
        response.bottom = getColorPickerValueById(id + "Bottom");
        response.left = getColorPickerValueById(id + "Left");
    }
    return response;
}


function styleToString(values, measurements) {
    checkValues();
    return values.top + measurements.top + " " +
            values.right + measurements.right + " " +
            values.bottom + measurements.bottom + " " +
            values.left + measurements.left + ";";

    function checkValues() {
        values.top = !measurements.top ? values.top : numberValue(values.top);
        values.right = !measurements.right ? values.right : numberValue(values.right);
        values.bottom = !measurements.bottom ? values.bottom : numberValue(values.bottom);
        values.left = !measurements.left ? values.left : numberValue(values.left);
    }
}

function styleToStringWithSign(values, measurements) {
    checkSignedIntValues();
    return values.top + measurements.top + " " +
            values.right + measurements.right + " " +
            values.bottom + measurements.bottom + " " +
            values.left + measurements.left + ";";

    function checkSignedIntValues() {
        values.top = !measurements.top ? values.top : signedNumberValue(values.top);
        values.right = !measurements.right ? values.right : signedNumberValue(values.right);
        values.bottom = !measurements.bottom ? values.bottom : signedNumberValue(values.bottom);
        values.left = !measurements.left ? values.left : signedNumberValue(values.left);
    }
}


var styleColorPickers;
function showStyleColorPicker(settings) {
    var id = settings.id;
    var value = settings.value;
    var onChangeFunction = settings.onChangeFunction;
    styleColorPickers = styleColorPickers ? styleColorPickers : new Array();
    var colorPicker = new ColorPicker({
        renderTo: id,
        width: 85,
        textInputFieldWidth: 50,
        selectedColor : value,
        onColorStateChanged : function() {
            try {
                onChangeFunction();
            } catch(ex) {
            }
        }
    });
    colorPicker.id = id;
    styleColorPickers.push(colorPicker);

    if ($("#siteOnItemRightType").val() == "READ") {
        disableColorPicker(id);
    }
}

function getColorPickerById(id) {
    for (var i in styleColorPickers) {
        if (styleColorPickers[i].id == id) {
            return styleColorPickers[i];
        }
    }
    return undefined;
}

function getColorPickerValueById(id) {
    var colorPicker = getColorPickerById(id);
    if (colorPicker) {
        return colorPicker.getValue();
    } else {
        return "transparent";
    }
}

function getStyleTypeById(id) {
    try {
        return document.getElementById(id).value;
    } catch(ex) {
        return "NONE";
    }
}


