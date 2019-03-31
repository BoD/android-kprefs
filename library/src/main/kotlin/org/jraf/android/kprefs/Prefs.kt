/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2019-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jraf.android.kprefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Key(val name: String)

class Prefs(
    context: Context,
    fileName: String? = null,
    fileMode: Int = Context.MODE_PRIVATE
) {
    val sharedPreferences: SharedPreferences =
        if (fileName != null) context.getSharedPreferences(fileName, fileMode) else PreferenceManager.getDefaultSharedPreferences(context)

    private fun getKey(property: KProperty<*>, key: String?) = key ?: property.name

    private inner class NullablePrefsProperty<T>(
        private val key: String?,
        private val getter: SharedPreferences.(String) -> T,
        private val setter: SharedPreferences.Editor.(String, T) -> Unit
    ) : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            val key = getKey(property, key)
            if (!sharedPreferences.contains(key)) return null
            return sharedPreferences.getter(key)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            val key = getKey(property, key)
            val editor = sharedPreferences.edit()
            if (value == null) {
                editor.remove(key)
            } else {
                editor.setter(key, value)
            }
            editor.apply()
        }
    }

    private inner class NonNullPrefsProperty<T>(
        private val key: String?,
        private val getter: SharedPreferences.(String) -> T,
        private val setter: SharedPreferences.Editor.(String, T) -> Unit
    ) : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return sharedPreferences.getter(getKey(property, key))
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            sharedPreferences.edit().apply {
                setter(getKey(property, key), value)
                apply()
            }
        }
    }


    fun Boolean(key: String? = null): ReadWriteProperty<Any, Boolean?> = NullablePrefsProperty(key,
        { k -> getBoolean(k, false) },
        { k, v -> putBoolean(k, v) }
    )

    fun Boolean(default: Boolean, key: String? = null): ReadWriteProperty<Any, Boolean> = NonNullPrefsProperty(key,
        { k -> getBoolean(k, default) },
        { k, v -> putBoolean(k, v) }
    )


    fun String(key: Key? = null): ReadWriteProperty<Any, String?> = NullablePrefsProperty(key?.name,
        { k -> getString(k, "") },
        { k, v -> putString(k, v) }
    )

    fun String(default: String, key: Key? = null): ReadWriteProperty<Any, String> = NonNullPrefsProperty(key?.name,
        { k -> getString(k, default) },
        { k, v -> putString(k, v) }
    )


    fun Int(key: String? = null): ReadWriteProperty<Any, Int?> = NullablePrefsProperty(key,
        { k -> getInt(k, 0) },
        { k, v -> putInt(k, v) }
    )

    fun Int(default: Int, key: String? = null): ReadWriteProperty<Any, Int> = NonNullPrefsProperty(key,
        { k -> getInt(k, default) },
        { k, v -> putInt(k, v) }
    )


    fun Float(key: String? = null): ReadWriteProperty<Any, Float?> = NullablePrefsProperty(key,
        { k -> getFloat(k, 0F) },
        { k, v -> putFloat(k, v) }
    )

    fun Float(default: Float, key: String? = null): ReadWriteProperty<Any, Float> = NonNullPrefsProperty(key,
        { k -> getFloat(k, default) },
        { k, v -> putFloat(k, v) }
    )


    fun Long(key: String? = null): ReadWriteProperty<Any, Long?> = NullablePrefsProperty(key,
        { k -> getLong(k, 0) },
        { k, v -> putLong(k, v) }
    )

    fun Long(default: Long, key: String? = null): ReadWriteProperty<Any, Long> = NonNullPrefsProperty(key,
        { k -> getLong(k, default) },
        { k, v -> putLong(k, v) }
    )


    fun StringSet(key: String? = null): ReadWriteProperty<Any, Set<String>?> = NullablePrefsProperty(key,
        { k -> getStringSet(k, mutableSetOf()) },
        { k, v -> putStringSet(k, v) }
    )

    fun StringSet(default: Set<String>, key: String? = null): ReadWriteProperty<Any, Set<String>> = NonNullPrefsProperty(key,
        { k -> getStringSet(k, default) },
        { k, v -> putStringSet(k, v) }
    )
}