package org.projectsw.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class OneToOneHashmap<K,V> {
    private final Map<K, V> forwardMap;
    private final Map<V, K> reverseMap;
    private ArrayList<K> allKey;
    private ArrayList<V> allValue;

    public OneToOneHashmap() {
        forwardMap = new HashMap<>();
        reverseMap = new HashMap<>();
        allValue = new ArrayList<>();
        allKey = new ArrayList<>();
    }
    public void put(K key, V value) {
        forwardMap.put(key, value);
        reverseMap.put(value, key);
        allKey.add(key);
        allValue.add(value);
    }
    public ArrayList<K> getAllKey(){
        return allKey;
    }
    public ArrayList<V> getAllValue(){
        return allValue;
    }
    public V getValue(K key) {
        return forwardMap.get(key);
    }
    public K getKey(V value) {
        return reverseMap.get(value);
    }
}
