package com.toni.base;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class JacksonJsonConverter implements JsonConverter {
    private static final Log logger = LogFactory.getLog(JacksonJsonConverter.class);
    private static JacksonJsonConverter instance = null;
    private ObjectMapper mapper = new ObjectMapper();

    private JacksonJsonConverter() {
    }

    public static JacksonJsonConverter getInstance() {
        if (instance == null) {
            instance = new JacksonJsonConverter();
        }

        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }

    public Object fromJson(String objectAsString, Class<?> tipoObjeto) throws IOException {
        return this.mapper.readValue(objectAsString, tipoObjeto);
    }

    public Object fromJsonView(String objectAsString, Class<?> view) throws IOException {
        this.mapper.disable(new MapperFeature[]{MapperFeature.DEFAULT_VIEW_INCLUSION});
        return this.mapper.readerWithView(view).readValue(objectAsString);
    }

    public Object fromJson(String objectAsString, JavaType tipoObjeto) throws IOException {
        return this.mapper.readValue(objectAsString, tipoObjeto);
    }

    public String toJson(Object object) throws IOException {
        return this.mapper.writeValueAsString(object);
    }

    public String toJson(Object object, Class<?> view) throws IOException {
        this.mapper.disable(new MapperFeature[]{MapperFeature.DEFAULT_VIEW_INCLUSION});
        return this.mapper.writerWithView(view).writeValueAsString(object);
    }
}