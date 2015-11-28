package org.hibernate.validator.internal.util;

import android.support.v4.util.LruCache;

import java.io.Serializable;
import java.util.*;

/**
 * Implementation of the ConcurrentReferenceHashMap API, using Android's LruCache.
 *
 * Created by kat on 11/27/15.
 */
final public class LruCacheHashMap<K, V> implements java.util.concurrent.ConcurrentMap<K, V>, Serializable {

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

    @Override
    public boolean isEmpty() {
        return lruCache.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return lruCache.get((K)key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return lruCache.get((K)key);
    }

    @Override
    public int size() {
        return lruCache.size();
    }

    @Override
    public V put(K key, V value) {
        // LruCache does not allow null keys or values
        return lruCache.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return lruCache.remove((K)key);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        // LruCache does not allow null keys or values
        return lruCache.put(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        V current = lruCache.get((K)key);
        if (current == value) {
            return lruCache.remove((K)key) != null;
        } else {
            return false;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    // Removes the entry for the specified key only if it is currently mapped to the specified value.
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        V current = lruCache.get(key);
        if (current == oldValue) {
            return lruCache.put(key, newValue) == oldValue;
        } else {
            return false;
        }
    }

    // Replaces the entry for the specified key only if it is currently mapped to some value.
    @Override
    public V replace(K key, V value) {
        V current = lruCache.get(key);
        if (current != null) {
            return lruCache.put(key, value);
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }

    @Override
    public Set<K> keySet() {
        return lruCache.snapshot().keySet();
    }

    @Override
    public Collection<V> values() {
        return lruCache.snapshot().values();
    }

    @Override
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
