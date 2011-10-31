/**
 * Create text editor with settings
 * @param place - element for insert editor
 * @param editorId - id
 * @param value - start text
 * @param root - path to web application root
 * @param showLastSavedData - boolean, restore text from content
 */

function createEditor(settings) {
    // Create div for mce editor
    var editor = document.createElement("div");

    // Create container for mce editor and save, cancel buttons
    var editorContainer = document.createElement("div");
    editorContainer.appendChild(editor);
    settings.place.innerHTML = "";
    settings.place.appendChild(editorContainer);

    editor.id = settings.editorId;

    var onInitFunction = "";
    if (settings.disableEditor) {
        onInitFunction = function (editorId, disable, content) {
            if (!editorId) {
                disable = true;
                editorId = tinyMCE.activeEditor.editorId;
            }
            if (disable) {
                if (!document.getElementById("disabledTextArea")) {
                    tinyMCE.execCommand("mceToggleEditor", false, editorId);
                    settings.place.appendChild(createDisabledTextArea(editorId, content));
                }
            } else {
                if (document.getElementById("disabledTextArea")) {
                    tinyMCE.execCommand("mceToggleEditor", false, editorId);
                    if (content) {
                        setEditorContent(editorId, content);
                    }
                    settings.place.removeChild(document.getElementById("disabledTextArea"));
                }
            }
        };
    }
    var start;

    $(function() {
        $("#" + settings.editorId).tinymce({
            script_url: "/tinymce/tiny_mce.js",
            width: settings.width,
            height: settings.height,
            mode : "exact",
            theme : "advanced",
            elements : settings.editorId,
            setupcontent_callback : function (editor_id, body, doc) {
                var editor = tinyMCE.get(editor_id);
                editor.setContent(settings.value);
                start = true;
                window.parent.setTextEditorId(editor_id);
                try {
                    window.parent.setWindowSettingsUnchanged();
                } catch(ex) {
                }
                tinyMCE.getInstanceById(editor_id).getWin().document.body.style.backgroundColor = '#e4e4e3';//http://jira.web-deva.com/browse/SW-5730

                //if (buttonsContainer.saveButton) buttonsContainer.saveButton.disabled = "";
                //if (buttonsContainer.cancelButton) buttonsContainer.cancelButton.disabled = "";
            },
            paste_use_dialog: false,
            //        paste_auto_cleanup_on_paste : true,
            plugins : "pagelink,example,safari,layer,table,save,advhr,advimage,advlink,emotions,iespell,media,searchreplace,contextmenu,paste,directionality,template,inlinepopups,xhtmlxtras,style,spellchecker",
            extended_valid_elements : "a[class|href|name|id|pageId],iframe[src|width|height|frameborder]",
            // Theme options
            theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,formatselect,fontselect,fontsizeselect",
            theme_advanced_buttons2 : "cut,copy,paste,,pastetext,|,search,|,bullist,numlist,|,code,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,pagelink,image,example,|,inserttime,forecolor,backcolor",
            theme_advanced_buttons3 : "tablecontrols,pasteword,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,removeformat,styleprops,attribs,|,spellchecker",
            spellchecker_languages : "+English=en,Swedish=sv",
            spellchecker_rpc_url : "/googleSpellChecker",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            theme_advanced_statusbar_location : "bottom",
            theme_advanced_resizing_min_height : 20,
            theme_advanced_resizing : true,
            //convert_fonts_to_spans : true,
            //font_size_style_values:"8pt,10pt,12pt,14pt,18pt,24pt,36pt",
            content_css : "../css/customFonts.css",

            theme_advanced_fonts :
                    "Arial=arial,helvetica,sans-serif;"+
                    "Arial Black=arial black,avant garde;"+
                    "Advert MF Italic=Advert MF Italic;"+
                    "Agent Orange=Agent Orange;"+
                    "Ale and Wenches BB Bold=Ale and Wenches BB Bold;"+
                    "Angelic War=Angelic War;"+
                    "Arbuckle Inline NF=Arbuckle Inline NF;"+
                    "Aunt Bertha NF=Aunt Bertha NF;"+
                    "B690 Script Bold=B690 Script Bold;"+
                    "B821 Deco Regular=B821 Deco Regular;"+
                    "Book Antiqua=book antiqua,palatino;"+
                    "Bluff=Bluff;"+
                    "Carmen Caps=Carmen Caps;"+
                    "Carmen CapsInside=Carmen CapsInside;"+
                    "Carmen CapsOutside=Carmen CapsOutside;"+
                    "Champignon Alt Swash=Champignon Alt Swash;"+
                    "Chrome Yellow NF=Chrome Yellow NF;"+
                    "Courier New=courier new,courier;"+
                    "Comic Sans MS=comic sans ms,sans-serif;"+
                    "Creampuff=Creampuff;"+
                    "Dadhand=Dadhand;"+
                    "Eager Naturalist=Eager Naturalist;"+
                    "Encapsulate BRK=Encapsulate BRK;"+
                    "Fleurs de Liane=Fleurs de Liane;"+
                    "Flores=Flores;"+
                    "Folkard=Folkard;"+
                    "Georgia=georgia,palatino;"+
                    "Grover Heavy=Grover Heavy;"+
                    "Hawaii Lover=Hawaii Lover;"+
                    "Helvetica=helvetica;"+
                    "Impact=impact,chicago;"+
                    "Jungle Fever Inline NF=Jungle Fever Inline NF;"+
                    "Koch Quadrat=Koch Quadrat;"+
                    "Lombriz=Lombriz;"+
                    "Medoc=Medoc;"+
                    "My type of font=My type of font;"+
                    "Rough Draft=Rough Draft;"+
                    "Scarlet Ribbons=Scarlet Ribbons;"+
                    "SydneySerial Medium=SydneySerial Medium;"+
                    "Symbol=symbol;"+
                    "Tahoma=tahoma,arial,helvetica,sans-serif;"+
                    "Terminal=terminal,monaco;"+
                    "Times New Roman=times new roman,times;"+
                    "Trebuchet MS=trebuchet ms,geneva;"+
                    "Umberto=Umberto;"+
                    "Verdana=verdana,geneva;"+
                    "Vtks Caps Loco=Vtks Caps Loco;"+
                    "Webdings=webdings;"+
                    "Wingdings=wingdings,zapf dingbats",


            setup: function (editor) {
                editor.onInit.add(function(editor) {
                    editor.focus();
                    var place = settings.place;
                    var loadingMessageDiv = window.parent.document.getElementById("tinyMCELoadingMessage");
                    if (loadingMessageDiv) {
                        loadingMessageDiv.style.display = "none";
                    }
                    place.style.display = "block";

                    $("#" + settings.editorId + "_ifr").css("height", settings.height + "px");
                    try {
                        getActiveWindow().resize();

                        // For some extra fast browsers (like Chrome), we need to redo resizing after some time.
                        // It's done
                        setTimeout(function () {
                            if (isAnyWindowOpened()) {
                                getActiveWindow().resize();
                            }
                        }, 100);

                    } catch(ex) {
                    }
                    //
                    tinymce.dom.Event.add(editor.getWin(), 'resize', function(e) {
                        try {
                            getActiveWindow().resize();
                        } catch(ex) {

                        }
                    });

                });
            }
        });
    });

    if (settings.showLastSavedData) {
        window.contentStoreControl.bind({
            id: settings.editorId,

            onRestore: function (object) {
                if (start && tinyMCE) {
                    tinyMCE.get(object.id).setContent(object.value);
                } else {
                    settings.value = object.value;
                }

                var restoreContextDiv = document.getElementById("editorRestoreContext");
                if (object.date) {
                    var dateString = object.date.format("dddd, mmmm d, yyyy. h:MM tt");
                    if (restoreContextDiv) {
                        restoreContextDiv.innerHTML = "Content restored. Is has been saved on " + dateString;
                    } else {
                        alert("Content restored. Is has been saved on " + dateString);
                    }
                }
            },

            onStore: function (id) {
                return tinyMCE.get(id).getContent();
            }
        });
    }

    function createDisabledTextArea(editorId, content) {
        var textarea = document.createElement("textarea");
        var controlsHeight = 80;
        textarea.style.width = settings.width + "px";
        textarea.style.height = settings.height + controlsHeight + "px";
        textarea.disabled = true;
        textarea.id = "disabledTextArea";
        if (content) {
            textarea.innerHTML = content;
        } else {
            textarea.innerHTML = settings.value;
        }
        return textarea;
    }
}

