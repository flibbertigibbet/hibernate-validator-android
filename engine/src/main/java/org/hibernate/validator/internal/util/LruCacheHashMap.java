package org.hibernate.validator.internal.util;

import android.support.v4.util.LruCache;

import java.util.*;

/**
 * Implementation of the ConcurrentReferenceHashMap API, using Android's LruCache.
 *
 * Created by kat on 11/27/15.
 */
final public class LruCacheHashMap<K, V> {

    /**
     * The default size for this table, in MiB,
     * used when not otherwise specified in a constructor.
     */
    static final int DEFAULT_SIZE_MB = 4 * 1024 * 1024; // 4MiB

    private LruCache<K, V> lruCache;

    public LruCacheHashMap(int initialSize) {
        lruCache = new LruCache(initialSize);
    }

    public LruCacheHashMap() {
        this(DEFAULT_SIZE_MB);
    }

    public boolean isEmpty() {
        return lruCache.size() == 0;
    }

    public int size() {
        return lruCache.size();
    }

    public V get(K key) {
        return lruCache.get(key);
    }

    public boolean containsKey(K key) {
        return lruCache.get(key) != null;
    }

    public V put(K key, V value) {
        // LruCache does not allow null keys or values
        return lruCache.put(key, value);
    }

    public V putIfAbsent(K key, V value) {
        // LruCache does not allow null keys or values
        return lruCache.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    public V remove(K key) {
        return lruCache.remove(key);
    }

    public boolean remove(K key, V value) {
        V current = lruCache.get(key);
        if (current == value) {
            return lruCache.remove(key) != null;
        } else {
            return false;
        }
    }

    // Removes the entry for the specified key only if it is currently mapped to the specified value.
    public boolean replace(K key, V oldValue, V newValue) {
        V current = lruCache.get(key);
        if (current == oldValue) {
            return lruCache.put(key, newValue) == oldValue;
        } else {
            return false;
        }
    }

    // Replaces the entry for the specified key only if it is currently mapped to some value.
    public V replace(K key, V value) {
        V current = lruCache.get(key);
        if (current != null) {
            return lruCache.put(key, value);
        } else {
            return null;
        }
    }

    public void clear() {
        lruCache.evictAll();
    }

    public Set<K> keySet() {
        return lruCache.snapshot().keySet();
    }

    public Collection<V> values() {
        return lruCache.snapshot().values();
    }

    public Set<Map.Entry<K,V>> entrySet() {
        return lruCache.snapshot().entrySet();
    }

    public Enumeration<V> elements() {
        return Collections.enumeration(lruCache.snapshot().values());
    }

    public Enumeration<K> keys() {
        return Collections.enumeration(lruCache.snapshot().keySet());
    }

}
