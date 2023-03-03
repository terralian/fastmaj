package com.github.terralian.fastmaj.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，提供字符串处理的相关方法及防止空指针的包装方法
 * 
 * @author terra.lian 
 */
public abstract class StringUtil {

    /** 空字符串 */
    public static final String EMPTY_STRING = "";
    /** 形如{0}{1}{2}形式的占位符正则匹配 */
    static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile("[{](?<idx>\\d+)}");

    /**
     * 将一个对象转换为String， 若对象为空，则返回空
     * 
     * @param object 对象
     */
    public static String toString(Object object) {
        return object == null ? null : object.toString();
    }

    /**
     * 将一个对象转换为String，若对象为空，则返回空字符串{@link StringUtil#EMPTY_STRING}
     * 
     * @param object 对象
     */
    public static String toStringEmpty(Object object) {
        return object == null ? EMPTY_STRING : object.toString();
    }

    /**
     * 去掉去掉字符串两端的多余的空格
     * 
     * @param value 字符串
     */
    public static String trim(String value) {
        return EmptyUtil.isNotEmpty(value) ? value.trim() : value;
    }

    /**
     * 当一个字符串过长，对其进行省略，若省略会在其后添加"..."作为省略标识
     * 
     * @param string 字符串
     * @param maxLength 最大长度（如200 指字符串下标 0-199），超过该长度则进行省略
     */
    public static String ellipsis(String string, int maxLength) {
        if (EmptyUtil.isEmpty(string) || string.length() <= maxLength) {
            return string;
        }
        return string.substring(0, maxLength - 1) + "...";
    }

    /**
     * 当一个字符串过长，对其进行省略，若省略会在其后添加"..."作为省略标识<br>
     * 由于一般情况下StringBuilder应当都进行了初始化，所以该方法不会对StringBuilder进行空指针判断
     * 
     * @param builder 可变的字符序列
     * @param maxLength 最大长度（如200 指字符串下标 0-199），超过该长度则进行省略
     */
    public static void ellipsis(StringBuilder builder, int maxLength) {
        if (builder.length() > maxLength) {
            builder.delete(maxLength, builder.length() - 1);
            builder.append("...");
        }
    }

