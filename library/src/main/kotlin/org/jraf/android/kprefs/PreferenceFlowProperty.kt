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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jraf.android.kprefs.Prefs.Companion.getKey
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private abstract class WrappedMutableStateFlow<T> : MutableStateFlow<T> {
    private val wrapped by lazy { MutableStateFlow(getInitialValue()) }

    override val replayCache: List<T> get() = wrapped.replayCache

    override val subscriptionCount: StateFlow<Int> get() = wrapped.subscriptionCount

    override var value: T
        get() = wrapped.value
        set(value) {
            wrapped.value = value
        }

    override suspend fun collect(collector: FlowCollector<T>) = wrapped.collect(collector)

    override fun compareAndSet(expect: T, update: T): Boolean = wrapped.compareAndSet(expect, update)

    override suspend fun emit(value: T) = wrapped.emit(value)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun resetReplayCache() = wrapped.resetReplayCache()

    override fun tryEmit(value: T): Boolean = wrapped.tryEmit(value)

    abstract fun getInitialValue(): T
}

private class NonNullPreferenceFlow<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
) : WrappedMutableStateFlow<T>() {

    private val listener = OnSharedPreferenceChangeListener { _, key ->
        if (this.key == key) super.value = getPreferenceValue()
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun getPreferenceValue(): T {
        return if (!sharedPreferences.contains(key)) default else sharedPreferences.getter(this.key, default)!!
    }

    private fun updateSharedPreference(value: T) {
        sharedPreferences.edit().apply {
            setter(key, value)
            apply()
        }
    }

    override var value: T
        get() = super.value
        set(value) {
            updateSharedPreference(value)
        }

    override fun getInitialValue() = getPreferenceValue()
}

private class NullablePreferenceFlow<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : WrappedMutableStateFlow<T?>() {

    private val listener = OnSharedPreferenceChangeListener { _, key ->
        if (this.key == key) super.value = getPreferenceValue()
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun getPreferenceValue(): T? {
        return if (!sharedPreferences.contains(key)) null else sharedPreferences.getter(this.key, default)
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

    override var value: T?
        get() = super.value
        set(value) {
            updateSharedPreference(value)
        }

    override fun getInitialValue() = getPreferenceValue()
}


internal class NonNullPreferenceFlowProperty<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadOnlyProperty<Any, MutableStateFlow<T>> {
    private var value: MutableStateFlow<T>? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): MutableStateFlow<T> {
        if (value == null) {
            value = NonNullPreferenceFlow(sharedPreferences, getKey(property, key), default, getter, setter)
        }
        return value!!
    }
}

internal class NullablePreferenceFlowProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadOnlyProperty<Any, MutableStateFlow<T?>> {
    private var value: MutableStateFlow<T?>? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): MutableStateFlow<T?> {
        if (value == null) {
            value = NullablePreferenceFlow(sharedPreferences, getKey(property, key), default, getter, setter)
        }
        return value!!
    }
}
