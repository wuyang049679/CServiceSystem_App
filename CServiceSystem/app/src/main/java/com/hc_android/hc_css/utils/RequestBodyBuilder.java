package com.hc_android.hc_css.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Title:
 * Description:
 *
 * @author wuyang
 * date 2019/8/15
 */
public class RequestBodyBuilder {




    public static final class Builder {


        HashMap<String, String> map = new HashMap<>();
        HashMap<String, Object> maps = new HashMap<>();

        public Builder() {
        }

        private Builder(Builder builder) {
        }

        public Builder params(String key, String value) {
            map.put(key, value);
            maps.put(key, value);
            return this;
        }
        public Builder params(String key, int value) {
            maps.put(key, value);
            return this;
        }
        public Builder params(HashMap<String, String> values) {
            Iterator<String> iterator = values.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                map.put(next, values.get(next));
                maps.put(next, values.get(next));
            }
            return this;
        }
        public Builder params_Map(HashMap<String, Object> values) {
            Iterator<String> iterator = values.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                maps.put(next, values.get(next));
            }
            return this;
        }



        public HashMap<String, String> getMap() {
            return map;
        }
        public HashMap<String, Object> getMaps() {
            return maps;
        }
        public RequestBody build() {
            Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
            String s = gson2.toJson(map);
            Log.i("RequestBody", "build: " + s);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
            return body;
        }
        public RequestBody build_Map() {
            Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
            String s = gson2.toJson(maps);
            Log.i("RequestBody_map", "build: " + s);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
            return body;
        }

        public RequestBody build(Object o){

            Gson gson=new Gson();
            String json = gson.toJson(o);
            Log.i("RequestBody", "build(object): " + json);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            return body;
        }
    }
}