/*
 *
 *    Copyright 2016 zhaosc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.limi88.financialplanner.tool;

import android.database.Cursor;

/**
 * Cursor Tool
 *
 * @author zhaosc
 * @version 1.0.0
 * @since 2016-04-18
 */
public final class CursorTool {

    /**
     * 代表boolean false.
     */
    public static final int BOOLEAN_FALSE = 0;

    /**
     * 代表boolean true.
     */
    public static final int BOOLEAN_TRUE = 1;

    private CursorTool() {
        //not called
    }

    /**
     * 通过cursor获得字段columnName对应的string.
     *
     * @param cursor     cursor
     * @param columnName 字段名称
     * @return String
     */
    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * 通过cursor获得字段columnName对应的boolean.
     *
     * @param cursor     cursor
     * @param columnName 字段名称
     * @return boolean
     */
    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    /**
     * 通过cursor获得字段columnName对应的long.
     *
     * @param cursor     cursor
     * @param columnName 字段名称
     * @return long
     */
    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * 通过cursor获得字段columnName对应的int.
     *
     * @param cursor     cursor
     * @param columnName 字段名称
     * @return int
     */
    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
