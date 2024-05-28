package com.toni.base;

import java.io.IOException;

public interface JsonConverter {
    Object fromJson(String var1, Class<?> var2) throws IOException;

    String toJson(Object var1) throws IOException;

    String toJson(Object var1, Class<?> var2) throws IOException;
}
