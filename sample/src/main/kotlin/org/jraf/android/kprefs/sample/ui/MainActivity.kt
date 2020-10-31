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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jraf.android.kprefs.sample.R
import org.jraf.android.kprefs.sample.prefs.EncryptedPrefs
import org.jraf.android.kprefs.sample.prefs.MainPrefs
import org.jraf.android.kprefs.sample.prefs.SettingsPrefs
import java.util.Date
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val mainPrefs by lazy { MainPrefs(this) }
    private val settingsPrefs by lazy { SettingsPrefs(this) }
    private val encryptedPrefs by lazy { EncryptedPrefs(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        with(mainPrefs) {
            passwordLiveData.observe(this@MainActivity) {
                Log.d(TAG, "passwordLiveData=$it")
            }

            premiumLiveData.observe(this@MainActivity) {
                Log.d(TAG, "premiumLiveData=$it")
            }

            login = "john"
            password = "p4Ssw0Rd ${Date()}"
            age = null

            Log.d(TAG, "login=$login")
            Log.d(TAG, "password=$password")
            Log.d(TAG, "age=$age")
            Log.d(TAG, "premium=$premium")
            premiumLiveData.value = true
            Log.d(TAG, "premium=$premium")
            premiumLiveData.value = false
            Log.d(TAG, "premium=$premium")
            premiumLiveData.postValue(true)
            Log.d(TAG, "premium=$premium")
        }

        with(settingsPrefs) {
            Log.d(TAG, "preferredColor=$preferredColor")
            preferredColor = Random.nextInt()
            Log.d(TAG, "preferredColor=$preferredColor")
            Log.d(TAG, "weekDays=$weekDays")
            weekDays = setOf("a", "b", "c")
            Log.d(TAG, "weekDays=$weekDays")
        }

        with(encryptedPrefs) {
            Log.d(TAG, "encryptedSecret=$encryptedSecret")
            encryptedSecret = "new secret ${Date()}"
            Log.d(TAG, "encryptedSecret=$encryptedSecret")
        }

        GlobalScope.launch {
            mainPrefs.passwordFlow.onEach {
                Log.d(TAG, "passwordFlow=$it")
            }.launchIn(this)

            delay(1000)
            mainPrefs.password = "p4Ssw0Rd ${Date()}"
            delay(1000)
            mainPrefs.password = "p4Ssw0Rd ${Date()}"
        }

        GlobalScope.launch {
            mainPrefs.premiumFlow.onEach {
                Log.d(TAG, "premiumFlow=$it")
            }.launchIn(this)

            delay(1200)
            mainPrefs.premiumFlow.value = false
            delay(1200)
            mainPrefs.premiumFlow.value = true
            delay(1200)
            mainPrefs.premiumFlow.value = false
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
