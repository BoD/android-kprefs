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
package org.jraf.android.kprefs.sample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.jraf.android.kprefs.sample.R
import org.jraf.android.kprefs.sample.prefs.MainPrefs
import org.jraf.android.kprefs.sample.prefs.SettingsPrefs
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val mainPrefs by lazy { MainPrefs(this) }
    private val settingsPrefs by lazy { SettingsPrefs(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mainPrefs.passwordLiveData.observe(this, Observer {
            Log.d(TAG, "observed password=$it")
        })

        mainPrefs.premiumLiveData.observe(this, Observer {
            Log.d(TAG, "observed premium=$it")
        })

        mainPrefs.login = "john"
        mainPrefs.password = "p4Ssw0Rd ${Date()}"
        mainPrefs.age = null

        with(mainPrefs) {
            Log.d(TAG, "login=$login")
            Log.d(TAG, "password=$password")
            Log.d(TAG, "age=$age")
            Log.d(TAG, "premium=$premium")
        }

        with(settingsPrefs) {
            Log.d(TAG, "preferredColor=$preferredColor")
            Log.d(TAG, "weekDays=$weekDays")
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
