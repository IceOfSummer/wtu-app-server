package pers.xds.wtuapp.web.service.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 一个工具类，用于结合两个查询集合
 * <p>
 * 应该确保泛型`T`的结果集是最先被查询出来的，泛型`U`的结果集是依靠`T`的结果集查询出来的。
 * <p>
 * 提供的结果集的顺序不能随意更改，例如结果集<code>T</code>拥有属性<code>id</code>, 那么结果集<code>U</code>应该严格按照 <code>T</code>
 * 中<code>id</code>的顺序来查询
 * @author DeSen Xu
 * @date 2022-11-03 16:45
 */
public class DataBeanCombiner<T, U, R> {

    /**
     * 结合函数，将`T`,`U`合并并返回`R`
     * <p>
     * <b>请注意，`U`的值可能为null。</b>
     * <p>
     * 如果该函数返回null，则<b>不会</b>被加到最终的结果集中。
     * <p>
     * 调用该函数时保证{@link DataBeanCombiner#compareFunction}的返回值一定为true
     */
    private final BiFunction<T, U, R> combineFunction;

    /**
     * 比较函数，用于标记两个实体类是能否结合到一起
     * <p>
     * 若要比较包装类，建议直接拆箱后进行判断！防止出现取出的数据为null的情况
     */
    private final BiFunction<T, U, Boolean> compareFunction;

    public DataBeanCombiner(BiFunction<T, U, R> combineFunction, BiFunction<T, U, Boolean> compareFunction) {
        this.combineFunction = combineFunction;
        this.compareFunction = compareFunction;
    }



    /**
     * 结合两个结果集，<b>请确保list1的长度大于等于list2，否则可能会抛出数组越界异常</b>
     * @param list1 结果集1
     * @param list2 结果集2
     * @return 合并后的结果集，长度一定大于等于<code>list1.size()</code>
     */
    public List<R> combine(List<T> list1, List<U> list2) {
        return combine(list1, list2, null, null);
    }


    /**
     * 结合两个结果集，<b>请确保list1的长度大于等于list2，否则可能会抛出数组越界异常</b>
     * <p>
     * <code>T</code>和<code>U</code>合并后的对象可能需要添加一些额外信息，可以传入<code>attach</code>和<code>afterCombine</code>
     * 参数来进行额外信息的添加，<code>list1</code>的索引与<code>attach</code>的索引一一对应，所以请确保attach的长度与list1相等
     * @param list1 结果集1
     * @param list2 结果集2
     * @param attach 附加内容，请保证长度和list1相等
     * @param afterCombine 当T和U合并完成后进行一些额外的操作
     * @return 合并后的结果集，长度一定大于等于<code>list1.size()</code>
     * @param <A> 任意其它结果集
     */
    public <A> List<R> combine(List<T> list1, List<U> list2, List<A> attach, BiFunction<A, R, R> afterCombine) {
        Iterator<T> it1 = list1.iterator();
        Iterator<U> it2 = list2.iterator();
        Iterator<A> attachIt = null;
        if (attach != null) {
            if (list1.size() != attach.size()) {
                throw new IllegalArgumentException("attach.size() must equals to list1.size()");
            }
            attachIt = attach.iterator();
        }
        ArrayList<R> result = new ArrayList<>(list1.size());

        U last = null;
        if (it2.hasNext()) {
            last = it2.next();
        }
        // 默认list1.size() >= list2.size() 来处理
        while (it1.hasNext()) {
            T next = it1.next();
            R apply;
            if (last != null && compareFunction.apply(next, last)) {
                // 可以合并
                apply = combineFunction.apply(next, last);
                if (it2.hasNext()) {
                    last = it2.next();
                } else {
                    last = null;
                }
            } else {
                apply = combineFunction.apply(next, null);
            }
            if (apply != null) {
                if (afterCombine != null && attachIt != null) {
                    apply = afterCombine.apply(attachIt.next(), apply);
                }
                result.add(apply);
            }
        }
        return result;
    }

}
