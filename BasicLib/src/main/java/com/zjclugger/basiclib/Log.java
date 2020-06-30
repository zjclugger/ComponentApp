package com.zjclugger.basiclib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

public final class Log {
    public static Printer t(@Nullable String tag) {
        return Logger.t(tag);
    }

    public static void addLogAdapter(@NonNull LogAdapter adapter) {
        Logger.addLogAdapter(adapter);
    }

    public static void log(int priority, @Nullable String tag, @Nullable String message,
                           @Nullable Throwable throwable) {
        Logger.log(priority, tag, message, throwable);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        Logger.d(message, args);
    }

    public static void d(@Nullable Object object) {
        Logger.d(object);
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        Logger.e(message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message,
                         @Nullable Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        Logger.i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        Logger.v(message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        Logger.wtf(message, args);
    }

    public static void json(@Nullable String json) {
        Logger.json(json);
    }

    public static void xml(@Nullable String xml) {
        Logger.xml(xml);
    }

    public static void D(@Nullable String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).d(message, args);
    }

    public static void I(@Nullable String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).i(message, args);
    }

    public static void E(@Nullable String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).e(message, args);
    }

    public static void W(@Nullable String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).w(message, args);
    }

    public static void V(@Nullable String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).v(message, args);
    }
}
