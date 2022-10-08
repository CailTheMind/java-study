package com.xzc.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xzc
 */
public class ImmutableExample {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(map);
        unmodifiableMap.put("a", 1);
    }
}