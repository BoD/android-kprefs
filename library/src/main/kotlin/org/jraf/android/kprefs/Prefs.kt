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
import androidx.lifecycle.LiveData
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Key(val name: String)

class Prefs(
    context: Context,
    fileName: String? = null,
    fileMode: Int = Context.MODE_PRIVATE
) {
    private val sharedPreferences: SharedPreferences =
        if (fileName != null) context.getSharedPreferences(fileName, fileMode) else PreferenceManager.getDefaultSharedPreferences(context)


    fun Boolean(key: String? = null): ReadWriteProperty<Any, Boolean?> = NullablePreferenceProperty(
        sharedPreferences, key,
        { k -> getBoolean(k, false) },
        { k, v -> putBoolean(k, v) }
    )

    fun Boolean(default: Boolean, key: String? = null): ReadWriteProperty<Any, Boolean> = NonNullPreferenceProperty(
        sharedPreferences, key,
        { k -> getBoolean(k, default) },
        { k, v -> putBoolean(k, v) }
    )

    fun BooleanLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Boolean>> = NullablePreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getBoolean(k, false)
    }

    fun BooleanLiveData(default: Boolean, key: String? = null): ReadOnlyProperty<Any, LiveData<Boolean>> =
        NonNullPreferenceLiveDataProperty(sharedPreferences, key) { k ->
            getBoolean(k, default)
        }


    fun String(key: Key? = null): ReadWriteProperty<Any, String?> = NullablePreferenceProperty(
        sharedPreferences, key?.name,
        { k -> getString(k, "") },
        { k, v -> putString(k, v) }
    )

    fun String(default: String, key: Key? = null): ReadWriteProperty<Any, String> = NonNullPreferenceProperty(
        sharedPreferences, key?.name,
        { k -> getString(k, default) },
        { k, v -> putString(k, v) }
    )

    fun StringLiveData(key: Key? = null): ReadOnlyProperty<Any, LiveData<String>> = NullablePreferenceLiveDataProperty(sharedPreferences, key?.name) { k ->
        getString(k, "")
    }

    fun StringLiveData(default: String, key: Key? = null): ReadOnlyProperty<Any, LiveData<String>> =
        NonNullPreferenceLiveDataProperty(sharedPreferences, key?.name) { k ->
            getString(k, default)
        }


    fun Int(key: String? = null): ReadWriteProperty<Any, Int?> = NullablePreferenceProperty(
        sharedPreferences, key,
        { k -> getInt(k, 0) },
        { k, v -> putInt(k, v) }
    )

    fun Int(default: Int, key: String? = null): ReadWriteProperty<Any, Int> = NonNullPreferenceProperty(
        sharedPreferences, key,
        { k -> getInt(k, default) },
        { k, v -> putInt(k, v) }
    )

    fun IntLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Int>> = NullablePreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getInt(k, 0)
    }

    fun IntLiveData(default: Int, key: String? = null): ReadOnlyProperty<Any, LiveData<Int>> = NonNullPreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getInt(k, default)
    }


    fun Float(key: String? = null): ReadWriteProperty<Any, Float?> = NullablePreferenceProperty(
        sharedPreferences, key,
        { k -> getFloat(k, 0F) },
        { k, v -> putFloat(k, v) }
    )

    fun Float(default: Float, key: String? = null): ReadWriteProperty<Any, Float> = NonNullPreferenceProperty(
        sharedPreferences, key,
        { k -> getFloat(k, default) },
        { k, v -> putFloat(k, v) }
    )

    fun FloatLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Float>> = NullablePreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getFloat(k, 0F)
    }

    fun FloatLiveData(default: Float, key: String? = null): ReadOnlyProperty<Any, LiveData<Float>> =
        NonNullPreferenceLiveDataProperty(sharedPreferences, key) { k ->
            getFloat(k, default)
        }


    fun Long(key: String? = null): ReadWriteProperty<Any, Long?> = NullablePreferenceProperty(
        sharedPreferences, key,
        { k -> getLong(k, 0) },
        { k, v -> putLong(k, v) }
    )

    fun Long(default: Long, key: String? = null): ReadWriteProperty<Any, Long> = NonNullPreferenceProperty(
        sharedPreferences, key,
        { k -> getLong(k, default) },
        { k, v -> putLong(k, v) }
    )

    fun LongLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Long>> = NullablePreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getLong(k, 0)
    }

    fun LongLiveData(default: Long, key: String? = null): ReadOnlyProperty<Any, LiveData<Long>> =
        NonNullPreferenceLiveDataProperty(sharedPreferences, key) { k ->
            getLong(k, default)
        }


    fun StringSet(key: String? = null): ReadWriteProperty<Any, Set<String>?> = NullablePreferenceProperty(
        sharedPreferences, key,
        { k -> getStringSet(k, setOf()) },
        { k, v -> putStringSet(k, v) }
    )

    fun StringSet(default: Set<String>, key: String? = null): ReadWriteProperty<Any, Set<String>> = NonNullPreferenceProperty(
        sharedPreferences, key,
        { k -> getStringSet(k, default) },
        { k, v -> putStringSet(k, v) }
    )

    fun StringSetLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Set<String>>> = NullablePreferenceLiveDataProperty(sharedPreferences, key) { k ->
        getStringSet(k, setOf())
    }

    fun StringSetLiveData(default: Set<String>, key: String? = null): ReadOnlyProperty<Any, LiveData<Set<String>>> =
        NonNullPreferenceLiveDataProperty(sharedPreferences, key) { k ->
            getStringSet(k, default)
        }


    internal companion object {
        internal fun getKey(property: KProperty<*>, key: String?) = key ?: property.name
    }
}