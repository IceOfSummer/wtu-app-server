package pers.xds.wtuapp.im.message.common;

/**
 * @author DeSen Xu
 * @date 2022-10-30 23:57
 */
public enum ResponseCode {
    /**
     * 请求成功
     */
    SUCCESS(0),
    /**
     * 服务器错误
     */
    SERVER_ERROR(1),
    /**
     * 请求参数有误
     */
    BAD_REQUEST(2);

    private final byte[] byteCode;

    private final int code;

    ResponseCode(int code) {
        this.code = code;
        this.byteCode = new byte[4];
        // 大端写入
        byteCode[3] = (byte) (code & 0xff);
        byteCode[2] = (byte) (code >> 8 & 0xff);
        byteCode[1] = (byte) (code >> 16 & 0xff);
        byteCode[0] = (byte) (code >> 24 & 0xff);
    }

    public int getCode() {
        return code;
    }

    public byte[] getByteCode() {
        return byteCode;
    }
}
