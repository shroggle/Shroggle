/**
 * $Id: editor_plugin_src.js 201 2007-02-12 15:56:56Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright � 2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
	// Load plugin specific language pack
	tinymce.PluginManager.requireLangPack('example');

	tinymce.create('tinymce.plugins.ExamplePlugin', {
		/**
		 * Initializes the plugin, this will be executed after the plugin has been created.
		 * This call is done before the editor instance has finished it's initialization so use the onInit event
		 * of the editor instance to intercept that event.
		 *
		 * @param {tinymce.Editor} ed Editor instance that the plugin is initialized in.
		 * @param {string} url Absolute URL to where the plugin is located.
		 */
		init : function(ed, url) {
			// Register the command so that it can be invoked by using tinyMCE.activeEditor.execCommand('mceExample');
			ed.addCommand('mceShowImageUpload', function() {
                var uploadImagesWindow = createConfigureWindow({
                    width: 600, height: 600});

                uploadImagesWindow.setContent(
                        "<div>" +
                                "<div id=\"tinymceSelectImages\" style=\"background-color: white; width: 570px; height: 360px; overflow: auto;\"></div>" +
                                "<br><div id=\"tinymceUploadImages\"></div>" +
                                "<br>" +
                                "<div class=\"fr\" style=\"text-align:right\">" +
                                "<input class=\"but_w73_Over\" id=\"tinymceImagesCancel\" type=\"button\" value=\"Cancel\" onmouseout=\"this.className='but_w73_Over';\" onmouseover=\"this.className='but_w73';\">" +
                                "<input class=\"but_w73_Over\" id=\"tinymceImagesInsert\" type=\"button\" value=\"Insert\" onmouseout=\"this.className='but_w73_Over';\" onmouseover=\"this.className='but_w73';\">" +
                                "</div><br><br><br><br></div>");

                window.uploadFilesControl.bind({
                    element: "#tinymceUploadImages",
                    label: "Browse and Upload",
                    siteId: window.parent.siteId,
                    onClose: function () {
                        window.images.select.refresh("#tinymceSelectImages");
                    }
                });

                var selectedImage = null;

                window.images.select.bind({
                    element: "#tinymceSelectImages",
                    siteId: window.parent.siteId,
                    lines: 2,
                    onClick: function (image) {
                        selectedImage = image;
                    }
                });

                $("#tinymceImagesCancel").click(function () {
                    // SW-6568 | Let the parent window destroy its child window
                    getParentWindow().closeConfigureWidgetDiv();
                });

                $("#tinymceImagesInsert").click(function () {
                    if (selectedImage) {
                        var text = "<img src=\"" + selectedImage.url + "\" alt=\"" + selectedImage.name + "\">";
                        ed.execCommand('mceInsertContent', false, text);
                    }
                    // SW-6568 | Let the parent window destroy its child window
                    getParentWindow().closeConfigureWidgetDiv();
                });

//                ed.windowManager.open({
//					file : url + '/dialog.jsp',
//					width : 600 + parseInt(ed.getLang('example.delta_width', 0)),
//					height : 510 + parseInt(ed.getLang('example.delta_height', 0)),
//					inline : 1
//				}, {
//					plugin_url : url, // Plugin absolute URL
//					some_custom_arg : 'custom arg' // Custom argument
//				});
            });

			// Register example button
			ed.addButton('example', {
				title : 'example.desc',
				cmd : 'mceShowImageUpload',
				image : url + '/img/example.gif'
			});

			// Add a node change handler, selects the button in the UI when a image is selected
			ed.onNodeChange.add(function(ed, cm, n) {
				cm.setActive('example', n.nodeName == 'IMG');
			});
		},

		/**
		 * Creates control instances based in the incomming name. This method is normally not
		 * needed since the addButton method of the tinymce.Editor class is a more easy way of adding buttons
		 * but you sometimes need to create more complex controls like listboxes, split buttons etc then this
		 * method can be used to create those.
		 *
		 * @param {String} n Name of the control to create.
		 * @param {tinymce.ControlManager} cm Control manager to use inorder to create new control.
		 * @return {tinymce.ui.Control} New control instance or null if no control was created.
		 */
		createControl : function(n, cm) {
			return null;
		},

		/**
		 * Returns information about the plugin as a name/value array.
		 * The current keys are longname, author, authorurl, infourl and version.
		 *
		 * @return {Object} Name/value array containing information about the plugin.
		 */
		getInfo : function() {
			return {
				longname : 'Image Upload plugin',
				author : 'Stasuk Artem',
				authorurl : '',
				infourl : '',
				version : "1.0"
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('example', tinymce.plugins.ExamplePlugin);
})();