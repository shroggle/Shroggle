window.images = {

    select: {

        bind: function (settings) {
            var imagesArea = settings.element;

            settings.element = null;
            $(imagesArea)[0].settings = settings;

            window.images.select.refresh(imagesArea);
        },

        refresh: function (imagesArea) {
            var settings = $(imagesArea)[0].settings;

            var request = {
                lineWidth: settings.lineWidth ? settings.lineWidth : 600,
                siteId: settings.siteId,
                imageItemId: settings.imageItemId ? settings.imageItemId : null,
                keywords: []
            };

            if (settings.keywords) {
                request.keywords = settings.keywords;
            }

            createLoadingArea({
                element: $(imagesArea)[0],
                text: "Loading images...",
                color: "green",
                guaranteeVisibility: true
            });

            var serviceCall = new ServiceCall();
            serviceCall.executeViaDwr("GetImagesService", "execute", request, function (response) {
                $(imagesArea).html(response);

                $(".unselectedConfigureImage", imagesArea).each(function () {
                    this.imagesArea = imagesArea;
                });

                $(".unselectedConfigureImage", imagesArea).click(function () {
                    window.images.select.selectImage(this);
                });

                // Selecting image if imageId was provided in settings.
                $("img[imageId='" + settings.imageId + "']").click();

                $(".uploadedImageDeleteImg", imagesArea).click(function () {
                    window.images.select.remove(imagesArea, $("img[image='image']",
                            window.images.select.getImageBlock(this)));
                });

                $(".uploadedImageEditImg", imagesArea).click(function () {
                    window.images.select.showEdit($("img[image='image']",
                            window.images.select.getImageBlock(this)));
                });

                removeLoadingArea();

                addContentChangedTrigger(imagesArea);

                if (settings.onAfterRefresh) {
                    settings.onAfterRefresh();
                }
            });
        },

        selectImage: function (image) {
            var imagesArea = $(image)[0].imagesArea;

            $("img[image='image']", imagesArea).removeClass("selectedConfigureImage");
            $("img[image='image']", imagesArea).addClass("unselectedConfigureImage");
            $(image)[0].className = "selectedConfigureImage";
            window.images.select.getSettings(imagesArea).onClick({
                id: $(image).attr("imageId"),
                width: $(image).attr("imageWidth"),
                height: $(image).attr("imageHeight"),
                url: $(image).attr("imageUrl"),
                name: $(image).attr("imageName")
            });
        },

        getSettings: function (imagesArea) {
            return $(imagesArea)[0].settings;
        },

        fixImageTitleWidth: function (image) {
            $(".uploadedImageName", window.images.select.getImageBlock(image)).css("width", image.offsetWidth + "px");
            $(".uploadedImageNameEdit > input", window.images.select.getImageBlock(image)).css("width", (image.offsetWidth - 2) + "px");
        },

        removeSelected: function (imagesArea) {
            $(".selectedConfigureImage", imagesArea).each(function () {
                window.images.select.remove(imagesArea, this);
            });
        },

        showEdit: function (image) {
            var imageBlock = $(window.images.select.getImageBlock(image));

            // Hide all image name inputs if any is shown.
            $(".uploadedImageNameEdit").hide();
            $(".uploadedImageName").show();

            // Hide image name and show input with name.
            $(".uploadedImageName", imageBlock).hide();
            $(".uploadedImageNameEdit", imageBlock).show();

            $(".uploadedImageNameEdit > input", imageBlock)[0].focus();

            // Assign event to document so user can close edit by clicking anywhere on document.
            $(window.parent.document).click(function (event) {
                if (!$(event.target).hasClass("uploadedImageNameEdit") &&
                        !$(event.target).hasClass("uploadedImageEditImg") &&
                        !$(event.target).hasClass("uploadedImageNameEditInput")) {
                    window.images.select.saveEdit(image);

                    $(window.parent.document).unbind("click");
                }
            });
        },

        saveEditOnEnter: function (event, input) {
            var keyId = (window.event) ? window.event.keyCode : event.keyCode;
            if (keyId == 13) {
                window.images.select.saveEdit($(input).parents(".imageDiv").find("img[image='image']")[0]);

                $(window.parent.document).unbind("click");
            }
        },

        saveEdit: function (image) {
            var imageBlock = $(window.images.select.getImageBlock(image));

            $(".uploadedImageName", imageBlock).show();
            $(".uploadedImageNameEdit", imageBlock).hide();

            var imageTitleDiv = $(".uploadedImageName", imageBlock);
            var imageTitleInput = $(".uploadedImageNameEdit > input", imageBlock);
            var imageTitleNewName = $(imageTitleInput).val();

            var serviceCall = new ServiceCall();
            serviceCall.addExceptionHandler("com.shroggle.exception.ImageDuplicateNameException", function(exception) {
                var alert = createConfigureWindow({width: 350, height:100, asDialog: true, dialogHeader:"Rename image error"});
                alert.setContent("There is another file with a name your have entered.<br>Name of this file has been modified.");

                $(imageTitleDiv).html(exception.message);
                $(imageTitleInput).val(exception.message);
            });
            serviceCall.executeViaDwr("SaveImageNameService", "execute",
                    $(image).attr("imageId"), imageTitleNewName, function () {
                imageTitleDiv.html(imageTitleNewName);
            });
        },

        getImageBlock: function(elementInsideMediaBlock) {
            return $(elementInsideMediaBlock).parents("td:first");
        },

        remove: function (imagesArea, image) {
            if (confirm("Do you really want to remove this image?")) {
                var settings = window.images.select.getSettings(imagesArea);
                var imageId = $(image).attr("imageId");

                new ServiceCall().executeViaDwr("DeleteImageService",
                        "execute", imageId, function () {
                });

                $(window.images.select.getImageBlock(image)).remove();

                if (settings.onRemove) {
                    settings.onRemove(imageId);
                }

                if ($(image)[0].className == "selectedConfigureImage" && settings.onSelectedRemove) {
                    settings.onSelectedRemove(imageId);
                }
            }
        }

    }

};