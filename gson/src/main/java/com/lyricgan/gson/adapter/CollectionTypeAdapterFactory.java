package com.lyricgan.gson.adapter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 自定义json集合类解析
 * @author Lyric Gan
 */
public class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);
        // create() doesn't define a type parameter
        return new Adapter(gson, elementType, elementTypeAdapter, constructor);
    }

    private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        Adapter(Gson context, Type elementType, TypeAdapter<E> elementTypeAdapter, ObjectConstructor<? extends Collection<E>> constructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(context, elementTypeAdapter, elementType);
            this.constructor = constructor;
        }

        @Override
        public Collection<E> read(JsonReader in) throws IOException {
            // 判断json数组是否为空
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                // 将null转换为返回空数组
                return constructor.construct();
            }
            if (in.peek() == JsonToken.STRING) {
                in.nextString();
                return constructor.construct();
            }
            if (in.peek() == JsonToken.BEGIN_OBJECT) {
                in.beginObject();
                in.endObject();
                return constructor.construct();
            }

            Collection<E> collection = constructor.construct();
            in.beginArray();
            while (in.hasNext()) {
                E instance = elementTypeAdapter.read(in);
                if (instance != null) {
                    collection.add(instance);
                } else {
                    logErrorMessage("read error: json arrays contains null object");
                }
            }
            in.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter out, Collection<E> collection) throws IOException {
            if (collection == null) {
                out.nullValue();
                return;
            }
            out.beginArray();
            for (E element : collection) {
                if (element != null) {
                    elementTypeAdapter.write(out, element);
                } else {
                    logErrorMessage("write error: json arrays contains null object");
                }
            }
            out.endArray();
        }

        private void logErrorMessage(String message) {
            Log.e("Gson", message);
        }
    }
}

