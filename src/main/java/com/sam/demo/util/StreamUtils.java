package com.sam.demo.util;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;


public class StreamUtils {

    /**
     * Distinct by key predicate.
     *
     * @param <T>          the type parameter
     * @param keyExtractor the key extractor
     * @return the predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
