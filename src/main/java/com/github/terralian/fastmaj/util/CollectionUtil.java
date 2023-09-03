package com.github.terralian.fastmaj.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author terra.lian
 */
public abstract class CollectionUtil {

    // ArrayList

    /**
     * 构建一个空ArrayList
     */
    public static <E> List<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 构建一个包含初始容量的ArrayList
     *
     * @param <E> 元素类型
     * @param initialCapacity 初始容量
     */
    public static <E> List<E> newArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 将元素转换为ArrayList，数组大小为默认集合大小（10）
     *
     * @param <E> 元素类型
     * @param element 数组
     */
    public static <E> List<E> newArrayList(E element) {
        List<E> list = new ArrayList<>();
        list.add(element);
        return list;
    }

    /**
     * 将数组转换为ArrayList，转换后的集合内部容量等于数组大小。<br>
     *
     * @param <E> 元素类型
     * @param elements 数组
     */
    @SafeVarargs
    public static <E> List<E> newArrayList(E... elements) {
        List<E> list = newArrayList(elements.length);
        list.addAll(Arrays.asList(elements));
        return list;
    }

    /**
     * 将集合转换为ArrayList
     *
     * @param <E> 元素类型
     * @param elements 集合
     */
    public static <E> List<E> newArrayList(Collection<E> elements) {
        return new ArrayList<>(elements);
    }

    /**
     * 将数组转换为ArrayList，并过滤其中的空元素，只保留非空元素<br>
     * 考虑到增加元素的可能性，集合的内部容量会等于数组的大小，当然集合的size为实际元素的大小。<br>
     *
     * @param elements 数组
     */
    @SafeVarargs
    public static <E> List<E> newNonNullArrayList(E... elements) {
        List<E> list = newArrayList(elements.length);
        for (E element : elements)
            if (element != null)
                list.add(element);
        return list;
    }

    // HashSet

    /**
     * 构建一个空的HashSet
     */
    public static <E> Set<E> newHashSet() {
        return new HashSet<>();
    }

