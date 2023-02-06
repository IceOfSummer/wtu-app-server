package pers.xds.wtuapp.web.util;

import org.jetbrains.annotations.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author HuPeng
 * @date 2022-10-23 15:20
 */
public class StringUtils {

    private StringUtils() {}

    public static final String TRUE = "1";

    /**
     * 判断字符串"是否为真"
     * @param value 要判断的字符串
     * @return 是否为真
     */
    public static boolean isTrue(String value) {
        return TRUE.equals(value);
    }

    /**
     * 将一行的字符串解析为数字, 字符串以指定的分隔符分隔，<b>仅支持大于0的数</b><p>
     *
     * 如:<pre>
     *  - 输入: `"1,2,3,4,5"`, splitter = `,`
     *  - 输出: [1,2,3,4,5]
     *  <p>
     *  <b>不支持负数的输入!</b>
     * @param line 要解析的字符串
     * @param splitter 分隔符
     * @param maxListLen 数组最大长度，当超过该长度后将直接返回
     * @return 解析后的数字数组, 解析失败返回null(如有其他字符出现)
     */
    @Nullable
    public static List<Integer> parseLineString(String line, char splitter, int maxListLen) {
        LinkedList<Integer> list = new LinkedList<>();
        int size = 0;
        int cur = 0;
        for (int i = 0; i < line.length() && size < maxListLen; i++) {
            char ch = line.charAt(i);
            if (ch == splitter) {
                if (cur == 0) {
                    continue;
                }
                size++;
                list.add(cur);
                cur = 0;
            } else if (ch >= '0' && ch <= '9') {
                cur = cur * 10 + (ch ^ '0');
            } else {
                return null;
            }
        }
        if (cur > 0) {
            list.add(cur);
        }
        return list;
    }

    /**
     * 将一行的字符串解析为数字, 字符串以指定的分隔符分隔，<b>支持负数</b><p>
     * 如:<pre>
     *  - 输入: `"1,2,3,4,5"` 支持任意分隔符，甚至可以出现多个分隔符
     *  - 输出: [1,2,3,4,5]
     *  <p>
     * @param line 要解析的字符串
     * @param maxListLen 数组最大长度，当超过该长度后将直接返回
     * @return 解析后的数字数组
     */
    public static List<Integer> parseLineString(String line, int maxListLen) {
        LinkedList<Integer> result = new LinkedList<>();
        StringIterator stringIterator = new StringIterator(line);
        int size = 0;
        Integer integer;
        while ((integer = stringIterator.nextInteger()) != null && size < maxListLen) {
            result.add(integer);
            size++;
        }
        return result;
    }

    /**
     * 邮箱检查
     */
    private static final Pattern EMAIL_CHECK = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    /**
     * 检查字符串是否为非法邮箱
     * @param str 字符串
     * @return 是否为非法邮箱
     */
    public static boolean isInvalidEmail(String str) {
        if (str.length() > 25) {
            return true;
        }
        return !EMAIL_CHECK.matcher(str).find();
    }

}