    /**
     * 将多个字符串中非null的字符串拼接在一起
     * 
     * @param strings 字符串集合
     * @return 若集合为空，返回null
     */
    public static String cat(Collection<String> strings) {
        if (EmptyUtil.isEmpty(strings))
            return null;
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            if (EmptyUtil.isNotEmpty(string))
                builder.append(string);
        }
        return builder.toString();
    }

    /**
     * 将多个字符串中非null的字符串拼接在一起
     * 
     * @param strings 字符串集合
     * @return 若集合为空，返回null
     */
    public static String cat(String... strings) {
        if (EmptyUtil.isEmpty(strings))
            return null;
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            if (EmptyUtil.isNotEmpty(string))
                builder.append(string);
        }
        return builder.toString();
    }

    /**
     * 将字符集合拼接
     * 
     * @param delimiter 分隔符
     * @param elements 需要拼接的字符
     */
    public static String join(Character delimiter, Character... elements) {
        if (EmptyUtil.isEmpty(elements)) {
            return EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder(elements.length * 2 - 1);
        builder.append(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            builder.append(delimiter).append(elements[i]);
        }
        return builder.toString();
    }

    /**
     * 将字符集合拼接
     * 
     * @param delimiter 分隔符
     * @param values 需要拼接的字符
     */
    public static String join(Character delimiter, int... values) {
        StringBuilder builder = new StringBuilder(values.length * 2 - 1);
        builder.append(values[0]);
        for (int i = 1; i < values.length; i++) {
            builder.append(delimiter).append(values[i]);
        }
        return builder.toString();
    }

    /**
     * 将字符串集合拼接
     * 
     * @param delimiter 分割符
     * @param elements 需要拼接的字符串
     */
    public static String join(CharSequence delimiter, CharSequence... elements) {
        return EmptyUtil.isEmpty(elements) ? EMPTY_STRING : String.join(delimiter, elements);
    }

    /**
     * 将字符类型拼接
     * 
     * @param delimiter 分隔符
     * @param elements 需要拼接的元素，会使用toString()转换为String
     */
    public static String join(CharSequence delimiter, Number... elements) {
        if (EmptyUtil.isEmpty(elements)) {
            return EMPTY_STRING;
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object cs : elements) {
            joiner.add(toString(cs));
        }
        return joiner.toString();
    }

    /**
     * 将一个集合的所有元素拼接为字符串，默认使用逗号作为分隔符，若集合为空，则返回空字符串
     * 
     * @param elements 集合
     */
    public static String join(Collection<?> elements) {
        return join(",", elements);
    }

    /**
     * 将一个集合的所有元素拼接为字符串，使用分隔符进行分割，若集合为空，则返回空字符串
     * 
     * @param delimiter 分隔符
     * @param elements 集合
     */
    public static String join(CharSequence delimiter, Collection<?> elements) {
        if (EmptyUtil.isEmpty(elements))
            return EMPTY_STRING;
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object cs : elements) {
            joiner.add(toString(cs));
        }
        return joiner.toString();
    }

    /**
     * 将一个任意类型的迭代器拼接为字符串，使用分隔符进行分割，若迭代器为空，则返回空字符串
     * 
     * @param delimiter 分隔符
     * @param elements 迭代器
     */
    public static String join(CharSequence delimiter, Iterator<?> elements) {
        if (EmptyUtil.isEmpty(elements))
            return EMPTY_STRING;
        StringJoiner joiner = new StringJoiner(delimiter);
        joiner.add(toString(elements.next()));
        while (elements.hasNext()) {
            joiner.add(toString(elements.next()));
        }
        return joiner.toString();
    }

    /**
     * 
     * @param delimiter
     * @param elements
     */
    public static String join(CharSequence delimiter, int[] elements) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object cs : elements) {
            joiner.add(String.valueOf(cs));
        }
        return joiner.toString();
    }

    /**
     * 字符串中的首字母小写
     * 
     * @param string 字符串
     */
    public static String toLowerCaseFirstOne(String string) {
        if (EmptyUtil.isEmpty(string) || Character.isLowerCase(string.charAt(0)))
            return string;
        else {
            char[] chars = string.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return String.valueOf(chars);
        }
    }

    /**
     * 字符串中的首字母大写
     * 
     * @param string 字符串
     */
    public static String toUpperCaseFirstOne(String string) {
        if (EmptyUtil.isEmpty(string) || Character.isUpperCase(string.charAt(0))) {
            return string;
        } else {
            char[] chars = string.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return String.valueOf(chars);
        }
    }

    /**
     * 将字符串中的下划线转驼峰
     * 
     * @param string 字符串
     */
    public static String underlineToCamelCase(String string) {
        if (EmptyUtil.isEmpty(string)) {
            return string;
        }
        char[] charArray = string.toCharArray();
        StringBuilder builder = new StringBuilder(string.length());
        boolean underlineBefore = false;
        for (int i = 0, l = charArray.length; i < l; i++) {
            if (charArray[i] == '_') {
                underlineBefore = true;
            } else if (underlineBefore) {
                builder.append(Character.toUpperCase(charArray[i]));
                underlineBefore = false;
            } else {
                builder.append(charArray[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 将字符串中的驼峰转下划线
     * 
     * @param string 字符串
     */
    public static String cameCaseToUnderline(String string) {
        if (EmptyUtil.isEmpty(string)) {
            return string;
        }
        char[] charArray = string.toCharArray();
        StringBuilder builder = new StringBuilder(string.length());
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i])) {
                builder.append('_').append(Character.toLowerCase(charArray[i]));
            } else {
                builder.append(charArray[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 判断两个字符串是否相等，当字符串都为null时，返回true
     * 
     * @param a 字符串a
     * @param b 字符串b
     * @return 是否相等
     */
    public static boolean equals(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else if (a != null && b != null) {
            return a.equals(b);
        }
        return false;
    }

    /**
     * 类Logger的字符串格式化，使用形如{0}{1}{2}的占位符，例如输入format("姓名:{0}", "爱因斯坦")，会输出："姓名:爱因斯坦"
     * 
     * <p/>
     * 该实现copy至MybatisPlus的StringUtil#sqlArgsFill方法
     * </p>
     * 
     * @param content 内容
     * @param args 参数
     * @throws ArrayIndexOutOfBoundsException 当数组中参数不足时，该方法会抛出该异常
     */
    public static String format(String content, Object... args) {
        if (EmptyUtil.isNotBlank(content) && EmptyUtil.isNotEmpty(args)) {
            Function<Matcher, CharSequence> handler = (m) -> toString(args[Integer.parseInt(m.group("idx"))]);
            Matcher m = PLACE_HOLDER_PATTERN.matcher(content);
            StringBuilder sb = new StringBuilder();
            int last = 0, len = content.length();
            // 扫描一次字符串
            while (m.find()) {
                sb.append(content, last, m.start()).append(handler.apply(m));
                last = m.end();
            }
            // 如果表达式没有匹配或者匹配未到末尾，该判断保证字符串完整性
            if (last < len) {
                sb.append(content, last, len);
            }
            return sb.toString();
        }
        return content;
    }

    /**
     * 当字符串是否以某些字符串开头
     * <p/>
     * 该方法根据参数多次调用{@link String#startsWith}方法
     * 
     * @param target 目标字符串
     * @param prefixs 前缀集合
     * @return 字符串为null或者未匹配返回false，若参数一个匹配，返回true
     */
    public static boolean startWith(String target, String... prefixs) {
        if (target == null)
            return false;
        for (String prefix : prefixs) {
            if (target.startsWith(prefix))
                return true;
        }
        return false;
    }
}
