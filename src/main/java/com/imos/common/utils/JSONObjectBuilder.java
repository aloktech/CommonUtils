/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author alok.meher
 */
public final class JSONObjectBuilder {

    private final JSONObject json = new JSONObject();

    public JSONObjectBuilder addInteger(String key, int value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addDouble(String key, double value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addBoolean(String key, boolean value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addString(String key, String value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addCollection(String key, Collection<?> value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addMap(String key, Map<String, ?> value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addJSONObject(String key, JSONObject value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObjectBuilder addJSONArray(String key, JSONArray value) {
        json.put(key, value);
        return this;
    }
    
    public JSONObject toJSONObject() {
        return json;
    }
    
    public String toJSONString() {
        return json.toString();
    }
    
    public String toJSONString(int indent) {
        return json.toString(indent);
    }
}
