package com.zjclugger.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by King.Zi on 2020/4/9.
 */
public class SharedPreferencesUtils {
    private static final String FILE_NAME_DEFAULT = "erp_data";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;

    private Context mContext;
    private String mFileName;

    public SharedPreferencesUtils(Context context) {
        this(context, FILE_NAME_DEFAULT);
    }

    public SharedPreferencesUtils(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }

    public void put(String key, Object value) {
        put(key, value, false);
    }

    /**
     * 保存数据<br>
     * 根据保存数据的具体类型，调用不同的保存方法
     *
     * @param key
     * @param value
     * @param sync  同步提交修改
     */
    public void put(String key, Object value, boolean sync) {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName, PREFERENCES_MODE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SharedPreferencesCompat.apply(editor, sync);
    }

    /**
     * 获取指定Key的值<br>
     * 根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object get(String key, Object defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName, PREFERENCES_MODE);

        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     * @param sync 同步提交修改
     */
    public void remove(String key, boolean sync) {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName, PREFERENCES_MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor, sync);
    }

    /**
     * 清除所有数据
     *
     * @param sync 同步提交修改
     */
    public void clear(boolean sync) {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName,
                PREFERENCES_MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor, sync);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName,
                PREFERENCES_MODE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName, PREFERENCES_MODE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到并且明显指定不使用同步方式修改，则使用apply执行，否则使用commit;<br>
         * apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘;<br>
         * commit是同步的提交到硬件磁盘
         *
         * @param editor
         * @param synchronous 同步提交修改
         */
        public static void apply(SharedPreferences.Editor editor, boolean synchronous) {
            try {
                if (sApplyMethod != null && !synchronous) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}