    /**
     * 构建一个带有初始容量的HashSet
     *
     * @param initialCapacity 初始容量
     */
    public static <E> Set<E> newHashSet(int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * 将数组转化为HashSet,其中集合的内部容量等于数组的大小，当然其真实大小不一定
     *
     * @param elements 数组
     */
    @SafeVarargs
    public static <E> Set<E> newHashSet(E... elements) {
        Set<E> set = newHashSet(elements.length);
        set.addAll(Arrays.asList(elements));
        return set;
    }

    /**
     * 将集合转换为SET
     *
     * @param <E> 元素类型
     * @param elements 数组类型
     */
    public static <E> Set<E> newHashSet(Collection<E> elements) {
        return new HashSet<>(elements);
    }

    /**
     * 将数组转化为HashSet,过滤其中的空元素,保留非空元素 ,其中集合的内部容量等于数组的大小，当然其真实大小不一定
     *
     * @param elements 数组
     */
    @SafeVarargs
    public static <E> Set<E> newNonNullHashSet(E... elements) {
        Set<E> set = newHashSet(elements.length);
        for (E element : elements)
            if (element != null)
                set.add(element);
        return set;
    }


    // HashMap

    /**
     * 获得集合的第一个元素，若集合为空，则返回NULL
     *
     * @param <E> 元素类型
     * @param list 集合
     */
    public static <E> E getFirstElement(List<E> list) {
        return EmptyUtil.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 获得集合的第一个元素，若集合为空，则返回NULL
     *
     * @param <E> 元素类型
     * @param set 集合
     */
    public static <E> E getFirstElement(Set<E> set) {
        return EmptyUtil.isEmpty(set) ? null : set.iterator().next();
    }

    /**
     * 获取集合符合条件的第一个元素，若集合为空或者没有符合的元素，则返回NULL
     *
     * @param <E> 元素类型
     * @param collection 集合
     * @param predicate 判断条件
     */
    public static <E> E getFirstElement(Collection<E> collection, Predicate<E> predicate) {
        if (EmptyUtil.isNotEmpty(collection))
            for (E e : collection)
                if (predicate.test(e))
                    return e;
        return null;
    }

    /**
     * 取集合中符合条件的一条。使用多个条件对集合进行判断，先用第一个条件遍历集合，再切换下一个，直到存在一个元素满足某个条件
     * <p/>
     * 如若优先顺序为 负数 > 2 > 大于3的数，则使用该方法如下：<br>
     * firstElement([1, 2, 3], k -> k < 0, k -> k ==2 , k -> k > 3) 取到的是2<br>
     * firstElement([-1, 5, 7], k -> k < 0, k -> k ==2 , k -> k > 3) 取到的是-1<br>
     *
     * @param <E> 元素类型
     * @param collection 集合
     * @param predicates 判断条件集合
     */
    @SafeVarargs
    public static <E> E getFirstElement(Collection<? extends E> collection, Predicate<E>... predicates) {
        if (EmptyUtil.isNotEmpty(collection))
            for (Predicate<E> predicate : predicates)
                for (E e : collection)
                    if (predicate.test(e))
                        return e;
        return null;
    }

    /**
     * 获取最后一个元素
     *
     * @param <E> 集合类型
     * @param list 集合
     */
    public static <E> E getLastElement(List<E> list) {
        return list == null || list.isEmpty() ? null : list.get(list.size() - 1);
    }

    /**
     * 获取一个集合的大小，若集合为NULL，返回0
     *
     * @param collection 集合
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * [J8代码简化] 循环
     *
     * @param list 集合
     * @param consumer (元素) => {}
     */
    public static <E> void forEach(E[] list, Consumer<E> consumer) {
        if (EmptyUtil.isNotEmpty(list))
            for (E e : list)
                consumer.accept(e);
    }

    /**
     * [J8代码简化] 带下标的ForEach
     *
     * @param list 集合
     * @param biConsumer (元素, 元素下标) => {}
     */
    public static <E> void forEach(E[] list, BiConsumer<E, Integer> biConsumer) {
        if (EmptyUtil.isNotEmpty(list))
            for (int i = 0; i < list.length; i++)
                biConsumer.accept(list[i], i);
    }

    /**
     * [J8代码简化] 循环
     *
     * @param list 集合
     * @param consumer (元素) => {}
     */
    public static <E> void forEach(Collection<E> list, Consumer<E> consumer) {
        if (EmptyUtil.isNotEmpty(list))
            for (E e : list)
                consumer.accept(e);
    }

    /**
     * [J8代码简化] 带下标的ForEach
     *
     * @param list 集合
     * @param biConsumer (元素, 元素下标) => {}
     */
    public static <E> void forEach(List<E> list, BiConsumer<E, Integer> biConsumer) {
        if (EmptyUtil.isNotEmpty(list))
            for (int i = 0; i < list.size(); i++)
                biConsumer.accept(list.get(i), i);
    }

    /**
     * [J8代码简化] 对一个集合进行分组，支持空键
     *
     * @param <K> 返回Map的键
     * @param <V> 返回Map的集合元素
     * @param collection 集合
     * @param classifier 获取Map分组用的KEY
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> collection,
            Function<? super V, ? extends K> classifier) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().collect(Collectors.toMap(classifier, CollectionUtil::newArrayList, (List<V> olds, List<V> news) -> {
                olds.addAll(news);
                return olds;
            }));
        }
        return new HashMap<>();
    }

    /**
     * [J8代码简化] 将一个集合转换为Map，若KEY重复，则使用最新的对象作为其值
     *
     * @param <K> 返回Map的键
     * @param <V> 返回Map的集合元素
     * @param collection 集合
     * @param keyMapper 获取Map分组用的KEY
     */
    public static <K, V> Map<K, V> toMap(Collection<? extends V> collection,
            Function<? super V, ? extends K> keyMapper) {
        return toMap(collection, keyMapper, v -> v);
    }

    /**
     * 将一个集合转换为Map，若KEY重复，则使用最新的对象作为其值（解决lambda的toMap不支持键重复的问题）
     *
     * @param <K> 返回Map的键
     * @param <V> 返回Map的集合元素
     * @param <E> 集合元素
     * @param collection 集合
     * @param keyMapper 获取Map分组用的KEY
     * @param valueMapper 值设置方法
     */
    public static <E, K, V> Map<K, V> toMap(Collection<? extends E> collection,
            Function<? super E, ? extends K> keyMapper,
            Function<? super E, ? extends V> valueMapper) {
        Map<K, V> map = new HashMap<>();
        if (EmptyUtil.isNotEmpty(collection)) {
            for (E e : collection) {
                map.put(keyMapper.apply(e), valueMapper.apply(e));
            }
        }
        return map;
    }

    /**
     * [J8代码简化] 过滤一个集合并收集为List
     *
     * @param <E> 集合元素类型
     * @param collection 集合
     * @param predicate 过滤条件
     */
    public static <E> List<E> filterToList(Collection<E> collection, Predicate<? super E> predicate) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().filter(predicate).collect(Collectors.toList());
        }
        return newArrayList();
    }

