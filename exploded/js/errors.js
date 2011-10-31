/*
 Settings:
 highlighting - if true it will highlight error block when error is occured
 scrolling - if true it will scroll window to the top of the page when error is occured
 errorClassName - Class that will be set to error if one occurs. "error" by default.
 emptyErrorClassName - Class that will be set to error on remove or on class creation. "emptyError" by default
 */
function Errors(settings, errorBlockName) {
    // Accuring error block
    if (!errorBlockName) {
        this.errors = document.getElementById("errors");
    } else {
        this.errors = document.getElementById(errorBlockName);
    }
    // Applying settings
    if (settings) {
        this.settings = settings;
        if (!this.settings.errorClassName) {
            this.settings.errorClassName = "error";
        }
        if (!this.settings.emptyErrorClassName) {
            this.settings.emptyErrorClassName = "emptyError";
        }
    } else {
        this.settings = new Object();
        this.settings.highlighting = true;
        this.settings.scrolling = true;
        this.settings.errorClassName = "error";
        this.settings.emptyErrorClassName = "emptyError";
    }
    this.highlightedFields = new Array();
    for (var i = 0; ; i++) {
        if (this.highlightedFields[i] == undefined) break;
        this.highlightedFields[i].className = this.highlightedFields[i].previousClassName;
    }
    if (!this.errors) {
        alert("Error block not found.");
    }
}

// ---------------------------------------------------------------------------------------------------------------------

Errors.prototype.exceptionAction = function (settings) {
    //errorId, fields, alternativeMessage, onException, onAfterException
    var errors = this;

    return function (exception) {
        if (settings.onException) {
            settings.onException();
        }
        errors.set(settings.errorId, settings.alternativeMessage ? settings.alternativeMessage : exception.message, settings.fields);
        if (settings.onAfterException) {
            settings.onAfterException();
        }
    };
};

// ---------------------------------------------------------------------------------------------------------------------

//Clears error block and changes class to empty error
Errors.prototype.clear = function () {
    this.errors.innerHTML = "";
    this.errors.className = this.settings.emptyErrorClassName;

    for (var i = 0; ; i++) {
        if (this.highlightedFields[i] == undefined) {
            break;
        }

        this.highlightedFields[i].className = this.highlightedFields[i].previousClassName;
    }
};

// ---------------------------------------------------------------------------------------------------------------------

//Returns true if page has errors
Errors.prototype.hasErrors = function () {
    return this.errors.childNodes.length != 0;
};

// ---------------------------------------------------------------------------------------------------------------------

//Removes only one error by ERROR_ID
Errors.prototype.remove = function (errorId) {
    for (var i = 0; i < this.errors.childNodes.length; i++) {
        var error = this.errors.childNodes[i];
        if (error.id == "error" + errorId) {
            error.parentNode.removeChild(error);
            break;
        }
    }

    if (this.errors.childNodes.length == 0) {
        this.errors.className = this.settings.emptyErrorClassName;
    }
};

// ---------------------------------------------------------------------------------------------------------------------

//Id - id for error
//Message - is the message that will appear on the top of the page
//Fields - fied
Errors.prototype.set = function (errorId, message, fields) {
    //Enabling content on error.
    if (isAnyWindowOpened()) {
        getActiveWindow().enableContentAfterException();
    }

    //Scroll window to the top of the page
    window.scrollTo(0, findPosAbs(this.errors).top - 100);

    this.errors.className = this.settings.errorClassName;
    if (this.settings.highlighting) {
        $(this.errors).effect("highlight", {}, 1000, function() {
        });
    }

    if (!errorId || !message) {
        alert("Can't add error with null field or message!");
        return;
    }

    var error = document.getElementById("error" + errorId);
    if (!error) {
        error = document.createElement("div");
    }

    error.id = "error" + errorId;
    error.innerHTML = message;
    this.errors.appendChild(error);

    if (fields) {
        if ($.isArray(fields)) {
            for (var i in fields) {
                showErrorField(fields[i], this.highlightedFields);
            }
        } else {
            showErrorField(fields, this.highlightedFields);
        }
    }
    function showErrorField(field, highlightedFields) {
        if (field) {
            field.previousClassName = field.className;
            if (field.previousClassName != "txt") {
                field.className = "errorBorder " + field.previousClassName;
            } else {
                field.className = "errorBorder";
            }
            highlightedFields.push(field);
        }
    }
};

// ---------------------------------------------------------------------------------------------------------------------

//This method is for stipes validation's errors if you want to make "red" fields that cause errors
function addValidationErrors() {
    $("#errors > div").each(function () {
        var errorFieldId = $(this).attr("errorFieldId");
        $("#" + errorFieldId).addClass("errorBorder");
    });
}


