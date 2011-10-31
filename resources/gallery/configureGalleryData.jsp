<%@ page import="com.shroggle.entity.GalleryOrientation" %>
<jsp:useBean id="galleryService" scope="request" type="com.shroggle.presentation.gallery.ConfigureGalleryService"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<textarea onfocus="trimTextArea(this);" rows="5" cols="5" style="display: none;" id="configureGalleryData">

    window.configureGalleryInternational = new Object();
    window.configureGalleryInternational.eCommerce = {
        selectShoppingCart: "Shopping cart should be selected",
        selectFullPrice: "Full price field should be selected"
    };

    // For information in default form look in FormsLogic.getDefault()
    window.configureGalleryDefaultForm = {
        items: [
            <c:forEach items="${galleryService.gallery.defaultFormItems}" var="item" varStatus="index">
                <c:if test="${index.index > 0}">,</c:if>
                {id: ${index.index}, type: "${item.formItemName}", name: "${item.itemName}", formItemType: "${item.formItemName.type}"}
            </c:forEach>
        ]
    };
    <% final String defaultSettings = "rows: 1,\n" +
                                       "columns: 9,\n" +
                                       "thumbnailWidth: 60,\n" +
                                       "thumbnailHeight: 60,\n" +
                                       "cellWidth: 70,\n" +
                                       "cellHeight: 67,\n" +
                                       "cellVerticalMargin: 7,\n" +
                                       "cellHorizontalMargin: 10, \n"; %>
    <% final String defaultSort = "firstSort: \"DATE_ADDED\",\n" +
                                   "secondSort: \"DATE_ADDED\",\n" +
                                   "firstSortType: \"DESCENDING\",\n" +
                                   "secondSortType: \"ASCENDING\","; %>
    window.configureGalleryDefault= {
        <%= GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW %>: {

            test1: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationAboveDataBelowNoDescriptionThumbnail.gif",
                image: "/images/gallery/navigationAboveDataBelowNoDescription.png",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", name: "", column: "COLUMN_1", align: "CENTER", width: 800, height: 480}
                ]
            },

            test2: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationAboveDataBelowDescriptionLeftThumbnail.gif",
                image: "/images/gallery/navigationAboveDataBelowDescriptionLeft.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"},
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "RIGHT", width: 550, height: 550}
                ]
            },

            test3: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationAboveDataBelowDescriptionRightThumbnail.gif",
                image: "/images/gallery/navigationAboveDataBelowDescriptionRight.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "NAME", name: "Name:", column: "COLUMN_2", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_2", align: "LEFT"},
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "LEFT", width: 550, height: 550}
                ]
            },

            test4: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/navigationAboveDataBelowDescriptionBelowThumbnail.gif",
                image: "/images/gallery/navigationAboveDataBelowDescriptionBelow.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 800, height: 500},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test5: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/bigNavigationAboveDataBelowDescriptionRightThumbnail.gif",
                image: "/images/gallery/bigNavigationAboveDataBelowDescriptionRight.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "LEFT", width: 335, height: 440},
                    {type: "NAME", name: "Name:", column: "COLUMN_2", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_2", align: "LEFT"}
                ]
            },

            test6: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/bigNavigationAboveDataBelowDescriptionLeftThumbnail.gif",
                image: "/images/gallery/bigNavigationAboveDataBelowDescriptionLeft.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "LEFT", width: 335, height: 440 },
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            }
        },

        <%= GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW %>: {
            test1: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationBelowDataAboveNoDescriptionThumbnail.gif",
                image: "/images/gallery/navigationBelowDataAboveNoDescription.jpg",
                
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 800, height: 480}
                ]
            },

            test2: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationBelowDataAboveDescriptionLeftThumbnail.gif",
                image: "/images/gallery/navigationBelowDataAboveDescriptionLeft.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"},
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "RIGHT", width: 550, height: 500}
                ]
            },

            test3: {
                <%= defaultSettings + defaultSort %>
                thumbnail: "/images/gallery/navigationBelowDataAboveDescriptionRightThumbnail.gif",
                image: "/images/gallery/navigationBelowDataAboveDescriptionRight.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "NAME", name: "Name:", column: "COLUMN_2", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_2", align: "LEFT"},
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "LEFT", width: 550, height: 500}
                ]
            },

            test4: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/navigationBelowDataAboveDescriptionAboveThumbnail.gif",
                image: "/images/gallery/navigationBelowDataAboveDescriptionAbove.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 800, height: 500},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test5: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/bigNavigationBelowDataAboveDescriptionLeftThumbnail.gif",
                image: "/images/gallery/bigNavigationBelowDataAboveDescriptionLeft.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "RIGHT", width: 335, height: 440},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test6: {
                <%= defaultSettings.replace("rows: 1", "rows: 2") + defaultSort %>
                thumbnail: "/images/gallery/bigNavigationBelowDataAboveDescriptionRightThumbnail.gif",
                image: "/images/gallery/bigNavigationBelowDataAboveDescriptionRight.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "LEFT", width: 335, height: 440},
                    {type: "NAME", name: "Name:", column: "COLUMN_2", align: "LEFT"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_2", align: "LEFT"}
                ]
            }
        },

        <%= GalleryOrientation.NAVIGATION_LEFT_DATA_RIGHT %>: {
            test1: {
                rows: 7,
                columns: 3,
                thumbnailWidth: 60,
                thumbnailHeight: 60,
                cellWidth: 67,
                cellHeight: 67,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationLeftDataRightNoDescriptionThumbnail.gif",
                image: "/images/gallery/navigationLeftDataRightNoDescription.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 530, height: 510}
                ]
            },

            test2: {
                rows: 7,
                columns: 2,
                thumbnailWidth: 50,
                thumbnailHeight: 50,
                cellWidth: 57,
                cellHeight: 57,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationLeftDataRightDescriptionLeftThumbnail.gif",
                image: "/images/gallery/navigationLeftDataRightDescriptionLeft.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 450, height: 430},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },
    
            test3: {
                rows: 8,
                columns: 1,
                thumbnailWidth: 65,
                thumbnailHeight: 50,
                cellWidth: 74,
                cellHeight: 59,
                cellVerticalMargin: 9,
                cellHorizontalMargin: 9,
                <%= defaultSort %>
                thumbnail: "/images/gallery/smallNavigationLeftDataRightNoDescriptionThumbnail.gif",
                image: "/images/gallery/smallNavigationLeftDataRightNoDescription.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 720, height: 540}
                ]
            },

            test4: {
                rows: 7,
                columns: 3,
                thumbnailWidth: 70,
                thumbnailHeight: 70,
                cellWidth: 77,
                cellHeight: 77,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationLeftDataRightDescriptionBelowThumbnail.gif",
                image: "/images/gallery/navigationLeftDataRightDescriptionBelow.gif",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 450, height: 450},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            }
        },

        <%= GalleryOrientation.DATA_LEFT_NAVIGATION_RIGHT %>: {
            test1: {
                rows: 7,
                columns: 3,
                thumbnailWidth: 69,
                thumbnailHeight: 65,
                cellWidth: 76,
                cellHeight: 72,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationRightDataLeftNoDescriptionThumbnail.gif",
                image: "/images/gallery/navigationRightDataLeftNoDescription.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 530, height: 510}
                ]
            },    

            test2: {
                rows: 7,
                columns: 2,
                thumbnailWidth: 57,
                thumbnailHeight: 57,
                cellWidth: 64,
                cellHeight: 64,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationRightDataLeftDescriptionLeftThumbnail.gif",
                image: "/images/gallery/navigationRightDataLeftDescriptionRight.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 450, height: 450},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },
    
            test3: {
                rows: 8,
                columns: 1,
                thumbnailWidth: 65,
                thumbnailHeight: 50,
                cellWidth: 74,
                cellHeight: 59,
                cellVerticalMargin: 9,
                cellHorizontalMargin: 9,
                <%= defaultSort %>
                thumbnail: "/images/gallery/smallNavigationRightDataLeftNoDescriptionThumbnail.gif",
                image: "/images/gallery/smallNavigationRightDataLeftNoDescription.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 720, height: 540}
                ]
            },

            test4: {
                rows: 7,
                columns: 3,
                thumbnailWidth: 70,
                thumbnailHeight: 70,
                cellWidth: 77,
                cellHeight: 77,
                cellVerticalMargin: 7,
                cellHorizontalMargin: 7,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigationRightDataLeftDescriptionBelowThumbnail.gif",
                image: "/images/gallery/navigationLeftDataRightDescriptionBelow.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 450, height: 450},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            }
        },

        <%= GalleryOrientation.DATA_ONLY %>: {
            test1: {
                <%= defaultSort %>
                thumbnail: "/images/gallery/dataLeftDescriptionRightThumbnail.gif",
                image: "/images/gallery/dataLeftDescriptionRight.jpg",
                labels: [],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 500, height: 500},
                    {type: "NAME", name: "Name:", column: "COLUMN_2", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_2", align: "LEFT"}
                ]
            },

            test2: {
                <%= defaultSort %>
                thumbnail: "/images/gallery/dataRightDescriptionLeftThumbnail.gif",
                image: "/images/gallery/dataRightDescriptionLeft.jpg",
                labels: [],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 500, height: 500},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test3: {
                <%= defaultSort %>
                thumbnail: "/images/gallery/dataAboveDescriptionBelowThumbnail.gif",
                image: "/images/gallery/dataAboveDescriptionBelow.jpg",
                labels: [],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 700, height: 400},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test4: {
                <%= defaultSort %>
                thumbnail: "/images/gallery/dataBelowDescriptionAboveThumbnail.gif",
                image: "/images/gallery/dataBelowDescriptionAbove.jpg",
                labels: [],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 700, height: 400},
                    {type: "NAME", name: "Name:", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", name: "Description:", column: "COLUMN_1", align: "LEFT"}
                ]
            },

            test5: {
                <%= defaultSort %>
                thumbnail: "/images/gallery/dataThumbnail.gif",
                image: "/images/gallery/data.png",
                labels: [],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_1", align: "CENTER", width: 700, height: 500}
                ]
            }
        },

        <%= GalleryOrientation.NAVIGATION_ONLY %>: {
            test1: {
                rows: 4,
                columns: 6,
                thumbnailWidth: 100,
                thumbnailHeight: 100,
                cellWidth: 124,
                cellHeight: 124,
                cellVerticalMargin: 12,
                cellHorizontalMargin: 12,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigation4x6Thumbnail.gif",
                image: "/images/gallery/navigation4x6.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 600, height: 600},
                    {type: "NAME", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", column: "COLUMN_1", align: "CENTER"}
                ]
            },

            test2: {
                rows: 3,
                columns: 4,
                thumbnailWidth: 150,
                thumbnailHeight: 150,
                cellWidth: 160,
                cellHeight: 160,
                cellVerticalMargin: 10,
                cellHorizontalMargin: 10,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigation4x3Thumbnail.gif",
                image: "/images/gallery/navigation4x3.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 600, height: 600},
                    {type: "NAME", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", column: "COLUMN_1", align: "CENTER"}
                ]
            },

            test3: {
                rows: 3,
                columns: 3,
                thumbnailWidth: 210,
                thumbnailHeight: 160,
                cellWidth: 220,
                cellHeight: 170,
                cellVerticalMargin: 10,
                cellHorizontalMargin: 10,
                <%= defaultSort %>
                thumbnail: "/images/gallery/navigation3x3Thumbnail.gif",
                image: "/images/gallery/navigation3x3.jpg",
                labels: [
                    {type: "IMAGE_FILE_UPLOAD", align: "CENTER", column: 0}
                ],
                items: [
                    {type: "IMAGE_FILE_UPLOAD", column: "COLUMN_2", align: "CENTER", width: 600, height: 600},
                    {type: "NAME", column: "COLUMN_1", align: "CENTER"},
                    {type: "DESCRIPTION", column: "COLUMN_1", align: "CENTER"}
                ]
            }
        }
    };

</textarea>