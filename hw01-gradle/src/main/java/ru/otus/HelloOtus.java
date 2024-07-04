package ru.otus;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public class HelloOtus {
    public static void main(String[] args) {

        Map<Integer, String> mutableMap = new HashMap<>();
        mutableMap.put(1, "One");
        mutableMap.put(2, "Two");
        mutableMap.put(3, "Three");

        ImmutableMap<Integer, String> immutableMap = ImmutableMap.copyOf(mutableMap);
        StringBuilder sb = new StringBuilder();
        for (String value : immutableMap.values()) {
            sb.append(value);
        }
        System.out.println("sb = " + sb);
    }

}
