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

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData
import org.jraf.android.kprefs.Prefs.Companion.getKey
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private class NonNullPreferenceLiveData<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : LiveData<T>() {

    private val listener = OnSharedPreferenceChangeListener { _, _ ->
        if (this.key == key) value = getPreferenceValue()
    }

    private fun getPreferenceValue(): T {
        return if (!sharedPreferences.contains(key)) default else sharedPreferences.getter(this.key, default)!!
    }

    override fun onActive() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        val preferenceValue = getPreferenceValue()
        if (preferenceValue != value) value = preferenceValue
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

private class NullablePreferenceLiveData<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : LiveData<T?>() {

    private val listener = OnSharedPreferenceChangeListener { _, _ ->
        if (this.key == key) value = getPreferenceValue()
    }

    private fun getPreferenceValue(): T? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getter(this.key, default)
    }

    override fun onActive() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        val preferenceValue = getPreferenceValue()
        if (preferenceValue != value) value = preferenceValue
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

internal class NonNullPreferenceLiveDataProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : ReadOnlyProperty<Any, LiveData<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): LiveData<T> {
        return NonNullPreferenceLiveData(sharedPreferences, getKey(property, key), default, getter)
    }
}

internal class NullablePreferenceLiveDataProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : ReadOnlyProperty<Any, LiveData<T?>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): LiveData<T?> {
        return NullablePreferenceLiveData<T>(sharedPreferences, getKey(property, key), default, getter)
    }
}
