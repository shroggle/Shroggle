var defaultColorPickerSettings = {
    renderTo: "colorPicker",
    width: 160,
    textInputFieldWidth: 125,
    selectedColor : ""
};


function ColorPicker(settings) {
    window.parent.document.onclick = function(event) {
        var element;
        if (event) {
            element = event.target;
        } else if (window.event) {
            element = window.event.srcElement;
        }
        if (element.className != "color_picker_trigger_image") {
            hidePalette();
        }
    };
    this.settings = createColorPickerSettings(settings);
    var mainDiv = document.getElementById(settings.renderTo);
    if (mainDiv) {
        if (!this.settings.notSetWidth) {
            mainDiv.style.width = this.settings.width + "px";
        }
        mainDiv.colorPicker = this;

        var inputText = window.parent.document.createElement("input");
        inputText.type = "text";
        this.settings.valueInputId = "ColorPickerTextValue" + this.settings.renderTo;
        inputText.id = this.settings.valueInputId;
        inputText.style.width = this.settings.textInputFieldWidth + "px";
        inputText.maxLength = "7";
        inputText.className = "color_picker_text";
        inputText.value = this.settings.selectedColor;
        inputText.colorPicker = this;
        inputText.onkeypress = function (event) {
            return formatColorPickerText(this, event);
        };
        inputText.onkeyup = function () {
            colorStateChanged(this);
        };

        var span = window.parent.document.createElement("span");
        this.settings.triggerImageId = "ColorPickerTriggerSpan" + this.settings.renderTo;
        span.id = this.settings.triggerImageId;
        span.style.backgroundColor = this.settings.selectedColor;
        span.className = "color_picker_trigger_image";
        span.colorPicker = this;
        span.onclick = function () {
            showPalette(this.colorPicker);
        };

        var brClear = window.parent.document.createElement("br");
        brClear.clear = "all";

        mainDiv.appendChild(inputText);
        mainDiv.appendChild(span);
        mainDiv.appendChild(brClear);
    }

    function colorStateChanged(textInput) {
        if (isCorrectValue(textInput.value)) {
            try {
                if (textInput.colorPicker.settings.onColorStateChanged) {
                    textInput.colorPicker.settings.onColorStateChanged();
                }
            } catch(ex) {
            }
            textInput.className = "color_picker_text";
            document.getElementById("ColorPickerTriggerSpan" +
                                    textInput.id.substring(20, textInput.id.length)).style.backgroundColor = textInput.value;
        }
        else {
            textInput.className = "color_picker_text_invalid";
        }
    }
}

function isCorrectValue(value) {
    return (value && (value.length == 7 || value == "transparent") || value == "");
}

// ---------------------------------------------------------------------------------------------------------------------

function createColorPickerSettings(settings) {
    if (!settings) {
        settings = defaultColorPickerSettings;
    } else {
        settings.width = settings.width ? settings.width : defaultColorPickerSettings.width;
        settings.renderTo = settings.renderTo ? settings.renderTo : defaultColorPickerSettings.renderTo;
        settings.textInputFieldWidth = settings.textInputFieldWidth ? settings.textInputFieldWidth :
                                       defaultColorPickerSettings.textInputFieldWidth;

        if (settings.textInputFieldWidth > settings.width) {
            settings.textInputFieldWidth = defaultColorPickerSettings.textInputFieldWidth;
            settings.width = defaultColorPickerSettings.width;
        }
        settings.selectedColor = (settings.selectedColor && settings.selectedColor != "transparent") ? settings.selectedColor : defaultColorPickerSettings.selectedColor;
    }
    return settings;
}

// ---------------------------------------------------------------------------------------------------------------------

