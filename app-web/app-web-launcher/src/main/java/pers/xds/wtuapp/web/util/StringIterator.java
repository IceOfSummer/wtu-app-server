package pers.xds.wtuapp.web.util;

/**
 * @author DeSen Xu
 * @date 2023-02-06 13:30
 */
public class StringIterator {


    private final String str;

    private int current;

    public StringIterator(String str) {
        this.str = str;
    }

    public Integer nextInteger() {
        if (current == str.length()) {
            return null;
        }
        int result = 0, f = 1;
        char ch = str.charAt(current++);
        while (ch < '0' || ch > '9') {
            if (ch == '-') {
                f = -1;
            }
            if (current == str.length()) {
                return null;
            }
            ch = str.charAt(current++);
        }
        while (ch >= '0' && ch <= '9') {
            result = result * 10 + ch - '0';
            if (current == str.length()) {
                return result * f;
            }
            ch = str.charAt(current++);
        }
        return result * f;
    }


}
