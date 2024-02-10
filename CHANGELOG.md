# Changelog

## v1.7.2 (2024-02-10)
- Only listen to sharedPreferences when a Flow has at least one subscription. This also fixes the R8 optimization issue for good.

## v1.7.1 (2024-02-10)
- Fix an issue with an R8 optimization that was causing observers to not be called.
- Update dependencies to latest versions:
    - Kotlin 1.9.22
    - Coroutines 1.7.3
    - LiveData 2.7.0

## v1.7.0 (2023-05-18)
- Update dependencies to latest versions:
    - Kotlin 1.8.21
    - Coroutines 1.7.1
    - LiveData 2.6.1

## v1.6.0 (2021-02-20)
- Flow wrappers are now MutableStateFlow.
- Library is now published to Maven Central instead of JCenter

## v1.5.0 (2020-10-31)
- Update dependencies to latest versions
- \[Breaking change] Use of `Key` for all types instead of only for String (for consistency)

## v1.4.0 (2020-10-04)
LiveData wrappers are now MutableLiveData.

## v1.3.0 (2020-05-08)
Allow passing a SharedPreference to make the lib compatible with the androidx security-crypto library (and any other SharedPreference wrapper).

## v1.2.0 (2020-05-03)
Support Flow

## v1.1.0 (2019-04-13)
Make `Prefs.sharedPreferences` public

## v1.0.0 (2019-04-07)
Initial release