function hidePalette() {
    var paletteDiv = document.getElementById("colorPickerPaletteDiv");
    if (paletteDiv) {
        window.parent.document.body.removeChild(paletteDiv);
        return true;
    } else {
        return false;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showPalette(colorPicker) {
    if (hidePalette()) {
        return;
    }
    var selectedColor = colorPicker.getValue();
    var colors = [
        "#000000","#993300", "#333300", "#003300", "#003366", "#000080", "#333399", "#333333", "#800000","#FF6600",
        "#808000", "#008000", "#008080", "#0000FF", "#666699", "#808080", "#FF0000", "#FF9900", "#99CC00","#339966",
        "#33CCCC", "#3366FF", "#800080", "#969696", "#FF00FF", "#FFCC00", "#FFFF00", "#00FF00", "#00FFFF","#00CCFF",
        "#993366", "#C0C0C0", "#FF99CC", "#FFCC99", "#FFFF99", "#CCFFCC", "#CCFFFF", "#99CCFF", "#CC99FF","#FFFFFF", "transparent"];
    var mainDiv = window.parent.document.createElement("div");
    mainDiv.style.position = "absolute";
    mainDiv.style.zIndex = "99999999";
    mainDiv.id = "colorPickerPaletteDiv";
    mainDiv.className = "color_picker_palette";
    colorPicker.settings.paletteDivId = mainDiv.id;
    for (var i = 0; i < colors.length; i++) {
        createPaletteColor();
    }
    window.parent.document.body.appendChild(mainDiv);
    var textInput = document.getElementById(colorPicker.settings.valueInputId);
    if (textInput) {
        mainDiv.style.top = (findPosAbs(textInput).top + textInput.offsetHeight) + "px";
        mainDiv.style.left = findPosAbs(textInput).left + "px";
    }
    function createPaletteColor() {
        var colorDiv = window.parent.document.createElement("div");
        colorDiv.colorPicker = colorPicker;
        colorDiv.onclick = function () {
            hidePalette();
            this.colorPicker.setValue(this.id);
        };
        colorDiv.id = colors[i];
        if (selectedColor == colors[i]) {
            colorDiv.className = "color_picker_selected_palette";
        }
        if (colors[i] == "transparent") {
            colorDiv.innerHTML = "not set";
            colorDiv.style.width = "95%";
            colorDiv.style.backgroundColor = colors[i];
            colorDiv.style.textAlign = "center";
        } else {
            var colorSpan = window.parent.document.createElement("span");
            colorSpan.style.backgroundColor = colors[i];
            colorDiv.appendChild(colorSpan);
        }
        mainDiv.appendChild(colorDiv);
    }
}


ColorPicker.prototype.getValue = function () {
    if (this.settings && this.settings.valueInputId) {
        var valueInput = document.getElementById(this.settings.valueInputId);
        if (valueInput && valueInput.value) {
            return valueInput.value;
        }
    }
    return "transparent";
};

ColorPicker.prototype.setValue = function (value) {
    if (isCorrectValue(value)) {
        if (this.settings.valueInputId) {
            var valueInput = document.getElementById(this.settings.valueInputId);
            if (valueInput) {
                valueInput.value = value == "transparent" ? "" : value;
            }
        }
        if (this.settings.triggerImageId) {
            var triggerImage = document.getElementById(this.settings.triggerImageId);
            if (triggerImage) {
                triggerImage.style.backgroundColor = value;
            }
        }
        try {
            if (this.settings.onColorStateChanged) {
                this.settings.onColorStateChanged();
            }
            document.getElementById(this.settings.valueInputId).className = "color_picker_text";
        } catch(ex) {
        }
    }
};

// ---------------------------------------------------------------------------------------------------------------------

function formatColorPickerText(myfield, e) {
    var key, keychar;
    if (window.event) {
        key = window.event.keyCode;
    } else if (e) {
        key = e.which;
    } else {
        return true;
    }
    var caretPosition = getCaretPosition(myfield);
    keychar = String.fromCharCode(key);
    if ((key == null) || (key == 0) || (key == 8) ||
        (key == 9) || (key == 13) || (key == 27)) {
        return true;
    } else if (keychar == "#" && caretPosition == 0) {
        return true;
    } else return (("0123456789abcdef").indexOf(keychar) > -1 && caretPosition > 0);
}

// ---------------------------------------------------------------------------------------------------------------------

function getCaretPosition(element) {
    var caretPosition = 0;
    // IE Support
    if (document.selection) {
        element.focus();
        var selection = document.selection.createRange();
        selection.moveStart('character', -element.value.length);
        caretPosition = selection.text.length;
    }
    // Firefox support
    else if (element.selectionStart || element.selectionStart == '0') {
        caretPosition = element.selectionStart;
    }
    return caretPosition;
}


