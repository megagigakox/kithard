package pl.kithard.core.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public final class CollectionSerializer {

    private CollectionSerializer() {}

    public static String serializeCollection(Set<String> collection) {
        if (collection.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : collection) {
            stringBuilder.append(string).append("@");
        }
        return stringBuilder.toString();
    }

    public static Set<String> deserializeCollection(String serializedData) {
        if (serializedData == null || serializedData.isEmpty()) {
            return new HashSet<>();
        }
        Set<String> set = new HashSet<>();
        String[] split = serializedData.split("@");
        Collections.addAll(set, split);
        return set;
    }

    public static String serializeMap(Map<String, Integer> map) {
        if (map.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }

        return stringBuilder.toString();
    }

    public static Map<String, Integer> deserializeMap(String serializedData) {
        if (serializedData == null || serializedData.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Integer> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(mapSplit[0], Integer.parseInt(mapSplit[1]));
            }
        }

        return map;
    }

    public static String serializeMapInteger(Map<Integer, String> map) {
        if (map.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }

        return stringBuilder.toString();
    }

    public static Map<Integer, String> deserializeMapInteger(String serializedData) {
        if (serializedData == null || serializedData.isEmpty()) {
            return new HashMap<>();
        }

        Map<Integer, String> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(Integer.valueOf(mapSplit[0]), mapSplit[1]);
            }
        }

        return map;
    }

    public static String serializeMapLongString(Map<Long, String> map) {
        if (map.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }

        return stringBuilder.toString();
    }

    public static Map<Long, String> deserializeMapLongString(String serializedData) {
        if (serializedData == null || serializedData.isEmpty()) {
            return new HashMap<>();
        }

        Map<Long, String> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(Long.valueOf(mapSplit[0]), mapSplit[1]);
            }
        }

        return map;
    }

    public static String serializeMapLong(Map<String, Long> map) {
        if (map.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }
        return stringBuilder.toString();
    }

    public static Map<String, Long> deserializeMapLong(String serializedData) {
        if (StringUtils.isEmpty(serializedData)) {
            return new HashMap<>();
        }

        Map<String, Long> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(mapSplit[0], Long.parseLong(mapSplit[1]));
            }
        }
        return map;
    }
}