    /**
     * [J8代码简化] 过滤一个集合并收集为SET
     *
     * @param <E> 集合元素类型
     * @param collection 集合
     * @param predicate 过滤条件
     */
    public static <E> Set<E> filterToSet(Collection<E> collection, Predicate<? super E> predicate) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().filter(predicate).collect(Collectors.toSet());
        }
        return newHashSet();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素作为List
     *
     * @param collection 集合
     * @param mapper 定义每个元素如何进行收集的接口
     */
    public static <E, R> List<R> mapToList(Collection<? extends E> collection,
            Function<? super E, ? extends R> mapper) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().map(mapper).collect(Collectors.toList());
        }
        return newArrayList();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素作为List
     *
     * @param <E> 集合元素类型
     * @param <R> 返回值元素类型
     * @param collection 集合
     * @param mapper 定义每个元素如何进行收集的接口
     */
    public static <E, R> List<R> mapToList(E[] collection, Function<? super E, ? extends R> mapper) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return mapToList(newArrayList(collection), mapper);
        }
        return newArrayList();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素作为List，并去重（使用Object.equal）
     *
     * @param collection 集合
     * @param mapper 定义每个元素如何进行收集的接口
     */
    public static <E, R> List<R> mapDistinctToList(Collection<? extends E> collection,
            Function<? super E, ? extends R> mapper) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().map(mapper).distinct().collect(Collectors.toList());
        }
        return newArrayList();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素作为List，并去重（使用Object.equal）
     *
     * @param <E> 集合元素类型
     * @param <R> 返回值元素类型
     * @param collection 集合
     * @param mapper 定义每个元素如何进行收集的接口
     */
    public static <E, R> List<R> mapDistinctToList(E[] collection, Function<? super E, ? extends R> mapper) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return mapDistinctToList(newArrayList(collection), mapper);
        }
        return newArrayList();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素作为Set
     *
     * @param <E> 元素类型
     * @param <R> 返回值类型
     * @param collection 集合
     * @param mapper 字段获取方法
     */
    public static <E, R> Set<R> mapToSet(Collection<? extends E> collection, Function<? super E, ? extends R> mapper) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().map(mapper).collect(Collectors.toSet());
        }
        return newHashSet();
    }

    /**
     * [J8代码简化] 收集集合中的某些元素集合作为List
     *
     * @param <E> 元素类型
     * @param <R> 返回值类型
     * @param collection 集合
     * @param mapper 定义每个元素集合如何进行收集的接口
     */
    public static <E, R> List<R> mapAllToList(Collection<? extends E> collection,
            Function<? super E, List<? extends R>> mapper) {
        List<R> list = newArrayList();
        if (EmptyUtil.isNotEmpty(collection)) {
            forEach(collection, k -> list.addAll(mapper.apply(k)));
        }
        return list;
    }

    /**
     * [J8代码简化] 判断集合中是否存在任意一个符合的元素
     *
     * @param collection 集合，若为空返回false
     * @param predicate 判断条件
     */
    public static <E> boolean anyMatch(Collection<E> collection, Predicate<? super E> predicate) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().anyMatch(predicate);
        }
        return false;
    }

    /**
     * [J8代码简化] 判断集合中是否存在任意一个符合的元素
     *
     * @param collection 集合，若为空返回false
     * @param predicate 判断条件
     */
    public static <E> boolean anyMatch(E[] collection, Predicate<? super E> predicate) {
        if (EmptyUtil.isNotEmpty(collection))
            for (E e : collection)
                if (predicate.test(e))
                    return true;
        return false;
    }

    /**
     * [J8代码简化] 判断集合中是否不存在任意一个符合的元素
     *
     * @param collection 集合，若为空返回True
     * @param predicate 判断条件
     */
    public static <E> boolean noneMatch(Collection<E> collection, Predicate<? super E> predicate) {
        if (EmptyUtil.isNotEmpty(collection)) {
            return collection.stream().noneMatch(predicate);
        }
        return true;
    }

    /**
     * 取集合中最佳的一条，可以是最大值最小值或者其他，方法内部取第一条作为初始条件，遍历集合所有元素进行比较<br>
     * 一般{@link Collections#max},或者{@link Collections#min} 等若需要传入比较器的非简单数值比较时，可以使用该方法代替实现会更清晰
     *
     * @param collection 集合
     * @param compare 比较器，返回两条中的一条，该条会作为下次比较的第一参数
     * @return 若集合为空返回null，否则根据比较器返回最佳的元素
     */
    public static <E> E best(Collection<? extends E> collection, BinaryOperator<E> compare) {
        if (EmptyUtil.isEmpty(collection)) {
            return null;
        }
        Iterator<? extends E> i = collection.iterator();
        E candidate = i.next();
        while (i.hasNext()) {
            candidate = compare.apply(candidate, i.next());
        }
        return candidate;
    }

    /**
     * 判断目标是否在集合之内，若为null使用==进行判断，若不为null使用equal进行判断。<br>
     * 如
     * <p/>
     * IN(10) = false <br>
     * IN(10, 20,30,40) = false <br>
     * IN(10, 20,10,30) = true <br>
     * IN(null, 10,20,30) = false<br>
     * IN(null, 20,null,30) = true <br>
     * </p>
     *
     * @param target 目标
     * @param collect 集合
     */
    @SafeVarargs
    public static <E> boolean in(E target, E... collect) {
        if (EmptyUtil.isNotEmpty(collect)) {
            final Predicate<E> predicate = target == null ? Objects::isNull : target::equals;
            for (E e : collect)
                if (predicate.test(e))
                    return true;
        }
        return false;
    }

    /**
     * 判断目标是否不在集合之内，若为null使用==进行判断，若不为null使用equal进行判断。<br>
     *
     * @param <E> 元素类型
     * @param target 目标
     * @param collect 集合
     * @see CollectionUtil#in(Object, Object...)
     */
    @SafeVarargs
    public static <E> boolean notIn(E target, E... collect) {
        return !in(target, collect);
    }

    /**
     * 根据条件去重，若存在重复，则以下标从小到大取其第一条数据作为返回值，使用如下：
     * <p/>
     * persons = CollectionUtil.distinct(persons, k -> k.getName())
     * <p/>
     *
     * @param list 集合
     * @param keyMapper 条件获取器
     * @return 若集合为空包含null，返回空集合
     */
    public static <E> List<E> distinct(List<? extends E> list, Function<? super E, ?> keyMapper) {
        return distinct(list, keyMapper, null);
    }

    /**
     * 根据条件去重，若存在重复，则以mergeMapper进行合并处理，若元素本身为null,则不会触发mergeMapper<br>
     * 先遍历的对象会优先出现在mergeMapper的第一参数<br>
     * 如参数集合为[a1, b, c, d, a2], 则 mergeMapper.apply(a1, a2)
     *
     * @param list 集合
     * @param keyMapper 条件获取器
     * @param mergeMapper （可选） 合并处理器，根据两个元素返回处理后的元素，若处理器为NULL，等效于 {@link #distinct(List, Function)}
     * @return 若集合为空包含null，返回空集合
     * @see #distinct(List, Function)
     */
    public static <E> List<E> distinct(List<? extends E> list, Function<? super E, ?> keyMapper,
            BinaryOperator<E> mergeMapper) {
        if (EmptyUtil.isNotEmpty(list)) {
            LinkedHashMap<? super Object, E> linkedHashMap = new LinkedHashMap<>((int) (list.size() * 0.75));
            forEach(list, k -> {
                Object key = keyMapper.apply(k);
                E element = linkedHashMap.get(key);
                if (element == null) {
                    linkedHashMap.put(key, k);
                } else if (mergeMapper != null) {
                    linkedHashMap.put(key, mergeMapper.apply(element, k));
                }
            });
            return new ArrayList<>(linkedHashMap.values());
        }
        return newArrayList();
    }

    /**
     * 对一个Map进行排序，返回排序后的流
     *
     * @param <K> 键
     * @param <V> 值
     * @param map Map
     * @param comparator 排序方法
     */
    public static <K, V> Stream<Entry<K, V>> sort(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
        if (EmptyUtil.isNotEmpty(map)) {
            return map.entrySet().stream().sorted(comparator);
        }
        return Stream.empty();
    }

    /**
     * 检查一个集合是否包含重复项
     *
     * @param <E> 元素类型
     * @param collection 集合
     */
    public static <E> boolean containsDuplicate(Collection<? extends E> collection) {
        Set<E> container = new HashSet<>(collection.size());
        for (E element : collection) {
            if (container.contains(element)) {
                return true;
            }
            container.add(element);
        }
        return false;
    }

    /**
     * [J8代码简化] 根据条件将集合分割为两个集合，若所有元素都不满足，对应集合返回为空集合。<br>
     *
     * @param <E> 集合元素类型
     * @param collection 集合
     * @param predicate 判断条件，符合条件的为返回分割容器的第一集合，不符合的为第二集合
     */
    public static <E> Partition<E> partitionBy(Collection<? extends E> collection, Predicate<? super E> predicate) {
        List<E> first = new ArrayList<>();
        List<E> second = new ArrayList<>();
        if (EmptyUtil.isNotEmpty(collection)) {
            collection.forEach(k -> {
                if (predicate.test(k)) {
                    first.add(k);
                } else {
                    second.add(k);
                }
            });
        }
        return new Partition<>(first, second);
    }

    /**
     * [J8代码简化] 根据条件将集合分割为两个集合，若所有元素都不满足，对应集合返回为空集合，若一个元素两个条件都满足，则两个集合中都会包含该元素。<br>
     *
     * @param <E> 集合元素类型
     * @param collection 集合
     * @param firstPredicate 第一集合条件，对应返回分割容器中的第一集合
     * @param secondPredicate 第二集合条件，对应返回分割容器的第二集合
     * @see {@link Partition}
     */
    public static <E> Partition<E> partitionBy(Collection<? extends E> collection, Predicate<? super E> firstPredicate,
            Predicate<? super E> secondPredicate) {
        List<E> first = new ArrayList<>();
        List<E> second = new ArrayList<>();
        if (EmptyUtil.isNotEmpty(collection)) {
            collection.forEach(k -> {
                if (firstPredicate.test(k)) {
                    first.add(k);
                }
                if (secondPredicate.test(k)) {
                    second.add(k);
                }
            });
        }
        return new Partition<>(first, second);
    }

    /**
     * 用于存储分类出的两个集合，一般符合条件/符合第一个条件的分为first集合，不符合条件/符合第二个条件的分为second集合
     *
     * @param <E> 集合元素类型
     */
    public static class Partition<E> {
        private final List<E> first;
        private final List<E> second;

        public Partition(List<E> first, List<E> second) {
            this.first = first;
            this.second = second;
        }

        public List<E> getFirst() {
            return first;
        }

        public List<E> getSecond() {
            return second;
        }
    }
}
