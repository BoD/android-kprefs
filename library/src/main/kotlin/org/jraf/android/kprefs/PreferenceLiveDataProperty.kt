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
import androidx.lifecycle.MutableLiveData
import org.jraf.android.kprefs.Prefs.Companion.getKey
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private class NonNullPreferenceLiveData<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : MutableLiveData<T>() {

    private val listener = OnSharedPreferenceChangeListener { _, key ->
        if (this.key == key) super.setValue(getPreferenceValue())
    }

    private fun getPreferenceValue(): T {
        return if (!sharedPreferences.contains(key)) default else sharedPreferences.getter(this.key, default)!!
    }

    override fun onActive() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        val preferenceValue = getPreferenceValue()
        if (preferenceValue != value) super.setValue(preferenceValue)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun setValue(value: T) {
        updateSharedPreference(value)
    }

    override fun postValue(value: T) {
        updateSharedPreference(value)
    }

    private fun updateSharedPreference(value: T) {
        sharedPreferences.edit().apply {
            setter(key, value)
            apply()
        }
    }
}

private class NullablePreferenceLiveData<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : MutableLiveData<T?>() {

    private val listener = OnSharedPreferenceChangeListener { _, key ->
        if (this.key == key) super.setValue(getPreferenceValue())
    }

    private fun getPreferenceValue(): T? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getter(this.key, default)
    }

    override fun onActive() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        val preferenceValue = getPreferenceValue()
        if (preferenceValue != value) super.setValue(preferenceValue)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun setValue(value: T?) {
        updateSharedPreference(value)
    }

    override fun postValue(value: T?) {
        updateSharedPreference(value)
    }

    private fun updateSharedPreference(value: T?) {
        sharedPreferences.edit().apply {
            if (value == null) {
                remove(key)
            } else {
                setter(key, value)
            }
            apply()
        }
    }
}

internal class NonNullPreferenceLiveDataProperty<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadOnlyProperty<Any, MutableLiveData<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): MutableLiveData<T> {
        return NonNullPreferenceLiveData(sharedPreferences, getKey(property, key), default, getter, setter)
    }
}

internal class NullablePreferenceLiveDataProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadOnlyProperty<Any, MutableLiveData<T?>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): MutableLiveData<T?> {
        return NullablePreferenceLiveData(sharedPreferences, getKey(property, key), default, getter, setter)
    }
}
