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
    val sharedPreferences: SharedPreferences =
        if (fileName != null) context.getSharedPreferences(fileName, fileMode) else PreferenceManager.getDefaultSharedPreferences(context)


    // Boolean

    fun Boolean(key: String? = null): ReadWriteProperty<Any, Boolean?> = NullablePreferenceProperty(
        sharedPreferences,
        key,
        false,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun Boolean(default: Boolean, key: String? = null): ReadWriteProperty<Any, Boolean> = NonNullPreferenceProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun BooleanLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Boolean>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key,
        false,
        SharedPreferences::getBoolean
    )

    fun BooleanLiveData(default: Boolean, key: String? = null): ReadOnlyProperty<Any, LiveData<Boolean>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getBoolean
    )


    // String

    fun String(key: Key? = null): ReadWriteProperty<Any, String?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        "",
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    fun String(default: String, key: Key? = null): ReadWriteProperty<Any, String> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    fun StringLiveData(key: Key? = null): ReadOnlyProperty<Any, LiveData<String>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        "",
        SharedPreferences::getString
    )

    fun StringLiveData(default: String, key: Key? = null): ReadOnlyProperty<Any, LiveData<String>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getString
    )


    // Int

    fun Int(key: String? = null): ReadWriteProperty<Any, Int?> = NullablePreferenceProperty(
        sharedPreferences,
        key,
        0,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    fun Int(default: Int, key: String? = null): ReadWriteProperty<Any, Int> = NonNullPreferenceProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    fun IntLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Int>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key,
        0,
        SharedPreferences::getInt
    )

    fun IntLiveData(default: Int, key: String? = null): ReadOnlyProperty<Any, LiveData<Int>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getInt
    )


    // Float

    fun Float(key: String? = null): ReadWriteProperty<Any, Float?> = NullablePreferenceProperty(
        sharedPreferences,
        key,
        0F,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun Float(default: Float, key: String? = null): ReadWriteProperty<Any, Float> = NonNullPreferenceProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun FloatLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Float>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key,
        0F,
        SharedPreferences::getFloat
    )

    fun FloatLiveData(default: Float, key: String? = null): ReadOnlyProperty<Any, LiveData<Float>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getFloat
    )


    // Long

    fun Long(key: String? = null): ReadWriteProperty<Any, Long?> = NullablePreferenceProperty(
        sharedPreferences,
        key,
        0,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    fun Long(default: Long, key: String? = null): ReadWriteProperty<Any, Long> = NonNullPreferenceProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    fun LongLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Long>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key,
        0,
        SharedPreferences::getLong
    )

    fun LongLiveData(default: Long, key: String? = null): ReadOnlyProperty<Any, LiveData<Long>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getLong
    )


    // Set<String>

    fun StringSet(key: String? = null): ReadWriteProperty<Any, Set<String>?> = NullablePreferenceProperty(
        sharedPreferences,
        key,
        setOf(),
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSet(default: Set<String>, key: String? = null): ReadWriteProperty<Any, Set<String>> = NonNullPreferenceProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSetLiveData(key: String? = null): ReadOnlyProperty<Any, LiveData<Set<String>>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key,
        setOf(),
        SharedPreferences::getStringSet
    )

    fun StringSetLiveData(default: Set<String>, key: String? = null): ReadOnlyProperty<Any, LiveData<Set<String>>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key,
        default,
        SharedPreferences::getStringSet
    )


    internal companion object {
        internal fun getKey(property: KProperty<*>, key: String?) = key ?: property.name
    }
}