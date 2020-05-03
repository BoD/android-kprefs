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

@file:OptIn(ExperimentalCoroutinesApi::class)

package org.jraf.android.kprefs

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.jraf.android.kprefs.Prefs.Companion.getKey
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private fun <T> nonNullPreferenceFlow(
    sharedPreferences: SharedPreferences,
    key: String,
    default: T,
    getter: SharedPreferences.(String, T) -> T?
): Flow<T> = callbackFlow {
    fun offer() = offer(if (!sharedPreferences.contains(key)) default else sharedPreferences.getter(key, default)!!)
    offer()

    val listener = OnSharedPreferenceChangeListener { _, _ ->
        offer()
    }
    sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    awaitClose {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

private fun <T> nullablePreferenceFlow(
    sharedPreferences: SharedPreferences,
    key: String,
    default: T,
    getter: SharedPreferences.(String, T) -> T?
): Flow<T?> = callbackFlow {
    fun offer() = offer(if (!sharedPreferences.contains(key)) null else sharedPreferences.getter(key, default))
    offer()

    val listener = OnSharedPreferenceChangeListener { _, _ ->
        offer()
    }
    sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    awaitClose {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}


internal class NonNullPreferenceFlowProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : ReadOnlyProperty<Any, Flow<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Flow<T> {
        return nonNullPreferenceFlow(sharedPreferences, getKey(property, key), default, getter)
    }
}

internal class NullablePreferenceFlowProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String?,
    private val default: T,
    private val getter: SharedPreferences.(String, T) -> T?
) : ReadOnlyProperty<Any, Flow<T?>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Flow<T?> {
        return nullablePreferenceFlow(sharedPreferences, getKey(property, key), default, getter)
    }
}
