//Creates text editor for registration visual item and gets text for header if this item have form.
function createConfigureItemDescriptionTextEditor(header) {
    createEditor({
        width: 740,
        height: 100,
        showLastSavedData: false,
        place: document.getElementById("configureItemDescriptionTextEditor"),
        disableEditor : false,
        editorId: "configureItemDescriptionEditorId",
        value: (header != null ? header : ""),
        root: "../"});
}

function showConfigureItemDescription(settings) {
    var configureItemDescriptionWindow = createConfigureWindow({width:800, height:380});
    configureItemDescriptionWindow.setContent(createItemDescriptionInnerHTML(settings));
    createConfigureItemDescriptionTextEditor(($("#" + settings.id + "Header")[0].innerHTML));
    addEventHandlers();
    if ($("#show" + settings.id + "Header")[0] &&
            $("#show" + settings.id + "Header")[0].value == "true") {
        window.parent.document.getElementById("showHeader").checked = true;
    }

    function addEventHandlers() {
        var saveButton = window.parent.document.getElementById("saveButtonItemDescription");
        saveButton.id = "windowSave";
        saveButton.onclick = function() {
            saveConfigureItemDescription(settings.id);
        };
        saveButton.onmouseout = function () {
            document.getElementById("saveButtonItemDescription").className = 'but_w73';
        };
        saveButton.onmouseover = function () {
            document.getElementById("saveButtonItemDescription").className = 'but_w73_Over';
        };

        var cancelButton = window.parent.document.getElementById("cancelButtonItemDescription");
        cancelButton.id = "windowCancel";
        cancelButton.onclick = function() {
            closeConfigureWidgetDivWithConfirm();
        };
        cancelButton.onmouseout = function () {
            document.getElementById("cancelButtonItemDescription").className = 'but_w73';
        };
        cancelButton.onmouseover = function () {
            document.getElementById("cancelButtonItemDescription").className = 'but_w73_Over';
        };
    }

    function createItemDescriptionInnerHTML(settings) {
        checkSettings();
        var windowOneColumn = window.parent.document.createElement("div");
        windowOneColumn.className = "windowOneColumn";

        var header = window.parent.document.createElement("h1");
        var headerText = window.parent.document.createTextNode(settings.header);
        header.appendChild(headerText);

        //----------------------------------------------------text editor---------------------------------------------------
        var configureItemDescriptionTextEditor = window.parent.document.createElement("div");
        configureItemDescriptionTextEditor.id = "configureItemDescriptionTextEditor";
        configureItemDescriptionTextEditor.style.marginTop = "20px";
        configureItemDescriptionTextEditor.style.display = "none";
        //----------------------------------------------------text editor---------------------------------------------------


        //--------------------------------------------------Loading message-------------------------------------------------
        var tinyMCELoadingMessage = window.parent.document.createElement("div");
        tinyMCELoadingMessage.id = "tinyMCELoadingMessage";
        tinyMCELoadingMessage.style.width = "740px";
        tinyMCELoadingMessage.style.height = "203px";
        tinyMCELoadingMessage.style.marginBottom = "15px";


        $(tinyMCELoadingMessage).html(
                "<table style=\"width: 100%; height: 100%;\">" +
                        "<tbody><tr>" +
                        "<td style=\"width: 100%; height: 100%; text-align: center; vertical-align: middle;\" align=\"center\">" +
                        "<img alt=\"Loading text editor...\" src=\"/images/ajax-loader.gif\">" +
                        "<br>" +
                        "Loading text editor..." +
                        "</td>" +
                        "</tr>" +
                        "</tbody></table>");
        //--------------------------------------------------Loading message-------------------------------------------------

        //----------------------------------------------Display header checkbox---------------------------------------------
        var checkboxDiv = window.parent.document.createElement("div");
        checkboxDiv.style.display = settings.showCheckbox ? "block" : "none";
        checkboxDiv.style.marginTop = "5px";

        var checkbox = window.parent.document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.onchange = setWindowSettingsChanged;
        checkbox.id = "showHeader";

        var label = window.parent.document.createElement("label");
        label.htmlFor = checkbox.id;
        var displayHeader = document.getElementById("itemDescriptionDisplayHeader").value;
        var labelText = window.parent.document.createTextNode(displayHeader);
        label.appendChild(labelText);


        checkboxDiv.appendChild(checkbox);
        checkboxDiv.appendChild(label);
        //----------------------------------------------Display header checkbox---------------------------------------------

        //----------------------------------------------------Buttons box---------------------------------------------------
        var buttonsDiv = window.parent.document.createElement("div");
        buttonsDiv.className = "buttons_box";

        var saveButton = window.parent.document.createElement("input");
        saveButton.type = "button";
        saveButton.value = "Save";
        saveButton.id = "saveButtonItemDescription";
        saveButton.className = "but_w73";
        saveButton.style.marginRight = "4px";

        var cancelButton = window.parent.document.createElement("input");
        cancelButton.type = "button";
        cancelButton.value = "Cancel";
        cancelButton.id = "cancelButtonItemDescription";
        cancelButton.className = "but_w73";


        buttonsDiv.appendChild(saveButton);
        buttonsDiv.appendChild(cancelButton);
        //----------------------------------------------------Buttons box---------------------------------------------------

        windowOneColumn.appendChild(header);
        windowOneColumn.appendChild(configureItemDescriptionTextEditor);
        windowOneColumn.appendChild(tinyMCELoadingMessage);
        windowOneColumn.appendChild(checkboxDiv);
        windowOneColumn.appendChild(buttonsDiv);

        var mainDiv = window.parent.document.createElement("div");
        mainDiv.appendChild(windowOneColumn);

        return mainDiv.innerHTML;

        function checkSettings() {
            if (!settings) {
                settings = new Object();
            }
            var defaultHeader;
            if (settings.headerId) {
                defaultHeader = document.getElementById(settings.headerId).value;
            } else {
                defaultHeader = document.getElementById("itemDescriptionDefaultHeader").value;
            }
            settings.id = settings.id ? settings.id : settings;
            settings.header = defaultHeader;
            settings.showCheckbox = settings.showCheckbox == false ? false : true;
            settings.id = settings.id ? settings.id : "";
        }
    }
}


function saveConfigureItemDescription(id) {
    $("#" + id + "Header")[0].innerHTML = getEditorContent("configureItemDescriptionEditorId");
    $("#show" + id + "Header").val($("#showHeader")[0].checked);
    closeConfigureWidgetDiv();
}