package pers.xds.wtuapp.web.service.util;

import pers.xds.wtuapp.web.service.exception.BadArgumentException;

/**
 * @author DeSen Xu
 * @date 2023-02-06 12:11
 */
public class ImageType {

    /**
     * image/png
     */
    public static final String IMAGE_PNG = "png";

    /**
     * image/jpeg
     */
    public static final String IMAGE_JPEG = "jpeg";

    /**
     * image/gif
     */
    public static final String IMAGE_GIF =  "gif";

    /**
     * image/webp
     */
    public static final String IMAGE_WEBP = "webp";

    public static final int IMAGE_PNG_ID = 0;

    public static final int IMAGE_JPEG_ID = 1;

    public static final int IMAGE_GIF_ID = 2;

    public static final int IMAGE_WEBP_ID = 3;


    private ImageType() {}

    /**
     * 根据typeId获取文件类型
     * @param id 类型id
     * @return 文件类型
     * @throws BadArgumentException 未知的类型id
     */
    public static String getTypeNameFromId(int id) {
        switch (id) {
            case IMAGE_PNG_ID:
                return IMAGE_PNG;
            case IMAGE_JPEG_ID:
                return IMAGE_JPEG;
            case IMAGE_GIF_ID:
                return IMAGE_GIF;
            case IMAGE_WEBP_ID:
                return IMAGE_WEBP;
            default:
                throw new BadArgumentException();
        }
    }
}
