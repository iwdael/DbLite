package com.hacknife.onlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

class SharedPreferencesLite extends OnLite<SharedPreferences> {

    public static final String TABLE_NAME = "shared_preferences_map";

    public static final int VERSION = 1;

    public static final String value = "shared_preferences_value";

    public static final String key = "shared_preferences_key";

    public SharedPreferencesLite() {
        tableName = "shared_preferences_map";
        version = 1;
    }

    @Override
    protected String createTable() {
        return "CREATE TABLE IF NOT EXISTS shared_preferences_map(" + /**
         * column: shared_preferences_value
         * class: {@link SharedPreferences}
         * field: {@link SharedPreferences.value}
         * */
                "shared_preferences_value varchar(255) DEFAULT NULL," + /**
         * column: shared_preferences_key
         * class: {@link SharedPreferences}
         * field: {@link SharedPreferences.key}
         * */
                "shared_preferences_key varchar(255) UNIQUE NOT NULL" + ")";
    }

    @Override
    protected SharedPreferences createObject(Cursor cursor) {
        SharedPreferences sharedPreferences = new SharedPreferences();
        sharedPreferences.setValue(cursor.getString(cursor.getColumnIndex("shared_preferences_value")));
        sharedPreferences.setKey(cursor.getString(cursor.getColumnIndex("shared_preferences_key")));
        return sharedPreferences;
    }

    @Override
    protected ContentValues createContentValues(SharedPreferences sharedPreferences) {
        if (sharedPreferences == null)
            return null;
        ContentValues values = new ContentValues();
        if (sharedPreferences.getValue() != null)
            values.put("shared_preferences_value", sharedPreferences.getValue());
        if (sharedPreferences.getKey() != null)
            values.put("shared_preferences_key", sharedPreferences.getKey());
        return values;
    }

    @Override
    protected String createSelection(SharedPreferences where) {
        if (where == null)
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append("1 = 1 ");
        if (where.getValue() != null)
            builder.append("and shared_preferences_value = ? ");
        if (where.getKey() != null)
            builder.append("and shared_preferences_key = ? ");
        return builder.toString();
    }

    @Override
    protected String[] createSelectionArgv(SharedPreferences where) {
        if (where == null)
            return null;
        List<String> list = new ArrayList();
        if (where.getValue() != null)
            list.add(String.valueOf(where.getValue()));
        if (where.getKey() != null)
            list.add(String.valueOf(where.getKey()));
        return list.toArray(new String[0]);
    }
}
