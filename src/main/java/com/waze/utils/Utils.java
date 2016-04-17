package com.waze.utils;

import com.fasterxml.jackson.databind.JsonNode;

public final class Utils {

    private Utils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    public static String getStringOrNull(String key, JsonNode node){
        return node.has(key) ? node.get(key).asText() : null ;
    }

    public static String getX2StringOrNull(String key, String key2, JsonNode node){
        if (node.has(key)){
            JsonNode newNode = node.get(key);
            return newNode.has(key2) ? newNode.get(key2).asText() : null ;
        }else return null;
    }

    public static int getIntOrNull(String key, JsonNode node){
        return node.has(key) ? node.get(key).asInt() : null ;
    }
}
