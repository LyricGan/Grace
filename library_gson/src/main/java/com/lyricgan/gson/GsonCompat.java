package com.lyricgan.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.lyricgan.gson.adapter.BooleanTypeAdapter;
import com.lyricgan.gson.adapter.CollectionTypeAdapterFactory;
import com.lyricgan.gson.adapter.DoubleTypeAdapter;
import com.lyricgan.gson.adapter.FloatTypeAdapter;
import com.lyricgan.gson.adapter.IntegerTypeAdapter;
import com.lyricgan.gson.adapter.LongTypeAdapter;
import com.lyricgan.gson.adapter.NumberTypeAdapterHelper;
import com.lyricgan.gson.adapter.ReflectiveTypeAdapterFactory;
import com.lyricgan.gson.adapter.ShortTypeAdapter;
import com.lyricgan.gson.adapter.StringTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public class GsonCompat {

    private GsonCompat() {
    }

    public static Gson build() {
        GsonBuilder builder = new GsonBuilder();
        try {
            Class<?> builderClass = builder.getClass();
            Field field = builderClass.getDeclaredField("instanceCreators");
            field.setAccessible(true);
            Map<Type, InstanceCreator<?>> creatorMap = (Map<Type, InstanceCreator<?>>) field.get(builder);
            ConstructorConstructor constructorConstructor = new ConstructorConstructor(creatorMap);
            // 此处需要注意添加顺序
            builder.registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(constructorConstructor));
            builder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(constructorConstructor));
            builder.registerTypeAdapterFactory(new MapTypeAdapterFactory(constructorConstructor, false));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 添加默认的解析器
        builder.registerTypeAdapterFactory(TypeAdapters.JSON_ELEMENT_FACTORY);
        builder.registerTypeAdapterFactory(ObjectTypeAdapter.FACTORY);
        // type adapters for basic platform types
        builder.registerTypeAdapterFactory(TypeAdapters.STRING_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.INTEGER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.BOOLEAN_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.BYTE_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.SHORT_FACTORY);
        TypeAdapter<Number> longAdapter = NumberTypeAdapterHelper.longAdapter(LongSerializationPolicy.DEFAULT);
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, NumberTypeAdapterHelper.doubleAdapter(false)));
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, NumberTypeAdapterHelper.floatAdapter(false)));
        builder.registerTypeAdapterFactory(TypeAdapters.NUMBER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(AtomicLong.class, NumberTypeAdapterHelper.atomicLongAdapter(longAdapter)));
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(AtomicLongArray.class, NumberTypeAdapterHelper.atomicLongArrayAdapter(longAdapter)));
        builder.registerTypeAdapterFactory(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.CHARACTER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.STRING_BUILDER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.STRING_BUFFER_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        builder.registerTypeAdapterFactory(TypeAdapters.URL_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.URI_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.UUID_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.CURRENCY_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.LOCALE_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.INET_ADDRESS_FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.BIT_SET_FACTORY);
        builder.registerTypeAdapterFactory(DateTypeAdapter.FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.CALENDAR_FACTORY);
        builder.registerTypeAdapterFactory(TimeTypeAdapter.FACTORY);
        builder.registerTypeAdapterFactory(SqlDateTypeAdapter.FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.TIMESTAMP_FACTORY);
        builder.registerTypeAdapterFactory(ArrayTypeAdapter.FACTORY);
        builder.registerTypeAdapterFactory(TypeAdapters.CLASS_FACTORY);
        // type adapters for composite and user-defined types
        builder.registerTypeAdapterFactory(TypeAdapters.ENUM_FACTORY);

        // 添加自定义解析器
        builder.registerTypeAdapter(String.class, new StringTypeAdapter());
        builder.registerTypeAdapter(int.class, new IntegerTypeAdapter());
        builder.registerTypeAdapter(float.class, new FloatTypeAdapter());
        builder.registerTypeAdapter(double.class, new DoubleTypeAdapter());
        builder.registerTypeAdapter(long.class, new LongTypeAdapter());
        builder.registerTypeAdapter(boolean.class, new BooleanTypeAdapter());
        builder.registerTypeAdapter(short.class, new ShortTypeAdapter());

        return builder.create();
    }
}
