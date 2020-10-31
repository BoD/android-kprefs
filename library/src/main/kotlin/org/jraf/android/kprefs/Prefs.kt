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
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Key(val name: String)

@Suppress("FunctionName", "unused", "MemberVisibilityCanBePrivate")
class Prefs(
    val sharedPreferences: SharedPreferences
) {
    constructor(
        context: Context,
        fileName: String? = null,
        fileMode: Int = Context.MODE_PRIVATE
    ) : this(
        if (fileName != null) context.getSharedPreferences(
            fileName,
            fileMode
        ) else PreferenceManager.getDefaultSharedPreferences(context)
    )

    // region Boolean

    fun Boolean(key: Key? = null): ReadWriteProperty<Any, Boolean?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        false,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun Boolean(default: Boolean, key: Key? = null): ReadWriteProperty<Any, Boolean> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun BooleanLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Boolean?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        false,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun BooleanLiveData(default: Boolean, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Boolean>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun BooleanFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<Boolean?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        false,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    fun BooleanFlow(default: Boolean, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<Boolean>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

    // endregion


    // region String

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
        { k, defValue -> getString(k, defValue)!! },
        SharedPreferences.Editor::putString
    )

    fun StringLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<String?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        "",
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    fun StringLiveData(default: String, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<String>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    fun StringFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<String?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        "",
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    fun StringFlow(default: String, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<String>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

    // endregion


    // region Int

    fun Int(key: Key? = null): ReadWriteProperty<Any, Int?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    fun Int(default: Int, key: Key? = null): ReadWriteProperty<Any, Int> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    fun IntLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Int?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )


    fun IntLiveData(default: Int, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Int>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    fun IntFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<Int?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )


    fun IntFlow(default: Int, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<Int>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

    // endregion


    // region Float

    fun Float(key: Key? = null): ReadWriteProperty<Any, Float?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        0F,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun Float(default: Float, key: Key? = null): ReadWriteProperty<Any, Float> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun FloatLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Float?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        0F,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun FloatLiveData(default: Float, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Float>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun FloatFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<Float?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        0F,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    fun FloatFlow(default: Float, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<Float>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat
    )

    // endregion


    // region Long

    fun Long(key: Key? = null): ReadWriteProperty<Any, Long?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    fun Long(default: Long, key: Key? = null): ReadWriteProperty<Any, Long> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    fun LongLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Long?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )


    fun LongLiveData(default: Long, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Long>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    fun LongFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<Long?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        0,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )


    fun LongFlow(default: Long, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<Long>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

    // endregion


    // region Set<String>

    fun StringSet(key: Key? = null): ReadWriteProperty<Any, Set<String>?> = NullablePreferenceProperty(
        sharedPreferences,
        key?.name,
        setOf(),
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSet(default: Set<String>, key: Key? = null): ReadWriteProperty<Any, Set<String>> = NonNullPreferenceProperty(
        sharedPreferences,
        key?.name,
        default,
        { k, defValue -> getStringSet(k, defValue)!! },
        SharedPreferences.Editor::putStringSet
    )

    fun StringSetLiveData(key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Set<String>?>> = NullablePreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        setOf(),
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSetLiveData(default: Set<String>, key: Key? = null): ReadOnlyProperty<Any, MutableLiveData<Set<String>>> = NonNullPreferenceLiveDataProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSetFlow(key: Key? = null): ReadOnlyProperty<Any, Flow<Set<String>?>> = NullablePreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        setOf(),
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    fun StringSetFlow(default: Set<String>, key: Key? = null): ReadOnlyProperty<Any, MutableStateFlow<Set<String>>> = NonNullPreferenceFlowProperty(
        sharedPreferences,
        key?.name,
        default,
        SharedPreferences::getStringSet,
        SharedPreferences.Editor::putStringSet
    )

    // endregion


    internal companion object {
        internal fun getKey(property: KProperty<*>, key: String?) = key ?: property.name
    }
}
