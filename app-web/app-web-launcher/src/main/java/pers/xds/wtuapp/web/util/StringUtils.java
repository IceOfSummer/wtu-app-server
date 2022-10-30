package pers.xds.wtuapp.web.util;

import org.jetbrains.annotations.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author HuPeng
 * @date 2022-10-23 15:20
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * 将一行的字符串解析为数字, 字符串以指定的分隔符分隔，<b>仅支持大于0的数</b><p>
     *
     * 如:<pre>
     *  - 输入: `"1,2,3,4,5"`, splitter = `,`
     *  - 输出: [1,2,3,4,5]
     * @param line 要解析的字符串
     * @param splitter 分隔符
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
                cur = cur * 10 + (ch - '0');
            } else {
                return null;
            }
        }
        if (cur > 0) {
            list.add(cur);
        }
        return list;
    }


}
