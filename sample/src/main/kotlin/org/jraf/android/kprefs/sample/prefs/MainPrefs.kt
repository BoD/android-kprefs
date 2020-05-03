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
package org.jraf.android.kprefs.sample.prefs

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.jraf.android.kprefs.Key
import org.jraf.android.kprefs.Prefs

class MainPrefs(context: Context) {
    private val prefs = Prefs(context)

    var login: String? by prefs.String()
    var password: String? by prefs.String(Key(KEY_PASSWORD))
    val passwordLiveData: LiveData<String?> by prefs.StringLiveData(Key(KEY_PASSWORD))
    val passwordFlow: Flow<String?> by prefs.StringFlow(Key(KEY_PASSWORD))
    var premium: Boolean by prefs.Boolean(false)
    val premiumLiveData: LiveData<Boolean> by prefs.BooleanLiveData(false)
    var age: Int? by prefs.Int()

    companion object {
        private const val KEY_PASSWORD = "PASSWORD"
    }
}
