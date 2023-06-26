package org.projectsw.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A one-to-one hashmap implementation that allows mapping between keys and values.
 * @param <K> The type of keys.
 * @param <V> The type of values.
 */
public class OneToOneHashmap<K,V> {
    private final Map<K, V> forwardMap;
    private final Map<V, K> reverseMap;
    private ArrayList<K> allKey;
    private ArrayList<V> allValue;

    /**
     * Constructs a new OneToOneHashmap.
     */
    public OneToOneHashmap() {
        forwardMap = new HashMap<>();
        reverseMap = new HashMap<>();
        allValue = new ArrayList<>();
        allKey = new ArrayList<>();
    }

    /**
     * Associates the specified key with the specified value in the hashmap.
     * @param key   The key to be associated.
     * @param value The value to be associated.
     */
    public void put(K key, V value) {
        forwardMap.put(key, value);
        reverseMap.put(value, key);
        allKey.add(key);
        allValue.add(value);
    }

    /**
     * Removes the mapping for the specified key from the hashmap.
     * @param key The key to be removed.
     */
    public void removeByKey(K key) {
        V value = forwardMap.remove(key);
        if (value != null) {
            reverseMap.remove(value);
        }
        allKey.remove(key);
        allValue.remove(value);
    }

    /**
     * Removes the mapping for the specified value from the hashmap.
     * @param value The value to be removed.
     */
    public void removeByValue(V value) {
        K key = reverseMap.remove(value);
        if (key != null) {
            forwardMap.remove(key);
        }
        allKey.remove(key);
        allValue.remove(value);
    }

    /**
     * Clears all the mappings in the hashmap.
     */
    public void clear(){
        forwardMap.clear();
        reverseMap.clear();
    }

    /**
     * Returns an ArrayList containing all the keys in the hashmap.
     * @return An ArrayList of keys.
     */
    public ArrayList<K> getAllKey(){
        return allKey;
    }

    /**
     * Returns an ArrayList containing all the values in the hashmap.
     * @return An ArrayList of values.
     */
    public ArrayList<V> getAllValue(){
        return allValue;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if the key is not present in the hashmap.
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or null if the key is not present.
     */
    public V getValue(K key) {
        return forwardMap.get(key);
    }

    /**
     * Returns the key to which the specified value is mapped, or null if the value is not present in the hashmap.
     * @param value The value whose associated key is to be returned.
     * @return The key to which the specified value is mapped, or null if the value is not present.
     */
    public K getKey(V value) {
        return reverseMap.get(value);
    }
}