function closeEditor(id) {
    tinyMCE.execCommand("mceRemoveControl", false, id);
    window.contentStoreControl.reset(id);
}

/**
 * @param editorId
 */
function getEditorContent(editorId) {
    var editor = getTextEditor(editorId);
    var content = editor ? editor.getContent() : "";
    return removeIEComments(content);
}

function isTextEditorContentChanged(editorId) {
    var editor = getTextEditor(editorId);
    return editor && editor.isDirty();
}

function setTextEditorContentChanged(editorId, changed) {
    var editor = getTextEditor(editorId);
    if (editor) {
        editor.isNotDirty = changed ? 0 : 1;
    }
}

/**
 * @param editorId, content
 */
function setEditorContent(editorId, content) {
    var editor = getTextEditor(editorId);
    return editor ? editor.setContent(removeIEComments(content)) : "";
}

function getTextEditor(editorId) {
    try {
        var editor = tinyMCE.get(editorId);
        return (!editor || editor.length == 0) ? null : editor;
    } catch(ex) {
        return null;
    }
}

function removeIEComments(content) {
    if (content) {
        var regexp = /(?:<|&lt;)\s*?!--\s*?\[\s*?if\s*?!supportLineBreakNewLine\s*?\]\s*?--(?:>|&gt;)(.*?|\n*?)(?:<|&lt;)!--\s*?\[\s*?endif\s*?\]\s*?--(?:>|&gt;)/;
        while (content.match(regexp)) {
            content = content.replace(regexp, "$1");
        }
    }
    return content;
}