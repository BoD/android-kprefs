# KPrefs: pref like a winner™!

A very small library (~400 loc) for Android/Kotlin to reduce
shared preferences boilerplate.

_This is a much lighter, Kotlin specific, followup to my [Prefs lib](https://github.com/BoD/android-prefs)
which was annotation processor based whereas this one is
Kotlin delegates based._

## Usage
### 1/ Add the dependencies to your project

```groovy
dependencies {
    /* ... */
    implementation 'org.jraf:kprefs:1.7.1'
}
```
_Note: the artifact is hosted on Maven Central since v1.6.0 - it used to be hosted on JCenter before this version_

### 2/ Define your preferences
Create a `Prefs` instance and pass it a `Context`.  Optionally pass it a `fileName`, and a `fileMode`.

You can also instead pass a `SharedPreferences` which is handy for instance when using the androidx security-crypto library.

```kotlin
    private val mainPrefs = Prefs(context)
    
    private val settingPrefs = Prefs(
        context,
        fileName = "settings_prefs",
        fileMode = Context.MODE_PRIVATE
    )

    private val encryptedPrefs = Prefs(getEncryptedSharedPreferences())
    
    // (...)
```

Then declare your `val`s or `var`s using delegates on the `Prefs` instance:

```kotlin
    var login by prefs.String()

    var password by prefs.String(Key(KEY_PASSWORD))
    val passwordLiveData by prefs.StringLiveData(Key(KEY_PASSWORD))
    val passwordFlow by prefs.StringFlow(Key(KEY_PASSWORD))

    var premium by prefs.Boolean(false)
    var age by prefs.Int()
    var preferredColor by prefs.Int(0xFF0000)
    var weekDays by prefs.StringSet(setOf("Friday", "Saturday"))
```

Currently, the available types are:
- Boolean
- Float
- Integer
- Long
- String
- StringSet

And they can be exposed as:
- Raw type
- `MutableLiveData`
- `MutableStateFlow`

👉 If you pass a `default` value, the attribute type will be non nullable.<br>
In the example above, `age` is `Int?` whereas `preferredColor` is `Int`.

👉 Optionally pass a `key` parameter, which will be used
to store the preference (useful when migrating from already used preferences).
By default the attribute name is used.

### 3/ Be a winner!
Simply use your class like this:
```kotlin
    private val mainPrefs by lazy { MainPrefs(this) }
    // (...)
    
    // Get a preference
    val login = mainPrefs.login
    
    // Put a preference
    mainPrefs.password = "p4Ssw0Rd"
    
    // Observe a preference, with LiveData
    mainPrefs.passwordLiveData.observe(this) {
        log("observed password=$it")
    }

    // Update a preference, with LiveData
    mainPrefs.passwordLiveData.value = "qwerty"

    // Observe a preference, with Flow
    mainPrefs.passwordFlow.onEach {
        Log.d(TAG, "observed password=$it")
    }.launchIn(scope)

    // Update a preference, with Flow
    mainPrefs.passwordFlow.value = "zxcvbn"
```

You can also have a look at the [sample](sample/).

## License

```
Copyright (C) 2019-present Benoit 'BoD' Lubek (BoD@JRAF.org)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
